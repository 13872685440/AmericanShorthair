package com.cat.boot.jsonbean;

import org.apache.commons.lang3.StringUtils;

public class QueryParamBean {

	/** 参数名称 */
	private String pn;

	/** 文本值 */
	private String sv;

	private Object obj = null;

	private Object value = null;

	/** 参数级别 当 清理参数时 清理低于指定级别参数 */
	private int pl = 0;

	private String des;

	private String valueDes;

	public boolean isDataNull() {

		if (sv == null) {
			return true;
		} else {
			return false;
		}
	}

	public String strValue() {

		return null;// StringUtil.toStr(value, "未设置");
	}

	/***
	 * 获取文档字符值 用于打印
	 * 
	 */
	public Object getValue() {

		return value;
	}

	public String getSv() {

		return sv;
	}

	public void setSv(String sv) {

		this.sv = StringUtils.trim(sv);
		this.value = sv;
	}

	public Object getObj() {

		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
		this.value = obj;
	}

	@Override
	public String toString() {
		return "QueryParamBean [value=" + String.valueOf(getValue()) + "]";
	}

	public String getPn() {

		return pn;
	}

	public void setPn(String pn) {

		this.pn = StringUtils.trim(pn);
	}

	public void setNull() {
		setSv(null);
		setObj(null);
		this.value = null;
	}

	public void setValue(Object value) {

		if (value == null) {
			setNull();
			return;
		}
		setObj(value);
	}

	public QueryParamBean(String pn, Object value) {
		super();
		setPn(pn);
		if (value == null)
			return;
		setValue(value);
	}

	public QueryParamBean(int pl, String pn, Object value) {
		super();
		setPn(pn);
		setPl(pl);
		if (value == null)
			return;
		setValue(value);
	}

	public QueryParamBean() {

		super();
	}

	public int getPl() {

		return pl;
	}

	public void setPl(int pl) {

		this.pl = pl;
	}

	public String getDes() {

		return des;
	}

	public void setDes(String des) {

		this.des = des;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((pn == null) ? 0 : pn.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QueryParamBean other = (QueryParamBean) obj;
		if (pn == null) {
			if (other.pn != null)
				return false;
		} else if (!pn.equals(other.pn))
			return false;
		return true;
	}

	public String getValueDes() {

		return valueDes;
	}

	public void setValueDes(String valueDes) {

		this.valueDes = valueDes;
	}

}
