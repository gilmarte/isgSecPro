package com.isg.ifrend.view.control.renderer;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Toolbarbutton;

import com.isg.ifrend.model.bean.SaUserGroup;
import com.isg.ifrend.service.SaUserGroupManager;
import com.isg.ifrend.service.ServiceLocator;
import com.isg.ifrend.utils.Commons;
import com.isg.ifrend.utils.GlobalUtils;

public class SaUserGroupListRenderer implements ListitemRenderer{

	@Override
	public void render(Listitem item, Object data) throws Exception {		
		@SuppressWarnings("unused")
		/*TmpSaUserGroupManager groupmanager = ServiceLocator.getTmpSaUserGroupManager();*/
		SaUserGroupManager sa_groupmanager = ServiceLocator.getSaUserGroupManager();
		GlobalUtils globalUtils = new GlobalUtils();

		SaUserGroup usergroup = (SaUserGroup) data;		
		String url = globalUtils.returnPropValue(Commons.GLOBAL_PROPERTIES, Commons.URL_UPDATE_USERGROUP) + 
		"?group_id=" + usergroup.getGroup_id() +
		"&previous_page=search_page"; 

		Toolbarbutton toolBarBtn = new Toolbarbutton(usergroup.getGroup_id());		
		toolBarBtn.setHref(url);
		toolBarBtn.setTooltiptext(globalUtils.returnPropValue(Commons.MESSAGES_PROPERTIES_EN, Commons.TOOLTIP_CLICK_TO_UPDATE));
		
		
		Listcell cellCriteriaID = new Listcell();
		toolBarBtn.setParent(cellCriteriaID);
		cellCriteriaID.setParent(item);

		/*new Listcell(usergroup.getGroup_id()).setParent(item);*/
		new Listcell(usergroup.getDescription()).setParent(item);
		new Listcell(usergroup.getStatus()).setParent(item);
//		new Listcell(usergroup.getMaker_id()).setParent(item);
//		new Listcell(usergroup.getCreation_dt()).setParent(item);
		new Listcell(usergroup.getModifier_id()).setParent(item);
		new Listcell(usergroup.getModified_dt()).setParent(item);
//		new Listcell(usergroup.getChecker_id()).setParent(item);
//		new Listcell(usergroup.getDecision_dt()).setParent(item);
	}

}
