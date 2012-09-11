package com.isg.ifrend.model.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.isg.ifrend.model.bean.UserOrganization;

public class UserOrganizationDAO extends HibernateDaoSupport {
	
	public void saveOrUpdate(UserOrganization userOrganization) throws DataAccessException {
        getHibernateTemplate().saveOrUpdate(userOrganization);
    }
	
	public void save(UserOrganization userOrganization) throws DataAccessException {
        getHibernateTemplate().save(userOrganization);
    }
	
	public void update(UserOrganization userOrganization) throws DataAccessException {
        getHibernateTemplate().update(userOrganization);
    }
 
    public void delete(UserOrganization userOrganization) throws DataAccessException {
        getHibernateTemplate().delete(userOrganization);
    }
    
//    public void updateUserOrgStatus(String orgID, int status) throws DataAccessException {
//		UserOrganization userOrg = (UserOrganization)getHibernateTemplate().load(UserOrganization.class, orgID);
//		userOrg.setStatus(status);
//		getHibernateTemplate().update(userOrg);
//		
//	}
//	
//	public void updateUserOrgAction(String orgID, int action) throws DataAccessException {
//		UserOrganization userOrg = (UserOrganization)getHibernateTemplate().load(UserOrganization.class, orgID);
//		userOrg.setAction(action);
//		getHibernateTemplate().update(userOrg);
//		
//	}
 
    public UserOrganization find(Class<UserOrganization> clazz, String id) throws DataAccessException {
    	UserOrganization userOrganization = (UserOrganization) getHibernateTemplate().load(clazz, id);
        return userOrganization;
    }
     
    @SuppressWarnings("unchecked")
	public List<UserOrganization> findAll(Class<UserOrganization> clazz) throws DataAccessException {
        List<UserOrganization> list = getHibernateTemplate().findByCriteria(DetachedCriteria.forClass(clazz)
        			.addOrder(Order.asc("orgID")));
        return list;
    }
    
    @SuppressWarnings("unchecked")
	public List<String> findAllId(Class<UserOrganization> clazz) throws DataAccessException {
        List<String> list = getHibernateTemplate().findByCriteria(DetachedCriteria.forClass(clazz)
        			.setProjection(Projections.property("orgID")).addOrder(Order.asc("orgID")));
        return list;
    }
    
    @SuppressWarnings("unchecked")
	public List<String> findAllById(String orgID) throws DataAccessException {
    	orgID = orgID.replaceAll("//s", "%");
    	StringBuffer sb = new StringBuffer("%");
    	sb.append(orgID).append("%");
    	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
    	List<String> list = session.createCriteria(UserOrganization.class)
    		.setProjection(Projections.property("orgID"))
			.add(Restrictions.ilike("orgID", sb.toString()))
			.addOrder(Order.asc("orgID"))
			.list();
        return list;
    }
    
    @SuppressWarnings("unchecked")
	public List<UserOrganization> findAllUserOrgById(String orgID) throws DataAccessException {
    	orgID = orgID.replaceAll("//s", "%");
    	StringBuffer sb = new StringBuffer("%");
    	sb.append(orgID).append("%");
    	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
    	List<UserOrganization> list = session.createCriteria(UserOrganization.class)
			.add(Restrictions.ilike("orgID", sb.toString()))
			.addOrder(Order.asc("orgID"))
			.list();
        return list;
    }
    
    public List<String> findByStatus(Integer... status) {
    	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
    	@SuppressWarnings("unchecked")
		List<String> list = session.createCriteria(UserOrganization.class)
			.setProjection(Projections.property("orgID"))
			.add(Restrictions.in("status", status))
			.addOrder(Order.asc("orgID"))
			.list();
    	return list;
    }
    
    public boolean isUserOrgExist(String orgID) throws DataAccessException {
    	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
    	if (session.createCriteria(UserOrganization.class).add(Restrictions.idEq(orgID)).list().isEmpty()) {
    		return false;
    	}
    	return true;
    }
    
}
