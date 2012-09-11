package com.isg.ifrend.model.dao;

import java.util.List;

import com.isg.ifrend.model.bean.Global;

public interface GlobalDAO<M extends Global, T extends Global> {

	public List<M> findAllMst();

	public List<T> findAllTmp();

	public List<M> findAllMst(M m);

	public List<T> findAllTmp(T t);

	public M findMst(String id);

	public T findTmp(String id);

	public void saveMst(T t);

	public String saveTmp(T t);

	public void saveHst(T t);

	public void updateMst(T t);

	public void updateMstStatusAndAction(T t);

	public void updateTmp(T t);

	public void deleteMst(T t);

	public void deleteTmp(T t);

	public boolean isNotOnMst(T t);
}