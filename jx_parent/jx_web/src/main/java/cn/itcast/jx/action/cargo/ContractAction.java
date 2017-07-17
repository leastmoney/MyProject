package cn.itcast.jx.action.cargo;

import cn.itcast.jx.action.BaseAction;
import cn.itcast.jx.common.SysConstant;
import cn.itcast.jx.domain.Contract;
import cn.itcast.jx.domain.Dept;
import cn.itcast.jx.domain.User;
import cn.itcast.jx.print.ContractPrint;
import cn.itcast.jx.service.ContractService;
import cn.itcast.jx.service.DeptService;
import cn.itcast.jx.service.UserService;
import cn.itcast.jx.util.Page;

import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.ServletActionContext;

import java.awt.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContractAction extends BaseAction implements ModelDriven<Contract>{
	//模型
	private Contract model = new Contract();
	private ContractService contractService;
	
	public void setContractService(ContractService contractService) {
		this.contractService = contractService;
	}
//	副总
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
	String hql2 = "";
	public String list() throws Exception {
		String hql = "from Contract where 1=1";
		//判断当前用户的权限
		User user = (User) this.getSession().get(SysConstant.CURRENT_USER_INFO);
		//4 普通员工
		if(user.getUserInfo().getDegree()==4){
			hql += " and createBy='"+user.getId()+"'";
		}
		//3管理本部门
		else if(user.getUserInfo().getDegree()==3){
			hql += " and createDept='"+user.getDept().getId()+"'";
		}
		//2 管理所有的下属部门和人员
		/*else if(user.getUserInfo().getDegree()==2){
			String cstr = DeptList(user.getDept().getId());
			System.out.println(cstr);
			String str = cstr.substring(0, cstr.length()-1);
			hql += "and createDept in("+ str +")";
		}*/
		else if(user.getUserInfo().getDegree()==2){
//			不用返回值的地柜
			DeptList(user.getDept().getId());
			System.out.println(str);
			String cstr = str.substring(0, str.length()-1);
			hql += "and createDept in("+ cstr +")";
		}
		//1跨部门跨人员进行管理
		else if(user.getUserInfo().getDegree()==1){
			hql2 = "from Contract where createBy in (select id from FuBoss where type = '员工' and fubossId = '"+user.getId()+"') or createDept in(select id from FuBoss where type = '部门' and fubossId = '"+user.getId()+"')";
		}
		//0 超管
		else{

		}

		//查找数据
		if(user.getUserInfo().getDegree()==1){
			contractService.findPage(hql2, page,Contract.class, null);
		}else{
			contractService.findPage(hql, page,Contract.class, null);
		}
		//设置URL跳转路径
		page.setUrl("contractAction_list");
		//将数据放入值栈
		super.push(page);

		return "list";
	}


	public Contract getModel() {
		return model;
	}
	public String toview() throws Exception {
//		/查找用户
		Contract Contract = contractService.get(Contract.class, model.getId());
		//将用户信息放入值栈
		super.push(Contract);
		
		return "toview";
	}
	public String tocreate() throws Exception {
		return "tocreate";
	}
	
	public String insert() throws Exception {
		//1.得到当前登录的用户对象
		User user = (User) session.get(SysConstant.CURRENT_USER_INFO);
		//2.设置创建者的id
		model.setCreateBy(user.getId());

		//3.设置创建者所在部门的id
		model.setCreateDept(user.getDept().getId());
		model.setCreateTime(new Date());
		contractService.saveOrUpdate(model);
		return list();
	}
	public String toupdate() throws Exception {
		//1.加载当前要更新的对象
		Contract obj = contractService.get(Contract.class, model.getId());
		super.push(obj);
		return "toupdate";
	}
	public String update() throws Exception {
		contractService.saveOrUpdate(model);
		return list();
	}

	public String deleteById() throws Exception {	
		contractService.deleteById(Contract.class, model.getId());
		return list();
	}
	
	public String delete() throws Exception {
		//1.获取删除记录的id集合
		String ids[] = model.getId().split(", ");
		//2.调用业务方法，删除记录
		for (String id : ids) {
			contractService.deleteById(Contract.class, id);
		}
		return list();
	}
	
	public String submit() throws Exception {
		if(model.getId()==null){
			return list();
		}
		String[] ids = model.getId().split(", ");
		for (String id : ids) {
			Contract contract = contractService.get(Contract.class, id);
			contract.setState(1);
			contractService.saveOrUpdate(contract);	
		}
		return list();
		
	}
	public String cancel() throws Exception {
		if(model.getId()==null){
			return list();
		}
		String[] ids = model.getId().split(", ");
		
		for(String id:ids){
			Contract contract = contractService.get(Contract.class, id);
			contract.setState(0);
			
			contractService.saveOrUpdate(contract);
		}
		
		return list();
	}
	//	不需要返回值
//	定义全局变量用来接收id
	private String str = "";
	public void DeptList(String id){
//		通过副部们来查出所有的子部门
		List<Dept> list = deptService.find("from Dept where parent.id = ?", Dept.class, new String[]{id});
		if(list!=null && list.size()!=0){
			for (Dept d : list) {
				str+="'"+d.getId()+"'"+",";
//				递归
				DeptList(d.getId());
			}
		}
	}
	/*private String str = "";
	public String DeptList(String id){
		List<Dept> list = deptService.find("from Dept where parent.id = ?", Dept.class, new String[]{id});
			if(list!=null && list.size()!=0){
				for (Dept d : list) {
					str+="'"+d.getId()+"'"+",";
				DeptList(d.getId());
			}
		}
		return str;
	}*/
	public String print() throws Exception {
//		拿到合同
		Contract contract = contractService.get(Contract.class, model.getId());
//		获取真实路径
		String path = ServletActionContext.getRequest().getRealPath("/");

		ContractPrint contractPrint = new ContractPrint();
		contractPrint.print(contract,path,ServletActionContext.getResponse());
		return NONE;
	}
}
