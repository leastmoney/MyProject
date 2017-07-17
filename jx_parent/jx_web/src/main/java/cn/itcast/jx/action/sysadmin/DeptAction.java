package cn.itcast.jx.action.sysadmin;

import java.util.List;

import cn.itcast.jx.action.BaseAction;
import cn.itcast.jx.domain.Dept;
import cn.itcast.jx.service.DeptService;
import cn.itcast.jx.util.Page;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

public class DeptAction extends BaseAction implements ModelDriven<Dept>{
	private Dept dept = new Dept();
//	注入service
	private DeptService deptService;

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
//	分页组件
	private Page page = new Page();

	public Page getPage() {
		return page;
	}
	
	public void setPage(Page page) {
		this.page = page;
	}
	
	public String list(){
		System.out.println(which);
		super.put("cwhich",which);
		
		String hql = "from Dept";
		
		deptService.findPage(hql, page, Dept.class, null);		
		//设置分页组件的url
		 page.setUrl("deptAction_list?which="+which);//相对定位
		
		//将page组件手动放入栈顶
		ActionContext.getContext().getValueStack().push(page);
		return "list";
	}
//	查询指定id的部门
	public String toview(){
//		dept1返回的是通过id查出来的部门;
		Dept dept1 = deptService.get(Dept.class, dept.getId());
		super.push(dept1);
		return "toview";
	}
//	查询所有的部门
	public String tocreate(){
		String hql = "from Dept where state = 1";
		List<Dept> deptList  = deptService.find(hql, Dept.class, null);
		super.put("deptList", deptList);
		return "tocreate";
	}
	
//	保存新的部门
	public String insert(){
		deptService.saveOrUpdate(dept);
		return SUCCESS;
	}	
//	修改
	public String toupdate(){
//		先把所有状态为1的查询出来
		List<Dept> deptList = deptService.find("from Dept where state = 1", Dept.class, null);
//		然后把自身查出来
		Dept viewDept = deptService.get(Dept.class, dept.getId());
//		由于自己不能作为自己的父亲，所以此时从父部门列表中移除自己
		deptList.remove(viewDept);
		super.push(viewDept);
		super.put("deptList", deptList);
		return "toupdate";
	}
	//更新 
	public String update(){
		deptService.saveOrUpdate(dept);
		return SUCCESS;
	}
	//单挑删除
//	多条删除
	public String delete(){
		String[] ids = dept.getId().split(", ");
		for (String string : ids) {
			System.out.println(string);
		}
		deptService.deleteById(Dept.class,ids);
		return SUCCESS;
	}
	public Dept getModel() {
		return dept;
	}

}
