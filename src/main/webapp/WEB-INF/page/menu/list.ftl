<#assign base=request.contextPath />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset="utf-8">
<title> 菜单管理 | 菜单列表</title>
<@fm.header />
<script>
function doSearch(){    
    $('#tt').datagrid('load', {
        itemid: $('#itemid').val(),
        productid: $('#productid').val()    
    });
}
function add(){
    showMyWindow('菜单管理 | 新增菜单', '/menu/add', 380,340);
}
var editingId;
function edit(){
	if (editingId != undefined){
		$('#tt').treegrid('select', editingId);
		return;
	}
	var row = $('#tt').treegrid('getSelected');
	if (row){
		editingId = row.resourceId;
		$('#tt').treegrid('beginEdit', editingId);
	}
}
function save(){
	if (editingId != undefined){
		var t = $('#tt');
		t.treegrid('endEdit', editingId);
		var row = $('#tt').treegrid('getSelected');
		$.post('/menu/update',row,function(data,status){
			console.info(data);
			if(data != undefined){
				$.messager.alert("菜单修改", data.return_msg);
			}
		},"JSON");
		editingId = undefined;
	}
}
function cancel(){
	if (editingId != undefined){
	$('#tt').treegrid('cancelEdit', editingId);
		editingId = undefined;
	}
}
function del(){
		var row = $('#tt').treegrid('getSelected');
		if (row){
			editingId = row.resourceId;
			var parent=$('#tt').treegrid("getParent",editingId);
			showConfirm(market.content.title, market.content.confirm, function(){
			  $.post('/menu/'+editingId+'/delete',editingId, function(data) {
			  	if (data.return_code == '1') {
			  		$('#tt').treegrid('reload', parent.resourceId);
			  		$.messager.alert(market.content.title, data.return_msg);
				} else {
					$.messager.alert(market.content.title, data.return_msg);
				}
			  });
		   });
		}
}
function formatterType(value,row,index){
	if (value==1) {
		return '模块';
	} else {
		return '菜单';
	}
}
</script>
</head>
<body class="cmp-list-body">
<div id="tb" style="padding:3px">
    <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">新增</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del();">删除</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="edit();">编辑</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="save();">保存修改</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="cancel();">取消编辑</a>
</div>
<div>
<table id="tt" title="菜单管理 | 菜单列表" class="easyui-treegrid" height="100%"
	url="${base}/menu/list?json" toolbar="#tb" rownumbers="true"
	idField="resourceId" treeField="resourceName"fitColumns="true">
	<thead>
		<tr>
            <th align="left" width="18%" field="resourceName" editor='text'>菜单名称</th>    
            <th align="center" width="10%" field="description"editor='text'>菜单描述</th>
            <th align="center" width="12%" field="permission"editor='text'>权限</th>
            <th align="center" width="12%" field="resourceUrl"editor='text'>菜单地址</th>
            <th align="center" width="10%" field="displaySort"editor='numberbox'>展示顺序</th>
            <th align="center" width="10%" field="displayType"editor='numberbox' formatter="formatterType">展示类型</th>
		</tr>
	</thead>
</table>
</div>
</body>
</html>