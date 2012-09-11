package com.isg.ifrend.view.control.renderer;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Toolbarbutton;

import com.isg.ifrend.model.bean.ActionType;
import com.isg.ifrend.model.bean.Global;
import com.isg.ifrend.model.bean.HistCriteria;
import com.isg.ifrend.model.bean.HstCodeType;
import com.isg.ifrend.model.bean.HstElement;
import com.isg.ifrend.model.bean.HstLabel;
import com.isg.ifrend.model.bean.HstMli;
import com.isg.ifrend.model.bean.HstScript;
import com.isg.ifrend.model.bean.StatusType;
import com.isg.ifrend.utils.Commons;
import com.isg.ifrend.utils.DateUtil;
import com.isg.ifrend.utils.GlobalUtils;

public class GlobalHistoryListItemRenderer implements ListitemRenderer {

	public void render(Listitem item, Object data) throws Exception {

		if (data instanceof HstElement) {
			HstElement entity = (HstElement) data;
			this.setListItems(
					item,
					entity,
					getUrl(Commons.URL_VIEW_ELEMENT,
							entity.getHis_elem_trans_id()));

		} else if (data instanceof HistCriteria) {
			HistCriteria entity = (HistCriteria) data;
			this.setListItems(item, entity,
					getUrl(Commons.URL_VIEW_CRITERIA, entity.getTrans_id()));

		} else if (data instanceof HstScript) {
			HstScript entity = (HstScript) data;
			this.setListItems(item, entity,
					getUrl(Commons.URL_VIEW_SCRIPT, entity.getHstScriptID()));

		} else if (data instanceof HstMli) {
			HstMli entity = (HstMli) data;
			this.setListItems(item, entity,
					getUrl(Commons.URL_VIEW_MLI, entity.getHstMliID()));

		} else if (data instanceof HstCodeType) {
			HstCodeType entity = (HstCodeType) data;
			this.setListItems(
					item,
					entity,
					getUrl(Commons.URL_VIEW_CODETYPE, entity.getHstCodeTypeID()));

		} else if (data instanceof HstLabel) {
			HstLabel entity = (HstLabel) data;
			this.setListItems(item, entity,
					getUrl(Commons.URL_VIEW_LABEL, entity.getHstLabelID()));
		}
	}

	private void setListItems(Listitem item, Global g, String href) {
		Toolbarbutton toolBarBtn = new Toolbarbutton(g.getId());
		toolBarBtn.setHref(href);

		Listcell cellId = new Listcell();
		toolBarBtn.setParent(cellId);
		cellId.setParent(item);

		new Listcell(g.getDesc()).setParent(item);

		new Listcell(StatusType.getDesc(g.getStatusID())).setParent(item);
		new Listcell(ActionType.getDesc(g.getActionID())).setParent(item);

		new Listcell(g.getLastModifierUserID()).setParent(item);
		new Listcell(DateUtil.format(g.getDateLastModified())).setParent(item);

		new Listcell(g.getApproverUserID()).setParent(item);
		if (null == g.getApproverUserID()) {
			new Listcell().setParent(item);
		} else {
			new Listcell(DateUtil.format(g.getDateApproved())).setParent(item);
		}
	}

	private String getUrl(String url, int id) {
		return new StringBuilder(GlobalUtils.getGlobalUtilsInstance()
				.getGlobalPropValue(url)).append(Commons.URL_DELIMITER)
				.append(Commons.URL_PARAM_HST_ID).append(Commons.EQUAL_SIGN)
				.append(id).toString();
	}
}