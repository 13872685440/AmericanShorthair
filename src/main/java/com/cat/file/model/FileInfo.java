package com.cat.file.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;

import com.cat.file.utils.FileUtils;
import com.cat.boot.model.BaseEntity;
import com.cat.boot.util.StringUtil;

@Entity
@Table(name = "File_Fileinfo")
@BatchSize(size = 50)
@DynamicInsert
public class FileInfo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5649126361924095205L;

	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Id
	@GeneratedValue(generator = "system-uuid")
	@Column(length = 40)
	private String id;

	@Column(name = "docSize", nullable = true)
	private double size;

	/**
	 * 文档URL 文档保存在服务器上的随机路径 或url 文件在服务器上保存文件为 uploadRootPath+urlName+\\+originalName
	 * 
	 */
	@Column(nullable = false, length = 400)
	private String urlName;

	/** 原始文件名 */
	@Column(nullable = true, length = 200)
	private String originalName;

	/** 缩略图 缩略图文件名可以通过原始文件名来获取 文件名+_+缩略比例 */
	@Column(nullable = true, length = 200)
	private String scaleName;

	/** 文件后缀 */
	@Column(nullable = true, length = 50)
	private String fileExt;

	/** 文件相对路径 非持久化 */
	@Transient
	private String fileDir;

	/** 文件随机文件名 非持久化 */
	@Transient
	private String randomFilename;

	public boolean isJpgImage() {
		if (!StringUtil.isEmpty(originalName)) {
			fileExt = FileUtils.getTypePart(originalName);
			if (fileExt.toLowerCase().matches("jpg")) {
				return true;
			}
		}
		return false;
	}

	public boolean isDOC() {
		if (!StringUtil.isEmpty(originalName)) {
			fileExt = FileUtils.getTypePart(originalName);
			if (fileExt.toLowerCase().matches("doc")) {
				return true;
			}
		}
		return false;
	}

	public String getFileDir() {
		return fileDir;
	}

	public String getFileExt() {
		return fileExt;
	}

	public String getId() {
		return id;
	}

	public String getOriginalName() {
		return originalName;
	}

	public String getRandomFilename() {
		return randomFilename;
	}

	public String getUrlName() {
		return urlName;
	}

	public void setFileDir(String fileDir) {
		this.fileDir = fileDir;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
		this.fileExt = FileUtils.getTypePart(originalName).toLowerCase();
	}

	public void setRandomFilename(String randomFilename) {
		this.randomFilename = randomFilename;
	}

	public void setUrlName(String urlName) {
		this.urlName = urlName;
	}

	@Override
	public String toString() {
		return this.id;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public String getScaleName() {
		return scaleName;
	}

	public void setScaleName(String scaleName) {
		this.scaleName = scaleName;
	}
}
