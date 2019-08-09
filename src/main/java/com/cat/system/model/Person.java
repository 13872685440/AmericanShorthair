package com.cat.system.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Table(name = "Org_Person")
@BatchSize(size = 50)
@DynamicInsert
public class Person extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2360610594649791315L;

	

	
}
