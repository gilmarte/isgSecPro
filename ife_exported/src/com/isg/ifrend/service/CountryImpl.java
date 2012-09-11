package com.isg.ifrend.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.isg.ifrend.model.bean.Country;
import com.isg.ifrend.model.dao.CountryDAO;

public class CountryImpl implements CountryManager {

	private CountryDAO countryDAO;

	public CountryDAO getCountryDAO() {
		return countryDAO;
	}

	public void setCountryDAO(CountryDAO countryDAO) {
		this.countryDAO = countryDAO;
	}
	
	@Transactional
	public boolean save(Country country) {
		try {
			countryDAO.saveOrUpdate(country);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean delete(Country country) {
		try {
			countryDAO.delete(country);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional(readOnly = true)
	public Country findById(String country_code) {
		try {
			return countryDAO.find(Country.class, country_code);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Transactional(readOnly = true)
	public List<Country> getCountryList() {
		try {
			return countryDAO.findAll(Country.class);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Country findByDescription(String description) {
		// TODO Auto-generated method stub
		return countryDAO.findByDescription(description);
	}
}
