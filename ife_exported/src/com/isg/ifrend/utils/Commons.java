package com.isg.ifrend.utils;

public class Commons {

	public Commons() {
	}

	public static final String STRING_ZERO = "0";

	public static final String SPACE = " ";
	public static final String NEW_LINE = System.getProperty("line.separator");
	public static final String COLON = ": ";
	public static final String STYLE_COMPARE = "style_compare";
	public static final String STYLE_ERR_MSG = "style_err_msg";
	public static final int DISPLAY_ROWS = 3;
	public static final String ID_FORMAT_PATTERN = "id_format_pattern";

	// Global use
	public static final String GLOBAL_PROPERTIES = "/com/isg/ifrend/utils/resources/globalprops.properties";
	public static final String MESSAGES_PROPERTIES_EN = "/com/isg/ifrend/utils/resources/messages_en.properties";
	public static final String MSG_USER_LOGOUT = "msg_user_logout";
	public static final String URL_USER_FLAGGED = "url_user_flagged";
	public static final String URL_USER_LOGIN = "url_user_login";

	/* GLOBAL CRITERIA ---------------------------------------- START */

	// Elements - Criteria
	public static final String FUNC_MAX_ID_PREFIX = "functionmaxid_prefix";
	public static final String GLOB_MAX_ID_PREFIX = "globalmaxid_prefix";
	public static final String ELEM_ID_DEFAULT = "element_id_default";
	public static final String ELEM_IDNO_DEFAULT = "element_idno_default";
	public static final String RANGE_0_9_PAD = "range_0_9_pad";
	public static final String RANGE_10_99_PAD = "range_10_99_pad";
	public static final String RANGE_100_999_PAD = "range_100_999_pad";
	public static final String ADD_STATUS = "add_status";
	public static final String OPTR_EQ = "operator_eq";
	public static final String OPTR_NE = "operator_ne";
	public static final String OPTR_GT = "operator_gt";
	public static final String OPTR_GE = "operator_ge";
	public static final String OPTR_LT = "operator_lt";
	public static final String OPTR_LE = "operator_le";
	public static final String FORMAT_NUMBER = "format_number";
	public static final String FORMAT_DATE = "format_date";
	public static final String FORMAT_AMOUNT = "format_amount";
	public static final String DATE_FORMAT = "dd/mm/yyyy HH:mm:ss";

	public static final String MESSAGE_WARN_OPERATORS = "warn_msg_operators";
	public static final String MESSAGE_WARN_SELECT_OPERATOR = "warn_msg_select_operator";
	public static final String MESSAGE_WARN_SELECT_ACTION = "warn_msg_select_action";
	public static final String MESSAGE_WARN_SELECT_STATUS = "warn_msg_select_status";
	public static final String MESSAGE_WARN_SELECT_ENTITY = "warn_msg_select_entity";
	public static final String MESSAGE_WARN_ELEMTYPE = "warn_msg_required_elemtype";
	public static final String MESSAGE_WARN_ELEMENT = "warn_msg_required_element";
	public static final String MESSAGE_WARN_ELEMFORMAT = "warn_msg_required_elemformat";
	public static final String MESSAGE_WARN_DATEFORMAT = "warn_msg_required_dateformat";
	public static final String MESSAGE_WARN_LENGTH = "warn_msg_required_length";
	public static final String MESSAGE_WARN_LENGTH_ZERO = "warn_msg_required_length_zero";
	public static final String MESSAGE_WARN_DECIMAL = "warn_msg_required_decimal";
	public static final String MESSAGE_WARN_BLANK_CRITERIA = "warn_msg_blank_criteria";
	public static final String MESSAGE_WARN_NO_CRITERIA = "warn_msg_no_criteria";
	public static final String MESSAGE_WARN_BLANK_OPERANT = "warn_msg_blank_operant";
	public static final String MESSAGE_WARN_BLANK_VALUE_CHAR = "warn_msg_blank_value_char";
	public static final String MESSAGE_WARN_BLANK_VALUE_INT = "warn_msg_blank_value_int";
	public static final String MESSAGE_WARN_BLANK_VALUE_DATE = "warn_msg_blank_value_date";
	public static final String MESSAGE_WARN_DUPLICATE_CRITERIA = "warn_msg_duplicate_criteria";
	public static final String MESSAGE_WARN_COUNTRY = "warn_msg_required_country";
	public static final String MESSAGE_WARN_USERFIELD = "warn_msg_required_userfield";
	public static final String MESSAGE_WARN_FUNCTIONCODE = "warn_msg_required_functioncode";
	public static final String MESSAGE_WARN_PRIORITY = "warn_msg_required_priority";
	public static final String MESSAGE_WARN_ACTION = "warn_msg_required_action";
	public static final String MESSAGE_WARN_MSGTYPE = "warn_msg_required_msgtype";
	public static final String MESSAGE_WARN_LETTERCODE = "warn_msg_required_lettercode";
	public static final String MESSAGE_WARN_CMNTTYPE = "warn_msg_required_cmnttype";
	public static final String MESSAGE_WARN_PENDING_ONE = "warn_msg_pending_one";
	public static final String MESSAGE_WARN_PENDING_MULTIPLE = "warn_msg_pending_multiple";
	public static final String MESSAGE_WARN_PENDING_MULTIPLE_1 = "warn_msg_pending_multiple_1";
	public static final String MSG_INFORMATION = "Information";
	public static final String MESSAGE_WARN_INVALID_FOR_APPROVAL_MULTIPLE_1 = "warn_msg_invalid_for_authorisation_multiple_1";
	public static final String MESSAGE_WARN_INVALID_FOR_APPROVAL_MULTIPLE = "warn_msg_invalid_for_authorisation_multiple";
	public static final String MESSAGE_WARN_INVALID_FOR_APPROVAL_ONE = "warn_msg_invalid_for_authorisation_one";
	public static final String MESSAGE_AUTHORIZE_MULTIPLE = "message_authorize_multiple";
	public static final String MESSAGE_AUTHORIZE_CONFIRM = "message_authorize_confirm";
	public static final String MESSAGE_CANCEL_REQUEST_MULTIPLE = "message_cancel_request_multiple";
	public static final String MESSAGE_CANCEL_REQUEST_CONFIRM = "message_cancel_request_confirm";
	public static final String MESSAGE_AUTHORIZE_ONE = "message_authorize_one";
	public static final String MESSAGE_CANCEL_REQUEST_ONE = "message_cancel_request_one";


