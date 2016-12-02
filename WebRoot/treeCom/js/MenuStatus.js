
function turnLayer(name,stats,doc) {
  var obj,v;
  obj=findInDoc(name,doc);
  if (obj!=null){
		if (obj.style) { 
		 	obj=obj.style;
			stats=(stats=='show')?'visible':(stats=='hide')?'hidden':stats;
		}
    	obj.visibility=stats;
  }
}
var sbmt=false;
function OverItem(src,value){
	if(!sbmt){
		var old=findInDoc(path).value;
		if(old=="") findInDoc(path).value=value;
		else findInDoc(path).value=value+"-"+findInDoc(path).value;
	}
	src.className=OverMenu;
}
function OutItem(src){
	if(!sbmt)	findInDoc(path).value="";
	src.className=OutMenu;
}
