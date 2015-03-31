package com.app.core.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.app.core.service.util.PageUtil;
import com.app.core.service.util.QueryParameter;

/**
 * 基础Service接口
 * 
 * @author ZF
 * @2011-5-16
 * @param <T>
 */
public interface BaseService<T> {

	/** ---------------------------查找对象---------------------------------* */
	/** 通过get方法查找对象; 适用于需要直接获取对象的属性值 */
	public T get(Serializable id);

	/** 通过load方法查找对象; 适用于建立关联关系 */
	public T load(Serializable id);

	/** ---------------------------查找集合---------------------------------* */
	/** 查找所有对象 */
	public List<T> findAll();

	/** 查找对象总记录数 */
	public int findCount(QueryParameter param);

	/** 查找所有对象；通过属性 ,默认条件为  “ = ” */
	public List<T> findListByMap(Map<String, Object> conditionMap);

	/** 查找所有对象；通过属性 + 排序字段 ,默认条件为  “ = ” */
	public List<T> findListByMap(Map<String, Object> conditionMap, String order);

	/** 通过HQL查询列表,HQL中只能通过“？”占位符 */
	public List<T> findListByHql(String hql, Serializable... values);

	/** 通过QueryParameter 查询 */
	public List<T> findList(QueryParameter param);

	/** 分页查询 */
	public List<T> findPageList(PageUtil page, QueryParameter param);

	/** ---------------------------保存--------------------------------- **/
	/** 保存实体 */
	public T save(T entity);

	/** ---------------------------修改--------------------------------- **/
	/** 修改实体 */
	public T update(T entity);
	
	/** ---------------------------批操作---,HQL中只能通过“？”占位符 ------------------------------* */
	public int updateBatch(String hql, Serializable... values);

	/** ---------------------------保存与修改--------------------------------- **/
	/** 保存或更新实体 */
	public T saveOrUpdate(T entity);

	/** 保存或更新集合 */
	public void saveOrUpdateCollection(Collection<T> collection);

	/** ---------------------------删除--------------------------------- **/
	/** 删除实体 */
	public void delete(T entity);

	/** 通过主键删除实体 */
	public void deleteById(Serializable id);

	/** 通过主键数组批量删除实体 */
	public void deleteByIds(Serializable[] ids);

	/** 删除集合 */
	public void deleteCollection(Collection<T> collection);
	
	/** 软删除，只更新实体对象删除标志，只针对字段：deleteState */
	public int deleteStateByIds(Object[] ids);

	/**
	 * ----------------------------Connection------------------想自定义SQL请采用Spring
	 * jdbcTemplate--------------
	 **/
	/** 获取JDBC Connection连接，记得调用CloseConn() 方法关闭连接 */
	// public Connection getConnection();

	/** 关闭JDBC Connection连接,释放资源 */
	// public void closeConn(Connection conn, PreparedStatement stmt, ResultSet
	// rs);

}
