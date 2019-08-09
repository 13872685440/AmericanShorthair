package com.cat.system.jsonbean;

import java.util.ArrayList;
import java.util.List;

import com.cat.boot.jsonbean.UserBean;

public class UserInfoBean extends UserBean {

	private String avatar;

	private String telephone;

	private List<RoleInfoBean> roles = new ArrayList<RoleInfoBean>();
	
	private List<PostInformationBean> posts = new ArrayList<PostInformationBean>();

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public List<RoleInfoBean> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleInfoBean> roles) {
		this.roles = roles;
	}

	public List<PostInformationBean> getPosts() {
		return posts;
	}

	public void setPosts(List<PostInformationBean> posts) {
		this.posts = posts;
	}

	public UserInfoBean() {

	}

	public UserInfoBean(UserBean bean) {
		super(bean);
	}
}
