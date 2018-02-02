package com.crd.demo.common.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Criterion;

import com.crd.demo.common.model.HibernateSort;
import com.crd.demo.common.model.PageView;



public interface IBaseService {
	/**
	 * @Description: 根据id属性查找唯一对象
	 * @param id 对象id属性
	 * @param clz 对象的class
	 * @return 对象实体
	 * @Since :2016年7月26日 下午3:51:46
	 */
	public <T, PK extends Serializable> T get(final PK id, Class<? extends T> clz);

	/**
	 * @Description: 创建对象
	 * @param t 待创建对象实体
	 * @Since :2016年7月26日 下午3:51:57
	 */
	public <T> void create(final T t);

	/**
	 * @Description: 修改或编辑对象，须先从数据库获取对象实体
	 * @param t 待修改或编辑的对象实体
	 * @Since :2016年7月26日 下午3:52:30
	 */
	public <T> void update(final T t);

	/**
	 * @Description: 删除对象
	 * @param t 待删除对象实体
	 * @Since :2016年7月26日 下午3:52:50
	 */
	public <T> void delete(final T t);

	/**
	 * @Description: 根据id属性删除对象
	 * @param id 对象id属性
	 * @param clz 对象的class
	 * @Since :2016年7月26日 下午3:53:09
	 */
	public <T, PK extends Serializable> void delete(final PK id, Class<? extends T> clz);

	/**
	 * @Description: 获取数据表中所有记录
	 * @param clz 对象的Class
	 * @return 对象记录列表
	 * @Since :2016年7月26日 下午3:55:37
	 */
	public <T> List<T> findAll(Class<? extends T> clz);

	/**
	 * @Description: 单表查询：根据标准查询条件查找符合条件的唯一实体记录
	 * @param clz 对象实体的class
	 * @param criterions 标准条件类
	 * @return 唯一对象实体
	 * @Since :2016年7月26日 下午3:54:42
	 */
	public <T> T findUnique(Class<? extends T> clz, Criterion... criterions);

	/**
	 * @Description: 单表查询：根据标准查询条件查找符合条件的唯一实体记录
	 * @param clz 对象实体的class
	 * @param criterions 标准条件类列表
	 * @return 唯一对象实体
	 * @Since :2016年7月26日 下午3:54:42
	 */
	public <T> T findUnique(Class<? extends T> clz, List<Criterion> criterions);
	
	/**
	 * @Description: 单表查询：根据标准查询条件查找符合条件的对象列表
	 * @param clz 对象的class
	 * @param sort 排序参数类
	 * @param criterions 标准条件类
	 * @return 对象列表
	 * @Since :2016年7月26日 下午3:56:01
	 */
	public <T> List<T> findList(Class<? extends T> clz, HibernateSort sort,
			Criterion... criterions);
	
	/**
	 * @Description: 单表查询：根据标准查询条件查找符合条件的对象列表
	 * @param clz 对象的class
	 * @param sort 排序参数类
	 * @param criterions 标准条件类列表
	 * @return 对象列表
	 * @Since :2016年7月26日 下午3:56:01
	 */
	public <T> List<T> findList(Class<? extends T> clz, HibernateSort sort,
			List<Criterion> criterions);

	/**
	 * @Description: 单表查询：根据标准查询条件，分页查找符合条件的实体记录列表
	 * @param clz 实体的class
	 * @param sorts 排序参数列表
	 * @param page 封装的分页对象
	 * @param criterions 标准查询类
	 * @return 封装的分页对象
	 * @Since :2016年7月26日 下午3:56:08
	 */
	public <T> PageView findPage(Class<? extends T> clz, HibernateSort sort, PageView page,
			Criterion... criterions);
	
	/**
	 * @Description: 单表查询：根据标准查询条件，分页查找符合条件的实体记录列表
	 * @param clz 实体的class
	 * @param sort 排序参数类
	 * @param page 封装的分页对象
	 * @param criterions 标准查询类列表
	 * @return 封装的分页对象
	 * @Since :2016年7月26日 下午3:56:08
	 */
	public <T> PageView findPage(Class<? extends T> clz, HibernateSort sort, PageView page,
			List<Criterion> criterions);
}
