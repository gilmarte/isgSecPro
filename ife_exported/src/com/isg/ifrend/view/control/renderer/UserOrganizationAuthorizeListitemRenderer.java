package com.isg.ifrend.view.control.renderer;

import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.isg.ifrend.model.bean.ActionType;
import com.isg.ifrend.model.bean.TempUserOrganization;
import com.isg.ifrend.model.bean.UserOrganization;
import com.isg.ifrend.service.ServiceLocator;
import com.isg.ifrend.service.TempUserOrganizationManager;
import com.isg.ifrend.service.UserOrganizationManager;
import com.isg.ifrend.utils.Commons;
import com.isg.ifrend.utils.DateUtil;
import com.isg.ifrend.utils.GlobalUtils;
import com.mysql.jdbc.StringUtils;

public class UserOrganizationAuthorizeListitemRenderer implements
		ListitemRenderer {

	@Override
	public void render(Listitem listItem, Object object) throws Exception {
		TempUserOrganizationManager tempUserOrgManager = ServiceLocator.getTempUserOrganizationManager();
		UserOrganizationManager userOrgManager = ServiceLocator.getUserOrganizationManager();
		GlobalUtils globalUtils = GlobalUtils.getGlobalUtilsInstance();

		TempUserOrganization tempUserOrg = tempUserOrgManager.findById(object.toString());

		listItem.setValue(tempUserOrg.getOrgID());
		StringBuffer oldValues = new StringBuffer();
		StringBuffer newValues = new StringBuffer();
		if (tempUserOrg.getAction() == ActionType.UPDATE.getId()) {
			UserOrganization userOrg = userOrgManager.findById(tempUserOrg.getOrgID());
			try {
				if (!userOrg.getOrgDesc().equals(tempUserOrg.getOrgDesc().toString())) {
					oldValues.append(Labels.getLabel(Commons.ORGANIZATION_DESCRIPTION))
						.append(Commons.COLON).append(userOrg.getOrgDesc())
						.append(Commons.NEW_LINE);
					newValues.append(Labels.getLabel(Commons.ORGANIZATION_DESCRIPTION))
						.append(Commons.COLON).append(tempUserOrg.getOrgDesc())
						.append(Commons.NEW_LINE);
				}
			}
			catch(NullPointerException e) {
				if(!StringUtils.isNullOrEmpty(userOrg.getOrgDesc())) {
					oldValues.append(Labels.getLabel(Commons.ORGANIZATION_DESCRIPTION)).append(Commons.COLON)
						.append(userOrg.getOrgDesc()).append(Commons.NEW_LINE);
					newValues.append(Labels.getLabel(Commons.ORGANIZATION_DESCRIPTION)).append(Commons.COLON)
						.append(Commons.NEW_LINE);
				}
				else if(!StringUtils.isNullOrEmpty(tempUserOrg.getOrgDesc())) {
					oldValues.append(Labels.getLabel(Commons.ORGANIZATION_DESCRIPTION)).append(Commons.COLON)
						.append(Commons.NEW_LINE);
					newValues.append(Labels.getLabel(Commons.ORGANIZATION_DESCRIPTION)).append(Commons.COLON)
						.append(tempUserOrg.getOrgDesc()).append(Commons.NEW_LINE);
				}
			}
			if (!userOrg.getDisposeAdjLimit().equals(tempUserOrg.getDisposeAdjLimit())) {
				oldValues.append(Labels.getLabel(Commons.DISPOSE_ADJUSTMENT_LIMIT))
						.append(Commons.COLON)
						.append(userOrg.getDisposeAdjLimit())
						.append(Commons.NEW_LINE);
				newValues.append(Labels.getLabel(Commons.DISPOSE_ADJUSTMENT_LIMIT))
						.append(Commons.COLON)
						.append(tempUserOrg.getDisposeAdjLimit())
						.append(Commons.NEW_LINE);
			}
			if (!userOrg.getCreditLimitC1().equals(tempUserOrg.getCreditLimitC1())) {
				oldValues.append(Labels.getLabel(Commons.C1_CREDIT_LIMIT))
						.append(Commons.COLON)
						.append(userOrg.getCreditLimitC1())
						.append(Commons.NEW_LINE);
				newValues.append(Labels.getLabel(Commons.C1_CREDIT_LIMIT))
						.append(Commons.COLON)
						.append(tempUserOrg.getCreditLimitC1())
						.append(Commons.NEW_LINE);
			}
			if (!userOrg.getCreditLimitC2().equals(tempUserOrg.getCreditLimitC2())) {
				oldValues.append(Labels.getLabel(Commons.C2_CREDIT_LIMIT))
						.append(Commons.COLON)
						.append(userOrg.getCreditLimitC2())
						.append(Commons.NEW_LINE);
				newValues.append(Labels.getLabel(Commons.C2_CREDIT_LIMIT))
						.append(Commons.COLON)
						.append(tempUserOrg.getCreditLimitC2())
						.append(Commons.NEW_LINE);
			}
			if (!userOrg.getChargeBackLimit().equals(tempUserOrg.getChargeBackLimit())) {
				oldValues.append(Labels.getLabel(Commons.CHARGE_BACK_LIMIT))
						.append(Commons.COLON)
						.append(userOrg.getChargeBackLimit())
						.append(Commons.NEW_LINE);
				newValues.append(Labels.getLabel(Commons.CHARGE_BACK_LIMIT))
						.append(Commons.COLON)
						.append(tempUserOrg.getChargeBackLimit())
						.append(Commons.NEW_LINE);
			}
			if (!userOrg.getRetrievalAmount().equals(tempUserOrg.getRetrievalAmount())) {
				oldValues.append(Labels.getLabel(Commons.RETRIEVAL_AMOUNT))
						.append(Commons.COLON)
						.append(userOrg.getRetrievalAmount())
						.append(Commons.NEW_LINE);
				newValues.append(Labels.getLabel(Commons.RETRIEVAL_AMOUNT))
						.append(Commons.COLON)
						.append(tempUserOrg.getRetrievalAmount())
						.append(Commons.NEW_LINE);
			}
			if (!userOrg.getFraudAmount().equals(tempUserOrg.getFraudAmount())) {
				oldValues.append(Labels.getLabel(Commons.FRAUD_AMOUNT))
						.append(Commons.COLON).append(userOrg.getFraudAmount())
						.append(Commons.NEW_LINE);
				newValues.append(Labels.getLabel(Commons.FRAUD_AMOUNT))
						.append(Commons.COLON)
						.append(tempUserOrg.getFraudAmount())
						.append(Commons.NEW_LINE);
			}
			if (!userOrg.getFeeAmount().equals(tempUserOrg.getFeeAmount())) {
				oldValues.append(Labels.getLabel(Commons.FEE_AMOUNT))
						.append(Commons.COLON).append(userOrg.getFeeAmount())
						.append(Commons.NEW_LINE);
				newValues.append(Labels.getLabel(Commons.FEE_AMOUNT))
						.append(Commons.COLON)
						.append(tempUserOrg.getFeeAmount())
						.append(Commons.NEW_LINE);
			}
			if (!userOrg.getPermCreditLimit().equals(tempUserOrg.getPermCreditLimit())) {
				oldValues.append(Labels.getLabel(Commons.PERMANENT_CREDIT_LIMIT))
						.append(Commons.COLON)
						.append(userOrg.getPermCreditLimit())
						.append(Commons.NEW_LINE);
				newValues.append(Labels.getLabel(Commons.PERMANENT_CREDIT_LIMIT))
						.append(Commons.COLON)
						.append(tempUserOrg.getPermCreditLimit())
						.append(Commons.NEW_LINE);
			}
			if (!userOrg.getTempCreditLimit().equals(tempUserOrg.getTempCreditLimit())) {
				oldValues.append(Labels.getLabel(Commons.TEMPORARY_CREDIT_LIMIT))
						.append(Commons.COLON)
						.append(userOrg.getTempCreditLimit())
						.append(Commons.NEW_LINE);
				newValues.append(Labels.getLabel(Commons.TEMPORARY_CREDIT_LIMIT))
						.append(Commons.COLON)
						.append(tempUserOrg.getTempCreditLimit())
						.append(Commons.NEW_LINE);
			}
			if (!userOrg.getFeeAdjLimit().equals(tempUserOrg.getFeeAdjLimit())) {
				oldValues.append(Labels.getLabel(Commons.FEE_ADJUSTMENT_LIMIT))
						.append(Commons.COLON).append(userOrg.getFeeAdjLimit())
						.append(Commons.NEW_LINE);
				newValues.append(Labels.getLabel(Commons.FEE_ADJUSTMENT_LIMIT))
						.append(Commons.COLON)
						.append(tempUserOrg.getFeeAdjLimit())
						.append(Commons.NEW_LINE);
			}
			if (!userOrg.getLateChargeAdjLimit().equals(tempUserOrg.getLateChargeAdjLimit())) {
				oldValues
						.append(Labels.getLabel(Commons.LATE_CHARGE_ADJUSTMENT_LIMIT))
						.append(Commons.COLON)
						.append(userOrg.getLateChargeAdjLimit())
						.append(Commons.NEW_LINE);
				newValues
						.append(Labels.getLabel(Commons.LATE_CHARGE_ADJUSTMENT_LIMIT))
						.append(Commons.COLON)
						.append(tempUserOrg.getLateChargeAdjLimit())
						.append(Commons.NEW_LINE);
			}
			if (!userOrg.getBalAdjLimit().equals(tempUserOrg.getBalAdjLimit())) {
				oldValues.append(Labels.getLabel(Commons.BALANCE_ADJUSTMENT_LIMIT))
						.append(Commons.COLON).append(userOrg.getBalAdjLimit())
						.append(Commons.NEW_LINE);
				newValues.append(Labels.getLabel(Commons.BALANCE_ADJUSTMENT_LIMIT))
						.append(Commons.COLON)
						.append(tempUserOrg.getBalAdjLimit())
						.append(Commons.NEW_LINE);
			}
			if (!userOrg.getTransactionLimit().equals(tempUserOrg.getTransactionLimit())) {
				oldValues.append(Labels.getLabel(Commons.TRANSACTION_LIMIT))
						.append(Commons.COLON)
						.append(userOrg.getTransactionLimit())
						.append(Commons.NEW_LINE);
				newValues.append(Labels.getLabel(Commons.TRANSACTION_LIMIT))
						.append(Commons.COLON)
						.append(tempUserOrg.getTransactionLimit())
						.append(Commons.NEW_LINE);
			}
			try {
				if (!userOrg.getTransactionCode().equals(tempUserOrg.getTransactionCode().toString())) {
					oldValues.append(Labels.getLabel(Commons.TRANSACTION_CODE))
						.append(Commons.COLON)
						.append(userOrg.getTransactionCode())
						.append(Commons.NEW_LINE);
					newValues.append(Labels.getLabel(Commons.TRANSACTION_CODE))
						.append(Commons.COLON)
						.append(tempUserOrg.getTransactionCode())
						.append(Commons.NEW_LINE);
				}
			}
			catch(NullPointerException e) {
				if(!StringUtils.isNullOrEmpty(userOrg.getTransactionCode())) {
					oldValues.append(Labels.getLabel(Commons.TRANSACTION_CODE)).append(Commons.COLON)
						.append(userOrg.getTransactionCode()).append(Commons.NEW_LINE);
					newValues.append(Labels.getLabel(Commons.TRANSACTION_CODE)).append(Commons.COLON)
						.append(Commons.NEW_LINE);
				}
				else if(!StringUtils.isNullOrEmpty(tempUserOrg.getTransactionCode())) {
					oldValues.append(Labels.getLabel(Commons.TRANSACTION_CODE)).append(Commons.COLON)
						.append(Commons.NEW_LINE);
					newValues.append(Labels.getLabel(Commons.TRANSACTION_CODE)).append(Commons.COLON)
						.append(tempUserOrg.getTransactionCode()).append(Commons.NEW_LINE);
				}
			}
		}

		// Construct Listcells
		StringBuffer sb = new StringBuffer(globalUtils.getGlobalPropValue(Commons.URL_AUTHORIZE_VIEW_USER_ORG));
		sb.append(Commons.URL_DELIMITER)
			.append(Commons.URL_PARAM_TMP_ID)
			.append(Commons.EQUAL_SIGN)
			.append(tempUserOrg.getOrgID());
		Toolbarbutton userOrgIdButton = new Toolbarbutton(tempUserOrg.getOrgID());
		userOrgIdButton.setHref(sb.toString());
		userOrgIdButton.setTooltiptext(globalUtils
				.getMessagePropValue(Commons.TOOLTIP_CLICK_TO_VIEW));

//		Toolbarbutton compareButton = new Toolbarbutton(Labels.getLabel(Commons.VIEW));
//		compareButton.setHref(sb.toString());
//		compareButton.setTooltiptext(globalUtils
//				.getMessagePropValue(Commons.TOOLTIP_CLICK_TO_VIEW));

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
		userOrgIdButton.setParent(userIdCell);
//		new Listcell(StatusType.getDesc(tempUserOrg.getStatus()))
//				.setParent(listItem);
//		new Listcell(ActionType.getDesc(tempUserOrg.getAction()))
//				.setParent(listItem);
//		if(tempUserOrg.getStatus() == StatusType.PENDING.getId()) {
//			new Listcell(PendingEntityStatusType.getDesc(tempUserOrg.getAction())).setParent(listItem);
//		}
//		else {
//			new Listcell(StatusType.getDesc(tempUserOrg.getStatus())).setParent(listItem);
//		}
		new Listcell(ActionType.getDesc(tempUserOrg.getAction())).setParent(listItem);
		new Listcell(tempUserOrg.getLastModifiedBy()).setParent(listItem);
		new Listcell(DateUtil.format(tempUserOrg.getDateLastModified())).setParent(listItem);
//		Double width = (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.22);
		Listcell oldValueCell = new Listcell();
		oldValueCell.setParent(listItem);
		Listcell newValueCell = new Listcell();
		newValueCell.setParent(listItem);
		
		if(tempUserOrg.getAction() == ActionType.UPDATE.getId()) {
			oldValuesText.setWidth("210px");
			oldValuesText.setParent(oldValueCell);
			newValuesText.setWidth("210px");
			newValuesText.setParent(newValueCell);
		}
//		Listcell compareCell = new Listcell();
//		compareCell.setParent(listItem);
//		compareButton.setParent(compareCell);
	}

}
