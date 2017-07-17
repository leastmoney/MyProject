package cn.itcast.jx.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import cn.itcast.jx.common.SysConstant;
import cn.itcast.jx.dao.BaseDao;
import cn.itcast.jx.domain.Module;
import cn.itcast.jx.service.ModuleService;
import cn.itcast.jx.util.Page;

public class ModuleServiceImpl implements ModuleService{
private BaseDao baseDao;
	
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	public Page<Module> findPage(String hql, Page page, Class<Module> entityClass,
			Object[] params) {
		return baseDao.findPage(hql, page, entityClass, params);
	}

	public Module get(Class<Module> entityClass, Serializable id) {
		return baseDao.get(entityClass, id);
	}

	public List<Module> find(String hql, Class<Module> entityClass, Object[] params) {
		return baseDao.find(hql, entityClass, params);
	}

	public void saveOrUpdate(Module Module) {
		baseDao.saveOrUpdate(Module);
	}

	public void deleteById(Class<Module> entityClass, Serializable id) {
		baseDao.deleteById(entityClass, id);
	}

	public void deleteById(Class<Module> entityClass, Serializable[] ids) {
		for (Serializable id : ids) {
			deleteById(Module.class, id);
		}
	}

}
