package com.cat.activiti.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.cat.boot.model.BaseEntity;

@MappedSuperclass
public abstract class ActivitiEntity extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1814430909090473406L;

	@Column(name = "act_processDefinition", length = 20)
	private String processDefinition;

	@Column(name = "act_processId", length = 20)
	private String processId;

	@Column(name = "task_id", length = 40)
	private String taskId;

	@Column(name = "shjdnow", length = 50)
	private String shjdnow;

	// 步骤 0-1-2-3-4
	@Column(length = 40)
	private Integer step = 0;

	public String getProcessDefinition() {
		return processDefinition;
	}

	public void setProcessDefinition(String processDefinition) {
		this.processDefinition = processDefinition;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getShjdnow() {
		return shjdnow;
	}

	public void setShjdnow(String shjdnow) {
		this.shjdnow = shjdnow;
	}

	public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}
}
