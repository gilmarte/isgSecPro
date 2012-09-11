package com.isg.ifrend.view.control.renderer;

import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.isg.ifrend.model.bean.ActionType;
import com.isg.ifrend.model.bean.SaUserGroup;
import com.isg.ifrend.model.bean.TempUser;
import com.isg.ifrend.model.bean.User;
import com.isg.ifrend.model.bean.UserOrganization;
import com.isg.ifrend.service.BusinessEntityManager;
import com.isg.ifrend.service.LanguageCodeManager;
import com.isg.ifrend.service.ServiceLocator;
import com.isg.ifrend.service.TempUserManager;
import com.isg.ifrend.service.UserManager;
import com.isg.ifrend.utils.Commons;
import com.isg.ifrend.utils.DateUtil;
import com.isg.ifrend.utils.GlobalUtils;

public class UserProfileAuthorizeListitemRenderer implements ListitemRenderer {

	@Override
	public void render(Listitem listItem, Object object) throws Exception {
		try {
			GlobalUtils globalUtils = GlobalUtils.getGlobalUtilsInstance();
			TempUserManager tempUserManager = ServiceLocator
					.getTempUserManager();
			UserManager userManager = ServiceLocator.getUserManager();
			BusinessEntityManager businessEntityManager = ServiceLocator.getBusinessEntity();
			LanguageCodeManager languageManager = ServiceLocator
					.getLanguageCodeManager();
			String labelAllowed = globalUtils.getGlobalPropValue(Commons.LABEL_ALLOWED);
			String labelNotAllowed = globalUtils.getGlobalPropValue(Commons.LABEL_NOT_ALLOWED);
			TempUser tempUser = tempUserManager.findById(object.toString());

			StringBuffer oldValues = new StringBuffer();
			StringBuffer newValues = new StringBuffer();
			listItem.setValue(object.toString());
			if (tempUser.getAction() == ActionType.UPDATE.getId()) {
				User user = userManager.findById(tempUser.getUserID());
				if (!user.getUserName().equals(tempUser.getUserName())) {
					oldValues.append(Labels.getLabel(Commons.USER_NAME))
							.append(Commons.COLON).append(user.getUserName())
							.append(Commons.NEW_LINE);
					newValues.append(Labels.getLabel(Commons.USER_NAME))
							.append(Commons.COLON)
							.append(tempUser.getUserName())
							.append(Commons.NEW_LINE);
				}
				if (!user.getEmployeeID().equals(tempUser.getEmployeeID())) {
					oldValues.append(Labels.getLabel(Commons.EMPLOYEE_ID))
							.append(Commons.COLON).append(user.getEmployeeID())
							.append(Commons.NEW_LINE);
					newValues.append(Labels.getLabel(Commons.EMPLOYEE_ID))
							.append(Commons.COLON)
							.append(tempUser.getEmployeeID())
							.append(Commons.NEW_LINE);
				}
				if (!user.geteMail().equals(tempUser.geteMail())) {
					oldValues.append(Labels.getLabel(Commons.EMAIL))
							.append(Commons.COLON).append(user.geteMail())
							.append(Commons.NEW_LINE);
					newValues.append(Labels.getLabel(Commons.EMAIL))
							.append(Commons.COLON).append(tempUser.geteMail())
							.append(Commons.NEW_LINE);
				}
				try {
					if (!user.getBusinessEntity().equals(tempUser.getBusinessEntity())) {
						oldValues
								.append(Labels.getLabel(Commons.BUSINESS_ENTITY))
								.append(Commons.COLON)
								.append(businessEntityManager.findById(
										user.getBusinessEntity())
										.getBus_ent_desc())
								.append(Commons.NEW_LINE);
						newValues
								.append(Labels.getLabel(Commons.BUSINESS_ENTITY))
								.append(Commons.COLON)
								.append(businessEntityManager.findById(
										tempUser.getBusinessEntity())
										.getBus_ent_desc())
								.append(Commons.NEW_LINE);
					}
				} catch (NullPointerException e) {
					oldValues.append(Labels.getLabel(Commons.BUSINESS_ENTITY))
							.append(Commons.COLON).append(Commons.NEW_LINE);
					newValues
							.append(Labels.getLabel(Commons.BUSINESS_ENTITY))
							.append(Commons.COLON)
							.append(businessEntityManager.findById(
									tempUser.getBusinessEntity())
									.getBus_ent_desc())
							.append(Commons.NEW_LINE);
				}
				if (user.isMaxAccountBalAndStatus() != tempUser.isMaxAccountBalAndStatus()) {
					oldValues
							.append(Labels.getLabel(Commons.MAX_ACCT_BAL_AND_STATUS))
							.append(Commons.COLON)
							.append(user.isMaxAccountBalAndStatus() ? labelAllowed
									: labelNotAllowed).append(Commons.NEW_LINE);
					newValues
							.append(Labels.getLabel(Commons.MAX_ACCT_BAL_AND_STATUS))
							.append(Commons.COLON)
							.append(tempUser.isMaxAccountBalAndStatus() ? labelAllowed
									: labelNotAllowed).append(Commons.NEW_LINE);
				}
				if (user.isVipAccountAllowed() != tempUser.isVipAccountAllowed()) {
					oldValues
							.append(Labels.getLabel(Commons.VIP_ACCT_ALLOWED))
							.append(Commons.COLON)
							.append(user.isVipAccountAllowed() ? labelAllowed
									: labelNotAllowed).append(Commons.NEW_LINE);
					newValues
							.append(Labels.getLabel(Commons.VIP_ACCT_ALLOWED))
							.append(Commons.COLON)
							.append(tempUser.isVipAccountAllowed() ? labelAllowed
									: labelNotAllowed).append(Commons.NEW_LINE);
				}
				if (user.isStaffAccountAllowed() != tempUser.isStaffAccountAllowed()) {
					oldValues
							.append(Labels.getLabel(Commons.STAFF_ACCT_ALLOWED))
							.append(Commons.COLON)
							.append(user.isStaffAccountAllowed() ? labelAllowed
									: labelNotAllowed).append(Commons.NEW_LINE);
					newValues
							.append(Labels.getLabel(Commons.STAFF_ACCT_ALLOWED))
							.append(Commons.COLON)
							.append(tempUser.isStaffAccountAllowed() ? labelAllowed
									: labelNotAllowed).append(Commons.NEW_LINE);
				}
				if (!user.getSupervisorID().equals(tempUser.getSupervisorID())) {
					oldValues.append(Labels.getLabel(Commons.SUPERVISOR_ID))
							.append(Commons.COLON)
							.append(user.getSupervisorID())
							.append(Commons.NEW_LINE);
					newValues.append(Labels.getLabel(Commons.SUPERVISOR_ID))
							.append(Commons.COLON)
							.append(tempUser.getSupervisorID())
							.append(Commons.NEW_LINE);
				}
				if (!user.getCustomerID().equals(tempUser.getCustomerID())) {
					oldValues.append(Labels.getLabel(Commons.CUSTOMER_ID))
							.append(Commons.COLON).append(user.getCustomerID())
							.append(Commons.NEW_LINE);
					newValues.append(Labels.getLabel(Commons.CUSTOMER_ID))
							.append(Commons.COLON)
							.append(tempUser.getCustomerID())
							.append(Commons.NEW_LINE);
				}
				try {
					if (!user.getLanguagePref().equals(tempUser.getLanguagePref())) {
						oldValues
								.append(Labels.getLabel(Commons.LANGUAGE_PREFERENCE))
								.append(Commons.COLON)
								.append(languageManager.findById(
										user.getLanguagePref())
										.getLcLanguageCodeDesc())
								.append(Commons.NEW_LINE);
						newValues
								.append(Labels.getLabel(Commons.LANGUAGE_PREFERENCE))
								.append(Commons.COLON)
								.append(languageManager.findById(
										tempUser.getLanguagePref())
										.getLcLanguageCodeDesc())
								.append(Commons.NEW_LINE);
					}
				} catch (NullPointerException e) {
					oldValues.append(Labels.getLabel(Commons.LANGUAGE_PREFERENCE))
							.append(Commons.COLON).append(Commons.NEW_LINE);
					newValues
							.append(Labels.getLabel(Commons.LANGUAGE_PREFERENCE))
							.append(Commons.COLON)
							.append(languageManager.findById(
									tempUser.getLanguagePref())
									.getLcLanguageCodeDesc())
							.append(Commons.NEW_LINE);
				}
				//TODO: modify checking of equality of groups and orgs
				try {
					if (user.getUserGroup().size() != tempUser.getUserGroup()
							.size()) {
						oldValues.append(Labels.getLabel(Commons.USER_GROUP))
								.append(Commons.COLON).append(Commons.NEW_LINE);
						newValues.append(Labels.getLabel(Commons.USER_GROUP))
								.append(Commons.COLON).append(Commons.NEW_LINE);
						for (SaUserGroup grp : tempUser.getUserGroup()) {
							newValues.append(grp.getGroup_id());
							newValues.append(Commons.NEW_LINE);
						}
						for (SaUserGroup grp : user.getUserGroup()) {
							oldValues.append(grp.getGroup_id());
							oldValues.append(Commons.NEW_LINE);
						}
					}
				} catch (NullPointerException e) {
					oldValues.append(Labels.getLabel(Commons.USER_GROUP))
							.append(Commons.COLON).append(Commons.NEW_LINE);
					newValues.append(Labels.getLabel(Commons.USER_GROUP))
							.append(Commons.COLON).append(Commons.NEW_LINE);
					for (SaUserGroup grp : tempUser.getUserGroup()) {
						newValues.append(grp.getGroup_id());
						newValues.append(Commons.NEW_LINE);
					}
				}
				try {
					if (user.getUserOrganization().size() != tempUser
							.getUserOrganization().size()) {
						oldValues.append(Labels.getLabel(Commons.USER_ORG))
								.append(Commons.COLON).append(Commons.NEW_LINE);
						newValues.append(Labels.getLabel(Commons.USER_ORG))
								.append(Commons.COLON).append(Commons.NEW_LINE);
						for (UserOrganization org : tempUser
								.getUserOrganization()) {
							newValues.append(org.getOrgID());
							newValues.append(Commons.NEW_LINE);
						}
						for (UserOrganization org : user.getUserOrganization()) {
							oldValues.append(org.getOrgID());
							oldValues.append(Commons.NEW_LINE);
						}
					}
				} catch (NullPointerException e) {
					oldValues.append(Labels.getLabel(Commons.USER_ORG))
							.append(Commons.COLON).append(Commons.NEW_LINE);
					newValues.append(Labels.getLabel(Commons.USER_ORG))
							.append(Commons.COLON).append(Commons.NEW_LINE);
					for (UserOrganization org : tempUser.getUserOrganization()) {
						newValues.append(org.getOrgID());
						newValues.append(Commons.NEW_LINE);
					}
				}
			}

			// Construct Listcells
			StringBuffer sb = new StringBuffer(globalUtils.getGlobalPropValue(Commons.URL_AUTHORIZE_VIEW_USER_PROFILE));
			sb.append(Commons.URL_DELIMITER)
				.append(Commons.URL_PARAM_TMP_ID)
				.append(Commons.EQUAL_SIGN)
				.append(tempUser.getUserID());
			Toolbarbutton userIdButton = new Toolbarbutton(tempUser.getUserID());
			userIdButton.setHref(sb.toString());
			userIdButton.setTooltiptext(globalUtils.getMessagePropValue(Commons.TOOLTIP_CLICK_TO_VIEW));

//			Toolbarbutton compareButton = new Toolbarbutton(Labels.getLabel(Commons.VIEW));
//			compareButton.setHref(sb.toString());
//			compareButton.setTooltiptext(globalUtils.getMessagePropValue(Commons.TOOLTIP_CLICK_TO_VIEW));

			Textbox oldValuesText = new Textbox(oldValues.toString());
			oldValuesText.setMultiline(true);
			oldValuesText.setRows(Commons.DISPLAY_ROWS);
			oldValuesText.setReadonly(true);
			Textbox newValuesText = new Textbox(newValues.toString());
			newValuesText.setMultiline(true);
			newValuesText.setRows(Commons.DISPLAY_ROWS);
			newValuesText.setReadonly(true);

			Listcell userIdCell = new Listcell();
			userIdCell.setParent(listItem);
			userIdButton.setParent(userIdCell);
//			new Listcell(StatusType.getDesc(tempUser.getStatus()))
//					.setParent(listItem);
//			new Listcell(ActionType.getDesc(tempUser.getAction()))
//					.setParent(listItem);
//			if(tempUser.getStatus() == StatusType.PENDING.getId()) {
//				new Listcell(PendingEntityStatusType.getDesc(tempUser.getAction())).setParent(listItem);
//			}
//			else {
//				new Listcell(StatusType.getDesc(tempUser.getStatus())).setParent(listItem);
//			}
			new Listcell(ActionType.getDesc(tempUser.getAction())).setParent(listItem);
			new Listcell(tempUser.getLastModifiedBy()).setParent(listItem);
			new Listcell(DateUtil.format(tempUser.getDateLastModified())).setParent(listItem);			
//			Double width = (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.22);
			Listcell oldValueCell = new Listcell();
			oldValueCell.setParent(listItem);
			Listcell newValueCell = new Listcell();
			newValueCell.setParent(listItem);
			
			if(tempUser.getAction() == ActionType.UPDATE.getId()) {
				oldValuesText.setWidth("210px");
				oldValuesText.setParent(oldValueCell);
				newValuesText.setWidth("210px");
				newValuesText.setParent(newValueCell);
			}
			
//			Listcell compareCell = new Listcell();
//			compareCell.setParent(listItem);
//			compareButton.setParent(compareCell);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
