package com.cat.boot.jsonbean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class QueryFilterBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8841018623188441487L;

	/** 查询 语句 */
	private String ql;
	
	private String instag;
	
	private String elsource;
	
	private String el;

	/** 查询参数 */
	private Map<String, QueryParamDefineBean> qpds = new HashMap<String, QueryParamDefineBean>();

	public void reset() {
		instag = null;
		elsource = null;
		el = null;
		ql = null;
		qpds.clear();
	}

	public String getQl() {

		return ql;
	}

	public void setQl(String ql) {

		this.ql = ql;
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

	public String getInstag() {
		return instag;
	}

	public void setInstag(String instag) {
		this.instag = instag;
	}

	public String getElsource() {
		return elsource;
	}

	public void setElsource(String elsource) {
		this.elsource = elsource;
	}

	public String getEl() {
		return el;
	}

	public void setEl(String el) {
		this.el = el;
	}

}