	public static final String MESSAGE_ERR_BLANK_CRITERIA = "err_msg_blank_criteria";
	public static final String MESSAGE_ERR_DUPLICATE_CRITERIA = "err_msg_duplicate_criteria";

	public static final String MESSAGE_DELETE_ONE = "message_delete_one";
	public static final String MESSAGE_DELETE_MULTIPLE = "message_delete_multiple";
	public static final String MESSAGE_DELETE_CONFIRM = "message_delete_confirm";
	public static final String MESSAGE_DELETE_OK_ONE = "message_delete_ok_one";
	public static final String MESSAGE_DELETE_OK_MULTIPLE = "message_delete_ok_multiple";
	public static final String MESSAGE_AUTH_CONFIRM_ONE = "message_auth_confirm_one";
	public static final String MESSAGE_CANCEL_CONFIRM_ONE = "message_cancel_confirm_one";
	public static final String MESSAGE_AUTH_CONFIRM_MULTIPLE = "message_auth_confirm_multiple";
	public static final String MESSAGE_CANCEL_CONFIRM_MULTIPLE = "message_cancel_confirm_multiple";


	public static final String OPERATOR_EQUALS = "EQ";
	public static final String OPERATOR_NOT_EQUAL = "NE";
	public static final String OPERATOR_GREATER = "GT";
	public static final String OPERATOR_GREATER_OR_EQUAL = "GE";
	public static final String OPERATOR_LESSER = "LT";
	public static final String OPERATOR_LESSER_OR_EQUAL = "LE";

	public static final String CONJUNCTION = " && ";
	public static final String MSG_WARNING = "Warning";
	public static final String RADIO_BTN_INTEGER = "radio_btn_integer";
	public static final String CONCATENATOR = "?";
	
	public static final String REQUEST_ACTION_ADD = "addition";
	public static final String REQUEST_ACTION_UPDATE = "update";
	public static final String REQUEST_ACTION_DELETE = "deletion";
	public static final String ACTION_NONE = "action_none";

	//For Export to Excel
	public static final String EXCEL_FILE_DATE_SUFFIX = "yyyymmdd";
	public static final String EXCEL_FILE_DELIMITER = "_";
	public static final String EXCEL_FILE_PREFIX = "Export";
	public static final String EXCEL_FILE_EXTENSION = ".xls";
	public static final String EXCEL_FILE_TYPE = "XLS";
	public static final String ERR_MSG_EXPORT = "err_msg_export";
	
	// Global Authorization
	public static final String AUTH_STATUS_ITEM_PENDING = "auth_status_item_pending_auth";
	public static final String AUTH_STATUS_ITEM_APPROVED = "auth_status_item_approved";
	public static final String AUTH_STATUS_ITEM_REJECTED = "auth_status_item_rejected";
	public static final String AUTH_STATUS_ITEM_CANCELLED = "auth_status_item_cancelled";

	public static final String AUTH_ACTION_ITEM_ADD = "auth_action_item_add";
	public static final String AUTH_ACTION_ITEM_UPDATE = "auth_action_item_update";
	public static final String AUTH_ACTION_ITEM_DELETE = "auth_action_item_delete";
	public static final String AUTH_ACTION_ITEM_ALL = "auth_action_item_all";

	// URL - Home
	public static final String URL_HOME = "url_home";

	// URL - Search
	public static final String URL_SEARCH_GLOBAL = "url_search_global";
	public static final String URL_AUTHORIZE_GLOBAL = "url_authorize_global";
	public static final String URL_HISTORY_GLOBAL = "url_history_global";

