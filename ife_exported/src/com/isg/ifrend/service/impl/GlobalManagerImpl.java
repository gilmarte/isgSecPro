package com.isg.ifrend.service.impl;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import com.isg.ifrend.model.bean.ActionType;
import com.isg.ifrend.model.bean.Global;
import com.isg.ifrend.model.bean.StatusType;
import com.isg.ifrend.model.dao.GlobalDAO;
import com.isg.ifrend.service.GlobalManager;
import com.isg.ifrend.utils.Commons;
import com.isg.ifrend.utils.DateUtil;

public class GlobalManagerImpl<M extends Global, T extends Global> implements
		GlobalManager<M, T> {

	private GlobalDAO<M, T> globalDAO;
	private String currentUser;

	@Override
	public List<M> viewActiveList() {
		return globalDAO.findAllMst();
	}

	@Override
	public List<T> viewPendingList() {
		return globalDAO.findAllTmp();
	}

	@Override
	public List<M> searchActiveList(M m) {
		return globalDAO.findAllMst(m);
	}

	@Override
	public List<T> searchPendingList(T t) {
		return globalDAO.findAllTmp(t);
	}

	@Override
	public M viewActiveDetails(String id) {
		return globalDAO.findMst(id);
	}

	@Override
	public T viewPendingDetails(String id) {
		return globalDAO.findTmp(id);
	}

	@Override
	@Transactional
	public String requestAdd(T t) {
		if (globalDAO.isNotOnMst(t)) {
			setLastModifier(t);
			t.setCreatorUserID(t.getLastModifierUserID());
			t.setDateCreated(t.getDateLastModified());
			t.setStatusID(StatusType.PENDING.getId());
			t.setActionID(ActionType.ADD.getId());

			String id = globalDAO.saveTmp(t);
			globalDAO.saveHst(t);

			return id;
		} else {
			throw new DataIntegrityViolationException(Commons.RECORD_EXIST);
		}
	}

	@Override
	@Transactional
	public void requestUpdate(T t) {
		t.setStatusID(StatusType.PENDING.getId());
		t.setActionID(ActionType.UPDATE.getId());
		setLastModifier(t);
		removeApprover(t);

		globalDAO.updateMstStatusAndAction(t);
		globalDAO.saveTmp(t);
		globalDAO.saveHst(t);
	}

	@Override
	@Transactional
	public void requestDelete(T t) {
		setLastModifier(t);
		removeApprover(t);
		t.setStatusID(StatusType.PENDING.getId());
		t.setActionID(ActionType.DELETE.getId());

		globalDAO.updateMstStatusAndAction(t);
		globalDAO.saveTmp(t);
		globalDAO.saveHst(t);
	}

	@Override
	@Transactional
	public void updateRequest(T t) {
		setLastModifier(t);

		globalDAO.updateTmp(t);
		globalDAO.saveHst(t);
	}

	@Override
	@Transactional
	public void cancelRequest(T t) {
		setLastModifier(t);
		deleteRequest(t, StatusType.CANCELLED.getId());
	}

	@Override
	@Transactional
	public void approveRequest(T t) {
		setApprover(t);

		globalDAO.deleteTmp(t);

		t.setStatusID(StatusType.APPROVED.getId());
		globalDAO.saveHst(t);

		if (ActionType.ADD.getId() == t.getActionID()) {
			t.setActionID(ActionType.ALL.getId());
			t.setStatusID(StatusType.ACTIVE.getId());
			globalDAO.saveMst(t);

		} else if (ActionType.UPDATE.getId() == t.getActionID()) {
			t.setActionID(ActionType.ALL.getId());
			t.setStatusID(StatusType.ACTIVE.getId());
			globalDAO.updateMst(t);

		} else if (ActionType.DELETE.getId() == t.getActionID()) {
			t.setActionID(ActionType.ALL.getId());
			t.setStatusID(StatusType.DELETED.getId());
			globalDAO.updateMstStatusAndAction(t);
		}
	}

	@Override
	@Transactional
	public void rejectRequest(T t) {
		setApprover(t);
		deleteRequest(t, StatusType.REJECTED.getId());
	}

	private void deleteRequest(T t, int hstStatus) {
		globalDAO.deleteTmp(t);

		int actionId = t.getActionID();
		t.setStatusID(hstStatus);
		globalDAO.saveHst(t);

		if (ActionType.ADD.getId() != t.getActionID()) {
			t.setActionID(ActionType.ALL.getId());
			t.setStatusID(StatusType.ACTIVE.getId());
			globalDAO.updateMstStatusAndAction(t);
		}

		t.setActionID(actionId);
		t.setStatusID(hstStatus);
	}

	private void setLastModifier(T t) {
		t.setLastModifierUserID(currentUser);
		t.setDateLastModified(DateUtil.getCurrentDate());
	}

	private void setApprover(T t) {
		t.setApproverUserID(currentUser);
		t.setDateApproved(DateUtil.getCurrentDate());
	}

	private void removeApprover(T t) {
		t.setApproverUserID(null);
		t.setDateApproved(null);
	}

	public void setGlobalDAO(GlobalDAO<M, T> globalDAO) {
		this.globalDAO = globalDAO;
	}

	// XXX Cannot call securityUtil
	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}
}