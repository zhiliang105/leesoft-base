/**
 * @author Lee 
 * @date 2018-05-21
 */

/**
 * 土司
 */
var toast = window.toast || {};
$.extend(toast, {

	warn : function(msg) {
		this.open("警告", msg, "warning");
	},

	info : function(msg) {
		this.open("提示", msg, "info");
	},

	success : function(msg) {
		this.open("提示", msg, "success");
	},
	
	error : function(msg) {
		this.open("错误提示", msg, "error");
	},

	/**
	 * type : error/info/notice/success/warning
	 */
	open : function(title, message, type) {
		$.Toast(title, message, type, {
			position_class : "toast-top-center", // 顶部居中显示
			width : 300,
			stack : false,
			has_icon : true,
			has_close_btn : true,
			fullscreen : false,
			timeout : 3000,
			spacing : 10,
			sticky : false,
			has_progress : false,
			rtl : false
		});
	}
});

/**
 * 对话框
 */
var dialog = window.dialog || {};
$.extend(dialog, {
	
	alert : function(msg, callback) {
		layer.alert(msg, {icon : 0}, function(index){
			if(callback) callback();
			layer.close(index);
		})
	},
	
	confirm : function(msg, callback) {
		layer.confirm(msg, { icon : 3, title : '操作提示', area: '300px' }, function(index) {
			layer.close(index);
			if(callback) callback(); 
		});
	},
	
	/**
	 * 打开对话框(iframe层), 并指定一个页面
	 */
	open : function(options) {
		var windowHeight = $(window).height();
		var windowWidth = $(window).width();
		var defaultOptions = {
				width : "800px",
				height : (windowHeight - 50) + 'px', // 如果options中不指定宽和高,则采用默认值
				content : window.location.href,
				title : "Dialog" ,
				type : 2,
				maxmin : false,
				scrollbar : false ,
				okBtnText : "确定",
				showOkBtn : true,  // 是否显示确定按钮,默认显示,否则只显示取消按钮 , 如果需要显示确定按钮, 那么对话框中打开的页面中必须包含submit方法供按钮调用
				callback : function(rs) {}, // 如果显示确定按钮, 那么提交事件处理成功之后的回调
				cancelCallback : function(){} // 取消按钮的回调
		}
		var $options = $.extend(true, {}, defaultOptions, options);
		
		var w_ = parseInt($options.width,10), h_ = parseInt($options.height, 10);
		if(w_ > windowWidth) w_ = windowWidth;
		if(h_ > windowHeight) h_ = windowHeight;
		$options['area'] = [w_+"px", h_ + "px"];
		if($options.showOkBtn) {
			$options['btn'] = [ $options.okBtnText, '取消' ];
			$options['yes'] = function(index, layero) {
				var iframeWin = window[layero.find('iframe')[0]['name']];
				
				// 注册打开iframe对话框的窗体回调方法
				window.formSubmitCallback = function(rs) {
					layer.close(index);
					$options.callback(rs);
					window.formSubmitCallback = null;
				}
				
				iframeWin.submit(); 
				return false;
			}
			$options['btn2'] = function(index, layero) {
				layer.close(index);
				if($options.cancelCallback) $options.cancelCallback();
			}
		} else {
			$options['btn'] = [ '取消' ];
			$options['yes'] = function(index, layero) {
				layer.close(index);
				if($options.cancelCallback) $options.cancelCallback();
			}
		}
		
		layer.open($options);
	}, 
	
	loading : function() {
		layer.load(1, { shade: [0.1,'#000'] });
	}, 
	
	loaded : function() {
		layer.closeAll('loading'); 
	}
	
});

$(function(){
	layer.config({
		skin: 'custom-class'
	})
});