	// URL - View
	public static final String URL_VIEW_ELEMENT = "url_view_element";
	public static final String URL_VIEW_CRITERIA = "url_view_criteria";
	public static final String URL_VIEW_MLI = "url_view_mli";
	public static final String URL_VIEW_SCRIPT = "url_view_script";
	public static final String URL_VIEW_CODETYPE = "url_view_codeType";
	public static final String URL_VIEW_LABEL = "url_view_label";

	//URL - COMPARE
	public static final String URL_COMPARE_CRITERIA = "url_compare_criteria";

	// URL - Update
	public static final String URL_UPDATE_ELEMENT = "url_update_element";
	public static final String URL_UPDATE_CRITERIA_ = "url_update_criteria";
	public static final String URL_UPDATE_MLI = "url_update_mli";
	public static final String URL_UPDATE_SCRIPT = "url_update_script";
	public static final String URL_UPDATE_CODETYPE = "url_update_codeType";
	public static final String URL_UPDATE_LABEL = "url_update_label";

	// URL - Add
	public static final String URL_ADD_ELEMENT = "url_add_element";
	public static final String URL_ADD_CRITERIA_ = "url_add_criteria";
	public static final String URL_ADD_MLI = "url_add_mli";
	public static final String URL_ADD_SCRIPT = "url_add_script";
	public static final String URL_ADD_CODETYPE = "url_add_codeType";
	public static final String URL_ADD_LABEL = "url_add_label";

	// URL Params
	public static final String URL_DELIMITER = "?";
	public static final String EQUAL_SIGN = "=";
	public static final String URL_PARAM_MST_ID = "mid";
	public static final String URL_PARAM_TMP_ID = "tid";
	public static final String URL_PARAM_HST_ID = "hid";

	// Session Objects
	public static final String SESSION_GLOBAL = "global";
	public static final String SESSION_URL = "url";
	public static final String SESSION_MESSAGE = "message";

	// Modules
	public static final String MODULE_ADD = "Add";
	public static final String MODULE_SEARCH = "Active";
	public static final String MODULE_AUTHORIZE = "Pending";
	public static final String MODULE_HISTORY = "History";

	// MLI - HMESSAGE Function
	public static final String FUNCTION_MLI = "HMESSAGE";

	
	public static final int ELEMENT_GLOBAL = 1;
	public static final int ELEMENT_FUNCTION = 2;

	/* Common Labels */
	public static final String LABEL_ALLOWED = "label_allowed";
	public static final String LABEL_NOT_ALLOWED = "label_not_allowed";

	/* Messages - Input Validation - LABEL */
	public static final String BLANK_ENTER_NATIVE_STRING = "blank_enter_native_string";
	public static final String BLANK_SELECT_LANGUAGE_CODE = "blank_select_language_code";
	public static final String BLANK_ENTER_LABEL_ID = "blank_enter_label_id";

	/* Messages - Input Validation - CODE TYPE */
	public static final String BLANK_ENTER_CODETYPE_DESC = "blank_enter_codeType_desc";
	public static final String ZERO_CODE_LIST = "zero_code_list";
	public static final String BLANK_ENTER_CODE_VALUE = "blank_enter_code_value";
	public static final String BLANK_ENTER_CODE_DESC = "blank_enter_code_desc";
	
	/* Messages - Input Validation - MLI & SCRIPT */
	public static final String BLANK_ENTER_DESC = "blank_enter_desc";
	public static final String BLANK_SELECT_FUNCTION_CODE = "blank_select_function_code";
	public static final String BLANK_SELECT_USER_FIELD = "blank_select_user_field";
	public static final String BLANK_SELECT_RESPONSE_CODE = "blank_select_response_code";
	public static final String BLANK_SELECT_PRIORITY = "blank_select_priority";
	
	public static final String BLANK_SELECT_MESSAGE_TYPE = "blank_select_message_type";
	public static final String BLANK_SELECT_COMMENT_TYPE = "blank_select_comment_type";
	public static final String BLANK_ENTER_MESSAGE = "blank_enter_message";
	public static final String BLANK_ENTER_COMMENT = "blank_enter_comment";
	
	public static final String BLANK_SELECT_PASSACTION_DROPDOWN = "blank_selected_passaction";
	public static final String BLANK_SELECT_PASSMESSAGE_DROPDOWN = "blank_selected_passmessage";
	public static final String BLANK_SELECT_PASSLETTERCODE_DROPDOWN = "blank_selected_passlettercode";
	public static final String BLANK_SELECT_PASSCOMMENTTYPE_DROPDOWN = "blank_selected_passcommenttype_dropdown";
	public static final String BLANK_SELECT_FAILACTION_DROPDOWN = "blank_selected_failaction";
	public static final String BLANK_SELECT_FAILMESSAGE_DROPDOWN = "blank_selected_failmessage";
	public static final String BLANK_SELECT_FAIL_LETTERCODE_DROPDOWN = "blank_selected_fail_lettercode";
	public static final String BLANK_SELECT_FAIL_COMMENTTYPE_DROPDOWN = "blank_selected_fail_commenttype";
	public static final String BLANK_DESCRIPTION_TEXTBOX = "blank_description";
	public static final String BLANK_PASSMESSAGE_TEXTBOX = "blank_passmessage";
	public static final String BLANK_PASSCOMMENT_TEXTBOX = "blank_passcomment";
	public static final String BLANK_FAILMESSAGE_TEXTBOX = "blank_failmessage";
	public static final String BLANK_FAILCOMMENT_TEXTBOX = "blank_failcomment";
	
	
	public static final String EMPTY_STRING = "";
	public static final int ID_NUM_LEN = 4;

