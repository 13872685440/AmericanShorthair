package com.cat.boot.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.cat.boot.jsonbean.BaseAppBean;
import com.cat.boot.jsonbean.PropParamBean;
import com.cat.boot.jsonbean.UserBean;
import com.cat.boot.model.BaseEntity;
import com.cat.boot.util.StringUtil;

@Service
public class BaseService {

	@Autowired(required = true)
	protected BaseDao baseDao;
	
	@Autowired
	public RedisUtils redisUtils;

	public boolean save(Object u) {
		if (u instanceof BaseEntity) {
			String uid = ((BaseEntity) u).getId();
			String ct = ((BaseEntity) u).getCt();
			if (uid != null && !"".equals(uid) && ct != null && !"".equals(ct)) {
				baseDao.save(u, true);
			} else {
				return baseDao.save(u, false);
			}
		}
		return true;
	}

	public boolean save(Object u, BaseAppBean bean) {
		if (u instanceof BaseEntity) {
			String uid = ((BaseEntity) u).getId();
			String ct = ((BaseEntity) u).getCt();
			if (uid != null && !"".equals(uid) && ct != null && !"".equals(ct)) {
				baseDao.save(u, bean, true);
			} else {
				return baseDao.save(u, bean, false);
			}
		}
		return true;
	}

	public boolean save(Object u, boolean flag) {
		return baseDao.save(u, flag);
	}

	public boolean noAnnotationSave(Object u, boolean flag) {
		return baseDao.noAnnotationSave(u, flag);
	}

	public boolean noAnnotationSave(Object u, BaseAppBean bean, boolean flag) {
		return baseDao.noAnnotationSave(u, bean, flag);
	}

	public boolean delete(Object u) {
		return baseDao.delete(u);
	}

	public boolean noAnnotationDelete(Object u) {
		return baseDao.noAnnotationDelete(u);
	}

	public void delete4Prop(String name, boolean isnative, Map<Object, Object> map) {
		baseDao.delete4Prop(name, isnative, map);
	}

	public void noAnnotationDelete4Prop(String name, boolean isnative, Map<Object, Object> map) {
		baseDao.noAnnotationDelete4Prop(name, isnative, map);
	}

	public void delete4Prop(String name, boolean isnative, List<PropParamBean> beans) {
		baseDao.delete4Prop(name, isnative, beans);
	}

	public void noAnnotationDelete4Prop(String name, boolean isnative, List<PropParamBean> beans) {
		baseDao.noAnnotationDelete4Prop(name, isnative, beans);
	}

	public void update4Prop(String name, boolean isnative, Map<Object, Object> map, Map<Object, Object> parmmap) {
		baseDao.update4Prop(name, isnative, map, parmmap);
	}

	public void noAnnotationUpdate4Prop(String name, boolean isnative, Map<Object, Object> map,
			Map<Object, Object> parmmap) {
		baseDao.noAnnotationUpdate4Prop(name, isnative, map, parmmap);
	}

	public void update4Prop(String name, boolean isnative, Map<Object, Object> map, List<PropParamBean> beans) {
		baseDao.update4Prop(name, isnative, map, beans);
	}

	public void noAnnotationUpdate4Prop(String name, boolean isnative, Map<Object, Object> map,
			List<PropParamBean> beans) {
		baseDao.noAnnotationUpdate4Prop(name, isnative, map, beans);
	}

	@SuppressWarnings("rawtypes")
	public void noAnnotationUpdate(Query query) {
		baseDao.noAnnotationUpdate(query);
	}

	public void update(String namespace, String xmlpath, String methodname, Map<Object, Object> map) {
		baseDao.update(namespace, xmlpath, methodname, map, true);
	}

	public void noAnnotationUpdate(String namespace, String xmlpath, String methodname, Map<Object, Object> map) {
		baseDao.update(namespace, xmlpath, methodname, map, false);
	}

	@SuppressWarnings("rawtypes")
	public Object findById(Class clazz, String id) {
		Object t = baseDao.findById(clazz, id);
		return t;
	}

	public String getCode(String clazz, String supcode, int i) {
		return baseDao.getCode(clazz, supcode, i);
	}

