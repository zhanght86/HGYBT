<!--
//ProposalShowPic.js

var w;	        //图片的宽度
var h;          //图片的高度，可不使用
//var b_img	= 0;  //放大图片的次数
//var s_img = 0;	//缩小图片的次数

//得到使用中的图片的实际宽度（单位：象素）
//if (typeof(top.fraPic.service) == "object") w = top.fraPic.service.width;
//alert("wControl:"+w);
//if (w == 0) w = 800;

//图片的队列数组
//pic_name = new Array();
//pic_place = 0;

function document_onkeydown() {
	//去键值
	var keycode = event.keyCode
	//var key = event
    //var realkey = String.fromCharCode(event.keyCode)
    //alert("keycode: " + keycode + "\nrealkey: " + realkey)
  try {
    if (top.initWidth == 0) {
      top.initWidth = top.fraPic.service.width;
      w = top.fraPic.service.width;
    } else {
      w = top.initWidth; 
    }
    top.pic_name = top.fraPic.arrPicName;
    //alert(w + "\n" + top.pic_name);
  } catch(ex) {
    try { 
      top.fraPic.innerHTML = "该扫描件图片未加载完成或不存在！";
    } 
    catch(e) {}
  }

	//判定是否按下PageUp
	if (keycode == "33") {
	  try { hiddenPosition(); } catch(e) {}
	  
		//循环图片队列
		for (var i=top.pic_name.length; i >= 0; i--){
			//找到第一个满足条件的队列项
			if (i < top.pic_place){
//				//改变左页的图片对象
//				top.fraPic.service.src = top.pic_name[i];
//				top.fraPic.centerPic.innerHTML = top.pic_name[i];
//				//重新定位所选图片所在队列的位置
//				top.pic_place = i;
				goToPic(i);
				break;
			}
		}
	}
	//判定是否按下PageDown
	if (keycode == "34") {
	  try { hiddenPosition(); } catch(e) {}
	  
		for (var i=0; i < top.pic_name.length; i++){
			if (i > top.pic_place){
//				top.fraPic.service.src = top.pic_name[i];
//				top.fraPic.centerPic.innerHTML = top.pic_name[i];
//				top.pic_place = i;
        goToPic(i);
				break;
			}
		}
	}
	//判定是否按下小键盘上的*
	if (keycode == "106") {
		//还原图片的实际大小，并将放大和缩小次数置为0
		top.fraPic.service.width = w
		top.s_img = 0;
		top.b_img = 0;
	}
	
	//判定是否按下小建盘的+，同时按住ctrl
	if ((keycode == "107")&&(event.ctrlKey == true)) {
	  try { hiddenPosition(); } catch(e) {}
	  
		//判定放大次数，目前设置为10次，可修改
		//alert("ctrl+" + top.b_img+ "\n" + w + "\n" + (w / (1 + 0.2 * (top.s_img - 1))) );
		if (top.b_img <= 10){
			//判定是否缩小过
			if (top.s_img != 0){
				top.fraPic.service.width = w / (1 + 0.2 * (top.s_img - 1))
				top.s_img = top.s_img - 1;
			}
			else{
				top.b_img = top.b_img + 1;
				top.fraPic.service.width = w * (1 + 0.2 * top.b_img)
			}
		}
	}
	
	//判定是否按下小建盘的-，同时按住ctrl，不可改变到比原图还小
	if ((keycode == "109")&&(event.ctrlKey == true)) {
	  try { hiddenPosition(); } catch(e) {}
	  
		if (top.b_img != 0){
			top.fraPic.service.width = w * (1 + 0.2 * (top.b_img - 1))
			top.b_img = top.b_img - 1;
		}
	}
	
	//判定是否按下小键盘/，可改变到比原图还小，和上面的-处理两选一使用
	if (keycode == "111") {
	  try { hiddenPosition(); } catch(e) {}
	  
		//判定缩小次数，目前设置为10次，可修改
		if (top.s_img <= 10){
			if (top.b_img != 0){
				top.fraPic.service.width = w * (1 + 0.2 * (top.b_img - 1))
				top.b_img = top.b_img - 1;
			}	else {
				top.s_img = top.s_img + 1;
				top.fraPic.service.width = w / (1 + 0.2 * top.s_img)
			}
		}
	}
	
	//判定是否按下小键盘HOME，切换横竖显示图片（Minim增加该方法）
	if (keycode == "36") {
	  //得到使用中的图片的实际宽度（单位：象素）
	  if (top.fraSet.rows == "*") {
	    top.fraSet.rows = "0,50%,*,0";
	    top.fraSet.cols = "*";
	  } else {
	    top.fraSet.rows = "*";
	    top.fraSet.cols = "0%,40%,*,0%";
	  }
	}
	
	//判定是否按下小键盘END，旋转显示图片（Minim增加该方法）
	if (keycode == "35") {
	  var rotation = (top.fraPic.filterDIV.filters.item(0).rotation + 1) % 4;
	  top.fraPic.filterDIV.filters.item(0).rotation = rotation;
	}
	
	var scrollW = 300;
	var scrollH = 300;
	
	//图片桢控制
	//按上箭头+ctrlKey
	if ((keycode == "104") && (event.ctrlKey == true)) {
	  top.fraPic.scrollBy(0, -scrollH);
	}	
	//按下箭头+ctrlKey
	if ((keycode == "98") && (event.ctrlKey == true)) {
	  top.fraPic.scrollBy(0, scrollH);
	}	
	//按左箭头+ctrlKey
	if ((keycode == "100") && (event.ctrlKey == true)) {
	  top.fraPic.scrollBy(-scrollW, 0);
	}	
	//按右箭头+ctrlKey
	if ((keycode == "102") && (event.ctrlKey == true)) {
	  top.fraPic.scrollBy(scrollW, 0);
	}
	

	//录入桢控制
	//按上箭头+altKey
	if ((keycode == "104") && (event.altKey == true)) {
	  top.fraInterface.scrollBy(0, -scrollH);
	}	
	//按下箭头+altKey
	if ((keycode == "98") && (event.altKey == true)) {
	  top.fraInterface.scrollBy(0, scrollH);
	}	
	//按左箭头+altKey
	if ((keycode == "100") && (event.altKey == true)) {
	  top.fraInterface.scrollBy(-scrollW, 0);
	}
	//按右箭头+altKey
	if ((keycode == "102") && (event.altKey == true)) {
	  top.fraInterface.scrollBy(scrollW, 0);
	}
	
	//alert("keycode:" + keycode);
	if ((parseFloat(keycode) == 33) || (parseFloat(keycode) == 34) || (parseFloat(keycode) == 27)) 
	  event.returnValue = false;
}

function goToPic(index) { 
  if (index != top.pic_place) {
    top.fraPic.service.src = top.fraPic.arrPicName[index];
  	//top.fraPic.centerPic.innerHTML = top.fraPic.arrPicName[index];
  	//重新定位所选图片所在队列的位置
  	top.pic_place = index; 
  	
  	try { 
  	  if (prtNo.substring(2,4)=="12" && index==2) top.fraPic.filterDIV.filters.item(0).rotation = "3"; 
  	  else top.fraPic.filterDIV.filters.item(0).rotation = "0";
  	} catch(e) {}
  }
}

//-->