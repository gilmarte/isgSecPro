package com.isg.ifrend.model.bean;

import java.io.Serializable;

public class CriteriaMaxID  implements Serializable {

	private static final long serialVersionUID = 1L;

	private int criteriaMax_id;
	
	public CriteriaMaxID(){}

	public CriteriaMaxID(int criteriaMax_id) {
		super();
		this.criteriaMax_id = criteriaMax_id;
	}

	public int getCriteriaMax_id() {
		return criteriaMax_id;
	}

	public void setCriteriaMax_id(int criteriaMax_id) {
		this.criteriaMax_id = criteriaMax_id;
	}

}
