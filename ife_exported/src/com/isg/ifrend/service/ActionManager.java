package com.isg.ifrend.service;

import java.util.List;

import com.isg.ifrend.model.bean.Action;

public interface ActionManager {

	public boolean save(Action action);
	public boolean delete(Action action);
	public Action findById(int action_id);
	public Action findByDescription(String description);
	public List<Action> getActionList();
}
