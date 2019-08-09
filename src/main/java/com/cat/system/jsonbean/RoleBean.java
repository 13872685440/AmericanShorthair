package com.cat.system.jsonbean;

import java.util.ArrayList;
import java.util.List;

import com.cat.boot.jsonbean.BaseAppBean;
import com.cat.boot.model.BaseEntity;
import com.cat.boot.service.BaseService;
import com.cat.boot.util.NameQueryUtil;
import com.cat.boot.util.StringUtil;
import com.cat.system.model.Role;

public class RoleBean extends BaseAppBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6671731908396707216L;

	private String name;

	private String des;

	private List<String> app_data = new ArrayList<String>();

	private List<String> organ_user_data = new ArrayList<String>();

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

	public List<String> getApp_data() {
		return app_data;
	}

	public void setApp_data(List<String> app_data) {
		this.app_data = app_data;
	}

	public List<String> getOrgan_user_data() {
		return organ_user_data;
	}

	public void setOrgan_user_data(List<String> organ_user_data) {
		this.organ_user_data = organ_user_data;
	}

	public RoleBean() {

	}

	public RoleBean(BaseEntity entity) {
		super(entity);
	}

	@SuppressWarnings("unchecked")
	public static RoleBean setThis(Role entity, BaseService baseService) {
		RoleBean bean = new RoleBean();
		bean.setName(entity.getName());
		bean.setDes(entity.getDes());
		if (!StringUtil.isEmpty(entity.getName())) {
			List<String> app_data = (List<String>) baseService.getList("sys_role_app", null, true, "app_id",
					NameQueryUtil.setParams("role_id", entity.getName()));
			bean.setApp_data(app_data);

			List<String> organ_user_data = (List<String>) baseService.getList("org_user_role", null, true, "ywid",
					NameQueryUtil.setParams("role", entity.getName()));
			bean.setOrgan_user_data(organ_user_data);
		}
		return bean;
	}

	public static List<RoleBean> setThis(List<Role> entitys, BaseService baseService) {
		List<RoleBean> beans = new ArrayList<RoleBean>();
		if (!StringUtil.isListEmpty(entitys)) {
			for (Role bean : entitys) {
				beans.add(setThis(bean, baseService));
			}
		}
		return beans;
	}

	public static void clone(Role bean, RoleBean entity, BaseService baseService) {
		bean.setDes(entity.getDes());
		bean.setName(entity.getName());
	}
}
