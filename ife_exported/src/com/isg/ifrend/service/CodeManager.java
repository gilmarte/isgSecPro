package com.isg.ifrend.service;

import java.util.List;

import com.isg.ifrend.model.bean.Code;

/**
 * 
 * @author kristine.furto
 *
 */
public interface CodeManager {
	
	public Code findById(int codeID);
	
	public List<Code> findByCodeType(int codeTypeID);
}
