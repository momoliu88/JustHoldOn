package com.ebupt.justholdon.server.database.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.dao.SystemInfoDao;
import com.ebupt.justholdon.server.database.dao.UserDao;
import com.ebupt.justholdon.server.database.entity.SystemInfo;
import com.ebupt.justholdon.server.database.entity.SystemInfoSended;
import com.ebupt.justholdon.server.database.entity.User;

@Service("systemInfoSendedService")
@Transactional
public class SystemInfoServiceImpl implements SystemInfoService {
	@Autowired
	private SystemInfoDao systemInfoDao;
	@Autowired
	private UserDao userDao;
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
		systemInfoDao.delete(id);
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
		allSystemInfos.retainAll(hasReceivedInfos);
		Collections.sort(allSystemInfos,SystemInfo.getDateComparator());
		return allSystemInfos;
	}

	@Override
	public List<SystemInfo> getUnreadedSystemInfo(Long uid, Integer start,
			Integer end) {
		return Utils.subList(start, end, getUnreadedSystemInfo(uid));
	}

	@Override
	public List<SystemInfo> getAllSystemInfo(Long uid, Integer start,
			Integer end) {
		List<SystemInfo> allSystemInfos = systemInfoDao.findAll();
		List<SystemInfo> subList = Utils.subList(start, end,allSystemInfos);
		Collections.sort(subList,SystemInfo.getDateComparator());
		return subList;
	}

}
