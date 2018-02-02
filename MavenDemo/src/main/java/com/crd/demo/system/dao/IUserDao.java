package com.crd.demo.system.dao;

import com.crd.demo.common.dao.IBaseDao;
import com.crd.demo.common.model.PageView;

public interface IUserDao extends IBaseDao{

	PageView findList(PageView page, String whereClause);

}
