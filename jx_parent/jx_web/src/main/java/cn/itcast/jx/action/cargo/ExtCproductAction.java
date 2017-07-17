package cn.itcast.jx.action.cargo;

import cn.itcast.jx.action.BaseAction;
import cn.itcast.jx.domain.ExtCproduct;
import cn.itcast.jx.domain.Factory;
import cn.itcast.jx.service.ExtCproductService;
import cn.itcast.jx.service.FactoryService;
import cn.itcast.jx.service.UserService;
import cn.itcast.jx.util.Page;

import com.opensymphony.xwork2.ModelDriven;

import java.util.List;

public class ExtCproductAction extends BaseAction implements ModelDriven<ExtCproduct>{
	//模型
	private ExtCproduct model = new ExtCproduct();
	private ExtCproductService extCproductService;
	
	public void setExtCproductService(ExtCproductService extCproductService) {
		this.extCproductService = extCproductService;
	}
	private FactoryService factoryService;

	public void setFactoryService(FactoryService factoryService) {
		this.factoryService = factoryService;
	}

	private Page page = new Page();

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public ExtCproduct getModel() {
		return model;
	}


	public String tocreate() throws Exception {
		// 1.得到用于生产附件的厂家列表
		List<Factory> factoryList = factoryService.find("from Factory where state=1 and ctype='附件'", Factory.class,
				null);
		// 放入值栈中
		super.put("factoryList", factoryList);
		// 2.加载当前货物下的所有附件
		extCproductService.findPage("from ExtCproduct where contractProduct.id=?", page, ExtCproduct.class,
				new Object[] { model.getContractProduct().getId() });
		//设置分页组件的url
		page.setUrl("extCproductAction_tocreate");
		super.push(page);//将分页组件放入栈顶
		// 跳页面
		return "tocreate";
	}


	public String insert() throws Exception {
		extCproductService.saveOrUpdate(model);
		return tocreate();
	}
	public String toupdate() throws Exception {
        ExtCproduct obj = extCproductService.get(ExtCproduct.class, model.getId());
        super.push(obj);
        // 1.得到用于生产附件的厂家列表
        List<Factory> factoryList = factoryService.find("from Factory where state=1 and ctype='附件'", Factory.class,
                null);
        // 放入值栈中
        super.put("factoryList", factoryList);
        return "toupdate";
	}
	public String update() throws Exception {
// 1.根据id,得到要更新的对象
        ExtCproduct obj = extCproductService.get(ExtCproduct.class, model.getId());

        // 2.页面修改的属性，就要更新值
        obj.setFactory(model.getFactory());
        obj.setFactoryName(model.getFactoryName());
        obj.setProductNo(model.getProductNo());
        obj.setProductImage(model.getProductImage());
        obj.setCnumber(model.getCnumber());
        obj.setPackingUnit(model.getPackingUnit());
        obj.setPrice(model.getPrice());
        obj.setOrderNo(model.getOrderNo());
        obj.setProductDesc(model.getProductDesc());
        obj.setProductRequest(model.getProductRequest());
        // 3.更新
        extCproductService.saveOrUpdate(obj);
        return tocreate();

	}

	public String deleteById() throws Exception {	
		return  tocreate();
	}

	public String delete() throws Exception {
        extCproductService.deleteById(ExtCproduct.class, model.getId());
		return  tocreate();
	}

}
