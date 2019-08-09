package com.cat.system.jsonbean;

public class PermissionInfoBean {

	private String permissionId;

	private String permissionName;

	public String getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public PermissionInfoBean() {

	}

	public PermissionInfoBean(String permissionId, String permissionName) {
		this.permissionId = permissionId;
		this.permissionName = permissionName;
	}
}
