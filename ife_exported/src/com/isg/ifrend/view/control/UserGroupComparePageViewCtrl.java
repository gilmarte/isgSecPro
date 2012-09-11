/**
 * 
 */
package com.isg.ifrend.view.control;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

import com.isg.ifrend.model.bean.SaSelectedFunctions;
import com.isg.ifrend.model.bean.SaUserGroup;
import com.isg.ifrend.model.bean.SaUserGroupHistory;
import com.isg.ifrend.model.bean.SelectedFunctions;
import com.isg.ifrend.model.bean.TmpSaUserGroup;
import com.isg.ifrend.service.SaUserGroupHistoryManager;
import com.isg.ifrend.service.SaUserGroupManager;
import com.isg.ifrend.service.ServiceLocator;
import com.isg.ifrend.service.TmpSaUserGroupManager;
import com.isg.ifrend.utils.Commons;
import com.isg.ifrend.utils.SecurityAdminUtils;

/**
 * @author gerald.deguzman
 *
 */

public class UserGroupComparePageViewCtrl extends GenericForwardComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	AnnotateDataBinder binder;

	@SuppressWarnings("unused")
	private Window userGroupComparePage;
	private Label m_status;
	private Label m_action;
	private Label m_description;
	private Label m_group_id;
	/*private Splitter s1;*/
	private Label t_status;
	private Label t_action;
	private Label t_description;
	private Label t_group_id;
	@SuppressWarnings("unused")
	private Listbox src;
	@SuppressWarnings("unused")
	private Listbox dst;


	private TmpSaUserGroupManager tmp_sausergroup_manager = ServiceLocator.getTmpSaUserGroupManager();
	private SaUserGroupManager sa_usergroup_manager = ServiceLocator.getSaUserGroupManager();
	private SaUserGroupHistoryManager sa_usergroup_history_manager = ServiceLocator.getSaUserGroupHistoryManager();

