package com.cat.system.jsonbean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cat.boot.jsonbean.BaseAppBean;
import com.cat.boot.model.BaseEntity;
import com.cat.boot.service.BaseService;
import com.cat.boot.util.PassWordUtil;
import com.cat.boot.util.StringUtil;
import com.cat.dictionary.model.Dictionary;
import com.cat.system.model.Organization;
import com.cat.system.model.Person;
import com.cat.system.model.PostInformation;

public class PostInformationBean extends BaseAppBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8191109316056145682L;

	private String person_id;

	private String person_name;

	private String organ_id;

	private String organ_name;

	private String leaf_code;

	private String leaf_data;

	private String phone;

	private String duty;

	private Integer xh;
	
	private List<String> roles = new ArrayList<String>();
	
	private List<String> permissions = new ArrayList<String>();

	public String getPerson_id() {
		return person_id;
	}

	public void setPerson_id(String person_id) {
		this.person_id = person_id;
	}

	public String getPerson_name() {
		return person_name;
	}

	public void setPerson_name(String person_name) {
		this.person_name = person_name;
	}

	public String getOrgan_id() {
		return organ_id;
	}

	public void setOrgan_id(String organ_id) {
		this.organ_id = organ_id;
	}

	public String getOrgan_name() {
		return organ_name;
	}

	public void setOrgan_name(String organ_name) {
		this.organ_name = organ_name;
	}

	public String getLeaf_code() {
		return leaf_code;
	}

	public void setLeaf_code(String leaf_code) {
		this.leaf_code = leaf_code;
	}

	public String getLeaf_data() {
		return leaf_data;
	}

	public void setLeaf_data(String leaf_data) {
		this.leaf_data = leaf_data;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public List<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}

	public PostInformationBean() {

	}

	public PostInformationBean(BaseEntity entity) {
		super(entity);
	}

	public static PostInformationBean setThis(PostInformation entity) {
		PostInformationBean bean = new PostInformationBean(entity);
		bean.setId(entity.getId());
		bean.setXh(entity.getXh());
		bean.setPerson_id(entity.getPerson() != null ? entity.getPerson().getId() : "");
		bean.setPerson_name(entity.getPerson() != null ? entity.getPerson().getName() : "");
		bean.setOrgan_id(entity.getOrganization() != null ? entity.getOrganization().getId() : "");
		bean.setOrgan_name(entity.getOrganization() != null ? entity.getOrganization().getName() : "");
		bean.setLeaf_code(entity.getIsleaf() != null ? entity.getIsleaf().getCode() : "00010001");
		bean.setLeaf_data(entity.getIsleaf() != null ? entity.getIsleaf().getName() : "");
		bean.setPhone(entity.getPerson() != null ? entity.getPerson().getPhone() : "");
		bean.setDuty(entity.getDuty());
		return bean;
	}

	public static List<PostInformationBean> setThis(List<PostInformation> entitys) {
		List<PostInformationBean> beans = new ArrayList<PostInformationBean>();
		if (!StringUtil.isListEmpty(entitys)) {
			for (PostInformation bean : entitys) {
				beans.add(setThis(bean));
			}
		}
		return beans;
	}

	public static void clone(PostInformation bean, PostInformationBean entity, BaseService baseService) {
		Organization organ = (Organization) baseService.findById(Organization.class, entity.getOrgan_id());
		bean.setXh(entity.getXh());
		bean.setWeighted(organ.getWeighted() * 10);
		bean.setDuty(entity.getDuty());
		bean.setIsleaf((Dictionary) baseService.findById(Dictionary.class, entity.getLeaf_code()));
		bean.setOrganization(organ);
		bean.setPerson(savePerson(entity, baseService));
	}

	static Person savePerson(PostInformationBean entity, BaseService baseService) {
		if (StringUtil.isEmpty(entity.getPerson_id())) {
			Person p = new Person();
			p.setPhone(entity.getPhone());
			p.setLoginName(entity.getPhone());
			p.setName(entity.getPerson_name());
			Map<String, String> pwds = PassWordUtil.entryptPassword("1", true);
			p.setPwd(pwds.get("pwd"));
			p.setSalt(pwds.get("salt"));
			baseService.save(p, false);
			return p;
		} else {
			Person p = (Person) baseService.findById(Person.class, entity.getPerson_id());
			p.setLoginName(entity.getPhone());
			p.setPhone(entity.getPhone());
			p.setName(entity.getPerson_name());
			baseService.save(p, true);
			return p;
		}
	}

}
