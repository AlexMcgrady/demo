package com.crd.demo.system.utils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.crd.demo.common.utils.Global;

public class AjaxFilter implements Filter{
public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	
	public void doFilter(ServletRequest servletRequestt, ServletResponse servletResponse,
			FilterChain chain) throws IOException, ServletException {
		 HttpServletRequest request=(HttpServletRequest) servletRequestt;
		 HttpServletResponse response=(HttpServletResponse) servletResponse;
		 
		 String currentURL = request.getRequestURI();//取得根目录所对应的绝对路径:   
		 String targetURL = currentURL.substring(currentURL.indexOf("/", 1), currentURL.length());  //截取到当前文件名用于比较
		 
		 String ajaxSubmit = request.getHeader("X-Requested-With");
		 if(!targetURL.equals(Global.getAdminPath()+"/login")&&!targetURL.contains("/static")&&!targetURL.contains("/app")){
			 if(ajaxSubmit != null && ajaxSubmit.equals("XMLHttpRequest")){
				 if (request.getSession(false) == null) {
					 response.setHeader("sessionstatus", "timeout");
					 response.getWriter().print("sessionstatus");
					 return;
				 }
			 }else{
				 if(request.getSession(false)==null){
					 String loginUrl=Global.getAdminPath()+"/login"; 
					 response.setContentType("text/html; charset=utf-8");
					 response.setCharacterEncoding("UTF-8");
					 response.getWriter().print("<script type='text/javascript'>window.top.location.href='"+ request.getContextPath()+loginUrl+"';</script>");
					 return;
				 }
			 }
		 }
		 chain.doFilter(servletRequestt, servletResponse);
	}

	public void destroy() {
		
	}

}
