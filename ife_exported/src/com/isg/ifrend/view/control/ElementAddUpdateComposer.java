package com.isg.ifrend.view.control;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

import com.isg.ifrend.model.bean.DateFormats;
import com.isg.ifrend.model.bean.Element;
import com.isg.ifrend.model.bean.ElementFormats;
import com.isg.ifrend.model.bean.ElementTypes;
import com.isg.ifrend.model.bean.TmpElement;
import com.isg.ifrend.service.DateFormatsManager;
import com.isg.ifrend.service.ElementFormatsManager;
import com.isg.ifrend.service.ElementTypesManager;
import com.isg.ifrend.service.GlobalManager;
import com.isg.ifrend.service.ServiceLocator;
import com.isg.ifrend.utils.Commons;

public class ElementAddUpdateComposer extends
		GlobalAddUpdateComposer<Element, TmpElement> {

	private static final long serialVersionUID = 5202846609238215304L;

	private AnnotateDataBinder binder;

	// ZUL Components
	private Combobox cbb_elementtype;
	private Combobox cbb_elementformat;
	private Combobox cbb_element_dateformat;
	private Checkbox chb_element_operator_eq;
	private Checkbox chb_element_operator_ne;
	private Checkbox chb_element_operator_gt;
	private Checkbox chb_element_operator_ge;
	private Checkbox chb_element_operator_lt;
	private Checkbox chb_element_operator_le;
	private Textbox tbx_element_desc;
	private Intbox ibx_element_length;
	private Intbox ibx_element_decimal;

	private Row rowLength;
	private Row rowDecimal;
	private Row rowDateFormat;

	private ElementTypesManager elementTypesManager = ServiceLocator
			.getElementTypesManager();
	private ElementFormatsManager elementFormatsManager = ServiceLocator
			.getElementFormatsManager();
	private DateFormatsManager dateFormatsManager = ServiceLocator
			.getDateFormatsManager();

	List<ElementTypes> elemTypeList;
	List<ElementFormats> elemFormatList;
	List<DateFormats> dateFormatList;

	@Override
	protected GlobalManager<Element, TmpElement> getManager() {
		return ServiceLocator.getElementManager();
	}

	@Override
	protected TmpElement getNewTmpGlobalInstance() {
		return new TmpElement();
	}

	@Override
	protected TmpElement toTmp(Element m) {
		return new TmpElement(m);
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		comp.setAttribute(comp.getId() + "Ctrl", this, true);
		binder = new AnnotateDataBinder(comp);
		binder.loadComponent(comp);
	}

	@Override
	protected void setComponents() {
		elemTypeList = elementTypesManager.getElementTypesList();
		elemFormatList = elementFormatsManager.getElementFormatsList();
		dateFormatList = dateFormatsManager.getDateFormatsList();

	}

	@Override
	protected void setViewValue() {
		cbb_elementtype.setValue(elementTypesManager.findById(
				tmpGlobal.getTmp_elemtype_id()).getElementtype_desc());
		
		tbx_element_desc.setValue(tmpGlobal.getTmp_elem_desc());
		
		cbb_elementformat.setValue(elementFormatsManager.findById(
				tmpGlobal.getTmp_elem_format_id()).getElementFormat_desc());
		
		setVisibleByFormat();
		
		boolean isDateNotNull = tmpGlobal.getTmp_elem_dateformat_id() != null;
		boolean isLengthNotNull = tmpGlobal.getTmp_elem_length() != null;
		boolean isDecNotNull = tmpGlobal.getTmp_elem_decimal() != null;
		
		if (isDateNotNull)
				cbb_element_dateformat.setValue(dateFormatsManager.findById(
				tmpGlobal.getTmp_elem_dateformat_id()).getDateformat_desc());
		
		if (isLengthNotNull)
			ibx_element_length.setValue(tmpGlobal.getTmp_elem_length());
		
		if (isDecNotNull)
			ibx_element_decimal.setValue(tmpGlobal.getTmp_elem_decimal());
		
		boolean element_operator_eq_has_value = StringUtils
				.isNotEmpty(tmpGlobal.getTmp_elem_operator_eq());
		boolean element_operator_ne_has_value = StringUtils
				.isNotEmpty(tmpGlobal.getTmp_elem_operator_ne());
		boolean element_operator_gt_has_value = StringUtils
				.isNotEmpty(tmpGlobal.getTmp_elem_operator_gt());
		boolean element_operator_ge_has_value = StringUtils
				.isNotEmpty(tmpGlobal.getTmp_elem_operator_ge());
		boolean element_operator_lt_has_value = StringUtils
				.isNotEmpty(tmpGlobal.getTmp_elem_operator_lt());
		boolean element_operator_le_has_value = StringUtils
				.isNotEmpty(tmpGlobal.getTmp_elem_operator_le());

		chb_element_operator_eq.setChecked(element_operator_eq_has_value);
		chb_element_operator_ne.setChecked(element_operator_ne_has_value);
		chb_element_operator_gt.setChecked(element_operator_gt_has_value);
		chb_element_operator_ge.setChecked(element_operator_ge_has_value);
		chb_element_operator_lt.setChecked(element_operator_lt_has_value);
		chb_element_operator_le.setChecked(element_operator_le_has_value);
		
		// set disabled
		if (StringUtils.isNotEmpty(tmpGlobal.getId())) {
			cbb_elementtype.setDisabled(true);
			cbb_elementformat.setDisabled(true);
			chb_element_operator_eq.setDisabled(element_operator_eq_has_value);
			chb_element_operator_ne.setDisabled(element_operator_ne_has_value);
			chb_element_operator_gt.setDisabled(element_operator_gt_has_value);
			chb_element_operator_ge.setDisabled(element_operator_ge_has_value);
			chb_element_operator_lt.setDisabled(element_operator_lt_has_value);
			chb_element_operator_le.setDisabled(element_operator_le_has_value);
		}
	}

	@Override
	protected boolean validateInput() {
		boolean isValid = true;
		
		Comboitem elemTypeItem = cbb_elementtype.getSelectedItem();
		Comboitem elemFormatItem = cbb_elementformat.getSelectedItem();

		if (elemTypeItem == null){
			cbb_elementtype.setErrorMessage(getMsgPropVal(Commons.MESSAGE_WARN_ELEMTYPE));	
			isValid = false;
		}

		if (elemFormatItem == null){
			cbb_elementformat.setErrorMessage(getMsgPropVal(Commons.MESSAGE_WARN_ELEMFORMAT));
			isValid = false;
		} else {
			int elemFormatSelected = (Integer) elemFormatItem.getValue();
			if ((elemFormatSelected == 1) || (elemFormatSelected == 2) || (elemFormatSelected == 3)){
				if (ibx_element_length.getValue() == null){
					ibx_element_length.setErrorMessage(getMsgPropVal(Commons.MESSAGE_WARN_LENGTH));
					isValid = false;
				} else if (ibx_element_length.getValue() == 0){
					ibx_element_length.setErrorMessage(getMsgPropVal(Commons.MESSAGE_WARN_LENGTH_ZERO));
					isValid = false;
				} 
			} 

			if (elemFormatSelected == 4){
				Comboitem dateFormatItem = cbb_element_dateformat.getSelectedItem();
				if (dateFormatItem == null){
					cbb_element_dateformat.setErrorMessage(getMsgPropVal(Commons.MESSAGE_WARN_DATEFORMAT));
					isValid = false;
				}
			}

			if (elemFormatSelected == 5){
				if (ibx_element_length.getValue() == null){
					ibx_element_length.setErrorMessage(getMsgPropVal(Commons.MESSAGE_WARN_LENGTH));
					isValid = false;
				} else if (ibx_element_length.getValue() == 0){
					ibx_element_length.setErrorMessage(getMsgPropVal(Commons.MESSAGE_WARN_LENGTH_ZERO));
					isValid = false;
				}
				if (ibx_element_decimal.getValue() == null){
					ibx_element_decimal.setErrorMessage(getMsgPropVal(Commons.MESSAGE_WARN_DECIMAL));
					isValid = false;
				}
			} 
		}

		boolean flag = this.validateOperators();
		if (!flag) {
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

		Comboitem elemTypeItem = cbb_elementtype.getSelectedItem();
		if (elemTypeItem != null) {
			int elemtypeSelected = (Integer) elemTypeItem.getValue();

			tmpGlobal.setTmp_elemtype_id(elemtypeSelected);
		}

		Comboitem elemFormatItem = cbb_elementformat.getSelectedItem();
		if (elemFormatItem != null) {
			int elemFormatSelected = (Integer) elemFormatItem.getValue();
			tmpGlobal.setTmp_elem_format_id(elemFormatSelected);

			if ((elemFormatSelected == 1) || (elemFormatSelected == 2)
					|| (elemFormatSelected == 3)) {
				tmpGlobal.setTmp_elem_length(ibx_element_length.getValue());
			}
			if (elemFormatSelected == 4) {
				Comboitem dateFormatItem = cbb_element_dateformat
						.getSelectedItem();
				if (dateFormatItem != null) {
					int dateFormatSelected = (Integer) dateFormatItem
							.getValue();
					tmpGlobal.setTmp_elem_dateformat_id(dateFormatSelected);
				}
			}
			if (elemFormatSelected == 5) {
				tmpGlobal.setTmp_elem_length(ibx_element_length.getValue());
				tmpGlobal.setTmp_elem_decimal(ibx_element_decimal.getValue());
			}
		}

		tmpGlobal.setTmp_elem_desc(tbx_element_desc.getValue());

		if (chb_element_operator_eq.isChecked()) {
			tmpGlobal
					.setTmp_elem_operator_eq(getGlobalPropVal(Commons.OPTR_EQ));
		}
		if (chb_element_operator_ne.isChecked()) {
			tmpGlobal
					.setTmp_elem_operator_ne(getGlobalPropVal(Commons.OPTR_NE));
		}
		if (chb_element_operator_gt.isChecked()) {
			tmpGlobal
					.setTmp_elem_operator_gt(getGlobalPropVal(Commons.OPTR_GT));
		}
		if (chb_element_operator_ge.isChecked()) {
			tmpGlobal
					.setTmp_elem_operator_ge(getGlobalPropVal(Commons.OPTR_GE));
		}
		if (chb_element_operator_lt.isChecked()) {
			tmpGlobal
					.setTmp_elem_operator_lt(getGlobalPropVal(Commons.OPTR_LT));
		}
		if (chb_element_operator_le.isChecked()) {
			tmpGlobal
					.setTmp_elem_operator_le(getGlobalPropVal(Commons.OPTR_LE));
		}
	}

	@Override
	protected void resetViewValue() {
		cbb_elementtype.setValue(Commons.EMPTY_STRING);
		cbb_elementformat.setValue(Commons.EMPTY_STRING);
		cbb_element_dateformat.setValue(Commons.EMPTY_STRING);

		chb_element_operator_eq.setChecked(false);
		chb_element_operator_ne.setChecked(false);
		chb_element_operator_gt.setChecked(false);
		chb_element_operator_ge.setChecked(false);
		chb_element_operator_lt.setChecked(false);
		chb_element_operator_le.setChecked(false);

		tbx_element_desc.setValue(Commons.EMPTY_STRING);
		ibx_element_length.setValue(null);
		ibx_element_decimal.setValue(null);
	}

	@Override
	protected String getViewUrl() {
		return Commons.URL_VIEW_ELEMENT;
	}
	
	/*******************************************************************
	 * EVENT HANDLERS
	 ******************************************************************/
	public void onChange$cbb_elementformat() {
		setVisibleByFormat();
	}
	
	/*******************************************************************
	 * GETTERS / SETTERS
	 ******************************************************************/
	public AnnotateDataBinder getBinder() {
		return binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public List<ElementTypes> getElemTypeList() {
		return elemTypeList;
	}

	public void setElemTypeList(List<ElementTypes> elemTypeList) {
		this.elemTypeList = elemTypeList;
	}

	public List<ElementFormats> getElemFormatList() {
		return elemFormatList;
	}

	public void setElemFormatList(List<ElementFormats> elemFormatList) {
		this.elemFormatList = elemFormatList;
	}

	public List<DateFormats> getDateFormatList() {
		return dateFormatList;
	}

	public void setDateFormatList(List<DateFormats> dateFormatList) {
		this.dateFormatList = dateFormatList;
	}

	/*******************************************************************
	 * UTILITY METHODS
	 ******************************************************************/
	private void setVisibleByFormat() {
		if (cbb_elementformat.getValue().equalsIgnoreCase(
				getGlobalPropVal(Commons.FORMAT_NUMBER))) {
			rowLength.setVisible(true);
			rowDecimal.setVisible(false);
			rowDateFormat.setVisible(false);
		} else if (cbb_elementformat.getValue().equalsIgnoreCase(
				getGlobalPropVal(Commons.FORMAT_DATE))) {
			rowLength.setVisible(false);
			rowDecimal.setVisible(false);
			rowDateFormat.setVisible(true);
		} else if (cbb_elementformat.getValue().equalsIgnoreCase(
				getGlobalPropVal(Commons.FORMAT_AMOUNT))) {
			rowLength.setVisible(true);
			rowDecimal.setVisible(true);
			rowDateFormat.setVisible(false);
		} else {
			rowLength.setVisible(true);
			rowDecimal.setVisible(false);
			rowDateFormat.setVisible(false);
		}
	}
	
	private boolean validateOperators() {
		boolean element_operator_eq_checked = chb_element_operator_eq
				.isChecked();
		boolean element_operator_ne_checked = chb_element_operator_ne
				.isChecked();
		boolean element_operator_gt_checked = chb_element_operator_gt
				.isChecked();
		boolean element_operator_ge_checked = chb_element_operator_ge
				.isChecked();
		boolean element_operator_lt_checked = chb_element_operator_lt
				.isChecked();
		boolean element_operator_le_checked = chb_element_operator_le
				.isChecked();

		int flag = 0;
		if (!element_operator_eq_checked) {
			flag++;
		}

		if (!element_operator_ne_checked) {
			flag++;
		}

		if (!element_operator_gt_checked) {
			flag++;
		}

		if (!element_operator_ge_checked) {
			flag++;
		}

		if (!element_operator_lt_checked) {
			flag++;
		}

		if (!element_operator_le_checked) {
			flag++;
		}

		if (flag == 6) {
			try {
				Messagebox.show(
						getMsgPropVal(Commons.MESSAGE_WARN_OPERATORS),
						Commons.MSG_WARNING, Messagebox.OK,
						Messagebox.EXCLAMATION);
				return false;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return true;
	}
}