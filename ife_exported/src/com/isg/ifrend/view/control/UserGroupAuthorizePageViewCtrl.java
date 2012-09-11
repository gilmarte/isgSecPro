package com.isg.ifrend.view.control;

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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.isg.ifrend.model.bean.ActionType;
import com.isg.ifrend.model.bean.SaSelectedFunctions;
import com.isg.ifrend.model.bean.SaUserGroup;
import com.isg.ifrend.model.bean.SaUserGroupHistory;
import com.isg.ifrend.model.bean.SelectedFunctions;
import com.isg.ifrend.model.bean.TmpSaUserGroup;
import com.isg.ifrend.security.utils.SecurityUtils;
import com.isg.ifrend.service.SaUserGroupHistoryManager;
import com.isg.ifrend.service.SaUserGroupManager;
import com.isg.ifrend.service.ServiceLocator;
import com.isg.ifrend.service.TmpSaUserGroupManager;
import com.isg.ifrend.utils.Commons;
import com.isg.ifrend.utils.DateUtil;
import com.isg.ifrend.utils.GlobalUtils;
import com.isg.ifrend.utils.SecurityAdminUtils;
import com.isg.ifrend.view.control.renderer.SaUserGroupAuthListRenderer2;

/**
 * @author gerald.deguzman
 *
 */
