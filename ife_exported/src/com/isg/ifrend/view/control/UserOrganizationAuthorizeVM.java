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

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.api.Combobox;

import com.isg.ifrend.model.bean.ActionType;
import com.isg.ifrend.model.bean.StatusActionType;
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
import com.isg.ifrend.view.control.renderer.UserOrganizationAuthorizeListitemRenderer;

/**
 * @author elvin.aquino
 *
 */
public class UserOrganizationAuthorizeVM extends GenericForwardComposer {


	private static final long serialVersionUID = 1L;

	private AnnotateDataBinder binder;
	private GlobalUtils globalUtils = GlobalUtils.getGlobalUtilsInstance();

	private Bandbox bb_user_org;
	private Combobox cbb_action;
	private Listbox lb_user_org_search;
	private Button btn_approve;
	private Button btn_reject;
	private Button btn_view_history;
	private Listbox lb_user_org_list;
	private Set<Listitem> selectedUserOrgSet;
	
	UserOrganizationManager userOrgManager = ServiceLocator.getUserOrganizationManager();
	TempUserOrganizationManager tempUserOrgManager = ServiceLocator.getTempUserOrganizationManager();
	SecurityUtils securityUtils = SecurityUtils.getSecurityUtilsInstance();
	
	List<String> tempUserOrgList;
	List<String> actionList = new ArrayList<String>();

	public void doBeforeComposeChildren(Component comp) throws Exception {
		super.doBeforeComposeChildren(comp);
		
		tempUserOrgList = tempUserOrgManager.getUserOrgIdListByStatus(StatusType.PENDING.getId());
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		comp.setAttribute(comp.getId() + "Control", this, true);
		
//		for(PendingEntityStatusType action : PendingEntityStatusType.values()) {
//			actionList.add(action.getDesc());
//		}
//		cbb_action.setText(PendingEntityStatusType.ALL.getDesc());
		for(ActionType action : ActionType.values()) {
			actionList.add(action.getDesc());
		}
		cbb_action.setText(ActionType.ALL.getDesc());
		lb_user_org_list.setItemRenderer(new UserOrganizationAuthorizeListitemRenderer());
		manageButtons();
		btn_view_history.setHref(globalUtils.getGlobalPropValue(Commons.URL_HISTORY_USER_ORG));
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
	}

	public void onChange$bb_user_org()throws InterruptedException {		
		if(!bb_user_org.getValue().isEmpty()){
//			searchUser(bb_user_org.getValue().toUpperCase());
			searchUserOrg(bb_user_org.getValue(), StatusActionType.getId(cbb_action.getValue()));
		}
	}

	public void onSelect$lb_user_org_search()throws InterruptedException{
		bb_user_org.setValue(lb_user_org_search.getSelectedItem().getLabel());
		bb_user_org.close();

		searchUserOrg(bb_user_org.getValue(), StatusActionType.getId(cbb_action.getValue()));
	}
	
	public void onClick$btn_list() {
		searchUserOrg(bb_user_org.getValue(), StatusActionType.getId(cbb_action.getValue()));
	}
	
