package com.isg.ifrend.model.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.isg.ifrend.model.bean.LanguageCode;

/**
 * 
 * @author kristine.furto
 *
 */
public class LanguageCodeDAO extends HibernateDaoSupport {

	public void saveOrUpdate(LanguageCode languageCode)
			throws DataAccessException {
		getHibernateTemplate().saveOrUpdate(languageCode);
	}

	public void delete(LanguageCode languageCode) throws DataAccessException {
		getHibernateTemplate().delete(languageCode);
	}

	public LanguageCode find(Class<LanguageCode> languageCodeClass,
			String languageCode_id) throws DataAccessException {
		LanguageCode languageCode = (LanguageCode) getHibernateTemplate().load(
				languageCodeClass, languageCode_id);
		return languageCode;
	}

	@SuppressWarnings("unchecked")
	public List<LanguageCode> findAll(Class<LanguageCode> languageCodeClass)
			throws DataAccessException {
		List<LanguageCode> list = getHibernateTemplate().find(
				"from " + languageCodeClass.getName());
		return list;
	}

}
