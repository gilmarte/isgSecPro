/**
 * 
 */
package com.isg.ifrend.view.control;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Menuitem;

import com.isg.ifrend.security.utils.SecurityUtils;
import com.isg.ifrend.utils.Commons;

/**
 * @author kristine.furto
 *
 */
public class MainComposer extends GenericForwardComposer {

	private static final long serialVersionUID = -8617504491548451683L;
	private SecurityUtils securityUtils = SecurityUtils.getSecurityUtilsInstance();
//	private GlobalUtils globalUtils = GlobalUtils.getGlobalUtilsInstance();
	private Menuitem mi_security;

	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		if(securityUtils.isUserSecurityAdmin()) {
			mi_security.setVisible(true);
		}
//		if(securityUtils.isUserFlagged()) {
//			Executions.sendRedirect(globalUtils.getGlobalPropValue(Commons.URL_USER_FLAGGED));
//		}
	}
	
	public void onDrop$addMenu() {
		if (session.hasAttribute(Commons.SESSION_GLOBAL)) {
			session.removeAttribute(Commons.SESSION_GLOBAL);
		}
	}
	
	public void onOpen$addMenu() {
		if (session.hasAttribute(Commons.SESSION_GLOBAL)) {
			session.removeAttribute(Commons.SESSION_GLOBAL);
		}
	}
	
	public void onClick$searchMenu() {
		if (session.hasAttribute(Commons.SESSION_GLOBAL)) {
			session.removeAttribute(Commons.SESSION_GLOBAL);
		}
	}
	
	public void onClick$authorizeMenu() {
		if (session.hasAttribute(Commons.SESSION_GLOBAL)) {
			session.removeAttribute(Commons.SESSION_GLOBAL);
		}
	}
	
	
	
	

}
