package cn.itcast.jx.service;

import java.io.Serializable;
import java.util.List;

import cn.itcast.jx.domain.ExtCproduct;
import cn.itcast.jx.util.Page;

public interface ExtCproductService {
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
	public abstract Page<ExtCproduct> findPage(String hql,Page page ,Class<ExtCproduct> entityClass,Object[] params);

	/**
	 * @Description:根据id查询部门
	 * @param entityClass
	 * @param id
	 * @return
	 * @version 1.0
	 * @author 未来的程序员 Mr.钱
	 * @time 2017年6月26日下午10:01:53
	 */
	public  ExtCproduct get(Class<ExtCproduct> entityClass, Serializable id);

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
	public List<ExtCproduct> find(String hql,Class<ExtCproduct> entityClass,Object[] params);


	/**
	 * @Description:新增部门
	 * @param ExtCproduct
	 * @version 1.0
	 * @author 未来的程序员 Mr.钱
	 * @time 2017年6月26日下午10:20:46
	 */
	public void saveOrUpdate(ExtCproduct ExtCproduct);

	/**
	 * @Description:单挑删除
	 * @param entityClass
	 * @param id
	 * @version 1.0
	 * @author 未来的程序员 Mr.钱
	 * @time 2017年6月27日上午9:12:01
	 */
	public void deleteById(Class<ExtCproduct> entityClass, Serializable id);

	/**
	 * @Description:批量删除
	 * @param entityClass
	 * @param ids
	 * @version 1.0
	 * @author 未来的程序员 Mr.钱
	 * @time 2017年6月27日上午9:46:01
	 */
	public void deleteById(Class<ExtCproduct> entityClass, Serializable[] ids);
}
