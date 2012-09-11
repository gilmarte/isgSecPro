package com.isg.ifrend.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.isg.ifrend.model.bean.Action;

public class ActionDAO extends HibernateDaoSupport {
	
	public void saveOrUpdate(Action action) throws DataAccessException {
		getHibernateTemplate().saveOrUpdate(action);
	}

	public void delete(Action action) throws DataAccessException {
		getHibernateTemplate().delete(action);
	}

	public Action find(Class<Action> actionClass, int action_id) throws DataAccessException {
		Action action = (Action) getHibernateTemplate().load(actionClass, action_id);
		return action;
	}
	
@SuppressWarnings("unchecked")
public Action findByDescription(String description) throws DataAccessException{
		
	
		/*Session session = (Session) getHibernateTemplate().getSessionFactory().getCurrentSession();
		String sql = "select * from ref_actions where ac_action_desc = ?";
		
		Query query = ((org.hibernate.Session) session).createSQLQuery(sql).addEntity(Action.class);
		query.setString(0, description);
		
		List<Action>lst_action = new ArrayList<Action>();
		lst_action = query.list();
		return lst_action.get(0); */
		DetachedCriteria criteria = DetachedCriteria.forClass(Action.class);
		criteria.add(Restrictions.eq("action_desc", description));
		List<Action> lst_action = new ArrayList<Action>();
		lst_action = getHibernateTemplate().findByCriteria(criteria);
		return lst_action.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public List<Action> findAll(Class<Action> actionClass) throws DataAccessException {
		List<Action> list = getHibernateTemplate().find("from " + actionClass.getName());
		return list;
	}

}
