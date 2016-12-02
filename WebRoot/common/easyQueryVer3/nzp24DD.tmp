/*****************************************************************
 *      Program NAME: 客户端打印窗口处理                    
 *        programmer: guoxiang                    
 *       Create DATE: 2004-04-19                                         
 * Modify programmer: guoxiang                                  
 *       Modify DATE: 2004-7-6 20:47 add function   easyQueryPrintEx                        
 *                    2004-7-21 14:59 add functon   easyQueryPrintShowCodeName 
 *                    2004-7-22 11:10 以前的win 变量与CCodeOperate.js中冲突                 
 *****************************************************************                                               
 *****************************************************************
 */

//sFeatures：打印窗口样式  
var sFeatures = "status:0;help:0;close:0;scroll:0;dialogWidth:1200px;dialogHeight:800px;resizable=0";
	              + "dialogLeft:0;dialogTop:0;";
//父窗口的句柄 以前的win 变量与CCodeOperate.js中冲突
var win_father=window;


//普通
function easyQueryPrint(vFlag,vMultiline,vTurnPage) {
    //urlStr：打印窗口URL和查询参数
    var urlStr = "../f1print/EasyF1Print.jsp?Flag="+vFlag+"&GridName="+vMultiline+"&turnPageName="+vTurnPage;
    window.showModalDialog(urlStr, win_father, sFeatures);  
	
	
	//下面的方法会在地址栏中显示数据，在右键中显示源代码 所以不用
	//fm.action = "../f1print/EasyF1Print.jsp?Flag="+vFlag+"&GridName="+vMultiline+"&turnPageName="+vTurnPage;
	//fm.target = "_blank";
	//fm.submit();
}

//支持合计扩展
function easyQueryPrintEx(vFlag,vMultiline,vTurnPage,vArray) {
    //urlStr：打印窗口URL和查询参数
    var urlStr = "../f1print/EasyF1Print.jsp?Flag="+vFlag+"&GridName="+vMultiline+"&turnPageName="+vTurnPage+"&PrintArray="+vArray;
    window.showModalDialog(urlStr, win_father, sFeatures);  
	
	
	//下面的方法会在地址栏中显示数据，在右键中显示源代码 所以不用
	//fm.action = "../f1print/EasyF1Print.jsp?Flag="+vFlag+"&GridName="+vMultiline+"&turnPageName="+vTurnPage+"&PrintArray="+vArray;
	//fm.target = "_blank";
	//fm.submit();
}

//支持打印showcodename in multiline
function easyQueryPrintShowCodeName(vFlag,vMultiline,vTurnPage,vShowCodename){
    //urlStr：打印窗口URL和查询参数
    var urlStr = "../f1print/EasyF1Print.jsp?Flag="+vFlag+"&GridName="+vMultiline+"&turnPageName="+vTurnPage+"&ShowCodename="+vShowCodename;
    window.showModalDialog(urlStr, win_father, sFeatures);  
}