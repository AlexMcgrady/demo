Date.prototype.format = function(fmt){  
	  var o = {   
	    "M+" : this.getMonth()+1,                 //月份   
	    "d+" : this.getDate(),                    //日   
	    "H+" : this.getHours(),                   //小时   
	    "m+" : this.getMinutes(),                 //分   
	    "s+" : this.getSeconds(),                 //秒   
	    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
	    "S"  : this.getMilliseconds()             //毫秒   
	  };   
	  if(/(y+)/.test(fmt))   
	    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
	  for(var k in o)   
	    if(new RegExp("("+ k +")").test(fmt))   
	  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
	  return fmt;   
} 

/**
 * 传入长整数的时间，转换成对应格式的日期类型 
 * @param {Object} data
 * @param {Object} pattern
 */
function formatDate(data,pattern) {
	if (data != null) {
		var time = new Date(data);
		var nowStr = time.format(pattern);
		return nowStr;
	} else {
		return "";
	}
}

/**
 * 对日期按天数进行加减计算
 * @param dateStr
 * @param days
 * @param pattern
 * @returns
 */
function addDays(dateStr, days, pattern) {
	if (dateStr != null && dateStr != "") {
		var date = new Date(dateStr);
		date = date.valueOf();
		date = date + days * 24 * 60 * 60 * 1000;
		date = new Date(date)
		return formatDate(date,pattern);
	}
	return "";
}
//获得上个月在昨天这一天的日期
function getLastMonthYestdy(date) {
	var daysInMonth = new Array([ 0 ], [ 31 ], [ 28 ], [ 31 ], [ 30 ], [ 31 ],
			[ 30 ], [ 31 ], [ 31 ], [ 30 ], [ 31 ], [ 30 ], [ 31 ]);
	var strYear = date.getFullYear();
	var strDay = date.getDate();
	var strMonth = date.getMonth() + 1;
	if (strYear % 4 == 0 && strYear % 100 != 0) {
		daysInMonth[2] = 29;
	}
	if (strMonth - 1 == 0) {
		strYear -= 1;
		strMonth = 12;
	} else {
		strMonth -= 1;
	}
	strDay = daysInMonth[strMonth] >= strDay ? strDay : daysInMonth[strMonth];
	if (strMonth < 10) {
		strMonth = "0" + strMonth;
	}
	if (strDay < 10) {
		strDay = "0" + strDay;
	}
	datastr = strYear + "-" + strMonth + "-" + strDay;
	return datastr;
}
/**
 * 获取当前时间的季度
 * @param date
 * @returns
 */
function getCurrQuarter(date){
	var currMonth=date.getMonth()+1;
	return Math.floor(( currMonth % 3 == 0 ? (currMonth / 3 ) : (currMonth / 3 + 1 ) ) );
}
