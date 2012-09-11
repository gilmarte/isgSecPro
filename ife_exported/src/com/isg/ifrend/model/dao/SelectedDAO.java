package com.isg.ifrend.model.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.isg.ifrend.model.bean.ActionType;
import com.isg.ifrend.model.bean.Code;
import com.isg.ifrend.model.bean.CodeType;
import com.isg.ifrend.model.bean.Criteria;
import com.isg.ifrend.model.bean.Element;
import com.isg.ifrend.model.bean.EnhancedCriterion;
import com.isg.ifrend.model.bean.Function;
import com.isg.ifrend.model.bean.HistCriteria;
import com.isg.ifrend.model.bean.HistEnhancedCriteria;
import com.isg.ifrend.model.bean.HstCode;
import com.isg.ifrend.model.bean.HstCodeType;
import com.isg.ifrend.model.bean.HstElement;
import com.isg.ifrend.model.bean.HstLabel;
import com.isg.ifrend.model.bean.HstMli;
import com.isg.ifrend.model.bean.HstScript;
import com.isg.ifrend.model.bean.Label;
import com.isg.ifrend.model.bean.Mli;
import com.isg.ifrend.model.bean.Script;
import com.isg.ifrend.model.bean.StatusType;
import com.isg.ifrend.model.bean.TempCriteria;
import com.isg.ifrend.model.bean.TempEnhancedCriterion;
import com.isg.ifrend.model.bean.TmpCode;
import com.isg.ifrend.model.bean.TmpCodeType;
import com.isg.ifrend.model.bean.TmpElement;
import com.isg.ifrend.model.bean.TmpLabel;
import com.isg.ifrend.model.bean.TmpMli;
import com.isg.ifrend.model.bean.TmpScript;
import com.isg.ifrend.security.utils.SecurityUtils;
import com.isg.ifrend.utils.DateUtil;

public class SelectedDAO  extends HibernateDaoSupport {

	@SuppressWarnings({ "rawtypes" })
	public List findAllRecords(Class className) throws DataAccessException {
		List list = getHibernateTemplate().find("from " + className.getName());
		return list;
	}

	@SuppressWarnings("rawtypes")
	public List searchEntityByCriteria(DetachedCriteria criteria){
		return getHibernateTemplate().findByCriteria(criteria);
	}

