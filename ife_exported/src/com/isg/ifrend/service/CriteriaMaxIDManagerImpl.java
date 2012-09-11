package com.isg.ifrend.service;

import org.springframework.dao.DataAccessException;

import com.isg.ifrend.model.bean.CriteriaMaxID;
import com.isg.ifrend.model.dao.CriteriaMaxIDDAO;

public class CriteriaMaxIDManagerImpl implements CriteriaMaxIDManager {

		private CriteriaMaxIDDAO criteriaMaxIDDAO;

		public CriteriaMaxIDDAO getCriteriaMaxIDDAO() {
			return criteriaMaxIDDAO;
		}

		public void setCriteriaMaxIDDAO(CriteriaMaxIDDAO criteriaMaxIDDAO) {
			this.criteriaMaxIDDAO = criteriaMaxIDDAO;
		}

		@Override
		public boolean save(CriteriaMaxID criteriaMaxID) {
			try {
				criteriaMaxIDDAO.save(criteriaMaxID);
				return true;
			} catch (DataAccessException e) {
				e.printStackTrace();
				return false;
			}
		}

		@Override
		public boolean delete(CriteriaMaxID criteriaMaxID) {
			try {
				criteriaMaxIDDAO.delete(criteriaMaxID);
				return true;
			} catch (DataAccessException e) {
				e.printStackTrace();
				return false;
			}
		}

		@Override
		public int getCriteriaMaxID() {
			return criteriaMaxIDDAO.findMaxID(CriteriaMaxID.class);
		}
}
