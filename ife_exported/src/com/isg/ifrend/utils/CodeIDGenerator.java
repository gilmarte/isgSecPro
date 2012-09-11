package com.isg.ifrend.utils;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IncrementGenerator;

import com.isg.ifrend.model.bean.TmpCode;

public class CodeIDGenerator extends IncrementGenerator {

	@Override
	public Serializable generate(SessionImplementor session, Object object)
			throws HibernateException {

		TmpCode code = (TmpCode) object;

		if (0 == code.getCodeID()) {
			return super.generate(session, object);

		} else {
			return code.getCodeID();
		}
	}
}