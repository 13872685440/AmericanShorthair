package com.cat.boot.jsonbean;

import java.util.List;

public interface IResultConvert<T> {
	public List<T> toResult(List<Object> ls);
}