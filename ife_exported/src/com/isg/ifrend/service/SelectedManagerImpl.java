package com.isg.ifrend.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.isg.ifrend.model.bean.Action;
import com.isg.ifrend.model.bean.Code;
import com.isg.ifrend.model.bean.CodeType;
import com.isg.ifrend.model.bean.Country;
import com.isg.ifrend.model.bean.Criteria;
import com.isg.ifrend.model.bean.Element;
import com.isg.ifrend.model.bean.EnhancedCriterion;
import com.isg.ifrend.model.bean.Function;
import com.isg.ifrend.model.bean.Label;
import com.isg.ifrend.model.bean.Mli;
import com.isg.ifrend.model.bean.Script;
import com.isg.ifrend.model.bean.TempCriteria;
import com.isg.ifrend.model.bean.TempEnhancedCriterion;
import com.isg.ifrend.model.bean.TmpCode;
import com.isg.ifrend.model.bean.TmpCodeType;
import com.isg.ifrend.model.bean.TmpElement;
import com.isg.ifrend.model.bean.TmpLabel;
import com.isg.ifrend.model.bean.TmpMli;
import com.isg.ifrend.model.bean.TmpScript;
import com.isg.ifrend.model.dao.SelectedDAO;

public class SelectedManagerImpl implements SelectedManager {

	private SelectedDAO selectedDAO;

	public SelectedDAO getSelectedDAO() {
		return selectedDAO;
	}

	public void setSelectedDAO(SelectedDAO selectedDAO) {
		this.selectedDAO = selectedDAO;
	}

	@SuppressWarnings("rawtypes")
	@Transactional(readOnly = true)
	public List getRecordList(Object obj) {

		Class className = null;

		if (obj instanceof TmpCodeType){
			className = TmpCodeType.class;
		} else if (obj instanceof TmpLabel){
			className = TmpLabel.class;
		} else if (obj instanceof TmpMli){
			className = TmpMli.class;
		} else if (obj instanceof TmpScript){
			className = TmpScript.class;
		} else if (obj instanceof TmpElement){
			className = TmpElement.class;
		} else if (obj instanceof TempCriteria){
			className = TempCriteria.class;
		} else if (obj instanceof CodeType){
			className = CodeType.class;
		} else if (obj instanceof Label){
			className = Label.class;
		} else if (obj instanceof Mli){
			className = Mli.class;
		} else if (obj instanceof Script){
			className = Script.class;
		} else if (obj instanceof Element){
			className = Element.class;
		} else if (obj instanceof Criteria){
			className = Criteria.class;
		} else if (obj instanceof Function){
			className = Function.class;
		} else if (obj instanceof Country){
			className = Country.class;
		} else if (obj instanceof Action){
			className = Action.class;
		} else if (obj instanceof Code){
			className = Code.class;
		} 

		try {
			return selectedDAO.findAllRecords(className);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Transactional(readOnly = true)
	public Object findTemp(Object tempObj, Object id) {
		try {
			return selectedDAO.findTemp(tempObj, id);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Transactional(readOnly = true)
	public Object findMast(Object mastObj, Object id) {
		try {
			return selectedDAO.findMast(mastObj, id);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	@Transactional(readOnly = true)
	public List findChildren(Object classObj, Object id) {
		if (EnhancedCriterion.class.isInstance(classObj)){
			return selectedDAO.findChildren(EnhancedCriterion.class, id);
		} else if (TempEnhancedCriterion.class.isInstance(classObj)){
			return selectedDAO.findChildren(TempEnhancedCriterion.class, id);
		} else if (Code.class.isInstance(classObj)){
			return selectedDAO.findChildren(Code.class, id);
		} else if (TmpCode.class.isInstance(classObj)){
			return selectedDAO.findChildren(TmpCode.class, id);
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Transactional(readOnly = true)
	public List findEntityByStatus(Object classObj, int status_id) {

		if (TmpElement.class.isInstance(classObj)){
			return selectedDAO.findEntityByStatus(TmpElement.class, status_id);

		} else if (TempCriteria.class.isInstance(classObj)){
			return selectedDAO.findEntityByStatus(TempCriteria.class, status_id);

		} else if (TmpMli.class.isInstance(classObj)){
			return selectedDAO.findEntityByStatus(TmpMli.class, status_id);

		} else if (TmpScript.class.isInstance(classObj)){
			return selectedDAO.findEntityByStatus(TmpScript.class, status_id);

		} else if (TmpCodeType.class.isInstance(classObj)){
			return selectedDAO.findEntityByStatus(TmpCodeType.class, status_id);

		} else if (TmpLabel.class.isInstance(classObj)){
			return selectedDAO.findEntityByStatus(TmpLabel.class, status_id);
		}

		return null;
	}

	@SuppressWarnings("rawtypes")
	@Transactional(readOnly = true)
	public List findAllMasterDataNotDeleted(Object classObj) {

		if (Element.class.isInstance(classObj)){
			return selectedDAO.findAllMasterDataNotDeleted(classObj);

		} else if (Criteria.class.isInstance(classObj)){
			return selectedDAO.findAllMasterDataNotDeleted(classObj);

		} else if (Mli.class.isInstance(classObj)){
			return selectedDAO.findAllMasterDataNotDeleted(classObj);

		} else if (Script.class.isInstance(classObj)){
			return selectedDAO.findAllMasterDataNotDeleted(classObj);

		} else if (CodeType.class.isInstance(classObj)){
			return selectedDAO.findAllMasterDataNotDeleted(classObj);

		} else if (Label.class.isInstance(classObj)){
			return selectedDAO.findAllMasterDataNotDeleted(classObj);
		}

		return null;
	}

	@Transactional
	public boolean requestDeletion(Object obj) {
		try {
			selectedDAO.requestDeletion(obj);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Transactional
	public boolean authorizeEntities(Object obj) {
		try {
			selectedDAO.authorizeEntities(obj);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Transactional
	public boolean cancelRequest(Object obj) {
		try {
			selectedDAO.cancelRequest(obj);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@SuppressWarnings("rawtypes")
	@Transactional(readOnly = true)
	public List searchEntityByCriteria(DetachedCriteria criteria){
		return selectedDAO.searchEntityByCriteria(criteria);
	}
}
