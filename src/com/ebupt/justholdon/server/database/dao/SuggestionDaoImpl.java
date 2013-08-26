package com.ebupt.justholdon.server.database.dao;

import java.util.Arrays;

import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.entity.Suggestion;

@Repository("suggestionDao")
@Transactional
public class SuggestionDaoImpl extends GenericHibernateDaoImpl<Suggestion,Integer>implements SuggestionDao {

	public SuggestionDaoImpl(Class<Suggestion> type) {
		super(type);
	}
	public SuggestionDaoImpl(){
		this(Suggestion.class);
		setGlobalOrder(Arrays.asList(Order.desc("createTime"),Order.desc("id")));
	}
	

}
