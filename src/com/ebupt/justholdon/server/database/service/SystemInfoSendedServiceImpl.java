package com.ebupt.justholdon.server.database.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
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
	@Autowired
	private UserService userService;
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
		SystemInfoSended info = systemInfoSendedDao.get(id);
		info.getSystemInfo().getSendedSystemInfos().remove(info);
		info.getUser().getReceiveSystemInfos().remove(info);
		info.setSystemInfo(null);
		info.setUser(null);
		systemInfoSendedDao.delete(info);
	}

	@Override
	public List<SystemInfoSended> findAll() {
		return systemInfoSendedDao.findAll();
	}

	@Override
	public void saveOrUpdate(SystemInfoSended transientObject) {
		systemInfoSendedDao.saveOrUpdate(transientObject);
	}

	private Criterion [] userCriterions(Long uid){
		Criterion[] crits = {
				Restrictions.eq("user", userService.get(uid))
		};
		return crits;
	}
	@Override
	public List<Integer> getSendedInfos(Long uid) {
		ProjectionList projectionList = Projections.projectionList().add(Projections.property("systemInfo"));
		List<?> ret = systemInfoSendedDao.findByCriteria(projectionList, userCriterions(uid));
		List<Integer> results = new ArrayList<Integer>();
		Iterator<?> iterator =ret.iterator();
		while(iterator.hasNext()){
			Object[] obj = (Object[]) iterator.next();
			results.add(Integer.valueOf(obj[0].toString()));
		}
		return results;
	}

}
