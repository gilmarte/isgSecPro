package com.isg.ifrend.view.control;

import org.apache.commons.lang3.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;

import com.isg.ifrend.model.bean.Element;
import com.isg.ifrend.model.bean.ElementFormats;
import com.isg.ifrend.model.bean.HstElement;
import com.isg.ifrend.model.bean.TmpElement;
import com.isg.ifrend.service.DateFormatsManager;
import com.isg.ifrend.service.ElementFormatsManager;
import com.isg.ifrend.service.ElementTypesManager;
import com.isg.ifrend.service.GlobalManager;
import com.isg.ifrend.service.ServiceLocator;
import com.isg.ifrend.utils.Commons;

/**
 * 
 */

/**
 * @author kristine.furto
 * 
 */
public class ElementViewComposer extends GlobalViewComposer<Element, TmpElement, HstElement> {
	private static final long serialVersionUID = -2798903030585858677L;

	private static final String YES = "Yes";
	private static final String NO = "No";

	private Label elementTypeLbl;
	private Label descLbl;

	private Label formatLbl;
	private Label lengthLbl;
	private Label decimalLbl;
	private Label dateFormatLbl;

	private Row dateFormatRow;
	private Row decimalRow;
	private Row lengthRow;

	private Label equalsLbl;
	private Label notEqualsLbl;
	private Label greaterLbl;
	private Label greaterEqualsLbl;
	private Label lesserLbl;
	private Label lesserEqualsLbl;
	
	// service
	private ElementTypesManager elementTypesManager = ServiceLocator
			.getElementTypesManager();
	private ElementFormatsManager elementFormatsManager = ServiceLocator
			.getElementFormatsManager();
	private DateFormatsManager dateFormatsManager = ServiceLocator
			.getDateFormatsManager();

	@Override
	protected GlobalManager<Element, TmpElement> getManager() {
		return ServiceLocator.getElementManager();
	}
	
	@Override
	protected TmpElement toTmp(Element m) {
		return new TmpElement(m);
	}
	
	@Override
	protected TmpElement toTmpFromHst(HstElement h) {
		return new TmpElement(h);
	}
	
	@Override
	protected Class<HstElement> getHstGlobalClass() {
		return HstElement.class;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		setVisibleByFormat();
		setAllowedOperators();
	}

	private void setAllowedOperators() {

		if (StringUtils.isNotEmpty(tmpGlobal.getTmp_elem_operator_eq()))
			equalsLbl.setValue(YES);
		else
			equalsLbl.setValue(NO);

		if (StringUtils.isNotEmpty(tmpGlobal.getTmp_elem_operator_ne()))
			notEqualsLbl.setValue(YES);
		else
			notEqualsLbl.setValue(NO);

		if (StringUtils.isNotEmpty(tmpGlobal.getTmp_elem_operator_gt()))
			greaterLbl.setValue(YES);
		else
			greaterLbl.setValue(NO);

		if (StringUtils.isNotEmpty(tmpGlobal.getTmp_elem_operator_ge()))
			greaterEqualsLbl.setValue(YES);
		else
			greaterEqualsLbl.setValue(NO);

		if (StringUtils.isNotEmpty(tmpGlobal.getTmp_elem_operator_lt()))
			lesserLbl.setValue(YES);
		else
			lesserLbl.setValue(NO);

		if (StringUtils.isNotEmpty(tmpGlobal.getTmp_elem_operator_le()))
			lesserEqualsLbl.setValue(YES);
		else
			lesserEqualsLbl.setValue(NO);

	}

	private void setVisibleByFormat() {
		String elementFormat = ((ElementFormats) elementFormatsManager.findById(
				tmpGlobal.getTmp_elem_format_id())).getElementFormat_desc();
		/*.getTmp_elemFormats()
				.getElementFormat_desc();*/

		if (StringUtils.isNotEmpty(elementFormat)) {
			if (elementFormat
					.equalsIgnoreCase(getGlobalPropVal(Commons.FORMAT_NUMBER))) {
				lengthRow.setVisible(true);
				decimalRow.setVisible(false);
				dateFormatRow.setVisible(false);
			} else if (elementFormat
					.equalsIgnoreCase(getGlobalPropVal(Commons.FORMAT_DATE))) {
				lengthRow.setVisible(false);
				decimalRow.setVisible(false);
				dateFormatRow.setVisible(true);
			} else if (elementFormat
					.equalsIgnoreCase(getGlobalPropVal(Commons.FORMAT_AMOUNT))) {
				lengthRow.setVisible(true);
				decimalRow.setVisible(true);
				dateFormatRow.setVisible(false);
			} else {
				lengthRow.setVisible(true);
				decimalRow.setVisible(false);
				dateFormatRow.setVisible(false);
			}
		}
	}

	@Override
	protected void setViewValue() {
		elementTypeLbl.setValue(elementTypesManager.findById(
				this.tmpGlobal.getTmp_elemtype_id()).getElementtype_desc());
		descLbl.setValue(this.tmpGlobal.getTmp_elem_desc());

		formatLbl.setValue(elementFormatsManager.findById(
				this.tmpGlobal.getTmp_elem_format_id()).getElementFormat_desc());
		
		boolean isDateNotNull = this.tmpGlobal.getTmp_elem_dateformat_id() != null;
		boolean isLengthNotNull = this.tmpGlobal.getTmp_elem_length() != null;
		boolean isDecNotNull = this.tmpGlobal.getTmp_elem_decimal() != null;
				
		if (isDateNotNull)
			dateFormatLbl.setValue(dateFormatsManager.findById(
					this.tmpGlobal.getTmp_elem_dateformat_id()).getDateformat_desc());
		if (isLengthNotNull)
			lengthLbl.setValue(this.tmpGlobal.getTmp_elem_length().toString());

		if (isDecNotNull)
			decimalLbl.setValue(this.tmpGlobal.getTmp_elem_decimal().toString());
		
	}

	@Override
	protected String getViewUrl() {
		return Commons.URL_VIEW_ELEMENT;
	}

	@Override
	protected String getUpdateUrl() {
		return Commons.URL_UPDATE_ELEMENT;
	}

	@Override
	protected String getAddUrl() {
		return Commons.URL_ADD_ELEMENT;
	}
}