	public String getCode(int i, String clazz, String prop) {
		return baseDao.getCode(i, clazz, prop, new ArrayList<PropParamBean>());
	}

	public String getCode(int i, String clazz, String prop, List<PropParamBean> beans) {
		return baseDao.getCode(i, clazz, prop, beans);
	}

	public Long longResult(String name, String countby, boolean isfiter, boolean isdistinct) {
		Object tmp = baseDao.query4Prop(name, countby, isfiter, true, isdistinct, new HashMap<>());
		if (tmp == null) {
			return null;
		}
		if (tmp instanceof java.math.BigDecimal) {
			return ((BigDecimal) tmp).longValue();
		} else {
			return ((Long) tmp).longValue();
		}
	}

	public Long longResult(String name, String countby, boolean isfiter, boolean isdistinct, Map<Object, Object> map) {
		Object tmp = baseDao.query4Prop(name, countby, isfiter, true, isdistinct, map);
		if (tmp == null) {
			return null;
		}
		if (tmp instanceof java.math.BigDecimal) {
			return ((BigDecimal) tmp).longValue();
		} else {
			return ((Long) tmp).longValue();
		}
	}

	public Long longResult(String name, String countby, boolean isfiter, boolean isdistinct,
			List<PropParamBean> beans) {
		Object tmp = baseDao.query4Prop(name, countby, isfiter, true, isdistinct, beans);
		if (tmp == null) {
			return null;
		}
		if (tmp instanceof java.math.BigDecimal) {
			return ((BigDecimal) tmp).longValue();
		} else {
			return ((Long) tmp).longValue();
		}
	}

	public Long longResult(String namespace, String xmlpath, String methodname, Map<Object, Object> map) {
		Object tmp = baseDao.query(namespace, xmlpath, methodname, map, 0);
		if (tmp == null) {
			return null;
		}
		if (tmp instanceof java.math.BigDecimal) {
			return ((BigDecimal) tmp).longValue();
		} else {
			return ((Long) tmp).longValue();
		}
	}

	public Object getList(String namespace, String xmlpath, String methodname, Map<Object, Object> map) {
		return baseDao.query(namespace, xmlpath, methodname, map, 0);
	}

	public Object getList(String name, String orderby, boolean isfiter) {
		return baseDao.query4Prop(name, orderby, isfiter, false, false, new HashMap<>());
	}

	public Object getList(String name, String orderby, boolean isfiter, Map<Object, Object> map) {
		return baseDao.query4Prop(name, orderby, isfiter, false, false, map);
	}

	public Object getList(String name, String orderby, boolean isfiter, List<PropParamBean> beans) {
		return baseDao.query4Prop(name, orderby, isfiter, false, false, beans);
	}

	public Object getList(String table_name, String orderby, boolean isfiter, String prop) {
		return baseDao.query4Prop(table_name, orderby, isfiter, prop, new HashMap<>());
	}

	public Object getList(String table_name, String orderby, boolean isfiter, String prop, Map<Object, Object> map) {
		return baseDao.query4Prop(table_name, orderby, isfiter, prop, map);
	}

	public Object getList(String table_name, String orderby, boolean isfiter, String prop, List<PropParamBean> beans) {
		return baseDao.query4Prop(table_name, orderby, isfiter, prop, beans);
	}

	@SuppressWarnings("unchecked")
	public Object getSimple(String namespace, String xmlpath, String methodname, Map<Object, Object> map) {
		List<Object> objs = (List<Object>) getList(namespace, xmlpath, methodname, map);
		if (StringUtil.isListEmpty(objs)) {
			return null;
		} else {
			return objs.get(0);
		}
	}

	@SuppressWarnings("unchecked")
	public Object getSimple(String name, String orderby, boolean isfiter) {
		List<Object> objs = (List<Object>) getList(name, orderby, isfiter, new HashMap<>());
		if (StringUtil.isListEmpty(objs)) {
			return null;
		} else {
			return objs.get(0);
		}
	}

