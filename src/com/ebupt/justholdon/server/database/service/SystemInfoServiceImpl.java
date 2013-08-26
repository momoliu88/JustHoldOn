package com.ebupt.justholdon.server.database.service;

//import java.util.Collections;
//import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.dao.SystemInfoDao;
import com.ebupt.justholdon.server.database.entity.SystemInfo;
import com.ebupt.justholdon.server.database.entity.SystemInfoSended;
import com.ebupt.justholdon.server.database.entity.User;

@Service("systemInfoService")
@Transactional
public class SystemInfoServiceImpl implements SystemInfoService {
	@Autowired
	private SystemInfoDao systemInfoDao;
	@Autowired
	private UserService userService;
	@Autowired
	private SystemInfoSendedService systemInfoSendedService;
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
	private Criterion[] unreadedSystemInfosCriterions(Long uid){
		List<Integer> systemInfoIds = systemInfoSendedService.getSendedInfos(uid);
		Criterion[] crits ={
				  Restrictions.not(Restrictions.in("id", systemInfoIds))
		};
		return crits;
	}
	@Override
	public List<SystemInfo> getUnreadedSystemInfo(Long uid) {
//		User receiver = userService.get(uid);
//		List<SystemInfoSended> hasReceivedSysInfos =  receiver.getReceiveSystemInfos();
//		List<SystemInfo> allSystemInfos = systemInfoDao.findAll();
//		List<SystemInfo> hasReceivedInfos = new ArrayList<SystemInfo>();
//		for(SystemInfoSended info:hasReceivedSysInfos)
//		{
//			hasReceivedInfos.add(info.getSystemInfo());
//		}
//		allSystemInfos.removeAll(hasReceivedInfos);
//		return allSystemInfos;
		return systemInfoDao.findByCriteria(unreadedSystemInfosCriterions(uid));
	}
	private List<SystemInfo> warpCriterions(Integer length,Integer startId,boolean after,Criterion...crits){
		if(Utils.checkIdIsZero(startId)) 
			return systemInfoDao.findByCriteria(length,crits);
		SystemInfo systemInfo = get(startId);
		return systemInfoDao.findByCriteria(length,
				Utils.warpIdRangeLimit(systemInfo.getCreateTime(),"createTime",after, crits));
	}
	@Override
	public List<SystemInfo> getUnreadedSystemInfo(Long uid, Integer startId,
			Integer length,boolean after) {
	//	return Utils.subList(start, end, getUnreadedSystemInfo(uid));
		//return Utils.cutEventList(getUnreadedSystemInfo(uid), startId, length, after,false);
	//	return systemInfoDao.findByCriteria(length,Utils.warpIdRangeLimit(after, startId, unreadedSystemInfosCriterions(uid)));
		return warpCriterions(length,startId,after,unreadedSystemInfosCriterions(uid));
	}

	@Override
	public List<SystemInfo> getAllSystemInfo(Integer startId,
			Integer length,boolean after) {
//		List<SystemInfo> allSystemInfos = systemInfoDao.findAll();
//		Criterion [] crits = {};
	//	return systemInfoDao.findByCriteria(length,Utils.warpIdRangeLimit(after, startId, crits));
		return warpCriterions(length,startId,after,new Criterion[]{});
	//	Collections.sort(allSystemInfos,comparator);
		//return Utils.subList(start, end,allSystemInfos);
//		return Utils.cutEventList(allSystemInfos, startId, length, after,false);
	}

	@Override
	public void readASystemInfo(Long uid, Integer id) {
		User user = userService.get(uid);
		SystemInfo systemInfo = systemInfoDao.get(id);
		SystemInfoSended systemInfoSended = new SystemInfoSended().setUser(user).setSystemInfo(systemInfo);
		systemInfoSendedService.saveOrUpdate(systemInfoSended);
	}

	@Override
	public Set<SystemInfoSended> getSendedSystemInfos(Integer id) {
		return new HashSet<SystemInfoSended>(get(id).getSendedSystemInfos());
	}

	@Override
	public Integer countSendedSystemInfos(Integer id) {
		return get(id).getSendedSystemInfos().size();
	}

	@Override
	public void saveOrUpdate(SystemInfo transientObject) {
		systemInfoDao.saveOrUpdate(transientObject);		
	}

}
