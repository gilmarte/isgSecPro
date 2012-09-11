package com.isg.ifrend.model.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.isg.ifrend.model.bean.BusinessEntity;


public class BusinessEntityDAO extends HibernateDaoSupport {
	
	public void saveOrUpdate(BusinessEntity businessEntity) throws DataAccessException {
		getHibernateTemplate().saveOrUpdate(businessEntity);
	}

	public void delete(BusinessEntity businessEntity) throws DataAccessException {
		getHibernateTemplate().delete(businessEntity);
	}

	public BusinessEntity find(Class<BusinessEntity> businessEntityClass, String bus_ent_id) throws DataAccessException {
		BusinessEntity action = (BusinessEntity) getHibernateTemplate().load(businessEntityClass, bus_ent_id);
		return action;
	}
	
	@SuppressWarnings("unchecked")
	public List<BusinessEntity> findAll(Class<BusinessEntity> businessEntityClass) throws DataAccessException {
		List<BusinessEntity> list = getHibernateTemplate().find("from " + businessEntityClass.getName());
		return list;
	}


}
