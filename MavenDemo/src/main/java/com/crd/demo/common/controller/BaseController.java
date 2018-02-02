package com.crd.demo.common.controller;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.crd.demo.common.model.HibernateSort;
import com.crd.demo.common.model.HibernateSort.Direction;
import com.crd.demo.common.model.HibernateSort.Sort;
import com.crd.demo.common.model.PageView;
import com.crd.demo.common.persistence.Page;
import com.crd.demo.common.utils.DateUtils;
import com.crd.demo.common.utils.StringUtils;
import com.crd.demo.system.entity.User;

/**
 * 基础控制器 
 * 其他控制器继承此控制器获得日期字段类型转换和防止XSS攻击的功能
 * @description 
 * @author ty
 * @date 2014年3月19日
 */
public class BaseController {
	
	
	public static final String PAGE_PARAM = "offset";
	public static final String PAGE_SIZE_PARAM = "limit";
	public static final String PAGE_RESULT_PARAM = "rows";
	public static final String PAGE_RESULT_TOTAL = "total";
	public static final HibernateSort BASE_SORT= new HibernateSort(new Sort(Direction.ASC, "orderSort"));
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
		binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
			}
			@Override
			public String getAsText() {
				Object value = getValue();
				return value != null ? value.toString() : "";
			}
		});
		
		// Date 类型转换
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(DateUtils.parseDate(text));
			}
		});
		
		// Timestamp 类型转换
		binder.registerCustomEditor(Timestamp.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				Date date = DateUtils.parseDate(text);
				setValue(date==null?null:new Timestamp(date.getTime()));
			}
		});
	}
	
	/**
	 * 获取page对象
	 * @param request
	 * @return page对象
	 */
	public PageView getPage(HttpServletRequest request){
		int pageNo=1;	//当前页码
		int pageSize=20;	//每页行数
		String pageStr = request.getParameter(PAGE_PARAM);
		String pageSizeStr = request.getParameter(PAGE_SIZE_PARAM);
		if(StringUtils.isNotEmpty(pageStr)) {
			pageNo=Integer.valueOf(pageStr);
		}
		if(StringUtils.isNotEmpty(pageSizeStr)) {
			pageSize=Integer.valueOf(pageSizeStr);
		}
		pageNo = pageNo == 0 ? pageNo : pageNo / pageSize + 1;
		return new PageView(pageNo, pageSize);
	}
	
	public Map<String, Object> getViewData(PageView page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", page.getResult());
		map.put("total", page.getTotalCount());
		return map;
	}
	
	/**
	 * 获取easyui分页数据
	 * @param page
	 * @return map对象
	 */
	public <T> Map<String, Object> getEasyUIData(Page<T> page){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", page.getResult());
		map.put("total", page.getTotalCount());
		return map;
	}
	
	/**
	 * 用来获取当前登录用户
	 * @return 当前登录用户
	 */
	public User getCurUser() {
		
		//Object = null;
		User curUser = (User) SecurityUtils.getSubject().getPrincipal();
		return curUser;
	}
}
