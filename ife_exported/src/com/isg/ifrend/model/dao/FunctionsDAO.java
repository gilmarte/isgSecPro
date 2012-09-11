package com.isg.ifrend.model.dao;



import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.isg.ifrend.model.bean.Functions;

public class FunctionsDAO extends HibernateDaoSupport {
	
	public void saveOrUpdate(Functions functions) throws DataAccessException {
        getHibernateTemplate().saveOrUpdate(functions);
    }
 
    public void delete(Functions functions) throws DataAccessException {
        getHibernateTemplate().delete(functions);
    }
 
    public Functions find(Class<Functions> clazz, Integer id) throws DataAccessException {
    	Functions functions = (Functions) getHibernateTemplate().load(clazz, id);
        return functions;
    }
     
    @SuppressWarnings("unchecked")
	public List<Functions> findAll(Class<Functions> clazz) throws DataAccessException {
        List<Functions> list = getHibernateTemplate().find("from " + clazz.getName());
        return list;
    }
}
