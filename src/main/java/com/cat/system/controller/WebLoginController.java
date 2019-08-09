package com.cat.system.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.cat.boot.jsonbean.ResultBean;
import com.cat.boot.jsonbean.UserBean;
import com.cat.boot.service.BaseLoginService;
import com.cat.boot.util.SpringContextUtil;
import com.cat.boot.util.StringUtil;

@RestController
@RequestMapping("/auth")
public class WebLoginController extends LoginController {

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestBody UserBean token) throws Exception {
		try {
			BaseLoginService service = (BaseLoginService) SpringContextUtil.getBean(token.getService());
			UserBean bean = service.login(token);
			if (bean != null) {
				bean.setService(token.getService());
				bean.setToken(iniToken(bean));
				return ResultBean.getSucess(bean);
			} else {
				return ResultBean.getResultBean("401", "用户名或者密码错误", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResultBean.getResultBean("500", "服务异常", null);
		}
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public void logout(@RequestParam String token, @RequestParam String type) throws Exception {
		if (!StringUtil.isEmpty(token)) {
			UserBean ybean = JSON.parseObject(redisUtils.get(token), UserBean.class);
			// 清理掉redis
			if (!StringUtil.isEmpty(ybean.getId())) {
				redisUtils.delete(ybean.getId() + "_" + type);
			}
			redisUtils.delete(token);
		}
	}
}
