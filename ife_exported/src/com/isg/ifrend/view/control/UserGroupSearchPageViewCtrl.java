/**
 * 
 */
package com.isg.ifrend.view.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;

import com.isg.ifrend.model.bean.SaUserGroup;
import com.isg.ifrend.model.bean.SaUserGroupHistory;
import com.isg.ifrend.model.bean.TmpSaUserGroup;
import com.isg.ifrend.security.utils.SecurityUtils;
import com.isg.ifrend.service.SaUserGroupHistoryManager;
import com.isg.ifrend.service.SaUserGroupManager;
import com.isg.ifrend.service.ServiceLocator;
import com.isg.ifrend.service.TmpSaUserGroupManager;
import com.isg.ifrend.utils.Commons;
import com.isg.ifrend.utils.DateUtil;
import com.isg.ifrend.utils.ExportUtil;
import com.isg.ifrend.utils.GlobalUtils;
import com.isg.ifrend.utils.SecurityAdminUtils;
import com.isg.ifrend.view.control.renderer.SaUserGroupListRenderer;

/**
 * @author gerald.deguzman
 *
 */
public class UserGroupSearchPageViewCtrl extends GenericForwardComposer {


	private static final long serialVersionUID = 1L;

	AnnotateDataBinder binder;

	private Listbox lstsausergroup;
	private Listbox list_sausergroup;
	private Bandbox bandbox;
	private Label lblinfo;
	private Image img_info;
	private Button btn_delete;
	private Button btn_back;

	private GlobalUtils globalUtils = new GlobalUtils();
	private SecurityAdminUtils securityAdminUtils = new SecurityAdminUtils();

	private TmpSaUserGroupManager tmp_sa_groupmanager = ServiceLocator.getTmpSaUserGroupManager();
	private SaUserGroupManager sa_groupmanager = ServiceLocator.getSaUserGroupManager();
	private SaUserGroupHistoryManager usergroup_history_manager = ServiceLocator.getSaUserGroupHistoryManager();

	private List<TmpSaUserGroup>lst_usergroup = new ArrayList<TmpSaUserGroup>();
	private List<SaUserGroup>lst_sausergroup = new ArrayList<SaUserGroup>();
	private ListModelList lml_tmpsausergroup;
	private ListModelList lml_sausergroup;

	//	String current_Date = SecurityAdminUtils.getCurrentDate();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		self.setAttribute(self.getId(), this);

		String lblInfo_ = (String) Executions.getCurrent().getSession().getAttribute(Commons.SA_LBLINFO);
		if(lblInfo_ != null) {
			lblinfo.setValue(securityAdminUtils.getMessage(lblInfo_));
			securityAdminUtils.setMsgStyle(img_info, lblinfo, false);
			Executions.getCurrent().getSession().removeAttribute(Commons.SA_LBLINFO);
		}

		Perform_Refresh();
		Apply_User_Roles();

