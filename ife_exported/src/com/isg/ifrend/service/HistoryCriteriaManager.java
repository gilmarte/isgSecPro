package com.isg.ifrend.service;

import java.util.List;
import com.isg.ifrend.model.bean.HistCriteria;

public interface HistoryCriteriaManager {
	
	public List<HistCriteria> findAllHist();
	public HistCriteria findByID(Integer histCriterion_id);
	
}
