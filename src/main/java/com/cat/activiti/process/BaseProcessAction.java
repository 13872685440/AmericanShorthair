package com.cat.activiti.process;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.cat.boot.jsonbean.TaskBean;
import com.cat.boot.service.BaseGetUserService;
import com.cat.boot.service.BaseService;
import com.cat.boot.util.CalendarUtil;
import com.cat.boot.util.SpringContextUtil;
import com.cat.boot.util.StringUtil;

public class BaseProcessAction {

	@Autowired
	protected ProcessContorller processContorller;

	@Autowired
	protected BaseService baseService;

	@SuppressWarnings("unchecked")
	protected List<String> get下一步办理人(Map<String, Object> variables, TaskBean bean) {
		BaseGetUserService service = (BaseGetUserService) SpringContextUtil.getBean(bean.getService());
		List<String> userIds = (List<String>) service.setVariables(variables, bean);
		if (!StringUtil.isListEmpty(userIds)) {
			variables.put("task_userIds", userIds);
		}
		return userIds;
	}

	protected void set流程变量(Map<String, Object> map, Object... obj) {
		for (int i = 0; i < obj.length; i++) {
			if (i % 2 == 0) {
				map.put((String) obj[i], obj[i + 1]);
			}
		}
	}

	protected void set初始化流程变量(Map<String, Object> map, TaskBean entity) {
		map.put("userId", entity.getUserId());
		map.put("userName", entity.getUserName());
		map.put("shjgName", entity.getShjgName());
		map.put("keyValue", entity.getId());
		map.put("taskName", entity.getTaskName());
	}

	protected void set意见流程变量(Map<String, Object> map, TaskBean shenhebean) {
		map.put("shyj", shenhebean.getShyj());
		map.put("shjl", shenhebean.getShjl());
		map.put("shsj",
				StringUtil.isEmpty(shenhebean.getShsj()) ? CalendarUtil.getYyyyMmDdHHmmss(Calendar.getInstance())
						: shenhebean.getShsj());
	}

	protected void set流程变量(Map<String, Object> map, String variables) {
		// 解析variables
		if (!StringUtil.isEmpty(variables)) {
			List<String> list = new ArrayList<>();
			if (variables.indexOf(",") != -1) {
				list = Arrays.asList(variables.split(","));
			} else {
				list.add(variables);
			}
			for (String string : list) {
				String[] strings = string.split(":");
				map.put(strings[0], strings[1]);
			}
		}
	}
}
