package com.isg.ifrend.view.control;

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.isg.ifrend.model.bean.ActionType;
import com.isg.ifrend.model.bean.StatusActionType;
import com.isg.ifrend.model.bean.StatusType;
import com.isg.ifrend.model.bean.TempUser;
import com.isg.ifrend.model.bean.User;
import com.isg.ifrend.security.utils.SecurityUtils;
import com.isg.ifrend.service.ServiceLocator;
import com.isg.ifrend.service.TempUserManager;
import com.isg.ifrend.service.UserManager;
import com.isg.ifrend.utils.Commons;
import com.isg.ifrend.utils.DateUtil;
import com.isg.ifrend.utils.GlobalUtils;
import com.isg.ifrend.view.control.renderer.UserProfileAuthorizeListitemRenderer;

/**
 * @author elvin.aquino
 *
 */
public class UserProfileAuthorizeVM extends GenericForwardComposer {

	private static final long serialVersionUID = 1L;
	
	private AnnotateDataBinder binder;
	private GlobalUtils globalUtils = GlobalUtils.getGlobalUtilsInstance();
	
	private Bandbox bb_user_profile;
	private Listbox lb_user_search;
	private Button btn_approve;
	private Button btn_reject;
	private Button btn_view_history;
	private Combobox cbb_action;
//	private Combobox cbb_status;
	private Listbox lb_user_list;
	
	TempUserManager tempUserManager = ServiceLocator.getTempUserManager();
	UserManager userManager = ServiceLocator.getUserManager();
	SecurityUtils securityUtils = SecurityUtils.getSecurityUtilsInstance();
	
	List<String> tempUserList;
	List<String> actionList = new ArrayList<String>();
	Set<Listitem> selectedUserSet;

	public void doBeforeComposeChildren(Component comp) throws Exception {
		super.doBeforeComposeChildren(comp);

		tempUserList = tempUserManager.getUserIdListByStatus(StatusType.PENDING.getId());
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

//		for(PendingEntityStatusType action : PendingEntityStatusType.values()) {
//			actionList.add(action.getDesc());
//		}
//		cbb_action.setText(PendingEntityStatusType.ALL.getDesc());
		for(ActionType action : ActionType.values()) {
			actionList.add(action.getDesc());
		}
		cbb_action.setText(ActionType.ALL.getDesc());
		lb_user_list.setItemRenderer(new UserProfileAuthorizeListitemRenderer());
		manageButtons();
		btn_view_history.setHref(globalUtils.getGlobalPropValue(Commons.URL_HISTORY_USER_PROFILE));
		
		comp.setAttribute(comp.getId() + "Control", this, true);
		
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
	}
	
	public void onChange$bb_user_profile()throws InterruptedException{		
		if(!bb_user_profile.getValue().isEmpty()){
			searchUser(bb_user_profile.getValue(), StatusActionType.getId(cbb_action.getValue()));
		}
	}
	
	public void onSelect$lb_user_search()throws InterruptedException{
		bb_user_profile.setValue(lb_user_search.getSelectedItem().getLabel());
		bb_user_profile.close();

		searchUser(bb_user_profile.getValue(), StatusActionType.getId(cbb_action.getValue()));
	}
	
	public void onClick$btn_list() {
		searchUser(bb_user_profile.getValue(), StatusActionType.getId(cbb_action.getValue()));
	}
	
