package cn.itcast.jx.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Role extends BaseEntity{
	private String id;
	//角色名
	private String name;
	//备注
	private String remark;
	private Integer orderNo;
	//角色与用户是多对多的关系
	private List<User> users = new ArrayList<User>();
	
	private List<Module> modules = new ArrayList<Module>();

    public List<Module> getModules() {
		return modules;
	}
    
	public void setModules(List<Module> modules) {
		this.modules = modules;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	
}
