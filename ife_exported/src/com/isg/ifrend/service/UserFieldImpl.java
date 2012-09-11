package com.isg.ifrend.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.isg.ifrend.model.bean.UserField;
import com.isg.ifrend.model.dao.UserFieldDAO;

public class UserFieldImpl implements UserFieldManager {

	private UserFieldDAO userFieldDAO;

	public UserFieldDAO getUserFieldDAO() {
		return userFieldDAO;
	}

	public void setUserFieldDAO(UserFieldDAO userFieldDAO) {
		this.userFieldDAO = userFieldDAO;
	}

	@Transactional
	public boolean save(UserField userField) {
		try {
			userFieldDAO.saveOrUpdate(userField);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean delete(UserField userField) {
		try {
			userFieldDAO.delete(userField);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional(readOnly = true)
	public UserField findById(int userFieldID) {
		try {
			return userFieldDAO.find(UserField.class, userFieldID);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Transactional(readOnly = true)
	public List<UserField> getUserFieldList() {
		try {
			return userFieldDAO.findAll(UserField.class);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
}
