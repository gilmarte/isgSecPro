package com.isg.ifrend.model.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.isg.ifrend.model.bean.TempUser;
import com.isg.ifrend.model.bean.User;

public class TempUserDAO extends HibernateDaoSupport {
	
	public void saveOrUpdate(TempUser tempUser) throws DataAccessException {
        getHibernateTemplate().saveOrUpdate(tempUser);
        getHibernateTemplate().flush();
    }
	
	public void save(TempUser tempUser) throws DataAccessException {
		getHibernateTemplate().save(tempUser);
		getHibernateTemplate().flush();
	}
	
	public void update(TempUser tempUser) throws DataAccessException {
		getHibernateTemplate().update(tempUser);
		getHibernateTemplate().flush();
	}
	
//	public void updateUserStatus(String userID, int status) throws DataAccessException {
//		TempUser user = (TempUser) getHibernateTemplate().load(TempUser.class, userID);
//		user.setStatus(status);
//		getHibernateTemplate().update(user);
//		getHibernateTemplate().flush();
//		
//	}
//	
//	public void updateUserAction(String userID, int action) throws DataAccessException {
//		TempUser user = (TempUser) getHibernateTemplate().load(TempUser.class, userID);
//		user.setAction(action);
//		getHibernateTemplate().update(user);
//		
//	}
 
    public void delete(TempUser tempUser) throws DataAccessException {
        getHibernateTemplate().delete(tempUser);
    }
 
    public TempUser find(Class<TempUser> clazz, String id) throws DataAccessException {
    	TempUser tempUser = (TempUser) getHibernateTemplate().load(clazz, id);
        return tempUser;
    }
     
//    public List<String> findAll(Class<TempUser> clazz) throws DataAccessException {
//        List<String> list = getHibernateTemplate().findByCriteria(DetachedCriteria.forClass(clazz)
//        			.setProjection(Projections.property("userID")).addOrder(Order.asc("userID")));
//        return list;
//    }
    
    @SuppressWarnings("unchecked")
	public List<String> findAllById(String userID) throws DataAccessException {
    	userID = userID.replaceAll("//s", "%");
    	StringBuffer sb = new StringBuffer("%");
    	sb.append(userID).append("%");
    	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
    	List<String> list = session.createCriteria(TempUser.class)
			.setProjection(Projections.property("userID"))
    		.add(Restrictions.ilike("userID", sb.toString()))
			.addOrder(Order.asc("userID"))
			.list();
        return list;
    }
    
    public List<String> findByIdAndStatus(String userID, Integer...status) {
    	userID = userID.replaceAll("//s", "%");
    	StringBuffer sb = new StringBuffer("%");
    	sb.append(userID).append("%");
    	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
    	@SuppressWarnings("unchecked")
		List<String> list = session.createCriteria(TempUser.class)
			.add(Restrictions.in("status", status))
			.add(Restrictions.ilike("userID", sb.toString()))
			.setProjection(Projections.property("userID"))
			.addOrder(Order.asc("userID"))
			.list();
    	return list;
    }
    
    public List<String> findByStatus(Integer... status) {
    	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
    	@SuppressWarnings("unchecked")
		List<String> list = session.createCriteria(TempUser.class)
			.add(Restrictions.in("status", status))
			.setProjection(Projections.property("userID"))
			.addOrder(Order.asc("userID"))
			.list();
    	return list;
    }
    
    public List<String> findByIdAndAction(String userID, Integer...action) {
    	userID = userID.replaceAll("//s", "%");
    	StringBuffer sb = new StringBuffer("%");
    	sb.append(userID).append("%");
    	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
    	@SuppressWarnings("unchecked")
		List<String> list = session.createCriteria(TempUser.class)
			.setProjection(Projections.property("userID"))
			.add(Restrictions.in("action", action))
			.add(Restrictions.ilike("userID", sb.toString()))
			.addOrder(Order.asc("userID"))
			.list();
    	return list;
    }
    
    public List<String> findByAction(Integer... action) {
    	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
    	@SuppressWarnings("unchecked")
		List<String> list = session.createCriteria(TempUser.class)
			.setProjection(Projections.property("userID"))
			.add(Restrictions.in("action", action))
			.addOrder(Order.asc("userID"))
			.list();
    	return list;
    }
    
    @SuppressWarnings("unchecked")
	public List<String> findAllUserID() {
    	 List<String> list = 
         	getHibernateTemplate().findByCriteria(DetachedCriteria.forClass(TempUser.class)
         			.setProjection(Projections.property("userID"))
         			.addOrder(Order.asc("userID")));
         return list;
    }
    
    public boolean isUserExist(String userID) {
    	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
    	if (session.createCriteria(User.class).add(Restrictions.idEq(userID)).list().isEmpty()) {
    		return false;
    	}
    	return true;
    }
    
}
