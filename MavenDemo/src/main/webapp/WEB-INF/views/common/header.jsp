<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!-- 样式表 -->
<link rel="shortcut icon" href="${ctx}/static/images/favicon.ico">

<link rel="stylesheet" href="${ctx }/static/css/basic.css"> 
<link rel="stylesheet" href="${ctx }/static/css/table/tableAll.css">
<link rel="stylesheet" href="${ctx }/static/css/table/bootstrap-table-detail.css">
<link rel="stylesheet" href="${ctx }/static/plugins/select2/css/select2.css">
<link rel="stylesheet" href="${ctx }/static/plugins/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${ctx }/static/plugins/ztree/css/metroStyle/metroStyle.css">
<link rel="stylesheet" href="${ctx }/static/plugins/bootstrap-table/bootstrap-table.min.css">
<link rel="stylesheet" href="${ctx }/static/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.css">

<link rel="stylesheet" href="${ctx }/static/css/common.css">

<!-- 脚本库 -->
<script type="text/javascript" src="${ctx }/static/js/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="${ctx }/static/plugins/bootstrap/js/bootstrap.min.js"></script>

<script type="text/javascript" src="${ctx }/static/js/common.js"></script>
<script type="text/javascript" src="${ctx }/static/js/dateutils.js"></script>
<script type="text/javascript" src="${ctx }/static/js/jquery.form.js"></script>
<script type="text/javascript" src="${ctx }/static/js/nicescolling.js"></script>
<script type="text/javascript" src="${ctx }/static/plugins/layer/layer.js"></script>
<script type="text/javascript" src="${ctx }/static/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx }/static/plugins/select2/js/select2.full.js"></script>
<script type="text/javascript" src="${ctx }/static/plugins/select2/js/i18n/zh-CN.js"></script>
<script type="text/javascript" src="${ctx }/static/plugins/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx }/static/plugins/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${ctx }/static/plugins/ztree/js/jquery.ztree.exedit-3.5.min.js"></script>
<script type="text/javascript" src="${ctx }/static/plugins/ztree/js/jquery.ztree.exhide-3.5.min.js"></script>
<script type="text/javascript" src="${ctx }/static/plugins/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript" src="${ctx }/static/plugins/bootstrap-table/bootstrap-table.min.js"></script>
<script type="text/javascript" src="${ctx }/static/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
<script type="text/javascript" src="${ctx }/static/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="${ctx }/static/plugins/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<style>
body {
	font-family: '微软雅黑'!important;
}
</style>
<script type="text/javascript">
	var ctx="${ctx}";
	
	//全局的AJAX访问，处理AJAX清求时SESSION超时
	$.ajaxSetup({
		cache: false, //关闭AJAX相应的缓存
	    contentType:"application/x-www-form-urlencoded;charset=utf-8",
	    complete:function(XMLHttpRequest,textStatus){
	          //通过XMLHttpRequest取得响应头，sessionstatus           
	          var sessionstatus=XMLHttpRequest.getResponseHeader("sessionstatus"); 
	          if(sessionstatus=="timeout"){
	               //跳转的登录页面
	               window.top.location.replace('${ctx}/web/login');
	       		}	
	    }
	});
	
	//处理键盘事件 禁止后退键（Backspace）密码或单行、多行文本框除外
    function forbidBackSpace(e) {
		var ev = e || window.event; //获取event对象
		var obj = ev.target || ev.srcElement; //获取事件源 
		var t = obj.type || obj.getAttribute('type'); //获取事件源类型
		//获取作为判断条件的事件类型 
		var vReadOnly = obj.readOnly;
		var vDisabled = obj.disabled;
		//处理undefined值情况 
		vReadOnly = (vReadOnly == undefined) ? false : vReadOnly;
		vDisabled = (vDisabled == undefined) ? true : vDisabled;
		//当敲Backspace键时，事件源类型为密码或单行、多行文本的，
		//并且readOnly属性为true或disabled属性为true的，则退格键失效
		var flag1 = ev.keyCode == 8 && (t == "password" || t == "number" || t == "text" || t == "textarea") && (vReadOnly == true || vDisabled == true); 
		//当敲Backspace键时，事件源类型非密码或单行、多行文本的，则退格键失效
		var flag2 = ev.keyCode == 8 && t != "password" && t != "number" && t != "text" && t != "textarea"; 
		//判断 
		if (flag2 || flag1) return false;
	} 
 
    //禁止后退键 作用于Firefox、Opera
    document.onkeypress = forbidBackSpace;
    //禁止后退键  作用于IE、Chrome
    document.onkeydown = forbidBackSpace;
</script>
<meta http-equiv="X-UA-Compatible" content="IE=edge">