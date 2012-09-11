package com.isg.ifrend.service;

import java.util.List;

import com.isg.ifrend.model.bean.Country;

public interface CountryManager {

	public boolean save(Country country);
	public boolean delete(Country country);
	public Country findById(String country_code);
	public Country findByDescription(String description);
	public List<Country> getCountryList();
	
}
