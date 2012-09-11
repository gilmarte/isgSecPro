package com.isg.ifrend.model.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.isg.ifrend.model.bean.SelectedFunctions;

public class SelectedFunctionsDAO extends HibernateDaoSupport {
	public void saveOrUpdate(SelectedFunctions selectedfunctions) throws DataAccessException {
		
		getHibernateTemplate().saveOrUpdate(selectedfunctions);			
	}

	public void delete(SelectedFunctions selectedfunctions) throws DataAccessException {
		getHibernateTemplate().delete(selectedfunctions);
	}

	public SelectedFunctions find(Class<SelectedFunctions> clazz, Integer id) throws DataAccessException {
		SelectedFunctions selectedfunctions = (SelectedFunctions) getHibernateTemplate().load(clazz, id);
		return selectedfunctions;
	}

	@SuppressWarnings("unchecked")
	public List<SelectedFunctions> findAll(Class<SelectedFunctions> clazz) throws DataAccessException {
		List<SelectedFunctions> list = getHibernateTemplate().find("from " + clazz.getName());
		return list;
	}
	
	
}
