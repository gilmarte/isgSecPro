package com.isg.ifrend.view.control.renderer;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Toolbarbutton;

import com.isg.ifrend.model.bean.StatusActionType;
import com.isg.ifrend.model.bean.StatusType;
import com.isg.ifrend.model.bean.UserOrganization;
import com.isg.ifrend.service.ServiceLocator;
import com.isg.ifrend.service.UserOrganizationManager;
import com.isg.ifrend.utils.Commons;
import com.isg.ifrend.utils.DateUtil;
import com.isg.ifrend.utils.GlobalUtils;

public class UserOrganizationSearchListitemRenderer implements ListitemRenderer {

//	private final String url = "/securityadmin/userorganization/userorganization_view_edit.zul?id=";
	private GlobalUtils globalUtils = GlobalUtils.getGlobalUtilsInstance();
	
	@Override
	public void render(Listitem item, Object object) throws Exception {
		UserOrganizationManager userOrgManager = ServiceLocator.getUserOrganizationManager();
		
		UserOrganization userOrg = userOrgManager.findById(object.toString());
		item.setValue(userOrg.getOrgID());
		Listcell cellUserOrgID = new Listcell();
		cellUserOrgID.setParent(item);
		Toolbarbutton button = new Toolbarbutton(userOrg.getOrgID());
		button.setParent(cellUserOrgID);
		StringBuffer sb = new StringBuffer(globalUtils.getGlobalPropValue(Commons.URL_UPDATE_USER_ORG));
		sb.append(Commons.URL_DELIMITER);
		sb.append(Commons.URL_PARAM_MST_ID);
		sb.append(Commons.EQUAL_SIGN);
		sb.append(userOrg.getOrgID());
		button.setHref(sb.toString());
//		button.setWidth(cellUserID.getWidth());
		
		new Listcell(userOrg.getOrgDesc()).setParent(item);
//		new Listcell(StatusType.getDesc(userOrg.getStatus())).setParent(item);
//		new Listcell(ActionType.getDesc(userOrg.getAction())).setParent(item);
		if(userOrg.getStatus() == StatusType.PENDING.getId()) {
			new Listcell(StatusActionType.getDesc(userOrg.getAction())).setParent(item);;
		}
		else {
			new Listcell(StatusType.getDesc(userOrg.getStatus())).setParent(item);
		}
//		new Listcell(userOrg.getCreatedBy()).setParent(item);
//		new Listcell(DateUtil.format(userOrg.getDateCreated())).setParent(item);
		new Listcell(userOrg.getLastModifiedBy()).setParent(item);
		new Listcell(DateUtil.format(userOrg.getDateLastModified())).setParent(item);
//		new Listcell(userOrg.getApprovedBy()).setParent(item);
//		new Listcell(DateUtil.format(userOrg.getDateApproved())).setParent(item);
	}

}
