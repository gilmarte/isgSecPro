package com.isg.ifrend.model.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.isg.ifrend.model.bean.GlobalMaxID;

public class GlobalMaxIDDAO extends HibernateDaoSupport {

	public void saveOrUpdate(GlobalMaxID globalMaxID) throws DataAccessException {
		getHibernateTemplate().saveOrUpdate(globalMaxID);
	}

	public void delete(GlobalMaxID globalMaxID) throws DataAccessException {
		getHibernateTemplate().delete(globalMaxID);
	}

	@SuppressWarnings("unchecked")
	public List<GlobalMaxID> findAll(Class<GlobalMaxID> globalMaxIDClass) throws DataAccessException {
		List<GlobalMaxID> list = getHibernateTemplate().find("from " + globalMaxIDClass.getName());
		return list;
	}
}
