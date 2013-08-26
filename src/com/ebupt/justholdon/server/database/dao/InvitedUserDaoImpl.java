package com.ebupt.justholdon.server.database.dao;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.entity.InvitedUser;

@Repository("invitedUserDao")
@Transactional
public class InvitedUserDaoImpl extends GenericHibernateDaoImpl<InvitedUser, Integer>
		implements InvitedUserDao {

	public InvitedUserDaoImpl(Class<InvitedUser> type) {
		super(type);
	}
	public InvitedUserDaoImpl(){
		this(InvitedUser.class);
	}
	 
}
