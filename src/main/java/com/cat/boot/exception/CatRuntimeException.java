package com.cat.boot.exception;

public class CatRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 2390019270348597364L;

	public String message;

	public CatRuntimeException(String message) {
		super(message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
