package com.cat.activiti.jsonbean;

import java.util.ArrayList;
import java.util.List;

import com.cat.activiti.model.Test;
import com.cat.boot.jsonbean.BaseAppBean;
import com.cat.boot.jsonbean.TaskBean;
import com.cat.boot.model.BaseEntity;
import com.cat.boot.util.StringUtil;

public class TestBean extends TaskBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -692132617743342956L;

	private String simple;

	public String getSimple() {
		return simple;
	}

	public void setSimple(String simple) {
		this.simple = simple;
	}

	public TestBean() {

	}

	public TestBean(BaseEntity entity) {
		super(entity);
	}

	public static TestBean setThis(Test entity) {
		TestBean bean = new TestBean(entity);
		bean.setSimple(entity.getSimple());
		bean.setStep(entity.getStep());
		
		bean.setTask_id(entity.getTaskId());
		return bean;
	}

	public static List<TestBean> setThis(List<Test> entitys) {
		List<TestBean> beans = new ArrayList<TestBean>();
		if (!StringUtil.isListEmpty(entitys)) {
			for (Test bean : entitys) {
				beans.add(setThis(bean));
			}
		}
		return beans;
	}

	public static void clone(Test bean, TestBean entity) {
		BaseAppBean.clone(bean, entity);
		bean.setSimple(entity.getSimple());
		bean.setStep(entity.getStep() != null ? entity.getStep() : 0);
		bean.setShjdnow(entity.getNodeName());
	}
}
