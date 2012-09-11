package com.isg.ifrend.model.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.isg.ifrend.model.bean.Operator;

public class OperatorDAO extends HibernateDaoSupport {
	
	public void saveOrUpdate(Operator operator) throws DataAccessException {
		getHibernateTemplate().saveOrUpdate(operator);
	}

	public void delete(Operator operator) throws DataAccessException {
		getHibernateTemplate().delete(operator);
	}

	public Operator find(Class<Operator> operatorClass, String operator_code) throws DataAccessException {
		Operator operator = (Operator) getHibernateTemplate().load(operatorClass, operator_code);
		return operator;
	}
	
	@SuppressWarnings("unchecked")
	public List<Operator> findAll(Class<Operator> operatorClass) throws DataAccessException {
		List<Operator> list = getHibernateTemplate().find("from " + operatorClass.getName());
		return list;
	}

}
