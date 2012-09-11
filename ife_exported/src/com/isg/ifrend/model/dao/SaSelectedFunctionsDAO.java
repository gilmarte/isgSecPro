package com.isg.ifrend.model.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.isg.ifrend.model.bean.SaSelectedFunctions;

public class SaSelectedFunctionsDAO extends HibernateDaoSupport {
	public void saveOrUpdate(SaSelectedFunctions saselectedfunctions) throws DataAccessException {
		
		getHibernateTemplate().saveOrUpdate(saselectedfunctions);		
	}

	public void delete(SaSelectedFunctions saselectedfunctions) throws DataAccessException {
		getHibernateTemplate().delete(saselectedfunctions);
	}

	public SaSelectedFunctions find(Class<SaSelectedFunctions> clazz, Integer id) throws DataAccessException {
		SaSelectedFunctions saselectedfunctions = (SaSelectedFunctions) getHibernateTemplate().load(clazz, id);
		return saselectedfunctions;
	}

	@SuppressWarnings("unchecked")
	public List<SaSelectedFunctions> findAll(Class<SaSelectedFunctions> clazz) throws DataAccessException {
		List<SaSelectedFunctions> list = getHibernateTemplate().find("from " + clazz.getName());
		return list;
	}
	
	
}
