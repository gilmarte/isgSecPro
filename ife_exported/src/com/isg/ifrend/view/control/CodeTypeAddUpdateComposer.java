/**
 * 
 */
package com.isg.ifrend.view.control;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

import com.isg.ifrend.model.bean.CodeType;
import com.isg.ifrend.model.bean.TmpCode;
import com.isg.ifrend.model.bean.TmpCodeType;
import com.isg.ifrend.service.GlobalManager;
import com.isg.ifrend.service.ServiceLocator;
import com.isg.ifrend.utils.Commons;
import com.isg.ifrend.utils.StringUtil;
import com.isg.ifrend.view.control.renderer.CodeItemRenderer;

public class CodeTypeAddUpdateComposer extends
		GlobalAddUpdateComposer<CodeType, TmpCodeType> {

	private static final long serialVersionUID = -6735839879295771910L;

	private Textbox codeTypeDescTxt;
	private Textbox codeValueTxt;
	private Textbox codeDescTxt;
	private Listbox codeListbox;

	// private Button removeBtn;

	private ArrayList<TmpCode> codeList;

	@Override
	protected GlobalManager<CodeType, TmpCodeType> getManager() {
		return ServiceLocator.getCodeTypeManager();
	}

	@Override
	protected TmpCodeType getNewTmpGlobalInstance() {
		return new TmpCodeType();
	}

	@Override
	protected TmpCodeType toTmp(CodeType m) {
		return new TmpCodeType(m);
	}

	@Override
	protected void setComponents() {
		// removeBtn.setDisabled(true);
		codeListbox.setItemRenderer(new CodeItemRenderer());
	}

	@Override
	protected void resetViewValue() {
		// removeBtn.setDisabled(true);
		codeTypeDescTxt.setValue(Commons.EMPTY_STRING);

		codeList = new ArrayList<TmpCode>();
		codeListbox.setModel(new ListModelList(codeList));

		codeValueTxt.setValue(Commons.EMPTY_STRING);
		codeDescTxt.setValue(Commons.EMPTY_STRING);
	}

	@Override
	protected void setViewValue() {
		codeList = new ArrayList<TmpCode>(tmpGlobal.getTmpCodeSet());

		// removeBtn.setDisabled(codeSet.isEmpty());

		codeTypeDescTxt.setValue(tmpGlobal.getDesc());

		codeListbox
				.setModel(new ListModelList(new ArrayList<TmpCode>(codeList)));

		codeTypeDescTxt.setDisabled(true);

		codeValueTxt.setValue(Commons.EMPTY_STRING);
		codeDescTxt.setValue(Commons.EMPTY_STRING);
	}

	@Override
	protected boolean validateInput() {
		boolean isValid = true;

		if (StringUtils.isEmpty(codeTypeDescTxt.getValue())) {
			messageLbl.setValue(getMsgPropVal(Commons.MSG_ERROR));
			codeTypeDescTxt.setErrorMessage(getMsgPropVal(Commons.BLANK_ENTER_CODE_DESC));
			isValid = false;

		} else if (codeList.isEmpty()) {
			messageLbl.setValue(getMsgPropVal(Commons.ZERO_CODE_LIST));
			isValid = false;
		}
		super.setMsgStyle();

		return isValid;
	}

	@Override
	protected void getViewValue() {
		tmpGlobal.setDesc(codeTypeDescTxt.getValue());

		// XXX Not a good solution, possible bug in reset button, but minor
		Set<TmpCode> tmpCodeSetToRemove = new HashSet<TmpCode>();
		Set<TmpCode> tmpCodeSetAlreadyAdded = new HashSet<TmpCode>();

		for (TmpCode tC : tmpGlobal.getTmpCodeSet()) {
			boolean found = false;
			in: for (TmpCode tmpCode : codeList) {
				if (tmpCode.getCodeValue().equals(tC.getCodeValue())) {
					tC.setCodeDesc(tmpCode.getCodeDesc());

					tmpCodeSetAlreadyAdded.add(tmpCode);
					found = true;
					break in;
				}
			}

			if (!found) {
				tmpCodeSetToRemove.add(tC);
			}
		}

		tmpGlobal.getTmpCodeSet().removeAll(tmpCodeSetToRemove);
		codeList.removeAll(tmpCodeSetAlreadyAdded);

		for (TmpCode tmpCode : codeList) {
			tmpGlobal.addToTmpCodeSet(tmpCode);
		}

		codeList.addAll(tmpCodeSetAlreadyAdded);
	}

	@Override
	protected String getViewUrl() {
		return Commons.URL_VIEW_CODETYPE;
	}

	public void onClick$addBtn() {
		String codeValue = codeValueTxt.getValue().trim();
		String codeDesc = codeDescTxt.getValue().trim();

		boolean isValid = true;

		if (StringUtils.isEmpty(codeValue)) {
			codeValueTxt
					.setErrorMessage(getMsgPropVal(Commons.BLANK_ENTER_CODE_VALUE));
			isValid = false;
		} else if (StringUtils.isEmpty(codeDesc)) {
			codeDescTxt
					.setErrorMessage(getMsgPropVal(Commons.BLANK_ENTER_CODE_DESC));
			isValid = false;
		} else {
			codeValue = StringUtil.lPad(codeValue, Commons.ID_NUM_LEN, Commons.STRING_ZERO);
			for (TmpCode code : codeList) {
				if (code.getCodeValue().equalsIgnoreCase(codeValue)) {
					codeValueTxt.setErrorMessage("Code Value already exists.");
					isValid = false;
					break;
				}
			}
		}

		if (isValid) {
			TmpCode code = new TmpCode();
			code.setCodeValue(codeValue);
			code.setCodeDesc(codeDesc);

			codeList.add(code);

			codeDescTxt.setValue(Commons.EMPTY_STRING);
			codeValueTxt.setValue(Commons.EMPTY_STRING);

			codeListbox.setModel(new ListModelList(codeList));

			// removeBtn.setDisabled(false);
		}
	}

	@SuppressWarnings("unchecked")
	public void onClick$removeBtn() {
		Set<Listitem> codeListitem = codeListbox.getSelectedItems();
		ListModel modelList = codeListbox.getModel();

		for (Listitem item : codeListitem) {
			codeList.remove((TmpCode) modelList.getElementAt(item.getIndex()));
		}

		codeListbox.setModel(new ListModelList(codeList));

		// removeBtn.setDisabled(codeSet.isEmpty());
	}
}