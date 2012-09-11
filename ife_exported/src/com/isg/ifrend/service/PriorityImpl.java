package com.isg.ifrend.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.isg.ifrend.model.bean.Priority;
import com.isg.ifrend.model.dao.PriorityDAO;

public class PriorityImpl implements PriorityManager {

	private PriorityDAO priorityDAO;
	
	public PriorityDAO getPriorityDAO() {
		return priorityDAO;
	}

	public void setPriorityDAO(PriorityDAO priorityDAO) {
		this.priorityDAO = priorityDAO;
	}

	@Transactional
	public boolean save(Priority priority) {
		try {
			priorityDAO.saveOrUpdate(priority);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean delete(Priority priority) {
		try {
			priorityDAO.delete(priority);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional(readOnly = true)
	public Priority findById(int priorityID) {
		try {
			return priorityDAO.find(Priority.class, priorityID);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Transactional(readOnly = true)
	public List<Priority> getPriorityList() {
		try {
			return priorityDAO.findAll(Priority.class);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Priority findByDescription(String description) {
		// TODO Auto-generated method stub
		return priorityDAO.findByDescription(description);
	}
}
