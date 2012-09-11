package com.isg.ifrend.view.control;

import java.text.MessageFormat;
import java.util.Date;

import org.springframework.dao.DataIntegrityViolationException;
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

import com.isg.ifrend.model.bean.ActionType;
import com.isg.ifrend.model.bean.StatusType;
import com.isg.ifrend.model.bean.TempUserOrganization;
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
public class UserOrganizationAddVM extends GenericForwardComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AnnotateDataBinder binder;
	private GlobalUtils globalUtils = GlobalUtils.getGlobalUtilsInstance();
	private SecurityUtils securityUtils = SecurityUtils.getSecurityUtilsInstance();
	private SecurityAdminUtils securityAdminUtils = SecurityAdminUtils.getSecurityAdminUtilsInstance();

	private TempUserOrganization userOrg;
	UserOrganizationManager userOrgManager = ServiceLocator.getUserOrganizationManager();
	private TempUserOrganizationManager tempUserOrgManager = ServiceLocator.getTempUserOrganizationManager();

	// private Combobox cbb_org_id;
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
	
	private Image img_info;
	private Label lb_info;
	
	private Button btn_back_top;
	private Button btn_back_foot;

	public void doBeforeComposeChildren(Component comp) throws Exception {
		super.doBeforeComposeChildren(comp);

	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		userOrg = new TempUserOrganization();
		btn_back_top.setHref(globalUtils.getGlobalPropValue(Commons.URL_SECURITY_ADMIN));
		btn_back_foot.setHref(globalUtils.getGlobalPropValue(Commons.URL_SECURITY_ADMIN));
		comp.setAttribute(comp.getId() + "Control", this, true);

		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
	}

	public void onClick$btn_done_top() throws InterruptedException {
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
	
	private void save() {
		try {
			if(isFormValid()) {
				userOrg.setOrgID(tbx_org_id.getText());
				userOrg.setOrgDesc(tbx_org_desc.getText());
				userOrg.setDisposeAdjLimit(dbx_dispose_adj_lim.getText());
				userOrg.setCreditLimitC1(dbx_cr_lim_c1.getText());
				userOrg.setCreditLimitC2(dbx_cr_lim_c2.getText());
				userOrg.setChargeBackLimit(dbx_charge_back_lim.getText());
				userOrg.setRetrievalAmount(dbx_retrieval_amt.getText());
				userOrg.setFraudAmount(dbx_fraud_amt.getText());
				userOrg.setFeeAmount(dbx_fee_amt.getText());
				userOrg.setPermCreditLimit(dbx_perm_cr_lim.getText());
				userOrg.setTempCreditLimit(dbx_temp_cr_lim.getText());
				userOrg.setFeeAdjLimit(dbx_fee_adj_lim.getText());
				userOrg.setLateChargeAdjLimit(dbx_late_charge_adj_lim.getText());
				userOrg.setBalAdjLimit(dbx_bal_adj_lim.getText());
				userOrg.setTransactionLimit(dbx_txn_lim.getText());
				userOrg.setTransactionCode(tbx_txn_code.getText());
				userOrg.setStatus(StatusType.PENDING.getId());
				userOrg.setAction(ActionType.ADD.getId());
				Date date = DateUtil.getCurrentDate();
				userOrg.setCreatedBy(securityUtils.getUserName());
				userOrg.setDateCreated(date);
				userOrg.setLastModifiedBy(securityUtils.getUserName());
				userOrg.setDateLastModified(date);
				tempUserOrgManager.save(userOrg);
				StringBuffer sb = new StringBuffer(globalUtils.getGlobalPropValue(Commons.URL_AUTHORIZE_VIEW_USER_ORG));
				sb.append(Commons.URL_DELIMITER);
				sb.append(Commons.URL_PARAM_TMP_ID);
				sb.append(Commons.EQUAL_SIGN);
				sb.append(userOrg.getOrgID());
				Executions.getCurrent().getSession().setAttribute(Commons.SA_LBLINFO,
						MessageFormat.format(globalUtils.getMessagePropValue(Commons.MSG_USER_ORG_ADD), userOrg.getOrgID()));
				Executions.sendRedirect(sb.toString());
//				Messagebox.show(MessageFormat.format(globalUtils.getMessagePropValue(
//					Commons.MSG_USER_ORG_ADD), userOrg.getOrgID()),
//					globalUtils.getGlobalPropValue(Commons.SCREEN_USER_ORG_ADD),
//					Messagebox.OK, Messagebox.NONE, new EventListener() {
//						@Override
//						public void onEvent(Event evt) throws Exception {
//							switch (((Integer)evt.getData()).intValue()) {
//							case Messagebox.OK:
//								Executions.sendRedirect(globalUtils.getGlobalPropValue(Commons.URL_ADD_USER_ORG));
//								break;
//							};
//						}
//				});
			}
			else {
				lb_info.setValue(globalUtils.getMessagePropValue(Commons.MSG_ERROR));
				securityAdminUtils.setMsgStyle(img_info, lb_info, true);
			}
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			e = (DataIntegrityViolationException) (GlobalUtils.getRootCauseException((Throwable) e));
			if (e.getMessage().contains("PK")) {
				lb_info.setValue(MessageFormat.format(globalUtils.getMessagePropValue(
						Commons.ERR_MSG_USER_ORG_EXIST), userOrg.getOrgID()));
				securityAdminUtils.setMsgStyle(img_info, lb_info, true);
//				GlobalUtils.showMessage(MessageFormat.format(globalUtils.getGlobalPropValue(
//					Commons.ERR_MSG_USER_ORG_EXIST), userOrg.getOrgID()),
//					globalUtils.getGlobalPropValue(Commons.MSG_HEADER_ERROR),
//					Messagebox.OK, Messagebox.ERROR);
			}
			else {
//				lb_info.setValue(globalUtils.getMessagePropValue(Commons.ERR_MSG_USER_ORG_ADD));
//				securityAdminUtils.setMsgStyle(img_info, lb_info, true);
				GlobalUtils.showMessage(globalUtils.getMessagePropValue(Commons.ERR_MSG_USER_ORG_ADD),
					globalUtils.getGlobalPropValue(Commons.MSG_HEADER_ERROR),
					Messagebox.OK, Messagebox.ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
//			lb_info.setValue(globalUtils.getMessagePropValue(Commons.ERR_MSG_USER_ORG_ADD));
//			securityAdminUtils.setMsgStyle(img_info, lb_info, true);
			GlobalUtils.showMessage(globalUtils.getMessagePropValue(Commons.ERR_MSG_USER_ORG_ADD),
				globalUtils.getGlobalPropValue(Commons.MSG_HEADER_ERROR),
				Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	public void onClick$btn_done_foot() throws InterruptedException {
		onClick$btn_done_top();
	}
	
	public void onClick$btn_reset_top() {
		tbx_org_id.setText("");
		tbx_org_desc.setText("");
		dbx_dispose_adj_lim.setText("0");
		dbx_cr_lim_c1.setText("0");
		dbx_cr_lim_c2.setText("0");
		dbx_charge_back_lim.setText("0");
		dbx_retrieval_amt.setText("0");
		dbx_fraud_amt.setText("0");
		dbx_fee_amt.setText("0");
		dbx_perm_cr_lim.setText("0");
		dbx_temp_cr_lim.setText("0");
		dbx_fee_adj_lim.setText("0");
		dbx_late_charge_adj_lim.setText("0");
		dbx_bal_adj_lim.setText("0");
		dbx_txn_lim.setText("0");
		tbx_txn_code.setText("");
	}
	
	public void onClick$btn_reset_foot() {
		onClick$btn_reset_top();
	}
	
	private boolean isFormValid() {
		boolean flag = true;
		try {
			String orgID = tbx_org_id.getText();
			if(orgID.isEmpty()) {
				tbx_org_id.setErrorMessage(globalUtils.getMessagePropValue(Commons.WARN_MSG_FIELD_EMPTY));
				flag = false;
			}
			else if (userOrgManager.isUserOrgExist(orgID) || tempUserOrgManager.isUserOrgExist(orgID)) {
				tbx_org_id.setErrorMessage(globalUtils.getMessagePropValue(Commons.WARN_MSG_ID_NOT_UNIQUE));
				flag = false;
			}
			else if(!orgID.matches(globalUtils.getGlobalPropValue(Commons.ID_FORMAT_PATTERN))) {
				tbx_org_id.setErrorMessage(globalUtils.getMessagePropValue(Commons.ERR_MSG_INVALID_ID_FORMAT));
				flag = false;
			}
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
			e.printStackTrace();
			return false;
		}
	}

}
