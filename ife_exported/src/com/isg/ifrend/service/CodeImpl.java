package com.isg.ifrend.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.isg.ifrend.model.bean.Code;
import com.isg.ifrend.model.dao.CodeDAO;

/**
 * 
 * @author kristine.furto
 *
 */
public class CodeImpl implements CodeManager {

	private CodeDAO codeDAO;

	public void setCodeDAO(CodeDAO codeDAO) {
		this.codeDAO = codeDAO;
	}

	public CodeDAO getCodeDAO() {
		return codeDAO;
	}
	
	@Transactional(readOnly = true)
	public Code findById(int codeID) {
		try {
			return codeDAO.findById(Code.class, codeID);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Transactional(readOnly = true)
	public List<Code> findByCodeType(int codeTypeID) {
		try {
			return codeDAO.findByCodeType(Code.class, codeTypeID);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	

	
}
