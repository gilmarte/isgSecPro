package com.isg.ifrend.model.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.isg.ifrend.model.bean.Function;

public class FunctionDAO extends HibernateDaoSupport {
	
	public void saveOrUpdate(Function function) throws DataAccessException {
		getHibernateTemplate().saveOrUpdate(function);
	}

	public void delete(Function function) throws DataAccessException {
		getHibernateTemplate().delete(function);
	}

	public Function find(Class<Function> functionClass, String function_code) throws DataAccessException {
		Function function = (Function) getHibernateTemplate().load(functionClass, function_code);
		return function;
	}
	
	@SuppressWarnings("unchecked")
	public List<Function> findAll(Class<Function> functionClass) throws DataAccessException {
		List<Function> list = getHibernateTemplate().find("from " + functionClass.getName());
		return list;
	}

}
