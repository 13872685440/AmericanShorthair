package com.cat.activiti.controller;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cat.activiti.enumable.TaskType;
import com.cat.activiti.model.TaskExt;
import com.cat.boot.jsonbean.ResultBean;
import com.cat.boot.jsonbean.TaskBean;
import com.cat.boot.service.BaseHome;

@RestController
@RequestMapping("/taskext")
public class TaskExtHome extends BaseHome<TaskExt> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1743331337224900660L;

	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/receive", method = RequestMethod.POST)
	public String receiveTask(TaskBean bean) {
		TaskExt task = (TaskExt) baseService.findById(TaskExt.class, bean.getId());
		if (!TaskType.待领取.equals(task.getTaskType())) {
			return ResultBean.getResultBean("400", "任务已被领取", "");
		} else {
			task.setTransactor(bean.getUserId());
			task.setTaskType(TaskType.已领取);
			baseService.save(task);
		}
		return ResultBean.getSucess("");
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/cancelreceive", method = RequestMethod.POST)
	public String cancelreceiveTask(TaskBean bean) {
		TaskExt task = (TaskExt) baseService.findById(TaskExt.class, bean.getId());
		task.setTaskType(TaskType.待领取);
		task.setTransactor(null);
		baseService.save(task);
		return ResultBean.getSucess("");
	}
}
