package com.isg.ifrend.view.control;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

import com.isg.ifrend.model.bean.ActionType;
import com.isg.ifrend.model.bean.Global;
import com.isg.ifrend.model.bean.StatusType;
import com.isg.ifrend.model.bean.TmpMli;
import com.isg.ifrend.model.bean.TmpScript;
import com.isg.ifrend.security.utils.SecurityUtils;
import com.isg.ifrend.service.GlobalManager;
import com.isg.ifrend.service.ServiceLocator;
import com.isg.ifrend.service.impl.GenericManagerImpl;
import com.isg.ifrend.utils.Commons;
import com.isg.ifrend.utils.DateUtil;
import com.isg.ifrend.utils.GlobalUtils;

public abstract class GlobalViewComposer<M extends Global, T extends Global, H extends Global>
		extends GenericForwardComposer {

	private static final long serialVersionUID = 820251870339249072L;

	private static final String TITLE_DELIMITER = " - ";
	private static final String TITLE_SUFFIX = " Details";

	private GlobalManager<M, T> globalManager;
	private GenericManagerImpl genericManager;
	private GlobalUtils globalUtils = GlobalUtils.getGlobalUtilsInstance();
	private SecurityUtils securityUtils = SecurityUtils.getSecurityUtilsInstance();

	private Window globalWin;
	private Label messageLbl;
	private Image messageImg;

	private Label idLbl;
	private Label statusLbl;
	private Label actionLbl;

	private Label creatorLbl;
	private Label dateCreatedLbl;

	private Row modifierRow;
	private Label modifierLbl;
	private Label dateModifiedLbl;

	private Row approverRow;
	private Label approverLbl;
	private Label dateApprovedLbl;

	private Button updateTopBtn;
	protected Button deleteTopBtn;
	private Button approveTopBtn;
	private Button rejectTopBtn;
	private Button editRequestTopBtn;
	private Button cancelRequestTopBtn;

	protected T tmpGlobal;

	protected abstract GlobalManager<M, T> getManager();

	protected abstract T toTmp(M m);

	protected abstract T toTmpFromHst(H h);

	protected abstract void setViewValue();

	protected abstract String getViewUrl();

	protected abstract String getAddUrl();

	protected abstract String getUpdateUrl();

	protected abstract Class<H> getHstGlobalClass();

	public GlobalViewComposer() {
		globalManager = getManager();
		globalManager.setCurrentUser(securityUtils.getUserName());
		genericManager = ServiceLocator.getGenericManager();
	}

	private boolean isFromAdd;
	private boolean isFromAuthorize;
	private boolean isFromHistory;

	@SuppressWarnings("unchecked")
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		String msg = (String) session.getAttribute(Commons.SESSION_MESSAGE);
		session.removeAttribute(Commons.SESSION_MESSAGE);
		if(StringUtils.isNotEmpty(msg)){
			messageLbl.setValue(msg);
			setMsgStyle(false);
		}

		String url = session.getAttribute(Commons.SESSION_URL).toString();
		isFromAdd = Commons.MODULE_ADD.equals(url);
		isFromAuthorize = Commons.MODULE_AUTHORIZE.equals(url);
		isFromHistory = Commons.MODULE_HISTORY.equals(url);

		tmpGlobal = (T) session.getAttribute(Commons.SESSION_GLOBAL);
		session.removeAttribute(Commons.SESSION_GLOBAL);

		if (null == tmpGlobal) {
			if (isFromAuthorize) {
				tmpGlobal = globalManager.viewPendingDetails(getId(Commons.URL_PARAM_TMP_ID));
				setTitle(Commons.MODULE_AUTHORIZE);

			} else if (isFromHistory) {
				tmpGlobal = toTmpFromHst(genericManager.viewDetails(getHstGlobalClass(), getId(Commons.URL_PARAM_HST_ID)));
				setTitle(Commons.MODULE_HISTORY);

			} else {
				tmpGlobal = toTmp(globalManager.viewActiveDetails(getId(Commons.URL_PARAM_MST_ID)));
				setTitle(Commons.MODULE_SEARCH);
			}
		}

		setPage();
	}

	protected void setMsgStyle(boolean isError) {
		if (StringUtils.isNotEmpty(messageLbl.getValue())) {
			messageImg.setVisible(true);
			if (!isError) {
				messageLbl.setSclass(getGlobalPropVal(Commons.STYLE_MSG_INFO));
				messageImg.setSrc(getGlobalPropVal(Commons.IMG_SRC_MSG_INFO));
			} else {
				messageLbl.setSclass(getGlobalPropVal(Commons.STYLE_MSG_ERR));
				messageImg.setSrc(getGlobalPropVal(Commons.IMG_SRC_MSG_ERR));
			}
		} else {
			messageImg.setVisible(false);
		}
	}

	private String getId(String key) {
		return Executions.getCurrent().getParameter(key);
	}

	private void setTitle(String title) {
		globalWin.setTitle(new StringBuilder(tmpGlobal.getEntityDesc())
				.append(TITLE_DELIMITER).append(title).append(TITLE_SUFFIX)
				.toString());
	}

	private void setPage() {
		setCommonDetails();
		setComponentVisibility();
		setViewValue();
	}

	private void setCommonDetails() {
		idLbl.setValue(tmpGlobal.getId());

		statusLbl.setValue(StatusType.getDesc(tmpGlobal.getStatusID()));

		if (0 == tmpGlobal.getActionID()) {
			actionLbl.setValue(getMsgPropVal(Commons.ACTION_NONE));
		} else {
			actionLbl.setValue(ActionType.getDesc(tmpGlobal.getActionID()));
		}

		creatorLbl.setValue(tmpGlobal.getCreatorUserID());
		dateCreatedLbl.setValue(DateUtil.format(tmpGlobal.getDateCreated()));

		modifierLbl.setValue(tmpGlobal.getLastModifierUserID());
		dateModifiedLbl.setValue(DateUtil.format(tmpGlobal.getDateLastModified()));

		if (StatusType.isStatusAuthorized(tmpGlobal.getStatusID())) {
			approverLbl.setValue(tmpGlobal.getApproverUserID());
			dateApprovedLbl.setValue(DateUtil.format(tmpGlobal.getDateApproved()));
		}
	}

	private void setComponentVisibility() {
		boolean isPending = StatusType.PENDING.getId() == tmpGlobal.getStatusID();

		modifierRow.setVisible(!isPending || (ActionType.ADD.getId() != tmpGlobal.getActionID()));
		approverRow.setVisible(StatusType.isStatusAuthorized(tmpGlobal.getStatusID()));

		boolean isMaker = securityUtils.hasMakerRole(getGlobalPropVal(getViewUrl()));

		boolean isUpdatable = isMaker && (StatusType.ACTIVE.getId() == tmpGlobal.getStatusID());
		updateTopBtn.setVisible(isUpdatable);
		deleteTopBtn.setVisible(isUpdatable);

		boolean isSameUser = tmpGlobal.getLastModifierUserID().equals(securityUtils.getUserName());

		boolean isToAuthorize = isPending && isFromAuthorize && !isSameUser && securityUtils.hasCheckerRole(getGlobalPropVal(getViewUrl()));
		approveTopBtn.setVisible(isToAuthorize);
		rejectTopBtn.setVisible(isToAuthorize);

		boolean isSameMaker = isPending && isMaker && isSameUser && (isFromAdd || isFromAuthorize);
		cancelRequestTopBtn.setVisible(isSameMaker);
		editRequestTopBtn.setVisible(isSameMaker && (ActionType.DELETE.getId() != tmpGlobal.getActionID()));
	}

	public void onClick$updateTopBtn() {
		session.setAttribute(Commons.SESSION_GLOBAL, tmpGlobal);
		redirect(getUpdateUrl());
	}

	public void onClick$deleteTopBtn() throws InterruptedException {
		showMessageAskDelete();
	}

	private void showMessageAskDelete() throws InterruptedException {
		Messagebox.show(getMsgPropVal(Commons.MSG_ASK_DELETE),
				getMsgPropVal(Commons.MSG_TITLE_DELETE), Messagebox.OK
						| Messagebox.CANCEL, Messagebox.EXCLAMATION,
				new EventListener() {
					public void onEvent(Event evt) {
						if (Messagebox.ON_OK.equals(evt.getName())) {
							requestDelete();
						}
					}
				});
	}

	private void requestDelete() {
		globalManager.requestDelete(tmpGlobal);
		messageLbl.setValue(getMsgPropVal(Commons.MSG_INFO_DELETE));
		setMsgStyle(false);
		setPage();
	}

	public void onClick$editRequestTopBtn() {
		session.setAttribute(Commons.SESSION_GLOBAL, tmpGlobal);
		redirect(getUpdateUrl());
	}

	public void onClick$cancelRequestTopBtn() throws InterruptedException {
		showMessageAskCancel();
	}

	private void showMessageAskCancel() throws InterruptedException {
		Messagebox.show(getMsgPropVal(Commons.MSG_ASK_CANCEL),
				getMsgPropVal(Commons.MSG_TITLE_CANCEL), Messagebox.OK
						| Messagebox.CANCEL, Messagebox.EXCLAMATION,
				new EventListener() {
					public void onEvent(Event evt) {
						if (Messagebox.ON_OK.equals(evt.getName())) {
							cancelRequest();
						}
					}
				});
	}

	private void cancelRequest() {
		globalManager.cancelRequest(tmpGlobal);
		messageLbl.setValue(getMsgPropVal(Commons.MSG_INFO_CANCEL));
		setMsgStyle(false);
		setPage();
	}

	public void onClick$approveTopBtn() throws InterruptedException {
		showMessageAskApprove();
	}

	private void showMessageAskApprove() throws InterruptedException {
		Messagebox.show(getMsgPropVal(Commons.MSG_ASK_APPROVE),
				getMsgPropVal(Commons.MSG_TITLE_APPROVE), Messagebox.OK
						| Messagebox.CANCEL, Messagebox.EXCLAMATION,
				new EventListener() {
					public void onEvent(Event evt) throws InterruptedException {
						if (Messagebox.ON_OK.equals(evt.getName())) {
							approveRequest();
						}
					}
				});
	}

	private void approveRequest() {
		try {
			globalManager.approveRequest(tmpGlobal);
			messageLbl.setValue(getMsgPropVal(Commons.MSG_INFO_APPROVE));
			setMsgStyle(false);
			setPage();
		} catch (DataIntegrityViolationException e) {
			if (tmpGlobal instanceof TmpMli) {
				messageLbl.setValue(getMsgPropVal(Commons.MSG_ERR_MLI_ASSOC));
			} else if (tmpGlobal instanceof TmpScript) {
				messageLbl.setValue(getMsgPropVal(Commons.MSG_ERR_SCRIPT_ASSOC));
			} else {
				messageLbl.setValue(getMsgPropVal(Commons.RECORD_DUPLICATE));
			}
			setMsgStyle(true);
		}
	}

	public void onClick$rejectTopBtn() throws InterruptedException {
		showMessageAskReject();
	}

	private void showMessageAskReject() throws InterruptedException {
		Messagebox.show(getMsgPropVal(Commons.MSG_ASK_REJECT),
				getMsgPropVal(Commons.MSG_TITLE_REJECT), Messagebox.OK
						| Messagebox.CANCEL, Messagebox.EXCLAMATION,
				new EventListener() {
					public void onEvent(Event evt) throws InterruptedException {
						if (Messagebox.ON_OK.equals(evt.getName())) {
							rejectRequest();
						}
					}
				});
	}

	private void rejectRequest() {
		globalManager.rejectRequest(tmpGlobal);
		messageLbl.setValue(getMsgPropVal(Commons.MSG_INFO_REJECT));
		setMsgStyle(false);
		setPage();
	}

	public void onClick$okTopBtn() {
		if (isFromAuthorize) {
			redirect(Commons.URL_AUTHORIZE_GLOBAL);

		} else if (isFromAdd) {
			redirect(getAddUrl());

		} else if (isFromHistory) {
			redirect(Commons.URL_HISTORY_GLOBAL);

		} else {
			redirect(Commons.URL_SEARCH_GLOBAL);
		}
	}

	private void redirect(String url) {
		Executions.sendRedirect(getGlobalPropVal(url));
	}

	protected String getGlobalPropVal(String key) {
		return globalUtils.getGlobalPropValue(key);
	}

	protected String getMsgPropVal(String key) {
		return globalUtils.getMessagePropValue(key);
	}
}