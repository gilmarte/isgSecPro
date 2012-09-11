package com.isg.ifrend.service;

import java.util.List;

import com.isg.ifrend.model.bean.FunctionMaxID;

public interface FunctionMaxIDManager {

	public boolean save(FunctionMaxID functionMaxID);
	public boolean delete(FunctionMaxID functionMaxID);
	public List<FunctionMaxID> getFunctionMaxIDList();
}
