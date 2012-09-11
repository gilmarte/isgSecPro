package com.isg.ifrend.model.dao;

import java.util.Date;
import java.util.List;

import com.isg.ifrend.model.bean.Global;

public interface GenericDAO {

	public <T extends Global> List<T> findAll(T t, String idPropertyName);

	public <T extends Global> List<T> findAll(T t, String idPropertyName,
			String descPropertyName);

	public <T extends Global> List<T> findAll(T t, String idPropertyName,
			String descPropertyName, Date startDate, Date endDate);

	public <T> T find(Class<T> clazz, String id);

	public <T> String save(T t);

	public <T> void update(T t);

	public <T> void delete(T t);
}
