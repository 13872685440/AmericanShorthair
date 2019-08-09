package com.cat.activiti.process;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;

import com.cat.activiti.enumable.TaskType;
import com.cat.activiti.model.ActivitiEntity;
import com.cat.activiti.model.Comment;
import com.cat.activiti.model.TaskExt;
import com.cat.boot.jsonbean.BaseAppBean;
import com.cat.boot.service.BaseService;
import com.cat.boot.util.CalendarUtil;
import com.cat.boot.util.StringUtil;

public class BaseTaskListener {

	@Autowired
	protected ProcessContorller processContorller;

	@Autowired
	protected BaseService baseService;

	// 角色
	private Expression role;

	// 流程变量
	private Expression variable;

	// 访问路径
	private Expression view;

	// 节点变量
	private Expression node;

	@SuppressWarnings("unchecked")
	protected TaskExt save待办任务(DelegateTask delegatetask, Map<String, Object> maps) {
		ProcessDefinition pd = processContorller.get流程定义(delegatetask.getProcessDefinitionId());
		List<String> task_userIds = (List<String>) maps.get("task_userIds");
		String userIds = StringUtil.listToString(task_userIds);
		TaskExt task = new TaskExt((String) maps.get("keyValue"), (String) getView().getValue(delegatetask),
				delegatetask.getId(), delegatetask.getProcessInstanceId(), pd.getDeploymentId(),
				pd.getDiagramResourceName(), pd.getName() + "[" + (String) maps.get("taskName") + "]",
				delegatetask.getName(), userIds, null, (String) maps.get("userName"), (String) maps.get("userName"),
				null, null, null);
		if (task_userIds.size() > 1) {
			delegatetask.addCandidateUser(userIds);
			task.setTaskType(TaskType.待领取);
		} else {
			delegatetask.setAssignee(userIds);
			task.setTaskType(TaskType.待办);
			task.setTransactor(userIds);
		}
		baseService.save(task, BaseAppBean.iniBean(maps));
		return task;
	}

	protected TaskExt save待办任务(DelegateTask delegatetask, Map<String, Object> maps, String userIds) {
		ProcessDefinition pd = processContorller.get流程定义(delegatetask.getProcessDefinitionId());
		TaskExt task = new TaskExt((String) maps.get("keyValue"), (String) getView().getValue(delegatetask),
				delegatetask.getId(), delegatetask.getProcessInstanceId(), pd.getDeploymentId(),
				pd.getDiagramResourceName(), pd.getName() + "[" + (String) maps.get("taskName") + "]",
				delegatetask.getName(), userIds, null, (String) maps.get("userName"), (String) maps.get("userName"),
				null, null, null);
		delegatetask.setAssignee(userIds);
		task.setTaskType(TaskType.待办);
		task.setTransactor(userIds);
		baseService.save(task, BaseAppBean.iniBean(maps));
		return task;
	}

	protected TaskExt save会签待办任务(DelegateTask delegatetask, Map<String, Object> maps, String userIds) {
		ProcessDefinition pd = processContorller.get流程定义(delegatetask.getProcessDefinitionId());
		TaskExt task = new TaskExt((String) maps.get("keyValue"), (String) getView().getValue(delegatetask),
				delegatetask.getId(), delegatetask.getProcessInstanceId(), pd.getDeploymentId(),
				pd.getDiagramResourceName(), pd.getName() + "[" + (String) maps.get("taskName") + "]",
				delegatetask.getName(), null, null, (String) maps.get("userName"), (String) maps.get("userName"), null,
				null, null);
		task.setCountersignr(userIds);
		baseService.save(task, BaseAppBean.iniBean(maps));
		return task;
	}

	protected void save审核历史(String nodeName, Map<String, Object> maps, boolean isfirst) {
		Comment comment = new Comment((String) maps.get("ebcn"), (String) maps.get("keyValue"),
				(String) maps.get("shyj"), CalendarUtil.getYyyyMmDdHHmmss(Calendar.getInstance()),
				(String) maps.get("shsj"), (String) maps.get("userId"), (String) maps.get("userName"),
				(String) maps.get("shjgName"), (String) maps.get("shjl"), nodeName, true, isfirst, 0);
		baseService.save(comment, BaseAppBean.iniBean(maps));
	}

	protected void saveEntity(DelegateTask delegatetask, TaskExt task, Map<String, Object> maps) {
		saveEntity(delegatetask.getProcessInstanceId(), delegatetask.getName(), task, maps);
	}

	protected void saveEntity(DelegateExecution execution, TaskExt task, Map<String, Object> maps) {
		saveEntity(execution.getProcessInstanceId(), execution.getCurrentActivityName(), task, maps);
	}

	private void saveEntity(String processInstanceId, String shjdnow, TaskExt task, Map<String, Object> maps) {
		try {
			Object entity = baseService.findById(Class.forName((String) maps.get("ebcn")),
					(String) maps.get("keyValue"));
			if (entity instanceof ActivitiEntity) {
				ActivitiEntity ae = (ActivitiEntity) entity;
				ae.setProcessId(processInstanceId);
				ae.setTaskId(task != null ? task.getId() : null);
				ae.setShjdnow(shjdnow);

				baseService.save(ae);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Expression getRole() {
		return role;
	}

	public void setRole(Expression role) {
		this.role = role;
	}

	public Expression getVariable() {
		return variable;
	}

	public void setVariable(Expression variable) {
		this.variable = variable;
	}

	public Expression getView() {
		return view;
	}

	public void setView(Expression view) {
		this.view = view;
	}

	public Expression getNode() {
		return node;
	}

	public void setNode(Expression node) {
		this.node = node;
	}
}
