package cn.itcast.jx.action.cargo;

import java.util.List;

import cn.itcast.jx.action.BaseAction;
import cn.itcast.jx.domain.ContractProduct;
import cn.itcast.jx.domain.Factory;
import cn.itcast.jx.service.ContractProductService;
import cn.itcast.jx.service.FactoryService;
import cn.itcast.jx.util.Page;

import com.opensymphony.xwork2.ModelDriven;

public class ContractProductAction extends BaseAction implements ModelDriven<ContractProduct>{
	//模型
	private ContractProduct model = new ContractProduct();
	private ContractProductService contractProductService;
	
	private FactoryService factoryService;
	
	public void setFactoryService(FactoryService factoryService) {
		this.factoryService = factoryService;
	}

	public void setContractProductService(ContractProductService contractProductService) {
		this.contractProductService = contractProductService;
	}
	
	private Page page = new Page();

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public ContractProduct getModel() {
		return model;
	}
	

	public String tocreate() throws Exception {
//		工厂列表
		List<Factory> factoryList = factoryService.find("from Factory where state = 1 and ctype = '货物'",Factory.class,null);
		super.put("factoryList", factoryList);		
//		当前购销合同下的所有货物列表
		contractProductService.findPage("from ContractProduct where contract.id=?", page, ContractProduct.class, new Object[]{model.getContract().getId()});
		page.setUrl("contractProductAction_tocreate");
		super.push(page);
		return "tocreate";
	}
	
	public String insert() throws Exception {
		contractProductService.saveOrUpdate(model);
		return tocreate();
//		return SUCCESS;
	}
	public String toupdate() throws Exception {
		// 1.加载当前要更新的对象
		ContractProduct obj = contractProductService.get(ContractProduct.class, model.getId());
		super.push(obj);
		//2.加载生产厂家
		List<Factory> factoryList = factoryService.find("from Factory where state=1 and ctype='货物'", Factory.class,null);
		super.put("factoryList", factoryList);
		return "toupdate";

	}
public String update() throws Exception {
		
		//先设置属性,去service中计算
		// 1 根据id查找货物的信息
		ContractProduct contractProduct = contractProductService.get(ContractProduct.class, model.getId());
		// 2 将修改的属性赋值给contractProduct
		//生产厂家
		contractProduct.setFactory(model.getFactory());
		contractProduct.setFactoryName(model.getFactoryName());
		//货物照片
		contractProduct.setProductImage(model.getProductImage());
		//数量
		contractProduct.setCnumber(model.getCnumber());
		//装率
		contractProduct.setLoadingRate(model.getLoadingRate());
		//单价
		contractProduct.setPrice(model.getPrice());
		//货描
		contractProduct.setProductDesc(model.getProductDesc());
		//货号
		contractProduct.setProductNo(model.getProductNo());
		//包装单位
		contractProduct.setPackingUnit(model.getPackingUnit());
		//箱数
		contractProduct.setBoxNum(model.getBoxNum());
		//排序号
		contractProduct.setOrderNo(model.getOrderNo());
		//要求
		contractProduct.setProductRequest(model.getProductRequest());
		
		
		contractProductService.saveOrUpdate(contractProduct);
		
		return tocreate();
	}


	public String delete() throws Exception {	
		contractProductService.deleteById(ContractProduct.class, model.getId());
		return tocreate();
	}
	
	

}
