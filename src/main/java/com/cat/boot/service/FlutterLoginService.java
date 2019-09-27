package com.cat.boot.service;

import org.springframework.stereotype.Service;

import com.cat.boot.jsonbean.UserBean;
import com.cat.boot.util.Encodes;
import com.cat.boot.util.NameQueryUtil;

@Service("flutterLoginService")
public class FlutterLoginService extends BaseLoginService {

	@Override
	public UserBean login(UserBean token) {
		UserBean bean = UserBean.getUserBean((Object) baseService.getSimple("User", "system", "User_by_username",
				NameQueryUtil.setParams("loginName", token.getUsername())));
		if (bean != null) {
			byte[] salt = Encodes.decodeHex(bean.getSalt());
			String pwd = bean.getPassword();
			// 将pwd 和 salt 移出
			bean.setSalt("");
			bean.setPassword("");
			String password = entryptPassword(token.getPassword(), salt);
			if (pwd.equals(password)) {
				return bean;
			}
		}
		return null;
	}
}
