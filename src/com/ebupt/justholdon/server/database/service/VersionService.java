package com.ebupt.justholdon.server.database.service;

import com.ebupt.justholdon.server.database.entity.DeviceType;
//import com.ebupt.justholdon.server.database.entity.NewestVersion;
import com.ebupt.justholdon.server.database.entity.Version;


public interface VersionService extends GenericService<Version,Integer>{
	public void addVersion(DeviceType type,String  version ,String description);
	//used
	//public NewestVersion getNewestVersion(DeviceType type);
}
