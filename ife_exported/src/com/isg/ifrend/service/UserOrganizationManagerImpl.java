package com.isg.ifrend.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.isg.ifrend.model.bean.UserOrganization;
import com.isg.ifrend.model.dao.UserOrganizationDAO;

public class UserOrganizationManagerImpl implements UserOrganizationManager {
	
	private UserOrganizationDAO userOrgDAO;

	@Transactional
	public boolean save(UserOrganization userOrg) {
		try {
			userOrgDAO.save(userOrg);
			return true;
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean update(UserOrganization userOrg) {
		try {
			userOrgDAO.update(userOrg);
			return true;
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Transactional
	public boolean saveOrUpdate(UserOrganization userOrg) {
		try {
			userOrgDAO.saveOrUpdate(userOrg);
			return true;
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Transactional
	public boolean delete(UserOrganization userOrg) {
		try {
			userOrgDAO.delete(userOrg);
			return true;
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

//	@Transactional
//	public boolean updateUserOrgStatus(String orgID, int status) {
//		try {
//			userOrgDAO.updateUserOrgStatus(orgID, status);
//			return true;
//		}
//		catch (DataAccessException e) {
//			e.printStackTrace();
//			return false;
//		}
//	}
//
//	@Transactional
//	public boolean updateUserOrgAction(String orgID, int action) {
//		try {
//			userOrgDAO.updateUserOrgAction(orgID, action);
//			return true;
//		}
//		catch (DataAccessException e) {
//			e.printStackTrace();
//			return false;
//		}
//	}

	@Transactional(readOnly = true)
	public UserOrganization findById(String org_id) {
		try {
			return userOrgDAO.find(UserOrganization.class, org_id);
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<UserOrganization> getUserOrgList() {
		try {
			return userOrgDAO.findAll(UserOrganization.class);
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<String> getUserOrgIdList() {
		try {
			return userOrgDAO.findAllId(UserOrganization.class);
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Transactional
	public List<String> getUserOrgIdListByID(String orgID) {
		try {
			return userOrgDAO.findAllById(orgID);
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Transactional
	public List<UserOrganization> getUserOrgListByID(String orgID) {
		try {
			return userOrgDAO.findAllUserOrgById(orgID);
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Transactional
	public List<String> getUserOrgIdListByStatus (Integer... status) {
		try {
			return userOrgDAO.findByStatus(status);
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Transactional
	public boolean isUserOrgExist(String orgID) {
		try {
			return userOrgDAO.isUserOrgExist(orgID);
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	public UserOrganizationDAO getUserOrgDAO() {
		return userOrgDAO;
	}

	public void setUserOrgDAO(UserOrganizationDAO userOrgDAO) {
		this.userOrgDAO = userOrgDAO;
	}
}
