package com.isg.ifrend.service;

import java.util.List;

import com.isg.ifrend.model.bean.UserOrganization;


public interface UserOrganizationManager {
	
	public boolean save(UserOrganization userOrg);
	public boolean update(UserOrganization userOrg);
	public boolean saveOrUpdate(UserOrganization userOrg);
	public boolean delete(UserOrganization userOrg);
//	public boolean updateUserOrgStatus(String orgID, int status);
//	public boolean updateUserOrgAction(String orgID, int action);
	public boolean isUserOrgExist(String orgID);
	public UserOrganization findById(String userOrgID);
	public List<UserOrganization> getUserOrgList();
	public List<UserOrganization> getUserOrgListByID(String orgID);
	public List<String> getUserOrgIdList();
	public List<String> getUserOrgIdListByID(String orgID);
	public List<String> getUserOrgIdListByStatus (Integer... status);

}
