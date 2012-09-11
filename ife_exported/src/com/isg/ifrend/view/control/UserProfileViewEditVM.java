/**
 * 
 */
package com.isg.ifrend.view.control;

import java.text.MessageFormat;
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

/**
 * @author elvin.aquino
 *
 */
public class UserProfileViewEditVM extends GenericForwardComposer {
	
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
	private UserManager userManager = (UserManager) ServiceLocator.getUserManager();
	private TempUserManager tempUserManager = (TempUserManager) ServiceLocator.getTempUserManager();
	private SaUserGroupManager userGroupManager = (SaUserGroupManager) ServiceLocator.getSaUserGroupManager();
	private UserOrganizationManager userOrgManager = (UserOrganizationManager) ServiceLocator.getUserOrganizationManager();
	private BusinessEntityManager businessEntityManager = (BusinessEntityManager) ServiceLocator.getBusinessEntity();
	private LanguageCodeManager languageManager = (LanguageCodeManager) ServiceLocator.getLanguageCodeManager();
	private List<SaUserGroup> userGroupList;
	private List<UserOrganization> userOrgList;
	private List<SaUserGroup> assignedUserGroupList;
	private List<UserOrganization> assignedUserOrgList;
	private List<BusinessEntity> busEntityList;
	private List<LanguageCode> languageList;
//	private List<Status> statusList; 
		
	private Window userprofile_view_edit;
	private Button btn_update_top;
	private Button btn_submit_top;
	private Button btn_delete_top;
	private Button btn_clear_top;
	private Button btn_back_top;
	private Button btn_update_foot;
	private Button btn_submit_foot;
	private Button btn_delete_foot;
	private Button btn_clear_foot;
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
//		statusList = statusManager.getStatusList();
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		userprofile_view_edit.setTitle(globalUtils.getGlobalPropValue(Commons.SCREEN_USER_PROFILE_VIEW));
		userID = Executions.getCurrent().getParameter(Commons.URL_PARAM_MST_ID);
		loadUserDetails(userID);
		manageButtons();
		btn_back_top.setHref(globalUtils.getGlobalPropValue(Commons.URL_SEARCH_USER_PROFILE));
		btn_back_foot.setHref(globalUtils.getGlobalPropValue(Commons.URL_SEARCH_USER_PROFILE));
		
		comp.setAttribute(comp.getId() + "Control", this, true);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
	}
	
	public void onClick$btn_update_top() throws InterruptedException {
		updateUser();
	}
	
	public void onClick$btn_update_foot() throws InterruptedException {
		updateUser();
	}
	
	public void onClick$btn_delete_top() throws InterruptedException {
		deleteUser();
	}
	
	public void onClick$btn_delete_foot() throws InterruptedException {
		deleteUser();
	}
	
	public void onClick$btn_submit_top() throws InterruptedException {
		submitUpdate();
	}
	
	public void onClick$btn_submit_foot() throws InterruptedException {
		submitUpdate();
	}
	
