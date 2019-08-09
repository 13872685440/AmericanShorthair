package com.cat.system.jsonbean;

import java.util.ArrayList;
import java.util.List;

import com.cat.boot.jsonbean.BaseAppBean;
import com.cat.boot.service.BaseService;
import com.cat.boot.util.NameQueryUtil;
import com.cat.boot.util.StringUtil;
import com.cat.system.model.Permission;

public class PermissionBean extends BaseAppBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7628967917476580565L;

	private String roleName;

	private String name;

	private String des;

	private List<String> user_data = new ArrayList<String>();

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public List<String> getUser_data() {
		return user_data;
	}

	public void setUser_data(List<String> user_data) {
		this.user_data = user_data;
	}

	@SuppressWarnings("unchecked")
	public static PermissionBean setThis(Permission entity, BaseService baseService) {
		PermissionBean bean = new PermissionBean();
		bean.setName(entity.getName());
		bean.setDes(entity.getDes());
		if (!StringUtil.isEmpty(entity.getName())) {
			List<String> user_data = (List<String>) baseService.getList("org_user_permission", null, true, "ywid",
					NameQueryUtil.setParams("permission", entity.getName()));
			bean.setUser_data(user_data);
		}
		return bean;
	}
}
