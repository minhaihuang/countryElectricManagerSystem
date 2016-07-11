function openWindowWithName(url,width,height,name) {
  var left=(screen.availWidth-width)/2;
  var top=(screen.availHeight-height)/2;
  var ref="";
  ref += "width="+width+"px,height="+height+"px,";
  ref += "left="+left+"px,top="+top+"px,";
  ref += "resizable=yes,scrollbars=yes,status=yes,toolbar=no,systemmenu=no,location=no,borderSize=thin";//channelmode,fullscreen
  var childWindow = window.open(url,name,ref,false);
  childWindow.focus();
}

function  openWindow(url,width,height){
  openWindowWithName(url,width,height,'newwindow');
}



function showJinDuTiao(){
	var colors=["red","green","yellow","white"];
		//设置遮罩层
		var div1=document.createElement("div");
		div1.style.position = 'absolute';//使用绝对定位
		div1.style.top=0;//距离顶部
		div1.style.left=0;//距离左部
		div1.style.width="100%";//相对屏幕宽度
		div1.style.height="100%";//相对屏幕高度
		div1.style.backgroundColor="gray";//背景颜色
		div1.style.opacity = '0.3';//设置透明度
		document.body.appendChild(div1);
		
		
		//设置进度条所在的层
		var div2=document.createElement("div");
		div2.style.position = 'absolute';//使用绝对定位
		div2.style.top='40%';//距离顶部
		div2.style.left='30%';//距离左部
		div2.style.width="40%";//相对屏幕宽度
		div2.style.height="2%";//相对屏幕高度
		div2.style.backgroundColor="gray";//背景颜色
		document.body.appendChild(div2);
		
		//设置进度条
		var div3=document.createElement("div");
		div2.appendChild(div3);
		div3.style.width="20%";//相对屏幕宽度
		div3.style.height="100%";//相对屏幕高度
		div3.style.backgroundColor=colors[0];//背景颜色
		
		//计时器，使进度条随时间而发生变化
		var percent=1;
		var colorIndex=1;
		setInterval(function(){
			div3.style.width=percent+"%";//进度条随时间变化
			percent+=1;
			
			if(percent==100){//进度条达到100%
				percent=1;
				div3.style.backgroundColor=colors[colorIndex];//换种颜色
				colorIndex++;
			}
		}, 100);
		}


//格式化日期函数
function formatDate(date ,pattern){
    if(!pattern){
	    pattern="yyyy-MM-dd";
    }
	var o = {
		'M+' : date.getMonth() + 1, //月份\n"
		'd+' : date.getDate(), //日 \n"
		'h+' : date.getHours(), //小时\n"
		'm+' : date.getMinutes(), //分 \n"
		's+' : date.getSeconds(), //秒 \n"
		'S' : date.getMilliseconds()
	};
	//替换填充年份
	if (/(y+)/.test(pattern)) {
		pattern = pattern.replace(RegExp.$1, (date.getFullYear() + '')
				.substr(4 - RegExp.$1.length));
	}
	//填充替换剩余的时间元素
	for ( var key in o){
		if (new RegExp('(' + key + ')').test(pattern)){
			pattern = pattern.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[key])
					: (('00' + o[key]).substr(('' + o[key]).length)));
		}
	}
	//返回格式化结果
    return pattern;	
}

