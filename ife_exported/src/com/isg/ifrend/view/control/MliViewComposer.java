/**
 * 
 */
package com.isg.ifrend.view.control;

import org.zkoss.zul.Label;

import com.isg.ifrend.model.bean.Code;
import com.isg.ifrend.model.bean.CommentType;
import com.isg.ifrend.model.bean.FunctionType;
import com.isg.ifrend.model.bean.HstMli;
import com.isg.ifrend.model.bean.MessageType;
import com.isg.ifrend.model.bean.Mli;
import com.isg.ifrend.model.bean.TmpMli;
import com.isg.ifrend.service.GlobalManager;
import com.isg.ifrend.service.ServiceLocator;
import com.isg.ifrend.service.UserFieldManager;
import com.isg.ifrend.service.impl.GenericManagerImpl;
import com.isg.ifrend.utils.Commons;

/**
 * @author kristine.furto
 * 
 */
public class MliViewComposer extends GlobalViewComposer<Mli, TmpMli, HstMli> {

	private static final long serialVersionUID = -3385924133328564229L;

	private Label functionTypeLbl;
	private Label functionIdLbl;
	private Label userFieldLbl;
	private Label responseCodeLbl;
	private Label descLbl;
	private Label messageTypeLbl;
	private Label messageFldLbl;
	private Label commentTypeLbl;
	private Label commentLbl;

	// service
	private UserFieldManager userFieldManager = ServiceLocator
			.getUserFieldManager();

	@Override
	protected GlobalManager<Mli, TmpMli> getManager() {
		return ServiceLocator.getMliManager();
	}

	@Override
	protected TmpMli toTmp(Mli m) {
		return new TmpMli(m);
	}

	@Override
	protected void setViewValue() {
		functionTypeLbl.setValue(FunctionType.MLI.getDesc());
		functionIdLbl.setValue(Commons.FUNCTION_MLI);
		
		userFieldLbl.setValue(userFieldManager.findById(
				this.tmpGlobal.getUserFieldID()).getUserFieldDesc());
		GenericManagerImpl genericManager = ServiceLocator.getGenericManager();
		responseCodeLbl.setValue(genericManager.viewDetails(Code.class,
				String.valueOf(this.tmpGlobal.getResponseCodeID())).getCodeForDisplay());
		
		descLbl.setValue(this.tmpGlobal.getDesc());
		messageTypeLbl.setValue(MessageType.getDesc(this.tmpGlobal
				.getMessageTypeID()));
		messageFldLbl.setValue(this.tmpGlobal.getMessage());
		commentTypeLbl.setValue(CommentType.getDesc(this.tmpGlobal
				.getCommentTypeID()));
		commentLbl.setValue(this.tmpGlobal.getComment());
		

	}

	@Override
	protected String getViewUrl() {
		return Commons.URL_VIEW_MLI;
	}

	@Override
	protected String getAddUrl() {
		return Commons.URL_ADD_MLI;
	}

	@Override
	protected String getUpdateUrl() {
		return Commons.URL_UPDATE_MLI;
	}

	@Override
	protected TmpMli toTmpFromHst(HstMli h) {
		return new TmpMli(h);
	}

	@Override
	protected Class<HstMli> getHstGlobalClass() {
		return HstMli.class;
	}
}
