package com.isg.ifrend.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.isg.ifrend.model.bean.SaUserGroupHistory;
import com.isg.ifrend.model.dao.SaUserGroupHistoryDAO;

public class SaUserGroupHistoryManagerImpl implements SaUserGroupHistoryManager {

	
	
	SaUserGroupHistoryDAO sausergrouphistoryDAO;
	
	public SaUserGroupHistoryDAO getSausergrouphistoryDAO() {
		return sausergrouphistoryDAO;
	}

	public void setSausergrouphistoryDAO(SaUserGroupHistoryDAO sausergrouphistoryDAO) {
		this.sausergrouphistoryDAO = sausergrouphistoryDAO;
	}

	@Transactional
	public boolean save(SaUserGroupHistory tmpsausergroup) {
		// TODO Auto-generated method stub
		try {
			sausergrouphistoryDAO.saveOrUpdate(tmpsausergroup);			
			return true;
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}

	@Transactional
	public boolean delete(SaUserGroupHistory tmpsausergroup) {
		// TODO Auto-generated method stub
		try {
			sausergrouphistoryDAO.delete(tmpsausergroup);
			return true;
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public SaUserGroupHistory getSaUserGroupHistory(String id) {
		// TODO Auto-generated method stub
		try {
			return sausergrouphistoryDAO.find(id);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@Transactional
	public SaUserGroupHistory getSaUserGroupHistory(String id, Integer seq_id) {
		// TODO Auto-generated method stub
		try {
			return sausergrouphistoryDAO.find(id, seq_id);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Transactional
	public List<SaUserGroupHistory> getSaUserGroupHistoryList() {
		// TODO Auto-generated method stub
		return sausergrouphistoryDAO.findAll(SaUserGroupHistory.class);
	}
	
	@Transactional
	public List<SaUserGroupHistory> getPending_SaUserGroupHistoryList() {
		// TODO Auto-generated method stub
		return sausergrouphistoryDAO.findAllPending(SaUserGroupHistory.class);
	}

	@Override
	public List<SaUserGroupHistory> getSaUserGroupHistoryListById(String group_id) {
		// TODO Auto-generated method stub
		return sausergrouphistoryDAO.findAllbyId(group_id);
	}

	@Override
	public List<SaUserGroupHistory> getCustom_SaUserGroupHistoryList(String group_id,
			String action, String status, String startdate_, String enddate_) {
				
		return sausergrouphistoryDAO.findAllby_CustomParams(group_id, action, status, startdate_, enddate_);
	}	
	

	@Override
	public List<SaUserGroupHistory> getDistinct_SaUserGroupHistoryList() {
		// TODO Auto-generated method stub
		return sausergrouphistoryDAO.getDistinct_SaUserGroupHistoryList();
	}
}
