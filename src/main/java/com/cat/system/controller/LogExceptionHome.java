package com.cat.system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cat.boot.jsonbean.ResultBean;
import com.cat.boot.service.BaseHome;
import com.cat.system.model.LogException;

@RestController
@RequestMapping("/logexception")
public class LogExceptionHome extends BaseHome<LogException> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1363807935966984648L;

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(@RequestParam String id) {
		LogException entity = findById(id);
		return ResultBean.getSucess(entity);
	}

}
