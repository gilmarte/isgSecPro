package com.isg.ifrend.service;

import java.util.List;

import com.isg.ifrend.model.bean.UserField;

public interface UserFieldManager {

	public boolean save(UserField userField);
	public boolean delete(UserField userField);
	public UserField findById(int userFieldID);
	public List<UserField> getUserFieldList();
}
