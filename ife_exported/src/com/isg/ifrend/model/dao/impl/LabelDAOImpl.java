package com.isg.ifrend.model.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.isg.ifrend.model.bean.HstLabel;
import com.isg.ifrend.model.bean.Label;
import com.isg.ifrend.model.bean.TmpLabel;
import com.isg.ifrend.utils.Commons;
import com.isg.ifrend.utils.GlobalUtils;

public class LabelDAOImpl extends GlobalDAOImpl<Label, TmpLabel, HstLabel> {

	private static final String MST_GLOBAL_NAME = "Label";
	private static final String SEQUENCE_NAME = "SEQ_LABELS";

	private static final String PROPERTY_LABEL_NATIVE_STRING = "labelNativeString";
	private static final String PROPERTY_LABEL_LANGUAGE_CODE = "labelLanguageCode";
	private static final String PROPERTY_LABEL_NAME = "labelName";

	@Override
	protected void setMstSearchCriteria(Label m, DetachedCriteria criteria) {
		if (null != m.getLabelName() && !m.getLabelName().isEmpty()) {
			criteria.add(Restrictions.eq(PROPERTY_LABEL_NAME, m.getLabelName()));
		}
		if (null != m.getLabelLanguageCode()
				&& !m.getLabelLanguageCode().isEmpty()) {
			criteria.add(Restrictions.eq(PROPERTY_LABEL_LANGUAGE_CODE,
					m.getLabelLanguageCode()));
		}
		if (null != m.getLabelNativeString()
				&& !m.getLabelNativeString().isEmpty()) {
			criteria.add(Restrictions.eq(PROPERTY_LABEL_NATIVE_STRING,
					m.getLabelNativeString()));
		}
	}

	@Override
	protected void setTmpSearchCriteria(TmpLabel t, DetachedCriteria criteria) {
		if (null != t.getLabelName() && !t.getLabelName().isEmpty()) {
			criteria.add(Restrictions.eq(PROPERTY_LABEL_NAME, t.getLabelName()));
		}
		if (null != t.getLabelLanguageCode()
				&& !t.getLabelLanguageCode().isEmpty()) {
			criteria.add(Restrictions.eq(PROPERTY_LABEL_LANGUAGE_CODE,
					t.getLabelLanguageCode()));
		}
		if (null != t.getLabelNativeString()
				&& !t.getLabelNativeString().isEmpty()) {
			criteria.add(Restrictions.eq(PROPERTY_LABEL_NATIVE_STRING,
					t.getLabelNativeString()));
		}
	}

	@Override
	protected void setGlobalNewId(TmpLabel t) {
		t.setId(GlobalUtils.formatID(Commons.PREFIX_LABEL,
				getSeqNextVal(SEQUENCE_NAME)));
	}

	@Override
	protected String getMstGlobalName() {
		return MST_GLOBAL_NAME;
	}

	@Override
	protected Class<Label> getMstGlobalClass() {
		return Label.class;
	}

	@Override
	protected Class<TmpLabel> getTmpGlobalClass() {
		return TmpLabel.class;
	}

	@Override
	protected Label getNewMstInstance() {
		return new Label();
	}

	@Override
	protected TmpLabel getNewTmpInstance() {
		return new TmpLabel();
	}

	@Override
	protected Label toMst(TmpLabel t) {
		return new Label(t);
	}

	@Override
	protected HstLabel toHst(TmpLabel tmp) {
		return new HstLabel(tmp);
	}

	@Override
	protected Label getUniqueAttributes(TmpLabel t) {
		Label m = new Label();
		m.setLabelName(t.getLabelName());
		m.setLabelLanguageCode(t.getLabelLanguageCode());
		return m;
	}
}