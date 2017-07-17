package cn.itcast.jx.service.impl;

import java.io.Serializable;
import java.util.List;

import cn.itcast.jx.common.SysConstant;
import cn.itcast.jx.dao.BaseDao;
import cn.itcast.jx.domain.Factory;
import cn.itcast.jx.service.FactoryService;
import cn.itcast.jx.util.Page;

public class FactoryServiceImpl implements FactoryService{
private BaseDao baseDao;
	
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	public Page<Factory> findPage(String hql, Page page, Class<Factory> entityClass,
			Object[] params) {
		return baseDao.findPage(hql, page, entityClass, params);
	}

	public Factory get(Class<Factory> entityClass, Serializable id) {
		return baseDao.get(entityClass, id);
	}

	public List<Factory> find(String hql, Class<Factory> entityClass, Object[] params) {
		return baseDao.find(hql, entityClass, params);
	}

	public void saveOrUpdate(Factory Factory) {
		baseDao.saveOrUpdate(Factory);
	}

	public void deleteById(Class<Factory> entityClass, Serializable id) {
		Factory Factory = baseDao.get(Factory.class, id);
		Factory.setState(SysConstant.DISABLED);
		baseDao.saveOrUpdate(Factory);
	}

	public void deleteById(Class<Factory> entityClass, Serializable[] ids) {
		for (Serializable id : ids) {
			deleteById(Factory.class, id);
		}
	}

}
