package com.cat.file.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.cat.boot.util.StringUtil;

@Component
@ConfigurationProperties
@PropertySource(value = "classpath:path.properties")
public class PathConfig {

	private Map<String, String> pathMap = new HashMap<String, String>();

	public Map<String, String> getPathMap() {
		return pathMap;
	}

	public void setPathMap(Map<String, String> pathMap) {
		this.pathMap = pathMap;
	}

	public String getPath(String key) {
		if (StringUtil.isMapContainsKey(pathMap, key)) {
			return pathMap.get(key);
		} else {
			return "";
		}
	}
}
