(function ($) {
	$.fn.comboZTree = function(options, param) {
		//如果是调用方法
        if (typeof options == 'string') {
            return $.fn.comboZTree.methods[options](this, param);
        }
        var opts = $.extend({}, $.fn.comboZTree.defaults, options);
        var targetObj = $(this);
        init(targetObj,opts);
        $(this).click(function(){
        	showTreeMenu(targetObj, opts);
        })
		
	};
	
	function init(obj, options) {
		var objId = obj.attr("id");
		$("#menuContent_" + objId).remove();
		var treePanleWidth = options.width == 0 ? obj.outerWidth() : options.width;
		obj.after('<div id="menuContent_' + objId + '" class="menuContent" style="display:none; position: absolute;z-index: 1051;background: white;'
				+'border: 1px solid #ccc;width:' + treePanleWidth + 'px;height:300px;overflow-y:auto;">'
				+'<ul id="menuTree_' + objId + '" class="ztree" style="margin-top:0; width:160px;"></ul></div>');
		var target_name = obj.attr("name");
		obj.after('<input id="ztree_ipt_' + objId + '" type="hidden" name="'+target_name+'">');
		obj.removeAttr("name");
		obj.attr("readOnly","readOnly");
		obj.parent().css("position","relative");
		var zNodes=null;
		var setting = options.treeSetting;
		setting.data.key = {name : options.textField, url : ""};
		setting.view = {dblClickExpand : false};
		setting.callback = {onClick : onSelectNode};
		$.ajax({
			type : options.type,
			url : options.url,
			dataType : "json",
			success:function(data){
				zNodes=data;
				if (data.length > 0) {
					$.fn.zTree.init($("#menuTree_" + objId), setting, zNodes);
					loadTreeData(obj);
				}
			}
		});
	};
	
	function showTreeMenu(obj, options) {
		var objId = obj.attr("id");
		if($("#menuContent_" + objId).css("display")=="none"){
			var treePanleTop = options.top == 0 ? obj.outerHeight() : options.top;
			$("#menuContent_" + objId).css({left : obj.position().left + "px",top : treePanleTop + "px"}).slideDown("fast");			
		} else {
			hideTreeMenu(obj);
		}
		$(document).bind("mousedown", obj, onBodyDown);
	}
	
	function hideTreeMenu(obj) {
		var objId = obj.attr("id");
		$("#menuContent_" + objId).fadeOut("fast");
		$(document).unbind("mousedown", onBodyDown);
	}
	
	function onBodyDown(event) {
		var objId = event.data.attr("id");
		if (!(event.target.id == objId || event.target.id == ("menuContent_" + objId)
			|| $(event.target).parents("#menuContent_" + objId).length>0)) {
			hideTreeMenu(event.data);
		}
	}
	
	function onSelectNode(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj(treeId);
		var treeSetting = zTree.setting;
		var vals = treeNode[treeSetting.data.simpleData.idKey];
		var showText = treeNode[treeSetting.data.key.name];
		var objId = treeId.substring(9);
		$("#" + objId).val(showText);
		$("#ztree_ipt_" + objId).val(vals);
		hideTreeMenu($("#" + objId));
	}
	
	function loadTreeData(obj) {
		var objId = obj.attr("id");
		var ztreeObj = $.fn.zTree.getZTreeObj("menuTree_" + objId);
		ztreeObj.expandAll(true);
		var initValue = obj.val();
		if (typeof initValue != "undefined" && initValue != null && initValue != "") {
			var treeSetting = ztreeObj.setting;
			var initNode = ztreeObj.getNodeByParam(treeSetting.data.simpleData.idKey,initValue);
			$("#ztree_ipt_" + objId).val(initValue);
			obj.val(initNode[treeSetting.data.key.name]);
			ztreeObj.selectNode(initNode, false, false);
		}
	}
	
	$.fn.comboZTree.methods = {
	        getZTreeObj : function (target, data) {
	            return $.fn.zTree.getZTreeObj("menuTree_" + $(target).attr("id"));
	        }
	    };
	
	$.fn.comboZTree.defaults = {
			width : 0,
			top : 0,
			idField : "id",
			parentId : "pid",
			textField : "name",
			type : "get",
			url : "",
			treeSetting : {
				data : {
					simpleData : {
						enable : true,
						idKey : "id",
						pIdKey : "pid",
						rootPId : null
					}
				}
			}
			
	}
	
})(jQuery);