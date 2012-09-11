package com.isg.ifrend.model.dao.impl;

import org.hibernate.criterion.DetachedCriteria;

import com.isg.ifrend.model.bean.HstMli;
import com.isg.ifrend.model.bean.Mli;
import com.isg.ifrend.model.bean.TmpMli;
import com.isg.ifrend.utils.Commons;
import com.isg.ifrend.utils.GlobalUtils;

/**
 * 
 * @author kristine.furto
 * 
 */
public class MliDAOImpl extends GlobalDAOImpl<Mli, TmpMli, HstMli> {

	private static final String MST_GLOBAL_NAME = "Mli";
	private static final String SEQUENCE_NAME = "SEQ_MLI";

	@Override
	protected void setMstSearchCriteria(Mli m, DetachedCriteria criteria) {
	}

	@Override
	protected void setTmpSearchCriteria(TmpMli t, DetachedCriteria criteria) {
	}

	@Override
	protected void setGlobalNewId(TmpMli t) {
		t.setId(GlobalUtils.formatID(Commons.PREFIX_MLI,
				getSeqNextVal(SEQUENCE_NAME)));
	}

	@Override
	protected String getMstGlobalName() {
		return MST_GLOBAL_NAME;
	}

	@Override
	protected Class<Mli> getMstGlobalClass() {
		return Mli.class;
	}

	@Override
	protected Class<TmpMli> getTmpGlobalClass() {
		return TmpMli.class;
	}

	@Override
	protected Mli getNewMstInstance() {
		return new Mli();
	}

	@Override
	protected TmpMli getNewTmpInstance() {
		return new TmpMli();
	}

	@Override
	protected Mli toMst(TmpMli t) {
		return new Mli(t);
	}

	@Override
	protected HstMli toHst(TmpMli tmp) {
		return new HstMli(tmp);
	}

	@Override
	protected Mli getUniqueAttributes(TmpMli t) {
		Mli m = new Mli();
		return m;
	}
}