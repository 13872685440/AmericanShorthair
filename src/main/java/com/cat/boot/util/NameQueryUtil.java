package com.cat.boot.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.query.Query;

import com.cat.boot.catconst.QueryPathConst;
import com.cat.boot.jsonbean.NameQueryBean;
import com.cat.boot.jsonbean.PropParamBean;
import com.cat.boot.jsonbean.QueryFilterBean;
import com.cat.boot.jsonbean.QueryParamDefineBean;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class NameQueryUtil {

	@SuppressWarnings("rawtypes")
	public static NameQueryBean getNameQuery(String namespace, String xmlpath, String methodname, String orderby, int i)
			throws Exception {
		Map maps = getQueryMap(namespace, xmlpath, methodname, i);
		if (StringUtil.isMapEmpty(maps)) {
			return iniNameQueryBean(namespace, orderby);
		} else {
			return iniNameQueryBean(maps);
		}
	}

	@SuppressWarnings("rawtypes")
	public static boolean isreload(int i) {
		Map maps = XmlUtil.parserXml(QueryPathConst.reload);
		if (!StringUtil.isMapEmpty(maps)) {
			String reload;
			switch (i) {
			case 0:
				reload = (String) ((Map) maps.get("queryreload")).get("reload");
				break;
			case 2:
				reload = (String) ((Map) maps.get("queryreload")).get("namequeryreload");
				break;
			case 4:
				reload = (String) ((Map) maps.get("queryreload")).get("exportreload");
				break;
			default:
				reload = (String) ((Map) maps.get("queryreload")).get("reload");
				break;
			}
			return Boolean.valueOf(reload);
		} else {
			return false;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static NameQueryBean iniNameQueryBean(Map maps) {
		NameQueryBean nb = new NameQueryBean();
		if (maps.containsKey("isnative")) {
			nb.setUserNativeQuery(Boolean.valueOf((String) maps.get("isnative")));
		} else {
			nb.setUserNativeQuery(false);
		}
		nb.setQl((String) maps.get("sql"));
		nb.setQlCount((String) maps.get("countsql"));
		if (maps.containsKey("orderby")) {
			nb.setOrderBy((String) maps.get("orderby"));
		}
		if (maps.containsKey("parm")) {
			Map<Object, Object> parms = (Map<Object, Object>) maps.get("parm");
			for (Object key : parms.keySet()) {
				String key1 = ((String) key).replaceFirst("name", "");
				nb.putQpd(key1, new QueryParamDefineBean(key1, (String) ((Map) parms.get(key)).get("type")));
			}
		}
		if (maps.containsKey("filter")) {
			Map<Object, Object> filters = (Map<Object, Object>) maps.get("filter");
			for (Object key : filters.keySet()) {
				Map<Object, Object> tmps = (Map<Object, Object>) filters.get(key);
				QueryFilterBean bean = new QueryFilterBean();
				bean.setQl((String) tmps.get("sql"));
				bean.setEl((String) tmps.get("el"));
				bean.setElsource((String) tmps.get("elsource"));
				if (tmps.containsKey("instag")) {
					bean.setInstag((String) tmps.get("instag"));
				}
				if (tmps.containsKey("parm")) {
					Map<Object, Object> parms = (Map<Object, Object>) tmps.get("parm");
					for (Object key2 : parms.keySet()) {
						String key3 = ((String) key2).replaceFirst("parm", "");
						bean.putQpd(key3, new QueryParamDefineBean(key3, (String) ((Map) parms.get(key2)).get("type")));
					}
				}
				nb.putQfb((String) key, bean);
			}
		}
		return nb;
	}

	public static NameQueryBean iniNameQueryBean(String namespace, String orderby) {
		NameQueryBean nb = new NameQueryBean();
		nb.setUserNativeQuery(false);
		nb.setQl("select o from " + namespace + " o where 1=1");
		nb.setQlCount("select count(*) from " + namespace + " where 1=1");
		nb.setOrderBy(orderby);
		return nb;
	}

	public static Map<Object, Object> setParams(Object... prams) {
		Map<Object, Object> maps = new HashMap<Object, Object>();
		for (int i = 0; i < prams.length; i++) {
			if (i % 2 == 0) {
				maps.put((String) prams[i], prams[i + 1]);
			}
		}
		return maps;
	}

	public static Map<String, String> setParam(String namespace, String xmlpath, String methodname, String orderby) {
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("namespace", namespace);
		maps.put("xmlpath", xmlpath);
		maps.put("methodname", methodname);
		if (StringUtil.isEmpty(orderby)) {
			maps.put("orderby", "o.ct desc");
		} else {
			maps.put("orderby", orderby);
		}
		return maps;
	}

	public static boolean isNull(Object value) {
		if (value == null) {
			return true;
		}
		if (value instanceof java.lang.String) {
			if ("null".equals((String) value) || "not null".equals((String) value)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public static NameQueryBean getNameQuery(Map<String, String> tmps, int i) throws Exception {
		return NameQueryUtil.getNameQuery(tmps.get("namespace"), tmps.get("xmlpath"), tmps.get("methodname"),
				tmps.get("orderby"), i);
	}

	@SuppressWarnings("rawtypes")
	public static Map getQueryMap(String namespace, String xmlpath, String methodname, int i) {
		CacheManager manager = CacheManager.create();
		// 通过manager可以生成指定名称的Cache对象
		Cache cache = manager.getCache("daoCache");
		Element element2 = cache.get(namespace + xmlpath + methodname);
		if (element2 != null && !isreload(i)) {
			return (Map) element2.getObjectValue();
		} else {
			String tmp = "";
			switch (i) {
			case 0:
				tmp = QueryPathConst.getPath(xmlpath);
				break;
			case 1:
				tmp = QueryPathConst.getUpdatePath(xmlpath);
				break;
			case 2:
				tmp = QueryPathConst.getNamequeryPath(xmlpath);
				break;
			case 3:
				tmp = QueryPathConst.getSelectorPath(xmlpath);
				break;
			case 4:
				tmp = QueryPathConst.getExportPath(xmlpath);
				break;
			default:
				tmp = QueryPathConst.getPath(xmlpath);
				break;
			}
			Map maps = XmlUtil.parserXml(tmp);
			if (!StringUtil.isMapEmpty(maps)) {
				try {
					Map map2s = (Map) ((Map) ((Map) maps.get(xmlpath)).get(namespace)).get("query" + methodname);
					Element element = new Element(namespace + xmlpath + methodname, map2s);
					cache.put(element);
					return map2s;
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			} else {
				return null;
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public static boolean isnative(Map maps) {
		if (!maps.containsKey("isnative")) {
			return false;
		}
		String isnative = (String) maps.get("isnative");
		return Boolean.valueOf(isnative);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean isList(Map maps, Object key) {
		Map<Object, Object> map = (Map<Object, Object>) maps.get(key);
		String type = (String) map.get("type");
		if (type.equals("List")) {
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings("rawtypes")
	public static boolean iscount(Map maps) {
		if (!maps.containsKey("iscount")) {
			return false;
		}
		String iscount = (String) maps.get("iscount");
		return Boolean.valueOf(iscount);
	}

	public static String replaceUserId(String sql) {
		sql = sql.replaceAll("@@([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@@", " ");
		return sql;
	}

	public static String replaceUserId(String sql, String userId) {
		if (StringUtil.isEmpty(userId)) {
			sql = sql.replaceAll("@@([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@@", " ");
			return sql;
		} else {
			sql = sql.replaceAll("@@([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@@", " ").replaceAll("#userId#",
					"'" + userId + "'");
			return sql;
		}
	}

	public static List<PropParamBean> getBeans(PropParamBean bean) {
		List<PropParamBean> beans = new ArrayList<PropParamBean>();
		beans.add(bean);
		return beans;
	}

	@SuppressWarnings("rawtypes")
	public static String linkSql(String sql, Map map) {
		sql = sql + " where (1=1) ";
		if (!StringUtil.isMapEmpty(map)) {
			for (Object key : map.keySet()) {
				sql = sql + " and ";
				Object value = map.get(key);
				if (value instanceof java.util.List) {
					sql = sql + "o." + key + " in (:" + ((String) key).replaceAll("\\.", "") + ")";
				} else {
					if (NameQueryUtil.isNull(value)) {
						sql = sql + "o." + key + " is " + (String) value;
					} else {
						sql = sql + "o." + key + " =:" + ((String) key).replaceAll("\\.", "");
					}
				}
			}
		}
		return sql;
	}

	public static String linkSql(String sql, List<PropParamBean> beans) {
		sql = sql + " where (1=1) ";
		if (!StringUtil.isListEmpty(beans)) {
			for (PropParamBean bean : beans) {
				sql = sql + " " + bean.getLinkword() + " ";
				Object key = bean.getParam();
				Object value = bean.getValue();
				if (value instanceof java.util.List) {
					sql = sql + "o." + key + " " + bean.getKeyword() + " (:" + ((String) key).replaceAll("\\.", "")
							+ ")";
				} else {
					if (NameQueryUtil.isNull(value)) {
						sql = sql + "o." + key + " is " + value;
					} else {
						sql = sql + "o." + key + " " + bean.getKeyword() + " :" + ((String) key).replaceAll("\\.", "");
					}
				}
			}
		}
		return sql;
	}

	@SuppressWarnings("rawtypes")
	public static void setParameter(Query query, Map<Object, Object> map) {
		if (!StringUtil.isMapEmpty(map)) {
			for (Object key : map.keySet()) {
				Object value = map.get(key);
				if (value instanceof java.util.List) {
					query.setParameterList(((String) key).replaceAll("\\.", ""), (List) value);
				} else {
					if (!NameQueryUtil.isNull(value)) {
						query.setParameter(((String) key).replaceAll("\\.", ""), StringUtil.toBoolean(value));
					}
				}
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public static void setParameter(Query query, List<PropParamBean> beans) {
		if (!StringUtil.isListEmpty(beans)) {
			for (PropParamBean bean : beans) {
				Object value = bean.getValue();
				if (value instanceof java.util.List) {
					query.setParameterList(((String) bean.getParam()).replaceAll("\\.", ""), (List) value);
				} else {
					if (!NameQueryUtil.isNull(value)) {
						query.setParameter(((String) bean.getParam()).replaceAll("\\.", ""), value);
					}
				}
			}
		}
	}
}
