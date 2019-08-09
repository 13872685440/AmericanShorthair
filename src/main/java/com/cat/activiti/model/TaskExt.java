package com.cat.activiti.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;

import com.cat.activiti.enumable.TaskType;
import com.cat.boot.exception.CatException;
import com.cat.boot.model.BaseEntity;
import com.cat.boot.util.CalendarUtil;

@Entity
@Table(name = "Task_TaskExt")
@BatchSize(size = 50)
@DynamicInsert
public class TaskExt extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 729312770327930824L;

	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Id
	@GeneratedValue(generator = "system-uuid")
	@Column(length = 40)
	private String id;

	/** 实体bean关键域值 */
	@Column(nullable = false, length = 50)
	private String keyValue;

	/** 任务路径 */
	@Column(nullable = false, length = 100, name = "task_view")
	private String view;

	/** 流程ID */
	@Column(nullable = true, name = "task_id", length = 30)
	private String taskId;

	/** 流程实例ID */
	@Column(nullable = true, name = "processInstance_id", length = 30)
	private String processInstanceId;

	/** 流程部署ID */
	@Column(nullable = true, name = "deployment_id", length = 30)
	private String deploymentId;

	/** 流程图名称 */
	@Column(nullable = true, name = "view_name", length = 30)
	private String viewName;

	/** 任务名称 */
	@Column(nullable = false, length = 200, name = "task_name")
	private String taskName;

	/** 节点名称 */
	@Column(nullable = false, length = 50, name = "node_name")
	private String nodeName;

	/** 经办人 当经办人为多人时,用,隔开 */
	@Column(nullable = true, length = 500, name = "transactor_groups")
	private String transactorgroups;

	/** 当前经办人 */
	@Column(nullable = true, length = 40, name = "transactor")
	private String transactor;

	/** 申请人 此处灵活存储 可存储机构，也可存储人员 */
	@Column(nullable = true, length = 50, name = "first_transactor")
	private String firsttransactor;

	/** 上一步办理人 此处灵活存储 可存储机构，也可存储人员 */
	@Column(nullable = true, length = 50, name = "previous_transactor")
	private String previoustransactor;

	/** 任务办理状态 */
	@Enumerated(EnumType.STRING)
	@Column(name = "task_type")
	private TaskType taskType = TaskType.待办;

	/** 挂起前状态 */
	@Enumerated(EnumType.STRING)
	@Column(name = "hangup")
	private TaskType hangup;

	/** 挂起原因 */
	@Column(nullable = true, length = 40, name = "tqreason")
	private String tqreason;

	/** 任务提醒时间 */
	@Column(nullable = true, length = 40, name = "txsj")
	private String txsj;

	/** 任务到期时间 */
	@Column(nullable = true, length = 40, name = "dqsj")
	private String dqsj;

	/** 存储会签人员，用于任务到期和短信提醒 */
	@Column(nullable = true, length = 1000, name = "countersign_r")
	private String countersignr;

	@Transient
	private String userName;

	@Transient
	private boolean istx;

	@Transient
	private boolean isdq;

	public boolean isIstx() {
		if (this.txsj == null) {
			return false;
		}
		try {
			if (Calendar.getInstance().compareTo(CalendarUtil.StringToCalendar(txsj)) <= 0) {
				return false;
			} else {
				return true;
			}
		} catch (CatException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void setIstx(boolean istx) {
		this.istx = istx;
	}

	public boolean isIsdq() {
		if (this.dqsj == null) {
			return false;
		}
		try {
			if (Calendar.getInstance().compareTo(CalendarUtil.StringToCalendar(dqsj)) <= 0) {
				return false;
			} else {
				return true;
			}
		} catch (CatException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void setIsdq(boolean isdq) {
		this.isdq = isdq;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getDeploymentId() {
		return deploymentId;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getTransactorgroups() {
		return transactorgroups;
	}

	public void setTransactorgroups(String transactorgroups) {
		this.transactorgroups = transactorgroups;
	}

	public String getTransactor() {
		return transactor;
	}

	public void setTransactor(String transactor) {
		this.transactor = transactor;
	}

	public String getFirsttransactor() {
		return firsttransactor;
	}

	public void setFirsttransactor(String firsttransactor) {
		this.firsttransactor = firsttransactor;
	}

	public String getPrevioustransactor() {
		return previoustransactor;
	}

	public void setPrevioustransactor(String previoustransactor) {
		this.previoustransactor = previoustransactor;
	}

	public TaskType getTaskType() {
		return taskType;
	}

	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}

	public TaskType getHangup() {
		return hangup;
	}

	public void setHangup(TaskType hangup) {
		this.hangup = hangup;
	}

	public String getTqreason() {
		return tqreason;
	}

	public void setTqreason(String tqreason) {
		this.tqreason = tqreason;
	}

	public String getTxsj() {
		return txsj;
	}

	public void setTxsj(String txsj) {
		this.txsj = txsj;
	}

	public String getDqsj() {
		return dqsj;
	}

	public void setDqsj(String dqsj) {
		this.dqsj = dqsj;
	}

	public String getCountersignr() {
		return countersignr;
	}

	public void setCountersignr(String countersignr) {
		this.countersignr = countersignr;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return this.id;
	}

	public TaskExt() {
	}

	public TaskExt(String keyValue, String view, String taskId, String processInstanceId, String deploymentId,
			String viewName, String taskName, String nodeName, String transactorgroups, String transactor,
			String firsttransactor, String previoustransactor, TaskType taskType, String txsj, String dqsj) {
		this.keyValue = keyValue;
		this.view = view;
		this.taskId = taskId;
		this.processInstanceId = processInstanceId;
		this.deploymentId = deploymentId;
		this.viewName = viewName;
		this.taskName = taskName;
		this.nodeName = nodeName;
		this.transactorgroups = transactorgroups;
		this.transactor = transactor;
		this.firsttransactor = firsttransactor;
		this.previoustransactor = previoustransactor;
		this.taskType = taskType;
		this.txsj = txsj;
		this.dqsj = dqsj;
	}

	public TaskExt(String keyValue, String view, String taskName, String nodeName, String transactorgroups,
			String transactor, String firsttransactor, String previoustransactor, TaskType taskType, String txsj,
			String dqsj) {
		this.keyValue = keyValue;
		this.view = view;
		this.taskName = taskName;
		this.nodeName = nodeName;
		this.transactorgroups = transactorgroups;
		this.transactor = transactor;
		this.firsttransactor = firsttransactor;
		this.previoustransactor = previoustransactor;
		this.taskType = taskType;
		this.txsj = txsj;
		this.dqsj = dqsj;
	}
}
