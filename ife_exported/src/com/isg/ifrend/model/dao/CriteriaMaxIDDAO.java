package com.isg.ifrend.model.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.isg.ifrend.model.bean.CriteriaMaxID;

public class CriteriaMaxIDDAO extends HibernateDaoSupport {

	public void save(CriteriaMaxID criteriaMaxID) throws DataAccessException {
		getHibernateTemplate().save(criteriaMaxID);
	}

	public void delete(CriteriaMaxID criteriaMaxID) throws DataAccessException {
		getHibernateTemplate().delete(criteriaMaxID);
	}

	@SuppressWarnings("unchecked")
	public int findMaxID(Class<CriteriaMaxID> criteriaMaxIDClass) throws DataAccessException {
		StringBuilder queryStr = new StringBuilder();
		queryStr.append("SELECT max(a.criteriaMax_id) from CriteriaMaxID a");

		Query query = getSession().createQuery(queryStr.toString());
		List<Integer> maxID = query.list();
		int criMaxID;
		try {
			criMaxID = maxID.get(0);
			return criMaxID;
		} catch (Exception e) {
			return 1;
		}
	}
}
