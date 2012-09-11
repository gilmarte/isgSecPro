package com.isg.ifrend.utils;

import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

import com.isg.ifrend.security.utils.SecurityUtils;
import com.isg.ifrend.service.ServiceLocator;
import com.isg.ifrend.service.UserManager;
import com.mysql.jdbc.StringUtils;

/**
 * Author: Gerald L. de Guzman
 * **/

public class SecurityAdminUtils {

	private static GlobalUtils globalUtils = GlobalUtils.getGlobalUtilsInstance();
	private UserManager userManager = ServiceLocator.getUserManager();
	private static SecurityAdminUtils securityAdminUtils;
	
	public static SecurityAdminUtils getSecurityAdminUtilsInstance() {
		if(securityAdminUtils == null) {
			securityAdminUtils = new SecurityAdminUtils();
			return securityAdminUtils;
		}
		return securityAdminUtils;
	}

	public static void Redirect_to_Searchpage(){
//		GlobalUtils globalUtils = new GlobalUtils();

		Executions.sendRedirect(globalUtils.getGlobalPropValue(Commons.URL_SEARCH_USERGROUP));
	}


	public static void Redirect_to_Page(String CommonClass_URL){
//		GlobalUtils globalUtils = new GlobalUtils();

		Executions.sendRedirect(globalUtils.getGlobalPropValue(CommonClass_URL));

	}

//	public static String getCurrentDate(){
//		return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").format(new Date());
//	}

	
	/**
	 * Instructions to use this:
	 * 1. Create a list of objects : refer to Create_Required_Fields_List() in UserGroupEditPageViewCtrl
	 * 2. Put Create_Required_Fields_List() inside doAfterCompose
	 * 3. On your onClick$button method declare it like this:
	 * 
	 * if(!securityAdminUtils.ValidateFields(lst_requiredfields)){
	 * 		// Call your method whatsoever
	 * } 
	 * **/
	public boolean validateFields(List<Object>fields_){
		boolean hasError = false;

		for(Object obj_: fields_){
			
			if(obj_ instanceof Combobox){
				Combobox combobox = (Combobox) obj_;
				if(combobox.getText().equals("")){
					combobox.setErrorMessage(globalUtils.getMessagePropValue(Commons.SA_MSG_SELECT_FROM_LIST));
					hasError = true;
					continue;
				}
			}
			
			/*Check if textbox*/
			if(obj_ instanceof Textbox){
				Textbox txtbox = (Textbox) obj_;
				/**Check if null**/
				if(txtbox == null || txtbox.getValue().equals("")){
					txtbox.setErrorMessage(globalUtils.getMessagePropValue(Commons.SA_MSG_FIELD_MUST_NOT_BLANK));
					hasError = true;
					continue;
				}				
				/**No need to check for length since object.SetMaxLength fixed the problem**/
				/*if(txtbox.getValue().length() > txtbox.getMaxlength()){
					String msg_ = String.format(globalUtils.returnPropValue(Commons.MESSAGES_PROPERTIES_EN, Commons.SA_MSG_MUST_NOT_EXCEED), String.valueOf(txtbox.getMaxlength()));
					txtbox.setErrorMessage(msg_);
					hasError = true;
					break;
				}*/
			}			
		}		
		return hasError;
	}
	
	public boolean validateID(Textbox id) {
		try {
			if(id.getText().matches(globalUtils.getGlobalPropValue(Commons.ID_FORMAT_PATTERN))) {
				return true;
			}
			id.setErrorMessage(globalUtils.getMessagePropValue(Commons.ERR_MSG_INVALID_ID_FORMAT));
			return false;
		}
		catch(NullPointerException e) {
			return false;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static String get_Logged_in_Username(){
		return SecurityUtils.getSecurityUtilsInstance().getUserName().toUpperCase();
	}
	
		
	public String getGlobalString(String Common_Class_String){
		
//		GlobalUtils gb = new GlobalUtils();
		String returnPropValue = "";
		
		returnPropValue = globalUtils.returnPropValue(Commons.GLOBAL_PROPERTIES, Common_Class_String);
		if(returnPropValue == null || returnPropValue.isEmpty()){
			returnPropValue = "";
		}
		
		return returnPropValue;
	}
	
	public String getMessage(String Common_Class_String){
//		GlobalUtils gb = new GlobalUtils();
		String returnPropValue = "";
		
		returnPropValue = globalUtils.returnPropValue(Commons.MESSAGES_PROPERTIES_EN, Common_Class_String);
		if(returnPropValue == null || returnPropValue.isEmpty()){
			returnPropValue = "";
		}
				
		return toCamelcase(returnPropValue);
	}	
	
	/**
	 * Convert all string to camelcase (Uppcase 1st letter only)
	 * **/
	@SuppressWarnings({ "null" })
	public String toCamelcase(String str){
		String str_camelcased = "";
		
		if((str != null || !str.isEmpty()) && str.length() > 0){
			str_camelcased = str.substring(0,1).toUpperCase() + str.substring(1);
		}else{
			str_camelcased = "";
		}
		
		return str_camelcased;
	}
	
	public boolean isUserAdmin(String userID) {
		try {
			return userManager.isUserAdmin(userID);
		}
		catch(Exception e) {
			return false;
		}
	}
	
	public void setMsgStyle(Image image, Label info, boolean isError) {
		System.out.println(">>>>>>>>>>>> image.isVisible " + image.isVisible());
		if (StringUtils.isNullOrEmpty(info.getValue())) {
			System.out.println(">>>>>>>>>>>> info is null or empty");
			image.setVisible(false);
		} else {
			System.out.println(">>>>>>>>>>>> info is okay");
			info.setVisible(true);
			if(!image.isVisible()) {
				image.setVisible(true);
			}
			if(isError) {
				info.setSclass(globalUtils.getGlobalPropValue(Commons.STYLE_MSG_ERR));
				image.setSrc(globalUtils.getGlobalPropValue(Commons.IMG_SRC_MSG_ERR));
			}
			else {
				info.setSclass(globalUtils.getGlobalPropValue(Commons.STYLE_MSG_INFO));
				image.setSrc(globalUtils.getGlobalPropValue(Commons.IMG_SRC_MSG_INFO));
			}
		}
	}
	
}
