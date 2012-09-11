package com.isg.ifrend.service;

import java.util.List;

import com.isg.ifrend.model.bean.SaUserGroup;

public interface SaUserGroupManager {
	public boolean save(SaUserGroup sausergroup);
	public boolean delete(SaUserGroup sausergroup);
	public SaUserGroup getSaUserGroup(String id);
	public List<SaUserGroup> getSaUserGroupList();
	public List<SaUserGroup> getSaUserGroupListById(String group_id);
	public List<SaUserGroup>find_all_Active_Pending(String group_id);
}
