package com.ebupt.justholdon.server.database.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.entity.Quote;

@Repository("quoteDao")
@Transactional
public class QuoteDaoImpl extends GenericHibernateDaoImpl<Quote, Integer> implements QuoteDao {

	public QuoteDaoImpl(Class<Quote> type) {
		super(type);
	}
	public QuoteDaoImpl()
	{
		this(Quote.class);
	}

}
