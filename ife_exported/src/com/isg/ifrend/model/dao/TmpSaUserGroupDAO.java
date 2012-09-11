package com.isg.ifrend.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.isg.ifrend.model.bean.ActionType;
import com.isg.ifrend.model.bean.TmpSaUserGroup;
import com.isg.ifrend.utils.Commons;

public class TmpSaUserGroupDAO extends HibernateDaoSupport {


	public void saveOrUpdate(TmpSaUserGroup tmpsausergroup) throws DataAccessException {

		getHibernateTemplate().saveOrUpdate(tmpsausergroup);		
	}

	public void delete(TmpSaUserGroup tmpsausergroup) throws DataAccessException {
		getHibernateTemplate().delete(tmpsausergroup);
	}

	@SuppressWarnings("unchecked")
	public TmpSaUserGroup find(String group_id) throws DataAccessException {
		TmpSaUserGroup tmpsausergroup = new TmpSaUserGroup();
		List<TmpSaUserGroup>list = new ArrayList<TmpSaUserGroup>();
		String tablename = "TMP_TBL_SA_USERGROUP";

		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		String sql = "SELECT * FROM  " + tablename + " WHERE lower(GROUP_ID) = ?";

		Query query = session.createSQLQuery(sql).addEntity(TmpSaUserGroup.class);
		query.setString(0,group_id.toLowerCase());

		list = query.list();
		if(list.isEmpty()){
			tmpsausergroup = null;
		}else{
			tmpsausergroup = list.get(0);
		}

		return tmpsausergroup;
	}

	@SuppressWarnings("unchecked")
	public List<TmpSaUserGroup> findAll(Class<TmpSaUserGroup> clazz) throws DataAccessException {
		List<TmpSaUserGroup> list = getHibernateTemplate().find("from " + clazz.getName());
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<TmpSaUserGroup> findAllPending(Class<TmpSaUserGroup> clazz) throws DataAccessException {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		String sql = "SELECT * FROM TMP_TBL_SA_USERGROUP WHERE STATUS = '"  + Commons.SA_STATUS_PENDING  + "'";
		
		Query query = session.createSQLQuery(sql).addEntity(TmpSaUserGroup.class);		
		List<TmpSaUserGroup> list = query.list();
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<TmpSaUserGroup> findAllbyId(String group_id){
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		String sql = "SELECT * FROM TMP_TBL_SA_USERGROUP";
		
		if(group_id != null && group_id.length() > 0){
			 sql = sql + " WHERE lower(GROUP_ID) LIKE ?";
		}
		
		Query query = session.createSQLQuery(sql).addEntity(TmpSaUserGroup.class);
		if(group_id != null && group_id.length() > 0){
			query.setString(0,"%" + group_id.toLowerCase() + "%");
		}	
		
		List<TmpSaUserGroup> list = query.list();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<TmpSaUserGroup> findAllby_CustomParams(String group_id, String action, String status){
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
				
		String sql = "SELECT * FROM TMP_TBL_SA_USERGROUP WHERE lower(GROUP_ID) LIKE ? AND ACTION != 'ADD' " + 
					 "AND (STATUS = 'ACTIVE'  OR STATUS = 'PENDING')";
		
		if(action.equalsIgnoreCase(ActionType.ALL.getDesc())) {
			action = "";
		}
		Query query = session.createSQLQuery(sql).addEntity(TmpSaUserGroup.class);
		query.setString(0,"%" + group_id.toLowerCase() + "%");
		/*query.setString(1,"%" + action + "%");*/
		/*query.setString(1,"%" + status + "%");*/
		
		List<TmpSaUserGroup> list = query.list();
		return list;
	}	
	
	@SuppressWarnings("unchecked")
	public List<TmpSaUserGroup> Auth_Search_findAllby_CustomParams(String group_id, String action, String status){
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		String sql = "SELECT * FROM TMP_TBL_SA_USERGROUP WHERE lower(GROUP_ID) LIKE ? AND " + 
					 "ACTION LIKE ? AND STATUS LIKE ?";
		
		if(action.equalsIgnoreCase(ActionType.ALL.getDesc())) {
			action = "";
		}
		Query query = session.createSQLQuery(sql).addEntity(TmpSaUserGroup.class);
		query.setString(0,"%" + group_id.toLowerCase() + "%");
		query.setString(1,"%" + action + "%");
		query.setString(2,"%" + status + "%");
		
		List<TmpSaUserGroup> list = query.list();
		return list;
	}	
	
	
}
