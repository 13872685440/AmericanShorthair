package com.cat.system.controller;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cat.boot.jsonbean.ResultBean;
import com.cat.boot.service.BaseHome;
import com.cat.boot.util.StringUtil;
import com.cat.system.jsonbean.AppTreeBean;
import com.cat.system.model.AppTree;

@RestController
@RequestMapping("/apptree")
public class AppTreeHome extends BaseHome<AppTree> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6987335446356310743L;

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(@RequestParam String id) {
		if (!StringUtil.isEmpty(id)) {
			AppTree entity = findById(id);
			return ResultBean.getSucess(AppTreeBean.setThis(entity));
		} else {
			AppTree entity = new AppTree();
			entity.setClc(baseService.getCode("AppTree", null, 4).trim());
			//entity.setCode(entity.getClc());
			return ResultBean.getSucess(AppTreeBean.setThis(entity));
		}
	}

	@RequestMapping(value = "/addlower", method = RequestMethod.GET)
	public String addlower(@RequestParam String id) {
		AppTree entity = new AppTree();
		entity.setSuperior((AppTree) baseService.findById(AppTree.class, id));
		entity.setClc(baseService.getCode("AppTree", id, 4).trim());
		entity.setCode(id + entity.getClc());
		return ResultBean.getSucess(AppTreeBean.setThis(entity));
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(AppTreeBean entity) throws Exception {
		AppTree bean = findById(entity.getCode());
		if (bean == null) {
			bean = new AppTree();
			bean.setCode(entity.getCode());
		}
		AppTreeBean.clone(bean, entity, baseService);
		baseService.save(bean);
		return ResultBean.getSucess("sucess");
	}

}
