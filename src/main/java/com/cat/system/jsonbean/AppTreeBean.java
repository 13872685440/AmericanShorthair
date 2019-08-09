package com.cat.system.jsonbean;

import java.util.ArrayList;
import java.util.List;

import com.cat.boot.jsonbean.BaseAppBean;
import com.cat.boot.model.BaseEntity;
import com.cat.boot.service.BaseService;
import com.cat.boot.util.StringUtil;
import com.cat.system.model.AppTree;

public class AppTreeBean extends BaseAppBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5778305382702295389L;

	private String des;

	private String path;

	private Integer xh;

	private String code;

	private String clc;

	private String wn;

	private String name;

	private String superior_code;

	private String superior_name;

	private Boolean isLeaf;

	private String titleImage;

	private String colorCode;

	private String component;

	private String icon;

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getXh() {
		return xh;
	}

	public void setXh(Integer xh) {
		this.xh = xh;
	}

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

	public String getTitleImage() {
		return titleImage;
	}

	public void setTitleImage(String titleImage) {
		this.titleImage = titleImage;
	}

	public String getColorCode() {
		return colorCode;
	}

	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public AppTreeBean() {

	}

	public AppTreeBean(BaseEntity entity) {
		super(entity);
	}

	public static AppTreeBean setThis(AppTree entity) {
		AppTreeBean bean = new AppTreeBean(entity);
		bean.setClc(entity.getClc());
		bean.setCode(entity.getCode());
		bean.setColorCode(entity.getColorCode());
		bean.setDes(entity.getDes());
		bean.setIsLeaf(entity.isLeaf());
		bean.setName(entity.getName());
		bean.setPath(entity.getPath());
		bean.setTitleImage(entity.getTitleImage());
		bean.setWn(entity.getWn());
		bean.setXh(entity.getXh());
		bean.setSuperior_code(entity.getSuperior() != null ? entity.getSuperior().getCode() : "");
		bean.setSuperior_name(entity.getSuperior() != null ? entity.getSuperior().getName() : "");
		bean.setComponent(entity.getComponent());
		bean.setIcon(entity.getIcon());

		return bean;
	}

	public static List<AppTreeBean> setThis(List<AppTree> entitys) {
		List<AppTreeBean> beans = new ArrayList<AppTreeBean>();
		if (!StringUtil.isListEmpty(entitys)) {
			for (AppTree bean : entitys) {
				beans.add(setThis(bean));
			}
		}
		return beans;
	}

	public static void clone(AppTree bean, AppTreeBean entity, BaseService baseService) {
		bean.setClc(entity.getClc());
		bean.setColorCode(entity.getColorCode());
		bean.setDes(entity.getDes());

		bean.setName(entity.getName());
		bean.setPath(entity.getPath());
		bean.setTitleImage(entity.getTitleImage());
		bean.setWn(entity.getWn());
		bean.setXh(entity.getXh());
		bean.setComponent(entity.getComponent());
		bean.setIcon(entity.getIcon());
		if (!StringUtil.isEmpty(entity.getSuperior_code())) {
			AppTree sup = (AppTree) baseService.findById(AppTree.class, entity.getSuperior_code());
			bean.setSuperior(sup);
			
			bean.setLxh(sup.getLxh()*10 + bean.getXh());
		}else {
			bean.setLxh(bean.getXh().longValue());
		}
	}	
}
