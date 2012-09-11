package com.isg.ifrend.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.isg.ifrend.model.bean.SaUserGroup;
import com.isg.ifrend.model.dao.SaUserGroupDAO;

public class SaUserGroupManagerImpl implements SaUserGroupManager {

	SaUserGroupDAO sausergroupDAO;
	
	
	public SaUserGroupDAO getSausergroupDAO() {
		return sausergroupDAO;
	}

	public void setSausergroupDAO(SaUserGroupDAO sausergroupDAO) {
		this.sausergroupDAO = sausergroupDAO;
	}

	@Transactional
	public boolean save(SaUserGroup sausergroup) {
		// TODO Auto-generated method stub
		try {
			sausergroupDAO.saveOrUpdate(sausergroup);			
			return true;
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}

	@Transactional
	public boolean delete(SaUserGroup sausergroup) {
		// TODO Auto-generated method stub
		try {
			sausergroupDAO.delete(sausergroup);
			return true;
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public SaUserGroup getSaUserGroup(String id) {
		// TODO Auto-generated method stub
		try {
			return sausergroupDAO.find(id);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Transactional
	public List<SaUserGroup> getSaUserGroupList() {
		// TODO Auto-generated method stub
		return sausergroupDAO.findAll(SaUserGroup.class);
	}

	@Override
	public List<SaUserGroup> getSaUserGroupListById(String group_id) {
		// TODO Auto-generated method stub
		return sausergroupDAO.findAllbyId(group_id);
	}

	@Override
	public List<SaUserGroup> find_all_Active_Pending(String group_id) {
		// TODO Auto-generated method stub
		return sausergroupDAO.find_all_Active_Pending(group_id);
	}

}
