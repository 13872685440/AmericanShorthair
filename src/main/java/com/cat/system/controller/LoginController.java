package com.cat.system.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.cat.boot.jsonbean.UserBean;
import com.cat.boot.service.BaseService;
import com.cat.boot.service.RedisUtils;
import com.cat.boot.util.StringUtil;
import com.cat.boot.util.JWTUtil;

public class LoginController {

	@Autowired(required = true)
	public BaseService baseService;

	@Autowired
	public RedisUtils redisUtils;

	// 双设备 web app
	protected String iniToken(UserBean bean) {
		String userId = bean.getId();
		// web
		if (isWeb(bean.getService())) {
			userId = userId + "_web";
		} else {
			userId = userId + "_app";
		}
		String JWTtoken = JWTUtil.generateToken(bean.getId());
		// 查询是否有redis中是否有userId
		if (redisUtils.hasKey(userId)) {
			String ytoken = redisUtils.get(userId);
			if (redisUtils.hasKey(ytoken)) {
				// 删除原token
				redisUtils.delete(ytoken);
			}
			// 替换userId的token
			redisUtils.replace(userId, JWTtoken);
		} else {
			if (!StringUtil.isEmpty(bean.getToken())) {
				if (redisUtils.hasKey(bean.getToken())) {
					// 删除原token
					redisUtils.delete(bean.getToken());
				}
			}
			redisUtils.put(userId, JWTtoken);
		}
		// 加入token
		bean.setToken(JWTtoken);
		redisUtils.put(JWTtoken, JSONObject.toJSONString(bean));
		
		return JWTtoken;
	}

	protected boolean isWeb(String service) {
		if (!"webLoginService".equals(service)) {
			return false;
		} else {
			return true;
		}
	}
}
