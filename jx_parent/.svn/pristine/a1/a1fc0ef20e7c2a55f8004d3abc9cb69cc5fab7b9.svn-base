package cn.itcast.jx.service;

import java.io.Serializable;
import java.util.List;

import cn.itcast.jx.domain.User;
import cn.itcast.jx.util.Page;

public interface UserService {
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
	public abstract Page<User> findPage(String hql,Page page ,Class<User> entityClass,Object[] params);

	/**
	 * @Description:根据id查询部门
	 * @param entityClass
	 * @param id
	 * @return
	 * @version 1.0
	 * @author 未来的程序员 Mr.钱
	 * @time 2017年6月26日下午10:01:53
	 */
	public  User get(Class<User> entityClass, Serializable id);

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
	public List<User> find(String hql,Class<User> entityClass,Object[] params);


	/**
	 * @Description:新增部门
	 * @param User
	 * @version 1.0
	 * @author 未来的程序员 Mr.钱
	 * @time 2017年6月26日下午10:20:46
	 */
	public void saveOrUpdate(User User);

	/**
	 * @Description:单挑删除
	 * @param entityClass
	 * @param id
	 * @version 1.0
	 * @author 未来的程序员 Mr.钱
	 * @time 2017年6月27日上午9:12:01
	 */
	public void deleteById(Class<User> entityClass, Serializable id);

	/**
	 * @Description:批量删除
	 * @param entityClass
	 * @param ids
	 * @version 1.0
	 * @author 未来的程序员 Mr.钱
	 * @time 2017年6月27日上午9:46:01
	 */
	public void deleteById(Class<User> entityClass, Serializable[] ids);

	/**
	 * @Description:根据用户名到数据库查找用户是否存在
	 * @param username
	 * @return
	 * @version 1.0
	 * @author 未来的程序员 Mr.钱
	 * @time 2017年6月29日下午9:55:57
	 */
	public abstract User findUserByName(String username);
}
