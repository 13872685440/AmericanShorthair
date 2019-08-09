package com.cat.file.jsonbean;

import java.util.ArrayList;
import java.util.List;

import com.cat.boot.util.StringUtil;
import com.cat.file.model.FileInfo;
import com.cat.file.model.YeWuBLZLWD;

public class FileInfoBean {

	private String uid;

	private String name;

	private String status = "done";

	private ResultFileBean response;

	private String url;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ResultFileBean getResponse() {
		return response;
	}

	public void setResponse(ResultFileBean response) {
		this.response = response;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public static FileInfoBean setThis(YeWuBLZLWD entity) {
		FileInfo info = entity.getFileinfo();
		FileInfoBean bean = new FileInfoBean();
		bean.setUid(info.getId());
		bean.setName(info.getOriginalName());
		bean.setUrl(entity.getId());
		bean.setResponse(new ResultFileBean(200, entity.getId()));

		return bean;
	}

	public static List<FileInfoBean> setThis(List<YeWuBLZLWD> entitys) {
		List<FileInfoBean> beans = new ArrayList<FileInfoBean>();
		if (!StringUtil.isListEmpty(entitys)) {
			for (YeWuBLZLWD bean : entitys) {
				beans.add(setThis(bean));
			}
		}
		return beans;
	}
}
