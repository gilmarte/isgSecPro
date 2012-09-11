/**
 * 
 */
package com.isg.ifrend.view.control;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

import com.isg.ifrend.model.bean.CommentType;
import com.isg.ifrend.model.bean.Function;
import com.isg.ifrend.model.bean.FunctionType;
import com.isg.ifrend.model.bean.MessageType;
import com.isg.ifrend.model.bean.Priority;
import com.isg.ifrend.model.bean.Script;
import com.isg.ifrend.model.bean.TmpScript;
import com.isg.ifrend.model.bean.UserField;
import com.isg.ifrend.service.FunctionManager;
import com.isg.ifrend.service.GlobalManager;
import com.isg.ifrend.service.PriorityManager;
import com.isg.ifrend.service.ServiceLocator;
import com.isg.ifrend.service.UserFieldManager;
import com.isg.ifrend.utils.Commons;

/**
 * @author kristine.furto
 * 
 */
public class ScriptAddUpdateComposer extends
		GlobalAddUpdateComposer<Script, TmpScript> {

	private static final long serialVersionUID = 5103253456793246154L;
	private AnnotateDataBinder binder;
	// ZUL Components
	private Label functionTypeLbl;
	private Combobox functionCmb;
	private Textbox descTxt;
	private Combobox userFieldCmb;
	private Combobox priorityCmb;
	private Textbox commentTxt;
	private Combobox commentTypeCmb;
	private Textbox messageTxt;
	private Combobox messageTypeCmb;

	// Service
	private FunctionManager functionManager = ServiceLocator
			.getFunctionManager();
	private UserFieldManager userFieldManager = ServiceLocator
			.getUserFieldManager();
	private PriorityManager priorityManager = ServiceLocator
			.getPriorityManager();

	@Override
	protected GlobalManager<Script, TmpScript> getManager() {
		return ServiceLocator.getScriptManager();
	}

	@Override
	protected TmpScript getNewTmpGlobalInstance() {
		return new TmpScript();
	}

	@Override
	protected TmpScript toTmp(Script m) {
		return new TmpScript(m);
	}

	// LOV
	private List<Function> functionList = new ArrayList<Function>();
	private List<CommentType> commentTypeList = new ArrayList<CommentType>();
	private List<MessageType> messageTypeList = new ArrayList<MessageType>();
	private List<UserField> userFieldList = new ArrayList<UserField>();
	private List<Priority> priorityList = new ArrayList<Priority>();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void setComponents() {
		functionTypeLbl.setValue(FunctionType.SCRIPT.getDesc());

		functionList = functionManager.getFunctionList();

		messageTypeList = new ArrayList(Arrays.asList(MessageType.values()));
		setCommentTypeList(new ArrayList(Arrays.asList(CommentType.values())));

		priorityList = priorityManager.getPriorityList();
		userFieldList = userFieldManager.getUserFieldList();
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		// for binding purposes
		comp.setAttribute(comp.getId() + "Ctrl", this, true);
		binder = new AnnotateDataBinder(comp);
		binder.loadComponent(comp);
	}

	@Override
	protected void setViewValue() {
		functionCmb.setValue(functionManager.findById(
				this.tmpGlobal.getFunctionCode()).getFunction_desc());
		userFieldCmb.setValue(userFieldManager.findById(
				this.tmpGlobal.getUserFieldID()).getUserFieldDesc());
		priorityCmb.setValue(priorityManager.findById(
				this.tmpGlobal.getPriorityID()).getPriorityDesc());
		descTxt.setValue(tmpGlobal.getDesc());
		messageTypeCmb.setValue(MessageType.getDesc(this.tmpGlobal
				.getMessageTypeID()));
		messageTxt.setValue(tmpGlobal.getMessage());
		commentTypeCmb.setValue(CommentType.getDesc(this.tmpGlobal
				.getCommentTypeID()));
		commentTxt.setValue(tmpGlobal.getComment());

		// set disabled
		if (StringUtils.isNotEmpty(tmpGlobal.getId())) {
			userFieldCmb.setDisabled(true);
			// priorityCmb.setDisabled(true);
		}

	}

	@Override
	protected boolean validateInput() {
		boolean isValid = true;
		if (userFieldCmb.getSelectedItem() == null) {
			userFieldCmb
					.setErrorMessage(getMsgPropVal(Commons.BLANK_SELECT_USER_FIELD));
			isValid = false;
		}
		if (functionCmb.getSelectedItem() == null) {
			functionCmb
					.setErrorMessage(getMsgPropVal(Commons.BLANK_SELECT_FUNCTION_CODE));
			isValid = false;
		}

		if (priorityCmb.getSelectedItem() == null) {
			priorityCmb
					.setErrorMessage(getMsgPropVal(Commons.BLANK_SELECT_PRIORITY));
			isValid = false;
		}
		if (StringUtils.isEmpty(descTxt.getValue())) {
			descTxt.setErrorMessage(getMsgPropVal(Commons.BLANK_ENTER_DESC));
			isValid = false;
		}

		messageTxt.clearErrorMessage();
		if (messageTypeCmb.getSelectedItem() == null) {
			messageTypeCmb
					.setErrorMessage(getMsgPropVal(Commons.BLANK_SELECT_MESSAGE_TYPE));
			isValid = false;
		} else {
			// TODO Refactor
			int messageTypeId = ((Integer)
					messageTypeCmb.getSelectedItem().getValue()).intValue();
			if ( messageTypeId !=  MessageType.NONE.getId()
					&& messageTypeId != MessageType.OPTIONAL.getId()) {
				if (StringUtils.isEmpty(messageTxt.getValue())) {
					messageTxt
							.setErrorMessage(getMsgPropVal(Commons.BLANK_ENTER_MESSAGE));
					isValid = false;
				}
			} 
		}
		
		commentTxt.clearErrorMessage();
		if (commentTypeCmb.getSelectedItem() == null) {
			commentTypeCmb
					.setErrorMessage(getMsgPropVal(Commons.BLANK_SELECT_COMMENT_TYPE));
			isValid = false;
		} else {
			// TODO Refactor
			int commentTypeId = ((Integer)
					commentTypeCmb.getSelectedItem().getValue()).intValue();
			if (commentTypeId != CommentType.NONE.getId()) {
				if (StringUtils.isEmpty(commentTxt.getValue())) {
					commentTxt
							.setErrorMessage(getMsgPropVal(Commons.BLANK_ENTER_COMMENT));
					isValid = false;
				}
			}
		}
		
		if(!isValid) {
			messageLbl.setValue(getMsgPropVal(Commons.MSG_ERROR));
		}
		
		super.setMsgStyle();
		
		return isValid;

	}

	@Override
	protected void getViewValue() {
		// TODO get user - country settings
		tmpGlobal.setCountryCode("PH");

		tmpGlobal.setFunctionCode(functionCmb.getSelectedItem().getValue()
				.toString());
		tmpGlobal.setUserFieldID(Integer.valueOf(
				userFieldCmb.getSelectedItem().getValue().toString())
				.intValue());
		tmpGlobal
				.setPriorityID(Integer.valueOf(
						priorityCmb.getSelectedItem().getValue().toString())
						.intValue());
		tmpGlobal.setDesc(descTxt.getValue());

		tmpGlobal.setMessageTypeID(Integer.valueOf(
				messageTypeCmb.getSelectedItem().getValue().toString())
				.intValue());
		tmpGlobal.setMessage(messageTxt.getValue());
		tmpGlobal.setCommentTypeID(Integer.valueOf(
				commentTypeCmb.getSelectedItem().getValue().toString())
				.intValue());
		tmpGlobal.setComment(commentTxt.getValue());

	}

	@Override
	protected void resetViewValue() {
		descTxt.setValue(Commons.EMPTY_STRING);
		userFieldCmb.setValue(Commons.EMPTY_STRING);
		priorityCmb.setValue(Commons.EMPTY_STRING);
		commentTxt.setValue(Commons.EMPTY_STRING);
		commentTypeCmb.setValue(Commons.EMPTY_STRING);
		messageTxt.setValue(Commons.EMPTY_STRING);
		messageTypeCmb.setValue(Commons.EMPTY_STRING);

	}

	@Override
	protected String getViewUrl() {
		return Commons.URL_VIEW_SCRIPT;
	}

	/*******************************************************************
	 * EVENT HANDLERS
	 ******************************************************************/
	public void onChange$commentTypeCmb() {
		commentTxt.clearErrorMessage();
		int commentTypeId = ((Integer) commentTypeCmb.getSelectedItem().getValue()).intValue();
		if ( commentTypeId
				==  CommentType.NONE.getId()) {
			commentTxt.setValue(Commons.EMPTY_STRING);
			commentTxt.setDisabled(true);
		} else {
			commentTxt.setDisabled(false);
		}
	}

	public void onChange$messageTypeCmb() {
		messageTxt.clearErrorMessage();
		int messageTypeId = ((Integer) messageTypeCmb.getSelectedItem().getValue()).intValue();
		if ( messageTypeId ==  MessageType.NONE.getId()) {
			messageTxt.setValue(Commons.EMPTY_STRING);
			messageTxt.setDisabled(true);
		} else {
			messageTxt.setDisabled(false);
		}
	}

	/*******************************************************************
	 * GETTERS / SETTERS
	 ******************************************************************/
	public List<CommentType> getCommentTypeList() {
		return commentTypeList;
	}

	public void setCommentTypeList(List<CommentType> commentTypeList) {
		this.commentTypeList = commentTypeList;
	}

	public List<MessageType> getMessageTypeList() {
		return messageTypeList;
	}

	public void setMessageTypeList(List<MessageType> messageTypeList) {
		this.messageTypeList = messageTypeList;
	}

	public List<UserField> getUserFieldList() {
		return userFieldList;
	}

	public void setUserFieldList(List<UserField> userFieldList) {
		this.userFieldList = userFieldList;
	}

	public void setFunctionList(List<Function> functionList) {
		this.functionList = functionList;
	}

	public List<Function> getFunctionList() {
		return functionList;
	}

	public void setPriorityList(List<Priority> priorityList) {
		this.priorityList = priorityList;
	}

	public List<Priority> getPriorityList() {
		return priorityList;
	}

}
