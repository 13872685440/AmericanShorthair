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
@Table(name = "Test_Test4")
@BatchSize(size = 50)
@DynamicInsert
public class Test4 extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4541241326668522253L;

	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Id
	@GeneratedValue(generator = "system-uuid")
	@Column(length = 40)
	private String id;

	@Column(length = 40)
	private String simple;
	
	@Column(length = 40)
	private String simple2;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSimple() {
		return simple;
	}

	public void setSimple(String simple) {
		this.simple = simple;
	}

	public String getSimple2() {
		return simple2;
	}

	public void setSimple2(String simple2) {
		this.simple2 = simple2;
	}

	@Override
	public String toString() {
		return this.id;
	}

}