	@SuppressWarnings("unchecked")
	public void onClick$btn_approve() throws InterruptedException {
		selectedUserSet = lb_user_list.getSelectedItems();
		if (selectedUserSet.isEmpty()) {
			GlobalUtils.showMessage(globalUtils.getMessagePropValue(Commons.ERR_MSG_USER_NO_SELECTED),
					globalUtils.getMessagePropValue(Commons.MSG_HEADER_APPROVE),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		Messagebox.show(MessageFormat.format(globalUtils.getMessagePropValue(
				Commons.MSG_USER_APPROVE_PROMPT_MULTI), (selectedUserSet.size())),
				globalUtils.getMessagePropValue(Commons.MSG_HEADER_APPROVE), 
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new EventListener() {
					
					@Override
					public void onEvent(Event evt) throws Exception {
						switch (((Integer)evt.getData()).intValue()) {
			            case Messagebox.OK:
							Set<String> tempUserSet = new HashSet<String>();
							for (Listitem item : selectedUserSet) {
								String tempUser = (String)item.getValue();
								tempUserSet.add(tempUser);
							}
			            	int approvedUsers = approveSelectedUser(tempUserSet);
		            		Messagebox.show(MessageFormat.format(globalUtils.getMessagePropValue(
		            				Commons.MSG_USER_APPROVE_MULTI), approvedUsers, (tempUserSet.size() - approvedUsers)),
		        					globalUtils.getMessagePropValue(Commons.MSG_HEADER_APPROVE),
		        					Messagebox.OK, Messagebox.INFORMATION);
		            		// Refresh listbox
		            		refreshUserList();
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
		selectedUserSet = lb_user_list.getSelectedItems();
		if (selectedUserSet.isEmpty()) {
			GlobalUtils.showMessage(globalUtils.getMessagePropValue(Commons.ERR_MSG_USER_NO_SELECTED),
					globalUtils.getMessagePropValue(Commons.MSG_HEADER_REJECT),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		Messagebox.show(MessageFormat.format(globalUtils.getMessagePropValue(
				Commons.MSG_USER_REJECT_PROMPT_MULTI), selectedUserSet.size()),
				globalUtils.getMessagePropValue(Commons.MSG_HEADER_REJECT), 
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new EventListener() {
					
					@Override
					public void onEvent(Event evt) throws Exception {
						switch (((Integer)evt.getData()).intValue()) {
			            case Messagebox.OK:
							Set<String> tempUserSet = new HashSet<String>();
							for (Listitem item : selectedUserSet) {
								String tempUser = (String)item.getValue();
								tempUserSet.add(tempUser);
							}
			            	int rejectedUsers = rejectSelectedUser(tempUserSet);
			            	GlobalUtils.showMessage(MessageFormat.format(globalUtils.getMessagePropValue(
		            				Commons.MSG_USER_REJECT_MULTI), rejectedUsers, (tempUserSet.size() - rejectedUsers)),
		        					globalUtils.getMessagePropValue(Commons.MSG_HEADER_REJECT),
		        					Messagebox.OK, Messagebox.INFORMATION);
		            		refreshUserList();
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
		selectedUserSet = lb_user_list.getSelectedItems();
		if (selectedUserSet.isEmpty()) {
			GlobalUtils.showMessage(globalUtils.getMessagePropValue(Commons.ERR_MSG_USER_NO_SELECTED),
					globalUtils.getMessagePropValue(Commons.MSG_HEADER_CANCEL),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		Messagebox.show(MessageFormat.format(globalUtils.getMessagePropValue(
				Commons.MSG_USER_CANCEL_PROMPT_MULTI), selectedUserSet.size()),
				globalUtils.getMessagePropValue(Commons.MSG_HEADER_CANCEL), 
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new EventListener() {
					
					@Override
					public void onEvent(Event evt) throws Exception {
						switch (((Integer)evt.getData()).intValue()) {
			            case Messagebox.OK:
							Set<String> tempUserSet = new HashSet<String>();
							for (Listitem item : selectedUserSet) {
								String tempUser = (String)item.getValue();
								tempUserSet.add(tempUser);
							}
			            	int cancelledUsers = cancelSelectedUser(tempUserSet);
			            	GlobalUtils.showMessage(MessageFormat.format(globalUtils.getMessagePropValue(
		            				Commons.MSG_USER_CANCEL_MULTI), cancelledUsers, (tempUserSet.size() - cancelledUsers)),
		        					globalUtils.getMessagePropValue(Commons.MSG_HEADER_CANCEL),
		        					Messagebox.OK, Messagebox.INFORMATION);
		            		refreshUserList();
			            	break;
			            case Messagebox.CANCEL:
			            	// Do nothing when user selects cancel
			            	break;
			            }
					}
				});
	}
	
	private void searchUser(String id, int action){
		if(id == "" && action == ActionType.ALL.getId()) {
			tempUserList = tempUserManager.getUserIdListByStatus(StatusType.PENDING.getId());
		}
		else if (action == ActionType.ALL.getId()) {
			tempUserList = tempUserManager.getUserIdListById(id);
		}
		else {
			tempUserList = tempUserManager.getUserIdListByIdAndAction(id, action);
		}
		ListModelList model = new ListModelList(tempUserList);
		lb_user_list.setModel(model);
	}
	
	private void refreshUserList() {
		tempUserList = tempUserManager.getUserIdListByStatus(StatusType.PENDING.getId());
		ListModelList model = new ListModelList(tempUserList);
		lb_user_list.setModel(model);
		lb_user_search.setModel(model);
	}
	
	private int approveSelectedUser(Set<String> tempUserSet) throws ParseException {
		int approved = 0;
		for (String tempUser : tempUserSet) {
			if(approveSelectedUser(tempUser)) {
				approved++;
			}
		}
		return approved;
	}
	
	private boolean approveSelectedUser(String tempUserID) throws ParseException {
		boolean flag = false;
		TempUser tempUser = tempUserManager.findById(tempUserID);
		int action = tempUser.getAction();
		// Check if logged in user is the same as the maker. If yes, skip.. else proceed
		if(securityUtils.getUserName().equals(tempUser.getLastModifiedBy())){
			// Do nothing
		}
		else {
			// Save or update user in the Master table
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
	
	private int rejectSelectedUser(Set<String> tempUserSet) throws Exception {
		int rejected = 0;
		for (String tempUser : tempUserSet) {
			if(rejectSelectedUser(tempUser)) {
				rejected++;
			}
		}
		return rejected;
	}
	
	private boolean rejectSelectedUser(String tempUserID) throws Exception {
		boolean flag = false;
		TempUser tempUser = tempUserManager.findById(tempUserID);
		int action = tempUser.getAction();
		
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
					securityUtils.deleteUserCredentials(tempUserID);
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
	
	private int cancelSelectedUser(Set<String> tempUserSet) throws Exception {
		int cancelled = 0;
		for (String tempUser : tempUserSet) {
			if(cancelSelectedUser(tempUser)) {
				cancelled++;
			}
		}
		return cancelled;
	}
	
	private boolean cancelSelectedUser(String tempUserID) throws Exception {
		boolean flag = false;
		TempUser tempUser = tempUserManager.findById(tempUserID);
		int action = tempUser.getAction();
		
		Date date = DateUtil.getCurrentDate();
		tempUser.setLastModifiedBy(securityUtils.getUserName());
		tempUser.setDateLastModified(date);
		// Check if logged in user is the same as the maker. If yes, skip.. else proceed
		if(securityUtils.getUserName().equals(tempUser.getLastModifiedBy())){
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
					tempUserManager.update(tempUser);
					tempUserManager.delete(tempUser);
					if(action == ActionType.ADD.getId()) {
						securityUtils.deleteUserCredentials(tempUserID);
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
	
	private void manageButtons() {
		if(securityUtils.hasCheckerRole(globalUtils.getGlobalPropValue(Commons.URL_AUTHORIZE_USER_PROFILE))) {
			btn_approve.setVisible(true);
			btn_reject.setVisible(true);
		}
	}
	
	public Bandbox getBb_user_profile() {
		return bb_user_profile;
	}

	public void setBb_user_profile(Bandbox bb_user_profile) {
		this.bb_user_profile = bb_user_profile;
	}

	public TempUserManager getTempUserManager() {
		return tempUserManager;
	}

	public void setTempUserManager(TempUserManager tempUserManager) {
		this.tempUserManager = tempUserManager;
	}

	public List<String> getTempUserList() {
		return tempUserList;
	}

	public void setTempUserList(List<String> tempUserList) {
		this.tempUserList = tempUserList;
	}

	public Set<Listitem> getSelectedUserSet() {
		return selectedUserSet;
	}

	public void setSelectedUserSet(Set<Listitem> selectedUserSet) {
		this.selectedUserSet = selectedUserSet;
	}

	public List<String> getActionList() {
		return actionList;
	}

	public void setActionList(List<String> actionList) {
		this.actionList = actionList;
	}

}
