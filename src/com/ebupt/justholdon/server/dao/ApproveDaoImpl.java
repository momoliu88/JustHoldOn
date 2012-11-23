package com.ebupt.justholdon.server.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.entity.Approve;

@Repository("approveDao")
@Transactional
public class ApproveDaoImpl extends GenericHibernateDaoImpl<Approve, Integer>
		implements ApproveDao {

	public ApproveDaoImpl(Class<Approve> type) {
		super(type);
	}

	public ApproveDaoImpl() {
		this(Approve.class);
	}
}
