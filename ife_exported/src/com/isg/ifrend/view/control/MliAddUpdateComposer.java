/**
 * 
 */
package com.isg.ifrend.view.control;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.isg.ifrend.model.bean.Code;
import com.isg.ifrend.model.bean.CodeType;
import com.isg.ifrend.model.bean.CommentType;
import com.isg.ifrend.model.bean.FunctionType;
import com.isg.ifrend.model.bean.MessageType;
import com.isg.ifrend.model.bean.Mli;
import com.isg.ifrend.model.bean.TmpCodeType;
import com.isg.ifrend.model.bean.TmpMli;
import com.isg.ifrend.model.bean.UserField;
import com.isg.ifrend.service.GlobalManager;
import com.isg.ifrend.service.ServiceLocator;
import com.isg.ifrend.service.UserFieldManager;
import com.isg.ifrend.service.impl.GenericManagerImpl;
import com.isg.ifrend.utils.Commons;

/**
 * @author kristine.furto
 * 
 */
public class MliAddUpdateComposer extends GlobalAddUpdateComposer<Mli, TmpMli> {

	private static final long serialVersionUID = 5103253456793246154L;

	private AnnotateDataBinder binder;

	// ZUL Components
	private Label functionTypeLbl;
	private Label functionIdLbl;
	private Textbox descTxt;
	private Combobox userFieldCmb;
	private Combobox responseCodeCmb;
	private Textbox commentTxt;
	private Combobox commentTypeCmb;
	private Textbox messageTxt;
	private Combobox messageTypeCmb;

	// Service
	private UserFieldManager userFieldManager = ServiceLocator
			.getUserFieldManager();
	private GlobalManager<CodeType, TmpCodeType> codeTypeManager = ServiceLocator
			.getCodeTypeManager();
	
	// LOV
	private List<CommentType> commentTypeList;
	private List<MessageType> messageTypeList;
	private List<UserField> userFieldList = new ArrayList<UserField>();
	private List<Code> responseCodeList = new ArrayList<Code>();

	@Override
	protected GlobalManager<Mli, TmpMli> getManager() {
		return ServiceLocator.getMliManager();
	}

	@Override
	protected TmpMli getNewTmpGlobalInstance() {
		return new TmpMli();
	}

	@Override
	protected TmpMli toTmp(Mli m) {
		return new TmpMli(m);
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		// for binding purposes
		comp.setAttribute(comp.getId() + "Ctrl", this, true);
		binder = new AnnotateDataBinder(comp);
		binder.loadComponent(comp);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void setComponents() {
		functionIdLbl.setValue(Commons.FUNCTION_MLI);
		functionTypeLbl.setValue(FunctionType.MLI.getDesc());

		messageTypeList = new ArrayList(Arrays.asList(MessageType.values()));
		commentTypeList = new ArrayList(Arrays.asList(CommentType.values()));

		CodeType codeType = new CodeType();
		codeType.setDesc(Commons.CODETYPE_MLI_RESPONSE_CODES);
		List<CodeType> codeTypeList = codeTypeManager.searchActiveList(codeType);
		if (codeTypeList != null && !codeTypeList.isEmpty()) {
			responseCodeList = new ArrayList<Code>(codeTypeList.get(0).getCodeSet());
		}
		
		if (responseCodeList == null || 
				(responseCodeList  != null && responseCodeList.isEmpty()) ) {
			messageLbl.setValue(getMsgPropVal(Commons.MSG_CONFIG_CT_RESPONSECODE));
			
			try {
				Messagebox.show(getMsgPropVal(Commons.MSG_CONFIG_CT_RESPONSECODE),
						getMsgPropVal(Commons.MSG_TITLE_SETUP_CT), Messagebox.OK, Messagebox.EXCLAMATION,
				new EventListener() {
					public void onEvent(Event evt) throws ParseException,
							InterruptedException {
						if (Messagebox.ON_OK.equals(evt.getName())) {
							Executions.sendRedirect(getGlobalPropVal(Commons.URL_ADD_CODETYPE));
						}
					}
				});
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
		userFieldList = userFieldManager.getUserFieldList();
	}

	@Override
	protected void setViewValue() {
		userFieldCmb.setValue(userFieldManager.findById(
				this.tmpGlobal.getUserFieldID()).getUserFieldDesc());
		GenericManagerImpl genericManager = ServiceLocator.getGenericManager();
		responseCodeCmb.setValue(genericManager.viewDetails(Code.class,
				String.valueOf(this.tmpGlobal.getResponseCodeID())).getCodeForDisplay());
		descTxt.setValue(this.tmpGlobal.getDesc());
		messageTypeCmb.setValue(MessageType.getDesc(this.tmpGlobal
				.getMessageTypeID()));
		messageTxt.setValue(this.tmpGlobal.getMessage());
		commentTypeCmb.setValue(CommentType.getDesc(this.tmpGlobal
				.getCommentTypeID()));
		commentTxt.setValue(this.tmpGlobal.getComment());
		
		// set disabled
		if (StringUtils.isNotEmpty(tmpGlobal.getId())) {
			userFieldCmb.setDisabled(true);
			responseCodeCmb.setDisabled(true);
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
		if (responseCodeCmb.getSelectedItem() == null) {
			responseCodeCmb
					.setErrorMessage(getMsgPropVal(Commons.BLANK_SELECT_RESPONSE_CODE));
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
		// TODO Country Code = global settings
		tmpGlobal.setCountryCode("PH");
		tmpGlobal.setFunctionCode(Commons.FUNCTION_MLI);
		tmpGlobal.setUserFieldID(Integer.valueOf(
				userFieldCmb.getSelectedItem().getValue().toString())
				.intValue());
		tmpGlobal.setResponseCodeID(Integer.valueOf(
				responseCodeCmb.getSelectedItem().getValue().toString())
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
		responseCodeCmb.setValue(Commons.EMPTY_STRING);
		commentTxt.setValue(Commons.EMPTY_STRING);
		commentTypeCmb.setValue(Commons.EMPTY_STRING);
		messageTxt.setValue(Commons.EMPTY_STRING);
		messageTypeCmb.setValue(Commons.EMPTY_STRING);
	}

	@Override
	protected String getViewUrl() {
		return Commons.URL_VIEW_MLI;
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

	public List<Code> getResponseCodeList() {
		return responseCodeList;
	}

	public void setResponseCodeList(List<Code> responseCodeList) {
		this.responseCodeList = responseCodeList;
	}

}
