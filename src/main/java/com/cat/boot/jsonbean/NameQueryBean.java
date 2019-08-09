package com.cat.boot.jsonbean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class NameQueryBean implements Serializable {

	private static final long serialVersionUID = 4885945993524889304L;

	/** 查询 语句 */
	private String ql;

	/** 查询数量 语句 */
	private String qlCount;

	private Map<String, QueryFilterBean> qfbs = new HashMap<String, QueryFilterBean>();

	/** 是否使用数据库 sql查询 */
	private boolean userNativeQuery = false;

	/** 默认排序 */
	private String orderBy;

	/** 查询参数 */
	private Map<String, QueryParamDefineBean> qpds = new HashMap<String, QueryParamDefineBean>();

	public void reset() {

		ql = null;
		userNativeQuery = false;
		orderBy = null;
		qlCount = null;
		qfbs.clear();
		qpds.clear();
	}

	public String getQl() {

		return ql;
	}

	public void setQl(String ql) {

		this.ql = ql;
	}

	public String getQlCount() {

		return qlCount;
	}

	public void setQlCount(String qlCount) {

		this.qlCount = qlCount;
	}

	public boolean isUserNativeQuery() {

		return userNativeQuery;
	}

	public void setUserNativeQuery(boolean userNativeQuery) {

		this.userNativeQuery = userNativeQuery;
	}

	public String getOrderBy() {

		return orderBy;
	}

	public void setOrderBy(String orderBy) {

		this.orderBy = orderBy;
	}

	public void setQpds(Map<String, QueryParamDefineBean> qpds) {

		this.qpds = qpds;
	}

	public Map<String, QueryParamDefineBean> getQpds() {

		return qpds;
	}

	public void putQpd(String name, QueryParamDefineBean bean) {

		if (getQpds().get(name) != null) {
			getQpds().remove(name);
		}
		getQpds().put(name, bean);
	}

	public void putQfb(String name, QueryFilterBean bean) {

		if (getQfbs().get(name) != null) {
			getQfbs().remove(name);
		}
		getQfbs().put(name, bean);
	}

	public Map<String, QueryFilterBean> getQfbs() {
		return qfbs;
	}

	public void setQfbs(Map<String, QueryFilterBean> qfbs) {
		this.qfbs = qfbs;
	}

}