	/* Messages - Global Criteria - General */
	public static final String MSG_TITLE_SUBMIT = "msg_title_submit";
	public static final String MSG_TITLE_DELETE = "msg_title_delete";
	public static final String MSG_TITLE_APPROVE = "msg_title_approve";
	public static final String MSG_TITLE_REJECT = "msg_title_reject";
	public static final String MSG_TITLE_CLEAR = "msg_title_clear";
	public static final String MSG_TITLE_RESET = "msg_title_reset";
	public static final String MSG_TITLE_CANCEL = "msg_title_cancel";

	public static final String MSG_ERROR = "msg_error";

	public static final String MSG_ASK_SUBMIT = "msg_ask_submit";
	public static final String MSG_ASK_DELETE = "msg_ask_delete";
	public static final String MSG_ASK_APPROVE = "msg_ask_approve";
	public static final String MSG_ASK_REJECT = "msg_ask_reject";
	public static final String MSG_ASK_CANCEL = "msg_ask_cancel";
	public static final String MSG_ASK_CLEAR = "msg_ask_clear";
	public static final String MSG_ASK_RESET = "msg_ask_reset";

	public static final String MSG_INFO_SUBMIT = "msg_info_submit";
	public static final String MSG_INFO_DELETE = "msg_info_delete";
	public static final String MSG_INFO_APPROVE = "msg_info_approve";
	public static final String MSG_INFO_REJECT = "msg_info_reject";
	public static final String MSG_INFO_CANCEL = "msg_info_cancel";

	public static final String RECORD_DUPLICATE = "msg_rec_duplicate";
	public static final String RECORD_EXIST = "msg_rec_already_exists";
	
	public static final String MSG_ERR_MLI_ASSOC = "msg_err_mli_assoc";
	public static final String MSG_ERR_SCRIPT_ASSOC = "msg_err_script_assoc";
	
	public static final String PREFIX_ELEM_FUNCTION = "prefix_elem_function";
	public static final String PREFIX_ELEM_GLOBAL = "prefix_elem_global";
	public static final String PREFIX_CRITERIA = "prefix_criteria";
	public static final String PREFIX_SCRIPT = "prefix_script";
	public static final String PREFIX_MLI = "prefix_mli";
	public static final String PREFIX_LABEL = "prefix_label";
	public static final String PREFIX_CODETYPE = "prefix_codetype";
	
	public static final String CODETYPE_MLI_RESPONSE_CODES = "MLI Response Codes";
	public static final String CODETYPE_LETTER_CODES = "Letter Codes";
	
	public static final String MSG_CONFIG_CT_RESPONSECODE = "msg_config_ct_responsecode";
	public static final String MSG_CONFIG_CT_LETTERCODE = "msg_config_ct_lettercode";
	public static final String MSG_TITLE_SETUP_CT = "msg_title_setup_ct";	
	
	public static final String IMG_SRC_MSG_INFO = "img_src_msg_info";
	public static final String IMG_SRC_MSG_ERR = "img_src_msg_err";
	public static final String STYLE_MSG_INFO = "style_msg_info";
	public static final String STYLE_MSG_ERR = "style_msg_err";
	
	/* GLOBAL CRITERIA ------------------------------------------ END */

	/* SECURITY ADMINISTRATION -------------------------------- START */
	
	/*Security Admin Use*/
	
	/**Security Admin Messages definitions**/
	public static String SA_RECORD_EXIST = "sa_record_exist";
	public static String SA_RECORD_NOTEXIST = "sa_record_noexist";
	public static String SA_ADD_SUCCESS = "sa_add_success";
	public static String SA_ADD_FAIL = "sa_add_fail";
	public static String SA_UPDATE_SUCCESS = "sa_update_success";
	public static String SA_UPDATE_FAIL = "sa_update_fail";
	public static String SA_DELETE_SUCCESS = "sa_delete_success";
	public static String SA_DELETE_FAIL = "sa_delete_fail";
	
	public static String SA_APPROVE_SUCCESS = "sa_approve_success";
	public static String SA_APPROVE_FAIL = "sa_approve_fail";
	
	public static String SA_REJECT_SUCCESS = "sa_reject_success";
	public static String SA_REJECT_FAIL = "sa_reject_fail";
				
	public static String SA_CANCEL_SUCCESS = "sa_cancel_success";
	public static String SA_CANCEL_FAIL = "sa_cancel_fail";
		
	public static String SA_MULTI_APPROVE_COMPLETE="sa_multi_approve_complete";
	public static String SA_MULTI_APPROVE_FAIL="sa_multi_approve_fail";
	
