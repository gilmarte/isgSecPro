package com.isg.ifrend.security.service;

import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.zkoss.zkplus.spring.SpringUtil;

import com.isg.ifrend.security.dao.CustomJdbcDaoImpl;

public class SecurityServiceLocator {

	private SecurityServiceLocator() {}
	
	public static CustomJdbcDaoImpl getCustomJdbcDaoImpl() {
		return (CustomJdbcDaoImpl) SpringUtil.getBean("jdbcUserService", CustomJdbcDaoImpl.class);
	}
	
	public static PasswordEncoder getPasswordEncoder() {
		return (PasswordEncoder) SpringUtil.getBean("passwordEncoder", PasswordEncoder.class);
	}
	
}
