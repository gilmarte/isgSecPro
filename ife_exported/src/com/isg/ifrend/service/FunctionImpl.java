package com.isg.ifrend.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.isg.ifrend.model.bean.Function;
import com.isg.ifrend.model.dao.FunctionDAO;

public class FunctionImpl implements FunctionManager {

	private FunctionDAO functionDAO;

	public FunctionDAO getFunctionDAO() {
		return functionDAO;
	}

	public void setFunctionDAO(FunctionDAO functionDAO) {
		this.functionDAO = functionDAO;
	}

	@Transactional
	public boolean save(Function function) {
		try {
			functionDAO.saveOrUpdate(function);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean delete(Function function) {
		try {
			functionDAO.delete(function);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional(readOnly = true)
	public Function findById(String function_code) {
		try {
			return functionDAO.find(Function.class, function_code);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Transactional(readOnly = true)
	public List<Function> getFunctionList() {
		try {
			return functionDAO.findAll(Function.class);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

}
