package com.cat.system.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "LOG_LogException")
@BatchSize(size = 50)
@DynamicInsert
public class LogException implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2433912044649489682L;

	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Id
	@GeneratedValue(generator = "system-uuid")
	@Column(length = 40)
	private String id;

	/** 用户 */
	@Column(length = 40)
	private String userId;

	/** 用户名 */
	@Column(length = 40)
	private String username;

	/** 异常发生的方法 */
	@Lob
	@Column
	@Basic(fetch = FetchType.LAZY)
	private String controllermethod;

	/** 异常消息 */
	@Lob
	@Column
	@Basic(fetch = FetchType.LAZY)
	private String message;

	/** 堆栈信息 */
	@Lob
	@Column
	@Basic(fetch = FetchType.LAZY)
	private String stackTrace;
	
	/** 发生时间 */
	@Column(name = "BE_ct", unique = false, length = 40)
	private String ct;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getControllermethod() {
		return controllermethod;
	}

	public void setControllermethod(String controllermethod) {
		this.controllermethod = controllermethod;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStackTrace() {
		return stackTrace;
	}

	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}

	public String getCt() {
		return ct;
	}

	public void setCt(String ct) {
		this.ct = ct;
	}

	@Override
	public String toString() {
		return this.id;
	}

}
