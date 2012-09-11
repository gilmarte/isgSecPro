/**
 * 
 */
package com.isg.ifrend.view.control;

import org.zkoss.zul.Label;

import com.isg.ifrend.model.bean.HstLabel;
import com.isg.ifrend.model.bean.TmpLabel;
import com.isg.ifrend.service.GlobalManager;
import com.isg.ifrend.service.LanguageCodeManager;
import com.isg.ifrend.service.ServiceLocator;
import com.isg.ifrend.utils.Commons;

public class LabelViewComposer extends
		GlobalViewComposer<com.isg.ifrend.model.bean.Label, TmpLabel, HstLabel> {

	private static final long serialVersionUID = 1060929474588753647L;

	private LanguageCodeManager languageCodeManager = ServiceLocator
			.getLanguageCodeManager();

	private Label labelNameLbl;
	private Label languageCodeLbl;
	private Label nativeStringLbl;

	@Override
	protected GlobalManager<com.isg.ifrend.model.bean.Label, TmpLabel> getManager() {
		return ServiceLocator.getLabelManager();
	}

	@Override
	protected TmpLabel toTmp(com.isg.ifrend.model.bean.Label m) {
		return new TmpLabel(m);
	}

	@Override
	protected void setViewValue() {
		labelNameLbl.setValue(tmpGlobal.getLabelName());

		languageCodeLbl.setValue(languageCodeManager.findById(
				tmpGlobal.getLabelLanguageCode()).getLanguageCodeForDisplay());

		nativeStringLbl.setValue(tmpGlobal.getLabelNativeString());
	}

	@Override
	protected String getViewUrl() {
		return Commons.URL_VIEW_LABEL;
	}

	@Override
	protected String getAddUrl() {
		return Commons.URL_ADD_LABEL;
	}

	@Override
	protected String getUpdateUrl() {
		return Commons.URL_UPDATE_LABEL;
	}

	@Override
	protected TmpLabel toTmpFromHst(HstLabel h) {
		return new TmpLabel(h);
	}

	@Override
	protected Class<HstLabel> getHstGlobalClass() {
		return HstLabel.class;
	}
}