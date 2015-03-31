package com.app.core.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import com.app.core.dao.BaseDao;

/**
 * 
 * @author ZF
 * @Dec 25, 2010
 * @param <T>
 */
// @Repository("baseDao")
@SuppressWarnings("unchecked")
public class BaseDaoImpl<T> extends BasicSupportDao implements BaseDao<T> {

	/** ---------------------------查找对象---------------------------------* */
	public T _get(Class<T> clazz, Serializable id) {
		return getHibernateTemplate().get(clazz, id);
	}

	public T _load(Class<T> clazz, Serializable id) {
		return getHibernateTemplate().load(clazz, id);
	}

	/** ---------------------------查找集合---------------------------------* */
	public List<T> _findAll(Class<T> clazz) {
		StringBuilder hql = new StringBuilder("from ");
		hql.append(clazz.getSimpleName());
		return getHibernateTemplate().find(hql.toString());
	}

	public int _findCount(Class<T> clazz, String whereHQL) {
		StringBuilder hql = new StringBuilder("select count(*) from ");
		hql.append(clazz.getSimpleName());

		if (StringUtils.isNotEmpty(whereHQL)) {
			if (StringUtils.startsWithIgnoreCase(whereHQL.trim(), "and")) {
				hql.append(" where 1=1 ").append(whereHQL);
			} else {
				hql.append(" where 1=1 and ").append(whereHQL);
			}
		}

		List<Object> list = getHibernateTemplate().find(hql.toString());
		Object count = list.get(0);
		return Integer.valueOf(count.toString());
	}

	public List<T> _findListByMap(Class<T> clazz, Map<String, Object> conditionMap, String order) {
		StringBuilder hql = new StringBuilder("from ").append(clazz.getSimpleName()).append(" where 1=1");
		if (MapUtils.isNotEmpty(conditionMap)) {
			Set<String> sets = conditionMap.keySet();
			for (String property : sets) {
				hql.append(" and ").append(property).append("=:").append(property);
			}
		}
		if (StringUtils.isNotEmpty(order))
			hql.append(" order by ").append(order);
		Session session = getSession();
		Query query = session.createQuery(hql.toString());
		if (MapUtils.isNotEmpty(conditionMap)) {
			for (Map.Entry<String, Object> entry : conditionMap.entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return query.list();
	}

	public List<T> _findListByHql(String hql, Serializable... values) {
		Session session = getSession();
		Query query = session.createQuery(hql);
		if (ArrayUtils.isNotEmpty(values)) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query.list();
	}

	public List<T> _findList(Class<T> clazz, String whereHQL, String order) {
		Session session = getSession();
		String hql = "from " + clazz.getSimpleName();
		if (StringUtils.isNotEmpty(whereHQL)) {
			if (StringUtils.startsWithIgnoreCase(whereHQL.trim(), "and")) {
				hql += " where 1=1 " + whereHQL;
			} else {
				hql += " where 1=1 and " + whereHQL;
			}
		}
		if (StringUtils.isNotEmpty(order))
			hql += " order by " + order;
		Query query = session.createQuery(hql);
		List<T> pageList = query.list();
		return pageList;
	}

	public List<T> _findPageList(Class<T> clazz, int start, int pageNumber, String whereHQL, String order) {
		Session session = getSession();
		String hql = "from " + clazz.getSimpleName();
		if (StringUtils.isNotEmpty(whereHQL)) {
			if (StringUtils.startsWithIgnoreCase(whereHQL.trim(), "and")) {
				hql += " where 1=1 " + whereHQL;
			} else {
				hql += " where 1=1 and " + whereHQL;
			}
		}
		if (StringUtils.isNotEmpty(order))
			hql += " order by " + order;
		Query query = session.createQuery(hql);
		query.setFirstResult(start);
		query.setMaxResults(pageNumber);
		List<T> pageList = query.list();
		return pageList;
	}

	// ---------------------------------------saveOrUpdate----------------------------------------------------
	public T _save(T entity) {
		try {
			getHibernateTemplate().save(entity);
		} catch (Exception e) {
			throw new RuntimeException("保存失败..." + e.getMessage());
		}
		return entity;
	}

	public T _update(T entity) {
		try {
			getHibernateTemplate().update(entity);
		} catch (Exception e) {
			throw new RuntimeException("更新失败..." + e.getMessage());
		}
		return entity;
	}

	public T _saveOrUpdate(T entity) {
		try {
			getHibernateTemplate().saveOrUpdate(entity);
		} catch (Exception e) {
			throw new RuntimeException("保存更新失败..." + e.getMessage());
		}
		return entity;
	}

	public void _saveOrUpdateCollection(Collection<T> collection) {
		try {
			getHibernateTemplate().saveOrUpdateAll(collection);
		} catch (Exception e) {
			throw new RuntimeException("保存更新失败..." + e.getMessage());
		}
	}

	public int _updateBatch(String hql, Serializable... values) {
		int size = 0;
		try {
			//size = getHibernateTemplate().bulkUpdate(hql, values);
			Session session = getSession();
			Query query = session.createQuery(hql);
			if (ArrayUtils.isNotEmpty(values)) {
				for (int i = 0; i < values.length; i++) {
					query.setParameter(i, values[i]);
				}
			}
			size = query.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException("保存更新失败..." + e.getMessage());
		}
		return size;
	}

	// ---------------------------------------delete----------------------------------------------------
	public void _delete(T entity) {
		try {
			getHibernateTemplate().delete(entity);
		} catch (Exception e) {
			throw new RuntimeException("删除失败..." + e.getMessage());
		}
	}

	public void _deleteById(Class<T> clazz, Serializable id) {
		try {
			_delete(_load(clazz, id));
		} catch (Exception e) {
			throw new RuntimeException("删除失败;resourceid=" + id + "..." + e.getMessage());
		}
	}

	public void _deleteByIds(Class<T> clazz, Serializable... ids) {
		if (ArrayUtils.isNotEmpty(ids)) {
			for (int i = 0; i < ids.length; i++) {
				_deleteById(clazz, ids[i]);
			}
		}
	}

	public void _deleteCollection(Collection<?> collection) {
		try {
			if (CollectionUtils.isNotEmpty(collection))
				getHibernateTemplate().deleteAll(collection);
		} catch (Exception e) {
			throw new RuntimeException("删除失败.." + e.getMessage());
		}
	}

	public int _deleteStateByIds(Class<T> clazz, Object[] ids) {
		int size = 0;
		try {
			String hql = "UPDATE " + clazz.getSimpleName() + " SET deleteState='y' WHERE resourceid in (:ids)";
			Session session = getSession();
			Query query = session.createQuery(hql);
			query.setParameterList("ids", ids);
			size = query.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException("软删除失败.." + e.getMessage());
		}
		return size;
	}

	// ---------------------------------------Connection----------------------------------------------------
	/*
	 * public void _closeConn(Connection conn, PreparedStatement stmt, ResultSet
	 * rs) { try { if (rs != null) rs.close(); if (stmt != null) stmt.close();
	 * if (conn != null) conn.close(); } catch (SQLException e) { throw new
	 * RuntimeException("关闭Connection失败..." + e.getMessage()); } }
	 * 
	 * public Connection _getConnection() { Connection conn = null; DataSource
	 * ds = SessionFactoryUtils.getDataSource(getSessionFactory()); try { conn =
	 * ds.getConnection(); } catch (SQLException e) { throw new
	 * RuntimeException("获取Connection失败..." + e.getMessage()); } return conn; }
	 */

}
