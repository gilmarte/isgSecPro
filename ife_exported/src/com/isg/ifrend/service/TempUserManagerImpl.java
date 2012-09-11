package com.isg.ifrend.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.isg.ifrend.model.bean.HistUser;
import com.isg.ifrend.model.bean.TempUser;
import com.isg.ifrend.model.dao.HistUserDAO;
import com.isg.ifrend.model.dao.TempUserDAO;


public class TempUserManagerImpl implements TempUserManager {
	
	private TempUserDAO tempUserDAO;
	private HistUserDAO histUserDAO;

	public HistUserDAO getHistUserDAO() {
		return histUserDAO;
	}

	public void setHistUserDAO(HistUserDAO histUserDAO) {
		this.histUserDAO = histUserDAO;
	}

	@Transactional
	public boolean saveOrUpdate(TempUser tempUser) {
		try {
			tempUserDAO.saveOrUpdate(tempUser);
			tempUser = tempUserDAO.find(TempUser.class, tempUser.getUserID());
			histUserDAO.save(new HistUser(tempUser));
			return true;
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Transactional
	public boolean save(TempUser tempUser) {
		try {
			tempUserDAO.save(tempUser);
			tempUser = tempUserDAO.find(TempUser.class, tempUser.getUserID());
			histUserDAO.save(new HistUser(tempUser));
			return true;
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Transactional
	public boolean update(TempUser tempUser) {
		try {
			tempUserDAO.update(tempUser);
			tempUser = tempUserDAO.find(TempUser.class, tempUser.getUserID());
			histUserDAO.save(new HistUser(tempUser));
			return true;
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}
	
//	@Transactional
//	public boolean updateUserStatus(String userID, int status) {
//		try {
//			tempUserDAO.updateUserStatus(userID, status);
//			TempUser tempUser = tempUserDAO.find(TempUser.class, userID);
//			histUserDAO.save(new HistUser(tempUser));
//			return true;
//		}
//		catch (DataAccessException e) {
//			e.printStackTrace();
//			return false;
//		}
//	}
//	
//	@Transactional
//	public boolean updateUserAction(String userID, int action) {
//		try {
//			tempUserDAO.updateUserAction(userID, action);
//			TempUser tempUser = tempUserDAO.find(TempUser.class, userID);
//			histUserDAO.save(new HistUser(tempUser));
//			return true;
//		}
//		catch (DataAccessException e) {
//			e.printStackTrace();
//			return false;
//		}
//	}

	@Transactional
	public boolean delete(TempUser tempUser) {
		try {
			tempUserDAO.delete(tempUser);
			return true;
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional(readOnly = true)
	public TempUser findById(String temp_user_id) {
		try {
			return tempUserDAO.find(TempUser.class, temp_user_id);
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

//	@Override
//	public List<TempUser> getUserList() {
//		return tempUserDAO.findAll(TempUser.class);
//	}
	
	public List<String> getUserIdList() {
		try {
			return tempUserDAO.findAllUserID();
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<String> getUserIdListById(String user_id) {
		try {
			return tempUserDAO.findAllById(user_id);
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<String> getUserIdListByIdAndStatus(String user_id, Integer...status) {
		try {
			return tempUserDAO.findByIdAndStatus(user_id, status);
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<String> getUserIdListByStatus (Integer... status) {
		try {
			return tempUserDAO.findByStatus(status);
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<String> getUserIdListByIdAndAction(String user_id, Integer...action) {
		try {
			return tempUserDAO.findByIdAndAction(user_id, action);
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<String> getUserIdListByAction(Integer...action) {
		try {
			return tempUserDAO.findByAction(action);
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean isUserExist(String userID) {
		try {
			return tempUserDAO.isUserExist(userID);
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return true;
		}
	}

	public TempUserDAO getTempUserDAO() {
		return tempUserDAO;
	}

	public void setTempUserDAO(TempUserDAO tempUserDAO) {
		this.tempUserDAO = tempUserDAO;
	}

}
