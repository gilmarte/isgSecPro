package com.isg.ifrend.view.control;

/* 
 * Author: Edward Ruiz
 *  
 *  
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

import com.isg.ifrend.model.bean.Action;
import com.isg.ifrend.model.bean.ActionType;
import com.isg.ifrend.model.bean.Code;
import com.isg.ifrend.model.bean.CodeType;
import com.isg.ifrend.model.bean.CommentType;
import com.isg.ifrend.model.bean.Criteria;
import com.isg.ifrend.model.bean.DateFormats;
import com.isg.ifrend.model.bean.Element;
import com.isg.ifrend.model.bean.EnhancedCriterion;
import com.isg.ifrend.model.bean.HistEnhancedCriteria;
import com.isg.ifrend.model.bean.MessageType;
import com.isg.ifrend.model.bean.Operator;
import com.isg.ifrend.model.bean.PriorityType;
import com.isg.ifrend.model.bean.StatusType;
import com.isg.ifrend.model.bean.TempCriteria;
import com.isg.ifrend.model.bean.TempEnhancedCriterion;
import com.isg.ifrend.model.bean.TmpCodeType;
import com.isg.ifrend.model.bean.TmpElement;
import com.isg.ifrend.security.utils.SecurityUtils;
import com.isg.ifrend.service.ActionManager;
import com.isg.ifrend.service.CodeManager;
import com.isg.ifrend.service.CountryManager;
import com.isg.ifrend.service.DateFormatsManager;
import com.isg.ifrend.service.FunctionManager;
import com.isg.ifrend.service.GlobalManager;
import com.isg.ifrend.service.OperatorManager;
import com.isg.ifrend.service.PriorityManager;
import com.isg.ifrend.service.SelectedManager;
import com.isg.ifrend.service.ServiceLocator;
import com.isg.ifrend.service.TempUserManager;
import com.isg.ifrend.service.UserFieldManager;
import com.isg.ifrend.service.UserManager;
import com.isg.ifrend.utils.Commons;
import com.isg.ifrend.utils.DateUtil;
import com.isg.ifrend.utils.GlobalUtils;
import com.isg.ifrend.view.control.renderer.EnhancedCriteriaListItemRenderer;
import com.isg.ifrend.view.control.renderer.GlobalComboItemRenderer;
import com.isg.ifrend.view.control.renderer.GlobalSearchBoxListItemRenderer;

/**
 * @author edward.ruiz
 *
 */
public class CriteriaViewControl extends GenericForwardComposer {

	private static final long serialVersionUID = 8929385568066157671L;

	private AnnotateDataBinder binder;
	/*private Label idLbl;*/
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

	private Textbox tbx_description;
	private Textbox tbx_pass_message;
	private Textbox tbx_pass_comment;
	private Textbox tbx_fail_message;
	private Textbox tbx_fail_comment;

	private Label view_cbb_country_code;
	private Label view_cbb_function_code;
	private Label view_cbb_userfield;
	private Label view_cbb_priority;
	private Label view_cbb_passaction;
	private Label view_cbb_pass_messagetype;
	private Label view_cbb_pass_lettercode;
	private Label view_cbb_pass_commenttype;
	private Label view_cbb_failaction;
	private Label view_cbb_fail_lettercode;
	private Label view_cbb_fail_commenttype;
	private Label view_cbb_fail_messagetype;

	private Label statusLbl;
	private Label actionLbl;
	private Label creatorLbl;
	private Label dateCreatedLbl;
	private Label approverLbl;
	private Label dateApprovedLbl;
	private Label modifierLbl;
	private Label dateModifiedLbl;
	private Label idLbl;


	private Label view_tbx_description;
	private Label view_pass_comment;
	private Label view_pass_message;
	private Label view_fail_message;
	private Label view_fail_comment;

	private Button btn_savecriterion;
	private Button btn_savecriterion2;

	private Button btn_updatecriterion;
	private Button btn_updatecriterion2;
	private Button btn_deletecriterion;
	private Button btn_deletecriterion2;
	private Button btn_approve;
	private Button btn_approve2;
	private Button btn_reject;
	private Button btn_reject2;
	private Button btn_resetcriterion2;
	private Button btn_resetcriterion;
	private Button btn_ok;
	private Button btn_ok2;
	private Button btn_cancel;
	private Button btn_cancel2;
	private Button btn_edit;
	private Button btn_edit2;
	private Button btn_closecriterion;
	private Button btn_closecriterion2;

	private ListModelList lml_countryList;
	private ListModelList lml_functionList;
	private ListModelList lml_userFieldList;
	private ListModelList lml_priorityList;
	private ListModelList lml_actionPassList;
	private ListModelList lml_actionFailList;
	private ListModelList lml_messageTypeList;
	private ListModelList lml_letterCodeList;
	private ListModelList lml_commentTypeList;

	private String criteriaID;
	private TempCriteria criteria = new TempCriteria();

	private CountryManager countryManager = ServiceLocator.getCountryManager();
	private FunctionManager functionManager = ServiceLocator.getFunctionManager();
	private UserFieldManager userFieldManager = ServiceLocator.getUserFieldManager();
	private PriorityManager priorityManager = ServiceLocator.getPriorityManager();
	private ActionManager actionManager = ServiceLocator.getActionManager();
	private CodeManager codeManager = ServiceLocator.getCodeManager();
	private GlobalManager<Criteria, TempCriteria> criteriaManager = ServiceLocator.getCriteriaManager2();
	private GlobalManager<Element, TmpElement> elementManager = ServiceLocator.getElementManager();
	private GlobalManager<CodeType, TmpCodeType> codeTypeManager = ServiceLocator.getCodeTypeManager();
	private OperatorManager operatorManager = ServiceLocator.getOperatorManager();
	private GlobalUtils globalUtils = GlobalUtils.getGlobalUtilsInstance();
	private SecurityUtils securityUtils = SecurityUtils.getSecurityUtilsInstance();
	private SelectedManager selectedManager = ServiceLocator.getSelectedManager();

	/** EnhancedCriterion **/
	private Row row_element;
	private Bandbox bbox_element_1;
	private Listbox lbx_elements;
	private Combobox cbb_operator;
	private Radiogroup compare_value_grp;
	private Radio rad_value;
	private Radio rad_element;
	private Intbox ibx_enhancedvalue_integer;
	private Textbox tbx_enhancedvalue_character;
	private Datebox dbx_enhancedvalue_date;
	private Bandbox bbox_element_2;
	private Listbox lbx_element_forvalue;
	private Listbox enhancedCriteriaList;
	private Textbox tbx_final_criteria;

	private Label asterisk_priority;
	private Label asterisk_passaction;
	private Label asterisk_passMessageType;
	private Label asterisk_passlettercode;
	private Label asterisk_passcommenttype;
	private Label asterisk_failaction;
	private Label asterisk_failmessagetype;
	private Label asterisk_faillettercode;
	private Label asterisk_failcommenttype;
	private Label asterisk_cbboperator;
	private Label asterisk_operant;
	private Label asterisk_element;

	private ListModelList lml_operatorList;
	private List<Element> elementsList;
	private List<Code> letterCodeList;
	private List<Operator> operatorList;
	private List<Action> actionPassList;
	private List<Action> actionFailList;

	private int element_type_id;
	private TempEnhancedCriterion tempenhancedcriterion;
	private HistEnhancedCriteria histenhancedcriteria;
	private List<Element> elemList;
	private List<TempEnhancedCriterion> tempEnhancedCriterionList = new ArrayList<TempEnhancedCriterion>();
	private List<HistEnhancedCriteria> histenhancedcriterionList = new ArrayList<HistEnhancedCriteria>();

	private int dateFormat_id;
	private String dateFormat;
	private DateFormatsManager dateformatManager = ServiceLocator.getDateFormatsManager();

	private UserManager userManager = ServiceLocator.getUserManager();
	private TempUserManager tempUserManager = ServiceLocator.getTempUserManager();

	private String previous_page = "";


