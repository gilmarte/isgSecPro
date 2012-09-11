package com.isg.ifrend.view.control;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;

import com.isg.ifrend.security.utils.SecurityUtils;
import com.isg.ifrend.utils.Commons;
import com.isg.ifrend.utils.GlobalUtils;

public class RedirectVM extends GenericForwardComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	SecurityUtils securityUtils = SecurityUtils.getSecurityUtilsInstance();
	GlobalUtils globalUtils = GlobalUtils.getGlobalUtilsInstance();
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		if(securityUtils.isUserFlagged()) {
			Executions.sendRedirect(globalUtils.getGlobalPropValue(Commons.URL_USER_FLAGGED));
		}
		else if(securityUtils.isUserGBCR()) {
			Executions.sendRedirect("/layout/main.zul");
		}
		else if(securityUtils.isUserSecurityAdmin()) {
			Executions.sendRedirect("/salayout/security.zul");
		}
		else {
			Executions.sendRedirect("/accessDenied.zul");
		}
	}

}
