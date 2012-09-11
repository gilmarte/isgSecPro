package com.isg.ifrend.service;

import java.util.List;

import com.isg.ifrend.model.bean.SaUserGroupHistory;

public interface SaUserGroupHistoryManager {

	public boolean save(SaUserGroupHistory sausergrouphistory);
    public boolean delete(SaUserGroupHistory sausergrouphistory);
    public SaUserGroupHistory getSaUserGroupHistory(String id);
    public SaUserGroupHistory getSaUserGroupHistory(String id, Integer seq_id);
    public List<SaUserGroupHistory> getSaUserGroupHistoryList();
    public List<SaUserGroupHistory> getPending_SaUserGroupHistoryList();
    public List<SaUserGroupHistory> getDistinct_SaUserGroupHistoryList();
    public List<SaUserGroupHistory> getSaUserGroupHistoryListById(String group_id);    
    public List<SaUserGroupHistory> getCustom_SaUserGroupHistoryList(String group_id, String action, String status, String startdate, String enddate);
}
