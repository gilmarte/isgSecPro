package com.isg.ifrend.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.isg.ifrend.model.bean.FunctionMaxID;
import com.isg.ifrend.model.dao.FunctionMaxIDDAO;

public class FunctionMaxIDImpl implements FunctionMaxIDManager{

	private FunctionMaxIDDAO functionMaxIDDAO;

	public FunctionMaxIDDAO getFunctionMaxIDDAO() {
		return functionMaxIDDAO;
	}

	public void setFunctionMaxIDDAO(FunctionMaxIDDAO functionMaxIDDAO) {
		this.functionMaxIDDAO = functionMaxIDDAO;
	}

	@Override
	public boolean save(FunctionMaxID functionMaxID) {
		try {
			functionMaxIDDAO.saveOrUpdate(functionMaxID);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(FunctionMaxID functionMaxID) {
		try {
			functionMaxIDDAO.delete(functionMaxID);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public List<FunctionMaxID> getFunctionMaxIDList() {
		try {
			return functionMaxIDDAO.findAll(FunctionMaxID.class);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
}
