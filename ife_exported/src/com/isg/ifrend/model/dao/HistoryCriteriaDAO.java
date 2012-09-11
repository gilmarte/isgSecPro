package com.isg.ifrend.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import com.isg.ifrend.model.bean.HistCriteria;

public class HistoryCriteriaDAO extends HibernateDaoSupport {
	
	@SuppressWarnings("unchecked")
	public List<HistCriteria> findAllHist(Class<HistCriteria> histCriteriaClass) throws DataAccessException{
		List<HistCriteria> list = getHibernateTemplate().find("from " + histCriteriaClass.getName());
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public HistCriteria findByID(Integer histCriterion_id) throws DataAccessException{
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		String sql = "select * from hst_criteria where hc_cr_criterion_id_fk = ?";
		
		Query query = session.createSQLQuery(sql).addEntity(HistCriteria.class);
		query.setLong(0, histCriterion_id);
		
		List<HistCriteria> lst_histcriteria = new ArrayList<HistCriteria>();
		lst_histcriteria = query.list();
		
		return lst_histcriteria.get(0);
	}
}
