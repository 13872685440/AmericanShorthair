package com.cat.boot.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.cat.boot.jsonbean.TaskBean;
import com.cat.boot.service.BaseService;

public abstract class BaseGetUserService {

	@Autowired
	protected BaseService baseService;

	public abstract Object setVariables(Map<String, Object> variables, TaskBean bean);
}
