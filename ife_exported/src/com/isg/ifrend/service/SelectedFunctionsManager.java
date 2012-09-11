package com.isg.ifrend.service;

import java.util.List;

import com.isg.ifrend.model.bean.SelectedFunctions;

public interface SelectedFunctionsManager {

	public boolean save(SelectedFunctions selectedfunctions);
    public boolean delete(SelectedFunctions selectedfunctions);
    public SelectedFunctions getSelectedFunctions(Integer id);
    public List<SelectedFunctions> getSelectedFunctionsList();
}
