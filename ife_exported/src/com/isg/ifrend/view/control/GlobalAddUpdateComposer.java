package com.isg.ifrend.view.control;

import org.apache.commons.lang.StringUtils;
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
import org.zkoss.zul.Window;
import org.zkoss.zul.impl.XulElement;

import com.isg.ifrend.model.bean.Global;
import com.isg.ifrend.model.bean.StatusType;
import com.isg.ifrend.security.utils.SecurityUtils;
import com.isg.ifrend.service.GlobalManager;
import com.isg.ifrend.utils.Commons;
import com.isg.ifrend.utils.GlobalUtils;

public abstract class GlobalAddUpdateComposer<M extends Global, T extends Global>
		extends GenericForwardComposer {

	private static final long serialVersionUID = -3690780163539776214L;

	private static final String TITLE_DELIMITER = " - ";
	private static final String TITLE_UPDATE = "Update";
	private static final String TITLE_EDIT = "Edit";

	private GlobalManager<M, T> globalManager;
	private GlobalUtils globalUtils = GlobalUtils.getGlobalUtilsInstance();
	private SecurityUtils securityUtils = SecurityUtils.getSecurityUtilsInstance();

	private Window globalWin;
	protected Label messageLbl;
	private Image messageImg;

	private XulElement idRow;
	private Label idLbl;

	private Button cancelTopBtn;

	protected T tmpGlobal;

	private boolean isNew;

	protected abstract GlobalManager<M, T> getManager();

	protected abstract T getNewTmpGlobalInstance();

	protected abstract T toTmp(M m);

	protected abstract void setComponents();

	protected abstract void resetViewValue();

	protected abstract void setViewValue();

	protected abstract boolean validateInput();

	protected abstract void getViewValue();

	protected abstract String getViewUrl();

	public GlobalAddUpdateComposer() {
		globalManager = getManager();
		globalManager.setCurrentUser(securityUtils.getUserName());
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		cancelTopBtn.setVisible(false);

		messageLbl.setValue(Commons.EMPTY_STRING);
		setComponents();

		tmpGlobal = (T) session.getAttribute(Commons.SESSION_GLOBAL);
		session.removeAttribute(Commons.SESSION_GLOBAL);

		isNew = null == tmpGlobal;

		if (isNew) {
			session.setAttribute(Commons.SESSION_URL, Commons.MODULE_ADD);

			tmpGlobal = getNewTmpGlobalInstance();

			idRow.setVisible(false);
			resetViewValue();

		} else {
			if (StatusType.ACTIVE.getId() == tmpGlobal.getStatusID()) {
				setTitle(TITLE_UPDATE);

			} else {
				tmpGlobal = globalManager.viewPendingDetails(tmpGlobal.getId());
				setTitle(TITLE_EDIT);
			}

			idLbl.setValue(tmpGlobal.getId());
			setViewValue();
		}
	}

	protected void setMsgStyle() {
		if (StringUtils.isNotEmpty(messageLbl.getValue())) {
			messageLbl.setSclass(getGlobalPropVal(Commons.STYLE_MSG_ERR));
			messageImg.setVisible(true);
			messageImg.setSrc(getGlobalPropVal(Commons.IMG_SRC_MSG_ERR));
		} else {
			messageImg.setVisible(false);
		}
	}

	private void setTitle(String title) {
		globalWin.setTitle(new StringBuilder(tmpGlobal.getEntityDesc())
				.append(TITLE_DELIMITER).append(title).toString());
	}

	public void onClick$submitTopBtn() throws InterruptedException {
		onClick$submitBtn();
	}

	public void onClick$submitBtn() throws InterruptedException {
		if (validateInput()) {
			// protected abstract boolean verifyChange();
			showMessageAskSubmit();
		}
	}

	private void showMessageAskSubmit() throws InterruptedException {
		Messagebox.show(getMsgPropVal(Commons.MSG_ASK_SUBMIT),
				getMsgPropVal(Commons.MSG_TITLE_SUBMIT), Messagebox.OK
						| Messagebox.CANCEL, Messagebox.EXCLAMATION,
				new EventListener() {
					public void onEvent(Event evt) throws InterruptedException {
						if (Messagebox.ON_OK.equals(evt.getName())) {
							submitRequest();
						}
					}
				});
	}

	private void submitRequest() {
		getViewValue();
		try {
			if (isNew) {
				tmpGlobal.setId(String.valueOf(globalManager
						.requestAdd(tmpGlobal)));
			} else {
				if (StatusType.ACTIVE.getId() == tmpGlobal.getStatusID()) {
					globalManager.requestUpdate(tmpGlobal);
				} else {
					globalManager.updateRequest(tmpGlobal);
				}
			}

			showMessageInfoSubmit();
		} catch (DataIntegrityViolationException e) {
			messageLbl.setValue(getMsgPropVal(Commons.RECORD_EXIST));
		}
	}

	private void showMessageInfoSubmit() {
		session.setAttribute(Commons.SESSION_MESSAGE, getMsgPropVal(Commons.MSG_INFO_SUBMIT));
		session.setAttribute(Commons.SESSION_GLOBAL, tmpGlobal);
		redirect(getViewUrl());
	}

	public void onClick$resetTopBtn() throws InterruptedException {
		onClick$resetBtn();
	}

	public void onClick$resetBtn() throws InterruptedException {
		if (isNew) {
			showMessageAskClear();
		} else {
			showMessageAskReset();
		}
	}

	private void showMessageAskClear() throws InterruptedException {
		Messagebox.show(getMsgPropVal(Commons.MSG_ASK_CLEAR),
				getMsgPropVal(Commons.MSG_TITLE_CLEAR), Messagebox.OK
						| Messagebox.CANCEL, Messagebox.EXCLAMATION,
				new EventListener() {
					public void onEvent(Event evt) throws InterruptedException {
						if (Messagebox.ON_OK.equals(evt.getName())) {
							messageLbl.setValue(Commons.EMPTY_STRING);
							resetViewValue();
							setMsgStyle();
						}
					}
				});
	}

	private void showMessageAskReset() throws InterruptedException {
		Messagebox.show(getMsgPropVal(Commons.MSG_ASK_RESET),
				getMsgPropVal(Commons.MSG_TITLE_RESET), Messagebox.OK
						| Messagebox.CANCEL, Messagebox.EXCLAMATION,
				new EventListener() {
					public void onEvent(Event evt) throws InterruptedException {
						if (Messagebox.ON_OK.equals(evt.getName())) {
							messageLbl.setValue(Commons.EMPTY_STRING);
							setViewValue();
							setMsgStyle();
						}
					}
				});
	}

	public void onClick$cancelTopBtn() {
		onClick$cancelBtn();
	}

	public void onClick$cancelBtn() {
		if (isNew) {
			redirect(Commons.URL_SEARCH_GLOBAL);
		} else {
			session.setAttribute(Commons.SESSION_GLOBAL, tmpGlobal);
			redirect(getViewUrl());
		}
	}

	public void onClick$backTopBtn() {
		onClick$backBtn();
	}

	public void onClick$backBtn() {
		if (isNew) {
			redirect(Commons.URL_HOME);
		} else {
			session.setAttribute(Commons.SESSION_GLOBAL, tmpGlobal);
			redirect(getViewUrl());
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

	protected String getCurrentUser() {
		return securityUtils.getUserName();
	}
}