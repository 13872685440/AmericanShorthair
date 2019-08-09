package com.cat.boot.jsonbean;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

import com.cat.boot.model.BaseEntity;
import com.cat.boot.util.StringUtil;

public class BaseAppBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6698933094309935028L;

	private String id;

	private String ct;
	
	private String tmpId;

	private String userId;

	private String userName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCt() {
		return ct;
	}

	public void setCt(String ct) {
		this.ct = ct;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTmpId() {
		return tmpId;
	}

	public void setTmpId(String tmpId) {
		this.tmpId = tmpId;
	}

	public BaseAppBean() {

	}

	public BaseAppBean(BaseEntity entity) {
		this.id = StringUtil.isEmpty(entity.getId()) ? "" : entity.getId();
		this.ct = StringUtil.isEmpty(entity.getCt()) ? "" : entity.getCt();
		this.tmpId = UUID.randomUUID().toString();
	}

	public static void clone(BaseEntity bean, BaseAppBean entity) {
		if (StringUtil.isEmpty(bean.getId()) && StringUtil.isEmpty(bean.getCt())) {
			bean.setCrtUid(entity.getUserId());
			bean.setCrtUname(entity.getUserName());
		} else {
			bean.setLmUid(entity.getUserId());
			bean.setLmUname(entity.getUserName());
		}
	}

	public static BaseAppBean iniBean(Map<String, Object> maps) {
		BaseAppBean bean = new BaseAppBean();
		bean.setUserId((String) maps.get("userId"));
		bean.setUserName((String) maps.get("userName"));
		return bean;
	}

}
