package com.isg.ifrend.model.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.isg.ifrend.model.bean.DateFormats;

public class DateFormatsDAO extends HibernateDaoSupport {
	
	public void saveOrUpdate(DateFormats dateFormats) throws DataAccessException {
		getHibernateTemplate().saveOrUpdate(dateFormats);
	}

	public void delete(DateFormats dateFormats) throws DataAccessException {
		getHibernateTemplate().delete(dateFormats);
	}

	public DateFormats find(Class<DateFormats> dateFormatsClass, Integer dateFormat_id) throws DataAccessException {
		DateFormats dateFormats = (DateFormats) getHibernateTemplate().load(dateFormatsClass, dateFormat_id);
		return dateFormats;
	}
	
	@SuppressWarnings("unchecked")
	public List<DateFormats> findAll(Class<DateFormats> dateFormatsClass) throws DataAccessException {
		List<DateFormats> list = getHibernateTemplate().find("from " + dateFormatsClass.getName());
		return list;
	}
}