	@SuppressWarnings("unchecked")
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		btn_resetcriterion2.setVisible(false);
		btn_resetcriterion.setVisible(false);

		cbb_operator.setDisabled(true);
		ibx_enhancedvalue_integer.setVisible(false);
		tbx_enhancedvalue_character.setVisible(false);
		bbox_element_2.setVisible(false);
		dbx_enhancedvalue_date.setVisible(false);

		elementsList = elementManager.viewActiveList();

		lbx_elements.setModel(new ListModelList(elementsList));
		lbx_elements.setItemRenderer(new GlobalSearchBoxListItemRenderer());

		lbx_element_forvalue.setModel(new ListModelList(elementsList));
		lbx_element_forvalue.setItemRenderer(new GlobalSearchBoxListItemRenderer());

		rad_value.setDisabled(true);
		rad_element.setDisabled(true);

		criteriaID = Executions.getCurrent().getParameter(Commons.URL_PARAM_TMP_ID);
		if (StringUtils.isNotEmpty(criteriaID)) {
			criteria = criteriaManager.viewPendingDetails(criteriaID);
		} else {
			criteriaID = Executions.getCurrent().getParameter(Commons.URL_PARAM_MST_ID);
			criteria = new TempCriteria(criteriaManager.viewActiveDetails(criteriaID));
		}
		setCommonDetails();
		actionPassList = GlobalUtils.removeActionItemList(0);
		actionFailList = GlobalUtils.removeActionItemList(1);

		view_cbb_userfield.setValue(userFieldManager.findById(criteria.getUser_field_id()).getUserFieldDesc());
		view_cbb_priority.setValue(priorityManager.findById(criteria.getPriority_id()).getPriorityDesc());
		view_cbb_passaction.setValue(actionManager.findById(criteria.getPass_action_id()).getAction_desc());
		view_cbb_pass_messagetype.setValue(MessageType.getDesc(criteria.getPass_messagetype_id()));
		view_cbb_pass_commenttype.setValue(CommentType.getDesc(criteria.getPass_commenttype_id()));
		view_cbb_failaction.setValue(actionManager.findById(criteria.getFail_action_id()).getAction_desc());
		view_cbb_fail_messagetype.setValue(MessageType.getDesc(criteria.getFail_messagetype_id()));
		view_cbb_fail_commenttype.setValue(CommentType.getDesc(criteria.getFail_commenttype_id()));


		/** CodeType letterCode **/
		CodeType letterCode = new CodeType();
		letterCode.setDesc(Commons.CODETYPE_LETTER_CODES);
		letterCodeList = new ListModelList(ServiceLocator.getCodeTypeManager().searchActiveList(letterCode).get(0).getCodeSet());

		view_cbb_fail_lettercode.setValue(this.getCodeDesc(criteria.getFail_lettercode_id()));
		view_cbb_pass_lettercode.setValue(this.getCodeDesc(criteria.getPass_lettercode_id()));

		manageButtons();

		self.setAttribute(self.getId(), this);

		List<EnhancedCriterion> ecList = selectedManager.findChildren(new EnhancedCriterion(), criteria.getCriteria_id());
		for(int index=0; index<ecList.size(); index++){
			EnhancedCriterion ecBean = ecList.get(index);
			tempenhancedcriterion = new TempEnhancedCriterion();
			tempenhancedcriterion.setElement_id(ecBean.getElement_id());
			tempenhancedcriterion.setOperator_code(ecBean.getOperator_code());
			tempenhancedcriterion.setEnhanced_value_character(ecBean.getEnhanced_value_character());
			tempenhancedcriterion.setEnhanced_value_integer(ecBean.getEnhanced_value_integer());
			tempenhancedcriterion.setEnhanced_value_dateformat_id(ecBean.getEnhanced_value_dateformat_id());

			if(tempenhancedcriterion.getEnhanced_value_dateformat_id()!= null){
				DateFormats dateFormat = dateformatManager.findById(tempenhancedcriterion.getEnhanced_value_dateformat_id());
				tempenhancedcriterion.setDateFormat(dateFormat.getDateformat_desc());
			}
			tempenhancedcriterion.setEnhanced_value_date(ecBean.getEnhanced_value_date());
			tempenhancedcriterion.setEnhanced_value_element(ecBean.getEnhanced_value_element());
			tempEnhancedCriterionList.add(tempenhancedcriterion);
		}

		enhancedCriteriaList.setModel(new ListModelList(tempEnhancedCriterionList));
		enhancedCriteriaList.setItemRenderer(new EnhancedCriteriaListItemRenderer());

		StringBuilder finalCriteriaString = new StringBuilder();
		for(int index=0; index < tempEnhancedCriterionList.size(); index++){
			TempEnhancedCriterion etBean = tempEnhancedCriterionList.get(index);

			if((index >= 1) && (index < tempEnhancedCriterionList.size())){
				finalCriteriaString.append(Commons.CONJUNCTION);
			}

			finalCriteriaString.append(etBean.getElement_id());
			finalCriteriaString.append(GlobalUtils.determineOperator(etBean.getOperator_code()));

			if(etBean.getEnhanced_value_element()!=null){
				finalCriteriaString.append("'" + etBean.getEnhanced_value_element() + "'");
			} else if(etBean.getEnhanced_value_character()!=null){
				finalCriteriaString.append("'" + etBean.getEnhanced_value_character() + "'");
			} else if(etBean.getEnhanced_value_date()!= null){
				SimpleDateFormat formatter = new SimpleDateFormat(etBean.getDateFormat());
				finalCriteriaString.append("'" + formatter.format(etBean.getEnhanced_value_date()) + "'");
			} else if(etBean.getEnhanced_value_integer()!= null){
				finalCriteriaString.append("'" + etBean.getEnhanced_value_integer() + "'");
			}
			tbx_final_criteria.setValue(finalCriteriaString.toString());
		}

		lml_countryList = new ListModelList(countryManager.getCountryList());
		lml_functionList = new ListModelList(functionManager.getFunctionList());
		lml_userFieldList = new ListModelList(userFieldManager.getUserFieldList());
		lml_priorityList = new ListModelList(priorityManager.getPriorityList());
		lml_actionPassList = new ListModelList(GlobalUtils.removeActionItemList(0));
		lml_actionFailList = new ListModelList(GlobalUtils.removeActionItemList(1));
		lml_messageTypeList = new ListModelList(new ArrayList<MessageType>(Arrays.asList(MessageType.values())));

		letterCode.setDesc(Commons.CODETYPE_LETTER_CODES);
		lml_letterCodeList = new ListModelList(ServiceLocator.getCodeTypeManager().searchActiveList(letterCode).get(0).getCodeSet());
		lml_commentTypeList = new ListModelList(new ArrayList<CommentType>(Arrays.asList(CommentType.values())));

