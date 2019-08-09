package com.cat.boot.selector;

import java.util.Map;

import com.cat.boot.jsonbean.BaseQueryHelp;
import com.cat.boot.jsonbean.ParamBean;
import com.cat.boot.service.BaseQuery;
import com.cat.boot.util.NameQueryUtil;

public abstract class BaseSelector<T> extends BaseQuery<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4231161491002243416L;
	
	private ParamBean bean;

	public abstract String select(ParamBean bean, BaseQueryHelp parms) throws Exception;

	@Override
	public Map<String, String> nameQueryName() {
		return NameQueryUtil.setParam(bean.getNamespace(), bean.getXmlpath(), bean.getMethodname(), null);
	}

	public void excuteSelect(BaseQueryHelp parms) throws Exception {
		perSelQuery(parms);
	}

	public void perSelQuery(BaseQueryHelp parms) throws Exception {
		iniQhb(parms);
		executeQuery(3);
		executeCountQuery(3);
		parms.setTotalRecordCount(getQhb().getTotalRecordCount());
	}

	private void iniQhb(BaseQueryHelp parms) {
		getQhb().setParams(parms.getParams());
		getQhb().setPageSize(parms.getPageSize() == 0 ? 20 : parms.getPageSize());
		getQhb().setPageNo(parms.getPageNo());
		getQhb().setSortOrder(parms.getSortOrder());
		getQhb().setSortField(parms.getSortField());
		getQhb().setUserId(parms.getUserId());
	}

	public ParamBean getBean() {
		return bean;
	}

	public void setBean(ParamBean bean) {
		this.bean = bean;
	};

}
