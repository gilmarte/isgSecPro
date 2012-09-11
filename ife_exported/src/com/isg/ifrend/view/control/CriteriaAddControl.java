package com.isg.ifrend.view.control;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;

import com.isg.ifrend.model.bean.Action;
import com.isg.ifrend.model.bean.ActionType;
import com.isg.ifrend.model.bean.Code;
import com.isg.ifrend.model.bean.CodeType;
import com.isg.ifrend.model.bean.CommentType;
import com.isg.ifrend.model.bean.Country;
import com.isg.ifrend.model.bean.Criteria;
import com.isg.ifrend.model.bean.DateFormats;
import com.isg.ifrend.model.bean.Element;
import com.isg.ifrend.model.bean.EnhancedCriterion;
import com.isg.ifrend.model.bean.Function;
import com.isg.ifrend.model.bean.HistCriteria;
import com.isg.ifrend.model.bean.HistEnhancedCriteria;
import com.isg.ifrend.model.bean.MessageType;
import com.isg.ifrend.model.bean.Operator;
import com.isg.ifrend.model.bean.Priority;
import com.isg.ifrend.model.bean.TempCriteria;
import com.isg.ifrend.model.bean.TempEnhancedCriterion;
import com.isg.ifrend.model.bean.TmpCodeType;
import com.isg.ifrend.model.bean.UserField;
import com.isg.ifrend.security.utils.SecurityUtils;
import com.isg.ifrend.service.CountryManager;
import com.isg.ifrend.service.CriteriaManager;
import com.isg.ifrend.service.DateFormatsManager;
import com.isg.ifrend.service.FunctionManager;
import com.isg.ifrend.service.GlobalManager;
import com.isg.ifrend.service.OperatorManager;
import com.isg.ifrend.service.PriorityManager;
import com.isg.ifrend.service.SelectedManager;
import com.isg.ifrend.service.ServiceLocator;
import com.isg.ifrend.service.UserFieldManager;
import com.isg.ifrend.utils.Commons;
import com.isg.ifrend.utils.DateUtil;
import com.isg.ifrend.utils.GlobalUtils;
import com.isg.ifrend.view.control.renderer.EnhancedCriteriaListItemRenderer;
import com.isg.ifrend.view.control.renderer.GlobalComboItemRenderer;
import com.isg.ifrend.view.control.renderer.GlobalSearchBoxListItemRenderer;
import com.mysql.jdbc.StringUtils;


public class CriteriaAddControl  extends GenericForwardComposer {

	private static final long serialVersionUID = 1L;

	private AnnotateDataBinder binder;
	private TempCriteria tempCriteria;
	private TempEnhancedCriterion tempEnhancedCriterion;
	private HistEnhancedCriteria histEnhancedCriteria;
	private GlobalUtils globalUtils = GlobalUtils.getGlobalUtilsInstance();
	private SecurityUtils securityUtils = SecurityUtils.getSecurityUtilsInstance();
	
	private int element_type_id;
	private int dateFormat_id;
	private String dateFormat;
	private String enhancedCriteriaElementID;
	private String enhancedCriteriaOperatorCode;
	private String enhancedCriteriaValue;

	private Combobox cbb_operator;
	private Combobox cbb_country_code;
	private Combobox cbb_function_code;
	private Combobox cbb_userfield;
	private Combobox cbb_priority;
	private Combobox cbb_passaction;
	private Combobox cbb_pass_messagetype;
	private Combobox cbb_pass_lettercode;
	private Combobox cbb_pass_commenttype;
	private Combobox cbb_failaction;
	private Combobox cbb_fail_messagetype;
	private Combobox cbb_fail_lettercode;
	private Combobox cbb_fail_commenttype;
	private Intbox ibx_enhancedvalue_integer;
	private Listbox criteriaList;
	private Listbox lbx_search_id;
	private Listbox lbx_elements;
	private Listbox lbx_element_forvalue;
	private Textbox tbx_final_criteria;
	private Textbox tbx_description;
	private Textbox pass_message;
	private Textbox pass_comment;
	private Textbox fail_message;
	private Textbox fail_comment;
	private Textbox tbx_enhancedvalue_character;
	private Radiogroup compare_value_grp;
	private Radio rad_value;
	private Radio rad_element;
	private Datebox dbx_enhancedvalue_date;
	private Bandbox bbox_element_1;
	private Bandbox bbox_element_2;

	private List<Country> countryList;
	private List<Function> functionList;
	private List<Priority> priorityList;
	private List<UserField> userFieldList;
	private List<MessageType> messageTypeList;
	private List<Action> actionPassList;
	private List<CommentType> commentTypeList;
	private List<Action> actionFailList;
	private List<Element> elementList;
	private List<Operator> operatorList = new ArrayList<Operator>();
	private List<TempEnhancedCriterion> tempEnhancedCriterionList = new ArrayList<TempEnhancedCriterion>();
	private List<HistEnhancedCriteria> histEnhancedCriterionList = new ArrayList<HistEnhancedCriteria>();
	private List<Element> elemList;
	private List<Criteria> criterionList;
	private List<Code> letterCodeList = new ArrayList<Code>();

	private GlobalManager<CodeType, TmpCodeType> codeTypeManager = ServiceLocator.getCodeTypeManager();
	private CountryManager countryManager = ServiceLocator.getCountryManager();
	private FunctionManager functionManager = ServiceLocator.getFunctionManager();
	private PriorityManager priorityManager = ServiceLocator.getPriorityManager();
	private UserFieldManager userFieldManager = ServiceLocator.getUserFieldManager();
	private OperatorManager operatorManager = ServiceLocator.getOperatorManager();
	private CriteriaManager criteriaManager = ServiceLocator.getCriteriaManager();
	private DateFormatsManager dateformatManager = ServiceLocator.getDateFormatsManager();
	private SelectedManager selectedManager = ServiceLocator.getSelectedManager();

	@SuppressWarnings("unchecked")
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		tempCriteria = new TempCriteria();
		comp.setAttribute(comp.getId() + "Control", this, true);

		cbb_operator.setDisabled(true);
		ibx_enhancedvalue_integer.setVisible(false);
		tbx_enhancedvalue_character.setVisible(false);
		bbox_element_2.setVisible(false);
		dbx_enhancedvalue_date.setVisible(false);

