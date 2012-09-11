/**
 * 
 */
package com.isg.ifrend.view.control;

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.Date;

import org.hibernate.ObjectNotFoundException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.isg.ifrend.model.bean.ActionType;
import com.isg.ifrend.model.bean.StatusType;
import com.isg.ifrend.model.bean.TempUserOrganization;
import com.isg.ifrend.model.bean.UserOrganization;
import com.isg.ifrend.security.utils.SecurityUtils;
import com.isg.ifrend.service.ServiceLocator;
import com.isg.ifrend.service.TempUserOrganizationManager;
import com.isg.ifrend.service.UserOrganizationManager;
import com.isg.ifrend.utils.Commons;
import com.isg.ifrend.utils.DateUtil;
import com.isg.ifrend.utils.GlobalUtils;
import com.isg.ifrend.utils.SecurityAdminUtils;

/**
 * @author elvin.aquino
 *
 */
public class UserOrganizationViewEditVM extends GenericForwardComposer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AnnotateDataBinder binder;
	private GlobalUtils globalUtils = GlobalUtils.getGlobalUtilsInstance();
	private SecurityUtils securityUtils = SecurityUtils.getSecurityUtilsInstance();
	private SecurityAdminUtils securityAdminUtils = SecurityAdminUtils.getSecurityAdminUtilsInstance();

	private UserOrganization userOrg;
	private TempUserOrganization tempUserOrg;
	private UserOrganizationManager userOrgManager = ServiceLocator.getUserOrganizationManager();
	private TempUserOrganizationManager tempUserOrgManager = ServiceLocator.getTempUserOrganizationManager();

	private Window userprofile_view_edit;
	private Textbox tbx_org_id;
	private Textbox tbx_org_desc;
	private Decimalbox dbx_dispose_adj_lim;
	private Decimalbox dbx_cr_lim_c1;
	private Decimalbox dbx_cr_lim_c2;
	private Decimalbox dbx_charge_back_lim;
	private Decimalbox dbx_retrieval_amt;
	private Decimalbox dbx_fraud_amt;
	private Decimalbox dbx_fee_amt;
	private Decimalbox dbx_perm_cr_lim;
	private Decimalbox dbx_temp_cr_lim;
	private Decimalbox dbx_fee_adj_lim;
	private Decimalbox dbx_late_charge_adj_lim;
	private Decimalbox dbx_bal_adj_lim;
	private Decimalbox dbx_txn_lim;
	private Textbox tbx_txn_code;
	private Label lb_info;
	private Image img_info;
	
	private Button btn_update_top;
	private Button btn_submit_top;
	private Button btn_reset_top;
	private Button btn_delete_top;
	private Button btn_back_top;
	
	private Button btn_update_foot;
	private Button btn_submit_foot;
	private Button btn_reset_foot;
	private Button btn_delete_foot;
	private Button btn_back_foot;
	
	private String orgID;

	public void doBeforeComposeChildren(Component comp) throws Exception {
		super.doBeforeComposeChildren(comp);

	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		orgID = Executions.getCurrent().getParameter(Commons.URL_PARAM_MST_ID);
		userprofile_view_edit.setTitle(globalUtils.getGlobalPropValue(Commons.SCREEN_USER_ORG_VIEW));
		loadOrganizationDetails(orgID);
		manageButtons();
		btn_back_top.setHref(globalUtils.getGlobalPropValue(Commons.URL_SEARCH_USER_ORG));
		btn_back_foot.setHref(globalUtils.getGlobalPropValue(Commons.URL_SEARCH_USER_ORG));
		
		comp.setAttribute(comp.getId() + "Control", this, true);

		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
	}

	public void onClick$btn_update_top() throws InterruptedException {
		if(userOrg.getStatus() == StatusType.ACTIVE.getId()) {
			enableFields();
		}
		else {
			lb_info.setValue(MessageFormat.format(globalUtils.getMessagePropValue(
					Commons.ERR_MSG_USER_ORG_UPDATE_NOT_ALLOWED), userOrg.getOrgID()));
			securityAdminUtils.setMsgStyle(img_info, lb_info, true);
//			Messagebox.show(MessageFormat.format(globalUtils.getMessagePropValue(
//					Commons.ERR_MSG_USER_ORG_UPDATE_NOT_ALLOWED), userOrg.getOrgID()),
//					globalUtils.getMessagePropValue(Commons.MSG_HEADER_ERROR), 
//					Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	public void onClick$btn_update_foot() throws InterruptedException {
		onClick$btn_update_top();
	}
	
	public void onClick$btn_delete_top() throws InterruptedException {
		if(userOrg.getStatus() == StatusType.ACTIVE.getId()) {
			Messagebox.show(MessageFormat.format(globalUtils.getMessagePropValue(
				Commons.MSG_USER_ORG_DELETE_PROMPT), userOrg.getOrgID()),
				globalUtils.getMessagePropValue(Commons.MSG_HEADER_DELETE), 
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new EventListener() {
					
					@Override
					public void onEvent(Event evt) throws Exception {
						switch (((Integer)evt.getData()).intValue()) {
			            case Messagebox.OK:
							if(deleteSelectedUserOrg(userOrg)) {
								lb_info.setValue(MessageFormat.format(globalUtils.getMessagePropValue(
		            				Commons.MSG_USER_ORG_DELETE), userOrg.getOrgID()));
								securityAdminUtils.setMsgStyle(img_info, lb_info, false);
//								GlobalUtils.showMessage(MessageFormat.format(globalUtils.getMessagePropValue(
//		            				Commons.MSG_USER_ORG_DELETE_MULTI), userOrg.getOrgID()),
//		        					globalUtils.getMessagePropValue(Commons.MSG_HEADER_DELETE),
//		        					Messagebox.OK, Messagebox.INFORMATION);
							}
			            	break;
			            case Messagebox.CANCEL:
			            	// Do nothing
			            	break;
			            }
					}
				});
		}
		else {
			lb_info.setValue(MessageFormat.format(globalUtils.getMessagePropValue(
					Commons.ERR_MSG_USER_ORG_DELETE_NOT_ALLOWED), userOrg.getOrgID()));
			securityAdminUtils.setMsgStyle(img_info, lb_info, true);
//			Messagebox.show(MessageFormat.format(globalUtils.getMessagePropValue(
//					Commons.ERR_MSG_USER_ORG_DELETE_NOT_ALLOWED), userOrg.getOrgID()),
//					globalUtils.getMessagePropValue(Commons.MSG_HEADER_ERROR), 
//					Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	public void onClick$btn_delete_foot() throws InterruptedException {
		onClick$btn_delete_top();
	}
	
	public void onClick$btn_submit_top() throws InterruptedException {
		Messagebox.show(MessageFormat.format(globalUtils.getMessagePropValue(
				Commons.MSG_USER_ORG_UPDATE_PROMPT), userOrg.getOrgID()),
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
	
	public void onClick$btn_submit_foot() throws InterruptedException {
		onClick$btn_submit_top();
	}
	
	public void onClick$btn_reset_top() throws InterruptedException {
		loadOrganizationDetails(orgID);
	}
	
	public void onClick$btn_reset_foot() throws InterruptedException {
		onClick$btn_reset_top();
	}
	
	private void enableFields() {
		userprofile_view_edit.setTitle(globalUtils.getGlobalPropValue(Commons.SCREEN_USER_ORG_UPDATE));
//		tbx_org_id.setReadonly(false);
		tbx_org_desc.setReadonly(false);
		dbx_dispose_adj_lim.setReadonly(false);
		dbx_cr_lim_c1.setReadonly(false);
		dbx_cr_lim_c2.setReadonly(false);
		dbx_charge_back_lim.setReadonly(false);
		dbx_retrieval_amt.setReadonly(false);
		dbx_fraud_amt.setReadonly(false);
		dbx_fee_amt.setReadonly(false);
		dbx_perm_cr_lim.setReadonly(false);
		dbx_temp_cr_lim.setReadonly(false);
		dbx_fee_adj_lim.setReadonly(false);
		dbx_late_charge_adj_lim.setReadonly(false);
		dbx_bal_adj_lim.setReadonly(false);
		dbx_txn_lim.setReadonly(false);
		tbx_txn_code.setReadonly(false);
		btn_update_top.setVisible(false);
		btn_delete_top.setVisible(false);
		btn_submit_top.setVisible(true);
		btn_reset_top.setVisible(true);
		btn_update_foot.setVisible(false);
		btn_delete_foot.setVisible(false);
		btn_submit_foot.setVisible(true);
		btn_reset_foot.setVisible(true);
	}
	
	private void disableFields() {
		userprofile_view_edit.setTitle(globalUtils.getGlobalPropValue(Commons.SCREEN_USER_ORG_VIEW));
//		tbx_org_id.setReadonly(true);
		tbx_org_desc.setReadonly(true);
		dbx_dispose_adj_lim.setReadonly(true);
		dbx_cr_lim_c1.setReadonly(true);
		dbx_cr_lim_c2.setReadonly(true);
		dbx_charge_back_lim.setReadonly(true);
		dbx_retrieval_amt.setReadonly(true);
		dbx_fraud_amt.setReadonly(true);
		dbx_fee_amt.setReadonly(true);
		dbx_perm_cr_lim.setReadonly(true);
		dbx_temp_cr_lim.setReadonly(true);
		dbx_fee_adj_lim.setReadonly(true);
		dbx_late_charge_adj_lim.setReadonly(true);
		dbx_bal_adj_lim.setReadonly(true);
		dbx_txn_lim.setReadonly(true);
		tbx_txn_code.setReadonly(true);
		btn_submit_top.setVisible(false);
		btn_reset_top.setVisible(false);
		btn_submit_foot.setVisible(false);
		btn_reset_foot.setVisible(false);
		manageButtons();
	}
	
	public void save() {
		try {
			if(isFormValid()) {
				Date date = DateUtil.getCurrentDate();
				userOrg.setLastModifiedBy(securityUtils.getUserName());
				userOrg.setDateLastModified(date);
				userOrg.setStatus(StatusType.PENDING.getId());
				userOrg.setAction(ActionType.UPDATE.getId());
				tempUserOrg = new TempUserOrganization(userOrg);
				tempUserOrg.setOrgID(tbx_org_id.getText());
				tempUserOrg.setOrgDesc(tbx_org_desc.getText());
				tempUserOrg.setDisposeAdjLimit(dbx_dispose_adj_lim.getText());
				tempUserOrg.setCreditLimitC1(dbx_cr_lim_c1.getText());
				tempUserOrg.setCreditLimitC2(dbx_cr_lim_c2.getText());
				tempUserOrg.setChargeBackLimit(dbx_charge_back_lim.getText());
				tempUserOrg.setRetrievalAmount(dbx_retrieval_amt.getText());
				tempUserOrg.setFraudAmount(dbx_fraud_amt.getText());
				tempUserOrg.setFeeAmount(dbx_fee_amt.getText());
				tempUserOrg.setPermCreditLimit(dbx_perm_cr_lim.getText());
				tempUserOrg.setTempCreditLimit(dbx_temp_cr_lim.getText());
				tempUserOrg.setFeeAdjLimit(dbx_fee_adj_lim.getText());
				tempUserOrg.setLateChargeAdjLimit(dbx_late_charge_adj_lim.getText());
				tempUserOrg.setBalAdjLimit(dbx_bal_adj_lim.getText());
				tempUserOrg.setTransactionLimit(dbx_txn_lim.getText());
				tempUserOrg.setTransactionCode(tbx_txn_code.getText());
				userOrgManager.update(userOrg);
				tempUserOrgManager.saveOrUpdate(tempUserOrg);
				lb_info.setValue(MessageFormat.format(globalUtils.getMessagePropValue(
					Commons.MSG_USER_ORG_UPDATE), userOrg.getOrgID()));
				securityAdminUtils.setMsgStyle(img_info, lb_info, false);
//				GlobalUtils.showMessage(MessageFormat.format(globalUtils.getMessagePropValue(
//					Commons.MSG_USER_ORG_UPDATE), userOrg.getOrgID()),
//					globalUtils.getGlobalPropValue(Commons.SCREEN_USER_ORG_UPDATE),
//					Messagebox.OK, Messagebox.NONE);
//				loadOrganizationDetails(orgID);
				disableFields();
			}
			else {
				lb_info.setValue(globalUtils.getMessagePropValue(Commons.MSG_ERROR));
				securityAdminUtils.setMsgStyle(img_info, lb_info, true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			GlobalUtils.showMessage(globalUtils.getMessagePropValue(Commons.ERR_MSG_USER_ORG_ADD),
					globalUtils.getMessagePropValue(Commons.MSG_HEADER_ERROR),
					Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	private boolean isFormValid() {
		boolean flag = true;
		try {
//			String orgID = tbx_org_id.getText();
//			if(orgID.isEmpty()) {
//				tbx_org_id.setErrorMessage(globalUtils.getMessagePropValue(Commons.WARN_MSG_FIELD_EMPTY));
//				flag = false;
//			}
			if(dbx_dispose_adj_lim.getValue().doubleValue() < 0) {
				dbx_dispose_adj_lim.setErrorMessage(globalUtils.getMessagePropValue(Commons.WARN_MSG_VALUE_NEGATIVE));
				flag = false;
			}
			if(dbx_cr_lim_c1.getValue().doubleValue() < 0) {
				dbx_cr_lim_c1.setErrorMessage(globalUtils.getMessagePropValue(Commons.WARN_MSG_VALUE_NEGATIVE));
				flag = false;
			}
			if(dbx_cr_lim_c2.getValue().doubleValue() < 0) {
				dbx_cr_lim_c2.setErrorMessage(globalUtils.getMessagePropValue(Commons.WARN_MSG_VALUE_NEGATIVE));
				flag = false;
			}
			if(dbx_charge_back_lim.getValue().doubleValue() < 0) {
				dbx_charge_back_lim.setErrorMessage(globalUtils.getMessagePropValue(Commons.WARN_MSG_VALUE_NEGATIVE));
				flag = false;
			}
			if(dbx_retrieval_amt.getValue().doubleValue() < 0) {
				dbx_retrieval_amt.setErrorMessage(globalUtils.getMessagePropValue(Commons.WARN_MSG_VALUE_NEGATIVE));
				flag = false;
			}
			if(dbx_fraud_amt.getValue().doubleValue() < 0) {
				dbx_fraud_amt.setErrorMessage(globalUtils.getMessagePropValue(Commons.WARN_MSG_VALUE_NEGATIVE));
				flag = false;
			}
			if(dbx_fee_amt.getValue().doubleValue() < 0) {
				dbx_fee_amt.setErrorMessage(globalUtils.getMessagePropValue(Commons.WARN_MSG_VALUE_NEGATIVE));
				flag = false;
			}
			if(dbx_perm_cr_lim.getValue().doubleValue() < 0) {
				dbx_perm_cr_lim.setErrorMessage(globalUtils.getMessagePropValue(Commons.WARN_MSG_VALUE_NEGATIVE));
				flag = false;
			}
			if(dbx_temp_cr_lim.getValue().doubleValue() < 0) {
				dbx_temp_cr_lim.setErrorMessage(globalUtils.getMessagePropValue(Commons.WARN_MSG_VALUE_NEGATIVE));
				flag = false;
			}
			if(dbx_fee_adj_lim.getValue().doubleValue() < 0) {
				dbx_fee_adj_lim.setErrorMessage(globalUtils.getMessagePropValue(Commons.WARN_MSG_VALUE_NEGATIVE));
				flag = false;
			}
			if(dbx_late_charge_adj_lim.getValue().doubleValue() < 0) {
				dbx_late_charge_adj_lim.setErrorMessage(globalUtils.getMessagePropValue(Commons.WARN_MSG_VALUE_NEGATIVE));
				flag = false;
			}
			if(dbx_bal_adj_lim.getValue().doubleValue() < 0) {
				dbx_bal_adj_lim.setErrorMessage(globalUtils.getMessagePropValue(Commons.WARN_MSG_VALUE_NEGATIVE));
				flag = false;
			}
			if(dbx_txn_lim.getValue().doubleValue() < 0) {
				dbx_txn_lim.setErrorMessage(globalUtils.getMessagePropValue(Commons.WARN_MSG_VALUE_NEGATIVE));
				flag = false;
			}
			return flag;
		}
		catch(Exception e) {
			return false;
		}
	}
	
	private void loadOrganizationDetails(String orgID) throws InterruptedException {
		try {
			userOrg = userOrgManager.findById(orgID);

			tbx_org_id.setText(userOrg.getOrgID());
			tbx_org_desc.setText(userOrg.getOrgDesc());
			dbx_dispose_adj_lim.setText(userOrg.getDisposeAdjLimit());
			dbx_cr_lim_c1.setText(userOrg.getCreditLimitC1());
			dbx_cr_lim_c2.setText(userOrg.getCreditLimitC2());
			dbx_charge_back_lim.setText(userOrg.getChargeBackLimit());
			dbx_retrieval_amt.setText(userOrg.getRetrievalAmount());
			dbx_fraud_amt.setText(userOrg.getFraudAmount());
			dbx_fee_amt.setText(userOrg.getFeeAmount());
			dbx_perm_cr_lim.setText(userOrg.getPermCreditLimit());
			dbx_temp_cr_lim.setText(userOrg.getTempCreditLimit());
			dbx_fee_adj_lim.setText(userOrg.getFeeAdjLimit());
			dbx_late_charge_adj_lim.setText(userOrg.getLateChargeAdjLimit());
			dbx_bal_adj_lim.setText(userOrg.getBalAdjLimit());
			dbx_txn_lim.setText(userOrg.getTransactionLimit());
			tbx_txn_code.setText(userOrg.getTransactionCode());
		}
		catch(ObjectNotFoundException e) {
			Messagebox.show(MessageFormat.format(globalUtils.getMessagePropValue(
				Commons.ERR_MSG_USER_ORG_NOT_FOUND), orgID),
				globalUtils.getMessagePropValue(Commons.MSG_HEADER_ERROR), 
				Messagebox.OK, Messagebox.ERROR,
				new EventListener() {
					@Override
					public void onEvent(Event evt) throws Exception {
						Executions.sendRedirect(globalUtils.getGlobalPropValue(Commons.URL_SEARCH_USER_ORG));
					}
				}
			);
		}
	}
	
	public boolean deleteSelectedUserOrg(UserOrganization userOrg) throws ParseException {
//		UserOrganization userOrg = userOrgManager.findById(userOrgID);
		if (userOrg.getStatus() == StatusType.PENDING.getId()) {
			return false;
		}
		else {
			userOrg.setStatus(StatusType.PENDING.getId());
			userOrg.setAction(ActionType.DELETE.getId());
			Date date = DateUtil.getCurrentDate();
			userOrg.setLastModifiedBy(securityUtils.getUserName());
			userOrg.setDateLastModified(date);
			if(userOrgManager.update(userOrg)) {
				TempUserOrganization tempUserOrg = new TempUserOrganization(userOrg);
				tempUserOrg.setStatus(StatusType.PENDING.getId());
				tempUserOrg.setAction(ActionType.DELETE.getId());
				tempUserOrg.setLastModifiedBy(securityUtils.getUserName());
				tempUserOrg.setDateLastModified(date);
				return tempUserOrgManager.save(tempUserOrg);
			}
			GlobalUtils.showMessage(globalUtils.getMessagePropValue(Commons.ERR_MSG_USER_ORG_DELETE),
					globalUtils.getMessagePropValue(Commons.MSG_HEADER_ERROR),
					Messagebox.OK, Messagebox.ERROR);
			return false;
		}
	}
	
	private void manageButtons() {
		if(userOrg.getStatus() == StatusType.ACTIVE.getId() &&
				securityUtils.hasMakerRole(globalUtils.getGlobalPropValue(Commons.URL_UPDATE_USER_ORG))) {
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
}
