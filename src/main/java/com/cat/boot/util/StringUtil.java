package com.cat.boot.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StringUtil {

	public static boolean isListEmpty(Object list) {
		if (list instanceof java.util.List) {
			if (list != null && !((List<Object>) list).isEmpty()) {
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}

	public static boolean isMapEmpty(Object list) {
		if (list instanceof java.util.Map) {
			if (list != null && !((Map<Object, Object>) list).isEmpty()) {
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}

	public static boolean isMapContainsKey(Map map, Object key) {
		if (isMapEmpty(map)) {
			return false;
		} else {
			if (map.containsKey(key)) {
				return true;
			} else {
				return false;
			}
		}
	}

	public static boolean isEmpty(String str) {
		if (str != null && !"".equals(str)) {
			return false;
		} else {
			return true;
		}
	}

	public static String removeLast(String str) {
		if (!isEmpty(str)) {
			return str.substring(0, str.length() - 1);
		} else {
			return str;
		}
	}

	public static String listToString(List<String> list) {
		if (!isListEmpty(list)) {
			String str = "";
			for (String string : list) {
				str = str + string + ",";
			}
			return removeLast(str);
		} else {
			return "";
		}
	}

	public static Object toBoolean(Object str) {
		if (str instanceof java.lang.String) {
			if ("true".equals(str) || "false".equals(str)) {
				return Boolean.valueOf((String) str);
			} else {
				return str;
			}
		} else {
			return str;
		}
	}

	@SuppressWarnings("unchecked")
	public static List<String> objToList(Object o) {
		if (o == null) {
			return null;
		}
		List<String> os = new ArrayList<String>();
		if (o instanceof java.lang.String) {
			os.add((String) o);
		} else {
			os = (List<String>) o;
		}
		return os;
	}
}
