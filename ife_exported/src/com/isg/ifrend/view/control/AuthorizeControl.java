package com.isg.ifrend.view.control;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
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
import com.isg.ifrend.model.bean.EntityType;
import com.isg.ifrend.model.bean.Global;
import com.isg.ifrend.model.bean.StatusType;
import com.isg.ifrend.model.bean.TempCriteria;
import com.isg.ifrend.model.bean.TmpCodeType;
import com.isg.ifrend.model.bean.TmpElement;
import com.isg.ifrend.model.bean.TmpLabel;
import com.isg.ifrend.model.bean.TmpMli;
import com.isg.ifrend.model.bean.TmpScript;
import com.isg.ifrend.security.utils.SecurityUtils;
import com.isg.ifrend.service.SelectedManager;
import com.isg.ifrend.service.ServiceLocator;
import com.isg.ifrend.service.impl.GenericManagerImpl;
import com.isg.ifrend.utils.Commons;
import com.isg.ifrend.utils.DateUtil;
import com.isg.ifrend.utils.GlobalUtils;
import com.isg.ifrend.view.control.renderer.GlobalAuthorizeListItemRenderer;

public class AuthorizeControl extends GenericForwardComposer {

	private static final long serialVersionUID = 2473247001903215829L;

	private static final String PROPERTY_ELEMENT_ID = "tmp_elem_id";
	private static final String PROPERTY_CRITERIA_ID = "criteria_id";
	private static final String PROPERTY_SCRIPT_ID = "scriptID";
	private static final String PROPERTY_MLI_ID = "mliID";
	private static final String PROPERTY_CODETYPE_ID = "codeTypeID";
	private static final String PROPERTY_LABEL_ID = "labelID";

	private AnnotateDataBinder binder;

	private Button btn_approve;
	private Button btn_cancel;
	private Textbox tbx_search_id;
	private Combobox cbb_entity;
	private Combobox cbb_action;

	private Listbox lbx_result;

	private ListModelList entityList = new ListModelList(Arrays.asList(EntityType.values()));
	private ListModelList actionList =  new ListModelList(new ArrayList<ActionType>(Arrays.asList(ActionType.values())));

	private GenericManagerImpl genericManager = ServiceLocator.getGenericManager();
	private SelectedManager selectedManager = ServiceLocator.getSelectedManager();

	private GlobalUtils globalUtils = GlobalUtils.getGlobalUtilsInstance();
	private SecurityUtils securityUtils = SecurityUtils.getSecurityUtilsInstance();

	private int count_add_elem = 0;
	private int count_update_elem = 0;
	private int count_delete_elem = 0;

	private int count_add_criteria = 0;
	private int count_update_criteria = 0;
	private int count_delete_criteria = 0;

	private int count_add_mli = 0;
	private int count_update_mli = 0;
	private int count_delete_mli = 0;

	private int count_add_script = 0;
	private int count_update_script = 0;
	private int count_delete_script = 0;

	private int count_add_codetable = 0;
	private int count_update_codetable = 0;
	private int count_delete_codetable = 0;

	private int count_add_label = 0;
	private int count_update_label = 0;
	private int count_delete_label = 0;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		session.setAttribute(Commons.SESSION_URL, Commons.MODULE_AUTHORIZE);

		String url = globalUtils.getGlobalPropValue(Commons.URL_AUTHORIZE_GLOBAL);
		btn_approve.setVisible(securityUtils.hasCheckerRole(url));
		btn_cancel.setVisible(securityUtils.hasMakerRole(url));

		lbx_result.setItemRenderer(new GlobalAuthorizeListItemRenderer());
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
//		list.addAll(getCriteriaPendingList());
		list.addAll(getScriptPendingList());
		list.addAll(getMliPendingList());
		list.addAll(getCodeTypePendingList());
		list.addAll(getLabelPendingList());
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void populateListBox(List list) {
		List finalList = new ArrayList();
		if (list != null) {
			for (int index = 0; index < list.size(); index++) {
				if (list.get(index) instanceof List) {
					List listSpecific = (List) list.get(index);

					for (int index2 = 0; index2 < listSpecific.size(); index2++) {
						if (listSpecific.get(index2) instanceof TmpElement) {
							finalList
									.add((TmpElement) listSpecific.get(index2));
						} else if (listSpecific.get(index2) instanceof TempCriteria) {
							finalList.add((TempCriteria) listSpecific
									.get(index2));
						} else if (listSpecific.get(index2) instanceof TmpMli) {
							finalList.add((TmpMli) listSpecific.get(index2));
						} else if (listSpecific.get(index2) instanceof TmpScript) {
							finalList.add((TmpScript) listSpecific.get(index2));
						} else if (listSpecific.get(index2) instanceof TmpCodeType) {
							finalList.add((TmpCodeType) listSpecific
									.get(index2));
						} else if (listSpecific.get(index2) instanceof TmpLabel) {
							finalList.add((TmpLabel) listSpecific.get(index2));
						}
					}
				} else {
					finalList = new ArrayList();
					finalList = list;
				}

			}
		}
		lbx_result.setModel(new ListModelList(finalList));
	}

