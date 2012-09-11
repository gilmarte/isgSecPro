package com.isg.ifrend.model.dao.impl;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;

import com.isg.ifrend.model.bean.Criteria;
import com.isg.ifrend.model.bean.EnhancedCriterion;
import com.isg.ifrend.model.bean.HistCriteria;
import com.isg.ifrend.model.bean.TempCriteria;
import com.isg.ifrend.model.bean.TempEnhancedCriterion;
import com.isg.ifrend.utils.Commons;
import com.isg.ifrend.utils.GlobalUtils;

public class CriteriaDAOImpl extends
		GlobalDAOImpl<Criteria, TempCriteria, HistCriteria> {
	
	private static final String GLOBAL_NAME = "Criteria";
	private static final String SEQUENCE_NAME = "SEQ_CRITERIA";

	@Override
	protected void setMstSearchCriteria(Criteria m, DetachedCriteria criteria) {

	}

	@Override
	protected void setTmpSearchCriteria(TempCriteria t,
			DetachedCriteria criteria) {

	}

	@Override
	protected void setGlobalNewId(TempCriteria t) {
		t.setId(GlobalUtils.formatID(Commons.PREFIX_CRITERIA,
				getSeqNextVal(SEQUENCE_NAME)));
	}

	@Override
	protected String getMstGlobalName() {
		return GLOBAL_NAME;
	}

	@Override
	protected Class<Criteria> getMstGlobalClass() {
		return Criteria.class;
	}

	@Override
	protected Class<TempCriteria> getTmpGlobalClass() {
		return TempCriteria.class;
	}

	@Override
	protected Criteria getNewMstInstance() {
		return new Criteria();
	}

	@Override
	protected TempCriteria getNewTmpInstance() {
		return new TempCriteria();
	}

	@Override
	protected Criteria toMst(TempCriteria t) {
		return new Criteria(t);
	}

	@Override
	protected HistCriteria toHst(TempCriteria t) {
		return new HistCriteria(t);
	}

	@Override
	public void updateMst(TempCriteria t) {
		Criteria criteria = (Criteria) getHibernateTemplate().load(Criteria.class,t.getCriteria_id());
		criteria.setCriteria_id(t.getCriteria_id());
		criteria.setCountry_code(t.getCountry_code());
		criteria.setFunction_id(t.getFunction_id());
		criteria.setUser_field_id(t.getUser_field_id());
		criteria.setPriority_id(t.getPriority_id());
		criteria.setDescription(t.getDescription());
		criteria.setPass_action_id(t.getPass_action_id());
		criteria.setPass_messagetype_id(t.getPass_messagetype_id());
		criteria.setPass_lettercode_id(t.getPass_lettercode_id());
		criteria.setPass_commenttype_id(t.getPass_commenttype_id());
		criteria.setPass_message(t.getPass_message());
		criteria.setPass_comment(t.getPass_comment());
		criteria.setFail_action_id(t.getFail_action_id());
		criteria.setFail_messagetype_id(t.getFail_messagetype_id());
		criteria.setFail_lettercode_id(t.getFail_lettercode_id());
		criteria.setFail_commenttype_id(t.getFail_commenttype_id());
		criteria.setFail_message(t.getFail_message());
		criteria.setFail_comment(t.getFail_comment());
		criteria.setActionID(t.getActionID());
		criteria.setStatusID(t.getStatusID());
		/* Common - START */
		criteria.setCreatorUserID(t.getCreatorUserID());
		criteria.setDateCreated(t.getDateCreated());
		criteria.setApproverUserID(t.getApproverUserID());
		criteria.setDateApproved(t.getDateApproved());
		criteria.setLastModifierUserID(t.getLastModifierUserID());
		criteria.setDateLastModified(t.getDateLastModified());	
		/* Common - END */
		criteria.setUser_field_id(t.getUser_field_id());
		
		/** Enhanced Criteria **/
		Set<EnhancedCriterion> codeSetToRemove = new HashSet<EnhancedCriterion>();
		Set<TempEnhancedCriterion> tempEnhancedCriterionalreadyadded = new HashSet<TempEnhancedCriterion>();

		for (EnhancedCriterion enhancedcriteria : criteria.getEnhancedCriterionSet()) {
			boolean found = false;
			in: for (TempEnhancedCriterion tempenhancedcriteria : t.getTempEnhancedCriterionSet()) {
				if (enhancedcriteria.getCriteria_id()==(tempenhancedcriteria.getCriteria_id())) {
					enhancedcriteria.setEnhancedcriteria_id(tempenhancedcriteria.getEnhancedcriteria_id());
					enhancedcriteria.setCriteria_id(tempenhancedcriteria.getCriteria_id());
					enhancedcriteria.setElement_id(tempenhancedcriteria.getElement_id());
					enhancedcriteria.setOperator_code (tempenhancedcriteria.getOperator_code());
					enhancedcriteria.setEnhanced_value_integer (tempenhancedcriteria.getEnhanced_value_integer());
					enhancedcriteria.setEnhanced_value_character(tempenhancedcriteria.getEnhanced_value_character());
					enhancedcriteria.setEnhanced_value_element(tempenhancedcriteria.getEnhanced_value_element());
					enhancedcriteria.setEnhanced_value_date(tempenhancedcriteria.getEnhanced_value_date());
					enhancedcriteria.setEnhanced_value_dateformat_id(tempenhancedcriteria.getEnhanced_value_dateformat_id());

					tempEnhancedCriterionalreadyadded.add(tempenhancedcriteria);
					found = true;
					break in;
				}

			}

			if (!found) {
				codeSetToRemove.add(enhancedcriteria);
			}
		}

		criteria.getEnhancedCriterionSet().removeAll(codeSetToRemove);
		t.getTempEnhancedCriterionSet().removeAll(tempEnhancedCriterionalreadyadded);

		for (TempEnhancedCriterion tempCriteria : t.getTempEnhancedCriterionSet()) {
			criteria.addToEnhancedCriteriaSet(new EnhancedCriterion(tempCriteria));
		}

		getHibernateTemplate().update(criteria);
		getHibernateTemplate().flush();
		
		t.getTempEnhancedCriterionSet().addAll(tempEnhancedCriterionalreadyadded);
		
	}

	@Override
	protected Criteria getUniqueAttributes(TempCriteria t) {
		Criteria m = new Criteria();
		return m;
	}
}