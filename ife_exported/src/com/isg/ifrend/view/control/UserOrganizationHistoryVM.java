/**
 * 
 */
package com.isg.ifrend.view.control;

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
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.isg.ifrend.model.bean.ActionType;
import com.isg.ifrend.model.bean.StatusType;
import com.isg.ifrend.service.HistUserOrganizationManager;
import com.isg.ifrend.service.ServiceLocator;
import com.isg.ifrend.utils.Commons;
import com.isg.ifrend.utils.ExportUtil;
import com.isg.ifrend.utils.GlobalUtils;
import com.isg.ifrend.utils.SecurityAdminUtils;
import com.isg.ifrend.view.control.renderer.UserOrganizationHistoryListitemRenderer;

/**
 * @author elvin.aquino
 *
 */
public class UserOrganizationHistoryVM extends GenericForwardComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	AnnotateDataBinder binder;

	@SuppressWarnings("unused")
	private Window userOrganizationHistoryPage;
	private Listbox lb_user_org_history;
	@SuppressWarnings("unused")
	private Button btn_search;
	private Button btn_back;
	private Textbox tbx_org_id;	
	private Combobox cbb_action;
	private Combobox cbb_status;
	private Datebox startdate;
	private Datebox enddate;


	GlobalUtils globalUtils = new GlobalUtils();

	HistUserOrganizationManager userOrgHistManager = ServiceLocator.getHistUserOrganizationManager();

	List<String> actionList = new ArrayList<String>();
	List<String> statusList = new ArrayList<String>();
	
	List<Integer> userOrgHistoryList;	


	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		self.setAttribute(self.getId(), this);

		userOrgHistoryList = userOrgHistManager.getUserOrgHistoryIndex();
		for(ActionType action : ActionType.values()) {
			actionList.add(action.getDesc());
		}
		for(StatusType status : StatusType.values()) {
			if(status.equals(StatusType.ACTIVE) || status.equals(StatusType.DELETED)) {
				continue;
			}
			statusList.add(status.getDesc());
		}
		cbb_action.setText(ActionType.ALL.getDesc());
		cbb_status.setText(StatusType.ALL.getDesc());
		
		lb_user_org_history.setItemRenderer(new UserOrganizationHistoryListitemRenderer());
		btn_back.setHref(globalUtils.getGlobalPropValue(Commons.URL_AUTHORIZE_USER_ORG));
		comp.setAttribute(comp.getId() + "Control", this, true);
		binder = new AnnotateDataBinder();
		binder.loadAll();

	}

	public void onClick$btn_search()throws InterruptedException{
		Date sdate = startdate.getValue();
		Date edate = enddate.getValue();

		userOrgHistoryList = userOrgHistManager.getUserOrgHistoryIndexByCustomFilter(tbx_org_id.getText(),
				ActionType.getId(cbb_action.getValue()), StatusType.getId(cbb_status.getValue()), sdate, edate);
		ListModelList model = new ListModelList(userOrgHistoryList);
		lb_user_org_history.setModel(model);
	}
	
	public void onClick$btn_export() throws InterruptedException {
		try {
			Listbox listBox = new Listbox();
			Listhead listHeader = new Listhead();
			for(Object o : lb_user_org_history.getChildren()) {
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
			listBox.setModel(new ListModelList(userOrgHistManager.getUserOrgHistoryByCustomFilter(tbx_org_id.getText(),
				ActionType.getId(cbb_action.getValue()), StatusType.getId(cbb_status.getValue()),
				startdate.getValue(), enddate.getValue())));
			ExportUtil.exportToExcel(globalUtils.getGlobalPropValue(Commons.SCREEN_USER_ORG_HISTORY), listBox);
		}
		catch (Exception e){
			e.printStackTrace();
			GlobalUtils.showMessage(globalUtils.getMessagePropValue(Commons.ERR_MSG_EXPORT),
				globalUtils.getGlobalPropValue(Commons.MSG_HEADER_ERROR),
				Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void onClick$btn_back()throws InterruptedException{
		SecurityAdminUtils.Redirect_to_Page(Commons.URL_AUTHORIZE_USER_ORG);
	}

	public List<Integer> getUserOrgHistoryList() {
		return userOrgHistoryList;
	}

	public void setUserOrgHistoryList(List<Integer> userOrgHistoryList) {
		this.userOrgHistoryList = userOrgHistoryList;
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
