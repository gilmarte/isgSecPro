package com.isg.ifrend.view.control;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.isg.ifrend.model.bean.Functions;
import com.isg.ifrend.model.bean.SaSelectedFunctions;
import com.isg.ifrend.model.bean.SaUserGroup;
import com.isg.ifrend.model.bean.SaUserGroupHistory;
import com.isg.ifrend.model.bean.SelectedFunctions;
import com.isg.ifrend.model.bean.TmpSaUserGroup;
import com.isg.ifrend.security.utils.SecurityUtils;
import com.isg.ifrend.service.FunctionsManager;
import com.isg.ifrend.service.SaUserGroupHistoryManager;
import com.isg.ifrend.service.SaUserGroupManager;
import com.isg.ifrend.service.ServiceLocator;
import com.isg.ifrend.service.TmpSaUserGroupManager;
import com.isg.ifrend.utils.Commons;
import com.isg.ifrend.utils.DateUtil;
import com.isg.ifrend.utils.GlobalUtils;
import com.isg.ifrend.utils.SecurityAdminUtils;

/**
 * @author gerald.deguzman
 *
 */
public class UserGroupEditPageViewCtrl extends GenericForwardComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Image movealltodest;
	private Image movetodest;
	private Image movetosrc;
	private Image movealltosrc;
	
	private Textbox txtGroupDesc;	
	private Textbox groupId;

	private Button btn_update_top;
	private Button btn_delete_top;	
	private Button btn_cancel_top;
	private Button btn_approve_top;
	private Button btn_reject_top;
	//	private Button btn_reset_top;
	private Button btn_back_top;
	
	private Button btn_update_foot;
	private Button btn_delete_foot;	
	private Button btn_cancel_foot;
	private Button btn_approve_foot;
	private Button btn_reject_foot;
	//	private Button btn_reset_foot;
	private Button btn_back_foot;

	/*private Button btn_update_foot;
	private Button btn_delete_foot;	
	private Button btn_cancel_foot;
	private Button btn_approve_foot;
	private Button btn_reject_foot;
	private Button btn_back_foot;*/

	private Label lblinfo;
	private Image img_info;
	private Listbox dst;
	private Listbox src;

	private TmpSaUserGroup t_usergroup;
	private SaUserGroup sa_usergroup;
	private List<Functions>functionlist = new ArrayList<Functions>();	
	private List<Functions>tmp_functionlist = new ArrayList<Functions>();
	private List<SelectedFunctions>selectedfunctionslist = new ArrayList<SelectedFunctions>();
	private List<SaSelectedFunctions>sa_selectedfunctionslist = new ArrayList<SaSelectedFunctions>();

	private List<Object>lst_requiredfields = new ArrayList<Object>();

	private ListModelList lml_functionlist;
	private ListModelList lml_selectedfunctions;

	private FunctionsManager fmanager = ServiceLocator.getFunctionsManager();
