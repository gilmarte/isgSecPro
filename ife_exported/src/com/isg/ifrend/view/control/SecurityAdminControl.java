package com.isg.ifrend.view.control;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Menuitem;

import com.isg.ifrend.security.utils.SecurityUtils;
import com.isg.ifrend.utils.SecurityAdminUtils;

public class SecurityAdminControl extends GenericForwardComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AnnotateDataBinder binder;
	private SecurityUtils securityUtils = SecurityUtils.getSecurityUtilsInstance();
	private SecurityAdminUtils securityAdminUtils = SecurityAdminUtils.getSecurityAdminUtilsInstance();
	
	private Menuitem password_admin;
	private Menuitem mi_gbcr;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		comp.setAttribute(comp.getId() + "Control", this, true);
		if(securityAdminUtils.isUserAdmin(securityUtils.getUserName())) {
			password_admin.setVisible(true);
		}
		if(securityUtils.isUserGBCR()) {
			mi_gbcr.setVisible(true);
		}
		
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
	}

}
