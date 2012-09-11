package com.isg.ifrend.service;

import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.isg.ifrend.model.bean.HistUser;
import com.isg.ifrend.model.dao.HistUserDAO;


public class HistUserManagerImpl implements HistUserManager {
	
	private HistUserDAO histUserDAO;

	
	@Transactional
	public boolean save(HistUser histUser) {
		try {
			histUserDAO.save(histUser);
			return true;
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public List<Integer> getUserHistoryIndex() {
		try {
			return histUserDAO.findAll(HistUser.class);
		}
		catch(DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public HistUser findByIndex(int historyIndex) {
		try {
			return histUserDAO.find(HistUser.class, historyIndex);
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<Integer> getUserHistoryIndexById(String user_id) {
		try {
			return histUserDAO.findAllById(user_id);
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Integer> getUserHistoryIndexByIdAndStatus(String user_id, Integer...status) {
		try {
			return histUserDAO.findByIdAndStatus(user_id, status);
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Integer> getUserHistoryIndexByStatus (Integer... status) {
		try {
			return histUserDAO.findByStatus(status);
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Integer> getUserHistoryIndexByIdAndAction(String user_id, Integer...action) {
		try {
			return histUserDAO.findByIdAndStatus(user_id, action);
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Integer> getUserHistoryIndexByAction(Integer...action) {
		try {
			return histUserDAO.findByStatus(action);
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Integer> getUserHistoryIndexByCustomFilter(
			String userID, int action, int status, Date startDate, Date endDate) {
		try {
			return histUserDAO.findByCustomFilter(userID, action, status, startDate, endDate);
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<HistUser> getUserHistoryByCustomFilter(
			String userID, int action, int status, Date startDate, Date endDate) {
		try {
			return histUserDAO.findHistUserByCustomFilter(userID, action, status, startDate, endDate);
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	public HistUserDAO getHistUserDAO() {
		return histUserDAO;
	}

	public void setHistUserDAO(HistUserDAO histUserDAO) {
		this.histUserDAO = histUserDAO;
	}
}
