package com.cat.system.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.cat.boot.jsonbean.UserBean;
import com.cat.boot.service.BaseService;
import com.cat.boot.util.NameQueryUtil;
import com.cat.boot.util.StringUtil;
import com.cat.system.jsonbean.PostInformationBean;
import com.cat.system.model.Organization;
import com.cat.system.model.Permission;
import com.cat.system.model.PostInformation;
import com.cat.system.model.Role;
import com.cat.system.model.User;

@RestController
public class UserInfoService {

	@Autowired
	public BaseService baseService;

	/**
	 * 根据userId查询任职情况 以及任职时对应的角色
	 * 
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PostInformationBean> getPostInformation(String userId) {
		List<PostInformation> ps = (List<PostInformation>) baseService.getList("PostInformation",
				"o.isleaf.code asc, o.ct asc", true, NameQueryUtil.setParams("person.id", userId, "isleaf.code",
						Arrays.asList(new String[] { "00010001", "00010002" })));
		List<PostInformationBean> beans = new ArrayList<PostInformationBean>();
		if (!StringUtil.isListEmpty(ps)) {
			for (PostInformation p : ps) {
				PostInformationBean bean = PostInformationBean.setThis(p);
				Organization o = p.getOrganization();
				// 查询任职机构对应的角色
				List<String> s = new ArrayList<String>();
				while (o != null) {
					s.add(o.getCode());
					o = o.getSuperior();
				}
				s.add(userId);
				List<String> roles = (List<String>) baseService.getList("org_user_role", "", true, "role",
						NameQueryUtil.setParams("ywid", s));
				bean.setRoles(roles);
				// 查询permission
				if (StringUtil.isListEmpty(roles)) {
					List<String> permissions = (List<String>) baseService.getList("Permission", "sys",
							"Permission_by_RoleAndUserId", NameQueryUtil.setParams("userId", userId, "roles", roles));
					bean.setPermissions(permissions);
				}
				beans.add(bean);
			}
		}
		return beans;
	}

	/**
	 * 根据role和organ查询用户信息 当organ为空时 根据role查询 role不能为空
	 * 
	 * @param role
	 *            or permission
	 * @param organ
	 * 
	 * @param fiter_part_time
	 *            过滤兼职
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<UserBean> getUserBean(String roleName, String organId, boolean fiter_part_time) {
		// 查询拥有权限的人
		Permission permission = (Permission) baseService.findById(Permission.class, roleName);
		Role role = new Role();
		if (permission == null) {
			role = (Role) baseService.findById(Role.class, roleName);
		} else {
			role = permission.getRole();
		}
		// 查询 role关联
		List<String> s = (List<String>) baseService.getList("org_user_role", "", true, "ywid",
				NameQueryUtil.setParams("role", role.getName()));
		Map<String, List<String>> organMap = getOrganMap();
		if (StringUtil.isListEmpty(s)) {
			return null;
		}
		List<String> os = new ArrayList<String>();
		for (String key : s) {
			os.add(key);
			if (StringUtil.isMapContainsKey(organMap, key)) {
				os.addAll(organMap.get(key));
			}
		}
		// 查询 所有人
		List<String> p = (List<String>) baseService.getList("org_post_information", "", true, "person_id",
				NameQueryUtil.setParams("isleaf",
						fiter_part_time ? "00010001" : Arrays.asList(new String[] { "00010001", "00010002" }),
						"organization_id", os));
		if (!StringUtil.isListEmpty(p)) {
			s.addAll(p);
		}

		// 如果permission==null 传入的是role 则查询所有有效用户且在organId中任职的
		List<User> u = new ArrayList<User>();
		if (permission == null) {
			u = (List<User>) baseService.getList("User", "system", "User_by_Ids",
					NameQueryUtil.setParams("ids", s, "organId", organId));
		} else {
			// 先查询在permission中的user
			List<String> pp = (List<String>) baseService.getList("Org_User_Permission", "", true,
					NameQueryUtil.setParams("ywid", s, "permission", roleName));
			if (!StringUtil.isListEmpty(pp)) {
				u = (List<User>) baseService.getList("User", "system", "User_by_Ids",
						NameQueryUtil.setParams("ids", pp, "organId", organId));
			}
		}

		List<UserBean> beans = new ArrayList<UserBean>();
		if (!StringUtil.isListEmpty(u)) {
			for (User user : u) {
				UserBean bean = new UserBean();
				bean.setId(user.getId());
				bean.setName(user.getName());
				beans.add(bean);
			}
		}
		return beans;
	}
	
	public List<String> getUserIds(String roleName, String organId, boolean fiter_part_time) {
		List<UserBean> beans = getUserBean(roleName, organId, fiter_part_time);
		List<String> ids = new ArrayList<String>();
		if (!StringUtil.isListEmpty(beans)) {
			for (UserBean user : beans) {
				ids.add(user.getId());
			}
		}
		return ids;
	}

	@SuppressWarnings("unchecked")
	public Map<String, List<String>> getOrganMap() {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		List<Organization> organs = (List<Organization>) baseService.getList("Organization", "o.code asc", true);
		List<String> all_organ = new ArrayList<String>();
		for (Organization organ : organs) {
			if (organ.getSuperior() != null) {
				String key = organ.getSuperior().getCode();
				if (StringUtil.isMapContainsKey(map, key)) {
					List<String> s = map.get(key);
					s.add(organ.getCode());
					map.replace(key, s);
				} else {
					List<String> s = new ArrayList<String>();
					s.add(organ.getCode());
					map.put(key, s);
				}
			}
			all_organ.add(organ.getCode());
		}
		map.put("all_organ", all_organ);
		return map;
	}
}
