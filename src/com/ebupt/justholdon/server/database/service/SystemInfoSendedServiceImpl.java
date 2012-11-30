package com.ebupt.justholdon.server.database.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.dao.SystemInfoSendedDao;
import com.ebupt.justholdon.server.database.entity.SystemInfoSended;

@Service("systemInfoSendedService")
@Transactional
public class SystemInfoSendedServiceImpl implements SystemInfoSendedService {

	@Autowired
	private SystemInfoSendedDao systemInfoSendedDao;
	@Override
	public Integer save(SystemInfoSended newInstance) {
		return systemInfoSendedDao.save(newInstance);
	}

	@Override
	public SystemInfoSended get(Integer id) {
		return systemInfoSendedDao.get(id);
	}

	@Override
	public void update(SystemInfoSended transientObject) {
		 systemInfoSendedDao.update(transientObject);
	}

	@Override
	public int update(Integer id, Map<String, Object> infos) {
		return systemInfoSendedDao.update(id, infos);
	}

	@Override
	public void delete(Integer id) {
		systemInfoSendedDao.delete(id);
	}

	@Override
	public List<SystemInfoSended> findAll() {
		return systemInfoSendedDao.findAll();
	}

}
