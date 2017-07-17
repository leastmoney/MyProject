package cn.itcast.jx.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import cn.itcast.jx.common.SysConstant;
import cn.itcast.jx.dao.BaseDao;
import cn.itcast.jx.domain.Contract;
import cn.itcast.jx.service.ContractService;
import cn.itcast.jx.util.Page;
import cn.itcast.jx.util.UtilFuns;

public class ContractServiceImpl implements ContractService{
private BaseDao baseDao;
	
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	public Page<Contract> findPage(String hql, Page page, Class<Contract> entityClass,
			Object[] params) {
		return baseDao.findPage(hql, page, entityClass, params);
	}

	public Contract get(Class<Contract> entityClass, Serializable id) {
		return baseDao.get(entityClass, id);
	}

	public List<Contract> find(String hql, Class<Contract> entityClass, Object[] params) {
		return baseDao.find(hql, entityClass, params);
	}

	public void saveOrUpdate(Contract entity) {
		if(UtilFuns.isEmpty(entity.getId())){
			//设置购销合同的初始总金额 为0
			entity.setTotalAmount(0d);
			entity.setState(0);//0草稿   1已上报    2已报运
              baseDao.saveOrUpdate(entity);
		}else{
			//1.根据id,得到要更新的对象
			Contract obj = baseDao.get(Contract.class, entity.getId());
			//2.页面修改的属性，就要更新值
			//客户名称
			obj.setCustomName(entity.getCustomName());
			//打印板式
			obj.setPrintStyle(entity.getPrintStyle());
			//合同号
			obj.setContractNo(entity.getContractNo());
			//收购方
			obj.setOfferor(entity.getOfferor());
			//制单人
			obj.setInputBy(entity.getInputBy());
			//审单人
			obj.setCheckBy(entity.getCheckBy());
			//验货员
			obj.setInspector(entity.getInspector());
			//签期
			obj.setSigningDate(entity.getSigningDate());
			//重要程度
			obj.setImportNum(entity.getImportNum());
			//船期
			obj.setShipTime(entity.getShipTime());
			//贸易条款
			obj.setTradeTerms(entity.getTradeTerms());
			//交期
			obj.setDeliveryPeriod(entity.getDeliveryPeriod());
			//要求
			obj.setCrequest(entity.getCrequest());
			//说明
			obj.setRemark(entity.getRemark());
//			日期
			obj.setUpdateTime(new Date());
			//更新
			baseDao.saveOrUpdate(obj);
		}
	}


	public void deleteById(Class<Contract> entityClass, Serializable id) {
		baseDao.deleteById(entityClass, id);
	}

	public void deleteById(Class<Contract> entityClass, Serializable[] ids) {
		for (Serializable id : ids) {
			deleteById(Contract.class, id);
		}
	}

}
