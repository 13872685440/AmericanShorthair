package com.cat.system.jsonbean;

import java.util.ArrayList;
import java.util.List;

import com.cat.boot.jsonbean.BaseAppBean;
import com.cat.boot.model.BaseEntity;
import com.cat.boot.service.BaseService;
import com.cat.boot.util.StringUtil;
import com.cat.system.model.Organization;

public class OrganizationBean extends BaseAppBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 737118319268038097L;

	private String code;

	private String clc;

	private String name;

	private String wn;

	private String superior_code;

	private String superior_name;

	private Boolean isLeaf;

	private Integer xh;

	private Integer weighted;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWn() {
		return wn;
	}

	public void setWn(String wn) {
		this.wn = wn;
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

	public Boolean getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public Integer getXh() {
		return xh;
	}

	public void setXh(Integer xh) {
		this.xh = xh;
	}

	public Integer getWeighted() {
		return weighted;
	}

	public void setWeighted(Integer weighted) {
		this.weighted = weighted;
	}

	public OrganizationBean() {

	}

	public OrganizationBean(BaseEntity entity) {
		super(entity);
	}

	public static OrganizationBean setThis(Organization entity) {
		OrganizationBean bean = new OrganizationBean(entity);
		bean.setClc(entity.getClc());
		bean.setCode(entity.getCode());
		bean.setIsLeaf(entity.isLeaf());
		bean.setName(entity.getName());
		bean.setWn(entity.getWn());
		bean.setXh(entity.getXh());
		bean.setWeighted(entity.getWeighted());
		bean.setSuperior_code(entity.getSuperior() != null ? entity.getSuperior().getCode() : "");
		bean.setSuperior_name(entity.getSuperior() != null ? entity.getSuperior().getName() : "");

		return bean;
	}

	public static List<OrganizationBean> setThis(List<Organization> entitys) {
		List<OrganizationBean> beans = new ArrayList<OrganizationBean>();
		if (!StringUtil.isListEmpty(entitys)) {
			for (Organization bean : entitys) {
				beans.add(setThis(bean));
			}
		}
		return beans;
	}

	public static void clone(Organization bean, OrganizationBean entity, BaseService baseService) {
		bean.setClc(entity.getClc());
		bean.setName(entity.getName());
		bean.setWn(entity.getWn());
		bean.setXh(entity.getXh());
		bean.setWeighted(entity.getWeighted());
		if (!StringUtil.isEmpty(entity.getSuperior_code())) {
			Organization superior = (Organization) baseService.findById(Organization.class, entity.getSuperior_code());
			bean.setSuperior(superior);
			bean.setWeighted(countWeighted(superior));
		} else {
			bean.setWeighted(1);
		}
	}

	public static Integer countWeighted(Organization superior) {
		int i = 10;
		while (superior.getSuperior() != null) {
			i = i * 10;
			superior = superior.getSuperior();
		}
		return i;
	}
}
