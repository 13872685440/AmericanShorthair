package com.cat.activiti.process;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cat.activiti.jsonbean.ActivityImplBean;
import com.cat.activiti.util.ProcessDiagramGenerator;

@Controller
public class ProcessContorller {

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private HistoryService historyService;

	public ProcessInstance 提交流程(String key, Map<String, Object> variables) {
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(key, variables);
		return pi;
	}

	public void 完成流程(String taskId, Map<String, Object> variables) {
		taskService.complete(taskId, variables);
	}

	public void 领取任务(String taskId, String userId) {
		taskService.claim(taskId, userId);
	}

	public void 取消领取(String taskId) {
		taskService.setAssignee(taskId, null);
	}

	public List<Task> get当前任务(String processInstanceId, String taskDefinitionKey) {
		List<Task> tasks = taskService.createTaskQuery().taskDefinitionKey(taskDefinitionKey)
				.processInstanceId(processInstanceId).orderByTaskCreateTime().asc().list();
		return tasks;
	}

	public ProcessInstance get流程实例(String processInstanceId) {
		ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId)
				.singleResult();
		return pi;
	}

	public ProcessDefinition get流程定义(String processDefinitionId) {
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId)
				.singleResult();
		return pd;
	}

	public void set流程变量(String processInstanceId, Object... objs) {
		Map<String, Object> maps = new HashMap<String, Object>();
		for (int i = 0; i < objs.length; i++) {
			if (i % 2 == 0) {
				maps.put((String) objs[i], objs[i + 1]);
			}
		}
		runtimeService.setVariables(processInstanceId, maps);
	}

	public void reset流程变量(String processInstanceId, String key, Object value) {
		runtimeService.removeVariable(processInstanceId, key);
		runtimeService.setVariable(processInstanceId, key, value);
	}

	public Object get流程变量(String processInstanceId, String varname) {
		return runtimeService.getVariable(processInstanceId, varname);
	}

	public Map<String, Object> get流程变量(String processInstanceId) {
		return runtimeService.getVariables(processInstanceId);
	}

	public void 部署流程(ZipInputStream zipInputStream) {
		repositoryService.createDeployment().addZipInputStream(zipInputStream).deploy();
	}

	public InputStream get流程图(String deploymentId, String resourceName) {
		return repositoryService.getResourceAsStream(deploymentId, resourceName);
	}

	public InputStream get流程执行轨迹(String processInstanceId) throws Exception {
		// 获取历史流程实例
		HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		if (historicProcessInstance == null) {
			throw new Exception("获取流程实例ID[" + processInstanceId + "]对应的历史流程实例失败！");
		} else {
			ProcessDefinitionEntity processDefinition = getProcessDefinitionEntity(
					historicProcessInstance.getProcessDefinitionId());
			// 获取流程历史中已执行节点，并按照节点在流程中执行先后顺序排序
			List<HistoricActivityInstance> historicActivityInstanceList = historyService
					.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId)
					.orderByHistoricActivityInstanceId().asc().list();
			// 已执行的节点ID集合
			List<String> executedActivityIdList = new ArrayList<String>();
			for (HistoricActivityInstance activityInstance : historicActivityInstanceList) {
				executedActivityIdList.add(activityInstance.getActivityId());
			}
			BpmnModel bpmnModel = repositoryService.getBpmnModel(historicProcessInstance.getProcessDefinitionId());
			return ProcessDiagramGenerator.generateDiagram(processDefinition, "png", executedActivityIdList, bpmnModel);
		}
	}

	public List<ActivityImplBean> find活动节点(String processdefinitionId) {
		List<ActivityImplBean> activitiList = new ArrayList<ActivityImplBean>();
		ProcessDefinitionEntity pe = getProcessDefinitionEntity(processdefinitionId);
		List<ActivityImpl> tmps = pe.getActivities();
		findActivityUserTask(tmps, activitiList, pe);
		return activitiList;
	}

	public List<ActivityImplBean> findHistoricActivity(String processInstanceId) {

		List<ActivityImplBean> activitiList = new ArrayList<ActivityImplBean>();

		HistoricProcessInstance pi = historyService.createHistoricProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService
				.getProcessDefinition(pi.getProcessDefinitionId());
		List<HistoricActivityInstance> hais = historyService.createHistoricActivityInstanceQuery()
				.processInstanceId(processInstanceId).orderByHistoricActivityInstanceEndTime().asc().list();

		for (HistoricActivityInstance hai : hais) {
			ActivityImpl xxx = processDefinitionEntity.findActivity(hai.getActivityId());
			ActivityImplBean ab = new ActivityImplBean();
			ab.setActivityImpl(xxx);
			if (hai.getEndTime() != null) {
				ab.setDqjd("2");
			} else {
				ab.setDqjd("1");
			}
			activitiList.add(ab);
		}

		return activitiList;
	}

	private List<ActivityImplBean> findActivityUserTask(List<ActivityImpl> tmps, List<ActivityImplBean> activitiList,
			ProcessDefinitionEntity pe) {
		if (tmps != null && !tmps.isEmpty()) {
			for (ActivityImpl activityImpl : tmps) {
				String type = (String) activityImpl.getProperty("type");
				// System.out.println(type + "\n");
				if ("userTask".equals(type)) {
					ActivityImplBean ab = new ActivityImplBean();
					ab.setActivityImpl(activityImpl);
					ab.setName(pe.getName());
					ab.setFlowId(pe.getId());
					ab.setNodeName((String) activityImpl.getProperty("name"));
					// 查询是否设定过办理时限
					// TaskAlert ta = taskAlertDao.getTaskAlert(
					// activityImpl.getId(), pe.getId());
					// if (ta != null) {
					// ab.setTaskAlertId(ta.getId());
					// }
					activitiList.add(ab);
				} else if ("subProcess".equals(type)) {
					List<ActivityImpl> subTmps = activityImpl.getActivities();
					findActivityUserTask(subTmps, activitiList, pe);
				}
			}
		}
		return activitiList;
	}

	public ProcessDefinitionEntity getProcessDefinitionEntity(String key) {
		return (ProcessDefinitionEntity) repositoryService.getProcessDefinition(key);
	}
}
