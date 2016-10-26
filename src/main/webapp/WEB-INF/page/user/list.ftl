<#assign base=request.contextPath />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset="utf-8">
<title>帐号管理 | 帐号列表</title>
<@fm.header />
<script>
function doSearch(){    
    $('#tt').datagrid('load', {    
        userName: $('#userName').val(),    
        rId: $('#rId').combobox('getValue')    
    });
}
function add(){
    showMyWindow('帐号管理 | 新建帐号', '/user/add', 400, 400);
}
function edit(){
    var row = $('#tt').datagrid('getSelected');
    if (row){
	   showMyWindow('帐号管理 | 编辑帐号', '/user/'+row.userId+'/update', 400, 400);
    } else {
    	showMsg(market.content.title, market.content.tips, alert);
    }
}
function channel(){
   var row = $('#tt').datagrid('getSelected');
    if (row){
	    showMyWindow('帐号管理 | 设置渠道', '/user/'+row.userId+'/channel', 980, 560);
    } else {
    	showMsg(market.content.title, market.content.tips, alert);
    }
}
function setMarket(){
    var row = $('#tt').datagrid('getSelected');
    if (row){
	    showMyWindow('帐号管理 | 设置市场', '/user/'+row.userId+'/market', 980, 560);
    } else {
    	showMsg(market.content.title, market.content.tips, alert);
    }
}
function del(){
	var row = $('#tt').datagrid('getSelected');
    if (row){
	   showConfirm(market.content.title, market.content.confirm, function(){
		  $.post('/user/'+row.userId+'/delete', function(data) {
		  	if (data.return_code == '1') {
				top.showMsg(market.content.title, data.return_msg, alert);
				$("#tt").datagrid('reload');
			} else {
				$.messager.alert(market.content.title, data.return_msg);
			}
		  });
	   });
    } else {
    	showMsg(market.content.title, market.content.tips, alert);
    }
}
</script>
</head>
<body class="cmp-list-body">
<div id="tb" style="padding:3px">
    <span>帐号:</span>    
    <input id="userName" style="line-height:20px;border:1px solid #ccc">
    <span>所属角色:</span>
    <input id="rId" class="easyui-combobox" style="width: 155px;" name="roleId" formatter="formatterRoleId"  data-options="
						url:'/role/content',
						method:'get',
						valueField:'roleId',
						textField:'roleName'" />
    <a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="doSearch();">查询</a> 
    <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">新增</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del();">删除</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="edit();">编辑</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-auth" plain="true" onclick="channel();">设置渠道</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-auth" plain="true" onclick="setMarket();">设置市场</a>
</div>
<table id="tt" class="easyui-datagrid" title="帐号管理 | 帐号列表" iconCls="icon-save" 
	url="${base}/user/list?json" toolbar="#tb" rownumbers="true" idField="userId"
	singleSelect="true" pagination="true" fitColumns="true" pageSize="15" pageList="[15,30,60,80,100]">    
    <thead>
        <tr>
            <th align="center" width="10%" field="userName">帐号</th>    
            <th align="center" width="10%" field="nickname">昵称</th>  
            <th align="center" width="20%" field="description">备注</th>
            <th align="center" width="12%" field="createTime">创建时间</th>
            <th align="center" width="12%" field="modifyTime">更新时间</th>
        </tr>
    </thead>   
</table>
</body>
</html>