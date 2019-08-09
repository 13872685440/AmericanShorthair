package com.cat.system.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cat.boot.service.BaseService;
import com.cat.boot.util.NameQueryUtil;
import com.cat.boot.util.PassWordUtil;
import com.cat.boot.util.StringUtil;
import com.cat.system.model.AppTree;
import com.cat.system.model.Role;
import com.cat.system.model.RoleApp;
import com.cat.system.model.User;

@RestController
@RequestMapping("/init")
public class InitController {

	@Autowired(required = true)
	public BaseService baseService;

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String init() {
		List obj = (List) baseService.getList("User", null, false);
		if (StringUtil.isListEmpty(obj)) {
			return "-1";
		} else {
			return "0";
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/initdata", method = RequestMethod.POST)
	public String initdata() {
		Map<Object, Object> map = NameQueryUtil.setParams("loginName", "admin");
		boolean flag = baseService.isUnion("User", null, false, map);
		if (flag) {
			initmethod();
		}
		return "ok";
	}

	// 根据需要复写初始化方法

	private void initmethod() {
		List<AppTree> list = new ArrayList<AppTree>();
		
		AppTree apptree = new AppTree();
		apptree.setCode("0001");
		apptree.setClc("0001");
		apptree.setWn("仪表盘");
		apptree.setName("仪表盘");
		apptree.setLeaf(false);
		apptree.setDes("仪表盘");
		apptree.setXh(1);
		apptree.setLxh(1L);
		apptree.setComponent("RouteView");
		baseService.save(apptree, false);
		
		AppTree apptree2 = new AppTree();
		apptree2.setCode("00010001");
		apptree2.setClc("0001");
		apptree2.setWn("仪表盘_工作台");
		apptree2.setName("工作台");
		apptree2.setLeaf(true);
		apptree2.setDes("工作台");
		apptree2.setXh(1);
		apptree2.setPath("/index");
		apptree2.setComponent("/dashboard/Workplace");
		apptree2.setSuperior(apptree);
		apptree2.setLxh(11L);
		baseService.save(apptree2, false);
		list.add(apptree2);
		
		AppTree apptree11 = new AppTree();
		apptree11.setCode("0002");
		apptree11.setClc("0002");
		apptree11.setWn("系统管理");
		apptree11.setName("系统管理");
		apptree11.setLeaf(false);
		apptree11.setDes("系统管理");
		apptree11.setXh(2);
		apptree11.setLxh(2L);
		apptree11.setComponent("PageView");
		baseService.save(apptree11, false);
		
		AppTree apptree12 = new AppTree();
		apptree12.setCode("00020001");
		apptree12.setClc("0001");
		apptree12.setWn("系统管理_菜单管理");
		apptree12.setName("菜单管理");
		apptree12.setLeaf(true);
		apptree12.setDes("菜单管理");
		apptree12.setXh(1);
		apptree12.setPath("/apptree/main");
		apptree12.setComponent("/system/apptree/Main");
		apptree12.setSuperior(apptree11);
		apptree12.setLxh(apptree11.getLxh()*10+apptree12.getXh());
		baseService.save(apptree12, false);
		list.add(apptree12);

		AppTree apptree13 = new AppTree();
		apptree13.setCode("00020002");
		apptree13.setClc("0002");
		apptree13.setWn("系统管理_角色管理");
		apptree13.setName("角色管理");
		apptree13.setLeaf(true);
		apptree13.setDes("角色管理");
		apptree13.setXh(2);
		apptree13.setPath("/role/main");
		apptree13.setComponent("/system/role/Main");
		apptree13.setSuperior(apptree11);
		apptree13.setLxh(apptree11.getLxh()*10+apptree13.getXh());
		baseService.save(apptree13, false);
		list.add(apptree13);
		
		AppTree apptree14 = new AppTree();
		apptree14.setCode("00020003");
		apptree14.setClc("0003");
		apptree14.setWn("系统管理_组织机构");
		apptree14.setName("组织机构");
		apptree14.setLeaf(true);
		apptree14.setDes("组织机构");
		apptree14.setXh(3);
		apptree14.setPath("/organization/main");
		apptree14.setComponent("/system/organization/Main");
		apptree14.setSuperior(apptree11);
		apptree14.setLxh(apptree11.getLxh()*10+apptree14.getXh());
		baseService.save(apptree14, false);
		list.add(apptree14);
		
		AppTree apptree15 = new AppTree();
		apptree15.setCode("00020004");
		apptree15.setClc("0004");
		apptree15.setWn("系统管理_人员管理");
		apptree15.setName("人员管理");
		apptree15.setLeaf(true);
		apptree15.setDes("人员管理");
		apptree15.setXh(4);
		apptree15.setPath("/postinformation/main");
		apptree15.setComponent("/system/postinformation/Main");
		apptree15.setSuperior(apptree11);
		apptree15.setLxh(apptree11.getLxh()*10+apptree15.getXh());
		baseService.save(apptree15, false);
		list.add(apptree15);
		
		AppTree apptree16 = new AppTree();
		apptree16.setCode("00020005");
		apptree16.setClc("0005");
		apptree16.setWn("系统管理_流程部署");
		apptree16.setName("流程部署");
		apptree16.setLeaf(true);
		apptree16.setDes("流程部署");
		apptree16.setXh(5);
		apptree16.setPath("/processdefinitionbean/main");
		apptree16.setComponent("/activiti/processdefinitionbean/Main");
		apptree16.setSuperior(apptree11);
		apptree16.setLxh(apptree11.getLxh()*10+apptree16.getXh());
		baseService.save(apptree16, false);
		list.add(apptree16);
		
		AppTree apptree17 = new AppTree();
		apptree17.setCode("00020006");
		apptree17.setClc("0006");
		apptree17.setWn("系统管理_任务路由");
		apptree17.setName("任务路由");
		apptree17.setLeaf(true);
		apptree17.setDes("任务路由");
		apptree17.setXh(6);
		apptree17.setPath("/taskrouter/main");
		apptree17.setComponent("/activiti/taskrouter/Main");
		apptree17.setSuperior(apptree11);
		apptree17.setLxh(apptree11.getLxh()*10+apptree17.getXh());
		baseService.save(apptree17, false);
		list.add(apptree17);
		
		AppTree apptree21 = new AppTree();
		apptree21.setCode("0003");
		apptree21.setClc("0003");
		apptree21.setWn("数据字典");
		apptree21.setName("数据字典");
		apptree21.setLeaf(false);
		apptree21.setDes("数据字典");
		apptree21.setXh(3);
		apptree21.setLxh(3L);
		apptree21.setComponent("PageView");
		baseService.save(apptree21, false);
		
		AppTree apptree22 = new AppTree();
		apptree22.setCode("00030001");
		apptree22.setClc("0001");
		apptree22.setWn("数据字典_数据字典");
		apptree22.setName("数据字典");
		apptree22.setLeaf(true);
		apptree22.setDes("数据字典");
		apptree22.setXh(1);
		apptree22.setPath("/dictionary/main");
		apptree22.setComponent("/dictionary/dictionary/Main");
		apptree22.setSuperior(apptree21);
		apptree22.setLxh(apptree21.getLxh()*10+apptree22.getXh());
		baseService.save(apptree22, false);
		list.add(apptree22);

		// 创建一个SYS_ADMINISTRATOR的角色
		Role role = new Role();
		role.setName("SYS_ADMINISTRATOR");
		role.setDes("超级管理员");
		baseService.save(role, false);

		// 将角色与菜单关联
		for (AppTree app : list) {
			RoleApp roleapp = new RoleApp();
			roleapp.setApp(app);
			roleapp.setRole(role);
			baseService.save(roleapp, false);
		}

		// 创建一个admin账号
		User user = new User();
		user.setLoginName("admin");
		user.setName("系统管理员");
		Map<String, String> pwds = PassWordUtil.entryptPassword("1", true);
		user.setPwd(pwds.get("pwd"));
		user.setSalt(pwds.get("salt"));
		baseService.save(user, false);
	}
}
