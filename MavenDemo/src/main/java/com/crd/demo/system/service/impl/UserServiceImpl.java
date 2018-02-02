package com.crd.demo.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crd.demo.common.model.PageView;
import com.crd.demo.common.service.impl.BaseServiceImpl;
import com.crd.demo.system.dao.IUserDao;
import com.crd.demo.system.service.IUserService;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl extends BaseServiceImpl implements IUserService{
	@Autowired
	private IUserDao userDao;

	@Override
	public PageView findList(PageView page, String whereClause) {
		// TODO Auto-generated method stub
		return userDao.findList(page,whereClause);
	}


}
