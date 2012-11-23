package com.ebupt.justholdon.server.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.entity.Flag;

@Repository("flagDao")
@Transactional
public class FlagDaoImpl extends GenericHibernateDaoImpl<Flag, Integer>
		implements FlagDao {

	public FlagDaoImpl(Class<Flag> type) {
		super(type);
	}

	public FlagDaoImpl() {
		this(Flag.class);
	}
	public int getUserSize(Integer id )
	{
		return get(id).getUsers().size();
	}
}
