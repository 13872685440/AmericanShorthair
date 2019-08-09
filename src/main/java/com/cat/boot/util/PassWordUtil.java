package com.cat.boot.util;

import java.util.HashMap;
import java.util.Map;

public class PassWordUtil {

	public static Map<String, String> entryptPassword(String pwd, boolean hasMd5) {
		if (hasMd5) {
			pwd = MD5.stringMD5(MD5.stringMD5(pwd));
		}
		Map<String, String> pwds = new HashMap<String, String>();
		byte[] salt = Digests.generateSalt(Digests.SALT_SIZE);
		pwds.put("salt", Encodes.encodeHex(salt));
		byte[] hashPassword = Digests.sha1(pwd.getBytes(), salt, Digests.HASH_INTERATIONS);
		pwds.put("pwd", Encodes.encodeHex(hashPassword));
		return pwds;
	}

	public static String entryptPassword(String data, String salt) {
		byte[] hashPassword = Digests.sha1(data.getBytes(), Encodes.decodeHex(salt), Digests.HASH_INTERATIONS);
		return Encodes.encodeHex(hashPassword);
	}
}
