package com.crd.demo.common.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Criterion;

import com.crd.demo.common.model.HibernateSort;
import com.crd.demo.common.model.PageView;

/**
 * @Description:公共数据访问层接口类
 * @Author:chenrundong
 * @Since :2018年1月25日  下午4:48:47
 */
public interface IBaseDao {
	/**
	 * @Description: 创建对象
	 * @param t 待创建对象实体
	 * @Since :2016年7月14日 下午3:37:22
	 */
	public <T> void create(T t);

	/**
	 * @Description: 修改或编辑对象，须先从数据库获取对象实体
	 * @param t 待修改或编辑的对象实体
	 * @Since :2016年7月14日 下午3:38:08
	 */
	public <T> void update(T t);

	/**
	 * @Description: 删除对象
	 * @param t 待删除对象实体
	 * @Since :2016年7月14日 下午3:39:37
	 */
	public <T> void delete(T t);

	/**
	 * @Description: 根据id属性删除对象
	 * @param id 对象id属性
	 * @param clz 对象的class
	 * @Since :2016年7月14日 下午3:47:58
	 */
	public <T, PK extends Serializable> void delete(PK id, Class<? extends T> clz);

	/**
	 * @Description: 根据id属性查找唯一对象
	 * @param id 对象id属性
	 * @param clz 对象的class
	 * @return 对象实体
	 * @Since :2016年7月14日 下午3:50:11
	 */
	public <T, PK extends Serializable> T get(PK id, Class<? extends T> clz);

	/**
	 * @Description: 单表查询：根据标准查询条件查找符合条件的唯一实体记录
	 * @param clz 对象实体的class
	 * @param criterions 标准条件类
	 * @return 唯一对象实体
	 * @Since :2016年7月15日 下午2:23:23
	 */
	public <T> T findUnique(Class<? extends T> clz, Criterion... criterions);
	
	/**
	 * @Description: 单表查询：根据标准查询条件查找符合条件的唯一实体记录
	 * @param clz 对象实体的class
	 * @param criterions 标准条件类列表
	 * @return 唯一对象实体
	 * @Since :2016年7月29日 上午11:46:47
	 */
	public <T> T findUnique(Class<? extends T> clz, List<Criterion> criterions);

	/**
	 * @Description: 通过hql语句查询唯一对象记录
	 * @param hql HQL语句
	 * @param params 查询条件参数
	 * @return 唯一对象实体
	 * @Since :2016年7月15日 下午2:26:23
	 */
	public <X> X findUniqueByHQL(String hql, Object... params);
	
	/**
	 * @Description: 根据HQL语句查询唯一实体对象记录
	 * @param hql HQL语句
	 * @param clz 实体对象的class
	 * @param params 查询条件参数
	 * @return 唯一实体对象
	 * @Since :2017年9月25日 下午2:14:04
	 */
	public <T> T findUniqueByHQLAsBean(String hql, Class<? extends T> clz, Object... params);

	/**
	 * @Description: 通过sql语句查询唯一对象记录
	 * @param sql SQL语句
	 * @param params 查询条件参数
	 * @return 唯一对象
	 * @Since :2016年7月15日 下午2:33:20
	 */
	public <X> X findUniqueBySQL(String sql, Object... params);
	
	/**
	 * @Description: 通过sql语句查询唯一实体类对象
	 * @param sql SQL语句
	 * @param clz 用于封装结果的实体对象类
	 * @param params 查询条件参数
	 * @return 唯一实体对象
	 * @Since :2017年9月25日 上午11:00:14
	 */
	public <T> T findUniqueBySQLAsBean(String sql, Class<? extends T> clz, Object... params);

	/**
	 * @Description: 获取数据表中所有记录
	 * @param clz 对象的Class
	 * @return 对象记录列表
	 * @Since :2016年7月15日 上午10:06:12
	 */
	public <T> List<T> findAll(Class<? extends T> clz);

	/**
	 * @Description: 单表查询：根据标准查询条件查找符合条件的对象列表
	 * @param clz 对象的class
	 * @param sort 排序参数类
	 * @param criterions 标准条件类
	 * @return 对象列表
	 * @Since :2016年7月15日 下午2:36:41
	 */
	public <T> List<T> findList(Class<? extends T> clz, HibernateSort sort,
			Criterion... criterions);

