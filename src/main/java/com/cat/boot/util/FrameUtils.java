package com.cat.boot.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class FrameUtils {

	public static javax.servlet.http.HttpSession getHttpSession() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		HttpSession session = request.getSession(true);
		return session;
	}

	@SuppressWarnings("rawtypes")
	public static Properties getProperties(Class clazz, String path) {
		Properties properties = new Properties();
		InputStream in = clazz.getResourceAsStream(path);
		try {
			properties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}

	@SuppressWarnings("rawtypes")
	public static String getProperties(Class clazz, String path, String pro) {
		Properties properties = getProperties(clazz, path);
		if (properties != null) {
			for (Object tmp : properties.keySet()) {
				String key = (String) tmp;
				if (key.equals(pro)) {
					return properties.getProperty(key);
				}
			}
		}
		return null;
	}

	public static String getUserInfo(HttpServletRequest request) {
		String token = request.getHeader("Access-Token");
		return getUserId(token);
	}

	public static String getUserId(String token) {
		if (StringUtil.isEmpty(token)) {
			return null;
		} else {
			try {
				return JWTUtil.getUserId(token);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}
}
