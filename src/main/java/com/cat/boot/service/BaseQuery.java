package com.cat.boot.service;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import com.cat.boot.jsonbean.BaseQueryHelp;
import com.cat.boot.jsonbean.IResultConvert;
import com.cat.boot.jsonbean.NameQueryBean;
import com.cat.boot.jsonbean.QueryFilterBean;
import com.cat.boot.jsonbean.QueryParamDefineBean;
import com.cat.boot.util.ExpressUtil;
import com.cat.boot.util.NameQueryUtil;
import com.cat.boot.util.StringUtil;

public abstract class BaseQuery<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3053594133513794832L;

	@Autowired(required = true)
	public BaseService baseService;

	@Autowired
	public RedisUtils redisUtils;

	/** 查询参数session Key */
	private String qhn;

	private BaseQueryHelp qhb = new BaseQueryHelp();

	private NameQueryBean nb;

	/** 查询结果 */
	protected List<T> results = new ArrayList<T>();

	public Map<String, String> nameQueryName() {
		return NameQueryUtil.setParam(getClassName(), getXmlPath(), getClassName() + "_mainQuery", null);
	}

	@SuppressWarnings("rawtypes")
	public void executeCountQuery(int i) throws Exception {
		String ql = getNqb(i).getQlCount();
		try {
			Query query = null;
			if (getNb().isUserNativeQuery()) {
				query = applyCriteriaToQuery(baseService.query(ql, true), i);
				setTotalRecordCount(((BigDecimal) query.uniqueResult()).longValue());
			} else {
				query = applyCriteriaToQuery(baseService.query(ql, false), i);
				setTotalRecordCount(((Long) query.uniqueResult()).longValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	protected Query applyCriteriaToQuery(Query query, int i) throws Exception {
		applyParmToQuery(query, i);
		return query;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void executeQuery(int i) throws Exception {
		NameQueryBean nb = getNqb(i);
		String ql = nb.getQl();
		if (!StringUtil.isEmpty(getQhb().getOrder())) {
			ql = ql + " order by " + getQhb().getOrder();
		} else if (!StringUtil.isEmpty(nb.getOrderBy())) {
			ql = ql + " order by " + nb.getOrderBy();
		}
		try {
			Query query = null;
			query = applyCriteriaToQuery(baseService.query(ql, getNb().isUserNativeQuery()), i);
			if (this instanceof IResultConvert) {
				if (getPageSize() == -1) {
					List<Object[]> ls = query.list();
					setResults(((IResultConvert) this).toResult(ls));
				} else {
					List<Object[]> ls = query.setFirstResult(Long.valueOf(getOffset()).intValue())
							.setMaxResults(Long.valueOf(getPageSize()).intValue()).list();
					setResults(((IResultConvert) this).toResult(ls));
				}
			} else {
				if (getPageSize() == -1) {
					setResults(query.list());
				} else {
					setResults(query.setFirstResult(Long.valueOf(getOffset()).intValue())
							.setMaxResults(Long.valueOf(getPageSize()).intValue()).list());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public BaseQueryHelp queryHelpInstance() {
		if (qhb == null) {
			return new BaseQueryHelp();
		} else {
			return qhb;
		}
	}

	public String queryHelpName() {
		return "qhb." + nameQueryName().get("methodname");
	}

	@SuppressWarnings("rawtypes")
	protected Query applyParmToQuery(Query query, int i) throws Exception {
		Map<String, QueryParamDefineBean> qpds = getNqb(i).getQpds();
		if (!StringUtil.isMapEmpty(qpds)) {
			for (String key : qpds.keySet()) {
				QueryParamDefineBean bean = qpds.get(key);
				Object value = getQhb().pv(key);
				setParameter(query, bean, value);
			}
		}
		return query;
	}

	@SuppressWarnings("rawtypes")
	private void setParameter(Query query, QueryParamDefineBean bean, Object value) {
		if (("List").equals(bean.getType())) {
			query.setParameterList(bean.getName(), (List) value);
		} else {
			query.setParameter(bean.getName(), value);
		}
	}

	private NameQueryBean initNqb(int i) throws Exception {
		Map<String, String> tmps = nameQueryName();
		nb = NameQueryUtil.getNameQuery(tmps, i);
		initQueryFilter(nb);
		nb.setQl(NameQueryUtil.replaceUserId(nb.getQl(), getQhb().getUserId()));
		if (!StringUtil.isEmpty(nb.getQlCount())) {
			nb.setQlCount(NameQueryUtil.replaceUserId(nb.getQlCount(), getQhb().getUserId()));
		}
		return nb;
	}

	private void initQueryFilter(NameQueryBean nb) {
		if (!StringUtil.isMapEmpty(nb.getQfbs())) {
			for (String key : nb.getQfbs().keySet()) {
				boolean isload = true;
				QueryFilterBean bean = nb.getQfbs().get(key);
				String el = bean.getEl();
				if (el.contains("and")) {
					String[] els = el.split("and");
					for (String string : els) {
						// 解析是否有not
						if (!elString(bean.getElsource(), string)) {
							isload = false;
							continue;
						}
					}
				} else {
					if (!elString(bean.getElsource(), el)) {
						isload = false;
					}
				}
				if (!isload) {
					continue;
				}

				if (StringUtil.isEmpty(bean.getInstag())) {
					nb.setQl(nb.getQl() + "   \r\n  " + bean.getQl() + "   \r\n  ");
					if (!StringUtil.isEmpty(nb.getQlCount())) {
						nb.setQlCount(nb.getQlCount() + "   \r\n  " + bean.getQl() + "   \r\n  ");
					}
				} else {
					nb.setQl(nb.getQl().replaceAll(bean.getInstag(), bean.getQl()));
					if (!StringUtil.isEmpty(nb.getQlCount())) {
						nb.setQlCount(nb.getQlCount().replaceAll(bean.getInstag(), bean.getQl()));
					}
				}
				Map<String, QueryParamDefineBean> beans = bean.getQpds();
				if (!StringUtil.isMapEmpty(beans)) {
					for (String key2 : beans.keySet()) {
						nb.putQpd(key2, beans.get(key2));
					}
				}
			}
		}
	}

	private Boolean elvalue(String elcontext, String el) {
		if ("Subject".equals(elcontext)) {
			Subject subject = SecurityUtils.getSubject();
			return ExpressUtil.elValue(subject, el);
		} else if ("BaseQueryHelp".equals(elcontext)) {
			BaseQueryHelp bqh = this.getQhb();
			return ExpressUtil.elValue(bqh, el);
		} else {
			return ExpressUtil.elValue(this, el);
		}
	}

	private boolean elString(String elcontext, String el) {
		boolean not = false;
		if (el.contains("not")) {
			el = el.replace("not", "");
			not = true;
		}
		// 解析string
		if (not) {
			not = !elvalue(elcontext, el);
		} else {
			not = elvalue(elcontext, el);
		}
		if (not) {
			return true;
		} else {
			return false;
		}
	}

	protected void setResults(List<T> results) {
		this.results = null;
		this.results = results;
	}

	public long getPageSize() {
		return getQhb().getPageSize();
	}

	public NameQueryBean getNqb(int i) throws Exception {
		if (nb == null || StringUtil.isEmpty(nb.getQl())) {
			return initNqb(i);
		} else {
			return nb;
		}

	}

	public BaseQueryHelp getQhb() {
		return this.qhb;
	}

	public void setQhb(BaseQueryHelp qhb) {
		this.qhb = qhb;
	}

	public String getQhn() {
		if (StringUtil.isEmpty(qhn)) {
			qhn = queryHelpName();
		}
		return qhn;
	}

	public void setQhn(String qhn) {
		this.qhn = qhn;
	}

	public NameQueryBean getNb() {
		return nb;
	}

	public void setNb(NameQueryBean nb) {
		this.nb = nb;
	}

	public List<T> getResults() {
		return results;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Class getEntityClass() {
		return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	protected String getClassName() {
		return getEntityClass().getSimpleName();
	}

	protected String getXmlPath() {
		String packagename = getEntityClass().getPackage().getName();
		String[] names = packagename.split("\\.");
		if (names.length > 3) {
			return names[2];
		} else {
			return "";
		}
	}

	protected void setTotalRecordCount(long totalRecordCount) {
		getQhb().setTotalRecordCount(totalRecordCount);
	}

	public long getOffset() {
		return getQhb().getOffset();
	}

}
