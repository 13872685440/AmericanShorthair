package com.cat.boot.jsonbean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cat.boot.util.StringUtil;

public class TreeBean {

	private String key;

	private String title;

	private String value;

	private String icon;

	private Map<String, String> scopedSlots = new HashMap<String, String>();

	private List<TreeBean> children;

	private boolean disabled = false;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public List<TreeBean> getChildren() {
		return children;
	}

	public void setChildren(List<TreeBean> children) {
		this.children = children;
	}

	public Map<String, String> getScopedSlots() {
		return scopedSlots;
	}

	public void setScopedSlots(Map<String, String> scopedSlots) {
		this.scopedSlots = scopedSlots;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public TreeBean() {

	}

	public TreeBean(Object key, Object title) {
		this.key = (String) key;
		this.title = (String) title;
		this.scopedSlots.put("title", "title");
	}

	public static List<TreeBean> getTree(List<Object[]> objs, Integer parm) {
		List<TreeBean> beans = new ArrayList<TreeBean>();
		Map<Object, List<TreeBean>> map = new HashMap<Object, List<TreeBean>>();
		Map<String, TreeBean> map2 = new HashMap<String, TreeBean>();
		List<TreeBean> first = new ArrayList<TreeBean>();
		for (Object[] obj : objs) {
			TreeBean bean = new TreeBean(obj[0], obj[1]);
			bean.setValue((String) obj[0]);
			String superior = (String) obj[2];
			map2.put((String) obj[0], bean);
			if (StringUtil.isEmpty(superior)) {
				map.put(bean.getKey(), new ArrayList<TreeBean>());
				first.add(bean);
			} else {
				if (!StringUtil.isMapContainsKey(map, superior)) {
					List<TreeBean> list = new ArrayList<TreeBean>();
					list.add(bean);
					map.put(superior, list);
				} else {
					List<TreeBean> list = map.get(superior);
					list.add(bean);
					map.replace(superior, list);
				}
			}
		}
		// 循环第一层
		for (TreeBean bean : first) {
			beans.add(iniTree(bean.getKey(), map, map2, parm));
		}
		return beans;
	}

	static TreeBean iniTree(Object key, Map<Object, List<TreeBean>> map, Map<String, TreeBean> map2, Integer parm) {
		TreeBean bean = new TreeBean();
		bean = map2.get(key);
		List<TreeBean> beans = new ArrayList<TreeBean>();
		if (StringUtil.isMapContainsKey(map, key)) {
			List<TreeBean> value = map.get(key);
			if (!StringUtil.isListEmpty(value)) {
				for (TreeBean bean1 : value) {
					TreeBean bean2 = iniTree(bean1.getKey(), map, map2, parm);
					beans.add(bean2);
				}
			}
		}
		if (!StringUtil.isListEmpty(beans)) {
			bean.setChildren(beans);
			if (parm != null && parm == 1) {
				// 禁用非末级
				bean.setDisabled(true);
			}
		}
		return bean;
	}
}
