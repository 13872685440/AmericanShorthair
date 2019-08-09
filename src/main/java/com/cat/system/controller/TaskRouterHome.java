package com.cat.system.controller;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cat.system.jsonbean.TaskRouterBean;
import com.cat.system.model.TaskRouter;
import com.cat.boot.jsonbean.ResultBean;
import com.cat.boot.service.BaseHome;
import com.cat.boot.util.StringUtil;

@RestController
@RequestMapping("/taskrouter")
public class TaskRouterHome extends BaseHome<TaskRouter> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5079095773304621404L;

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(@RequestParam String id) {
		if (!StringUtil.isEmpty(id)) {
			TaskRouter entity = findById(id);
			return ResultBean.getSucess(TaskRouterBean.setThis(entity));
		} else {
			TaskRouter entity = new TaskRouter();
			return ResultBean.getSucess(TaskRouterBean.setThis(entity));
		}
	}

	@RequestMapping(value = "/addlower", method = RequestMethod.GET)
	public String addlower(@RequestParam String id) {
		TaskRouter entity = new TaskRouter();
		entity.setSuperior((TaskRouter) baseService.findById(TaskRouter.class, id));
		entity.setClc(baseService.getCode("TaskRouter", id, 4).trim());
		entity.setCode(id + entity.getClc());
		return ResultBean.getSucess(TaskRouterBean.setThis(entity));
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(TaskRouterBean entity) throws Exception {
		TaskRouter bean = findById(entity.getCode());
		if (bean == null) {
			bean = new TaskRouter();
			bean.setCode(entity.getCode());
		}
		TaskRouterBean.clone(bean, entity, baseService);
		baseService.save(bean);
		return ResultBean.getSucess("sucess");
	}

}
