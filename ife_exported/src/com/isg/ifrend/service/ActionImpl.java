package com.isg.ifrend.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.isg.ifrend.model.bean.Action;
import com.isg.ifrend.model.dao.ActionDAO;

public class ActionImpl implements ActionManager {

	private ActionDAO actionDAO;
	
	public ActionDAO getActionDAO() {
		return actionDAO;
	}

	public void setActionDAO(ActionDAO actionDAO) {
		this.actionDAO = actionDAO;
	}

	@Transactional
	public boolean save(Action action) {
		try {
			actionDAO.saveOrUpdate(action);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean delete(Action action) {
		try {
			actionDAO.delete(action);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional(readOnly = true)
	public Action findById(int action_id) {
		try {
			return actionDAO.find(Action.class, action_id);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Transactional(readOnly = true)
	public List<Action> getActionList() {
		try {
			return actionDAO.findAll(Action.class);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Action findByDescription(String description) {
		// TODO Auto-generated method stub
		return actionDAO.findByDescription(description);

	}

}
