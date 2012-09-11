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
import com.isg.ifrend.model.bean.SaUserGroupHistory;
import com.isg.ifrend.model.bean.SelectedFunctions;
import com.isg.ifrend.model.bean.TmpSaUserGroup;
import com.isg.ifrend.service.FunctionsManager;
import com.isg.ifrend.service.SaUserGroupHistoryManager;
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
public class UserGroupAddPageViewCtrl extends GenericForwardComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	private Textbox txtGroupDesc;	
	private Textbox groupId;

	private Label lblinfo;
	private Image img_info;
	private Listbox dst;
	private Listbox src;
	
	@SuppressWarnings("unused")
	private Button btn_save_top;
	@SuppressWarnings("unused")
	private Button btn_save_foot;

	@SuppressWarnings("unused")
	private Button btn_back_top;
	@SuppressWarnings("unused")
	private Button btn_back_foot;

	@SuppressWarnings("unused")
	private Button btn_reset_top;
	@SuppressWarnings("unused")
	private Button btn_reset_foot;
	
//	private TmpSaUserGroup t_usergroup;
	private List<Functions>functionlist = new ArrayList<Functions>();	
	private List<Functions>tmp_functionlist = new ArrayList<Functions>();
	private List<SelectedFunctions>selectedfunctionslist = new ArrayList<SelectedFunctions>();

	private ListModelList lml_functionlist = new ListModelList();
	private ListModelList lml_selectedfunctions = new ListModelList();

	private FunctionsManager fmanager = ServiceLocator.getFunctionsManager();	
	private TmpSaUserGroupManager t_sagroupmanager = ServiceLocator.getTmpSaUserGroupManager();
//	private SelectedFunctionsManager selectedfunctionsmanager = ServiceLocator.getSelectedFunctionsManager();
	private SaUserGroupHistoryManager usergroup_history_manager = ServiceLocator.getSaUserGroupHistoryManager();

	private GlobalUtils globalUtils = GlobalUtils.getGlobalUtilsInstance();
	private SecurityAdminUtils securityAdminUtils = SecurityAdminUtils.getSecurityAdminUtilsInstance();


//	private String current_Date = SecurityAdminUtils.getCurrentDate();
	private Date current_Date;
	boolean saveSuccess = false;

	private List<Object>lst_requiredfields = new ArrayList<Object>();


	private AnnotateDataBinder binder;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		/*for functionslist reference table*/
		functionlist = fmanager.getFunctionsList();				
		selectedfunctionslist = new ArrayList<SelectedFunctions>();

		self.setAttribute(self.getId(), this);

		Create_Required_Fields_List();
		MapListBoxes();

		binder = new AnnotateDataBinder();
		binder.loadAll();
		

	}

	public void MapListBoxes(){		
		tmp_functionlist = new ArrayList<Functions>();
		tmp_functionlist.addAll(functionlist);
		
		/*Algorithm v2 
		 * */
		for(SelectedFunctions sf_: selectedfunctionslist){
			for(Functions f_: tmp_functionlist){
				if(sf_.getFunction_name().equals(f_.getFunction_name())){
					tmp_functionlist.remove(f_);
					break;
				}
			}
		}

		lml_functionlist = new ListModelList(functionlist);
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

		} else {
			lblinfo.setValue(globalUtils.getMessagePropValue(Commons.SA_SELECT_ITEM));
			securityAdminUtils.setMsgStyle(img_info, lblinfo, true);
		}
//			try {
				
