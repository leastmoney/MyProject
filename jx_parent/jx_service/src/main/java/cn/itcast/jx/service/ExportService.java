package cn.itcast.jx.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import cn.itcast.jx.domain.Export;
import cn.itcast.jx.util.Page;

public interface ExportService {
	//查询所有，带条件查询
		public List<Export> find(String hql, Class<Export> entityClass, Object[] params);
		//获取一条记录
		public Export get(Class<Export> entityClass, Serializable id);
		//分页查询，将数据封装到一个page分页工具类对象
		public Page<Export> findPage(String hql, Page<Export> page, Class<Export> entityClass, Object[] params);
		
		//新增和修改保存
		public void saveOrUpdate(Export entity);
		//批量新增和修改保存
		public  void saveOrUpdateAll(Collection<Export> entitys);
		
		//单条删除，按id	
		public void deleteById(Class<Export> entityClass, Serializable id);
		//批量删除
		public void delete(Class<Export> entityClass, Serializable[] ids);
}
