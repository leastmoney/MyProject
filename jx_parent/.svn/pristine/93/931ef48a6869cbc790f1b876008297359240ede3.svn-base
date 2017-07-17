package cn.itcast.jx.service;

import java.io.Serializable;
import java.util.List;

import cn.itcast.jx.domain.Module;
import cn.itcast.jx.util.Page;

public interface ModuleService {
	/**
	 * @Description:分页查询
	 * @param hql
	 * @param page
	 * @param entityClass
	 * @param params
	 * @return
	 * @version 1.0
	 * @author 未来的程序员 Mr.钱
	 * @time 2017年6月26日下午10:02:00
	 */
	public abstract Page<Module> findPage(String hql,Page page ,Class<Module> entityClass,Object[] params);

	/**
	 * @Description:根据id查询部门
	 * @param entityClass
	 * @param id
	 * @return
	 * @version 1.0
	 * @author 未来的程序员 Mr.钱
	 * @time 2017年6月26日下午10:01:53
	 */
	public  Module get(Class<Module> entityClass, Serializable id);

	/**
	 * @Description:查询所有的部门
	 * @param hql
	 * @param entityClass
	 * @param params
	 * @return
	 * @version 1.0
	 * @author 未来的程序员 Mr.钱
	 * @time 2017年6月26日下午10:01:30
	 */
	public List<Module> find(String hql,Class<Module> entityClass,Object[] params);


	/**
	 * @Description:新增部门
	 * @param Module
	 * @version 1.0
	 * @author 未来的程序员 Mr.钱
	 * @time 2017年6月26日下午10:20:46
	 */
	public void saveOrUpdate(Module Module);

	/**
	 * @Description:单挑删除
	 * @param entityClass
	 * @param id
	 * @version 1.0
	 * @author 未来的程序员 Mr.钱
	 * @time 2017年6月27日上午9:12:01
	 */
	public void deleteById(Class<Module> entityClass, Serializable id);

	/**
	 * @Description:批量删除
	 * @param entityClass
	 * @param ids
	 * @version 1.0
	 * @author 未来的程序员 Mr.钱
	 * @time 2017年6月27日上午9:46:01
	 */
	public void deleteById(Class<Module> entityClass, Serializable[] ids);
}
