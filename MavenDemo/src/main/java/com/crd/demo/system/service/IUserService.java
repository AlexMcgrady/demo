package com.crd.demo.system.service;

import com.crd.demo.common.model.PageView;
import com.crd.demo.common.service.IBaseService;



public interface IUserService extends IBaseService{

	PageView findList(PageView page,String whereClause);

}
