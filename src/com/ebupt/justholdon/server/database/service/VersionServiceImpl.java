package com.ebupt.justholdon.server.database.service;

import java.util.List;
import java.util.Map;

//import org.apache.log4j.Logger;
//import org.hibernate.criterion.Criterion;
//import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import com.ebupt.justholdon.server.database.dao.NewestVersionDao;
import com.ebupt.justholdon.server.database.dao.VersionDao;
import com.ebupt.justholdon.server.database.entity.DeviceType;
import com.ebupt.justholdon.server.database.entity.NewestVersion;
import com.ebupt.justholdon.server.database.entity.Version;

@Transactional
@Service("versionService")
public class VersionServiceImpl implements VersionService {
//	private Logger logger = Logger
//			.getLogger(VersionServiceImpl.class.getName());
	@Autowired
	private VersionDao versionDao;
	@Autowired
	private NewestVersionService newestVersionService;

	@Override
	public Integer save(Version newInstance) {
		return versionDao.save(newInstance);
	}

	@Override
	public Version get(Integer id) {
		return versionDao.get(id);
	}

	@Override
	public void update(Version transientObject) {
		versionDao.update(transientObject);
	}

	@Override
	public int update(Integer id, Map<String, Object> infos) {
		return versionDao.update(id, infos);
	}

	@Override
	public void delete(Integer id) {
		versionDao.delete(id);
	}

	@Override
	public List<Version> findAll() {
		return versionDao.findAll();
	}
	
	@Override
	public void addVersion(DeviceType type, String version, String description) {
		Version versionIns = new Version().setDescription(description)
				.setVersion(version).setDeviceType(type);
		versionDao.save(versionIns);
		NewestVersion newestV = newestVersionService.getNewestVersion(type);
		if(newestV==null)
		{
			NewestVersion nVersion = new NewestVersion().setVersion(versionIns).setType(type);
			newestVersionService.save(nVersion);
		}
		else if(newestV.getVersion().getVersion().compareTo(version)< 0)
		{
			newestV.setVersion(versionIns).setType(type);
			newestVersionService.update(newestV);
		}

	}

//	@Override
//	public NewestVersion getNewestVersion(DeviceType type) {
//		List<NewestVersion> newestVersions = newestVersionService.findAll();
//		if(newestVersions.size()>1) logger.warn("there are more than 1 rows in newestVersions!");
//		if(null == newestVersions || newestVersions.size()==0)return null;
//		for(NewestVersion nVersion:newestVersions){
//			if(nVersion.getVersion().getDeviceType().equals(type)){
//				return nVersion;
//			}
//		}
//		return null;
//	}

	@Override
	public void saveOrUpdate(Version transientObject) {
		versionDao.saveOrUpdate(transientObject);
	}

}
