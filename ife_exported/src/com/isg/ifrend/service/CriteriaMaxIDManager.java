package com.isg.ifrend.service;

import com.isg.ifrend.model.bean.CriteriaMaxID;

public interface CriteriaMaxIDManager {

	public boolean save(CriteriaMaxID criteriaMaxID);
	public boolean delete(CriteriaMaxID criteriaMaxID);
	public int getCriteriaMaxID();
	
}
