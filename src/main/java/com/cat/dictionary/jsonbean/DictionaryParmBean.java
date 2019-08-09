package com.cat.dictionary.jsonbean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cat.boot.util.StringUtil;

public class DictionaryParmBean {

	// 类型编码
	private String typeCode;

	// 过滤
	private String filters;

	// 过滤
	private List<String> filter = new ArrayList<String>();

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getFilters() {
		return filters;
	}

	public void setFilters(String filters) {
		if (!StringUtil.isEmpty(filters)) {
			String[] f = filters.split(",");
			this.filter.addAll(Arrays.asList(f));
		}
		this.filters = filters;
	}

	public List<String> getFilter() {
		return filter;
	}

	public void setFilter(List<String> filter) {
		this.filter = filter;
	}

	public List<String> getFilter(String prefix) {
		List<String> f = new ArrayList<String>();
		if (!StringUtil.isListEmpty(filter)) {
			for (String string : filter) {
				f.add(prefix + string);
			}
		}
		return f;
	}
}
