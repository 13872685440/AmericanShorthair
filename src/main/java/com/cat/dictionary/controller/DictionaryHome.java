package com.cat.dictionary.controller;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cat.boot.jsonbean.PropParamBean;
import com.cat.boot.jsonbean.ResultBean;
import com.cat.boot.service.BaseHome;
import com.cat.boot.util.NameQueryUtil;
import com.cat.boot.util.StringUtil;
import com.cat.dictionary.jsonbean.DictionaryBean;
import com.cat.dictionary.model.Dictionary;

@RestController
@RequestMapping("/dictionary")
public class DictionaryHome extends BaseHome<Dictionary> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7833244390007651087L;

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(@RequestParam String id) {
		if (!StringUtil.isEmpty(id)) {
			Dictionary entity = findById(id);
			return ResultBean.getSucess(DictionaryBean.setThis(entity));
		} else {
			Dictionary entity = new Dictionary();
			entity.setClc(baseService.getCode("Dictionary", null, 4).trim());
			entity.setCode(entity.getClc());
			return ResultBean.getSucess(DictionaryBean.setThis(entity));
		}
	}

	@RequestMapping(value = "/addlower", method = RequestMethod.GET)
	public String addlower(@RequestParam String id) {
		Dictionary entity = new Dictionary();
		Dictionary superior = (Dictionary) baseService.findById(Dictionary.class, id);
		entity.setSuperior(superior);
		entity.setClc(baseService.getCode("Dictionary", id, 4).trim());
		entity.setCode(id + entity.getClc());
		entity.setTypeCode(superior.getTypeCode());

		return ResultBean.getSucess(DictionaryBean.setThis(entity));
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(DictionaryBean entity) throws Exception {
		Dictionary bean = findById(entity.getCode());
		if (bean == null) {
			bean = new Dictionary();
			bean.setCode(entity.getCode());
		}
		DictionaryBean.clone(bean, entity, baseService);
		baseService.save(bean);
		baseService.update4Prop("Data_Dictionary", true, NameQueryUtil.setParams("type_code", entity.getTypeCode()),
				NameQueryUtil.getBeans(new PropParamBean("like", "and", "code", entity.getCode() + "%")));
		return ResultBean.getSucess("sucess");
	}

}
