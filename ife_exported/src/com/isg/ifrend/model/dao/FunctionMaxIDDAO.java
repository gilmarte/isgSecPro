package com.isg.ifrend.model.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.isg.ifrend.model.bean.FunctionMaxID;

public class FunctionMaxIDDAO extends HibernateDaoSupport {

	public void saveOrUpdate(FunctionMaxID functionMaxID) throws DataAccessException {
		getHibernateTemplate().saveOrUpdate(functionMaxID);
	}

	public void delete(FunctionMaxID functionMaxID) throws DataAccessException {
		getHibernateTemplate().delete(functionMaxID);
	}

	@SuppressWarnings("unchecked")
	public List<FunctionMaxID> findAll(Class<FunctionMaxID> functionMaxIDClass) throws DataAccessException {
		List<FunctionMaxID> list = getHibernateTemplate().find("from " + functionMaxIDClass.getName());
		return list;
	}
}
