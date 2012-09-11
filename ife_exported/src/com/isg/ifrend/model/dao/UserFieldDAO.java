package com.isg.ifrend.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.isg.ifrend.model.bean.UserField;

public class UserFieldDAO extends HibernateDaoSupport {
	
	public void saveOrUpdate(UserField userField) throws DataAccessException {
		getHibernateTemplate().saveOrUpdate(userField);
	}

	public void delete(UserField userField) throws DataAccessException {
		getHibernateTemplate().delete(userField);
	}

	public UserField find(Class<UserField> userFieldClass, int userFieldID) throws DataAccessException {
		UserField userField = (UserField) getHibernateTemplate().load(userFieldClass, userFieldID);
		return userField;
	}
	
	@SuppressWarnings("unchecked")
	public UserField findByDescription(String description)throws DataAccessException{
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		String sql = "select * from ref_userfields where uf_userfield_id_pk = ?";
		
		Query query = session.createSQLQuery(sql).addEntity(UserField.class);
		query.setString(0, description);
		
		List<UserField>lst_userfield = new ArrayList<UserField>();
		lst_userfield = query.list();
		
		return lst_userfield.get(0);
		
	}	
	@SuppressWarnings("unchecked")
	public List<UserField> findAll(Class<UserField> userFieldClass) throws DataAccessException {
		List<UserField> list = getHibernateTemplate().find("from " + userFieldClass.getName());
		return list;
	}

}
