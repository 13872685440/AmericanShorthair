package com.cat.system.jsonbean;

import java.util.ArrayList;
import java.util.List;

public class RoleInfoBean {

	private String roleId;

	private String roleName;

	private List<PermissionInfoBean> permissions = new ArrayList<PermissionInfoBean>();

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<PermissionInfoBean> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<PermissionInfoBean> permissions) {
		this.permissions = permissions;
	}

	public RoleInfoBean() {

	}

	public RoleInfoBean(String roleId, String roleName, List<PermissionInfoBean> permissions) {
		this.roleId = roleId;
		this.roleName = roleName;
		this.permissions = permissions;
	}
}
