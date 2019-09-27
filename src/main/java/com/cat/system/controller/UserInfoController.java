package com.cat.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cat.boot.jsonbean.ResultBean;
import com.cat.boot.jsonbean.UserBean;
import com.cat.boot.service.BaseService;
import com.cat.boot.service.RedisUtils;
import com.cat.boot.util.NameQueryUtil;
import com.cat.boot.util.StringUtil;
import com.cat.system.jsonbean.AppTreeInfoBean;
import com.cat.system.jsonbean.PermissionInfoBean;
import com.cat.system.jsonbean.PostInformationBean;
import com.cat.system.jsonbean.RoleInfoBean;
import com.cat.system.jsonbean.UserInfoBean;
import com.cat.system.model.AppTree;
import com.cat.system.model.Permission;
import com.cat.system.model.Role;

@RestController
@RequestMapping("/info")
public class UserInfoController {

	@Autowired
	public BaseService baseService;

	@Autowired
	public RedisUtils redisUtils;

	@Autowired
	public UserInfoService userInfoService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String user(HttpServletRequest request) throws Exception {
		String token = request.getHeader("Access-Token");
		UserBean ybean = JSON.parseObject(redisUtils.get(token), UserBean.class);
		UserInfoBean bean = new UserInfoBean(ybean);
		bean.setAvatar("");
		List<String> userRoles = new ArrayList<String>();
		if ("admin".equals(bean.getUsername())) {
			RoleInfoBean roleInfo = new RoleInfoBean("SYS_ADMINISTRATOR", "超级管理员", new ArrayList<PermissionInfoBean>());

			bean.getRoles().add(roleInfo);

			userRoles.add("SYS_ADMINISTRATOR");
		} else {
			List<PostInformationBean> posts = userInfoService.getPostInformation(bean.getId());
			bean.setPosts(posts);
			List<String> roles = new ArrayList<String>();
			List<String> permissions = new ArrayList<String>();
			if (!StringUtil.isListEmpty(posts)) {
				for (PostInformationBean p : posts) {
					if (!StringUtil.isListEmpty(p.getRoles())) {
						roles.addAll(p.getRoles());
					}
					if (!StringUtil.isListEmpty(p.getPermissions())) {
						permissions.addAll(p.getPermissions());
					}
				}
			}

			// 查询role
			if (!StringUtil.isListEmpty(roles)) {
				Map<String, RoleInfoBean> infos = new HashMap<String, RoleInfoBean>();
				List<Role> rs = (List<Role>) baseService.getList("Role", null, true,
						NameQueryUtil.setParams("name", roles));
				for (Role role : rs) {
					RoleInfoBean roleInfo = new RoleInfoBean(role.getName(), role.getDes(),
							new ArrayList<PermissionInfoBean>());
					infos.put(role.getName(), roleInfo);
					userRoles.add(role.getName());
				}
				if (!StringUtil.isListEmpty(permissions)) {
					List<Permission> ps = (List<Permission>) baseService.getList("Permission", null, true,
							NameQueryUtil.setParams("name", permissions));
					for (Permission permission : ps) {
						String key = permission.getName();
						String roleName = permission.getRole().getName();
						if (StringUtil.isMapContainsKey(infos, roleName)) {
							PermissionInfoBean p = new PermissionInfoBean(key, permission.getDes());
							RoleInfoBean info = infos.get(roleName);
							info.getPermissions().add(p);
							infos.replace(roleName, info);

							userRoles.add(key);
						}
					}
				}
				if (!StringUtil.isMapEmpty(infos)) {
					bean.getRoles().addAll(infos.values());
				}
			}
		}
		ybean.setUserRoles(userRoles);

		redisUtils.replace(token, JSONObject.toJSONString(ybean));
		bean.setUserRoles(userRoles);
		return ResultBean.getSucess(bean);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/router", method = RequestMethod.POST)
	public String router(@RequestBody UserBean bean) throws Exception {
		List<String> roles = bean.getUserRoles();
		if (!StringUtil.isListEmpty(roles)) {
			List<AppTree> apps = (List<AppTree>) baseService.getList("AppTree", "system", "AppTree_by_Role",
					NameQueryUtil.setParams("roles", roles));
			if (!StringUtil.isListEmpty(apps)) {
				return ResultBean.getSucess(AppTreeInfoBean.iniAppTree(apps, baseService));
			}
		}
		return ResultBean.getSucess("");
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/app_router", method = RequestMethod.POST)
	public String app_router(@RequestBody UserBean bean) throws Exception {
		List<String> roles = bean.getUserRoles();
		if (!StringUtil.isListEmpty(roles)) {
			List<AppTree> apps = (List<AppTree>) baseService.getList("AppTree", "system", "AppTree_by_Role",
					NameQueryUtil.setParams("roles", roles));
			if (!StringUtil.isListEmpty(apps)) {
				return ResultBean.getSucess(AppTreeInfoBean.iniAppTree_App(apps));
			}
		}
		return ResultBean.getSucess("");
	}
}
