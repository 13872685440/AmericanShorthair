package com.cat.system.model;

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
import com.cat.dictionary.model.Dictionary;

@Entity
@Table(name = "Org_Post_Information")
@BatchSize(size = 50)
@DynamicInsert
public class PostInformation extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8567384726887936704L;

	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Id
	@GeneratedValue(generator = "system-uuid")
	@Column(length = 40)
	private String id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "person_id", nullable = false)
	@BatchSize(size = 50)
	private Person person;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "organization_id", nullable = false)
	@BatchSize(size = 50)
	private Organization organization;

	/**
	 * 任职状态 在职 离职 兼职
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "isleaf", nullable = false)
	@BatchSize(size = 50)
	private Dictionary isleaf;

	/**
	 * 职务
	 */
	@Column(length = 30)
	private String duty;

	/**
	 * 入职时间
	 */
	@Column(length = 30)
	private String entrytime;

	/**
	 * 离职时间
	 */
	@Column(length = 30)
	private String leaftime;

	@Column
	private Integer xh = 1;
	
	@Column
	private Integer weighted;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@Override
	public String toString() {
		return this.id;
	}

	public Dictionary getIsleaf() {
		return isleaf;
	}

	public void setIsleaf(Dictionary isleaf) {
		this.isleaf = isleaf;
	}

	public String getEntrytime() {
		return entrytime;
	}

	public void setEntrytime(String entrytime) {
		this.entrytime = entrytime;
	}

	public String getLeaftime() {
		return leaftime;
	}

	public void setLeaftime(String leaftime) {
		this.leaftime = leaftime;
	}

	public Integer getXh() {
		return xh;
	}

	public void setXh(Integer xh) {
		this.xh = xh;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public Integer getWeighted() {
		return weighted;
	}

	public void setWeighted(Integer weighted) {
		this.weighted = weighted;
	}
}