	private List<Global> getElementPendingList() {
		return getList(new TmpElement(), PROPERTY_ELEMENT_ID);
	}

	private List<Global> getCriteriaPendingList() {
		return getList(new TempCriteria(), PROPERTY_CRITERIA_ID);
	}

	private List<Global> getScriptPendingList() {
		return getList(new TmpScript(), PROPERTY_SCRIPT_ID);
	}

	private List<Global> getMliPendingList() {
		return getList(new TmpMli(), PROPERTY_MLI_ID);
	}

	private List<Global> getCodeTypePendingList() {
		return getList(new TmpCodeType(), PROPERTY_CODETYPE_ID);
	}

	private List<Global> getLabelPendingList() {
		return getList(new TmpLabel(), PROPERTY_LABEL_ID);
	}

	@SuppressWarnings("unchecked")
	private <G extends Global> List<Global> getList(G g, String propertyNameId) {
		if (StringUtils.isNotEmpty(tbx_search_id.getValue())) {
			g.setId(tbx_search_id.getValue());
		}

		if (StringUtils.isNotEmpty(cbb_action.getValue())) {
			g.setActionID(ActionType.getId(cbb_action.getValue()));
		}

		return (List<Global>) genericManager.searchGlobalList(g, propertyNameId);
	}

	public void onClick$btn_search() throws InterruptedException {
		showPendingList();
	}