		operatorList = operatorManager.getOperatorList();
		lml_operatorList = new ListModelList(operatorList);

		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
	}

	private String getCodeDesc(int id){
		for (int index=0; index<letterCodeList.size(); index++){
			Code code = letterCodeList.get(index);
			if (code.getCodeID() == id)
				return code.getCodeDesc();
		}
		return null;
	}

	public int determineActionIndexforreset(int id, boolean isPassAction) {
		// TODO Auto-generated method stub
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

	/** Get Code in List **/
	private int getCodeInList(int id){

		for (int index=0; index<letterCodeList.size(); index++){
			Code code = letterCodeList.get(index);
			if (code.getCodeID() == id)
				return index;
		}

		return 0;
	}

	/** Reset Criterion **/
	public void onClick$btn_resetcriterion() throws InterruptedException{
		view_cbb_country_code.setVisible(true);
		cbb_country_code.setVisible(false);
		cbb_country_code.setDisabled(true);

		view_cbb_function_code.setVisible(true);
		cbb_function_code.setVisible(false);
		cbb_function_code.setDisabled(true);

		view_cbb_userfield.setVisible(true);
		cbb_userfield.setVisible(false);
		cbb_userfield.setDisabled(true);

		view_cbb_priority.setVisible(false);
		cbb_priority.setVisible(true);
		cbb_priority.setValue(PriorityType.getDesc(criteria.getPriority_id()));
		cbb_priority.setDisabled(false);

		view_cbb_passaction.setVisible(false);
		cbb_passaction.setVisible(true);
		cbb_passaction.setValue(ActionType.getDesc(criteria.getPass_action_id()));
		cbb_passaction.setDisabled(false);

		view_cbb_pass_messagetype.setVisible(false);
		cbb_pass_messagetype.setVisible(true);
		cbb_pass_messagetype.setValue(MessageType.getDesc(criteria.getPass_messagetype_id()));
		cbb_pass_messagetype.setDisabled(false);

		view_cbb_pass_lettercode.setVisible(false);
		cbb_pass_lettercode.setVisible(true);
		cbb_pass_lettercode.setSelectedIndex(this.getCodeInList(criteria.getPass_lettercode_id()));
		cbb_pass_lettercode.setDisabled(false);

		view_cbb_pass_commenttype.setVisible(false);
		cbb_pass_commenttype.setVisible(true);
		cbb_pass_commenttype.setValue(CommentType.getDesc(criteria.getPass_commenttype_id()));
		cbb_pass_commenttype.setDisabled(false);

		view_cbb_failaction.setVisible(false);
		cbb_failaction.setVisible(true);
		cbb_failaction.setSelectedIndex(this.determineActionIndexforreset(criteria.getFail_action_id(), true));
		cbb_passaction.setSelectedIndex(this.determineActionIndexforreset(criteria.getPass_action_id(), true));
		cbb_failaction.setDisabled(false);

		view_cbb_fail_messagetype.setVisible(false);
		cbb_fail_messagetype.setVisible(true);
		cbb_fail_messagetype.setValue(MessageType.getDesc(criteria.getFail_messagetype_id()));
		cbb_fail_messagetype.setDisabled(false);

		view_cbb_fail_lettercode.setVisible(false);
		cbb_fail_lettercode.setVisible(true);
		cbb_fail_lettercode.setSelectedIndex(this.getCodeInList(criteria.getFail_lettercode_id()));
		cbb_fail_lettercode.setDisabled(false);

		view_cbb_fail_commenttype.setVisible(false);
		cbb_fail_commenttype.setVisible(true);
		cbb_fail_commenttype.setValue(CommentType.getDesc(criteria.getFail_commenttype_id()));
		cbb_fail_commenttype.setDisabled(false);

		btn_savecriterion.setVisible(true);
		btn_savecriterion2.setVisible(true);

		btn_deletecriterion2.setVisible(false);
		btn_deletecriterion.setVisible(false);

		tbx_description.setVisible(true);
		tbx_description.setDisabled(false);
		tbx_description.setValue(criteria.getDescription());
		view_tbx_description.setVisible(false);

		view_fail_message.setVisible(false);
		tbx_fail_message.setVisible(true);
		tbx_fail_message.setValue(criteria.getFail_message());
		tbx_fail_message.setDisabled(false);

		view_fail_comment.setVisible(false);
		tbx_fail_comment.setVisible(true);
		tbx_fail_comment.setValue(criteria.getFail_comment());
		tbx_fail_comment.setDisabled(false);

		view_pass_message.setVisible(false);
		tbx_pass_message.setVisible(true);
		tbx_pass_message.setValue(criteria.getPass_message());
		tbx_pass_message.setDisabled(false);

		view_pass_comment.setVisible(false);
		tbx_pass_comment.setVisible(true);
		tbx_pass_comment.setValue(criteria.getPass_comment());
		tbx_pass_comment.setDisabled(false);

		btn_updatecriterion.setVisible(false);
		btn_updatecriterion2.setVisible(false);

		row_element.setVisible(true);
		ibx_enhancedvalue_integer.setVisible(true);
		tbx_enhancedvalue_character.setVisible(true);
		bbox_element_2.setVisible(true);
		dbx_enhancedvalue_date.setVisible(true);
		cbb_operator.setDisabled(false);

		asterisk_priority.setVisible(true);
		asterisk_passaction.setVisible(true);
		asterisk_passMessageType.setVisible(true);
		asterisk_passlettercode.setVisible(true);
		asterisk_passcommenttype.setVisible(true);
		asterisk_failaction.setVisible(true);
		asterisk_failmessagetype.setVisible(true);
		asterisk_faillettercode.setVisible(true);
		asterisk_failcommenttype.setVisible(true);
		asterisk_cbboperator.setVisible(true);
		asterisk_operant.setVisible(true);
		asterisk_element.setVisible(true);



		btn_resetcriterion2.setVisible(true);
		btn_resetcriterion.setVisible(true);
	}

	/** Reset Criterion **/
	public void onClick$btn_resetcriterion2() throws InterruptedException{
		onClick$btn_resetcriterion();
	} 

	/** Set Common Details **/
	private void setCommonDetails() {
		idLbl.setValue("CRIT" + criteria.getId());

		statusLbl.setValue(StatusType.getDesc(criteria.getStatusID()));
		actionLbl.setValue(ActionType.getDesc(criteria.getActionID()));

		creatorLbl.setValue(criteria.getCreatorUserID());
		dateCreatedLbl.setValue(DateUtil.format(criteria.getDateCreated()));

		modifierLbl.setValue(criteria.getLastModifierUserID());
		dateModifiedLbl.setValue(DateUtil.format(criteria
				.getDateLastModified()));


		if(StatusType.ACTIVE.getId() == criteria.getStatusID()) {
			approverLbl.setValue(criteria.getApproverUserID());
			dateApprovedLbl.setValue(DateUtil.format(criteria
					.getDateApproved()));
		}
	}
	
	/** button ok functionality **/
	public void onClick$btn_ok2() throws InterruptedException {
		if(securityUtils.hasMakerRole(globalUtils.getGlobalPropValue(Commons.URL_SEARCH_GLOBAL))){
		   if(criteria.getLastModifierUserID().equals(securityUtils.getUserName())){
			Executions.sendRedirect(globalUtils.getGlobalPropValue(Commons.URL_SEARCH_GLOBAL));
		   }
		}
		
		if(securityUtils.hasCheckerRole(globalUtils.getGlobalPropValue(Commons.URL_SEARCH_GLOBAL))){
			if(criteria.getLastModifierUserID().equals(securityUtils.getUserName())){
				Executions.sendRedirect(globalUtils.getGlobalPropValue(Commons.URL_SEARCH_GLOBAL));
			  }
		}
		
		if(securityUtils.hasMakerRole(globalUtils.getGlobalPropValue(Commons.URL_AUTHORIZE_GLOBAL))){
			if(criteria.getLastModifierUserID().equals(securityUtils.getUserName())){
				Executions.sendRedirect(globalUtils.getGlobalPropValue(Commons.URL_AUTHORIZE_GLOBAL));
			  }
		}
		
		if(securityUtils.hasCheckerRole(globalUtils.getGlobalPropValue(Commons.URL_AUTHORIZE_GLOBAL))){
			if(criteria.getLastModifierUserID().equals(securityUtils.getUserName())){
				Executions.sendRedirect(globalUtils.getGlobalPropValue(Commons.URL_AUTHORIZE_GLOBAL));
			  }
		}
		
	}
	
	
	
	public void onClick$btn_ok() throws InterruptedException {
		onClick$btn_ok2();
	}

	/** Manage button visibility **/
	public void manageButtons(){

		/** Start of Search Part **/

		/** Search View - Maker role **/
		if(securityUtils.hasMakerRole(globalUtils.getGlobalPropValue(Commons.URL_SEARCH_GLOBAL))){
			if(criteria.getLastModifierUserID().equals(securityUtils.getUserName())){
				btn_updatecriterion.setVisible(true);
				btn_deletecriterion.setVisible(true);
				btn_ok.setVisible(true);

				btn_updatecriterion2.setVisible(true);
				btn_deletecriterion2.setVisible(true);
				btn_ok2.setVisible(true);

			}else{
				btn_updatecriterion.setVisible(false);
				btn_deletecriterion.setVisible(false);
				btn_ok.setVisible(true);

				btn_updatecriterion2.setVisible(false);
				btn_deletecriterion2.setVisible(false);
				btn_ok2.setVisible(true);
			}

			if(criteria.getStatusID()== StatusType.PENDING.getId()){
				if((criteria.getActionID() == ActionType.DELETE.getId())){
					btn_cancel.setVisible(true);
					btn_cancel2.setVisible(true);
					btn_ok.setVisible(true);
					btn_ok2.setVisible(true);
				}else{
					btn_cancel.setVisible(false);
					btn_cancel2.setVisible(false);
					btn_ok.setVisible(false);
					btn_ok2.setVisible(false);
				}

				if((criteria.getActionID() == ActionType.UPDATE.getId())){
					btn_cancel.setVisible(true);
					btn_cancel2.setVisible(true);
					btn_ok.setVisible(true);
					btn_ok2.setVisible(true);
					btn_edit.setVisible(true);
					btn_edit2.setVisible(true);
				}else{
					btn_cancel.setVisible(false);
					btn_cancel2.setVisible(false);
					btn_ok.setVisible(false);
					btn_ok2.setVisible(false);
					btn_edit.setVisible(false);
					btn_edit2.setVisible(false);
				}
			}
		}

		/** Search View - Checker Role or Other**/
		if(securityUtils.hasCheckerRole(globalUtils.getGlobalPropValue(Commons.URL_SEARCH_GLOBAL))){
			if(criteria.getLastModifierUserID().equals(securityUtils.getUserName())){
				btn_ok.setVisible(false);
				btn_ok2.setVisible(false);
			}else{
				btn_ok.setVisible(true);
				btn_ok2.setVisible(true);
			}
			
			if(criteria.getStatusID() == StatusType.ACTIVE.getId()){
				btn_updatecriterion.setVisible(true);
				btn_deletecriterion.setVisible(true);
				btn_ok.setVisible(true);

				btn_updatecriterion2.setVisible(true);
				btn_deletecriterion2.setVisible(true);
				btn_ok2.setVisible(true);

			}else{
				btn_updatecriterion.setVisible(false);
				btn_deletecriterion.setVisible(false);
				btn_ok.setVisible(false);

				btn_updatecriterion2.setVisible(false);
				btn_deletecriterion2.setVisible(false);
				btn_ok2.setVisible(false);
			}

		}

		/** Start of Authorize Part **/

		/** View Authorize Checker Role **/ 
		if(securityUtils.hasCheckerRole(globalUtils.getGlobalPropValue(Commons.URL_AUTHORIZE_GLOBAL))){
			if(criteria.getLastModifierUserID().equals(securityUtils.getUserName())){
				btn_approve.setVisible(false);
				btn_reject.setVisible(false);
				btn_ok.setVisible(false);

				btn_approve2.setVisible(false);
				btn_reject2.setVisible(false);
				btn_ok2.setVisible(false);
			}
			else{
				btn_approve.setVisible(true);
				btn_reject.setVisible(true);
				btn_ok.setVisible(true);

				btn_approve2.setVisible(true);
				btn_reject2.setVisible(true);
				btn_ok2.setVisible(true);
			}
		}

		/** View Authorize Maker Role **/
		if(securityUtils.hasMakerRole(globalUtils.getGlobalPropValue(Commons.URL_AUTHORIZE_GLOBAL))){
			if(criteria.getLastModifierUserID().equals(securityUtils.getUserName())){

				btn_edit.setVisible(true);
				btn_cancel.setVisible(true);
				btn_ok.setVisible(true);

				btn_edit2.setVisible(true);
				btn_cancel2.setVisible(true);
				btn_ok2.setVisible(true);

			}else{

				btn_edit.setVisible(false);
				btn_cancel.setVisible(false);
				btn_ok.setVisible(false);

				btn_edit2.setVisible(false);
				btn_cancel2.setVisible(false);
				btn_ok2.setVisible(false);
			}
		}

		/** End of Authorize Part **/



	}



	/** EDIT BUTTON FUNCTIONALITY **/
	public void onClick$btn_edit() throws InterruptedException{
		onClick$btn_updatecriterion();
	}

	/** EDIT BUTTON2 FUNCTIONALITY **/
	public void onClick$btn_edit2() throws InterruptedException{
		onClick$btn_edit();
	}


	/** CLOSE BUTTON FUNCTIONALITY 2 **/
	public void onClick$btn_closecriterion2() throws InterruptedException {
		onClick$btn_closecriterion();
	}

	/** MESSAGEBOX CLOSE **/
	public void onClick$btn_closecriterion() throws InterruptedException {
		Executions.sendRedirect(globalUtils.getGlobalPropValue(Commons.URL_SEARCH_GLOBAL));
	}

	/**  BUTTON2 **/
	public void onClick$btn_approve2() throws InterruptedException {
		onClick$btn_approve();
	}

	/** APPROVE BUTTON1 **/
	public void onClick$btn_approve() throws InterruptedException {
		Messagebox.show(getMsgPropVal(Commons.MSG_ASK_APPROVE),
				getMsgPropVal(Commons.MSG_TITLE_APPROVE), Messagebox.OK
				| Messagebox.CANCEL, Messagebox.EXCLAMATION,
				new EventListener() {
			public void onEvent(Event evt) throws InterruptedException,
			ParseException {
				if (Messagebox.ON_OK.equals(evt.getName())) {
					approveCriteria();
				}
			}
		});
	}

	/** APPROVE CRITERIA FUNCTIONALITY **/
	public void approveCriteria() throws InterruptedException {
		setApprover();
		criteriaManager.approveRequest(criteria);
		showMessageInfoApprove();
	}

	private void setApprover() {
		criteria.setApproverUserID(securityUtils.getUserName());
		criteria.setDateApproved(DateUtil.getCurrentDate());
	}

	private void showMessageInfoApprove() throws InterruptedException {
		Messagebox.show(getMsgPropVal(Commons.MSG_INFO_APPROVE),
				getMsgPropVal(Commons.MSG_TITLE_APPROVE), Messagebox.OK,
				Messagebox.INFORMATION);
	}

	/** REJECT BUTTON2 **/
	public void onClick$btn_reject2() throws InterruptedException {
		onClick$btn_reject();
	}

	/** REJECT BUTTON1 **/
	public void onClick$btn_reject() throws InterruptedException {
		Messagebox.show(getMsgPropVal(Commons.MSG_ASK_REJECT),
				getMsgPropVal(Commons.MSG_TITLE_REJECT), Messagebox.OK
				| Messagebox.CANCEL, Messagebox.EXCLAMATION,
				new EventListener() {
			public void onEvent(Event evt) throws InterruptedException,
			ParseException {
				if (Messagebox.ON_OK.equals(evt.getName())) {
					rejectCriteria();
				}
			}
		});
	}

	/** REJECT CRITERIA FUNCTIONALITY **/
	public void rejectCriteria() throws InterruptedException {
		setApprover();
		criteriaManager.rejectRequest(criteria);
		showMessageInfoReject();
	}

	private void showMessageInfoReject() throws InterruptedException {
		Messagebox.show(getMsgPropVal(Commons.MSG_INFO_REJECT),
				getMsgPropVal(Commons.MSG_TITLE_REJECT), Messagebox.OK,
				Messagebox.INFORMATION);
	}

	/** HIDE DELETE BUTTON FUNCTIONALITY 2 **/
	public void onClick$btn_updatecriterion2() throws InterruptedException {
		onClick$btn_updatecriterion();
	}

	/** HIDE DELETE BUTTON FUNCTIONALITY **/
	public void onClick$btn_updatecriterion() throws InterruptedException {
		Messagebox.show("Update criterion?", "Update", Messagebox.OK
				| Messagebox.CANCEL, "", new EventListener() {

			@Override
			public void onEvent(Event e) throws Exception {
				if (e.getName().equals(Messagebox.ON_OK)) {
					view_cbb_country_code.setVisible(true);
					cbb_country_code.setVisible(false);
					cbb_country_code.setDisabled(true);

					view_cbb_function_code.setVisible(true);
					cbb_function_code.setVisible(false);
					cbb_function_code.setDisabled(true);

					view_cbb_userfield.setVisible(true);
					cbb_userfield.setVisible(false);
					cbb_userfield.setDisabled(true);

					view_cbb_priority.setVisible(false);
					cbb_priority.setVisible(true);
					cbb_priority.setValue(PriorityType.getDesc(criteria.getPriority_id()));
					cbb_priority.setDisabled(false);

					view_cbb_passaction.setVisible(false);
					cbb_passaction.setVisible(true);
					cbb_passaction.setValue(ActionType.getDesc(criteria.getPass_action_id()));
					cbb_passaction.setDisabled(false);

					view_cbb_pass_messagetype.setVisible(false);
					cbb_pass_messagetype.setVisible(true);
					cbb_pass_messagetype.setValue(MessageType.getDesc(criteria.getPass_messagetype_id()));
					cbb_pass_messagetype.setDisabled(false);

					view_cbb_pass_lettercode.setVisible(false);
					cbb_pass_lettercode.setVisible(true);
					cbb_pass_lettercode.setSelectedIndex(getCodeInList(criteria.getPass_lettercode_id()));
					cbb_pass_lettercode.setDisabled(false);

					view_cbb_pass_commenttype.setVisible(false);
					cbb_pass_commenttype.setVisible(true);
					cbb_pass_commenttype.setValue(CommentType.getDesc(criteria.getPass_commenttype_id()));
					cbb_pass_commenttype.setDisabled(false);

					view_cbb_failaction.setVisible(false);
					cbb_failaction.setVisible(true);
					cbb_failaction.setSelectedIndex(this.determineActionIndex(criteria.getFail_action_id(), true));
					cbb_passaction.setSelectedIndex(this.determineActionIndex(criteria.getPass_action_id(), true));
					cbb_failaction.setDisabled(false);

					view_cbb_fail_messagetype.setVisible(false);
					cbb_fail_messagetype.setVisible(true);
					cbb_fail_messagetype.setValue(MessageType.getDesc(criteria.getFail_messagetype_id()));
					cbb_fail_messagetype.setDisabled(false);

					view_cbb_fail_lettercode.setVisible(false);
					cbb_fail_lettercode.setVisible(true);
					cbb_fail_lettercode.setSelectedIndex(getCodeInList(criteria.getFail_lettercode_id()));
					cbb_fail_lettercode.setDisabled(false);

					view_cbb_fail_commenttype.setVisible(false);
					cbb_fail_commenttype.setVisible(true);
					cbb_fail_commenttype.setValue(CommentType.getDesc(criteria.getFail_commenttype_id()));
					cbb_fail_commenttype.setDisabled(false);



					btn_deletecriterion2.setVisible(false);
					btn_deletecriterion.setVisible(false);

					tbx_description.setVisible(true);
					tbx_description.setDisabled(false);
					tbx_description.setValue(criteria.getDescription());
					view_tbx_description.setVisible(false);

					view_fail_message.setVisible(false);
					tbx_fail_message.setVisible(true);
					tbx_fail_message.setValue(criteria.getFail_message());
					tbx_fail_message.setDisabled(false);

					view_fail_comment.setVisible(false);
					tbx_fail_comment.setVisible(true);
					tbx_fail_comment.setValue(criteria.getFail_comment());
					tbx_fail_comment.setDisabled(false);

					view_pass_message.setVisible(false);
					tbx_pass_message.setVisible(true);
					tbx_pass_message.setValue(criteria.getPass_message());
					tbx_pass_message.setDisabled(false);

					view_pass_comment.setVisible(false);
					tbx_pass_comment.setVisible(true);
					tbx_pass_comment.setValue(criteria.getPass_comment());
					tbx_pass_comment.setDisabled(false);

					btn_updatecriterion.setVisible(false);
					btn_updatecriterion2.setVisible(false);

					row_element.setVisible(true);
					ibx_enhancedvalue_integer.setVisible(true);
					tbx_enhancedvalue_character.setVisible(true);
					bbox_element_2.setVisible(true);
					dbx_enhancedvalue_date.setVisible(true);
					cbb_operator.setDisabled(false);

					asterisk_priority.setVisible(true);
					asterisk_passaction.setVisible(true);
					asterisk_passMessageType.setVisible(true);
					asterisk_passlettercode.setVisible(true);
					asterisk_passcommenttype.setVisible(true);
					asterisk_failaction.setVisible(true);
					asterisk_failmessagetype.setVisible(true);
					asterisk_faillettercode.setVisible(true);
					asterisk_failcommenttype.setVisible(true);
					asterisk_cbboperator.setVisible(true);
					asterisk_operant.setVisible(true);
					asterisk_element.setVisible(true);

					btn_resetcriterion2.setVisible(true);
					btn_resetcriterion.setVisible(true);

					btn_savecriterion.setVisible(true);
					btn_savecriterion2.setVisible(true);

					btn_closecriterion2.setVisible(true);
					btn_closecriterion.setVisible(true);

				} else {
					Executions.sendRedirect("criteria_view.zul");
				}
			}

			private int determineActionIndex(int id, boolean isPassAction) {
				// TODO Auto-generated method stub
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
		});

	}

	/** DELETE BTN2 FUNCTIONALITY **/
	public void onClick$btn_deletecriterion2() throws InterruptedException {
		onClick$btn_deletecriterion();
	}

	/** DELETE BTN1 FUNCTIONALITY **/
	public void onClick$btn_deletecriterion() throws InterruptedException {
		Messagebox.show(getMsgPropVal(Commons.MSG_ASK_DELETE),
				getMsgPropVal(Commons.MSG_TITLE_DELETE), Messagebox.OK
				| Messagebox.CANCEL, Messagebox.EXCLAMATION,
				new EventListener() {
			public void onEvent(Event evt) throws InterruptedException {
				if (Messagebox.ON_OK.equals(evt.getName())) {
					reqDelete();
				}
			}
		});
	}

	/** Delete Temp **/
	public void reqDelete() throws InterruptedException {
		criteriaManager.requestDelete(criteria);
		showMessageInfoDelete();
	}

	private void showMessageInfoDelete() throws InterruptedException {
		Messagebox.show(getMsgPropVal(Commons.MSG_INFO_DELETE),
				getMsgPropVal(Commons.MSG_TITLE_DELETE), Messagebox.OK,
				Messagebox.INFORMATION);
	}

	/** SAVE OR UPDATE FUNCTIONALITY buttonsave2 **/
	public void onClick$btn_savecriterion2() throws InterruptedException {
		onClick$btn_savecriterion();
	}

	/** SAVE OR UPDATE FUNCTIONALITY buttonsave1 **/
	public void onClick$btn_savecriterion() throws InterruptedException {
		Messagebox.show(getMsgPropVal(Commons.MSG_ASK_SUBMIT),
				getMsgPropVal(Commons.MSG_TITLE_SUBMIT), Messagebox.OK
				| Messagebox.CANCEL, Messagebox.EXCLAMATION,
				new EventListener() {

			@Override
			public void onEvent(Event e) throws Exception {
				if (e.getName().equals(Messagebox.ON_OK)) {
					reqUpdate();
				}
			}
		});
	}


	public void reqUpdate() throws InterruptedException {
		if (validateCriteria()) {
			String passActionSelected = (String.valueOf(cbb_passaction.getValue()));
			String passLetterCodeSelected = (String.valueOf(cbb_pass_lettercode.getValue()));
			Action actionbean = actionManager.findByDescription(passActionSelected);


			criteria.setFail_action_id(actionbean.getAction_id());
			criteria.setCountry_code(view_cbb_country_code.getValue());
			criteria.setPriority_id(priorityManager.findByDescription((String.valueOf(cbb_priority.getValue()))).getPriorityID());
			criteria.setPass_action_id(actionbean.getAction_id());
			criteria.setPass_messagetype_id(MessageType.getID_ByDesc((String.valueOf(cbb_pass_messagetype.getValue()))));
			criteria.setPass_commenttype_id(CommentType.getID_ByDesc((String.valueOf(cbb_pass_commenttype.getValue()))));
			criteria.setFail_action_id(actionbean.getAction_id());
			criteria.setFail_messagetype_id(MessageType.getID_ByDesc((String.valueOf(cbb_fail_messagetype.getValue()))));
			criteria.setFail_commenttype_id(CommentType.getID_ByDesc((String.valueOf(cbb_fail_commenttype.getValue()))));
			criteria.setDescription(tbx_description.getValue());
			criteria.setPass_message(tbx_pass_message.getValue());
			criteria.setPass_comment(tbx_pass_comment.getValue());
			criteria.setFail_message(tbx_fail_message.getValue());
			criteria.setFail_comment(tbx_fail_comment.getValue());
			criteria.setFunction_id(view_cbb_function_code.getValue());
			/* Common - START */
			criteria.setLastModifierUserID(securityUtils.getUserName());
			criteria.setDateLastModified(DateUtil.getCurrentDate());
			/* Common - END */
			criteria.setUser_field_id(Integer.valueOf(view_cbb_userfield.getValue().substring(11)));
			criteria.setTempEnhancedCriterionSet(new HashSet<TempEnhancedCriterion>(tempEnhancedCriterionList));

			for (int index = 0; index < tempEnhancedCriterionList.size(); index++) {
				TempEnhancedCriterion tcecBean = tempEnhancedCriterionList.get(index);
				criteria.getTempEnhancedCriterionSet().add(tcecBean);
				tempenhancedcriterion.setTempCriteria(criteria);
				criteria.addToTempEnhancedCriteriaSet(tcecBean);
			}

			criteriaManager.requestUpdate(criteria);
			showMessageInfoSubmit();
		}
	}

	private void showMessageInfoSubmit() throws InterruptedException {
		Messagebox.show(getMsgPropVal(Commons.MSG_INFO_SUBMIT),
				getMsgPropVal(Commons.MSG_TITLE_SUBMIT), Messagebox.OK,
				Messagebox.EXCLAMATION, new EventListener() {
			public void onEvent(Event evt) throws InterruptedException {
				Executions.getCurrent().sendRedirect("");
			}
		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean validateEnhancedCriteria() throws InterruptedException {
		if (lbx_elements.getSelectedItem() == null) {
			bbox_element_1.setErrorMessage(globalUtils.getMessagePropValue(Commons.MESSAGE_WARN_ELEMENT));
			return false;
		}
		if (cbb_operator.getSelectedItem() == null) {
			cbb_operator.setErrorMessage(globalUtils.getMessagePropValue(Commons.MESSAGE_WARN_SELECT_OPERATOR));
			return false;
		}
		if (compare_value_grp.getSelectedItem() == null) {
			Messagebox.show(globalUtils.getMessagePropValue(Commons.MESSAGE_WARN_BLANK_OPERANT),Commons.MSG_WARNING, Messagebox.OK, Messagebox.EXCLAMATION);
			return false;
		} else {
			if (rad_value.isSelected()) {
				if (this.getElement_type_id() == 1 || this.getElement_type_id() == 2) {
					if (tbx_enhancedvalue_character.getValue() == null) {
						tbx_enhancedvalue_character.setErrorMessage(globalUtils.getMessagePropValue(Commons.MESSAGE_WARN_BLANK_VALUE_CHAR));
						return false;
					}
				} else if (this.getElement_type_id() == 3 || this.getElement_type_id() == 5) {
					if (ibx_enhancedvalue_integer.getValue() == null) {
						ibx_enhancedvalue_integer.setErrorMessage(globalUtils.getMessagePropValue(Commons.MESSAGE_WARN_BLANK_VALUE_INT));
						return false;
					}
				} else if (this.getElement_type_id() == 4) {
					if (dbx_enhancedvalue_date.getValue() == null) {
						dbx_enhancedvalue_date.setErrorMessage(globalUtils.getMessagePropValue(Commons.MESSAGE_WARN_BLANK_VALUE_DATE));
						return false;
					}
				}
			} else {
				if (StringUtils.isEmpty(bbox_element_2.getValue())) {
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

		if (itemList.size() > 0) {
			Listitem selectedItem = (Listitem) itemList.get(0);
			Element elem = (Element) model.getElementAt(selectedItem.getIndex());
			elementID = elem.getElement_id();
		}

		Set itemsValue = lbx_element_forvalue.getSelectedItems();
		List itemValueList = new ArrayList(itemsValue);
		ListModel modelValue = lbx_element_forvalue.getModel();
		String elemIDValue = null;

		if (itemValueList.size() > 0) {
			Listitem selectedItem = (Listitem) itemValueList.get(0);
			Element elem = (Element) modelValue.getElementAt(selectedItem.getIndex());
			elemIDValue = elem.getElement_id();
		}

		int errFlag = 0;
		for (int index = 0; index < tempEnhancedCriterionList.size(); index++) {
			TempEnhancedCriterion criteria = tempEnhancedCriterionList.get(index);

			if ((criteria.getElement_id().equalsIgnoreCase(elementID)) && criteria.getOperator_code().equalsIgnoreCase(operatorCode)) {
				if (!StringUtils.isEmpty(tbx_enhancedvalue_character.getValue())) {
					if (criteria.getEnhanced_value_character().equalsIgnoreCase(tbx_enhancedvalue_character.getValue())) {
						errFlag++;
						break;
					}
				} else if (dbx_enhancedvalue_date.getValue() != null) {
					if (criteria.getEnhanced_value_date() != null) {
						if (criteria.getEnhanced_value_date().compareTo(dbx_enhancedvalue_date.getValue()) == 0) {
							errFlag++;
							break;
						}
					}
				} else if (ibx_enhancedvalue_integer.getValue() != null) {
					if (criteria.getEnhanced_value_integer() == ibx_enhancedvalue_integer.getValue().intValue()) {
						errFlag++;
						break;
					}
				} else if (lbx_element_forvalue.getSelectedItems() != null) {
					if (!StringUtils.isEmpty(criteria.getEnhanced_value_element())) {
						if (criteria.getEnhanced_value_element().equals(elemIDValue)) {
							errFlag++;
							break;
						}
					}
				}
			}
		}

		if (errFlag > 0) {
			Messagebox.show(globalUtils.getMessagePropValue(Commons.MESSAGE_WARN_DUPLICATE_CRITERIA), Commons.MSG_WARNING, Messagebox.OK, Messagebox.EXCLAMATION);
			return false;
		}
		return true;
	}

	/** compare **/
	public void onCheck$compare_value_grp() throws InterruptedException {
		if (rad_value.isSelected()) {
			if (this.getElement_type_id() == 1 || this.getElement_type_id() == 2) {
				tbx_enhancedvalue_character.setVisible(true);
				ibx_enhancedvalue_integer.setVisible(false);
				dbx_enhancedvalue_date.setVisible(false);
				bbox_element_2.setVisible(false);

				ibx_enhancedvalue_integer.setValue(null);
				dbx_enhancedvalue_date.setValue(null);
				bbox_element_2.setValue(null);
			} else if (this.getElement_type_id() == 3 || this.getElement_type_id() == 5) {
				ibx_enhancedvalue_integer.setVisible(true);
				tbx_enhancedvalue_character.setVisible(false);
				dbx_enhancedvalue_date.setVisible(false);
				bbox_element_2.setVisible(false);

				tbx_enhancedvalue_character.setValue(null);
				dbx_enhancedvalue_date.setValue(null);
				bbox_element_2.setValue(null);
			} else if (this.getElement_type_id() == 4) {
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

	/** Combobox operator **/
	public void onSelect$cbb_operator() throws InterruptedException {
		Comboitem operatorItem = cbb_operator.getSelectedItem();
		String operatorID = (String) operatorItem.getValue();

		if (operatorID != null) {
			rad_element.setDisabled(false);
			rad_value.setDisabled(false);
		}
	}

	/** elemnts operator **/
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

		elemList = new ArrayList<Element>(elementsList.size());

		for (Element elem : elementsList) {
			elemList.add(new Element(elem));
		}

		Set items = lbx_elements.getSelectedItems();
		List itemList = new ArrayList(items);
		ListModel model = lbx_elements.getModel();

		Element elem = new Element();
		if (itemList.size() > 0) {
			Listitem selectedItem = (Listitem) itemList.get(0);
			elem = (Element) model.getElementAt(selectedItem.getIndex());
		}

		for (int index = 0; index < elementsList.size(); index++) {
			Element elemBean = elementsList.get(index);

			if (elemBean.getElement_id().equalsIgnoreCase(elem.getElement_id())) {
				elemList.remove(index);

				if (elemBean.getElement_operator_eq() == null) {
					for (int index2 = 0; index2 < operatorList.size(); index2++) {
						Operator operator = operatorList.get(index2);
						if (operator.getOperator_code().equalsIgnoreCase(Commons.OPERATOR_EQUALS)) {
							operatorList.remove(index2);
						}
					}
				}
				if (elemBean.getElement_operator_ne() == null) {
					for (int index2 = 0; index2 < operatorList.size(); index2++) {
						Operator operator = operatorList.get(index2);
						if (operator.getOperator_code().equalsIgnoreCase(Commons.OPERATOR_NOT_EQUAL)) {
							operatorList.remove(index2);
						}
					}
				}

				if (elemBean.getElement_operator_gt() == null) {
					for (int index2 = 0; index2 < operatorList.size(); index2++) {
						Operator operator = operatorList.get(index2);
						if (operator.getOperator_code().equalsIgnoreCase(Commons.OPERATOR_GREATER)) {
							operatorList.remove(index2);
						}
					}
				}

				if (elemBean.getElement_operator_ge() == null) {
					for (int index2 = 0; index2 < operatorList.size(); index2++) {
						Operator operator = operatorList.get(index2);
						if (operator.getOperator_code().equalsIgnoreCase(Commons.OPERATOR_GREATER_OR_EQUAL)) {
							operatorList.remove(index2);
						}
					}
				}

				if (elemBean.getElement_operator_lt() == null) {
					for (int index2 = 0; index2 < operatorList.size(); index2++) {
						Operator operator = operatorList.get(index2);
						if (operator.getOperator_code().equalsIgnoreCase(Commons.OPERATOR_LESSER)) {
							operatorList.remove(index2);
						}
					}
				}

				if (elemBean.getElement_operator_le() == null) {
					for (int index2 = 0; index2 < operatorList.size(); index2++) {
						Operator operator = operatorList.get(index2);
						if (operator.getOperator_code().equalsIgnoreCase(Commons.OPERATOR_LESSER_OR_EQUAL)) {
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

	/** Enable or Disable Message Textbox**/
	public void onSelect$cbb_pass_messagetype() throws InterruptedException{
		Comboitem passMessageItem = cbb_pass_messagetype.getSelectedItem();
		int passMessageID = (Integer)passMessageItem.getValue();

		if( passMessageID == 4){
			tbx_pass_message.setDisabled(true);
			tbx_pass_message.setValue(null);
		}else{
			tbx_pass_message.setDisabled(false);
		}
	}

	/** Enable or Disable Message Textbox**/
	public void onSelect$cbb_fail_messagetype() throws InterruptedException{
		Comboitem failMessageItem = cbb_fail_messagetype.getSelectedItem();
		int failMessageID = (Integer)failMessageItem.getValue();

		if(failMessageID == 4){
			tbx_fail_message.setDisabled(true);
		}else{
			tbx_fail_message.setDisabled(false);
		}
	}

	/** Enable or Disable **/
	public void onSelect$cbb_fail_commenttype() throws InterruptedException{
		Comboitem failCommentItem = cbb_fail_commenttype.getSelectedItem();
		int failCommentID = (Integer) failCommentItem.getValue();

		if(failCommentID == 3){
			tbx_fail_comment.setDisabled(true);
			tbx_fail_comment.setValue(null);
		}else{
			tbx_fail_comment.setDisabled(false);
		}
	}

	/** Enable or Disable **/
	public void onSelect$cbb_pass_commenttype() throws InterruptedException{
		Comboitem passCommentItem = cbb_pass_commenttype.getSelectedItem();
		int passCommentID = (Integer) passCommentItem.getValue();

		if(passCommentID == 3){
			tbx_pass_comment.setDisabled(true);
			tbx_pass_comment.setValue(null);
		}else{
			tbx_pass_comment.setDisabled(false);
		}
	}


	private boolean validateCriteria() throws InterruptedException {
		if (cbb_priority.getSelectedItem() == null) {
			cbb_priority.setErrorMessage(globalUtils.getMessagePropValue(Commons.MESSAGE_WARN_PRIORITY));
			return false;
		}

		if (cbb_passaction.getSelectedItem() == null) {
			cbb_passaction.setErrorMessage(globalUtils.getMessagePropValue(Commons.MESSAGE_WARN_ACTION));
			return false;
		}

		if (cbb_pass_messagetype.getSelectedItem() == null) {
			cbb_pass_messagetype.setErrorMessage(globalUtils.getMessagePropValue(Commons.MESSAGE_WARN_MSGTYPE));
			return false;
		}

		if (cbb_pass_lettercode.getSelectedItem() == null) {
			cbb_pass_lettercode.setErrorMessage(globalUtils.getMessagePropValue(Commons.MESSAGE_WARN_LETTERCODE));
			return false;
		}

		if (cbb_pass_commenttype.getSelectedItem() == null) {
			cbb_pass_commenttype.setErrorMessage(globalUtils.getMessagePropValue(Commons.MESSAGE_WARN_CMNTTYPE));
			return false;
		}

		if (cbb_failaction.getSelectedItem() == null) {
			cbb_failaction.setErrorMessage(globalUtils.getMessagePropValue(Commons.MESSAGE_WARN_ACTION));
			return false;
		}

		if (cbb_fail_messagetype.getSelectedItem() == null) {
			cbb_fail_messagetype.setErrorMessage(globalUtils.getMessagePropValue(Commons.MESSAGE_WARN_MSGTYPE));
			return false;
		}

		if (cbb_fail_lettercode.getSelectedItem() == null) {
			cbb_fail_lettercode.setErrorMessage(globalUtils.getMessagePropValue(Commons.MESSAGE_WARN_LETTERCODE));
			return false;
		}

		if (cbb_fail_commenttype.getSelectedItem() == null) {
			cbb_fail_commenttype.setErrorMessage(globalUtils.getMessagePropValue(Commons.MESSAGE_WARN_CMNTTYPE));
			return false;
		}

		if (tempEnhancedCriterionList.size() == 0) {
			Messagebox.show(globalUtils.getMessagePropValue(Commons.MESSAGE_WARN_NO_CRITERIA), Commons.MSG_WARNING, Messagebox.OK, Messagebox.EXCLAMATION);
			return false;
		}
		return true;
	}

	/** button add criterion **/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void onClick$btn_addcriterion() throws InterruptedException {
		if (validateEnhancedCriteria()) {
			tempenhancedcriterion = new TempEnhancedCriterion();
			histenhancedcriteria = new HistEnhancedCriteria();

			String elementForValue = null;
			Set itemsValue = lbx_element_forvalue.getSelectedItems();
			List itemValueList = new ArrayList(itemsValue);
			ListModel modelValue = lbx_element_forvalue.getModel();

			if (itemValueList.size() > 0) {
				Listitem selectedItem = (Listitem) itemValueList.get(0);
				Element elem = (Element) modelValue.getElementAt(selectedItem.getIndex());
				elementForValue = elem.getElement_id();
			}

			if (compare_value_grp.getSelectedItem() != null) {
				if (rad_value.isSelected()) {
					if (this.getElement_type_id() == 1 || this.getElement_type_id() == 2) {
						if (tbx_enhancedvalue_character.getValue() != null) {
							tempenhancedcriterion.setEnhanced_value_character(tbx_enhancedvalue_character.getValue());
							histenhancedcriteria.setEnhanced_value_character(tbx_enhancedvalue_character.getValue());
						}
					} else if (this.getElement_type_id() == 3 || this.getElement_type_id() == 5) {
						if (tbx_enhancedvalue_character.getValue() != null) {
							tempenhancedcriterion.setEnhanced_value_integer(ibx_enhancedvalue_integer.getValue());
							histenhancedcriteria.setEnhanced_value_integer(ibx_enhancedvalue_integer.getValue());
						}
					} else if (this.getElement_type_id() == 4) {
						if (dbx_enhancedvalue_date.getValue() != null) {
							tempenhancedcriterion.setDateFormat(this.dateFormat);
							tempenhancedcriterion.setEnhanced_value_dateformat_id(this.dateFormat_id);
							tempenhancedcriterion.setEnhanced_value_date(dbx_enhancedvalue_date.getValue());
							histenhancedcriteria.setEnhanced_value_date(dbx_enhancedvalue_date.getValue());
						}
					}
				} else {
					if (elementForValue != null) {
						tempenhancedcriterion.setEnhanced_value_element(elementForValue);
						histenhancedcriteria.setEnhanced_value_element(elementForValue);
					}
				}
			}

			String elementID = null;
			Set items = lbx_elements.getSelectedItems();
			List itemList = new ArrayList(items);
			ListModel model = lbx_elements.getModel();

			if (itemList.size() > 0) {
				Listitem selectedItem = (Listitem) itemList.get(0);
				Element elem = (Element) model.getElementAt(selectedItem.getIndex());
				elementID = elem.getElement_id();
			}

			Comboitem operatorItem = cbb_operator.getSelectedItem();
			String operatorCode = (String) operatorItem.getValue();

			tempenhancedcriterion.setElement_id(elementID);
			tempenhancedcriterion.setOperator_code(operatorCode);
			histenhancedcriteria.setElement_id(elementID);
			histenhancedcriteria.setOperator_code(operatorCode);

			tempEnhancedCriterionList.add(tempenhancedcriterion);
			histenhancedcriterionList.add(histenhancedcriteria);

			enhancedCriteriaList.setModel(new ListModelList(tempEnhancedCriterionList));
			enhancedCriteriaList.setItemRenderer(new EnhancedCriteriaListItemRenderer());

			bbox_element_1.setValue(null);
			cbb_operator.setValue(null);
			ibx_enhancedvalue_integer.setValue(null);
			tbx_enhancedvalue_character.setValue(null);
			dbx_enhancedvalue_date.setValue(null);
			bbox_element_2.setValue(null);

			StringBuilder finalCriteriaString = new StringBuilder();
			for (int index = 0; index < tempEnhancedCriterionList.size(); index++) {
				TempEnhancedCriterion etBean = tempEnhancedCriterionList.get(index);

				if ((index >= 1) && (index < tempEnhancedCriterionList.size())) {
					finalCriteriaString.append(Commons.CONJUNCTION);
				}

				finalCriteriaString.append(etBean.getElement_id());
				finalCriteriaString.append(GlobalUtils.determineOperator(etBean.getOperator_code()));

				if (etBean.getEnhanced_value_element() != null) {
					finalCriteriaString.append("'" + etBean.getEnhanced_value_element() + "'");
				} else if (etBean.getEnhanced_value_character() != null) {
					finalCriteriaString.append("'" + etBean.getEnhanced_value_character() + "'");
				} else if (etBean.getEnhanced_value_date() != null) {
					SimpleDateFormat formatter = new SimpleDateFormat(this.getDateFormat());
					finalCriteriaString.append("'" + formatter.format(etBean.getEnhanced_value_date()) + "'");
				} else if (etBean.getEnhanced_value_integer() != null) {
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

	private String getMsgPropVal(String key) {
		return globalUtils.getMessagePropValue(key);
	}

	public TempCriteria getCriteria() {
		return criteria;
	}

	public void setCriteria(TempCriteria criteria) {
		this.criteria = criteria;
	}

	public ListModelList getLml_countryList() {
		return lml_countryList;
	}

	public void setLml_countryList(ListModelList lml_countryList) {
		this.lml_countryList = lml_countryList;
	}

	public ListModelList getLml_functionList() {
		return lml_functionList;
	}

	public void setLml_functionList(ListModelList lml_functionList) {
		this.lml_functionList = lml_functionList;
	}

	public ListModelList getLml_userFieldList() {
		return lml_userFieldList;
	}

	public void setLml_userFieldList(ListModelList lml_userFieldList) {
		this.lml_userFieldList = lml_userFieldList;
	}

	public ListModelList getLml_priorityList() {
		return lml_priorityList;
	}

	public void setLml_priorityList(ListModelList lml_priorityList) {
		this.lml_priorityList = lml_priorityList;
	}

	public ListModelList getLml_actionPassList() {
		return lml_actionPassList;
	}

	public void setLml_actionPassList(ListModelList lml_actionPassList) {
		this.lml_actionPassList = lml_actionPassList;
	}

	public ListModelList getLml_actionFailList() {
		return lml_actionFailList;
	}

	public void setLml_actionFailList(ListModelList lml_actionFailList) {
		this.lml_actionFailList = lml_actionFailList;
	}

	public ListModelList getLml_messageTypeList() {
		return lml_messageTypeList;
	}

	public void setLml_messageTypeList(ListModelList lml_messageTypeList) {
		this.lml_messageTypeList = lml_messageTypeList;
	}

	public ListModelList getLml_letterCodeList() {
		return lml_letterCodeList;
	}

	public void setLml_letterCodeList(ListModelList lml_letterCodeList) {
		this.lml_letterCodeList = lml_letterCodeList;
	}

	public ListModelList getLml_commentTypeList() {
		return lml_commentTypeList;
	}

	public void setLml_commentTypeList(ListModelList lml_commentTypeList) {
		this.lml_commentTypeList = lml_commentTypeList;
	}

	public ListModelList getLml_operatorList() {
		return lml_operatorList;
	}

	public void setLml_operatorList(ListModelList lml_operatorList) {
		this.lml_operatorList = lml_operatorList;
	}

	public void setDateFormat_id(int dateFormat_id) {
		this.dateFormat_id = dateFormat_id;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public int getElement_type_id() {
		return element_type_id;
	}

	public void setElement_type_id(int element_type_id) {
		this.element_type_id = element_type_id;
	}

	public List<Action> getActionPassList() {
		return actionPassList;
	}

	public void setActionPassList(List<Action> actionPassList) {
		this.actionPassList = actionPassList;
	}

	public List<Action> getActionFailList() {
		return actionFailList;
	}

	public void setActionFailList(List<Action> actionFailList) {
		this.actionFailList = actionFailList;
	}

	/*public LetterCodeManager getLetterCodeManager() {
		return letterCodeManager;
	}

	public void setLetterCodeManager(LetterCodeManager letterCodeManager) {
		this.letterCodeManager = letterCodeManager;
	}*/

	/*public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}*/

	/*	public TempUser getTempUser() {
		return tempUser;
	}

	public void setTempUser(TempUser tempUser) {
		this.tempUser = tempUser;
	}*/

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public TempUserManager getTempUserManager() {
		return tempUserManager;
	}

	public void setTempUserManager(TempUserManager tempUserManager) {
		this.tempUserManager = tempUserManager;
	}

	public List<Code> getLetterCodeList() {
		return letterCodeList;
	}

	public void setLetterCodeList(List<Code> letterCodeList) {
		this.letterCodeList = letterCodeList;
	}



}