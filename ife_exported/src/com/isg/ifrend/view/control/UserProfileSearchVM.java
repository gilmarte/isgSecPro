/**
 * 
 */
package com.isg.ifrend.view.control;

import java.text.MessageFormat;
import java.text.ParseException;
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
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.isg.ifrend.model.bean.ActionType;
import com.isg.ifrend.model.bean.StatusType;
import com.isg.ifrend.model.bean.TempUser;
import com.isg.ifrend.model.bean.User;
import com.isg.ifrend.security.utils.SecurityUtils;
import com.isg.ifrend.service.ServiceLocator;
import com.isg.ifrend.service.TempUserManager;
import com.isg.ifrend.service.UserManager;
import com.isg.ifrend.utils.Commons;
import com.isg.ifrend.utils.DateUtil;
import com.isg.ifrend.utils.ExportUtil;
import com.isg.ifrend.utils.GlobalUtils;
import com.isg.ifrend.view.control.renderer.UserProfileSearchListitemRenderer;

/**
 * @author elvin.aquino
 *
 */
public class UserProfileSearchVM extends GenericForwardComposer {


	private static final long serialVersionUID = 1L;

	private AnnotateDataBinder binder;
	private GlobalUtils globalUtils = GlobalUtils.getGlobalUtilsInstance();
	private SecurityUtils securityUtils = SecurityUtils.getSecurityUtilsInstance();

	private Bandbox bb_user_profile;
	private Listbox lb_user_search;
	private Button btn_add;
	private Button btn_delete;
	private Button btn_back;
	private Listbox lb_user_list;
	
	TempUserManager tempUserManager = ServiceLocator.getTempUserManager();
	UserManager userManager = ServiceLocator.getUserManager();
	
	List<String> userList;
	
	Set<Listitem> selectedUserSet;

	public void doBeforeComposeChildren(Component comp) throws Exception {
		super.doBeforeComposeChildren(comp);
		
		userList = userManager.getUserListByStatus(StatusType.PENDING.getId(), StatusType.ACTIVE.getId());
//				Commons.STATUS_PENDING_UPDATE, Commons.STATUS_PENDING_DELETE, //Commons.STATUS_PENDING_ADD,
//				Commons.STATUS_ACTIVE);
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		lb_user_list.setItemRenderer(new UserProfileSearchListitemRenderer());
		manageButtons();
		btn_back.setHref(globalUtils.getGlobalPropValue(Commons.URL_SECURITY_ADMIN));
		
		comp.setAttribute(comp.getId() + "Control", this, true);
		
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();

	}

	public void onChange$bb_user_profile()throws InterruptedException{		
		if(!bb_user_profile.getValue().isEmpty()){
			searchUser(bb_user_profile.getValue());
		}
	}

	public void onSelect$lb_user_search()throws InterruptedException{
		bb_user_profile.setValue(lb_user_search.getSelectedItem().getLabel());
		bb_user_profile.close();

		searchUser(bb_user_profile.getValue());
	}

	public void onClick$btn_add() throws InterruptedException{
		Executions.sendRedirect(globalUtils.getGlobalPropValue(Commons.URL_ADD_USER_PROFILE));
	}
	
