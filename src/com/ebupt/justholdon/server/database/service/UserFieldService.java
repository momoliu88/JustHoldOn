package com.ebupt.justholdon.server.database.service;

import com.ebupt.justholdon.server.database.entity.UserField;

public interface UserFieldService extends GenericService<UserField, Long> {
	public Long createAndSave(String userName, String pass, Long uid,
			String avatar, String deviceToken, String token);

}
