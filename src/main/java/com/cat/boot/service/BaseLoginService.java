package com.cat.boot.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.cat.boot.jsonbean.UserBean;
import com.cat.boot.util.Digests;
import com.cat.boot.util.Encodes;

public abstract class BaseLoginService {

	@Autowired
	public BaseService baseService;
	
	public abstract UserBean login(UserBean bean);
	
	protected String entryptPassword(String data, byte[] salt) {
		byte[] hashPassword = Digests.sha1(data.getBytes(), salt, Digests.HASH_INTERATIONS);
		return Encodes.encodeHex(hashPassword);
	}
}
