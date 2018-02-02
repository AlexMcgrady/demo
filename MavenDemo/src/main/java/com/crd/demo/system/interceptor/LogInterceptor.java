package com.crd.demo.system.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.crd.demo.common.mapper.JsonMapper;
import com.crd.demo.common.utils.DateUtils;
import com.crd.demo.common.utils.StringUtils;
import com.crd.demo.system.entity.Log;
import com.crd.demo.system.service.ILogService;
import com.crd.demo.system.utils.IPUtils;
import com.crd.demo.system.utils.UserUtils;

import eu.bitwalker.useragentutils.UserAgent;








/**
 * 日志拦截器
 * @date 2015年1月14日
 */
public class LogInterceptor implements HandlerInterceptor{
	@Autowired
	private ILogService logService;
	
	private Long beginTime;// 1、开始时间
	private Long endTime;// 2、结束时间

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		beginTime = System.currentTimeMillis();//计时
		return true;
	}

	public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		endTime = System.currentTimeMillis();
		String requestRri = request.getRequestURI();
		String uriPrefix = request.getContextPath();
		String operationCode=StringUtils.substringAfter(requestRri,uriPrefix);	//操作编码
		
		String requestParam=(new JsonMapper()).toJson(request.getParameterMap());	//请求参数
		
		//如果是GET请求，请求编码包含create，update(添加修改页)不记录日志
		if(request.getMethod().equals("GET")){
			if(operationCode.contains("create")||operationCode.contains("update")){
				return;
			}
		}
		Long executeTime=endTime-beginTime;
		String userAgentString=request.getHeader("User-Agent");
		UserAgent userAgent = UserAgent.parseUserAgentString(userAgentString); 
		String browser=userAgent.getOperatingSystem().getName();//获取客户端浏览器
		String os=userAgent.getOperatingSystem().getName();	//获取客户端操作系统
		if(browser.isEmpty()){
			browser=userAgent.getBrowser().getName();
		}
		Log log=new Log();
		log.setOs(os);
		log.setBrowser(browser);
		log.setIp(IPUtils.getIpAddress(request));
		log.setOperationCode(operationCode);
		log.setExecuteTime(Integer.valueOf(executeTime.toString()));
		log.setCreater(UserUtils.getCurrentUser().getName());
		log.setCreateDate(DateUtils.getSysTimestamp());
		//log.setDescription(LogCodeUtil.matchingOperationCode(operationCode));
		log.setRequestParam(requestParam);
		
		//放到一公共变量里，定时提交
		logService.create(log);
	}
}
