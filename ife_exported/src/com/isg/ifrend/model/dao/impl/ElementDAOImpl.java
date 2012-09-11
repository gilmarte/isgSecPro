package com.isg.ifrend.model.dao.impl;

import org.hibernate.criterion.DetachedCriteria;

import com.isg.ifrend.model.bean.Element;
import com.isg.ifrend.model.bean.HstElement;
import com.isg.ifrend.model.bean.TmpElement;
import com.isg.ifrend.utils.Commons;
import com.isg.ifrend.utils.GlobalUtils;

public class ElementDAOImpl extends
		GlobalDAOImpl<Element, TmpElement, HstElement> {

	private static final String MST_GLOBAL_NAME = "Element";
	private static final String SEQUENCE_NAME_GLOBAL = "SEQ_GLOBAL_ELEMENTS";
	private static final String SEQUENCE_NAME_FUNCTION = "SEQ_FUNCTION_ELEMENTS";

	@Override
	protected void setMstSearchCriteria(Element m, DetachedCriteria criteria) {
	}

	@Override
	protected void setTmpSearchCriteria(TmpElement t, DetachedCriteria criteria) {
	}

	@Override
	protected void setGlobalNewId(TmpElement t) {
		if (t.getTmp_elemtype_id() == Commons.ELEMENT_FUNCTION) {
			t.setId(GlobalUtils.formatID(Commons.PREFIX_ELEM_FUNCTION,
					getSeqNextVal(SEQUENCE_NAME_FUNCTION)));

		} else {
			t.setId(GlobalUtils.formatID(Commons.PREFIX_ELEM_GLOBAL,
					getSeqNextVal(SEQUENCE_NAME_GLOBAL)));
		}
	}

	@Override
	protected String getMstGlobalName() {
		return MST_GLOBAL_NAME;
	}

	@Override
	protected Class<Element> getMstGlobalClass() {
		return Element.class;
	}

	@Override
	protected Class<TmpElement> getTmpGlobalClass() {
		return TmpElement.class;
	}

	@Override
	protected Element getNewMstInstance() {
		return new Element();
	}

	@Override
	protected TmpElement getNewTmpInstance() {
		return new TmpElement();
	}

	@Override
	protected Element toMst(TmpElement t) {
		return new Element(t);
	}

	@Override
	protected HstElement toHst(TmpElement tmp) {
		return new HstElement(tmp);
	}

	@Override
	protected Element getUniqueAttributes(TmpElement t) {
		Element m = new Element();
		return m;
	}
}