//	private SelectedFunctionsManager selectedfunctionsmanager = ServiceLocator.getSelectedFunctionsManager();
	private TmpSaUserGroupManager tmp_sa_groupmanager = ServiceLocator.getTmpSaUserGroupManager();
	private SaUserGroupManager sa_groupmanager = ServiceLocator.getSaUserGroupManager();	

	private SaUserGroupHistoryManager usergroup_history_manager = ServiceLocator.getSaUserGroupHistoryManager();

	private GlobalUtils globalUtils = new GlobalUtils();
	private SecurityAdminUtils securityAdminUtils = new SecurityAdminUtils();

	//boolean saveSuccess = false;
	//boolean isOk = false;
	//	private Date current_Date;
	private String previous_page = "";

	private AnnotateDataBinder binder;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);


		/*String gp = (String) session.getAttribute("group_id");*/
		String _group_id = Executions.getCurrent().getParameter("group_id");		
		previous_page = Executions.getCurrent().getParameter(Commons.PREVIOUS_PAGE);

		/*for functionslist reference table*/
		functionlist = fmanager.getFunctionsList();

		/*get group and child*/
		t_usergroup = tmp_sa_groupmanager.getTmpSaUserGroup(_group_id);
		sa_usergroup = sa_groupmanager.getSaUserGroup(_group_id);

		selectedfunctionslist = t_usergroup.getSelectedfunctions();


		if(previous_page.equalsIgnoreCase("search_page")){
			if(sa_usergroup != null){
				groupId.setText(sa_usergroup.getGroup_id());
				txtGroupDesc.setText(sa_usergroup.getDescription());

				sa_selectedfunctionslist = sa_usergroup.getSa_selectedfunctions();	
				selectedfunctionslist.clear();

				if(sa_selectedfunctionslist.size() > 0){
					for(SaSelectedFunctions sasf_: sa_selectedfunctionslist){
						SelectedFunctions sf_ = new SelectedFunctions();
						sf_.setFunction_id(sasf_.getFunction_id());
						sf_.setFunction_name(sasf_.getFunction_name());
						sf_.setDescription(sasf_.getDescription());

						selectedfunctionslist.add(sf_);
					}
				}

			}else{
				groupId.setText(t_usergroup.getGroup_id());
				txtGroupDesc.setText(t_usergroup.getDescription());
			}			

		}else if(previous_page.equalsIgnoreCase("authorize_page")){
			groupId.setText(t_usergroup.getGroup_id());
			txtGroupDesc.setText(t_usergroup.getDescription());
		}


		self.setAttribute(self.getId(), this);

		Create_Required_Fields_List();
		MapListBoxes();
		Apply_User_Roles();

		binder = new AnnotateDataBinder();
		binder.loadAll();

	}

	public void MapListBoxes(){		
		tmp_functionlist = new ArrayList<Functions>();
		tmp_functionlist.addAll(functionlist);

		/*Perform a different algorithm */
		for(SelectedFunctions sf_: selectedfunctionslist){
			for(Functions f_: tmp_functionlist){
				if(sf_.getFunction_name().equals(f_.getFunction_name())){
					tmp_functionlist.remove(f_);
					break;
				}
			}
		}		

		lml_functionlist = new ListModelList(tmp_functionlist);
		lml_selectedfunctions = new ListModelList(selectedfunctionslist);

	}

	public ListModelList getLml_functionlist() {
		return lml_functionlist;
	}

	public void setLml_functionlist(ListModelList lml_functionlist) {
		this.lml_functionlist = lml_functionlist;
	}

	public ListModelList getLml_selectedfunctions() {
		return lml_selectedfunctions;
	}

	public void setLml_selectedfunctions(ListModelList lml_selectedfunctions) {
		this.lml_selectedfunctions = lml_selectedfunctions;
	}

	public List<Functions> getTmp_functionlist() {
		return tmp_functionlist;
	}

	public void setTmp_functionlist(List<Functions> tmp_functionlist) {
		this.tmp_functionlist = tmp_functionlist;
	}
	
	public Textbox getTxtGroupDesc() {
		return txtGroupDesc;
	}

	public void setTxtGroupDesc(Textbox txtGroupDesc) {
		this.txtGroupDesc = txtGroupDesc;
	}

	public Textbox getGroupId() {
		return groupId;
	}

	public void setGroupId(Textbox groupId) {
		this.groupId = groupId;
	}

	public TmpSaUserGroup getT_usergroup() {
		return t_usergroup;
	}

	public void setT_usergroup(TmpSaUserGroup t_usergroup) {
		this.t_usergroup = t_usergroup;
	}

	public List<Functions> getFunctionlist() {
		return functionlist;
	}

	public void setFunctionlist(List<Functions> functionlist) {
		this.functionlist = functionlist;
	}

	public List<SelectedFunctions> getSelectedfunctionslist() {
		return selectedfunctionslist;
	}

	public void setSelectedfunctionslist(
			List<SelectedFunctions> selectedfunctionslist) {
		this.selectedfunctionslist = selectedfunctionslist;
	}

	public void onClick$movetodest(){
		move_src_to_dsc(src, dst);
		binder.loadAll();
	}

	public void onClick$movealltodest(){
		moveall_to_dst(src, dst);	
		binder.loadAll();
	}

	public void onClick$movetosrc(){
		move_dsc_to_src(dst,src);
		binder.loadAll();
	}

	public void onClick$movealltosrc(){
		moveall_to_src(dst, src);	
		binder.loadAll();
	}

	@SuppressWarnings("unchecked")
	void move_src_to_dsc(Listbox src, Listbox dst){

		lml_functionlist = (ListModelList) src.getModel();
		lml_selectedfunctions = (ListModelList) dst.getModel();

		tmp_functionlist = lml_functionlist.getInnerList();
		selectedfunctionslist = lml_selectedfunctions.getInnerList();

		if(selectedfunctionslist == null || selectedfunctionslist.isEmpty()){
			selectedfunctionslist = new ArrayList<SelectedFunctions>();
			lml_selectedfunctions = new ListModelList(selectedfunctionslist);
			lml_selectedfunctions.clear();
		}

		Functions func = new Functions();
		try {
			func = (Functions) src.getSelectedItem().getValue();
		} catch (Exception e1) {
			func = null;
		}
		SelectedFunctions selfunc = new SelectedFunctions();

		if(func != null){

			selfunc.setFunction_id(func.getFunction_id());
			selfunc.setDescription(func.getDescription());
			selfunc.setFunction_name(func.getFunction_name());

			selectedfunctionslist.add(selfunc);
			tmp_functionlist.remove(func);			

			lml_functionlist = new ListModelList(tmp_functionlist);
			lml_selectedfunctions = new ListModelList(selectedfunctionslist);

			src.setModel(lml_functionlist);
			dst.setModel(lml_selectedfunctions);

		}
		else {
			lblinfo.setValue(globalUtils.getMessagePropValue(Commons.SA_SELECT_ITEM));
			securityAdminUtils.setMsgStyle(img_info, lblinfo, true);
		}
//			try {				
//				Messagebox.show(securityAdminUtils.getMessage(Commons.SA_SELECT_ITEM), securityAdminUtils.getGlobalString(Commons.MSGBOX_TITLE_UPDATE_RECORD), Messagebox.OK, Messagebox.INFORMATION);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
	}

	@SuppressWarnings("unchecked")
	void move_dsc_to_src(Listbox dst, Listbox src){

		lml_functionlist = (ListModelList) src.getModel();
		lml_selectedfunctions = (ListModelList) dst.getModel();

		tmp_functionlist = lml_functionlist.getInnerList();
		selectedfunctionslist = lml_selectedfunctions.getInnerList();

		if(tmp_functionlist == null || tmp_functionlist.isEmpty()){
			tmp_functionlist = new ArrayList<Functions>();
			lml_functionlist = new ListModelList(tmp_functionlist);
			lml_functionlist.clear();
		}

		SelectedFunctions selfunc = new SelectedFunctions();
		Functions func = new Functions();

		try {
			selfunc = (SelectedFunctions) dst.getSelectedItem().getValue();
		} catch (Exception e1) {			
			selfunc = null;
		}

		if(selfunc != null){
			func.setFunction_id(selfunc.getFunction_id());
			func.setDescription(selfunc.getDescription());
			func.setFunction_name(selfunc.getFunction_name());

			tmp_functionlist.add(func);
			selectedfunctionslist.remove(selfunc);

			lml_functionlist = new ListModelList(tmp_functionlist);
			lml_selectedfunctions = new ListModelList(selectedfunctionslist);

			src.setModel(lml_functionlist);
			dst.setModel(lml_selectedfunctions);

		} else {
			lblinfo.setValue(globalUtils.getMessagePropValue(Commons.SA_SELECT_ITEM));
			securityAdminUtils.setMsgStyle(img_info, lblinfo, true);
		}
//			try {
//				Messagebox.show(securityAdminUtils.getMessage(Commons.SA_SELECT_ITEM), securityAdminUtils.getGlobalString(Commons.MSGBOX_TITLE_UPDATE_RECORD), Messagebox.OK, Messagebox.INFORMATION);
//			} catch (InterruptedException e) {			
//				e.printStackTrace();
//			}
	}

	@SuppressWarnings("unchecked")
	void moveall_to_dst(Listbox src, Listbox dst) {

		//src.selectAll();
		lml_functionlist = (ListModelList) src.getModel();
		lml_selectedfunctions = (ListModelList) dst.getModel();

		tmp_functionlist = lml_functionlist.getInnerList();
		selectedfunctionslist = lml_selectedfunctions.getInnerList();

		if(tmp_functionlist != null && !tmp_functionlist.isEmpty()){
			for (Functions func: tmp_functionlist){

				SelectedFunctions selfunc = new SelectedFunctions();

				//selfunc.setSf_id("");
				selfunc.setGroup_id(groupId.getValue());
				selfunc.setFunction_id(func.getFunction_id());
				selfunc.setDescription(func.getDescription());
				selfunc.setFunction_name(func.getFunction_name());
				selfunc.setTmpsausergroup(null);

				selectedfunctionslist.add(selfunc);						
			}

			tmp_functionlist.clear();

			lml_functionlist = new ListModelList(tmp_functionlist);
			lml_selectedfunctions = new ListModelList(selectedfunctionslist);

			src.setModel(lml_functionlist);
			dst.setModel(lml_selectedfunctions);
		}
	}

	@SuppressWarnings("unchecked")
	void moveall_to_src(Listbox dst, Listbox src) {

		//dst.selectAll();

		lml_functionlist = (ListModelList) src.getModel();
		lml_selectedfunctions = (ListModelList) dst.getModel();

		tmp_functionlist = lml_functionlist.getInnerList();
		selectedfunctionslist = lml_selectedfunctions.getInnerList();

		if(selectedfunctionslist != null && !selectedfunctionslist.isEmpty()){
			for (SelectedFunctions selfunc: selectedfunctionslist){

				Functions func = new Functions();

				func.setFunction_id(selfunc.getFunction_id());
				func.setDescription(selfunc.getDescription());
				func.setFunction_name(selfunc.getFunction_name());

				tmp_functionlist.add(func);						
			}
			selectedfunctionslist.clear();

			lml_functionlist = new ListModelList(tmp_functionlist);
			lml_selectedfunctions = new ListModelList(selectedfunctionslist);

			src.setModel(lml_functionlist);
			dst.setModel(lml_selectedfunctions);
		}
	}

	public void onClick$btn_update_top() throws InterruptedException{

		/**Validation returns true if has errors**/
		if(!securityAdminUtils.validateFields(lst_requiredfields)) { //& saUtils.validateID(groupId)) { //no ID validation since ID field is read only

			Messagebox.show(String.format(securityAdminUtils.getMessage(Commons.SA_MSG_UPDATE_USERGROUP),groupId.getValue()), securityAdminUtils.getMessage(Commons.MSGBOX_TITLE_UPDATE_RECORD), Messagebox.OK|Messagebox.CANCEL, Messagebox.QUESTION, new EventListener() {

				@Override
				public void onEvent(Event e) throws Exception {
					if(e.getName().equals("onOK")){
						Perform_Update();
					}				
				}
			});	
		}
	}

	public void onClick$btn_update_foot() throws InterruptedException{
		onClick$btn_update_top();
	}	

	public void onClick$btn_cancel_top()throws InterruptedException{
		Messagebox.show(String.format(securityAdminUtils.getMessage(Commons.SA_MSG_CANCEL_USERGROUP), groupId.getValue()), securityAdminUtils.getMessage(Commons.MSGBOX_TITLE_UPDATE_RECORD), Messagebox.OK|Messagebox.CANCEL, Messagebox.QUESTION, new EventListener() {

			@Override
			public void onEvent(Event e) throws Exception {

				if(e.getName().equals("onOK")){
					Perform_Cancel();
				}				
			}
		});

	}

	public void onClick$btn_cancel_foot()throws InterruptedException{
		onClick$btn_cancel_top();
	}

	public void onClick$btn_delete_top() throws InterruptedException{
		Messagebox.show(String.format(securityAdminUtils.getMessage(Commons.SA_MSG_DELETE_USERGROUP), groupId.getValue()), securityAdminUtils.getMessage(Commons.MSGBOX_TITLE_DELETE_RECORD), Messagebox.OK|Messagebox.CANCEL, Messagebox.QUESTION, new EventListener() {

			@Override
			public void onEvent(Event e) throws Exception {
				if(e.getName().equals("onOK")){
					Perform_Delete();
				}				
			}
		});	
	}

	public void onClick$btn_delete_foot() throws InterruptedException{
		onClick$btn_delete_top();
	}

	public void onClick$btn_back_top() throws InterruptedException{
		if(previous_page.equals("search_page")){
			SecurityAdminUtils.Redirect_to_Page(Commons.URL_SEARCH_USERGROUP);
		}else if(previous_page.equals("authorize_page")){
			SecurityAdminUtils.Redirect_to_Page(Commons.URL_AUTHORIZE_USERGROUP);
		}
	}

	public void onClick$btn_back_foot() throws InterruptedException{
		onClick$btn_back_top();
	}

	public void onClick$btn_approve_top() throws InterruptedException{
		Messagebox.show(String.format(securityAdminUtils.getMessage(Commons.SA_MSG_APPROVE_USERGROUP), groupId.getValue()), securityAdminUtils.getMessage(Commons.MSGBOX_TITLE_AUTHORIZE), Messagebox.OK|Messagebox.CANCEL, Messagebox.QUESTION, new EventListener() {

			@Override
			public void onEvent(Event e) throws Exception {
				if(e.getName().equals("onOK")){
					Perform_Update_Approve();
				}				
			}
		});

	}

	public void onClick$btn_approve_foot() throws InterruptedException{
		onClick$btn_approve_top();
	}

	public void onClick$btn_reject_top() throws InterruptedException{
		Messagebox.show(String.format(securityAdminUtils.getMessage(Commons.SA_MSG_REJECT_USERGROUP), groupId.getValue()), securityAdminUtils.getMessage(Commons.MSGBOX_TITLE_AUTHORIZE), Messagebox.OK|Messagebox.CANCEL, Messagebox.QUESTION, new EventListener() {

			@Override
			public void onEvent(Event e) throws Exception {
				if(e.getName().equals("onOK")){
					Perform_Authorized_Reject();
				}				
			}
		});

	}

	public void onClick$btn_reject_foot() throws InterruptedException{
		onClick$btn_reject_top();
	}

	public void onClick$btn_reset_top() throws InterruptedException{
		/**The logic is to put back the previous fields instead of clearing it all**/
		String url_ = String.format(securityAdminUtils.getGlobalString(Commons.URL_UPDATE_USERGROUP) + "?group_id=%s&previous_page=%s",groupId.getValue(),previous_page);
		Executions.sendRedirect(url_);
	}

	public void onClick$btn_reset_foot() throws InterruptedException{
		onClick$btn_reset_top();
	}

	@SuppressWarnings("unchecked")
	public void Perform_Update()throws InterruptedException{
		lml_selectedfunctions = (ListModelList) dst.getModel();
		selectedfunctionslist = lml_selectedfunctions.getInnerList();

		UpdateUserGroup(groupId.getValue(), txtGroupDesc.getValue(), selectedfunctionslist);
	}

	public boolean UpdateUserGroup(String group_id_, String desc_, List<SelectedFunctions>selectedfunctionslist)throws InterruptedException{

		/**
		 * UPDATE
		 * 
		 * Rule: 
		 * - can be applied only to records that are active
		 * 
		 * **/

		TmpSaUserGroupManager  groupmanager = ServiceLocator.getTmpSaUserGroupManager();
		TmpSaUserGroup parent = new TmpSaUserGroup();		

		boolean updateSuccess = false;

		String group_id = group_id_.toUpperCase();
		String description = securityAdminUtils.toCamelcase(desc_);

		lblinfo.setValue("");

		parent = groupmanager.getTmpSaUserGroup(group_id);
		if(parent == null){
			lblinfo.setValue(globalUtils.getMessagePropValue(Commons.SA_RECORD_NOTEXIST));
			securityAdminUtils.setMsgStyle(img_info, lblinfo, true);
//			try {				
//				Messagebox.show(securityAdminUtils.getMessage(Commons.SA_RECORD_NOTEXIST));
//			} catch (InterruptedException e) {				
//				e.printStackTrace();
//			}
		}else{

			/*Only the owner can update*/
			if(!SecurityAdminUtils.get_Logged_in_Username().equalsIgnoreCase(parent.getModifier_id()) && parent.getStatus().equals(Commons.SA_STATUS_PENDING)){
				lblinfo.setValue(globalUtils.getMessagePropValue(Commons.SA_MSG_INVALID_OWNER));
				securityAdminUtils.setMsgStyle(img_info, lblinfo, true);
//				Messagebox.show(securityAdminUtils.getMessage(Commons.SA_MSG_INVALID_OWNER), securityAdminUtils.getMessage(Commons.MSGBOX_TITLE_WARNING), Messagebox.OK, Messagebox.INFORMATION);											
			}else{
				Date current_Date = DateUtil.getCurrentDate();
				parent.setGroup_id(group_id);
				parent.setDescription(description);
				//parent.setMaker_id(SecurityAdminUtils.get_Logged_in_Username());			
				parent.setChecker_id("");
				parent.setDecision_dt("");
				parent.setModifier_id(SecurityAdminUtils.get_Logged_in_Username());
				parent.setModified_dt(DateUtil.format(current_Date));


				/**
				 * If status is Pending ADD then dont change status
				 * **/
				if(parent.getStatus().equals(Commons.SA_STATUS_PENDING) && parent.getAction().equals(Commons.SA_ACTION_ADD)){
					parent.setStatus(Commons.SA_STATUS_PENDING);
					parent.setAction(Commons.SA_ACTION_ADD);
				}else{
					parent.setStatus(Commons.SA_STATUS_PENDING);
					parent.setAction(Commons.SA_ACTION_UPDATE);
				}			

				parent.getSelectedfunctions().removeAll(parent.getSelectedfunctions());

				groupmanager.save(parent);

				for(SelectedFunctions child: selectedfunctionslist){
					String ch_group_id = parent.getGroup_id();
					Integer ch_function_id = child.getFunction_id();
					String ch_description = child.getDescription();
					String ch_function_name = child.getFunction_name();

					SelectedFunctions childbean = new SelectedFunctions(ch_group_id, ch_function_id, ch_description,ch_function_name, parent);							
					parent.getSelectedfunctions().add(childbean);			
				}


				/*Update history table*/
				SaUserGroupHistory hst_usergroup = new SaUserGroupHistory();
				hst_usergroup.setGroup_id(group_id);
				hst_usergroup.setDescription(description);
				hst_usergroup.setMaker_id(parent.getMaker_id());
				hst_usergroup.setCreation_dt(parent.getCreation_dt());			
				hst_usergroup.setChecker_id("");
				hst_usergroup.setDecision_dt("");
				hst_usergroup.setModifier_id(parent.getModifier_id());
				hst_usergroup.setModified_dt(parent.getModified_dt());
				hst_usergroup.setStatus(parent.getStatus());
				hst_usergroup.setAction(parent.getAction());

				try {
					usergroup_history_manager.save(hst_usergroup);
					updateSuccess = groupmanager.save(parent);

					lblinfo.setValue(securityAdminUtils.getMessage(Commons.SA_UPDATE_SUCCESS));
					securityAdminUtils.setMsgStyle(img_info, lblinfo, false);
					/*Executions.getCurrent().getSession().setAttribute(Commons.SA_LBLINFO, globalUtils.returnPropValue(Commons.MESSAGES_PROPERTIES_EN, Commons.SA_UPDATE_SUCCESS));
				onClick$btn_back_top();*/
				} catch (Exception e) {				
					lblinfo.setValue(securityAdminUtils.getMessage(Commons.SA_UPDATE_FAIL));
					securityAdminUtils.setMsgStyle(img_info, lblinfo, true);
					return false;
				}
			}
		}		
		return updateSuccess;		
	}

	public void Perform_Delete()throws InterruptedException{


		TmpSaUserGroup parent = new TmpSaUserGroup();
		parent = tmp_sa_groupmanager.getTmpSaUserGroup(groupId.getValue());

		/**Avoid letting other user modification**/
		if(!SecurityAdminUtils.get_Logged_in_Username().equalsIgnoreCase(t_usergroup.getModifier_id())){
			Messagebox.show(securityAdminUtils.getMessage(Commons.SA_MSG_INVALID_OWNER), securityAdminUtils.getMessage(Commons.MSGBOX_TITLE_WARNING), Messagebox.OK, Messagebox.INFORMATION);
		}else{
			if(parent != null){
				Date current_Date = DateUtil.getCurrentDate();
				parent.setMaker_id(SecurityAdminUtils.get_Logged_in_Username());
				parent.setCreation_dt(DateUtil.format(current_Date));	
				parent.setChecker_id("");
				parent.setDecision_dt("");
				parent.setModified_dt(DateUtil.format(current_Date));
				parent.setStatus(Commons.SA_STATUS_PENDING);
				parent.setAction(Commons.SA_ACTION_DELETE);


				/*Update history table*/
				SaUserGroupHistory hst_usergroup = new SaUserGroupHistory();
				hst_usergroup.setGroup_id(parent.getGroup_id());
				hst_usergroup.setDescription(parent.getDescription());
				hst_usergroup.setMaker_id(SecurityAdminUtils.get_Logged_in_Username());
				hst_usergroup.setCreation_dt(parent.getCreation_dt());			
				hst_usergroup.setChecker_id("");
				hst_usergroup.setDecision_dt("");
				hst_usergroup.setModified_dt(parent.getModified_dt());
				hst_usergroup.setStatus(Commons.SA_STATUS_PENDING);
				hst_usergroup.setAction(Commons.SA_ACTION_DELETE);

				try {
					usergroup_history_manager.save(hst_usergroup);
					tmp_sa_groupmanager.save(parent);
					//lblinfo.setValue(globalUtils.returnPropValue(Commons.MESSAGES_PROPERTIES_EN,Commons.SA_DELETE_SUCCESS));
					Executions.getCurrent().getSession().setAttribute(Commons.SA_LBLINFO, Commons.SA_DELETE_SUCCESS);
					onClick$btn_back_top();
				} catch (Exception e) {
					lblinfo.setValue(securityAdminUtils.getMessage(Commons.SA_DELETE_FAIL));
					securityAdminUtils.setMsgStyle(img_info, lblinfo, true);
				}
			}else{
				//Nothing to put here yet 
			}
		}
	} 

	public void Perform_Update_Approve() throws InterruptedException{
		/*
		 * Perform Update Approve (Checker Level)
		 * */

		SaUserGroup mvar_sausergroup;

		String group_id_ = groupId.getValue();

		TmpSaUserGroup t_usergroup = tmp_sa_groupmanager.getTmpSaUserGroup(group_id_);
		mvar_sausergroup = sa_groupmanager.getSaUserGroup(group_id_);

		/**Avoid letting checker approve his own request**/
		if(SecurityAdminUtils.get_Logged_in_Username().equalsIgnoreCase(t_usergroup.getModifier_id())){
			Messagebox.show(t_usergroup.getGroup_id() + " " + securityAdminUtils.getMessage(Commons.SA_MSG_ERR_CHKER_APPROVE_OWN), securityAdminUtils.getMessage(Commons.MSGBOX_TITLE_ERROR), Messagebox.OK, Messagebox.EXCLAMATION);
		}else{
			Date current_Date = DateUtil.getCurrentDate();
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

			mvar_sausergroup.setCreation_dt(t_usergroup.getCreation_dt());
			mvar_sausergroup.setDecision_dt(DateUtil.format(current_Date));
			t_usergroup.setDecision_dt(DateUtil.format(current_Date));
			
			mvar_sausergroup.setModifier_id(t_usergroup.getModifier_id());
			mvar_sausergroup.setModified_dt(t_usergroup.getModified_dt());

			/*
			 * Do something based from the actions to perform
			 * */
			if(t_usergroup.getAction().equals(Commons.SA_ACTION_ADD) || (t_usergroup.getAction().equals(Commons.SA_ACTION_UPDATE)) && t_usergroup.getStatus().equals(Commons.SA_STATUS_PENDING)){

				Perform_Authorized_Add_Update(t_usergroup, mvar_sausergroup);
			}else if(t_usergroup.getAction().equals(Commons.SA_ACTION_DELETE) && t_usergroup.getStatus().equals(Commons.SA_STATUS_PENDING)){

				Perform_Authorized_Delete(t_usergroup, mvar_sausergroup);
			}
		}
	}

	public void Perform_Authorized_Add_Update(TmpSaUserGroup t_usergroup_, SaUserGroup sa_usergroup_) throws InterruptedException{


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

			SelectedFunctions sa_childbean = new SelectedFunctions(ch_group_id, ch_function_id, ch_description,ch_function_name ,t_usergroup_);							
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

		/*Set status*/
		t_usergroup_.setStatus(Commons.SA_STATUS_ACTIVE);
		sa_usergroup_.setStatus(Commons.SA_STATUS_ACTIVE);

		/*Update history table*/
		Date current_Date = DateUtil.getCurrentDate();
		SaUserGroupHistory hst_usergroup = new SaUserGroupHistory();
		hst_usergroup.setGroup_id(t_usergroup_.getGroup_id());
		hst_usergroup.setDescription(t_usergroup_.getDescription());
		hst_usergroup.setMaker_id(t_usergroup_.getMaker_id());
		hst_usergroup.setCreation_dt(t_usergroup_.getCreation_dt());			
		hst_usergroup.setChecker_id(SecurityAdminUtils.get_Logged_in_Username());
		hst_usergroup.setDecision_dt(DateUtil.format(current_Date));
		hst_usergroup.setModified_dt(t_usergroup_.getModified_dt());
		hst_usergroup.setStatus(Commons.SA_STATUS_ACTIVE);
		hst_usergroup.setAction(t_usergroup_.getAction());
		try {

			usergroup_history_manager.save(hst_usergroup);
			tmp_sa_groupmanager.save(t_usergroup_);
			sa_groupmanager.save(sa_usergroup_);

			//lblinfo.setValue(globalUtils.returnPropValue(Commons.MESSAGES_PROPERTIES_EN, Commons.SA_UPDATE_SUCCESS));	
			Executions.getCurrent().getSession().setAttribute(Commons.SA_LBLINFO, Commons.SA_APPROVE_SUCCESS);
			onClick$btn_back_top();
		} catch (Exception e) {
			lblinfo.setValue(securityAdminUtils.getMessage(Commons.SA_APPROVE_FAIL));
			securityAdminUtils.setMsgStyle(img_info, lblinfo, true);
			e.printStackTrace();
		}

	}

	public void Perform_Authorized_Delete(TmpSaUserGroup t_usergroup_, SaUserGroup sa_usergroup_) throws InterruptedException{

		/**
		 * AUTHORIZED DELETE - Only change the master record's status to deleted and dont delete in the 
		 * database.
		 * 
		 * Deleted records are non - viewable
		 * **/

		List<SelectedFunctions>mvar_usrgrp_list = new ArrayList<SelectedFunctions>();
		mvar_usrgrp_list.addAll(t_usergroup_.getSelectedfunctions());

		/**Delete the child first though this is not necessarry but I just included it anyway**/
		t_usergroup_.getSelectedfunctions().removeAll(t_usergroup_.getSelectedfunctions());
		sa_usergroup_.getSa_selectedfunctions().removeAll(sa_usergroup_.getSa_selectedfunctions());


		t_usergroup_.setStatus(Commons.SA_STATUS_DELETED);
		sa_usergroup_.setStatus(Commons.SA_STATUS_DELETED);

		/*Update history table*/
		Date current_Date = DateUtil.getCurrentDate();
		SaUserGroupHistory hst_usergroup = new SaUserGroupHistory();
		hst_usergroup.setGroup_id(t_usergroup_.getGroup_id());
		hst_usergroup.setDescription(t_usergroup_.getDescription());
		hst_usergroup.setMaker_id(t_usergroup_.getMaker_id());
		hst_usergroup.setCreation_dt(t_usergroup_.getCreation_dt());			
		hst_usergroup.setChecker_id(SecurityAdminUtils.get_Logged_in_Username());
		hst_usergroup.setDecision_dt(DateUtil.format(current_Date));
		hst_usergroup.setModified_dt(t_usergroup_.getModified_dt());
		hst_usergroup.setStatus(Commons.SA_STATUS_DELETED);
		hst_usergroup.setAction(t_usergroup_.getAction());

		try {			

			usergroup_history_manager.save(hst_usergroup);
			tmp_sa_groupmanager.save(t_usergroup_);
			sa_groupmanager.save(sa_usergroup_);

			//lblinfo.setValue(globalUtils.returnPropValue(Commons.MESSAGES_PROPERTIES_EN, Commons.SA_DELETE_SUCCESS));
			Executions.getCurrent().getSession().setAttribute(Commons.SA_LBLINFO, Commons.SA_APPROVE_SUCCESS);
			onClick$btn_back_top();
		} catch (Exception e) {
			lblinfo.setValue(securityAdminUtils.getMessage(Commons.SA_APPROVE_FAIL));
			securityAdminUtils.setMsgStyle(img_info, lblinfo, true);
		}

	}

	public void Perform_Authorized_Reject() throws InterruptedException{
		/*
		 * Perform Authorized Reject (Checker Level)
		 * */

		String group_id_ = groupId.getValue();

		TmpSaUserGroup t_usergroup = tmp_sa_groupmanager.getTmpSaUserGroup(group_id_);
		SaUserGroup mvar_usergroup = sa_groupmanager.getSaUserGroup(group_id_);

		/**Avoid letting checker reject his own request**/
		if(SecurityAdminUtils.get_Logged_in_Username().equalsIgnoreCase(t_usergroup.getModifier_id())){
			Messagebox.show(t_usergroup.getGroup_id() + " " + securityAdminUtils.getMessage(Commons.SA_MSG_ERR_CHKER_REJECT_OWN), securityAdminUtils.getMessage(Commons.MSGBOX_TITLE_ERROR), Messagebox.OK, Messagebox.EXCLAMATION);
		}else{

			if(t_usergroup != null){
				if(t_usergroup.getStatus().equals(Commons.SA_STATUS_PENDING)){

					if(mvar_usergroup == null){
						/*
						 * If no master record found then delete from temp table
						 * */
						t_usergroup.setStatus(Commons.SA_STATUS_REJECTED);

						/*Update history table*/
						Date current_Date = DateUtil.getCurrentDate();
						SaUserGroupHistory hst_usergroup = new SaUserGroupHistory();
						hst_usergroup.setGroup_id(t_usergroup.getGroup_id());
						hst_usergroup.setDescription(t_usergroup.getDescription());
						hst_usergroup.setMaker_id(t_usergroup.getMaker_id());
						hst_usergroup.setCreation_dt(t_usergroup.getCreation_dt());			
						hst_usergroup.setChecker_id(SecurityAdminUtils.get_Logged_in_Username());
						hst_usergroup.setDecision_dt(DateUtil.format(current_Date));
						hst_usergroup.setModified_dt(t_usergroup.getModified_dt());
						hst_usergroup.setStatus(Commons.SA_STATUS_REJECTED);
						hst_usergroup.setAction(t_usergroup.getAction());

						try {
							usergroup_history_manager.save(hst_usergroup);
							tmp_sa_groupmanager.delete(t_usergroup);	
							//lblinfo.setValue(globalUtils.returnPropValue(Commons.MESSAGES_PROPERTIES_EN, Commons.SA_REJECT_SUCCESS));
							Executions.getCurrent().getSession().setAttribute(Commons.SA_LBLINFO, Commons.SA_REJECT_SUCCESS);
							onClick$btn_back_top();
						} catch (Exception e) {
							lblinfo.setValue(securityAdminUtils.getMessage(Commons.SA_REJECT_FAIL));
							securityAdminUtils.setMsgStyle(img_info, lblinfo, true);
							e.printStackTrace();
						}
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
						Date current_Date = DateUtil.getCurrentDate();
						SaUserGroupHistory hst_usergroup = new SaUserGroupHistory();
						hst_usergroup.setGroup_id(t_usergroup.getGroup_id());
						hst_usergroup.setDescription(t_usergroup.getDescription());
						hst_usergroup.setMaker_id(t_usergroup.getMaker_id());
						hst_usergroup.setCreation_dt(t_usergroup.getCreation_dt());			
						hst_usergroup.setChecker_id(SecurityAdminUtils.get_Logged_in_Username());
						hst_usergroup.setDecision_dt(DateUtil.format(current_Date));
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

								SelectedFunctions childbean = new SelectedFunctions(ch_group_id, ch_function_id, ch_description,ch_function_name ,t_usergroup);							
								t_usergroup.getSelectedfunctions().add(childbean);		
							}


							try {										

								usergroup_history_manager.save(hst_usergroup);
								tmp_sa_groupmanager.save(t_usergroup);						

								//lblinfo.setValue(globalUtils.returnPropValue(Commons.MESSAGES_PROPERTIES_EN, Commons.SA_REJECT_SUCCESS));
								Executions.getCurrent().getSession().setAttribute(Commons.SA_LBLINFO, Commons.SA_REJECT_SUCCESS);
								onClick$btn_back_top();
							} catch (Exception e) {
								lblinfo.setValue(securityAdminUtils.getMessage(Commons.SA_REJECT_FAIL));
								securityAdminUtils.setMsgStyle(img_info, lblinfo, true);
							}
						}

					}
				}
			}
		}
	}

	public void Perform_Cancel() throws InterruptedException{
		/*
		 * Cancel Record (Maker Level)
		 * ToDo: if transaction is cancelled then overwrite tmprecord by master record if both exists else delete tmp record if not
		 * found in master record
		 */

		String group_id_ = groupId.getValue();

		TmpSaUserGroup t_usergroup = tmp_sa_groupmanager.getTmpSaUserGroup(group_id_);
		SaUserGroup mvar_usergroup = sa_groupmanager.getSaUserGroup(group_id_);
		if(t_usergroup != null){

			if(t_usergroup.getModifier_id().equals(SecurityAdminUtils.get_Logged_in_Username())){				

				if(t_usergroup.getStatus().equals(Commons.SA_STATUS_PENDING)){

					if(mvar_usergroup == null){
						t_usergroup.setStatus(Commons.SA_STATUS_CANCELLED);

						/*Update history table*/
						Date current_Date = DateUtil.getCurrentDate();
						SaUserGroupHistory hst_usergroup = new SaUserGroupHistory();
						hst_usergroup.setGroup_id(t_usergroup.getGroup_id());
						hst_usergroup.setDescription(t_usergroup.getDescription());
						hst_usergroup.setMaker_id(SecurityAdminUtils.get_Logged_in_Username());
						hst_usergroup.setCreation_dt(t_usergroup.getCreation_dt());			
						hst_usergroup.setChecker_id("");
						hst_usergroup.setDecision_dt("");
						hst_usergroup.setModified_dt(DateUtil.format(current_Date));
						hst_usergroup.setStatus(Commons.SA_STATUS_CANCELLED);
						hst_usergroup.setAction(t_usergroup.getAction());

						usergroup_history_manager.save(hst_usergroup);
						tmp_sa_groupmanager.delete(t_usergroup);
						Executions.getCurrent().getSession().setAttribute(Commons.SA_LBLINFO, Commons.SA_CANCEL_SUCCESS);
						onClick$btn_back_top();

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
					}

					/*Update history table*/
					Date current_Date = DateUtil.getCurrentDate();
					SaUserGroupHistory hst_usergroup = new SaUserGroupHistory();
					hst_usergroup.setGroup_id(t_usergroup.getGroup_id());
					hst_usergroup.setDescription(t_usergroup.getDescription());
					hst_usergroup.setMaker_id(t_usergroup.getMaker_id());
					hst_usergroup.setCreation_dt(t_usergroup.getCreation_dt());			
					hst_usergroup.setChecker_id("");
					hst_usergroup.setDecision_dt("");
					hst_usergroup.setModified_dt(DateUtil.format(current_Date));
					hst_usergroup.setStatus(Commons.SA_STATUS_CANCELLED);
					hst_usergroup.setAction(t_usergroup.getAction());

					try {
						usergroup_history_manager.save(hst_usergroup);
						tmp_sa_groupmanager.save(t_usergroup);						
						//lblinfo.setValue(globalUtils.returnPropValue(Commons.MESSAGES_PROPERTIES_EN, Commons.SA_CANCEL_SUCCESS));
						Executions.getCurrent().getSession().setAttribute(Commons.SA_LBLINFO, Commons.SA_CANCEL_SUCCESS);
						onClick$btn_back_top();
					} catch (Exception e) {
						lblinfo.setValue(securityAdminUtils.getMessage(Commons.SA_CANCEL_FAIL));
						securityAdminUtils.setMsgStyle(img_info, lblinfo, true);
						e.printStackTrace();
					}
				}else{
					lblinfo.setValue(globalUtils.getMessagePropValue(Commons.SA_MSG_UNABLE_TO_CANCEL));
					securityAdminUtils.setMsgStyle(img_info, lblinfo, true);
//					Messagebox.show(securityAdminUtils.getMessage(Commons.SA_MSG_UNABLE_TO_CANCEL), securityAdminUtils.getMessage(Commons.MSGBOX_TITLE_CANCEL), Messagebox.OK, Messagebox.ERROR);

				}					
			}
			else {
				lblinfo.setValue(globalUtils.getMessagePropValue(Commons.SA_MSG_INVALID_OWNER));
				securityAdminUtils.setMsgStyle(img_info, lblinfo, true);
//				Messagebox.show(securityAdminUtils.getMessage(Commons.SA_MSG_INVALID_OWNER), securityAdminUtils.getMessage(Commons.MSGBOX_TITLE_CANCEL), Messagebox.OK, Messagebox.EXCLAMATION);
			}
		}
	}

	public void Create_Required_Fields_List(){

		lst_requiredfields.clear();

		/**Set the length too**/
		groupId.setMaxlength(Commons.SA_MAXLENGTH_GROUP_ID);
		txtGroupDesc.setMaxlength(Commons.SA_MAXLENGTH_DESCRIPTION);

		lst_requiredfields.add(groupId);
		lst_requiredfields.add(txtGroupDesc);
	}

	public void Apply_User_Roles(){

		boolean is_Maker = false;
		boolean is_Checker = false;

		is_Maker = SecurityUtils.getSecurityUtilsInstance().hasMakerRole(securityAdminUtils.getGlobalString(Commons.URL_UPDATE_USERGROUP));
		is_Checker = SecurityUtils.getSecurityUtilsInstance().hasCheckerRole(securityAdminUtils.getGlobalString(Commons.URL_UPDATE_USERGROUP));

		/*Enable all feature if is_Maker and is_Checker*/
		txtGroupDesc.setReadonly(false);
		movealltodest.setVisible(true);
		movetodest.setVisible(true);
		movetosrc.setVisible(true);
		movealltosrc.setVisible(true);

		btn_update_top.setVisible(true);
		btn_delete_top.setVisible(false);			
		btn_approve_top.setVisible(true);
		btn_reject_top.setVisible(true);
		btn_back_top.setVisible(true);
		
		btn_update_foot.setVisible(true);
		btn_delete_foot.setVisible(false);			
		btn_approve_foot.setVisible(true);
		btn_reject_foot.setVisible(true);
		btn_back_foot.setVisible(true);

		/**If previous page is search_page**/
		if(t_usergroup.getStatus().equals(Commons.SA_STATUS_PENDING)){				
			btn_cancel_top.setVisible(true);
			btn_cancel_foot.setVisible(true);
		}else {
			btn_cancel_top.setVisible(false);
			btn_cancel_foot.setVisible(false);
		}



		if(is_Maker && !is_Checker){
			txtGroupDesc.setReadonly(false);
			movealltodest.setVisible(true);
			movetodest.setVisible(true);
			movetosrc.setVisible(true);
			movealltosrc.setVisible(true);

			btn_update_top.setVisible(true);
			btn_delete_top.setVisible(false);			
			btn_approve_top.setVisible(false);
			btn_reject_top.setVisible(false);

			btn_update_foot.setVisible(true);
			btn_delete_foot.setVisible(false);			
			btn_approve_foot.setVisible(false);
			btn_reject_foot.setVisible(false);


		}else if(!is_Maker && is_Checker){

			txtGroupDesc.setReadonly(true);	
			movealltodest.setVisible(false);
			movetodest.setVisible(false);
			movetosrc.setVisible(false);
			movealltosrc.setVisible(false);

			btn_update_top.setVisible(false);
			btn_delete_top.setVisible(false);	
			btn_cancel_top.setVisible(false);
			
			btn_update_foot.setVisible(false);
			btn_delete_foot.setVisible(false);	
			btn_cancel_foot.setVisible(false);

			/**If previous page is search_page**/
			if(previous_page.equals("search_page")){				
				btn_approve_top.setVisible(false);
				btn_reject_top.setVisible(false);
				btn_approve_foot.setVisible(false);
				btn_reject_foot.setVisible(false);
			}else if(previous_page.equals("authorize_page")){
				btn_approve_top.setVisible(true);
				btn_reject_top.setVisible(true);
				btn_approve_foot.setVisible(true);
				btn_reject_foot.setVisible(true);
			}				
		}
	}

}
