package com.cat.system.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import com.cat.boot.model.BaseEntity;

@Entity
@Table(name = "SYS_AppTree")
@BatchSize(size = 50)
@DynamicInsert
public class AppTree extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2670124752028136041L;

	/** 描述 */
	@Column(length = 50)
	@Length(max = 50)
	private String des;

	/** 路径 */
	@Column(length = 500)
	@Length(max = 500)
	private String path;

	/** 排序 */
	@Column
	private Integer xh = 10;

	/** 编码 作为ID使用 */
	@Id
	@GeneratedValue(generator = "assignedGenerator")
	@GenericGenerator(name = "assignedGenerator", strategy = "assigned")
	@Column(length = 40)
	private String code;

	/** 本级代码 current Level Code */
	@Column(length = 8, nullable = false)
	private String clc;

	/** 完整的名称 wholeName */
	@Column(length = 600, nullable = false)
	private String wn;

	/** 名称 */
	@Column(length = 500, nullable = false)
	private String name;

	/** 上级 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "scId", nullable = true)
	@BatchSize(size = 50)
	private AppTree superior;

	/** 末级 */
	@Column(nullable = false)
	private boolean isLeaf = true;
	
	/** 组件 */
	@Column(length = 100)
	private String component;
	
	/** 图标 */
	@Column(length = 20)
	private String icon;

	@Lob
	@Column
	@Basic(fetch = FetchType.LAZY)
	private String titleImage;

	/** 图标背景颜色(app) */
	@Column(length = 10)
	private String colorCode;
	
	/** 图标字体代码(app)*/
	@Column
	private Long fontCode;
	
	/** 图标库(app)*/
	@Column(length = 30)
	private String fontFamily; 

	@Transient
	private String id;

	@Column
	private Long lxh;

	public Long getLxh() {
		return lxh;
	}

	public void setLxh(Long lxh) {
		this.lxh = lxh;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public AppTree getSuperior() {
		return superior;
	}

	public void setSuperior(AppTree superior) {
		this.superior = superior;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
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

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getXh() {
		return xh;
	}

	public void setXh(Integer xh) {
		this.xh = xh;
	}

	public String getTitleImage() {
		return titleImage;
	}

	public void setTitleImage(String titleImage) {
		this.titleImage = titleImage;
	}

	public String getColorCode() {
		return colorCode;
	}

	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Long getFontCode() {
		return fontCode;
	}

	public void setFontCode(Long fontCode) {
		this.fontCode = fontCode;
	}

	public String getFontFamily() {
		return fontFamily;
	}

	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
	}
}
