package com.cat.activiti.print.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cat.activiti.model.Test4;
import com.cat.boot.service.BasePrintService;

@Service("testservice")
public class TestService extends BasePrintService {

	@SuppressWarnings("rawtypes")
	@Override
	public Map getDataMap(String keyValue) {
		Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();
		Test4 test = (Test4) baseService.findById(Test4.class, keyValue);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("entity.id", test.getId());
		dataMap.put("entity.dt.name", test.getSimple());
		map.put("dataMap", dataMap);
		// 输入循环
		Map<String, Object> dataList = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 2; i++) {
			Map<String, Object> tmps = new HashMap<String, Object>();
			tmps.put("pram1", 111111);
			tmps.put("pram2", 222222);
			tmps.put("pram3", 333333);
			list.add(tmps);
		}
		dataList.put("list1", list);
		map.put("dataList", dataList);
		return map;
	}

}
