// 0:viste 1:parent 2:childnum; 3 nodeName 4 nodecode 5 hide 6 showlist 7 check 8 id
/*treeArray的数据结构表示：一个10列的数组
第零列：遍历整个treeArray时用来标记节点是否已访问过
第一列：菜单节点的父节点节点编码
第二列：节点的子节点数
第三列：节点的名称
第四列：节点编码
第五列：标记节点是否在界面上被删除
第六列：节点的子节点列表是否打开
第七列：节点是否被选中
第八列：节点在html中对应的元素的id
第九列：节点是否是叶子节点
*/
//标记节点是否被隐藏
function tagInArray(tArray,id)
{
	for(var i = 0; i < tArray.length; i++) {
		var aryId = "chk_" + tArray[i][8];
		if (aryId != id)
		    continue;
	//	alert("tArray[i][7] = " + tArray[i][7]);
		tArray[i][7] = (tArray[i][7] == 0) ? 1 : 0;

		//遍历此节点的所有子节点，包括子节点的子节点，根据
		//tArray[i][7]的值将其标记为是否选中
		travels_chk(tArray,i,tArray[i][7]);
	}
}

//遍历节点的非递归算法，tArray为遍历的数组，start为遍历的开始位置
//chkVal表示将chkbox置为的值
function travels_chk(tArray,start,chkVal)
{
    var stackArray = new Array(); // 模拟栈结构
    var top = 0; // 指向栈顶的指针
    stackArray[top] = start;

    while (top != -1) {
    	var index = stackArray[top]; //栈顶元素出栈
    	top--;
    	if (tArray[index][2] == 0) //叶子节点，没有子节点
    	    continue;

    	var parentCode = tArray[index][4];
    	for (var i = index + 1; i < tArray.length; i++) {
    	    if (tArray[i][5] == 1) //此节点不在界面上
    		continue;
	    if (tArray[i][1] == parentCode) {
	    	tArray[i][7] = chkVal;
	    	var chkElmID = "chk_" + tArray[i][8];
	    	fm.all(chkElmID).checked = chkVal;
	    	top++;              //将这个节点压入栈
	  //  	alert("top:" + top);
	    	stackArray[top] = i;
	    }
	}
    }
}


//标记父节点列表是否打开
function tagShowlistInArray(tArray,id)
{
	for(var i = 0; i < tArray.length; i++) {
		var aryId = "fld_" + selectArray[i][8];
		if (aryId != id)
		    continue;

		tArray[i][6] = (tArray[i][6] == 0) ? 1: 0;
	}
}


//将节点nodecode从树形结构中去除，这个function的目的主要是在子节点数
//为0时，将其父节点也删除（隐藏）tArray是存储树形的数据结构，offset
//是节点在tArray中的偏移量
function removeNode(tArray,offset)
{
	//不是叶子节点
	if (tArray[offset][9] == 0)
	    return;

	//此节点已被删除
	if (tArray[offset][5] == 1)
	    return;

	//删除此节点
	tArray[offset][5] = 1;

	//向上回朔，将父节点的子节点数减1，如果为0，删除父节点
	var parent = tArray[offset][1];
	//alert("parent:" + parent);

	while (parent != 0) {
		var index = offset - 1;
		for (; index > 0; index--) {
			if (tArray[index][4] != parent)
			    continue;
			break;
		}

		tArray[index][2]--;

		//删除父节点
		if (tArray[index][2] == 0){
		    tArray[index][5] = 1;
		    parent = tArray[index][1];
		} else { //结束向上回溯
			parent = 0;
		}
	}
}

function clearTree(spanCtrl)
{
	var resultHtml = "";
	document.all(spanCtrl).innerHTML=resultHtml;
}

//在界面上画出tArray的树形视图
// 0:viste 1:parent 2:childnum; 3 nodeName 4 nodecode 
// 5 hide 6 showlist 7 check 8 id
function paintTree(tArray,spanCtrl)
{
        for (var i = 0; i < tArray.length; i++) {
            tArray[i][0] = 0;//全部标记为未访问
        }

    var resultHtml = "<ul>\n";
    resultHtml +=createTreeHtml(tArray,0,0);//P270
    resultHtml += "\n</ul>";
    //alert(resultHtml);
    //document.fm.all('tt').value = resultHtml;
    //alert(document.all(spanCtrl).innerHTML);
    document.all(spanCtrl).innerHTML=resultHtml;

    //如果有打开的列表，现在打开
//	for (var i = 0; i < tArray.length; i++) {
//		if (tArray[i][2] == 0)
//			continue;
//		if (tArray[i][5] == 1)
//			continue;
//		var elmName = "fld_" + tArray[i][8]
//		var sourceIndex = fm.all(elmName).sourceIndex + 1;
//		//var sourceIndex = fm.all(tArray[i][8]).sourceIndex + 1;
//		//alert(elmName);
//		//alert(tArray[i][6]);
//		if (tArray[i][6] == 0) {
//			document.all[sourceIndex].style.display = "none";
//			document.all[sourceIndex+1].style.display = "none";
//		}
//		else{
//			document.all[sourceIndex].style.display = '';
//			document.all[sourceIndex+1].style.display = '';
//		}
//	}
//    alert("over paint");
}

