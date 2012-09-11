package com.isg.ifrend.view.control;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.isg.ifrend.model.bean.ActionType;
import com.isg.ifrend.model.bean.CodeType;
import com.isg.ifrend.model.bean.Criteria;
import com.isg.ifrend.model.bean.Element;
import com.isg.ifrend.model.bean.EntityType;
import com.isg.ifrend.model.bean.Global;
import com.isg.ifrend.model.bean.Label;
import com.isg.ifrend.model.bean.Mli;
import com.isg.ifrend.model.bean.Script;
import com.isg.ifrend.model.bean.StatusActionType;
import com.isg.ifrend.model.bean.StatusType;
import com.isg.ifrend.security.utils.SecurityUtils;
import com.isg.ifrend.service.SelectedManager;
import com.isg.ifrend.service.ServiceLocator;
import com.isg.ifrend.service.impl.GenericManagerImpl;
import com.isg.ifrend.utils.Commons;
import com.isg.ifrend.utils.DateUtil;
import com.isg.ifrend.utils.ExportUtil;
import com.isg.ifrend.utils.GlobalUtils;
import com.isg.ifrend.view.control.renderer.GlobalSearchListItemRenderer;

public class SearchControl extends GenericForwardComposer {

	private static final long serialVersionUID = -6074610918543046805L;

	private static final String PROPERTY_ELEMENT_ID = "element_id";
	private static final String PROPERTY_CRITERIA_ID = "criteria_id";
	private static final String PROPERTY_SCRIPT_ID = "scriptID";
	private static final String PROPERTY_MLI_ID = "mliID";
	private static final String PROPERTY_CODETYPE_ID = "codeTypeID";
	private static final String PROPERTY_LABEL_ID = "labelID";

	private static final String PROPERTY_ELEMENT_DESC = "element_desc";
	private static final String PROPERTY_CRITERIA_DESC = "description";
	private static final String PROPERTY_SCRIPT_DESC = "desc";
	private static final String PROPERTY_MLI_DESC = "desc";
	private static final String PROPERTY_CODETYPE_DESC = "desc";
	private static final String PROPERTY_LABEL_DESC = "labelName";

	private AnnotateDataBinder binder;

	private Button btn_delete;
	private Textbox tbx_search_id;
	private Textbox tbx_search_desc;

	private Combobox cbb_entity;
	private Combobox cbb_status;

	private Listbox lbx_result;

	private ListModelList entityList = new ListModelList(Arrays.asList(EntityType.values()));
	private ListModelList statusList = new ListModelList(Arrays.asList(StatusActionType.values()));

	private GenericManagerImpl genericManager = ServiceLocator.getGenericManager();
	private SelectedManager selectedManager = ServiceLocator.getSelectedManager();

	private GlobalUtils globalUtils = GlobalUtils.getGlobalUtilsInstance();
	private SecurityUtils securityUtils = SecurityUtils.getSecurityUtilsInstance();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		session.setAttribute(Commons.SESSION_URL, Commons.MODULE_SEARCH);

		btn_delete.setVisible(securityUtils.hasCheckerRole(globalUtils.getGlobalPropValue(Commons.URL_AUTHORIZE_GLOBAL)));

		lbx_result.setItemRenderer(new GlobalSearchListItemRenderer());
		this.showPendingList();

