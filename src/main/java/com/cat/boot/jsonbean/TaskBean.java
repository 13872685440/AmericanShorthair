package com.cat.boot.jsonbean;

import java.util.HashMap;
import java.util.Map;

import com.cat.boot.model.BaseEntity;

public class TaskBean extends BaseAppBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8514299642685645213L;

	private String task_id;

	private String role;

	private String currentRole;

	private String view;

	private String nodeName;

	private String taskName;

	private String firsttransactor;

	private Integer step;
	
	private Integer ystep;

	private String bean_ebcn;

	private String shyj;

	private String shjl;

	private String shsj;

	private String shjd;

	private String shjgName;

	private Boolean isfirst = false;
	
	private String role_userId;
	
	private String service;
	
	private String processDefinition;
	
	private String processId;
	
	private String node;
	
	private Map<String, String> variables = new HashMap<String, String>();
	
	private Map<String, String> usermaps = new HashMap<String, String>();

	public String getTask_id() {
		return task_id;
	}

	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getCurrentRole() {
		return currentRole;
	}

	public void setCurrentRole(String currentRole) {
		this.currentRole = currentRole;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getFirsttransactor() {
		return firsttransactor;
	}

	public void setFirsttransactor(String firsttransactor) {
		this.firsttransactor = firsttransactor;
	}

	public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	public Integer getYstep() {
		return ystep;
	}

	public void setYstep(Integer ystep) {
		this.ystep = ystep;
	}

	public String getBean_ebcn() {
		return bean_ebcn;
	}

	public void setBean_ebcn(String bean_ebcn) {
		this.bean_ebcn = bean_ebcn;
	}

	public String getShyj() {
		return shyj;
	}

	public void setShyj(String shyj) {
		this.shyj = shyj;
	}

	public String getShjl() {
		return shjl;
	}

	public void setShjl(String shjl) {
		this.shjl = shjl;
	}

	public String getShsj() {
		return shsj;
	}

	public void setShsj(String shsj) {
		this.shsj = shsj;
	}

	public String getShjd() {
		return shjd;
	}

	public void setShjd(String shjd) {
		this.shjd = shjd;
	}

	public String getShjgName() {
		return shjgName;
	}

	public void setShjgName(String shjgName) {
		this.shjgName = shjgName;
	}

	public Boolean getIsfirst() {
		return isfirst;
	}

	public void setIsfirst(Boolean isfirst) {
		this.isfirst = isfirst;
	}

	public String getRole_userId() {
		return role_userId;
	}

	public void setRole_userId(String role_userId) {
		this.role_userId = role_userId;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public Map<String, String> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, String> variables) {
		this.variables = variables;
	}

	public Map<String, String> getUsermaps() {
		return usermaps;
	}

	public void setUsermaps(Map<String, String> usermaps) {
		this.usermaps = usermaps;
	}

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

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public TaskBean() {
	}

	public TaskBean(BaseEntity entity) {
		super(entity);
	}

}
