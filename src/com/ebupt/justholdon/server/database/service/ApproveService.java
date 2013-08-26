package com.ebupt.justholdon.server.database.service;

import com.ebupt.justholdon.server.database.entity.Approve;

public interface ApproveService  extends GenericService<Approve,Integer>{
	//used
	public Integer approveCheckIn(Long uid,Integer checkInId);
	public boolean isApproved(Long uid,Integer checkInId);
}
