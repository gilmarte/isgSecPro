package com.isg.ifrend.service;

import java.util.List;

import com.isg.ifrend.model.bean.TempUser;

public interface TempUserManager {

	public boolean saveOrUpdate(TempUser tempUser);
	public boolean save(TempUser tempUser);
	public boolean update(TempUser tempUser);
//	public boolean updateUserStatus(String userID, int status);
//	public boolean updateUserAction(String userID, int action);
	public boolean delete(TempUser tempUser);
	public TempUser findById(String temp_user_id);
//	public List<TempUser> getUserList();
	public List<String> getUserIdList();
	public List<String> getUserIdListById(String user_id);
	public List<String> getUserIdListByIdAndStatus(String user_id, Integer...status);
	public List<String> getUserIdListByStatus (Integer...status);
	public List<String> getUserIdListByIdAndAction(String user_id, Integer...action);
	public List<String> getUserIdListByAction(Integer...action);
	public boolean isUserExist(String userID);
}
