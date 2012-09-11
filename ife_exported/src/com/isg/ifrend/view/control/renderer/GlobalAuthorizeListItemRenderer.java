package com.isg.ifrend.view.control.renderer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.NullComparator;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.isg.ifrend.model.bean.ActionType;
import com.isg.ifrend.model.bean.CodeType;
import com.isg.ifrend.model.bean.Criteria;
import com.isg.ifrend.model.bean.DateFormats;
import com.isg.ifrend.model.bean.Element;
import com.isg.ifrend.model.bean.EnhancedCriterion;
import com.isg.ifrend.model.bean.Global;
import com.isg.ifrend.model.bean.Label;
import com.isg.ifrend.model.bean.Mli;
import com.isg.ifrend.model.bean.OldNewValuesBean;
import com.isg.ifrend.model.bean.Script;
import com.isg.ifrend.model.bean.TempCriteria;
import com.isg.ifrend.model.bean.TempEnhancedCriterion;
import com.isg.ifrend.model.bean.TmpCode;
import com.isg.ifrend.model.bean.TmpCodeType;
import com.isg.ifrend.model.bean.TmpElement;
import com.isg.ifrend.model.bean.TmpLabel;
import com.isg.ifrend.model.bean.TmpMli;
import com.isg.ifrend.model.bean.TmpScript;
import com.isg.ifrend.service.DateFormatsManager;
import com.isg.ifrend.service.SelectedManager;
import com.isg.ifrend.service.ServiceLocator;
import com.isg.ifrend.service.impl.GenericManagerImpl;
import com.isg.ifrend.utils.Commons;
import com.isg.ifrend.utils.DateUtil;
import com.isg.ifrend.utils.GlobalUtils;
import com.mysql.jdbc.StringUtils;

public class GlobalAuthorizeListItemRenderer implements ListitemRenderer {

	private GlobalUtils globalUtils = GlobalUtils.getGlobalUtilsInstance();

	public void render(Listitem item, Object data) throws Exception {
		if (data instanceof TmpElement) {
			this.setListItems(item, (TmpElement) data, Commons.URL_VIEW_ELEMENT);

		} else if (data instanceof TempCriteria) {
			this.setListItems(item, (TempCriteria) data,
					Commons.URL_VIEW_CRITERIA);

		} else if (data instanceof TmpScript) {
			this.setListItems(item, (TmpScript) data, Commons.URL_VIEW_SCRIPT);

		} else if (data instanceof TmpMli) {
			this.setListItems(item, (TmpMli) data, Commons.URL_VIEW_MLI);

		} else if (data instanceof TmpCodeType) {
			this.setListItems(item, (TmpCodeType) data,
					Commons.URL_VIEW_CODETYPE);

		} else if (data instanceof TmpLabel) {
			this.setListItems(item, (TmpLabel) data, Commons.URL_VIEW_LABEL);
		}
	}

