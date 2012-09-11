package com.isg.ifrend.model.dao.impl;

import org.hibernate.criterion.DetachedCriteria;

import com.isg.ifrend.model.bean.HstScript;
import com.isg.ifrend.model.bean.Script;
import com.isg.ifrend.model.bean.TmpScript;
import com.isg.ifrend.utils.Commons;
import com.isg.ifrend.utils.GlobalUtils;

/**
 * 
 * @author kristine.furto
 * 
 */
public class ScriptDAOImpl extends GlobalDAOImpl<Script, TmpScript, HstScript> {

	private static final String MST_GLOBAL_NAME = "Script";
	private static final String SEQUENCE_NAME = "SEQ_SCRIPTS";

	@Override
	protected void setMstSearchCriteria(Script m, DetachedCriteria criteria) {
	}

	@Override
	protected void setTmpSearchCriteria(TmpScript t, DetachedCriteria criteria) {
	}

	@Override
	protected void setGlobalNewId(TmpScript t) {
		t.setId(GlobalUtils.formatID(Commons.PREFIX_SCRIPT,
				getSeqNextVal(SEQUENCE_NAME)));
	}

	@Override
	protected String getMstGlobalName() {
		return MST_GLOBAL_NAME;
	}

	@Override
	protected Class<Script> getMstGlobalClass() {
		return Script.class;
	}

	@Override
	protected Class<TmpScript> getTmpGlobalClass() {
		return TmpScript.class;
	}

	@Override
	protected Script getNewMstInstance() {
		return new Script();
	}

	@Override
	protected TmpScript getNewTmpInstance() {
		return new TmpScript();
	}

	@Override
	protected Script toMst(TmpScript t) {
		return new Script(t);
	}

	@Override
	protected HstScript toHst(TmpScript tmp) {
		return new HstScript(tmp);
	}

	@Override
	protected Script getUniqueAttributes(TmpScript t) {
		Script m = new Script();
		return m;
	}
}