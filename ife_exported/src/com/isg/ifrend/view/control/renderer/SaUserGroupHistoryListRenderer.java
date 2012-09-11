package com.isg.ifrend.view.control.renderer;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Toolbarbutton;

import com.isg.ifrend.model.bean.SaUserGroupHistory;
import com.isg.ifrend.utils.Commons;
import com.isg.ifrend.utils.GlobalUtils;

public class SaUserGroupHistoryListRenderer implements ListitemRenderer {

	@Override
	public void render(Listitem item, Object data) throws Exception {		
			
		GlobalUtils globalUtils = new GlobalUtils();

		SaUserGroupHistory usergrouphistory = (SaUserGroupHistory) data;		
		String url = globalUtils.returnPropValue(Commons.GLOBAL_PROPERTIES, Commons.URL_COMPARE_USERGROUP) + "?group_id=" 
		+ usergrouphistory.getGroup_id() +
		"&seq_id=" + usergrouphistory.getSeq_id().toString() + 
		"&previous_page=history_page";

		Toolbarbutton toolBarBtn = new Toolbarbutton(usergrouphistory.getGroup_id());		
		toolBarBtn.setHref(url);
		toolBarBtn.setTooltiptext(globalUtils.returnPropValue(Commons.MESSAGES_PROPERTIES_EN, Commons.TOOLTIP_CLICK_TO_VIEW));
		
		
		Listcell cellCriteriaID = new Listcell();
		toolBarBtn.setParent(cellCriteriaID);
		cellCriteriaID.setParent(item);
		 						
		/*new Listcell(usergrouphistory.getGroup_id()).setParent(item);*/
		new Listcell(usergrouphistory.getStatus()).setParent(item);
		new Listcell(usergrouphistory.getAction()).setParent(item);
//		new Listcell(usergrouphistory.getMaker_id()).setParent(item);
//		new Listcell(usergrouphistory.getCreation_dt()).setParent(item);
		new Listcell(usergrouphistory.getModifier_id()).setParent(item);
		new Listcell(usergrouphistory.getModified_dt()).setParent(item);
		new Listcell(usergrouphistory.getChecker_id()).setParent(item);
		new Listcell(usergrouphistory.getDecision_dt()).setParent(item);
				
	}

}
