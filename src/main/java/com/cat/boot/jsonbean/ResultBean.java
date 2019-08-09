package com.cat.boot.jsonbean;

import com.alibaba.fastjson.JSONObject;

public class ResultBean {

	private String code;

	private String message;

	private Object result;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public ResultBean() {

	}

	public ResultBean(String code, String message, Object result) {
		this.code = code;
		this.message = message;
		this.result = result;
	}

	public static String getResultBean(String code, String message, Object result) {
		ResultBean bean = new ResultBean(code, message, result);
		String json = JSONObject.toJSONString(bean);
		return json;
	}

	public static String getSucess(Object result) {
		ResultBean bean = new ResultBean("200", "", result);
		String json = JSONObject.toJSONString(bean);
		System.out.println(json);
		return json;
	}
}
