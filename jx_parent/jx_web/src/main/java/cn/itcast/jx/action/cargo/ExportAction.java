package cn.itcast.jx.action.cargo;

import cn.itcast.jx.action.BaseAction;
import cn.itcast.jx.domain.Contract;
import cn.itcast.jx.domain.Export;
import cn.itcast.jx.domain.ExportProduct;
import cn.itcast.jx.service.ContractService;
import cn.itcast.jx.service.ExportProductService;
import cn.itcast.jx.service.ExportService;
import cn.itcast.jx.service.UserService;
import cn.itcast.jx.util.Page;

import cn.itcast.jx.util.UtilFuns;
import com.opensymphony.xwork2.ModelDriven;

import java.util.HashSet;
import java.util.Set;

public class ExportAction extends BaseAction implements ModelDriven<Export>{
	//模型
	private Export model = new Export();
	private ExportService exportService;
	
	public void setExportService(ExportService exportService) {
		this.exportService = exportService;
	}
	private ContractService contractService;
	
	public void setContractService(ContractService contractService) {
		this.contractService = contractService;
	}
	private Page page = new Page();

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Export getModel() {
		return model;
	}

	private String[] mr_id;//id
	private Integer[] mr_changed;//是否改变了
	private Integer[] mr_cnumber;//数量
	private Double[] mr_grossWeight;//毛重
	private Double[] mr_netWeight;//净重
	private Double[] mr_sizeLength;//长
	private Double[] mr_sizeWidth;//宽
	private Double[] mr_sizeHeight;//高
	private Double[] mr_exPrice;//单价
	private Double[] mr_tax;//税

	public String[] getMr_id() {
		return mr_id;
	}

	public void setMr_id(String[] mr_id) {
		this.mr_id = mr_id;
	}

	public Integer[] getMr_changed() {
		return mr_changed;
	}

	public void setMr_changed(Integer[] mr_changed) {
		this.mr_changed = mr_changed;
	}

	public Integer[] getMr_cnumber() {
		return mr_cnumber;
	}

	public void setMr_cnumber(Integer[] mr_cnumber) {
		this.mr_cnumber = mr_cnumber;
	}

	public Double[] getMr_grossWeight() {
		return mr_grossWeight;
	}

	public void setMr_grossWeight(Double[] mr_grossWeight) {
		this.mr_grossWeight = mr_grossWeight;
	}

	public Double[] getMr_netWeight() {
		return mr_netWeight;
	}

	public void setMr_netWeight(Double[] mr_netWeight) {
		this.mr_netWeight = mr_netWeight;
	}

	public Double[] getMr_sizeLength() {
		return mr_sizeLength;
	}

	public void setMr_sizeLength(Double[] mr_sizeLength) {
		this.mr_sizeLength = mr_sizeLength;
	}

	public Double[] getMr_sizeWidth() {
		return mr_sizeWidth;
	}

	public void setMr_sizeWidth(Double[] mr_sizeWidth) {
		this.mr_sizeWidth = mr_sizeWidth;
	}

	public Double[] getMr_sizeHeight() {
		return mr_sizeHeight;
	}

	public void setMr_sizeHeight(Double[] mr_sizeHeight) {
		this.mr_sizeHeight = mr_sizeHeight;
	}

	public Double[] getMr_exPrice() {
		return mr_exPrice;
	}

	public void setMr_exPrice(Double[] mr_exPrice) {
		this.mr_exPrice = mr_exPrice;
	}

	public Double[] getMr_tax() {
		return mr_tax;
	}

	public void setMr_tax(Double[] mr_tax) {
		this.mr_tax = mr_tax;
	}
	private ExportProductService exportProductService;
	public void setExportProductService(ExportProductService exportProductService) {
		this.exportProductService = exportProductService;
	}

	public String list() throws Exception {
		String hql = "from Export";
		//查找数据
		exportService.findPage(hql, page,Export.class, null);
		//设置URL跳转路径
		page.setUrl("ExportAction_list");		
		//将数据放入值栈
		super.push(page);
		
		return "list";
	}
	public String contractList(){
		String hql = "from Contract where state = 1";
		contractService.findPage(hql, page, Contract.class, null);
		page.setUrl("exportAction_contractList");
		super.push(page);
		return "contractList";
	}
	
