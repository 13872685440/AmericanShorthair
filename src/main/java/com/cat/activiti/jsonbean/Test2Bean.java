package com.cat.activiti.jsonbean;

import java.util.ArrayList;
import java.util.List;

import com.cat.activiti.model.Test2;
import com.cat.boot.jsonbean.BaseAppBean;
import com.cat.boot.jsonbean.TaskBean;
import com.cat.boot.model.BaseEntity;
import com.cat.boot.util.StringUtil;

public class Test2Bean extends TaskBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8322380597207989572L;

	private String simple;

	private String shjdnow;

	public String getSimple() {
		return simple;
	}

	public void setSimple(String simple) {
		this.simple = simple;
	}

	public String getShjdnow() {
		return shjdnow;
	}

	public void setShjdnow(String shjdnow) {
		this.shjdnow = shjdnow;
	}

	public Test2Bean() {

	}

	public Test2Bean(BaseEntity entity) {
		super(entity);
	}

	public static Test2Bean setThis(Test2 entity) {
		Test2Bean bean = new Test2Bean(entity);
		bean.setSimple(entity.getSimple());
		bean.setStep(entity.getStep());
		bean.setShjdnow(entity.getShjdnow());

		bean.setTask_id(entity.getTaskId());
		return bean;
	}

	public static List<Test2Bean> setThis(List<Test2> entitys) {
		List<Test2Bean> beans = new ArrayList<Test2Bean>();
		if (!StringUtil.isListEmpty(entitys)) {
			for (Test2 bean : entitys) {
				beans.add(setThis(bean));
			}
		}
		return beans;
	}

	public static void clone(Test2 bean, Test2Bean entity) {
		BaseAppBean.clone(bean, entity);
		bean.setSimple(entity.getSimple());
		bean.setStep(entity.getStep() != null ? entity.getStep() : 0);
		bean.setShjdnow(entity.getNodeName());
	}
}
