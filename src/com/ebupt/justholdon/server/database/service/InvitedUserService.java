package com.ebupt.justholdon.server.database.service;

import java.util.Date;
import java.util.List;

import com.ebupt.justholdon.server.database.entity.InvitedUser;

public interface InvitedUserService  extends GenericService<InvitedUser,Integer>{
	//count the number of sponsor haved invited betweend startDate and endDate.
	public Integer countInvitedUserNums(Date startDate,Date endDate,Long sponsorId);
	//if System invite a user,Id should be 1
	public void inviteUser(Long sponsorId,String invitedUserName);
	public boolean hasInvited(String userName);
	public List<String> hasInvitedUser();
}
