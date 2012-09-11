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

import org.springframework.dao.DataIntegrityViolationException;
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
import org.zkoss.zul.api.Listbox;

import com.isg.ifrend.model.bean.ActionType;
import com.isg.ifrend.model.bean.BusinessEntity;
import com.isg.ifrend.model.bean.LanguageCode;
import com.isg.ifrend.model.bean.SaUserGroup;
import com.isg.ifrend.model.bean.StatusType;
import com.isg.ifrend.model.bean.TempUser;
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
public class UserProfileAddVM extends GenericForwardComposer {
	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//	@Wire("#win")
//	private Window win;
	private AnnotateDataBinder binder;
	private GlobalUtils globalUtils = GlobalUtils.getGlobalUtilsInstance();
	private SecurityUtils securityUtils = SecurityUtils.getSecurityUtilsInstance();
	private SecurityAdminUtils securityAdminUtils = SecurityAdminUtils.getSecurityAdminUtilsInstance();
	
	private StatusType status = StatusType.PENDING;
	private ActionType action = ActionType.ADD;
	private String labelAllowed = globalUtils.getGlobalPropValue(Commons.LABEL_ALLOWED);
	private String labelNotAllowed = globalUtils.getGlobalPropValue(Commons.LABEL_NOT_ALLOWED);
	private boolean codeAllowed = true;
	private boolean codeNotAllowed = false;
	
	private TempUser tempUser;
	private TempUserManager tempUserManager = ServiceLocator.getTempUserManager();
	private UserManager userManager = ServiceLocator.getUserManager();
	private SaUserGroupManager userGroupManager = ServiceLocator.getSaUserGroupManager();
	private UserOrganizationManager userOrgManager = ServiceLocator.getUserOrganizationManager();
//	private StatusManager statusManager = (StatusManager) ServiceLocator.getStatusManager();
	private BusinessEntityManager businessEntityManager = ServiceLocator.getBusinessEntity();
	private LanguageCodeManager languageManager = ServiceLocator.getLanguageCodeManager();
	private List<SaUserGroup> userGroupList;
	private List<UserOrganization> userOrgList;
	private List<SaUserGroup> assignedUserGroupList;
	private List<UserOrganization> assignedUserOrgList;
	private List<BusinessEntity> busEntityList;
	private List<LanguageCode> languageList;
		
	private Button btn_back_top;
	private Button btn_back_foot;

	private Textbox tbx_user_id;
	private Textbox tbx_emp_id;
	private Textbox tbx_username;
	private Textbox tbx_email;
	private Combobox cbb_business_entity;
	private Combobox cbb_max_acctbal;
	private Combobox cbb_vip_acct;
	private Combobox cbb_staff_acct;
	private Textbox tbx_default_password;
	private Textbox tbx_confirm_password;
	private Textbox tbx_supervisor_id;
	private Textbox tbx_customer_id;
	private Combobox cbb_language_pref;
	private Combobox cbb_user_group;
	private Listbox	lb_user_grp;
	private Combobox cbb_user_org;
	private Listbox	lb_user_org;
	
	private Label lb_info;
	private Image img_info;
	
	public void doBeforeComposeChildren(Component comp) throws Exception {
		super.doBeforeComposeChildren(comp);
		userGroupList = userGroupManager.getSaUserGroupList();
		userOrgList = userOrgManager.getUserOrgList();
		busEntityList = businessEntityManager.getBusinessEntityList();
		languageList = languageManager.getLanguageCodeList();
		assignedUserGroupList = new ArrayList<SaUserGroup>();
		assignedUserOrgList = new ArrayList<UserOrganization>();
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		tempUser = new TempUser();
		tempUser.setStatus(status.getId());
		tempUser.setAction(action.getId());
		btn_back_top.setHref(globalUtils.getGlobalPropValue(Commons.URL_SECURITY_ADMIN));
		btn_back_foot.setHref(globalUtils.getGlobalPropValue(Commons.URL_SECURITY_ADMIN));
		
		comp.setAttribute(comp.getId() + "Control", this, true);
				
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
	}
	
