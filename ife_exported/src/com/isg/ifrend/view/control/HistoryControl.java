/**
 * 
 */
package com.isg.ifrend.view.control;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

import com.isg.ifrend.model.bean.ActionType;
import com.isg.ifrend.model.bean.EntityType;
import com.isg.ifrend.model.bean.Global;
import com.isg.ifrend.model.bean.HistCriteria;
import com.isg.ifrend.model.bean.HstCodeType;
import com.isg.ifrend.model.bean.HstElement;
import com.isg.ifrend.model.bean.HstLabel;
import com.isg.ifrend.model.bean.HstMli;
import com.isg.ifrend.model.bean.HstScript;
import com.isg.ifrend.model.bean.StatusType;
import com.isg.ifrend.service.ServiceLocator;
import com.isg.ifrend.service.impl.GenericManagerImpl;
import com.isg.ifrend.utils.Commons;
import com.isg.ifrend.utils.DateUtil;
import com.isg.ifrend.utils.ExportUtil;
import com.isg.ifrend.view.control.renderer.GlobalHistoryListItemRenderer;

public class HistoryControl extends GenericForwardComposer {

	private static final long serialVersionUID = -4240621218814704860L;

	private static final String PROPERTY_ELEMENT_ID = "hst_elem_id";
	private static final String PROPERTY_CRITERIA_ID = "criteria_id";
	private static final String PROPERTY_SCRIPT_ID = "scriptID";
	private static final String PROPERTY_MLI_ID = "mliID";
	private static final String PROPERTY_CODETYPE_ID = "codeTypeID";
	private static final String PROPERTY_LABEL_ID = "labelID";

	private static final String PROPERTY_ELEMENT_DESC = "hst_elem_desc";
	private static final String PROPERTY_CRITERIA_DESC = "description";
	private static final String PROPERTY_SCRIPT_DESC = "desc";
	private static final String PROPERTY_MLI_DESC = "desc";
	private static final String PROPERTY_CODETYPE_DESC = "desc";
	private static final String PROPERTY_LABEL_DESC = "labelName";

	private Combobox entityCmb;
	private Textbox idTxt;
	private Textbox descTxt;
	private Combobox statusCmb;
	private Combobox actionCmb;
	private Datebox startDate;
	private Datebox endDate;
	private Listbox resultListbox;

	private GenericManagerImpl genericManager;

	public HistoryControl() {
		genericManager = ServiceLocator.getGenericManager();
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		session.setAttribute(Commons.SESSION_URL, Commons.MODULE_HISTORY);
	
		resultListbox.setItemRenderer(new GlobalHistoryListItemRenderer());
		setDates();
		searchHistoryList();
	}

	private void searchHistoryList() {
		int entityID = EntityType.getId(entityCmb.getValue());

		if (EntityType.ELEMENT.getId() == entityID) {
			setListboxModel(getElementHistoryList());

		} else if (EntityType.CRITERIA.getId() == entityID) {
			setListboxModel(getCriteriaHistoryList());

		} else if (EntityType.SCRIPT.getId() == entityID) {
			setListboxModel(getScriptHistoryList());

		} else if (EntityType.MLI.getId() == entityID) {
			setListboxModel(getMliHistoryList());

		} else if (EntityType.CODETYPE.getId() == entityID) {
			setListboxModel(getCodeTypeHistoryList());

		} else if (EntityType.LABEL.getId() == entityID) {
			setListboxModel(getLabelHistoryList());

		} else {
			List<Global> list = getElementHistoryList();
//			list.addAll(getCriteriaHistoryList());
			list.addAll(getScriptHistoryList());
			list.addAll(getMliHistoryList());
			list.addAll(getCodeTypeHistoryList());
			list.addAll(getLabelHistoryList());
			setListboxModel(list);
		}
	}

	private void setListboxModel(List<Global> list) {
		resultListbox.setModel(new ListModelList(list));
	}

	private List<Global> getElementHistoryList() {
		return getList(new HstElement(), PROPERTY_ELEMENT_ID,
				PROPERTY_ELEMENT_DESC);
	}

	private List<Global> getCriteriaHistoryList() {
		return getList(new HistCriteria(), PROPERTY_CRITERIA_ID,
				PROPERTY_CRITERIA_DESC);
	}

	private List<Global> getScriptHistoryList() {
		return getList(new HstScript(), PROPERTY_SCRIPT_ID,
				PROPERTY_SCRIPT_DESC);
	}

	private List<Global> getMliHistoryList() {
		return getList(new HstMli(), PROPERTY_MLI_ID, PROPERTY_MLI_DESC);
	}

	private List<Global> getCodeTypeHistoryList() {
		return getList(new HstCodeType(), PROPERTY_CODETYPE_ID,
				PROPERTY_CODETYPE_DESC);
	}

	private List<Global> getLabelHistoryList() {
		return getList(new HstLabel(), PROPERTY_LABEL_ID, PROPERTY_LABEL_DESC);
	}

	@SuppressWarnings("unchecked")
	private <G extends Global> List<Global> getList(G g, String propertyNameId,
			String propertyNameDesc) {

		if (StringUtils.isNotEmpty(idTxt.getValue())) {
			g.setId(idTxt.getValue());
		}

		if (StringUtils.isNotEmpty(descTxt.getValue())) {
			g.setDesc(descTxt.getValue());
		}

		if (StringUtils.isNotEmpty(statusCmb.getValue())) {
			g.setStatusID(StatusType.getId(statusCmb.getValue()));
		}

		if (StringUtils.isNotEmpty(actionCmb.getValue())) {
			g.setActionID(ActionType.getId(actionCmb.getValue()));
		}

		if (null == startDate.getValue()) {
			return (List<Global>) genericManager.searchGlobalList(g,
					propertyNameId, propertyNameDesc);

		} else {
			return (List<Global>) genericManager.searchGlobalList(g,
					propertyNameId, propertyNameDesc, startDate.getValue(),
					endDate.getValue());
		}
	}

	public void onClick$searchBtn() {
		searchHistoryList();
	}

	public void onClick$resetBtn() {
		entityCmb.setValue(Commons.EMPTY_STRING);
		idTxt.setValue(Commons.EMPTY_STRING);
		descTxt.setValue(Commons.EMPTY_STRING);
		setDates();
		statusCmb.setValue(Commons.EMPTY_STRING);
		actionCmb.setValue(Commons.EMPTY_STRING);
	}

	private void setDates() {
		Date now = DateUtil.setToMidnight(DateUtil.getCurrentDate());
		startDate.setValue(DateUtil.add(now, -1));
		endDate.setValue(now);
	}

	public void onClick$exportBtn() throws IOException {
		ExportUtil.exportToExcel("History", resultListbox);
	}
}