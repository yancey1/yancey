if (!window.console || !console.firebug) {
	var names = [ "log", "debug", "info", "warn", "error", "assert", "dir",	"dirxml", "group", "groupEnd", "time", "timeEnd", "count", "trace", "profile", "profileEnd" ];
	window.console = {};
	for ( var i = 0; i < names.length; ++i) {
		window.console[names[i]] = function() {};
	}
}

/*
 * MAP对象，实现MAP功能
 * 
 * 接口： size() 获取MAP元素个数 isEmpty() 判断MAP是否为空 clear() 删除MAP所有元素 put(key, value)
 * 向MAP中增加元素（key, value) remove(key) 删除指定KEY的元素，成功返回True，失败返回False get(key)
 * 获取指定KEY的元素值VALUE，失败返回NULL element(index)
 * 获取指定索引的元素（使用element.key，element.value获取KEY和VALUE），失败返回NULL containsKey(key)
 * 判断MAP中是否含有指定KEY的元素 containsValue(value) 判断MAP中是否含有指定VALUE的元素 values()
 * 获取MAP中所有VALUE的数组（ARRAY） keys() 获取MAP中所有KEY的数组（ARRAY）
 * 
 * 例子： var map = new Map();
 * 
 * map.put("key", "value"); var val = map.get("key") ……
 * 
 */
function Map() {
	this.elements = new Array();

	// 获取MAP元素个数
	this.size = function() {
		return this.elements.length;
	}

	// 判断MAP是否为空
	this.isEmpty = function() {
		return (this.elements.length < 1);
	}

	// 删除MAP所有元素
	this.clear = function() {
		this.elements = new Array();
	}

	// 向MAP中增加元素（key, value)
	this.put = function(_key, _value) {
		this.elements.push({
			key : _key,
			value : _value
		});
	}

	// 删除指定KEY的元素，成功返回True，失败返回False
	this.remove = function(_key) {
		var bln = false;
		try {
			for (i = 0; i < this.elements.length; i++) {
				if (this.elements[i].key == _key) {
					this.elements.splice(i, 1);
					return true;
				}
			}
		} catch (e) {
			bln = false;
		}
		return bln;
	}

	// 获取指定KEY的元素值VALUE，失败返回NULL
	this.get = function(_key) {
		try {
			for (i = 0; i < this.elements.length; i++) {
				if (this.elements[i].key == _key) {
					return this.elements[i].value;
				}
			}
		} catch (e) {
			return null;
		}
	}

	// 获取指定索引的元素（使用element.key，element.value获取KEY和VALUE），失败返回NULL
	this.element = function(_index) {
		if (_index < 0 || _index >= this.elements.length) {
			return null;
		}
		return this.elements[_index];
	}

	// 判断MAP中是否含有指定KEY的元素
	this.containsKey = function(_key) {
		var bln = false;
		try {
			for (i = 0; i < this.elements.length; i++) {
				if (this.elements[i].key == _key) {
					bln = true;
				}
			}
		} catch (e) {
			bln = false;
		}
		return bln;
	}

	// 判断MAP中是否含有指定VALUE的元素
	this.containsValue = function(_value) {
		var bln = false;
		try {
			for (i = 0; i < this.elements.length; i++) {
				if (this.elements[i].value == _value) {
					bln = true;
				}
			}
		} catch (e) {
			bln = false;
		}
		return bln;
	}

	// 获取MAP中所有VALUE的数组（ARRAY）
	this.values = function() {
		var arr = new Array();
		for (i = 0; i < this.elements.length; i++) {
			arr.push(this.elements[i].value);
		}
		return arr;
	}

	// 获取MAP中所有KEY的数组（ARRAY）
	this.keys = function() {
		var arr = new Array();
		for (i = 0; i < this.elements.length; i++) {
			arr.push(this.elements[i].key);
		}
		return arr;
	}
}

/*
 * =========== MARKET ===============
 */
var market = {};

/*
 * 页面的通用的一些提示语常量
 */
