package com.cat.file.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;

import com.cat.boot.model.BaseEntity;

@Entity
@Table(name = "File_Yewubanlizllx")
@BatchSize(size = 50)
@DynamicInsert
public class YeWuBanLiZlLx extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4232005221277889756L;

	@Id
	@GeneratedValue(generator = "assignedGenerator")
	@GenericGenerator(name = "assignedGenerator", strategy = "assigned")
	@Column(length = 40)
	private String code;

	/** 本级代码 */
	@Column(length = 10, nullable = false)
	private String clc;

	/** 资料描述 */
	@Column(length = 200)
	private String zlms;

	/** 名称 */
	@Column(length = 200, nullable = false)
	private String name;

	/** 排序 */
	private Integer xh = 1;

	/** 存储路径(存储路径使用相对路径 以便存储盘符发生变化时维护) */
	@Column(length = 200)
	private String path;

	/** 业务办理 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ywbl_id", nullable = false)
	@BatchSize(size = 50)
	private YeWuBanLiLx ywbl;

	/** 操作 用,隔开 */
	/** 如值为上传表示允许上传 如值为上传,下载表示允许上传和下载 */
	@Column(length = 50)
	private String operate;

	/** 下载路径(下载路径使用相对路径 以便存储盘符发生变化时维护) */
	@Column(length = 50)
	private String downloadPath;

	/** 打印输出service */
	@Column(length = 20)
	private String print_service;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getZlms() {
		return zlms;
	}

	public void setZlms(String zlms) {
		this.zlms = zlms;
	}

	public Integer getXh() {
		return xh;
	}

	public void setXh(Integer xh) {
		this.xh = xh;
	}

	public YeWuBanLiLx getYwbl() {
		return ywbl;
	}

	public void setYwbl(YeWuBanLiLx ywbl) {
		this.ywbl = ywbl;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public String getDownloadPath() {
		return downloadPath;
	}

	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}

	public String getClc() {
		return clc;
	}

	public void setClc(String clc) {
		this.clc = clc;
	}

	@Override
	public void setId(String id) {
		this.code = id;
	}

	@Override
	public String toString() {
		return this.code;
	}

	@Override
	public String getId() {
		return this.code;
	}

	public String getPrint_service() {
		return print_service;
	}

	public void setPrint_service(String print_service) {
		this.print_service = print_service;
	}
}
