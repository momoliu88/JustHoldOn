package com.ebupt.justholdon.server.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.entity.UserField;

@Repository("userFieldDao")
@Transactional 
public class UserFieldDaoImpl extends GenericHibernateDaoImpl<UserField,Integer> implements UserFieldDao{

	public UserFieldDaoImpl(Class<UserField> type) {
		super(type);
	}
	public UserFieldDaoImpl()
	{
		this(UserField.class);
	}	 
	
}
