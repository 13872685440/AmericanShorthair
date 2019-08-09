package com.cat.file.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.GenericGenerator;

import com.cat.boot.model.BaseEntity;

@Entity
@Table(name = "File_Yewublzlwd")
@BatchSize(size = 50)
public class YeWuBLZLWD extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5229626958294466507L;

	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Id
	@GeneratedValue(generator = "system-uuid")
	@Column(length = 40)
	private String id;

	/** 上传标记 用于进行简易上传（不使用YeWuBanLiZlLx）时 同一个ebcn和keyvalue上传时进行标记位置 */
	/** 使用A1-A99 B1-B99的标记方式 A表示文档存储在服务器上 B表示文档存储在FTP上 */
	@Column(name = "a_sign", length = 200)
	private String sign;

	/** 上传文档 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fileId", nullable = false)
	@BatchSize(size = 50)
	private FileInfo fileinfo;

	/** 序号 */
	@Column
	private long xh = 10;

	/** 实体Bean类名 Entity Bean Class Name */
	@Column(nullable = false, length = 200)
	private String ebcn;

	/** 实体Bean关键域值 */
	@Column(nullable = false, length = 80)
	private String keyValue;

	/** 用来记录上传时的毫秒值 用来排序 */
	@Column(nullable = true, length = 20)
	private String timeInMillis;

	/** 记录是否当前插入 1为当前插入 */
	@Column(nullable = true, length = 20)
	private String nowinsert;

	/** 动态关联文档 dynamics Related 不保存 */
	@Transient
	private boolean dynaRelated = false;

	@Override
	public String toString() {
		return "YeWuBLZLWD [id=" + id + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public FileInfo getFileinfo() {
		return fileinfo;
	}

	public void setFileinfo(FileInfo fileinfo) {
		this.fileinfo = fileinfo;
	}

	public long getXh() {
		return xh;
	}

	public void setXh(long xh) {
		this.xh = xh;
	}

	public boolean isDynaRelated() {
		return dynaRelated;
	}

	public void setDynaRelated(boolean dynaRelated) {
		this.dynaRelated = dynaRelated;
	}

	public String getEbcn() {
		return ebcn;
	}

	public void setEbcn(String ebcn) {
		this.ebcn = ebcn;
	}

	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	public String getTimeInMillis() {
		return timeInMillis;
	}

	public void setTimeInMillis(String timeInMillis) {
		this.timeInMillis = timeInMillis;
	}

	public String getNowinsert() {
		return nowinsert;
	}

	public void setNowinsert(String nowinsert) {
		this.nowinsert = nowinsert;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
}
