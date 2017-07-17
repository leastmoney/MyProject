package cn.itcast.jx.action.sysadmin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import cn.itcast.jx.action.BaseAction;
import cn.itcast.jx.domain.Dept;
import cn.itcast.jx.domain.Role;
import cn.itcast.jx.domain.User;
import cn.itcast.jx.service.DeptService;
import cn.itcast.jx.service.RoleService;
import cn.itcast.jx.service.UserService;
import cn.itcast.jx.util.Page;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ModelDriven;

public class UserAction extends BaseAction implements ModelDriven<User>{
	private String deptId;

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	private User user = new User();
	
	private UserService userService;
	
	private RoleService roleService;
	
	private String roleIds;
	 
	 
	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}


	private List<User> managerList;
	
	public List<User> getManagerList() {
		return managerList;
	}

	public void setManagerList(List managerList) {
		this.managerList = managerList;
	}

	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	private DeptService deptService;
	
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	private Page page = new Page();

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}




	public String list(){
		System.out.println(which);
		super.put("cwhich","usersysadmin");
		
		String hql = "from User";
		userService.findPage(hql, page, User.class, null);
		page.setUrl("userAction_list?which="+which);
		super.push(page);
		return "list";
	}
	
	public String toview(){
		User viewUser = userService.get(User.class, user.getId());
		super.push(viewUser);
		return "toview";
	}

	public String tocreate(){
//		查找部门
		List<Dept> deptList = deptService.find( "from Dept where state = 1", Dept.class, null);
//  只有目前系统中的领导才可以出现在领导栏
		List<User> userList  = userService.find( "from User where state = 1 and userInfo.degree != 4",User.class,null);
		super.put("deptList", deptList);
		super.put("userList", userList);
		return "tocreate";
	}
	
	public String insert(){
		userService.saveOrUpdate(user);
		return SUCCESS;
	}
	
	public String toupdate(){
//		查询所有的部门
		List<Dept> deptList = deptService.find("from Dept where state = 1", Dept.class, null);
//		查询id的部门
		User viewUser = userService.get(User.class, user.getId());
		//放入值栈
		super.put("deptList", deptList);
		super.push(viewUser);
		return "toupdate";
	}
	
	public String update(){
		userService.saveOrUpdate(user);
		return SUCCESS;
	}
	
	public String deleteById(){
		userService.deleteById(User.class, user.getId());
		return SUCCESS;
	}
	
	public String delete(){
		String[] ids = user.getId().split(", ");
		userService.deleteById(User.class, ids);
		return SUCCESS;
	}
	
/*	public String ajaxManager(){
		managerList = userService.find(" from User  where state = 1 and dept.id=? and userInfo.degree != 4", User.class, new Object[]{user.getId()});
		System.out.println(managerList);
		super.push(managerList);
		return "ajaxManager";
	}*/
	
	public String torole(){
		/**
		 * 这个方法需要跳转到torole对应页面，这个页面是哪个？需要准备什么数据？ 
		 * 答：jUserRole.jsp
		 *   1 所有的角色
		 *   2 用户具有的角色
		 */
//		1 所有的角色
		List<Role> roleList = roleService.find("from Role", Role.class, null);
//		查找用户已经具有的角色
		User viewUser = userService.get(User.class, user.getId());
		
		List<Role> roles = viewUser.getRoles();
//		存放的是所有用户已经具有的角色的名字
		String userRoleStr = "";
		for (Role r : roles) {
			userRoleStr+=","+r.getName();
		}
		super.put("roleList", roleList);
		super.put("userRoleStr", userRoleStr);
//		super.put("roles",roles);
		super.push(viewUser);
		return "torole";
	}
	
	public String role() throws Exception {
		//获取当前的用户对象
		User viewUser = userService.get(User.class, user.getId());
		//分割RoleIds
		String[] ids = roleIds.split(", ");
		
		List<Role> set = new ArrayList<Role>();
		//根据roleId获取所有的角色，将角色放入set集合
		for(String id:ids){
			Role role = roleService.get(Role.class, id);
			set.add(role);
		}
		// 替换当前用户的角色集合 
		viewUser.setRoles(set);
		//更新
		userService.saveOrUpdate(viewUser);
		
		return SUCCESS;
	}

	 //直属领导
		public String ajaxManager() throws IOException {
			List<User> userList = userService.find("from User  where state = 1 and dept.id=? and userInfo.degree != 4", User.class, new Object[]{deptId});
			List<Map<String,String>> list = new ArrayList<Map<String,String>>();
			for (User user: userList ) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("id",user.getId());
				map.put("userInfoName",user.getUserInfo().getName());
				list.add(map);
			}
			//将list集合的信息装成json字符串
			String str = JSON.toJSONString(list);
			//5返回信息
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("application/json;charset=UTF-8");
			response.setHeader("cache-control", "no-cache");

			response.getWriter().write(str);

			return NONE;
		}
	
	public User getModel() {
		return user;
	}

}
