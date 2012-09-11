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
import com.isg.ifrend.model.bean.TempUserOrganization;
import com.isg.ifrend.model.bean.UserOrganization;
import com.isg.ifrend.security.utils.SecurityUtils;
import com.isg.ifrend.service.ServiceLocator;
import com.isg.ifrend.service.TempUserOrganizationManager;
import com.isg.ifrend.service.UserOrganizationManager;
import com.isg.ifrend.utils.Commons;
import com.isg.ifrend.utils.DateUtil;
import com.isg.ifrend.utils.ExportUtil;
import com.isg.ifrend.utils.GlobalUtils;
import com.isg.ifrend.view.control.renderer.UserOrganizationSearchListitemRenderer;

/**
 * @author elvin.aquino
 *
 */
public class UserOrganizationSearchVM extends GenericForwardComposer {


	private static final long serialVersionUID = 1L;

	private AnnotateDataBinder binder;
	private GlobalUtils globalUtils = GlobalUtils.getGlobalUtilsInstance();
	private SecurityUtils securityUtils = SecurityUtils.getSecurityUtilsInstance();

	private Bandbox bb_user_org;
	private Listbox lb_user_org_search;
	private Button btn_add;
	private Button btn_delete;
	private Listbox lb_user_org_list;
	private Listitem selectedUserOrg;
	
	private Button btn_back;
	
	UserOrganizationManager userOrgManager = ServiceLocator.getUserOrganizationManager();
	TempUserOrganizationManager tempUserOrgManager = ServiceLocator.getTempUserOrganizationManager();
	
	List<String> userOrgList;
	Set<Listitem> selectedOrgSet;

	public void doBeforeComposeChildren(Component comp) throws Exception {
		super.doBeforeComposeChildren(comp);
		
		userOrgList = userOrgManager.getUserOrgIdListByStatus(
				StatusType.PENDING.getId(), StatusType.ACTIVE.getId());
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		lb_user_org_list.setItemRenderer(new UserOrganizationSearchListitemRenderer());
		manageButtons();
		btn_back.setHref(globalUtils.getGlobalPropValue(Commons.URL_SECURITY_ADMIN));
		comp.setAttribute(comp.getId() + "Control", this, true);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();

	}

	public void onChange$bb_user_org()throws InterruptedException{		

		if(!bb_user_org.getValue().isEmpty()){
			searchUserOrg(bb_user_org.getValue());//.toUpperCase());
		}
	}

	public void onSelect$lb_user_org_search()throws InterruptedException{
		bb_user_org.setValue(lb_user_org_search.getSelectedItem().getLabel());
		bb_user_org.close();

		searchUserOrg(bb_user_org.getValue());
	}
	
	public void onSelect$lb_user_org_list()throws InterruptedException{
//		System.out.println("Executed: onSelect$lb_user_org_list");	
	}

	public void onClick$btn_add() throws InterruptedException{
		Executions.sendRedirect(globalUtils.getGlobalPropValue(Commons.URL_ADD_USER_ORG));
	}
	
	@SuppressWarnings("unchecked")
	public void onClick$btn_delete() throws InterruptedException{
		selectedOrgSet = lb_user_org_list.getSelectedItems();
		if (selectedOrgSet.isEmpty()) {
			GlobalUtils.showMessage(globalUtils.getMessagePropValue(Commons.ERR_MSG_USER_ORG_NO_SELECTED),
					globalUtils.getMessagePropValue(Commons.MSG_HEADER_DELETE),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		Messagebox.show(MessageFormat.format(globalUtils.getMessagePropValue(
				Commons.MSG_USER_ORG_DELETE_PROMPT_MULTI), selectedOrgSet.size()),
				globalUtils.getMessagePropValue(Commons.MSG_HEADER_DELETE), 
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new EventListener() {
					
					@Override
					public void onEvent(Event evt) throws Exception {
						switch (((Integer)evt.getData()).intValue()) {
			            case Messagebox.OK:
							Set<String> userOrgSet = new HashSet<String>();
							for (Listitem item : selectedOrgSet) {
								String userOrg = (String)item.getValue();
								userOrgSet.add(userOrg);
							}
			            	int deletedUsers = deleteSelectedUserOrg(userOrgSet);
			            	GlobalUtils.showMessage(MessageFormat.format(globalUtils.getMessagePropValue(
		            				Commons.MSG_USER_ORG_DELETE_MULTI), deletedUsers, 
		            				(userOrgSet.size() - deletedUsers)),
		        					globalUtils.getMessagePropValue(Commons.MSG_HEADER_DELETE),
		        					Messagebox.OK, Messagebox.INFORMATION);
			            	break;
			            case Messagebox.CANCEL:
			            	// Do nothing
			            	break;
			            }
					}
				});
	}
	
	public void onClick$btn_export() throws InterruptedException {
		try {
			Listbox listBox = new Listbox();
			Listhead listHeader = new Listhead();
			for(Object o : lb_user_org_list.getChildren()) {
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
			listBox.setModel(new ListModelList(userOrgManager.getUserOrgListByID(bb_user_org.getValue())));
			ExportUtil.exportToExcel(globalUtils.getGlobalPropValue(Commons.SCREEN_USER_ORG_SEARCH), listBox);
		}
		catch (Exception e){
			e.printStackTrace();
			GlobalUtils.showMessage(globalUtils.getMessagePropValue(Commons.ERR_MSG_EXPORT),
				globalUtils.getGlobalPropValue(Commons.MSG_HEADER_ERROR),
				Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void searchUserOrg(String orgID){
		userOrgList = userOrgManager.getUserOrgIdListByID(orgID);
		ListModelList model = new ListModelList(userOrgList);
		lb_user_org_list.setModel(model);
	}
	
	public int deleteSelectedUserOrg(Set<String> userOrgSet) throws ParseException {
		int deleted = 0;
		for (String userOrg : userOrgSet) {
			if(deleteSelectedUserOrg(userOrg)) {
				deleted++;
			}
		}
		return deleted;
	}
	
	public boolean deleteSelectedUserOrg(String userOrgID) throws ParseException {
		UserOrganization userOrg = userOrgManager.findById(userOrgID);
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
			return false;
		}
	}
	
	private void manageButtons() {
		if(securityUtils.hasMakerRole(globalUtils.getGlobalPropValue(Commons.URL_SEARCH_USER_ORG))) {
			btn_add.setVisible(true);
			btn_delete.setVisible(true);
		}
//		if(securityUtils.getUserName().equals(tempUserOrg.getLastModifiedBy())) {
//			btn_cancel.setVisible(true);
//		}
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

	public Listitem getSelectedUserOrg() {
		return selectedUserOrg;
	}

	public void setSelectedUserOrg(Listitem selectedUserOrg) {
		this.selectedUserOrg = selectedUserOrg;
	}

	public List<String> getUserOrgList() {
		return userOrgList;
	}

	public void setUserOrgList(List<String> userOrgList) {
		this.userOrgList = userOrgList;
	}

	
}
