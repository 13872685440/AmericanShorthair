package com.cat.system.controller;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cat.boot.jsonbean.BaseQueryHelp;
import com.cat.boot.jsonbean.QueryResultBean;
import com.cat.boot.jsonbean.ResultBean;
import com.cat.boot.service.BaseNqtQuery;
import com.cat.boot.util.NameQueryUtil;
import com.cat.system.jsonbean.PermissionBean;
import com.cat.system.jsonbean.RoleBean;
import com.cat.system.model.Permission;
import com.cat.system.model.Role;

@RestController
@Scope("prototype")
@RequestMapping("/role")
public class RoleQuery extends BaseNqtQuery<Role> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8020811162265557457L;

	@Override
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String query(BaseQueryHelp parms) throws Exception {
		excuteQuery(parms);
		return ResultBean.getSucess(new QueryResultBean(parms, RoleBean.setThis(results, baseService)));
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(@RequestParam String id) {
		return super.delete(id);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/query_permission", method = RequestMethod.GET)
	public String query_permission(PermissionBean bean) throws Exception {
		List<Permission> p = (List<Permission>) baseService.getList("Permission", "o.ct desc", true,
				NameQueryUtil.setParams("role.name", bean.getRoleName()));
		return ResultBean.getSucess(p);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/delete_permission", method = RequestMethod.POST)
	public String delete_permission(@RequestParam String id) {
		// 删除对应的人员权限
		baseService.delete4Prop("org_user_permission", true, NameQueryUtil.setParams("permission", id));
		Permission entity = (Permission) baseService.findById(Permission.class, id);
		baseService.delete(entity);
		return ResultBean.getSucess("删除成功");
	}
}
