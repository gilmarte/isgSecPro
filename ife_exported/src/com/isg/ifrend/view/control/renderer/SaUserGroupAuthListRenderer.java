package com.isg.ifrend.view.control.renderer;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Toolbarbutton;

import com.isg.ifrend.model.bean.TmpSaUserGroup;
import com.isg.ifrend.utils.Commons;
import com.isg.ifrend.utils.GlobalUtils;

/**
 * author: Gerald L. de Guzman
 * 
 * description:
 * This renderer will be used if usergroup_compare.zul will be used. Else 
 * SaUserGroupAuthListRenderer2 will be used to display the old and new values 
 * in the list item.
 * **/


public class SaUserGroupAuthListRenderer implements ListitemRenderer{

	@Override
	public void render(Listitem item, Object data) throws Exception {		
			
		GlobalUtils globalUtils = new GlobalUtils();

		TmpSaUserGroup usergroup = (TmpSaUserGroup) data;		
		String url = globalUtils.returnPropValue(Commons.GLOBAL_PROPERTIES, Commons.URL_UPDATE_USERGROUP) + "?group_id=" 
		+ usergroup.getGroup_id() +
		"&previous_page=authorize_page";

		String url2 = globalUtils.returnPropValue(Commons.GLOBAL_PROPERTIES, Commons.URL_COMPARE_USERGROUP) + "?group_id=" 
		+ usergroup.getGroup_id();/* +
		"&previous_page=authorize_page";*/
		
		Toolbarbutton toolBarBtn = new Toolbarbutton(usergroup.getGroup_id());		
		toolBarBtn.setHref(url);
		toolBarBtn.setTooltiptext(globalUtils.returnPropValue(Commons.MESSAGES_PROPERTIES_EN, Commons.TOOLTIP_CLICK_TO_UPDATE));
						
		Listcell cellCriteriaID = new Listcell();		
		cellCriteriaID.setParent(item);
				
		toolBarBtn.setParent(cellCriteriaID);
		/*new Listcell(usergroup.getGroup_id()).setParent(item);*/
		new Listcell(usergroup.getAction()).setParent(item);
		new Listcell(usergroup.getStatus()).setParent(item);
		new Listcell(usergroup.getMaker_id()).setParent(item);
		new Listcell(usergroup.getCreation_dt()).setParent(item);
		new Listcell(usergroup.getModified_dt()).setParent(item);
		new Listcell(usergroup.getChecker_id()).setParent(item);
		new Listcell(usergroup.getDecision_dt()).setParent(item);
		
		Toolbarbutton toolBarBtn2 = new Toolbarbutton("COMPARE");	
		toolBarBtn2.setHref(url2);
		toolBarBtn2.setTooltiptext(globalUtils.returnPropValue(Commons.MESSAGES_PROPERTIES_EN, Commons.TOOLTIP_CLICK_TO_COMPARE));
		
		Listcell cellCriteriaID2 = new Listcell();		
		cellCriteriaID2.setParent(item);
		
		toolBarBtn2.setParent(cellCriteriaID2);
				
	}

}