	private void setListItems(Listitem item, Global g, String url) {
		Toolbarbutton toolBarBtn = new Toolbarbutton(g.getId());
		toolBarBtn.setHref(getTmpUrl(url, g.getId()));

		Listcell cellId = new Listcell();
		toolBarBtn.setParent(cellId);
		cellId.setParent(item);

		new Listcell(g.getEntityDesc()).setParent(item);
		new Listcell(ActionType.getDesc(g.getActionID())).setParent(item);

		new Listcell(g.getLastModifierUserID()).setParent(item);
		new Listcell(DateUtil.format(g.getDateLastModified())).setParent(item);

		if (ActionType.UPDATE.getId() == g.getActionID()) {
			List<OldNewValuesBean> onvList = this.compareChanges(g);

			StringBuilder strBuildOld = new StringBuilder();
			StringBuilder strBuildNew = new StringBuilder();

			for (OldNewValuesBean onValue : onvList) {
				strBuildOld.append(onValue.getOldValues()).append(
						Commons.NEW_LINE);
				strBuildNew.append(onValue.getNewValues()).append(
						Commons.NEW_LINE);
			}

			Textbox tbxOld = new Textbox();
			tbxOld.setValue(strBuildOld.toString());
			tbxOld.setMultiline(true);
			tbxOld.setReadonly(true);
			tbxOld.setRows(Commons.DISPLAY_ROWS);
			tbxOld.setWidth("280px");

			Listcell cellOld = new Listcell();
			tbxOld.setParent(cellOld);
			cellOld.setParent(item);

			Textbox tbxNew = new Textbox();
			tbxNew.setValue(strBuildNew.toString());
			tbxNew.setMultiline(true);
			tbxNew.setReadonly(true);
			tbxNew.setRows(Commons.DISPLAY_ROWS);
			tbxNew.setWidth("280px");

			Listcell cellNew = new Listcell();
			tbxNew.setParent(cellNew);
			cellNew.setParent(item);

		} else {
			new Listcell(Commons.EMPTY_STRING).setParent(item);
			new Listcell(Commons.EMPTY_STRING).setParent(item);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<OldNewValuesBean> compareChanges(Global tempBean) {
		GenericManagerImpl genericManager = ServiceLocator.getGenericManager();

		List<OldNewValuesBean> diffList = new ArrayList<OldNewValuesBean>();
		OldNewValuesBean onvBean;

		Comparator comparator = null;
		int result = 0;
		if (tempBean instanceof TmpElement) {
			String selected = null;
			final String selectedYes = "Selected";
			final String selectedNo = "Not Selected";

			TmpElement mstBean = new TmpElement(genericManager.viewDetails(
					Element.class, tempBean.getId()));
			TmpElement tmpBean = (TmpElement) tempBean;

			comparator = new BeanComparator("tmp_elemtype_id",
					new NullComparator(true));
			result = comparator.compare(mstBean, tmpBean);
			if (result != 0) {
				onvBean = new OldNewValuesBean();
				onvBean.setOldValues("Element Type: "
						+ mstBean.getTmp_elemtype_id());
				onvBean.setNewValues("Element Type: "
						+ tmpBean.getTmp_elemtype_id());
				diffList.add(onvBean);
			}

			comparator = new BeanComparator("tmp_elem_format_id",
					new NullComparator(true));
			result = comparator.compare(mstBean, tmpBean);
			if (result != 0) {
				onvBean = new OldNewValuesBean();
				onvBean.setOldValues("Element Format: "
						+ mstBean.getTmp_elem_format_id());
				onvBean.setNewValues("Element Format: "
						+ tmpBean.getTmp_elem_format_id());
				diffList.add(onvBean);
			}

			comparator = new BeanComparator("tmp_elem_length",
					new NullComparator(true));
			result = comparator.compare(mstBean, tmpBean);
			if (result != 0) {
				onvBean = new OldNewValuesBean();
				onvBean.setOldValues("Length: " + mstBean.getTmp_elem_length());
				onvBean.setNewValues("Length: " + tmpBean.getTmp_elem_length());
				diffList.add(onvBean);
			}

			comparator = new BeanComparator("tmp_elem_decimal",
					new NullComparator(true));
			result = comparator.compare(mstBean, tmpBean);
			if (result != 0) {
				onvBean = new OldNewValuesBean();
				onvBean.setOldValues("Decimal: "
						+ mstBean.getTmp_elem_decimal());
				onvBean.setNewValues("Decimal: "
						+ tmpBean.getTmp_elem_decimal());
				diffList.add(onvBean);
			}

			comparator = new BeanComparator("tmp_elem_dateformat_id",
					new NullComparator(true));
			result = comparator.compare(mstBean, tmpBean);
			if (result != 0) {
				onvBean = new OldNewValuesBean();
				onvBean.setOldValues("Date Format: "
						+ mstBean.getTmp_elem_dateformat_id());
				onvBean.setNewValues("Date Format: "
						+ tmpBean.getTmp_elem_dateformat_id());
				diffList.add(onvBean);
			}

			comparator = new BeanComparator("tmp_elem_desc",
					new NullComparator(true));
			result = comparator.compare(mstBean, tmpBean);
			if (result != 0) {
				onvBean = new OldNewValuesBean();
				onvBean.setOldValues("Description: "
						+ mstBean.getTmp_elem_desc());
				onvBean.setNewValues("Description: "
						+ tmpBean.getTmp_elem_desc());
				diffList.add(onvBean);
			}

			comparator = new BeanComparator("tmp_elem_operator_eq",
					new NullComparator(true));
			result = comparator.compare(mstBean, tmpBean);
			if (result != 0) {
				onvBean = new OldNewValuesBean();

				if (StringUtils
						.isNullOrEmpty(mstBean.getTmp_elem_operator_eq())) {
					selected = selectedNo;
				} else {
					selected = selectedYes;
				}

				onvBean.setOldValues("Operator-Equal: " + selected);

				if (StringUtils
						.isNullOrEmpty(tmpBean.getTmp_elem_operator_eq())) {
					selected = selectedNo;
				} else {
					selected = selectedYes;
				}

				onvBean.setNewValues("Operator-Equal: " + selected);
				diffList.add(onvBean);
			}

			comparator = new BeanComparator("tmp_elem_operator_ne",
					new NullComparator(true));
			result = comparator.compare(mstBean, tmpBean);
			if (result != 0) {
				onvBean = new OldNewValuesBean();

				if (StringUtils
						.isNullOrEmpty(mstBean.getTmp_elem_operator_ne())) {
					selected = selectedNo;
				} else {
					selected = selectedYes;
				}

				onvBean.setOldValues("Operator-Not Equal: " + selected);

				if (StringUtils
						.isNullOrEmpty(tmpBean.getTmp_elem_operator_ne())) {
					selected = selectedNo;
				} else {
					selected = selectedYes;
				}

				onvBean.setNewValues("Operator-Not Equal: " + selected);
				diffList.add(onvBean);

			}

			comparator = new BeanComparator("tmp_elem_operator_gt",
					new NullComparator(true));
			result = comparator.compare(mstBean, tmpBean);
			if (result != 0) {
				onvBean = new OldNewValuesBean();

				if (StringUtils
						.isNullOrEmpty(mstBean.getTmp_elem_operator_gt())) {
					selected = selectedNo;
				} else {
					selected = selectedYes;
				}

				onvBean.setOldValues("Operator-Greater Than: " + selected);

				if (StringUtils
						.isNullOrEmpty(tmpBean.getTmp_elem_operator_gt())) {
					selected = selectedNo;
				} else {
					selected = selectedYes;
				}

				onvBean.setNewValues("Operator-Greater Than: " + selected);
				diffList.add(onvBean);

			}

			comparator = new BeanComparator("tmp_elem_operator_ge",
					new NullComparator(true));
			result = comparator.compare(mstBean, tmpBean);
			if (result != 0) {
				onvBean = new OldNewValuesBean();

				if (StringUtils
						.isNullOrEmpty(mstBean.getTmp_elem_operator_ge())) {
					selected = selectedNo;
				} else {
					selected = selectedYes;
				}

				onvBean.setOldValues("Operator-Greater/Equals: " + selected);

				if (StringUtils
						.isNullOrEmpty(tmpBean.getTmp_elem_operator_ge())) {
					selected = selectedNo;
				} else {
					selected = selectedYes;
				}

				onvBean.setNewValues("Operator-Greater/Equals: " + selected);
				diffList.add(onvBean);

			}

			comparator = new BeanComparator("tmp_elem_operator_lt",
					new NullComparator(true));
			result = comparator.compare(mstBean, tmpBean);
			if (result != 0) {
				onvBean = new OldNewValuesBean();

				if (StringUtils
						.isNullOrEmpty(mstBean.getTmp_elem_operator_lt())) {
					selected = selectedNo;
				} else {
					selected = selectedYes;
				}

				onvBean.setOldValues("Operator-Lesser: " + selected);

				if (StringUtils
						.isNullOrEmpty(tmpBean.getTmp_elem_operator_lt())) {
					selected = selectedNo;
				} else {
					selected = selectedYes;
				}

				onvBean.setNewValues("Operator-Lesser: " + selected);
				diffList.add(onvBean);

			}

			comparator = new BeanComparator("tmp_elem_operator_le",
					new NullComparator(true));
			result = comparator.compare(mstBean, tmpBean);
			if (result != 0) {
				onvBean = new OldNewValuesBean();

				if (StringUtils
						.isNullOrEmpty(mstBean.getTmp_elem_operator_le())) {
					selected = selectedNo;
				} else {
					selected = selectedYes;
				}

				onvBean.setOldValues("Operator-Lesser/Equals: " + selected);

				if (StringUtils
						.isNullOrEmpty(tmpBean.getTmp_elem_operator_le())) {
					selected = selectedNo;
				} else {
					selected = selectedYes;
				}

				onvBean.setNewValues("Operator-Lesser/Equals: " + selected);
				diffList.add(onvBean);

			}
		} else if (tempBean instanceof TempCriteria) {

			TempCriteria mstBean = new TempCriteria(genericManager.viewDetails(
					Criteria.class, tempBean.getId()));
			;
			TempCriteria tmpBean = (TempCriteria) tempBean;

			comparator = new BeanComparator("country_code", new NullComparator(
					true));
			result = comparator.compare(mstBean, tmpBean);
			if (result != 0) {
				onvBean = new OldNewValuesBean();
				onvBean.setOldValues("Country Code: "
						+ mstBean.getCountry_code());
				onvBean.setNewValues("Country Code: "
						+ tmpBean.getCountry_code());
				diffList.add(onvBean);
			}

			comparator = new BeanComparator("function_id", new NullComparator(
					true));
			result = comparator.compare(mstBean, tmpBean);
			if (result != 0) {
				onvBean = new OldNewValuesBean();
				onvBean.setOldValues("Function: " + mstBean.getFunction_id());
				onvBean.setNewValues("Function: " + tmpBean.getFunction_id());
				diffList.add(onvBean);
			}

			comparator = new BeanComparator("user_field_id",
					new NullComparator(true));
			result = comparator.compare(mstBean, tmpBean);
			if (result != 0) {
				onvBean = new OldNewValuesBean();
				onvBean.setOldValues("User Field: "
						+ mstBean.getUser_field_id());
				onvBean.setNewValues("User Field: "
						+ tmpBean.getUser_field_id());
				diffList.add(onvBean);
			}

			comparator = new BeanComparator("priority_id", new NullComparator(
					true));
			result = comparator.compare(mstBean, tmpBean);
			if (result != 0) {
				onvBean = new OldNewValuesBean();
				onvBean.setOldValues("Priority: " + mstBean.getPriority_id());
				onvBean.setNewValues("Priority: " + tmpBean.getPriority_id());
				diffList.add(onvBean);
			}

			comparator = new BeanComparator("description", new NullComparator(
					true));
			result = comparator.compare(mstBean, tmpBean);
			if (result != 0) {
				onvBean = new OldNewValuesBean();
				onvBean.setOldValues("Description: " + mstBean.getDescription());
				onvBean.setNewValues("Description: " + tmpBean.getDescription());
				diffList.add(onvBean);
			}

			comparator = new BeanComparator("pass_action_id",
					new NullComparator(true));
			result = comparator.compare(mstBean, tmpBean);
			if (result != 0) {
				onvBean = new OldNewValuesBean();
				onvBean.setOldValues("Pass Action: "
						+ mstBean.getPass_action_id());
				onvBean.setNewValues("Pass Action: "
						+ tmpBean.getPass_action_id());
				diffList.add(onvBean);
			}

			comparator = new BeanComparator("pass_messagetype_id",
					new NullComparator(true));
			result = comparator.compare(mstBean, tmpBean);
			if (result != 0) {
				onvBean = new OldNewValuesBean();
				onvBean.setOldValues("Pass Message Type: "
						+ mstBean.getPass_messagetype_id());
				onvBean.setNewValues("Pass Message Type: "
						+ tmpBean.getPass_messagetype_id());
				diffList.add(onvBean);
			}

			comparator = new BeanComparator("pass_commenttype_id",
					new NullComparator(true));
			result = comparator.compare(mstBean, tmpBean);
			if (result != 0) {
				onvBean = new OldNewValuesBean();
				onvBean.setOldValues("Pass Comment Type: "
						+ mstBean.getPass_commenttype_id());
				onvBean.setNewValues("Pass Comment Type: "
						+ tmpBean.getPass_commenttype_id());
				diffList.add(onvBean);
			}

			comparator = new BeanComparator("pass_message", new NullComparator(
					true));
			result = comparator.compare(mstBean, tmpBean);
			if (result != 0) {
				onvBean = new OldNewValuesBean();
				onvBean.setOldValues("Pass Message: "
						+ mstBean.getPass_message());
				onvBean.setNewValues("Pass Message: "
						+ tmpBean.getPass_message());
				diffList.add(onvBean);
			}

			comparator = new BeanComparator("pass_comment", new NullComparator(
					true));
			result = comparator.compare(mstBean, tmpBean);
			if (result != 0) {
				onvBean = new OldNewValuesBean();
				onvBean.setOldValues("Pass Comment: "
						+ mstBean.getPass_comment());
				onvBean.setNewValues("Pass Comment: "
						+ tmpBean.getPass_comment());
				diffList.add(onvBean);
			}

			comparator = new BeanComparator("fail_action_id",
					new NullComparator(true));
			result = comparator.compare(mstBean, tmpBean);
			if (result != 0) {
				onvBean = new OldNewValuesBean();
				onvBean.setOldValues("Fail Action: "
						+ mstBean.getFail_action_id());
				onvBean.setNewValues("Fail Action: "
						+ tmpBean.getFail_action_id());
				diffList.add(onvBean);
			}

			comparator = new BeanComparator("fail_messagetype_id",
					new NullComparator(true));
			result = comparator.compare(mstBean, tmpBean);
			if (result != 0) {
				onvBean = new OldNewValuesBean();
				onvBean.setOldValues("Fail Message Type: "
						+ mstBean.getFail_messagetype_id());
				onvBean.setNewValues("Fail Message Type: "
						+ tmpBean.getFail_messagetype_id());
				diffList.add(onvBean);
			}

			comparator = new BeanComparator("fail_commenttype_id",
					new NullComparator(true));
			result = comparator.compare(mstBean, tmpBean);
			if (result != 0) {
				onvBean = new OldNewValuesBean();
				onvBean.setOldValues("Fail Comment Type: "
						+ mstBean.getFail_commenttype_id());
				onvBean.setNewValues("Fail Comment Type: "
						+ tmpBean.getFail_commenttype_id());
				diffList.add(onvBean);
			}

			comparator = new BeanComparator("fail_message", new NullComparator(
					true));
			result = comparator.compare(mstBean, tmpBean);
			if (result != 0) {
				onvBean = new OldNewValuesBean();
				onvBean.setOldValues("Fail Message: "
						+ mstBean.getFail_message());
				onvBean.setNewValues("Fail Message: "
						+ tmpBean.getFail_message());
				diffList.add(onvBean);
			}

			comparator = new BeanComparator("fail_comment", new NullComparator(
					true));
			result = comparator.compare(mstBean, tmpBean);
			if (result != 0) {
				onvBean = new OldNewValuesBean();
				onvBean.setOldValues("Fail Comment: "
						+ mstBean.getFail_comment());
				onvBean.setNewValues("Fail Comment: "
						+ tmpBean.getFail_comment());
				diffList.add(onvBean);
			}

			SelectedManager selectedManager = ServiceLocator
					.getSelectedManager();
			List<EnhancedCriterion> ecList = (List<EnhancedCriterion>) selectedManager
					.findChildren(new EnhancedCriterion(),
							mstBean.getCriteria_id());
			List<TempEnhancedCriterion> tecList = (List<TempEnhancedCriterion>) selectedManager
					.findChildren(new TempEnhancedCriterion(),
							tmpBean.getCriteria_id());

			if (ecList.size() == tecList.size()) {
				for (int index = 0; index < ecList.size(); index++) {
					EnhancedCriterion eCriterion = (EnhancedCriterion) ecList
							.get(index);
					TempEnhancedCriterion mstEnhancedCriterion = new TempEnhancedCriterion(
							eCriterion);
					TempEnhancedCriterion tCriterion = (TempEnhancedCriterion) tecList
							.get(index);

					comparator = new BeanComparator("element_id",
							new NullComparator(true));
					result = comparator.compare(mstEnhancedCriterion,
							tCriterion);
					if (result != 0) {
						onvBean = new OldNewValuesBean();
						onvBean.setOldValues("Element: "
								+ mstEnhancedCriterion.getElement_id());
						onvBean.setNewValues("Element: "
								+ tCriterion.getElement_id());
						diffList.add(onvBean);
					}

					comparator = new BeanComparator("operator_code",
							new NullComparator(true));
					result = comparator.compare(mstEnhancedCriterion,
							tCriterion);
					if (result != 0) {
						onvBean = new OldNewValuesBean();
						onvBean.setOldValues("Operator: "
								+ mstEnhancedCriterion.getOperator_code());
						onvBean.setNewValues("Operator: "
								+ tCriterion.getOperator_code());
						diffList.add(onvBean);
					}

					comparator = new BeanComparator("enhanced_value_integer",
							new NullComparator(true));
					result = comparator.compare(mstEnhancedCriterion,
							tCriterion);
					if (result != 0) {
						onvBean = new OldNewValuesBean();
						onvBean.setOldValues("Value (Integer): "
								+ mstEnhancedCriterion
										.getEnhanced_value_integer());
						onvBean.setNewValues("Value (Integer): "
								+ tCriterion.getEnhanced_value_integer());
						diffList.add(onvBean);
					}

					comparator = new BeanComparator("enhanced_value_character",
							new NullComparator(true));
					result = comparator.compare(mstEnhancedCriterion,
							tCriterion);
					if (result != 0) {
						onvBean = new OldNewValuesBean();
						onvBean.setOldValues("Value (Character): "
								+ mstEnhancedCriterion
										.getEnhanced_value_character());
						onvBean.setNewValues("Value (Character): "
								+ tCriterion.getEnhanced_value_character());
						diffList.add(onvBean);
					}

					comparator = new BeanComparator("enhanced_value_element",
							new NullComparator(true));
					result = comparator.compare(mstEnhancedCriterion,
							tCriterion);
					if (result != 0) {
						onvBean = new OldNewValuesBean();
						onvBean.setOldValues("Value (Element): "
								+ mstEnhancedCriterion
										.getEnhanced_value_element());
						onvBean.setNewValues("Value (Element): "
								+ tCriterion.getEnhanced_value_element());
						diffList.add(onvBean);
					}

					comparator = new BeanComparator("enhanced_value_date",
							new NullComparator(true));
					result = comparator.compare(mstEnhancedCriterion,
							tCriterion);
					DateFormatsManager dateformatManager = ServiceLocator
							.getDateFormatsManager();
					if (result != 0) {
						onvBean = new OldNewValuesBean();
						if ((mstEnhancedCriterion
								.getEnhanced_value_dateformat_id() != null)
								&& (mstEnhancedCriterion
										.getEnhanced_value_date() != null)) {
							DateFormats mstDateFormat = dateformatManager
									.findById(mstEnhancedCriterion
											.getEnhanced_value_dateformat_id());
							onvBean.setOldValues("Value (Date): "
									+ DateUtil.formatDate(mstEnhancedCriterion
											.getEnhanced_value_date(),
											mstDateFormat.getDateformat_desc()));
						} else {
							onvBean.setOldValues("Value (Date): ");
						}

						if ((tCriterion.getEnhanced_value_dateformat_id() != null)
								&& (tCriterion.getEnhanced_value_date() != null)) {
							DateFormats tmpDateFormat = dateformatManager
									.findById(tCriterion
											.getEnhanced_value_dateformat_id());
							onvBean.setNewValues("Value (Date): "
									+ DateUtil.formatDate(
											tCriterion.getEnhanced_value_date(),
											tmpDateFormat.getDateformat_desc()));
						} else {
							onvBean.setNewValues("Value (Date): ");
						}

						diffList.add(onvBean);
					}
				}
			} else {
				onvBean = new OldNewValuesBean();
				onvBean.setOldValues("Enhanced Criteria Count: "
						+ ecList.size());
				onvBean.setNewValues("Enhanced Criteria Count: "
						+ tecList.size());
				diffList.add(onvBean);
			}
		} else if (tempBean instanceof TmpMli) {

			TmpMli mstBean = new TmpMli(genericManager.viewDetails(Mli.class,
					tempBean.getId()));
			TmpMli tmpBean = (TmpMli) tempBean;

			comparator = new BeanComparator("countryCode", new NullComparator(
					true));
			result = comparator.compare(mstBean, tmpBean);
			if (result != 0) {
				onvBean = new OldNewValuesBean();
				onvBean.setOldValues("Country Code: "
						+ mstBean.getCountryCode());
				onvBean.setNewValues("Country Code: "
						+ tmpBean.getCountryCode());
				diffList.add(onvBean);
			}

			comparator = new BeanComparator("functionCode", new NullComparator(
					true));
			result = comparator.compare(mstBean, tmpBean);
			if (result != 0) {
				onvBean = new OldNewValuesBean();
				onvBean.setOldValues("Function Code: "
						+ mstBean.getFunctionCode());
				onvBean.setNewValues("Function Code: "
						+ tmpBean.getFunctionCode());
				diffList.add(onvBean);
			}

			comparator = new BeanComparator("userFieldID", new NullComparator(
					true));
			result = comparator.compare(mstBean, tmpBean);
			if (result != 0) {
				onvBean = new OldNewValuesBean();
				onvBean.setOldValues("User Field: " + mstBean.getUserFieldID());
				onvBean.setNewValues("User Field: " + tmpBean.getUserFieldID());
				diffList.add(onvBean);
			}

			comparator = new BeanComparator("responseCodeID",
					new NullComparator(true));
			result = comparator.compare(mstBean, tmpBean);
			if (result != 0) {
				onvBean = new OldNewValuesBean();
				onvBean.setOldValues("Response Code: "
						+ mstBean.getResponseCodeID());
				onvBean.setNewValues("Response Code: "
						+ tmpBean.getResponseCodeID());
				diffList.add(onvBean);
			}

			comparator = new BeanComparator("desc", new NullComparator(true));
			result = comparator.compare(mstBean, tmpBean);
			if (result != 0) {
				onvBean = new OldNewValuesBean();
				onvBean.setOldValues("Description: " + mstBean.getDesc());
				onvBean.setNewValues("Description: " + tmpBean.getDesc());
				diffList.add(onvBean);
			}

			comparator = new BeanComparator("messageTypeID",
					new NullComparator(true));
			result = comparator.compare(mstBean, tmpBean);
			if (result != 0) {
				onvBean = new OldNewValuesBean();
				onvBean.setOldValues("Message Type: "
						+ mstBean.getMessageTypeID());
				onvBean.setNewValues("Message Type: "
						+ tmpBean.getMessageTypeID());
				diffList.add(onvBean);
			}

			comparator = new BeanComparator("message", new NullComparator(true));
			result = comparator.compare(mstBean, tmpBean);
			if (result != 0) {
				onvBean = new OldNewValuesBean();
				onvBean.setOldValues("Message: " + mstBean.getMessage());
				onvBean.setNewValues("Message: " + tmpBean.getMessage());
				diffList.add(onvBean);
			}

			comparator = new BeanComparator("commentTypeID",
					new NullComparator(true));
			result = comparator.compare(mstBean, tmpBean);
			if (result != 0) {
				onvBean = new OldNewValuesBean();
				onvBean.setOldValues("Comment Type: "
						+ mstBean.getCommentTypeID());
				onvBean.setNewValues("Comment Type: "
						+ tmpBean.getCommentTypeID());
				diffList.add(onvBean);
			}

			comparator = new BeanComparator("comment", new NullComparator(true));
			result = comparator.compare(mstBean, tmpBean);
			if (result != 0) {
				onvBean = new OldNewValuesBean();
				onvBean.setOldValues("Comment: " + mstBean.getComment());
				onvBean.setNewValues("Comment: " + tmpBean.getComment());
				diffList.add(onvBean);
			}
		} else if (tempBean instanceof TmpScript) {

			TmpScript mstBean = new TmpScript(genericManager.viewDetails(
					Script.class, tempBean.getId()));
			TmpScript tmpBean = (TmpScript) tempBean;

			comparator = new BeanComparator("countryCode", new NullComparator(
					true));
			result = comparator.compare(mstBean, tmpBean);
			if (result != 0) {
				onvBean = new OldNewValuesBean();
				onvBean.setOldValues("Country Code: "
						+ mstBean.getCountryCode());
				onvBean.setNewValues("Country Code: "
						+ tmpBean.getCountryCode());
				diffList.add(onvBean);
			}

			comparator = new BeanComparator("functionCode", new NullComparator(
					true));
			result = comparator.compare(mstBean, tmpBean);
			if (result != 0) {
				onvBean = new OldNewValuesBean();
				onvBean.setOldValues("Function Code: "
						+ mstBean.getFunctionCode());
				onvBean.setNewValues("Function Code: "
						+ tmpBean.getFunctionCode());
				diffList.add(onvBean);
			}

			comparator = new BeanComparator("userFieldID", new NullComparator(
					true));
			result = comparator.compare(mstBean, tmpBean);
			if (result != 0) {
				onvBean = new OldNewValuesBean();
				onvBean.setOldValues("User Field: " + mstBean.getUserFieldID());
				onvBean.setNewValues("User Field: " + tmpBean.getUserFieldID());
				diffList.add(onvBean);
			}

			comparator = new BeanComparator("priorityID", new NullComparator(
					true));
			result = comparator.compare(mstBean, tmpBean);
			if (result != 0) {
				onvBean = new OldNewValuesBean();
				onvBean.setOldValues("Priority: " + mstBean.getPriorityID());
				onvBean.setNewValues("Priority: " + tmpBean.getPriorityID());
				diffList.add(onvBean);
			}

			comparator = new BeanComparator("desc", new NullComparator(true));
			result = comparator.compare(mstBean, tmpBean);
			if (result != 0) {
				onvBean = new OldNewValuesBean();
				onvBean.setOldValues("Description: " + mstBean.getDesc());
				onvBean.setNewValues("Description: " + tmpBean.getDesc());
				diffList.add(onvBean);
			}

			comparator = new BeanComparator("messageTypeID",
					new NullComparator(true));
			result = comparator.compare(mstBean, tmpBean);
			if (result != 0) {
				onvBean = new OldNewValuesBean();
				onvBean.setOldValues("Message Type: "
						+ mstBean.getMessageTypeID());
				onvBean.setNewValues("Message Type: "
						+ tmpBean.getMessageTypeID());
				diffList.add(onvBean);
			}

			comparator = new BeanComparator("message", new NullComparator(true));
			result = comparator.compare(mstBean, tmpBean);
			if (result != 0) {
				onvBean = new OldNewValuesBean();
				onvBean.setOldValues("Message: " + mstBean.getMessage());
				onvBean.setNewValues("Message: " + tmpBean.getMessage());
				diffList.add(onvBean);
			}

			comparator = new BeanComparator("commentTypeID",
					new NullComparator(true));
			result = comparator.compare(mstBean, tmpBean);
			if (result != 0) {
				onvBean = new OldNewValuesBean();
				onvBean.setOldValues("Comment Type: "
						+ mstBean.getCommentTypeID());
				onvBean.setNewValues("Comment Type: "
						+ tmpBean.getCommentTypeID());
				diffList.add(onvBean);
			}

			comparator = new BeanComparator("comment", new NullComparator(true));
			result = comparator.compare(mstBean, tmpBean);
			if (result != 0) {
				onvBean = new OldNewValuesBean();
				onvBean.setOldValues("Comment: " + mstBean.getComment());
				onvBean.setNewValues("Comment: " + tmpBean.getComment());
				diffList.add(onvBean);
			}
		} else if (tempBean instanceof TmpCodeType) {

			TmpCodeType mstBean = new TmpCodeType(genericManager.viewDetails(
					CodeType.class, tempBean.getId()));
			TmpCodeType tmpBean = genericManager.viewDetails(TmpCodeType.class,
					tempBean.getId());

			if (mstBean.getDesc().equals(tmpBean.getDesc())) {
				onvBean = new OldNewValuesBean();
				onvBean.setOldValues("Description: " + mstBean.getDesc());
				onvBean.setNewValues("Description: " + tmpBean.getDesc());
				diffList.add(onvBean);
			}

			Set<TmpCode> oldList = mstBean.getTmpCodeSet();
			Set<TmpCode> newList = tmpBean.getTmpCodeSet();

			if (oldList.size() == newList.size()) {
				Set<TmpCode> notNewOldList = new HashSet<TmpCode>();
				Set<TmpCode> notNewNewList = new HashSet<TmpCode>();

				for (TmpCode oldItem : oldList) {
					for (TmpCode newItem : newList) {
						if (oldItem.getCodeValue().equals(
								newItem.getCodeValue())) {
							if (!oldItem.getCodeDesc().equals(
									newItem.getCodeDesc())) {
								onvBean = new OldNewValuesBean();
								onvBean.setOldValues("Code Description: "
										+ oldItem.getCodeDesc());
								onvBean.setNewValues("Code Description: "
										+ newItem.getCodeDesc());
								diffList.add(onvBean);
							}
							notNewOldList.add(oldItem);
							notNewNewList.add(newItem);
						}
					}
				}

				oldList.removeAll(notNewOldList);
				notNewOldList = null;
				newList.removeAll(notNewNewList);
				notNewNewList = null;

				List<OldNewValuesBean> tempList = new ArrayList<OldNewValuesBean>();
				for (TmpCode oldItem : oldList) {
					onvBean = new OldNewValuesBean();
					onvBean.setOldValues("Code Value: "
							+ oldItem.getCodeValue());
					tempList.add(onvBean);
				}
				oldList = null;

				int i = 0;
				for (TmpCode newItem : newList) {
					tempList.get(i++).setNewValues(
							"Code Value: " + newItem.getCodeValue());
				}
				newList = null;

				diffList.addAll(tempList);
			} else {
				onvBean = new OldNewValuesBean();
				onvBean.setOldValues("Code Count: " + oldList.size());
				onvBean.setNewValues("Code Count: " + newList.size());
				diffList.add(onvBean);
			}

		} else if (tempBean instanceof TmpLabel) {

			TmpLabel mstBean = new TmpLabel(genericManager.viewDetails(
					Label.class, tempBean.getId()));
			TmpLabel tmpBean = (TmpLabel) tempBean;

			comparator = new BeanComparator("labelLanguageCode",
					new NullComparator(true));
			result = comparator.compare(mstBean, tmpBean);
			if (result != 0) {
				onvBean = new OldNewValuesBean();
				onvBean.setOldValues("Language Code: "
						+ mstBean.getLabelLanguageCode());
				onvBean.setNewValues("Language Code: "
						+ tmpBean.getLabelLanguageCode());
				diffList.add(onvBean);
			}

			comparator = new BeanComparator("labelName", new NullComparator(
					true));
			result = comparator.compare(mstBean, tmpBean);
			if (result != 0) {
				onvBean = new OldNewValuesBean();
				onvBean.setOldValues("Name: " + mstBean.getLabelName());
				onvBean.setNewValues("Name: " + tmpBean.getLabelName());
				diffList.add(onvBean);
			}

			comparator = new BeanComparator("labelNativeString",
					new NullComparator(true));
			result = comparator.compare(mstBean, tmpBean);
			if (result != 0) {
				onvBean = new OldNewValuesBean();
				onvBean.setOldValues("Native String: "
						+ mstBean.getLabelNativeString());
				onvBean.setNewValues("Native String: "
						+ tmpBean.getLabelNativeString());
				diffList.add(onvBean);
			}
		}
		return diffList;
	}

	private String getTmpUrl(String url, String id) {
		return new StringBuilder(globalUtils.getGlobalPropValue(url))
				.append(Commons.URL_DELIMITER).append(Commons.URL_PARAM_TMP_ID)
				.append(Commons.EQUAL_SIGN).append(id).toString();
	}
}