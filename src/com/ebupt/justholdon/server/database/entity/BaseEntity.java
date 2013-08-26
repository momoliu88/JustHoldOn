package com.ebupt.justholdon.server.database.entity;

import java.util.Date;

public interface BaseEntity<PK extends Number> {
	public PK getId();

	public Date getCreateTime();

	public Date getModifyTime();

	public void setModifyTime(Date modifyTime);
	
	public void onUpdate();
}
