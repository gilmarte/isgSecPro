package com.isg.ifrend.view.control.renderer;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Toolbarbutton;

import com.isg.ifrend.model.bean.CodeType;
import com.isg.ifrend.model.bean.Criteria;
import com.isg.ifrend.model.bean.Element;
import com.isg.ifrend.model.bean.Global;
import com.isg.ifrend.model.bean.Label;
import com.isg.ifrend.model.bean.Mli;
import com.isg.ifrend.model.bean.Script;
import com.isg.ifrend.model.bean.StatusActionType;
import com.isg.ifrend.model.bean.StatusType;
import com.isg.ifrend.utils.Commons;
import com.isg.ifrend.utils.DateUtil;
import com.isg.ifrend.utils.GlobalUtils;

public class GlobalSearchListItemRenderer implements ListitemRenderer {

	private GlobalUtils globalUtils = GlobalUtils.getGlobalUtilsInstance();

	public void render(Listitem item, Object data) throws Exception {
		if (data instanceof Element) {
			this.setListItems(item, (Element) data, Commons.URL_VIEW_ELEMENT);

		} else if (data instanceof Criteria) {
			this.setListItems(item, (Criteria) data, Commons.URL_VIEW_CRITERIA);

		} else if (data instanceof Script) {
			this.setListItems(item, (Script) data, Commons.URL_VIEW_SCRIPT);

		} else if (data instanceof Mli) {
			this.setListItems(item, (Mli) data, Commons.URL_VIEW_MLI);

		} else if (data instanceof CodeType) {
			this.setListItems(item, (CodeType) data, Commons.URL_VIEW_CODETYPE);

		} else if (data instanceof Label) {
			this.setListItems(item, (Label) data, Commons.URL_VIEW_LABEL);
		}
	}

	private void setListItems(Listitem item, Global g, String url) {
		Toolbarbutton toolBarBtn = new Toolbarbutton(g.getId());
		toolBarBtn.setHref(getMstUrl(url, g.getId()));

		Listcell cellId = new Listcell();
		toolBarBtn.setParent(cellId);
		cellId.setParent(item);

		new Listcell(g.getDesc()).setParent(item);
		if (StatusType.PENDING.getId() == g.getStatusID()) {
			new Listcell(StatusActionType.getDesc(g.getActionID()))
					.setParent(item);
		} else {
			new Listcell(StatusType.getDesc(g.getStatusID())).setParent(item);
		}

		new Listcell(g.getLastModifierUserID()).setParent(item);
		new Listcell(DateUtil.format(g.getDateLastModified())).setParent(item);
	}

	private String getMstUrl(String url, String id) {
		return new StringBuilder(globalUtils.getGlobalPropValue(url))
				.append(Commons.URL_DELIMITER).append(Commons.URL_PARAM_MST_ID)
				.append(Commons.EQUAL_SIGN).append(id).toString();
	}
}