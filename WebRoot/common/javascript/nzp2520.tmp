/*****************************************************************
 *               Program NAME: 通用普通查询                       
 *                 programmer: HST                    
 *                Create DATE: 2002-09-06                    
 *             Create address: Beijing                       
 *                Modify DATE: 2002-10-09                              
 *             Modify address: hubo                              
 *****************************************************************
 *                                                
 *         通用普通查询处理SQL语句的查询,然后将查询结果显示到相应的界面
 *     控件上，多行结果支持上一页、下一页等翻页的功能
 *
 *	   说明：代码中使用部分了Common.js中定义的全局设置
 * 
 *****************************************************************
 */
var arrEasyQuery = new Array();		  // 存放使用easyQuery()得到的查询结果数组
var arrScreenResult = new Array();	  // 存放当前页所需要的查询结果数组
var totalBlock = 0;			  // 查询结果所能分离出的块数
var currBlockIndex = 0;			  // 当前使用块的索引
var currPageIndex = 0;			  // 当前屏幕显示页在当前使用块中的索引
var currPageCount = 0;			  // 当前块的页数
var currRecordCount = 0;		  // 当前使用块包含的记录个数
var currBlockRecord = 0;		  // 当前块中的记录数
var startRecordIndex = 0;		  // 后台取数的开始记录位置
var strEvent = "";			  // 标志EasyQuery调用的事件
var strError = "";			  // 错误信息

/*************************************************************
 *  查询提交后的各种处理
 *  参数  ：  错误信息
 *  返回值：  无
 *************************************************************
 */
function afterEasyQuery( strResult )
{
	var arrResult = new Array();

	//alert("strResult:" +strResult);
	decodeResult(strResult);
	//alert("decode:" +arrEasyQuery);
	getPageArray();

	// 显示查询结果
	displayEasyResult( arrScreenResult );
}

/*************************************************************
 *  查询拼串工具
 *  参数  ：  fieldName -- 字段名称
 *			 controlName -- 控件名称
 *			 strOperate -- 操作符
 *			 type -- 字段类型( 0:字符型　1:数字型 )
 *  返回值：  拼好的串
 *************************************************************
 */
function getWherePart( fieldName, controlName, strOperate, fieldType )
{
	var strWherePart = "";
	var value = "";
	if( controlName == "" || controlName == null ) controlName = fieldName;
	value = eval( "fm." + trim( controlName ) + ".value" );
	if( value == "" || value == null ) return strWherePart;
	if( fieldType == null || fieldType == "" ) fieldType = "0";
	if( strOperate == "" || strOperate == null ) strOperate = "=";
	if( fieldType == "0" )	// 0:字符型
	{
		if(strOperate == "like")
		{
			strWherePart = " and " + trim( fieldName ) + " like '" + trim( value ) + "%25'";
		}
		else
		{
			strWherePart = " and " + trim( fieldName ) + trim( strOperate ) + "'" + trim( value ) + " '";
		}
	}	
	if( fieldType == "1" )	// 1:数字型
	{
		strWherePart = " and " + trim( fieldName ) + trim( strOperate ) + trim( value ) + " ";
	}
	return strWherePart;
}	

/*********************************************************************
 *  查找下一页主函数，主要针对查询显示下一页按钮的事件
 *  参数  ：  无
 *  返回值：  如果执行成功，则可使用查询的当前显示部分结果数组arrScreenResult，
 *          如果执行不成功，则返回false
 *********************************************************************
 */
function getNextPage()
{
	//alert("currBlockIndex:"+currBlockIndex +":currPageIndex:" + currPageIndex);
	// 校验是否查询
	if( totalBlock == 0 )
	{
		strError = "请先查询!";
		alert( strError );
		return false;
	}
	// 校验是否到达尾页
	if( currBlockIndex == totalBlock && currPageIndex == currPageCount )
	{
		strError = "已经到达尾页!";
		alert( strError );
		return false;
	}
	
	if( currPageIndex == currPageCount )		// 已经到当前使用块的末尾，则需要到后台查询下一块数据
	{
		startRecordIndex = currBlockIndex * MAXSCREENLINES * MAXMEMORYPAGES + 1;
		strEvent = "NEXTPAGE";
		mustQueryFlag = true;
		strStart = startRecordIndex;
		QueryCache()
		mustQueryFlag = false;
	}
	else
	{
		currPageIndex = currPageIndex + 1;
		if( currBlockIndex == totalBlock && currPageIndex == currPageCount )
		{
			currRecordCount = currBlockRecord - ( currPageIndex - 1 ) * MAXSCREENLINES;
		}
		getPageData( currPageIndex );

		// 显示查询结果
		displayEasyResult( arrScreenResult );
	}
}

