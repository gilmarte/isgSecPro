package com.isg.ifrend.service;

import java.util.List;

import com.isg.ifrend.model.bean.TempUserOrganization;


public interface TempUserOrganizationManager {
	
	public boolean saveOrUpdate(TempUserOrganization tempUserOrg);
	public boolean save(TempUserOrganization tempUserOrg);
	public boolean update(TempUserOrganization tempUserOrg);
	public boolean delete(TempUserOrganization tempUserOrg);
//	public boolean updateUserOrgStatus(String orgID, int status);
//	public boolean updateUserOrgAction(String orgID, int action);
	public TempUserOrganization findById(String orgID);
	public List<String> getUserOrgIdList();
	public List<String> getUserOrgIdListByID(String orgID);
	public List<String> getUserOrgIdListByIdAndStatus(String orgID, Integer...status);
	public List<String> getUserOrgIdListByStatus (Integer...status);
	public List<String> getUserOrgIdListByIdAndAction(String orgID, Integer...action);
	public List<String> getUserOrgIdListByAction(Integer...action);
	public boolean isUserOrgExist(String orgID);
}
