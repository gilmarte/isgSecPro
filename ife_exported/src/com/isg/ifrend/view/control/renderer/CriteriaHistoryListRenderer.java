package com.isg.ifrend.view.control.renderer;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Toolbarbutton;

import com.isg.ifrend.model.bean.HistCriteria;
import com.isg.ifrend.utils.Commons;
import com.isg.ifrend.utils.GlobalUtils;

public class CriteriaHistoryListRenderer implements ListitemRenderer{

	public void render(Listitem item, Object data) throws Exception {
		// TODO Auto-generated method stub
		
		/*SimpleDateFormat sdFormat = new SimpleDateFormat(Commons.DATE_FORMAT);*/
		GlobalUtils globalUtils = new GlobalUtils();
		HistCriteria histcriteria = (HistCriteria) data;
		
		/*/gbcr/criteria/criteria_view.zul?SEARCH_ID=1*/
		String url_ = globalUtils.returnPropValue(Commons.GLOBAL_PROPERTIES,Commons.URL_COMPARE_CRITERIA) + "?mid=" + histcriteria.getCriteria_id();
		
		Toolbarbutton toolBarBtn = new Toolbarbutton(String.valueOf(histcriteria.getCriteria_id()));
		toolBarBtn.setHref(url_);
		
		Listcell cellCriteriaID = new Listcell();
		toolBarBtn.setParent(cellCriteriaID);
		cellCriteriaID.setParent(item);
		
		/*new Listcell(String.valueOf(histcriteria.getCriteria_id())).setParent(item);*/
		new Listcell(histcriteria.getDescription()).setParent(item);
		/* Common - START */
		new Listcell(String.valueOf(histcriteria.getStatusID())).setParent(item);
		new Listcell(histcriteria.getCreatorUserID()).setParent(item);
		new Listcell(String.valueOf(histcriteria.getDateCreated())).setParent(item);

		new Listcell(histcriteria.getLastModifierUserID()).setParent(item);
		new Listcell(String.valueOf(histcriteria.getDateLastModified())).setParent(item);
		new Listcell(histcriteria.getApproverUserID()).setParent(item);
		new Listcell(String.valueOf(histcriteria.getDateApproved())).setParent(item);
		/* Common - END */
		/*Executions.getCurrent().setAttribute(globalUtils.returnPropValue(Commons.GLOBAL_PROPERTIES,Commons.SESSION_CRITERIA),criteria);*/
	}

}
