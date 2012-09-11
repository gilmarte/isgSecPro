package com.isg.ifrend.service;

import java.util.List;

import com.isg.ifrend.model.bean.User;

public interface UserManager {

	public boolean saveOrUpdate(User user);
	public boolean save(User user);
	public boolean update(User user);
//	public boolean updateUserStatus(String userID, int status);
//	public boolean updateUserAction(String userID, int action);
	public boolean delete(User user);
	public User findById(String user_id);
	public List<User> getUserListById(String user_id);
	public List<String> getUserIdListById(String user_id);
	public List<String> getUserList();
	public List<String> getUserListByStatus (Integer... status);
	public boolean isUserExist(String userID);
	public boolean isUserAdmin(String userID);
}
