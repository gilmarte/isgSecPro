/**
 * 
 */
package com.isg.ifrend.security.control;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.isg.ifrend.utils.Commons;
import com.isg.ifrend.utils.DateUtil;
import com.isg.ifrend.utils.SecurityAdminUtils;

/**
 * @author gerald.deguzman
 *
 */
@SuppressWarnings("serial")
public class GlobalHeaderPageViewCtrl extends GenericForwardComposer {

	@SuppressWarnings("unused")
	private Window GlobalHeaderPage;
	@SuppressWarnings("unused")
	private Button btn_logout;
	private Label lblUser;

	SecurityAdminUtils saUtils = new SecurityAdminUtils();
	
	/**
	 *
	 *
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		String username_ = ""; 
		String currentDateTime = ""; 
		String headerLabel = "";

		try {
			currentDateTime = Executions.getCurrent().getSession().getAttribute("current_date").toString();
		} catch (Exception e) {
			currentDateTime = "";
		}
		
		if(currentDateTime.equals("")){
			currentDateTime = DateUtil.format(DateUtil.getCurrentDate());
			Executions.getCurrent().getSession().setAttribute("current_date", currentDateTime);
		}

		username_ = SecurityAdminUtils.get_Logged_in_Username();		
		headerLabel = String.format("Welcome %s!", username_);

		lblUser.setValue(headerLabel);

	}
	
	public void onClick$btn_logout() throws InterruptedException{
		Messagebox.show(saUtils.getMessage(Commons.MSG_USER_LOGOUT), saUtils.getMessage(Commons.MSGBOX_TITLE_LOGOUT), Messagebox.OK|Messagebox.CANCEL, Messagebox.QUESTION, new EventListener() {
			
			@Override
			public void onEvent(Event e) throws Exception {
				
				if(e.getName().equals("onOK")){
					Executions.getCurrent().getSession().invalidate();
					Executions.getCurrent().sendRedirect("/j_spring_security_logout");
				}
			}
		});		
	}
	
	public void onClick$btn_back() {
		Executions.sendRedirect("/");
	}

}