public class UserGroupAuthorizePageViewCtrl extends GenericForwardComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private AnnotateDataBinder binder;

	@SuppressWarnings("unused")
	private Window userGroupAuthorizePage;
	private Listbox list_sausergroup;
	@SuppressWarnings("unused")
	private Button btn_search;
	private Listbox lstsausergroup;
	private Bandbox bandbox;
	private Label lblinfo;
	private Image img_info;
	private Combobox cmbAction;
	@SuppressWarnings("unused")
	private Combobox cmbStatus;

	private Button btn_approve;
	private Button btn_reject;
	private Button btn_cancel;
	private Button btn_back;
	
	private List<String> actionList = new ArrayList<String>();

	private GlobalUtils globalUtils = new GlobalUtils();
	private SecurityAdminUtils securityAdminUtils = new SecurityAdminUtils();

	private TmpSaUserGroupManager tmp_sa_groupmanager = ServiceLocator.getTmpSaUserGroupManager();
	private SaUserGroupManager sa_groupmanager = ServiceLocator.getSaUserGroupManager();
	private SaUserGroupHistoryManager usergroup_history_manager = ServiceLocator.getSaUserGroupHistoryManager();


	private List<TmpSaUserGroup>lst_usergroup = new ArrayList<TmpSaUserGroup>();
	private ListModelList lml_tmpsausergroup = new ListModelList();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		self.setAttribute(self.getId(), this);

		/*lst_usergroup = new ListModelList(); 
		lst_usergroup = tmp_sa_groupmanager.getTmpSaUserGroupList();
		lml_tmpsausergroup = new ListModelList(lst_usergroup);

		list_sausergroup.setItemRenderer(new SaUserGroupListRenderer());*/
		for(ActionType action : ActionType.values()) {
			actionList.add(action.getDesc());
		}
		cmbAction.setText(ActionType.ALL.getDesc());

		String lblInfo_ = (String) Executions.getCurrent().getSession().getAttribute(Commons.SA_LBLINFO);
		if(lblInfo_ != null){
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

	public void onSelect$lstsausergroup()throws InterruptedException{
		bandbox.setValue(lstsausergroup.getSelectedItem().getLabel());
		bandbox.close();

		//Perform_Search(bandbox.getValue());
	}

	public void onClick$btn_add() throws InterruptedException{
		Executions.sendRedirect(globalUtils.returnPropValue(Commons.GLOBAL_PROPERTIES, Commons.URL_ADD_USERGROUP));		
	}

	/*public void onClick$btn_delete()throws InterruptedException{

		Messagebox.show("Delete selected items?", "Delete", Messagebox.OK|Messagebox.CANCEL, "", new EventListener() {

			@Override
			public void onEvent(Event evt) throws Exception {
				if(evt.getName().equals("onOK")){
					Perform_Multiple_Delete();
					Perform_Refresh();
				}else{
					lblinfo.setValue("");
				}				
			}
		});		
	}*/

	public void onClick$btn_cancel() throws InterruptedException{
		/**
		 * Cancel Function (Maker Level)
		 * **/
		Messagebox.show(securityAdminUtils.getMessage(Commons.SA_MSG_CANCEL_SELECTED_USERGROUP), securityAdminUtils.getMessage(Commons.MSGBOX_TITLE_AUTHORIZE), Messagebox.OK|Messagebox.CANCEL, Messagebox.QUESTION, new EventListener() {

			@Override
			public void onEvent(Event e) throws Exception {

				if(e.getName().equals("onOK")){
					Perform_Multiple_Cancel();
				}
			}
		});

	}

	public void onClick$btn_search()throws InterruptedException{
		Perform_Custom_Search(bandbox.getValue(), cmbAction.getValue(), Commons.SA_STATUS_PENDING);
	}

	public void onClick$btn_back()throws InterruptedException{
		SecurityAdminUtils.Redirect_to_Page(Commons.URL_SECURITY_ADMIN);
	}	

	public void onClick$btn_approve() throws InterruptedException{

		Messagebox.show(securityAdminUtils.getMessage(Commons.SA_MSG_APPROVE_SELECTED_USERGROUP), securityAdminUtils.getMessage(Commons.MSGBOX_TITLE_AUTHORIZE), Messagebox.OK|Messagebox.CANCEL, Messagebox.QUESTION, new EventListener() {

			@Override
			public void onEvent(Event e) throws Exception {
				if(e.getName().equals("onOK"))
					Perform_Multiple_Approve();
			}
		});		
	}

	public void onClick$btn_reject() throws InterruptedException{
		Messagebox.show(securityAdminUtils.getMessage(Commons.SA_MSG_REJECT_SELECTED_USERGROUP), securityAdminUtils.getMessage(Commons.MSGBOX_TITLE_AUTHORIZE), Messagebox.OK|Messagebox.CANCEL, Messagebox.QUESTION, new EventListener() {

			@Override
			public void onEvent(Event e) throws Exception {
				if(e.getName().equals("onOK"))
					Perform_Multiple_Reject();
			}
		});	
	}

	/**Capture the bandbox when Enter key was pressed**/
	public void onOK$bandbox()throws InterruptedException{
		onClick$btn_search();
	}

	public void Perform_Search(String group_id){
		lst_usergroup = tmp_sa_groupmanager.getTmpSaUserGroupListById(group_id);
		lml_tmpsausergroup = new ListModelList(lst_usergroup);
		list_sausergroup.setModel(lml_tmpsausergroup);
	}

	public void Perform_Custom_Search(String group_id, String action, String status){
		lst_usergroup = tmp_sa_groupmanager.getAuth_Search_Custom_TmpSaUserGroupList(group_id, action, Commons.SA_STATUS_PENDING);
		lml_tmpsausergroup = new ListModelList(lst_usergroup);
		list_sausergroup.setModel(lml_tmpsausergroup);
	}

	@SuppressWarnings("unchecked")
	public void Perform_Multiple_Cancel() throws InterruptedException{
		/*
		 * Multiple Delete (Maker Level)
		 * 
		 */

		StringBuffer errMsg = new StringBuffer();
		ListModelList lml = (ListModelList) list_sausergroup.getModel();		
		Set<TmpSaUserGroup> set = lml.getSelection();

		if(set.isEmpty()){
			Messagebox.show(securityAdminUtils.getMessage(Commons.SA_SELECT_ITEM), 
					securityAdminUtils.getMessage(Commons.MSGBOX_TITLE_CANCEL), 
					Messagebox.OK, Messagebox.EXCLAMATION, new EventListener() {

				@Override
				public void onEvent(Event e) throws Exception {
					if(e.getName().equals("onOK")){
						Executions.getCurrent().getSession().setAttribute(Commons.SA_LBLINFO, "");
						SecurityAdminUtils.Redirect_to_Page(Commons.URL_AUTHORIZE_USERGROUP);
					}							
				}
			});
		}else{

			for(TmpSaUserGroup t: set){
				TmpSaUserGroup t_usergroup = tmp_sa_groupmanager.getTmpSaUserGroup(t.getGroup_id());
				SaUserGroup mvar_usergroup = sa_groupmanager.getSaUserGroup(t.getGroup_id());

				if(t_usergroup != null){

					if(t_usergroup.getModifier_id().equalsIgnoreCase(SecurityAdminUtils.get_Logged_in_Username())){				

						if(t_usergroup.getStatus().equals(Commons.SA_STATUS_PENDING)){

							if(mvar_usergroup == null){
								t_usergroup.setStatus(Commons.SA_STATUS_CANCELLED);

								/*Update history table*/
								SaUserGroupHistory hst_usergroup = new SaUserGroupHistory();
								hst_usergroup.setGroup_id(t_usergroup.getGroup_id());
								hst_usergroup.setDescription(t_usergroup.getDescription());
								hst_usergroup.setMaker_id(SecurityAdminUtils.get_Logged_in_Username());
								hst_usergroup.setCreation_dt(t_usergroup.getCreation_dt());			
								hst_usergroup.setChecker_id("");
								hst_usergroup.setDecision_dt("");
								hst_usergroup.setModified_dt("");
								hst_usergroup.setStatus(Commons.SA_STATUS_CANCELLED);
								hst_usergroup.setAction(t_usergroup.getAction());

								usergroup_history_manager.save(hst_usergroup);								
								tmp_sa_groupmanager.delete(t_usergroup);
								lblinfo.setValue(securityAdminUtils.getMessage(Commons.SA_CANCEL_SUCCESS));
								securityAdminUtils.setMsgStyle(img_info, lblinfo, false);

							}else{						

								/*Copy Master to Temp*/
								t_usergroup.setDescription(mvar_usergroup.getDescription());
								t_usergroup.setAction(mvar_usergroup.getAction());
								t_usergroup.setStatus(mvar_usergroup.getStatus());
								t_usergroup.setMaker_id(mvar_usergroup.getMaker_id());
								t_usergroup.setCreation_dt(mvar_usergroup.getCreation_dt());
								t_usergroup.setChecker_id(mvar_usergroup.getChecker_id());
								t_usergroup.setDecision_dt(mvar_usergroup.getDecision_dt());
								t_usergroup.setModified_dt(mvar_usergroup.getModified_dt());

								List<SaSelectedFunctions> mvar_usrgrp_list = new ArrayList<SaSelectedFunctions>();
								mvar_usrgrp_list = mvar_usergroup.getSa_selectedfunctions();

								t_usergroup.getSelectedfunctions().removeAll(t_usergroup.getSelectedfunctions());				
								tmp_sa_groupmanager.save(t_usergroup);

								for(SaSelectedFunctions child: mvar_usrgrp_list){
									String ch_group_id = t_usergroup.getGroup_id();
									Integer ch_function_id = child.getFunction_id();
									String ch_description = child.getDescription();
									String ch_function_name = child.getFunction_name();

									SelectedFunctions childbean = new SelectedFunctions(ch_group_id, ch_function_id, ch_description,ch_function_name, t_usergroup);							
									t_usergroup.getSelectedfunctions().add(childbean);		
								}

								/*Update history table*/
								SaUserGroupHistory hst_usergroup = new SaUserGroupHistory();
								hst_usergroup.setGroup_id(t_usergroup.getGroup_id());
								hst_usergroup.setDescription(t_usergroup.getDescription());
								hst_usergroup.setMaker_id(t_usergroup.getMaker_id());
								hst_usergroup.setCreation_dt(t_usergroup.getCreation_dt());			
								hst_usergroup.setChecker_id("");
								hst_usergroup.setDecision_dt("");
								hst_usergroup.setModified_dt("");
								hst_usergroup.setStatus(Commons.SA_STATUS_CANCELLED);
								hst_usergroup.setAction(t_usergroup.getAction());

								try {
									usergroup_history_manager.save(hst_usergroup);
									tmp_sa_groupmanager.save(t_usergroup);
									lblinfo.setValue(securityAdminUtils.getMessage(Commons.SA_CANCEL_SUCCESS));
									securityAdminUtils.setMsgStyle(img_info, lblinfo, false);

								} catch (Exception e) {
									lblinfo.setValue(securityAdminUtils.getMessage(Commons.SA_CANCEL_FAIL));
									securityAdminUtils.setMsgStyle(img_info, lblinfo, true);
									/*Executions.getCurrent().getSession().setAttribute(Commons.SA_LBLINFO, Commons.SA_MULTI_CANCELLATION_FAIL);*/
									e.printStackTrace();
								}
							}							
						}					
					}else{
						errMsg.append(t_usergroup.getGroup_id() + Commons.NEW_LINE);
						/*String msg_ = "Unable to cancel group : " + t_usergroup.getGroup_id() + 
						"\n Reason: The record is owned by another user.";
						lblinfo.setValue(msg_);	*/					
					}
				}
			}
			if(errMsg.length() == 0){
				Executions.getCurrent().getSession().setAttribute(Commons.SA_LBLINFO, Commons.SA_MULTI_CANCELLATION_COMPLETE);
				SecurityAdminUtils.Redirect_to_Page(Commons.URL_AUTHORIZE_USERGROUP);
			}else{
				errMsg.append(securityAdminUtils.getMessage(Commons.SA_MSG_ERR_CHKER_CANCEL_OWN));
				Executions.getCurrent().getSession().setAttribute(Commons.SA_LBLINFO, "");
				Messagebox.show(errMsg.toString(), securityAdminUtils.getMessage(Commons.MSGBOX_TITLE_ERROR), Messagebox.OK, Messagebox.EXCLAMATION, new EventListener() {

					@Override
					public void onEvent(Event e) throws Exception {
						if(e.getName().equals("onOK")){
							SecurityAdminUtils.Redirect_to_Page(Commons.URL_AUTHORIZE_USERGROUP);
						}						
					}
				});
			}	
		}
	}

	@SuppressWarnings("unchecked")
	public void Perform_Multiple_Approve() throws InterruptedException{
		/**
		 * MULTIPLE APPROVE (CHECKER LEVEL)
		 * 
		 * **/
		StringBuffer errMsg = new StringBuffer();
		ListModelList lml = (ListModelList) list_sausergroup.getModel();
		Set<TmpSaUserGroup> set = lml.getSelection();
		SaUserGroup mvar_sausergroup;

		if(set.isEmpty()){
			Messagebox.show(securityAdminUtils.getMessage(Commons.SA_SELECT_ITEM), 
					securityAdminUtils.getMessage(Commons.MSGBOX_TITLE_AUTHORIZE), 
					Messagebox.OK, Messagebox.EXCLAMATION, new EventListener() {

				@Override
				public void onEvent(Event e) throws Exception {
					if(e.getName().equals("onOK")){
						Executions.getCurrent().getSession().setAttribute(Commons.SA_LBLINFO, "");
						SecurityAdminUtils.Redirect_to_Page(Commons.URL_AUTHORIZE_USERGROUP);
					}							
				}
			});
		}else{

			for(TmpSaUserGroup t: set){
				/*Get beans per list*/
				String group_id_ = t.getGroup_id();

				TmpSaUserGroup t_usergroup = tmp_sa_groupmanager.getTmpSaUserGroup(group_id_);
				mvar_sausergroup = sa_groupmanager.getSaUserGroup(group_id_);

				/**Avoid letting checker approve his own request**/
				if(SecurityAdminUtils.get_Logged_in_Username().equalsIgnoreCase(t_usergroup.getModifier_id())){
					/*Messagebox.show(saUtils.getMessage(Commons.SA_MSG_ERR_CHKER_APPROVE_OWN), saUtils.getMessage(Commons.MSGBOX_TITLE_ERROR), Messagebox.OK, Messagebox.EXCLAMATION);*/
					errMsg.append(t_usergroup.getGroup_id() + Commons.NEW_LINE);
				}else{

					if(mvar_sausergroup == null){
						mvar_sausergroup = new SaUserGroup();
					}

					//mvar_sausergroup = new SaUserGroup();
					mvar_sausergroup.setGroup_id(t_usergroup.getGroup_id());
					mvar_sausergroup.setDescription(t_usergroup.getDescription());
					mvar_sausergroup.setAction(t_usergroup.getAction());			
					mvar_sausergroup.setMaker_id(t_usergroup.getMaker_id());					

					/*Set Approver*/			
					mvar_sausergroup.setChecker_id(SecurityAdminUtils.get_Logged_in_Username());
					t_usergroup.setChecker_id(SecurityAdminUtils.get_Logged_in_Username());

					/*Set DateTime value*/
					Date current_DateTime = DateUtil.getCurrentDate();

					mvar_sausergroup.setCreation_dt(t_usergroup.getCreation_dt());
					mvar_sausergroup.setDecision_dt(DateUtil.format(current_DateTime));
					t_usergroup.setDecision_dt(DateUtil.format(current_DateTime));

					mvar_sausergroup.setModified_dt(t_usergroup.getModified_dt());
					mvar_sausergroup.setModifier_id(t_usergroup.getModifier_id());

					/*
					 * Do something based from the actions to perform
					 * */
					if((t_usergroup.getAction().equals(Commons.SA_ACTION_ADD) || t_usergroup.getAction().equals(Commons.SA_ACTION_UPDATE)) 
							&& t_usergroup.getStatus().equals(Commons.SA_STATUS_PENDING)){

						Perform_Authorized_Add_Update(t_usergroup, mvar_sausergroup);

					}else if(t_usergroup.getAction().equals(Commons.SA_ACTION_DELETE) && t_usergroup.getStatus().equals(Commons.SA_STATUS_PENDING)){

						Perform_Authorized_Delete(t_usergroup, mvar_sausergroup);
					}
				}	
			}
			if(errMsg.length() == 0){
				Executions.getCurrent().getSession().setAttribute(Commons.SA_LBLINFO, Commons.SA_MULTI_APPROVE_COMPLETE);
				SecurityAdminUtils.Redirect_to_Page(Commons.URL_AUTHORIZE_USERGROUP);
			}else{
				errMsg.append(securityAdminUtils.getMessage(Commons.SA_MSG_ERR_CHKER_REJECT_OWN));
				Executions.getCurrent().getSession().setAttribute(Commons.SA_LBLINFO, "");
				Messagebox.show(errMsg.toString(), securityAdminUtils.getMessage(Commons.MSGBOX_TITLE_ERROR), Messagebox.OK, Messagebox.EXCLAMATION, new EventListener() {

					@Override
					public void onEvent(Event e) throws Exception {
						if(e.getName().equals("onOK")){
							SecurityAdminUtils.Redirect_to_Page(Commons.URL_AUTHORIZE_USERGROUP);
						}						
					}
				});
			}			

		}		
	}

	@SuppressWarnings("unchecked")
	public void Perform_Multiple_Reject() throws InterruptedException{
		/*
		 * Perform Multiple Reject (Checker Level)
		 * */

		StringBuffer errMsg = new StringBuffer();
		ListModelList lml = (ListModelList) list_sausergroup.getModel();		
		Set<TmpSaUserGroup> set = lml.getSelection();



		if(set.isEmpty()){
			Messagebox.show(securityAdminUtils.getMessage(Commons.SA_SELECT_ITEM), 
					securityAdminUtils.getMessage(Commons.MSGBOX_TITLE_AUTHORIZE), 
					Messagebox.OK, "", new EventListener() {

				@Override
				public void onEvent(Event e) throws Exception {
					if(e.getName().equals("onOK")){
						Executions.getCurrent().getSession().setAttribute(Commons.SA_LBLINFO, "");
						SecurityAdminUtils.Redirect_to_Page(Commons.URL_AUTHORIZE_USERGROUP);
					}							
				}
			});
		}else{

			for(TmpSaUserGroup t: set){
				/*Get beans per list*/
				String group_id_ = t.getGroup_id();

				TmpSaUserGroup t_usergroup = tmp_sa_groupmanager.getTmpSaUserGroup(group_id_);
				SaUserGroup mvar_usergroup = sa_groupmanager.getSaUserGroup(group_id_);

				/**Avoid letting checker reject his own request**/
				if(SecurityAdminUtils.get_Logged_in_Username().equalsIgnoreCase(t_usergroup.getModifier_id())){
					/*Messagebox.show(saUtils.getMessage(Commons.SA_MSG_ERR_CHKER_APPROVE_OWN), saUtils.getMessage(Commons.MSGBOX_TITLE_ERROR), Messagebox.OK, Messagebox.EXCLAMATION);*/
					errMsg.append(t_usergroup.getGroup_id() + Commons.NEW_LINE);
				}else{
					if(t_usergroup != null){
						if(t_usergroup.getStatus().equals(Commons.SA_STATUS_PENDING)){

							if(mvar_usergroup == null){
								/*
								 * If no master record found then delete from temp table
								 * */
								t_usergroup.setStatus(Commons.SA_STATUS_REJECTED);

								/*Update history table*/
								SaUserGroupHistory hst_usergroup = new SaUserGroupHistory();
								hst_usergroup.setGroup_id(t_usergroup.getGroup_id());
								hst_usergroup.setDescription(t_usergroup.getDescription());
								hst_usergroup.setMaker_id(t_usergroup.getMaker_id());
								hst_usergroup.setCreation_dt(t_usergroup.getCreation_dt());			
								hst_usergroup.setChecker_id(SecurityAdminUtils.get_Logged_in_Username());
								hst_usergroup.setDecision_dt(DateUtil.format(DateUtil.getCurrentDate()));
								hst_usergroup.setModified_dt(t_usergroup.getModified_dt());
								hst_usergroup.setStatus(Commons.SA_STATUS_REJECTED);
								hst_usergroup.setAction(t_usergroup.getAction());

								usergroup_history_manager.save(hst_usergroup);
								tmp_sa_groupmanager.delete(t_usergroup);

								lblinfo.setValue(securityAdminUtils.getMessage(Commons.SA_REJECT_SUCCESS));
								securityAdminUtils.setMsgStyle(img_info, lblinfo, false);
								//Executions.getCurrent().getSession().setAttribute(Commons.SA_LBLINFO, Commons.SA_MULTI_REJECT_COMPLETE);
							}else{
								/*
								 * If master exists and temp was rejected simply copy master to temp
								 * */

								t_usergroup.setDescription(mvar_usergroup.getDescription());
								t_usergroup.setMaker_id(mvar_usergroup.getMaker_id());
								t_usergroup.setCreation_dt(mvar_usergroup.getCreation_dt());
								t_usergroup.setChecker_id(mvar_usergroup.getChecker_id());
								t_usergroup.setAction(mvar_usergroup.getAction());
								t_usergroup.setStatus(mvar_usergroup.getStatus());
								t_usergroup.setDecision_dt(mvar_usergroup.getDecision_dt());
								t_usergroup.setModified_dt(mvar_usergroup.getModified_dt());


								/*Update history table*/
								SaUserGroupHistory hst_usergroup = new SaUserGroupHistory();
								hst_usergroup.setGroup_id(t_usergroup.getGroup_id());
								hst_usergroup.setDescription(t_usergroup.getDescription());
								hst_usergroup.setMaker_id(t_usergroup.getMaker_id());
								hst_usergroup.setCreation_dt(t_usergroup.getCreation_dt());			
								hst_usergroup.setChecker_id(SecurityAdminUtils.get_Logged_in_Username());
								hst_usergroup.setDecision_dt(DateUtil.format(DateUtil.getCurrentDate()));
								hst_usergroup.setModified_dt(t_usergroup.getModified_dt());
								hst_usergroup.setStatus(Commons.SA_STATUS_REJECTED);
								hst_usergroup.setAction(t_usergroup.getAction());


								List<SaSelectedFunctions>mvar_usrgrp_list = new ArrayList<SaSelectedFunctions>();
								mvar_usrgrp_list = mvar_usergroup.getSa_selectedfunctions();

								if(t_usergroup != null){
									t_usergroup.getSelectedfunctions().removeAll(t_usergroup.getSelectedfunctions());				
									tmp_sa_groupmanager.save(t_usergroup);

									for(SaSelectedFunctions child: mvar_usrgrp_list){
										String ch_group_id = t_usergroup.getGroup_id();
										Integer ch_function_id = child.getFunction_id();
										String ch_description = child.getDescription();
										String ch_function_name = child.getFunction_name();

										SelectedFunctions childbean = new SelectedFunctions(ch_group_id, ch_function_id, ch_description,ch_function_name, t_usergroup);							
										t_usergroup.getSelectedfunctions().add(childbean);		
									}


									try {										

										usergroup_history_manager.save(hst_usergroup);
										tmp_sa_groupmanager.save(t_usergroup);						
										lblinfo.setValue(securityAdminUtils.getMessage(Commons.SA_REJECT_SUCCESS));
										securityAdminUtils.setMsgStyle(img_info, lblinfo, false);
										//Executions.getCurrent().getSession().setAttribute(Commons.SA_LBLINFO, Commons.SA_MULTI_REJECT_COMPLETE);
									} catch (Exception e) {
										lblinfo.setValue(securityAdminUtils.getMessage(Commons.SA_REJECT_FAIL));
										securityAdminUtils.setMsgStyle(img_info, lblinfo, true);
										//Executions.getCurrent().getSession().setAttribute(Commons.SA_LBLINFO, Commons.SA_MULTI_REJECT_FAIL);
										break;
									}
								}
							}
						}					
					}			
				}
			}
			if(errMsg.length() == 0){
				Executions.getCurrent().getSession().setAttribute(Commons.SA_LBLINFO, Commons.SA_MULTI_REJECT_COMPLETE);
				SecurityAdminUtils.Redirect_to_Page(Commons.URL_AUTHORIZE_USERGROUP);
			}else{
				errMsg.append(securityAdminUtils.getMessage(Commons.SA_MSG_ERR_CHKER_REJECT_OWN));
				Executions.getCurrent().getSession().setAttribute(Commons.SA_LBLINFO, "");
				Messagebox.show(errMsg.toString(), securityAdminUtils.getMessage(Commons.MSGBOX_TITLE_ERROR), Messagebox.OK, Messagebox.EXCLAMATION, new EventListener() {

					@Override
					public void onEvent(Event e) throws Exception {
						if(e.getName().equals("onOK")){
							SecurityAdminUtils.Redirect_to_Page(Commons.URL_AUTHORIZE_USERGROUP);
						}						
					}
				});

			}

		}		
	}

	public void Perform_Authorized_Add_Update(TmpSaUserGroup t_usergroup_, SaUserGroup sa_usergroup_){
		/**
		 * AUTHORIZED ADD - Set status to ACTIVE
		 * **/

		List<SelectedFunctions>mvar_usrgrp_list = new ArrayList<SelectedFunctions>();
		mvar_usrgrp_list.addAll(t_usergroup_.getSelectedfunctions());

		t_usergroup_.getSelectedfunctions().removeAll(t_usergroup_.getSelectedfunctions());
		sa_usergroup_.getSa_selectedfunctions().removeAll(sa_usergroup_.getSa_selectedfunctions());


		if(sa_usergroup_.getAction().equals(Commons.SA_ACTION_UPDATE)){
			/*
			 * Only Perform this when doing update
			 * */
			tmp_sa_groupmanager.save(t_usergroup_);
			sa_groupmanager.save(sa_usergroup_);				
		}

		for(SelectedFunctions child: mvar_usrgrp_list){
			String ch_group_id = t_usergroup_.getGroup_id();
			Integer ch_function_id = child.getFunction_id();
			String ch_description = child.getDescription();
			String ch_function_name = child.getFunction_name();

			SelectedFunctions sa_childbean = new SelectedFunctions(ch_group_id, ch_function_id, ch_description,ch_function_name, t_usergroup_);							
			t_usergroup_.getSelectedfunctions().add(sa_childbean);		
		}			


		for(SelectedFunctions child: t_usergroup_.getSelectedfunctions()){
			String ch_group_id = sa_usergroup_.getGroup_id();
			Integer ch_function_id = child.getFunction_id();
			String ch_description = child.getDescription();
			String ch_function_name = child.getFunction_name();

			SaSelectedFunctions childbean = new SaSelectedFunctions(ch_group_id, ch_function_id, ch_description,ch_function_name, sa_usergroup_);							
			sa_usergroup_.getSa_selectedfunctions().add(childbean);		
		}

		/*Update history table*/
		SaUserGroupHistory hst_usergroup = new SaUserGroupHistory();
		hst_usergroup.setGroup_id(t_usergroup_.getGroup_id());
		hst_usergroup.setDescription(t_usergroup_.getDescription());
		hst_usergroup.setMaker_id(t_usergroup_.getMaker_id());
		hst_usergroup.setCreation_dt(t_usergroup_.getCreation_dt());			
		hst_usergroup.setChecker_id(SecurityAdminUtils.get_Logged_in_Username());
		hst_usergroup.setDecision_dt(DateUtil.format(DateUtil.getCurrentDate()));
		hst_usergroup.setModified_dt(t_usergroup_.getModified_dt());
		hst_usergroup.setStatus(Commons.SA_STATUS_ACTIVE);
		hst_usergroup.setAction(t_usergroup_.getAction());

		try {			

			/*Set status*/
			t_usergroup_.setStatus(Commons.SA_STATUS_ACTIVE);
			sa_usergroup_.setStatus(Commons.SA_STATUS_ACTIVE);
			
			/*Just set actions*/
			t_usergroup_.setAction(Commons.SA_STATUS_APPROVED);
			sa_usergroup_.setAction(Commons.SA_STATUS_APPROVED);
			
			usergroup_history_manager.save(hst_usergroup);
			tmp_sa_groupmanager.save(t_usergroup_);
			sa_groupmanager.save(sa_usergroup_);

			//Executions.getCurrent().getSession().setAttribute(Commons.SA_LBLINFO, Commons.SA_MULTI_APPROVE_COMPLETE);
			lblinfo.setValue(securityAdminUtils.getMessage(Commons.SA_APPROVE_SUCCESS));
			securityAdminUtils.setMsgStyle(img_info, lblinfo, false);
		} catch (Exception e) {
			//Executions.getCurrent().getSession().setAttribute(Commons.SA_LBLINFO, Commons.SA_MULTI_APPROVE_FAIL);
			lblinfo.setValue(securityAdminUtils.getMessage(Commons.SA_MULTI_APPROVE_FAIL));
			securityAdminUtils.setMsgStyle(img_info, lblinfo, true);
			e.printStackTrace();
		}

	}

	public void Perform_Authorized_Delete(TmpSaUserGroup t_usergroup_, SaUserGroup sa_usergroup_){

		/**
		 * AUTHORIZED DELETE - Only change the master record's status to deleted and dont delete in the 
		 * database.
		 * **/


		List<SelectedFunctions>mvar_usrgrp_list = new ArrayList<SelectedFunctions>();
		mvar_usrgrp_list.addAll(t_usergroup_.getSelectedfunctions());


		/**Delete the child first though this is not necessarry but I just included it anyway**/
		t_usergroup_.getSelectedfunctions().removeAll(t_usergroup_.getSelectedfunctions());
		sa_usergroup_.getSa_selectedfunctions().removeAll(sa_usergroup_.getSa_selectedfunctions());

		/*Update history table*/
		SaUserGroupHistory hst_usergroup = new SaUserGroupHistory();
		hst_usergroup.setGroup_id(t_usergroup_.getGroup_id());
		hst_usergroup.setDescription(t_usergroup_.getDescription());
		hst_usergroup.setMaker_id(t_usergroup_.getMaker_id());
		hst_usergroup.setCreation_dt(t_usergroup_.getCreation_dt());			
		hst_usergroup.setChecker_id(SecurityAdminUtils.get_Logged_in_Username());
		hst_usergroup.setDecision_dt(DateUtil.format(DateUtil.getCurrentDate()));
		hst_usergroup.setModified_dt(t_usergroup_.getModified_dt());
		hst_usergroup.setStatus(Commons.SA_STATUS_DELETED);
		hst_usergroup.setAction(t_usergroup_.getAction());

		try {
			t_usergroup_.setStatus(Commons.SA_STATUS_DELETED);
			sa_usergroup_.setStatus(Commons.SA_STATUS_DELETED);

			/*Just set actions*/
			t_usergroup_.setAction(Commons.SA_STATUS_APPROVED);
			sa_usergroup_.setAction(Commons.SA_STATUS_APPROVED);
			
			usergroup_history_manager.save(hst_usergroup);
			tmp_sa_groupmanager.save(t_usergroup_);
			sa_groupmanager.save(sa_usergroup_);

			//Executions.getCurrent().getSession().setAttribute(Commons.SA_LBLINFO, Commons.SA_MULTI_APPROVE_COMPLETE);
			lblinfo.setValue(Commons.SA_DELETE_SUCCESS);
			securityAdminUtils.setMsgStyle(img_info, lblinfo, false);
		} catch (Exception e) {
			//Executions.getCurrent().getSession().setAttribute(Commons.SA_LBLINFO, Commons.SA_MULTI_APPROVE_FAIL);
			lblinfo.setValue(Commons.SA_DELETE_FAIL);
			securityAdminUtils.setMsgStyle(img_info, lblinfo, true);
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	public void Perform_Refresh(){

		lst_usergroup = new ListModelList(); 
		lst_usergroup = tmp_sa_groupmanager.getAuth_Search_Custom_TmpSaUserGroupList("", "", Commons.SA_STATUS_PENDING);
		lml_tmpsausergroup = new ListModelList(lst_usergroup);

		/*list_sausergroup.setItemRenderer(new SaUserGroupAuthListRenderer());*/
		list_sausergroup.setItemRenderer(new SaUserGroupAuthListRenderer2());
	}

	public void Apply_User_Roles(){

		boolean is_Maker = false;
		boolean is_Checker = false;

		is_Maker = SecurityUtils.getSecurityUtilsInstance().hasMakerRole(securityAdminUtils.getGlobalString(Commons.URL_UPDATE_USERGROUP));
		is_Checker = SecurityUtils.getSecurityUtilsInstance().hasCheckerRole(securityAdminUtils.getGlobalString(Commons.URL_UPDATE_USERGROUP));

		btn_approve.setVisible(true);
		btn_reject.setVisible(true);
		btn_cancel.setVisible(true);
		btn_back.setVisible(true);

		if(is_Maker && !is_Checker){
			btn_approve.setVisible(false);
			btn_reject.setVisible(false);

		}

		if(!is_Maker && is_Checker){
			btn_cancel.setVisible(false);			
		}
	}

	public List<String> getActionList() {
		return actionList;
	}

	public void setActionList(List<String> actionList) {
		this.actionList = actionList;
	}

}
