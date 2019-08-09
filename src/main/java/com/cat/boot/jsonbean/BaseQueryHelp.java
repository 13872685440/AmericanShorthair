package com.cat.boot.jsonbean;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.cat.boot.util.StringUtil;

/**
 * 
 * @Title: BaseQueryHelp.java
 * @Package com.xt.frame.controller
 * @Description: 查询参数 用以存储分页,查询参数等参数 存储于session中
 * @author wul
 * @date 2015年7月10日 下午4:52:33
 * @version V1.0
 */
public class BaseQueryHelp {

	private long pageSize = 0;

	private long pageNo = 0;
	
	private String userId;

	private String clearQpn;

	private long totalRecordCount = 0;

	private String sortField;

	private String sortOrder;

	private Map<String,Object> params = new HashMap<String,Object>();

	public Object parm(String name) {
		name = StringUtils.trim(name);
		Object p = params.get(name);
		if (p == null) {
			params.put(name, p);
			return p;
		} else {
			return p;
		}
	}

	public boolean hasparm(String name) {
		name = StringUtils.trim(name);
		Object p = params.get(name);
		if (p == null) {
			return false;
		} else {
			if (StringUtil.isEmpty(String.valueOf(p))) {
				return false;
			}
		}
		return true;
	}

	public boolean equalparm(String name, String value) {
		name = StringUtils.trim(name);
		Object p = params.get(name);
		if (p == null) {
			return false;
		} else {
			if (StringUtil.isEmpty(String.valueOf(p)) || !value.equals(String.valueOf(p))) {
				return false;
			}
		}
		return true;
	}

	public boolean hasnotparm(String name) {
		return !hasparm(name);
	}

	public void clearParm() {
		if (!StringUtils.isEmpty(clearQpn)) {
			params.remove(clearQpn);
			clearQpn = null;
		}
	}

	public void clearParm(String _pn) {
		if (!StringUtils.isEmpty(_pn)) {
			params.remove(_pn);
		}
	}

	public Object pv(String name) {
		name = StringUtils.trim(name);
		Object p = params.get(name);
		if (p == null) {
			return null;
		} else {
			return p;
		}
	}

	public long getOffset() {
		return (pageNo - 1) * pageSize;
	}

	public long getPageSize() {

		return pageSize;
	}

	public String getOrder() {
		if (!StringUtil.isEmpty(sortField)) {
			return "o." + sortField + " " + ("ascend".equals(sortOrder) ? "asc" : "desc");
		} else {
			return "";
		}
	}

	public void setPageSize(long pageSize) {

		this.pageSize = pageSize;
	}

	public long getPageNo() {
		return pageNo;
	}

	public void setPageNo(long pageNo) {
		this.pageNo = pageNo;
	}

	public void setClearQpn(String clearQpn) {

		this.clearQpn = clearQpn;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public long getTotalRecordCount() {
		return totalRecordCount;
	}

	public void setTotalRecordCount(long totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
