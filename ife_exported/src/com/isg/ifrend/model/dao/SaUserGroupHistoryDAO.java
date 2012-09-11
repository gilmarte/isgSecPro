package com.isg.ifrend.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.isg.ifrend.model.bean.ActionType;
import com.isg.ifrend.model.bean.SaUserGroupHistory;
import com.isg.ifrend.model.bean.StatusType;
import com.isg.ifrend.utils.Commons;

public class SaUserGroupHistoryDAO extends HibernateDaoSupport {

	public void saveOrUpdate(SaUserGroupHistory sausergrouphistory) throws DataAccessException {

		getHibernateTemplate().saveOrUpdate(sausergrouphistory);
		getHibernateTemplate().flush();
	}

	public void delete(SaUserGroupHistory sausergrouphistory) throws DataAccessException {
		getHibernateTemplate().delete(sausergrouphistory);
	}

	@SuppressWarnings("unchecked")
	public SaUserGroupHistory find(String group_id) throws DataAccessException {
		SaUserGroupHistory sausergrouphistory = new SaUserGroupHistory();
		List<SaUserGroupHistory>list = new ArrayList<SaUserGroupHistory>();
		String tablename = "TBL_HST_SA_USERGROUP";

		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		String sql = "SELECT * FROM  " + tablename + " WHERE lower(GROUP_ID) = ?";

		Query query = session.createSQLQuery(sql).addEntity(SaUserGroupHistory.class);
		query.setString(0,group_id.toLowerCase());

		list = query.list();
		if(list.isEmpty()){
			sausergrouphistory = null;
		}else{
			sausergrouphistory = list.get(0);
		}

		return sausergrouphistory;
	}
	
	@SuppressWarnings("unchecked")
	public SaUserGroupHistory find(String group_id, Integer seq_id) throws DataAccessException {
		SaUserGroupHistory sausergrouphistory = new SaUserGroupHistory();
		List<SaUserGroupHistory>list = new ArrayList<SaUserGroupHistory>();
		String tablename = "TBL_HST_SA_USERGROUP";

		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		String sql = "SELECT * FROM  " + tablename + " WHERE lower(GROUP_ID) = ? AND SEQ_ID = ?";

		Query query = session.createSQLQuery(sql).addEntity(SaUserGroupHistory.class);
		query.setString(0,group_id.toLowerCase());
		query.setInteger(1, seq_id);

		list = query.list();
		if(list.isEmpty()){
			sausergrouphistory = null;
		}else{
			sausergrouphistory = list.get(0);
		}

		return sausergrouphistory;
	}

	@SuppressWarnings("unchecked")
	public List<SaUserGroupHistory> findAll(Class<SaUserGroupHistory> clazz) throws DataAccessException {
		List<SaUserGroupHistory> list = getHibernateTemplate().find("from " + clazz.getName());
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<SaUserGroupHistory> findAllPending(Class<SaUserGroupHistory> clazz) throws DataAccessException {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		String sql = "SELECT * FROM TBL_HST_SA_USERGROUP WHERE STATUS = '"  + Commons.SA_STATUS_PENDING  + "'";

		Query query = session.createSQLQuery(sql).addEntity(SaUserGroupHistory.class);		
		List<SaUserGroupHistory> list = query.list();

		return list;
	}

	@SuppressWarnings("unchecked")
	public List<SaUserGroupHistory> findAllbyId(String group_id){

		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		String sql = "SELECT * FROM TBL_HST_SA_USERGROUP";

		if(group_id != null && group_id.length() > 0){
			sql = sql + " WHERE lower(GROUP_ID) LIKE ?";
		}

		Query query = session.createSQLQuery(sql).addEntity(SaUserGroupHistory.class);
		if(group_id != null && group_id.length() > 0){
			query.setString(0,"%" + group_id.toLowerCase() + "%");
		}	

		List<SaUserGroupHistory> list = query.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<SaUserGroupHistory> findAllby_CustomParams(String group_id, String action, String status, String startdate, String enddate){

		String sql_decision;
		
		if(action.equalsIgnoreCase(ActionType.ALL.getDesc())) {
			action = "";
		}
		if(status.equalsIgnoreCase(StatusType.ALL.getDesc())) {
			status = "";
		}

		if(!startdate.equals("") || !enddate.equals("")){
			sql_decision = "AND MODIFIED_DT BETWEEN ? AND ?";
		}else{
			sql_decision = "";
		}

		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		String sql = "SELECT * FROM TBL_HST_SA_USERGROUP WHERE lower(GROUP_ID) LIKE ? AND " + 
		"ACTION LIKE ? AND STATUS LIKE ? " + sql_decision + 
		" ORDER BY SEQ_ID DESC";

		Query query = session.createSQLQuery(sql).addEntity(SaUserGroupHistory.class);
		query.setString(0,"%" + group_id.toLowerCase() + "%");
		query.setString(1,"%" + action + "%");
		query.setString(2,"%" + status + "%");

		if(!startdate.equals("") || !enddate.equals("")){
			query.setString(3,startdate);
			query.setString(4,enddate);
		}

		List<SaUserGroupHistory> list = query.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<SaUserGroupHistory> getDistinct_SaUserGroupHistoryList(){
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		String sql = "SELECT DISTINCT * FROM TBL_HST_SA_USERGROUP";

		Query query = session.createSQLQuery(sql).addEntity(SaUserGroupHistory.class);

		List<SaUserGroupHistory> list = query.list();
		return list;
	}

}
