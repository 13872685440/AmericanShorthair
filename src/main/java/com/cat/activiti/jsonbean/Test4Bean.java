package com.cat.activiti.jsonbean;

import java.util.ArrayList;
import java.util.List;

import com.cat.activiti.model.Test4;
import com.cat.boot.jsonbean.BaseAppBean;
import com.cat.boot.model.BaseEntity;
import com.cat.boot.util.StringUtil;

public class Test4Bean extends BaseAppBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8322380597207989572L;

	private String simple;

	private String simple2;
	
	public String getSimple() {
		return simple;
	}

	public void setSimple(String simple) {
		this.simple = simple;
	}

	public String getSimple2() {
		return simple2;
	}

	public void setSimple2(String simple2) {
		this.simple2 = simple2;
	}

	public Test4Bean() {

	}

	public Test4Bean(BaseEntity entity) {
		super(entity);
	}

	public static Test4Bean setThis(Test4 entity) {
		Test4Bean bean = new Test4Bean(entity);
		bean.setSimple(entity.getSimple());
		bean.setSimple2(entity.getSimple2());
		
		return bean;
	}

	public static List<Test4Bean> setThis(List<Test4> entitys) {
		List<Test4Bean> beans = new ArrayList<Test4Bean>();
		if (!StringUtil.isListEmpty(entitys)) {
			for (Test4 bean : entitys) {
				beans.add(setThis(bean));
			}
		}
		return beans;
	}

	public static void clone(Test4 bean, Test4Bean entity) {
		BaseAppBean.clone(bean, entity);
		bean.setSimple(entity.getSimple());
		bean.setSimple2(entity.getSimple2());
	}
}
