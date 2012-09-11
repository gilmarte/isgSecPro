package com.isg.ifrend.view.control;

import java.text.MessageFormat;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.isg.ifrend.security.utils.SecurityUtils;
import com.isg.ifrend.service.ServiceLocator;
import com.isg.ifrend.service.TempUserManager;
import com.isg.ifrend.service.UserManager;
import com.isg.ifrend.utils.Commons;
import com.isg.ifrend.utils.GlobalUtils;
import com.isg.ifrend.utils.SecurityAdminUtils;
import com.mysql.jdbc.StringUtils;

public class UserAdministrationControl extends GenericForwardComposer{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AnnotateDataBinder binder;
	private GlobalUtils globalUtils = GlobalUtils.getGlobalUtilsInstance();
	private SecurityUtils securityUtils = SecurityUtils.getSecurityUtilsInstance();
	private SecurityAdminUtils securityAdminUtils = SecurityAdminUtils.getSecurityAdminUtilsInstance();
	private UserManager userManager = ServiceLocator.getUserManager();
	private TempUserManager tempUserManager = ServiceLocator.getTempUserManager();
	
//	private Window userAdministration;
	private Textbox tbx_username;
	private Textbox tbx_new_pwd;
	private Textbox tbx_confirm_pwd;
	private Label lbl_info;
	private Image img_info;
	
	public void doBeforeComposeChildren(Component comp) throws Exception {
		super.doBeforeComposeChildren(comp);
		
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		comp.setAttribute(comp.getId() + "Control", this, true);
		
//		setPassword.setPosition("center");
//		setPassword.doOverlapped();
		
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
	}
	
	public void onClick$btn_set_pwd() throws InterruptedException {
		if (isFormValid()) {
			if(securityUtils.updatePassword(tbx_username.getText(), tbx_confirm_pwd.getText())) {
				lbl_info.setValue(MessageFormat.format(
						globalUtils.getMessagePropValue(Commons.MSG_PASSWORD_UPDATE_SUCCESSFUL),
						tbx_username.getText()));
				securityAdminUtils.setMsgStyle(img_info, lbl_info, false);
				clear();
			}
			else {
//				lbl_info.setValue(globalUtils.getMessagePropValue(Commons.ERR_MSG_UPDATE_PASSWORD_FAILED));
//				securityAdminUtils.setMsgStyle(img_info, lbl_info, true);
				Messagebox.show(globalUtils.getMessagePropValue(Commons.ERR_MSG_UPDATE_PASSWORD_FAILED),
					globalUtils.getMessagePropValue(Commons.MSG_HEADER_ERROR),
					Messagebox.OK, Messagebox.ERROR);
			}
		}
		else {
			lbl_info.setValue(globalUtils.getMessagePropValue(Commons.MSG_ERROR));
			securityAdminUtils.setMsgStyle(img_info, lbl_info, true);
		}
	}
	
	public void onClick$btn_reset() {
		clear();
	}
	
	private boolean isFormValid() {
		boolean flag = true;
		String userID = tbx_username.getText();
		if(StringUtils.isNullOrEmpty(userID)) {
			tbx_username.setErrorMessage(globalUtils.getMessagePropValue(Commons.WARN_MSG_FIELD_EMPTY));
			flag = false;
		}
		else if(!(userManager.isUserExist(userID) || tempUserManager.isUserExist(userID))) {
			tbx_username.setErrorMessage(globalUtils.getMessagePropValue(Commons.WARN_MSG_USER_DOES_NOT_EXIST));
			flag = false;
		}
		if(StringUtils.isNullOrEmpty(tbx_new_pwd.getText())) {
			tbx_new_pwd.setErrorMessage(globalUtils.getMessagePropValue(Commons.WARN_MSG_FIELD_EMPTY));
			flag = false;
		}
		if(StringUtils.isNullOrEmpty(tbx_confirm_pwd.getText())) {
			tbx_confirm_pwd.setErrorMessage(globalUtils.getMessagePropValue(Commons.WARN_MSG_FIELD_EMPTY));
			flag = false;
		}
		else if(!(tbx_new_pwd.getText().equals(tbx_confirm_pwd.getText()))) {
			tbx_confirm_pwd.setErrorMessage(globalUtils.getMessagePropValue(Commons.ERR_MSG_PASSWORD_DO_NOT_MATCH));
			flag = false;
		}
		return flag;
	}
	
	private void clear() {
		tbx_username.setText("");
		tbx_new_pwd.setText("");
		tbx_confirm_pwd.setText("");
	}

}