/*********************************************************************
 *  查找上一页主函数，主要针对查询显示上一页按钮的事件
 *  参数  ：  无
 *  返回值：  如果执行成功，则可使用查询的当前显示部分结果数组arrScreenResult，
 *          如果执行不成功，则返回false
 *********************************************************************
 */
function getPreviousPage()
{
	//alert("totalBlock:"+totalBlock+";currBlockIndex:"+currBlockIndex+";currPageIndex:"+currPageIndex);
	//alert("currPageCount:"+currPageCount+";currRecordCount:"+currRecordCount+";currBlockRecord:"+currBlockRecord);
	// 校验是否查询
	if( totalBlock == 0 )
	{
		strError = "请先查询!";
		alert( strError );
		return false;
	}
	// 校验是否到达首页
	if( currBlockIndex == 1 && currPageIndex == 1 )
	{
		strError = "已经到达首页!";
		alert( strError );
		return false;
	}
	
	if( currPageIndex == 1 )		// 已经到当前使用块的首页，则需要到后台查询上一块数据
	{
		startRecordIndex = ( currBlockIndex - 2 ) * MAXSCREENLINES * MAXMEMORYPAGES + 1;
		strEvent = "PREVIOUSPAGE";
		mustQueryFlag = true;
		strStart = startRecordIndex.toString();
		QueryCache()
		mustQueryFlag = false;

	}
	else
	{
		currPageIndex = currPageIndex - 1;
		currRecordCount = MAXSCREENLINES;
		getPageData( currPageIndex );

		// 显示查询结果
		displayEasyResult( arrScreenResult );
	}
}

/*********************************************************************
 *  查找首页主函数，主要针对查询显示首页按钮的事件
 *  参数  ：  无
 *  返回值：  如果执行成功，则可使用查询的当前显示部分结果数组arrScreenResult，
 *          如果执行不成功，则返回false
 *********************************************************************
 */
function getFirstPage()
{
	//alert("totalBlock:"+totalBlock+";currBlockIndex:"+currBlockIndex+";currPageIndex:"+currPageIndex);
	// 校验是否查询
	if( totalBlock == 0 )
	{
		strError = "请先查询!";
		alert( strError );
		return false;
	}
	// 校验是否到达首页
	if( currBlockIndex == 1 && currPageIndex == 1 )
	{
		strError = "已经到达首页!";
		alert( strError );
		return false;
	}
	
	if( currBlockIndex != 1 )		// 当前使用块不是第一块，则需要到后台查询第一块数据
	{
		startRecordIndex = 1;
		strEvent = "FIRSTPAGE";
		mustQueryFlag = true;
		strStart = startRecordIndex.toString();
		QueryCache()
		mustQueryFlag = false;
	}
	else
	{
		currPageIndex = 1;
		if( currPageCount == 1 )
		{
			currRecordCount = currBlockRecord;
		}
		else
		{
			currRecordCount = MAXSCREENLINES;
		}
		getPageData( currPageIndex );

		// 显示查询结果
		displayEasyResult( arrScreenResult );
	}
}

/*********************************************************************
 *  查找尾页主函数，主要针对查询显示尾页按钮的事件
 *  参数  ：  无
 *  返回值：  如果执行成功，则可使用查询的当前显示部分结果数组arrScreenResult，
 *          如果执行不成功，则返回false
 *********************************************************************
 */
