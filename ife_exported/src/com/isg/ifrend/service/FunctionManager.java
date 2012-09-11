package com.isg.ifrend.service;

import java.util.List;

import com.isg.ifrend.model.bean.Function;

public interface FunctionManager {

	public boolean save(Function function);
	public boolean delete(Function function);
	public Function findById(String function_code);
	public List<Function> getFunctionList();
}
