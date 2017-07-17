package cn.itcast.jx.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cn.itcast.jx.dao.BaseDao;
import cn.itcast.jx.domain.Role;
import cn.itcast.jx.service.RoleService;
import cn.itcast.jx.util.DateUtils;
import cn.itcast.jx.util.Page;

public class RoleServiceImpl implements RoleService{

	private BaseDao baseDao;
	
	
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public Page<Role> findPage(String hql, Page page, Class<Role> entityClass,
			Object[] params) {
		return baseDao.findPage(hql, page, entityClass, params);
	}

	public Role get(Class<Role> entityClass, Serializable id) {
		return baseDao.get(entityClass, id);
	}

	public List<Role> find(String hql, Class<Role> entityClass, Object[] params) {
		return baseDao.find(hql, entityClass, params);
	}

	public void saveOrUpdate(Role role) {
	try {
		role.setCreateTime(new Date());
	} catch (Exception e) {
		e.printStackTrace();
	}
	baseDao.saveOrUpdate(role);
		}

	public void deleteById(Class<Role> entityClass, Serializable id) {
		baseDao.deleteById(Role.class, id);
	}

	public void deleteById(Class<Role> entityClass, Serializable[] ids) {
		for (Serializable id : ids) {
			deleteById(entityClass,id);
		}
	}

}
