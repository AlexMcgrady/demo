<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<%@include file="/WEB-INF/views/common/header.jsp"%>
<script type="text/javascript"
	src="${ctx }/static/js/echarts/echarts.js"></script>
<script type="text/javascript"
	src="http://api.map.baidu.com/api?v=2.0&ak=4Ebk9w9Tmd9eWeywct5ijfkrLj33GKRt"></script>

<style type="text/css">
body, html, #allmap {
	width: 100%;
	height: 100%;
	overflow: hidden;
	margin: 0;
	font-family: "微软雅黑";
}
</style>
<title>Insert title here</title>
</head>
<body>
	<div id="allmap"></div>
</body>
<script type="text/javascript">
	//创建Map实例
	var map = new BMap.Map("allmap");
	//设置中心点坐标
	var point = new BMap.Point(116.331398, 39.897445);
	//初始化地图,并设置地图级别
	map.centerAndZoom(point, 12);
	//进行浏览器定位
	var geolocation = new BMap.Geolocation();
	geolocation.getCurrentPosition(function(r) {
		// 定位成功事件
		if (this.getStatus() == BMAP_STATUS_SUCCESS) {
			//创建一个图像标注实例。point参数指定了图像标注所在的地理位置
			var mk = new BMap.Marker(r.point);
			//将覆盖物添加到地图中，一个覆盖物实例只能向地图中添加一次。
			map.addOverlay(mk);
			//将地图的中心点更改为给定的点
			map.panTo(r.point);
			//alert('您的位置：'+r.point.lng+','+r.point.lat);
		} else {
			alert('failed' + this.getStatus());
		}
	}, {
		enableHighAccuracy : true
	})
	var top_left_control = new BMap.ScaleControl({
		anchor : BMAP_ANCHOR_TOP_LEFT
	});// 左上角，添加比例尺
	var top_left_navigation = new BMap.NavigationControl(); //左上角，添加默认缩放平移控件
	var top_right_navigation = new BMap.NavigationControl({
		anchor : BMAP_ANCHOR_TOP_RIGHT,
		type : BMAP_NAVIGATION_CONTROL_SMALL
	}); //右上角，仅包含平移和缩放按钮

	$(function() {
		map.addControl(top_left_control);
		map.addControl(top_left_navigation);
		map.addControl(top_right_navigation);
		map.enableScrollWheelZoom();//启动鼠标滚轮缩放地图
	})
</script>
</html>
