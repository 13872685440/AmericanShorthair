package com.cat.system.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cat.boot.annotation.RequiredRoles;
import com.cat.boot.jsonbean.BaseQueryHelp;
import com.cat.boot.jsonbean.QueryResultBean;
import com.cat.boot.jsonbean.ResultBean;
import com.cat.boot.service.BaseNqtQuery;
import com.cat.system.jsonbean.AppTreeBean;
import com.cat.system.model.AppTree;

@RestController
@Scope("prototype")
@RequestMapping("/apptree")
public class AppTreeQuery extends BaseNqtQuery<AppTree> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8847621551932537219L;

	@Override
	@RequiredRoles({ "SYS_ADMINISTRATOR" })
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String query(BaseQueryHelp parms) throws Exception {
		excuteQuery(parms);
		return ResultBean.getSucess(new QueryResultBean(parms, AppTreeBean.setThis(results)));
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(@RequestParam String id) {
		return super.delete(id);
	}

}
