package com.ebupt.justholdon.server.database.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.entity.RelationShip;

@Repository("relationShipDao")
@Transactional
public class RelationShipDaoImpl extends GenericHibernateDaoImpl<RelationShip, Integer> implements RelationShipDao {

	public RelationShipDaoImpl(Class<RelationShip> type) {
		super(type);
	}
	public RelationShipDaoImpl() {
		this(RelationShip.class);
	}
 

}
