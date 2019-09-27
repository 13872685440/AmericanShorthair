package com.cat.boot.exception;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

public class CatException extends Exception {

	private static final long serialVersionUID = 124755116438092973L;

	public String message;

	public CatException(String message) {
		super(message);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static void responseData(HttpServletResponse response, int code, String data) {
		try {
			response.setStatus(401);
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out;
			out = response.getWriter();
			JSONObject obj = new JSONObject();
			obj.put("code", code);
			obj.put("message", data);
			out.println(obj.toJSONString());
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

}
