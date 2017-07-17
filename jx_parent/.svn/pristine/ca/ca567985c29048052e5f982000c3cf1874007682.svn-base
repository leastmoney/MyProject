package cn.itcast.jx.domain;

import java.util.HashSet;
import java.util.Set;

public class Dept {
	private String id; //编号
	private String deptName;//部门名称
	
	private Dept parent;//父部门  自关联
	
	private Integer state;//状态
	
	 private Set<User> users = new HashSet<User>();
	
    //getter和setter方法

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Dept getParent() {
		return parent;
	}

	public void setParent(Dept parent) {
		this.parent = parent;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}
