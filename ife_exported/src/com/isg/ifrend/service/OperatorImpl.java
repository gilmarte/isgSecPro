package com.isg.ifrend.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.isg.ifrend.model.bean.Operator;
import com.isg.ifrend.model.dao.OperatorDAO;

public class OperatorImpl implements OperatorManager {

	private OperatorDAO operatorDAO;

	public OperatorDAO getOperatorDAO() {
		return operatorDAO;
	}

	public void setOperatorDAO(OperatorDAO operatorDAO) {
		this.operatorDAO = operatorDAO;
	}

	@Transactional
	public boolean save(Operator operator) {
		try {
			operatorDAO.saveOrUpdate(operator);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean delete(Operator operator) {
		try {
			operatorDAO.delete(operator);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional(readOnly = true)
	public Operator findById(String operator_code) {
		try {
			return operatorDAO.find(Operator.class, operator_code);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Transactional(readOnly = true)
	public List<Operator> getOperatorList() {
		try {
			return operatorDAO.findAll(Operator.class);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

}
