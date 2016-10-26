<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset="utf-8">
<title>帐号管理 | 新增帐号</title>
<@fm.header />
</head>
<body>
<div class="easyui-layout" style="text-align: center; height: 270px;" fit="true">
	<div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
	<form id="ff" method="post">
	    <table>
	        <tr>
	            <td><label for="userName">帐号:</label></td>
	            <td><input class="easyui-validatebox" type="text" name="userName" data-options="required:true,validType:'length[0,25]'"></td>
	        </tr>
	        <tr>
	            <td><label for="nickname">昵称:</label></td>
	            <td><input class="easyui-validatebox" type="text" name="nickname" data-options="validType:'length[0,25]'"></td>
	        </tr>
	        <tr>
	            <td><label for="password">密码:</label></td>
	            <td><input class="easyui-validatebox" type="text" name="password" data-options="required:true,validType:'length[0,20]'"></td>
	        </tr>
	        <tr>
	            <td><label for="roleId">所属角色:</label></td>
	            <td><input id="cc" class="easyui-combobox" style="width: 155px;" name="roleId" formatter="formatterRoleId"  data-options="
						required:true,
						url:'/role/content',
						method:'get',
						valueField:'roleId',
						textField:'roleName',
						multiple:true " /></td>
	        </tr>
	        <tr>
	            <td><label for="companyId">所属公司:</label></td>
	            <td><input id="cc" class="easyui-combobox" style="width: 155px;" name="companyId"  data-options="
	            		required:true,
						url:'/company/content',
						method:'get',
						valueField:'companyId',
						textField:'companyName',
						panelHeight:'auto' " /></td>
	        </tr>
	        <tr>
	            <td><label for="description">备注:</label></td>
	            <td><input class="easyui-validatebox" name="description" style="width:155px;" data-options="validType:'length[0,30]'"></td>
	        </tr>
	    </table>
	</form>
	</div>
	<div region="south" border="false" style="text-align: center; padding: 5px 5px 5px 0;">
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm('/user/add');">提交</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm();">重置</a>
	</div>
</div>
<script>
$(function(){
	$("input").bind("keyup afterpaste", function(){
		$(this).val($(this).val().replace(/\s/g,''));
	});
});
function formatterRoleId(value){
	value=$("#cc").combobox('getValues');
	return value;
}
</script>
</body>
</html>