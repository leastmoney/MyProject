package cn.itcast.jx.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cn.itcast.jx.dao.BaseDao;
import cn.itcast.jx.domain.ExportProduct;
import cn.itcast.jx.service.ExportProductService;
import cn.itcast.jx.util.Page;

public class ExportProductServiceImpl implements ExportProductService{
	private BaseDao baseDao;
	
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public List<ExportProduct> find(String hql, Class<ExportProduct> entityClass, Object[] params) {
		return baseDao.find(hql, entityClass, params);
	}

	@Override
	public ExportProduct get(Class<ExportProduct> entityClass, Serializable id) {
		return baseDao.get(entityClass, id);
	}

	@Override
	public Page<ExportProduct> findPage(String hql, Page<ExportProduct> page,
			Class<ExportProduct> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, entityClass, params);
	}

	@Override
	public void saveOrUpdate(ExportProduct entity) {
		if(StringUtils.isBlank(entity.getId())){
			//新增
			baseDao.saveOrUpdate(entity);
		}else{
//			修改
			ExportProduct exportProduct = baseDao.get(ExportProduct.class, entity.getId());
			
			
			
			baseDao.saveOrUpdate(exportProduct);
		}
		
	}

	@Override
	public void saveOrUpdateAll(Collection<ExportProduct> entitys) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Class<ExportProduct> entityClass, Serializable id) {
		baseDao.deleteById(entityClass, id);
	}

	@Override
	public void delete(Class<ExportProduct> entityClass, Serializable[] ids) {
		for(Serializable id:ids){
			deleteById(entityClass, id);
		}
	}
	
}
