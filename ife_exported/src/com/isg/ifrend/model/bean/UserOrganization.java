package com.isg.ifrend.model.bean;

import java.io.Serializable;
import java.util.Date;

public class UserOrganization implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String orgID = "";
	private String orgDesc = "";
	private String disposeAdjLimit = "0";
	private String creditLimitC1 = "0";
	private String creditLimitC2 = "0";
	private String chargeBackLimit = "0";
	private String retrievalAmount = "0";
	private String fraudAmount = "0";
	private String feeAmount = "0";
	private String permCreditLimit = "0";
	private String tempCreditLimit = "0";
	private String feeAdjLimit = "0";
	private String lateChargeAdjLimit = "0";
	private String balAdjLimit = "0";
	private String transactionLimit = "0";
	private String transactionCode = "";
	private int status;
	private int action;
	
	private String createdBy = "";
	private Date dateCreated;
	private String lastModifiedBy = "";
	private Date dateLastModified;
	private String approvedBy = "";
	private Date dateApproved;
	
	public UserOrganization() {
		
	}

	public UserOrganization(TempUserOrganization org) {
		setOrgID(org.getOrgID());
		setOrgDesc(org.getOrgDesc());
		setDisposeAdjLimit(org.getDisposeAdjLimit());
		setCreditLimitC1(org.getCreditLimitC1());
		setCreditLimitC2(org.getCreditLimitC2());
		setChargeBackLimit(org.getChargeBackLimit());
		setRetrievalAmount(org.getRetrievalAmount());
		setFraudAmount(org.getFraudAmount());
		setFeeAmount(org.getFeeAmount());
		setPermCreditLimit(org.getPermCreditLimit());
		setTempCreditLimit(org.getTempCreditLimit());
		setFeeAdjLimit(org.getFeeAdjLimit());
		setLateChargeAdjLimit(org.getChargeBackLimit());
		setBalAdjLimit(org.getBalAdjLimit());
		setTransactionLimit(org.getTransactionLimit());
		setTransactionCode(org.getTransactionCode());
		setStatus(org.getStatus());
		setAction(org.getAction());
		setCreatedBy(org.getCreatedBy());
		setDateCreated(org.getDateCreated());
		setLastModifiedBy(org.getLastModifiedBy());
		setDateLastModified(org.getDateLastModified());
		setApprovedBy(org.getApprovedBy());
		setDateApproved(org.getDateApproved());
	}

	public String getOrgID() {
		return orgID;
	}

	public void setOrgID(String orgID) {
		this.orgID = orgID;
	}

	public String getCreditLimitC1() {
		return creditLimitC1;
	}

	public void setCreditLimitC1(String creditLimitC1) {
		this.creditLimitC1 = creditLimitC1;
	}

	public String getCreditLimitC2() {
		return creditLimitC2;
	}

	public void setCreditLimitC2(String creditLimitC2) {
		this.creditLimitC2 = creditLimitC2;
	}

	public String getChargeBackLimit() {
		return chargeBackLimit;
	}

	public void setChargeBackLimit(String chargeBackLimit) {
		this.chargeBackLimit = chargeBackLimit;
	}

	public String getRetrievalAmount() {
		return retrievalAmount;
	}

	public void setRetrievalAmount(String retrievalAmount) {
		this.retrievalAmount = retrievalAmount;
	}

	public String getFraudAmount() {
		return fraudAmount;
	}

	public void setFraudAmount(String fraudAmount) {
		this.fraudAmount = fraudAmount;
	}

	public String getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(String feeAmount) {
		this.feeAmount = feeAmount;
	}

	public String getPermCreditLimit() {
		return permCreditLimit;
	}

	public void setPermCreditLimit(String permCreditLimit) {
		this.permCreditLimit = permCreditLimit;
	}

	public String getTempCreditLimit() {
		return tempCreditLimit;
	}

	public void setTempCreditLimit(String tempCreditLimit) {
		this.tempCreditLimit = tempCreditLimit;
	}

	public String getFeeAdjLimit() {
		return feeAdjLimit;
	}

	public void setFeeAdjLimit(String feeAdjLimit) {
		this.feeAdjLimit = feeAdjLimit;
	}

	public String getLateChargeAdjLimit() {
		return lateChargeAdjLimit;
	}

	public void setLateChargeAdjLimit(String lateChargeAdjLimit) {
		this.lateChargeAdjLimit = lateChargeAdjLimit;
	}

	public String getBalAdjLimit() {
		return balAdjLimit;
	}

	public void setBalAdjLimit(String balAdjLimit) {
		this.balAdjLimit = balAdjLimit;
	}

	public String getTransactionLimit() {
		return transactionLimit;
	}

	public void setTransactionLimit(String transactionLimit) {
		this.transactionLimit = transactionLimit;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	public String getDisposeAdjLimit() {
		return disposeAdjLimit;
	}

	public void setDisposeAdjLimit(String disposeAdjLimit) {
		this.disposeAdjLimit = disposeAdjLimit;
	}

	public String getOrgDesc() {
		return orgDesc;
	}

	public void setOrgDesc(String orgDesc) {
		this.orgDesc = orgDesc;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateLastModified() {
		return dateLastModified;
	}

	public void setDateLastModified(Date dateLastModified) {
		this.dateLastModified = dateLastModified;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Date getDateApproved() {
		return dateApproved;
	}

	public void setDateApproved(Date dateApproved) {
		this.dateApproved = dateApproved;
	}
	
}
