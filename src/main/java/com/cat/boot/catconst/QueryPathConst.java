package com.cat.boot.catconst;

public class QueryPathConst {

	public static final String reload = "/reload.query.xml";

	public static final String path = "/com/cat/@aa@/controller/@bb@.query.xml";

	public static final String updatepath = "/com/cat/@aa@/controller/@bb@.update.xml";

	public static final String namequerypath = "/com/cat/@aa@/controller/@bb@.namequery.xml";

	public static final String selectorpath = "/com/cat/@aa@/selector/@bb@.selector.xml";

	public static final String exportpath = "/com/cat/@aa@/excel/controller/@bb@.excel.xml";

	// public static final String frame =
	// "/com/cat/frame/controller/frame.query.xml";

	// public static final String sys = "/com/cat/sys/controller/sys.query.xml";

	public static String getPath(String name) {
		return path.replaceAll("@bb@", name).replaceAll("@aa@", name.replaceAll("\\.", "/"));
	}

	public static String getUpdatePath(String name) {
		return updatepath.replaceAll("@bb@", name).replaceAll("@aa@", name.replaceAll("\\.", "/"));
	}

	public static String getNamequeryPath(String name) {
		return namequerypath.replaceAll("@bb@", name).replaceAll("@aa@", name.replaceAll("\\.", "/"));
	}

	public static String getSelectorPath(String name) {
		return selectorpath.replaceAll("@bb@", name).replaceAll("@aa@", name.replaceAll("\\.", "/"));
	}

	public static String getExportPath(String name) {
		return exportpath.replaceAll("@bb@", name).replaceAll("@aa@", name.replaceAll("\\.", "/"));
	}
}
