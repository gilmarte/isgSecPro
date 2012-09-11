/**
 * 
 */
package com.isg.ifrend.view.control;

import org.hibernate.ObjectNotFoundException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.isg.ifrend.model.bean.ActionType;
import com.isg.ifrend.model.bean.BusinessEntity;
import com.isg.ifrend.model.bean.HistUser;
import com.isg.ifrend.model.bean.LanguageCode;
import com.isg.ifrend.model.bean.SaUserGroup;
import com.isg.ifrend.model.bean.StatusType;
import com.isg.ifrend.model.bean.User;
import com.isg.ifrend.model.bean.UserOrganization;
import com.isg.ifrend.service.BusinessEntityManager;
import com.isg.ifrend.service.HistUserManager;
import com.isg.ifrend.service.LanguageCodeManager;
import com.isg.ifrend.service.ServiceLocator;
import com.isg.ifrend.service.UserManager;
import com.isg.ifrend.utils.Commons;
import com.isg.ifrend.utils.GlobalUtils;

/**
 * @author elvin.aquino
 *
 */

public class UserProfileCompareVM extends GenericForwardComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	AnnotateDataBinder binder;

	@SuppressWarnings("unused")
	private Window userProfileComparePage;
	private Label lbl_temp_user_id;
	private Label lbl_temp_current_status;
	private Label lbl_temp_username;
	private Label lbl_temp_emp_id;
	private Label lbl_temp_email;
	private Label lbl_temp_business_entity;
	private Label lbl_temp_max_acctbal;
	private Label lbl_temp_vip_acct;
	private Label lbl_temp_supervisor_id;
	private Label lbl_temp_staff_acct;
	private Label lbl_temp_customer_id;
	private Label lbl_temp_language_pref;
	private Label lbl_temp_user_grp;
	private Label lbl_temp_user_org;
	private Label lbl_mst_user_id;
	private Label lbl_mst_current_status;
	private Label lbl_mst_username;
	private Label lbl_mst_emp_id;
	private Label lbl_mst_email;
	private Label lbl_mst_business_entity;
	private Label lbl_mst_max_acctbal;
	private Label lbl_mst_vip_acct;
	private Label lbl_mst_supervisor_id;
	private Label lbl_mst_staff_acct;
	private Label lbl_mst_customer_id;
	private Label lbl_mst_language_pref;
	private Label lbl_mst_user_grp;
	private Label lbl_mst_user_org;
	
	private Button btn_back;
	
	private UserManager userManager = ServiceLocator.getUserManager();
	private HistUserManager histUserManager = ServiceLocator.getHistUserManager();
	private BusinessEntityManager businessEntityManager = ServiceLocator.getBusinessEntity();
	private LanguageCodeManager languageManager = ServiceLocator.getLanguageCodeManager();
	private GlobalUtils globalUtils = GlobalUtils.getGlobalUtilsInstance();
	private String labelAllowed = globalUtils.getGlobalPropValue(Commons.LABEL_ALLOWED);
	private String labelNotAllowed = globalUtils.getGlobalPropValue(Commons.LABEL_NOT_ALLOWED);
	private String style = globalUtils.getGlobalPropValue(Commons.STYLE_COMPARE);  
	
	private HistUser histUser;
	private User user;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		binder = new AnnotateDataBinder();

		String historyIndex = Executions.getCurrent().getParameter("id");
		
		histUser = histUserManager.findByIndex(Integer.parseInt(historyIndex));
		
		try {
			int histStatus = histUser.getStatus();
		
			if (!((histStatus == StatusType.PENDING.getId() || histStatus == StatusType.CANCELLED.getId() ||
					histStatus == StatusType.REJECTED.getId()) &&
					histUser.getAction() == ActionType.ADD.getId())) {
				user = userManager.findById(histUser.getUserID());
			}
			else {
				user = new User();
			}

			compareUserFields();
			btn_back.setHref(globalUtils.getGlobalPropValue(Commons.URL_HISTORY_USER_PROFILE));
		}
		catch(ObjectNotFoundException e) {
			Messagebox.show(globalUtils.getMessagePropValue(Commons.ERR_MSG_USER_HISTORY_NOT_FOUND),
				globalUtils.getMessagePropValue(Commons.MSG_HEADER_ERROR), 
				Messagebox.OK, Messagebox.ERROR,
				new EventListener() {
					@Override
					public void onEvent(Event evt) throws Exception {
						Executions.sendRedirect(globalUtils.getGlobalPropValue(Commons.URL_HISTORY_USER_PROFILE));
					}
				}
			);
		}
		
		comp.setAttribute(comp.getId() + "Control", this, true);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();

	}
	
	private void compareUserFields() {
		StringBuffer sb = new StringBuffer();
		BusinessEntity userBusEnt;
		LanguageCode userLangPref;
		
		if(histUser.getStatus() == StatusType.PENDING.getId() && histUser.getAction() == ActionType.DELETE.getId()) {
			lbl_temp_user_id.setValue(histUser.getUserID());
			sb.setLength(0);
			sb.append(StatusType.PENDING.getDesc());
			sb.append(" ");
			sb.append(ActionType.getDesc(histUser.getAction()));
			lbl_temp_current_status.setValue(sb.toString());
			lbl_temp_username.setValue(histUser.getUserName());
		}
		else {
			lbl_temp_user_id.setValue(histUser.getUserID());
			sb.setLength(0);
			if(histUser.getStatus() == StatusType.PENDING.getId()) {
				sb.append(StatusType.PENDING.getDesc());
				sb.append(" ");
				sb.append(ActionType.getDesc(histUser.getAction()));
			}
			else {
				sb.append(StatusType.getDesc(histUser.getStatus()));
			}
			lbl_temp_current_status.setValue(sb.toString());
			lbl_temp_username.setValue(histUser.getUserName());
			lbl_temp_emp_id.setValue(histUser.getEmployeeID());
			lbl_temp_email.setValue(histUser.geteMail());
			userBusEnt = businessEntityManager.findById(histUser.getBusinessEntity()); 
			lbl_temp_business_entity.setValue(userBusEnt.getBus_ent_desc());
			if(histUser.isMaxAccountBalAndStatus()) {
				lbl_temp_max_acctbal.setValue(labelAllowed);
			}
			else {
				lbl_temp_max_acctbal.setValue(labelNotAllowed);
			}
			if(histUser.isVipAccountAllowed()) {
				lbl_temp_vip_acct.setValue(labelAllowed);
			}
			else {
				lbl_temp_vip_acct.setValue(labelNotAllowed);
			}
			lbl_temp_supervisor_id.setValue(histUser.getSupervisorID());
			if(histUser.isStaffAccountAllowed()) {
				lbl_temp_staff_acct.setValue(labelAllowed);
			}
			else {
				lbl_temp_staff_acct.setValue(labelNotAllowed);
			}
			lbl_temp_customer_id.setValue(histUser.getSupervisorID());
			userLangPref = languageManager.findById(histUser.getLanguagePref());
			lbl_temp_language_pref.setValue(userLangPref.getLcLanguageCodeDesc());
			sb.setLength(0);
			for(SaUserGroup grp : histUser.getUserGroup()) {
				sb.append(grp.getGroup_id());
				sb.append(Commons.NEW_LINE);
			}
			lbl_temp_user_grp.setValue(sb.toString());
			sb.setLength(0);
			for(UserOrganization org : histUser.getUserOrganization()) {
				sb.append(org.getOrgID());
				sb.append(Commons.NEW_LINE);
			}
			lbl_temp_user_org.setValue(sb.toString());
		}
		
		lbl_mst_user_id.setValue(user.getUserID());
		sb.setLength(0);
		if(user.getStatus() == StatusType.PENDING.getId()) {
			sb.append(StatusType.PENDING.getDesc());
			sb.append(" ");
			sb.append(ActionType.getDesc(user.getAction()));
		}
		else {
			sb.append(StatusType.getDesc(user.getStatus()));
		} 
		try {
			lbl_mst_current_status.setValue(sb.toString());
			lbl_mst_username.setValue(user.getUserName());
			lbl_mst_emp_id.setValue(user.getEmployeeID());
			lbl_mst_email.setValue(user.geteMail());
			userBusEnt = businessEntityManager.findById(user.getBusinessEntity()); 
			lbl_mst_business_entity.setValue(userBusEnt.getBus_ent_desc());
			if(user.isMaxAccountBalAndStatus()) {
				lbl_mst_max_acctbal.setValue(labelAllowed);
			}
			else {
				lbl_mst_max_acctbal.setValue(labelNotAllowed);
			}
			if(user.isVipAccountAllowed()) {
				lbl_mst_vip_acct.setValue(labelAllowed);
			}
			else {
				lbl_mst_vip_acct.setValue(labelNotAllowed);
			}
			lbl_mst_supervisor_id.setValue(user.getSupervisorID());
			if(user.isStaffAccountAllowed()) {
				lbl_mst_staff_acct.setValue(labelAllowed);
			}
			else {
				lbl_mst_staff_acct.setValue(labelNotAllowed);
			}
			lbl_mst_customer_id.setValue(user.getSupervisorID());
			userLangPref = languageManager.findById(user.getLanguagePref());
			lbl_mst_language_pref.setValue(userLangPref.getLcLanguageCodeDesc());
			sb.setLength(0);
			for(SaUserGroup grp : user.getUserGroup()) {
				sb.append(grp.getGroup_id());
				sb.append(Commons.NEW_LINE);
			}
			lbl_mst_user_grp.setValue(sb.toString());
			sb.setLength(0);
			for(UserOrganization org : user.getUserOrganization()) {
				sb.append(org.getOrgID());
				sb.append(Commons.NEW_LINE);
			}
			lbl_mst_user_org.setValue(sb.toString());
		}
		catch(NullPointerException e) {
			
		}
		
		if(!lbl_temp_current_status.getValue().equals(lbl_mst_current_status.getValue())) {
			lbl_temp_current_status.setStyle(style);
			lbl_mst_current_status.setStyle(style);
		}
		if(!lbl_temp_username.getValue().equals(lbl_mst_username.getValue())) {
			lbl_temp_username.setStyle(style);
			lbl_mst_username.setStyle(style);
		}
		if(!lbl_temp_emp_id.getValue().equals(lbl_mst_emp_id.getValue())) {
			lbl_temp_emp_id.setStyle(style);
			lbl_mst_emp_id.setStyle(style);
		}
		if(!lbl_temp_email.getValue().equals(lbl_mst_email.getValue())) {
			lbl_temp_email.setStyle(style);
			lbl_mst_email.setStyle(style);
		}
		if(!lbl_temp_business_entity.getValue().equals(lbl_mst_business_entity.getValue())) {
			lbl_temp_business_entity.setStyle(style);
			lbl_mst_business_entity.setStyle(style);
		}
		if(!lbl_temp_max_acctbal.getValue().equals(lbl_mst_max_acctbal.getValue())) {
			lbl_temp_max_acctbal.setStyle(style);
			lbl_mst_max_acctbal.setStyle(style);
		}
		if(!lbl_temp_vip_acct.getValue().equals(lbl_mst_vip_acct.getValue())) {
			lbl_temp_vip_acct.setStyle(style);
			lbl_mst_vip_acct.setStyle(style);
		}
		if(!lbl_temp_supervisor_id.getValue().equals(lbl_mst_supervisor_id.getValue())) {
			lbl_temp_supervisor_id.setStyle(style);
			lbl_mst_supervisor_id.setStyle(style);
		}
		if(!lbl_temp_staff_acct.getValue().equals(lbl_mst_staff_acct.getValue())) {
			lbl_temp_staff_acct.setStyle(style);
			lbl_mst_staff_acct.setStyle(style);
		}
		if(!lbl_temp_customer_id.getValue().equals(lbl_mst_customer_id.getValue())) {
			lbl_temp_customer_id.setStyle(style);
			lbl_mst_customer_id.setStyle(style);
		}
		if(!lbl_temp_language_pref.getValue().equals(lbl_mst_language_pref.getValue())) {
			lbl_temp_language_pref.setStyle(style);
			lbl_mst_language_pref.setStyle(style);
		}
		if(!lbl_temp_user_grp.getValue().equals(lbl_mst_user_grp.getValue())) {
			lbl_temp_user_grp.setStyle(style);
			lbl_mst_user_grp.setStyle(style);
		}
		if(!lbl_temp_user_org.getValue().equals(lbl_mst_user_org.getValue())) {
			lbl_temp_user_org.setStyle(style);
			lbl_mst_user_org.setStyle(style);
		}
	}

}