//				Messagebox.show(securityAdminUtils.getMessage(Commons.SA_SELECT_ITEM), securityAdminUtils.getGlobalString(Commons.MSGBOX_TITLE_ADD_RECORD), Messagebox.OK, Messagebox.INFORMATION);
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
//				Messagebox.show(securityAdminUtils.getMessage(Commons.SA_SELECT_ITEM), securityAdminUtils.getGlobalString(Commons.MSGBOX_TITLE_ADD_RECORD), Messagebox.OK, Messagebox.INFORMATION);
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

	@SuppressWarnings("unchecked")
	public void onClick$btn_save_top() throws InterruptedException{
		lml_selectedfunctions = (ListModelList) dst.getModel();
		selectedfunctionslist = lml_selectedfunctions.getInnerList();

		/**Validation returns true if has errors**/
		if(!securityAdminUtils.validateFields(lst_requiredfields) & securityAdminUtils.validateID(groupId)) {

			Messagebox.show(String.format(securityAdminUtils.getMessage(Commons.SA_MSG_ADD_NEW_USERGROUP),
					groupId.getValue()), securityAdminUtils.getMessage(Commons.MSGBOX_TITLE_ADD_RECORD),
					Messagebox.OK|Messagebox.CANCEL, Messagebox.QUESTION,
					new EventListener() {

						@Override
						public void onEvent(Event e) throws Exception {
							if(e.getName().equals("onOK")){					
								SaveUserGroup(groupId.getValue(), txtGroupDesc.getValue(), selectedfunctionslist);					
							}
							else{
								/*
								 * Do nothing here else you want to add some
								 * */
							}			
						}
			});				
		}
		else {
			lblinfo.setValue(globalUtils.getMessagePropValue(Commons.MSG_ERROR));
			securityAdminUtils.setMsgStyle(img_info, lblinfo, true);
		}
	}

	public void onClick$btn_save_foot() throws InterruptedException{

		onClick$btn_save_top();	
	}

	/*public void onClick$btn_cancel_top()throws InterruptedException{

		Messagebox.show(saUtils.getMessage(Commons.SA_MSG_CANCEL_USERGROUP), "Cancel", Messagebox.OK|Messagebox.CANCEL, "", new EventListener() {

			@Override
			public void onEvent(Event e) throws Exception {

				if(e.getName().equals("onOK")){
					SecurityAdminUtils.Redirect_to_Searchpage();
				}else{
					
					 * Do nothing here else you want to add some
					 * 
				}
			}
		});

	}*/

	/*public void onClick$btn_cancel_foot()throws InterruptedException{
		onClick$btn_cancel_top();
	}*/

	public void onClick$btn_back_top()throws InterruptedException{
		SecurityAdminUtils.Redirect_to_Page(Commons.URL_SECURITY_ADMIN);		
	}

	public void onClick$btn_back_foot()throws InterruptedException{
		onClick$btn_back_top();
	}

	public void ClearContents(boolean success){
		if(success){
			
			lblinfo.setValue(securityAdminUtils.getMessage(Commons.SA_ADD_SUCCESS));
			securityAdminUtils.setMsgStyle(img_info, lblinfo, false);

			groupId.setValue(null);
			txtGroupDesc.setValue("");
			
			moveall_to_src(dst, src);
		}else{
			lblinfo.setValue(securityAdminUtils.getMessage(Commons.SA_ADD_FAIL));
			securityAdminUtils.setMsgStyle(img_info, lblinfo, true);
		}
	}	

	public boolean SaveUserGroup(String group_id_, String desc_, List<SelectedFunctions>selectedfunctionslist){

		TmpSaUserGroupManager  groupmanager = ServiceLocator.getTmpSaUserGroupManager();
//		GlobalUtils globalUtils = new GlobalUtils();
		
		String group_id = group_id_.toUpperCase();
		String description = securityAdminUtils.toCamelcase(desc_);
		
		boolean saveSuccess = false;

		TmpSaUserGroup parent = new TmpSaUserGroup();

		/*Prevent the user from overwriting existing record. Use Edit feature instead.*/ 
		parent = groupmanager.getTmpSaUserGroup(group_id);
		if(parent != null && (parent.getStatus().equalsIgnoreCase(Commons.SA_STATUS_ACTIVE) || parent.getStatus().equalsIgnoreCase(Commons.SA_STATUS_PENDING))){
			lblinfo.setValue(globalUtils.getMessagePropValue(Commons.SA_RECORD_EXIST));
			securityAdminUtils.setMsgStyle(img_info, lblinfo, true);
//			try {				
//				Messagebox.show(securityAdminUtils.getMessage(Commons.SA_RECORD_EXIST));
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
		}
		else {
			if(parent == null){
				parent = new TmpSaUserGroup();
				parent.setGroup_id(group_id);
			}				

			current_Date = DateUtil.getCurrentDate();
			parent.setDescription(description);			
			parent.setMaker_id(SecurityAdminUtils.get_Logged_in_Username());
			parent.setCreation_dt(DateUtil.format(current_Date));			
			parent.setChecker_id("");
			parent.setDecision_dt("");
			parent.setModified_dt(DateUtil.format(current_Date));
			parent.setModifier_id(SecurityAdminUtils.get_Logged_in_Username());
			parent.setStatus(Commons.SA_STATUS_PENDING);
			parent.setAction(Commons.SA_ACTION_ADD);
			

			parent.getSelectedfunctions().removeAll(parent.getSelectedfunctions());
			t_sagroupmanager.save(parent);			

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
			hst_usergroup.setGroup_id(parent.getGroup_id());
			hst_usergroup.setDescription(parent.getDescription());
			hst_usergroup.setMaker_id(SecurityAdminUtils.get_Logged_in_Username());
			hst_usergroup.setCreation_dt(parent.getCreation_dt());			
			hst_usergroup.setChecker_id("");
			hst_usergroup.setDecision_dt("");
			hst_usergroup.setModified_dt(parent.getModified_dt());
			hst_usergroup.setModifier_id(SecurityAdminUtils.get_Logged_in_Username());
			hst_usergroup.setStatus(parent.getStatus());
			hst_usergroup.setAction(parent.getAction());

			try {
				usergroup_history_manager.save(hst_usergroup);
				saveSuccess = t_sagroupmanager.save(parent);				
				lblinfo.setValue(securityAdminUtils.getMessage(Commons.SA_ADD_SUCCESS));
				securityAdminUtils.setMsgStyle(img_info, lblinfo, false);
				ClearContents(true);

			} catch (Exception e) {				
				lblinfo.setValue(securityAdminUtils.getMessage(Commons.SA_ADD_FAIL));
				securityAdminUtils.setMsgStyle(img_info, lblinfo, true);
				return false;
			}
		}
		return saveSuccess;
	}

	public void Create_Required_Fields_List(){

		lst_requiredfields.clear();

		/**Set the length too**/
		groupId.setMaxlength(Commons.SA_MAXLENGTH_GROUP_ID);
		txtGroupDesc.setMaxlength(Commons.SA_MAXLENGTH_DESCRIPTION);

		lst_requiredfields.add(groupId);
		lst_requiredfields.add(txtGroupDesc);		
	}
	
	public void onClick$btn_reset_top()throws InterruptedException{
		
		String url_ = securityAdminUtils.getGlobalString(Commons.URL_ADD_USERGROUP);		
		
		Executions.getCurrent().sendRedirect(url_);
	}
	
	public void onClick$btn_reset_foot()throws InterruptedException{
		onClick$btn_reset_top();
	}

}
