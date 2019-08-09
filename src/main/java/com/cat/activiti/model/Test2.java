package com.cat.activiti.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Test_Test2")
@BatchSize(size = 50)
@DynamicInsert
public class Test2 extends ActivitiEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3795831034233635767L;

	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Id
	@GeneratedValue(generator = "system-uuid")
	@Column(length = 40)
	private String id;

	@Column(length = 40)
	private String simple;


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

	@Override
	public String toString() {
		return this.id;
	}

	public Test2() {
		setProcessDefinition("test");
	}

}
