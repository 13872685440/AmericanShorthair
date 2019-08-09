package com.cat.activiti.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.cat.activiti.jsonbean.TestBean;
import com.cat.activiti.model.Test;
import com.cat.activiti.service.TaskService;
import com.cat.boot.exception.CatException;
import com.cat.boot.jsonbean.ResultBean;
import com.cat.boot.service.BaseHome;
import com.cat.boot.util.StringUtil;
import com.cat.system.controller.UserInfoService;

@RestController
@RequestMapping("/test")
public class TestHome extends BaseHome<Test> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4296921248310816531L;

	@Autowired
	public UserInfoService userInfoService;

	@Autowired
	public TaskService taskService;

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(@RequestParam String id) {
		if (!StringUtil.isEmpty(id)) {
			Test entity = findById(id);
			return ResultBean.getSucess(TestBean.setThis(entity));
		} else {
			return ResultBean.getSucess(TestBean.setThis(new Test()));
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(TestBean entity) throws Exception {
		saveEntity(entity);
		return ResultBean.getSucess("sucess");
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public String submit(TestBean entity) throws CatException {
		Test bean = saveEntity(entity);
		entity.setId(bean.getId());
		if (StringUtil.isEmpty(entity.getRole_userId()) && StringUtil.isEmpty(entity.getRole())) {
			String a = taskService.end流程(entity);
			return a;
		}
		List<String> ids = new ArrayList<String>();
		if (!StringUtil.isEmpty(entity.getRole_userId())) {
			ids.add(entity.getRole_userId());
		} else {
			ids = userInfoService.getUserIds(entity.getRole(), "", false);
		}
		String a = taskService.save待办任务(entity, ids);
		ResultBean resultBean = JSONObject.parseObject(a, ResultBean.class);
		if (!"200".equals(resultBean.getCode())) {
			return a;
		} else {
			bean.setTaskId((String) resultBean.getResult());
			baseService.save(bean, entity);
		}
		return a;
	}

	private Test saveEntity(TestBean entity) {
		Test bean = StringUtil.isEmpty(entity.getId()) ? new Test() : findById(entity.getId());
		TestBean.clone(bean, entity);
		baseService.save(bean);

		return bean;
	}
}
