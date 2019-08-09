package com.cat.system.jsonbean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cat.boot.service.BaseService;
import com.cat.boot.util.StringUtil;
import com.cat.system.model.AppTree;
import com.cat.system.model.TaskRouter;

public class AppTreeInfoBean {

	// 构建菜单
	private String title;

	private String key;

	private String redirect;

	private String path;

	private String code;

	private String name;

	private String component;

	private String icon;

	private boolean hide = false;

	private List<AppTreeInfoBean> children = new ArrayList<AppTreeInfoBean>();

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getRedirect() {
		return redirect;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public boolean isHide() {
		return hide;
	}

	public void setHide(boolean hide) {
		this.hide = hide;
	}

	public List<AppTreeInfoBean> getChildren() {
		return children;
	}

	public void setChildren(List<AppTreeInfoBean> children) {
		this.children = children;
	}

	public static List<AppTreeInfoBean> iniAppTree(List<AppTree> entitys, BaseService baseService) {
		// 构建根目录
		AppTreeInfoBean bean = new AppTreeInfoBean();
		bean.setTitle("首页");
		bean.setKey("");
		bean.setName("index");
		bean.setComponent("BasicLayout");
		bean.setRedirect("/index");

		Map<String, List<AppTree>> map = new HashMap<String, List<AppTree>>();
		Map<String, AppTree> map2 = new HashMap<String, AppTree>();

		for (AppTree entity : entitys) {
			do {
				if (!StringUtil.isMapContainsKey(map2, entity.getCode())) {
					map2.put(entity.getCode(), entity);
					String key = "index";
					if (entity.getSuperior() != null) {
						key = entity.getSuperior().getCode();
					}
					if (StringUtil.isMapContainsKey(map, key)) {
						List<AppTree> a = map.get(key);
						a.add(entity);
						map.replace(key, a);
					} else {
						List<AppTree> a = new ArrayList<AppTree>();
						a.add(entity);
						map.put(key, a);
					}
				}
				entity = entity.getSuperior();
			} while (entity != null);
		}

		for (AppTree appTree : map.get("index")) {
			AppTreeInfoBean a = iniTree(appTree.getCode(), map, map2, baseService);
			bean.getChildren().add(a);
		}
		List<AppTreeInfoBean> bs = new ArrayList<AppTreeInfoBean>();
		bs.add(bean);
		return bs;
	}

	static AppTreeInfoBean iniTree(String key, Map<String, List<AppTree>> map, Map<String, AppTree> map2,
			BaseService baseService) {
		AppTreeInfoBean tbean = new AppTreeInfoBean();
		AppTree bean = map2.get(key);
		List<AppTreeInfoBean> beans = new ArrayList<AppTreeInfoBean>();
		if (StringUtil.isMapContainsKey(map, key)) {
			List<AppTree> value = map.get(key);
			if (!StringUtil.isListEmpty(value)) {
				for (AppTree bean1 : value) {
					AppTreeInfoBean bean2 = iniTree(bean1.getCode(), map, map2, baseService);
					beans.add(bean2);
				}
			}
		}
		tbean.setTitle(bean.getName());
		tbean.setComponent(bean.getComponent());
		tbean.setIcon(bean.getIcon());
		if (!StringUtil.isListEmpty(beans)) {
			if ("0001".equals(bean.getCode())) {
				beans.addAll(iniTaskRouter(baseService));
			}
			tbean.setChildren(beans);
			tbean.setKey("/" + bean.getCode());
		} else {
			tbean.setKey("A" + bean.getCode());
			tbean.setPath(bean.getPath());
		}
		return tbean;
	}

	@SuppressWarnings("unchecked")
	static List<AppTreeInfoBean> iniTaskRouter(BaseService baseService) {
		List<AppTreeInfoBean> tasks = new ArrayList<AppTreeInfoBean>();
		// 查询任务路由
		List<TaskRouter> t = (List<TaskRouter>) baseService.getList("TaskRouter", "", true);
		if (StringUtil.isListEmpty(t)) {
			return tasks;
		} else {
			Map<TaskRouter, List<AppTreeInfoBean>> map = new HashMap<TaskRouter, List<AppTreeInfoBean>>();
			for (TaskRouter key : t) {
				if (key.getSuperior() == null) {
					if (!StringUtil.isMapContainsKey(map, key)) {
						map.put(key, new ArrayList<AppTreeInfoBean>());
					}
				} else {
					AppTreeInfoBean bean = new AppTreeInfoBean();
					bean.setTitle(key.getName());
					bean.setComponent(key.getComponent());
					bean.setKey(key.getCode());
					TaskRouter superior = key.getSuperior();
					if (!StringUtil.isMapContainsKey(map, superior)) {
						List<AppTreeInfoBean> r = new ArrayList<AppTreeInfoBean>();
						r.add(bean);
						map.put(superior, r);
					} else {
						List<AppTreeInfoBean> r = map.get(superior);
						r.add(bean);
						map.replace(superior, r);
					}
				}
			}
			for (TaskRouter key : map.keySet()) {
				AppTreeInfoBean bean = new AppTreeInfoBean();
				bean.setTitle(key.getName());
				bean.setComponent(key.getComponent());

				bean.setHide(true);

				if (!StringUtil.isListEmpty(map.get(key))) {
					bean.setKey("/" + key.getCode());
					bean.setChildren(map.get(key));
				} else {
					bean.setKey(key.getCode());
				}
				tasks.add(bean);
			}
		}
		return tasks;
	}
}
