//扩展jquery对象序列化表单的方法
$.fn.serializeObject = function() {
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		if (o[this.name]) {
			if (!o[this.name].push) {
				o[this.name] = [ o[this.name] ];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
};

/**
 * 传入长整数的时间，转换成对应格式的日期类型 
 * @param {Object} data 长整数时间
 * @param {Object} pattern 格式化字符串
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
 * bootstrap table 提取分页及排序参数
 * @param params
 */
function queryParams(params){
	return {
		limit: params.limit,
		offset: params.offset,
		sort: params.sort,
		order: params.order
	}
}

/**
 * 弹出消息提示框
 * @param message 提示信息
 * @param icon 图标（默认为0：警告；1：正常；2.错误）
 */
function showLayer(resultMessage,iconNumber){
	layer.alert(resultMessage, {
	    title: '提示',
	    time: 5000,
	    shadeClose: true, 
	    offset: 'top',
	    btn: '关闭',
	    icon: iconNumber || 0
    });

}

/**
 * 关闭modal弹出框
 * @param dlg
 */
function closeModal(modalDlgId) {
	$("#" + modalDlgId + " .modal-content").empty();
	$("#" + modalDlgId).hide();
	$('.modal-backdrop').addClass('hidden');
}

/**
 * 弹出新增面板
 * @param modalDlgId modal的id属性值
 * @param createUrl 新增页面请求地址
 */
function addModal(modalDlgId, createUrl) {
	$("#" + modalDlgId + " .modal-content").empty();
	$("#" + modalDlgId).modal({
		backdrop: "static",
		remote: ctx + createUrl
	});
}

/**
 * 弹出修改面板
 * @param tableObj bootstrapTable对象
 * @param modalDlgId modal的id属性值
 * @param updateUrl 编辑页面的请求地址
 */
function updateModal(tableObj, modalDlgId, updateUrl) {
	var rows = tableObj.bootstrapTable('getSelections');
	if (rows.length == 0) {
		showLayer('请选择一条数据！');
	} else if (rows.length > 1) {
		showLayer('修改只能选择一条数据！');
	} else {
		$("#" + modalDlgId + " .modal-content").empty();
		$("#" + modalDlgId).modal({
			backdrop: "static",
			remote: ctx + updateUrl + "/" + rows[0].id
		});
	}
}

/**
 * 单条记录删除
 * @param tableObj bootstrapTable对象
 * @param deleteUrl 删除请求地址
 */
function singleDelete(tableObj, deleteUrl) {
	var rows = tableObj.bootstrapTable('getSelections');
	if (rows.length == 0) {
		showLayer('请选择一条数据！');
	} else if (rows.length > 1) {
		showLayer('只能选择一条数据进行删除！');
	} else {
		layer.confirm('删除后无法恢复您确定要删除吗？', {
			title: '提示',
			offset: 'top',
			icon: 3
		}, function(){
			$.ajax({
				type : "get",
				dataType : "json",
				url : ctx + deleteUrl + "/" + rows[0].id,
				success: function(data){
					if (data.message == "success") {
						var dataCount = tableObj.bootstrapTable("getData").length - 1;
						if (dataCount < 1) {
							tableObj.bootstrapTable("prevPage");
						} else {
							tableObj.bootstrapTable("refresh");
						}
						showLayer('删除成功！',1);
					} else {
						showLayer(data.message, 2);
					}
				}
			});
		});
	}
}

/**
 * 批量删除
 * @param tableObj bootstrapTable对象 
 * @param deleteUrl 删除请求地址
 */
function batchDelete(tableObj, deleteUrl) {
	var rows = tableObj.bootstrapTable('getSelections');
	if (rows.length < 1) {
		showLayer('请选择要删除的数据数据！');
	} else {
		layer.confirm('删除后无法恢复您确定要删除吗？', {
			title: '提示',
			offset: 'top',
			icon: 3
		}, function(){
			var idArray = [];
			for(var i = 0; i < rows.length; i++){
				idArray.push(rows[i].id);
			}
			$.ajax({
				type : "get",
				url : ctx + deleteUrl,
				dataType : "json",
				data : JSON.stringify(idArray),
				contentType : 'application/json;charset=utf-8',
				success: function(data){
					if (data.message == "success") {
						var dataCount = tableObj.bootstrapTable("getData").length - rows.length;
						if (dataCount < 1) {
							tableObj.bootstrapTable("prevPage");
						} else {
							tableObj.bootstrapTable("refresh");
						}
						showLayer('删除成功！',1);
					} else {
						showLayer(data.message, 2);
					}
				}
			});
		});
	}
}

/**
 * 弹出详情面板
 * @param tableObj bootstrapTable对象 
 * @param modalDlgId modal的id属性值
 * @param detailUrl 详情页面请求地址
 */
function detailModal(tableObj, modalDlgId, detailUrl) {
	var rows=tableObj.bootstrapTable("getSelections");
	if(rows.length==0){
		showLayer("请选择行数据！");
	} else if (rows.length > 1) {
		showLayer('只能选择一条数据查看详情！');
	} else{
		$("#" + modalDlgId + " .modal-content").empty();
		$("#" + modalDlgId).modal({
	        backdrop: "static",
	        remote: ctx + detailUrl + "/" + rows[0].id
	    });
	}	
}

/**
 * 提交新增或修改的表单数据
 * @param tableObj bootstrapTable对象 
 * @param modalDlgId modal的id属性值
 * @param mainFormId 表单Form的id属性值
 */
function saveFormData(tableObj, modalDlgId, mainFormId){
	$.ajax({
		url: $('#' + mainFormId).attr("action"),
		type: "post",
		dataType : "json",
		data: $('#' + mainFormId).serializeObject(),
		success: function(data){
			if (data.message == "success") {
				showLayer('操作成功！',1);
				closeModal(modalDlgId);
				tableObj.bootstrapTable("refresh");
			} else {
				showLayer(data.message, 2);
			}
		}
	});
}

/**
 * 展开/折叠高级条件查询面板
 * @param searchPanelId 查询面板id属性值
 */
function openSearchPanel(searchPanelId) {
	$("#" + searchPanelId).slideToggle("slow");
}

/**
 * 高级条件查询
 * @param tableObj bootstrapTable对象 
 * @param searchFormId 查询表单form的属性值
 */
function doSearch(tableObj, searchFormId) {
	var queryObj = $("#" + searchFormId).serializeObject();
	tableObj.bootstrapTable('refresh',{
		silent: true,
		query: queryObj
	});
}

/**
 * 触发form表单的提交事件
 * @param mainFormId
 */
function submitMainForm(mainFormId) {
	$("#" + mainFormId + " input[type='submit']").click();
}

/**
 * 打开excel导入面板
 * @param tableObj bootstrapTable对象 
 * @param modalDlgId modal的id属性值
 * @param importUrl 导入页面请求地址
 */
function openImport(tableObj, modalDlgId, importUrl) {
	$("#" + modalDlgId).modal({
		backdrop: "static",
        remote: ctx + importUrl
	}).on('hide.bs.modal', function (e) {
		tableObj.bootstrapTable("refresh", {silent: true});
	});
}

/**
 * 下载excel导入模板
 * @param templateUrl 导入模板的下载路径
 */
function downExcelTemplate(templateUrl){
	window.location.href = ctx + templateUrl;
}

function exportExcel(tableObj, searchFormId, exportUrl){
	$("#" + searchFormId)[0].method = "post"
	$("#" + searchFormId)[0].action = ctx + exportUrl;
	$("#" + searchFormId)[0].target = '_blank';
	$("#" + searchFormId).submit();
	doSearch(tableObj, searchFormId);
}
/*
 * 富文本提交方法前引用
 * */
function CKupdate() {
    for (instance in CKEDITOR.instances)
        CKEDITOR.instances[instance].updateElement();
}
/*
 * 树形表格盒子高度改变
 * */
function resizeTb(){   
   var Hei=window.innerHeight; 
   $('.tbBox').css({'height':Hei-160+'px'});      
   $('.ulBox').css({'height':Hei-160+'px'}); 
   $('.ulBox').niceScroll({
	        cursorcolor: "#999",//#CC0071 光标颜色
	        cursoropacitymax: .6, //改变不透明度非常光标处于活动状态（scrollabar“可见”状态），范围从1到0
	        touchbehavior: false, //使光标拖动滚动像在台式电脑触摸设备
	        cursorwidth: "12px", //像素光标的宽度
	        cursorborder: "0", // 游标边框css定义
	        cursorborderradius: "4px",//以像素为光标边界半径
	        autohidemode: true //是否隐藏滚动条
   });
   $('.tbBox').niceScroll({
       cursorcolor: "#999",//#CC0071 光标颜色
       cursoropacitymax: .6, //改变不透明度非常光标处于活动状态（scrollabar“可见”状态），范围从1到0
       touchbehavior: false, //使光标拖动滚动像在台式电脑触摸设备
       cursorwidth: "12px", //像素光标的宽度
       cursorborder: "0", // 游标边框css定义
       cursorborderradius: "4px",//以像素为光标边界半径
       autohidemode: true //是否隐藏滚动条
   });
}
$(window).resize(function(){
	resizeTb()
	modalBoduHeight()	
})
$(function(){
	resizeTb();
	modalBoduHeight()
})	
function userPhoto(src){
	alert(src)
	$('#user_photo').attr('src',src)
	
	
}
function modalBoduHeight(){
	 var Hei=window.innerHeight; 
	/* if(Hei<600){$('.modalBoduHeight').css('height','400px')}else{
		 $('.modalBoduHeight').css('height','auto')
	 }*/
	
}


