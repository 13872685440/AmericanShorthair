package com.cat.activiti.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;

import com.cat.boot.model.BaseEntity;

@Entity
@Table(name = "Task_Comment")
@BatchSize(size = 50)
@DynamicInsert
public class Comment extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 377245787851846960L;

	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Id
	@GeneratedValue(generator = "system-uuid")
	@Column(length = 40)
	private String id;

	/** 实体Bean类名 Entity Bean Class Name */
	@Column(nullable = false, length = 200)
	private String ebcn;

	/** 实体Bean关键域值 */
	@Column(nullable = false, length = 50)
	private String keyValue;

	/** 审核意见 */
	@Column(name = "shyj", length = 500)
	private String shyj;

	/** 审核时间（系统操作时间） */
	@Column(name = "shtime", length = 30)
	private String shtime;

	/** 审核时间（实际审核时间） */
	@Column(name = "shtime_bu", length = 30)
	private String shtime_bu;

	/** 审核用户 */
	@Column(nullable = true)
	private String shyh;

	/** 冗余字段 */
	@Column(nullable = true)
	private String shr;

	/** 审核机构名称 */
	@Column(nullable = true, name = "shjgname", length = 50)
	private String shjgName;

	/** 审核结论 */
	@Column(nullable = true, name = "shjl", length = 50)
	private String shjl;

	/** 审核节点 */
	@Column(name = "shjd", length = 30)
	private String shjd;

	/** 是否最终结论 */
	@Column(nullable = false, length = 5)
	private boolean zzjl = true;

	/** 是否流程开始 */
	@Column(nullable = true, length = 5)
	private Boolean isfrist = false;

	/** 步骤 */
	@Column(nullable = true)
	private Integer step;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getShyj() {
		return shyj;
	}

	public void setShyj(String shyj) {
		this.shyj = shyj;
	}

	public String getShtime() {
		return shtime;
	}

	public void setShtime(String shtime) {
		this.shtime = shtime;
	}

	public String getShtime_bu() {
		return shtime_bu;
	}

	public void setShtime_bu(String shtime_bu) {
		this.shtime_bu = shtime_bu;
	}

	public String getShr() {
		return shr;
	}

	public void setShr(String shr) {
		this.shr = shr;
	}

	public String getShjgName() {
		return shjgName;
	}

	public void setShjgName(String shjgName) {
		this.shjgName = shjgName;
	}

	public String getShjl() {
		return shjl;
	}

	public void setShjl(String shjl) {
		this.shjl = shjl;
	}

	public String getShjd() {
		return shjd;
	}

	public void setShjd(String shjd) {
		this.shjd = shjd;
	}

	public boolean isZzjl() {
		return zzjl;
	}

	public void setZzjl(boolean zzjl) {
		this.zzjl = zzjl;
	}

	public Boolean getIsfrist() {
		return isfrist;
	}

	public void setIsfrist(Boolean isfrist) {
		this.isfrist = isfrist;
	}

	public String getShyh() {
		return shyh;
	}

	public void setShyh(String shyh) {
		this.shyh = shyh;
	}

	public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	@Override
	public String toString() {
		return this.id;
	}

	public Comment() {
	}

	public Comment(String ebcn, String keyValue, String shyj, String shtime, String shtime_bu, String shyh, String shr,
			String shjgName, String shjl, String shjd, boolean zzjl, boolean isfrist, Integer step) {
		this.ebcn = ebcn;
		this.keyValue = keyValue;
		this.shyj = shyj;
		this.shtime_bu = shtime_bu;
		this.shtime = shtime;
		this.shyh = shyh;
		this.shr = shr;
		this.shjgName = shjgName;
		this.shjl = shjl;
		this.shjd = shjd;
		this.zzjl = zzjl;
		this.isfrist = isfrist;
		this.step = step;
	}
}
