package com.isg.ifrend.service;

import java.util.List;

import com.isg.ifrend.model.bean.TmpSaUserGroup;

public interface TmpSaUserGroupManager {
	public boolean save(TmpSaUserGroup tmpsausergroup);
    public boolean delete(TmpSaUserGroup tmpsausergroup);
    public TmpSaUserGroup getTmpSaUserGroup(String id);
    public List<TmpSaUserGroup> getTmpSaUserGroupList();
    public List<TmpSaUserGroup> getPending_TmpSaUserGroupList();    
    public List<TmpSaUserGroup> getTmpSaUserGroupListById(String group_id);    
    public List<TmpSaUserGroup> getCustom_TmpSaUserGroupList(String group_id, String action, String status);
    public List<TmpSaUserGroup> getAuth_Search_Custom_TmpSaUserGroupList(String group_id, String action, String status);
}
