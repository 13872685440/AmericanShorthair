package com.cat.boot.selector;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cat.boot.jsonbean.BaseQueryHelp;
import com.cat.boot.jsonbean.ParamBean;
import com.cat.boot.jsonbean.QueryResultBean;
import com.cat.boot.jsonbean.ResultBean;

@SuppressWarnings("rawtypes")
@RestController
@Scope("prototype")
@RequestMapping("/common")
public class CommonSelector extends BaseSelector {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3512765940801223586L;

	@Override
	@RequestMapping(value = "/selector", method = RequestMethod.GET)
	public String select(ParamBean bean, BaseQueryHelp parms) throws Exception {
		bean.setMethodname("Dictionary_mainQuery_selector");
		bean.setNamespace("Dictionary");
		bean.setXmlpath("dictionary");
		setBean(bean);
		excuteSelect(parms);
		return ResultBean.getSucess(new QueryResultBean(parms, results));
	}

}
