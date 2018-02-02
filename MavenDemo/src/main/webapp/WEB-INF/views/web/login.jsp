<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/views/common/header.jsp"%>
<script type="text/javascript" 
			src="${ctx }/static/js/echarts/echarts.js"></script>
<style type="text/css">
#picturePlace {
	width: 600px;
	height: 350px;
	margin: 0 auto;
}

</style>
<title>Insert title here</title>
</head>
<body>
	<div id="picturePlace"></div>
</body>
<script type="text/javascript">
var myChart = echarts.init(document.getElementById('picturePlace'));
 var option = {
		    tooltip: {
		        trigger: 'item',
		        formatter: "{a} <br/>{b}: {c} ({d}%)"
		    },
		    legend: {
		        orient: 'vertical',
		        x: 'left',
		        data:['直达','营销广告','搜索引擎','邮件营销','联盟广告','视频广告','百度','谷歌','必应','其他']
		    },
		    series: [
		        {
		            name:'访问来源',
		            type:'pie',
		            selectedMode: 'single',
		            radius: [0, '30%'],

		            label: {
		                normal: {
		                    position: 'inner'
		                }
		            },
		            labelLine: {
		                normal: {
		                    show: false
		                }
		            },
		            data:[
		                {value:335, name:'直达', selected:true},
		                {value:679, name:'营销广告'},
		                {value:1548, name:'搜索引擎'}
		            ]
		        },
		        {
		            name:'访问来源',
		            type:'pie',
		            radius: ['40%', '55%'],

		            data:[
		                {value:335, name:'直达'},
		                {value:310, name:'邮件营销'},
		                {value:234, name:'联盟广告'},
		                {value:135, name:'视频广告'},
		                {value:1048, name:'百度'},
		                {value:251, name:'谷歌'},
		                {value:147, name:'必应'},
		                {value:102, name:'其他'}
		            ]
		        }
		    ]
	};
 $(function(){
	 myChart.setOption(option);
 })
</script>
</html>