function getLastPage()
{
	//alert("totalBlock:"+totalBlock+";currBlockIndex:"+currBlockIndex+";currPageIndex:"+currPageIndex);
	// 校验是否查询
	if( totalBlock == 0 )
	{
		strError = "请先查询!";
		alert( strError );
		return false;
	}
	// 校验是否到达尾页
	if( currBlockIndex == totalBlock && currPageIndex == currPageCount )
	{
		strError = "已经到达尾页!";
		alert( strError );
		return false;
	}
	
	if( currBlockIndex != totalBlock )		// 当前使用块不是最后一块，则需要到后台查询最后一块数据
	{
		startRecordIndex = ( totalBlock - 1 ) * MAXSCREENLINES * MAXMEMORYPAGES + 1;
		strEvent = "LASTPAGE";
		mustQueryFlag = true;
		strStart = startRecordIndex.toString();
		QueryCache()
		mustQueryFlag = false;

	}
	else
	{
		currPageIndex = currPageCount;
		currRecordCount = currBlockRecord - ( currPageIndex - 1 ) * MAXSCREENLINES;
		getPageData( currPageIndex );

		// 显示查询结果
		displayEasyResult( arrScreenResult );
	}
}

/*************************************************************
 *  取得内部存储块中的指定页的查询结果
 *  参数  ：  pageIndex -- 页在块中的索引
 *  返回值：  如果执行成功，则可使用查询结果数组arrPageResult，如果执行不成功，则返回false
 *************************************************************
 */
function getPageData( pageIndex )
{
	var startIndex;
	var endIndex;
	var arrRecord;
	var i, j, n, k;
    arrScreenResult = new Array();	  // 存放当前页所需要的查询结果数组
	
	if( pageIndex < 1 || pageIndex > MAXMEMORYPAGES )
	{
		strError = "查询页索引pageIndex指定错误!";
		return false;
	}
	else
	{
		startIndex = ( pageIndex - 1 ) * MAXSCREENLINES;
		endIndex = ( pageIndex * MAXSCREENLINES ) - 1;
		if( endIndex > startIndex + currRecordCount - 1 )
			endIndex = startIndex + currRecordCount - 1;
		//alert("startIndex:"+startIndex+"|endIndex:"+endIndex+"pageIndex:"+pageIndex+"currRecordCount"+currRecordCount);
		k = 0
		for( i = startIndex; i <= endIndex; i++ )
		{
			arrScreenResult[k] = new Array();
			n = arrEasyQuery[i].length;
			for( j = 0; j < n; j++ )
			{
				arrScreenResult[k][j] = arrEasyQuery[i][j];
			} // end of for
			k++;
		} // end of for
	} // end of if
}


/*************************************************************
 *  查询结果串拆分到查询结果数组中
 *  参数  ：  无
 *  返回值：  无
 *************************************************************
 */
function decodeResult(strResult1)
{
	var strResult = "";
	var arrRow = new Array();
	var arrField = new Array();
	var n, m, i, j;
	var recordCount;

	strResult = strResult1;
	//arrEasyQuery = null;
	if( strResult == "" )
	{
		//alert( "没有找到相关的数据!" );
		arrEasyQuery = null;
	}
	else
	{
		// 拆分查询结果，得到块的数据
		arrRow = strResult.split( RECORDDELIMITER );
		recordCount = arrRow[0];
		//recordCount = arrRow.length - 1;
		
		n = arrRow.length - 1;
		
		for( i = 1; i <= n; i++ )
		{
			arrField = arrRow[i].split( FIELDDELIMITER );
			m = arrField.length;
			arrEasyQuery[i-1] = new Array();
			for( j = 0; j < m; j++ )
			{
				arrEasyQuery[i-1][j] = arrField[j];
			}
		}
		//alert("arrEasyQuery"+arrEasyQuery);
		// 设置块信息
		totalBlock = Math.ceil( recordCount / ( MAXSCREENLINES * MAXMEMORYPAGES ));
		if (n < MAXSCREENLINES * MAXMEMORYPAGES) {
		currBlockRecord = n; 
		} else {
		currBlockRecord = MAXSCREENLINES * MAXMEMORYPAGES; 
		}
		//alert("currBlockRecord:"+currBlockRecord)
		currPageCount = Math.ceil( currBlockRecord / MAXSCREENLINES );
		//alert(recordCount+":"+totalBlock+":"+currBlockRecord+":"+currPageCount)
	}
}

/*************************************************************
 *  根据不同的按钮事件得到不同的页面需要的数组
 *  参数  ：  无
 *  返回值：  arrScreenResult
 *************************************************************
 */