	public static String SA_MULTI_REJECT_COMPLETE="sa_multi_reject_complete";
	public static String SA_MULTI_REJECT_FAIL="sa_multi_reject_fail";
	
	public static String SA_MULTI_CANCELLATION_COMPLETE="sa_multi_cancellation_complete";
	public static String SA_MULTI_CANCELLATION_FAIL="sa_multi_cancellation_fail";
		
	public static String SA_MULTI_DELETE_COMPLETE="sa_multi_delete_complete";	
	public static String SA_MULTI_DELETE_FAIL="sa_multi_delete_fail";	
	
	public static String SA_SELECT_ITEM = "sa_select_item";
	
	public static String SA_LBLINFO = "SA_LBLINFO";
	
	/**Security Admin URL definitions**/
	public static String URL_ADD_USERGROUP = "url_add_usergroup";
	public static String URL_UPDATE_USERGROUP = "url_update_usergroup";
	public static String URL_SEARCH_USERGROUP = "url_search_usergroup";
	public static String URL_AUTHORIZE_USERGROUP = "url_authorize_usergroup";
	public static String URL_HISTORY_USERGROUP = "url_history_usergroup";
	public static String URL_COMPARE_USERGROUP = "url_compare_usergroup";
	public static String URL_SECURITY_ADMIN = "url_security_admin";
	
	
	
	/**Security Admin Actions definitions**/
	public static String SA_ACTION_ADD = "ADD";
	public static String SA_ACTION_UPDATE = "UPDATE";
	public static String SA_ACTION_DELETE = "DELETE";
	
	/**Security Admin Status definitions**/
	public static String SA_STATUS_ACTIVE = "ACTIVE";
	public static String SA_STATUS_DELETED = "DELETED";
	public static String SA_STATUS_APPROVED = "APPROVED";
	public static String SA_STATUS_REJECTED = "REJECTED";
	public static String SA_STATUS_CANCELLED = "CANCELLED";
	public static String SA_STATUS_PENDING = "PENDING";
			
	public static String PREVIOUS_PAGE = "previous_page";
	
	/**Security Admin Tooltip text**/
	public static String TOOLTIP_CLICK_TO_UPDATE = "tooltip_click_to_update";
	public static String TOOLTIP_CLICK_TO_COMPARE = "tooltip_click_to_compare";
	public static String TOOLTIP_CLICK_TO_VIEW = "tooltip_click_to_view";
	
	/**Security Admin Messagebox Title definitions**/
	public static String MSGBOX_TITLE_INFORMATION = "msgbox_title_information";
	public static String MSGBOX_TITLE_ERROR = "msgbox_title_error";
	public static String MSGBOX_TITLE_WARNING = "msgbox_title_warning";
	public static String MSGBOX_TITLE_ADD_RECORD = "msgbox_title_add_record";
	public static String MSGBOX_TITLE_UPDATE_RECORD = "msgbox_title_update_record";
	public static String MSGBOX_TITLE_DELETE_RECORD = "msgbox_title_delete_record";
	public static String MSGBOX_TITLE_AUTHORIZE = "msgbox_title_authorize";
	public static String MSGBOX_TITLE_CANCEL = "msgbox_title_cancel";
	public static String MSGBOX_TITLE_LOGOUT = "msgbox_title_logout";
		
	/**Security Admin Field Validation Messages**/
	public static String SA_MSG_FIELD_MUST_NOT_BLANK = "sa_msg_field_must_not_blank";
	public static String SA_MSG_SELECT_FROM_LIST = "sa_msg_select_from_list";
	public static String SA_MSG_MUST_NOT_EXCEED = "sa_msg_must_not_exceed";
	public static String SA_MSG_UNABLE_TO_CANCEL = "sa_msg_unable_to_cancel";
	public static String SA_MSG_ADD_NEW_USERGROUP = "sa_msg_add_new_usergroup";
	public static String SA_MSG_CANCEL_USERGROUP = "sa_msg_cancel_usergroup";
	public static String SA_MSG_UPDATE_USERGROUP = "sa_msg_update_usergroup";
	public static String SA_MSG_DELETE_USERGROUP = "sa_msg_delete_usergroup";
	public static String SA_MSG_APPROVE_USERGROUP = "sa_msg_approve_usergroup";
	public static String SA_MSG_REJECT_USERGROUP = "sa_msg_reject_usergroup";
	public static String SA_MSG_CANCEL_SELECTED_USERGROUP = "sa_msg_cancel_selected_usergroup";
	public static String SA_MSG_APPROVE_SELECTED_USERGROUP = "sa_msg_approve_selected_usergroup";
	public static String SA_MSG_REJECT_SELECTED_USERGROUP = "sa_msg_reject_selected_usergroup";
	public static String SA_MSG_ERR_EXPORT_TO_EXCEL = "sa_msg_err_export_to_excel";
	public static String SA_MSG_ERR_CHKER_APPROVE_OWN = "sa_msg_err_chker_approve_own";
	public static String SA_MSG_ERR_CHKER_REJECT_OWN = "sa_msg_err_chker_reject_own";
	public static String SA_MSG_ERR_CHKER_CANCEL_OWN = "sa_msg_err_chker_cancel_own";
	public static String SA_MSG_ERR_MULTI_PENDING_DELETE = "sa_msg_err_multi_pending_delete";
		
