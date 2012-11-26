package com.ebupt.justholdon.server.database.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.dao.ImpressionDao;
import com.ebupt.justholdon.server.database.dao.UserDao;
import com.ebupt.justholdon.server.database.entity.Impression;
import com.ebupt.justholdon.server.database.entity.User;

@Service("impressionService")
@Transactional
public class ImpressionServiceImpl implements ImpressionService {
	
	@Autowired
	private ImpressionDao impressionDao;
	@Autowired
	private UserDao userDao;
	
	private Logger logger = Logger.getLogger(this.getClass());
	@Override
	public Integer save(Impression newInstance) {
		return impressionDao.save(newInstance);
	}

	@Override
	public Impression get(Integer id) {
		return impressionDao.get(id);
	}

	@Override
	public void update(Impression transientObject) {
		impressionDao.update(transientObject);
	}

	@Override
	public int update(Integer id, Map<String, Object> infos) {
		return impressionDao.update(id, infos);
	}

	@Override
	public void delete(Integer id) {
		impressionDao.delete(id);
	}

	@Override
	public List<Impression> findAll() {
		return impressionDao.findAll();
	}

	@Override
	public void addImpression(Long uidA, Long uidB, String content) {
		User userA = userDao.get(uidA);
		User userB = userDao.get(uidB);
		if(userA == null || userB == null)
		{
			logger.warn("userA["+uidA+"] => userB["+uidB+"] can't be NULL.Add impression failed!");
			return ;
		}
		Impression impression = new Impression().setContent(content).setSponsor(userA).setReceiver(userB);
		userA.getSponseImpressiones().add(impression);
		userB.getReceivedImpressiones().add(impression);
		impressionDao.saveOrUpdate(impression);
		userDao.update(userA);
		userDao.update(userB);
	}

	@Override
	public void addImpression(Long uidA, Long uidB, Impression impression) {
		User userA = userDao.get(uidA);
		User userB = userDao.get(uidB);
		if(userA == null || userB == null || impression == null)
		{
			logger.warn("userA["+uidA+"] => userB["+uidB+"] can't be NULL.Add impression failed!");
			return ;
		}
		userA.getSponseImpressiones().add(impression);
		userB.getReceivedImpressiones().add(impression);
		impressionDao.saveOrUpdate(impression);
		userDao.update(userA);
		userDao.update(userB);
	}
}
