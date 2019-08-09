package com.cat.activiti.jsonbean;

import java.util.ArrayList;
import java.util.List;

import com.cat.activiti.model.Test3;
import com.cat.boot.jsonbean.BaseAppBean;
import com.cat.boot.jsonbean.TaskBean;
import com.cat.boot.model.BaseEntity;
import com.cat.boot.util.StringUtil;

public class Test3Bean extends TaskBean {
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

	public Test3Bean() {

	}

	public Test3Bean(BaseEntity entity) {
		super(entity);
	}

	public static Test3Bean setThis(Test3 entity) {
		Test3Bean bean = new Test3Bean(entity);
		bean.setSimple(entity.getSimple());
		bean.setStep(entity.getStep());
		bean.setShjdnow(entity.getShjdnow());
		bean.setProcessDefinition(entity.getProcessDefinition());
		bean.setProcessId(entity.getProcessId());

		bean.setTask_id(entity.getTaskId());
		return bean;
	}

	public static List<Test3Bean> setThis(List<Test3> entitys) {
		List<Test3Bean> beans = new ArrayList<Test3Bean>();
		if (!StringUtil.isListEmpty(entitys)) {
			for (Test3 bean : entitys) {
				beans.add(setThis(bean));
			}
		}
		return beans;
	}

	public static void clone(Test3 bean, Test3Bean entity) {
		BaseAppBean.clone(bean, entity);
		bean.setSimple(entity.getSimple());
		bean.setStep(entity.getStep() != null ? entity.getStep() : 0);
		bean.setShjdnow(entity.getNodeName());
	}
}
