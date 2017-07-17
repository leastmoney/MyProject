package cn.itcast.jx.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import cn.itcast.jx.dao.BaseDao;
import cn.itcast.jx.domain.Contract;
import cn.itcast.jx.domain.ContractProduct;
import cn.itcast.jx.domain.Export;
import cn.itcast.jx.domain.ExportProduct;
import cn.itcast.jx.domain.ExtCproduct;
import cn.itcast.jx.domain.ExtEproduct;
import cn.itcast.jx.service.ExportService;
import cn.itcast.jx.util.Page;
import cn.itcast.jx.util.UtilFuns;

public class ExportServiceImpl implements ExportService{
	private BaseDao baseDao;

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public List<Export> find(String hql, Class<Export> entityClass, Object[] params) {
		return baseDao.find(hql, entityClass, params);
	}

	@Override
	public Export get(Class<Export> entityClass, Serializable id) {
		return baseDao.get(entityClass, id);
	}

	@Override
	public Page<Export> findPage(String hql, Page<Export> page,
			Class<Export> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, entityClass, params);
	}

	@Override
	public void saveOrUpdate(Export entity) {
		if(StringUtils.isBlank(entity.getId())){
			/**
			 * 新增业务：
			 * 1 拼接合同和确认书号
			 * 2 数据搬家（货物和附件）
			 */
			//新增
			//准备合同和确认书号 11JK1080 11JK1081 11JK1080
			//分割购销合同的id
			String[] ids = entity.getContractIds().split(", ");
//          遍历拼接合同确认书号
			String customerContract = "";
			for (String id:ids) {
				Contract contract = baseDao.get(Contract.class, id);
				customerContract += contract.getContractNo()+" ";
				//修改购销合同的状态
				contract.setState(2);
				baseDao.saveOrUpdate(contract);
			}
//			合同确认书号
			entity.setCustomerContract(customerContract);
			//报运单日期
			entity.setInputDate(new Date());
			//新增的报运单是 草稿
			entity.setState(0);
			//数据搬家
//			使用工具类进行拼接sql
			String str = UtilFuns.joinInStr(ids);
//         查询所有的货物
			String hql = "from ContractProduct where contract.id in ("+str+")";
			List<ContractProduct> cpList = baseDao.find(hql, ContractProduct.class, null);
//			创建一个集合用来装货物的
			Set<ExportProduct> epSet = new HashSet<ExportProduct>();

//			遍历所有货物
			for (ContractProduct cp: cpList) {
//				创建一个货物对象
				ExportProduct ep = new ExportProduct();
//				使用工具来复制参数
//				第一个参数是数据源.第二个参数是目标对象
				BeanUtils.copyProperties(cp,ep);
//				但是要注意,id页会被复制,所以要情况id
				ep.setId(null);
//				设置报运单信息
				ep.setExport(entity);
				epSet.add(ep);
//				获取附件
				Set<ExtCproduct> extCList = cp.getExtCproducts();
//				创建一个集合用来接收一个货物下的多个附件
				Set<ExtEproduct> extEproducts = new HashSet<ExtEproduct>();
				for (ExtCproduct extE:extCList) {
					ExtEproduct exte = new ExtEproduct();
					BeanUtils.copyProperties(extE,exte);
					exte.setId(null);
					//设置附件所属货物
					exte.setExportProduct(ep);
					extEproducts.add(exte);
				}
				ep.setExtEproducts(extEproducts);
			}
//epList与Export产生关系
			entity.setExportProducts(epSet);
			baseDao.saveOrUpdate(entity);
		}else{
//			修改
			Export export = baseDao.get(Export.class, entity.getId());


			baseDao.saveOrUpdate(export);
		}

	}

	@Override
	public void saveOrUpdateAll(Collection<Export> entitys) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Class<Export> entityClass, Serializable id) {
		baseDao.deleteById(entityClass, id);
	}

	@Override
	public void delete(Class<Export> entityClass, Serializable[] ids) {
		for(Serializable id:ids){
			deleteById(entityClass, id);
		}
	}

}
