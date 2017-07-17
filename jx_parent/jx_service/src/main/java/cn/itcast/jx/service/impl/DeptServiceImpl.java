package cn.itcast.jx.service.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cn.itcast.jx.common.SysConstant;
import cn.itcast.jx.dao.BaseDao;
import cn.itcast.jx.domain.Dept;
import cn.itcast.jx.service.DeptService;
import cn.itcast.jx.util.Page;

/**
 * @Description:
 * @author:     未来的程序员 :Mr.钱
 * @version:    1.0
 * @Company:    http://java.itcast.cn 
 * @date:       2017年6月26日
 */
public class DeptServiceImpl implements DeptService {
	private BaseDao baseDao;
	
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public Page<Dept> findPage(String hql, Page page, Class<Dept> entityClass,
			Object[] params) {
		return baseDao.findPage(hql, page, entityClass, params);
	}

	/* 根据id找到部门
	 * @see cn.itcast.jx.service.DeptService#get(java.lang.Class, java.io.Serializable)
	 */
	@Override
	public Dept get(Class<Dept> entityClass, Serializable id) {
		return baseDao.get(entityClass, id);
	}

	/* 查询所有的部门
	 * @see cn.itcast.jx.service.DeptService#find(java.lang.String, java.lang.Class, java.lang.Object[])
	 */
	public List<Dept> find(String hql,Class<Dept> entityClass,Object[] params){
		return baseDao.find(hql, entityClass, params);
	}

	/* 新增部门
	 * @see cn.itcast.jx.service.DeptService#saveOrUpdate(cn.itcast.jx.domain.Dept)
	 */
	@Override
	public void saveOrUpdate(Dept dept) {
		if(StringUtils.isBlank(dept.getId())){
			//默认新增的部门都是可用的
			//当项目中使用0/1这样的数字表示状态的时候，可以使用静态常量，也可以使用枚举
//			entity.setState(1);
			dept.setState(SysConstant.ENABLED);//enabled可用
			baseDao.saveOrUpdate(dept);
		}else{
		//修改
//			先查再改
			Dept viewDept = baseDao.get(Dept.class, dept.getId());
//			更新自己的部门和父部门
			viewDept.setParent(dept.getParent());
			viewDept.setDeptName(dept.getDeptName());
//			更新
		/*	baseDao.saveOrUpdate(dept);*/
			baseDao.saveOrUpdate(viewDept);
		}
	}
//单挑删除
	@Override
	public void deleteById(Class<Dept> entityClass, Serializable id) {
		//根据父部门查询所有的子部门
		List<Dept> ChildList = baseDao.find("from Dept where parent.id = ?", entityClass, new Serializable[]{id});
		// 2 遍历子部门，如果子部门又是父部门，继续递归
		if(ChildList!=null&&ChildList.size()!=0){
			for (Dept dept : ChildList) {
				//d是一个子部门，但是他有可能又是一个父部门,递归
				deleteById(entityClass, dept.getId());
			}
		}
//		删除
//		如果查不到了.说明已经别删除了,没必要重复去删除
		Dept dept = baseDao.get(entityClass, id);
		if(dept!=null){
//			真删除
//			baseDao.deleteById(entityClass, id);
//			假删除
			dept.setState(SysConstant.DISABLED);
			baseDao.saveOrUpdate(dept);
		}
	}
//批量删除
	@Override
	public void deleteById(Class<Dept> entityClass, Serializable[] ids) {
		for (Serializable id : ids) {
			deleteById(entityClass,id);
		}
	}

}
