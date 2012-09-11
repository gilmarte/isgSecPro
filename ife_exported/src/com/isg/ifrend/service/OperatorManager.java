package com.isg.ifrend.service;

import java.util.List;

import com.isg.ifrend.model.bean.Operator;

public interface OperatorManager {

	public boolean save(Operator operator);
	public boolean delete(Operator operator);
	public Operator findById(String operator_code);
	public List<Operator> getOperatorList();
}
