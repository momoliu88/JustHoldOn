package com.ebupt.justholdon.server.database.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.dao.QuoteDao;
import com.ebupt.justholdon.server.database.entity.Quote;

@Service("quoteService")
@Transactional
public class QuoteServiceImpl implements QuoteService{
	@Autowired
	private QuoteDao quoteDao;
	
	@Override
	public Integer save(Quote newInstance) {
		return quoteDao.save(newInstance);
	}

	@Override
	public Quote get(Integer id) {
		return quoteDao.get(id);
	}

	@Override
	public void update(Quote transientObject) {
		//do nothing
	}

	@Override
	public int update(Integer id, Map<String, Object> infos) {
		//do nothing
		return 0;
	}

	@Override
	public void delete(Integer id) {
		//quoteDao.delete(id);
	}

	@Override
	public List<Quote> findAll() {
		return quoteDao.findAll();
	}

	@Override
	public QuoteNamePair getQuoteByRandom(Long uid) {
		List<Quote> quotes = findAll();
		if(null == quotes || quotes.isEmpty()) return null;
		Long time = new Date().getTime();
		Long random = 31L * (uid + time);
		Quote quote = quotes.get((int)(random%quotes.size()));
		QuoteNamePair pair = new QuoteNamePair()
									.setContent(quote.getContent())
									.setAuthor(quote.getAuthor());
		return pair;
	}

	@Override
	public void saveOrUpdate(Quote transientObject) {
		quoteDao.saveOrUpdate(transientObject);		
	}
	

}
