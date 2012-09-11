package com.isg.ifrend.service;

import java.util.Date;
import java.util.List;

import com.isg.ifrend.model.bean.HistUser;

public interface HistUserManager {

//	public boolean saveOrUpdate(TempUser tempUser);
	public boolean save(HistUser histUser);
//	public boolean update(TempUser tempUser);
//	public boolean updateUserStatus(String userID, int status);
//	public boolean updateUserAction(String userID, int action);
//	public boolean delete(TempUser tempUser);
//	public HistUser findById(String user_id);
	public HistUser findByIndex(int historyIndex);
	public List<Integer> getUserHistoryIndex();
	public List<Integer> getUserHistoryIndexById(String user_id);
	public List<Integer> getUserHistoryIndexByIdAndStatus(String user_id, Integer...status);
	public List<Integer> getUserHistoryIndexByStatus (Integer...status);
	public List<Integer> getUserHistoryIndexByIdAndAction(String user_id, Integer...action);
	public List<Integer> getUserHistoryIndexByAction(Integer...action);
	public List<Integer> getUserHistoryIndexByCustomFilter(
			String userID, int action, int status, Date startDate, Date endDate);
	public List<HistUser> getUserHistoryByCustomFilter(
			String userID, int action, int status, Date startDate, Date endDate);
//	public boolean isUserExist(String userID);
}
