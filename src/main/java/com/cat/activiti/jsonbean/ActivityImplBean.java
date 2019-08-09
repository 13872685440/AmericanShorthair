package com.cat.activiti.jsonbean;

import org.activiti.engine.impl.pvm.process.ActivityImpl;

public class ActivityImplBean {

	private ActivityImpl activityImpl;

	private String nodeName;

	private String name;

	private String taskAlertId;

	private String flowId;

	private String dqjd;

	public ActivityImpl getActivityImpl() {
		return activityImpl;
	}

	public void setActivityImpl(ActivityImpl activityImpl) {
		this.activityImpl = activityImpl;
	}

	public String getDqjd() {
		return dqjd;
	}

	public void setDqjd(String dqjd) {
		this.dqjd = dqjd;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTaskAlertId() {
		return taskAlertId;
	}

	public void setTaskAlertId(String taskAlertId) {
		this.taskAlertId = taskAlertId;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

}