	public void onClick$btn_save_top() throws InterruptedException {
		Messagebox.show(globalUtils.getMessagePropValue(Commons.MSG_ASK_SUBMIT),
				globalUtils.getMessagePropValue(Commons.MSG_HEADER_ADD), Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION, new EventListener() {
					
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
	
	public void onClick$btn_save_foot() {
		save();		
	}
	
	public void onClick$btn_clear_top() {
		clear();
	}
	
	public void onClick$btn_clear_foot() {
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void onClick$btn_remove_user_org() {
		Set selectedOrg = lb_user_org.getSelectedItems();
		List itemsToRemove = new ArrayList(selectedOrg);
		for (Object selected : itemsToRemove) {
			Listitem item = (Listitem) selected;
			assignedUserOrgList.remove(item.getValue());
			item.setParent(null);
			lb_user_org.removeChild((Component)item);
		}
	}
	
	public void save() {
		try {
			if(isFormValid()) {
				tempUser = new TempUser();
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
				tempUser.setAction(ActionType.ADD.getId());
				Date date = DateUtil.getCurrentDate();
				tempUser.setCreatedBy(securityUtils.getUserName());
				tempUser.setDateCreated(date);
				tempUser.setLastModifiedBy(securityUtils.getUserName());
				tempUser.setDateLastModified(date);
				tempUserManager.save(tempUser);
				securityUtils.createUserCredentials(tempUser.getUserID(), tbx_default_password.getText());
				StringBuffer sb = new StringBuffer(globalUtils.getGlobalPropValue(Commons.URL_AUTHORIZE_VIEW_USER_PROFILE));
				sb.append(Commons.URL_DELIMITER);
				sb.append(Commons.URL_PARAM_TMP_ID);
				sb.append(Commons.EQUAL_SIGN);
				sb.append(tempUser.getUserID());
				Executions.getCurrent().getSession().setAttribute(Commons.SA_LBLINFO,
						MessageFormat.format(globalUtils.getMessagePropValue(Commons.MSG_USER_ADD), tempUser.getUserID()));
				Executions.sendRedirect(sb.toString());
//				Messagebox.show(MessageFormat.format(globalUtils.getMessagePropValue(
//						Commons.MSG_USER_ADD), tempUser.getUserID()),
//						globalUtils.getGlobalPropValue(Commons.SCREEN_USER_PROFILE_ADD),
//						Messagebox.OK, Messagebox.NONE, new EventListener() {
//							@Override
//							public void onEvent(Event evt) throws Exception {
//								switch (((Integer)evt.getData()).intValue()) {
//								case Messagebox.OK:
//									Executions.sendRedirect(globalUtils.getGlobalPropValue(Commons.URL_ADD_USER_PROFILE));
//									break;
//								};
//							}
//						});
			}
			else {
				lb_info.setValue(globalUtils.getMessagePropValue(Commons.MSG_ERROR));
				securityAdminUtils.setMsgStyle(img_info, lb_info, true);
			}
		}
		catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			e = (DataIntegrityViolationException)(GlobalUtils.getRootCauseException((Throwable) e));
			if (e.getMessage().contains("PK"))
			{
				lb_info.setValue(MessageFormat.format(globalUtils.getMessagePropValue(
						Commons.ERR_MSG_USER_EXIST), tempUser.getUserID()));
       			securityAdminUtils.setMsgStyle(img_info, lb_info, true);
//				GlobalUtils.showMessage(MessageFormat.format(globalUtils.getMessagePropValue(
//						Commons.ERR_MSG_USER_EXIST), tempUser.getUserID()),
//						globalUtils.getMessagePropValue(Commons.MSG_HEADER_ERROR),
//						Messagebox.OK, Messagebox.ERROR);
			}
			GlobalUtils.showMessage(globalUtils.getMessagePropValue(Commons.ERR_MSG_USER_ADD),
					globalUtils.getMessagePropValue(Commons.MSG_HEADER_ERROR),
					Messagebox.OK, Messagebox.ERROR);
		}
		catch (Exception e) {
			e.printStackTrace();
			GlobalUtils.showMessage(globalUtils.getMessagePropValue(Commons.ERR_MSG_USER_ADD),
					globalUtils.getMessagePropValue(Commons.MSG_HEADER_ERROR),
					Messagebox.OK, Messagebox.ERROR);
		}
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
	
	private boolean isFormValid() {
		boolean flag = true;
		try {
			String userID = tbx_user_id.getText();
			if(userID.isEmpty()) {
				tbx_user_id.setErrorMessage(globalUtils.getMessagePropValue(Commons.WARN_MSG_FIELD_EMPTY));
				flag = false;
			}
			else if (userManager.isUserExist(userID) || tempUserManager.isUserExist(userID)) {
				tbx_user_id.setErrorMessage(globalUtils.getMessagePropValue(Commons.WARN_MSG_ID_NOT_UNIQUE));
				flag = false;
			}
			else if(!userID.matches(globalUtils.getGlobalPropValue(Commons.ID_FORMAT_PATTERN))) {
				tbx_user_id.setErrorMessage(globalUtils.getMessagePropValue(Commons.ERR_MSG_INVALID_ID_FORMAT));
				flag = false;
			}
			if(tbx_default_password.getText().isEmpty()) {
				tbx_default_password.setErrorMessage(globalUtils.getMessagePropValue(Commons.WARN_MSG_FIELD_EMPTY));
				flag = false;
			}
			else if(tbx_confirm_password.getText().isEmpty()) {
				tbx_confirm_password.setErrorMessage(globalUtils.getMessagePropValue(Commons.WARN_MSG_FIELD_EMPTY));
				flag = false;
			}
			else if(!tbx_default_password.getText().equals(tbx_confirm_password.getText())) {
				tbx_confirm_password.setErrorMessage(globalUtils.getMessagePropValue(Commons.ERR_MSG_PASSWORD_DO_NOT_MATCH));
				flag = false;
			}
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

	public boolean getCodeAllowed() {
		return codeAllowed;
	}

	public void setCodeAllowed(boolean codeAllowed) {
		this.codeAllowed = codeAllowed;
	}

	public boolean getCodeNotAllowed() {
		return codeNotAllowed;
	}

	public void setCodeNotAllowed(boolean codeNotAllowed) {
		this.codeNotAllowed = codeNotAllowed;
	}

	
}
