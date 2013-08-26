package com.ebupt.justholdon.server.database.service;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.dao.NewestVersionDao;
import com.ebupt.justholdon.server.database.entity.DeviceType;
import com.ebupt.justholdon.server.database.entity.NewestVersion;
@Service("newestVersionService")
@Transactional
public class NewestVersionServiceImpl implements NewestVersionService {

	@Autowired
	private NewestVersionDao newestVersionDao;
	@Override
	public Integer save(NewestVersion newInstance) {
		return newestVersionDao.save(newInstance);
	}

	@Override
	public NewestVersion get(Integer id) {
		return newestVersionDao.get(id);
	}

	@Override
	public void update(NewestVersion transientObject) {
		newestVersionDao.update(transientObject);
	}

	@Override
	public int update(Integer id, Map<String, Object> infos) {
		return newestVersionDao.update(id, infos);
	}

	@Override
	public void delete(Integer id) {
	//	newestVersionDao.delete(id);
	}

	@Override
	public List<NewestVersion> findAll() {
		return newestVersionDao.findAll();
	}

	@Override
	public void saveOrUpdate(NewestVersion transientObject) {
		newestVersionDao.saveOrUpdate(transientObject);
	}
	private Criterion [] newestVersionCriterion(DeviceType type){
		Criterion [] crits = {
				Restrictions.eq("type",type)
		};
		return crits;
	}
	@Override
	public NewestVersion getNewestVersion(DeviceType type) {
		List<NewestVersion> results = newestVersionDao.findByCriteria(1, newestVersionCriterion(type));
		if(null == results || results.isEmpty()) return null;
		return results.get(0);
	}

}
