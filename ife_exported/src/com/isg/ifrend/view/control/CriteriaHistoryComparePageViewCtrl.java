/**
 * 
 */
package com.isg.ifrend.view.control;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;

import com.isg.ifrend.model.bean.Criteria;
import com.isg.ifrend.model.bean.FunctionType;
import com.isg.ifrend.model.bean.HistCriteria;
import com.isg.ifrend.model.bean.TempCriteria;
import com.isg.ifrend.service.CriteriaManager;
import com.isg.ifrend.service.HistoryCriteriaManager;
import com.isg.ifrend.service.SelectedManager;
import com.isg.ifrend.service.ServiceLocator;
import com.isg.ifrend.utils.Commons;
import com.isg.ifrend.utils.GlobalUtils;

/**
 * @author edward.ruiz
 *
 */
public class CriteriaHistoryComparePageViewCtrl extends GenericForwardComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6905881875095323169L;
	private Label mst_fail_comment;
	private Label mst_pass_comment;
	private Label mst_fail_commenttype;
	private Label mst_pass_commenttype;
	private Label mst_fail_lettercode;
	private Label mst_pass_lettercode;
	private Label mst_fail_message;
	private Label mst_pass_message;
	private Label mst_fail_message_type;
	private Label mst_pass_message_type;
	private Label mst_fail_action;
	private Label mst_pass_action;
	private Label mst_description;
	private Label mst_priority;
	private Label mst_userfield;
	private Label mst_function_code;
	private Label mst_function_type;
	private Label mst_country_code;
	private Label history_fail_comment;
	private Label history_pass_comment;
	private Label history_fail_commenttype;
	private Label history_pass_commenttype;
	private Label history_fail_lettercode;
	private Label history_pass_lettercode;
	private Label history_fail_message;
	private Label history_pass_message;
	private Label history_fail_message_type;
	private Label history_pass_message_type;
	private Label history_fail_action;
	private Label history_pass_action;
	private Label history_description;
	private Label history_priority;
	private Label history_userfield;
	private Label history_function_code;
	private Label history_function_type;
	private Label history_country_code;
	private AnnotateDataBinder binder;
	
	Criteria criteria = new Criteria();
	TempCriteria tempcriteria = new TempCriteria();
	HistCriteria histcriteria = new HistCriteria();
	
	private String criteriaID = "";
	
	private GlobalUtils globalUtils = GlobalUtils.getGlobalUtilsInstance();
	
	SelectedManager selectedManager = ServiceLocator.getSelectedManager();
	CriteriaManager criManager = ServiceLocator.getCriteriaManager();
	HistoryCriteriaManager historyCriteriaManager = ServiceLocator.getHistoryCriteriaManager();
	
	
	/**
	 *
	 *
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		// TODO Auto-generated method stub
		criteriaID = Executions.getCurrent().getParameter("mid");
		/** String criteriaID = Executions.getCurrent().getParameter("criteria_id"); **/
		
		int cID;
		
		if(criteriaID == null){
			cID = 0;
		}else{
			cID = Integer.valueOf(criteriaID);
		}
		criteria = (Criteria)criManager.findByIdMaster(cID);
		histcriteria = (HistCriteria) historyCriteriaManager.findByID(cID);
		
		/*this.setValueLabel(histcriteria);*/
		comp.setAttribute(comp.getId() + "Control", this, true);
		Populate_Fields(histcriteria, criteria);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
	}
	
	public void Populate_Fields(HistCriteria histcriteria, Criteria criteria){
		mst_fail_comment.setValue(criteria.getFail_comment());
		mst_pass_comment.setValue(criteria.getPass_comment());
		mst_fail_commenttype.setValue(String.valueOf(criteria.getFail_commenttype_id()));
		mst_pass_commenttype.setValue(String.valueOf(criteria.getPass_commenttype_id()));
		mst_fail_lettercode.setValue(String.valueOf(criteria.getFail_lettercode_id()));
		mst_pass_lettercode.setValue(String.valueOf(criteria.getPass_lettercode_id()));
		mst_fail_message.setValue(criteria.getFail_message());
		mst_pass_message.setValue(criteria.getPass_message());
		mst_fail_message_type.setValue(String.valueOf(criteria.getFail_messagetype_id()));
		mst_pass_message_type.setValue(String.valueOf(criteria.getPass_messagetype_id()));
		mst_fail_action.setValue(String.valueOf(criteria.getFail_action_id()));
		mst_pass_action.setValue(String.valueOf(criteria.getPass_action_id()));
		mst_description.setValue(criteria.getDescription());
		mst_priority.setValue(String.valueOf(criteria.getPriority_id()));
		mst_userfield.setValue(String.valueOf(criteria.getUser_field_id()));
		mst_function_code.setValue(String.valueOf(criteria.getFunction_id()));
		mst_function_type.setValue(FunctionType.CRITERIA.getDesc());
		mst_country_code.setValue(String.valueOf(criteria.getCountry_code()));
		
		
		history_fail_comment.setValue(histcriteria.getFail_comment());
		history_pass_comment.setValue(histcriteria.getPass_comment());
		history_fail_commenttype.setValue(String.valueOf(histcriteria.getFail_commenttype_id()));
		history_pass_commenttype.setValue(String.valueOf(histcriteria.getPass_commenttype_id()));
		history_fail_lettercode.setValue(String.valueOf(histcriteria.getFail_lettercode_id()));
		history_pass_lettercode.setValue(String.valueOf(histcriteria.getPass_lettercode_id()));
		history_fail_message.setValue(histcriteria.getFail_message());
		history_pass_message.setValue(histcriteria.getPass_message());
		history_fail_message_type.setValue(String.valueOf(histcriteria.getFail_messagetype_id()));
		history_pass_message_type.setValue(String.valueOf(histcriteria.getPass_messagetype_id()));
		history_fail_action.setValue(String.valueOf(histcriteria.getFail_action_id()));
		history_pass_action.setValue(String.valueOf(histcriteria.getPass_action_id()));
		history_description.setValue(histcriteria.getDescription());
		history_priority.setValue(String.valueOf(histcriteria.getPriority_id()));
		history_userfield.setValue(String.valueOf(histcriteria.getUser_field_id()));
		history_function_code.setValue(String.valueOf(histcriteria.getFunction_id()));
		history_function_type.setValue(FunctionType.CRITERIA.getDesc());
		history_country_code.setValue(String.valueOf(histcriteria.getCountry_code()));
		
	}
	
	
	public void onClick$btn_back() throws InterruptedException{
		
		final String url_ = globalUtils.returnPropValue(Commons.GLOBAL_PROPERTIES, Commons.URL_VIEW_CRITERIA) + 
		  "?mid=" + criteriaID;
		
		Messagebox.show("Back to View History Criterion?", "back", Messagebox.OK|Messagebox.CANCEL, "", new EventListener() {

			@Override
			public void onEvent(Event e) throws Exception {
				// TODO Auto-generated method stub
				if(e.getName().equals("onOK")){
					Executions.getCurrent().sendRedirect(url_);
				}
			}
		});
	}

}
