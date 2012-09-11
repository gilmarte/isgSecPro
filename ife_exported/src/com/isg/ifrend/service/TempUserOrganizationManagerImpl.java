package com.isg.ifrend.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.isg.ifrend.model.bean.HistUserOrganization;
import com.isg.ifrend.model.bean.TempUserOrganization;
import com.isg.ifrend.model.dao.HistUserOrganizationDAO;
import com.isg.ifrend.model.dao.TempUserOrganizationDAO;

public class TempUserOrganizationManagerImpl implements TempUserOrganizationManager {
	
	private TempUserOrganizationDAO tempUserOrgDAO;
	private HistUserOrganizationDAO histUserOrgDAO;

	@Transactional
	public boolean saveOrUpdate(TempUserOrganization tempUserOrg) {
		try {
			tempUserOrgDAO.saveOrUpdate(tempUserOrg);
			tempUserOrg = tempUserOrgDAO.find(TempUserOrganization.class, tempUserOrg.getOrgID());
			histUserOrgDAO.save(new HistUserOrganization(tempUserOrg));
			return true;
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Transactional
	public boolean save(TempUserOrganization tempUserOrg) {
		try {
			tempUserOrgDAO.save(tempUserOrg);
			tempUserOrg = tempUserOrgDAO.find(TempUserOrganization.class, tempUserOrg.getOrgID());
			histUserOrgDAO.save(new HistUserOrganization(tempUserOrg));
			return true;
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Transactional
	public boolean update(TempUserOrganization tempUserOrg) {
		try {
			tempUserOrgDAO.update(tempUserOrg);
			tempUserOrg = tempUserOrgDAO.find(TempUserOrganization.class, tempUserOrg.getOrgID());
			histUserOrgDAO.save(new HistUserOrganization(tempUserOrg));
			return true;
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean delete(TempUserOrganization tempUserOrg) {
		try {
			tempUserOrgDAO.delete(tempUserOrg);
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
//			tempUserOrgDAO.updateUserOrgStatus(orgID, status);
//			TempUserOrganization tempUserOrg = tempUserOrgDAO.find(TempUserOrganization.class, orgID);
//			histUserOrgDAO.save(new HistUserOrganization(tempUserOrg));
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
//			tempUserOrgDAO.updateUserOrgAction(orgID, action);
//			TempUserOrganization tempUserOrg = tempUserOrgDAO.find(TempUserOrganization.class, orgID);
//			histUserOrgDAO.save(new HistUserOrganization(tempUserOrg));
//			return true;
//		}
//		catch (DataAccessException e) {
//			e.printStackTrace();
//			return false;
//		}
//	}

	@Transactional(readOnly = true)
	public TempUserOrganization findById(String org_id) {
		try {
			return tempUserOrgDAO.find(TempUserOrganization.class, org_id);
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<String> getUserOrgIdList() {
		try {
			return tempUserOrgDAO.findAll(TempUserOrganization.class);
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Transactional
	public List<String> getUserOrgIdListByID(String orgID) {
		try {
			return tempUserOrgDAO.findAllById(orgID);
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Transactional
	public List<String> getUserOrgIdListByIdAndStatus(String orgID, Integer...status) {
		try {
			return tempUserOrgDAO.findByIdAndStatus(orgID, status);
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Transactional
	public List<String> getUserOrgIdListByStatus (Integer... status) {
		try {
			return tempUserOrgDAO.findByStatus(status);
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	@Transactional
	public List<String> getUserOrgIdListByIdAndAction(String orgID, Integer...action) {
		try {
			return tempUserOrgDAO.findByIdAndAction(orgID, action);
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Transactional
	public List<String> getUserOrgIdListByAction(Integer...action) {
		try {
			return tempUserOrgDAO.findByAction(action);
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Transactional
	public boolean isUserOrgExist(String orgID) {
		try {
			return tempUserOrgDAO.isUserOrgExist(orgID);
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	public TempUserOrganizationDAO getTempUserOrgDAO() {
		return tempUserOrgDAO;
	}

	public void setTempUserOrgDAO(TempUserOrganizationDAO tempUserOrgDAO) {
		this.tempUserOrgDAO = tempUserOrgDAO;
	}

	public HistUserOrganizationDAO getHistUserOrgDAO() {
		return histUserOrgDAO;
	}

	public void setHistUserOrgDAO(HistUserOrganizationDAO histUserOrgDAO) {
		this.histUserOrgDAO = histUserOrgDAO;
	}
}
