package cn.itcast.jx.action.sysadmin;

import cn.itcast.jx.action.BaseAction;
import cn.itcast.jx.domain.Module;
import cn.itcast.jx.service.ModuleService;
import cn.itcast.jx.service.UserService;
import cn.itcast.jx.util.Page;

import com.opensymphony.xwork2.ModelDriven;

public class ModuleAction extends BaseAction implements ModelDriven<Module>{
	//模型
	private Module model = new Module();
	private ModuleService moduleService;
	
	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}
	
	private Page page = new Page();

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Module getModel() {
		return model;
	}
	
	private UserService userService;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String list() throws Exception {
	/*	Subject subject = SecurityUtils.getSubject();
		User loginUser = (User) subject.getPrincipal();
		User user = userService.get(User.class,loginUser.getId());
		Set<Role> rolesList = user.getRoles();
		super.put("rolesList",rolesList);*/
		System.out.println(which);
		super.put("cwhich",which);
		
		String hql = "from Module";
		//查找数据
		moduleService.findPage(hql, page,Module.class, null);
		//设置URL跳转路径
		page.setUrl("moduleAction_list?which="+which);		
		//将数据放入值栈
		super.push(page);
		
		return "list";
	}
	public String toview() throws Exception {
//		/查找用户
		Module module = moduleService.get(Module.class, model.getId());
		//将用户信息放入值栈
		super.push(module);
		
		return "toview";
	}
	public String tocreate() throws Exception {
		return "tocreate";
	}
	
	public String insert() throws Exception {
		moduleService.saveOrUpdate(model);
		return SUCCESS;
	}
	public String toupdate() throws Exception {
		//1.加载当前要更新的对象
		Module obj = moduleService.get(Module.class, model.getId());
		super.push(obj);
		return "toupdate";
	}
	public String update() throws Exception {
		//1.根据id,得到要更新的对象
		Module obj = moduleService.get(Module.class, model.getId());
		//2.页面修改的属性，就要更新值
		obj.setName(model.getName());
		obj.setLayerNum(model.getLayerNum());
		obj.setCpermission(model.getCpermission());
		obj.setCurl(model.getCurl());
		obj.setCtype(model.getCtype());
		obj.setState(model.getState());
		obj.setBelong(model.getBelong());
		obj.setCwhich(model.getCwhich());
		obj.setRemark(model.getRemark());
		obj.setOrderNo(model.getOrderNo());
		//3.更新
		moduleService.saveOrUpdate(obj);
		return SUCCESS;
	}

	public String deleteById() throws Exception {	
		moduleService.deleteById(Module.class, model.getId());
		return SUCCESS;
	}
	public String delete() throws Exception {
		//1.获取删除记录的id集合
		String ids[] = model.getId().split(", ");
		//2.调用业务方法，删除记录
		for (String id : ids) {
			moduleService.deleteById(Module.class, id);
		}
		return SUCCESS;
	}

}
