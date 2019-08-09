package com.cat.file.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;

import com.cat.boot.model.BaseEntity;

@Entity
@Table(name = "File_Yewubanlilx")
@BatchSize(size = 50)
@DynamicInsert
public class YeWuBanLiLx extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3587827189714272442L;

	@Id
	@GeneratedValue(generator = "assignedGenerator")
	@GenericGenerator(name = "assignedGenerator", strategy = "assigned")
	@Column(length = 40)
	private String code;

	/** 是否FTP上传 */
	@Column(name = "isftp", nullable = true)
	private Boolean isftp = false;

	/** 业务办理 说明 指南 */
	@Lob
	@Column
	@Basic(fetch = FetchType.LAZY)
	private String shuoMin;


	/** 资料类型 */
	@OneToMany(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY, mappedBy = "ywbl")
	@OrderBy(value = "code asc")
	@BatchSize(size = 50)
	private List<YeWuBanLiZlLx> zls = new ArrayList<YeWuBanLiZlLx>();

	/** 本级代码 current Level Code */
	@Column(length = 8, nullable = false)
	private String clc;

	/** 完整的名称 wholeName */
	@Column(length = 250, nullable = false)
	private String wn;

	/** 名称 */
	@Column(length = 50, nullable = false)
	private String name;

	/** 上级 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "scId", nullable = true)
	@BatchSize(size = 50)
	private YeWuBanLiLx superior;

	/** 简称 ShortName */
	@Column(length = 50, nullable = true)
	private String sn;

	/** 末级 */
	@Column(nullable = false)
	private boolean isLeaf = true;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getShuoMin() {
		return shuoMin;
	}

	public void setShuoMin(String shuoMin) {
		this.shuoMin = shuoMin;
	}

	public List<YeWuBanLiZlLx> getZls() {
		return zls;
	}

	public void setZls(List<YeWuBanLiZlLx> zls) {
		this.zls = zls;
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

	public Boolean getIsftp() {
		return isftp;
	}

	public void setIsftp(Boolean isftp) {
		this.isftp = isftp;
	}

	public String getClc() {
		return clc;
	}

	public void setClc(String clc) {
		this.clc = clc;
	}

	public String getWn() {
		return wn;
	}

	public void setWn(String wn) {
		this.wn = wn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public YeWuBanLiLx getSuperior() {
		return superior;
	}

	public void setSuperior(YeWuBanLiLx superior) {
		this.superior = superior;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

}
