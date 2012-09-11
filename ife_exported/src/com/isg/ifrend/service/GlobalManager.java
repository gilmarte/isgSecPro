package com.isg.ifrend.service;

import java.util.List;

import com.isg.ifrend.model.bean.Global;

public interface GlobalManager<M extends Global, T extends Global> {

	public List<M> viewActiveList();

	public List<T> viewPendingList();

	public List<M> searchActiveList(M m);

	public List<T> searchPendingList(T t);

	public M viewActiveDetails(String id);

	public T viewPendingDetails(String id);

	public String requestAdd(T t);

	public void requestUpdate(T t);

	public void requestDelete(T t);

	public void updateRequest(T t);

	public void cancelRequest(T t);

	public void approveRequest(T t);

	public void rejectRequest(T t);

	public void setCurrentUser(String currentUser);
}