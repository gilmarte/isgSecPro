package com.isg.ifrend.model.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.isg.ifrend.model.bean.ActionType;
import com.isg.ifrend.model.bean.HistUserOrganization;
import com.isg.ifrend.model.bean.StatusType;

public class HistUserOrganizationDAO extends HibernateDaoSupport {

	public void save(HistUserOrganization histUserOrg) throws DataAccessException {
		getHibernateTemplate().save(histUserOrg);
		getHibernateTemplate().flush();
	}
	
    public HistUserOrganization find(Class<HistUserOrganization> clazz, String id) throws DataAccessException {
    	HistUserOrganization histUserOrg= (HistUserOrganization) getHibernateTemplate().load(clazz, id);
        return histUserOrg;
    }
    
    public HistUserOrganization find(Class<HistUserOrganization> clazz, int id) throws DataAccessException {
    	HistUserOrganization histUserOrg = (HistUserOrganization) getHibernateTemplate().load(clazz, id);
        return histUserOrg;
    }
     
    @SuppressWarnings("unchecked")
	public List<Integer> findAll(Class<HistUserOrganization> clazz) throws DataAccessException {
        List<Integer> list = //getHibernateTemplate().find("from " + clazz.getName());
        	getHibernateTemplate().findByCriteria(DetachedCriteria.forClass(clazz)
        			.setProjection(Projections.property("historyIndex")).addOrder(Order.desc("historyIndex")));
        return list;
    }
    
    @SuppressWarnings("unchecked")
	public List<Integer> findAllById(String orgID) throws DataAccessException {
    	StringBuffer sb = new StringBuffer("%");
    	sb.append(orgID).append("%");
    	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
    	List<Integer> list = session.createCriteria(HistUserOrganization.class)
    		.setProjection(Projections.property("historyIndex"))
			.add(Restrictions.ilike("orgID", sb.toString()))
			.addOrder(Order.desc("historyIndex"))
			.list();
        return list;
    }
    
    public List<Integer> findByIdAndStatus(String orgID, Integer...status) {
    	StringBuffer sb = new StringBuffer("%");
    	sb.append(orgID).append("%");
    	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
    	@SuppressWarnings("unchecked")
		List<Integer> list = session.createCriteria(HistUserOrganization.class)
			.setProjection(Projections.property("historyIndex"))
			.add(Restrictions.in("status", status))
			.add(Restrictions.ilike("orgID", sb.toString()))
			.addOrder(Order.desc("historyIndex"))
			.list();
    	return list;
    }
    
    public List<Integer> findByStatus(Integer... status) {
    	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
    	@SuppressWarnings("unchecked")
		List<Integer> list = session.createCriteria(HistUserOrganization.class)
			.setProjection(Projections.property("historyIndex"))
			.add(Restrictions.in("status", status))
			.addOrder(Order.desc("historyIndex"))
			.list();
    	return list;
    }
    
    public List<Integer> findByIdAndAction(String orgID, Integer...action) {
    	StringBuffer sb = new StringBuffer("%");
    	sb.append(orgID).append("%");
    	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
    	@SuppressWarnings("unchecked")
		List<Integer> list = session.createCriteria(HistUserOrganization.class)
			.setProjection(Projections.property("historyIndex"))
			.add(Restrictions.in("action", action))
			.add(Restrictions.ilike("orgID", sb.toString()))
			.addOrder(Order.desc("historyIndex"))
			.list();
    	return list;
    }
    
    public List<Integer> findByAction(Integer... action) {
    	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
    	@SuppressWarnings("unchecked")
		List<Integer> list = session.createCriteria(HistUserOrganization.class)
			.setProjection(Projections.property("historyIndex"))
			.add(Restrictions.in("action", action))
			.addOrder(Order.desc("historyIndex"))
			.list();
    	return list;
    }
    
    @SuppressWarnings("unchecked")
	public List<Integer> findByCustomFilter(String orgID, int action, int status, Date startDate, Date endDate) {
    	orgID = orgID.replaceAll("//s", "%");
    	StringBuffer sb = new StringBuffer("%");
    	sb.append(orgID).append("%");
    	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
    	Criteria criteria = session.createCriteria(HistUserOrganization.class);
    	criteria.setProjection(Projections.property("historyIndex"));
    	criteria.add(Restrictions.ilike("orgID", sb.toString()));
    	if(action != ActionType.ALL.getId()) { //try {
    		Criterion actionCriterion = Restrictions.eq("action", action);
    		criteria.add(actionCriterion);
    	}
    	if(status != StatusType.ALL.getId()) { //try {
    		Criterion statusCriterion = Restrictions.eq("status", status);
    		criteria.add(statusCriterion);
    	}
    	if(startDate != null) {
    		Criterion statusCriterion = Restrictions.ge("dateLastModified", startDate);
    		criteria.add(statusCriterion);
    	}
    	if(endDate != null) {
    		// Increase the end date by 1 day. For some reason, the result set does not include the transactions in the
    		// said endDate
    		Calendar calendar = Calendar.getInstance();
    		calendar.setTime(endDate);
    		calendar.add(Calendar.DATE, 1);
    		endDate = calendar.getTime();
    		Criterion statusCriterion = Restrictions.le("dateLastModified", endDate);
    		criteria.add(statusCriterion);
    	}
    	criteria.addOrder(Order.desc("historyIndex"));
    		
    	return criteria.list();
    }
    
    @SuppressWarnings("unchecked")
	public List<HistUserOrganization> findHistUserOrgListByCustomFilter(
    		String orgID, int action, int status, Date startDate, Date endDate) {
    	orgID = orgID.replaceAll("//s", "%");
    	StringBuffer sb = new StringBuffer("%");
    	sb.append(orgID).append("%");
    	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
    	Criteria criteria = session.createCriteria(HistUserOrganization.class);
    	criteria.add(Restrictions.ilike("orgID", sb.toString()));
    	if(action != ActionType.ALL.getId()) { //try {
    		Criterion actionCriterion = Restrictions.eq("action", action);
    		criteria.add(actionCriterion);
    	}
    	if(status != StatusType.ALL.getId()) { //try {
    		Criterion statusCriterion = Restrictions.eq("status", status);
    		criteria.add(statusCriterion);
    	}
    	if(startDate != null) {
    		Criterion statusCriterion = Restrictions.ge("dateLastModified", startDate);
    		criteria.add(statusCriterion);
    	}
    	if(endDate != null) {
    		// Increase the end date by 1 day. For some reason, the result set does not include the transactions in the
    		// said endDate
    		Calendar calendar = Calendar.getInstance();
    		calendar.setTime(endDate);
    		calendar.add(Calendar.DATE, 1);
    		endDate = calendar.getTime();
    		Criterion statusCriterion = Restrictions.le("dateLastModified", endDate);
    		criteria.add(statusCriterion);
    	}
    	criteria.addOrder(Order.desc("historyIndex"));
    		
    	return criteria.list();
    }
}
