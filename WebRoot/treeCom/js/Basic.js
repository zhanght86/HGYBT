// JavaScript Document
function findInDoc(name,doc){
  var i,obj;
  if(!doc){ doc=document;}
  obj=doc[name];
  if(!obj&&doc.getElementById){
  	obj=doc.getElementById(name);				//DOM 1
  }
  if(!obj&&doc.all){
  	obj=doc.all[name];								//IE only
  }
  for(i=0;!obj&&doc.forms&&i<doc.forms.length;i++){
  	obj=doc.forms[i][name];						//In form
  }
  for(i=0;!obj&&doc.layers&&i<doc.layers.length;i++){
    obj=findObj(name,doc.layers[i].document);	//In layers(NS only)
  }
  return obj;
}
function findInFrame(up){						//Up level
	var i,p=window;
	for(i=0;i<up;i++){
		p=p.parent;
	}
	for(i=1;i<findInFrame.arguments.length;i++){
		p=p.frames[findInFrame.arguments[i]];	//Go down here
	}
	return p.document;
}
