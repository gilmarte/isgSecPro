package com.isg.ifrend.view.control.renderer;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Toolbarbutton;

import com.isg.ifrend.model.bean.ActionType;
import com.isg.ifrend.model.bean.HistUserOrganization;
import com.isg.ifrend.model.bean.StatusType;
import com.isg.ifrend.service.HistUserOrganizationManager;
import com.isg.ifrend.service.ServiceLocator;

public class UserOrganizationHistoryListitemRenderer implements ListitemRenderer {

	private final String url = "/securityadmin/userorganization/userorganization_compare.zul?id=";
	
	@Override
	public void render(Listitem item, Object object) throws Exception {
		HistUserOrganizationManager userOrgManager = ServiceLocator.getHistUserOrganizationManager();
		
		HistUserOrganization userOrg = userOrgManager.findByIndex(Integer.parseInt(object.toString()));
		item.setValue(userOrg.getHistoryIndex());
		Listcell cellUserID = new Listcell();
		cellUserID.setParent(item);
		Toolbarbutton button = new Toolbarbutton(userOrg.getOrgID());
		button.setParent(cellUserID);
		button.setHref(url + userOrg.getHistoryIndex());

		new Listcell(StatusType.getDesc(userOrg.getStatus())).setParent(item);
		new Listcell(ActionType.getDesc(userOrg.getAction())).setParent(item);
//		new Listcell(userOrg.getCreatedBy()).setParent(item);
//		try{
//			new Listcell(userOrg.getDateCreated().toString()).setParent(item);
//		}
//		catch(NullPointerException e) {
//			new Listcell().setParent(item);
//		}
		new Listcell(userOrg.getLastModifiedBy()).setParent(item);
		try {
			new Listcell(userOrg.getDateLastModified().toString()).setParent(item);
		}
		catch(NullPointerException e) {
			new Listcell().setParent(item);
		}
		new Listcell(userOrg.getApprovedBy()).setParent(item);
		try {
			new Listcell(userOrg.getDateApproved().toString()).setParent(item);
		}
		catch(NullPointerException e) {
			new Listcell().setParent(item);
		}
	}

}
