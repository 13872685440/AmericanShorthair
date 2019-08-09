package com.cat.activiti.process;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cat.activiti.enumable.TaskType;
import com.cat.activiti.model.TaskExt;
import com.cat.boot.exception.CatException;
import com.cat.boot.jsonbean.BaseAppBean;
import com.cat.boot.jsonbean.ResultBean;
import com.cat.boot.jsonbean.TaskBean;
import com.cat.boot.util.StringUtil;

@RestController
@RequestMapping("/process")
public class ProcessAction extends BaseProcessAction {

	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	@Transactional(propagation = Propagation.REQUIRED)
	public String submit(TaskBean bean) throws CatException {
		Map<String, Object> variables = new HashMap<String, Object>();
		set初始化流程变量(variables, bean);
		variables.putAll(bean.getVariables());
		set意见流程变量(variables, bean);

		List<String> userIds = get下一步办理人(variables, bean);
		if (StringUtil.isListEmpty(userIds)) {
			return ResultBean.getResultBean("500", "未找到下一步办理人", "");
		}

		processContorller.提交流程(bean.getProcessDefinition(), variables);
		return ResultBean.getSucess("");
	}

	@RequestMapping(value = "/next", method = RequestMethod.POST)
	@Transactional(propagation = Propagation.REQUIRED)
	public String next(TaskBean bean) throws CatException {
		Map<String, Object> variables = new HashMap<String, Object>();
		set初始化流程变量(variables, bean);
		variables.putAll(bean.getVariables());
		set意见流程变量(variables, bean);
		if(!StringUtil.isMapEmpty(bean.getUsermaps())) {
		List<String> userIds = get下一步办理人(variables, bean);
		if (StringUtil.isListEmpty(userIds)) {
			return ResultBean.getResultBean("500", "未找到下一步办理人", "");
		}}

		TaskExt task = (TaskExt) baseService.findById(TaskExt.class, bean.getTask_id());
		processContorller.完成流程(task.getTaskId(), variables);
		// 结束该任务
		task.setTaskType(TaskType.已办);
		baseService.save(task, BaseAppBean.iniBean(variables));
		return ResultBean.getSucess("");
	}

	@RequestMapping(value = "/countersign", method = RequestMethod.POST)
	@Transactional(propagation = Propagation.REQUIRED)
	public String countersign(TaskBean bean) throws CatException {
		Map<String, Object> variables = new HashMap<String, Object>();

		set初始化流程变量(variables, bean);
		variables.putAll(bean.getVariables());
		set意见流程变量(variables, bean);

		List<String> userIds = get下一步办理人(variables, bean);
		if (StringUtil.isListEmpty(userIds)) {
			return ResultBean.getResultBean("500", "未找到下一步办理人", "");
		}

		variables.put(bean.getNode(), userIds);

		TaskExt task = (TaskExt) baseService.findById(TaskExt.class, bean.getTask_id());
		processContorller.完成流程(task.getTaskId(), variables);
		// 结束该任务
		task.setTaskType(TaskType.已办);
		baseService.save(task, BaseAppBean.iniBean(variables));
		return ResultBean.getSucess("");
	}

}
