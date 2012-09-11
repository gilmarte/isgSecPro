package com.isg.ifrend.model.dao;

import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.isg.ifrend.model.bean.StatusType;
import com.isg.ifrend.model.bean.User;

public class UserDAO extends HibernateDaoSupport {
	
	public void saveOrUpdate(User user) throws DataAccessException {
        getHibernateTemplate().saveOrUpdate(user);
    }
	
	public void save(User user) throws DataAccessException {
		getHibernateTemplate().save(user);
	}
	
	public void update(User user) throws DataAccessException {
		getHibernateTemplate().update(user);
	}
	
//	public void updateUserStatus(String userID, int status) throws DataAccessException {
//		User user = (User) getHibernateTemplate().load(User.class, userID);
//		user.setStatus(status);
//		getHibernateTemplate().update(user);
//		
//	}
//	
//	public void updateUserAction(String userID, int action) throws DataAccessException {
//		User user = (User) getHibernateTemplate().load(User.class, userID);
//		user.setAction(action);
//		getHibernateTemplate().update(user);
//		
//	}
 
    public void delete(User user) throws DataAccessException {
        getHibernateTemplate().delete(user);
    }
 
    public User find(Class<User> clazz, String id) throws DataAccessException {
    	User user = (User) getHibernateTemplate().load(clazz, id);
    	// If user is sys admin account(hidden account which uses status ID ALL), throw ObjectNotFoundException
    	if (user.getStatus() == StatusType.ALL.getId()) {
    		throw new ObjectNotFoundException(1L, id);
    	}
        return user;
    }
     
    @SuppressWarnings("unchecked")
	public List<String> findAllId(Class<User> clazz) throws DataAccessException {
        List<String> list = getHibernateTemplate().findByCriteria(DetachedCriteria.forClass(clazz)
        						.setProjection(Projections.property("userID"))
        						.add(Restrictions.gt("status", StatusType.ALL.getId()))
        						.addOrder(Order.asc("userID")));
        return list;
    }
    
    @SuppressWarnings("unchecked")
	public List<String> findAllById(String userID) throws DataAccessException {
    	userID = userID.replaceAll("//s", "%");
    	StringBuffer sb = new StringBuffer("%");
    	sb.append(userID).append("%");
    	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
    	List<String> list = session.createCriteria(User.class)
    		.setProjection(Projections.property("userID"))
			.add(Restrictions.ilike("userID", sb.toString()))
			.add(Restrictions.gt("status", StatusType.ALL.getId()))
			.addOrder(Order.asc("userID"))
			.list();
        return list;
    }
    
    @SuppressWarnings("unchecked")
	public List<User> findAllUsersById(String userID) throws DataAccessException {
    	userID = userID.replaceAll("//s", "%");
    	StringBuffer sb = new StringBuffer("%");
    	sb.append(userID).append("%");
    	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
    	List<User> list = session.createCriteria(User.class)
			.add(Restrictions.ilike("userID", sb.toString()))
			.add(Restrictions.gt("status", StatusType.ALL.getId()))
			.addOrder(Order.asc("userID"))
			.list();
        return list;
    }
    
    public List<String> findByStatus(Integer... status) {
    	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
    	@SuppressWarnings("unchecked")
		List<String> list = session.createCriteria(User.class)
			.setProjection(Projections.property("userID"))
			.add(Restrictions.in("status", status))
			.addOrder(Order.asc("userID"))
			.list();
    	return list;
    }
    
    public boolean isUserExist(String userID) throws DataAccessException {
    	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
    	if (session.createCriteria(User.class).add(Restrictions.idEq(userID)).list().isEmpty()) {
    		return false;
    	}
    	return true;
    }
    
    public boolean isUserAdmin(String userID) throws DataAccessException {
    	User user = (User) getHibernateTemplate().load(User.class, userID);
    	if(user.getStatus() == StatusType.ALL.getId()) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
}
