package com.ebupt.justholdon.server.database.service;

import com.ebupt.justholdon.server.database.entity.Quote;

public interface QuoteService  extends GenericService<Quote,Integer> {
	public QuoteNamePair getQuoteByRandom(Long uid);

}
