package com.isg.ifrend.view.control.renderer;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Toolbarbutton;

import com.isg.ifrend.model.bean.StatusActionType;
import com.isg.ifrend.model.bean.StatusType;
import com.isg.ifrend.model.bean.User;
import com.isg.ifrend.service.ServiceLocator;
import com.isg.ifrend.service.UserManager;
import com.isg.ifrend.utils.Commons;
import com.isg.ifrend.utils.DateUtil;
import com.isg.ifrend.utils.GlobalUtils;

public class UserProfileSearchListitemRenderer implements ListitemRenderer {

//	private final String url = "/securityadmin/userprofile/userprofile_view_edit.zul?id=";
	private GlobalUtils globalUtils = GlobalUtils.getGlobalUtilsInstance();
	
	@Override
	public void render(Listitem item, Object object) throws Exception {
		UserManager userManager = ServiceLocator.getUserManager();
		
		User user = userManager.findById(object.toString());
		item.setValue(user.getUserID());
		Listcell cellUserID = new Listcell();
		cellUserID.setParent(item);
		Toolbarbutton button = new Toolbarbutton(user.getUserID());
		button.setParent(cellUserID);
		StringBuffer sb = new StringBuffer(globalUtils.getGlobalPropValue(Commons.URL_UPDATE_USER_PROFILE));
		sb.append(Commons.URL_DELIMITER);
		sb.append(Commons.URL_PARAM_MST_ID);
		sb.append(Commons.EQUAL_SIGN);
		sb.append(user.getUserID());
		button.setHref(sb.toString());
//		button.setWidth(cellUserID.getWidth());
		
		Listcell cellUserName = new Listcell(user.getUserName());
		cellUserName.setParent(item);
//		new Listcell(StatusType.getDesc(user.getStatus())).setParent(item);
//		new Listcell(ActionType.getDesc(user.getAction())).setParent(item);
		if(user.getStatus() == StatusType.PENDING.getId()) {
			new Listcell(StatusActionType.getDesc(user.getAction())).setParent(item);;
		}
		else {
			new Listcell(StatusType.getDesc(user.getStatus())).setParent(item);
		}
//		new Listcell(user.getCreatedBy()).setParent(item);
//		new Listcell(DateUtil.format(user.getDateCreated())).setParent(item);
		new Listcell(user.getLastModifiedBy()).setParent(item);
		new Listcell(DateUtil.format(user.getDateLastModified())).setParent(item);
//		new Listcell(user.getApprovedBy()).setParent(item);
//		new Listcell(DateUtil.format(user.getDateApproved())).setParent(item);
	}

}
