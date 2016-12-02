<!--
//ProposalShowPic.js

var w	//图片的宽度
var h	//图片的高度，可不使用
var b_img	//放大图片的次数
var s_img	//缩小图片的次数
b_img = 0
s_img = 0

//得到使用中的图片的实际宽度（单位：象素）
//if (typeof(window.top.fraPic) == "object") 
//window.top.fraPic.window.location = "../app/testPropersal/pic.html";
//w = window.top.fraPic.service.width;
//alert("wControl:"+w);
w = 800;

//图片的队列数组
pic_name = new Array(3);
pic_place = 0;
pic_name[0] = "./p1.JPG";
pic_name[1] = "./p2.JPG";
pic_name[2] = "./p3.JPG";
//app/testPropersal/

function document_onkeydown() {
	//去键值
	var keycode = event.keyCode
	//var key = event
    //var realkey = String.fromCharCode(event.keyCode)
    //alert("keycode: " + keycode + "\nrealkey: " + realkey)

	//判定是否按下PageUp
	if (keycode == "33") {
		//循环图片队列
		for (var i=pic_name.length; i >= 0; i--){
			//找到第一个满足条件的队列项
			if (i < pic_place){
				//改变左页的图片对象
				window.top.fraPic.service.src = pic_name[i];
				//重新定位所选图片所在队列的位置
				pic_place = i;
				//中断循环
				break;
			}
		}
	}
	//判定是否按下PageDown
	if (keycode == "34") {
		for (var i=0; i < pic_name.length; i++){
			if (i > pic_place){
				window.top.fraPic.service.src = pic_name[i];
				pic_place = i;
				break;
			}
		}
	}
	//判定是否按下小键盘上的*
	if (keycode == "106") {
		//还原图片的实际大小，并将放大和缩小次数置为0
		window.top.fraPic.service.width = w
		s_img = 0;
		b_img = 0;
	}
	
	//判定是否按下小建盘的+，同时按住ctrl
	if ((keycode == "107")&&(event.ctrlKey == true)) {
		//判定放大次数，目前设置为6次，可修改
		//alert("ctrl+" + b_img+ "\n" + w + "\n" + (w / (1 + 0.2 * (s_img - 1))) );
		if (b_img <= 5){
			//判定是否缩小过
			if (s_img != 0){
				window.top.fraPic.service.width = w / (1 + 0.2 * (s_img - 1))
				s_img = s_img - 1;
			}
			else{
				b_img = b_img + 1;
				window.top.fraPic.service.width = w * (1 + 0.2 * b_img)
			}
		}
	}
	
	//判定是否按下小建盘的-，同时按住ctrl，不可改变到比原图还小
	if ((keycode == "109")&&(event.ctrlKey == true)) {
		if (b_img != 0){
			window.top.fraPic.service.width = w * (1 + 0.2 * (b_img - 1))
			b_img = b_img - 1;
		}
	}
	
	//判定是否按下小键盘/，可改变到比原图还小，和上面的-处理两选一使用
	if (keycode == "111") {
		//判定缩小次数，目前设置为6次，可修改
		if (s_img <= 5){
			if (b_img != 0){
				window.top.fraPic.service.width = w * (1 + 0.2 * (b_img - 1))
				b_img = b_img - 1;
			}	else {
				s_img = s_img + 1;
				window.top.fraPic.service.width = w / (1 + 0.2 * s_img)
			}
		}
	}
	
	//判定是否按下小键盘`，显示或隐藏图片桢（Minim增加该方法）
	if (keycode == "192") {
	  if (top.fraTalkCol.cols == "0, *") {
	    top.fraTalkCol.cols = "50%, *";
	  } else {
	    top.fraTalkCol.cols = "0, *";
	  }
	}
	
	//alert("keycode:" + keycode);
}

//-->