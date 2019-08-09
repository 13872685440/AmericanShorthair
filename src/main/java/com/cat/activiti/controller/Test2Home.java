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
import com.cat.activiti.jsonbean.Test2Bean;
import com.cat.activiti.model.Test2;
import com.cat.activiti.service.TaskService;
import com.cat.boot.exception.CatException;
import com.cat.boot.jsonbean.ResultBean;
import com.cat.boot.service.BaseHome;
import com.cat.boot.util.StringUtil;
import com.cat.system.controller.UserInfoService;

@RestController
@RequestMapping("/test2")
public class Test2Home extends BaseHome<Test2> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3984412936343025548L;

	@Autowired
	public UserInfoService userInfoService;

	@Autowired
	public TaskService taskService;

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(@RequestParam String id) {
		if (!StringUtil.isEmpty(id)) {
			Test2 entity = findById(id);
			return ResultBean.getSucess(Test2Bean.setThis(entity));
		} else {
			return ResultBean.getSucess(Test2Bean.setThis(new Test2()));
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Test2Bean entity) throws Exception {
		saveEntity(entity);
		return ResultBean.getSucess("sucess");
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public String submit(Test2Bean entity) throws CatException {
		Test2 bean = saveEntity(entity);
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
		if (StringUtil.isListEmpty(ids)) {
			return ResultBean.getResultBean("500", "未找到下一步办理人", "");
		}
		String a = taskService.save待办任务(entity, ids);
		ResultBean resultBean = JSONObject.parseObject(a, ResultBean.class);
		bean.setTaskId((String) resultBean.getResult());
		baseService.save(bean, entity);
		return a;
	}

	private Test2 saveEntity(Test2Bean entity) {
		Test2 bean = StringUtil.isEmpty(entity.getId()) ? new Test2() : findById(entity.getId());
		Test2Bean.clone(bean, entity);
		baseService.save(bean);

		return bean;
	}
}