	@SuppressWarnings("unchecked")
	public void onClick$btn_approve() throws InterruptedException {
		selectedUserOrgSet = lb_user_org_list.getSelectedItems();
		if (selectedUserOrgSet.isEmpty()) {
			GlobalUtils.showMessage(globalUtils.getMessagePropValue(Commons.ERR_MSG_USER_ORG_NO_SELECTED),
					globalUtils.getMessagePropValue(Commons.MSG_HEADER_APPROVE),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		Messagebox.show(MessageFormat.format(globalUtils.getMessagePropValue(
				Commons.MSG_USER_ORG_APPROVE_PROMPT_MULTI), selectedUserOrgSet.size()),
				globalUtils.getMessagePropValue(Commons.MSG_HEADER_APPROVE), 
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new EventListener() {
					
					@Override
					public void onEvent(Event evt) throws Exception {
						switch (((Integer)evt.getData()).intValue()) {
			            case Messagebox.OK:
							Set<String> tempUserOrgSet = new HashSet<String>();
							for (Listitem item : selectedUserOrgSet) {
								String tempUserOrg = (String)item.getValue();
								tempUserOrgSet.add(tempUserOrg);
							}
			            	int approvedUserOrgs = approveSelectedUserOrg(tempUserOrgSet);
		            		GlobalUtils.showMessage(MessageFormat.format(globalUtils.getMessagePropValue(
		            				Commons.MSG_USER_ORG_APPROVE_MULTI), approvedUserOrgs, 
		            				(tempUserOrgSet.size() - approvedUserOrgs)),
		        					globalUtils.getMessagePropValue(Commons.MSG_HEADER_APPROVE),
		        					Messagebox.OK, Messagebox.INFORMATION);
		            		refreshUserOrgList();
			            	break;
			            case Messagebox.CANCEL:
			            	// Do nothing when user clicks cancel
			            	break;
			            }
					}
				});
	}
	
	@SuppressWarnings("unchecked")
	public void onClick$btn_reject() throws InterruptedException {
		selectedUserOrgSet = lb_user_org_list.getSelectedItems();
		if (selectedUserOrgSet.isEmpty()) {
			GlobalUtils.showMessage(globalUtils.getMessagePropValue(Commons.ERR_MSG_USER_ORG_NO_SELECTED),
					globalUtils.getMessagePropValue(Commons.MSG_HEADER_REJECT),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		Messagebox.show(MessageFormat.format(globalUtils.getMessagePropValue(
				Commons.MSG_USER_ORG_REJECT_PROMPT_MULTI), selectedUserOrgSet.size()),
				globalUtils.getMessagePropValue(Commons.MSG_HEADER_REJECT), 
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new EventListener() {
					
					@Override
					public void onEvent(Event evt) throws Exception {
						switch (((Integer)evt.getData()).intValue()) {
			            case Messagebox.OK:
							Set<String> tempUserOrgSet = new HashSet<String>();
							for (Listitem item : selectedUserOrgSet) {
								String tempUserOrg = (String)item.getValue();
								tempUserOrgSet.add(tempUserOrg);
							}
			            	int rejectedUsers = rejectSelectedUser(tempUserOrgSet);
		            		GlobalUtils.showMessage(MessageFormat.format(globalUtils.getMessagePropValue(
		            				Commons.MSG_USER_ORG_REJECT_MULTI), rejectedUsers, 
		            				(tempUserOrgSet.size() - rejectedUsers)),
		        					globalUtils.getMessagePropValue(Commons.MSG_HEADER_REJECT),
		        					Messagebox.OK, Messagebox.INFORMATION);
		            		refreshUserOrgList();
			            	break;
			            case Messagebox.CANCEL:
			            	// Do nothing when user selects cancel
			            	break;
			            }
					}
				});
	}
	
	@SuppressWarnings("unchecked")
	public void onClick$btn_cancel() throws InterruptedException {
		selectedUserOrgSet = lb_user_org_list.getSelectedItems();
		if (selectedUserOrgSet.isEmpty()) {
			GlobalUtils.showMessage(globalUtils.getMessagePropValue(Commons.ERR_MSG_USER_ORG_NO_SELECTED),
					globalUtils.getMessagePropValue(Commons.MSG_HEADER_CANCEL),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		Messagebox.show(MessageFormat.format(globalUtils.getMessagePropValue(
				Commons.MSG_USER_ORG_CANCEL_PROMPT_MULTI), selectedUserOrgSet.size()),
				globalUtils.getMessagePropValue(Commons.MSG_HEADER_CANCEL), 
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new EventListener() {
					
					@Override
					public void onEvent(Event evt) throws Exception {
						switch (((Integer)evt.getData()).intValue()) {
			            case Messagebox.OK:
							Set<String> tempUserOrgSet = new HashSet<String>();
							for (Listitem item : selectedUserOrgSet) {
								String tempUserOrg = (String)item.getValue();
								tempUserOrgSet.add(tempUserOrg);
							}
			            	int cancelledUserOrgs = cancelSelectedUserOrg(tempUserOrgSet);
		            		GlobalUtils.showMessage(MessageFormat.format(globalUtils.getMessagePropValue(
		            				Commons.MSG_USER_ORG_CANCEL_MULTI), cancelledUserOrgs, 
		            				(tempUserOrgSet.size() - cancelledUserOrgs)),
		        					globalUtils.getMessagePropValue(Commons.MSG_HEADER_CANCEL),
		        					Messagebox.OK, Messagebox.INFORMATION);
		            		refreshUserOrgList();
			            	break;
			            case Messagebox.CANCEL:
			            	// Do nothing when user selects cancel
			            	break;
			            }
					}
				});
	}
	
	public void onSelect$lb_user_org_list()throws InterruptedException{
//		System.out.println("Executed: onSelect$lb_user_org_list");	
	}

	public void onClick$btn_add() throws InterruptedException{
		Executions.sendRedirect(globalUtils.getGlobalPropValue(Commons.URL_ADD_USER_PROFILE));
	}

	private void searchUserOrg(String id, int action){
		if(id == "" && action == 0) {
			tempUserOrgList = tempUserOrgManager.getUserOrgIdListByStatus(StatusType.PENDING.getId()); 
		}
		else if (action == 0) {
			tempUserOrgList = tempUserOrgManager.getUserOrgIdListByID(id);
		}
		else {
			tempUserOrgList = tempUserOrgManager.getUserOrgIdListByIdAndAction(id, action);
		}
		ListModelList model = new ListModelList(tempUserOrgList);
		lb_user_org_list.setModel(model);
	}

	private int approveSelectedUserOrg(Set<String> tempUserOrgSet) throws ParseException {
		int approved = 0;
		for (String tempUserOrg : tempUserOrgSet) {
			if(approveSelectedUserOrg(tempUserOrg)) {
				approved++;
			}
		}
		return approved;
	}
	
	private boolean approveSelectedUserOrg(String tempUserOrgID) throws ParseException {
		boolean flag = false;
		TempUserOrganization tempUserOrg = tempUserOrgManager.findById(tempUserOrgID);
		int action = tempUserOrg.getAction();
		// Check if logged in user is the same as the maker. If yes, skip.. else proceed
		if(securityUtils.getUserName().equals(tempUserOrg.getLastModifiedBy())){
			// Do nothing
		}
		else {
			// Save or update user in the Master table
//			tempUserOrg = tempUserOrgManager.findById(tempUserOrg.getOrgID());
			Date date = DateUtil.getCurrentDate();
			tempUserOrg.setApprovedBy(securityUtils.getUserName());
			tempUserOrg.setDateApproved(date);
			UserOrganization userOrg = new UserOrganization(tempUserOrg);
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
	
	private int rejectSelectedUser(Set<String> tempUserOrgSet) throws ParseException {
		int rejected = 0;
		for (String tempUserOrg : tempUserOrgSet) {
			if(rejectSelectedUserOrg(tempUserOrg)) {
				rejected++;
			}
		}
		return rejected;
	}
	
	private boolean rejectSelectedUserOrg(String tempUserOrgID) throws ParseException {
		boolean flag = false;
		TempUserOrganization tempUserOrg = tempUserOrgManager.findById(tempUserOrgID);
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
				flag = false;
			}
		}
		return flag;
	}
	
	private int cancelSelectedUserOrg(Set<String> tempUserOrgSet) throws ParseException {
		int cancelled = 0;
		for (String tempUserOrg : tempUserOrgSet) {
			if(cancelSelectedUser(tempUserOrg)) {
				cancelled++;
			}
		}
		return cancelled;
	}
	
	private boolean cancelSelectedUser(String tempUserOrgID) throws ParseException {
		boolean flag = false;
		TempUserOrganization tempUserOrg = tempUserOrgManager.findById(tempUserOrgID);
		int action = tempUserOrg.getAction();
		
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
	
	private void refreshUserOrgList() {
		tempUserOrgList = tempUserOrgManager.getUserOrgIdListByStatus(StatusType.PENDING.getId());
		ListModelList model = new ListModelList(tempUserOrgList);
		lb_user_org_search.setModel(model);
		lb_user_org_list.setModel(model);
	}
	
	private void manageButtons() {
		if(securityUtils.hasCheckerRole(globalUtils.getGlobalPropValue(Commons.URL_AUTHORIZE_USER_ORG))) {
			btn_approve.setVisible(true);
			btn_reject.setVisible(true);
		}
	}
	
	public Bandbox getBb_user_org() {
		return bb_user_org;
	}

	public void setBb_user_org(Bandbox bb_user_org) {
		this.bb_user_org = bb_user_org;
	}

	public Listbox getLb_user_org_search() {
		return lb_user_org_search;
	}

	public void setLb_user_org_search(Listbox lb_user_org_search) {
		this.lb_user_org_search = lb_user_org_search;
	}

	public Listbox getLb_user_org_list() {
		return lb_user_org_list;
	}

	public void setLb_user_org_list(Listbox lb_user_org_list) {
		this.lb_user_org_list = lb_user_org_list;
	}

	public List<String> getTempUserOrgList() {
		return tempUserOrgList;
	}

	public void setTempUserOrgList(List<String> tempUserOrgList) {
		this.tempUserOrgList = tempUserOrgList;
	}

	public List<String> getActionList() {
		return actionList;
	}

	public void setActionList(List<String> actionList) {
		this.actionList = actionList;
	}
	
}