//	private SelectedFunctionsManager selected_functions_manager = ServiceLocator.getSelectedFunctionsManager();
//	private SaSelectedFunctionsManager sa_selected_functions_manager = ServiceLocator.getSaSelectedFunctionsManager();

	private TmpSaUserGroup tmp_sausergroup = new TmpSaUserGroup();
	private SaUserGroup sausergroup = new SaUserGroup();
	private SaUserGroupHistory sausergroup_history = new SaUserGroupHistory();

	private List<SelectedFunctions>lst_selectedfunctions = new ArrayList<SelectedFunctions>();
	private List<SaSelectedFunctions>lst_saselectedfunctions = new ArrayList<SaSelectedFunctions>();

	private ListModelList lml_selectedfunctions;
	private ListModelList lml_saselectedfunctions;
	private String previous_page = "";

	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		binder = new AnnotateDataBinder();

		String _group_id = Executions.getCurrent().getParameter("group_id");
		previous_page = Executions.getCurrent().getParameter(Commons.PREVIOUS_PAGE);

		tmp_sausergroup = tmp_sausergroup_manager.getTmpSaUserGroup(_group_id);
		sausergroup = sa_usergroup_manager.getSaUserGroup(_group_id);
		

		self.setAttribute(self.getId(), this);

		if(previous_page.equalsIgnoreCase("history_page")){	
			
			/*if from history page then get seuence id*/
			Integer seq_id_ =  Integer.valueOf(Executions.getCurrent().getParameter("seq_id"));
			sausergroup_history = sa_usergroup_history_manager.getSaUserGroupHistory(_group_id, seq_id_);
			
			Populate_Fields(sausergroup_history, sausergroup);
		}else if(previous_page.equalsIgnoreCase("authorize_page")){
			Populate_Fields(tmp_sausergroup, sausergroup);
		}		

		binder.loadAll();

	}

	public List<SelectedFunctions> getLst_selectedfunctions() {
		return lst_selectedfunctions;
	}

	public void setLst_selectedfunctions(
			List<SelectedFunctions> lst_selectedfunctions) {
		this.lst_selectedfunctions = lst_selectedfunctions;
	}

	public List<SaSelectedFunctions> getLst_saselectedfunctions() {
		return lst_saselectedfunctions;
	}

	public void setLst_saselectedfunctions(
			List<SaSelectedFunctions> lst_saselectedfunctions) {
		this.lst_saselectedfunctions = lst_saselectedfunctions;
	}

	public ListModelList getLml_selectedfunctions() {
		return lml_selectedfunctions;
	}

	public void setLml_selectedfunctions(ListModelList lml_selectedfunctions) {
		this.lml_selectedfunctions = lml_selectedfunctions;
	}

	public ListModelList getLml_saselectedfunctions() {
		return lml_saselectedfunctions;
	}

	public void setLml_saselectedfunctions(ListModelList lml_saselectedfunctions) {
		this.lml_saselectedfunctions = lml_saselectedfunctions;
	}

	public void Populate_Fields(TmpSaUserGroup tmpsausergroup_, SaUserGroup sausergroup_){

		String style_ = "background:yellow";

		if(tmpsausergroup_ == null ){
			tmpsausergroup_ = new TmpSaUserGroup();
		}

		if(sausergroup_ == null){
			sausergroup_ = new SaUserGroup();
		}else{
			if(sausergroup_.getStatus().equals(Commons.SA_STATUS_DELETED)){
				sausergroup_.setStatus("");
				sausergroup_.setDescription("");
				sausergroup_.getSa_selectedfunctions().removeAll(sausergroup_.getSa_selectedfunctions());
			}
		}


		t_group_id.setValue(tmpsausergroup_.getGroup_id());
		t_description.setValue(tmpsausergroup_.getDescription());
		t_status.setValue(tmpsausergroup_.getStatus());
		t_action.setValue(tmpsausergroup_.getAction());

		m_group_id.setValue(sausergroup_.getGroup_id());
		m_description.setValue(sausergroup_.getDescription());
		m_status.setValue(sausergroup_.getStatus());
		m_action.setValue(sausergroup_.getAction());

		if(!tmpsausergroup_.getGroup_id().equals(sausergroup_.getGroup_id())){
			t_group_id.setStyle(style_);
			m_group_id.setStyle(style_);
		}
		if(!tmpsausergroup_.getDescription().equals(sausergroup_.getDescription())){
			t_description.setStyle(style_);
			m_description.setStyle(style_);
		}
		
		if(!tmpsausergroup_.getStatus().equals(sausergroup_.getStatus())){
			t_status.setStyle(style_);
			m_status.setStyle(style_);
		}


		/*
		 * Populate the list
		 * */
		lst_selectedfunctions = tmpsausergroup_.getSelectedfunctions();
		lst_saselectedfunctions = sausergroup_.getSa_selectedfunctions();

		lml_selectedfunctions = new ListModelList(lst_selectedfunctions);
		lml_saselectedfunctions = new ListModelList(lst_saselectedfunctions);

		/*src.setModel(lml_selectedfunctions);
		dst.setModel(lml_saselectedfunctions);*/
	}

	public void Populate_Fields(SaUserGroupHistory sausergrouphistory_, SaUserGroup sausergroup_){

		String style_ = "background:yellow";

		
		
		if(sausergrouphistory_ == null ){
			sausergrouphistory_ = new SaUserGroupHistory();
		}

		if(sausergroup_ == null){
			sausergroup_ = new SaUserGroup();
		}else{
			if(sausergroup_.getStatus().equals(Commons.SA_STATUS_DELETED)){
				sausergroup_.setStatus("");
				sausergroup_.setDescription("");
				sausergroup_.getSa_selectedfunctions().removeAll(sausergroup_.getSa_selectedfunctions());
			}
		}


		t_group_id.setValue(sausergrouphistory_.getGroup_id());
		t_description.setValue(sausergrouphistory_.getDescription());
		t_status.setValue(sausergrouphistory_.getStatus());
		t_action.setValue(sausergrouphistory_.getAction());

		m_group_id.setValue(sausergroup_.getGroup_id());
		m_description.setValue(sausergroup_.getDescription());
		m_status.setValue(sausergroup_.getStatus());
		m_action.setValue(sausergroup_.getAction());

		if(!sausergrouphistory_.getGroup_id().equals(sausergroup_.getGroup_id())){
			t_group_id.setStyle(style_);
			m_group_id.setStyle(style_);
		}
		if(!sausergrouphistory_.getDescription().equals(sausergroup_.getDescription())){
			t_description.setStyle(style_);
			m_description.setStyle(style_);
		}
		
		if(!sausergrouphistory_.getStatus().equals(sausergroup_.getStatus())){
			t_status.setStyle(style_);
			m_status.setStyle(style_);
		}

		 
		lst_selectedfunctions = new ArrayList<SelectedFunctions>();
		lst_saselectedfunctions = sausergroup_.getSa_selectedfunctions();

		lml_selectedfunctions = new ListModelList(lst_selectedfunctions);
		lml_saselectedfunctions = new ListModelList(lst_saselectedfunctions);

		/*src.setModel(lml_selectedfunctions);
		dst.setModel(lml_saselectedfunctions);*/
	}
	
	public void onClick$btn_back_top()throws InterruptedException{
		
		if(previous_page.equals("history_page")){
			SecurityAdminUtils.Redirect_to_Page(Commons.URL_HISTORY_USERGROUP);
		}else if(previous_page.equals("authorize_page")){
			SecurityAdminUtils.Redirect_to_Page(Commons.URL_AUTHORIZE_USERGROUP);
		}		
	}
	
	public void onClick$btn_back_foot()throws InterruptedException{
		
		if(previous_page.equals("history_page")){
			SecurityAdminUtils.Redirect_to_Page(Commons.URL_HISTORY_USERGROUP);
		}else if(previous_page.equals("authorize_page")){
			SecurityAdminUtils.Redirect_to_Page(Commons.URL_AUTHORIZE_USERGROUP);
		}		
	}
	

}
