package com.ebupt.justholdon.server.database.service;

import com.ebupt.justholdon.server.database.entity.DeviceType;
import com.ebupt.justholdon.server.database.entity.NewestVersion;;

public interface NewestVersionService  extends GenericService<NewestVersion,Integer>{
	public NewestVersion getNewestVersion(DeviceType type);
}
