function setCookieInSession(name,value){
	document.cookie = name + "="+ escape (value) ;
}
//写cookies
function setCookie(name,value)
{
	var Days = 30;
	var exp = new Date(); 
	exp.setTime(exp.getTime() + Days*24*60*60*1000);
	document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
}
//读取cookies
function getCookie(name)
{
	var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
	if(arr=document.cookie.match(reg)) return unescape(arr[2]);
	else return "";
}
function saveCookies(){
	if(document.getElementById("j_username").value!=""){
		setCookie("loginName",document.getElementById("j_username").value);
	}
}
function loadCookies(loginform){
	document.getElementById("j_username").value=getCookie("loginName");
}

//判断登陆是否为空或转换用户名'.'后面的大写
function upperLogin(ele){
 var j_username=ele.j_username.value.trim();
 var j_password =ele.j_password.value;
 if(""==j_username){
 	alert("用户名不能为空");
 	ele.j_username.focus();
 	return false ;
 }
 if(""==j_password){
 	alert("密码不能为空");
 	ele.j_password.focus();
 	return false;
  }
  var pos = j_username.indexOf(".");
  /*if(0<pos){
   j_username=j_username.substring(0,pos)+j_username.substring(pos,j_username.length).toUpperCase();
   ele.j_username.value=j_username;
  }*/
  ele.j_username.value=j_username.toLowerCase();
  return true;
}

//去掉首与尾的空格
String.prototype.trim = function(){
    return this.replace( /(^\s*)|(\s*$)/g, '' ) ;
}

function covertDigitToCn(num){
   if(num==0){return "零";} 
   if(num==1){return "一";} 
   if(num==2){return "二";}
   if(num==3){return "三";} 
   if(num==4){return "四";} 
   if(num==5){return "五";} 
   if(num==6){return "六";} 
   if(num==7){return "七";}
   if(num==8){return "八";}
   if(num==9){return "九";}
   if(num==10){return "十";}
}

