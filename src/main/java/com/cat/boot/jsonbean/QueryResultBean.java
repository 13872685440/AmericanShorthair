package com.cat.boot.jsonbean;

import com.alibaba.fastjson.JSONObject;

public class QueryResultBean {

	private Long pageSize;

	private Long pageNo;

	private Long totalPage;

	private Long totalCount;

	private Object data;

	public Long getPageSize() {
		return pageSize;
	}

	public void setPageSize(Long pageSize) {
		this.pageSize = pageSize;
	}

	public Long getPageNo() {
		return pageNo;
	}

	public void setPageNo(Long pageNo) {
		this.pageNo = pageNo;
	}

	public Long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Long totalPage) {
		this.totalPage = totalPage;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public QueryResultBean() {

	}

	public QueryResultBean(BaseQueryHelp parms, Object result) {
		this.pageSize = parms.getPageSize() == 0 ? 20 : parms.getPageSize();
		this.pageNo = parms.getPageNo() == 0 ? 1 : parms.getPageNo();
		this.totalCount = parms.getTotalRecordCount();
		if (totalCount % pageSize > 0) {
			this.totalPage = totalCount / pageSize + 1;
		} else {
			this.totalPage = totalCount / pageSize;
		}
		this.data = result;
	}

	public static String getResultBean(BaseQueryHelp parms, Object result) {
		QueryResultBean bean = new QueryResultBean(parms, result);
		String json = JSONObject.toJSONString(bean);
		System.out.println(json);
		return json;
	}
}