	@SuppressWarnings("unchecked")
	public void onClick$btn_delete() throws InterruptedException {
		selectedUserSet = lb_user_list.getSelectedItems();
		if (selectedUserSet.isEmpty()) {
			GlobalUtils.showMessage(globalUtils.getMessagePropValue(Commons.ERR_MSG_USER_NO_SELECTED),
					globalUtils.getMessagePropValue(Commons.MSG_HEADER_DELETE),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		Messagebox.show(MessageFormat.format(globalUtils.getMessagePropValue(
				Commons.MSG_USER_DELETE_PROMPT_MULTI), selectedUserSet.size()),
				globalUtils.getMessagePropValue(Commons.MSG_HEADER_DELETE), 
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new EventListener() {
					
					@Override
					public void onEvent(Event evt) throws Exception {
						switch (((Integer)evt.getData()).intValue()) {
			            case Messagebox.OK:
							Set<String> userSet = new HashSet<String>();
							for (Listitem item : selectedUserSet) {
								String user = (String)item.getValue();
								userSet.add(user);
							}
			            	int deletedUsers = deleteSelectedUser(userSet);
		            		GlobalUtils.showMessage(MessageFormat.format(globalUtils.getMessagePropValue(
		            				Commons.MSG_USER_DELETE_MULTI), deletedUsers, (selectedUserSet.size() - deletedUsers)),
		        					globalUtils.getMessagePropValue(Commons.MSG_HEADER_DELETE),
		        					Messagebox.OK, Messagebox.INFORMATION);
		            		refreshUserList();
			            	break;
			            case Messagebox.CANCEL:
			            	break;
			            }
					}
				});
	}
	
	public void onClick$btn_export() throws InterruptedException {
		try {
			Listbox listBox = new Listbox();
			Listhead listHeader = new Listhead();
			for(Object o : lb_user_list.getChildren()) {
				if(o instanceof Listhead) {
					Listhead head = (Listhead) o;
					for(Object o1 : head.getChildren()) {
						if(o1 instanceof Listheader) {
							Listheader header = (Listheader) o1;
							Listheader newHeader = new Listheader(header.getLabel());
							newHeader.setParent(listHeader);
						}
					}
					listHeader.setParent(listBox);
				}
			}
			listBox.setModel(new ListModelList(userManager.getUserListById(bb_user_profile.getValue())));
			ExportUtil.exportToExcel(globalUtils.getGlobalPropValue(Commons.SCREEN_USER_PROFILE_SEARCH), listBox);
		}
		catch (Exception e){
			e.printStackTrace();
			GlobalUtils.showMessage(globalUtils.getMessagePropValue(Commons.ERR_MSG_EXPORT),
				globalUtils.getGlobalPropValue(Commons.MSG_HEADER_ERROR),
				Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	public void onClick$listItem() {
//		System.out.println("UserProfileSearchVM >> onClick$listItem()");
	}

	public void searchUser(String user_id){
		userList = userManager.getUserIdListById(user_id);
		ListModelList model = new ListModelList(userList);
		lb_user_list.setModel(model);
	}
	
	public int deleteSelectedUser(Set<String> userSet) throws ParseException {
		int deleted = 0;
		for (String user : userSet) {
			if(deleteSelectedUser(user)) {
				deleted++;
			}
		}
		return deleted;
	}
	
	public boolean deleteSelectedUser(String userID) throws ParseException {
		User user = userManager.findById(userID);
		if(user.getStatus() == StatusType.PENDING.getId()) {
			return false;
		}
		else {
			user.setStatus(StatusType.PENDING.getId());
			user.setAction(ActionType.DELETE.getId());
			Date date = DateUtil.getCurrentDate();
			user.setLastModifiedBy(securityUtils.getUserName());
			user.setDateLastModified(date);
			if(userManager.update(user)) {
				TempUser tempUser = new TempUser(userManager.findById(user.getUserID()));
				tempUser.setStatus(StatusType.PENDING.getId());
				tempUser.setAction(ActionType.DELETE.getId());
				tempUser.setLastModifiedBy(securityUtils.getUserName());
				tempUser.setDateLastModified(date);
				return tempUserManager.save(tempUser);
			}
			return false;
		}
	}
	
	private void refreshUserList() {
		userList = userManager.getUserListByStatus(StatusType.PENDING.getId(), StatusType.ACTIVE.getId());
		ListModelList model = new ListModelList(userList);
		lb_user_list.setModel(model);
		lb_user_search.setModel(model);
	}
	
	private void manageButtons() {
		if(securityUtils.hasMakerRole(globalUtils.getGlobalPropValue(Commons.URL_SEARCH_USER_PROFILE))) {
			btn_add.setVisible(true);
			btn_delete.setVisible(true);
		}
//		if(securityUtils.getUserName().equals(tempUserOrg.getLastModifiedBy())) {
//			btn_cancel.setVisible(true);
//		}
	}

	public Listbox getLb_user_list() {
		return lb_user_list;
	}

	public void setLb_user_list(Listbox lb_user_list) {
		this.lb_user_list = lb_user_list;
	}

	public List<String> getUserList() {
		return userList;
	}

	public void setUserList(List<String> userList) {
		this.userList = userList;
	}
	
}