	public Object findTemp(Object tempObj, Object id) throws DataAccessException {

		if (TmpElement.class.isInstance(tempObj)){
			return (TmpElement) getHibernateTemplate().load(TmpElement.class, (String)id);

		} else if (TempCriteria.class.isInstance(tempObj)){
			return (TempCriteria) getHibernateTemplate().load(TempCriteria.class, (Integer)id);

		} else if (TmpCodeType.class.isInstance(tempObj)){
			return (TmpCodeType) getHibernateTemplate().load(TmpCodeType.class, (String)id);

		} else if (TmpLabel.class.isInstance(tempObj)){
			return (TmpLabel) getHibernateTemplate().load(TmpLabel.class, (String)id);

		} else if (TmpMli.class.isInstance(tempObj)){
			return (TmpMli) getHibernateTemplate().load(TmpMli.class, (String)id);

		} else if (TmpScript.class.isInstance(tempObj)){
			return (TmpScript) getHibernateTemplate().load(TmpScript.class, (String)id);
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes" })
	public List findAllMasterDataNotDeleted(Object classObj) throws DataAccessException {

		if (Element.class.isInstance(classObj)){
			return getHibernateTemplate().find("from Element where statusID != " + StatusType.DELETED.getId());

		} else if (Criteria.class.isInstance(classObj)){
			return getHibernateTemplate().find("from Criteria where statusID != " + StatusType.DELETED.getId());

		} else if (CodeType.class.isInstance(classObj)){
			return getHibernateTemplate().find("from CodeType where statusID != " + StatusType.DELETED.getId());

		} else if (Label.class.isInstance(classObj)){
			return getHibernateTemplate().find("from Label where statusID != " + StatusType.DELETED.getId());

		} else if (Mli.class.isInstance(classObj)){
			return getHibernateTemplate().find("from Mli where statusID != " + StatusType.DELETED.getId());

		} else if (Script.class.isInstance(classObj)){
			return getHibernateTemplate().find("from Script where statusID != " + StatusType.DELETED.getId());
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public void authorizeEntities(Object obj){

		if (TmpElement.class.isInstance(obj)){
			int final_status = 0;
			TmpElement tempElements = (TmpElement) obj;
			Element element = new Element(tempElements);

			if (element.getStatusID() == StatusType.PENDING.getId()) {
				if ((element.getActionID() == ActionType.ADD.getId())
						|| (element.getActionID() == ActionType.UPDATE.getId()) ) {
					final_status = StatusType.ACTIVE.getId();
				} else if (element.getActionID() == ActionType.DELETE.getId()) {
					final_status = StatusType.DELETED.getId();
				}
			}
			element.setStatusID(final_status);
			if (StatusType.ACTIVE.getId() == final_status) {
				getHibernateTemplate().save(element);
			} else {
				getHibernateTemplate().update(element);
			}

			getHibernateTemplate().delete(tempElements);

			HstElement histElement = new HstElement(tempElements);

			if (histElement.getStatusID() == StatusType.PENDING.getId()) {
				final_status = StatusType.APPROVED.getId();
			}
			
			histElement.setStatusID(final_status);
			getHibernateTemplate().save(histElement);

		} else if (TempCriteria.class.isInstance(obj)){
			int final_status = 0;
			TempCriteria tempCriteria = (TempCriteria) obj;
			Criteria criteria = new Criteria(tempCriteria);

			if ((criteria.getStatusID() == StatusType.PENDING.getId())) {
				if ((criteria.getActionID() == ActionType.ADD.getId()) || (criteria.getActionID() == ActionType.UPDATE.getId()) ) {
					final_status = StatusType.ACTIVE.getId();
				} else if (criteria.getActionID() == ActionType.DELETE.getId()) {
					final_status = StatusType.DELETED.getId();
				}
			}

			criteria.setStatusID(final_status);

			List<EnhancedCriterion> eCriterion = new ArrayList<EnhancedCriterion>();
			if (StatusType.ACTIVE.getId() == final_status){

				List<TempEnhancedCriterion> teCri = this.findChildren(TempEnhancedCriterion.class, tempCriteria.getCriteria_id());
				for (int index=0; index<teCri.size(); index++){
					TempEnhancedCriterion tCri = teCri.get(index);
					EnhancedCriterion eCri = new EnhancedCriterion();
					eCri.setCriteria_id(tCri.getCriteria_id());
					eCri.setElement_id(tCri.getElement_id());
					eCri.setOperator_code(tCri.getOperator_code());
					eCri.setEnhanced_value_character(tCri.getEnhanced_value_character());
					eCri.setEnhanced_value_date(tCri.getEnhanced_value_date());
					eCri.setEnhanced_value_dateformat_id(tCri.getEnhanced_value_dateformat_id());
					eCri.setEnhanced_value_element(tCri.getEnhanced_value_element());
					eCri.setEnhanced_value_integer(tCri.getEnhanced_value_integer());
					eCriterion.add(eCri);
				}

				EnhancedCriterion eCrit = new EnhancedCriterion();
				criteria.setEnhancedCriterionSet(new HashSet<EnhancedCriterion>(eCriterion));
				for (int index=0; index < eCriterion.size(); index++ ){
					EnhancedCriterion ecBean = eCriterion.get(index);
					criteria.getEnhancedCriterionSet().add(ecBean);
					eCrit.setCriteria(criteria);
					criteria.addToEnhancedCriteriaSet(ecBean);
				}

				getHibernateTemplate().save(criteria);
			} else {
				getHibernateTemplate().update(criteria);
			}

			tempCriteria.setTempEnhancedCriterionSet(new HashSet<TempEnhancedCriterion>(this.findChildren(TempEnhancedCriterion.class, tempCriteria.getCriteria_id())));
			getHibernateTemplate().delete(tempCriteria);

			HistCriteria histCriteria = new HistCriteria(tempCriteria);

			if (tempCriteria.getStatusID() == StatusType.PENDING.getId()) {
				final_status = StatusType.APPROVED.getId();
			}

			histCriteria.setStatusID(final_status);
			getHibernateTemplate().save(histCriteria);

		} else if (TmpCodeType.class.isInstance(obj)){
			int final_status = 0;
			TmpCodeType tmpCodeType = (TmpCodeType) obj;
			tmpCodeType.setTmpCodeSet(new HashSet<TmpCode>(this.findChildren(TmpCode.class, tmpCodeType.getCodeTypeID())));

			CodeType codeType = new CodeType(tmpCodeType);

			if (tmpCodeType.getStatusID() == StatusType.PENDING.getId()) {
				
				if ((tmpCodeType.getActionID() == ActionType.ADD.getId()) || (tmpCodeType.getActionID() ==  ActionType.UPDATE.getId())){
					final_status = StatusType.ACTIVE.getId();

				} else if (tmpCodeType.getActionID() ==  ActionType.DELETE.getId()){
					final_status = StatusType.DELETED.getId();
				}
			}
				
			codeType.setStatusID(final_status);
			
			List<Code> codeList = new ArrayList<Code>();
			if (StatusType.ACTIVE.getId() == final_status){

				List<TmpCode> tmpCodeList = this.findChildren(TmpCode.class, tmpCodeType.getCodeTypeID());
				for (int index=0; index<tmpCodeList.size(); index++){
					TmpCode tCode = tmpCodeList.get(index);
					Code code = new Code();

					code.setCodeTypeID(tCode.getCodeTypeID());
					code.setCodeDesc(tCode.getCodeDesc());
					code.setCodeValue(tCode.getCodeValue());
					codeList.add(code);
				}

				Code code = new Code();
				codeType.setCodeSet(new HashSet<Code>(codeList));
				for (int index=0; index < codeList.size(); index++ ){
					Code codeBean = codeList.get(index);
					codeType.getCodeSet().add(codeBean);
					code.setCodeType(codeType);
					codeType.addToCodeSet(codeBean);
				}

				getHibernateTemplate().save(codeType);
			} else {
				getHibernateTemplate().update(codeType);
			}

			getHibernateTemplate().delete(tmpCodeType);

			HstCodeType hstCodeType= new HstCodeType(tmpCodeType);

			final_status = 0;
			
			// Mark Pending to Approved
			if (tmpCodeType.getStatusID() == StatusType.PENDING.getId()) {
				final_status = StatusType.APPROVED.getId();
			}

			hstCodeType.setStatusID(final_status);
			getHibernateTemplate().save(hstCodeType);

		} else if (TmpLabel.class.isInstance(obj)){
			int final_status = 0;
			TmpLabel tmpLabel = (TmpLabel) obj;
			Label label = new Label(tmpLabel);
			
			if (label.getStatusID() == StatusType.PENDING.getId()) {
				if ((label.getActionID() == ActionType.ADD.getId()) || (label.getActionID() ==  ActionType.UPDATE.getId())){
					final_status = StatusType.ACTIVE.getId();
				} else if (label.getActionID() == ActionType.DELETE.getId()) {
					final_status = StatusType.DELETED.getId();
				}
			}
			
			label.setStatusID(final_status);

			if (StatusType.ACTIVE.getId() == final_status){
				getHibernateTemplate().save(label);
			} else {
				getHibernateTemplate().update(label);
			}

			getHibernateTemplate().delete(tmpLabel);

			HstLabel hstLabel = new HstLabel(tmpLabel);
			
			// Mark Pending to Approved
			if (tmpLabel.getStatusID() == StatusType.PENDING.getId()) {
				final_status = StatusType.APPROVED.getId();
			}


			hstLabel.setStatusID(final_status);
			getHibernateTemplate().save(hstLabel);

		} else if (TmpMli.class.isInstance(obj)){
			int final_status = 0;
			TmpMli tmpMli = (TmpMli) obj;
			Mli mli = new Mli(tmpMli);

			if (mli.getStatusID() == StatusType.PENDING.getId()) {
				if ((mli.getActionID() == ActionType.ADD.getId()) || (mli.getActionID() ==  ActionType.UPDATE.getId())){
					final_status = StatusType.ACTIVE.getId();
				} else if (mli.getActionID() == ActionType.DELETE.getId()) {
					final_status = StatusType.DELETED.getId();
				}
			}

			mli.setStatusID(final_status);

			if (StatusType.ACTIVE.getId() == mli.getStatusID()){
				getHibernateTemplate().save(mli);
			} else {
				getHibernateTemplate().update(mli);
			}

			getHibernateTemplate().delete(tmpMli);

			HstMli hstMli = new HstMli(tmpMli);

			// Mark Pending to Approved
			if (tmpMli.getStatusID() == StatusType.PENDING.getId()) {
				final_status = StatusType.APPROVED.getId();
			}
			

			hstMli.setStatusID(final_status);
			getHibernateTemplate().save(hstMli);

		} else if (TmpScript.class.isInstance(obj)){
			int final_status = 0;
			TmpScript tmpScript = (TmpScript) obj;
			Script script = new Script(tmpScript);

			
			if (script.getStatusID() == StatusType.PENDING.getId()) {
				if ((script.getActionID() == ActionType.ADD.getId()) || (script.getActionID() ==  ActionType.UPDATE.getId())){
					final_status = StatusType.ACTIVE.getId();
				} else if (script.getActionID() == ActionType.DELETE.getId()) {
					final_status = StatusType.DELETED.getId();
				}
			}

			script.setStatusID(StatusType.ACTIVE.getId());

			if (StatusType.ACTIVE.getId() == final_status){
				getHibernateTemplate().save(script);
			} else {
				getHibernateTemplate().update(script);
			}

			getHibernateTemplate().delete(tmpScript);

			HstScript hstScript = new HstScript(tmpScript);

			// Mark Pending to Approved
			if (tmpScript.getStatusID() == StatusType.PENDING.getId()) {
				final_status = StatusType.APPROVED.getId();
			}
			
			hstScript.setStatusID(final_status);

			getHibernateTemplate().save(hstScript);

		}
	}

	@SuppressWarnings("unchecked")
	public void requestDeletion(Object obj){

		SecurityUtils securityUtils = SecurityUtils.getSecurityUtilsInstance();
		
		if (Element.class.isInstance(obj)){
			Element element = (Element) obj;
			element.setStatusID(StatusType.PENDING.getId());
			element.setActionID(ActionType.DELETE.getId());
			element.setApproverUserID(securityUtils.getUserName());
			element.setDateApproved(DateUtil.getCurrentDate());
			element.setLastModifierUserID(securityUtils.getUserName());
			element.setDateLastModified(DateUtil.getCurrentDate());

			getHibernateTemplate().update(element);

			TmpElement tempElement = new TmpElement(element);
			tempElement.setNew(false);
			getHibernateTemplate().save(tempElement);

			HstElement histElement = new HstElement(tempElement);
			getHibernateTemplate().save(histElement);


		} else if (Criteria.class.isInstance(obj)){
			Criteria criteria = (Criteria)obj;
			criteria.setStatusID(StatusType.PENDING.getId());
			criteria.setActionID(ActionType.DELETE.getId());
			/* Common - START */
			criteria.setApproverUserID(securityUtils.getUserName());
			criteria.setDateApproved(DateUtil.getCurrentDate());
			criteria.setLastModifierUserID(securityUtils.getUserName());
			criteria.setDateLastModified(DateUtil.getCurrentDate());
			/* Common - END */
			getHibernateTemplate().update(criteria);

			TempCriteria tempCriteria = new TempCriteria(criteria);
			tempCriteria.setNew(false);

			TempEnhancedCriterion tempEnhancedCriterion = new TempEnhancedCriterion();

			List<EnhancedCriterion> ecList = this.findChildren(EnhancedCriterion.class, criteria.getCriteria_id()); 
			List <TempEnhancedCriterion> teList = new ArrayList<TempEnhancedCriterion>();
			List <HistEnhancedCriteria> heList = new ArrayList<HistEnhancedCriteria>();

			for (int index=0; index<ecList.size(); index++){
				EnhancedCriterion eCri = ecList.get(index);
				TempEnhancedCriterion teCriterion = new TempEnhancedCriterion(eCri);
				teList.add(teCriterion);
			}

			tempCriteria.setTempEnhancedCriterionSet(new HashSet<TempEnhancedCriterion>(teList));

			for (int index=0; index < teList.size(); index++ ){
				TempEnhancedCriterion tecBean = teList.get(index);
				tempCriteria.getTempEnhancedCriterionSet().add(tecBean);
				tempEnhancedCriterion.setTempCriteria(tempCriteria);
				tempCriteria.addToTempEnhancedCriteriaSet(tecBean);
			}

			getHibernateTemplate().save(tempCriteria);

			HistCriteria histCriteria = new HistCriteria(tempCriteria);
			HistEnhancedCriteria histEnhancedCriteria = new HistEnhancedCriteria();

			for (int index=0; index<ecList.size(); index++){
				EnhancedCriterion eCri = ecList.get(index);
				HistEnhancedCriteria heCriterion = new HistEnhancedCriteria(new TempEnhancedCriterion(eCri));
				heList.add(heCriterion);
			}

			histCriteria.setHistEnhancedCriteriaSet(new HashSet<HistEnhancedCriteria>(heList));

			for (int index=0; index < heList.size(); index++ ){
				HistEnhancedCriteria hecBean = heList.get(index);
				histCriteria.getHistEnhancedCriteriaSet().add(hecBean);
				histEnhancedCriteria.setHistCriteria(histCriteria);
				histCriteria.addToHistEnhancedCriteriaSet(hecBean);
			}

			getHibernateTemplate().save(histCriteria);


		} else if (CodeType.class.isInstance(obj)){
			CodeType codeType = (CodeType)obj;
			codeType.setStatusID(StatusType.PENDING.getId());
			codeType.setActionID(ActionType.DELETE.getId());
			codeType.setApproverUserID(securityUtils.getUserName());
			codeType.setDateApproved(DateUtil.getCurrentDate());
			codeType.setLastModifierUserID(securityUtils.getUserName());
			codeType.setDateLastModified(DateUtil.getCurrentDate());

			getHibernateTemplate().update(codeType);

			TmpCodeType tmpCodeType = new TmpCodeType(codeType);

			TmpCode tmpCode = new TmpCode();

			List<Code> codeList = this.findChildren(Code.class, codeType.getCodeTypeID()); 
			List <TmpCode> tcodeList = new ArrayList<TmpCode>();
			List <HstCode> hcodeList = new ArrayList<HstCode>();

			for (int index=0; index<codeList.size(); index++){
				Code codeBean = codeList.get(index);
				TmpCode tCode = new TmpCode(codeBean, tmpCodeType);
				tcodeList.add(tCode);
			}

			tmpCodeType.setTmpCodeSet((new HashSet<TmpCode>(tcodeList)));

			for (int index=0; index < tcodeList.size(); index++ ){
				TmpCode tcodeBean = tcodeList.get(index);
				tmpCodeType.getTmpCodeSet().add(tcodeBean);
				tmpCode.setTmpCodeType(tmpCodeType);
				tmpCodeType.addToTmpCodeSet(tcodeBean);
			}

			getHibernateTemplate().merge(tmpCodeType);

			HstCodeType hstCodeType = new HstCodeType(tmpCodeType);
			HstCode hstCode = new HstCode();

			for (int index=0; index<codeList.size(); index++){
				Code codeBean = codeList.get(index);
				HstCode hsCode = new HstCode(new TmpCode(codeBean,tmpCodeType));
				hcodeList.add(hsCode);
			}

			hstCodeType.setHstCodeSet(new HashSet<HstCode>(hcodeList));

			for (int index=0; index < hcodeList.size(); index++ ){
				HstCode hstCodeBean = hcodeList.get(index);
				hstCodeType.getHstCodeSet().add(hstCodeBean);
				hstCode.setHstCodeType(hstCodeType);
				hstCodeType.addToHstCodeSet(hstCodeBean);
			}

			getHibernateTemplate().save(hstCodeType);

		} else if (Label.class.isInstance(obj)){
			Label label = (Label)obj;
			label.setStatusID(StatusType.PENDING.getId());
			label.setActionID(ActionType.DELETE.getId());
			label.setApproverUserID(securityUtils.getUserName());
			label.setDateApproved(DateUtil.getCurrentDate());
			label.setLastModifierUserID(securityUtils.getUserName());
			label.setDateLastModified(DateUtil.getCurrentDate());
			getHibernateTemplate().update(label);

			this.insertCopyRecordToTemp(label);

			HstLabel hstLabel = new HstLabel(new TmpLabel(label));
			getHibernateTemplate().save(hstLabel);

		} else if (Mli.class.isInstance(obj)){
			Mli mli = (Mli) obj;
			mli.setStatusID(StatusType.PENDING.getId());
			mli.setActionID(ActionType.DELETE.getId());
			mli.setApproverUserID(securityUtils.getUserName());
			mli.setDateApproved(DateUtil.getCurrentDate());
			mli.setLastModifierUserID(securityUtils.getUserName());
			mli.setDateLastModified(DateUtil.getCurrentDate());

			getHibernateTemplate().update(mli);

			this.insertCopyRecordToTemp(mli);

			HstMli hstMli = new HstMli(new TmpMli(mli));
			getHibernateTemplate().save(hstMli);

		} else if (Script.class.isInstance(obj)){

			Script script = (Script) obj;
			script.setStatusID(StatusType.PENDING.getId());
			script.setActionID(ActionType.DELETE.getId());
			script.setApproverUserID(securityUtils.getUserName());
			script.setDateApproved(DateUtil.getCurrentDate());
			script.setLastModifierUserID(securityUtils.getUserName());
			script.setDateLastModified(DateUtil.getCurrentDate());

			getHibernateTemplate().update(script);

			this.insertCopyRecordToTemp(script);

			HstScript hstScript = new HstScript(new TmpScript(script));
			getHibernateTemplate().save(hstScript);

		}
	}

	public void insertCopyRecordToTemp(Object obj){

		if (Mli.class.isInstance(obj)){
			Mli mli = (Mli) obj;

			StringBuilder queryStr = new StringBuilder();
			queryStr.append("INSERT INTO TmpMli (");
			queryStr.append("mliID,");
			queryStr.append("countryCode,");
			queryStr.append("functionCode,");
			queryStr.append("userFieldID,");
			queryStr.append("responseCodeID,");
			queryStr.append("desc,");
			queryStr.append("messageTypeID,");
			queryStr.append("message,");
			queryStr.append("commentTypeID,");
			queryStr.append("comment,");
			queryStr.append("statusID,");
			queryStr.append("actionID,");
			queryStr.append("creatorUserID,");
			queryStr.append("dateCreated,");
			queryStr.append("approverUserID,");
			queryStr.append("dateApproved,");
			queryStr.append("lastModifierUserID,");
			queryStr.append("dateLastModified) ");

			queryStr.append("SELECT ");
			queryStr.append("a.mliID,");
			queryStr.append("a.countryCode,");
			queryStr.append("a.functionCode,");
			queryStr.append("a.userFieldID,");
			queryStr.append("a.responseCodeID,");
			queryStr.append("a.desc,");
			queryStr.append("a.messageTypeID,");
			queryStr.append("a.message,");
			queryStr.append("a.commentTypeID,");
			queryStr.append("a.comment,");
			queryStr.append("a.statusID,");
			queryStr.append("a.actionID,");
			queryStr.append("a.creatorUserID,");
			queryStr.append("a.dateCreated,");
			queryStr.append("a.approverUserID,");
			queryStr.append("a.dateApproved,");
			queryStr.append("a.lastModifierUserID,");
			queryStr.append("a.dateLastModified ");
			queryStr.append("from Mli a where a.mliID=?");

			Query query = getSession().createQuery(queryStr.toString());
			query.setParameter(0,mli.getMliID());
			query.executeUpdate();

		} else if (Script.class.isInstance(obj)){
			Script script = (Script)obj;

			StringBuilder queryStr = new StringBuilder();
			queryStr.append("INSERT INTO TmpScript (");
			queryStr.append("scriptID,");
			queryStr.append("countryCode,");
			queryStr.append("functionCode,");
			queryStr.append("userFieldID,");
			queryStr.append("priorityID,");
			queryStr.append("desc,");
			queryStr.append("messageTypeID,");
			queryStr.append("message,");
			queryStr.append("commentTypeID,");
			queryStr.append("comment,");
			queryStr.append("statusID,");
			queryStr.append("actionID,");
			queryStr.append("creatorUserID,");
			queryStr.append("dateCreated,");
			queryStr.append("approverUserID,");
			queryStr.append("dateApproved,");
			queryStr.append("lastModifierUserID,");
			queryStr.append("dateLastModified) ");

			queryStr.append("SELECT ");
			queryStr.append("a.scriptID,");
			queryStr.append("a.countryCode,");
			queryStr.append("a.functionCode,");
			queryStr.append("a.userFieldID,");
			queryStr.append("a.priorityID,");
			queryStr.append("a.desc,");
			queryStr.append("a.messageTypeID,");
			queryStr.append("a.message,");
			queryStr.append("a.commentTypeID,");
			queryStr.append("a.comment,");
			queryStr.append("a.statusID,");
			queryStr.append("a.actionID,");
			queryStr.append("a.creatorUserID,");
			queryStr.append("a.dateCreated,");
			queryStr.append("a.approverUserID,");
			queryStr.append("a.dateApproved,");
			queryStr.append("a.lastModifierUserID,");
			queryStr.append("a.dateLastModified ");
			queryStr.append("from Script a where a.scriptID=?");

			Query query = getSession().createQuery(queryStr.toString());
			query.setParameter(0,script.getScriptID());
			query.executeUpdate();

		} else if (Label.class.isInstance(obj)){
			Label label = (Label)obj;

			StringBuilder queryStr = new StringBuilder();
			queryStr.append("INSERT INTO TmpLabel (");
			queryStr.append("labelID,");
			queryStr.append("labelLanguageCode,");
			queryStr.append("labelName,");
			queryStr.append("labelNativeString,");
			queryStr.append("statusID,");
			queryStr.append("actionID,");
			queryStr.append("creatorUserID,");
			queryStr.append("dateCreated,");
			queryStr.append("lastModifierUserID,");
			queryStr.append("dateLastModified,");
			queryStr.append("approverUserID,");
			queryStr.append("dateApproved) ");

			queryStr.append("SELECT ");
			queryStr.append("a.labelID,");
			queryStr.append("a.labelLanguageCode,");
			queryStr.append("a.labelName,");
			queryStr.append("a.labelNativeString,");
			queryStr.append("a.statusID,");
			queryStr.append("a.actionID,");
			queryStr.append("a.creatorUserID,");
			queryStr.append("a.dateCreated,");
			queryStr.append("a.lastModifierUserID,");
			queryStr.append("a.dateLastModified,");
			queryStr.append("a.approverUserID,");
			queryStr.append("a.dateApproved ");
			queryStr.append("from Label a where labelID=?");

			Query query = getSession().createQuery(queryStr.toString());
			query.setParameter(0,label.getLabelID());
			query.executeUpdate();
		}

	}

	public Object findMast(Object mastObj, Object id) throws DataAccessException {

		if (Element.class.isInstance(mastObj)){
			return (Element) getHibernateTemplate().load(Element.class, (String)id);

		} else if (Criteria.class.isInstance(mastObj)){
			return (Criteria) getHibernateTemplate().load(Criteria.class, (Integer)id);

		} else if (CodeType.class.isInstance(mastObj)){
			return (CodeType) getHibernateTemplate().load(CodeType.class, (String)id);

		} else if (Label.class.isInstance(mastObj)){
			return (Label) getHibernateTemplate().load(Label.class, (String)id);

		} else if (Mli.class.isInstance(mastObj)){
			return (Mli) getHibernateTemplate().load(Mli.class, (String)id);

		} else if (Script.class.isInstance(mastObj)){
			return (Script) getHibernateTemplate().load(Script.class, (String)id);
			
		} else if (Function.class.isInstance(mastObj)){
			return (Function) getHibernateTemplate().load(Function.class, (String)id);
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public List findChildren(Class classObj, Object id) throws DataAccessException {

		List list = new ArrayList();

		if (classObj.isInstance(new Code())){
			list = getHibernateTemplate().find("from " + classObj.getName() + " where CO_CODETYPE_ID_FK = " + (String)id);
		} else if (classObj.isInstance(new TmpCode())){
			list = getHibernateTemplate().find("from " + classObj.getName() + " where TMP_CO_CODETYPE_ID_FK = " + (String)id);
		} else if (classObj.isInstance(new EnhancedCriterion())){
			list = getHibernateTemplate().find("from " + classObj.getName() + " where ec_criteria_id_fk = " + (String)id);
		} else if (classObj.isInstance(new TempEnhancedCriterion())){
			list = getHibernateTemplate().find("from " + classObj.getName() + " where te_ec_criteria_id_fk = " + (String)id);
		}

		return list;
	}

	@SuppressWarnings("rawtypes")
	public List findEntityByStatus(Class classObj, int status_id) throws DataAccessException {

		List list = new ArrayList();

		if (classObj.isInstance(new TmpElement())){
			list = getHibernateTemplate().find("from " + classObj.getName() + " where statusID = " + status_id);
			
		} else if (classObj.isInstance(new TempCriteria())){
			list = getHibernateTemplate().find("from " + classObj.getName() + " where statusID = " + status_id);

		} else if (classObj.isInstance(new TmpMli())){
			list = getHibernateTemplate().find("from " + classObj.getName() + " where statusID = " + status_id);

		} else if (classObj.isInstance(new TmpScript())){
			list = getHibernateTemplate().find("from " + classObj.getName() + " where statusID = " + status_id);

		} else if (classObj.isInstance(new TmpCodeType())){
			list = getHibernateTemplate().find("from " + classObj.getName() + " where statusID = " + status_id);

		} else if (classObj.isInstance(new TmpLabel())){
			list = getHibernateTemplate().find("from " + classObj.getName() + " where statusID = " + status_id);
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	public void cancelRequest(Object obj){

		if (TmpElement.class.isInstance(obj)){
			int final_status = 0;
			TmpElement tempElements = (TmpElement) obj;
			Element element = new Element(tempElements);

			element.setStatusID(StatusType.ACTIVE.getId());
			getHibernateTemplate().update(element);

			getHibernateTemplate().delete(tempElements);

			HstElement histElement = new HstElement(tempElements);

			if (histElement.getStatusID() == StatusType.PENDING.getId()) {
				final_status = StatusType.CANCELLED.getId();
			}
			
			histElement.setStatusID(final_status);
			getHibernateTemplate().save(histElement);

		} else if (TempCriteria.class.isInstance(obj)){
			int final_status = 0;
			TempCriteria tempCriteria = (TempCriteria) obj;
			Criteria criteria = new Criteria(tempCriteria);

			criteria.setStatusID(StatusType.ACTIVE.getId());

			getHibernateTemplate().update(criteria);


			tempCriteria.setTempEnhancedCriterionSet(new HashSet<TempEnhancedCriterion>(this.findChildren(TempEnhancedCriterion.class, tempCriteria.getCriteria_id())));
			getHibernateTemplate().delete(tempCriteria);

			HistCriteria histCriteria = new HistCriteria(tempCriteria);

			if (histCriteria.getStatusID() == StatusType.PENDING.getId()) {
				final_status = StatusType.CANCELLED.getId();
			}

			histCriteria.setStatusID(final_status);
			getHibernateTemplate().save(histCriteria);

		} else if (TmpCodeType.class.isInstance(obj)){
			int final_status = 0;
			TmpCodeType tmpCodeType = (TmpCodeType) obj;
			tmpCodeType.setTmpCodeSet(new HashSet<TmpCode>(this.findChildren(TmpCode.class, tmpCodeType.getCodeTypeID())));

			CodeType codeType = new CodeType(tmpCodeType);

			codeType.setStatusID(StatusType.ACTIVE.getId());

			getHibernateTemplate().update(codeType);

			getHibernateTemplate().delete(tmpCodeType);

			HstCodeType hstCodeType= new HstCodeType(tmpCodeType);

			final_status = 0;
			if (hstCodeType.getStatusID() == StatusType.PENDING.getId()) {
				final_status = StatusType.CANCELLED.getId();
			}

			hstCodeType.setStatusID(final_status);
			getHibernateTemplate().save(hstCodeType);

		} else if (TmpLabel.class.isInstance(obj)){
			int final_status = 0;
			TmpLabel tmpLabel = (TmpLabel) obj;
			Label label = new Label(tmpLabel);

			label.setStatusID(StatusType.ACTIVE.getId());

			if (label.getStatusID() == 1){
				getHibernateTemplate().save(label);
			} else {
				getHibernateTemplate().update(label);
			}

			getHibernateTemplate().delete(tmpLabel);

			HstLabel hstLabel = new HstLabel(tmpLabel);

			if (hstLabel.getStatusID() == StatusType.PENDING.getId()) {
				final_status = StatusType.CANCELLED.getId();
			}

			hstLabel.setStatusID(final_status);
			getHibernateTemplate().save(hstLabel);

		} else if (TmpMli.class.isInstance(obj)){
			int final_status = 0;
			TmpMli tmpMli = (TmpMli) obj;
			Mli mli = new Mli(tmpMli);

			mli.setStatusID(StatusType.ACTIVE.getId());
			getHibernateTemplate().update(mli);

			getHibernateTemplate().delete(tmpMli);

			HstMli hstMli = new HstMli(tmpMli);

			if (hstMli.getStatusID() == StatusType.PENDING.getId()) {
				final_status = StatusType.CANCELLED.getId();
			}

			hstMli.setStatusID(final_status);
			getHibernateTemplate().save(hstMli);

		} else if (TmpScript.class.isInstance(obj)){
			int final_status = 0;
			TmpScript tmpScript = (TmpScript) obj;
			Script script = new Script(tmpScript);

			script.setStatusID(StatusType.ACTIVE.getId());
			getHibernateTemplate().update(script);
			
			getHibernateTemplate().delete(tmpScript);

			HstScript hstScript = new HstScript(tmpScript);

			if (hstScript.getStatusID() == StatusType.PENDING.getId()) {
				final_status = StatusType.CANCELLED.getId();
			}

			hstScript.setStatusID(final_status);

			getHibernateTemplate().save(hstScript);

		}
	}
}
