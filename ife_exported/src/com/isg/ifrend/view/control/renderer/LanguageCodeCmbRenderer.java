package com.isg.ifrend.view.control.renderer;

import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

import com.isg.ifrend.model.bean.LanguageCode;

public class LanguageCodeCmbRenderer implements ComboitemRenderer {

	@Override
	public void render(Comboitem item, Object data) throws Exception {
		final LanguageCode languageCode = (LanguageCode) data;
		item.setValue(languageCode.getLcLanguageCodeID());
		item.setLabel(languageCode.getLanguageCodeForDisplay());
	}
}