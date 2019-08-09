package com.cat.system.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import com.cat.boot.model.BaseEntity;

@Entity
@Table(name = "SYS_USERS")
@Inheritance(strategy = InheritanceType.JOINED)
@BatchSize(size = 50)
@DynamicInsert
public class User extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8371365615204991104L;

	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Id
	@GeneratedValue(generator = "system-uuid")
	@Column(length = 40)
	private String id;

	/** 登陆名 */
	@Column(length = 50, nullable = false)
	@Length(min = 1, max = 50)
	private String loginName;

	/** 用户名 */
	@Column(length = 50)
	private String name;

	/** 密码 */
	@Column(length = 400)
	private String pwd;

	/** salt */
	@Column(length = 100)
	private String salt;

	/** 手机号码(用来接收短信) */
	@Column(length = 20)
	private String phone;

	/** 随机码 */
	@Column(length = 10)
	private String radom;

	/** 钉钉Id */
	@Column(length = 50)
	private String ddId;

	/**
	 * 微信用户ID
	 */
	@Column(length = 50)
	private String wechatId;

	/**
	 * 企业微信用户ID
	 */
	@Column(length = 50)
	private String qyWechatId;

	@Transient
	private String tmp111;

	@Transient
	private String plainPassword;

	@Transient
	private String plainPassword2;

	public User() {
	}

	public User(String loginName, String name) {
		this.name = name;
		this.loginName = loginName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return this.id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getPlainPassword() {
		return plainPassword;
	}

	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
	}

	public String getPlainPassword2() {
		return plainPassword2;
	}

	public void setPlainPassword2(String plainPassword2) {
		this.plainPassword2 = plainPassword2;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRadom() {
		return radom;
	}

	public void setRadom(String radom) {
		this.radom = radom;
	}

	public String getDdId() {
		return ddId;
	}

	public void setDdId(String ddId) {
		this.ddId = ddId;
	}

	public String getTmp111() {
		return tmp111;
	}

	public void setTmp111(String tmp111) {
		this.tmp111 = tmp111;
	}

	public String getWechatId() {
		return wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}

	public String getQyWechatId() {
		return qyWechatId;
	}

	public void setQyWechatId(String qyWechatId) {
		this.qyWechatId = qyWechatId;
	}

}
