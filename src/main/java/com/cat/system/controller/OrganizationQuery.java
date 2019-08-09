package com.cat.system.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cat.boot.jsonbean.BaseQueryHelp;
import com.cat.boot.jsonbean.PropParamBean;
import com.cat.boot.jsonbean.QueryResultBean;
import com.cat.boot.jsonbean.ResultBean;
import com.cat.boot.jsonbean.TreeBean;
import com.cat.boot.jsonbean.TreeParmBean;
import com.cat.boot.service.BaseNqtQuery;
import com.cat.boot.util.NameQueryUtil;
import com.cat.boot.util.StringUtil;
import com.cat.system.jsonbean.OrganizationBean;
import com.cat.system.model.Organization;

@RestController
@Scope("prototype")
@RequestMapping("/organization")
public class OrganizationQuery extends BaseNqtQuery<Organization> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5992956687999194584L;

	@Override
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String query(BaseQueryHelp parms) throws Exception {
		excuteQuery(parms);
		return ResultBean.getSucess(new QueryResultBean(parms, OrganizationBean.setThis(results)));
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(@RequestParam String id) {
		return super.delete(id);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/tree", method = RequestMethod.GET)
	public String getTree(TreeParmBean bean) throws Exception {
		List<PropParamBean> beans = new ArrayList<PropParamBean>();
		if (!StringUtil.isEmpty(bean.getRoot())) {
			beans.add(new PropParamBean("like", "and", "code", bean.getRoot() + "%"));
		}
		List<Object[]> orgs = (List<Object[]>) baseService.getList("Org_Organization", "o.code asc", true,
				"o.code,o.name,o.sc_id", beans);
		if (bean.getOther() != null && bean.getOther() == 1) {
			List<Object[]> persons = (List<Object[]>) baseService.getList("PostInformation", "system",
					"User_by_PostInformation",
					NameQueryUtil.setParams("leafs", Arrays.asList(new String[] { "00010001", "00010002" })));
			if (!StringUtil.isListEmpty(persons)) {
				orgs.addAll(persons);
			}
		}
		if (!StringUtil.isListEmpty(orgs)) {
			List<TreeBean> tree = TreeBean.getTree(orgs, null);
			return ResultBean.getSucess(tree);
		}
		return ResultBean.getSucess(new ArrayList<TreeBean>());
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/filter_tree", method = RequestMethod.GET)
	public String getFilter_Tree(TreeParmBean bean) throws Exception {
		if (StringUtil.isListEmpty(bean.getIds())) {
			return ResultBean.getResultBean("200", "", "");
		}
		List<PropParamBean> beans = new ArrayList<PropParamBean>();
		if (!StringUtil.isEmpty(bean.getRoot())) {
			beans.add(new PropParamBean("like", "and", "code", bean.getRoot() + "%"));
		}
		List<Object[]> orgs = (List<Object[]>) baseService.getList("Org_Organization", "o.code asc", true,
				"o.code,o.name,o.sc_id", beans);
		if (bean.getOther() != null && bean.getOther() == 1) {
			List<Object[]> persons = (List<Object[]>) baseService.getList("PostInformation", "system",
					"User_by_PostInformation",
					NameQueryUtil.setParams("leafs", Arrays.asList(new String[] { "00010001", "00010002" })));
			if (!StringUtil.isListEmpty(persons)) {
				orgs.addAll(persons);
			}
		}

		Map<String, List<String>> lowermap = new HashMap<String, List<String>>();
		Map<String, String> supermap = new HashMap<String, String>();
		for (Object[] obj : orgs) {
			String key = (String) obj[0];
			String superior = (String) obj[2];
			supermap.put(key, superior);
			if (StringUtil.isEmpty(superior)) {
				lowermap.put(key, new ArrayList<String>());
			} else {
				if (!StringUtil.isMapContainsKey(lowermap, superior)) {
					List<String> list = new ArrayList<String>();
					list.add(key);
					lowermap.put(superior, list);
				} else {
					List<String> list = lowermap.get(superior);
					list.add(key);
					lowermap.replace(superior, list);
				}
			}
		}
		List<String> keys = new ArrayList<String>();
		for (String id : bean.getIds()) {
			keys.add(id);
			String idtmp = id;
			// 将super 加入
			while (StringUtil.isMapContainsKey(supermap, idtmp) && !StringUtil.isEmpty(supermap.get(idtmp))) {
				idtmp = supermap.get(idtmp);
				keys.add(idtmp);
			}
			// 将lower 加入
			addLower(keys, lowermap, id);
		}
		List<Object[]> objs = new ArrayList<Object[]>();
		for (Object[] obj : orgs) {
			String key = (String) obj[0];
			if (keys.contains(key)) {
				objs.add(obj);
			}
		}
		List<TreeBean> tree = TreeBean.getTree(objs, 1);
		return ResultBean.getSucess(tree);
	}

	void addLower(List<String> keys, Map<String, List<String>> lowermap, String key) {
		if (StringUtil.isMapContainsKey(lowermap, key)) {
			List<String> tmp = lowermap.get(key);
			if (!StringUtil.isListEmpty(tmp)) {
				keys.addAll(tmp);
				for (String string : tmp) {
					addLower(keys, lowermap, string);
				}
			}
		}
	}
}
