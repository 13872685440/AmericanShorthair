package com.cat.system.jsonbean;

import java.util.ArrayList;
import java.util.List;

import com.cat.system.model.TaskRouter;
import com.cat.boot.jsonbean.BaseAppBean;
import com.cat.boot.model.BaseEntity;
import com.cat.boot.service.BaseService;
import com.cat.boot.util.StringUtil;

public class TaskRouterBean extends BaseAppBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7777525968411090290L;

	private String code;

	private String clc;

	private String wn;

	private String name;

	private String superior_code;

	private String superior_name;

	private Boolean isLeaf;

	private String component;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getClc() {
		return clc;
	}

	public void setClc(String clc) {
		this.clc = clc;
	}

	public String getWn() {
		return wn;
	}

	public void setWn(String wn) {
		this.wn = wn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public String getSuperior_code() {
		return superior_code;
	}

	public void setSuperior_code(String superior_code) {
		this.superior_code = superior_code;
	}

	public String getSuperior_name() {
		return superior_name;
	}

	public void setSuperior_name(String superior_name) {
		this.superior_name = superior_name;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public TaskRouterBean() {

	}

	public TaskRouterBean(BaseEntity entity) {
		super(entity);
	}

	public static TaskRouterBean setThis(TaskRouter entity) {
		TaskRouterBean bean = new TaskRouterBean(entity);
		bean.setClc(entity.getClc());
		bean.setCode(entity.getCode());
		bean.setIsLeaf(entity.isLeaf());
		bean.setName(entity.getName());
		bean.setWn(entity.getWn());
		bean.setSuperior_code(entity.getSuperior() != null ? entity.getSuperior().getCode() : "");
		bean.setSuperior_name(entity.getSuperior() != null ? entity.getSuperior().getName() : "");
		bean.setComponent(entity.getComponent());

		return bean;
	}

	public static List<TaskRouterBean> setThis(List<TaskRouter> entitys) {
		List<TaskRouterBean> beans = new ArrayList<TaskRouterBean>();
		if (!StringUtil.isListEmpty(entitys)) {
			for (TaskRouter bean : entitys) {
				beans.add(setThis(bean));
			}
		}
		return beans;
	}

	public static void clone(TaskRouter bean, TaskRouterBean entity, BaseService baseService) {
		bean.setClc(entity.getClc());

		bean.setName(entity.getName());
		bean.setWn(entity.getWn());
		bean.setComponent(entity.getComponent());
		if (!StringUtil.isEmpty(entity.getSuperior_code())) {
			bean.setSuperior((TaskRouter) baseService.findById(TaskRouter.class, entity.getSuperior_code()));
		}
	}
}
