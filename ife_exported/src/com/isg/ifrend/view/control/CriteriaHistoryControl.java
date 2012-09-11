package com.isg.ifrend.view.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;

import com.isg.ifrend.model.bean.Criteria;
import com.isg.ifrend.model.bean.HistCriteria;
import com.isg.ifrend.model.bean.TempCriteria;
import com.isg.ifrend.model.bean.User;
import com.isg.ifrend.service.CriteriaManager;
import com.isg.ifrend.service.HistoryCriteriaManager;
import com.isg.ifrend.service.SelectedManager;
import com.isg.ifrend.service.ServiceLocator;
import com.isg.ifrend.utils.Commons;
import com.isg.ifrend.utils.ExportUtil;
import com.isg.ifrend.utils.GlobalUtils;
import com.isg.ifrend.view.control.renderer.CriteriaHistoryListRenderer;

public class CriteriaHistoryControl extends GenericForwardComposer {

//	private Row criteria_history;
	private Listbox criteriaHistoryHeaderListBox;
	private static final long serialVersionUID = 1L;
	private AnnotateDataBinder binder;
	Criteria criteria = new Criteria();
	HistCriteria histcriteria = new HistCriteria();
	TempCriteria tmpcriteria = new TempCriteria();
	private List<HistCriteria> histcriteriaList = new ArrayList<HistCriteria>();
	private CriteriaManager criteriaManager = ServiceLocator.getCriteriaManager();
	private HistoryCriteriaManager historyManager = ServiceLocator.getHistoryCriteriaManager();

	private ListModelList lml_historylist;

	private String criteriaID = "";
	private GlobalUtils globalUtils = GlobalUtils.getGlobalUtilsInstance();
	
	User user = new User();
	SelectedManager selectedManager = ServiceLocator.getSelectedManager();

	@SuppressWarnings("unused")
	@Override
	public void doAfterCompose(Component comp) throws Exception{
		super.doAfterCompose(comp);
		criteriaID = Executions.getCurrent().getParameter(Commons.URL_PARAM_MST_ID);
		int cID;

		if(criteriaID == null){
			cID = 0;			
		}else{		
			cID= Integer.valueOf(criteriaID);
		}

		histcriteriaList = historyManager.findAllHist();
		lml_historylist = new ListModelList(histcriteriaList);
		criteriaHistoryHeaderListBox.setItemRenderer(new CriteriaHistoryListRenderer());

		TempCriteria tcri = (TempCriteria)criteriaManager.findByIdTemp(cID);
		self.setAttribute(comp.getId(), this);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();

	}

	public void gethistory(){
		criteriaManager.getHistCriteriaList();
	}

	/*private void setlistbox(HistCriteria hlist){
		histcriteria.setCountry_code(hlist.getCountry_code());
		histcriteria.setCriteria_id(hlist.getCriteria_id());
		histcriteria.setDescription(hlist.getDescription());
		histcriteria.setCriteria_creator(hlist.getCriteria_creator());
		histcriteria.setCriteria_date_created(hlist.getCriteria_date_created());
		histcriteria.setCriteria_approver(hlist.getCriteria_approver());
		histcriteria.setCriteria_date_approved(hlist.getCriteria_date_approved());
		histcriteria.setCriteria_last_modifier(hlist.getCriteria_last_modifier());
		histcriteria.setCriteria_last_date_modified(hlist.getCriteria_last_date_modified());
		histcriteria.setStatus_id(hlist.getStatus_id());

	}*/

	public void onClick$btn_back() throws InterruptedException{
		
		final String url_ = globalUtils.returnPropValue(Commons.GLOBAL_PROPERTIES, Commons.URL_AUTHORIZE_GLOBAL) + 
		  "?mid=" + criteriaID;
		
		Messagebox.show("Back to View Criterion?", "back", Messagebox.OK|Messagebox.CANCEL, "", new EventListener() {

			@Override
			public void onEvent(Event e) throws Exception {
				// TODO Auto-generated method stub
				if(e.getName().equals("onOK")){
					Executions.getCurrent().sendRedirect(url_);
				}
			}
		});
	}
	
	/** Export to Excel **/
    public void onClick$exportBtn() throws IOException {
    	
	   ExportUtil.exportToExcel("History", criteriaHistoryHeaderListBox);
	}

	public ListModelList getLml_historylist() {
		return lml_historylist;
	}
	public void setLml_historylist(ListModelList lml_historylist) {
		this.lml_historylist = lml_historylist;
	}


}