	@SuppressWarnings("unchecked")
	public Object getSimple(String name, String orderby, boolean isfiter, Map<Object, Object> map) {
		List<Object> objs = (List<Object>) getList(name, orderby, isfiter, map);
		if (StringUtil.isListEmpty(objs)) {
			return null;
		} else {
			return objs.get(0);
		}
	}

	@SuppressWarnings("unchecked")
	public Object getSimple(String name, String orderby, boolean isfiter, List<PropParamBean> beans) {
		List<Object> objs = (List<Object>) getList(name, orderby, isfiter, beans);
		if (StringUtil.isListEmpty(objs)) {
			return null;
		} else {
			return objs.get(0);
		}
	}

	@SuppressWarnings("unchecked")
	public Object getSimple(String table_name, String orderby, boolean isfiter, String prop) {
		List<Object> objs = (List<Object>) getList(table_name, orderby, isfiter, prop, new HashMap<>());
		if (StringUtil.isListEmpty(objs)) {
			return null;
		} else {
			return objs.get(0);
		}
	}

	@SuppressWarnings("unchecked")
	public Object getSimple(String table_name, String orderby, boolean isfiter, String prop, Map<Object, Object> map) {
		List<Object> objs = (List<Object>) getList(table_name, orderby, isfiter, prop, map);
		if (StringUtil.isListEmpty(objs)) {
			return null;
		} else {
			return objs.get(0);
		}
	}

	@SuppressWarnings("unchecked")
	public Object getSimple(String table_name, String orderby, boolean isfiter, String prop,
			List<PropParamBean> beans) {
		List<Object> objs = (List<Object>) getList(table_name, orderby, isfiter, prop, beans);
		if (StringUtil.isListEmpty(objs)) {
			return null;
		} else {
			return objs.get(0);
		}
	}

	@SuppressWarnings("unchecked")
	public boolean isUnion(String namespace, String xmlpath, String methodname, Map<Object, Object> map) {
		List<Object> objs = (List<Object>) getList(namespace, xmlpath, methodname, map);
		if (StringUtil.isListEmpty(objs)) {
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public boolean isUnion(String name, String orderby, boolean isfiter) {
		List<Object> objs = (List<Object>) getList(name, orderby, isfiter, new HashMap<>());
		if (StringUtil.isListEmpty(objs)) {
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public boolean isUnion(String name, String orderby, boolean isfiter, Map<Object, Object> map) {
		List<Object> objs = (List<Object>) getList(name, orderby, isfiter, map);
		if (StringUtil.isListEmpty(objs)) {
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public boolean isUnion(String name, String orderby, boolean isfiter, List<PropParamBean> beans) {
		List<Object> objs = (List<Object>) getList(name, orderby, isfiter, beans);
		if (StringUtil.isListEmpty(objs)) {
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public boolean isUnion(String table_name, String orderby, boolean isfiter, String prop) {
		List<Object> objs = (List<Object>) getList(table_name, orderby, isfiter, prop, new HashMap<>());
		if (StringUtil.isListEmpty(objs)) {
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public boolean isUnion(String table_name, String orderby, boolean isfiter, String prop, Map<Object, Object> map) {
		List<Object> objs = (List<Object>) getList(table_name, orderby, isfiter, prop, map);
		if (StringUtil.isListEmpty(objs)) {
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public boolean isUnion(String table_name, String orderby, boolean isfiter, String prop, List<PropParamBean> beans) {
		List<Object> objs = (List<Object>) getList(table_name, orderby, isfiter, prop, beans);
		if (StringUtil.isListEmpty(objs)) {
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings("rawtypes")
	public Object query(Map maps, Map<Object, Object> map) {
		return baseDao.query(maps, map);
	}

	@SuppressWarnings("rawtypes")
	public Query query(String ql, boolean isnative) {
		return baseDao.query(ql, isnative);
	}

	public void setEntityManager(EntityManager entityManager) {
		baseDao.setEntityManager(entityManager);
	}
	
	public UserBean getUserInfo(HttpServletRequest request) {
		try {
			String token = request.getHeader("Access-Token");
			UserBean ybean = JSON.parseObject(redisUtils.get(token), UserBean.class);
			return ybean;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
