package com.isg.ifrend.model.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.isg.ifrend.model.bean.ElementFormats;

public class ElementFormatsDAO extends HibernateDaoSupport{

	public void saveOrUpdate(ElementFormats elementFormats) throws DataAccessException {
		getHibernateTemplate().saveOrUpdate(elementFormats);
	}

	public void delete(ElementFormats elementFormats) throws DataAccessException {
		getHibernateTemplate().delete(elementFormats);
	}

	public ElementFormats find(Class<ElementFormats> elementFormatsClass, Integer elementFormats_id) throws DataAccessException {
		ElementFormats elementFormats = (ElementFormats) getHibernateTemplate().load(elementFormatsClass, elementFormats_id);
		return elementFormats;
	}
	
	@SuppressWarnings("unchecked")
	public List<ElementFormats> findAll(Class<ElementFormats> elementFormatsClass) throws DataAccessException {
		List<ElementFormats> list = getHibernateTemplate().find("from " + elementFormatsClass.getName());
		return list;
	}
	
}