//	public void onClick$btn_back_top() {
//		cancel();
//	}
//	
//	public void onClick$btn_back_foot() {
//		cancel();
//	}
	
	public void onClick$btn_clear_top() throws InterruptedException {
		clear();
	}
	
	public void onClick$btn_clear_foot() throws InterruptedException {
		clear();
	}
	
	public void onClick$btn_add_user_grp() {
		try {
			SaUserGroup selectedUserGroup = (SaUserGroup)(cbb_user_group.getSelectedItem().getValue());
			if (!assignedUserGroupList.contains(selectedUserGroup) &&
					assignedUserGroupList.add(selectedUserGroup)) {
				Listitem item = new Listitem();
				item.setLabel(selectedUserGroup.getGroup_id());
				item.setValue(selectedUserGroup);
				item.setParent(lb_user_grp);
				lb_user_grp.addItemToSelectionApi(item);
			}
			else {
				lb_info.setValue(MessageFormat.format(globalUtils.getMessagePropValue(
						Commons.ERR_MSG_USER_ADDED_ALREADY), selectedUserGroup.getGroup_id()));
				securityAdminUtils.setMsgStyle(img_info, lb_info, true);
//				GlobalUtils.showMessage(MessageFormat.format(globalUtils.getMessagePropValue(
//						Commons.ERR_MSG_USER_ADDED_ALREADY), selectedUserGroup.getGroup_id()),
//						globalUtils.getGlobalPropValue(Commons.SCREEN_USER_PROFILE_ADD),
//						Messagebox.OK, Messagebox.INFORMATION);
			}
		}
		catch (NullPointerException e) {
			lb_info.setValue(globalUtils.getMessagePropValue(Commons.ERR_MSG_USER_GRP_NO_SELECTED));
			securityAdminUtils.setMsgStyle(img_info, lb_info, true);
//			GlobalUtils.showMessage(globalUtils.getMessagePropValue(Commons.ERR_MSG_USER_GRP_NO_SELECTED),
//					globalUtils.getGlobalPropValue(Commons.SCREEN_USER_PROFILE_ADD),
//					Messagebox.OK, Messagebox.INFORMATION);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
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
			UserOrganization selectedUserOrg = (UserOrganization)(cbb_user_org.getSelectedItem().getValue());
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
//						globalUtils.getGlobalPropValue(Commons.SCREEN_USER_PROFILE_ADD),
//						Messagebox.OK, Messagebox.INFORMATION);
			}
		}
		catch (NullPointerException e) {
			lb_info.setValue(globalUtils.getMessagePropValue(Commons.ERR_MSG_USER_ORG_NO_SELECTED));
			securityAdminUtils.setMsgStyle(img_info, lb_info, true);
//			GlobalUtils.showMessage(globalUtils.getMessagePropValue(Commons.ERR_MSG_USER_ORG_NO_SELECTED),
//					globalUtils.getGlobalPropValue(Commons.SCREEN_USER_PROFILE_ADD),
//					Messagebox.OK, Messagebox.INFORMATION);
		}
	}
	
	public void deleteUser() throws InterruptedException {
		if(user.getStatus() == StatusType.ACTIVE.getId()) {
			Messagebox.show(MessageFormat.format(globalUtils.getMessagePropValue(
					Commons.MSG_USER_DELETE_PROMPT), user.getUserID()),
					globalUtils.getMessagePropValue(Commons.MSG_HEADER_DELETE), 
					Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
					new EventListener() {

						@Override
						public void onEvent(Event evt) throws Exception {
							switch (((Integer)evt.getData()).intValue()) {
				            case Messagebox.OK:
				            	if(deleteSelectedUser(user)) {
				            		lb_info.setValue(MessageFormat.format(globalUtils.getMessagePropValue(
				            				Commons.MSG_USER_DELETE), user.getUserID()));
				        			securityAdminUtils.setMsgStyle(img_info, lb_info, false);
//				            		GlobalUtils.showMessage(MessageFormat.format(globalUtils.getMessagePropValue(
//				            				Commons.MSG_USER_DELETE), user.getUserID()),
//				        					globalUtils.getMessagePropValue(Commons.MSG_HEADER_DELETE),
//				        					Messagebox.OK, Messagebox.INFORMATION);
				            		loadUserDetails(user.getUserID());
				            	}
				            	else {
				        			GlobalUtils.showMessage(globalUtils.getMessagePropValue(Commons.ERR_MSG_USER_DELETE),
				        				globalUtils.getMessagePropValue(Commons.MSG_HEADER_DELETE),
				        				Messagebox.OK, Messagebox.ERROR);
				        		}
				            	break;
				            case Messagebox.CANCEL:
				            	// Do nothing when user cancels
				            	break;
				            }
						}
					});
		}
		else {
			lb_info.setValue(MessageFormat.format(globalUtils.getMessagePropValue(
					Commons.ERR_MSG_USER_DELETE_NOT_ALLOWED), user.getUserID()));
			securityAdminUtils.setMsgStyle(img_info, lb_info, true);
//			Messagebox.show(MessageFormat.format(globalUtils.getMessagePropValue(
//					Commons.ERR_MSG_USER_DELETE_NOT_ALLOWED), user.getUserID()),
//					globalUtils.getMessagePropValue(Commons.MSG_HEADER_ERROR), 
//					Messagebox.OK, Messagebox.ERROR);
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
			user = userManager.findById(userID);
			tbx_user_id.setText(user.getUserID());
			if (user.getStatus() == StatusType.PENDING.getId()) {
				StringBuffer sb = new StringBuffer();
				sb.append(StatusType.PENDING.getDesc());
				sb.append(" - ");
				sb.append(ActionType.getDesc(user.getAction()));
				tbx_current_status.setText(sb.toString());
			}
			else {
				tbx_current_status.setText(StatusType.getDesc(user.getStatus()));
			}
			tbx_username.setText(user.getUserName());
			tbx_emp_id.setText(user.getEmployeeID());
			tbx_email.setText(user.geteMail());
			BusinessEntity userBusEnt = businessEntityManager.findById(user.getBusinessEntity()); 
//			cbb_business_entity.setValue(userBusEnt.getBus_ent_code());
			cbb_business_entity.setText(userBusEnt.getBus_ent_desc());
			if(user.isMaxAccountBalAndStatus()) {
//				cbb_max_acctbal.setSelectedIndex(0);
				cbb_max_acctbal.setText(labelAllowed);
			}
			else {
//				cbb_max_acctbal.setSelectedIndex(1);
				cbb_max_acctbal.setText(labelNotAllowed);
			}
			if(user.isVipAccountAllowed()) {
//				cbb_vip_acct.setSelectedIndex(0);
				cbb_vip_acct.setText(labelAllowed);
			}
			else {
//				cbb_vip_acct.setSelectedIndex(1);
				cbb_vip_acct.setText(labelNotAllowed);
			}
			tbx_supervisor_id.setText(user.getSupervisorID());
			if(user.isStaffAccountAllowed()) {
//				cbb_staff_acct.setSelectedIndex(0);
				cbb_staff_acct.setText(labelAllowed);
			}
			else {
//				cbb_staff_acct.setSelectedIndex(1);
				cbb_staff_acct.setText(labelNotAllowed);
			}
			tbx_customer_id.setText(user.getCustomerID());
			LanguageCode userLangPref = languageManager.findById(user.getLanguagePref()); 
			cbb_language_pref.setText(userLangPref.getLcLanguageCodeDesc());
//			cbb_language_pref.setValue(userLangPref.getLcLanguageCodeID());
			assignedUserGroupList = new ArrayList<SaUserGroup>();//(user.getUserGroup());
			for (SaUserGroup grp : user.getUserGroup()) {
				assignedUserGroupList.add(grp);
				Listitem item = new Listitem();
				item.setLabel(grp.getGroup_id());
				item.setValue(grp);
				item.setParent(lb_user_grp);
				lb_user_grp.addItemToSelectionApi(item);
			}
			assignedUserOrgList = new ArrayList<UserOrganization>();
			for (UserOrganization org : user.getUserOrganization()) {
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
						Executions.sendRedirect(globalUtils.getGlobalPropValue(Commons.URL_SEARCH_USER_PROFILE));
					}
				}
			);
		}
	}
	
	private void enableFields() {
		userprofile_view_edit.setTitle(globalUtils.getGlobalPropValue(Commons.SCREEN_USER_PROFILE_UPDATE));
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
		btn_update_top.setVisible(false);
		btn_update_foot.setVisible(false);
		btn_delete_top.setVisible(false);
		btn_delete_foot.setVisible(false);
		btn_submit_top.setVisible(true);
		btn_submit_foot.setVisible(true);
		btn_clear_top.setVisible(true);
		btn_clear_foot.setVisible(true);
	}
	
	private void disableFields() {
		userprofile_view_edit.setTitle(globalUtils.getGlobalPropValue(Commons.SCREEN_USER_PROFILE_VIEW));
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
		btn_submit_foot.setVisible(false);
		btn_clear_top.setVisible(false);
		btn_clear_foot.setVisible(false);
		manageButtons();
	}
	
	private void updateUser() throws InterruptedException {
		if(user.getStatus() == StatusType.ACTIVE.getId()) {
			enableFields();
		}
		else {
			lb_info.setValue(MessageFormat.format(globalUtils.getMessagePropValue(
					Commons.ERR_MSG_USER_UPDATE_NOT_ALLOWED), user.getUserID()));
			securityAdminUtils.setMsgStyle(img_info, lb_info, true);
//			Messagebox.show(MessageFormat.format(globalUtils.getMessagePropValue(
//					Commons.ERR_MSG_USER_UPDATE_NOT_ALLOWED), user.getUserID()),
//					globalUtils.getMessagePropValue(Commons.MSG_HEADER_ERROR), 
//					Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	private void submitUpdate() throws InterruptedException {
		Messagebox.show(MessageFormat.format(globalUtils.getMessagePropValue(
				Commons.MSG_USER_UPDATE_PROMPT), user.getUserID()),
				globalUtils.getMessagePropValue(Commons.MSG_HEADER_UPDATE), 
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
				new EventListener() {
					@Override
					public void onEvent(Event evt) throws Exception {
						switch (((Integer)evt.getData()).intValue()) {
			            case Messagebox.OK:
			            	save();
			            	break;
			            case Messagebox.CANCEL:
			            	break;
						}
					}
			});
	}
	
	private boolean deleteSelectedUser(User user) {
		try {
			Date date = DateUtil.getCurrentDate();
			user.setStatus(StatusType.PENDING.getId());
			user.setAction(ActionType.DELETE.getId());
			user.setLastModifiedBy(securityUtils.getUserName());
			user.setDateLastModified(date);
			userManager.update(user);
			
			TempUser tempUser = new TempUser(userManager.findById(user.getUserID()));
			tempUser.setStatus(StatusType.PENDING.getId());
			tempUser.setAction(ActionType.DELETE.getId());
			tempUser.setLastModifiedBy(securityUtils.getUserName());
			tempUser.setDateLastModified(date);
			return tempUserManager.save(tempUser);
		}
		catch (Exception e) {
			return false;
		}
	}
	
	private void save() {
		try {
			if(isFormValid()) {
				Date date = DateUtil.getCurrentDate();
				user.setStatus(StatusType.PENDING.getId());
				user.setAction(ActionType.UPDATE.getId());
				user.setLastModifiedBy(securityUtils.getUserName());
				user.setDateLastModified(date);
				tempUser = new TempUser(user);
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
				tempUserManager.saveOrUpdate(tempUser);
				userManager.update(user);
				lb_info.setValue(MessageFormat.format(globalUtils.getMessagePropValue(
					Commons.MSG_USER_UPDATE), tempUser.getUserID()));
				securityAdminUtils.setMsgStyle(img_info, lb_info, false);
//				GlobalUtils.showMessage(MessageFormat.format(globalUtils.getMessagePropValue(
//					Commons.MSG_USER_UPDATE), tempUser.getUserID()),
//					globalUtils.getGlobalPropValue(Commons.SCREEN_USER_PROFILE_UPDATE),
//					Messagebox.OK, Messagebox.NONE);
//            	loadUserDetails(userID);
            	disableFields();
			}
			else {
				lb_info.setValue(globalUtils.getMessagePropValue(Commons.MSG_ERROR));
				securityAdminUtils.setMsgStyle(img_info, lb_info, true);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			GlobalUtils.showMessage(MessageFormat.format(globalUtils.getMessagePropValue(
					Commons.ERR_MSG_USER_ADD), tempUser.getUserID()),
					globalUtils.getGlobalPropValue(Commons.MSG_HEADER_ERROR),
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	private void clear() throws InterruptedException {
		lb_user_grp.selectAll();
		onClick$btn_remove_user_grp();
		lb_user_org.selectAll();
		onClick$btn_remove_user_org();
		loadUserDetails(userID);
	}
	
//	private void cancel() {
//		Executions.sendRedirect(globalUtils.getGlobalPropValue(Commons.URL_SEARCH_USER_PROFILE));
//	}
	
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
		if(user.getStatus() == StatusType.ACTIVE.getId() &&
				securityUtils.hasMakerRole(globalUtils.getGlobalPropValue(Commons.URL_UPDATE_USER_PROFILE))) {
			btn_update_top.setVisible(true);
			btn_delete_top.setVisible(true);
			btn_update_foot.setVisible(true);
			btn_delete_foot.setVisible(true);
		}
		else {
			btn_update_top.setVisible(false);
			btn_delete_top.setVisible(false);
			btn_update_foot.setVisible(false);
			btn_delete_foot.setVisible(false);
		}
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
