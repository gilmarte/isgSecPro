package com.isg.ifrend.model.bean;

import java.io.Serializable;

public class Country implements Serializable {

	private static final long serialVersionUID = 1L;

	private String country_code;
	private String country_desc;
	
	public Country(){}
	
	public Country(String country_code, String country_desc) {
		super();
		this.country_code = country_code;
		this.country_desc = country_desc;
	}

	public String getCountry_code() {
		return country_code;
	}

	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}

	public String getCountry_desc() {
		return country_desc;
	}

	public void setCountry_desc(String country_desc) {
		this.country_desc = country_desc;
	}
	
}
