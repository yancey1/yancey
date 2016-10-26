<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>账号管理 | 添加渠道</title>
<@fm.header />
<link rel="stylesheet" href="/resources/js/jqueryui/jquery-ui.css" />
<link rel="stylesheet" href="/resources/js/wookmark/lightbox/css/main.css">
<script src="/resources/js/jqueryui/jquery.ui.core.min.js"></script>
<script src="/resources/js/jqueryui/jquery.ui.widget.min.js"></script>
<script src="/resources/js/jqueryui/jquery.ui.mouse.min.js"></script>
<script src="/resources/js/jqueryui/jquery.ui.draggable.min.js"></script>
<script src="/resources/js/jqueryui/jquery.ui.droppable.min.js"></script>
<script src="/resources/js/jqueryui/jquery.ui.sortable.min.js"></script>
<script src="/resources/js/jqueryui/jquery.ui.tabs.min.js"></script>
<script src="/resources/js/underscore-min.js"></script>
<style type="text/css">
.left { width:580px; float:left;}
.right { width:340px; height:462px; float:right; overflow-x: hidden; overflow-y: hidden;}
.bottom { position: absolute; margin:0 80px 10px 120px; bottom:0px; height:auto;}
#tabs-2 {width: 580px; height: 462px; overflow-x:hidden; overflow-y:scroll;}
.destAssembly7 {width:340px; height:460px; float:left; overflow-x: hidden; overflow-y: scroll; border: 1px solid #dedede; background:url('/resources/image/user-channel.png') no-repeat center center;}
/********** 重置标题的样式 ******/
#main-2 li {
	width: 268px;
	height: 36px;
}
#main-2 li .apk-info-basic p {
  padding-left: 10px;
  color: #666;
  font-size: 12px;
  margin: 5px 0;
  word-break: break-all;
}
.destAssembly7 li {
  width: 310px;
  height: 36px;
}
.destAssembly7 li .apk-info-basic p {
  padding-left: 10px;
}
</style>
</head>
<body style="overflow: hidden;">
<div id="tabs" class="left">
  <ul>
    <li><a href="#tabs-2">渠道</a></li>
  </ul>
  <div id="tabs-2">
  	<div id="tb-apk" style="padding:3px">
	    <span>渠道名称:</span>
	    <input id="channelName" style="line-height:20px;border:1px solid #ccc">
	    <a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="doSearchChannel();">查询</a> 
	</div>
	<table id="tt-apk" class="easyui-datagrid" toolbar="#tb-apk" style="width:560px;"></table>
	<ul id="main-2"></ul>
	<div id="more-apk" class="more-info">查看更多渠道...</div>
  </div>
</div>
<div class="right">
	<div class="destAssembly7">
	<#if user?exists && user.channelForms?size gt 0>
	<#list user.channelForms as channel>
			<li>
				<span class="apk-info-basic">
					<p><label style="font-size:14px;">名称：</label>[${channel.channelId!''}/${channel.channelName}]</p>
					<input type="hidden" name="id" value="${channel.id!''}" />
				</span>
				<a title="删除" class="btn-icon delete-link" onclick="$(this).parent().remove();">删除</a>
			</li>
	</#list>
	</#if>
	</div>
	<div class="bottom">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitAssembly7Form('/user/${user.userId}/channel');">保存渠道</a>
	</div>
</div>
<form id="ff" method="post">
	<input type="hidden" name="cObj" value="">
</form>
<script>
function submitAssembly7Form(url){
    $('#ff').form(
	'submit',
	{
		url : url,
		onSubmit : function() {
			// 处理数据及验证
			var cidArr = [];
			var cid = '';
		    $('.destAssembly7 li').each(function(index, ele){
		    	var clId = $(ele).find('input[name="id"]').val();
		    	cidArr.push(clId);
		    });
		    cidArr = _.uniq(cidArr);
		    
		    for(i=0;i<cidArr.length;i++){
		    	cid += ";"+cidArr[i];
		    }
		    
		    $("input[name='cObj']").val(cid);
			//var flag = $(this).form('validate');
			var flag = true;
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
///////////////////////////////////////////////////////////////////////////////
var pageChannel = 1, channelName = '';
var apiURLChannel = '/channel/form?json';
market.loadChannelData = function() {
	$('#more-apk').hide();
	$.ajax({
		url: apiURLChannel,
		dataType: 'json',
		data: {page: pageChannel, channelName: channelName},
		success: market.onLoadChannelData
	});
};
market.onLoadChannelData = function(data) {
	if (data != null && data !== undefined && data.length > 0) {
		var html = '';
		var i=0, length=data.length, channel;
		for(; i<length; i++) {
			channel = data[i];
			html += '<li>';
			html += '<span class="apk-info-basic">';
			html += '<p ><label style="font-size:14px;">名称：</label>['+ channel.channelId + '/' + channel.channelName +']</p>';
			html += '<input type="hidden" name="id" value="'+channel.id+'" />';
			html += '</span>';
			html += '</li>';
		}
		$('#main-2').append(html);
		$('#more-apk').show();
		market.bindChannelDrag();
	} else {
		pageChannel = 1;
	}
};
market.bindChannelDrag = function() {
	$("#main-2 li").disableSelection();
	$('#main-2 li').draggable({ 
        revert: "invalid",
		helper: 'clone',
        opacity: 0.6,
        cursor: 'move',
        connectToSortable: '.destAssembly7'
	});
	
    $(".destAssembly7").sortable({
	  connectWith: "#main-2 li",
	  update : function(event, ui) {
	  	if($(ui.item[0]).find(".delete-link").length === 0) {
			$(ui.item[0]).append('<a title="删除" class="btn-icon delete-link" onclick="$(this).parent().remove();">删除</a>');
		}
	  }
    });
    
    $(".destAssembly7").on("mouseover", "li", function() {
		$(this).addClass("hover");
	});
	$(".destAssembly7").on("mouseleave", "li", function() {
		$(this).removeClass("hover");
	});
};
market.getMoreChannel = function() {
	pageChannel++;
	market.loadChannelData();
};
market.initChannel = function() {
	market.loadChannelData();
	$('#more-apk').click(function(){
		console.log('get-more-apk....');
		market.getMoreChannel();
	});
};
function doSearchChannel(){
	pageChannel = 1;
	channelName = $('#channelName').val();
	$('#main-2').empty();
	market.initChannel();
}
////////////////////////////////////////////////////////////////////////////////
$(function() {
    $("#tabs").tabs();
    market.initChannel();
});
</script>
</body>
</html>