//在界面上画出tArray的树形视图
function paintTree_ReadOnly(tArray,spanCtrl)
{
	//alert("treemenu.js:paintTree_ReadOnly");//2005
	for (var i = 0; i < tArray.length; i++)
	{
		tArray[i][0] = 0;
	}
	var resultHtml = "<ul>\n";
	resultHtml +=createTreeHtml_ReadOnly(tArray,0,0);
	resultHtml += "\n</ul>";
	document.all(spanCtrl).innerHTML=resultHtml;
	//如果有打开的列表，现在打开
	for (var i = 0; i < tArray.length; i++)
	{
		if (tArray[i][2] == 0)
			continue;
		if (tArray[i][5] == 1)
			continue;
		var sourceIndex = fm.all(tArray[i][8]).sourceIndex + 1;
		if (tArray[i][6] == 0)
		{
			document.all[sourceIndex].style.display = "none";
		}
		else
		{
			document.all[sourceIndex].style.display = '';
		}
	}
}

//将节点nodecode加入树形结构中，这个function的目的主要是将父节点
//也加上，tArray是存储树形的数据结构，offset是节点在tArray中的偏移量
function addNode(tArray,offset)
{
	//alert("treemenu.js:addNodee");//2005
	var kkk = 0;
	if (offset >= tArray.length)
	    return;
	//不是叶子节点
	if (tArray[offset][9] == 0)
	    return;
	//此节点已存在
	if (tArray[offset][5] == 0)
	    return;
	//增加此节点
	tArray[offset][5] = 0;
	tArray[offset][7] = 0;
	//向上回朔，将父节点的子节点数加1，如果非为0，增加父节点
	var parent = tArray[offset][1];

	while (parent != 0) {
		var index = offset - 1;
		for (; index > 0; index--) {
			if (tArray[index][4] != parent)
			    continue;
			break;
		}
		tArray[index][2]++;
                //增加父节点
		if (tArray[index][2] == 1){
		    tArray[index][5] = 0;
		    tArray[index][7] = 0; //此代码可以不写
		    parent = tArray[index][1];
		} else { //结束向上回溯
			parent = 0;
		}
	}
}

// 0:viste 1:parent 2:childnum; 3 nodeName 4 nodecode 5 hide
function createTreeHtml_ReadOnly(tArray,pos,parent)
{
	//alert("treemenu.js:createTreeHtml_ReadOnly");//2005
	var resultHtml = "";
	if (pos >= tArray.length)
	    return resultHtml;

	for (var i = pos; i < tArray.length ; i++) {
		// 节点是隐藏点
		if(tArray[i][5] == 1)
		    continue;

		//节点已访问过
		if (tArray[i][0]==1)
		    continue;

		if (tArray[i][1] != parent)
		    continue;

		//标记节点已访问过
		tArray[i][0] = 1;

		if (tArray[i][9] == 1) { //叶子节点
		    var nodeHtml = '<li>' + tArray[i][3] + "</a></li>\n";
		    resultHtml += nodeHtml;
		} else { //非叶子节点
			resultHtml = resultHtml + '<li name="foldheader" class=common id =' + tArray[i][8] + '>' + tArray[i][3]  + "</li>\n";
			resultHtml = resultHtml + '<ul name=foldinglist style=display:none style=&{head};>\n';
			resultHtml += createTreeHtml_ReadOnly(tArray,i+1, tArray[i][4]);
			resultHtml += "</ul>\n";
		}
	}

	return resultHtml;
}


// 0:viste 1:parent 2:childnum; 3 nodeName 4 nodecode 5 hide
// 6 showlist 7 check 8 id
function createTreeHtml(tArray,pos,parent)
{
	//alert("treemenu.js:createTreeHtml");//2005
		var resultHtml = "";
		if (pos >= tArray.length)
		    return resultHtml;

		for (var i = pos; i < tArray.length ; i++) {
			// 节点是隐藏点			
			if(tArray[i][5] == 1)
			    continue;
			//节点已访问过
			if (tArray[i][0]==1)
			    continue;
			if (tArray[i][1] != parent)
			    continue;

			//标记节点已访问过
			tArray[i][0] = 1;

			if (tArray[i][9] == 1) { //叶子节点
			    var nodeHtml = '<li><INPUT TYPE="checkbox" id="chk_' + tArray[i][8]+ '">' + tArray[i][3] + "</a></li>\n";
			    resultHtml += nodeHtml;

			} else { //非叶子节点
			//	resultHtml = resultHtml + '<li name="foldheader"  class=common id =' + tArray[i][8] + '>' + tArray[i][3]  + "</li>\n";
			        resultHtml = resultHtml + '<li name="foldheader"  class=common id =fld_' + tArray[i][8] + '>'
			        	+ '<INPUT  TYPE="checkbox" id = chk_' + tArray[i][8] +  ' style=' + '"display:' + "'" + "'" + ' ">' + tArray[i][3]  + "</li>\n";
				resultHtml = resultHtml + '<ul name=foldinglist style=display:none style=&{head};>\n';

				resultHtml += createTreeHtml(tArray,i+1, tArray[i][4]);
				resultHtml += "</ul>\n";
			}
		}
// alert(resultHtml);//2005
	return resultHtml;
}
