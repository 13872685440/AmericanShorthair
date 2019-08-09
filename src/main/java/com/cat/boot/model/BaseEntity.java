package com.cat.boot.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6383197430925268118L;

	/** 版本 */
	@Version
	@Column(name = "BE_ver")
	private Integer version = 0;

	/** 创建时间 */
	@Column(name = "BE_ct", nullable = false, unique = false, length = 40)
	private String ct;

	/** 创建者编码 */
	@Column(name = "BE_crtUid", length = 40, nullable = false, unique = false)
	private String crtUid = "System";

	/** 创建者名称 */
	@Column(name = "BE_crtUname", length = 40, unique = false)
	private String crtUname = "System";

	/** 最后一次修改时间 */
	@Column(name = "BE_lmt", nullable = true, unique = false, length = 40)
	private String lmt;

	/** 最后一次修改者编码 */
	@Column(name = "BE_lmUid", length = 40, nullable = true, unique = false)
	private String lmUid;

	/** 最后一次修改者名称 */
	@Column(name = "BE_lmUname", length = 40, nullable = true, unique = false)
	private String lmUname;

	/** 作废前状态 */
	@Column(name = "invalidzt", length = 40, nullable = true, unique = false)
	private String invalidzt;

	/** 数据状态(1=正常，0=刪除,2=锁定,99=作废) */
	@Column(name = "dictionary_data", length = 40, nullable = true, unique = false)
	private String dictionaryData = "1";

	/** 随机标签 每一次操作均会导致随机标签发生变化*/
	@Column(name = "be_token", length = 40)
	private String token;

	/** 临时ID */
	@Transient
	private String tmpId = UUID.randomUUID().toString();

	@Transient
	private String ly;

	public abstract void setId(String id);

	public abstract String toString();

	public abstract String getId();

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getVersion() {
		return version;
	}

	public String getCrtUid() {
		return crtUid;
	}

	public void setCrtUid(String crtUid) {
		this.crtUid = crtUid;
	}

	public String getCt() {
		return ct;
	}

	public String getFormatCt() {
		return ct.substring(0, 10);
	}

	public void setCt(String ct) {
		this.ct = ct;
	}

	public String getLmt() {
		return lmt;
	}

	public void setLmt(String lmt) {
		this.lmt = lmt;
	}

	public String getLmUid() {
		return lmUid;
	}

	public void setLmUid(String lmUid) {
		this.lmUid = lmUid;
	}

	public String getDictionaryData() {
		return dictionaryData;
	}

	public void setDictionaryData(String dictionaryData) {
		this.dictionaryData = dictionaryData;
	}

	public String getTmpId() {
		return tmpId;
	}

	public void setTmpId(String tmpId) {
		this.tmpId = tmpId;
	}

	public void clone(BaseEntity be) {
		this.crtUid = be.getCrtUid();
		this.ct = be.getCt();
		this.dictionaryData = be.getDictionaryData();
		this.lmt = be.getLmt();
		this.lmUid = be.getLmUid();
	}

	public String getCrtUname() {
		return crtUname;
	}

	public void setCrtUname(String crtUname) {
		this.crtUname = crtUname;
	}

	public String getLmUname() {
		return lmUname;
	}

	public void setLmUname(String lmUname) {
		this.lmUname = lmUname;
	}

	public String getInvalidzt() {
		return invalidzt;
	}

	public void setInvalidzt(String invalidzt) {
		this.invalidzt = invalidzt;
	}

	public String getLy() {
		return ly;
	}

	public void setLy(String ly) {
		this.ly = ly;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
