package com.cat.system.controller;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cat.boot.jsonbean.ResultBean;
import com.cat.boot.service.BaseHome;
import com.cat.boot.util.NameQueryUtil;
import com.cat.boot.util.StringUtil;
import com.cat.system.jsonbean.RoleBean;
import com.cat.system.model.AppTree;
import com.cat.system.model.Role;
import com.cat.system.model.RoleApp;
import com.cat.system.model.UserRole;

@RestController
@RequestMapping("/role")
public class RoleHome extends BaseHome<Role> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7239096940289755826L;

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(@RequestParam String id) {
		if (!StringUtil.isEmpty(id)) {
			Role entity = findById(id);
			return ResultBean.getSucess(RoleBean.setThis(entity, baseService));
		} else {
			return ResultBean.getSucess(RoleBean.setThis(new Role(), baseService));
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(RoleBean entity) throws Exception {
		Role bean = findById(entity.getName());
		if (bean == null) {
			bean = new Role();
			bean.setName(entity.getName());
		}
		RoleBean.clone(bean, entity, baseService);
		baseService.save(bean);
		return ResultBean.getSucess("sucess");
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/save_app", method = RequestMethod.POST)
	public String save_app(RoleBean entity) throws Exception {
		// 清理原关联
		baseService.delete4Prop("sys_role_app", true, NameQueryUtil.setParams("role_id", entity.getName()));

		Role bean = findById(entity.getName());
		if (!StringUtil.isListEmpty(entity.getApp_data())) {
			// 查询App
			List<AppTree> as = (List<AppTree>) baseService.getList("AppTree", null, true,
					NameQueryUtil.setParams("code", entity.getApp_data()));
			if (!StringUtil.isListEmpty(as)) {
				for (AppTree a : as) {
					RoleApp r = new RoleApp();
					r.setApp(a);
					r.setRole(bean);
					baseService.save(r);
				}
			}
		}
		return ResultBean.getSucess(entity.getApp_data());
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/save_organ_user", method = RequestMethod.POST)
	public String save_organ_user(RoleBean entity) throws Exception {
		// 清理原关联
		baseService.delete4Prop("org_user_role", true, NameQueryUtil.setParams("role", entity.getName()));

		if (!StringUtil.isListEmpty(entity.getOrgan_user_data())) {
			for (String string : entity.getOrgan_user_data()) {
				UserRole u = new UserRole();
				u.setRole(entity.getName());
				u.setYwid(string);
				baseService.save(u);
			}
		}
		return ResultBean.getSucess(entity.getOrgan_user_data());
	}
}
