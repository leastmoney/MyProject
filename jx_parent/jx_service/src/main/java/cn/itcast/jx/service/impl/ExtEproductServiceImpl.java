package cn.itcast.jx.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cn.itcast.jx.dao.BaseDao;
import cn.itcast.jx.domain.ExtEproduct;
import cn.itcast.jx.service.ExtEproductService;
import cn.itcast.jx.util.Page;

public class ExtEproductServiceImpl implements ExtEproductService{
	private BaseDao baseDao;
	
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public List<ExtEproduct> find(String hql, Class<ExtEproduct> entityClass, Object[] params) {
		return baseDao.find(hql, entityClass, params);
	}

	@Override
	public ExtEproduct get(Class<ExtEproduct> entityClass, Serializable id) {
		return baseDao.get(entityClass, id);
	}

	@Override
	public Page<ExtEproduct> findPage(String hql, Page<ExtEproduct> page,
			Class<ExtEproduct> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, entityClass, params);
	}

	@Override
	public void saveOrUpdate(ExtEproduct entity) {
		if(StringUtils.isBlank(entity.getId())){
			//新增
			baseDao.saveOrUpdate(entity);
		}else{
//			修改
			ExtEproduct extEproduct = baseDao.get(ExtEproduct.class, entity.getId());
			
			
			
			baseDao.saveOrUpdate(extEproduct);
		}
		
	}

	@Override
	public void saveOrUpdateAll(Collection<ExtEproduct> entitys) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Class<ExtEproduct> entityClass, Serializable id) {
		baseDao.deleteById(entityClass, id);
	}

	@Override
	public void delete(Class<ExtEproduct> entityClass, Serializable[] ids) {
		for(Serializable id:ids){
			deleteById(entityClass, id);
		}
	}
	
}
