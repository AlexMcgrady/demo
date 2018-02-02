package com.crd.demo.common.service.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crd.demo.common.dao.IBaseDao;
import com.crd.demo.common.model.HibernateSort;
import com.crd.demo.common.model.PageView;
import com.crd.demo.common.service.IBaseService;

@Transactional
@Service
public class BaseServiceImpl implements IBaseService{
	@Autowired
	protected IBaseDao hibernateDao;
	
	@Override
	public <T, PK extends Serializable> T get(PK id, Class<? extends T> clz) {
		return hibernateDao.get(id, clz);
	}

	@Override
	@Transactional(readOnly = false)
	public <T> void create(T t) {
		hibernateDao.create(t);
	}

	@Override
	@Transactional(readOnly = false)
	public <T> void update(T t) {
		hibernateDao.update(t);
	}

	@Override
	@Transactional(readOnly = false)
	public <T> void delete(T t) {
		hibernateDao.delete(t);
	}

	@Override
	@Transactional(readOnly = false)
	public <T, PK extends Serializable> void delete(PK id, Class<? extends T> clz) {
		hibernateDao.delete(id, clz);
	}

	@Override
	public <T> List<T> findAll(Class<? extends T> clz) {
		return hibernateDao.findAll(clz);
	}

	@Override
	public <T> T findUnique(Class<? extends T> clz, Criterion... criterions) {
		return hibernateDao.findUnique(clz, criterions);
	}

	@Override
	public <T> List<T> findList(Class<? extends T> clz, HibernateSort sort,
			Criterion... criterions) {
		return hibernateDao.findList(clz, sort, criterions);
	}

	@Override
	public <T> PageView findPage(Class<? extends T> clz, HibernateSort sort, PageView page,
			Criterion... criterions) {
		return hibernateDao.findPage(clz, sort, page, criterions);
	}

	@Override
	public <T> T findUnique(Class<? extends T> clz, List<Criterion> criterions) {
		return hibernateDao.findUnique(clz, criterions);
	}

	@Override
	public <T> List<T> findList(Class<? extends T> clz, HibernateSort sort,
			List<Criterion> criterions) {
		return hibernateDao.findList(clz, sort, criterions);
	}

	@Override
	public <T> PageView findPage(Class<? extends T> clz, HibernateSort sort, PageView page,
			List<Criterion> criterions) {
		return hibernateDao.findPage(clz, sort, page, criterions);
	}
}
