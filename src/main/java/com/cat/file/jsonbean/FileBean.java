package com.cat.file.jsonbean;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class FileBean {

	private String url;

	private String size;

	private String type;

	private String title;

	private String state;

	private byte[] bytes;

	public FileBean() {
	}

	public FileBean(MultipartFile file) throws IOException {
		this.setBytes(file.getBytes());
		this.setTitle(file.getOriginalFilename());
		this.setSize(String.valueOf(file.getSize()));
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

}
