package com.isg.ifrend.model.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.isg.ifrend.model.bean.Code;

/**
 * 
 * @author kristine.furto
 * 
 */
public class CodeDAO extends HibernateDaoSupport {

	public Code findById(Class<Code> codeClass, int codeID)
			throws DataAccessException {
		Code code = (Code) getHibernateTemplate().load(codeClass, codeID);
		return code;
	}

	@SuppressWarnings("unchecked")
	public List<Code> findByCodeType(Class<Code> codeClass, int codeTypeID)
			throws DataAccessException {
		List<Code> list = getHibernateTemplate().find(
				"from " + codeClass.getName());
		return list;
	}

}
