package com.isg.ifrend.service;

import java.util.List;

import com.isg.ifrend.model.bean.GlobalMaxID;

public interface GlobalMaxIDManager {
	
	public boolean save(GlobalMaxID globalMaxID);
	public boolean delete(GlobalMaxID globalMaxID);
	public List<GlobalMaxID> getGlobalMaxIDList();
}