function getPageArray()
{
	// 查询按钮或首页按钮
	if( strEvent == "EASYQUERY" || strEvent == "FIRSTPAGE" )
	{
		currBlockIndex = 1;
		currPageIndex = 1;
		if( currPageCount == 1 ) 
			currRecordCount = currBlockRecord;
		else
			currRecordCount = MAXSCREENLINES;
	}

	// 下一页按钮
	if( strEvent == "NEXTPAGE" )
	{
		currBlockIndex = currBlockIndex + 1;
		currPageIndex = 1;
		if( currPageCount == 1 ) 
			currRecordCount = currBlockRecord;
		else
			currRecordCount = MAXSCREENLINES;
	}

	// 上一页按钮
	if( strEvent == "PREVIOUSPAGE" )
	{
		currBlockIndex = currBlockIndex - 1;
		currPageIndex = currPageCount;
		currRecordCount = currBlockRecord - ( currPageIndex - 1 ) * MAXSCREENLINES;
	}

	// 尾页按钮
	if( strEvent == "LASTPAGE" )
	{
		currBlockIndex = totalBlock;
		currPageIndex = currPageCount;
		currRecordCount = currBlockRecord - ( currPageIndex - 1 ) * MAXSCREENLINES;
	}

	// 得到显示的数组
	getPageData( currPageIndex );
}


/******************************************************************
*******************************************************************
******************************************************************/
var showInfo;
var mDebug="0";
var arrStrReturn = new Array();
var arrGrid;

var mustQueryFlag = false;
var strStart = "1";
var strSql = "";

var multilineArray = new Array(); // 储存MULTILINE数据的数组，显示查询结果时要用到
var multilineGrid;                 // 存储MULTILINE对象，显示查询结果时要用到


// 当QueryCache.js在其他页面时，中转查询结果
function transferQueryResult( strResult ) {
  queryCacheWin.setQueryResult( strResult );	
}

function QueryCache() {       
  //强制查询，先清空该条SQL语句在数组中的缓存
  if (mustQueryFlag) {
    queryCacheWin.mustQuery( strSql );	
  }
           
  //alert(queryCacheWin.SqlCache( strSql ));
  if (!queryCacheWin.SqlCache( strSql )) {              
    NewEasyQuery( strSql, strStart );   //如果SQL语句无重复	  
  } else {
    afterEasyQuery( queryCacheWin.SqlCache( strSql ) );	
  }
}

// 提交查询SQL语句，进行后台查询
function NewEasyQuery(strSql, strStart) {
  showWaitDialog();
  
  var urlStr = queryWindowURL + "?sql=" + strSql + "&strStart=" + strStart; 
  //window.open(urlStr,null,"height=220,width=600,top="+(screen.availHeight-220)/2+",left="+(screen.availWidth-600)/2+",status=no,toolbar=no,menubar=no,location=no");      	
  window.open(urlStr,null,"height=220,width=600,top=1000,left=1000,status=no,toolbar=no,menubar=no,location=no");    
}

// 显示查询结果
function displayEasyResult( arrResult )
{
	var i, j, m, n;
		
	if( arrResult == null ) {
		alert( "没有找到相关的数据!" );		
	}	
	else
	{
		arrGrid = arrResult;
		
		// 显示查询结果
		n = arrResult.length;
		
		// 初始化MULTILINE表格
	        try {
	          multilineGrid.mulLineCount = n;   //控制显示行数
	          // 使记录数与序号统一
	          multilineGrid.recordNo = (currBlockIndex-1) * MAXSCREENLINES * MAXMEMORYPAGES + (currPageIndex-1)*MAXSCREENLINES;
	          multilineGrid.loadMulLine(multilineArray);  
		} catch(ex) {
		  alert("IninGrid Error!");
		}

		for( i = 0; i < n; i++ )
		{
			m = arrResult[i].length;
			for( j = 0; j < m; j++ )
			{
				multilineGrid.setRowColData( i, j+1, arrResult[i][j] );
			} 
		} 
		//alert("result:"+arrResult);
	} 
	
	closeDialog();
}

// 查询失败后，清空Multiline的查询结果显示
function queryFaile() {
  try {
    totalBlock = 0;
    
    multilineGrid.mulLineCount = 0;   
	  multilineGrid.loadMulLine(multilineArray); 
  } catch(ex) {
    alert("IninGrid Error!");
  }	
  
  closeDialog();
  alert("没有找到相关的数据!");
}

function showWaitDialog() {
  var showStr="正在用NewEasyQuery查询数据，请您不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   	
}

function closeDialog() {
	if (showInfo != null) {
		showInfo.close();
	};
}