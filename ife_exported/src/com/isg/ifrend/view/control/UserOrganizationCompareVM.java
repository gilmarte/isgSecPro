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
import com.isg.ifrend.model.bean.HistUserOrganization;
import com.isg.ifrend.model.bean.StatusType;
import com.isg.ifrend.model.bean.UserOrganization;
import com.isg.ifrend.service.HistUserOrganizationManager;
import com.isg.ifrend.service.ServiceLocator;
import com.isg.ifrend.service.UserOrganizationManager;
import com.isg.ifrend.utils.Commons;
import com.isg.ifrend.utils.GlobalUtils;

/**
 * @author elvin.aquino
 *
 */

public class UserOrganizationCompareVM extends GenericForwardComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	AnnotateDataBinder binder;

	@SuppressWarnings("unused")
	private Window userOrganizationComparePage;
	private Label lbl_temp_org_id;
	private Label lbl_temp_org_desc;
	private Label lbl_temp_dispose_adj_lim;
	private Label lbl_temp_cr_lim_c1;
	private Label lbl_temp_cr_lim_c2;
	private Label lbl_temp_charge_back_lim;
	private Label lbl_temp_retrieval_amt;
	private Label lbl_temp_fraud_amt;
	private Label lbl_temp_fee_amt;
	private Label lbl_temp_perm_cr_lim;
	private Label lbl_temp_temp_cr_lim;
	private Label lbl_temp_fee_adj_lim;
	private Label lbl_temp_late_charge_adj_lim;
	private Label lbl_temp_bal_adj_lim;
	private Label lbl_temp_txn_lim;
	private Label lbl_temp_txn_code;
	
	private Label lbl_mst_org_id;
	private Label lbl_mst_org_desc;
	private Label lbl_mst_dispose_adj_lim;
	private Label lbl_mst_cr_lim_c1;
	private Label lbl_mst_cr_lim_c2;
	private Label lbl_mst_charge_back_lim;
	private Label lbl_mst_retrieval_amt;
	private Label lbl_mst_fraud_amt;
	private Label lbl_mst_fee_amt;
	private Label lbl_mst_perm_cr_lim;
	private Label lbl_mst_temp_cr_lim;
	private Label lbl_mst_fee_adj_lim;
	private Label lbl_mst_late_charge_adj_lim;
	private Label lbl_mst_bal_adj_lim;
	private Label lbl_mst_txn_lim;
	private Label lbl_mst_txn_code;
	
	private Button btn_back;
	
	private UserOrganizationManager userOrgManager = ServiceLocator.getUserOrganizationManager();
	private HistUserOrganizationManager histUserOrgManager = ServiceLocator.getHistUserOrganizationManager();
	private GlobalUtils globalUtils = GlobalUtils.getGlobalUtilsInstance();
	private String style = globalUtils.getGlobalPropValue(Commons.STYLE_COMPARE);  
	
	
	private HistUserOrganization histUserOrg;
	private UserOrganization userOrg;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		binder = new AnnotateDataBinder();

		String historyIndex = Executions.getCurrent().getParameter("id");
		try {
			histUserOrg = histUserOrgManager.findByIndex(Integer.parseInt(historyIndex));
			int histStatus = histUserOrg.getStatus(); 
			if (!((histStatus == StatusType.PENDING.getId() || histStatus == StatusType.CANCELLED.getId() ||
					histStatus == StatusType.REJECTED.getId()) &&
					histUserOrg.getAction() == ActionType.ADD.getId())) {
				userOrg = userOrgManager.findById(histUserOrg.getOrgID());
			}
			else {
				userOrg = new UserOrganization();
			}
			compareUserOrgFields();
			btn_back.setHref(globalUtils.getGlobalPropValue(Commons.URL_HISTORY_USER_ORG));
		}
		catch(ObjectNotFoundException e) {
			Messagebox.show(globalUtils.getMessagePropValue(Commons.ERR_MSG_USER_ORG_HISTORY_NOT_FOUND),
				globalUtils.getMessagePropValue(Commons.MSG_HEADER_ERROR), 
				Messagebox.OK, Messagebox.ERROR,
				new EventListener() {
					@Override
					public void onEvent(Event evt) throws Exception {
						Executions.sendRedirect(globalUtils.getGlobalPropValue(Commons.URL_HISTORY_USER_ORG));
					}
				}
			);
		}
		comp.setAttribute(comp.getId() + "Control", this, true);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();

	}

	private void compareUserOrgFields() {
		StringBuffer sb = new StringBuffer();
		
		if(histUserOrg.getStatus() == StatusType.PENDING.getId() && histUserOrg.getAction() == ActionType.DELETE.getId()) {
			lbl_temp_org_id.setValue(histUserOrg.getOrgID());
			sb.setLength(0);
			sb.append(StatusType.PENDING.getDesc());
			sb.append(" ");
			sb.append(ActionType.getDesc(histUserOrg.getAction()));
		}
		else {
			lbl_temp_org_id.setValue(histUserOrg.getOrgID());
			lbl_temp_org_desc.setValue(histUserOrg.getOrgDesc());
			lbl_temp_dispose_adj_lim.setValue(histUserOrg.getDisposeAdjLimit());
			lbl_temp_cr_lim_c1.setValue(histUserOrg.getCreditLimitC1());
			lbl_temp_cr_lim_c2.setValue(histUserOrg.getCreditLimitC2());
			lbl_temp_charge_back_lim.setValue(histUserOrg.getChargeBackLimit());
			lbl_temp_retrieval_amt.setValue(histUserOrg.getRetrievalAmount());
			lbl_temp_fraud_amt.setValue(histUserOrg.getFraudAmount());
			lbl_temp_fee_amt.setValue(histUserOrg.getFeeAmount());
			lbl_temp_perm_cr_lim.setValue(histUserOrg.getPermCreditLimit());
			lbl_temp_temp_cr_lim.setValue(histUserOrg.getTempCreditLimit());
			lbl_temp_fee_adj_lim.setValue(histUserOrg.getFeeAdjLimit());
			lbl_temp_late_charge_adj_lim.setValue(histUserOrg.getLateChargeAdjLimit());
			lbl_temp_bal_adj_lim.setValue(histUserOrg.getBalAdjLimit());
			lbl_temp_txn_lim.setValue(histUserOrg.getTransactionLimit());
			lbl_temp_txn_code.setValue(histUserOrg.getTransactionCode());
		}
		
		try {
			lbl_mst_org_id.setValue(userOrg.getOrgID());
			lbl_mst_org_desc.setValue(userOrg.getOrgDesc());
			lbl_mst_dispose_adj_lim.setValue(userOrg.getDisposeAdjLimit());
			lbl_mst_cr_lim_c1.setValue(userOrg.getCreditLimitC1());
			lbl_mst_cr_lim_c2.setValue(userOrg.getCreditLimitC2());
			lbl_mst_charge_back_lim.setValue(userOrg.getChargeBackLimit());
			lbl_mst_retrieval_amt.setValue(userOrg.getRetrievalAmount());
			lbl_mst_fraud_amt.setValue(userOrg.getFraudAmount());
			lbl_mst_fee_amt.setValue(userOrg.getFeeAmount());
			lbl_mst_perm_cr_lim.setValue(userOrg.getPermCreditLimit());
			lbl_mst_temp_cr_lim.setValue(userOrg.getTempCreditLimit());
			lbl_mst_fee_adj_lim.setValue(userOrg.getFeeAdjLimit());
			lbl_mst_late_charge_adj_lim.setValue(userOrg.getLateChargeAdjLimit());
			lbl_mst_bal_adj_lim.setValue(userOrg.getBalAdjLimit());
			lbl_mst_txn_lim.setValue(userOrg.getTransactionLimit());
			lbl_mst_txn_code.setValue(userOrg.getTransactionCode());
		}
		catch(NullPointerException e) {
			
		}
		
		if(!lbl_temp_org_desc.getValue().equals(lbl_mst_org_desc.getValue())) {
			lbl_temp_org_desc.setStyle(style);
			lbl_mst_org_desc.setStyle(style);
		}
		if(!lbl_temp_dispose_adj_lim.getValue().equals(lbl_mst_dispose_adj_lim.getValue())) {
			lbl_temp_dispose_adj_lim.setStyle(style);
			lbl_mst_dispose_adj_lim.setStyle(style);
		}
		if(!lbl_temp_cr_lim_c1.getValue().equals(lbl_mst_cr_lim_c1.getValue())) {
			lbl_temp_cr_lim_c1.setStyle(style);
			lbl_mst_cr_lim_c1.setStyle(style);
		}
		if(!lbl_temp_cr_lim_c2.getValue().equals(lbl_mst_cr_lim_c2.getValue())) {
			lbl_temp_cr_lim_c2.setStyle(style);
			lbl_mst_cr_lim_c2.setStyle(style);
		}
		if(!lbl_temp_charge_back_lim.getValue().equals(lbl_mst_charge_back_lim.getValue())) {
			lbl_temp_charge_back_lim.setStyle(style);
			lbl_mst_charge_back_lim.setStyle(style);
		}
		if(!lbl_temp_retrieval_amt.getValue().equals(lbl_mst_retrieval_amt.getValue())) {
			lbl_temp_retrieval_amt.setStyle(style);
			lbl_mst_retrieval_amt.setStyle(style);
		}
		if(!lbl_temp_fraud_amt.getValue().equals(lbl_mst_fraud_amt.getValue())) {
			lbl_temp_fraud_amt.setStyle(style);
			lbl_mst_fraud_amt.setStyle(style);
		}
		if(!lbl_temp_fee_amt.getValue().equals(lbl_mst_fee_amt.getValue())) {
			lbl_temp_fee_amt.setStyle(style);
			lbl_mst_fee_amt.setStyle(style);
		}
		if(!lbl_temp_perm_cr_lim.getValue().equals(lbl_mst_perm_cr_lim.getValue())) {
			lbl_temp_perm_cr_lim.setStyle(style);
			lbl_mst_perm_cr_lim.setStyle(style);
		}
		if(!lbl_temp_temp_cr_lim.getValue().equals(lbl_mst_temp_cr_lim.getValue())) {
			lbl_temp_temp_cr_lim.setStyle(style);
			lbl_mst_temp_cr_lim.setStyle(style);
		}
		if(!lbl_temp_fee_adj_lim.getValue().equals(lbl_mst_fee_adj_lim.getValue())) {
			lbl_temp_fee_adj_lim.setStyle(style);
			lbl_mst_fee_adj_lim.setStyle(style);
		}
		if(!lbl_temp_late_charge_adj_lim.getValue().equals(lbl_mst_late_charge_adj_lim.getValue())) {
			lbl_temp_late_charge_adj_lim.setStyle(style);
			lbl_mst_late_charge_adj_lim.setStyle(style);
		}
		if(!lbl_temp_bal_adj_lim.getValue().equals(lbl_mst_bal_adj_lim.getValue())) {
			lbl_temp_bal_adj_lim.setStyle(style);
			lbl_mst_bal_adj_lim.setStyle(style);
		}
		if(!lbl_temp_txn_lim.getValue().equals(lbl_mst_txn_lim.getValue())) {
			lbl_temp_txn_lim.setStyle(style);
			lbl_mst_txn_lim.setStyle(style);
		}
		if(!lbl_temp_txn_code.getValue().equals(lbl_mst_txn_code.getValue())) {
			lbl_temp_txn_code.setStyle(style);
			lbl_mst_txn_code.setStyle(style);
		}
	}
	
}
