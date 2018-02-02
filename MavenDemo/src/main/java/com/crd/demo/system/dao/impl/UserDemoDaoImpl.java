package com.crd.demo.system.dao.impl;

import org.springframework.stereotype.Repository;

import com.crd.demo.common.dao.impl.HibernateDaoImpl;
import com.crd.demo.common.model.PageView;
import com.crd.demo.system.dao.IUserDao;

@Repository
public class UserDemoDaoImpl extends HibernateDaoImpl implements IUserDao{

	@Override
	public PageView findList(PageView page, String whereClause) {
		StringBuffer hql = new StringBuffer();
		hql.append("from User ").append(whereClause)
		.append(" order by id asc");
		return findPageByHQL(hql.toString(), page);
	}

}
