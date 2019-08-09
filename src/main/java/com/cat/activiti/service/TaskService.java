package com.cat.activiti.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cat.activiti.enumable.TaskType;
import com.cat.activiti.model.Comment;
import com.cat.activiti.model.TaskExt;
import com.cat.boot.jsonbean.ResultBean;
import com.cat.boot.jsonbean.TaskBean;
import com.cat.boot.service.BaseService;
import com.cat.boot.util.CalendarUtil;
import com.cat.boot.util.NameQueryUtil;
import com.cat.boot.util.StringUtil;

@RestController
@RequestMapping("/task")
public class TaskService {

	@Autowired
	public BaseService baseService;

	@RequestMapping(value = "/savetask", method = RequestMethod.POST)
	public String save待办任务(TaskBean bean, List<String> userIds) {
		TaskExt task = new TaskExt(bean.getId(), bean.getView(), bean.getTaskName(), bean.getNodeName(),
				StringUtil.listToString(userIds), null,
				StringUtil.isEmpty(bean.getFirsttransactor()) ? bean.getUserName() : bean.getFirsttransactor(),
				bean.getUserName(), null, null, null);
		if (userIds.size() == 1) {
			task.setTaskType(TaskType.待办);
			task.setTransactor(StringUtil.listToString(userIds));
		} else {
			task.setTaskType(TaskType.待领取);
		}
		baseService.save(task, bean);
		save审核历史(bean);
		if (!StringUtil.isEmpty(bean.getTask_id())) {
			end待办任务(bean);
		}
		return ResultBean.getSucess(task.getId());
	}

	public String end流程(TaskBean bean) {
		try {
			save审核历史(bean);
			end待办任务(bean);
			return ResultBean.getSucess("");
		} catch (Exception e) {
			e.printStackTrace();
			return ResultBean.getResultBean("500", "服务异常", "");
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/comments", method = RequestMethod.GET)
	public String getComments(TaskBean bean) {
		List<Comment> c = (List<Comment>) baseService.getList("Comment", "o.ct asc", true,
				NameQueryUtil.setParams("ebcn", bean.getBean_ebcn(), "keyValue", bean.getId()));
		return ResultBean.getSucess(c);
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public String getTask(TaskBean bean) {
		TaskExt t = (TaskExt) baseService.findById(TaskExt.class, bean.getId());
		return ResultBean.getSucess(t);
	}

	public void save审核历史(TaskBean bean) {
		Comment comment = new Comment(bean.getBean_ebcn(), bean.getId(), bean.getShyj(),
				CalendarUtil.getYyyyMmDdHHmmss(Calendar.getInstance()),
				StringUtil.isEmpty(bean.getShsj()) ? CalendarUtil.getYyyyMmDdHHmmss(Calendar.getInstance())
						: bean.getShsj(),
				bean.getUserId(), bean.getUserName(), bean.getShjgName(), bean.getShjl(), bean.getShjd(), true,
				bean.getIsfirst(), bean.getYstep());
		baseService.save(comment, bean);
	}

	protected void end待办任务(TaskBean bean) {
		TaskExt task = (TaskExt) baseService.findById(TaskExt.class, bean.getTask_id());
		task.setTaskType(TaskType.已办);
		task.setTransactor(bean.getUserId());
		baseService.save(task);
	}
}