		binder = new AnnotateDataBinder();
		binder.loadAll();

	}

	public Listbox getLstsausergroup() {
		return lstsausergroup;
	}

	public void setLstsausergroup(Listbox lstsausergroup) {
		this.lstsausergroup = lstsausergroup;
	}

	public List<TmpSaUserGroup> getLst_usergroup() {
		return lst_usergroup;
	}

	public void setLst_usergroup(List<TmpSaUserGroup> lst_usergroup) {
		this.lst_usergroup = lst_usergroup;
	}

	public ListModelList getLml_tmpsausergroup() {
		return lml_tmpsausergroup;
	}

	public void setLml_tmpsausergroup(ListModelList lml_tmpsausergroup) {
		this.lml_tmpsausergroup = lml_tmpsausergroup;
	}



	/*
	 * Additional feature. Can be activated if needed
	 * public void onChange$bandbox()throws InterruptedException{		

		if(!bandbox.getValue().isEmpty()){			
			Perform_Custom_Search(bandbox.getValue(), "", Commons.SA_STATUS_PENDING);
		}
	}*/

	public ListModelList getLml_sausergroup() {
		return lml_sausergroup;
	}

	public void setLml_sausergroup(ListModelList lml_sausergroup) {
		this.lml_sausergroup = lml_sausergroup;
	}

	public void onSelect$lstsausergroup()throws InterruptedException{
		bandbox.setValue(lstsausergroup.getSelectedItem().getLabel());
		bandbox.close();

		/*Additional feature. Can be activated if needed		
		Perform_Custom_Search(bandbox.getValue(), "", Commons.SA_STATUS_PENDING);
		 */
	}

	public void onClick$btn_export() throws InterruptedException{
		try {
			ExportUtil.exportToExcel("UserGroupList.xls", list_sausergroup);
		} catch (IOException e) {
			StringBuffer err_ = new StringBuffer();
			err_.append(securityAdminUtils.getMessage(Commons.SA_MSG_ERR_EXPORT_TO_EXCEL));
			err_.append(Commons.NEW_LINE);
			err_.append("Error:" + e.getMessage());
			Messagebox.show(err_.toString(), Commons.MSGBOX_TITLE_ERROR, Messagebox.OK, Messagebox.ERROR);			
		}
	}

	public void onClick$btn_back() throws InterruptedException{
		SecurityAdminUtils.Redirect_to_Page(Commons.URL_SECURITY_ADMIN);
	}

	public void onClick$btn_delete()throws InterruptedException{

		/**
		 * Multiple Delete (maker level)
		 * **/
		Messagebox.show(securityAdminUtils.getMessage(Commons.MSG_DELETE_SELECTED_ITEMS), 
				securityAdminUtils.getMessage(Commons.MSGBOX_TITLE_DELETE_RECORD), Messagebox.OK|Messagebox.CANCEL, Messagebox.QUESTION, new EventListener() {

			@Override
			public void onEvent(Event evt) throws Exception {
				if(evt.getName().equals("onOK")){
					Perform_Multiple_Delete();					
				}else{
					lblinfo.setValue("");
				}				
			}
		});		
	}

	public void onClick$btn_search()throws InterruptedException{
		Perform_Custom_Search(bandbox.getValue().toUpperCase(), "", "");
	}

	/**Capture the bandbox when Enter key was pressed**/
	public void onOK$bandbox()throws InterruptedException{
		onClick$btn_search();
	}	

	public void Perform_Custom_Search(String group_id, String action, String status){
		/*lst_usergroup = tmp_sa_groupmanager.getCustom_TmpSaUserGroupList(group_id, action, Commons.SA_STATUS_ACTIVE);*/
		/*lml_tmpsausergroup = new ListModelList(lst_usergroup);*/

		lst_sausergroup = sa_groupmanager.find_all_Active_Pending(group_id);
		lml_sausergroup = new ListModelList(lst_sausergroup);
		list_sausergroup.setModel(lml_sausergroup);
	}

	@SuppressWarnings("unchecked")
	public void Perform_Multiple_Delete() throws InterruptedException{

		StringBuffer errMsg = new StringBuffer();
		ListModelList lml = (ListModelList) list_sausergroup.getModel();		
		Set<SaUserGroup> set = lml.getSelection();

		if(set.isEmpty()){
			Messagebox.show(securityAdminUtils.getMessage(Commons.SA_SELECT_ITEM), 
					securityAdminUtils.getMessage(Commons.MSGBOX_TITLE_DELETE_RECORD), 
					Messagebox.OK, Messagebox.EXCLAMATION, new EventListener() {

				@Override
				public void onEvent(Event e) throws Exception {
					if(e.getName().equals("onOK")){
						Executions.getCurrent().getSession().setAttribute(Commons.SA_LBLINFO, "");
						Perform_Refresh();
					}
				}
			});
		}else{

			for(SaUserGroup t: set){
				TmpSaUserGroup parent = tmp_sa_groupmanager.getTmpSaUserGroup(t.getGroup_id());

				/*Check if there is already pending*/
				if(parent.getStatus().equals(Commons.SA_STATUS_PENDING)){
					errMsg.append(parent.getGroup_id() + Commons.NEW_LINE);
				}else{

					Date current_Date = DateUtil.getCurrentDate();
					parent.setMaker_id(SecurityAdminUtils.get_Logged_in_Username());				
					parent.setChecker_id("");
					parent.setDecision_dt("");
					parent.setModified_dt(DateUtil.format(current_Date));
					parent.setModifier_id(SecurityAdminUtils.get_Logged_in_Username());
					parent.setStatus(Commons.SA_STATUS_PENDING);
					parent.setAction(Commons.SA_ACTION_DELETE);


					/*Update history table*/
					SaUserGroupHistory hst_usergroup = new SaUserGroupHistory();
					hst_usergroup.setGroup_id(parent.getGroup_id());
					hst_usergroup.setDescription(parent.getDescription());
					/*hst_usergroup.setVcategory(parent.getVcategory());*/
					hst_usergroup.setMaker_id(SecurityAdminUtils.get_Logged_in_Username());
					hst_usergroup.setCreation_dt(parent.getCreation_dt());			
					hst_usergroup.setChecker_id("");
					hst_usergroup.setDecision_dt("");
					hst_usergroup.setModified_dt(DateUtil.format(current_Date));
					hst_usergroup.setModifier_id(SecurityAdminUtils.get_Logged_in_Username());
					hst_usergroup.setStatus(Commons.SA_STATUS_PENDING);
					hst_usergroup.setAction(Commons.SA_ACTION_DELETE);

					try {
						usergroup_history_manager.save(hst_usergroup);
						tmp_sa_groupmanager.save(parent);
						lblinfo.setValue(globalUtils.returnPropValue(Commons.MESSAGES_PROPERTIES_EN, Commons.SA_DELETE_SUCCESS));
						securityAdminUtils.setMsgStyle(img_info, lblinfo, false);
						Executions.getCurrent().getSession().setAttribute(Commons.SA_LBLINFO, Commons.SA_MULTI_DELETE_COMPLETE);
					} catch (Exception e) {
						lblinfo.setValue(globalUtils.returnPropValue(Commons.MESSAGES_PROPERTIES_EN, Commons.SA_DELETE_FAIL));
						securityAdminUtils.setMsgStyle(img_info, lblinfo, true);
						Executions.getCurrent().getSession().setAttribute(Commons.SA_LBLINFO, Commons.SA_MULTI_DELETE_FAIL);
					} 
				}	
			}
			if(errMsg.length() == 0){
				Executions.getCurrent().getSession().setAttribute(Commons.SA_LBLINFO, Commons.SA_MULTI_DELETE_COMPLETE);
				SecurityAdminUtils.Redirect_to_Page(Commons.URL_SEARCH_USERGROUP);
			}else{
				errMsg.append(securityAdminUtils.getMessage(Commons.SA_MSG_ERR_MULTI_PENDING_DELETE));
				Executions.getCurrent().getSession().setAttribute(Commons.SA_LBLINFO, "");
				Messagebox.show(errMsg.toString(), securityAdminUtils.getMessage(Commons.MSGBOX_TITLE_ERROR), Messagebox.OK, Messagebox.EXCLAMATION, new EventListener() {

					@Override
					public void onEvent(Event e) throws Exception {
						if(e.getName().equals("onOK")){
							SecurityAdminUtils.Redirect_to_Page(Commons.URL_SEARCH_USERGROUP);
						}						
					}
				});
			}
			
		}
	} 

	@SuppressWarnings("unchecked")
	public void Perform_Refresh(){

		lst_sausergroup = new ListModelList(); 
		lst_sausergroup = sa_groupmanager.find_all_Active_Pending("");
		lml_sausergroup = new ListModelList(lst_sausergroup);

		list_sausergroup.setItemRenderer(new SaUserGroupListRenderer());		
	}

	public void Apply_User_Roles(){

		boolean is_Maker = false;
		boolean is_Checker = false;

		is_Maker = SecurityUtils.getSecurityUtilsInstance().hasMakerRole(globalUtils.returnPropValue(Commons.GLOBAL_PROPERTIES, Commons.URL_UPDATE_USERGROUP));
		is_Checker = SecurityUtils.getSecurityUtilsInstance().hasCheckerRole(globalUtils.returnPropValue(Commons.GLOBAL_PROPERTIES, Commons.URL_UPDATE_USERGROUP));

		/*Enable all feature if is_Maker and is_Checker*/
		btn_delete.setVisible(true);			
		btn_back.setVisible(true);
		list_sausergroup.setCheckmark(true);

		if(is_Maker && !is_Checker){

			btn_delete.setVisible(true);			

		}

		if(!is_Maker && is_Checker){
			list_sausergroup.setCheckmark(false);
			btn_delete.setVisible(false);			
		}
	}
}
