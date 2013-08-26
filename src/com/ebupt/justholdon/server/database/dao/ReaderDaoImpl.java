package com.ebupt.justholdon.server.database.dao;

import java.util.Arrays;

import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.entity.Reader;

@Transactional
@Repository("readerDao")
public class ReaderDaoImpl extends GenericHibernateDaoImpl<Reader,Integer> implements ReaderDao{

	public ReaderDaoImpl(Class<Reader> type) {
		super(type);
	}
	public ReaderDaoImpl(){
		this(Reader.class);
		setGlobalOrder(Arrays.asList(Order.desc("createTime")));
	}
}
