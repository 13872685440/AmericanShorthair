package com.cat.system.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cat.boot.annotation.RequiredRoles;
import com.cat.boot.jsonbean.ResultBean;
import com.cat.boot.service.BaseService;
import com.cat.boot.util.SigarUtil;

@RestController
@RequestMapping("/sigar")
public class SigarSevice {

	@Autowired
	public BaseService baseService;

	@RequiredRoles({ "SYS_ADMINISTRATOR" })
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String getInfo() throws Exception {
		Map<String, Object> maps = SigarUtil.getInfo();
		return ResultBean.getSucess(maps);
	}
}
