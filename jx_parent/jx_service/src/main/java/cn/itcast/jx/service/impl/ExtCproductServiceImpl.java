package cn.itcast.jx.service.impl;

import java.io.Serializable;
import java.util.List;

import cn.itcast.jx.dao.BaseDao;
import cn.itcast.jx.domain.Contract;
import cn.itcast.jx.domain.ExtCproduct;
import cn.itcast.jx.service.ExtCproductService;
import cn.itcast.jx.util.Page;
import cn.itcast.jx.util.UtilFuns;
import org.apache.commons.lang3.StringUtils;

public class ExtCproductServiceImpl implements ExtCproductService{
private BaseDao baseDao;
	
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public Page<ExtCproduct> findPage(String hql, Page page, Class<ExtCproduct> entityClass,
									  Object[] params) {
		return baseDao.findPage(hql, page, entityClass, params);
	}


	public ExtCproduct get(Class<ExtCproduct> entityClass, Serializable id) {
		return baseDao.get(entityClass, id);
	}

	public List<ExtCproduct> find(String hql, Class<ExtCproduct> entityClass, Object[] params) {
		return baseDao.find(hql, entityClass, params);
	}

	public void saveOrUpdate(ExtCproduct ExtCproduct) {
		if(StringUtils.isBlank(ExtCproduct.getId())){
//			说明是保存
			Double amount = 0d;
			if(UtilFuns.isNotEmpty(ExtCproduct.getCnumber())&&UtilFuns.isNotEmpty(ExtCproduct.getPrice())){
				amount = ExtCproduct.getCnumber() * ExtCproduct.getPrice();
			}
			ExtCproduct.setAmount(amount);
//			获取购销合同
            Contract contract = baseDao.get(Contract.class, ExtCproduct.getContractProduct().getContract().getId());
            contract.setTotalAmount(contract.getTotalAmount() + amount);
//            修改合同
            baseDao.saveOrUpdate(contract);
//            保存附件
            baseDao.saveOrUpdate(ExtCproduct);
        }else{
//			更新
            Double amount = 0d;
            if(UtilFuns.isNotEmpty(ExtCproduct.getCnumber())&&UtilFuns.isNotEmpty(ExtCproduct.getPrice())){
                amount = ExtCproduct.getCnumber() * ExtCproduct.getPrice();
            }
//            现货区购销合同,先把老的附价格删除在添加新的
            Contract contract = ExtCproduct.getContractProduct().getContract();
            contract.setTotalAmount(contract.getTotalAmount()-ExtCproduct.getAmount()+amount);

//            然后设置新的附件价格
            ExtCproduct.setAmount(amount);
//            修改合同
            baseDao.saveOrUpdate(contract);
//            保存附件
            baseDao.saveOrUpdate(ExtCproduct);
        }



		baseDao.saveOrUpdate(ExtCproduct);
	}

	public void deleteById(Class<ExtCproduct> entityClass, Serializable id) {

        //1 获取附件信息
        ExtCproduct extCproduct = baseDao.get(ExtCproduct.class, id);
        //2 获取购销合同信息
        Contract contract = extCproduct.getContractProduct().getContract();
        //3 计算金额
        contract.setTotalAmount(contract.getTotalAmount()-extCproduct.getAmount());

        //更新
        baseDao.saveOrUpdate(contract);
        //删除附件
        baseDao.deleteById(entityClass, id);
	}

	public void deleteById(Class<ExtCproduct> entityClass, Serializable[] ids) {
		for (Serializable id : ids) {
			deleteById(ExtCproduct.class, id);
		}
	}

}
