/*
 * 判断当前对象是否可见
 */ 
function isVisible(obj){   
    var visAtt,disAtt;   
    try{   
        disAtt=obj.style.display;   
        visAtt=obj.style.visibility;   
    }catch(e){}   
    if(disAtt=="none" || visAtt=="hidden")   
        return false;   
    return true;   
}   
    
/*
 * 判断当前对象及其父对象是否可见
 */ 
function checkPrVis(obj){   
    var pr=obj.parentNode;   
    do{   
        if(pr == undefined || pr == "undefined") return true;   
        else{   
            if(!isVisible(pr)) return false;   
        }   
    }while(pr=pr.parentNode);   
    return true;   
}  

/* 
 * 检测字符串是否为空 
 */ 
function isnull(str)   
{   
    var i;   
    if(str.length == 0)   
        return true;   
    for (i=0;i<str.length;i++)   
    {   
        if (str.charAt(i)!=' ')    
            return false;   
    }   
    return true;   
}   

/*
 * 通用正则表达式验证方法
 * 参数：
 * obj：验证对象
 * reg：正则表达式
 * msg：提示信息
 */
function f_check_reg(obj,reg,nullmsg,msg){  
	
	if(isnull(obj.val())){
		f_alert(obj , nullmsg); 
    	return false;    
	}
    var re = new RegExp(reg);   
    if (re.test( obj.val() )) {    
      return true;   
    }   
    f_alert(obj,msg);   
    return false;   
}

/*
 * 通用正则表达式验证方法是否为空
 * 参数：
 * obj：验证对象
 * reg：正则表达式
 * msg：提示信息
 */
function f_check_null(obj,nullmsg){  
	
	if(isnull(obj.val())){
		f_alert(obj , nullmsg); 
    	return false;    
	} 
    return true;   
}

/*
*  弹出警告对话框，用户点确定后将光标置于出错文本框上， 
*  并且将原来输入内容选中。
*  根据对象输出提示信息--对象置于信息中
*/
function f_alert(obj,alertInfo)   
{     
	top.showMsg(market.content.title, alertInfo, alert);
    if(isVisible(obj) && checkPrVis(obj))   
        obj.focus();   
}

/*
 * 通用字符串长度验证
 * obj: 验证对象
 * len: 限制的长度 
 * msg: 提示消息
 */
function f_check_strLength(obj, len, msg)
{   
	var w = 0;   
	var s = obj.val();
	for (var i=0; i<s.length; i++) {   
	   var c = s.charCodeAt(i);   
	   //单字节加1   
	   if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) {   
	    w++;   
	   }   
	   else {   
	    w+=2;   
	   }   
	}   
	if (w > len) {   
	   f_alert(obj , msg); 
	   return false;   
	}   
	return true;   
}

/**
 * 校验传入的参数是否是数字(INT 长度)
 * @param {Object} num
 */
function isNum(num)
{
    var reg = /^[0-9]{1,30}$/;
    return reg.test(num);
}