		criterionList = (List<Criteria>) selectedManager.findAllMasterDataNotDeleted(new Criteria());

		lbx_search_id.setModel(new ListModelList(criterionList));
		lbx_search_id.setItemRenderer(new GlobalSearchBoxListItemRenderer());

		if (elementList.size() > 0){
			lbx_elements.setModel(new ListModelList(elementList));
			lbx_elements.setItemRenderer(new GlobalSearchBoxListItemRenderer());
		}

		rad_value.setDisabled(true);
		rad_element.setDisabled(true);

		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
	}

	@SuppressWarnings("unchecked")
	public void doBeforeComposeChildren(Component comp) throws Exception {
		super.doBeforeComposeChildren(comp);

		countryList = countryManager.getCountryList();
		functionList = functionManager.getFunctionList();
		priorityList = priorityManager.getPriorityList();
		userFieldList = userFieldManager.getUserFieldList();

		actionPassList = GlobalUtils.removeActionItemList(0);
		actionFailList = GlobalUtils.removeActionItemList(1);

		messageTypeList = new ArrayList<MessageType>(Arrays.asList(MessageType.values()));

		CodeType letterCode = new CodeType();
		letterCode.setDesc(Commons.CODETYPE_LETTER_CODES);
		List<CodeType> codeTypeList = codeTypeManager.searchActiveList(letterCode);
		if (codeTypeList != null && !codeTypeList.isEmpty()) {
			letterCodeList = new ArrayList<Code>(codeTypeList.get(0).getCodeSet());
		}
		
		/* Configuration Setup Warning */
		if (letterCodeList == null || 
				(letterCodeList  != null && letterCodeList.isEmpty()) ) {
			//messageLbl.setValue(getMsgPropVal(Commons.MSG_CONFIG_CT_LETTERCODE));
			
			try {
				Messagebox.show(getMsgPropVal(Commons.MSG_CONFIG_CT_LETTERCODE),
						getMsgPropVal(Commons.MSG_TITLE_SETUP_CT), Messagebox.OK, Messagebox.EXCLAMATION,
				new EventListener() {
					public void onEvent(Event evt) throws ParseException,
							InterruptedException {
						if (Messagebox.ON_OK.equals(evt.getName())) {
							Executions.sendRedirect(globalUtils.getGlobalPropValue(Commons.URL_ADD_CODETYPE));
						}
					}
				});
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
		commentTypeList = new ArrayList<CommentType>(Arrays.asList(CommentType.values()));
		elementList = (List<Element>) selectedManager.findAllMasterDataNotDeleted(new Element());
	}

	private int determineActionIndex(int id, boolean isPassAction){

		if (isPassAction){
			for (int index=0; index<actionPassList.size(); index++){
				Action actionBean = actionPassList.get(index);
				if (actionBean.getAction_id() == id){
					return index;
				}
			}
		} else {
			for (int index=0; index<actionFailList.size(); index++){
				Action actionBean = actionFailList.get(index);
				if (actionBean.getAction_id() == id){
					return index;
				}
			}
		}
		return 0;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void onClick$btn_copy() throws InterruptedException {

		Set items = lbx_search_id.getSelectedItems();
		List itemList = new ArrayList(items);
		ListModel model = lbx_search_id.getModel();

		if (itemList.size() > 0){
			Listitem selectedItem = (Listitem) itemList.get(0);
			Criteria criterion = (Criteria) model.getElementAt(selectedItem.getIndex());

			cbb_country_code.setSelectedIndex(this.getPositionInList(new Country(), criterion.getCountry_code()));
			cbb_function_code.setSelectedIndex(this.getPositionInList(new Function(), criterion.getFunction_id()));
			cbb_userfield.setSelectedIndex(this.getPositionInList(new UserField(), criterion.getUser_field_id()));
			cbb_priority.setSelectedIndex(this.getPositionInList(new Priority(), criterion.getPriority_id()));
			tbx_description.setValue(criterion.getDescription());

			cbb_passaction.setSelectedIndex(this.determineActionIndex(criterion.getPass_action_id(), true));

			cbb_pass_messagetype.setSelectedIndex(this.getEnumIndex(criterion.getPass_messagetype_id(), true));
			cbb_pass_lettercode.setSelectedIndex(this.getPositionInList(new Code(), criterion.getPass_lettercode_id()));
			cbb_pass_commenttype.setSelectedIndex(this.getEnumIndex(criterion.getPass_commenttype_id(), false));
			pass_message.setValue(criterion.getPass_message());
			pass_comment.setValue(criterion.getPass_comment());

			cbb_failaction.setSelectedIndex(this.determineActionIndex(criterion.getFail_action_id(), false));
			cbb_fail_messagetype.setSelectedIndex(this.getEnumIndex(criterion.getFail_messagetype_id(), true));
			cbb_fail_lettercode.setSelectedIndex(this.getPositionInList(new Code(), criterion.getFail_lettercode_id()));
			cbb_fail_commenttype.setSelectedIndex(this.getEnumIndex(criterion.getFail_commenttype_id(), false));
			fail_message.setValue(criterion.getFail_message());
			fail_comment.setValue(criterion.getFail_comment());


			List<EnhancedCriterion> ecList = selectedManager.findChildren(new EnhancedCriterion(), criterion.getCriteria_id());
			for (int index=0; index<ecList.size(); index++){
				EnhancedCriterion ecBean = ecList.get(index);
				tempEnhancedCriterion = new TempEnhancedCriterion();
				tempEnhancedCriterion.setElement_id(ecBean.getElement_id());
				tempEnhancedCriterion.setOperator_code(ecBean.getOperator_code());
				tempEnhancedCriterion.setEnhanced_value_character(ecBean.getEnhanced_value_character());
				tempEnhancedCriterion.setEnhanced_value_integer(ecBean.getEnhanced_value_integer());
				tempEnhancedCriterion.setEnhanced_value_dateformat_id(ecBean.getEnhanced_value_dateformat_id());

				if (tempEnhancedCriterion.getEnhanced_value_dateformat_id() != null){
					DateFormats dateFormat = dateformatManager.findById(tempEnhancedCriterion.getEnhanced_value_dateformat_id());
					tempEnhancedCriterion.setDateFormat(dateFormat.getDateformat_desc());
				}

				tempEnhancedCriterion.setEnhanced_value_date(ecBean.getEnhanced_value_date());
				tempEnhancedCriterion.setEnhanced_value_element(ecBean.getEnhanced_value_element());
				tempEnhancedCriterionList.add(tempEnhancedCriterion);
			}
			criteriaList.setModel(new ListModelList(tempEnhancedCriterionList));
			criteriaList.setItemRenderer(new EnhancedCriteriaListItemRenderer());

			StringBuilder finalCriteriaString = new StringBuilder();
			for (int index=0; index < tempEnhancedCriterionList.size(); index++ ){
				TempEnhancedCriterion etBean = tempEnhancedCriterionList.get(index);

				if ((index >= 1) && (index < tempEnhancedCriterionList.size())){
					finalCriteriaString.append(Commons.CONJUNCTION);
				}

				finalCriteriaString.append(etBean.getElement_id());
				finalCriteriaString.append(GlobalUtils.determineOperator(etBean.getOperator_code()));

				if (etBean.getEnhanced_value_element() != null){
					finalCriteriaString.append("'" + etBean.getEnhanced_value_element() + "'");
				} else if (etBean.getEnhanced_value_character() != null){
					finalCriteriaString.append("'" + etBean.getEnhanced_value_character() + "'");
				} else if (etBean.getEnhanced_value_date() != null) {
					SimpleDateFormat formatter= new SimpleDateFormat(etBean.getDateFormat());
					finalCriteriaString.append("'" + formatter.format(etBean.getEnhanced_value_date()) + "'");
				} else if (etBean.getEnhanced_value_integer() != null){
					finalCriteriaString.append("'" + etBean.getEnhanced_value_integer() + "'");
				}

				tbx_final_criteria.setValue(finalCriteriaString.toString());
			}
		}
	}

	@SuppressWarnings("unchecked")
	private int getPositionInList(Object obj, Object id){

		if (Country.class.isInstance(obj)){
			for (int index=0; index<countryList.size(); index++){
				Country country = countryList.get(index);
				if (country.getCountry_code().equals((String)id))
					return index;
			}
		} else if (Function.class.isInstance(obj)){
			for (int index=0; index<functionList.size(); index++){
				Function function = functionList.get(index);
				if (function.getFunction_code().equals((String)id))
					return index;
			}
		} else if (Action.class.isInstance(obj)){
			List<Action> allActionList = (List<Action>) selectedManager.getRecordList(new Action());

			for (int index=0; index<allActionList.size(); index++){
				Action action = allActionList.get(index);
				if (action.getAction_id() == ((Integer)id).intValue())
					return index;
			}
		} else if (Code.class.isInstance(obj)){

			for (int index=0; index<letterCodeList.size(); index++){
				Code code = letterCodeList.get(index);
				if (code.getCodeID() == (Integer) id)
					return index;
			}
		} else if (UserField.class.isInstance(obj)){
			for (int index=0; index<userFieldList.size(); index++){
				UserField userField = userFieldList.get(index);
				if (userField.getUserFieldID() == ((Integer)id).intValue())
					return index;
			}
		} else if (Priority.class.isInstance(obj)){
			for (int index=0; index<priorityList.size(); index++){
				Priority priority = priorityList.get(index);
				if (priority.getPriorityID() == ((Integer)id).intValue())
					return index;
			}
		} 

		return 0;
	}

	private int getEnumIndex(int id, boolean isMessageType){

		if(isMessageType){
			for (int index=0; index<messageTypeList.size(); index++){
				MessageType messageType = messageTypeList.get(index);
				if (messageType.getId() == id)
					return index;
			}
		} else {
			for (int index=0; index<commentTypeList.size(); index++){
				CommentType commentType = commentTypeList.get(index);
				if (commentType.getId() == id)
					return index;
			}
		}
		return 0;
	}

	public void onClick$btn_save_top() throws InterruptedException {

		doBtnsSubmit();
	}

	public void onClick$btn_save_bottom() throws InterruptedException {

		doBtnsSubmit();
	}

	public void doBtnsSubmit() throws InterruptedException {

		boolean validated = validateCriteria();

		if (validated){

			tempCriteria = new TempCriteria();

			Comboitem countryItem = cbb_country_code.getSelectedItem();
			String countrySelected = (String) countryItem.getValue();

			Comboitem functionItem = cbb_function_code.getSelectedItem();
			String functionSelected = (String) functionItem.getValue();

			Comboitem userFieldItem = cbb_userfield.getSelectedItem();
			int userFieldSelected = (Integer) userFieldItem.getValue();

			Comboitem priorityItem = cbb_priority.getSelectedItem();
			int prioritySelected = (Integer) priorityItem.getValue();

			Comboitem passActionItem = cbb_passaction.getSelectedItem();
			int passActionSelected = (Integer) passActionItem.getValue();

			int passMessageTypeSelected = ((Integer) cbb_pass_messagetype.getSelectedItem().getValue()).intValue();

			Comboitem passLetterCodeItem = cbb_pass_lettercode.getSelectedItem();
			int passLetterCodeSelected = (Integer) passLetterCodeItem.getValue();

			int passCommentTypeSelected = ((Integer) cbb_pass_commenttype.getSelectedItem().getValue()).intValue();

			Comboitem failActionItem = cbb_failaction.getSelectedItem();
			int failActionItemSelected = (Integer) failActionItem.getValue();

			int failMessageTypeSelected = ((Integer) cbb_fail_messagetype.getSelectedItem().getValue()).intValue();

			Comboitem failLetterCodeItem = cbb_fail_lettercode.getSelectedItem();
			int failLetterCodeSelected = (Integer) failLetterCodeItem.getValue();

			int failCommentTypeSelected = ((Integer) cbb_fail_commenttype.getSelectedItem().getValue()).intValue();

			tempCriteria.setCountry_code(countrySelected);
			tempCriteria.setFunction_id(functionSelected);
			tempCriteria.setUser_field_id(userFieldSelected);
			tempCriteria.setPriority_id(prioritySelected);
			tempCriteria.setDescription(tbx_description.getValue());
			tempCriteria.setPass_action_id(passActionSelected);
			tempCriteria.setPass_messagetype_id(passMessageTypeSelected);
			tempCriteria.setPass_lettercode_id(passLetterCodeSelected);
			tempCriteria.setPass_commenttype_id(passCommentTypeSelected);
			tempCriteria.setPass_message(pass_message.getValue());
			tempCriteria.setPass_comment(pass_comment.getValue());
			tempCriteria.setFail_action_id(failActionItemSelected);
			tempCriteria.setFail_messagetype_id(failMessageTypeSelected);
			tempCriteria.setFail_lettercode_id(failLetterCodeSelected);
			tempCriteria.setFail_commenttype_id(failCommentTypeSelected);
			tempCriteria.setFail_message(fail_message.getValue());
			tempCriteria.setFail_comment(fail_comment.getValue());
			/* Common - START */
			tempCriteria.setCreatorUserID(securityUtils.getUserName());
			tempCriteria.setDateCreated(DateUtil.getCurrentDate());
			tempCriteria.setLastModifierUserID(securityUtils.getUserName());
			tempCriteria.setDateLastModified(DateUtil.getCurrentDate());
			/* Common - END */
			tempCriteria.setNew(true);
			tempCriteria.setActionID(ActionType.ADD.getId());

			tempCriteria.setTempEnhancedCriterionSet(new HashSet<TempEnhancedCriterion>(tempEnhancedCriterionList));

			for (int index=0; index < tempEnhancedCriterionList.size(); index++ ){
				TempEnhancedCriterion ecBean = tempEnhancedCriterionList.get(index);
				tempCriteria.getTempEnhancedCriterionSet().add(ecBean);
				tempEnhancedCriterion.setTempCriteria(tempCriteria);
				tempCriteria.addToTempEnhancedCriteriaSet(ecBean);
			}

			String newID = criteriaManager.saveTemp(tempCriteria);

			HistCriteria histCriteria = new HistCriteria(tempCriteria);
			histCriteria.setHistEnhancedCriteriaSet(new HashSet<HistEnhancedCriteria>(histEnhancedCriterionList));

			for (int index=0; index < histEnhancedCriterionList.size(); index++ ){
				HistEnhancedCriteria hecBean = histEnhancedCriterionList.get(index);
				histCriteria.getHistEnhancedCriteriaSet().add(hecBean);
				histEnhancedCriteria.setHistCriteria(histCriteria);
				histCriteria.addToHistEnhancedCriteriaSet(hecBean);
			}

			criteriaManager.saveHist(histCriteria);

			showMessageInfoSubmit(newID);
		}
	}

	private void showMessageInfoSubmit(final String newID) throws InterruptedException {
		Messagebox.show(getMsgPropVal(Commons.MSG_INFO_SUBMIT),
				getMsgPropVal(Commons.MSG_TITLE_SUBMIT), Messagebox.OK,
				Messagebox.EXCLAMATION, new EventListener() {
			public void onEvent(Event evt) throws InterruptedException {
				Executions.getCurrent().sendRedirect(new StringBuilder(globalUtils.getGlobalPropValue(Commons.URL_VIEW_CRITERIA))
				.append(Commons.URL_DELIMITER).append(Commons.URL_PARAM_TMP_ID)
				.append(Commons.EQUAL_SIGN).append(newID).toString());
			}
		});
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void onSelect$lbx_elements() throws InterruptedException {

		compare_value_grp.setSelectedItem(null);
		cbb_operator.setValue(null);

		tbx_enhancedvalue_character.setVisible(false);
		ibx_enhancedvalue_integer.setVisible(false);
		dbx_enhancedvalue_date.setVisible(false);

		tbx_enhancedvalue_character.setValue(null);
		ibx_enhancedvalue_integer.setValue(null);
		dbx_enhancedvalue_date.setValue(null);


		operatorList = operatorManager.getOperatorList();

		elemList = new ArrayList<Element>(elementList.size());

		for (Element elem : elementList) {
			elemList.add(new Element(elem));
		}

		//		Comboitem elementItem = cbb_element.getSelectedItem();
		//		String elementID = (String) elementItem.getValue();

		Set items = lbx_elements.getSelectedItems();
		List itemList = new ArrayList(items);
		ListModel model = lbx_elements.getModel();

		Element elem = new Element();
		if (itemList.size() > 0){
			Listitem selectedItem = (Listitem) itemList.get(0);
			elem = (Element) model.getElementAt(selectedItem.getIndex());
		}

		for (int index=0; index < elementList.size(); index++ ){
			Element elemBean = elementList.get(index);

			if (elemBean.getElement_id().equalsIgnoreCase(elem.getElement_id())){

				elemList.remove(index);

				if (elemBean.getElement_operator_eq() == null ){
					for (int index2=0; index2 < operatorList.size(); index2++ ){
						Operator operator = operatorList.get(index2);
						if (operator.getOperator_code().equalsIgnoreCase(Commons.OPERATOR_EQUALS)){
							operatorList.remove(index2);
						}
					}
				} 

				if (elemBean.getElement_operator_ne() == null ){
					for (int index2=0; index2 < operatorList.size(); index2++ ){
						Operator operator = operatorList.get(index2);
						if (operator.getOperator_code().equalsIgnoreCase(Commons.OPERATOR_NOT_EQUAL)){
							operatorList.remove(index2);
						}
					}
				} 

				if (elemBean.getElement_operator_gt() == null ){
					for (int index2=0; index2 < operatorList.size(); index2++ ){
						Operator operator = operatorList.get(index2);
						if (operator.getOperator_code().equalsIgnoreCase(Commons.OPERATOR_GREATER)){
							operatorList.remove(index2);
						}
					}
				} 

				if (elemBean.getElement_operator_ge() == null ){
					for (int index2=0; index2 < operatorList.size(); index2++ ){
						Operator operator = operatorList.get(index2);
						if (operator.getOperator_code().equalsIgnoreCase(Commons.OPERATOR_GREATER_OR_EQUAL)){
							operatorList.remove(index2);
						}
					}
				} 

				if (elemBean.getElement_operator_lt() == null ){
					for (int index2=0; index2 < operatorList.size(); index2++ ){
						Operator operator = operatorList.get(index2);
						if (operator.getOperator_code().equalsIgnoreCase(Commons.OPERATOR_LESSER)){
							operatorList.remove(index2);
						}
					}
				} 

				if (elemBean.getElement_operator_le() == null ){
					for (int index2=0; index2 < operatorList.size(); index2++ ){
						Operator operator = operatorList.get(index2);
						if (operator.getOperator_code().equalsIgnoreCase(Commons.OPERATOR_LESSER_OR_EQUAL)){
							operatorList.remove(index2);
						}
					}
				}

				if (elemBean.getElement_format_id() == 1) {
					this.setElement_type_id(1);
				} else if (elemBean.getElement_format_id() == 2) {
					this.setElement_type_id(2);
				} else if (elemBean.getElement_format_id() == 3) {
					this.setElement_type_id(3);
				} else if (elemBean.getElement_format_id() == 4) {
					this.setElement_type_id(4);
					DateFormats dateFormat = dateformatManager.findById(elemBean.getElement_dateformat_id());
					this.setDateFormat(dateFormat.getDateformat_desc());
					this.setDateFormat_id(dateFormat.getDateformat_id());
				} else if (elemBean.getElement_format_id() == 5) {
					this.setElement_type_id(5);
				}
			}
		}

		cbb_operator.setDisabled(false);
		cbb_operator.setModel(new ListModelList(operatorList));
		cbb_operator.setItemRenderer(new GlobalComboItemRenderer());

		lbx_element_forvalue.setModel(new ListModelList(elemList));
		lbx_element_forvalue.setItemRenderer(new GlobalSearchBoxListItemRenderer());

	}

	public void onSelect$cbb_operator() throws InterruptedException {

		Comboitem operatorItem = cbb_operator.getSelectedItem();
		String operatorID = (String) operatorItem.getValue();

		if (operatorID != null ){
			rad_element.setDisabled(false);
			rad_value.setDisabled(false);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean validateEnhancedCriteria() throws InterruptedException{

		if (lbx_elements.getSelectedItems() == null){
			bbox_element_1.setErrorMessage(globalUtils.getMessagePropValue(Commons.MESSAGE_WARN_ELEMENT));
			return false;
		}

		if (cbb_operator.getSelectedItem() == null){
			cbb_operator.setErrorMessage(globalUtils.getMessagePropValue(Commons.MESSAGE_WARN_SELECT_OPERATOR));
			return false;
		}

		if (compare_value_grp.getSelectedItem() == null){
			Messagebox.show(globalUtils.getMessagePropValue(Commons.MESSAGE_WARN_BLANK_OPERANT), Commons.MSG_WARNING, Messagebox.OK, Messagebox.EXCLAMATION);
			return false;
		} else {
			if (rad_value.isSelected()){
				if (this.getElement_type_id() == 1 || this.getElement_type_id() == 2){
					if (tbx_enhancedvalue_character.getValue() == null){
						tbx_enhancedvalue_character.setErrorMessage(globalUtils.getMessagePropValue(Commons.MESSAGE_WARN_BLANK_VALUE_CHAR));
						return false;
					} 
				} else if (this.getElement_type_id() == 3 || this.getElement_type_id() == 5){
					if (ibx_enhancedvalue_integer.getValue() == null){
						ibx_enhancedvalue_integer.setErrorMessage(globalUtils.getMessagePropValue(Commons.MESSAGE_WARN_BLANK_VALUE_INT));
						return false;
					} 
				} else if (this.getElement_type_id() == 4){
					if (dbx_enhancedvalue_date.getValue() == null){
						dbx_enhancedvalue_date.setErrorMessage(globalUtils.getMessagePropValue(Commons.MESSAGE_WARN_BLANK_VALUE_DATE));
						return false;
					}
				}
			} else {
				if (StringUtils.isNullOrEmpty(bbox_element_2.getValue())){
					bbox_element_2.setErrorMessage(globalUtils.getMessagePropValue(Commons.MESSAGE_WARN_ELEMENT));
					return false;
				}
			}
		}

		Comboitem operatorItem = cbb_operator.getSelectedItem();
		String operatorCode = (String) operatorItem.getValue();

		Set items = lbx_elements.getSelectedItems();
		List itemList = new ArrayList(items);
		ListModel model = lbx_elements.getModel();
		String elementID = null;

		if (itemList.size() > 0){
			Listitem selectedItem = (Listitem) itemList.get(0);
			Element elem = (Element) model.getElementAt(selectedItem.getIndex());
			elementID = elem.getElement_id();
		}

		Set itemsValue = lbx_element_forvalue.getSelectedItems();
		List itemValueList = new ArrayList(itemsValue);
		ListModel modelValue = lbx_element_forvalue.getModel();
		String elemIDValue = null;

		if (itemValueList.size() > 0){
			Listitem selectedItem = (Listitem) itemValueList.get(0);
			Element elem = (Element) modelValue.getElementAt(selectedItem.getIndex());
			elemIDValue = elem.getElement_id();
		}

		int errFlag = 0;
		for (int index=0; index < tempEnhancedCriterionList.size(); index++ ){
			TempEnhancedCriterion criteria = tempEnhancedCriterionList.get(index);

			if ((criteria.getElement_id().equalsIgnoreCase(elementID)) && criteria.getOperator_code().equalsIgnoreCase(operatorCode)){
				if (!StringUtils.isNullOrEmpty(tbx_enhancedvalue_character.getValue())){
					if (criteria.getEnhanced_value_character().equalsIgnoreCase(tbx_enhancedvalue_character.getValue())) {
						errFlag++;
						break;
					}
				} else if (dbx_enhancedvalue_date.getValue() != null){
					if (criteria.getEnhanced_value_date() != null){
						if (criteria.getEnhanced_value_date().compareTo(dbx_enhancedvalue_date.getValue())==0) {
							errFlag++;
							break;
						}
					}
				} else if (ibx_enhancedvalue_integer.getValue() != null){
					if (criteria.getEnhanced_value_integer() == ibx_enhancedvalue_integer.getValue().intValue()) {
						errFlag++;
						break;
					}
				} else if (lbx_element_forvalue.getSelectedItems() != null){
					if (!StringUtils.isNullOrEmpty(criteria.getEnhanced_value_element())){
						if (criteria.getEnhanced_value_element().equals(elemIDValue)) {
							errFlag++;
							break;
						}
					}
				}
			}
		}

		if (errFlag > 0){
			Messagebox.show(globalUtils.getMessagePropValue(Commons.MESSAGE_WARN_DUPLICATE_CRITERIA), Commons.MSG_WARNING, Messagebox.OK, Messagebox.EXCLAMATION);
			return false;
		}

		return true;
	}

	private boolean validateCriteria() throws InterruptedException{

		if (cbb_country_code.getSelectedItem() == null){
			cbb_country_code.setErrorMessage(globalUtils.getMessagePropValue(Commons.MESSAGE_WARN_COUNTRY));
			return false;
		}

		if (cbb_function_code.getSelectedItem() == null){
			cbb_function_code.setErrorMessage(globalUtils.getMessagePropValue(Commons.MESSAGE_WARN_FUNCTIONCODE));
			return false;
		}

		if (cbb_userfield.getSelectedItem() == null){
			cbb_userfield.setErrorMessage(globalUtils.getMessagePropValue(Commons.MESSAGE_WARN_USERFIELD));
			return false;
		}

		if (cbb_priority.getSelectedItem() == null){
			cbb_priority.setErrorMessage(globalUtils.getMessagePropValue(Commons.MESSAGE_WARN_PRIORITY));
			return false;
		}

		if (cbb_passaction.getSelectedItem() == null){
			cbb_passaction.setErrorMessage(globalUtils.getMessagePropValue(Commons.MESSAGE_WARN_ACTION));
			return false;
		}

		if (cbb_pass_messagetype.getSelectedItem() == null){
			cbb_pass_messagetype.setErrorMessage(globalUtils.getMessagePropValue(Commons.MESSAGE_WARN_MSGTYPE));
			return false;
		}

		if (cbb_pass_lettercode.getSelectedItem() == null){
			cbb_pass_lettercode.setErrorMessage(globalUtils.getMessagePropValue(Commons.MESSAGE_WARN_LETTERCODE));
			return false;
		}

		if (cbb_pass_commenttype.getSelectedItem() == null){
			cbb_pass_commenttype.setErrorMessage(globalUtils.getMessagePropValue(Commons.MESSAGE_WARN_CMNTTYPE));
			return false;
		}

		if (cbb_failaction.getSelectedItem() == null){
			cbb_failaction.setErrorMessage(globalUtils.getMessagePropValue(Commons.MESSAGE_WARN_ACTION));
			return false;
		}

		if (cbb_fail_messagetype.getSelectedItem() == null){
			cbb_fail_messagetype.setErrorMessage(globalUtils.getMessagePropValue(Commons.MESSAGE_WARN_MSGTYPE));
			return false;
		}

		if (cbb_fail_lettercode.getSelectedItem() == null){
			cbb_fail_lettercode.setErrorMessage(globalUtils.getMessagePropValue(Commons.MESSAGE_WARN_LETTERCODE));
			return false;
		}

		if (cbb_fail_commenttype.getSelectedItem() == null){
			cbb_fail_commenttype.setErrorMessage(globalUtils.getMessagePropValue(Commons.MESSAGE_WARN_CMNTTYPE));
			return false;
		}

		if (tempEnhancedCriterionList.size() == 0){
			Messagebox.show(globalUtils.getMessagePropValue(Commons.MESSAGE_WARN_NO_CRITERIA), Commons.MSG_WARNING, Messagebox.OK, Messagebox.EXCLAMATION);
			return false;
		}
		return true;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void onClick$btn_addcriterion() throws InterruptedException {

		if (validateEnhancedCriteria()){
			tempEnhancedCriterion = new TempEnhancedCriterion();
			histEnhancedCriteria = new HistEnhancedCriteria();

			String elementForValue = null;
			Set itemsValue = lbx_element_forvalue.getSelectedItems();
			List itemValueList = new ArrayList(itemsValue);
			ListModel modelValue = lbx_element_forvalue.getModel();

			if (itemValueList.size() > 0){
				Listitem selectedItem = (Listitem) itemValueList.get(0);
				Element elem = (Element) modelValue.getElementAt(selectedItem.getIndex());
				elementForValue = elem.getElement_id();
			}

			if (compare_value_grp.getSelectedItem() != null){
				if (rad_value.isSelected()){
					if (this.getElement_type_id() == 1 || this.getElement_type_id() == 2){
						if (tbx_enhancedvalue_character.getValue() != null){
							tempEnhancedCriterion.setEnhanced_value_character(tbx_enhancedvalue_character.getValue());
							histEnhancedCriteria.setEnhanced_value_character(tbx_enhancedvalue_character.getValue());
						}
					} else if (this.getElement_type_id() == 3 || this.getElement_type_id() == 5){
						if (ibx_enhancedvalue_integer.getValue() != null){
							tempEnhancedCriterion.setEnhanced_value_integer(ibx_enhancedvalue_integer.getValue());
							histEnhancedCriteria.setEnhanced_value_integer(ibx_enhancedvalue_integer.getValue());
						}
					} else if (this.getElement_type_id() == 4){
						if (dbx_enhancedvalue_date.getValue() != null){
							tempEnhancedCriterion.setDateFormat(this.dateFormat);
							tempEnhancedCriterion.setEnhanced_value_dateformat_id(this.dateFormat_id);
							tempEnhancedCriterion.setEnhanced_value_date(dbx_enhancedvalue_date.getValue());
							histEnhancedCriteria.setEnhanced_value_date(dbx_enhancedvalue_date.getValue());
						}
					}
				} else {
					if (elementForValue != null){
						tempEnhancedCriterion.setEnhanced_value_element(elementForValue);
						histEnhancedCriteria.setEnhanced_value_element(elementForValue);
					}
				}
			}

			String elementID = null;
			Set items = lbx_elements.getSelectedItems();
			List itemList = new ArrayList(items);
			ListModel model = lbx_elements.getModel();

			if (itemList.size() > 0){
				Listitem selectedItem = (Listitem) itemList.get(0);
				Element elem = (Element) model.getElementAt(selectedItem.getIndex());
				elementID = elem.getElement_id();
			}

			Comboitem operatorItem = cbb_operator.getSelectedItem();

			String operatorCode = (String) operatorItem.getValue();

			tempEnhancedCriterion.setElement_id(elementID);
			tempEnhancedCriterion.setOperator_code(operatorCode);
			histEnhancedCriteria.setElement_id(elementID);
			histEnhancedCriteria.setOperator_code(operatorCode);

			tempEnhancedCriterionList.add(tempEnhancedCriterion);
			histEnhancedCriterionList.add(histEnhancedCriteria);

			criteriaList.setModel(new ListModelList(tempEnhancedCriterionList));
			criteriaList.setItemRenderer(new EnhancedCriteriaListItemRenderer());


			bbox_element_1.setValue(null);
			cbb_operator.setValue(null);
			ibx_enhancedvalue_integer.setValue(null);
			tbx_enhancedvalue_character.setValue(null);
			dbx_enhancedvalue_date.setValue(null);
			bbox_element_2.setValue(null);

			StringBuilder finalCriteriaString = new StringBuilder();
			for (int index=0; index < tempEnhancedCriterionList.size(); index++ ){

				TempEnhancedCriterion etBean = tempEnhancedCriterionList.get(index);

				if ((index >= 1) && (index < tempEnhancedCriterionList.size())){
					finalCriteriaString.append(Commons.CONJUNCTION);
				}

				finalCriteriaString.append(etBean.getElement_id());
				finalCriteriaString.append(GlobalUtils.determineOperator(etBean.getOperator_code()));

				if (etBean.getEnhanced_value_element() != null){
					finalCriteriaString.append("'" + etBean.getEnhanced_value_element() + "'");
				} else if (etBean.getEnhanced_value_character() != null){
					finalCriteriaString.append("'" + etBean.getEnhanced_value_character() + "'");
				} else if (etBean.getEnhanced_value_date() != null) {
					SimpleDateFormat formatter= new SimpleDateFormat(this.getDateFormat());
					finalCriteriaString.append("'" + formatter.format(etBean.getEnhanced_value_date()) + "'");
				} else if (etBean.getEnhanced_value_integer() != null){
					finalCriteriaString.append("'" + etBean.getEnhanced_value_integer() + "'");
				}

				tbx_final_criteria.setValue(finalCriteriaString.toString());

				bbox_element_1.setValue(null);
				cbb_operator.setValue(null);
				ibx_enhancedvalue_integer.setValue(null);
				tbx_enhancedvalue_character.setValue(null);
				dbx_enhancedvalue_date.setValue(null);
				bbox_element_2.setValue(null);

				ibx_enhancedvalue_integer.setVisible(false);
				tbx_enhancedvalue_character.setVisible(false);
				bbox_element_2.setVisible(false);
				dbx_enhancedvalue_date.setVisible(false);

				cbb_operator.setDisabled(true);
				rad_element.setDisabled(true);
				rad_value.setDisabled(true);
				rad_element.setSelected(false);
				rad_value.setSelected(false);
				rad_value.setValue(null);
				rad_element.setValue(null);
			}
		}
	}

	public void onCheck$compare_value_grp() throws InterruptedException {

		if (rad_value.isSelected()){
			if (this.getElement_type_id() == 1 || this.getElement_type_id() == 2){
				tbx_enhancedvalue_character.setVisible(true);
				ibx_enhancedvalue_integer.setVisible(false);
				dbx_enhancedvalue_date.setVisible(false);
				bbox_element_2.setVisible(false);

				ibx_enhancedvalue_integer.setValue(null);
				dbx_enhancedvalue_date.setValue(null);
				bbox_element_2.setValue(null);
			} else if (this.getElement_type_id() == 3 || this.getElement_type_id() == 5){
				ibx_enhancedvalue_integer.setVisible(true);
				tbx_enhancedvalue_character.setVisible(false);
				dbx_enhancedvalue_date.setVisible(false);
				bbox_element_2.setVisible(false);

				tbx_enhancedvalue_character.setValue(null);
				dbx_enhancedvalue_date.setValue(null);
				bbox_element_2.setValue(null);
			} else if (this.getElement_type_id() == 4){
				dbx_enhancedvalue_date.setVisible(true);
				dbx_enhancedvalue_date.setFormat(this.getDateFormat());
				ibx_enhancedvalue_integer.setVisible(false);
				tbx_enhancedvalue_character.setVisible(false);
				bbox_element_2.setVisible(false);

				ibx_enhancedvalue_integer.setValue(null);
				tbx_enhancedvalue_character.setValue(null);
				bbox_element_2.setValue(null);
			}
		} else {
			bbox_element_2.setVisible(true);
			tbx_enhancedvalue_character.setVisible(false);
			ibx_enhancedvalue_integer.setVisible(false);
			dbx_enhancedvalue_date.setVisible(false);

			tbx_enhancedvalue_character.setValue(null);
			ibx_enhancedvalue_integer.setValue(null);
			dbx_enhancedvalue_date.setValue(null);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void onClick$btn_remove() throws InterruptedException {

		Set items = criteriaList.getSelectedItems();
		List itemList = new ArrayList(items);
		for (Iterator item = itemList.iterator(); item.hasNext();) {
			Listitem selectedItem = (Listitem)item.next();
			selectedItem.setSelected(false);
			criteriaList.removeChild(selectedItem);

			TempEnhancedCriterion ecTempBean = (TempEnhancedCriterion) selectedItem.getValue();
			tempEnhancedCriterionList.remove(ecTempBean);
		}

		bbox_element_1.setValue(null);
		cbb_operator.setValue(null);
		ibx_enhancedvalue_integer.setValue(null);
		tbx_enhancedvalue_character.setValue(null);

		StringBuilder finalCriteriaString = new StringBuilder();
		for (int index=0; index < tempEnhancedCriterionList.size(); index++ ){

			TempEnhancedCriterion etBean = tempEnhancedCriterionList.get(index);

			if ((index >= 1) && (index < tempEnhancedCriterionList.size())){
				finalCriteriaString.append(Commons.CONJUNCTION);
			}

			finalCriteriaString.append(etBean.getElement_id());
			finalCriteriaString.append(GlobalUtils.determineOperator(etBean.getOperator_code()));
			finalCriteriaString.append("'" + etBean.getEnhanced_value_integer() + "'");
		}

		tbx_final_criteria.setValue(finalCriteriaString.toString());
	}

	/** Enable or Disable Message Textbox**/
	public void onSelect$cbb_pass_messagetype() throws InterruptedException{
		Comboitem passMessageItem = cbb_pass_messagetype.getSelectedItem();
		int passMessageID = (Integer)passMessageItem.getValue();

		if( passMessageID == 4){
			pass_message.setDisabled(true);
		}else{
			pass_message.setDisabled(false);
		}
	}

	/** Enable or Disable Message Textbox**/
	public void onSelect$cbb_fail_messagetype() throws InterruptedException{
		Comboitem failMessageItem = cbb_fail_messagetype.getSelectedItem();
		int failMessageID = (Integer)failMessageItem.getValue();

		if(failMessageID == 4){
			fail_message.setDisabled(true);
		}else{
			fail_message.setDisabled(false);
		}
	}

	/** Enable or Disable **/
	public void onSelect$cbb_fail_commenttype() throws InterruptedException{
		Comboitem failCommentItem = cbb_fail_commenttype.getSelectedItem();
		int failCommentID = (Integer) failCommentItem.getValue();

		if(failCommentID == 3){
			fail_comment.setDisabled(true);
		}else{
			fail_comment.setDisabled(false);
		}
	}

	/** Enable or Disable **/
	public void onSelect$cbb_pass_commenttype() throws InterruptedException{
		Comboitem passCommentItem = cbb_pass_commenttype.getSelectedItem();
		int passCommentID = (Integer) passCommentItem.getValue();

		if(passCommentID == 3){
			pass_comment.setDisabled(true);
		}else{
			pass_comment.setDisabled(false);
		}
	}


	public TempCriteria getTempCriteria() {
		return tempCriteria;
	}

	public void setTempCriteria(TempCriteria tempCriteria) {
		this.tempCriteria = tempCriteria;
	}

	public List<Country> getCountryList() {
		return countryList;
	}

	public void setCountryList(List<Country> countryList) {
		this.countryList = countryList;
	}

	public List<Function> getFunctionList() {
		return functionList;
	}

	public void setFunctionList(List<Function> functionList) {
		this.functionList = functionList;
	}

	public List<Priority> getPriorityList() {
		return priorityList;
	}

	public void setPriorityList(List<Priority> priorityList) {
		this.priorityList = priorityList;
	}

	public List<UserField> getUserFieldList() {
		return userFieldList;
	}

	public void setUserFieldList(List<UserField> userFieldList) {
		this.userFieldList = userFieldList;
	}

	public List<MessageType> getMessageTypeList() {
		return messageTypeList;
	}

	public List<Action> getActionPassList() {
		return actionPassList;
	}

	public void setActionPassList(List<Action> actionPassList) {
		this.actionPassList = actionPassList;
	}

	public void setMessageTypeList(List<MessageType> messageTypeList) {
		this.messageTypeList = messageTypeList;
	}

	public List<Code> getLetterCodeList() {
		return letterCodeList;
	}

	public void setLetterCodeList(List<Code> letterCodeList) {
		this.letterCodeList = letterCodeList;
	}

	public List<CommentType> getCommentTypeList() {
		return commentTypeList;
	}

	public void setCommentTypeList(List<CommentType> commentTypeList) {
		this.commentTypeList = commentTypeList;
	}

	public List<Action> getActionFailList() {
		return actionFailList;
	}

	public void setActionFailList(List<Action> actionFailList) {
		this.actionFailList = actionFailList;
	}

	public List<Element> getElementList() {
		return elementList;
	}

	public void setElementList(List<Element> elementList) {
		this.elementList = elementList;
	}

	public List<Operator> getOperatorList() {
		return operatorList;
	}

	public void setOperatorList(List<Operator> operatorList) {
		this.operatorList = operatorList;
	}

	public List<TempEnhancedCriterion> getTempEnhancedCriterionList() {
		return tempEnhancedCriterionList;
	}

	public void setTempEnhancedCriterionList(
			List<TempEnhancedCriterion> tempEnhancedCriterionList) {
		this.tempEnhancedCriterionList = tempEnhancedCriterionList;
	}

	public String getEnhancedCriteriaElementID() {
		return enhancedCriteriaElementID;
	}

	public void setEnhancedCriteriaElementID(String enhancedCriteriaElementID) {
		this.enhancedCriteriaElementID = enhancedCriteriaElementID;
	}

	public String getEnhancedCriteriaOperatorCode() {
		return enhancedCriteriaOperatorCode;
	}

	public void setEnhancedCriteriaOperatorCode(String enhancedCriteriaOperatorCode) {
		this.enhancedCriteriaOperatorCode = enhancedCriteriaOperatorCode;
	}

	public String getEnhancedCriteriaValue() {
		return enhancedCriteriaValue;
	}

	public void setEnhancedCriteriaValue(String enhancedCriteriaValue) {
		this.enhancedCriteriaValue = enhancedCriteriaValue;
	}

	public TempEnhancedCriterion getEnhancedCriterionTempBean() {
		return tempEnhancedCriterion;
	}

	public void setEnhancedCriterionTempBean(
			TempEnhancedCriterion tempEnhancedCriterion) {
		this.tempEnhancedCriterion = tempEnhancedCriterion;
	}

	public Listbox getCriteriaList() {
		return criteriaList;
	}

	public void setCriteriaList(Listbox criteriaList) {
		this.criteriaList = criteriaList;
	}

	public List<Element> getElemList() {
		return elemList;
	}

	public void setElemList(List<Element> elemList) {
		this.elemList = elemList;
	}

	public int getElement_type_id() {
		return element_type_id;
	}

	public void setElement_type_id(int element_type_id) {
		this.element_type_id = element_type_id;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}


	public HistEnhancedCriteria getHistEnhancedCriteria() {
		return histEnhancedCriteria;
	}


	public void setHistEnhancedCriteria(HistEnhancedCriteria histEnhancedCriteria) {
		this.histEnhancedCriteria = histEnhancedCriteria;
	}


	public List<HistEnhancedCriteria> getHistEnhancedCriterionList() {
		return histEnhancedCriterionList;
	}


	public void setHistEnhancedCriterionList(
			List<HistEnhancedCriteria> histEnhancedCriterionList) {
		this.histEnhancedCriterionList = histEnhancedCriterionList;
	}

	public int getDateFormat_id() {
		return dateFormat_id;
	}

	public void setDateFormat_id(int dateFormat_id) {
		this.dateFormat_id = dateFormat_id;
	}

	private String getMsgPropVal(String key) {
		return globalUtils.getMessagePropValue(key);
	}
}
