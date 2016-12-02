/*****************************************************************
 *               Program NAME: 通用普通查询
 *                 programmer: HST
 *                Create DATE: 2002-09-06
 *             Create address: Beijing
 *                Modify DATE:
 *             Modify address:
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
var totalBlock = 0;					  // 查询结果所能分离出的块数
var currBlockIndex = 0;				  // 当前使用块的索引
var currPageIndex = 0;				  // 当前屏幕显示页在当前使用块中的索引
var currPageCount = 0;				  // 当前块的页数
var currRecordCount = 0;			  // 当前使用块包含的记录个数
var currBlockRecord = 0;			  // 当前块中的记录数
var startRecordIndex = 0;			  // 后台取数的开始记录位置
var strEvent = "";					  // 标志EasyQuery调用的事件
var strSQL = "";					  // 查询使用的SQL语句
var strError = "";					  // 错误信息


/*********************************************************************
 *  执行查询主函数，主要针对查询按钮的事件
 *  参数  ：  strSQL：正确的SQL语句
 *  返回值：  如果执行成功，则可使用查询的当前显示部分结果数组arrScreenResult，
 *          如果执行不成功，则返回false
 *********************************************************************
 */
function execEasyQuery( sqlString )
{
	strSQL = sqlString;
	startRecordIndex = 1;
	strEvent = "EASYQUERY";
	if ( easyQuery( strSQL, startRecordIndex ) == false )
		return false;
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
		easyQuery( strSQL, startRecordIndex );
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
		easyQuery( strSQL, startRecordIndex );
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
		easyQuery( strSQL, startRecordIndex );
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
		easyQuery( strSQL, startRecordIndex );
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
 *  传入正确的SQL语句，返回查询结果数组
 *  参数  ：  strSQL：正确的SQL语句
 *  返回值：  如果执行成功，则可使用查询结果数组arrEasyQuery，如果执行不成功，则返回false
 *************************************************************
 */
function easyQuery( sql, start )
{
	if( sql == null || sql == "" ) return false;
	//if( frameName == null || frameName == "" ) frameName = parent.fraInterface.name;

	// 到后台查询数据
	parent.EX.fm.all( 'txtSQL' ).value = sql;
	parent.EX.fm.all( 'mOperate' ).value = "EASYQUERY";
	parent.EX.fm.all( 'startIndex' ).value = start;
	//parent.EX.fm.all( 'txtFrameName' ).value = frameName;
	parent.EX.fm.submit();

}

/*************************************************************
 *  查询结果串拆分到查询结果数组中
 *  参数  ：  无
 *  返回值：  无
 *************************************************************
 */
function decodeResult()
{
	var strResult = "";
	var arrRow = new Array();
	var arrField = new Array();
	var n, m, i, j;
	var recordCount;

	strResult = parent.EX.fm.all('txtQueryResult').value;

	if( strResult == "" || strResult.substring(0, strResult.indexOf("|")) == "100")
	{
		//alert( "没有找到相关的数据!" );
		arrEasyQuery = null;
	}
	else
	{
		arrEasyQuery = new Array();

		// 拆分查询结果，得到块的数据
		arrRow = strResult.split( RECORDDELIMITER );
		recordCount = arrRow[0].substring(arrRow[0].indexOf(FIELDDELIMITER) + 1);	;

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

		// 设置块信息
		totalBlock = Math.ceil( recordCount / ( MAXSCREENLINES * MAXMEMORYPAGES ));
		currBlockRecord = n;
		currPageCount = Math.ceil( currBlockRecord / MAXSCREENLINES );
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

/*************************************************************
 *  查询提交后的各种处理
 *  参数  ：  错误信息
 *  返回值：  无
 *************************************************************
 */
function afterEasyQuery( strError )
{
	var arrResult = new Array();

	if( strError != "" )
	{
		alert( strError );
		return false;
	}

	decodeResult();
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
	//XinYQ modified on 2006-05-09 : 修正如果存在同名控件则无法获取任何值,修改为取首个同名控件的值
	//OLD : value = eval( "fm." + trim( controlName ) + ".value" );
	value = window.document.getElementsByName(trim(controlName))[0].value;
	if( value == "" || value == null ) return strWherePart;
	if( fieldType == null || fieldType == "" ) fieldType = "0";
	if( strOperate == "" || strOperate == null ) strOperate = "=";
	if( fieldType == "0" )	// 0:字符型
	{
		if(strOperate == "like")
		{
			strWherePart = " and " + trim( fieldName ) + " like '" + trim( value ) + "%' ";
		}
		else
		{
			strWherePart = " and " + trim( fieldName ) + trim( strOperate ) + "'" + trim( value ) + "' ";
		}
	}
	if( fieldType == "1" )	// 1:数字型
	{
		strWherePart = " and " + trim( fieldName ) + trim( strOperate ) + trim( value ) + " ";
	}
	return strWherePart;
}


