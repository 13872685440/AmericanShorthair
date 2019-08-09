package com.cat.boot.exception;

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

}
