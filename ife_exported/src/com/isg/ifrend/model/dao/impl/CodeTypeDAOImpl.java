package com.isg.ifrend.model.dao.impl;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.isg.ifrend.model.bean.Code;
import com.isg.ifrend.model.bean.CodeType;
import com.isg.ifrend.model.bean.HstCodeType;
import com.isg.ifrend.model.bean.TmpCode;
import com.isg.ifrend.model.bean.TmpCodeType;
import com.isg.ifrend.utils.Commons;
import com.isg.ifrend.utils.GlobalUtils;

public class CodeTypeDAOImpl extends
		GlobalDAOImpl<CodeType, TmpCodeType, HstCodeType> {

	private static final String GLOBAL_NAME = "CodeType";
	private static final String SEQUENCE_NAME = "SEQ_CODETYPES";

	private static final String PROPERTY_CODETYPE_DESC = "desc";

	@Override
	protected void setMstSearchCriteria(CodeType m, DetachedCriteria criteria) {
		if (null != m.getDesc() && !m.getDesc().isEmpty()) {
			criteria.add(Restrictions.eq(PROPERTY_CODETYPE_DESC, m.getDesc()));
		}
	}

	@Override
	protected void setTmpSearchCriteria(TmpCodeType t, DetachedCriteria criteria) {
		if (null != t.getDesc() && !t.getDesc().isEmpty()) {
			criteria.add(Restrictions.eq(PROPERTY_CODETYPE_DESC, t.getDesc()));
		}
	}

	@Override
	protected void setGlobalNewId(TmpCodeType t) {
		t.setId(GlobalUtils.formatID(Commons.PREFIX_CODETYPE,
				getSeqNextVal(SEQUENCE_NAME)));
	}

	@Override
	protected String getMstGlobalName() {
		return GLOBAL_NAME;
	}

	@Override
	protected Class<CodeType> getMstGlobalClass() {
		return CodeType.class;
	}

	@Override
	protected Class<TmpCodeType> getTmpGlobalClass() {
		return TmpCodeType.class;
	}

	@Override
	protected CodeType getNewMstInstance() {
		return new CodeType();
	}

	@Override
	protected TmpCodeType getNewTmpInstance() {
		return new TmpCodeType();
	}

	@Override
	protected CodeType toMst(TmpCodeType t) {
		return new CodeType(t);
	}

	@Override
	protected HstCodeType toHst(TmpCodeType t) {
		return new HstCodeType(t);
	}

	// XXX TmpSolution
	@Override
	public void updateMst(TmpCodeType t) {
		CodeType codeType = (CodeType) getHibernateTemplate().load(
				CodeType.class, t.getCodeTypeID());

		codeType.setCodeTypeID(t.getCodeTypeID());

		codeType.setDesc(t.getDesc());

		Set<Code> codeSetToRemove = new HashSet<Code>();
		Set<TmpCode> tmpCodeSetAlreadyAdded = new HashSet<TmpCode>();

		for (Code code : codeType.getCodeSet()) {
			boolean found = false;
			in: for (TmpCode tmpCode : t.getTmpCodeSet()) {
				if (code.getCodeValue().equals(tmpCode.getCodeValue())) {
					code.setCodeDesc(tmpCode.getCodeDesc());

					tmpCodeSetAlreadyAdded.add(tmpCode);
					found = true;
					break in;
				}

			}

			if (!found) {
				codeSetToRemove.add(code);
			}
		}

		codeType.getCodeSet().removeAll(codeSetToRemove);
		t.getTmpCodeSet().removeAll(tmpCodeSetAlreadyAdded);

		for (TmpCode tmpCode : t.getTmpCodeSet()) {
			codeType.getCodeSet().add(new Code(tmpCode, codeType));
		}

		codeType.setStatusID(t.getStatusID());
		codeType.setActionID(t.getActionID());

		codeType.setCreatorUserID(t.getCreatorUserID());
		codeType.setDateCreated(t.getDateCreated());

		codeType.setLastModifierUserID(t.getLastModifierUserID());
		codeType.setDateLastModified(t.getDateLastModified());

		codeType.setApproverUserID(t.getApproverUserID());
		codeType.setDateApproved(t.getDateApproved());

		getHibernateTemplate().update(codeType);
		getHibernateTemplate().flush();

		t.getTmpCodeSet().addAll(tmpCodeSetAlreadyAdded);
	}

	@Override
	protected CodeType getUniqueAttributes(TmpCodeType t) {
		CodeType m = new CodeType();
		m.setDesc(t.getDesc());
		return m;
	}
}