	public static String SA_MSG_INVALID_OWNER = "sa_msg_invalid_owner";
	
	/**Security Admin field lengths**/
	public static final Integer SA_MAXLENGTH_GROUP_ID = 20;
	public static final Integer SA_MAXLENGTH_DESCRIPTION = 100;
		
	public static String MSG_DELETE_SELECTED_ITEMS="msg_delete_selected_items";
	
	/* Message Headers */
	public static final String MSG_HEADER_ADD = "msg_header_add";
	public static final String MSG_HEADER_APPROVE = "msg_header_approve";
	public static final String MSG_HEADER_REJECT = "msg_header_reject";
	public static final String MSG_HEADER_CANCEL = "msg_header_cancel";
	public static final String MSG_HEADER_UPDATE = "msg_header_update";
	public static final String MSG_HEADER_DELETE = "msg_header_delete";
	public static final String MSG_HEADER_ERROR = "msg_header_error";
	
	/* Screen Labels */
	public static final String SCREEN_USER_PROFILE_ADD = "screen_user_profile_add";
	public static final String SCREEN_USER_PROFILE_VIEW = "screen_user_profile_view";
	public static final String SCREEN_USER_PROFILE_UPDATE = "screen_user_profile_update";
	public static final String SCREEN_USER_PROFILE_AUTHORIZE = "screen_user_profile_authorize";
	public static final String SCREEN_USER_PROFILE_SEARCH = "screen_user_profile_search";
	public static final String SCREEN_USER_PROFILE_HISTORY = "screen_user_profile_history";
	public static final String SCREEN_USER_ORG_ADD = "screen_user_org_add";
	public static final String SCREEN_USER_ORG_VIEW = "screen_user_org_view";
	public static final String SCREEN_USER_ORG_SEARCH = "screen_user_org_search";
	public static final String SCREEN_USER_ORG_HISTORY = "screen_user_org_history";
	public static final String SCREEN_USER_ORG_UPDATE = "screen_user_org_update";
	public static final String SCREEN_USER_ORG_AUTHORIZE = "screen_user_org_authorize";
	
	/* Messages - General */
	public static final String MSG_PASSWORD_UPDATE_SUCCESSFUL = "msg_password_update_successful";
	
	/* Error Messages - General */
	public static final String ERR_MSG_REQUIRED_FIELDS = "err_msg_required_fields";
	public static final String ERR_MSG_INVALID_CREDENTIALS = "err_msg_invalid_credentials";
	public static final String ERR_MSG_PASSWORD_DO_NOT_MATCH = "err_msg_password_do_not_match";
	public static final String ERR_MSG_WRONG_PASSWORD = "err_msg_wrong_password";
	public static final String ERR_MSG_INVALID_ID_FORMAT = "err_msg_invalid_id_format";
	public static final String ERR_MSG_UPDATE_PASSWORD_FAILED = "err_msg_update_password_failed";
	
	/* User Profile - Messages */
	public static final String MSG_USER_ADD = "msg_user_add";
	public static final String MSG_USER_APPROVE = "msg_user_approve";
	public static final String MSG_USER_REJECT = "msg_user_reject";
	public static final String MSG_USER_UPDATE = "msg_user_update";
	public static final String MSG_USER_CANCEL = "msg_user_cancel";
	public static final String MSG_USER_DELETE = "msg_user_delete";
	public static final String MSG_USER_AUTHORIZE = "msg_user_authorize";
	public static final String MSG_USER_APPROVE_PROMPT = "msg_user_approve_prompt";
	public static final String MSG_USER_REJECT_PROMPT = "msg_user_reject_prompt";
	public static final String MSG_USER_CANCEL_PROMPT = "msg_user_cancel_prompt";
	public static final String MSG_USER_UPDATE_PROMPT = "msg_user_update_prompt";
	public static final String MSG_USER_DELETE_PROMPT = "msg_user_delete_prompt";
	public static final String MSG_USER_APPROVE_PROMPT_MULTI = "msg_user_approve_prompt_multi";
	public static final String MSG_USER_REJECT_PROMPT_MULTI = "msg_user_reject_prompt_multi";
	public static final String MSG_USER_CANCEL_PROMPT_MULTI = "msg_user_cancel_prompt_multi";
	public static final String MSG_USER_DELETE_PROMPT_MULTI = "msg_user_delete_prompt_multi";
	public static final String MSG_USER_APPROVE_MULTI = "msg_user_approve_multi";
	public static final String MSG_USER_REJECT_MULTI = "msg_user_reject_multi";
	public static final String MSG_USER_CANCEL_MULTI = "msg_user_cancel_multi";
	public static final String MSG_USER_DELETE_MULTI = "msg_user_delete_multi";
	public static final String MSG_USER_AUTHORIZE_MULTI = "msg_user_authorize_multi";
	
