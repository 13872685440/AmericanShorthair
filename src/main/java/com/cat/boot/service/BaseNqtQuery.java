package com.cat.boot.service;

import com.cat.boot.jsonbean.BaseQueryHelp;
import com.cat.boot.jsonbean.ResultBean;

public abstract class BaseNqtQuery<T> extends BaseQuery<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 736515704643423088L;

	public abstract String query(BaseQueryHelp parms) throws Exception;

	public void excuteQuery(BaseQueryHelp parms) throws Exception {
		iniQhb(parms);
		executeQuery(2);
		executeCountQuery(2);
		parms.setTotalRecordCount(getQhb().getTotalRecordCount());
	};

	private void iniQhb(BaseQueryHelp parms) {
		getQhb().setParams(parms.getParams());
		getQhb().setPageSize(parms.getPageSize() == 0 ? 20 : parms.getPageSize());
		getQhb().setPageNo(parms.getPageNo()== 0 ? 1 : parms.getPageNo());
		getQhb().setSortOrder(parms.getSortOrder());
		getQhb().setSortField(parms.getSortField());
		getQhb().setUserId(parms.getUserId());
	}

	@SuppressWarnings("unchecked")
	protected String delete(String id) {
		T entity = (T) baseService.findById(getEntityClass(), id);
		boolean flag = baseService.noAnnotationDelete(entity);
		if (flag) {
			return ResultBean.getSucess("删除成功");
		} else {
			return ResultBean.getResultBean("400", "", "删除失败");
		}
	}
}
