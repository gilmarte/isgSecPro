package com.isg.ifrend.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.isg.ifrend.model.bean.SaUserGroup;

public class SaUserGroupDAO extends HibernateDaoSupport {
	
	public void saveOrUpdate(SaUserGroup sausergroup) throws DataAccessException {
		
		getHibernateTemplate().saveOrUpdate(sausergroup);
		getHibernateTemplate().flush();		
		
	}

	public void delete(SaUserGroup sausergroup) throws DataAccessException {
		getHibernateTemplate().delete(sausergroup);
	}

	@SuppressWarnings("unchecked")
	public SaUserGroup find(String group_id) throws DataAccessException {
		SaUserGroup sausergroup = new SaUserGroup();
		List<SaUserGroup>list = new ArrayList<SaUserGroup>();
		String tablename = "TBL_SA_USERGROUP";

		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		String sql = "select * from  " + tablename + " where lower(group_id) = ?";

		Query query = session.createSQLQuery(sql).addEntity(SaUserGroup.class);
		query.setString(0,group_id.toLowerCase());

		list = query.list();
		if(list.isEmpty()){
			sausergroup = null;
		}else{
			sausergroup = list.get(0);
		}

		return sausergroup;
	}

	@SuppressWarnings("unchecked")
	public List<SaUserGroup> findAll(Class<SaUserGroup> clazz) throws DataAccessException {
		List<SaUserGroup> list = getHibernateTemplate().find("from " + clazz.getName());
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<SaUserGroup> findAllbyId(String group_id){
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		String sql = "select * from TBL_SA_USERGROUP where lower(group_id) LIKE ?";
		
		Query query = session.createSQLQuery(sql).addEntity(SaUserGroup.class);
		query.setString(0,"%" + group_id.toLowerCase() + "%");
		
		List<SaUserGroup> list = query.list();
		return list;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<SaUserGroup> find_all_Active_Pending(String group_id){
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
				
		String sql = "SELECT * FROM TBL_SA_USERGROUP WHERE lower(GROUP_ID) LIKE ? AND ACTION != 'ADD' " + 
					 "AND (STATUS = 'ACTIVE'  OR STATUS = 'PENDING')";
				
		Query query = session.createSQLQuery(sql).addEntity(SaUserGroup.class);
		query.setString(0,"%" + group_id.toLowerCase() + "%");
		
		
		List<SaUserGroup> list = query.list();
		return list;
	}	
	
	
}
