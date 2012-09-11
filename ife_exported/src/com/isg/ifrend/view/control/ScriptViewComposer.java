/**
 * 
 */
package com.isg.ifrend.view.control;

import org.zkoss.zul.Label;

import com.isg.ifrend.model.bean.CommentType;
import com.isg.ifrend.model.bean.FunctionType;
import com.isg.ifrend.model.bean.HstScript;
import com.isg.ifrend.model.bean.MessageType;
import com.isg.ifrend.model.bean.Script;
import com.isg.ifrend.model.bean.TmpScript;
import com.isg.ifrend.service.FunctionManager;
import com.isg.ifrend.service.GlobalManager;
import com.isg.ifrend.service.PriorityManager;
import com.isg.ifrend.service.ServiceLocator;
import com.isg.ifrend.service.UserFieldManager;
import com.isg.ifrend.utils.Commons;

/**
 * @author kristine.furto
 * 
 */
public class ScriptViewComposer extends GlobalViewComposer<Script, TmpScript, HstScript> {

	private static final long serialVersionUID = -2197768747330045397L;

	private Label functionTypeLbl;
	private Label functionIdLbl;
	private Label userFieldLbl;
	private Label priorityLbl;
	private Label descLbl;
	private Label messageTypeLbl;
	private Label messageFldLbl;
	private Label commentTypeLbl;
	private Label commentLbl;

	// service
	private FunctionManager functionManager = ServiceLocator
			.getFunctionManager();
	private UserFieldManager userFieldManager = ServiceLocator
			.getUserFieldManager();
	private PriorityManager priorityManager = ServiceLocator
			.getPriorityManager();

	@Override
	protected GlobalManager<Script, TmpScript> getManager() {
		return ServiceLocator.getScriptManager();
	}

	@Override
	protected TmpScript toTmp(Script m) {
		return new TmpScript(m);
	}

	@Override
	protected void setViewValue() {

		functionTypeLbl.setValue(FunctionType.SCRIPT.getDesc());

		functionIdLbl.setValue(functionManager.findById(
				this.tmpGlobal.getFunctionCode()).getFunction_desc());

		userFieldLbl.setValue(userFieldManager.findById(
				this.tmpGlobal.getUserFieldID()).getUserFieldDesc());

		priorityLbl.setValue(priorityManager.findById(
				this.tmpGlobal.getPriorityID()).getPriorityDesc());

		descLbl.setValue(this.tmpGlobal.getDesc());
		messageTypeLbl.setValue(MessageType.getDesc(this.tmpGlobal
				.getMessageTypeID()));
		messageFldLbl.setValue(this.tmpGlobal.getMessage());
		commentTypeLbl.setValue(CommentType.getDesc(this.tmpGlobal
				.getCommentTypeID()));
		commentLbl.setValue(this.tmpGlobal.getComment());

	}

	@Override
	protected String getAddUrl() {
		return Commons.URL_ADD_SCRIPT;
	}

	@Override
	protected String getViewUrl() {
		return Commons.URL_VIEW_SCRIPT;
	}

	@Override
	protected String getUpdateUrl() {
		return Commons.URL_UPDATE_SCRIPT;
	}

	@Override
	protected TmpScript toTmpFromHst(HstScript h) {
		return new TmpScript(h);
	}

	@Override
	protected Class<HstScript> getHstGlobalClass() {
		return HstScript.class;
	}
}
