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
import com.mysql.jdbc.StringUtils;

/**
 * @author elvin.aquino
 *
 */
public class UserOrganizationAuthorizeEditVM extends GenericForwardComposer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AnnotateDataBinder binder;
	private GlobalUtils globalUtils = GlobalUtils.getGlobalUtilsInstance();
	SecurityUtils securityUtils = SecurityUtils.getSecurityUtilsInstance();
	private SecurityAdminUtils securityAdminUtils = SecurityAdminUtils.getSecurityAdminUtilsInstance();

	private UserOrganization userOrg;
	private TempUserOrganization tempUserOrg;
	private UserOrganizationManager userOrgManager = ServiceLocator.getUserOrganizationManager();
	private TempUserOrganizationManager tempUserOrgManager = ServiceLocator.getTempUserOrganizationManager();

	private Window userorganization_auth_edit;
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
	
	private String orgID;

	public void doBeforeComposeChildren(Component comp) throws Exception {
		super.doBeforeComposeChildren(comp);
		
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		btn_back_top.setHref(globalUtils.getGlobalPropValue(Commons.URL_AUTHORIZE_USER_ORG));
		btn_back_foot.setHref(globalUtils.getGlobalPropValue(Commons.URL_AUTHORIZE_USER_ORG));
		try {
			String info = (String) Executions.getCurrent().getSession().getAttribute(Commons.SA_LBLINFO);
			if(!StringUtils.isNullOrEmpty(info)) {
				lb_info.setValue(info);
				securityAdminUtils.setMsgStyle(img_info, lb_info, false);
				btn_back_top.setHref(globalUtils.getGlobalPropValue(Commons.URL_ADD_USER_ORG));
				btn_back_foot.setHref(globalUtils.getGlobalPropValue(Commons.URL_ADD_USER_ORG));
			}
		}
		catch(Exception e) {
			
		}
		finally {
			Executions.getCurrent().getSession().removeAttribute(Commons.SA_LBLINFO);
		}
		
		orgID = Executions.getCurrent().getParameter(Commons.URL_PARAM_TMP_ID);
		loadOrganizationDetails(orgID);
		manageButtons();
		comp.setAttribute(comp.getId() + "Control", this, true);

		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
	}
	
	public void onClick$btn_approve_top() throws InterruptedException {
		approveUserOrganization();
	}
	
	public void onClick$btn_approve_foot() throws InterruptedException {
		approveUserOrganization();
	}
	
	public void onClick$btn_reject_top() throws InterruptedException {
		rejectUserOrganization();
	}
	
	public void onClick$btn_reject_foot() throws InterruptedException {
		rejectUserOrganization();
	}

	public void onClick$btn_update_top() throws InterruptedException {
		enableFields();
	}
	
	public void onClick$btn_update_foot() throws InterruptedException {
		enableFields();
	}
	
	public void onClick$btn_cancel_top() throws InterruptedException {
		cancelUserOrganization();
	}
	
	public void onClick$btn_cancel_foot() throws InterruptedException {
		cancelUserOrganization();
	}
	
	public void onClick$btn_submit_top() throws InterruptedException {
		Messagebox.show(MessageFormat.format(globalUtils.getMessagePropValue(
			Commons.MSG_USER_ORG_UPDATE_PROMPT), tempUserOrg.getOrgID()),
			globalUtils.getMessagePropValue(Commons.MSG_HEADER_UPDATE), 
			Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
			new EventListener() {
				@Override
				public void onEvent(Event evt) throws Exception {
					switch (((Integer)evt.getData()).intValue()) {
			           case Messagebox.OK:
			        	   save();
			        	   disableFields();
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
		loadOrganizationDetails(orgID);
	}
	
	private void enableFields() {
		userorganization_auth_edit.setTitle(globalUtils.getGlobalPropValue(Commons.SCREEN_USER_ORG_UPDATE));
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
		btn_approve_top.setVisible(false);
		btn_reject_top.setVisible(false);
		btn_cancel_top.setVisible(false);
		btn_update_top.setVisible(false);
		btn_submit_top.setVisible(true);
		btn_reset_top.setVisible(true);
		btn_approve_foot.setVisible(false);
		btn_reject_foot.setVisible(false);
		btn_cancel_foot.setVisible(false);
		btn_update_foot.setVisible(false);
		btn_submit_foot.setVisible(true);
		btn_reset_foot.setVisible(true);
	}
	
	private void disableFields() {
		userorganization_auth_edit.setTitle(globalUtils.getGlobalPropValue(Commons.SCREEN_USER_ORG_AUTHORIZE));
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
	
	private void approveUserOrganization() throws InterruptedException {
		Messagebox.show(MessageFormat.format(globalUtils.getMessagePropValue(
			Commons.MSG_USER_ORG_APPROVE_PROMPT), tempUserOrg.getOrgID()),
			globalUtils.getMessagePropValue(Commons.MSG_HEADER_APPROVE), 
			Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
			new EventListener() {
				@Override
				public void onEvent(Event evt) throws Exception {
					switch (((Integer)evt.getData()).intValue()) {
			           case Messagebox.OK:
			        	   if(approveSelectedUserOrg(tempUserOrg)) {
			        		   lb_info.setValue(MessageFormat.format(globalUtils.getMessagePropValue(
			           				Commons.MSG_USER_ORG_APPROVE), tempUserOrg.getOrgID()));
			       				securityAdminUtils.setMsgStyle(img_info, lb_info, false);
//			           			Messagebox.show(MessageFormat.format(globalUtils.getMessagePropValue(
//			           				Commons.MSG_USER_ORG_APPROVE), tempUserOrg.getOrgID()),
//			           				globalUtils.getMessagePropValue(Commons.MSG_HEADER_APPROVE), 
//			           				Messagebox.OK, Messagebox.INFORMATION, new EventListener() {
//									
//									@Override
//									public void onEvent(Event evt) throws Exception {
//										if(((Integer)evt.getData()).intValue() == Messagebox.OK) {
//											Executions.sendRedirect(
//												globalUtils.getGlobalPropValue(Commons.URL_AUTHORIZE_USER_ORG));
//										}
//									}
//								});
			        	   }
			        	   else {
			        		   GlobalUtils.showMessage(globalUtils.getMessagePropValue(Commons.ERR_MSG_USER_ORG_APPROVE),
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
	
	private void rejectUserOrganization() throws InterruptedException {
		Messagebox.show(MessageFormat.format(globalUtils.getMessagePropValue(
			Commons.MSG_USER_ORG_REJECT_PROMPT), tempUserOrg.getOrgID()),
			globalUtils.getMessagePropValue(Commons.MSG_HEADER_REJECT), 
			Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
			new EventListener() {
				@Override
				public void onEvent(Event evt) throws Exception {
					switch (((Integer)evt.getData()).intValue()) {
			           case Messagebox.OK:
			        	   if(rejectSelectedUserOrg(tempUserOrg)) {
			        		   lb_info.setValue(MessageFormat.format(globalUtils.getMessagePropValue(
			           				Commons.MSG_USER_ORG_REJECT), tempUserOrg.getOrgID()));
			       				securityAdminUtils.setMsgStyle(img_info, lb_info, false);
//			           			Messagebox.show(MessageFormat.format(globalUtils.getMessagePropValue(
//			           				Commons.MSG_USER_ORG_REJECT), tempUserOrg.getOrgID()),
//			           				globalUtils.getMessagePropValue(Commons.MSG_HEADER_REJECT), 
//			           				Messagebox.OK, Messagebox.INFORMATION, new EventListener() {
//									
//									@Override
//									public void onEvent(Event evt) throws Exception {
//										if(((Integer)evt.getData()).intValue() == Messagebox.OK) {
//											Executions.sendRedirect(
//												globalUtils.getGlobalPropValue(Commons.URL_AUTHORIZE_USER_ORG));
//										}
//									}
//								});
			        	   }
			        	   else {
			        		   GlobalUtils.showMessage(globalUtils.getMessagePropValue(Commons.ERR_MSG_USER_ORG_REJECT),
			        				globalUtils.getMessagePropValue(Commons.MSG_HEADER_REJECT),
			        				Messagebox.OK, Messagebox.ERROR);
			        	   }
			           		break;
			           case Messagebox.CANCEL:
			        	   break;
					}
				}
		});
	}
	
	private void cancelUserOrganization() throws InterruptedException {
		Messagebox.show(MessageFormat.format(globalUtils.getMessagePropValue(
				Commons.MSG_USER_ORG_CANCEL_PROMPT), tempUserOrg.getOrgID()),
				globalUtils.getMessagePropValue(Commons.MSG_HEADER_CANCEL), 
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
				new EventListener() {
					@Override
					public void onEvent(Event evt) throws Exception {
						switch (((Integer)evt.getData()).intValue()) {
				           case Messagebox.OK:
				        	   if(cancelSelectedUserOrg(tempUserOrg)) {
				        		   lb_info.setValue(MessageFormat.format(globalUtils.getMessagePropValue(
				        				   Commons.MSG_USER_ORG_CANCEL), tempUserOrg.getOrgID()));
				       				securityAdminUtils.setMsgStyle(img_info, lb_info, false);
//				        		   Messagebox.show(MessageFormat.format(globalUtils.getMessagePropValue(
//				        				   Commons.MSG_USER_ORG_CANCEL), tempUserOrg.getOrgID()),
//				        				   globalUtils.getMessagePropValue(Commons.MSG_HEADER_CANCEL), 
//				        				   Messagebox.OK, Messagebox.INFORMATION, new EventListener() {
//										
//										@Override
//										public void onEvent(Event evt) throws Exception {
//											if(((Integer)evt.getData()).intValue() == Messagebox.OK) {
//												Executions.sendRedirect(
//													globalUtils.getGlobalPropValue(Commons.URL_AUTHORIZE_USER_ORG));
//											}
//										}
//									});
				        	   }
				        	   else {
				        		   GlobalUtils.showMessage(globalUtils.getMessagePropValue(Commons.ERR_MSG_USER_ORG_CANCEL),
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
	
	private boolean approveSelectedUserOrg(TempUserOrganization tempUserOrg) throws ParseException {
		boolean flag = false;
		int action = tempUserOrg.getAction();
		// Check if logged in user is the same as the maker. If yes, skip.. else proceed
		if(securityUtils.getUserName().equals(tempUserOrg.getLastModifiedBy())){
			// Do nothing
		}
		else {
			// Save or update user in the Master table
			tempUserOrg = tempUserOrgManager.findById(tempUserOrg.getOrgID());
			Date date = DateUtil.getCurrentDate();
			tempUserOrg.setApprovedBy(securityUtils.getUserName());
			tempUserOrg.setDateApproved(date);
			UserOrganization userOrg = new UserOrganization(tempUserOrg);
			userOrg.setApprovedBy(securityUtils.getUserName());
			userOrg.setDateApproved(date);
			if(action == ActionType.ADD.getId() || action == ActionType.UPDATE.getId()){
				userOrg.setStatus(StatusType.ACTIVE.getId());
			}
			else if(action == ActionType.DELETE.getId()) {
				userOrg.setStatus(StatusType.DELETED.getId());
			}
			// Update TEMP table to activate trigger for HISTORY
			// Delete record in the Temp table
			try {
				userOrgManager.saveOrUpdate(userOrg);
				tempUserOrg.setStatus(StatusType.APPROVED.getId());
				tempUserOrgManager.update(tempUserOrg);
				tempUserOrgManager.delete(tempUserOrg);
				flag = true;
			}
			catch(Exception e) {
				flag = false;
			}
		}
		return flag;
	}
	
	private boolean rejectSelectedUserOrg(TempUserOrganization tempUserOrg) throws ParseException {
		boolean flag = false;
		int action = tempUserOrg.getAction();
		
		// Check if logged in user is the same as the maker. If yes, skip.. else proceed
		if(securityUtils.getUserName().equals(tempUserOrg.getLastModifiedBy())){
			// Do nothing
		}
		else {
			Date date = DateUtil.getCurrentDate();
			tempUserOrg.setApprovedBy(securityUtils.getUserName());
			tempUserOrg.setDateApproved(date);
			// Revert Master status to active if update or delete
			if(action == ActionType.UPDATE.getId() || action == ActionType.DELETE.getId()) {
				try {
					UserOrganization org = userOrgManager.findById(tempUserOrg.getOrgID());
					org.setStatus(StatusType.ACTIVE.getId());
					org.setApprovedBy(securityUtils.getUserName());
					org.setDateApproved(date);
					flag = userOrgManager.update(org);
				}
				catch(Exception e) {
					flag = false;
				}
			}
			// Update TEMP table to activate trigger for HISTORY
			// Delete record in the Temp table
			try {
				tempUserOrg.setStatus(StatusType.REJECTED.getId());
				tempUserOrgManager.update(tempUserOrg);
				tempUserOrgManager.delete(tempUserOrg);
				flag = true;
			}
			catch(Exception e) {
				e.printStackTrace();
				flag = false;
			}
		}
		return flag;
	}
	
	private boolean cancelSelectedUserOrg(TempUserOrganization tempUserOrg) throws ParseException {
		boolean flag = false;
		int action = tempUserOrg.getAction();
		tempUserOrg = tempUserOrgManager.findById(tempUserOrg.getOrgID());
		
		// Check if logged in user is the same as the maker. If yes, skip.. else proceed
		if(securityUtils.getUserName().equals(tempUserOrg.getLastModifiedBy())){
			Date date = DateUtil.getCurrentDate();
			tempUserOrg.setApprovedBy(securityUtils.getUserName());
			tempUserOrg.setDateApproved(date);
			// Revert Master status to active if update or delete
			if(action == ActionType.UPDATE.getId() || action == ActionType.DELETE.getId()) {
				try {
					UserOrganization org = userOrgManager.findById(tempUserOrg.getOrgID());
					org.setStatus(StatusType.ACTIVE.getId());
					org.setApprovedBy(securityUtils.getUserName());
					org.setDateApproved(date);
					flag = userOrgManager.update(org);
				}
				catch(Exception e) {
					flag = false;
				}
			}
			// Update TEMP table to activate trigger for HISTORY
			// Delete record in the Temp table
			try {
				tempUserOrg.setStatus(StatusType.CANCELLED.getId());
				tempUserOrgManager.update(tempUserOrg);
				tempUserOrgManager.delete(tempUserOrg);
				flag = true;
			}
			catch(Exception e) {
				flag = false;
			}
		}
		else {
			// Do nothing
		}
		return flag;
	}
	
	private void save() {
		try {
			if(isFormValid()) {
//				tempUserOrg = new TempUserOrganization();
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
				tempUserOrg.setStatus(StatusType.PENDING.getId());
				try {
					userOrg = userOrgManager.findById(tempUserOrg.getOrgID());
					userOrg.setStatus(StatusType.PENDING.getId());
					userOrg.setAction(ActionType.UPDATE.getId());
					userOrgManager.saveOrUpdate(userOrg);
					tempUserOrg.setAction(ActionType.UPDATE.getId());
				}
				catch (NullPointerException e) {
					tempUserOrg.setAction(ActionType.ADD.getId());
				}
				catch(ObjectNotFoundException e) {
					tempUserOrg.setAction(ActionType.ADD.getId());
				}
				Date date = DateUtil.getCurrentDate();
				tempUserOrg.setLastModifiedBy(securityUtils.getUserName());
				tempUserOrg.setDateLastModified(date);
				tempUserOrgManager.saveOrUpdate(tempUserOrg);
				lb_info.setValue(MessageFormat.format(globalUtils.getMessagePropValue(
					Commons.MSG_USER_ORG_UPDATE), tempUserOrg.getOrgID()));
				securityAdminUtils.setMsgStyle(img_info, lb_info, false);
//				GlobalUtils.showMessage(MessageFormat.format(globalUtils.getMessagePropValue(
//					Commons.MSG_USER_ORG_UPDATE), tempUserOrg.getOrgID()),
//					globalUtils.getGlobalPropValue(Commons.SCREEN_USER_ORG_UPDATE),
//					Messagebox.OK, Messagebox.NONE);
				disableFields();
			}
		} catch (Exception e) {
			e.printStackTrace();
			GlobalUtils.showMessage(globalUtils.getMessagePropValue(Commons.ERR_MSG_USER_ORG_UPDATE),
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
//			else if (userOrgManager.isUserOrgExist(orgID) || tempUserOrgManager.isUserOrgExist(orgID)) {
//				tbx_org_id.setErrorMessage(globalUtils.getMessagePropValue(Commons.WARN_MSG_ID_NOT_UNIQUE));
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
			tempUserOrg = tempUserOrgManager.findById(orgID);

			tbx_org_id.setText(tempUserOrg.getOrgID());
			tbx_org_desc.setText(tempUserOrg.getOrgDesc());
			dbx_dispose_adj_lim.setText(tempUserOrg.getDisposeAdjLimit());
			dbx_cr_lim_c1.setText(tempUserOrg.getCreditLimitC1());
			dbx_cr_lim_c2.setText(tempUserOrg.getCreditLimitC2());
			dbx_charge_back_lim.setText(tempUserOrg.getChargeBackLimit());
			dbx_retrieval_amt.setText(tempUserOrg.getRetrievalAmount());
			dbx_fraud_amt.setText(tempUserOrg.getFraudAmount());
			dbx_fee_amt.setText(tempUserOrg.getFeeAmount());
			dbx_perm_cr_lim.setText(tempUserOrg.getPermCreditLimit());
			dbx_temp_cr_lim.setText(tempUserOrg.getTempCreditLimit());
			dbx_fee_adj_lim.setText(tempUserOrg.getFeeAdjLimit());
			dbx_late_charge_adj_lim.setText(tempUserOrg.getLateChargeAdjLimit());
			dbx_bal_adj_lim.setText(tempUserOrg.getBalAdjLimit());
			dbx_txn_lim.setText(tempUserOrg.getTransactionLimit());
			tbx_txn_code.setText(tempUserOrg.getTransactionCode());
		}
		catch(ObjectNotFoundException e) {
			Messagebox.show(MessageFormat.format(globalUtils.getMessagePropValue(
				Commons.ERR_MSG_USER_ORG_NOT_FOUND), orgID),
				globalUtils.getMessagePropValue(Commons.MSG_HEADER_ERROR), 
				Messagebox.OK, Messagebox.ERROR,
				new EventListener() {
					@Override
					public void onEvent(Event evt) throws Exception {
						Executions.sendRedirect(globalUtils.getGlobalPropValue(Commons.URL_AUTHORIZE_USER_ORG));
					}
				}
			);
		}
	}
	
	private void manageButtons() {
		// Approve, Reject: Visible when has checker and not last modified by
		// Update, Cancel: Visible when has maker and last modified by
		if(securityUtils.hasCheckerRole(globalUtils.getGlobalPropValue(Commons.URL_AUTHORIZE_VIEW_USER_ORG))) {
			if(tempUserOrg.getLastModifiedBy().equals(securityUtils.getUserName())) {
				btn_approve_top.setVisible(false);
				btn_reject_top.setVisible(false);
				btn_approve_foot.setVisible(false);
				btn_reject_foot.setVisible(false);
			}
			else {
				btn_approve_top.setVisible(true);
				btn_reject_top.setVisible(true);
				btn_approve_foot.setVisible(true);
				btn_reject_foot.setVisible(true);
			}
		}
		if(securityUtils.hasMakerRole(globalUtils.getGlobalPropValue(Commons.URL_AUTHORIZE_VIEW_USER_ORG))) {
			if(tempUserOrg.getLastModifiedBy().equals(securityUtils.getUserName())) {
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
}
