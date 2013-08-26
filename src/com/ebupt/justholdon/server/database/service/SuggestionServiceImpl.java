package com.ebupt.justholdon.server.database.service;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.dao.SuggestionDao;
import com.ebupt.justholdon.server.database.entity.Suggestion;
import com.ebupt.justholdon.server.database.entity.User;

@Transactional
@Service("suggestionService")
public class SuggestionServiceImpl implements SuggestionService {
	@Autowired
	private UserService userService;
	@Autowired
	private SuggestionDao suggestionDao;
	@Override
	public Integer save(Suggestion newInstance) {
		return suggestionDao.save(newInstance);
	}

	@Override
	public Suggestion get(Integer id) {
		return suggestionDao.get(id);
	}

	@Override
	public void update(Suggestion transientObject) {
		suggestionDao.update(transientObject);
	}

	@Override
	public int update(Integer id, Map<String, Object> infos) {
		return suggestionDao.update(id, infos)
				;
	}

	@Override
	public void delete(Integer id) {
		Suggestion suggestion = suggestionDao.get(id);
		suggestion.getUser().getSponsorSuggestions().remove(suggestion);
		suggestion.setUser(null);
		suggestionDao.update(suggestion);
	}

	@Override
	public List<Suggestion> findAll() {
		return suggestionDao.findAll();
	}

	@Override
	public Integer addSuggestion(Long uid, String content) {
		User user = userService.get(uid);
		Suggestion suggestion = new Suggestion();
		suggestion.setContent(content).setUser(user);
		return suggestionDao.save(suggestion);
	}
	private List<Suggestion> warpCriterions(Integer length,Integer startId,boolean after,Criterion...crits){
		if(Utils.checkIdIsZero(startId)) 
			return suggestionDao.findByCriteria(length,crits);
		Suggestion suggestion = get(startId);
		return suggestionDao.findByCriteria(length,
				Utils.warpIdRangeLimit(suggestion.getCreateTime(),"createTime",after, crits));
	}
	@Override
	public List<Suggestion> getAllSuggestions(Integer startId, Integer length,
			boolean after) {
//		Criterion [] crits = {};
		return warpCriterions(length,startId,after,new Criterion[]{});
	//	return suggestionDao.findByCriteria(length, Utils.warpIdRangeLimit(after, startId, crits));
	}

	@Override
	public void saveOrUpdate(Suggestion transientObject) {
		suggestionDao.saveOrUpdate(transientObject);		
	}

}
