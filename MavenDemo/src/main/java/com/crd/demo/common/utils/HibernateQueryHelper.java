package com.crd.demo.common.utils;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.crd.demo.common.model.HibernateSort;
import com.crd.demo.common.model.HibernateSort.Direction;
import com.crd.demo.common.model.HibernateSort.Sort;
import com.crd.demo.common.model.PageView;



/**
 * @Description:提供封装Hibernate查询相关的方法<br>适用于Hibernate4.x版本

 */
@Component
public class HibernateQueryHelper {
	@Autowired
	private SessionFactory hibernateSessionFactory;

	/**
	 * 取得当前Session.
	 * 
	 * @return Session
	 */
	public Session getSession() {
		return hibernateSessionFactory.getCurrentSession();
	}

	/**
	 * 设置分页参数和排序参数到Criteria对象,辅助函数.
	 */
	protected Criteria setParameterToCriteria(final Criteria c, final PageView page,
			HibernateSort sort) {
		Assert.isTrue(page.getPageSize() > 0, "Page Size must larger than zero");
		// hibernate的firstResult的序号从0开始
		c.setFirstResult(page.getViewFirstIndex());
		c.setMaxResults(page.getPageSize());
		setSortParameterToCriteria(c, sort);
		return c;
	}
	
	/**
	 * @Description: 设置排序参数到Criteria对象中
	 * @Since :2016年7月19日 下午2:32:20
	 */
	protected Criteria setSortParameterToCriteria(final Criteria c, HibernateSort sort) {
		if (sort != null) {
			List<Sort> sorts = sort.getSorts();
			for (Sort _sort: sorts) {
				if (Direction.ASC.equals(_sort.getDirection())) {
					c.addOrder(Order.asc(_sort.getParamName()));
				} else {
					c.addOrder(Order.desc(_sort.getParamName()));
				}
			}
		}
		return c;
	}

	/**
	 * 根据查询HQL与参数列表创建Query对象.
	 * 
	 * @param queryString
	 * @param values 数量可变的参数,按顺序绑定.
	 * @return Query
	 */
	public Query createQuery(final String queryString, final Object... values) {
		Assert.hasText(queryString, "queryString不能为空");
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(String.valueOf(i), values[i]);
			}
		}
		return query;
	}

	/**
	 * 根据查询HQL与参数列表创建Query对象. 与find()函数可进行更加灵活的操作.
	 * 
	 * @param queryString
	 * @param values 命名参数,按名称绑定.
	 * @return Query
	 */
	public Query createQuery(final String queryString, final Map<String, ?> values) {
		Assert.hasText(queryString, "queryString不能为空");
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			query.setProperties(values);
		}
		return query;
	}

	/**
	 * 根据查询SQL与参数列表创建Query对象.
	 * 
	 * @param queryString
	 * @param values 数量可变的参数,按顺序绑定.
	 * @return SQLQuery
	 */
	public SQLQuery createSQLQuery(final String queryString, final Object... values) {
		SQLQuery sqlQuery = getSession().createSQLQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				sqlQuery.setParameter(String.valueOf(i), values[i]);
			}
		}
		return sqlQuery;
	}

	/**
	 * 根据查询SQL与参数列表创建Query对象.
	 * 
	 * @param queryString
	 * @param values 命名参数,按名称绑定.
	 * @return SQLQuery
	 */
	public SQLQuery createSQLQuery(final String queryString, final Map<String, ?> values) {
		SQLQuery sqlQuery = getSession().createSQLQuery(queryString);
		if (values != null) {
			sqlQuery.setProperties(values);
		}
		return sqlQuery;
	}

	/**
	 * 根据Criterion条件创建Criteria. 与find()函数可进行更加灵活的操作.
	 * 
	 * @param criterions 数量可变的Criterion.
	 * @return Criteria
	 */
	public Criteria createCriteria(Class<?> clz, final Criterion...criterions) {
		Criteria criteria = getSession().createCriteria(clz);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	public Criteria createCriteria(Boolean isCache, Class<?> clz, final Criterion...criterions) {
		Criteria criteria = getSession().createCriteria(clz);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		criteria.setCacheable(isCache);
		return criteria;
	}

	/**
	 * 初始化对象. 使用load()方法得到的仅是对象Proxy, 在传到View层前需要进行初始化. 如果传入entity,
	 * 则只初始化entity的直接属性,但不会初始化延迟加载的关联集合和属性. 如需初始化关联属性,需执行:
	 * Hibernate.initialize(user.getRoles())，初始化User的直接属性和关联集合.
	 * Hibernate.initialize(user.getDescription())，初始化User的直接属性和延迟加载的Description属性.
	 */
	public void initProxyObject(Object proxy) {
		Hibernate.initialize(proxy);
	}

	/**
	 * 为Query添加distinct transformer. 预加载关联对象的HQL会引起主对象重复, 需要进行distinct处理.
	 * 
	 * @param query
	 * @return Query
	 */
	public Query distinct(Query query) {
		query.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return query;
	}

	/**
	 * 为Criteria添加distinct transformer. 预加载关联对象的HQL会引起主对象重复, 需要进行distinct处理.
	 * 
	 * @param criteria
	 * @return Criteria
	 */
	public Criteria distinct(Criteria criteria) {
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria;
	}
}
