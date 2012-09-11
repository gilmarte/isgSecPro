/**
 * 
 */
package com.isg.ifrend.view.control;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.isg.ifrend.model.bean.ActionType;
import com.isg.ifrend.model.bean.SaUserGroupHistory;
import com.isg.ifrend.model.bean.StatusType;
import com.isg.ifrend.service.SaUserGroupHistoryManager;
import com.isg.ifrend.service.ServiceLocator;
import com.isg.ifrend.utils.Commons;
import com.isg.ifrend.utils.ExportUtil;
import com.isg.ifrend.utils.SecurityAdminUtils;
import com.isg.ifrend.view.control.renderer.SaUserGroupHistoryListRenderer;

/**
 * @author gerald.deguzman
 *
 */
public class UserGroupHistoryPageViewCtrl extends GenericForwardComposer {

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
	private Textbox bandbox;	
	private Combobox cmbAction;
	private Combobox cmbStatus;
	private Datebox startdate;
	private Datebox enddate;


//	private GlobalUtils globalUtils = new GlobalUtils();
	private SecurityAdminUtils saUtils = new SecurityAdminUtils();
	
//	private TmpSaUserGroupManager tmp_sa_groupmanager = ServiceLocator.getTmpSaUserGroupManager();
//	private SaUserGroupManager sa_groupmanager = ServiceLocator.getSaUserGroupManager();

	private SaUserGroupHistoryManager usergroup_history_manager = ServiceLocator.getSaUserGroupHistoryManager();

	private List<SaUserGroupHistory>lst_usergroup = new ArrayList<SaUserGroupHistory>();	
	private ListModelList lml_tmpsausergroup = new ListModelList();
	
	private List<String> actionList = new ArrayList<String>();
	private List<String> statusList = new ArrayList<String>();


	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		self.setAttribute(self.getId(), this);

		for(ActionType action : ActionType.values()) {
			actionList.add(action.getDesc());
		}
		for(StatusType status : StatusType.values()) {
			if(status.equals(StatusType.ACTIVE) || status.equals(StatusType.DELETED)) {
				continue;
			}
			statusList.add(status.getDesc());
		}
		cmbAction.setText(ActionType.ALL.getDesc());
		cmbStatus.setText(StatusType.ALL.getDesc());
		
		Perform_Refresh();

//		comp.setAttribute(comp.getId() + "Control", this, true);
		binder = new AnnotateDataBinder();
		binder.loadAll();

	}

	public Listbox getLstsausergroup() {
		return lstsausergroup;
	}

	public void setLstsausergroup(Listbox lstsausergroup) {
		this.lstsausergroup = lstsausergroup;
	}

	public List<SaUserGroupHistory> getLst_usergroup() {
		return lst_usergroup;
	}

	public void setLst_usergroup(List<SaUserGroupHistory> lst_usergroup) {
		this.lst_usergroup = lst_usergroup;
	}

	public ListModelList getLml_tmpsausergroup() {
		return lml_tmpsausergroup;
	}

	public void setLml_tmpsausergroup(ListModelList lml_tmpsausergroup) {
		this.lml_tmpsausergroup = lml_tmpsausergroup;
	}

	/*public void onSelect$lstsausergroup()throws InterruptedException{
		bandbox.setValue(lstsausergroup.getSelectedItem().getLabel());
		bandbox.close();

		//Perform_Search(bandbox.getValue());
	}*/

	public void onClick$btn_search()throws InterruptedException{
		Date sdate = startdate.getValue();
		Date edate = enddate.getValue();
		
		String startdate_;
		String enddate_;
		
		if(sdate == null){
			startdate_ = "";
		}else{
			startdate_ = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").format(sdate);
		}
		
		if(edate == null){
			enddate_ = "";
		}else{
			enddate_ = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").format(edate);
		}

		Perform_Custom_Search(bandbox.getValue().toUpperCase(), cmbAction.getValue().toUpperCase(),
				cmbStatus.getValue().toUpperCase(), startdate_, enddate_ );
	}

	public void onClick$btn_back()throws InterruptedException{
		SecurityAdminUtils.Redirect_to_Page(Commons.URL_AUTHORIZE_USERGROUP);
	}

	/**Capture the bandbox when Enter key was pressed**/
	public void onOK$bandbox()throws InterruptedException{
		onClick$btn_search();
	}
	
	public void onClick$btn_export() throws InterruptedException{
		try {
			ExportUtil.exportToExcel("UserGroupHistoryList.xls", list_sausergroup);
		} catch (IOException e) {
			StringBuffer err_ = new StringBuffer();
			err_.append(saUtils.getMessage(Commons.SA_MSG_ERR_EXPORT_TO_EXCEL));
			err_.append(Commons.NEW_LINE);
			err_.append("Error:" + e.getMessage());
			Messagebox.show(err_.toString(), Commons.MSGBOX_TITLE_ERROR, Messagebox.OK, Messagebox.ERROR);			
		}
	}
	
	public void Perform_Search(String group_id){
		lst_usergroup = usergroup_history_manager.getSaUserGroupHistoryListById(group_id);		
		lml_tmpsausergroup = new ListModelList(lst_usergroup);
		list_sausergroup.setModel(lml_tmpsausergroup);
	}

	public void Perform_Custom_Search(String group_id, String action, String status, String starddate_, String endddate_){
		lst_usergroup = usergroup_history_manager.getCustom_SaUserGroupHistoryList(group_id, action, status,starddate_, endddate_);
		lml_tmpsausergroup = new ListModelList(lst_usergroup);
		list_sausergroup.setModel(lml_tmpsausergroup);
	}

	public void Perform_Refresh(){

		lst_usergroup = new ArrayList<SaUserGroupHistory>(); 
		lst_usergroup = usergroup_history_manager.getCustom_SaUserGroupHistoryList("", "", "","","");
		lml_tmpsausergroup = new ListModelList(lst_usergroup);

		list_sausergroup.setItemRenderer(new SaUserGroupHistoryListRenderer());
	}

	public List<String> getActionList() {
		return actionList;
	}

	public void setActionList(List<String> actionList) {
		this.actionList = actionList;
	}

	public List<String> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<String> statusList) {
		this.statusList = statusList;
	}


}
