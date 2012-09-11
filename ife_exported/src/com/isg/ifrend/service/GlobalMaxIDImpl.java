package com.isg.ifrend.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.isg.ifrend.model.bean.GlobalMaxID;
import com.isg.ifrend.model.dao.GlobalMaxIDDAO;

public class GlobalMaxIDImpl implements GlobalMaxIDManager {

	private GlobalMaxIDDAO globalMaxIDDAO;

	public GlobalMaxIDDAO getGlobalMaxIDDAO() {
		return globalMaxIDDAO;
	}

	public void setGlobalMaxIDDAO(GlobalMaxIDDAO globalMaxIDDAO) {
		this.globalMaxIDDAO = globalMaxIDDAO;
	}

	@Override
	public boolean save(GlobalMaxID elementType) {
		try {
			globalMaxIDDAO.saveOrUpdate(elementType);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(GlobalMaxID elementType) {
		try {
			globalMaxIDDAO.delete(elementType);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<GlobalMaxID> getGlobalMaxIDList() {
		try {
			return globalMaxIDDAO.findAll(GlobalMaxID.class);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
}
