/**
 * 
 */
package com.isg.ifrend.view.control;

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.ObjectNotFoundException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.api.Listbox;

import com.isg.ifrend.model.bean.ActionType;
import com.isg.ifrend.model.bean.BusinessEntity;
import com.isg.ifrend.model.bean.LanguageCode;
import com.isg.ifrend.model.bean.SaUserGroup;
import com.isg.ifrend.model.bean.StatusType;
import com.isg.ifrend.model.bean.TempUser;
import com.isg.ifrend.model.bean.User;
import com.isg.ifrend.model.bean.UserOrganization;
import com.isg.ifrend.security.utils.SecurityUtils;
import com.isg.ifrend.service.BusinessEntityManager;
import com.isg.ifrend.service.LanguageCodeManager;
import com.isg.ifrend.service.SaUserGroupManager;
import com.isg.ifrend.service.ServiceLocator;
import com.isg.ifrend.service.TempUserManager;
import com.isg.ifrend.service.UserManager;
import com.isg.ifrend.service.UserOrganizationManager;
import com.isg.ifrend.utils.Commons;
import com.isg.ifrend.utils.DateUtil;
import com.isg.ifrend.utils.GlobalUtils;
import com.isg.ifrend.utils.SecurityAdminUtils;
import com.mysql.jdbc.StringUtils;

/**
 * @author elvin.aquino
 *
 */
