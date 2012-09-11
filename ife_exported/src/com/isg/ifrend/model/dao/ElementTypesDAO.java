package com.isg.ifrend.model.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.isg.ifrend.model.bean.ElementTypes;

public class ElementTypesDAO extends HibernateDaoSupport {

	public void saveOrUpdate(ElementTypes elementTypes) throws DataAccessException {
		getHibernateTemplate().saveOrUpdate(elementTypes);
	}

	public void delete(ElementTypes elementTypes) throws DataAccessException {
		getHibernateTemplate().delete(elementTypes);
	}

	public ElementTypes find(Class<ElementTypes> elementTypesClass, Integer elementType_id) throws DataAccessException {
		ElementTypes elementTypes = (ElementTypes) getHibernateTemplate().load(elementTypesClass, elementType_id);
		return elementTypes;
	}
	
	@SuppressWarnings("unchecked")
	public List<ElementTypes> findAll(Class<ElementTypes> elementTypesClass) throws DataAccessException {
		List<ElementTypes> list = getHibernateTemplate().find("from " + elementTypesClass.getName());
		return list;
	}
}
