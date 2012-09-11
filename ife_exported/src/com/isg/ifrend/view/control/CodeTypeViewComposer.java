/**
 * 
 */
package com.isg.ifrend.view.control;

import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;

import com.isg.ifrend.model.bean.CodeType;
import com.isg.ifrend.model.bean.HstCodeType;
import com.isg.ifrend.model.bean.TmpCodeType;
import com.isg.ifrend.service.GlobalManager;
import com.isg.ifrend.service.ServiceLocator;
import com.isg.ifrend.utils.Commons;
import com.isg.ifrend.view.control.renderer.CodeItemRenderer;

public class CodeTypeViewComposer extends
		GlobalViewComposer<CodeType, TmpCodeType, HstCodeType> {

	private static final long serialVersionUID = -8331844213901569270L;

	private Label codeTypeDescLbl;
	private Listbox codeListbox;

	@Override
	protected GlobalManager<CodeType, TmpCodeType> getManager() {
		return ServiceLocator.getCodeTypeManager();
	}

	@Override
	protected TmpCodeType toTmp(CodeType m) {
		return new TmpCodeType(m);
	}

	@Override
	protected void setViewValue() {
		
		deleteTopBtn.setVisible(false);

		codeTypeDescLbl.setValue(tmpGlobal.getDesc());

		codeListbox.setModel(new ListModelList(tmpGlobal.getTmpCodeSet()));
		codeListbox.setItemRenderer(new CodeItemRenderer());
	}

	@Override
	protected String getViewUrl() {
		return Commons.URL_VIEW_CODETYPE;
	}

	@Override
	protected String getAddUrl() {
		return Commons.URL_ADD_CODETYPE;
	}

	@Override
	protected String getUpdateUrl() {
		return Commons.URL_UPDATE_CODETYPE;
	}

	@Override
	protected TmpCodeType toTmpFromHst(HstCodeType h) {
		return new TmpCodeType(h);
	}

	@Override
	protected Class<HstCodeType> getHstGlobalClass() {
		return HstCodeType.class;
	}
}