package com.crd.demo.common.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.crd.demo.common.dao.IBaseDao;
import com.crd.demo.common.model.HibernateSort;
import com.crd.demo.common.model.PageView;
import com.crd.demo.common.utils.HibernateQueryHelper;



@SuppressWarnings("unchecked")
@Repository("hibernateDao")
public class HibernateDaoImpl extends
HibernateQueryHelper implements IBaseDao {
	/**
	 * 用于Dao层子类的构造函数. 通过子类的泛型定义取得对象类型Class.
	 */
	@Override
	public <T> void create(T t) {
		getSession().save(t);
	}

	@Override
	public <T> void update(T t) {
		getSession().update(t);
	}

	@Override
	public <T> void delete(T t) {
		getSession().delete(t);
	}

	@Override
	public <T, PK extends Serializable> void delete(PK id, Class<? extends T> clz) {
		delete(get(id, clz));
	}

	@Override
	public <T, PK extends Serializable> T get(PK id, Class<? extends T> clz) {
		return (T) getSession().load(clz, id);
	}

	@Override
	public <T> T findUnique(Class<? extends T> clz, final Criterion... criterions) {
		Criteria criteria = createCriteria(clz, criterions);
		return (T) criteria.uniqueResult();
	}
	
	@Override
	public <T> T findUnique(Class<? extends T> clz, final List<Criterion> criterions) {
		return findUnique(clz, criterions.toArray(new Criterion[criterions.size()]));
	}

	@Override
	public <X> X findUniqueByHQL(final String hql, final Object... params) {
		Query query = createQuery(hql, params);
		return (X)query.uniqueResult();
	}
	
	public <T> T findUniqueByHQLAsBean(String hql, Class<? extends T> clz, Object... params) {
		Query query = createQuery(hql, params);
		query.setResultTransformer(Transformers.aliasToBean(clz));
		return (T)query.uniqueResult();
	}

	@Override
	public <X> X findUniqueBySQL(final String sql, final Object... params) {
		SQLQuery sqlQuery = createSQLQuery(sql, params);
		return (X)sqlQuery.uniqueResult();
	}
	
	public <T> T findUniqueBySQLAsBean(String sql, Class<? extends T> clz, Object... params) {
		SQLQuery sqlQuery = createSQLQuery(sql, params);
		sqlQuery.setResultTransformer(Transformers.aliasToBean(clz));
		return (T)sqlQuery.uniqueResult();
	}

	@Override
	public <T> List<T> findAll(Class<? extends T> clz) {
		return findList(clz, null);
	}

	@Override
	public <T> List<T> findList(Class<? extends T> clz, final HibernateSort sort,
			final Criterion... criterions) {
		Criteria criteria = createCriteria(clz, criterions);
		setSortParameterToCriteria(criteria, sort);
		return criteria.list();
	}
	
	@Override
	public <T> List<T> findList(Class<? extends T> clz, final HibernateSort sort,
			final List<Criterion> criterions) {
		return findList(clz, sort, criterions.toArray(new Criterion[criterions.size()]));
	}

	@Override
	public <T> PageView findPage(Class<? extends T> clz, final HibernateSort sort,
			final PageView page, final Criterion... criterions) {
		Criteria criteria = createCriteria(clz, criterions);
		if (page.isAutoCount()) {
			page.setTotalCount(criteria.list().size());
		}
		setParameterToCriteria(criteria, page, sort);
		page.setResult(criteria.list());
		return page;
	}
	
	@Override
	public <T> PageView findPage(Class<? extends T> clz, final HibernateSort sort,
			final PageView page, final List<Criterion> criterions) {
		return findPage(clz, sort, page, criterions.toArray(new Criterion[criterions.size()]));
	}

	@Override
	public <X> List<X> findListByHQL(final String hql, final Object... params) {
		Query query = createQuery(hql, params);
		return query.list();
	}
	
	public <T> List<T> findListByHQLAsBean(String hql, Class<? extends T> clz, Object... params) {
		Query query = createQuery(hql, params);
		query.setResultTransformer(Transformers.aliasToBean(clz));
		return query.list();
	}

	@Override
	public PageView findPageByHQL(final String hql, final PageView page, final Object... params) {
		Query query = createQuery(hql, params);
		if (page.isAutoCount()) {
			page.setTotalCount(query.list().size());
		}
		query.setFirstResult(page.getViewFirstIndex());
		query.setMaxResults(page.getPageSize());
		page.setResult(query.list());
		return page;
	}
	
	public PageView findPageByHQLAsBean(String hql, PageView page, Class<?> clz, Object... params) {
		Query query = createQuery(hql, params);
		if (page.isAutoCount()) {
			page.setTotalCount(query.list().size());
		}
		query.setFirstResult(page.getViewFirstIndex());
		query.setMaxResults(page.getPageSize());
		query.setResultTransformer(Transformers.aliasToBean(clz));
		page.setResult(query.list());
		return page;
	}

	@Override
	public <X> List<X> findListBySQL(final String sql, final Object... params) {
		SQLQuery sqlQuery = createSQLQuery(sql, params);
		return sqlQuery.list();
	}
	
	@Override
	public <T> List<T> findListBySQLAsBean(String sql, Class<? extends T> clz, Object... params) {
		SQLQuery sqlQuery = createSQLQuery(sql, params);
		sqlQuery.setResultTransformer(Transformers.aliasToBean(clz));
		return (List<T>)sqlQuery.list();
	}

	@Override
	public PageView findPageBySQL(final String sql, final PageView page, final Object... params) {
		SQLQuery sqlQuery = createSQLQuery(sql, params);
		if (page.isAutoCount()) {
			page.setTotalCount(sqlQuery.list().size());
		}
		sqlQuery.setFirstResult(page.getViewFirstIndex());
		sqlQuery.setMaxResults(page.getPageSize());
		page.setResult(sqlQuery.list());
		return page;
	}
	
	public PageView findPageBySQLAsBean(String sql, PageView page, Class<?> clz, Object... params) {
		SQLQuery sqlQuery = createSQLQuery(sql, params);
		if (page.isAutoCount()) {
			page.setTotalCount(sqlQuery.list().size());
		}
		sqlQuery.setFirstResult(page.getViewFirstIndex());
		sqlQuery.setMaxResults(page.getPageSize());
		sqlQuery.setResultTransformer(Transformers.aliasToBean(clz));
		page.setResult(sqlQuery.list());
		return page;
	}

	@Override
	public int executeByHQL(String hql, Object... params) {
		Query query = createQuery(hql, params);
		return query.executeUpdate();
	}

	@Override
	public int executeBySQL(String sql, Object... params) {
		SQLQuery sqlQuery = createSQLQuery(sql, params);
		return sqlQuery.executeUpdate();
	}

}
