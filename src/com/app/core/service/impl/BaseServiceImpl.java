package com.app.core.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.app.core.dao.BaseDao;
import com.app.core.service.BaseService;
import com.app.core.service.util.GenericsManager;
import com.app.core.service.util.PageUtil;
import com.app.core.service.util.QueryParameter;

/**
 * 基础Service实现类
 * 
 * @author ZF
 * @2011-5-16
 * @param <T>
 */
@Service("baseService")
public class BaseServiceImpl<T> implements BaseService<T> {

	/** 实体Class */
	private final Class<T> entityClass;

	/** 构造函数，初始化实体类型 */
	@SuppressWarnings("unchecked")
	public BaseServiceImpl() {
		this.entityClass = (Class<T>) GenericsManager.getGeneric(getClass());
	}

	/** 获取实体实体 */
	public Class<T> getEntityClass() {
		return entityClass;
	}

	// @Resource(name="baseDao")
	private BaseDao<T> baseDao;

	/** ---------------------------查找对象---------------------------------* */
	public T get(Serializable id) {
		return baseDao._get(getEntityClass(), id);
	}

	public T load(Serializable id) {
		return baseDao._load(getEntityClass(), id);
	}

	/** ---------------------------查找集合---------------------------------* */
	public List<T> findAll() {
		return baseDao._findAll(getEntityClass());
	}

	public int findCount(QueryParameter param) {
		String whereHQL = queryParameter(param);
		return baseDao._findCount(getEntityClass(), whereHQL);
	}

	public List<T> findListByMap(Map<String, Object> conditionMap) {
		return baseDao._findListByMap(getEntityClass(), conditionMap, "");
	}

	public List<T> findListByMap(Map<String, Object> conditionMap, String order) {
		return baseDao._findListByMap(getEntityClass(), conditionMap, order);
	}

	public List<T> findListByHql(String hql, Serializable... values) {
		return baseDao._findListByHql(hql, values);
	}

	public List<T> findList(QueryParameter param) {
		String whereHQL = queryParameter(param);

		StringBuilder order = new StringBuilder();
		if (StringUtils.isNotEmpty(param.getOrderBy()))
			order.append(param.getOrderBy());

		return baseDao._findList(getEntityClass(), whereHQL, order.toString());
	}

	public List<T> findPageList(PageUtil page, QueryParameter param) {
		String whereHQL = queryParameter(param);

		int size = baseDao._findCount(getEntityClass(), whereHQL); // 得到总行数
		if (size == 0)
			return new ArrayList<T>();

		StringBuilder order = new StringBuilder();
		if (StringUtils.isNotEmpty(page.getSort()) && StringUtils.isNotEmpty(page.getOrder())) {
			order.append(page.getSort()).append(" ").append(page.getOrder());
		} else if (StringUtils.isNotEmpty(param.getOrderBy())) {
			order.append(param.getOrderBy());
		}

		page.setTotalRows(size); // 计算分页数据

		return baseDao._findPageList(getEntityClass(), page.getStartRow(), page.getRows(), whereHQL, order.toString());
	}

	/**
	 * 组装查询条件
	 * 
	 * @param para
	 * @return
	 */
	public String queryParameter(QueryParameter para) {
		StringBuilder whereHQL = new StringBuilder();
		if (null != para) {
			List<QueryParameter> paraList = para.getParam();
			for (QueryParameter p : paraList) {
				if (StringUtils.isEmpty(p.getHql())) {
					whereHQL.append(p.getOperator()).append(p.getFieldName()).append(p.getCondition()).append(p.getPrefix()).append(p.getValue()).append(p.getSuffix()).append(" ");
				} else {
					whereHQL.append(p.getOperator()).append(p.getHql()).append(" ");
				}
			}
		}
		return whereHQL.toString();
	}

	/** ---------------------------保存--------------------------------- **/
	public T save(T entity) {
		return baseDao._save(entity);
	}

	/** ---------------------------修改--------------------------------- **/
	public T update(T entity) {
		return baseDao._update(entity);
	}

	/** ---------------------------批操作---------------------------------* */
	public int updateBatch(String hql, Serializable... values) {
		return baseDao._updateBatch(hql, values);
	}

	/** ---------------------------保存与修改--------------------------------- **/
	public T saveOrUpdate(T entity) {
		return baseDao._saveOrUpdate(entity);
	}

	public void saveOrUpdateCollection(Collection<T> collection) {
		baseDao._saveOrUpdateCollection(collection);
	}

	/** ---------------------------删除--------------------------------- **/
	public void delete(T entity) {
		baseDao._delete(entity);
	}

	public void deleteById(Serializable id) {
		baseDao._deleteById(getEntityClass(), id);
	}

	public void deleteByIds(Serializable[] ids) {
		baseDao._deleteByIds(getEntityClass(), ids);
	}

	public void deleteCollection(Collection<T> collection) {
		baseDao._deleteCollection(collection);
	}
	
	public int deleteStateByIds(Object[] ids) {
		return baseDao._deleteStateByIds(getEntityClass(), ids);
	}

	/**
	 * ----------------------------Connection--想自定义SQL请采用Spring
	 * jdbcTemplate-------------- ----------------
	 **/
	// public Connection getConnection() {
	// return baseDao._getConnection();
	// }

	// public void closeConn(Connection conn, PreparedStatement stmt, ResultSet
	// rs) {
	// baseDao._closeConn(conn, stmt, rs);
	// }

	/**
	 * ----------------------------baseDao get()
	 * set()--------------------------------
	 **/
	public BaseDao<T> getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao<T> baseDao) {
		this.baseDao = baseDao;
	}

}
