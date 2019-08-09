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
import com.cat.system.jsonbean.OrganizationBean;
import com.cat.system.model.Organization;

@RestController
@RequestMapping("/organization")
public class OrganizationHome extends BaseHome<Organization> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1901860146313022299L;

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(@RequestParam String id) {
		if (!StringUtil.isEmpty(id)) {
			Organization entity = findById(id);
			return ResultBean.getSucess(OrganizationBean.setThis(entity));
		} else {
			Organization entity = new Organization();
			entity.setClc(baseService.getCode("Organization", null, 4).trim());
			entity.setCode(entity.getClc());
			return ResultBean.getSucess(OrganizationBean.setThis(entity));
		}
	}

	@RequestMapping(value = "/addlower", method = RequestMethod.GET)
	public String addlower(@RequestParam String id) {
		Organization entity = new Organization();
		entity.setSuperior((Organization) baseService.findById(Organization.class, id));
		entity.setClc(baseService.getCode("Organization", id, 4).trim());
		entity.setCode(id + entity.getClc());
		return ResultBean.getSucess(OrganizationBean.setThis(entity));
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(OrganizationBean entity) throws Exception {
		Organization bean = findById(entity.getCode());
		if (bean == null) {
			bean = new Organization();
			bean.setCode(entity.getCode());
		}
		OrganizationBean.clone(bean, entity, baseService);
		baseService.save(bean);
		return ResultBean.getSucess("sucess");
	}
	
	
}
