<#assign base=request.contextPath />
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8"/>
<title>酷爱乐园运营管理系统</title>
<style type="text/css">
body {
	MARGIN: 0px;
	background-image: url();
}
.STYLE1 {font-size: 14px}
.STYLE2 {font-size: 13px;color:#F00;}
</style>
<style type="text/css">
.div1{
	/*padding-left:30px;*/
	background:url(${path}${base}/resources/css/login/images/1.png) 4px  no-repeat;
	background-color:#FFFFFF;
	width:170px;
	height:25px;
	line-height:25px;
	border:1px solid #ccc;
	margin-top:15px;
	}
	.div2{
	/*padding-left:30px;*/
	background:url(${base}/resources/css/login/images/2.png) 4px  no-repeat;
	background-color:#FFFFFF;
	width:170px;
	height:25px;
	line-height:25px;
	border:1px solid #ccc;
	}
input{
		border:0;
	font-size:18px;	
	}
#CheckPic {
	margin: 0 0 0 10px;
	width: 66px;
	border: #71CCD8 solid 1px;
}
</style>
<script>
if(window.parent.length>0){
	window.parent.location = "/user/login";
}
</script>
</head>
<body>

<img src="${base}/resources/css/login/img/loginNew.jpg" width="100%" height="100%" style="z-index:-100;position:absolute;left:0;top:0">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="200">&nbsp;</td>
  </tr>
</table>

<form action="${base}/login" method="post" id="loginForm">

<table width="530" height="280" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:-50px;">
  <tr>
    <td background="${base}/resources/css/login/img/loginInput2.png">
    
    <table width="320" border="0" align="center" cellpadding="0" cellspacing="15" style="margin-top:-270px;" >
    
      <tr>
   
        <!--<td width="25%"><span class="STYLE2" >帐　号：</span></td>-->
        <br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
        <td width="75%" style="padding-left:280px;"><div class="div1">
       	<input name="account" type="text" id="username"  style="height:22px;width:170px;outline:none; vertical-align:middle;" onkeydown="if(event.keyCode==32) return false; if(event.keyCode==13) return checkForm();" value="<#if user_account?exists>${user_account}</#if>" />
        </div></td>
      </tr>
      <tr>
        <!--<td width="25%"><span class="STYLE2">密　码：</span></td>-->
        <td width="75%" style="padding-left:280px;"><div class="div2">
          <input name="password" type="password" id="userpass" style="height:22px;width:170px;outline:none;vertical-align:middle;" onkeydown="if(event.keyCode==32) return false; if(event.keyCode==13) return checkForm();" value="<#if user_password?exists>${user_password}</#if>"/>
        </div></td>
      </tr>
      <tr>
        <!--<td><span class="STYLE2">验证码：</span></td>-->
        <td style="padding-left:280px;"><input name="randomCode" type="text" id="randomCode" size="5" onkeydown="if(event.keyCode==32) return false; if(event.keyCode==13) return checkForm();" value="" /><img id="CheckPic" title="看不清，换一张" style="cursor:pointer;padding-left:0px;" src="${base}/captchaImage" align="absmiddle" onclick="document.getElementById('CheckPic').src='${base}/captchaImage?'+Math.random()"></td>
      </tr>
      <tr>
        <td style="padding-left:280px;"><div><img src="${base}/resources/css/login/images/loginIn.png" width="145" height="45"  onclick="return checkForm();" /></div><div id="lblerr" style="color:#F00"><#if msg?exists>${msg}</#if></div></td>
        </tr>
    </table></td>
  </tr>
</table>
</form>

<script src="${base}/resources/js/jquery-1.8.0.min.js"></script>
<script src="${base}/resources/js/login/fun.base.js"></script>
<script>
$(function(){
	$("input.text").each(function(index, ele){
		if ($(ele).val() == '') {
			this.focus();
			return false;
		}
	});
    var msg = "${hasLogin?exists}";
	if(msg!=""){
		if(msg=="hasLogin"){
			alert("该账号已在其他地方登陆！");
			return;
		}
	}
});
function checkForm() {
	var uname = $("#username").val();
	if(uname == ""){
		$("#lblerr").text("帐号不能为空！");
		$("#username").focus();
		return false;
	}
	var passwd = $("#userpass").val();
	if(passwd == ""){
		$("#lblerr").text("密码不能为空！");
		$("#userpass").focus();
		return false;
	}
	var randomCode = $("#randomCode").val();
	if(randomCode == ""){
		$("#lblerr").text("验证码不能为空！");
		$("#randomCode").focus();
		return false;
	}
	$("#loginForm").submit();
	return true;
}
</script>
</body>
</html>