	/**
	 * @Description: 单表查询：根据标准查询条件查找符合条件的对象列表
	 * @param clz 对象的class
	 * @param sort 排序参数类
	 * @param criterions 标准条件类列表
	 * @return 对象列表
	 * @Since :2016年7月15日 下午2:36:41
	 */
	public <T> List<T> findList(Class<? extends T> clz, HibernateSort sort,
			List<Criterion> criterions);
	
	/**
	 * @Description: 单表查询：根据标准查询条件，分页查找符合条件的实体记录列表
	 * @param clz 实体的class
	 * @param sort 排序参数类
	 * @param page 封装的分页对象
	 * @param criterions 标准查询类
	 * @return 封装的分页对象
	 * @Since :2016年7月15日 下午2:47:54
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
	 * @Since :2016年7月15日 下午2:47:54
	 */
	public <T> PageView findPage(Class<? extends T> clz, HibernateSort sort, PageView page,
			List<Criterion> criterions);

	/**
	 * @Description: 根据hql语句查询数据记录列表
	 * @param hql HQL语句
	 * @param params 查询条件参数
	 * @return 数据记录列表
	 * @Since :2016年7月15日 下午2:50:45
	 */
	public <X> List<X> findListByHQL(String hql, Object... params);
	
	/**
	 * @Description: 根据HQL语句查询实体对象的数据记录列表
	 * @param hql HQL语句
	 * @param clz 实体对象的class
	 * @param params 查询条件参数
	 * @return 实体对象列表
	 * @Since :2017年9月25日 下午2:26:43
	 */
	public <T> List<T> findListByHQLAsBean(String hql, Class<? extends T> clz, Object... params);

	/**
	 * @Description: 根据hql语句分页查询数据记录列表
	 * @param hql HQL语句
	 * @param page 封装的分页对象
	 * @param params 查询条件参数
	 * @return 封装的分页对象
	 * @Since :2016年7月15日 下午2:58:00
	 */
	public PageView findPageByHQL(String hql, PageView page, Object... params);
	
	/**
	 * @Description: 根据hql语句分页查询数据记录列表
	 * @param hql HQL语句
	 * @param page 封装的分页对象
	 * @param clz 实体对象的class
	 * @param params 查询条件参数
	 * @return 封装的分页对象
	 * @Since :2016年7月15日 下午2:58:00
	 */
	public PageView findPageByHQLAsBean(String hql, PageView page, Class<?> clz, Object... params);

	/**
	 * @Description: 根据sql语句查询数据记录列表
	 * @param sql SQL语句
	 * @param params 查询条件参数
	 * @return 数据记录列表
	 * @Since :2016年7月15日 下午3:01:44
	 */
	public <X> List<X> findListBySQL(String sql, Object... params);
	
	/**
	 * @Description: 根据sql语句查询实体对象数据记录列表
	 * @param sql SQL语句
	 * @param clz 用于封装结果的实体对象类
	 * @param params 查询条件参数
	 * @return 实体对象数据记录列表
	 * @Since :2017年9月25日 上午11:05:21
	 */
	public <T> List<T> findListBySQLAsBean(String sql, Class<? extends T> clz, Object... params);

	/**
	 * @Description: 根据sql语句分页查询数据记录列表
	 * @param sql SQL语句
	 * @param page 封装的分页对象
	 * @param params 查询条件参数
	 * @return 封装的分页对象
	 * @Since :2016年7月15日 下午3:05:04
	 */
	public PageView findPageBySQL(String sql, PageView page, Object... params);
	
	/**
	 * @Description: 根据sql语句分页查询数据记录列表
	 * @param sql SQL语句
	 * @param page 封装的分页对象
	 * @param clz 用于封装结果的实体对象类
	 * @param params 查询条件参数
	 * @return 封装的分页对象
	 * @Since :2016年7月15日 下午3:05:04
	 */
	public PageView findPageBySQLAsBean(String sql, PageView page, Class<?> clz, Object... params);
	
	/**
	 * @Description: 执行HQL语句进行批量修改或删除操作.
	 * @param hql HQL语句
	 * @param params 查询条件参数
	 * @return 执行过的记录数
	 * @Since :2017年9月25日 下午2:51:49
	 */
	public int executeByHQL(String hql, Object... params);
	
	/**
	 * @Description: 执行SQL语句进行批量修改或删除操作.
	 * @param sql SQL语句
	 * @param params 查询条件参数
	 * @return 执行过的记录数
	 * @Since :2017年9月25日 下午2:51:49
	 */
	public int executeBySQL(String sql, Object... params);
	

}