	/* User Profile - Warning Messages */
	public static final String WARN_MSG_FIELD_EMPTY = "warn_msg_field_empty";
	public static final String WARN_MSG_NO_SELECTED_ITEM = "warn_msg_no_selected_item";
	public static final String WARN_MSG_ID_NOT_UNIQUE = "warn_msg_id_not_unique";
	public static final String WARN_MSG_VALUE_NEGATIVE = "warn_msg_value_negative";
	public static final String WARN_MSG_USER_DOES_NOT_EXIST = "warn_msg_user_does_not_exist";
	
	/* User Profile - Error Messages */
	public static final String ERR_MSG_USER_NOT_FOUND = "err_msg_user_not_found";
	public static final String ERR_MSG_USER_HISTORY_NOT_FOUND = "err_msg_user_history_not_found";
	public static final String ERR_MSG_USER_NO_SELECTED = "err_msg_user_no_selected";
	public static final String ERR_MSG_USER_ADDED_ALREADY = "err_msg_user_added_already";
	public static final String ERR_MSG_USER_GRP_NO_SELECTED = "err_msg_user_grp_no_selected";
	public static final String ERR_MSG_USER_ORG_NO_SELECTED = "err_msg_user_org_no_selected";
	public static final String ERR_MSG_USER_EXIST = "err_msg_user_already_exist";
	public static final String ERR_MSG_USER_ADD = "err_msg_user_add";
	public static final String ERR_MSG_USER_DELETE = "err_msg_user_delete";
	public static final String ERR_MSG_USER_APPROVE = "err_msg_user_approve";
	public static final String ERR_MSG_USER_REJECT = "err_msg_user_reject";
	public static final String ERR_MSG_USER_CANCEL = "err_msg_user_cancel";
	public static final String ERR_MSG_USER_AUTHORIZE = "err_msg_user_authorize";
	public static final String ERR_MSG_USER_UPDATE_NOT_ALLOWED = "err_msg_user_update_not_allowed";
	public static final String ERR_MSG_USER_DELETE_NOT_ALLOWED = "err_msg_user_delete_not_allowed";
	
	/* User Organization - Messages */
	public static final String MSG_USER_ORG_ADD = "msg_user_org_add";
	public static final String MSG_USER_ORG_APPROVE = "msg_user_org_approve";
	public static final String MSG_USER_ORG_REJECT = "msg_user_org_reject";
	public static final String MSG_USER_ORG_UPDATE = "msg_user_org_update";
	public static final String MSG_USER_ORG_CANCEL = "msg_user_org_cancel";
	public static final String MSG_USER_ORG_DELETE = "msg_user_org_delete";
	public static final String MSG_USER_ORG_AUTHORIZE = "msg_user_org_authorize";
	public static final String MSG_USER_ORG_UPDATE_PROMPT = "msg_user_org_update_prompt";
	public static final String MSG_USER_ORG_DELETE_PROMPT = "msg_user_org_delete_prompt";
	public static final String MSG_USER_ORG_APPROVE_PROMPT = "msg_user_org_approve_prompt";
	public static final String MSG_USER_ORG_REJECT_PROMPT = "msg_user_org_reject_prompt";
	public static final String MSG_USER_ORG_CANCEL_PROMPT = "msg_user_org_cancel_prompt";
	public static final String MSG_USER_ORG_APPROVE_PROMPT_MULTI = "msg_user_org_approve_prompt_multi";
	public static final String MSG_USER_ORG_REJECT_PROMPT_MULTI = "msg_user_org_reject_prompt_multi";
	public static final String MSG_USER_ORG_CANCEL_PROMPT_MULTI = "msg_user_org_cancel_prompt_multi";
	public static final String MSG_USER_ORG_DELETE_PROMPT_MULTI = "msg_user_org_delete_prompt_multi";
	public static final String MSG_USER_ORG_AUTHORIZE_PROMPT_MULTI = "msg_user_org_authorize_prompt_multi";
	public static final String MSG_USER_ORG_APPROVE_MULTI = "msg_user_org_approve_multi";
	public static final String MSG_USER_ORG_REJECT_MULTI = "msg_user_org_reject_multi";
	public static final String MSG_USER_ORG_CANCEL_MULTI = "msg_user_org_cancel_multi";
	public static final String MSG_USER_ORG_DELETE_MULTI = "msg_user_org_delete_multi";
	public static final String MSG_USER_ORG_AUTHORIZE_MULTI = "msg_user_org_authorize_multi";
	
