/**
 * 页面通用js脚本
 * @author Lee
 * @date 2018-05-21
 */

(function(win,ls){
	'use strict';
	
	// 页面初始化操作
	ls.init = function() {
		ajaxSetting();
		fitTableLayout();
		$(window).bind("resize", function () {
			fitTableLayout();
	    });
	}
	
	ls.getWinWidth = function() {
		return $(win).width();
	}
	
	ls.getWinHeight = function() {
		return $(win).height();
	}
	
	/**
	 * 初始化页面中的layui表单
	 * mods : 需要注册的组件,默认只注册['form']
	 */
	ls.initLayForm = function(options, mods) {
		var this_ = this;
		var defaultOptions = {
				initFun : function(layo){},
				sucCallback : function(rs){},
				errorCallback : function(rs){}
		}
		var defaultMods = ['form'];
		if(typeof mods=='string') {
			defaultMods.push(mods);
		} else if((typeof mods == 'object') && mods.constructor == Array) {
			if(mods.length > 0) {
				for(var i = 0;i<mods.length;i++) {
					defaultMods.push(mods[i]);
				}
			}
		}
		var $options = $.extend(true, {}, defaultOptions, options);
		layui.use(defaultMods, function(){
			var form = layui.form; 
			if($options.initFun) {
				var initObj = {form : form};
				for(var i = 0;i<defaultMods.length;i++) {
					var mod = defaultMods[i];
					if(mod != 'form') {
						initObj[mod] = layui[mod];
					}
				}
				$options.initFun(initObj);
			}
			
			// 注册自定义验证规则: layui form 默认验证： required, phone, email, url, number, date, identity
			// 覆盖默认验证规则(email和phone),在单个验证的时候,如果不输入,则不验证,否则进行验证
			form.verify({
				email : function(value, item) {
					if($.trim(value) != "") {
						if(!/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(value)){
					      return '邮箱格式不正确';
					    }
					}
				},
				phone : function(value, item) {
					if($.trim(value) != "") {
						if(!/^1\d{10}$/.test(value)){
					      return '请输入正确的手机号码';
					    }
					}
				},
				valid : function(value, item) { // 同名验证
					var input = $(item);
					var oldValue = input.attr("lay-oldValue");
					if(value != oldValue) {
						var msg ;
						$.ajax( {
							url : input.attr("lay-validUrl"),  type : "POST", global : false,
							data : {value : value , property : input.attr("name") }, dataType : "json", async : false, 
							success : function(rs) {
								if(rs.code == 2) msg = "已经存在,不可重复录入";
							}
						});
						return msg;
					}
				}
			});
			
			// 监听表单提交事件： 表单提交按钮的lay-filter="doSubmit"
			form.on('submit(doSubmit)', function(data){
	  		  var formData = data.field;
	  		  
	  		  // 解决checkbox如果多选只能提交一个值的问题,多个值用英文的逗号分隔
	  		  // 同时解决多个相同的name的问题
	  		  var checkBoxValues = {};
	  		  $("form").find("input[type='checkbox']:checked, input[type='text'], input[type='hidden'], input[type='number'] ").each(function(){
	  			   var name = $(this).attr("name"), value = $(this).val();
	  			   var values = checkBoxValues[name];
	  			   if(values) {
	  				   values.push(value);
	  			   } else {
	  				   values = [value];
	  			   }
	  			   checkBoxValues[name] = values;
	  		  });
	  		  for(var key in checkBoxValues) {
	  			  formData[key] = checkBoxValues[key].join(",");
	  		  }
	  		  
	  		  this_.formSubmitSingle($("form").attr("action"), formData, $options.sucCallback, $options.errorCallback );
	  		  return false; 
	  	  });
			
		});
	}

	/**
	 * 通过dailog.open打开的对话框中包含表单数据提交的时候,可以用此方法提交数据请求
	 */
	ls.formSubmitSingle = function(url,params,sucCallback,errorCallback) {
		var this_ = this;
		this_.formSubmit(url, params, function(rs){
			  if(sucCallback) sucCallback(rs);
			  var _callback = parent.formSubmitCallback;
			  if(_callback) _callback(rs);
		  },function(rs){
			  if(errorCallback) errorCallback(rs);
		  });
	}
	
	/**
	 * 表单提交-ajax请求处理
	 */
	ls.formSubmit = function(url, params,sucCallback,errorCallback) {
		var this_ = this;
		this_.ajax(url, params, function(rs){
			if(rs.code == 0) { // 出现错误信息
				top.toast.error(rs.msg);
				if(errorCallback) errorCallback(rs);
			} else if(rs.code == 1){ // 提交成功
				top.toast.success(rs.msg);
				if(sucCallback) sucCallback(rs);
			} else if(rs.code == 2){ // 数据校验失败
				var attributes = rs.attributes;
				for(var field in attributes) {
					var input = $("#" + field);
					layer.tips(attributes[field], function(){
		                if(typeof input.attr('lay-ignore') !== 'string'){
		                    if(input[0].tagName.toLowerCase() === 'select' || /^checkbox|radio$/.test(input[0].type)){
		                      return input.next();
		                    }
		                  }
		                  return input;
		                }() ,  { tips: [3, 'red'], tipsMore: true });
				}
			} else {
				top.toast.warn("提交失败,出现未知错误");
			}
		});
	}
	
	/**
	 * 数据修改-指定在bootstrap-table中操作修改功能
	 */
	ls.updateTableData = function(options) {
		var this_ = this;
		var table = $("#" + options.tableId);
		var selectRows = table.bootstrapTable("getSelections");
		var len = selectRows.length;
		if(len == 0) {
			top.toast.warn("请选择需要处理的数据");
		} else {
			if(len > 1) {
				top.toast.warn("只能选择一条记录进行处理");
			} else {
				var row = selectRows[0];
				var validate = options.validate;
				var validateResult = true;
				if(validate) validateResult = validate(row);
				if(validateResult === true) {
					var id = row.id;
					options.url = this_.appendUrlPara(options.url, "id", id);
					this_.openFormDialog(options);
				} else {
					top.toast.warn(validateResult);
				}
			}
		}
	}

	
	/**
	 * 通用的打开表单页面处理方法
	 * options参数： tableId, url, callback, width, height
	 */
	ls.openFormDialog = function(options) {
		var defaultOptions = {
				title : "新增数据", 
				tableId : '',
				url : '',
				okBtnText : "确定", 
				callback : function(rs){},
				cancelCallback : function(){}
		}
		var $options = $.extend(true, {}, defaultOptions, options);
		
		// 打开对话框表单提交成功之后的回调方法: 如果有table,强制刷新数据, 然后再回调传入的callback
		var sucCallback = function(rs) {
			var table = $("#" + $options.tableId);
			if(table.length > 0) {
				try {
					table.bootstrapTable("refresh");
				} catch (e) {}
			}
			if($options.callback) $options.callback(rs);
		}
		
		var dialogOptions = {
				title :$options.title,
				width : $options.width,
				height : $options.height,
				content : $options.url,
				okBtnText : $options.okBtnText,
				callback : sucCallback ,
				cancelCallback : $options.cancelCallback
		}
		top.dialog.open(dialogOptions);
	}
	
	/**
	 * 数据删除-指定在bootstrap-table中操作删除功能
	 */
	ls.deleteTableData = function(options) {
		var this_ = this;
		var table = $("#" + options.tableId);
		var selectRows = table.bootstrapTable("getSelections");
		if(selectRows.length == 0) {
			top.toast.warn("请选择需要处理的数据");
		} else {
			var msg = options.msg;
			if(!msg || $.trim(msg) == "") {
				msg = "您确定要删除选中的数据吗?";
			}
			top.dialog.confirm(msg, function(){
				var ids = [];
				for(var i = 0, len = selectRows.length; i < len; i++) {
					var id = selectRows[i].id;
					if(id) ids.push(id);
				}
				this_.ajax(options.url, {ids : ids}, function(rs){
					if(rs.code == 0) {
						var errorCallback = options.errorCallback;
						if(!errorCallback || errorCallback(rs)) {
							top.toast.error(rs.msg);
						}
					} else if(rs.code == 1){
						var succCallback = options.succCallback;
						if(!succCallback || succCallback(rs)) {
							top.toast.success(rs.msg);
							table.bootstrapTable("refresh");
						}
					} else {
						top.toast.warn("删除失败,出现未知错误");
					}
				});
			});
		}
	}
	
	/**
	 * 向url中添加参数
	 */
	ls.appendUrlPara = function(url, name, value) {  
		if(url.indexOf("?") == -1) url += "?";
	    else url += "&";
	    url += encodeURIComponent(name)+"="+encodeURIComponent(value);
	    return url;
	}
	
	/**
	 * 数据导出通用方法
	 */
	ls.exportExcelByUrl = function(url, params) {
		var $form = $("<form id='excel-export-form' method='post' action='"+url+"' style='display:none;'></form>").appendTo("body");
		for(key in params) {
			var input = $("<input type='hidden' name='"+key+"' value='" + params[key] + "'></input>").appendTo($form);
		}
	    $form.submit();
	    window.setTimeout(function(){ $form.remove();}, 100);
	}
	
	/**
	 * ajax请求
	 */
	ls.ajax = function(url, params, callback) {
		$.ajax( {
			url : url,
			type : "POST",
			cache : false,
			global : true,
			traditional : true,
			data : params,
			dataType : "json",
			success : function(rs) {
				if (callback) callback(rs);
			},
			beforeSend : function(XMLHttpRequest) {
				top.dialog.loading();
			}
		});
	}
	
	/**
	 * 渲染bootstrap-table, 并初始化通用的参数
	 */
	ls.bootstrapTable = function(id, options) {
		var defaultOptions = {
				method : "post",
				striped : false,
				cache : false,
				height : 400,
				contentType : "application/x-www-form-urlencoded",
				queryParams : function(sParams) {
					var params = sParams || {};
					return params;
				},
				search : true, 
				searchOnEnterKey : true, 
				searchTimeOut: 10, 
				queryParamsType : "noLimit",
				pagination : true,
				pageSize : 30,
				pageList : [30, 60, 100, 200, 500],
				paginationLoop : false, 
				sidePagination : "server",
				silentSort : false,
				sortable : true,
				idField: "id",
				uniqueId : "id", 
				sortOrder : "asc",
				clickToSelect : true, 
				searchAlign : 'left',
				buttonsAlign : "left",
				toolbarAlign : 'right',
				showPaginationSwitch: false,
				showRefresh : true,
				showQuery : false,  // 自定义属性,是否显示查询条件显示/隐藏按钮, 按钮是否显示与是否定义查询条件有关(也就是是否显示.bt-layout-top区域)
				showColumns : true,
				showToggle:false,
				showFullscreen : false,
				responseHandler: function(res) {
		            return { "total": res.total, "rows": res.rows };
			    },
			    onPostHeader : function() {
					$("#data_list").data("bootstrap-table-loaded", "loaded" );
				}
		}
		var $options = $.extend(true, {}, defaultOptions, options);
		$("#" + id).bootstrapTable($options);
	}


	
	/**
	 * ajax全局参数设置
	 */
	var ajaxSetting = function() {
		$(document).ajaxStop(function() {
			top.dialog.loaded();
		});
		$(document).ajaxSend(function(evt, request, settings) {
		});
		$(document).ajaxError( function(event, XMLHttpRequest, ajaxOptions, thrownError) {
			top.dialog.loaded();
			var status = XMLHttpRequest.status;
			if(status == 908) {
				top.location.reload();
			} else if(status == 403) {
				top.toast.warn("对不起,您没有访问权限");
			} else if(status == 500) {
				top.toast.error("对不起,服务器出现异常");
			} else {
				top.toast.error("系统出现未知错误,请联系系统管理员处理");
			}
		});
	}
	
	/**
	 * bootstrap-table单表格页面布局
	 */
	var fitTableLayout = function () {
		var winHeight = ls.getWinHeight(), winWidth = ls.getWinWidth();
		var container = $(".bt-container");
		if(container.length == 0 || container.length > 1) return ;
		
		var conHeight = winHeight - 2;
		container.css({height : conHeight + "px"});
		
		var lWidth = 0, lHeight = conHeight, tWidth = 0, tHeight = 0, tTop= 0, cWidth = 0, cHeight = 0;
		
		var lLeft = $(".bt-layout-left");
		if(lLeft.length > 0) {
			lLeft.css({height : lHeight + "px"});
			lWidth = lLeft.outerWidth();
			
			var lLeftHead = lLeft.find(".panel-heading");
    		var headerHeight = lLeftHead.length > 0 ? lLeftHead.outerHeight(true) : 0;
    		var lPaddingTop = parseInt(lLeft.css("padding-top"),10) , lPaddingBottom = parseInt(lLeft.css("padding-bottom"),10) ;
    		lLeft.find(".panel-body").css({height : (lHeight - headerHeight - lPaddingTop -lPaddingBottom) + "px" });
			
		}
		var lTop = $(".bt-layout-top");
		if(lTop.length > 0) {
			tWidth = winWidth - lWidth;
			lTop.css({width : tWidth + "px"});
			tHeight = lTop.outerHeight();
			var ltTop = parseInt(lTop.css("top"),10);
			if(ltTop == -10000) {
				tTop = 0 - tHeight;
			} else {
				tTop = ltTop;
			}
			lTop.css({top : tTop + "px"});
		}
		var lCenter = $(".bt-layout-center");
		if(lCenter.length > 0) {
			cWidth = winWidth - lWidth;
			cHeight = conHeight - (tHeight - Math.abs(tTop));
			lCenter.css({width : cWidth + "px", height : cHeight + "px"});
		}
		
		if(lCenter.find("table").length == 0) return ;
		
		var timeId = window.setInterval(function(){
			var table = lCenter.find(".fixed-table-body table");
			if(table.data("bootstrap-table-loaded") == "loaded" ) {
				table.bootstrapTable("resetView",{height : cHeight});
				var bootstrapTable = lCenter.find(".bootstrap-table");
				if(bootstrapTable.length != 0) {
					var loading = bootstrapTable.find(".fixed-table-loading");
					var loadingWidth = loading.width(), loadingHeight = loading.height();
					var loadingMsg = loading.find(".fixed-table-loading-msg");
					var msgWidth = loadingMsg.outerWidth(true), msgHeight = loadingMsg.outerHeight(true);
					var msgLeft = (loadingWidth - msgWidth) / 2, msgTop = (loadingHeight - msgHeight) / 2;
					loadingMsg.css({left : msgLeft + "px", top : msgTop + "px"});
					
					/*
					var tableContainer = bootstrapTable.find('.fixed-table-container');
					if(tableContainer.length != 0) {
						var toolBar = bootstrapTable.find('.fixed-table-toolbar');
						var pagination = bootstrapTable.find('.fixed-table-pagination');
					    
					    var toolbarHeight = toolBar.outerHeight(true);
					    var paginationHeight = pagination.outerHeight(true);
					}
					*/
				}
				
				// 绑定bootstrap-table中自定的按钮事件
				var queryToggleBtn = $(".btn-cus-e-bind");
				if(queryToggleBtn.length > 0) {
					if( queryToggleBtn.data("bindEvent") !== "1" ) {
						queryToggleBtn.bind("click",function(){
							var queryCon = $(".bt-layout.bt-layout-top");
							if(queryCon.length > 0) {
								var qcHeight = queryCon.outerHeight(true), qcTop = parseInt(queryCon.css("top"), 10 );
								if(qcTop < 0) {
									queryCon.css("top", "0px")
								} else {
									queryCon.css("top", -qcHeight + "px")
								}
								fitTableLayout();
							}
							queryToggleBtn.data("bindEvent","1");
						});
					}
				}
				
				window.clearInterval(timeId);
			}
		}, 10);
		
	}
	
	
	// 工具
	
	ls.getObjectURL = function(file) {
    	var url = null;
    	if (window.createObjectURL != undefined) { // basic
    		url = window.createObjectURL(file);
    	} else if (window.URL != undefined) { // mozilla(firefox)
    		url = window.URL.createObjectURL(file);
    	} else if (window.webkitURL != undefined) { // webkit or chrome
    		url = window.webkitURL.createObjectURL(file);
    	}
    	return url;
    }
	
    ls.getBase64Image = function(img) {  
        var canvas = document.createElement("canvas");  
        canvas.width = img.width;  
        canvas.height = img.height;  
        var ctx = canvas.getContext("2d");  
        ctx.drawImage(img, 0, 0, img.width, img.height);  
        var ext = img.src.substring(img.src.lastIndexOf(".")+1).toLowerCase();  
        var dataURL = canvas.toDataURL("image/"+ext);  
        return dataURL;  
   }  
	
})(window, window.LS = {});

$(function() {
	LS.init();
});