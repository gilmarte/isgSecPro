package com.isg.ifrend.service;

import java.util.List;

import com.isg.ifrend.model.bean.SaSelectedFunctions;

public interface SaSelectedFunctionsManager {
	public boolean save(SaSelectedFunctions saselectedfunctions);
    public boolean delete(SaSelectedFunctions saselectedfunctions);
    public SaSelectedFunctions getSaSelectedFunctions(Integer id);
    public List<SaSelectedFunctions> getSaSelectedFunctionsList();
}