public class UserProfileAuthorizeEditVM extends GenericForwardComposer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AnnotateDataBinder binder;
	private GlobalUtils globalUtils = GlobalUtils.getGlobalUtilsInstance();
	private SecurityUtils securityUtils = SecurityUtils.getSecurityUtilsInstance();
	private SecurityAdminUtils securityAdminUtils = SecurityAdminUtils.getSecurityAdminUtilsInstance();
	
	private String labelAllowed = globalUtils.returnPropValue(Commons.GLOBAL_PROPERTIES, Commons.LABEL_ALLOWED);
	private String labelNotAllowed = globalUtils.returnPropValue(Commons.GLOBAL_PROPERTIES, Commons.LABEL_NOT_ALLOWED);
	private String codeAllowed = "true";
	private String codeNotAllowed = "false";

	private User user;
	private TempUser tempUser;
	private UserManager userManager = ServiceLocator.getUserManager();
	private TempUserManager tempUserManager = ServiceLocator.getTempUserManager();
	private SaUserGroupManager userGroupManager = ServiceLocator.getSaUserGroupManager();
	private UserOrganizationManager userOrgManager = ServiceLocator.getUserOrganizationManager();
	private BusinessEntityManager businessEntityManager = ServiceLocator.getBusinessEntity();
	private LanguageCodeManager languageManager = ServiceLocator.getLanguageCodeManager();
	private List<SaUserGroup> userGroupList;
	private List<UserOrganization> userOrgList;
	private List<SaUserGroup> assignedUserGroupList;
	private List<UserOrganization> assignedUserOrgList;
	private List<BusinessEntity> busEntityList;
	private List<LanguageCode> languageList; 
		
	private Window userprofile_authorize_edit;
	private Button btn_approve_top;
	private Button btn_reject_top;
	private Button btn_update_top;
	private Button btn_submit_top;
	private Button btn_reset_top;
	private Button btn_cancel_top;
	private Button btn_back_top;
	private Button btn_approve_foot;
	private Button btn_reject_foot;
	private Button btn_update_foot;
	private Button btn_submit_foot;
	private Button btn_reset_foot;
	private Button btn_cancel_foot;
	private Button btn_back_foot;
	private Button btn_add_user_grp;
	private Button btn_remove_user_grp; 
	private Button btn_add_user_org;
	private Button btn_remove_user_org;
	
	private Textbox tbx_user_id;
	private Textbox tbx_emp_id;
	private Textbox tbx_username;
	private Textbox tbx_email;
	private Combobox cbb_business_entity;
	private Combobox cbb_max_acctbal;
	private Combobox cbb_vip_acct;
	private Combobox cbb_staff_acct;
	private Textbox tbx_current_status;
	private Textbox tbx_supervisor_id;
	private Textbox tbx_customer_id;
	private Combobox cbb_language_pref;
	private Combobox cbb_user_group;
	private Listbox	lb_user_grp;
	private Combobox cbb_user_org;
	private Listbox	lb_user_org;
	private Label lb_info;
	private Image img_info;
	
	private String userID;
		
	public void doBeforeComposeChildren(Component comp) throws Exception {
		super.doBeforeComposeChildren(comp);
		userGroupList = userGroupManager.getSaUserGroupList();
		userOrgList = userOrgManager.getUserOrgList();
		busEntityList = businessEntityManager.getBusinessEntityList();
		languageList = languageManager.getLanguageCodeList();
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		btn_back_top.setHref(globalUtils.getGlobalPropValue(Commons.URL_AUTHORIZE_USER_PROFILE));
		btn_back_foot.setHref(globalUtils.getGlobalPropValue(Commons.URL_AUTHORIZE_USER_PROFILE));
		try {
			String info = (String) Executions.getCurrent().getSession().getAttribute(Commons.SA_LBLINFO);
			if(!StringUtils.isNullOrEmpty(info)) {
				lb_info.setValue(info);
				securityAdminUtils.setMsgStyle(img_info, lb_info, false);
				btn_back_top.setHref(globalUtils.getGlobalPropValue(Commons.URL_ADD_USER_PROFILE));
				btn_back_foot.setHref(globalUtils.getGlobalPropValue(Commons.URL_ADD_USER_PROFILE));
			}
		}
		catch(Exception e) {
			
		}
		finally {
			Executions.getCurrent().getSession().removeAttribute(Commons.SA_LBLINFO);
		}
		
		userID = Executions.getCurrent().getParameter(Commons.URL_PARAM_TMP_ID);
		loadUserDetails(userID);
		manageButtons();
		
		comp.setAttribute(comp.getId() + "Control", this, true);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
	}
	
	public void onClick$btn_approve_top() throws InterruptedException {
		approveUser();
	}
	
	public void onClick$btn_approve_foot() throws InterruptedException {
		approveUser();
	}
	
	public void onClick$btn_reject_top() throws InterruptedException {
		rejectUser();
	}
	
	public void onClick$btn_reject_foot() throws InterruptedException {
		rejectUser();
	}
	
	public void onClick$btn_update_top() throws InterruptedException {
		updateUser();
	}
	
	public void onClick$btn_update_foot() throws InterruptedException {
		updateUser();
	}
	
	public void onClick$btn_submit_top() throws InterruptedException {
		submitUpdate();
	}
	
	public void onClick$btn_submit_foot() throws InterruptedException {
		submitUpdate();
	}
	
	public void onClick$btn_reset_top() throws InterruptedException {
		loadUserDetails(userID);
	}
	
	public void onClick$btn_reset_foot() throws InterruptedException {
		loadUserDetails(userID);
	}
	
	public void onClick$btn_cancel_top() throws InterruptedException {
		cancel();
	}
	
	public void onClick$btn_cancel_foot() throws InterruptedException {
		cancel();
	}
	
	public void onClick$btn_back_top() {
		
	}
	
	public void onClick$btn_back_foot() {
		
	}
	
	public void onClick$btn_add_user_grp() {
		try {
			SaUserGroup selectedUserGroup = (SaUserGroup)cbb_user_group.getSelectedItem().getValue();
			if (!assignedUserGroupList.contains(selectedUserGroup) &&
					assignedUserGroupList.add(selectedUserGroup)) {
				Listitem item = new Listitem();
				item.setLabel(selectedUserGroup.getGroup_id());
				item.setValue(selectedUserGroup);
				item.setParent(lb_user_grp);
				lb_user_grp.addItemToSelectionApi(item);
//				System.out.println("Item inserted: " + selectedUserGroup.getGroup_id());
			}
			else {
				lb_info.setValue(MessageFormat.format(globalUtils.getMessagePropValue(
						Commons.ERR_MSG_USER_ADDED_ALREADY), selectedUserGroup.getGroup_id()));
				securityAdminUtils.setMsgStyle(img_info, lb_info, true);
//				GlobalUtils.showMessage(MessageFormat.format(globalUtils.getMessagePropValue(
//						Commons.ERR_MSG_USER_ADDED_ALREADY), selectedUserGroup.getGroup_id()),
//						globalUtils.getGlobalPropValue(Commons.SCREEN_USER_PROFILE_AUTHORIZE),
//						Messagebox.OK, Messagebox.INFORMATION);
			}
		}
		catch (NullPointerException e) {
			lb_info.setValue(globalUtils.getMessagePropValue(Commons.ERR_MSG_USER_GRP_NO_SELECTED));
			securityAdminUtils.setMsgStyle(img_info, lb_info, true);
//			GlobalUtils.showMessage(globalUtils.getMessagePropValue(Commons.ERR_MSG_USER_GRP_NO_SELECTED),
//					globalUtils.getGlobalPropValue(Commons.SCREEN_USER_PROFILE_AUTHORIZE),
//					Messagebox.OK, Messagebox.INFORMATION);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void onClick$btn_remove_user_grp() {
		Set selectedGroups = lb_user_grp.getSelectedItems();
		List itemsToRemove = new ArrayList (selectedGroups);
		for (Object selected : itemsToRemove) {
			Listitem item = (Listitem) selected;
			assignedUserGroupList.remove(item.getValue());
			item.setParent(null);
			lb_user_grp.removeChild((Component)item);
		}
	}
	
	public void onClick$btn_add_user_org() {
		try {
			UserOrganization selectedUserOrg = (UserOrganization)cbb_user_org.getSelectedItem().getValue();
			if (!assignedUserOrgList.contains(selectedUserOrg) &&
					assignedUserOrgList.add(selectedUserOrg)) {
				Listitem item = new Listitem();
				item.setLabel(selectedUserOrg.getOrgID());
				item.setValue(selectedUserOrg);
				item.setParent(lb_user_org);
				lb_user_org.addItemToSelectionApi(item);
			}
			else {
				lb_info.setValue(MessageFormat.format(globalUtils.getMessagePropValue(
						Commons.ERR_MSG_USER_ADDED_ALREADY), selectedUserOrg.getOrgID()));
				securityAdminUtils.setMsgStyle(img_info, lb_info, true);
//				GlobalUtils.showMessage(MessageFormat.format(globalUtils.getMessagePropValue(
//						Commons.ERR_MSG_USER_ADDED_ALREADY), selectedUserOrg.getOrgID()),
//						globalUtils.getGlobalPropValue(Commons.SCREEN_USER_PROFILE_AUTHORIZE),
//						Messagebox.OK, Messagebox.INFORMATION);
			}
		}
		catch (NullPointerException e) {
			lb_info.setValue(globalUtils.getMessagePropValue(Commons.ERR_MSG_USER_ORG_NO_SELECTED));
			securityAdminUtils.setMsgStyle(img_info, lb_info, true);
//			GlobalUtils.showMessage(globalUtils.getMessagePropValue(Commons.ERR_MSG_USER_ORG_NO_SELECTED),
//					globalUtils.getGlobalPropValue(Commons.SCREEN_USER_PROFILE_AUTHORIZE),
//					Messagebox.OK, Messagebox.INFORMATION);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void onClick$btn_remove_user_org() {
		Set selectedOrg = lb_user_org.getSelectedItems();
		List itemsToRemove = new ArrayList (selectedOrg);
		for (Object selected : itemsToRemove) {
			Listitem item = (Listitem) selected;
			assignedUserOrgList.remove(item.getValue());
			item.setParent(null);
			lb_user_org.removeChild((Component)item);
		}
	}
	
	private void loadUserDetails(String userID) throws InterruptedException {
		try {
			tempUser = tempUserManager.findById(userID);
			tbx_user_id.setText(tempUser.getUserID());
			if (tempUser.getStatus() == StatusType.PENDING.getId()) {
				StringBuffer sb = new StringBuffer();
				sb.append(StatusType.PENDING.getDesc());
				sb.append(" - ");
				sb.append(ActionType.getDesc(tempUser.getAction()));
				tbx_current_status.setText(sb.toString());
			}
			else {
				tbx_current_status.setText(StatusType.getDesc(tempUser.getStatus()));
			}
			tbx_username.setText(tempUser.getUserName());
			tbx_emp_id.setText(tempUser.getEmployeeID());
			tbx_email.setText(tempUser.geteMail());
			BusinessEntity userBusEnt = businessEntityManager.findById(tempUser.getBusinessEntity()); 
			//		cbb_business_entity.setValue(userBusEnt.getBus_ent_code());
			cbb_business_entity.setText(userBusEnt.getBus_ent_desc());
			if(tempUser.isMaxAccountBalAndStatus()) {
				//			cbb_max_acctbal.setSelectedIndex(0);
				cbb_max_acctbal.setText(labelAllowed);
			}
			else {
				//			cbb_max_acctbal.setSelectedIndex(1);
				cbb_max_acctbal.setText(labelNotAllowed);
			}
			if(tempUser.isVipAccountAllowed()) {
				//			cbb_vip_acct.setSelectedIndex(0);
				cbb_vip_acct.setText(labelAllowed);
			}
			else {
				//			cbb_vip_acct.setSelectedIndex(1);
				cbb_vip_acct.setText(labelNotAllowed);
			}
			tbx_supervisor_id.setText(tempUser.getSupervisorID());
			if(tempUser.isStaffAccountAllowed()) {
				//			cbb_staff_acct.setSelectedIndex(0);
				cbb_staff_acct.setText(labelAllowed);
			}
			else {
				//			cbb_staff_acct.setSelectedIndex(1);
				cbb_staff_acct.setText(labelNotAllowed);
			}
			tbx_customer_id.setText(tempUser.getCustomerID());
			LanguageCode userLangPref = languageManager.findById(tempUser.getLanguagePref()); 
			cbb_language_pref.setText(userLangPref.getLcLanguageCodeDesc());
			//		cbb_language_pref.setValue(userLangPref.getLcLanguageCodeID());
			assignedUserGroupList = new ArrayList<SaUserGroup>();//(tempUser.getUserGroup());
			for (SaUserGroup grp : tempUser.getUserGroup()) {
				assignedUserGroupList.add(grp);
				Listitem item = new Listitem();
				item.setLabel(grp.getGroup_id());
				item.setValue(grp);
				item.setParent(lb_user_grp);
				lb_user_grp.addItemToSelectionApi(item);
			}
			assignedUserOrgList = new ArrayList<UserOrganization>();
			for (UserOrganization org : tempUser.getUserOrganization()) {
				assignedUserOrgList.add(org);
				Listitem item = new Listitem();
				item.setLabel(org.getOrgID());
				item.setValue(org);
				item.setParent(lb_user_org);
				lb_user_org.addItemToSelectionApi(item);
			}
		}
		catch(ObjectNotFoundException e) {
			Messagebox.show(MessageFormat.format(globalUtils.getMessagePropValue(
				Commons.ERR_MSG_USER_NOT_FOUND), userID),
				globalUtils.getMessagePropValue(Commons.MSG_HEADER_ERROR), 
				Messagebox.OK, Messagebox.ERROR,
				new EventListener() {
					@Override
					public void onEvent(Event evt) throws Exception {
						Executions.sendRedirect(globalUtils.getGlobalPropValue(Commons.URL_AUTHORIZE_USER_PROFILE));
					}
				}
			);
		}
	}
	
	public void enableFields() {
//		tbx_user_id.setReadonly(false);
		tbx_username.setReadonly(false);
		tbx_emp_id.setReadonly(false);
		tbx_email.setReadonly(false);
		cbb_business_entity.setButtonVisible(true);
		cbb_max_acctbal.setButtonVisible(true);
		cbb_vip_acct.setButtonVisible(true);
		tbx_supervisor_id.setReadonly(false);
		cbb_staff_acct.setButtonVisible(true);
		tbx_customer_id.setReadonly(false);
		cbb_language_pref.setButtonVisible(true);
		cbb_user_group.setButtonVisible(true);
		btn_add_user_grp.setDisabled(false);
		lb_user_grp.setCheckmark(true);
		btn_remove_user_grp.setDisabled(false);
		cbb_user_org.setButtonVisible(true);
		btn_add_user_org.setDisabled(false);
		lb_user_org.setCheckmark(true);
		btn_remove_user_org.setDisabled(false);
		btn_approve_top.setVisible(false);
		btn_reject_top.setVisible(false);
		btn_submit_top.setVisible(true);
		btn_reset_top.setVisible(true);
		btn_update_top.setVisible(false);
		btn_cancel_top.setVisible(false);
		btn_update_foot.setVisible(false);
		btn_cancel_foot.setVisible(false);
		btn_submit_foot.setVisible(true);
		btn_reset_foot.setVisible(true);
		btn_approve_foot.setVisible(false);
		btn_reject_foot.setVisible(false);
	}
	
	public void disableFields() {
//		tbx_user_id.setReadonly(true);
		tbx_username.setReadonly(true);
		tbx_emp_id.setReadonly(true);
		tbx_email.setReadonly(true);
		cbb_business_entity.setButtonVisible(false);
		cbb_max_acctbal.setButtonVisible(false);
		cbb_vip_acct.setButtonVisible(false);
		tbx_supervisor_id.setReadonly(true);
		cbb_staff_acct.setButtonVisible(false);
		tbx_customer_id.setReadonly(true);
		cbb_language_pref.setButtonVisible(false);
		cbb_user_group.setButtonVisible(false);
		btn_add_user_grp.setDisabled(true);
		lb_user_grp.setCheckmark(false);
		btn_remove_user_grp.setDisabled(true);
		cbb_user_org.setButtonVisible(false);
		btn_add_user_org.setDisabled(true);
		lb_user_org.setCheckmark(false);
		btn_remove_user_org.setDisabled(true);
		btn_submit_top.setVisible(false);
		btn_reset_top.setVisible(false);
		btn_submit_foot.setVisible(false);
		btn_reset_foot.setVisible(false);
		manageButtons();
	}
	
	public void approveUser() throws InterruptedException {
		Messagebox.show(MessageFormat.format(globalUtils.getMessagePropValue(
				Commons.MSG_USER_APPROVE_PROMPT), tempUser.getUserID()),
				globalUtils.getMessagePropValue(Commons.MSG_HEADER_APPROVE), 
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
				new EventListener() {
					@Override
					public void onEvent(Event evt) throws Exception {
						switch (((Integer)evt.getData()).intValue()) {
			            case Messagebox.OK:
			            	if(approveSelectedUser(tempUser)) {
			            		lb_info.setValue(MessageFormat.format(globalUtils.getMessagePropValue(
			            			Commons.MSG_USER_APPROVE), tempUser.getUserID()));
			        			securityAdminUtils.setMsgStyle(img_info, lb_info, false);
//			            		Messagebox.show(MessageFormat.format(globalUtils.getMessagePropValue(
//			            			Commons.MSG_USER_APPROVE), tempUser.getUserID()),
//			        				globalUtils.getMessagePropValue(Commons.MSG_HEADER_APPROVE), 
//			        				Messagebox.OK, Messagebox.INFORMATION, new EventListener() {
//										
//										@Override
//										public void onEvent(Event evt) throws Exception {
//											if(((Integer)evt.getData()).intValue() == Messagebox.OK) {
//												Executions.sendRedirect(
//													globalUtils.getGlobalPropValue(Commons.URL_AUTHORIZE_USER_PROFILE));
//											}
//										}
//									});
			            	}
			            	else {
			            		Messagebox.show(globalUtils.getMessagePropValue(Commons.ERR_MSG_USER_APPROVE),
				        				globalUtils.getMessagePropValue(Commons.MSG_HEADER_APPROVE), 
				        				Messagebox.OK, Messagebox.ERROR);
			            	}
			            	break;
			            case Messagebox.CANCEL:
			            	break;
						}
					}
			});
	}
	
	private void rejectUser() throws InterruptedException {
		Messagebox.show(MessageFormat.format(globalUtils.getMessagePropValue(
			Commons.MSG_USER_REJECT_PROMPT), tempUser.getUserID()),
			globalUtils.getMessagePropValue(Commons.MSG_HEADER_REJECT), 
			Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new EventListener() {
					
				@Override
				public void onEvent(Event evt) throws Exception {
					switch (((Integer)evt.getData()).intValue()) {
					case Messagebox.OK:
						if(rejectSelectedUser(tempUser)) {
							lb_info.setValue(MessageFormat.format(globalUtils.getMessagePropValue(
									Commons.MSG_USER_REJECT), tempUser.getUserID()));
							securityAdminUtils.setMsgStyle(img_info, lb_info, false);
//							Messagebox.show(MessageFormat.format(globalUtils.getMessagePropValue(
//									Commons.MSG_USER_REJECT), tempUser.getUserID()),
//		        					globalUtils.getMessagePropValue(Commons.MSG_HEADER_REJECT),
//		        					Messagebox.OK, Messagebox.INFORMATION, new EventListener() {
//										
//										@Override
//										public void onEvent(Event evt) throws Exception {
//											if(((Integer)evt.getData()).intValue() == Messagebox.OK) {
//												Executions.sendRedirect(
//													globalUtils.getGlobalPropValue(Commons.URL_AUTHORIZE_USER_PROFILE));
//											}
//										}
//									});
						}
						else {
							GlobalUtils.showMessage(globalUtils.getMessagePropValue(Commons.ERR_MSG_USER_REJECT),
		        				globalUtils.getMessagePropValue(Commons.MSG_HEADER_REJECT),
		        				Messagebox.OK, Messagebox.ERROR);
						}
			            break;
			        case Messagebox.CANCEL:
			        	// Do nothing when user selects cancel
			            break;
			        }
				}
			});
	}
	
	private boolean approveSelectedUser(TempUser tempUser) throws ParseException {
		boolean flag = false;
		int action = tempUser.getAction();
		// Check if logged in user is the same as the maker. If yes, skip.. else proceed
		if(securityUtils.getUserName().equals(tempUser.getLastModifiedBy())){
			// Do nothing
		}
		else {
			// Save or update user in the Master table
			tempUser = tempUserManager.findById(tempUser.getUserID());
			Date date = DateUtil.getCurrentDate();
			tempUser.setApprovedBy(securityUtils.getUserName());
			tempUser.setDateApproved(date);
			User user = new User(tempUser);
			user.setApprovedBy(securityUtils.getUserName());
			user.setDateApproved(date);
			if(action == ActionType.ADD.getId() || action == ActionType.UPDATE.getId()){
				user.setStatus(StatusType.ACTIVE.getId());
			}
			else if(action == ActionType.DELETE.getId()) {
				user.setStatus(StatusType.DELETED.getId());
			}
			// Update TEMP table to activate trigger for HISTORY
			// Delete record in the Temp table
			try {
				userManager.saveOrUpdate(user);
				tempUser.setStatus(StatusType.APPROVED.getId());
				tempUserManager.update(tempUser);
				tempUserManager.delete(tempUser);
				securityUtils.activateUser(tempUser.getUserID());
				flag = true;
			}
			catch(Exception e) {
				flag = false;
			}
		}
		return flag;
	}
	
	private boolean rejectSelectedUser(TempUser tempUser) throws Exception {
		boolean flag = false;
		int action = tempUser.getAction();
		tempUser = tempUserManager.findById(tempUser.getUserID());
		Date date = DateUtil.getCurrentDate();
		tempUser.setApprovedBy(securityUtils.getUserName());
		tempUser.setDateApproved(date);
		// Check if logged in user is the same as the maker. If yes, skip.. else proceed
		if(securityUtils.getUserName().equals(tempUser.getLastModifiedBy())){
			// Do nothing
		}
		else {
			// Revert Master status to active if update or delete
			if (action == ActionType.UPDATE.getId() || action == ActionType.DELETE.getId()) {
				try {
					User user = userManager.findById(tempUser.getUserID());
					user.setStatus(StatusType.ACTIVE.getId());
					user.setApprovedBy(securityUtils.getUserName());
					user.setDateApproved(date);
					userManager.update(user);
					flag = true;
				}
				catch (Exception e) {
					flag = false;
				}
			}
			// Update TEMP table to activate trigger for HISTORY
			// Delete record in the Temp table
//			if(flag || action == ActionType.ADD.getId()) {
			try {
				tempUser.setStatus(StatusType.REJECTED.getId());
				tempUserManager.update(tempUser);
				tempUserManager.delete(tempUser);
				if(action == ActionType.ADD.getId()) {
					securityUtils.deleteUserCredentials(tempUser.getUserID());
				}
				flag = true;
			}
			catch(Exception e) {
				flag = false;
			}
//			}
//			else {
//				flag = false;
//			}
		}
		return flag;
	}
	
	private void updateUser() throws InterruptedException {
			userprofile_authorize_edit.setTitle(globalUtils.getGlobalPropValue(Commons.SCREEN_USER_PROFILE_UPDATE));
			enableFields();
	}
	
	private void submitUpdate() throws InterruptedException {
		Messagebox.show(MessageFormat.format(globalUtils.getMessagePropValue(
				Commons.MSG_USER_UPDATE_PROMPT), tempUser.getUserID()),
				globalUtils.getMessagePropValue(Commons.MSG_HEADER_UPDATE), 
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
				new EventListener() {
					@Override
					public void onEvent(Event evt) throws Exception {
						switch (((Integer)evt.getData()).intValue()) {
			            case Messagebox.OK:
			            	save();
			            	userprofile_authorize_edit.setTitle(
			            			globalUtils.getGlobalPropValue(Commons.SCREEN_USER_PROFILE_AUTHORIZE));
			            	disableFields();
			            	break;
			            case Messagebox.CANCEL:
			            	break;
						}
					}
			});
	}
	
	public void save() {
		if(isFormValid()) {
			try {
//				tempUser = new TempUser();
				tempUser.setUserID(tbx_user_id.getText());
				tempUser.setEmployeeID(tbx_emp_id.getText());
				tempUser.setUserName(tbx_username.getText());
				tempUser.setBusinessEntity(cbb_business_entity.getSelectedItem().getValue().toString());
				tempUser.seteMail(tbx_email.getText());
				tempUser.setMaxAccountBalAndStatus(Boolean.valueOf(cbb_max_acctbal.getSelectedItem().getValue().toString()));
				tempUser.setVipAccountAllowed(Boolean.valueOf(cbb_vip_acct.getSelectedItem().getValue().toString()));
				tempUser.setStaffAccountAllowed(Boolean.valueOf(cbb_staff_acct.getSelectedItem().getValue().toString()));
				tempUser.setSupervisorID(tbx_supervisor_id.getText());
				tempUser.setCustomerID(tbx_customer_id.getText());
				tempUser.setLanguagePref(cbb_language_pref.getSelectedItem().getValue().toString());
				tempUser.setUserGroup(new HashSet<SaUserGroup>(assignedUserGroupList));
				tempUser.setUserOrganization(new HashSet<UserOrganization>(assignedUserOrgList));
				tempUser.setStatus(StatusType.PENDING.getId());
				try {
					user = userManager.findById(user.getUserID());
					user.setStatus(StatusType.PENDING.getId());
					user.setAction(ActionType.UPDATE.getId());
					userManager.saveOrUpdate(user);
					tempUser.setAction(ActionType.UPDATE.getId());
				}
				catch (NullPointerException e) {
					tempUser.setAction(ActionType.ADD.getId());
				}
				catch(ObjectNotFoundException e) {
					tempUser.setAction(ActionType.ADD.getId());
				}
				Date date = DateUtil.getCurrentDate();
				tempUser.setLastModifiedBy(securityUtils.getUserName());
				tempUser.setDateLastModified(date);
				tempUserManager.saveOrUpdate(tempUser);
				lb_info.setValue(MessageFormat.format(globalUtils.getMessagePropValue(
					Commons.MSG_USER_UPDATE),tempUser.getUserID()));
				securityAdminUtils.setMsgStyle(img_info, lb_info, false);
//				GlobalUtils.showMessage(MessageFormat.format(globalUtils.getMessagePropValue(
//					Commons.MSG_USER_UPDATE),tempUser.getUserID()),
//					globalUtils.getGlobalPropValue(Commons.SCREEN_USER_PROFILE_UPDATE),
//					Messagebox.OK, Messagebox.NONE);
			}
			catch (Exception e) {
				e.printStackTrace();
				GlobalUtils.showMessage(globalUtils.getMessagePropValue(Commons.ERR_MSG_USER_ADD),
					globalUtils.getGlobalPropValue(Commons.MSG_HEADER_ERROR),
					Messagebox.OK, Messagebox.ERROR);
			}
		}
		else {
			lb_info.setValue(globalUtils.getMessagePropValue(Commons.MSG_ERROR));
			securityAdminUtils.setMsgStyle(img_info, lb_info, true);
		}
	}
	
	private boolean cancelSelectedUser(TempUser tempUser) throws Exception {
		boolean flag = false;
		int action = tempUser.getAction();
		tempUser = tempUserManager.findById(tempUser.getUserID());
		// Check if logged in user is the same as the maker. If yes, skip.. else proceed
		if(securityUtils.getUserName().equals(tempUser.getLastModifiedBy())){
			Date date = DateUtil.getCurrentDate();
			// Revert Master status to active if update or delete
			if (action == ActionType.UPDATE.getId() || action == ActionType.DELETE.getId()) {
				try {
					User user = userManager.findById(tempUser.getUserID());
					user.setStatus(StatusType.ACTIVE.getId());
					user.setApprovedBy(securityUtils.getUserName());
					user.setDateApproved(date);
					userManager.update(user);
					flag = true;
				}
				catch (Exception e) {
					flag = false;
				}
			}
			// Update TEMP table to activate trigger for HISTORY
			// Delete record in the Temp table
//			if(flag || action == ActionType.ADD.getId()) {
			try {
				tempUser.setStatus(StatusType.CANCELLED.getId());
				tempUser.setApprovedBy(securityUtils.getUserName());
				tempUser.setDateApproved(date);
				tempUserManager.update(tempUser);
//				loadUserDetails(userID);
				tempUserManager.delete(tempUser);
				if(action == ActionType.ADD.getId()) {
					securityUtils.deleteUserCredentials(tempUser.getUserID());
				}
				flag = true;
			}
			catch(Exception e) {
				flag = false;
			}
//			}
//			else {
//				flag = false;
//			}
		}
		else {
			// Do nothing
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	public void clear() {
		tempUser = new TempUser();
		tbx_user_id.setText("");
		tbx_username.setText("");
		tbx_emp_id.setText("");
		tbx_email.setText("");
		cbb_business_entity.setSelectedItem(null);
		cbb_max_acctbal.setSelectedItem(null);
		cbb_vip_acct.setSelectedItem(null);
		cbb_staff_acct.setSelectedItem(null);
		tbx_supervisor_id.setText("");
		tbx_customer_id.setText("");
		cbb_language_pref.setSelectedItem(null);
		cbb_user_group.setSelectedItem(null);
		cbb_user_org.setSelectedItem(null);
		List<Object> list = lb_user_grp.getChildren();
		List<Object> itemsToRemove = new ArrayList<Object>(list);
		for (Object o : itemsToRemove) {
			lb_user_grp.removeChild((Component)o);
		}
		list = lb_user_org.getChildren();
		itemsToRemove = new ArrayList<Object>(list);
		for (Object o : itemsToRemove) {
			lb_user_org.removeChild((Component)o);
		}
		assignedUserGroupList.clear();
		assignedUserOrgList.clear();
	}
	
	private void cancel() throws InterruptedException {
		Messagebox.show(MessageFormat.format(globalUtils.getMessagePropValue(
				Commons.MSG_USER_CANCEL_PROMPT), tempUser.getUserID()),
				globalUtils.getMessagePropValue(Commons.MSG_HEADER_CANCEL), 
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
				new EventListener() {
					@Override
					public void onEvent(Event evt) throws Exception {
						switch (((Integer)evt.getData()).intValue()) {
			            case Messagebox.OK:
			            	if(cancelSelectedUser(tempUser)) {
			            		lb_info.setValue(MessageFormat.format(globalUtils.getMessagePropValue(
			            			Commons.MSG_USER_CANCEL), tempUser.getUserID()));
			        			securityAdminUtils.setMsgStyle(img_info, lb_info, false);
//			            		Messagebox.show(MessageFormat.format(globalUtils.getMessagePropValue(
//			            			Commons.MSG_USER_CANCEL), tempUser.getUserID()),
//			            			globalUtils.getMessagePropValue(Commons.MSG_HEADER_CANCEL), 
//				        			Messagebox.OK, Messagebox.INFORMATION, new EventListener() {
//										
//										@Override
//										public void onEvent(Event evt) throws Exception {
//											if(((Integer)evt.getData()).intValue() == Messagebox.OK) {
//												Executions.sendRedirect(
//													globalUtils.getGlobalPropValue(Commons.URL_AUTHORIZE_USER_PROFILE));
//											}
//										}
//									});
			            	}
			            	else {
			            		Messagebox.show(globalUtils.getMessagePropValue(Commons.ERR_MSG_USER_CANCEL),
				            			globalUtils.getMessagePropValue(Commons.MSG_HEADER_CANCEL), 
					        			Messagebox.OK, Messagebox.ERROR);
			            	}
			            	break;
			            case Messagebox.CANCEL:
			            	break;
						}
					}
			});
	}
	
	private boolean isFormValid() {
		boolean flag = true;
		try {
//			String userID = tbx_user_id.getText();
//			if(userID.isEmpty()) {
//				tbx_user_id.setErrorMessage(globalUtils.getMessagePropValue(Commons.WARN_MSG_FIELD_EMPTY));
//				flag = false;
//			}
//			else if (userManager.isUserExist(userID) || tempUserManager.isUserExist(userID)) {
//				tbx_user_id.setErrorMessage(globalUtils.getMessagePropValue(Commons.WARN_MSG_ID_NOT_UNIQUE));
//				flag = false;
//			}
			if(tbx_username.getText().isEmpty()) {
				tbx_username.setErrorMessage(globalUtils.getMessagePropValue(Commons.WARN_MSG_FIELD_EMPTY));
				flag = false;
			}
			if(tbx_emp_id.getText().isEmpty()) {
				tbx_emp_id.setErrorMessage(globalUtils.getMessagePropValue(Commons.WARN_MSG_FIELD_EMPTY));
				flag = false;
			}
			if(tbx_email.getText().isEmpty()) {
				tbx_email.setErrorMessage(globalUtils.getMessagePropValue(Commons.WARN_MSG_FIELD_EMPTY));
				flag = false;
			}
			if(cbb_business_entity.getSelectedItem() == null) {
				cbb_business_entity.setErrorMessage(globalUtils.getMessagePropValue(Commons.WARN_MSG_NO_SELECTED_ITEM));
				flag = false;
			}
			if(cbb_max_acctbal.getSelectedItem() == null) {
				cbb_max_acctbal.setErrorMessage(globalUtils.getMessagePropValue(Commons.WARN_MSG_NO_SELECTED_ITEM));
				flag = false;
			}
			if(cbb_vip_acct.getSelectedItem() == null) {
				cbb_vip_acct.setErrorMessage(globalUtils.getMessagePropValue(Commons.WARN_MSG_NO_SELECTED_ITEM));
				flag = false;
			}
			if(cbb_staff_acct.getSelectedItem() == null) {
				cbb_staff_acct.setErrorMessage(globalUtils.getMessagePropValue(Commons.WARN_MSG_NO_SELECTED_ITEM));
				flag = false;
			}
			if(tbx_supervisor_id.getText().isEmpty()) {
				tbx_supervisor_id.setErrorMessage(globalUtils.getMessagePropValue(Commons.WARN_MSG_FIELD_EMPTY));
				flag = false;
			}
			if(tbx_customer_id.getText().isEmpty()) {
				tbx_customer_id.setErrorMessage(globalUtils.getMessagePropValue(Commons.WARN_MSG_FIELD_EMPTY));
				flag = false;
			}
			if(cbb_language_pref.getSelectedItem() == null) {
				cbb_language_pref.setErrorMessage(globalUtils.getMessagePropValue(Commons.WARN_MSG_NO_SELECTED_ITEM));
				flag = false;
			}
			if(assignedUserGroupList.isEmpty()) {
				cbb_user_group.setErrorMessage(globalUtils.getMessagePropValue(Commons.WARN_MSG_NO_SELECTED_ITEM));
				flag = false;
			}
//			if(assignedUserOrgList.isEmpty()) {
//				cbb_user_org.setErrorMessage(globalUtils.getMessagePropValue(Commons.WARN_MSG_NO_SELECTED_ITEM));
//				flag = false;
//			}
			return flag;
		}
		catch (Exception e) {
			return false;
		}
	}
	
	private void manageButtons() {
		// Approve, Reject: Visible when has checker and not last modified by
		// Update, Cancel: Visible when has maker and last modified by
		if(securityUtils.hasCheckerRole(globalUtils.getGlobalPropValue(Commons.URL_AUTHORIZE_USER_PROFILE))) {
			if(tempUser.getLastModifiedBy().equals(securityUtils.getUserName())) {
				btn_approve_top.setVisible(false);
				btn_approve_foot.setVisible(false);
				btn_reject_top.setVisible(false);
				btn_reject_foot.setVisible(false);
			}
			else {
				btn_approve_top.setVisible(true);
				btn_approve_foot.setVisible(true);
				btn_reject_top.setVisible(true);
				btn_reject_foot.setVisible(true);
			}
		}
		if(securityUtils.hasMakerRole(globalUtils.getGlobalPropValue(Commons.URL_AUTHORIZE_USER_PROFILE))) {
			if(tempUser.getLastModifiedBy().equals(securityUtils.getUserName())) {
				btn_update_top.setVisible(true);
				btn_update_foot.setVisible(true);
				btn_cancel_top.setVisible(true);
				btn_cancel_foot.setVisible(true);
			}
			else {
				btn_update_top.setVisible(false);
				btn_update_foot.setVisible(false);
				btn_cancel_top.setVisible(false);
				btn_cancel_foot.setVisible(false);
			}
		}
	}
	
	public AnnotateDataBinder getBinder() {
		return binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public List<SaUserGroup> getUserGroupList() {
		return userGroupList;
	}

	public void setUserGroupList(List<SaUserGroup> userGroupList) {
		this.userGroupList = userGroupList;
	}

	public List<UserOrganization> getUserOrgList() {
		return userOrgList;
	}

	public void setUserOrgList(List<UserOrganization> userOrgList) {
		this.userOrgList = userOrgList;
	}

	public List<BusinessEntity> getBusEntityList() {
		return busEntityList;
	}

	public void setBusEntityList(List<BusinessEntity> busEntityList) {
		this.busEntityList = busEntityList;
	}

	public List<LanguageCode> getLanguageList() {
		return languageList;
	}

	public void setLanguageList(List<LanguageCode> languageList) {
		this.languageList = languageList;
	}

	public String getLabelAllowed() {
		return labelAllowed;
	}

	public void setLabelAllowed(String labelAllowed) {
		this.labelAllowed = labelAllowed;
	}

	public String getLabelNotAllowed() {
		return labelNotAllowed;
	}

	public void setLabelNotAllowed(String labelNotAllowed) {
		this.labelNotAllowed = labelNotAllowed;
	}

	public String getCodeAllowed() {
		return codeAllowed;
	}

	public void setCodeAllowed(String codeAllowed) {
		this.codeAllowed = codeAllowed;
	}

	public String getCodeNotAllowed() {
		return codeNotAllowed;
	}

	public void setCodeNotAllowed(String codeNotAllowed) {
		this.codeNotAllowed = codeNotAllowed;
	}

	public List<SaUserGroup> getAssignedUserGroupList() {
		return assignedUserGroupList;
	}

	public List<UserOrganization> getAssignedUserOrgList() {
		return assignedUserOrgList;
	}

	
}
