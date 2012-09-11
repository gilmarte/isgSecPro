package com.isg.ifrend.service;

import java.util.Date;
import java.util.List;

import com.isg.ifrend.model.bean.HistUserOrganization;

public interface HistUserOrganizationManager {

//	public boolean saveOrUpdate(TempUser tempUser);
	public boolean save(HistUserOrganization histUserOrg);
//	public boolean update(TempUser tempUser);
//	public boolean updateUserStatus(String userID, int status);
//	public boolean updateUserAction(String userID, int action);
//	public boolean delete(TempUser tempUser);
//	public HistUserOrganization findById(String orgID);
	public HistUserOrganization findByIndex(int historyIndex);
	public List<Integer> getUserOrgHistoryIndex();
	public List<Integer> getUserOrgHistoryIndexById(String orgID);
	public List<Integer> getUserOrgHistoryIndexByIdAndStatus(String orgID, Integer...status);
	public List<Integer> getUserOrgHistoryIndexByStatus (Integer...status);
	public List<Integer> getUserOrgHistoryIndexByIdAndAction(String orgID, Integer...action);
	public List<Integer> getUserOrgHistoryIndexByAction(Integer...action);
	public List<Integer> getUserOrgHistoryIndexByCustomFilter(
			String orgID, int action, int status, Date startDate, Date endDate);
	public List<HistUserOrganization> getUserOrgHistoryByCustomFilter(
    		String orgID, int action, int status, Date startDate, Date endDate);
//	public boolean isUserExist(String userID);
}
