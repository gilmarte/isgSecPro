/**
 * 
 */
package com.isg.ifrend.security.control;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

import com.isg.ifrend.utils.Commons;
import com.isg.ifrend.utils.GlobalUtils;
import com.isg.ifrend.utils.SecurityAdminUtils;

/**
 * @author gerald.deguzman
 *
 */
@SuppressWarnings("serial")
public class LoginwinViewCtrl extends GenericForwardComposer {

//	private Window loginwin;
	@SuppressWarnings("unused")
	private Textbox txtpassword;
	@SuppressWarnings("unused")
	private Textbox txtusername;
	private Label lblInfo;
	
	private GlobalUtils globalUtils = GlobalUtils.getGlobalUtilsInstance();

	/**
	 *
	 *
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		SecurityAdminUtils saUtils = new SecurityAdminUtils();
				
		String param = Executions.getCurrent().getParameter("login_error");
		if(param != null){
			String errMsg = saUtils.toCamelcase(globalUtils.getMessagePropValue(Commons.ERR_MSG_INVALID_CREDENTIALS));
			lblInfo.setStyle(globalUtils.getGlobalPropValue(Commons.STYLE_ERR_MSG));
			lblInfo.setValue(errMsg);
		}					
		
		/*loginwin.setPosition("left,center");
		loginwin.doOverlapped();*/
	}

}
