package com.cat.boot.jsonbean;

public class QueryParamDefineBean {

	/** 参数名称 */
	private String name;

	/** 参数类型 */
	private String type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	public QueryParamDefineBean(String name, String type) {
		super();
		this.name = name;
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public QueryParamDefineBean() {
		super();
	}

}
