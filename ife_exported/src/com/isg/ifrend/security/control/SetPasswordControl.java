package com.isg.ifrend.security.control;

import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.isg.ifrend.security.bean.SaltedUser;
import com.isg.ifrend.security.dao.CustomJdbcDaoImpl;
import com.isg.ifrend.security.service.SecurityServiceLocator;
import com.isg.ifrend.utils.Commons;
import com.isg.ifrend.utils.GlobalUtils;

public class SetPasswordControl extends GenericForwardComposer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CustomJdbcDaoImpl userManager = SecurityServiceLocator.getCustomJdbcDaoImpl();
	private GlobalUtils globalUtils = GlobalUtils.getGlobalUtilsInstance();
	
	private AnnotateDataBinder binder;
	private SaltedUser user;
	
	private Window setPassword;
	private Textbox tbx_username;
	private Textbox tbx_old_pwd;
	private Textbox tbx_new_pwd;
	private Textbox tbx_confirm_pwd;
	
	public void doBeforeComposeChildren(Component comp) throws Exception {
		super.doBeforeComposeChildren(comp);
		user = (SaltedUser)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
//			StringBuffer sb = new StringBuffer();
//			sb.append("select flag from user_sec where user_id='");
//			sb.append(username);
//			sb.append("'");
//			if(getJdbcTemplate().queryForInt(sb.toString()) == 1) {
//				System.out.println("CustomJdbcDaoImpl >> loadUserByUsername >> " + username +
//						" >> flag is set to 1");
//				// Login user with set temp password.. redirect to SET PASSWORD PAGE
//				// Successful set password must set flag to 0!!
//			}
//			System.out.println("CustomJdbcDaoImpl >> loadUserByUsername >> " + username +
//			" >> flag is set to 0");
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		comp.setAttribute(comp.getId() + "Control", this, true);
		tbx_username.setText(user.getUsername());
		setPassword.setPosition("center");
		setPassword.doOverlapped();
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
	}
	
	public void onClick$btn_set_pwd() {
		try {
			if(userManager.validatePassword(user.getPassword(), tbx_old_pwd.getText(), user.getSalt())) {
				if(tbx_new_pwd.getText().equals(tbx_confirm_pwd.getText())) {
					userManager.changePassword(user.getUsername(), tbx_new_pwd.getText());
					userManager.disableUserFlag(user.getUsername());
					user.setFlag(false);
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
