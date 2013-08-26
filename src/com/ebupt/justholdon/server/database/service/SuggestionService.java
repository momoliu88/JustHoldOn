package com.ebupt.justholdon.server.database.service;

import java.util.List;

import com.ebupt.justholdon.server.database.entity.Suggestion;

public interface SuggestionService extends GenericService<Suggestion,Integer>{
	//used
	public Integer addSuggestion(Long uid,String content);
	public List<Suggestion> getAllSuggestions(Integer startId,Integer length,boolean after);
}
