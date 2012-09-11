package com.isg.ifrend.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.isg.ifrend.model.bean.Priority;

public class PriorityDAO extends HibernateDaoSupport {
	
	public void saveOrUpdate(Priority priority) throws DataAccessException {
		getHibernateTemplate().saveOrUpdate(priority);
	}

	public void delete(Priority priority) throws DataAccessException {
		getHibernateTemplate().delete(priority);
	}

	public Priority find(Class<Priority> priorityClass, int priorityID) throws DataAccessException {
		Priority priority = (Priority) getHibernateTemplate().load(priorityClass, priorityID);
		return priority;
	}
	
	@SuppressWarnings("unchecked")
	public Priority findByDescription(String description) throws DataAccessException {
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		String sql = "select * from ref_priorities where pr_priority_desc = ?";
		
		Query query = session.createSQLQuery(sql).addEntity(Priority.class);
		query.setString(0, description);
		
		List<Priority>lst_priority = new ArrayList<Priority>();
		lst_priority = query.list();
		
		return lst_priority.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public List<Priority> findAll(Class<Priority> priorityClass) throws DataAccessException {
		List<Priority> list = getHibernateTemplate().find("from " + priorityClass.getName());
		return list;
	}

}
