package com.cat.boot.jsonbean;

import java.util.HashMap;
import java.util.Map;

public class ParamBean {

	private String url;

	private String srcId;

	private String prefixpath;

	private String path;

	private String lx;

	private String type;
	
	private String iniService;

	// 存储永久参数
	private Map<String, String> parms = new HashMap<String, String>();

	// 存储临时参数
	private Map<String, String> parmtmps = new HashMap<String, String>();

	// 存储非查询参数
	private Map<String, String> nqparms = new HashMap<String, String>();

	private String namespace;

	private String xmlpath;

	private String methodname;

	private String title;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSrcId() {
		return srcId;
	}

	public void setSrcId(String srcId) {
		this.srcId = srcId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Map<String, String> getParms() {
		return parms;
	}

	public void setParms(Map<String, String> parms) {
		this.parms = parms;
	}

	public Map<String, String> getParmtmps() {
		return parmtmps;
	}

	public void setParmtmps(Map<String, String> parmtmps) {
		this.parmtmps = parmtmps;
	}

	public Map<String, String> getNqparms() {
		return nqparms;
	}

	public void setNqparms(Map<String, String> nqparms) {
		this.nqparms = nqparms;
	}

	public String getLx() {
		return lx;
	}

	public void setLx(String lx) {
		this.lx = lx;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getXmlpath() {
		return xmlpath;
	}

	public void setXmlpath(String xmlpath) {
		this.xmlpath = xmlpath;
	}

	public String getMethodname() {
		return methodname;
	}

	public void setMethodname(String methodname) {
		this.methodname = methodname;
	}

	public String getPrefixpath() {
		return prefixpath;
	}

	public void setPrefixpath(String prefixpath) {
		this.prefixpath = prefixpath;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIniService() {
		return iniService;
	}

	public void setIniService(String iniService) {
		this.iniService = iniService;
	}

}
