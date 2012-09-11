package com.isg.ifrend.model.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.isg.ifrend.model.bean.ActionType;
import com.isg.ifrend.model.bean.Criteria;
import com.isg.ifrend.model.bean.HistCriteria;
import com.isg.ifrend.model.bean.StatusType;
import com.isg.ifrend.model.bean.TempCriteria;

public class CriteriaDAO extends HibernateDaoSupport {

	public void saveOrUpdateMaster(Criteria criteria) throws DataAccessException {
		getHibernateTemplate().saveOrUpdate(criteria);
	}

	public void deleteMaster(Criteria criteria) throws DataAccessException {
		getHibernateTemplate().delete(criteria);
	}

	public Criteria findMaster(Class<Criteria> criteriaClass, int criterion_id) throws DataAccessException {
		Criteria criteria = (Criteria) getHibernateTemplate().load(criteriaClass, criterion_id);
		return criteria;
	}

	@SuppressWarnings("unchecked")
	public List<Criteria> findAllMaster(Class<Criteria> criteriaClass) throws DataAccessException {
		List<Criteria> list = getHibernateTemplate().find("from " + criteriaClass.getName());
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Criteria> findAllNotDeletedMaster(Class<Criteria> criteriaClass) throws DataAccessException {
		List<Criteria> list = getHibernateTemplate().find("from " + criteriaClass.getName() + " where statusID != 14");
		return list;
	}

	public void updateMasterStatus(Criteria criteria) {
		String queryStr = "UPDATE Criteria SET statusID = :statusID where criteria_id = :id";
		Query query = getSession().createQuery(queryStr);
		query.setInteger("statusID", criteria.getStatusID());
		query.setString("id", criteria.getCriteria_id());
		query.executeUpdate();
	}

	public void saveOrUpdateTemp(TempCriteria tempCriteria) throws DataAccessException {
		getHibernateTemplate().saveOrUpdate(tempCriteria);
	}
	
	public String saveTemp(TempCriteria tempCriteria) throws DataAccessException {
	   String ID = String.valueOf((Integer) getHibernateTemplate().save(tempCriteria));
	   getHibernateTemplate().flush();
	   return ID;
		
	}

	public void deleteTemp(TempCriteria tempCriteria) throws DataAccessException {
		getHibernateTemplate().delete(tempCriteria);
	}

	public TempCriteria findTemp(Class<TempCriteria> tempCriteriaClass, int criteria_id) throws DataAccessException {
		TempCriteria tempCriteria = (TempCriteria) getHibernateTemplate().load(tempCriteriaClass, criteria_id);
		return tempCriteria;
	}

	@SuppressWarnings("unchecked")
	public List<TempCriteria> findAllTemp(Class<TempCriteria> tempCriteriaClass) throws DataAccessException {
		List<TempCriteria> list = getHibernateTemplate().find("from " + tempCriteriaClass.getName());
		return list;
	}

	public void saveHist(HistCriteria histCriteria) throws DataAccessException {
		getHibernateTemplate().save(histCriteria);
	}

	public void deleteHist(HistCriteria histCriteria) throws DataAccessException {
		getHibernateTemplate().delete(histCriteria);
	}

	@SuppressWarnings("unchecked")
	public List<HistCriteria> findAllHist(Class<HistCriteria> histCriteriaClass) throws DataAccessException {
		List<HistCriteria> list = getHibernateTemplate().find("from " + histCriteriaClass.getName());
		return list;
	}

	public void requestDeletion(Criteria criteria){
		criteria.setStatusID(StatusType.PENDING.getId());
		criteria.setActionID(ActionType.DELETE.getId());
		getHibernateTemplate().update(criteria);

		this.insertCopyRecordToTemp(criteria);
		
		HistCriteria histCriteria = new HistCriteria(new TempCriteria(criteria));
		getHibernateTemplate().save(histCriteria);
	}

	public void insertCopyRecordToTemp(Criteria criteria){
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO TempCriteria (");
		sql.append("criteria_id,");
		sql.append("country_code,");
		sql.append("functiontype_id,");
		sql.append("function_id,");
		sql.append("user_field_id,");
		sql.append("priority_id,");
		sql.append("description,");
		sql.append("pass_action_id,");
		sql.append("pass_messagetype_id,");
		sql.append("pass_lettercode_id,");
		sql.append("pass_commenttype_id,");
		sql.append("pass_message,");
		sql.append("pass_comment,");
		sql.append("fail_action_id,");
		sql.append("fail_messagetype_id,");
		sql.append("fail_lettercode_id,");
		sql.append("fail_commenttype_id,");
		sql.append("fail_message,");
		sql.append("fail_comment,");
		/* Common - START */
		sql.append("statusID,");
		sql.append("actionID,");
		sql.append("creatorUserID,");
		sql.append("dateCreated,");
		sql.append("approverUserID,");
		sql.append("dateApproved,");
		sql.append("lastModifierUserID,");
		sql.append("dateLastModified) ");
		/* Common - END */

		sql.append("SELECT ");
		sql.append("a.criteria_id,");
		sql.append("a.country_code,");
		sql.append("a.functiontype_id,");
		sql.append("a.function_id,");
		sql.append("a.user_field_id,");
		sql.append("a.priority_id,");
		sql.append("a.description,");
		sql.append("a.pass_action_id,");
		sql.append("a.pass_messagetype_id,");
		sql.append("a.pass_lettercode_id,");
		sql.append("a.pass_commenttype_id,");
		sql.append("a.pass_message,");
		sql.append("a.pass_comment,");
		sql.append("a.fail_action_id,");
		sql.append("a.fail_messagetype_id,");
		sql.append("a.fail_lettercode_id,");
		sql.append("a.fail_commenttype_id,");
		sql.append("a.fail_message,");
		sql.append("a.fail_comment,");
		/* Common - START */
		sql.append("a.statusID,");
		sql.append("a.actionID,");
		sql.append("a.creatorUserID,");
		sql.append("a.dateCreated,");
		sql.append("a.approverUserID,");
		sql.append("a.dateApproved,");
		sql.append("a.lastModifierUserID,");
		sql.append("a.dateLastModified ");
		/* Common - END */
		sql.append("from Criteria a where a.criteria_id=?");

		Query query = getSession().createQuery(sql.toString());
		query.setParameter(0,criteria.getCriteria_id());
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<TempCriteria> findPendingAuthorizationStatus(int status_id){
		StringBuilder queryStr = new StringBuilder();
		queryStr.append("SELECT ");
		queryStr.append("criteria_id,");
		queryStr.append("country_code,");
		queryStr.append("functiontype_id,");
		queryStr.append("function_id,");
		queryStr.append("user_field_id,");
		queryStr.append("priority_id,");
		queryStr.append("description,");
		queryStr.append("pass_action_id,");
		queryStr.append("pass_messagetype_id,");
		queryStr.append("pass_lettercode_id,");
		queryStr.append("pass_commenttype_id,");
		queryStr.append("pass_message,");
		queryStr.append("pass_comment,");
		queryStr.append("fail_action_id,");
		queryStr.append("fail_messagetype_id,");
		queryStr.append("fail_lettercode_id,");
		queryStr.append("fail_commenttype_id,");
		queryStr.append("fail_message,");
		queryStr.append("fail_comment,");
		/* Common - START */
		queryStr.append("statusID,");
		queryStr.append("actionID,");
		queryStr.append("creatorUserID,");
		queryStr.append("dateCreated,");
		queryStr.append("approverUserID,");
		queryStr.append("dateApproved,");
		queryStr.append("lastModifierUserID,");
		queryStr.append("dateLastModified) ");
		/* Common - END */
		queryStr.append("FROM ");
		queryStr.append("TempCriteria ");
		queryStr.append("WHERE ");
		queryStr.append("statusID = :statusID");
		
		Query query = getSession().createQuery(queryStr.toString());
		query.setInteger("statusID", status_id);

		return query.list();
	}
	
}
