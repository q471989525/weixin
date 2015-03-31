package com.app.core.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 基础DAO接口
 * 
 * @author ZF
 * @date 2010-7-12
 */
public interface BaseDao<T> {

	/** ---------------------------查找对象---------------------------------* */
	/** 通过get方法查找对象; 适用于需要直接获取对象的属性值 */
	public T _get(Class<T> clazz, Serializable id);

	/** 通过load方法查找对象; 适用于建立关联关系 */
	public T _load(Class<T> clazz, Serializable id);

	/** ---------------------------查找集合---------------------------------* */
	/** 查找所有对象 */
	public List<T> _findAll(Class<T> clazz);

	/** 查找对象总记录数 */
	public int _findCount(Class<T> clazz, String whereHQL);

	/** 查找所有对象；通过属性 + 排序字段 ,默认条件为  “ = ” */
	public List<T> _findListByMap(Class<T> clazz, Map<String, Object> conditionMap, String order);

	/** 通过HQL查询列表,HQL中只能通过“？”占位符  */
	public List<T> _findListByHql(String hql, Serializable... values);

	/** 通过Hql 查询 */
	public List<T> _findList(Class<T> clazz, String whereHQL, String order);

	/** 分页查询 */
	public List<T> _findPageList(Class<T> clazz, int start, int pageNumber, String whereHQL, String order);

	/** ---------------------------保存---------------------------------* */
	/** 保存实体 */
	public T _save(T entity);

	/** ---------------------------修改---------------------------------* */
	/** 修改实体 */
	public T _update(T entity);

	/** ---------------------------批操作--,HQL中只能通过“？”占位符 -------------------------------* */
	public int _updateBatch(String hql, Serializable... values);

	/** ---------------------------保存与修改---------------------------------* */
	/** 保存或更新实体 */
	public T _saveOrUpdate(T entity);

	/** 保存或更新集合 */
	public void _saveOrUpdateCollection(Collection<T> collection);

	/** ---------------------------删除---------------------------------* */
	/** 删除实体 */
	public void _delete(T entity);

	/** 通过主键删除指定类 */
	public void _deleteById(Class<T> clazz, Serializable id);

	/** 通过主键数组批量删除指定类 */
	public void _deleteByIds(Class<T> clazz, Serializable... ids);

	/** 删除集合 */
	public void _deleteCollection(Collection<?> collection);
	
	
	/** 软删除，只更新实体对象删除标志，只针对字段：deleteState */
	public int _deleteStateByIds(Class<T> clazz, Object[] ids);

	/** ----------------------------Connection--------------------------------* */
	/** 获取JDBC Connection连接，记得调用CloseConn() 方法关闭连接 */
	// public Connection _getConnection();

	/** 关闭JDBC Connection连接,释放资源 */
	// public void _closeConn(Connection conn, PreparedStatement stmt, ResultSet
	// rs);

}
