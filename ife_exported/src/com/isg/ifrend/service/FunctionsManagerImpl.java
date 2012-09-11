package com.isg.ifrend.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.isg.ifrend.model.bean.Functions;
import com.isg.ifrend.model.dao.FunctionsDAO;

public class FunctionsManagerImpl implements FunctionsManager {

	FunctionsDAO functionsDAO;
	
	
	public FunctionsDAO getFunctionsDAO() {
		return functionsDAO;
	}

	public void setFunctionsDAO(FunctionsDAO functionsDAO) {
		this.functionsDAO = functionsDAO;
	}

	@Override
	public boolean save(Functions functions) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Functions functions) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Functions getFunctions(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Functions> getFunctionsList() {
		// TODO Auto-generated method stub
		try {
			return functionsDAO.findAll(Functions.class);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
