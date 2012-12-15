package com.ebupt.justholdon.server.database.service;

import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.dao.SystemInfoDao;
import com.ebupt.justholdon.server.database.dao.SystemInfoSendedDao;
import com.ebupt.justholdon.server.database.dao.UserDao;
//import com.ebupt.justholdon.server.database.entity.GenericComparator;
import com.ebupt.justholdon.server.database.entity.SystemInfo;
import com.ebupt.justholdon.server.database.entity.SystemInfoSended;
import com.ebupt.justholdon.server.database.entity.User;

@Service("systemInfoService")
@Transactional
public class SystemInfoServiceImpl implements SystemInfoService {
	@Autowired
	private SystemInfoDao systemInfoDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private SystemInfoSendedDao systemInfoSendedDao;
//	@SuppressWarnings("rawtypes")
//	private Comparator comparator = GenericComparator.getInstance().getDateComparator();
	@Override
	public Integer save(SystemInfo newInstance) {
		return systemInfoDao.save(newInstance);
	}

	@Override
	public SystemInfo get(Integer id) {
		return systemInfoDao.get(id);
	}

	@Override
	public void update(SystemInfo transientObject) {
		systemInfoDao.update(transientObject);
	}

	@Override
	public int update(Integer id, Map<String, Object> infos) {
		return systemInfoDao.update(id, infos);
	}

	@Override
	public void delete(Integer id) {
//		SystemInfo info = systemInfoDao.get(id);
//		info.getSendedSystemInfos();
	}

	@Override
	public List<SystemInfo> findAll() {
		return systemInfoDao.findAll();
	}

	@Override
	public Integer createSystemInformation(String content) {
		if (null == content)
			throw new java.lang.NullPointerException(
					"systemInfo content is not null");
		SystemInfo systemInfo = new SystemInfo().setContent(content);
		return systemInfoDao.save(systemInfo);
	}

	@Override
	public Integer createSystemInformation(String content, String extra,
			Date createDate) {
		if (null == content)
			throw new java.lang.NullPointerException(
					"systemInfo content is not null");
		SystemInfo systemInfo = new SystemInfo().setContent(content)
				.setExtra(extra).setCreateTime(createDate);
		return systemInfoDao.save(systemInfo);
	}

	//@SuppressWarnings("unchecked")
	@Override
	public List<SystemInfo> getUnreadedSystemInfo(Long uid) {
		User receiver = userDao.get(uid);
		Set<SystemInfoSended> hasReceivedSysInfos =  receiver.getReceiveSystemInfos();
		List<SystemInfo> allSystemInfos = systemInfoDao.findAll();
		List<SystemInfo> hasReceivedInfos = new ArrayList<SystemInfo>();
		for(SystemInfoSended info:hasReceivedSysInfos)
		{
			hasReceivedInfos.add(info.getSystemInfo());
		}
		allSystemInfos.removeAll(hasReceivedInfos);
	//	Collections.sort(allSystemInfos,comparator);
		return allSystemInfos;
	}

	@Override
	public List<SystemInfo> getUnreadedSystemInfo(Long uid, Integer startId,
			Integer length,boolean after) {
	//	return Utils.subList(start, end, getUnreadedSystemInfo(uid));
		return Utils.cutEventList(getUnreadedSystemInfo(uid), startId, length, after);
	}

	@Override
	public List<SystemInfo> getAllSystemInfo(Integer startId,
			Integer length,boolean after) {
		List<SystemInfo> allSystemInfos = systemInfoDao.findAll();
	//	Collections.sort(allSystemInfos,comparator);
		//return Utils.subList(start, end,allSystemInfos);
		return Utils.cutEventList(allSystemInfos, startId, length, after);
	}

	@Override
	public void readASystemInfo(Long uid, Integer id) {
		User user = userDao.get(uid);
		SystemInfo systemInfo = systemInfoDao.get(id);
		SystemInfoSended systemInfoSended = new SystemInfoSended().setUser(user).setSystemInfo(systemInfo);
		systemInfoSendedDao.saveOrUpdate(systemInfoSended);
	}

}
