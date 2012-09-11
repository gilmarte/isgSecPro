package com.isg.ifrend.model.bean;

import java.util.Date;

public abstract class Global {

	public abstract String getId();

	public abstract void setId(String id);

	public abstract int getStatusID();

	public abstract void setStatusID(int statusID);

	public abstract int getActionID();

	public abstract void setActionID(int actionID);

	public abstract String getCreatorUserID();

	public abstract void setCreatorUserID(String creatorID);

	public abstract Date getDateCreated();

	public abstract void setDateCreated(Date dateCreated);

	public abstract String getLastModifierUserID();

	public abstract void setLastModifierUserID(String lastModifierUserID);

	public abstract Date getDateLastModified();

	public abstract void setDateLastModified(Date dateLastModified);

	public abstract String getApproverUserID();

	public abstract void setApproverUserID(String approverUserID);

	public abstract Date getDateApproved();

	public abstract void setDateApproved(Date dateApproved);

	public abstract String getDesc();
	
	public abstract void setDesc(String desc);

	public abstract String getEntityDesc();
}
