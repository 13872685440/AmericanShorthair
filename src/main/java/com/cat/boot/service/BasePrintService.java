package com.cat.boot.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.cat.boot.service.BaseService;

public abstract class BasePrintService {

	@Autowired
	public BaseService baseService;
	
	@SuppressWarnings("rawtypes")
	public abstract Map getDataMap(String keyValue);
}