	public void onClick$btn_reset() throws InterruptedException {
		tbx_search_id.setValue(Commons.EMPTY_STRING);
		cbb_entity.setValue(Commons.EMPTY_STRING);
		cbb_action.setValue(Commons.EMPTY_STRING);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private int getApproverMakerCount(){

		int invalidCount = 0;
		Set items = lbx_result.getSelectedItems();
		List itemList = new ArrayList(items);
		ListModel model = lbx_result.getModel();

		for (int index = 0; index < itemList.size(); index++) {
			Listitem selectedItem = (Listitem) itemList.get(index);
			if (model.getElementAt(selectedItem.getIndex()) instanceof TmpElement) {
				TmpElement tempElement = (TmpElement) model.getElementAt(selectedItem.getIndex());
				if (this.isApproverMaker(tempElement))
					invalidCount++;


			} else if (model.getElementAt(selectedItem.getIndex()) instanceof TempCriteria) {
				TempCriteria tempCriteria = (TempCriteria) model.getElementAt(selectedItem.getIndex());
				if (this.isApproverMaker(tempCriteria))
					invalidCount++;

			} else if (model.getElementAt(selectedItem.getIndex()) instanceof TmpMli) {
				TmpMli tmpMli = (TmpMli) model.getElementAt(selectedItem.getIndex());
				if (this.isApproverMaker(tmpMli))
					invalidCount++;

			} else if (model.getElementAt(selectedItem.getIndex()) instanceof TmpScript) {
				TmpScript tmpScript = (TmpScript) model.getElementAt(selectedItem.getIndex());
				if (this.isApproverMaker(tmpScript))
					invalidCount++;

			} else if (model.getElementAt(selectedItem.getIndex()) instanceof TmpCodeType) {
				TmpCodeType tmpCodeType = (TmpCodeType) model.getElementAt(selectedItem.getIndex());
				if (this.isApproverMaker(tmpCodeType))
					invalidCount++;

			} else if (model.getElementAt(selectedItem.getIndex()) instanceof TmpLabel) {
				TmpLabel tmpLabel = (TmpLabel) model.getElementAt(selectedItem.getIndex());
				if (this.isApproverMaker(tmpLabel))
					invalidCount++;
			}
		}
		return invalidCount;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean verifyApproverMaker(){

		Set items = lbx_result.getSelectedItems();
		List itemList = new ArrayList(items);
		ListModel model = lbx_result.getModel();
		boolean invalid = false;
		String message = null;

		if (lbx_result.getSelectedCount() > 1) {

			int count = this.getApproverMakerCount();
			if (count > 0){
				invalid = true;
				String msg = null;
				if (count == 1){
					msg = Commons.MESSAGE_WARN_INVALID_FOR_APPROVAL_MULTIPLE_1;
				} else {
					msg = Commons.MESSAGE_WARN_INVALID_FOR_APPROVAL_MULTIPLE;
				}

				message = MessageFormat.format(globalUtils.getMessagePropValue(msg)
						, count);
			}
		} else if (lbx_result.getSelectedCount() != 0) {
			Listitem selectedItem = (Listitem) itemList.get(0);
			String displayID = null;

			if (model.getElementAt(selectedItem.getIndex()) instanceof TmpElement) {
				TmpElement tempElements = (TmpElement) model.getElementAt(selectedItem.getIndex());
				if (this.isApproverMaker(tempElements)){
					displayID = EntityType.ELEMENT.getDesc() + Commons.SPACE + tempElements.getTmp_elem_id();
					invalid = true;
				}
			} else if (model.getElementAt(selectedItem.getIndex()) instanceof TempCriteria) {
				TempCriteria tempCriteria = (TempCriteria) model.getElementAt(selectedItem.getIndex());
				if (this.isApproverMaker(tempCriteria)){
					displayID = EntityType.CRITERIA.getDesc() + Commons.SPACE + String.valueOf(tempCriteria.getCriteria_id());
					invalid = true;
				}
			} else if (model.getElementAt(selectedItem.getIndex()) instanceof TmpMli) {
				TmpMli tmpMli = (TmpMli) model.getElementAt(selectedItem.getIndex());
				if (this.isApproverMaker(tmpMli)){
					displayID = EntityType.MLI.getDesc() + Commons.SPACE + String.valueOf(tmpMli.getMliID());
					invalid = true;
				}

			} else if (model.getElementAt(selectedItem.getIndex()) instanceof TmpScript) {
				TmpScript tmpScript = (TmpScript) model.getElementAt(selectedItem.getIndex());
				if (this.isApproverMaker(tmpScript)){
					displayID = EntityType.SCRIPT.getDesc() + Commons.SPACE + String.valueOf(tmpScript.getScriptID());
					invalid = true;
				}

			} else if (model.getElementAt(selectedItem.getIndex()) instanceof TmpCodeType) {
				TmpCodeType tmpCodeType = (TmpCodeType) model.getElementAt(selectedItem.getIndex());
				if (this.isApproverMaker(tmpCodeType)){
					displayID = EntityType.CODETYPE.getDesc() + Commons.SPACE + String.valueOf(tmpCodeType.getCodeTypeID());
					invalid = true;
				}

			} else if (model.getElementAt(selectedItem.getIndex()) instanceof TmpLabel) {
				TmpLabel tmpLabel = (TmpLabel) model.getElementAt(selectedItem.getIndex());
				if (this.isApproverMaker(tmpLabel)){
					displayID = EntityType.LABEL.getDesc() + Commons.SPACE + String.valueOf(tmpLabel.getLabelID());
					invalid = true;
				}
			} 
			message = MessageFormat.format(globalUtils.getMessagePropValue(Commons.MESSAGE_WARN_INVALID_FOR_APPROVAL_ONE), displayID);
		}

		if (invalid){
			this.showMessage(message);
			return invalid;
		}

		return invalid;
	}

	private void showMessage(String msg){
		try {
			Messagebox.show(msg, Commons.MSG_WARNING, Messagebox.OK, Messagebox.EXCLAMATION);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void onClick$btn_approve() throws InterruptedException {
		if (!this.verifyApproverMaker()) {
			this.processRequest(true);
		}
	}

	public void onClick$btn_cancel() throws InterruptedException {
		this.processRequest(false);
	}

	private boolean isApproverMaker(Global objBean) {
		return objBean.getLastModifierUserID().equals(securityUtils.getUserName());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void processRequest(boolean isAuthorise){

		Set items = lbx_result.getSelectedItems();
		List itemList = new ArrayList(items);
		ListModel model = lbx_result.getModel();
		String displayID = null;
		String action = null;

		if (itemList.size() > 0){

			if (lbx_result.getSelectedCount() > 1) {

				String msgKey = null;
				String headerKey = null;
				int act = 0;

				if (isAuthorise){
					msgKey = Commons.MESSAGE_AUTHORIZE_MULTIPLE;
					headerKey = Commons.MESSAGE_AUTHORIZE_CONFIRM;
					act = 1;
				} else {
					msgKey = Commons.MESSAGE_CANCEL_REQUEST_MULTIPLE;
					headerKey = Commons.MESSAGE_CANCEL_REQUEST_CONFIRM;
					act = 0;
				}

				this.showMessageAuthorize(MessageFormat.format(globalUtils.getMessagePropValue(msgKey), String
						.valueOf(itemList.size())), globalUtils.getMessagePropValue(headerKey),
						Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, act);

			} else {

				Listitem selectedItem = (Listitem) itemList.get(0);
				if (model.getElementAt(selectedItem.getIndex()) instanceof TmpElement) {
					TmpElement tempElements = (TmpElement) model
					.getElementAt(selectedItem.getIndex());
					displayID = EntityType.ELEMENT.getDesc() + Commons.SPACE + tempElements.getTmp_elem_id();
					action = StatusType.getDesc(tempElements.getStatusID());

				} else if (model.getElementAt(selectedItem.getIndex()) instanceof TempCriteria) {
					TempCriteria tempCriteria = (TempCriteria) model
					.getElementAt(selectedItem.getIndex());
					displayID = EntityType.CRITERIA.getDesc() + Commons.SPACE + String.valueOf(tempCriteria.getCriteria_id());
					action = StatusType.getDesc(tempCriteria.getStatusID());

				} else if (model.getElementAt(selectedItem.getIndex()) instanceof TmpMli) {
					TmpMli tmpMli = (TmpMli) model.getElementAt(selectedItem.getIndex());
					displayID = EntityType.MLI.getDesc() + Commons.SPACE + String.valueOf(tmpMli.getMliID());
					action = StatusType.getDesc(tmpMli.getStatusID());

				} else if (model.getElementAt(selectedItem.getIndex()) instanceof TmpScript) {
					TmpScript tmpScript = (TmpScript) model.getElementAt(selectedItem
							.getIndex());
					displayID = EntityType.SCRIPT.getDesc() + Commons.SPACE + String.valueOf(tmpScript.getScriptID());
					action = StatusType.getDesc(tmpScript.getStatusID());

				} else if (model.getElementAt(selectedItem.getIndex()) instanceof TmpCodeType) {
					TmpCodeType tmpCodeType = (TmpCodeType) model
					.getElementAt(selectedItem.getIndex());
					displayID = EntityType.CODETYPE.getDesc() + Commons.SPACE + String.valueOf(tmpCodeType.getCodeTypeID());
					action = StatusType.getDesc(tmpCodeType.getStatusID());

				} else if (model.getElementAt(selectedItem.getIndex()) instanceof TmpLabel) {
					TmpLabel tmpLabel = (TmpLabel) model.getElementAt(selectedItem
							.getIndex());
					displayID = EntityType.LABEL.getDesc() + Commons.SPACE + String.valueOf(tmpLabel.getLabelID());
					action = StatusType.getDesc(tmpLabel.getStatusID());
				}

				String msgKey = null;
				String headerKey = null;
				int act = 0;

				if (isAuthorise){
					msgKey = Commons.MESSAGE_AUTHORIZE_ONE;
					headerKey = Commons.MESSAGE_AUTHORIZE_CONFIRM;
					act = 1;
				} else {
					msgKey = Commons.MESSAGE_CANCEL_REQUEST_ONE;
					headerKey = Commons.MESSAGE_CANCEL_REQUEST_CONFIRM;
					act = 0;
				}

				this.showMessageAuthorize(MessageFormat.format(globalUtils.getMessagePropValue(msgKey), action, displayID),
						globalUtils.getMessagePropValue(headerKey), Messagebox.OK
						| Messagebox.CANCEL, Messagebox.QUESTION, act);
			}
		}
	}

	private String getMessageDisp(int status_id, String msg, String entity, String id, String action){

		if (status_id == StatusType.PENDING.getId()) {
			return MessageFormat.format(msg, entity + Commons.SPACE + id, action);
		}
		/*if (status_id == Commons.STATUS_PENDING_ADD){
			return MessageFormat.format(msg, entity + " " + id, action);
		} else if (status_id == Commons.STATUS_PENDING_UPDATE){
			return MessageFormat.format(msg, entity + " " + id, action);
		} else if (status_id == Commons.STATUS_PENDING_DELETE){
			return MessageFormat.format(msg, entity + " " + id, action);
		}*/

		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void processMarkedRecords(boolean isAuthorise){

		Set items = lbx_result.getSelectedItems();
		List itemList = new ArrayList(items);
		ListModel model = lbx_result.getModel();
		String stringDisp = null;
		count_add_elem = 0;
		count_update_elem = 0;
		count_delete_elem = 0;
		count_add_criteria = 0;
		count_update_criteria = 0;
		count_delete_criteria = 0;
		count_add_mli = 0;
		count_update_mli = 0;
		count_delete_mli = 0;
		count_add_script = 0;
		count_update_script = 0;
		count_delete_script = 0;
		count_add_codetable = 0;
		count_update_codetable = 0;
		count_delete_codetable = 0;
		count_add_label = 0;
		count_update_label = 0;
		count_delete_label = 0;

		for (int index = 0; index < itemList.size(); index++) {
			Listitem selectedItem = (Listitem) itemList.get(index);

			if (model.getElementAt(selectedItem.getIndex()) instanceof TmpElement) {
				TmpElement tempElement = (TmpElement) model.getElementAt(selectedItem.getIndex());
				tempElement.setApproverUserID(securityUtils.getUserName());
				tempElement.setDateApproved(DateUtil.getCurrentDate());
				tempElement.setLastModifierUserID(securityUtils.getUserName());
				tempElement.setDateLastModified(DateUtil.getCurrentDate());

				boolean success = false;
				if (isAuthorise){
					success = selectedManager.authorizeEntities(tempElement);
				} else {
					success = selectedManager.cancelRequest(tempElement);
				}

				if (success){
					String act = null;
					if (tempElement.getStatusID() == StatusType.PENDING.getId()) {
						if (tempElement.getActionID() == ActionType.ADD.getId()){
							count_add_elem++;
							act = Commons.REQUEST_ACTION_ADD;
						} else if (tempElement.getActionID() == ActionType.UPDATE.getId()){
							count_update_elem++;
							act = Commons.REQUEST_ACTION_UPDATE;
	
						} else if (tempElement.getActionID() == ActionType.DELETE.getId()){
							count_delete_elem++;
							act = Commons.REQUEST_ACTION_DELETE;
						}
					}	
					
					String msgKey = null;
					if (isAuthorise){
						msgKey = Commons.MESSAGE_AUTH_CONFIRM_ONE;
					} else {
						msgKey = Commons.MESSAGE_CANCEL_CONFIRM_ONE;
					}

					stringDisp = this.getMessageDisp(tempElement.getStatusID(), 
							globalUtils.getMessagePropValue(msgKey), 
							EntityType.ELEMENT.getDesc(), 
							tempElement.getTmp_elem_id(), 
							act);

				}
			} else if (model.getElementAt(selectedItem.getIndex()) instanceof TempCriteria) {
				TempCriteria tempCriteria = (TempCriteria) model.getElementAt(selectedItem.getIndex());
				/* Common - START */
				tempCriteria.setApproverUserID(securityUtils.getUserName());
				tempCriteria.setDateApproved(DateUtil.getCurrentDate());
				tempCriteria.setLastModifierUserID(securityUtils.getUserName());
				tempCriteria.setDateLastModified(DateUtil.getCurrentDate());
				/* Common - END */
				boolean success = false;
				if (isAuthorise){
					success = selectedManager.authorizeEntities(tempCriteria);
				} else {
					success = selectedManager.cancelRequest(tempCriteria);
				}

				if (success){
					String act = null;
					if (tempCriteria.getStatusID() == StatusType.PENDING.getId()) {
						// TODO
						if (tempCriteria.getActionID() == ActionType.ADD.getId()){
							count_add_criteria++;
							act = Commons.REQUEST_ACTION_ADD;
	
						} else if (tempCriteria.getActionID() == ActionType.UPDATE.getId()){
							count_update_criteria++;
							act = Commons.REQUEST_ACTION_UPDATE;
						} else if (tempCriteria.getActionID() == ActionType.DELETE.getId()){
							count_delete_criteria++;
							act = Commons.REQUEST_ACTION_DELETE;
	
						}
					}

					String msgKey = null;
					if (isAuthorise){
						msgKey = Commons.MESSAGE_AUTH_CONFIRM_ONE;
					} else {
						msgKey = Commons.MESSAGE_CANCEL_CONFIRM_ONE;
					}

					stringDisp = this.getMessageDisp(tempCriteria.getStatusID(), 
							globalUtils.getMessagePropValue(msgKey), 
							EntityType.CRITERIA.getDesc(), 
							String.valueOf(tempCriteria.getCriteria_id()), 
							act);
				}
			} else if (model.getElementAt(selectedItem.getIndex()) instanceof TmpMli) {
				TmpMli tmpMli = (TmpMli) model.getElementAt(selectedItem.getIndex());
				tmpMli.setApproverUserID(securityUtils.getUserName());
				tmpMli.setDateApproved(DateUtil.getCurrentDate());
				tmpMli.setLastModifierUserID(securityUtils.getUserName());
				tmpMli.setDateLastModified(DateUtil.getCurrentDate());

				boolean success = false;
				if (isAuthorise){
					success = selectedManager.authorizeEntities(tmpMli);
				} else {
					success = selectedManager.cancelRequest(tmpMli);
				}

				if (success){
					String act = null;
					if (tmpMli.getStatusID() == StatusType.PENDING.getId()) {
						if (tmpMli.getActionID() == ActionType.ADD.getId()){
							count_add_mli++;
							act = Commons.REQUEST_ACTION_ADD;
	
						} else if (tmpMli.getActionID() == ActionType.UPDATE.getId()){
							count_update_mli++;
							act = Commons.REQUEST_ACTION_UPDATE;
	
						} else if (tmpMli.getActionID() == ActionType.DELETE.getId()){
							count_delete_mli++;
							act = Commons.REQUEST_ACTION_DELETE;
	
						}
					}

					String msgKey = null;
					if (isAuthorise){
						msgKey = Commons.MESSAGE_AUTH_CONFIRM_ONE;
					} else {
						msgKey = Commons.MESSAGE_CANCEL_CONFIRM_ONE;
					}

					stringDisp = this.getMessageDisp(tmpMli.getStatusID(), 
							globalUtils.getMessagePropValue(msgKey), 
							EntityType.MLI.getDesc(), 
							String.valueOf(tmpMli.getMliID()), 
							act);
				}
			} else if (model.getElementAt(selectedItem.getIndex()) instanceof TmpScript) {
				TmpScript tmpScript = (TmpScript) model.getElementAt(selectedItem.getIndex());
				tmpScript.setApproverUserID(securityUtils.getUserName());
				tmpScript.setDateApproved(DateUtil.getCurrentDate());
				tmpScript.setLastModifierUserID(securityUtils.getUserName());
				tmpScript.setDateLastModified(DateUtil.getCurrentDate());

				boolean success = false;
				if (isAuthorise){
					success = selectedManager.authorizeEntities(tmpScript);
				} else {
					success = selectedManager.cancelRequest(tmpScript);
				}

				if (success){
					String act = null;
					if (tmpScript.getStatusID() == StatusType.PENDING.getId()) {
						if (tmpScript.getActionID() ==  ActionType.ADD.getId()){
							count_add_script++;
							act = Commons.REQUEST_ACTION_ADD;
	
						} else if (tmpScript.getActionID() == ActionType.UPDATE.getId()){
							count_update_script++;
							act = Commons.REQUEST_ACTION_UPDATE;
	
						} else if (tmpScript.getActionID() == ActionType.DELETE.getId()){
							count_delete_script++;
							act = Commons.REQUEST_ACTION_DELETE;
	
						}
					}
					String msgKey = null;
					if (isAuthorise){
						msgKey = Commons.MESSAGE_AUTH_CONFIRM_ONE;
					} else {
						msgKey = Commons.MESSAGE_CANCEL_CONFIRM_ONE;
					}

					stringDisp = this.getMessageDisp(tmpScript.getStatusID(), 
							globalUtils.getMessagePropValue(msgKey), 
							EntityType.SCRIPT.getDesc(), 
							String.valueOf(tmpScript.getScriptID()), 
							act);
				}
			} else if (model.getElementAt(selectedItem.getIndex()) instanceof TmpCodeType) {
				TmpCodeType tmpCodeType = (TmpCodeType) model.getElementAt(selectedItem.getIndex());
				tmpCodeType.setApproverUserID(securityUtils.getUserName());
				tmpCodeType.setDateApproved(DateUtil.getCurrentDate());
				tmpCodeType.setLastModifierUserID(securityUtils.getUserName());
				tmpCodeType.setDateLastModified(DateUtil.getCurrentDate());

				boolean success = false;
				if (isAuthorise){
					success = selectedManager.authorizeEntities(tmpCodeType);
				} else {
					success = selectedManager.cancelRequest(tmpCodeType);
				}

				if (success){
					String act = null;
					if (tmpCodeType.getStatusID() == StatusType.PENDING.getId()) {
						if (tmpCodeType.getActionID() ==  ActionType.ADD.getId()){
							count_add_codetable++;
							act = Commons.REQUEST_ACTION_ADD;
	
						} else if (tmpCodeType.getActionID() == ActionType.UPDATE.getId()){
							count_update_codetable++;
							act = Commons.REQUEST_ACTION_UPDATE;
	
						} else if (tmpCodeType.getActionID() == ActionType.DELETE.getId()){
							count_delete_codetable++;
							act = Commons.REQUEST_ACTION_DELETE;
						}
					}

					String msgKey = null;
					if (isAuthorise){
						msgKey = Commons.MESSAGE_AUTH_CONFIRM_ONE;
					} else {
						msgKey = Commons.MESSAGE_CANCEL_CONFIRM_ONE;
					}

					stringDisp = this.getMessageDisp(tmpCodeType.getStatusID(), 
							globalUtils.getMessagePropValue(msgKey), 
							EntityType.CODETYPE.getDesc(), 
							String.valueOf(tmpCodeType.getCodeTypeID()), 
							act);
				}
			} else if (model.getElementAt(selectedItem.getIndex()) instanceof TmpLabel) {
				TmpLabel tmpLabel = (TmpLabel) model.getElementAt(selectedItem.getIndex());
				tmpLabel.setApproverUserID(securityUtils.getUserName());
				tmpLabel.setDateApproved(DateUtil.getCurrentDate());
				tmpLabel.setLastModifierUserID(securityUtils.getUserName());
				tmpLabel.setDateLastModified(DateUtil.getCurrentDate());

				boolean success = false;
				if (isAuthorise){
					success = selectedManager.authorizeEntities(tmpLabel);
				} else {
					success = selectedManager.cancelRequest(tmpLabel);
				}

				if (success){
					String act = null;
					if (tmpLabel.getStatusID() == StatusType.PENDING.getId()) {
						if (tmpLabel.getActionID() ==  ActionType.ADD.getId()){
							count_add_label++;
							act = Commons.REQUEST_ACTION_ADD;
	
						} else if (tmpLabel.getActionID() == ActionType.UPDATE.getId()){
							count_update_label++;
							act = Commons.REQUEST_ACTION_UPDATE;
	
						} else if (tmpLabel.getActionID() == ActionType.DELETE.getId()){
							count_delete_label++;
							act = Commons.REQUEST_ACTION_DELETE;
						}
					}

					String msgKey = null;
					if (isAuthorise){
						msgKey = Commons.MESSAGE_AUTH_CONFIRM_ONE;
					} else {
						msgKey = Commons.MESSAGE_CANCEL_CONFIRM_ONE;
					}

					stringDisp = this.getMessageDisp(tmpLabel.getStatusID(), 
							globalUtils.getMessagePropValue(msgKey), 
							EntityType.LABEL.getDesc(), 
							String.valueOf(tmpLabel.getLabelID()), 
							act);
				}
			}
		}

		if (lbx_result.getSelectedCount() > 1) {
			StringBuilder details = new StringBuilder();
			details.append(Commons.NEW_LINE);

			String msgKey = null;
			if (isAuthorise){
				msgKey = Commons.MESSAGE_AUTH_CONFIRM_MULTIPLE;
			} else {
				msgKey = Commons.MESSAGE_CANCEL_CONFIRM_MULTIPLE;
			}

			details.append(MessageFormat.format(globalUtils.getMessagePropValue(msgKey), 
					String.valueOf(itemList.size())) + 
					Commons.NEW_LINE + 
					Commons.NEW_LINE);

			stringDisp = details.toString() + this.constructMessage();
		}

		try {
			Messagebox.show(stringDisp, Commons.MSG_INFORMATION, Messagebox.OK, Messagebox.INFORMATION);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		this.showPendingList();
	}

	private void showMessageAuthorize(String message, String header,
			int messageButtonCode, String messageType, final int action) {

		try {
			Messagebox.show(message, header, messageButtonCode, messageType,
					new org.zkoss.zk.ui.event.EventListener() {
				public void onEvent(Event evt)
				throws InterruptedException {
					if (evt.getName().equals(Messagebox.ON_OK)) {
						if (action == 1){
							processMarkedRecords(true);
						} else if (action == 0){
							processMarkedRecords(false);
						}
					}
				}
			});
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private String constructMessage(){

		StringBuilder msgBuilder = new StringBuilder();

		int total_count_add = count_add_elem + 
		count_add_criteria + 
		count_add_mli + 
		count_add_script + 
		count_add_codetable + 
		count_add_label;

		int total_count_update = count_update_elem + 
		count_update_criteria + 
		count_update_mli + 
		count_update_script + 
		count_update_codetable + 
		count_update_label;

		int total_count_delete = count_delete_elem + 
		count_delete_criteria + 
		count_delete_mli + 
		count_delete_script + 
		count_delete_codetable + 
		count_delete_label; 

		if (total_count_add > 0){
			msgBuilder.append(ActionType.ADD.getDesc() + Commons.COLON);
			msgBuilder.append(Commons.NEW_LINE);

			if (count_add_elem > 0)
				msgBuilder.append(this.buildMessage(EntityType.ELEMENT.getDesc(), count_add_elem));

			if (count_add_criteria > 0)
				msgBuilder.append(this.buildMessage(EntityType.CRITERIA.getDesc(), count_add_criteria));

			if (count_add_mli > 0)
				msgBuilder.append(this.buildMessage(EntityType.MLI.getDesc(), count_add_mli));

			if (count_add_script > 0)
				msgBuilder.append(this.buildMessage(EntityType.SCRIPT.getDesc(), count_add_script));

			if (count_add_codetable > 0)
				msgBuilder.append(this.buildMessage(EntityType.CODETYPE.getDesc(), count_add_codetable));

			if (count_add_label > 0)
				msgBuilder.append(this.buildMessage(EntityType.LABEL.getDesc(), count_add_label));
		}

		if (total_count_update > 0){
			msgBuilder.append(ActionType.UPDATE.getDesc() + Commons.COLON);
			msgBuilder.append(Commons.NEW_LINE);

			if (count_update_elem > 0)
				msgBuilder.append(this.buildMessage(EntityType.ELEMENT.getDesc(), count_update_elem));

			if (count_update_criteria > 0)
				msgBuilder.append(this.buildMessage(EntityType.CRITERIA.getDesc(), count_update_criteria));

			if (count_update_mli > 0)
				msgBuilder.append(this.buildMessage(EntityType.MLI.getDesc(), count_update_mli));

			if (count_update_script > 0)
				msgBuilder.append(this.buildMessage(EntityType.SCRIPT.getDesc(), count_update_script));

			if (count_update_codetable > 0)
				msgBuilder.append(this.buildMessage(EntityType.CODETYPE.getDesc(), count_update_codetable));

			if (count_update_label > 0)
				msgBuilder.append(this.buildMessage(EntityType.LABEL.getDesc(), count_update_label));
		}

		if (total_count_delete > 0){
			msgBuilder.append(ActionType.DELETE.getDesc() + Commons.COLON);
			msgBuilder.append(Commons.NEW_LINE);

			if (count_delete_elem > 0)
				msgBuilder.append(this.buildMessage(EntityType.ELEMENT.getDesc(), count_delete_elem));

			if (count_delete_criteria > 0)
				msgBuilder.append(this.buildMessage(EntityType.CRITERIA.getDesc(), count_delete_criteria));

			if (count_delete_mli > 0)
				msgBuilder.append(this.buildMessage(EntityType.MLI.getDesc(), count_delete_mli));

			if (count_delete_script > 0)
				msgBuilder.append(this.buildMessage(EntityType.SCRIPT.getDesc(), count_delete_script));

			if (count_delete_codetable > 0)
				msgBuilder.append(this.buildMessage(EntityType.CODETYPE.getDesc(), count_delete_codetable));

			if (count_delete_label > 0)
				msgBuilder.append(this.buildMessage(EntityType.LABEL.getDesc(), count_delete_label));
		}
		return msgBuilder.toString();
	}

	private String buildMessage(String entity, int count){
		return "- " + entity + Commons.COLON + count + Commons.NEW_LINE;
	}

	public ListModelList getEntityList() {
		return entityList;
	}

	public void setEntityList(ListModelList entityList) {
		this.entityList = entityList;
	}

	public ListModelList getActionList() {
		return actionList;
	}

	public void setActionList(ListModelList actionList) {
		this.actionList = actionList;
	}
}