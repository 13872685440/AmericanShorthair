package com.cat.boot.filter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.WebInitParam;

import com.alibaba.druid.support.http.StatViewServlet;

@WebServlet(urlPatterns = "/druid/*", initParams = { // IP白名单(没有配置或者为空，则允许所有访问)
		@WebInitParam(name = "deny", value = "192.168.1.73"), // IP黑名单 (存在共同时，deny优先于allow)
		@WebInitParam(name = "loginUsername", value = "admin"), // 用户名
		@WebInitParam(name = "loginPassword", value = "1"), // 密码
		@WebInitParam(name = "resetEnable", value = "false")// 禁用HTML页面上的“Reset All”功能
})
public class DruidStatViewServlet extends StatViewServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3100781968534013297L;

}
