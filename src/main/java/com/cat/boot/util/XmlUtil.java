package com.cat.boot.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XmlUtil {

	public static Map<Object, Object> parserXml(String fileName) {
		try {
			// String path = XmlUtil.class.getClassLoader().getResource(fileName) != null
			// ? XmlUtil.class.getClassLoader().getResource(fileName).getPath()
			// : XmlUtil.class.getClassLoader().getResource("").getPath() + fileName;
			// String path = XmlUtil.class.getResource("/").getPath() + fileName;
			InputStream resource = XmlUtil.class.getResourceAsStream(fileName);
			// File f = new File(resource);
			SAXReader reader = new SAXReader();
			Document doc = reader.read(resource);
			Element root = doc.getRootElement();
			Map<Object, Object> maps = new HashMap<Object, Object>();
			getNodes(root, maps);
			return maps;
		} catch (Exception e) {
			e.printStackTrace();
			return new HashMap<Object, Object>();
		}
	}

	private static void getNodes(Element node, Map<Object, Object> maps) {
		String key = node.getName();
		if (!StringUtil.isEmpty(node.attributeValue("id"))) {
			key = key + node.attributeValue("id");
		}
		maps.put(key, new HashMap<Object, Object>());

		// List<Attribute> listAttr = node.attributes();// 当前节点的所有属性的list
		// for (Attribute attr : listAttr) {// 遍历当前节点的所有属性
		// String name = attr.getName();// 属性名称
		// String value = attr.getValue();// 属性的值
		// ((Map<Object, Object>) maps.get(key)).put(name, value);
		// }

		// 递归遍历当前节点所有的子节点
		List<Element> listElement = node.elements();// 所有一级子节点的list

		for (Element e : listElement) {// 遍历所有一级子节点
			List<Element> listElement2 = e.elements();
			if (StringUtil.isListEmpty(listElement2)) {
				((Map<Object, Object>) maps.get(key)).put(e.getName(), e.getData());
			} else {
				getNodes(e, (Map<Object, Object>) maps.get(key));
			} // 递归
		}
	}
}
