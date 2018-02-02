package com.crd.demo.system.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crd.demo.common.controller.BaseController;
import com.crd.demo.common.model.PageView;
import com.crd.demo.common.utils.HibernateQueryFilter;
import com.crd.demo.system.service.IUserService;

@Controller
@RequestMapping("system/user")
public class UserDemoController extends BaseController{
	@Autowired
	private IUserService userService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String List(){
		return "weChat/customer/customerList";
	}
	
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getDate(HttpServletRequest request){
		
		PageView page = getPage(request);
		String whereClause=HibernateQueryFilter.buildWhereFromHttpRequest(request);
		page=userService.findList(page,whereClause);
		return getViewData(page);
	
	}
		
}
