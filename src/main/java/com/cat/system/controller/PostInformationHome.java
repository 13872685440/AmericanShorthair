package com.cat.system.controller;

import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cat.boot.jsonbean.ResultBean;
import com.cat.boot.service.BaseHome;
import com.cat.boot.util.PassWordUtil;
import com.cat.boot.util.RadomUtil;
import com.cat.boot.util.StringUtil;
import com.cat.dictionary.model.Dictionary;
import com.cat.system.jsonbean.PostInformationBean;
import com.cat.system.model.Organization;
import com.cat.system.model.PostInformation;
import com.cat.system.model.User;

@RestController
@RequestMapping("/postinformation")
public class PostInformationHome extends BaseHome<PostInformation> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 314586715829350604L;

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(@RequestParam String id) {
		if (!StringUtil.isEmpty(id)) {
			PostInformation entity = findById(id);
			return ResultBean.getSucess(PostInformationBean.setThis(entity));
		} else {
			PostInformation entity = new PostInformation();
			return ResultBean.getSucess(PostInformationBean.setThis(entity));
		}
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(@RequestParam String id) {
		PostInformation entity = new PostInformation();
		Organization organization;
		if (StringUtil.isEmpty(id)) {
			organization = (Organization) baseService.findById(Organization.class, "0001");
		} else {
			organization = (Organization) baseService.findById(Organization.class, id);
		}
		entity.setOrganization(organization);
		entity.setIsleaf((Dictionary) baseService.findById(Dictionary.class, "00020001"));
		return ResultBean.getSucess(PostInformationBean.setThis(entity));
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(PostInformationBean entity) {
		PostInformation bean = new PostInformation();
		if (!StringUtil.isEmpty(entity.getId())) {
			bean = findById(entity.getId());
		}
		PostInformationBean.clone(bean, entity, baseService);
		baseService.save(bean);
		return ResultBean.getSucess("sucess");
	}

	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	public String resetRadom(@RequestParam String id) {
		PostInformation entity = findById(id);
		User user = (User) entity.getPerson();
		user.setRadom(RadomUtil.radom(6));
		Map<String, String> pwds = PassWordUtil.entryptPassword(user.getRadom(), true);
		user.setPwd(pwds.get("pwd"));
		user.setSalt(pwds.get("salt"));
		baseService.noAnnotationSave(user, true);
		return ResultBean.getSucess(user.getRadom());
	}

	@RequestMapping(value = "/makepwd", method = RequestMethod.POST)
	public String makePassword(@RequestParam("userId") String userId, @RequestParam("newpwd") String newpwd) {
		User user = (User)baseService.findById(User.class, userId);
		Map<String, String> pwds = PassWordUtil.entryptPassword(newpwd, false);
		user.setPwd(pwds.get("pwd"));
		user.setSalt(pwds.get("salt"));
		baseService.noAnnotationSave(user, true);
		return ResultBean.getSucess("");
	}

}
