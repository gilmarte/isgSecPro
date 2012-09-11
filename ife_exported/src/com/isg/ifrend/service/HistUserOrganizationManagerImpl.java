package com.isg.ifrend.service;

import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.isg.ifrend.model.bean.HistUserOrganization;
import com.isg.ifrend.model.dao.HistUserOrganizationDAO;


public class HistUserOrganizationManagerImpl implements HistUserOrganizationManager{
	
	private HistUserOrganizationDAO histUserOrgDAO;

//	@Transactional
//	public boolean saveOrUpdate(HistUserOrganization histUserOrg) {
//		try {
//			histUserOrgDAO.saveOrUpdate(histUserOrg);
//			return true;
//		}
//		catch (DataAccessException e) {
//			e.printStackTrace();
//			return false;
//		}
//	}
	
	@Transactional
	public boolean save(HistUserOrganization histUserOrg) {
		try {
			histUserOrgDAO.save(histUserOrg);
			return true;
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}
	
//	@Transactional
//	public boolean update(HistUserOrganization histUserOrg) {
//		try {
//			histUserOrgDAO.update(histUserOrg);
//			return true;
//		}
//		catch (DataAccessException e) {
//			e.printStackTrace();
//			return false;
//		}
//	}
//	
//	@Transactional
//	public boolean updateUserStatus(String userID, int status) {
//		try {
//			histUserOrgDAO.updateUserStatus(userID, status);
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
//			histUserOrgDAO.updateUserAction(userID, action);
//			return true;
//		}
//		catch (DataAccessException e) {
//			e.printStackTrace();
//			return false;
//		}
//	}
//
//	@Transactional
//	public boolean delete(HistUserOrganization histUserOrg) {
//		try {
//			histUserOrgDAO.delete(histUserOrg);
//			return true;
//		}
//		catch (DataAccessException e) {
//			e.printStackTrace();
//			return false;
//		}
//	}

	@Transactional(readOnly = true)
	public HistUserOrganization findById(String temp_user_id) {
		try {
			return histUserOrgDAO.find(HistUserOrganization.class, temp_user_id);
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public HistUserOrganization findByIndex(int historyIndex) {
		try {
			return histUserOrgDAO.find(HistUserOrganization.class, historyIndex);
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Integer> getUserOrgHistoryIndex() {
		try {
			return histUserOrgDAO.findAll(HistUserOrganization.class);
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<Integer> getUserOrgHistoryIndexById(String orgID) {
		try {
			return histUserOrgDAO.findAllById(orgID);
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Integer> getUserOrgHistoryIndexByIdAndStatus(String orgID, Integer...status) {
		try {
			return histUserOrgDAO.findByIdAndStatus(orgID, status);
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Integer> getUserOrgHistoryIndexByStatus (Integer... status) {
		try {
			return histUserOrgDAO.findByStatus(status);
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Integer> getUserOrgHistoryIndexByIdAndAction(String orgID, Integer...action) {
		try {
			return histUserOrgDAO.findByIdAndStatus(orgID, action);
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Integer> getUserOrgHistoryIndexByAction(Integer...action) {
		try {
			return histUserOrgDAO.findByStatus(action);
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Integer> getUserOrgHistoryIndexByCustomFilter(
			String orgID, int action, int status, Date startDate, Date endDate) {
		try {
			return histUserOrgDAO.findByCustomFilter(orgID, action, status, startDate, endDate);
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<HistUserOrganization> getUserOrgHistoryByCustomFilter(
			String orgID, int action, int status, Date startDate, Date endDate) {
		try {
			return histUserOrgDAO.findHistUserOrgListByCustomFilter(orgID, action, status, startDate, endDate);
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}


	public HistUserOrganizationDAO getHistUserOrgDAO() {
		return histUserOrgDAO;
	}

	public void setHistUserOrgDAO(HistUserOrganizationDAO histUserOrgDAO) {
		this.histUserOrgDAO = histUserOrgDAO;
	}

}
