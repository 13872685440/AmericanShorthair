package com.cat.dictionary.jsonbean;

import java.util.ArrayList;
import java.util.List;

import com.cat.boot.jsonbean.BaseAppBean;
import com.cat.boot.model.BaseEntity;
import com.cat.boot.service.BaseService;
import com.cat.boot.util.StringUtil;
import com.cat.dictionary.model.Dictionary;

public class DictionaryBean extends BaseAppBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7805621702900286576L;

	private String typeCode;

	private String colorCode;

	private Integer xh;

	private String code;

	private String clc;

	private String wn;

	private String name;

	private Boolean isLeaf;

	private String superior_code;

	private String superior_name;

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getColorCode() {
		return colorCode;
	}

	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
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

	public Integer getXh() {
		return xh;
	}

	public void setXh(Integer xh) {
		this.xh = xh;
	}

	public DictionaryBean() {

	}

	public DictionaryBean(BaseEntity entity) {
		super(entity);
	}

	public static DictionaryBean setThis(Dictionary entity) {
		DictionaryBean bean = new DictionaryBean(entity);
		bean.setClc(entity.getClc());
		bean.setCode(entity.getCode());
		bean.setTypeCode(entity.getTypeCode());
		bean.setColorCode(entity.getColorCode());
		bean.setIsLeaf(entity.isLeaf());
		bean.setName(entity.getName());
		bean.setWn(entity.getWn());
		bean.setXh(entity.getXh());
		bean.setSuperior_code(entity.getSuperior() != null ? entity.getSuperior().getCode() : "");
		bean.setSuperior_name(entity.getSuperior() != null ? entity.getSuperior().getName() : "");

		return bean;
	}

	public static List<DictionaryBean> setThis(List<Dictionary> entitys) {
		List<DictionaryBean> beans = new ArrayList<DictionaryBean>();
		if (!StringUtil.isListEmpty(entitys)) {
			for (Dictionary bean : entitys) {
				beans.add(setThis(bean));
			}
		}
		return beans;
	}

	public static void clone(Dictionary bean, DictionaryBean entity, BaseService baseService) {
		bean.setClc(entity.getClc());
		bean.setTypeCode(entity.getTypeCode());
		bean.setColorCode(entity.getColorCode());
		
		bean.setName(entity.getName());
		bean.setWn(entity.getWn());
		bean.setXh(entity.getXh());

		if (!StringUtil.isEmpty(entity.getSuperior_code())) {
			bean.setSuperior((Dictionary) baseService.findById(Dictionary.class, entity.getSuperior_code()));
		}
	}
}
