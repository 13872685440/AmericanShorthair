package com.cat.activiti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cat.activiti.jsonbean.Test3Bean;
import com.cat.activiti.model.Test3;
import com.cat.activiti.service.TaskService;
import com.cat.boot.jsonbean.ResultBean;
import com.cat.boot.service.BaseHome;
import com.cat.boot.util.StringUtil;
import com.cat.system.controller.UserInfoService;

@RestController
@RequestMapping("/test3")
public class Test3Home extends BaseHome<Test3> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6632498524197187878L;

	@Autowired
	public UserInfoService userInfoService;

	@Autowired
	public TaskService taskService;

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(@RequestParam String id) {
		if (!StringUtil.isEmpty(id)) {
			Test3 entity = findById(id);
			return ResultBean.getSucess(Test3Bean.setThis(entity));
		} else {
			return ResultBean.getSucess(Test3Bean.setThis(new Test3()));
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Test3Bean entity) throws Exception {
		Test3 e = saveEntity(entity);
		return ResultBean.getSucess(Test3Bean.setThis(e));
	}

	private Test3 saveEntity(Test3Bean entity) {
		Test3 bean = StringUtil.isEmpty(entity.getId()) ? new Test3() : findById(entity.getId());
		Test3Bean.clone(bean, entity);
		baseService.save(bean);

		return bean;
	}
}
