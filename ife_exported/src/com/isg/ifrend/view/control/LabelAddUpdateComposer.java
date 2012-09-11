/**
 * 
 */
package com.isg.ifrend.view.control;

import org.apache.commons.lang3.StringUtils;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;

import com.isg.ifrend.model.bean.Label;
import com.isg.ifrend.model.bean.TmpLabel;
import com.isg.ifrend.service.GlobalManager;
import com.isg.ifrend.service.LanguageCodeManager;
import com.isg.ifrend.service.ServiceLocator;
import com.isg.ifrend.utils.Commons;
import com.isg.ifrend.view.control.renderer.LanguageCodeCmbRenderer;

public class LabelAddUpdateComposer extends
		GlobalAddUpdateComposer<Label, TmpLabel> {

	private static final long serialVersionUID = 4876425830219367547L;

	private LanguageCodeManager languageCodeManager = ServiceLocator
			.getLanguageCodeManager();

	private Textbox labelIdTxt;
	private Combobox languageCodeCmb;
	private Textbox nativeStringTxt;

	@Override
	protected GlobalManager<Label, TmpLabel> getManager() {
		return ServiceLocator.getLabelManager();
	}

	@Override
	protected TmpLabel getNewTmpGlobalInstance() {
		return new TmpLabel();
	}

	@Override
	protected TmpLabel toTmp(Label m) {
		return new TmpLabel(m);
	}

	@Override
	protected void setComponents() {
		languageCodeCmb.setModel(new ListModelList(languageCodeManager
				.getLanguageCodeList()));
		languageCodeCmb.setItemRenderer(new LanguageCodeCmbRenderer());
	}

	@Override
	protected void resetViewValue() {
		labelIdTxt.setValue(Commons.EMPTY_STRING);
		languageCodeCmb.setValue(Commons.EMPTY_STRING);
		nativeStringTxt.setValue(Commons.EMPTY_STRING);
	}

	@Override
	protected void setViewValue() {
		labelIdTxt.setValue(tmpGlobal.getLabelName());

		// TODO please refactor, set selected item by id.
		languageCodeCmb.setValue(languageCodeManager.findById(
				tmpGlobal.getLabelLanguageCode()).getLanguageCodeForDisplay());

		nativeStringTxt.setValue(tmpGlobal.getLabelNativeString());

		labelIdTxt.setDisabled(true);
		languageCodeCmb.setDisabled(true);
	}

	@Override
	protected boolean validateInput() {
		boolean isValid = true;

		if (StringUtils.isEmpty(labelIdTxt.getValue())) {
			labelIdTxt
					.setErrorMessage(getMsgPropVal(Commons.BLANK_ENTER_LABEL_ID));
			isValid = false;
		}

		if (null == languageCodeCmb.getSelectedItem()) {
			languageCodeCmb
					.setErrorMessage(getMsgPropVal(Commons.BLANK_SELECT_LANGUAGE_CODE));
			isValid = false;
		}

		if (StringUtils.isEmpty(nativeStringTxt.getValue())) {
			nativeStringTxt
					.setErrorMessage(getMsgPropVal(Commons.BLANK_ENTER_NATIVE_STRING));
			isValid = false;
		}

		if(!isValid) {
			messageLbl.setValue(getMsgPropVal(Commons.MSG_ERROR));
		}

		super.setMsgStyle();
		
		return isValid;
	}

	@Override
	protected void getViewValue() {
		tmpGlobal.setLabelName(labelIdTxt.getValue().trim());

		tmpGlobal.setLabelLanguageCode(languageCodeCmb.getSelectedItem()
				.getValue().toString());

		tmpGlobal.setLabelNativeString(nativeStringTxt.getValue().trim());
	}

	@Override
	protected String getViewUrl() {
		return Commons.URL_VIEW_LABEL;
	}
}