market.content = {
	title : '温馨提示',
	tips : '请先选择要操作的数据！',
	confirm : '您确定要删除该条记录吗？',
	submitting : '正在提交数据...',
	networkError : '由于网络或服务器太忙，提交失败，请重试！',
	refreshCache: '你确定要操作立即生效吗？'
};
$(function() {
	$('body').append('<div id="myWindow" class="easyui-dialog" closed="true"></div>');
});
function showMsg(title, msg, isAlert) {
	if (isAlert !== undefined && isAlert) {
		$.messager.alert(title, msg);
	} else {
		$.messager.show({
			title : title,
			msg : msg,
			showType : 'show'
		});
	}
}
function showMyWindow(title, href, width, height, modal, minimizable, maximizable) {
	$('#myWindow').window({
		title : title,
		width : width === undefined ? 600 : width,
		height : height === undefined ? 400 : height,
		content : '<iframe scrolling="yes" frameborder="0" src="' + href + '" style="width:100%;height:98%;"></iframe>',
		// href: href === undefined ? null : href,
		modal : modal === undefined ? true : modal,
		minimizable : minimizable === undefined ? false : minimizable,
		maximizable : maximizable === undefined ? false : maximizable,
		shadow : false,
		draggable : true,
		cache : false,
		closed : false,
		collapsible : false,
		resizable : true,
		loadingMessage : '正在加载数据，请稍等片刻......'
	});
}
function closeMyWindow() {
	$('#myWindow').window('close');
}
function showConfirm(title, msg, callback) {
	$.messager.confirm(title, msg, function(r) {
		if (r) {
			if (jQuery.isFunction(callback))
				callback.call();
		}
	});
}
function showProcess(isShow, title, msg) {
	if (!isShow) {
		$.messager.progress('close');
		return;
	}
	var win = $.messager.progress({
		title : title,
		msg : msg
	});
}
function submitForm(url) {
	$('#ff').form(
	'submit',
	{
		url : url,
		onSubmit : function() {
			var flag = $(this).form('validate');
			if (flag) {
				showProcess(true, market.content.title,	market.content.submitting);
			}
			return flag;
		},
		success : function(data) {
			showProcess(false);
			var data = eval('(' + data + ')');
			if (data.return_code == '1') {
				top.showMsg(market.content.title, data.return_msg, alert);
				if (parent !== undefined) {
					if ($.isFunction(parent.reloadParent)) {
						parent.reloadParent.call();
						parent.closeMyWindow();
					} else {
						parent.$("#tt").datagrid('reload');
						parent.closeMyWindow();
					}
				}
			} else {
				$.messager.alert(market.content.title, data.return_msg);
			}
		},
		onLoadError : function() {
			showProcess(false);
			$.messager.alert(market.content.title, market.content.networkError);
		}
	});
}
function clearForm() {
	$('#ff').form('clear');
}

// extends jquery function for scroll to top
$.fn.DynamicToTop = function(options) {
	var defaults = {
		text : "",
		min : "200",
		fade_in : 600,
		fade_out : 400,
		speed : "1000",
		easing : "",
		version : "",
		id : 'dynamic-to-top'
	};
	var settings = $.extend(defaults, options);
	if (settings.version == "") {
		settings.text = '';
	}
	var $toTop = $('<a href=\"#\" id=\"' + settings.id + '\"></a>').html(
			settings.text);
	$toTop.appendTo(document.body);
	$toTop.hide().click(function() {
		$('html, body').stop().animate({
			scrollTop : 0
		}, settings.speed, settings.easing);
		return false;
	});
	$(window).scroll(
		function() {
			var sd = $(window).scrollTop();
			if (typeof document.body.style.maxHeight === "undefined") {
				$toTop.css({
					'position' : 'absolute',
					'top' : $(window).scrollTop() + $(window).height()
							- mv_dynamic_to_top.margin
				});
			}
			if (sd > settings.min) {
				$toTop.fadeIn(settings.fade_in);
			} else {
				$toTop.fadeOut(settings.fade_out);
			}
		}
	);
};

/*
 * 3种组件类型
 */
market.assemblyType = new Map();
market.assemblyType.put("1", "Banner条");
market.assemblyType.put("2", "宫格N*1");
market.assemblyType.put("3", "列表1*N");

/*
 * 3种组件类型
 */
textType = new Map();
textType.put("1", "笑话");
textType.put("2", "文章");

/**
 * custom define validate rules
 */
$.extend($.fn.validatebox.defaults.rules, {
    equals: {
        validator: function(value,param){
            return value == $(param[0]).val();
        },
        message: '{1}.'
    }
});
var showArtDialog = function(title, url, width, height, lock, fixed){
	art.dialog.open(url, {
		title: title,
		width: width === undefined ? 400 : width,
		height: height === undefined ? 200 : height,
		lock: lock === undefined ? true : lock,
	  	fixed: fixed === undefined ? true : fixed
	});
};