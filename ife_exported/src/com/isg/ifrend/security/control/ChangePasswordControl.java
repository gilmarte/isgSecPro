package com.isg.ifrend.security.control;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.isg.ifrend.security.bean.SaltedUser;
import com.isg.ifrend.security.dao.CustomJdbcDaoImpl;
import com.isg.ifrend.security.service.SecurityServiceLocator;
import com.isg.ifrend.utils.Commons;
import com.isg.ifrend.utils.GlobalUtils;

public class ChangePasswordControl extends GenericForwardComposer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CustomJdbcDaoImpl userManager = SecurityServiceLocator.getCustomJdbcDaoImpl();
	private GlobalUtils globalUtils = GlobalUtils.getGlobalUtilsInstance();
	
	private AnnotateDataBinder binder;
	private SaltedUser user;
	
	private Window changePassword;
	private Textbox tbx_username;
	private Textbox tbx_old_pwd;
	private Textbox tbx_new_pwd;
	private Textbox tbx_confirm_pwd;
	private Button btn_cancel;
	
	public void doBeforeComposeChildren(Component comp) throws Exception {
		super.doBeforeComposeChildren(comp);
		
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		comp.setAttribute(comp.getId() + "Control", this, true);
		changePassword.setPosition("center");
		changePassword.doOverlapped();
		btn_cancel.setHref(globalUtils.getGlobalPropValue(Commons.URL_USER_LOGIN));
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
	}
	
	public void onClick$btn_set_pwd() {
		try {
			user = (SaltedUser) userManager.loadUserByUsername(tbx_username.getText());
			if(userManager.validatePassword(user.getPassword(), tbx_old_pwd.getText(), user.getSalt())) {
				if(tbx_new_pwd.getText().equals(tbx_confirm_pwd.getText())) {
					userManager.changePassword(user.getUsername(), tbx_new_pwd.getText());
					Executions.sendRedirect("/index.zul");
				}
				else {
					Messagebox.show(globalUtils.getMessagePropValue(Commons.ERR_MSG_PASSWORD_DO_NOT_MATCH),
						globalUtils.getMessagePropValue(Commons.MSG_HEADER_ERROR), Messagebox.OK, Messagebox.ERROR);
					clear();
				}
			}
			else {
				Messagebox.show(globalUtils.getMessagePropValue(Commons.ERR_MSG_WRONG_PASSWORD),
						globalUtils.getMessagePropValue(Commons.MSG_HEADER_ERROR), Messagebox.OK, Messagebox.ERROR);
				clear();
			}
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void onClick$btn_reset() {
		clear();
	}
	
	private void clear() {
		tbx_new_pwd.setText("");
		tbx_old_pwd.setText("");
		tbx_confirm_pwd.setText("");
	}

	public void setUserManager(CustomJdbcDaoImpl userManager) {
		this.userManager = userManager;
	}

}
