package com.cat.activiti.process;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.boot.jsonbean.TaskBean;
import com.cat.boot.service.BaseGetUserService;
import com.cat.boot.util.StringUtil;
import com.cat.system.controller.UserInfoService;

// 可以每个节点定义一个service 也可以整个流程定义一个service并通过shenhebean传入的node来进行判断 也可以两者结合使用
@Service("Test_Sq_Service")
public class TestSqService extends BaseGetUserService {

	@Autowired
	public UserInfoService userInfoService;

	@Override
	public Object setVariables(Map<String, Object> variables, TaskBean bean) {

		// 可以将该流程环节 一些特殊的处理塞入流程变量 如当前办理人的机构ID 机构名称等
		// 注意塞入流程变量的时 要区别哪些流程变量是可以被覆盖的 哪些是不能被覆盖会贯穿整个流程的
		// 不能被覆盖的流程变量建议使用不会被覆盖的变量名称 如task_节点名称_变量名称
		// 流程变量同样可以通过shenhebean.getMaps()来传递
		// variables.put("task_节点名称_变量名称", "");

		// String userIds =
		// "4028fc8a696be2d501696be31d1e000e,4028fc8a696be2d501696be31d1e000ex";
		// 通常流转过的节点 通过流程变量来获取
		// if (!StringUtil.isEmpty(entity.getProcessId())) {
		// processContorller.get流程变量(entity.getProcessId(), "属性");
		// }

		// 没有流转过的节点 或者 需要重新获取办理人 或者未将该节点的办理人塞入流程变量的 通过自定义的方法来获取
		// 通过shenhebean.getMaps()来获取参数 参数格式参考testact/add.jsp

		// 如果有下一步办理人 塞入流程变量
		Map<String, String> usermaps = bean.getUsermaps();
		List<String> userIds = new ArrayList<String>();
		String node = usermaps.get("node");
		if(!StringUtil.isEmpty(node)) {
			switch (node) {
			case "初审":
			case "复审":
				userIds = userInfoService.getUserIds(usermaps.get("role"), "", false);
				break;
			case "重新提交":
			case "会签":
				userIds.add((String) usermaps.get("userId"));
				break;
			default:
				break;
			}
		}
		// 如果下一步办理人未找到，返回
		return userIds;
	}

}
