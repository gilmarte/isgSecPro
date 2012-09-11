package com.isg.ifrend.service;

import java.util.List;

import com.isg.ifrend.model.bean.Functions;

public interface FunctionsManager {
	public boolean save(Functions functions);
    public boolean delete(Functions functions);
    public Functions getFunctions(Integer id);
    public List<Functions> getFunctionsList();
}
