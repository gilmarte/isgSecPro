package com.isg.ifrend.model.bean;

import java.io.Serializable;
import java.util.Date;

public class EnhancedCriterion implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int enhancedcriteria_id;
	private String criteria_id;
	private String element_id;
	private String operator_code;
	private Integer enhanced_value_integer;
	private String enhanced_value_character;
	private String enhanced_value_element;
	private Date enhanced_value_date;
	private Integer enhanced_value_dateformat_id;
	private String dateFormat;
	
	private Criteria criteria;
	
	public EnhancedCriterion(){}
	
	public EnhancedCriterion(TempEnhancedCriterion teCriterion){
		this.enhancedcriteria_id = teCriterion.getEnhancedcriteria_id();
		this.criteria_id = teCriterion.getCriteria_id() ;
		this.element_id = teCriterion.getElement_id() ;
		this.operator_code = teCriterion.getOperator_code();
		this.enhanced_value_integer = teCriterion.getEnhanced_value_integer() ;
		this.enhanced_value_character = teCriterion.getEnhanced_value_character();
		this.enhanced_value_element = teCriterion.getEnhanced_value_element();
		this.enhanced_value_date= teCriterion.getEnhanced_value_date();
		this.enhanced_value_dateformat_id= teCriterion.getEnhanced_value_dateformat_id();
	}
	
	public int getEnhancedcriteria_id() {
		return enhancedcriteria_id;
	}
	public void setEnhancedcriteria_id(int enhancedcriteria_id) {
		this.enhancedcriteria_id = enhancedcriteria_id;
	}
	public String getCriteria_id() {
		return criteria_id;
	}
	public void setCriteria_id(String criteria_id) {
		this.criteria_id = criteria_id;
	}
	public String getElement_id() {
		return element_id;
	}
	public void setElement_id(String element_id) {
		this.element_id = element_id;
	}
	public String getOperator_code() {
		return operator_code;
	}
	public void setOperator_code(String operator_code) {
		this.operator_code = operator_code;
	}
	public Integer getEnhanced_value_integer() {
		return enhanced_value_integer;
	}
	public void setEnhanced_value_integer(Integer enhanced_value_integer) {
		this.enhanced_value_integer = enhanced_value_integer;
	}
	public String getEnhanced_value_character() {
		return enhanced_value_character;
	}
	public void setEnhanced_value_character(String enhanced_value_character) {
		this.enhanced_value_character = enhanced_value_character;
	}
	public String getEnhanced_value_element() {
		return enhanced_value_element;
	}
	public void setEnhanced_value_element(String enhanced_value_element) {
		this.enhanced_value_element = enhanced_value_element;
	}
	public Date getEnhanced_value_date() {
		return enhanced_value_date;
	}
	public void setEnhanced_value_date(Date enhanced_value_date) {
		this.enhanced_value_date = enhanced_value_date;
	}
	public String getDateFormat() {
		return dateFormat;
	}
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	public Criteria getCriteria() {
		return criteria;
	}
	public void setCriteria(Criteria criteria) {
		this.criteria = criteria;
	}
	public Integer getEnhanced_value_dateformat_id() {
		return enhanced_value_dateformat_id;
	}
	public void setEnhanced_value_dateformat_id(Integer enhanced_value_dateformat_id) {
		this.enhanced_value_dateformat_id = enhanced_value_dateformat_id;
	}
	
}
