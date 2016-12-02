
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
function OverItem(src){
	src.className=OverMenu;
}
function OutItem(src){
	src.className=OutMenu;
}
