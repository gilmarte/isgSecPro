package com.isg.ifrend.service;

import java.util.List;

import com.isg.ifrend.model.bean.Priority;

public interface PriorityManager {

	public boolean save(Priority priority);
	public boolean delete(Priority priority);
	public Priority findById(int priorityID);
	public Priority findByDescription(String description);
	public List<Priority> getPriorityList();
}
