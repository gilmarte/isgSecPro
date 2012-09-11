package com.isg.ifrend.model.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.isg.ifrend.model.bean.TempUserOrganization;

public class TempUserOrganizationDAO extends HibernateDaoSupport {
	
	public void saveOrUpdate(TempUserOrganization userOrganization) throws DataAccessException {
        getHibernateTemplate().saveOrUpdate(userOrganization);
    }
	
	public void save(TempUserOrganization userOrganization) throws DataAccessException {
        getHibernateTemplate().save(userOrganization);
    }
	
	public void update(TempUserOrganization userOrganization) throws DataAccessException {
        getHibernateTemplate().update(userOrganization);
    }
 
    public void delete(TempUserOrganization userOrganization) throws DataAccessException {
        getHibernateTemplate().delete(userOrganization);
    }
 
//    public void updateUserOrgStatus(String orgID, int status) throws DataAccessException {
//		TempUserOrganization tempUserOrg = (TempUserOrganization)getHibernateTemplate().load(
//				TempUserOrganization.class, orgID);
//		tempUserOrg.setStatus(status);
//		getHibernateTemplate().update(tempUserOrg);
//		
//	}
//	
//	public void updateUserOrgAction(String orgID, int action) throws DataAccessException {
//		TempUserOrganization tempUserOrg = (TempUserOrganization)getHibernateTemplate().load(
//				TempUserOrganization.class, orgID);
//		tempUserOrg.setAction(action);
//		getHibernateTemplate().update(tempUserOrg);
//		
//	}
	
    public TempUserOrganization find(Class<TempUserOrganization> clazz, String id) throws DataAccessException {
    	TempUserOrganization userOrganization = (TempUserOrganization) getHibernateTemplate().load(clazz, id);
        return userOrganization;
    }
     
    @SuppressWarnings("unchecked")
	public List<String> findAll(Class<TempUserOrganization> clazz) throws DataAccessException {
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
    	List<String> list = session.createCriteria(TempUserOrganization.class)
    		.setProjection(Projections.property("orgID"))
			.add(Restrictions.ilike("orgID", sb.toString()))
			.addOrder(Order.asc("orgID"))
			.list();
        return list;
    }
    
    public List<String> findByIdAndStatus(String orgID, Integer...status) {
    	orgID = orgID.replaceAll("//s", "%");
    	StringBuffer sb = new StringBuffer("%");
    	sb.append(orgID).append("%");
    	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
    	@SuppressWarnings("unchecked")
		List<String> list = session.createCriteria(TempUserOrganization.class)
			.setProjection(Projections.property("orgID"))
			.add(Restrictions.in("status", status))
			.add(Restrictions.ilike("orgID", sb.toString()))
			.addOrder(Order.asc("orgID"))
			.list();
    	return list;
    }
    
    public List<String> findByStatus(Integer... status) {
    	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
    	@SuppressWarnings("unchecked")
		List<String> list = session.createCriteria(TempUserOrganization.class)
			.setProjection(Projections.property("orgID"))
			.add(Restrictions.in("status", status))
			.addOrder(Order.asc("orgID"))
			.list();
    	return list;
    }
    
    public List<String> findByIdAndAction(String orgID, Integer...action) {
    	orgID = orgID.replaceAll("//s", "%");
    	StringBuffer sb = new StringBuffer("%");
    	sb.append(orgID).append("%");
    	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
    	@SuppressWarnings("unchecked")
		List<String> list = session.createCriteria(TempUserOrganization.class)
			.setProjection(Projections.property("orgID"))
			.add(Restrictions.in("action", action))
			.add(Restrictions.ilike("orgID", sb.toString()))
			.addOrder(Order.asc("orgID"))
			.list();
    	return list;
    }
    
    public List<String> findByAction(Integer... action) {
    	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
    	@SuppressWarnings("unchecked")
		List<String> list = session.createCriteria(TempUserOrganization.class)
			.setProjection(Projections.property("orgID"))
			.add(Restrictions.in("action", action))
			.addOrder(Order.asc("orgID"))
			.list();
    	return list;
    }
    
    public boolean isUserOrgExist(String orgID) throws DataAccessException {
    	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
    	if (session.createCriteria(TempUserOrganization.class).add(Restrictions.idEq(orgID)).list().isEmpty()) {
    		return false;
    	}
    	return true;
    }
    
}
