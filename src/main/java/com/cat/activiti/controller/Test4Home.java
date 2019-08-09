package com.cat.activiti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cat.activiti.jsonbean.Test4Bean;
import com.cat.activiti.model.Test4;
import com.cat.activiti.service.TaskService;
import com.cat.boot.jsonbean.ResultBean;
import com.cat.boot.service.BaseHome;
import com.cat.boot.util.StringUtil;
import com.cat.file.controller.FileController;
import com.cat.system.controller.UserInfoService;

@RestController
@RequestMapping("/test4")
public class Test4Home extends BaseHome<Test4> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7674447286722807315L;

	@Autowired
	public UserInfoService userInfoService;

	@Autowired
	public TaskService taskService;

	@Autowired
	private FileController fileController;

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(@RequestParam String id) {
		if (!StringUtil.isEmpty(id)) {
			Test4 entity = findById(id);
			return ResultBean.getSucess(Test4Bean.setThis(entity));
		} else {
			return ResultBean.getSucess(Test4Bean.setThis(new Test4()));
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Test4Bean entity) throws Exception {
		Test4 e = saveEntity(entity);
		return ResultBean.getSucess(Test4Bean.setThis(e));
	}

	private Test4 saveEntity(Test4Bean entity) {
		Test4 bean = StringUtil.isEmpty(entity.getId()) ? new Test4() : findById(entity.getId());
		Test4Bean.clone(bean, entity);
		baseService.save(bean);

		fileController.replaceTmpId(entity.getTmpId(), bean.getId(), "com.cat.activiti.model.Test4");
		return bean;
	}
}
