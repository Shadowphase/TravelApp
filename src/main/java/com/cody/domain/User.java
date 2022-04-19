package com.cody.domain;

import java.util.HashSet;
import java.util.Set;

public class User {
	private Long id;
	private String name;
	private String password;
	private Set<Role> roles = new HashSet<>();

	public User() {}
	public User(String name, String password, Set<Role> roles) {
		super();
		this.name = name;
		this.password = password;
		this.roles = roles;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password=" + password + ", roles=" + roles + "]";
	}
}
