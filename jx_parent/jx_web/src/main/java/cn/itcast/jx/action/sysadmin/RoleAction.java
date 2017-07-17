package cn.itcast.jx.action.sysadmin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import cn.itcast.jx.action.BaseAction;
import cn.itcast.jx.domain.Module;
import cn.itcast.jx.domain.Role;
import cn.itcast.jx.service.ModuleService;
import cn.itcast.jx.service.RoleService;
import cn.itcast.jx.util.Page;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ModelDriven;

public class RoleAction extends BaseAction implements ModelDriven<Role>{
	private Role role = new Role();
	
	private ModuleService moduleService;
	
	private String moduleIds;
	
	
	public void setModuleIds(String moduleIds) {
		this.moduleIds = moduleIds;
	}

	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

	public Role getModel() {
		return role;
	}

	private RoleService roleService;
	
	
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	private Page page = new Page();

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	
	List moduleSet = new ArrayList();
	
	public String list(){
		System.out.println(which);
		super.put("cwhich","rolesysadmin");
		
		roleService.findPage("from Role", page, Role.class, null);
		page.setUrl("roleAction_list?which="+which);
		super.push(page);
		return "list";
	}
	
	public String toview(){
		Role viewRole = roleService.get(Role.class, role.getId());
		super.push(viewRole);
		return "toview";
	}
	
	public String tocreate(){
		return "tocreate";
	}
	
	public String insert(){
		roleService.saveOrUpdate(role);
		return SUCCESS;
	}
	
	public String toupdate(){
		Role obj = roleService.get(Role.class, role.getId());
		super.push(obj);
		return "toupdate";
	}
	public String update() throws Exception {
		//1.根据id,得到要更新的对象
		Role obj = roleService.get(Role.class, role.getId());
		//2.页面修改的属性，就要更新值
		obj.setName(role.getName());
		obj.setRemark(role.getRemark());
		obj.setUpdateTime(new Date());
		//3.更新
		roleService.saveOrUpdate(obj);
		return SUCCESS;
	}

	public String deleteById(){
		roleService.deleteById(Role.class, role.getId());
		return SUCCESS;
	}
	public String delete(){
		String[] ids = role.getId().split(", ");
		roleService.deleteById(Role.class, ids);
		return SUCCESS;
	}
	
	public String tomodule(){
		//1 根据角色id获取觉得对象
		Role viewRole = roleService.get(Role.class, role.getId());
//		放入值栈
		super.push(viewRole);
		return "tomodule";
	}
	
	/**
	 * 准备树的数据,ajax请求
	 * 
	 * [
			{ id:11, pId:1, name:"随意勾选 1-1", open:true},
			{ id:2, pId:0, name:"随意勾选 2", checked:true, open:true}
			]
			
			什么时候加open？parent==null的时候
			什么时候加checked？已经拥有的角色
			
			
		有哪些方式可以拼接json字符串？
		1 struts-json-plugin
		2 fastJson-alibaba（全球最快的json转换器）	
		3 gogle：Gson
		4   手动拼接
			
	 * @throws Exception
	 */
	
	public String roleModuleJsonStr() throws Exception {
		//1 获取所有的模块
		List<Module> moduleList = moduleService.find("from Module where state = 1", Module.class	, null);
		//2 获取角色信息
		Role viewRole = roleService.get(Role.class, role.getId());
		//3  获取当前角色拥有的模块
		List<Module> modules = viewRole.getModules();
		/*//4 拼接json格式字符串
		String str="";
		str+="[";
		int size = moduleList.size();
		for(Module m:moduleList){
			size--;
			str+="{";
			//数据必须要用引号引起来，否则直接出错
			str+="id:'"+m.getId()+"',";
			//str+="pId:"+m.getParent().getId()+",";//有问题的写法，问题是：有些模块是没有父模块的
			str+="pId:'"+(m.getParentId()==null?null:m.getParentId())+"',";
			
			str+="name:'"+m.getName()+"',";
			//默认不勾选
			boolean flag =false;
			if(modules.contains(m)){
				flag=true;
			}
			str+="checked:'"+flag+"'";
			
			str+="}";
			if(size>0){
				str+=",";
			}
		}
		
		str+="]";*/
		//firstjson
		ArrayList<ZtreeData> list = new ArrayList<ZtreeData>();
		for (Module m : moduleList) {
			String falg = "false";
			if(modules.contains(m)){
				falg = "true";
			}
			String pid = m.getParentId()==null?"null":m.getParentId();
			list.add(new ZtreeData(m.getId(),pid,m.getName(),falg));
		}
		String str = JSON.toJSONString(list);
		//使用reponse向前台写数据
		HttpServletResponse response = ServletActionContext.getResponse();
		//设置字符集编码和数据格式
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("cache-control", "no-cache");
		
		//写
		response.getWriter().write(str);
		
		return NONE;
	}
	/**
	 * 实现模块的分配
	 * @return
	 * @throws Exception
	 * 
		封装参数：
		   model.id--角色编号------------<input type="hidden" name="id" value="${id}"/>
		   private String moduleIds;模块编号-------------------<input type="hidden" id="moduleIds" name="moduleIds" value="" />
	 */
public String module(){
	//1.根据角色编号，得到角色对象
	Role viewRole = roleService.get(Role.class, role.getId());
//	2.将接收的模块编号进行切割，得到模块数组
	String []ids = moduleIds.split(",");
	//3.遍历模块的数组，加载出每个模块,并放入set集合中
	for (String id : ids) {
		Module module = moduleService.get(Module.class, id);
		moduleSet.add(module);
	}
	//4.设置角色与模块的关系
	viewRole.setModules(moduleSet);
	
	//5.保存角色与模块的关系
	roleService.saveOrUpdate(viewRole);

	return SUCCESS;
}

}
