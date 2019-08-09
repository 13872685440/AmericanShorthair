package com.cat.boot.jsonbean;

public class PropParamBean {

	private String keyword;

	private String linkword;

	private Object param;

	private Object value;

	public PropParamBean() {
	}

	public PropParamBean(String keyword, String linkword, Object param, Object value) {
		this.linkword = linkword;
		this.keyword = keyword;
		this.param = param;
		this.value = value;
	}

	public PropParamBean(Object param, Object value) {
		this.keyword = "=";
		this.linkword = "and";
		this.param = param;
		this.value = value;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Object getParam() {
		return param;
	}

	public void setParam(Object param) {
		this.param = param;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getLinkword() {
		return linkword;
	}

	public void setLinkword(String linkword) {
		this.linkword = linkword;
	}

}
