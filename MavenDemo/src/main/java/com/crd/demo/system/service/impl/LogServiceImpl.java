package com.crd.demo.system.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crd.demo.common.service.impl.BaseServiceImpl;
import com.crd.demo.system.service.ILogService;
@Service("logService")
@Transactional(readOnly = true)
public class LogServiceImpl extends BaseServiceImpl implements
ILogService{



}
