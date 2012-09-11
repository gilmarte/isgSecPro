package com.isg.ifrend.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.isg.ifrend.model.bean.Country;

public class CountryDAO extends HibernateDaoSupport {
	
	public void saveOrUpdate(Country country) throws DataAccessException {
		getHibernateTemplate().saveOrUpdate(country);
	}

	public void delete(Country country) throws DataAccessException {
		getHibernateTemplate().delete(country);
	}

	public Country find(Class<Country> countryClass, String country_code) throws DataAccessException {
		Country country = (Country) getHibernateTemplate().load(countryClass, country_code);
		return country;
	}
	
	@SuppressWarnings("unchecked")
	public Country findByDescription(String description) throws DataAccessException{
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		String sql = "select * from ref_countries where co_country_code_pk = ?";
		
		Query query = session.createSQLQuery(sql).addEntity(Country.class);
		query.setString(0, description);
		
		List<Country>lst_country = new ArrayList<Country>();
		lst_country = query.list();
		return lst_country.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public List<Country> findAll(Class<Country> countryClass) throws DataAccessException {
		List<Country> list = getHibernateTemplate().find("from " + countryClass.getName());
		return list;
	}
}
