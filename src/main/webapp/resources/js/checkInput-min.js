function isVisible(c){var a,d;try{d=c.style.display;a=c.style.visibility}catch(b){}if(d=="none"||a=="hidden"){return false}return true}function checkPrVis(a){var b=a.parentNode;do{if(b==undefined||b=="undefined"){return true}else{if(!isVisible(b)){return false}}}while(b=b.parentNode);return true}function isnull(b){var a;if(b.length==0){return true}for(a=0;a<b.length;a++){if(b.charAt(a)!=" "){return false}}return true}function f_check_reg(d,c,a,e){if(isnull(d.val())){f_alert(d,a);return false}var b=new RegExp(c);if(b.test(d.val())){return true}f_alert(d,e);return false}function f_check_null(b,a){if(isnull(b.val())){f_alert(b,a);return false}return true}function f_alert(a,b){top.showMsg(market.content.title,b,alert);if(isVisible(a)&&checkPrVis(a)){a.focus()}}function f_check_strLength(f,a,g){var b=0;var e=f.val();for(var d=0;d<e.length;d++){var h=e.charCodeAt(d);if((h>=1&&h<=126)||(65376<=h&&h<=65439)){b++}else{b+=2}}if(b>a){f_alert(f,g);return false}return true}function isNum(a){var b=/^[0-9]{1,30}$/;return b.test(a)};
