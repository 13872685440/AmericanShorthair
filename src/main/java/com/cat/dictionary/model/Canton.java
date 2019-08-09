package com.cat.dictionary.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import com.cat.dictionary.enumable.CantonLevel;

@Entity
@Table(name = "Data_Canton")
@BatchSize(size = 50)
@DynamicInsert
public class Canton extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6273755550972471849L;

	/** 采用国家标准编码 由于县以下行政区划变动较大 只根据需求进行导入*/
	@Id
	@GeneratedValue(generator = "assignedGenerator")
	@GenericGenerator(name = "assignedGenerator", strategy = "assigned")
	@Column(length = 40)
	private String code;
	
	/** 统计局编码 */
	@Column(length = 40)
	private String statsCode;
	
	/** 行政机构级别 */
	@Enumerated(EnumType.STRING)
	@Column(name = "c_level")
	private CantonLevel level;
	
	/** 拼音 */
	@Column(length = 50, nullable = true)
	private String spell;
	
	/** 完整的名称 wholeName */
	@Column(length = 250, nullable = true)
	private String wn;

	/** 名称 */
	@Column(length = 50, nullable = false)
	private String name;

	/** 上级 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "scId", nullable = true)
	@BatchSize(size = 50)
	private Canton superior;

	/** 简称 ShortName */
	@Column(length = 50, nullable = true)
	private String sn;

	/** 末级 */
	@Column(nullable = false)
	private boolean isLeaf = true;
	
	/** 扩展字段*/
	@Column(length = 50)
	private String extendField;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String getId() {
		return getCode();
	}

	@Override
	public void setId(String id) {
		setCode(id);
	}

	@Override
	public String toString() {
		return this.code;
	}

	public String getStatsCode() {
		return statsCode;
	}

	public void setStatsCode(String statsCode) {
		this.statsCode = statsCode;
	}

	public CantonLevel getLevel() {
		return level;
	}

	public void setLevel(CantonLevel level) {
		this.level = level;
	}

	public String getSpell() {
		return spell;
	}

	public void setSpell(String spell) {
		this.spell = spell;
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

	public Canton getSuperior() {
		return superior;
	}

	public void setSuperior(Canton superior) {
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

	public String getExtendField() {
		return extendField;
	}

	public void setExtendField(String extendField) {
		this.extendField = extendField;
	}
}