		comp.setAttribute(comp.getId() + "Control", this, true);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
	}

	private void showPendingList() {
		int entityID = EntityType.getId(cbb_entity.getValue());

		if (EntityType.ELEMENT.getId() == entityID) {
			this.populateListBox(getElementPendingList());

		} else if (EntityType.CRITERIA.getId() == entityID) {
			this.populateListBox(getCriteriaPendingList());

		} else if (EntityType.SCRIPT.getId() == entityID) {
			this.populateListBox(getScriptPendingList());

		} else if (EntityType.MLI.getId() == entityID) {
			this.populateListBox(getMliPendingList());

		} else if (EntityType.CODETYPE.getId() == entityID) {
			this.populateListBox(getCodeTypePendingList());

		} else if (EntityType.LABEL.getId() == entityID) {
			this.populateListBox(getLabelPendingList());
		} else {
			this.populateListBox(getAllPendingList());
		}
	}

	private List<Global> getAllPendingList() {
		List<Global> list = getElementPendingList();
		list.addAll(getCriteriaPendingList());
		list.addAll(getScriptPendingList());
		list.addAll(getMliPendingList());
		list.addAll(getCodeTypePendingList());
		list.addAll(getLabelPendingList());
		return list;
	}

	@SuppressWarnings("rawtypes")
	private void populateListBox(List list) {
		List finalList = new ArrayList();
		if (list != null) {
			for (int index = 0; index < list.size(); index++) {
				if (list.get(index) instanceof List) {
					finalList = new ArrayList();
					finalList = (List) list.get(index);
				} else {
					finalList = new ArrayList();
					finalList = list;
				}

			}
		}
		lbx_result.setModel(new ListModelList(finalList));
		lbx_result.setItemRenderer(new GlobalSearchListItemRenderer());
	}

	private List<Global> getElementPendingList() {
		return getList(new Element(), PROPERTY_ELEMENT_ID,
				PROPERTY_ELEMENT_DESC);
	}

	private List<Global> getCriteriaPendingList() {
		return getList(new Criteria(), PROPERTY_CRITERIA_ID,
				PROPERTY_CRITERIA_DESC);
	}

	private List<Global> getScriptPendingList() {
		return getList(new Script(), PROPERTY_SCRIPT_ID, PROPERTY_SCRIPT_DESC);
	}

	private List<Global> getMliPendingList() {
		return getList(new Mli(), PROPERTY_MLI_ID, PROPERTY_MLI_DESC);
	}

	private List<Global> getCodeTypePendingList() {
		return getList(new CodeType(), PROPERTY_CODETYPE_ID,
				PROPERTY_CODETYPE_DESC);
	}

	private List<Global> getLabelPendingList() {
		return getList(new Label(), PROPERTY_LABEL_ID, PROPERTY_LABEL_DESC);
	}

	@SuppressWarnings("unchecked")
	private <G extends Global> List<Global> getList(G g, String propertyNameId, String propertyNameDesc) {

		if (StringUtils.isNotEmpty(tbx_search_id.getValue())) {
			g.setId(tbx_search_id.getValue());
		}

		if (StringUtils.isNotEmpty(tbx_search_desc.getValue())) {
			g.setDesc(tbx_search_desc.getValue());
		}

		String statusAndAction = cbb_status.getValue();

		if (StatusActionType.ACTIVE.getDesc().equals(statusAndAction)) {
			g.setStatusID(StatusType.ACTIVE.getId());

		} else if (StatusActionType.PENDING_UPDATE.getDesc().equals(statusAndAction)) {
			g.setStatusID(StatusType.PENDING.getId());
			g.setActionID(ActionType.UPDATE.getId());

		} else if (StatusActionType.PENDING_DELETE.getDesc().equals(statusAndAction)) {
			g.setStatusID(StatusType.PENDING.getId());
			g.setActionID(ActionType.DELETE.getId());
		}

		return (List<Global>) genericManager.searchGlobalList(g, propertyNameId, propertyNameDesc);
	}

	public void onClick$btn_search() throws InterruptedException {
		showPendingList();
	}

	public void onClick$btn_reset() throws InterruptedException {
		tbx_search_id.setValue(Commons.EMPTY_STRING);
		tbx_search_desc.setValue(Commons.EMPTY_STRING);
		cbb_entity.setValue(Commons.EMPTY_STRING);
		cbb_status.setValue(Commons.EMPTY_STRING);
	}

	public void onClick$btn_export() throws IOException {
		ExportUtil.exportToExcel(Commons.MODULE_SEARCH, lbx_result);
	}

	public void onClick$btn_delete() throws InterruptedException {
		if (!this.isPendingRecord()) {
			this.determineRecordsForDeletionMessage();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private int getPendingStatus(Listbox objListBox) {
		int pendingCount = 0;
		Set items = objListBox.getSelectedItems();
		List itemList = new ArrayList(items);
		ListModel model = objListBox.getModel();

		for (int index = 0; index < itemList.size(); index++) {
			Listitem selectedItem = (Listitem) itemList.get(index);
			if (model.getElementAt(selectedItem.getIndex()) instanceof Element) {
				Element elementBean = (Element) model
				.getElementAt(selectedItem.getIndex());

				// Check if Status: Pending Authorisation, Action (UPDATE, DELETE)
				if ((elementBean.getStatusID() == StatusType.PENDING.getId())
						&& ( (elementBean.getActionID() == ActionType.UPDATE.getId())
						|| (elementBean.getActionID() == ActionType.DELETE.getId()) )) {
					pendingCount++;
				}
			} else if (model.getElementAt(selectedItem.getIndex()) instanceof Criteria) {
				Criteria criteriaBean = (Criteria) model
						.getElementAt(selectedItem.getIndex());

				if ((criteriaBean.getStatusID() == StatusType.PENDING.getId())
						&& ( (criteriaBean.getActionID() == ActionType.UPDATE.getId())
								|| (criteriaBean.getActionID() == ActionType.DELETE.getId()) )) {
					pendingCount++;
				}
			} else if (model.getElementAt(selectedItem.getIndex()) instanceof Mli) {
				Mli mliBean = (Mli) model.getElementAt(selectedItem.getIndex());
				if ((mliBean.getStatusID() == StatusType.PENDING.getId())
						&& ( (mliBean.getActionID() == ActionType.UPDATE.getId())
								|| (mliBean.getActionID() == ActionType.DELETE.getId()) )) {
					pendingCount++;
				}

			} else if (model.getElementAt(selectedItem.getIndex()) instanceof Script) {
				Script scriptBean = (Script) model.getElementAt(selectedItem
						.getIndex());
				if ((scriptBean.getStatusID() == StatusType.PENDING.getId())
						&& ( (scriptBean.getActionID() == ActionType.UPDATE.getId())
								|| (scriptBean.getActionID() == ActionType.DELETE.getId()) )) {
					pendingCount++;
				}

			} else if (model.getElementAt(selectedItem.getIndex()) instanceof CodeType) {
				CodeType codetypeBean = (CodeType) model
						.getElementAt(selectedItem.getIndex());
				if ((codetypeBean.getStatusID() == StatusType.PENDING.getId())
						&& ( (codetypeBean.getActionID() == ActionType.UPDATE.getId())
								|| (codetypeBean.getActionID() == ActionType.DELETE.getId()) )) {
					pendingCount++;
				}

			} else if (model.getElementAt(selectedItem.getIndex()) instanceof Label) {
				Label labelBean = (Label) model.getElementAt(selectedItem
						.getIndex());
				if ((labelBean.getStatusID() == StatusType.PENDING.getId())
						&& ( (labelBean.getActionID() == ActionType.UPDATE.getId())
								|| (labelBean.getActionID() == ActionType.DELETE.getId()) )) {
					pendingCount++;
				}
			}
		}
		return pendingCount;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean isPendingStatus() {
		Set items = lbx_result.getSelectedItems();
		List itemList = new ArrayList(items);
		ListModel model = lbx_result.getModel();
		boolean pending = false;

		for (int index = 0; index < itemList.size(); index++) {
			Listitem selectedItem = (Listitem) itemList.get(index);
			if (model.getElementAt(selectedItem.getIndex()) instanceof Element) {
				Element elementBean = (Element) model
				.getElementAt(selectedItem.getIndex());

				// Check if Status: Pending Authorisation, Action (UPDATE, DELETE)
				if ((elementBean.getStatusID() == StatusType.PENDING.getId())
						&& ( (elementBean.getActionID() == ActionType.UPDATE.getId())
								|| (elementBean.getActionID() == ActionType.DELETE.getId()) )) {
					pending = true;
				}
			} else if (model.getElementAt(selectedItem.getIndex()) instanceof Criteria) {
				Criteria criteriaBean = (Criteria) model
						.getElementAt(selectedItem.getIndex());

				if ((criteriaBean.getStatusID() == StatusType.PENDING.getId())
						&& ( (criteriaBean.getActionID() == ActionType.UPDATE.getId())
								|| (criteriaBean.getActionID() == ActionType.DELETE.getId()) )) {
					pending = true;
				}
			} else if (model.getElementAt(selectedItem.getIndex()) instanceof Mli) {
				Mli mliBean = (Mli) model.getElementAt(selectedItem.getIndex());
				if ((mliBean.getStatusID() == StatusType.PENDING.getId())
						&& ( (mliBean.getActionID() == ActionType.UPDATE.getId())
								|| (mliBean.getActionID() == ActionType.DELETE.getId()) )) {
					pending = true;
				}

			} else if (model.getElementAt(selectedItem.getIndex()) instanceof Script) {
				Script scriptBean = (Script) model.getElementAt(selectedItem
						.getIndex());
				if ((scriptBean.getStatusID() == StatusType.PENDING.getId())
						&& ( (scriptBean.getActionID() == ActionType.UPDATE.getId())
								|| (scriptBean.getActionID() == ActionType.DELETE.getId()) )) {
					pending = true;
				}

			} else if (model.getElementAt(selectedItem.getIndex()) instanceof CodeType) {
				CodeType codetypeBean = (CodeType) model
						.getElementAt(selectedItem.getIndex());

				if ((codetypeBean.getStatusID() == StatusType.PENDING.getId())
						&& ( (codetypeBean.getActionID() == ActionType.UPDATE.getId())
								|| (codetypeBean.getActionID() == ActionType.DELETE.getId()) )) {
					pending = true;
				}

			} else if (model.getElementAt(selectedItem.getIndex()) instanceof Label) {
				Label labelBean = (Label) model.getElementAt(selectedItem
						.getIndex());

				if ((labelBean.getStatusID() == StatusType.PENDING.getId())
						&& ( (labelBean.getActionID() == ActionType.UPDATE.getId())
								|| (labelBean.getActionID() == ActionType.DELETE.getId()) )) {
					pending = true;
				}
			}
		}
		return pending;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean isPendingRecord() throws InterruptedException {
		Set items = lbx_result.getSelectedItems();
		List itemList = new ArrayList(items);
		ListModel model = lbx_result.getModel();
		boolean isPending = false;
		String message = null;

		if (lbx_result.getSelectedCount() > 1) {

			int pendingCount = this.getPendingStatus(lbx_result);

			if (pendingCount > 0) {
				isPending = true;
				String msg = null;
				if (pendingCount == 1) {
					msg = Commons.MESSAGE_WARN_PENDING_MULTIPLE_1;
				} else {
					msg = Commons.MESSAGE_WARN_PENDING_MULTIPLE;
				}
				message = MessageFormat.format(globalUtils.getMessagePropValue(msg)
						, pendingCount);
			}
		} else if (lbx_result.getSelectedCount() == 1) {
			Listitem selectedItem = (Listitem) itemList.get(0);
			String displayID = null;

			if (model.getElementAt(selectedItem.getIndex()) instanceof Element) {
				Element elementBean = (Element) model
				.getElementAt(selectedItem.getIndex());

				if (this.isPendingStatus()) {
					displayID = EntityType.ELEMENT.getDesc() + Commons.SPACE + elementBean.getElement_id();
					isPending = true;
				}
			} else if (model.getElementAt(selectedItem.getIndex()) instanceof Criteria) {
				Criteria criteriaBean = (Criteria) model
						.getElementAt(selectedItem.getIndex());

				if (this.isPendingStatus()) {
					displayID = EntityType.CRITERIA.getDesc() + Commons.SPACE + criteriaBean.getId();
					isPending = true;
				}
			} else if (model.getElementAt(selectedItem.getIndex()) instanceof Mli) {
				Mli mliBean = (Mli) model.getElementAt(selectedItem.getIndex());
				if (this.isPendingStatus()) {
					displayID = mliBean.getId();
					isPending = true;
				}

			} else if (model.getElementAt(selectedItem.getIndex()) instanceof Script) {
				Script scriptBean = (Script) model.getElementAt(selectedItem
						.getIndex());
				if (this.isPendingStatus()) {
					displayID = EntityType.SCRIPT.getDesc() + Commons.SPACE + scriptBean.getId();
					isPending = true;
				}

			} else if (model.getElementAt(selectedItem.getIndex()) instanceof CodeType) {
				CodeType codetypeBean = (CodeType) model
						.getElementAt(selectedItem.getIndex());
				if (this.isPendingStatus()) {
					displayID = EntityType.CODETYPE.getDesc() + Commons.SPACE + codetypeBean.getId();
					isPending = true;
				}

			} else if (model.getElementAt(selectedItem.getIndex()) instanceof Label) {
				Label labelBean = (Label) model.getElementAt(selectedItem
						.getIndex());
				if (this.isPendingStatus()) {
					displayID = EntityType.LABEL.getDesc() + Commons.SPACE + labelBean.getId();
					isPending = true;
				}
			}

			message = MessageFormat.format(globalUtils.getMessagePropValue(Commons.MESSAGE_WARN_PENDING_ONE), displayID);
		}

		if (isPending) {
			this.showMessagePending(message);
			return true;
		}

		return false;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void determineRecordsForDeletionMessage() {

		Set items = lbx_result.getSelectedItems();
		List itemList = new ArrayList(items);
		ListModel model = lbx_result.getModel();
		String displayID = null;

		if (lbx_result.getSelectedCount() > 1) {
			this.showMessageDelete(MessageFormat.format(globalUtils.getMessagePropValue(Commons.MESSAGE_DELETE_MULTIPLE), String
					.valueOf(itemList.size())), globalUtils.getMessagePropValue( Commons.MESSAGE_DELETE_CONFIRM),
					Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION);

		} else if (lbx_result.getSelectedCount() == 1) {

			Listitem selectedItem = (Listitem) itemList.get(0);
			if (model.getElementAt(selectedItem.getIndex()) instanceof Element) {
				Element elementBean = (Element) model.getElementAt(selectedItem
						.getIndex());
				displayID = EntityType.ELEMENT.getDesc() + Commons.SPACE
						+ elementBean.getElement_id();

			} else if (model.getElementAt(selectedItem.getIndex()) instanceof Criteria) {
				Criteria criteriaBean = (Criteria) model
						.getElementAt(selectedItem.getIndex());
				displayID = EntityType.CRITERIA.getDesc() + Commons.SPACE + criteriaBean.getId();

			} else if (model.getElementAt(selectedItem.getIndex()) instanceof Mli) {
				Mli mliBean = (Mli) model.getElementAt(selectedItem.getIndex());
				displayID = EntityType.MLI.getDesc() + Commons.SPACE + mliBean.getId();

			} else if (model.getElementAt(selectedItem.getIndex()) instanceof Script) {
				Script scriptBean = (Script) model.getElementAt(selectedItem
						.getIndex());
				displayID = EntityType.SCRIPT.getDesc() + Commons.SPACE + scriptBean.getId();

			} else if (model.getElementAt(selectedItem.getIndex()) instanceof CodeType) {
				CodeType codetypeBean = (CodeType) model
						.getElementAt(selectedItem.getIndex());
				displayID = EntityType.CODETYPE.getDesc() + Commons.SPACE + codetypeBean.getId();

			} else if (model.getElementAt(selectedItem.getIndex()) instanceof Label) {
				Label labelBean = (Label) model.getElementAt(selectedItem
						.getIndex());
				displayID = EntityType.LABEL.getDesc() + Commons.SPACE + labelBean.getId();
			}

			this.showMessageDelete(MessageFormat.format(globalUtils.getMessagePropValue(Commons.MESSAGE_DELETE_ONE), displayID),
					globalUtils.getMessagePropValue(Commons.MESSAGE_DELETE_CONFIRM), Messagebox.OK
					| Messagebox.CANCEL, Messagebox.QUESTION);
		}
	}

	private void showMessagePending(String errMsg) throws InterruptedException {
		Messagebox.show(errMsg, Commons.MSG_WARNING, Messagebox.OK, Messagebox.EXCLAMATION);
	}

	private void showMessageDelete(String message, String header,
			int messageButtonCode, String messageType) {

		try {
			Messagebox.show(message, header, messageButtonCode, messageType,
					new org.zkoss.zk.ui.event.EventListener() {
						public void onEvent(Event evt)
								throws InterruptedException {
							if (evt.getName().equals(Messagebox.ON_OK)) {
								deleteMarkedRecords();
								showPendingList();
							}
						}
					});
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void deleteMarkedRecords() {
		Set items = lbx_result.getSelectedItems();
		List itemList = new ArrayList(items);
		ListModel model = lbx_result.getModel();
		boolean isDeleted = false;

		String stringDisp = null;

		if (lbx_result.getSelectedCount() > 1) {
			stringDisp = MessageFormat.format(globalUtils.getMessagePropValue(Commons.MESSAGE_DELETE_OK_MULTIPLE), String.valueOf(itemList.size()));
		}

		for (int index = 0; index < itemList.size(); index++) {
			Listitem selectedItem = (Listitem) itemList.get(index);
			if (model.getElementAt(selectedItem.getIndex()) instanceof Element) {
				Element elementBean = (Element) model.getElementAt(selectedItem
						.getIndex());
				elementBean.setApproverUserID(securityUtils.getUserName());
				elementBean.setDateApproved(DateUtil.getCurrentDate());
				elementBean.setLastModifierUserID(securityUtils.getUserName());
				elementBean.setDateLastModified(DateUtil.getCurrentDate());
				isDeleted = selectedManager.requestDeletion(elementBean);
				if (lbx_result.getSelectedCount() == 1) {
					if (isDeleted)
						stringDisp = MessageFormat.format(globalUtils.getMessagePropValue(Commons.MESSAGE_DELETE_OK_ONE), EntityType.ELEMENT.getDesc() + Commons.SPACE + elementBean.getElement_id());
				}

			} else if (model.getElementAt(selectedItem.getIndex()) instanceof Criteria) {
				Criteria criteriaBean = (Criteria) model
						.getElementAt(selectedItem.getIndex());
				/* Common - START */
				criteriaBean.setApproverUserID(securityUtils.getUserName());
				criteriaBean.setDateApproved(DateUtil.getCurrentDate());
				criteriaBean.setLastModifierUserID(securityUtils.getUserName());
				criteriaBean.setDateLastModified(DateUtil.getCurrentDate());
				/* Common - END */
				isDeleted = selectedManager.requestDeletion(criteriaBean);
				if (lbx_result.getSelectedCount() == 1) {
					if (isDeleted)
						stringDisp = MessageFormat.format(globalUtils.getMessagePropValue(Commons.MESSAGE_DELETE_OK_ONE), EntityType.CRITERIA.getDesc() + Commons.SPACE + criteriaBean.getCriteria_id());
				}

			} else if (model.getElementAt(selectedItem.getIndex()) instanceof Mli) {
				Mli mliBean = (Mli) model.getElementAt(selectedItem.getIndex());
				mliBean.setApproverUserID(securityUtils.getUserName());
				mliBean.setDateApproved(DateUtil.getCurrentDate());
				mliBean.setLastModifierUserID(securityUtils.getUserName());
				mliBean.setDateLastModified(DateUtil.getCurrentDate());
				isDeleted = selectedManager.requestDeletion(mliBean);
				if (lbx_result.getSelectedCount() == 1) {
					if (isDeleted)
						stringDisp = MessageFormat.format(globalUtils.getMessagePropValue(Commons.MESSAGE_DELETE_OK_ONE),EntityType.MLI.getDesc() + Commons.SPACE + mliBean.getMliID());
				}

			} else if (model.getElementAt(selectedItem.getIndex()) instanceof Script) {
				Script scriptBean = (Script) model.getElementAt(selectedItem
						.getIndex());
				scriptBean.setApproverUserID(securityUtils.getUserName());
				scriptBean.setDateApproved(DateUtil.getCurrentDate());
				scriptBean.setLastModifierUserID(securityUtils.getUserName());
				scriptBean.setDateLastModified(DateUtil.getCurrentDate());
				isDeleted = selectedManager.requestDeletion(scriptBean);
				if (lbx_result.getSelectedCount() == 1) {
					if (isDeleted)
						stringDisp = MessageFormat.format(globalUtils.getMessagePropValue(Commons.MESSAGE_DELETE_OK_ONE),EntityType.SCRIPT.getDesc() + Commons.SPACE + scriptBean.getScriptID());
				}

			} else if (model.getElementAt(selectedItem.getIndex()) instanceof CodeType) {
				CodeType codeTypeBean = (CodeType) model
						.getElementAt(selectedItem.getIndex());
				codeTypeBean.setApproverUserID(securityUtils.getUserName());
				codeTypeBean.setDateApproved(DateUtil.getCurrentDate());
				codeTypeBean.setLastModifierUserID(securityUtils.getUserName());
				codeTypeBean.setDateLastModified(DateUtil.getCurrentDate());
				isDeleted = selectedManager.requestDeletion(codeTypeBean);
				if (lbx_result.getSelectedCount() == 1) {
					if (isDeleted)
						stringDisp = MessageFormat.format(globalUtils.getMessagePropValue(Commons.MESSAGE_DELETE_OK_ONE),EntityType.CODETYPE.getDesc() + Commons.SPACE + codeTypeBean.getCodeTypeID());
				}

			} else if (model.getElementAt(selectedItem.getIndex()) instanceof Label) {
				Label labelBean = (Label) model.getElementAt(selectedItem
						.getIndex());
				labelBean.setApproverUserID(securityUtils.getUserName());
				labelBean.setDateApproved(DateUtil.getCurrentDate());
				labelBean.setLastModifierUserID(securityUtils.getUserName());
				labelBean.setDateLastModified(DateUtil.getCurrentDate());
				isDeleted = selectedManager.requestDeletion(labelBean);
				if (lbx_result.getSelectedCount() == 1) {
					if (isDeleted)
						stringDisp = MessageFormat.format(globalUtils.getMessagePropValue(Commons.MESSAGE_DELETE_OK_ONE),EntityType.LABEL.getDesc() + Commons.SPACE + labelBean.getLabelID());
				}
			}
		}
		try {
			Messagebox.show(stringDisp, Commons.MSG_INFORMATION, Messagebox.OK, Messagebox.INFORMATION);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public ListModelList getEntityList() {
		return entityList;
	}

	public void setEntityList(ListModelList entityList) {
		this.entityList = entityList;
	}

	public ListModelList getStatusList() {
		return statusList;
	}

	public void setStatusList(ListModelList statusList) {
		this.statusList = statusList;
	}
}