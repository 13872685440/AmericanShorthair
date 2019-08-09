package com.cat.system.controller;

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
import com.cat.system.jsonbean.PermissionBean;
import com.cat.system.model.Permission;
import com.cat.system.model.Role;
import com.cat.system.model.UserPermission;

@RestController
@RequestMapping("/permission")
public class PermissionHome extends BaseHome<Permission> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6342065398024024648L;

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(@RequestParam String id) {
		if (!StringUtil.isEmpty(id)) {
			Permission entity = findById(id);
			return ResultBean.getSucess(PermissionBean.setThis(entity, baseService));
		} else {
			return ResultBean.getSucess(PermissionBean.setThis(new Permission(), baseService));
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(PermissionBean entity) throws Exception {
		Permission bean = findById(entity.getName());
		if (bean == null) {
			bean = new Permission();
			bean.setName(entity.getName());
			bean.setRole((Role) baseService.findById(Role.class, entity.getRoleName()));
		} else {
			// 清理原关联
			baseService.delete4Prop("org_user_permission", true,
					NameQueryUtil.setParams("permission", entity.getName()));
		}
		bean.setDes(entity.getDes());
		baseService.save(bean);

		if (!StringUtil.isListEmpty(entity.getUser_data())) {
			for (String string : entity.getUser_data()) {
				UserPermission p = new UserPermission();
				p.setYwid(string);
				p.setPermission(bean.getName());
				baseService.save(p);
			}
		}
		return ResultBean.getSucess("sucess");
	}

}
