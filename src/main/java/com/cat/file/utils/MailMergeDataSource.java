package com.cat.file.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.aspose.words.IMailMergeDataSource;

public class MailMergeDataSource implements IMailMergeDataSource {

	private List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

	private String tableName = null;

	private int index = -1;

	public MailMergeDataSource() {
	}

	public MailMergeDataSource(List<Map<String, Object>> dataList,
			String tableName) {
		this.dataList = dataList;
		this.tableName = tableName;
	}

	/**
	 * @param data
	 *            单个数据集
	 * @param tableName
	 *            与模板中的Name对应
	 */
	public MailMergeDataSource(Map<String, Object> data, String tableName) {
		if (this.dataList == null) {
			this.dataList = new ArrayList<Map<String, Object>>();
			this.dataList.add(data);
		}
		this.tableName = tableName;
	}

	@Override
	public IMailMergeDataSource getChildDataSource(String arg0)
			throws Exception {
		return null;
	}

	@Override
	public String getTableName() throws Exception {
		return this.tableName;
	}

	@Override
	public boolean getValue(String key, Object[] arg1) throws Exception {
		if (index < 0 || index >= this.getCount()) {
			return false;
		}
		if (arg1 != null && arg1.length > 0) {
			arg1[0] = this.dataList.get(index).get(key);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean moveNext() throws Exception {
		index += 1;
		if (index >= this.getCount()) {
			return false;
		}
		return true;
	}

	private int getCount() {
		return this.dataList.size();
	}

}
