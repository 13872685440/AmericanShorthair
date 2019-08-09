package com.cat.system.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;

import com.cat.boot.model.BaseEntity;

@Entity
@Table(name = "Sys_RoleApp")
@BatchSize(size = 50)
@DynamicInsert
public class RoleApp extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4636325077242215082L;

	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Id
	@GeneratedValue(generator = "system-uuid")
	@Column(length = 40)
	private String id;

	/** 角色 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "roleId", nullable = false)
	@BatchSize(size = 50)
	private Role role;

	/** 应用 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "appId", nullable = false)
	@BatchSize(size = 50)
	private AppTree app;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public AppTree getApp() {
		return app;
	}

	public void setApp(AppTree app) {
		this.app = app;
	}

	@Override
	public String toString() {
		return this.id;
	}
}