	public String toview() throws Exception {
//		/查找用户
		Export export = exportService.get(Export.class, model.getId());
		//将用户信息放入值栈
		super.push(export);
		
		return "toview";
	}
	public String tocreate() throws Exception {
		return "tocreate";
	}
	
	public String insert() throws Exception {
		exportService.saveOrUpdate(model);
		return SUCCESS;
	}
	public String toupdate() throws Exception {
		//1.加载当前要更新的对象
		Export obj = exportService.get(Export.class, model.getId());
		super.push(obj);

		Set<ExportProduct> spList = obj.getExportProducts();

//		addTRRecord('mRecordTable', 'id', 'productNo', 'cnumber', 'grossWeight', 'netWeight', 'sizeLength', 'sizeWidth', 'sizeHeight', 'exPrice', 'tax')
		StringBuffer sb = new StringBuffer();
		for (ExportProduct ep : spList) {
			sb.append("addTRRecord('mRecordTable',");
			sb.append("'"+ep.getId()+"',");
			sb.append("'"+ep.getProductNo()+"',");
			sb.append("'"+ep.getCnumber()+"',");
			sb.append("'"+ UtilFuns.convertNull(ep.getGrossWeight())+"',");
			sb.append("'"+UtilFuns.convertNull(ep.getNetWeight())+"',");
			sb.append("'"+UtilFuns.convertNull(ep.getSizeLength())+"',");
			sb.append("'"+UtilFuns.convertNull(ep.getSizeWidth())+"',");
			sb.append("'"+UtilFuns.convertNull(ep.getSizeHeight())+"',");
			sb.append("'"+UtilFuns.convertNull(ep.getExPrice())+"',");
			sb.append("'"+UtilFuns.convertNull(ep.getTax())+"');");
		}
		System.out.println(sb.toString());
		super.put("mRecordData",sb.toString());

		return "toupdate";
	}
	public String update() throws Exception {
//先查出原来的报运单
		Export export = exportService.get(Export.class, model.getId());
//		往里面设置页面新的参数,实现更新功能
//报运号
		export.setCustomerContract(model.getCustomerContract());
		//制单日期
		export.setInputDate(model.getInputDate());
		//信用证号
		export.setLcno(model.getLcno());
		//收货人及地址
		export.setConsignee(model.getConsignee());
		//装运港
		export.setShipmentPort(model.getShipmentPort());
		//目的港
		export.setDestinationPort(model.getDestinationPort());
		//运输方式
		export.setTransportMode(model.getTransportMode());
		//价格条件
		export.setPriceCondition(model.getPriceCondition());
		//唛头
		export.setMarks(model.getMarks());
		//备注
		export.setRemark(model.getRemark());

		Set<ExportProduct> epSet = new HashSet<ExportProduct>();

		for (int i= 0;i<mr_id.length;i++){
			ExportProduct ep = exportProductService.get(ExportProduct.class, mr_id[i]);
			if(mr_changed[i] !=null &&mr_changed[i] == 1 ){
				ep.setCnumber(mr_cnumber[i]);
				ep.setGrossWeight(mr_grossWeight[i]);
				ep.setNetWeight(mr_netWeight[i]);
				ep.setSizeLength(mr_sizeLength[i]);
				ep.setSizeWidth(mr_sizeWidth[i]);
				ep.setSizeHeight(mr_sizeHeight[i]);
				ep.setExPrice(mr_exPrice[i]);
				ep.setTax(mr_tax[i]);
			}
			epSet.add(ep);
		}
		export.setExportProducts(epSet);
		exportService.saveOrUpdate(export);
		return SUCCESS;
	}

	public String deleteById() throws Exception {	
		exportService.deleteById(Export.class, model.getId());
		return SUCCESS;
	}
	public String delete() throws Exception {
		//1.获取删除记录的id集合
		String ids[] = model.getId().split(", ");
		//2.调用业务方法，删除记录
		for (String id : ids) {
			exportService.deleteById(Export.class, id);
		}
		return SUCCESS;
	}
	public String submit(){
		String ids[] = model.getId().split(", ");
		for (String id : ids) {
			Export export = exportService.get(Export.class, id);
			export.setState(1);
			exportService.saveOrUpdate(export);
		}
		return SUCCESS;
	}
	public String cancel(){
		String ids[] = model.getId().split(", ");
		for (String id : ids) {
			Export export = exportService.get(Export.class, id);
			export.setState(0);
			exportService.saveOrUpdate(export);
		}
		return SUCCESS;
	}

}
