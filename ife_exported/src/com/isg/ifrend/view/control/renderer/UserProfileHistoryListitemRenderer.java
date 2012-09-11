package com.isg.ifrend.view.control.renderer;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Toolbarbutton;

import com.isg.ifrend.model.bean.ActionType;
import com.isg.ifrend.model.bean.HistUser;
import com.isg.ifrend.model.bean.StatusType;
import com.isg.ifrend.service.HistUserManager;
import com.isg.ifrend.service.ServiceLocator;

public class UserProfileHistoryListitemRenderer implements ListitemRenderer {

	private final String url = "/securityadmin/userprofile/userprofile_compare.zul?id=";
	
	@Override
	public void render(Listitem item, Object object) throws Exception {
		HistUserManager histUserManager = ServiceLocator.getHistUserManager();
		HistUser user = histUserManager.findByIndex(Integer.parseInt(object.toString()));
		item.setValue(user.getHistoryIndex());
		Listcell cellUserID = new Listcell();
		cellUserID.setParent(item);
		Toolbarbutton button = new Toolbarbutton(user.getUserID());
		button.setParent(cellUserID);
		button.setHref(url + String.valueOf(user.getHistoryIndex()));
//		button.setWidth(cellUserID.getWidth());
		new Listcell(StatusType.getDesc(user.getStatus())).setParent(item);
		new Listcell(ActionType.getDesc(user.getAction())).setParent(item);
//		new Listcell(user.getCreatedBy()).setParent(item);
//		try{
//			new Listcell(user.getDateCreated().toString()).setParent(item);
//		}
//		catch(NullPointerException e) {
//			new Listcell().setParent(item);
//		}
		new Listcell(user.getLastModifiedBy()).setParent(item);
		try {
			new Listcell(user.getDateLastModified().toString()).setParent(item);
		}
		catch(NullPointerException e) {
			new Listcell().setParent(item);
		}
		new Listcell(user.getApprovedBy()).setParent(item);
		try {
			new Listcell(user.getDateApproved().toString()).setParent(item);
		}
		catch(NullPointerException e) {
			new Listcell().setParent(item);
		}
	}

}
