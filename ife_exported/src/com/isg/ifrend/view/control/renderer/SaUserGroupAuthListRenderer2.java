package com.isg.ifrend.view.control.renderer;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.isg.ifrend.model.bean.ActionType;
import com.isg.ifrend.model.bean.SaUserGroup;
import com.isg.ifrend.model.bean.TmpSaUserGroup;
import com.isg.ifrend.service.SaUserGroupManager;
import com.isg.ifrend.service.ServiceLocator;
import com.isg.ifrend.service.TmpSaUserGroupManager;
import com.isg.ifrend.utils.Commons;
import com.isg.ifrend.utils.GlobalUtils;
import com.mysql.jdbc.StringUtils;

public class SaUserGroupAuthListRenderer2 implements ListitemRenderer {

//	private Double width = (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.22);
	
	@SuppressWarnings("unused")
	@Override
	public void render(Listitem item, Object data) throws Exception {		

		GlobalUtils globalUtils = new GlobalUtils();
		TmpSaUserGroupManager tmp_sausergroup_manager = ServiceLocator.getTmpSaUserGroupManager();
		SaUserGroupManager sausergroup_manager = ServiceLocator.getSaUserGroupManager();

		TmpSaUserGroup tmp_sausergroup = (TmpSaUserGroup) data;
		SaUserGroup sausergroup_ = new SaUserGroup();

		StringBuffer sb_oldvalues = new StringBuffer();
		StringBuffer sb_newvalues = new StringBuffer();

		List<String>list1 = new ArrayList<String>();
		List<String>list2 = new ArrayList<String>();

		/*
		 * Query for that bean
		 * */
		if(tmp_sausergroup != null){
			sausergroup_ = sausergroup_manager.getSaUserGroup(tmp_sausergroup.getGroup_id());
		}

		if(sausergroup_ == null){
			sausergroup_ = new SaUserGroup();
		}else{
			if(sausergroup_.getStatus().equals(Commons.SA_STATUS_DELETED)){
				sausergroup_.setStatus("");
				sausergroup_.setDescription("");
				/*sausergroup_.setVcategory("");*/
				sausergroup_.getSa_selectedfunctions().removeAll(sausergroup_.getSa_selectedfunctions());
			}
		}

		/*Group ID Column (Start)*/
		String url = globalUtils.returnPropValue(Commons.GLOBAL_PROPERTIES, Commons.URL_UPDATE_USERGROUP) + "?group_id=" 
		+ tmp_sausergroup.getGroup_id() +
		"&previous_page=authorize_page";

		Toolbarbutton toolBarBtn = new Toolbarbutton(tmp_sausergroup.getGroup_id());		
		toolBarBtn.setHref(url);
		toolBarBtn.setTooltiptext(globalUtils.returnPropValue(Commons.MESSAGES_PROPERTIES_EN, Commons.TOOLTIP_CLICK_TO_UPDATE));

		Listcell cellGroupID = new Listcell();		
		cellGroupID.setParent(item);

		toolBarBtn.setParent(cellGroupID);
		/*Group ID Column (End)*/

		new Listcell(tmp_sausergroup.getAction()).setParent(item);
		new Listcell(tmp_sausergroup.getModifier_id()).setParent(item);
		new Listcell(tmp_sausergroup.getModified_dt()).setParent(item);
		/*new Listcell(tmp_sausergroup.getStatus()).setParent(item);*/

		// TASK: Will convert status and action to IDs?
		if(tmp_sausergroup.getAction().equalsIgnoreCase(ActionType.UPDATE.getDesc())) {
			try {
				if(!tmp_sausergroup.getDescription().equals(sausergroup_.getDescription().toString()) ){						
					sb_newvalues.append("Description : " + tmp_sausergroup.getDescription() + Commons.NEW_LINE);
					sb_oldvalues.append("Description : " + sausergroup_.getDescription() + Commons.NEW_LINE);
				}
			}
			catch(NullPointerException e) {
				if(!StringUtils.isNullOrEmpty(tmp_sausergroup.getDescription())) {
					sb_newvalues.append("Description : " + tmp_sausergroup.getDescription() + Commons.NEW_LINE);
					sb_oldvalues.append("Description : " + Commons.NEW_LINE);
				}
				else if(!StringUtils.isNullOrEmpty(sausergroup_.getDescription())) {
					sb_newvalues.append("Description : " + Commons.NEW_LINE);
					sb_oldvalues.append("Description : " + sausergroup_.getDescription() + Commons.NEW_LINE);
				}
			}

			/*try {
				if(!tmp_sausergroup.getVcategory().equalsIgnoreCase(sausergroup_.getVcategory().toString())){
					sb_newvalues.append("Category : " + tmp_sausergroup.getVcategory() + Commons.NEW_LINE);
					sb_oldvalues.append("Category : " + sausergroup_.getVcategory() + Commons.NEW_LINE);
				}
			}
			catch(NullPointerException e) {
				if(!StringUtils.isNullOrEmpty(tmp_sausergroup.getVcategory())) {
					sb_newvalues.append("Category : " + tmp_sausergroup.getVcategory() + Commons.NEW_LINE);
					sb_oldvalues.append("Category : " + Commons.NEW_LINE);
				}
				else if(!StringUtils.isNullOrEmpty(sausergroup_.getVcategory())) {
					sb_newvalues.append("Category : " + Commons.NEW_LINE);
					sb_oldvalues.append("Category : " + sausergroup_.getVcategory() + Commons.NEW_LINE);
				}
			}*/
		}

		/*for(SelectedFunctions sf: tmp_sausergroup.getSelectedfunctions()){
			list1.add(sf.getDescription());
		}

		for(SaSelectedFunctions s_sf: sausergroup_.getSa_selectedfunctions()){
			list2.add(s_sf.getDescription());
		}

		if(list1.size() < list2.size()){
			list1.retainAll(list2);
		}else{
			list2.retainAll(list1);
		}

		if(list1.size() != list2.size()){
			sb_oldvalues.append("Selected Functions are different.");
			sb_newvalues.append("Selected Functions are different.");
		}*/

		Textbox txt_Oldvalues = new Textbox();
		txt_Oldvalues.setMultiline(true);

		Textbox txt_Newvalues = new Textbox();		
		txt_Newvalues.setMultiline(true);

		Listcell cellOldValues = new Listcell();		
		cellOldValues.setParent(item);
		Listcell cellNewValues = new Listcell();		
		cellNewValues.setParent(item);
		if(tmp_sausergroup.getAction().equalsIgnoreCase(ActionType.UPDATE.getDesc())) {
			txt_Oldvalues.setParent(cellOldValues);
			txt_Oldvalues.setValue(sb_oldvalues.toString());
			txt_Oldvalues.setRows(Commons.DISPLAY_ROWS);
			txt_Oldvalues.setWidth("210px");
			txt_Oldvalues.setReadonly(true);
			txt_Newvalues.setParent(cellNewValues);
			txt_Newvalues.setValue(sb_newvalues.toString());
			txt_Newvalues.setRows(Commons.DISPLAY_ROWS);
			txt_Newvalues.setWidth("210px");
			txt_Newvalues.setReadonly(true);
		}
		


//		String url2 = globalUtils.returnPropValue(Commons.GLOBAL_PROPERTIES, Commons.URL_COMPARE_USERGROUP) + "?group_id=" 
//		+ tmp_sausergroup.getGroup_id() +
//		"&previous_page=authorize_page";
//
//		Toolbarbutton toolBarBtn2 = new Toolbarbutton("COMPARE");	
//		toolBarBtn2.setHref(url2);
//		toolBarBtn2.setTooltiptext(globalUtils.returnPropValue(Commons.MESSAGES_PROPERTIES_EN, Commons.TOOLTIP_CLICK_TO_COMPARE));
//
//		Listcell cellCriteriaID2 = new Listcell();		
//		cellCriteriaID2.setParent(item);
//
//		toolBarBtn2.setParent(cellCriteriaID2);

	}

}
