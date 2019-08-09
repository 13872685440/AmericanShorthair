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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cat.boot.jsonbean.BaseQueryHelp;
import com.cat.boot.jsonbean.PropParamBean;
import com.cat.boot.jsonbean.QueryResultBean;
import com.cat.boot.jsonbean.ResultBean;
import com.cat.boot.jsonbean.TreeBean;
import com.cat.boot.jsonbean.TreeParmBean;
import com.cat.boot.jsonbean.UserBean;
import com.cat.boot.service.BaseNqtQuery;
import com.cat.boot.util.NameQueryUtil;
import com.cat.boot.util.StringUtil;
import com.cat.system.jsonbean.PostInformationBean;
import com.cat.system.model.PostInformation;
import com.cat.system.model.User;

@RestController
@Scope("prototype")
@RequestMapping("/postinformation")
public class PostInformationQuery extends BaseNqtQuery<PostInformation> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5295892878289502288L;

	@Override
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String query(BaseQueryHelp parms) throws Exception {
		excuteQuery(parms);
		return ResultBean.getSucess(new QueryResultBean(parms, PostInformationBean.setThis(results)));
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(@RequestParam String id) {
		return super.delete(id);
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public String loadinformation_vue(@RequestParam String phone, @RequestParam String id) throws Exception {
		// 查询User
		User user = (User) baseService.getSimple("User", "", false,
				Arrays.asList(new PropParamBean("=", "and", "loginName", phone),
						new PropParamBean("!=", "and", "id", StringUtil.isEmpty(id) ? "xxxx" : id)));
		if (user != null) {
			List<PostInformation> list = (List<PostInformation>) baseService.getList("PostInformation", "o.ct desc",
					true, NameQueryUtil.setParams("person.id", user.getId()));
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("posts", PostInformationBean.setThis(list));
			UserBean bean = new UserBean();
			bean.setName(user.getName());
			bean.setId(user.getId());
			map.put("userbean", bean);
			return ResultBean.getSucess(map);
		} else {
			return ResultBean.getSucess(null);
		}
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

		if (!StringUtil.isListEmpty(orgs)) {
			List<TreeBean> tree = TreeBean.getTree(orgs, null);
			return ResultBean.getSucess(tree);
		}
		return ResultBean.getResultBean("200", "", "");
	}

}