	/* User Organization - Error Messages */
	public static final String ERR_MSG_USER_ORG_NOT_FOUND = "err_msg_user_org_not_found";
	public static final String ERR_MSG_USER_ORG_HISTORY_NOT_FOUND = "err_msg_user_org_history_not_found";
	public static final String ERR_MSG_USER_ORG_EXIST = "err_msg_user_org_already_exist";
	public static final String ERR_MSG_USER_ORG_ADD = "err_msg_user_org_add";
	public static final String ERR_MSG_USER_ORG_UPDATE = "err_msg_user_org_update";
	public static final String ERR_MSG_USER_ORG_DELETE = "err_msg_user_org_delete";
	public static final String ERR_MSG_USER_ORG_APPROVE = "err_msg_user_org_approve";
	public static final String ERR_MSG_USER_ORG_REJECT = "err_msg_user_org_reject";
	public static final String ERR_MSG_USER_ORG_CANCEL = "err_msg_user_org_cancel";
	public static final String ERR_MSG_USER_ORG_UPDATE_NOT_ALLOWED = "err_msg_user_org_update_not_allowed";
	public static final String ERR_MSG_USER_ORG_DELETE_NOT_ALLOWED = "err_msg_user_org_delete_not_allowed";
	
	/* URL Definitions */
	public static final String URL_ADD_USER_PROFILE = "url_add_user_profile";
	public static final String URL_SEARCH_USER_PROFILE = "url_search_user_profile";
	public static final String URL_UPDATE_USER_PROFILE = "url_update_user_profile";
	public static final String URL_AUTHORIZE_USER_PROFILE = "url_authorize_user_profile";
	public static final String URL_AUTHORIZE_VIEW_USER_PROFILE = "url_authorize_view_user_profile";
	public static final String URL_HISTORY_USER_PROFILE = "url_history_user_profile";
	public static final String URL_COMPARE_USER_PROFILE = "url_compare_user_profile";
	public static final String URL_ADD_USER_ORG = "url_add_user_org";
	public static final String URL_SEARCH_USER_ORG = "url_search_user_org";
	public static final String URL_UPDATE_USER_ORG = "url_update_user_org";
	public static final String URL_AUTHORIZE_USER_ORG = "url_authorize_user_org";
	public static final String URL_AUTHORIZE_VIEW_USER_ORG = "url_authorize_view_user_org";
	public static final String URL_HISTORY_USER_ORG = "url_history_user_org";
	public static final String URL_COMPARE_USER_ORG = "url_compare_user_org";

	/* Button Labels */
	public static final String BUTTON_LABEL_SUBMIT = "btn_label_submit";
	public static final String BUTTON_LABEL_UPDATE = "btn_label_update";
	public static final String BUTTON_LABEL_CANCEL = "btn_label_cancel";
	public static final String BUTTON_LABEL_RESET = "btn_label_reset";
	
	public static final String URL_COMPARE_ELEMENT = null;
	
	public static final String VIEW = "VIEW";
	public static final String USER_ID = "USER_ID";
	public static final String CURRENT_STATUS = "CURRENT_STATUS";
	public static final String USER_NAME = "USER_NAME";
	public static final String EMPLOYEE_ID = "EMPLOYEE_ID";
	public static final String EMAIL = "EMAIL";
	public static final String BUSINESS_ENTITY = "BUSINESS_ENTITY";
	public static final String MAX_ACCT_BAL_AND_STATUS = "MAX_ACCT_BAL_AND_STATUS";
	public static final String VIP_ACCT_ALLOWED = "VIP_ACCT_ALLOWED";
	public static final String SUPERVISOR_ID = "SUPERVISOR_ID";
	public static final String STAFF_ACCT_ALLOWED = "STAFF_ACCT_ALLOWED";
	public static final String CUSTOMER_ID = "CUSTOMER_ID";
	public static final String LANGUAGE_PREFERENCE = "LANGUAGE_PREFERENCE";
	public static final String USER_GROUP = "USER_GROUP";
	public static final String USER_ORG = "USER_ORG";
	
	public static final String ORGANIZATION_DESCRIPTION = "ORGANIZATION_DESCRIPTION";
	public static final String DISPOSE_ADJUSTMENT_LIMIT = "DISPOSE_ADJUSTMENT_LIMIT";
	public static final String C1_CREDIT_LIMIT = "C1_CREDIT_LIMIT";
	public static final String C2_CREDIT_LIMIT = "C2_CREDIT_LIMIT";
	public static final String CHARGE_BACK_LIMIT = "CHARGE_BACK_LIMIT";
	public static final String RETRIEVAL_AMOUNT = "RETRIEVAL_AMOUNT";
	public static final String FRAUD_AMOUNT = "FRAUD_AMOUNT";
	public static final String FEE_AMOUNT = "FEE_AMOUNT";
	public static final String PERMANENT_CREDIT_LIMIT = "PERMANENT_CREDIT_LIMIT";
	public static final String TEMPORARY_CREDIT_LIMIT = "TEMPORARY_CREDIT_LIMIT";
	public static final String FEE_ADJUSTMENT_LIMIT = "FEE_ADJUSTMENT_LIMIT";
	public static final String LATE_CHARGE_ADJUSTMENT_LIMIT = "LATE_CHARGE_ADJUSTMENT_LIMIT";
	public static final String BALANCE_ADJUSTMENT_LIMIT = "BALANCE_ADJUSTMENT_LIMIT";
	public static final String TRANSACTION_LIMIT = "TRANSACTION_LIMIT";
	public static final String TRANSACTION_CODE = "TRANSACTION_CODE";
	
	/* SECURITY ADMINISTRATION ---------------------------------- END */

}
