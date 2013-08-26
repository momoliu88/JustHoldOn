package com.ebupt.justholdon.server.database.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.entity.UserField;

@Repository("userFieldDao")
@Transactional 
public class UserFieldDaoImpl extends GenericHibernateDaoImpl<UserField,Long> implements UserFieldDao{

	public UserFieldDaoImpl(Class<UserField> type) {
		super(type);
	}
	public UserFieldDaoImpl()
	{
		this(UserField.class);
	}	 
	
}
