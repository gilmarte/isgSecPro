package com.isg.ifrend.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.isg.ifrend.model.bean.User;
import com.isg.ifrend.model.dao.UserDAO;


public class UserManagerImpl implements UserManager {
	
	private UserDAO userDAO;

	@Transactional
	public boolean saveOrUpdate(User user) {
		try {
			userDAO.saveOrUpdate(user);
			return true;
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Transactional
	public boolean save(User user) {
		try {
			userDAO.save(user);
			return true;
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Transactional
	public boolean update(User user) {
		try {
			userDAO.update(user);
			return true;
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean delete(User user) {
		try {
			userDAO.delete(user);
			return true;
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional(readOnly = true)
	public User findById(String user_id) {
		try {
			return userDAO.find(User.class, user_id);
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<User> getUserListById(String userID) {
		try {
			return userDAO.findAllUsersById(userID);
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<String> getUserIdListById(String user_id) {
		try {
			return userDAO.findAllById(user_id);
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean isUserExist(String userID) {
		try {
			return userDAO.isUserExist(userID);
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return true;
		}
	}
	
	public boolean isUserAdmin(String userID) {
		try {
			return userDAO.isUserAdmin(userID);
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return true;
		}
	}

	@Override
	public List<String> getUserList() {
		try {
			return userDAO.findAllId(User.class);
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<String> getUserListByStatus (Integer... status) {
		try {
			return userDAO.findByStatus(status);
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
}
