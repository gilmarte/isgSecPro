package com.isg.ifrend.model.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class HistUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int historyIndex;
	
	private String userID;
	private String employeeID;
	private String userName;
	private String eMail;
	private String businessEntity;
	private boolean maxAccountBalAndStatus;
	private boolean vipAccountAllowed;
	private boolean staffAccountAllowed;
//	private int currentStatus;
	private String supervisorID;
	private String customerID;
	private String languagePref;
	private int status;
	private int action;
	private String createdBy;
	private Date dateCreated;
	private String lastModifiedBy;
	private Date dateLastModified;
	private String approvedBy;
	private Date dateApproved;
	
	private Set<UserOrganization> userOrganization;
	private Set<SaUserGroup> userGroup;
	
	public HistUser() {
		
	}
	
	public HistUser(TempUser user) {
		setUserID(user.getUserID());
		setEmployeeID(user.getEmployeeID());
		setUserName(user.getUserName());
		seteMail(user.geteMail());
		setBusinessEntity(user.getBusinessEntity());
		setMaxAccountBalAndStatus(user.isMaxAccountBalAndStatus());
		setVipAccountAllowed(user.isVipAccountAllowed());
		setStaffAccountAllowed(user.isStaffAccountAllowed());
//		setCurrentStatus(user.getCurrentStatus());
		setStatus(user.getStatus());
		setAction(user.getAction());
		setSupervisorID(user.getSupervisorID());
		setCustomerID(user.getCustomerID());
		setLanguagePref(user.getLanguagePref());
		setUserGroup(new HashSet<SaUserGroup>(user.getUserGroup()));		
//		Set<TempUserOrganization> orgSet = new HashSet<TempUserOrganization>();
//		for(UserOrganization org : user.getUserOrganization()) {
//			orgSet.add(new TempUserOrganization(org));
//		}
		setUserOrganization(new HashSet<UserOrganization>(user.getUserOrganization()));
		setCreatedBy(user.getCreatedBy());
		setDateCreated(user.getDateCreated());
		setLastModifiedBy(user.getLastModifiedBy());
		setDateLastModified(user.getDateLastModified());
		setApprovedBy(user.getApprovedBy());
		setDateApproved(user.getDateApproved());
	}
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String geteMail() {
		return eMail;
	}
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}
	public String getBusinessEntity() {
		return businessEntity;
	}
	public void setBusinessEntity(String businessEntity) {
		this.businessEntity = businessEntity;
	}
	public boolean isMaxAccountBalAndStatus() {
		return maxAccountBalAndStatus;
	}
	public void setMaxAccountBalAndStatus(boolean maxAccountBalAndStatus) {
		this.maxAccountBalAndStatus = maxAccountBalAndStatus;
	}
	public boolean isVipAccountAllowed() {
		return vipAccountAllowed;
	}
	public void setVipAccountAllowed(boolean vipAccountAllowed) {
		this.vipAccountAllowed = vipAccountAllowed;
	}
	public boolean isStaffAccountAllowed() {
		return staffAccountAllowed;
	}
	public void setStaffAccountAllowed(boolean staffAccountAllowed) {
		this.staffAccountAllowed = staffAccountAllowed;
	}

	public String getSupervisorID() {
		return supervisorID;
	}
	public void setSupervisorID(String supervisorID) {
		this.supervisorID = supervisorID;
	}
	public String getCustomerID() {
		return customerID;
	}
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
	public String getLanguagePref() {
		return languagePref;
	}
	public void setLanguagePref(String languagePref) {
		this.languagePref = languagePref;
	}
	public Set<UserOrganization> getUserOrganization() {
		return userOrganization;
	}
	public void setUserOrganization(Set<UserOrganization> userOrganization) {
		this.userOrganization = userOrganization;
	}
	public Set<SaUserGroup> getUserGroup() {
		return userGroup;
	}
	public void setUserGroup(Set<SaUserGroup> userGroup) {
		this.userGroup = userGroup;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User: ");
		builder.append(userID);
		builder.append("\n");
//		builder.append("Current Status: ");
//		builder.append(currentStatus);
//		builder.append("\n");
		builder.append("Username: ");
		builder.append(userName);
		builder.append("\n");
		builder.append("Employee ID: ");
		builder.append(employeeID);
		builder.append("\n");
		builder.append("Email: ");
		builder.append(eMail);
		builder.append("\n");
		builder.append("Business Entity: ");
		builder.append(businessEntity);
		builder.append("\n");
		builder.append("Max Acct Bal and Status: ");
		builder.append(maxAccountBalAndStatus);
		builder.append("\n");
		builder.append("VIP Acct Allowed: ");
		builder.append(vipAccountAllowed);
		builder.append("\n");
		builder.append("Staff Acct Allowed: ");
		builder.append(staffAccountAllowed);
		builder.append("\n");
		builder.append("Supervisor ID: ");
		builder.append(supervisorID);
		builder.append("\n");
		builder.append("Customer ID: ");
		builder.append(customerID);
		builder.append("\n");
		builder.append("Language Preference: ");
		builder.append(languagePref);
		return builder.toString();
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

	public int getHistoryIndex() {
		return historyIndex;
	}

	public void setHistoryIndex(int historyIndex) {
		this.historyIndex = historyIndex;
	}
		
}
