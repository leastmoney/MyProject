package cn.itcast.jx.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

public class User extends BaseEntity{
	private String id;//主键
	private Dept dept;//用户与部门
	private UserInfo userInfo;//用户与拓展的信息;一对一
	private String userName;//用户名
	private String password;//密码
	private Integer state;//状态
	private List<Role> roles = new ArrayList<Role>();
	@JSON(serialize=false)
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public User(String id, String userName) {
		super();
		this.id = id;
		this.userName = userName;
	}
	
	public Dept getDept() {
		return dept;
	}
	public void setDept(Dept dept) {
		this.dept = dept;
	}
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public User(){
		
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName
				+ ", password=" + password + ", state=" + state + "]";
	}
	
}
