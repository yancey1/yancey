<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset="utf-8">
<title>帐号管理 | 编辑帐号</title>
<@fm.header />
<style type="text/css">
#main-2 li {
	margin: 5px auto;
	list-style-type: none;
	width: 750px;
	height: 80px;
	border: 1px dashed #f8b62b;
	border-radius: 4px;
     -moz-border-radius: 4px;
  -webkit-border-radius: 4px;
}
i {
	display: inline;
	font-style:normal;
	margin: 2px 4px;
}
</style>
</head>
<body>
<div class="easyui-layout" style="text-align: center; height: 270px;" fit="true">
	<div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
	    <div id="main-2"></div>
	</div>
	<div region="south" border="false" style="text-align: center; ">
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="allSelect()">全选</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="killAllSelect()">取消全选</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm1('/user/${user.userId}/market')">提交</a>
	</div>
</div>
<form id="ff" method="post">
	<input type="hidden" name="resObj" value="">
</form>
<script>
function submitForm1(url){
    $('#ff').form(
	'submit',
	{
		url : url,
		onSubmit : function() {
			// 处理数据及验证
			var f_str = '';
			$('#main-2').find('input[name="pindaoId"]:checked').map(function(idx, ele){
				f_str += ';'+$(ele).val();
			});
		    $("input[name='resObj']").val(f_str);
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
function bindSelectMarket() {
	$('input[name="pindaoId"]').click(function(){
		var checkedCount = $(this).parent().parent().find('input[name="pindaoId"]:checked').size();
		var flag = checkedCount > 0 ;
		$(this).parent().parent().parent().find('input[name="marketId"]').attr("checked", flag);
	});
	$('input[name="marketId"]').click(function(){
		$(this).parent().parent().parent().find('input[name="pindaoId"]').attr("checked", this.checked);
	});
}
function allSelect(){
	$('#main-2').find('input[type="checkbox"]').attr("checked",true);
}
function killAllSelect(){
	$('#main-2').find('input[type="checkbox"]').attr("checked",false);
}
////////////////////////////////////////////////////////////////////////////////////////
var marketId = '';
var apiURLMarket = '/market/form?json';
market.loadMarketData = function() {
	$.ajax({
		url: apiURLMarket,
		dataType: 'json',
		data: {marketId: marketId},
		success: market.onLoadMarketData
	});
};
market.onLoadMarketData = function(data) {
	var mArr = [];
	if (data != null && data !== undefined && data.length > 0) {
		var html = '';
		var hh = '<p>';
		var i=0, length=data.length, market;
		for(i; i<length; i++) {
			market = data[i];
			var marketId = market.marketId;
				html += '<li>';
				
				var checked = '';
				<#list user.markets as m>
					if(marketId == '${m.marketId}'){
						checked = 'checked = "checked"';
					}
				</#list>
				html += '<p><i><input type="checkbox" '+checked+' name="marketId" value="' + market.marketId + '"/><label style="font-weight: bold;font-size:16px;">'+ market.marketId + '/' + market.marketName+'</label></i></p>';
				html += '<p>';
				
				checked = '';
				<#list user.markets as m>
					var a = marketId+'/5';
					var b = '${m.marketId}'+'/'+${m.pindaoId};
					if(marketId == '${m.marketId}' &&  a == b){
						checked = 'checked = "checked"';
					}
				</#list>
				html += '<i><input type="checkbox" '+checked+' name="pindaoId"  value="'+market.marketId+'/5"/><label style="font-size:14px;">欢迎页</label></i>';
				
				checked = '';
				<#list user.markets as m>
					var a = marketId+'/1';
					var b = '${m.marketId}'+'/'+${m.pindaoId};
					if(marketId == '${m.marketId}' &&  a == b){
						checked = 'checked = "checked"';
					}
				</#list>
				html += '<i><input type="checkbox" '+checked+' name="pindaoId"  value="'+market.marketId+'/1"/><label style="font-size:14px;">首页</label></i>';
				
				checked = '';
				<#list user.markets as m>
					var a = marketId+'/2';
					var b = '${m.marketId}'+'/'+${m.pindaoId};
					if(marketId == '${m.marketId}' &&  a == b){
						checked = 'checked = "checked"';
					}
				</#list>
				html += '<i><input type="checkbox" '+checked+' name="pindaoId"  value="'+market.marketId+'/2"/><label style="font-size:14px;">专题</label></i>';
				
				checked = '';
				<#list user.markets as m>
					var a = marketId+'/3';
					var b = '${m.marketId}'+'/'+${m.pindaoId};
					if(marketId == '${m.marketId}' &&  a == b){
						checked = 'checked = "checked"';
					}
				</#list>
				html += '<i><input type="checkbox" '+checked+' name="pindaoId"  value="'+market.marketId+'/3"/><label style="font-size:14px;">软件</label></i>';
				
				checked = '';
				<#list user.markets as m>
					var a = marketId+'/4';
					var b = '${m.marketId}'+'/'+${m.pindaoId};
					if(marketId == '${m.marketId}' &&  a == b){
						checked = 'checked = "checked"';
					}
				</#list>
				html += '<i><input type="checkbox" '+checked+' name="pindaoId"  value="'+market.marketId+'/4"/><label style="font-size:14px;">游戏</label></i>';
				
				checked = '';
				<#list user.markets as m>
					var a = marketId+'/6';
					var b = '${m.marketId}'+'/'+${m.pindaoId};
					if(marketId == '${m.marketId}' &&  a == b){
						checked = 'checked = "checked"';
					}
				</#list>
				html += '<i><input type="checkbox" '+checked+' name="pindaoId"  value="'+market.marketId+'/6"/><label style="font-size:14px;">猜你喜欢</label></i>';
				
				checked = '';
				<#list user.markets as m>
					var a = marketId+'/7';
					var b = '${m.marketId}'+'/'+${m.pindaoId};
					
					if(marketId == '${m.marketId}' &&  a == b){
						checked = 'checked = "checked"';
					}
				</#list>
				html += '<i><input type="checkbox" '+checked+' name="pindaoId"  value="'+market.marketId+'/7"/><label style="font-size:14px;">搜索</label></i>';
				
				checked = '';
				<#list user.markets as m>
					var a = marketId+'/8';
					var b = '${m.marketId}'+'/'+${m.pindaoId};
					
					if(marketId == '${m.marketId}' &&  a == b){
						checked = 'checked = "checked"';
					}
				</#list>
				html += '<i><input type="checkbox" '+checked+' name="pindaoId"  value="'+market.marketId+'/8"/><label style="font-size:14px;">静默下载</label></i>';
				
				checked = '';
				<#list user.markets as m>
					var a = marketId+'/9';
					var b = '${m.marketId}'+'/'+${m.pindaoId};
					
					if(marketId == '${m.marketId}' &&  a == b){
						checked = 'checked = "checked"';
					}
				</#list>
				html += '<i><input type="checkbox" '+checked+' name="pindaoId"  value="'+market.marketId+'/9"/><label style="font-size:14px;">易查宝</label></i>';
				
				checked = '';
				<#list user.markets as m>
					var a = marketId+'/10';
					var b = '${m.marketId}'+'/'+${m.pindaoId};
					
					if(marketId == '${m.marketId}' &&  a == b){
						checked = 'checked = "checked"';
					}
				</#list>
				html += '<i><input type="checkbox" '+checked+' name="pindaoId"  value="'+market.marketId+'/10"/><label style="font-size:14px;">更新管理</label></i>';
				
				checked = '';
				<#list user.markets as m>
					var a = marketId+'/11';
					var b = '${m.marketId}'+'/'+${m.pindaoId};
					
					if(marketId == '${m.marketId}' &&  a == b){
						checked = 'checked = "checked"';
					}
				</#list>
				html += '<i><input type="checkbox" '+checked+' name="pindaoId"  value="'+market.marketId+'/11"/><label style="font-size:14px;">详情页</label></i>';
				
	html += '</p>';
				html += '</li>';
			}
		$('#main-2').append(html);
		bindSelectMarket();
	} 
};
$(function() {
	market.loadMarketData();
});
</script>
</body>
</html>