<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset="utf-8">
<title>菜单管理 | 新增菜单</title>
<@fm.header />
<style>
tr.hide
{display:none;}
</style>
</head>
<body>
<div class="easyui-layout" style="text-align: center; " fit="true">
	<div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
	<form id="ff" method="post" >
	    <table style=" border-spacing:10px; ">
	        <tr>
	            <td><label for="resourceName">菜单名称:</label></td>
	            <td><input class="easyui-validatebox" type="text" name="resourceName" data-options="required:true,validType:'length[1,20]'"></td>
	        </tr>
	        <tr>
			<td><label for="displayType">类型 </td>
			<td>
				<input id="menu.isLeaf" name="displayType" value="1" checked="" onclick="changeType(this);" type="radio">模块
				<input id="menu.isLeaf" name="displayType" value="2" onclick="changeType(this);" type="radio">菜单
			</td>
			</tr>
	        <tr>
	            <td><label for="displaySort">展示顺序:</label></td>
	            <td><input class="easyui-numberbox" type="text" id="displaySort" name="displaySort" required="true" ></td>
	        </tr>
	        <tr>
	            <td><label for="description">菜单描述:</label></td>
	            <td><textarea class="easyui-validatebox" type="text" name="description" data-options="validType:'length[1,25]'"></textarea></td>
	        </tr>
	        <tr class="hide" id="menu_url">
	            <td><label for="resourceUrl">菜单地址:</label></td>
	            <td><input class="easyui-validatebox" type="text" id="resourceUrl"  name="resourceUrl" data-options="validType:'length[1,100]'"></td>
	        </tr>
	        <tr class="hide" id="menu_parent">
	            <td><label for="parent">所属模块:</label></td>
	            <td><input id="parent" class="easyui-combobox" name="parent"
   				 data-options="valueField:'resourceId',textField:'resourceName',url:'/menu/parent?json'"></td>
	        </tr>
	    </table>
	</form>
	</div>
	<div region="south" border="false" style="text-align: center; padding: 5px 5px 5px 0;">
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm('/menu/add')">提交</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm();">重置</a>
	</div>
</div>
<script>
$(function(){
	$("input").bind("keyup afterpaste", function(){
		$(this).val($(this).val().replace(/\s/g,''));
	});
	
});
function changeType(obj) {
	if (obj.value == 2) {//菜单
		$("#menu_parent").removeClass("hide");
		$("#menu_url").removeClass("hide");
		$("#resourceUrl").validatebox({required:true});
		$("#parent").combobox({required:true});
	} else if (obj.value == 1){//模块
		$("#menu_parent").addClass("hide");
		$("#menu_url").addClass("hide");
		$("#resourceUrl").validatebox({required:false});
		$("#parent").combobox({required:false});
	}
}

</script>
</body>
</html>