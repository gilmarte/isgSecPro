package com.isg.ifrend.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.isg.ifrend.model.bean.TmpSaUserGroup;
import com.isg.ifrend.model.dao.TmpSaUserGroupDAO;

public class TmpSaUserGroupManagerImpl implements TmpSaUserGroupManager {

	TmpSaUserGroupDAO tmpsausergroupDAO;
	
		
	public TmpSaUserGroupDAO getTmpsausergroupDAO() {
		return tmpsausergroupDAO;
	}

	public void setTmpsausergroupDAO(TmpSaUserGroupDAO tmpsausergroupDAO) {
		this.tmpsausergroupDAO = tmpsausergroupDAO;
	}

	@Transactional
	public boolean save(TmpSaUserGroup tmpsausergroup) {
		// TODO Auto-generated method stub
		try {
			tmpsausergroupDAO.saveOrUpdate(tmpsausergroup);			
			return true;
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}

	@Transactional
	public boolean delete(TmpSaUserGroup tmpsausergroup) {
		// TODO Auto-generated method stub
		try {
			tmpsausergroupDAO.delete(tmpsausergroup);
			return true;
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public TmpSaUserGroup getTmpSaUserGroup(String id) {
		// TODO Auto-generated method stub
		try {
			return tmpsausergroupDAO.find(id);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Transactional
	public List<TmpSaUserGroup> getTmpSaUserGroupList() {
		// TODO Auto-generated method stub
		return tmpsausergroupDAO.findAll(TmpSaUserGroup.class);
	}
	
	@Transactional
	public List<TmpSaUserGroup> getPending_TmpSaUserGroupList() {
		// TODO Auto-generated method stub
		return tmpsausergroupDAO.findAllPending(TmpSaUserGroup.class);
	}

	@Override
	public List<TmpSaUserGroup> getTmpSaUserGroupListById(String group_id) {
		// TODO Auto-generated method stub
		return tmpsausergroupDAO.findAllbyId(group_id);
	}

	@Override
	public List<TmpSaUserGroup> getCustom_TmpSaUserGroupList(String group_id,
			String action, String status) {
				
		return tmpsausergroupDAO.findAllby_CustomParams(group_id, action, status);
	}

	@Override
	public List<TmpSaUserGroup> getAuth_Search_Custom_TmpSaUserGroupList(
			String group_id, String action, String status) {
		// TODO Auto-generated method stub
		return tmpsausergroupDAO.Auth_Search_findAllby_CustomParams(group_id, action, status);
	}


}
