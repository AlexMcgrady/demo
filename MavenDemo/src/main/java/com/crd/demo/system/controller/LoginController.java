package com.crd.demo.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.crd.demo.common.controller.BaseController;
import com.crd.demo.system.utils.PropertiesUtils;
@Controller
@RequestMapping("${adminPath}")
public class LoginController extends BaseController{
	public static final String WEB_PATH = PropertiesUtils.getString("adminPath");
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login() {
		
		System.out.println("2213");
		return WEB_PATH + "/index";
	}
}
