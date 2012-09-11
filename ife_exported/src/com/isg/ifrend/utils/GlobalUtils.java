package com.isg.ifrend.utils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Properties;

import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.isg.ifrend.model.bean.Action;
import com.isg.ifrend.service.ActionManager;
import com.isg.ifrend.service.ServiceLocator;


public class GlobalUtils {
	
	private static GlobalUtils globalUtilsInstance;
	private static Properties globalProperties;
	private static Properties messageProperties;

	public static GlobalUtils getGlobalUtilsInstance() {
		if (globalUtilsInstance == null) {
			globalUtilsInstance = new GlobalUtils();
			globalProperties = globalUtilsInstance.returnProperty(Commons.GLOBAL_PROPERTIES);
			messageProperties = globalUtilsInstance.returnProperty(Commons.MESSAGES_PROPERTIES_EN);
		}
		return globalUtilsInstance;
	}
	
	public String getGlobalPropValue(String key) {
		return globalProperties.getProperty(key);
	}
	
	public String getMessagePropValue(String key) {
		return messageProperties.getProperty(key);
	}
	
	private Properties returnProperty (String propertyFile) {
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(propertyFile);  

		Properties properties = new Properties();  
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}  
		return properties;
	}

	public String returnPropValue(String propertyFile, String key)
	{
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(propertyFile);  

		Properties properties = new Properties();  
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}  
		return properties.getProperty(key);
	}

	public static List<Action> removeActionItemList(int criteria){

		ActionManager actionManager = ServiceLocator.getActionManager();
		List<Action> baseList = actionManager.getActionList();
		List<Action> modifiedList = actionManager.getActionList();

		for (int i=0;i<baseList.size();i++){
			Action actionBean = baseList.get(i);
			if (actionBean.getAction_category() == criteria){
				modifiedList.remove(actionBean);
			}
		}
		return modifiedList;
	}

	public static String determineOperator(String operator){

		String sign = null;
		if (operator.equalsIgnoreCase(Commons.OPERATOR_EQUALS)){
			sign = " = ";
		}

		if (operator.equalsIgnoreCase(Commons.OPERATOR_NOT_EQUAL)){
			sign = " != ";
		}

		if (operator.equalsIgnoreCase(Commons.OPERATOR_GREATER)){
			sign = " > ";
		}

		if (operator.equalsIgnoreCase(Commons.OPERATOR_GREATER_OR_EQUAL)){
			sign = " >= ";
		}

		if (operator.equalsIgnoreCase(Commons.OPERATOR_LESSER)){
			sign = " < ";
		}

		if (operator.equalsIgnoreCase(Commons.OPERATOR_LESSER_OR_EQUAL)){
			sign = " <= ";
		}

		return sign;
	}

	public static Throwable getRootCauseException(Throwable e) {
		while (true) {
			Throwable cause = e.getCause();
			if (cause == null)
				return e;
			e = (Exception)cause;
		  }
	}

	public static int showMessage (String message, String header, int messageButtonCode, String messageType) {
		try {
//			return Messagebox.show(message, header, messageButtonCode, messageType);
			int test = -1;
			test = Messagebox.show(message, header, messageButtonCode, messageType);
			return test;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return Messagebox.ABORT;
		}
	}

	/**
	 * Formats ID for display
	 * @param prefix
	 * @param id
	 * @return
	 */
	public static String formatID(String prefix, String id) {
		GlobalUtils globalUtils = new GlobalUtils();
		String formattedID = StringUtil.lPad(globalUtils.returnPropValue(Commons.GLOBAL_PROPERTIES, prefix), id, Commons.ID_NUM_LEN, Commons.STRING_ZERO);
		return formattedID.toString();
	}
	
	/**
	 * For DEBUGGING: Invokes getter methods (methods which names start 
	 * with "get" or "is") from an object.
	 * 
	 * @param obj
	 * @return Returns a string of all returned objects.
	 */
	@SuppressWarnings("rawtypes")
	public static String invokeGetters(Object obj) {
		String toString = "\n";

		Class clazz = obj.getClass();
		Method[] methods = clazz.getMethods();

		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			String methodName = method.getName();

			if (methodName.startsWith("get") || methodName.startsWith("is")) {
				try {
					toString += methodName + ": "
							+ method.invoke(obj, (Object[]) null) + "\n";
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}

		return toString;
	}

	/**
	 * This is a generic validator for fields. 
	 * 
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
		GlobalUtils globalUtils = new GlobalUtils();
		boolean hasError = false;

		for(Object obj_: fields_){
			
			if(obj_ instanceof Combobox){
				Combobox combobox = (Combobox) obj_;
				if(combobox.getText().equals("")){
					combobox.setErrorMessage(globalUtils.returnPropValue(Commons.MESSAGES_PROPERTIES_EN, Commons.SA_MSG_SELECT_FROM_LIST));
					hasError = true;
					break;
				}
			}
			
			/*Check if textbox*/
			if(obj_ instanceof Textbox){
				Textbox txtbox = (Textbox) obj_;
				/**Check if null**/
				if(txtbox == null || txtbox.getValue().equals("")){
					txtbox.setErrorMessage(globalUtils.returnPropValue(Commons.MESSAGES_PROPERTIES_EN, Commons.SA_MSG_FIELD_MUST_NOT_BLANK));
					hasError = true;
					break;
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
}