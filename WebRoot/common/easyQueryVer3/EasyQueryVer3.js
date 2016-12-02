/*****************************************************************
 *  Program NAME: 通用查询第三版EasyQueryVer3.js
 *  programmer: 胡博
 *  Create DATE: 2002-10-19
 *  Modify programmer:
 *  Modify DATE:
 *****************************************************************
 *  通用查询处理SQL语句的查询,然后将查询结果以二维数组的形式返回
 *  说明：代码中使用部分了Common.js中定义的全局设置
 *****************************************************************
 */
var sqlCacheWindow; //指定用于缓存的窗口
var mLargeFlag; //大数据量标志
var mLimitFlag; //突破默认200条限制, XinYQ added on 2006-09-30

/**
 * 公用查询接口函数
 * @param strSql - SQL语句字符串
 * @param synchronization - 同异步设置，“1”同步，“0”异步，默认同步
 * @param useCache - 使用缓存设置，“1”使用，“0”不使用，默认不使用
 * @param strStart - 指定查找索引，默认从第一条记录开始查询
 * @param strLargeFlag - 大数据量查询，大于10000条记录使用，“1”使用，“0”不使用，默认不使用
 * @param strLimitFlag - 突破默认200条限制(小于1000条)，“1”使用，“0”不使用，默认不使用，当strLargeFlag有效时此参数无效
 * @return 查询结果字符串，格式为：首记录“0|记录总数”，以“^”分隔记录，以“|”分隔字段。失败返回FALSE
 */
function easyQueryVer3(strSql, synchronization, useCache, strStart, strLargeFlag, strLimitFlag)
{
    //strSql 接口参数处理
    if (typeof(strSql) == "undefined" || strSql == "")
    {
        alert("easyQueryVer3 需要输入一个SQL语句！");
        return false;
    }
    else
    {
        //解决JSP页面的缓存问题，使SQL语句每时都是不同的
        var strDate = Date.parse(new Date());
        var tStrSql = strSql.toLowerCase();
        if (tStrSql.indexOf("where") != -1)
        {
            //不明白这句话的含义？直接替换不就ok了？
            tStrSql = strSql.substring(tStrSql.indexOf("where"), tStrSql.indexOf("where")+5);
            strSql = strSql.replace("where", "where '" + strDate + "'='" + strDate + "' and ");
        }
        //解决=、%、?号在JS和JSP之间的传递问题
        //
        // Kevin, 2006-08-04
        // Conversion is not needed while using XMLHttp
        //
        // while(strSql.indexOf("=") != -1)
        //{
        //  strSql = strSql.replace("=", "%3D");
        //}
        while(strSql.indexOf("%25") != -1) {
            strSql = strSql.replace("%25", "%");
        }

        while(strSql.indexOf("%%") != -1) {
            strSql = strSql.replace("%%", "%");
        }
        //while(strSql.indexOf("?") != -1)
        //{
        //  strSql = strSql.replace("?", "%3F");
        //}
        //while(strSql.indexOf("+") != -1)
        //{
        //  strSql = strSql.replace("+", "%2B");
        //}
    }
    //synchronization 接口参数处理
    if (typeof(synchronization) == "undefined")
    {
        synchronization = true;
    }
    else if (synchronization == "0")
        {
            synchronization = false;
        }
        else
        {
            synchronization = true;
        }
    //useCache 接口参数处理
    if (useCache == "1")
    {
        if (sqlCacheWindow == null)
        {
            //遍历TOP窗口和各桢窗口，通过全局变量sqlCacheIndex找到缓存设置页面
            var i=0;
            var tLength = top.frames.length;
            while (i < tLength)
            {
                if (top.frames(i).sqlCacheIndex != null)
                {
                    sqlCacheWindow = top.frames(i);
                }
                i++;
            }
        }
        //判断是否设置在根桢中
        if (sqlCacheWindow == null && top.sqlCacheIndex != null)
        {
            sqlCacheWindow = top;
        }
        if (sqlCacheWindow == null)
        {
            sqlCacheWindow = false;
        }
        useCache = sqlCacheWindow;
    }
    else
    {
        useCache = false;
    }
    //strStart 接口参数处理
    if (typeof(strStart) == "undefined" || (typeof(useCache) == "string" && strStart == ""))
    {
        strStart = "1";
    }
    //strLargeFlag 接口参数处理
    if (typeof(strLargeFlag) == "undefined" || (typeof(strLargeFlag) == "string" && strLargeFlag == ""))
    {
        strLargeFlag = "0";
        mLargeFlag = strLargeFlag;
    }
    else
    {
        mLargeFlag = strLargeFlag;
    }
    //strLimitFlag 接口参数处理
    if (typeof(strLimitFlag) == "undefined" || (typeof(strLimitFlag) == "string" && strLimitFlag == ""))
    {
        strLimitFlag = "0";
        mLimitFlag = strLimitFlag;
    }
    else
    {
        mLimitFlag = strLimitFlag;
    }
    //urlStr：查询窗口URL和查询参数
    var urlStr = "../common/easyQueryVer3/EasyQueryVer3Window.jsp?strSql=" + strSql + "&strStart=" + strStart + "&LargeFlag=" + strLargeFlag + "&LimitFlag=" + strLimitFlag;
    //sFeatures：查询窗口样式
    var sFeatures = "status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;resizable=1";
    var strQueryResult = "";    //查询结果字符串
    var arrQueryResult = new Array();   //查询结果数组
    var sqlCacheFlag = false;
    //检查缓存，缓存存在，返回数据，不存在返回FALSE，则SQL语句无重复，需要进行查询
    if (typeof(useCache) == "object")
    {
        sqlCacheFlag = useCache.SqlCache(strSql);
    }
    if (!sqlCacheFlag && synchronization)
    {
        //同步查询
        //add by wangyc 改为XMLHTTP方式
        var strURL = "../common/easyQueryVer3/EasyQueryXML.jsp";
        //
        // Kevin, 2006-08-04
        // Make sure you have the correct order of params
        // 1. strSql; 2. strStart; 3. strLargeFlag; 4. strLimitFlag
        //
        var strPara = strSql + "&" + strStart + "&" + strLargeFlag + "&" + strLimitFlag;
        Request = new ActiveXObject("Microsoft.XMLHTTP");
        Request.open("POST",strURL, false);
        Request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
        Request.send(strPara);
//      Request.open("GET", strURL, false);
//      Request.send(null);
        try
        {
            strQueryResult = Request.responseText;
            if (strQueryResult != null && typeof(strQueryResult) == "string")
            {
                strQueryResult = strQueryResult.trim();
            }
        }
        catch (ex)
        {
            //alert("数据返回出错：" + ex.message);
        }
    }
    else if (!sqlCacheFlag && !synchronization)
        {
            //alert("异步查询！");
            //异步查询
            window.open(urlStr, "", sFeatures);
            return false;
        }
    if (!sqlCacheFlag && typeof(useCache) == "object")
    {
        useCache.setQueryResult(strQueryResult);    //缓存查询结果
    }
    if (sqlCacheFlag)
    {
        strQueryResult = sqlCacheFlag;  //如果缓存中有数据，不进行查询，直接获取数据
    }
    //特殊字符转换，该函数由LH制作
    if (strQueryResult.indexOf("@@Enter")!=-1 || strQueryResult.indexOf("@@DouQuot")!=-1 || strQueryResult.indexOf("@@SinQuot")!=-1)
    {
        try
        {
            strQueryResult = Conversion(strQueryResult);
        }
        catch(e)
        {}
    }
    //根据约定返回的字符串格式进行修改
    if (typeof(strQueryResult) == "string" && strQueryResult.substring(0, strQueryResult.indexOf("|")) != "0")
    {
        return false;
    }
    else
    {
        return strQueryResult;
    }
}

/**
 * 当查询结果缓存设置在其他页面时，中转查询结果
 * @param strQueryResult - 需要进行中转的数据
 */
function transferQueryResult(strQueryResult)
{
    if (typeof(sqlCacheWindow) == "object")
    {
        sqlCacheWindow.setQueryResult(strQueryResult);
    }
}

/**
 * 异步查询获取结果接口测试
 * @param strQueryResult - 异步查询结果字符串
 */
function afterEasyQueryVer3(strQueryResult)
{
    alert("异步查询，未完成:"+strQueryResult);
}

/**
 * 公用查询接口函数
 * @param strSql - SQL语句字符串
 * @param synchronization - 同异步设置，“1”同步，“0”异步，默认同步
 * @param useCache - 使用缓存设置，“1”使用，“0”不使用，默认使用
 * @param intStart - 指定查找索引，默认从第一条记录开始查询
 * @param notUseEasyQuery - 非EASYQUERY返回字符串标记，自己编写查询后台时使用，“1”非，“0”是，默认非
 * @param notUseTurnPage - 不使用翻页功能标记，“1”非，“0”是，默认非
 * @return 查询结果数组，失败返回NULL
 */
function easyExecSql(strSql, synchronization, useCache, strStart, notUseEasyQuery, notUseTurnPage)
{
    var strEasyQueryVer3Result;
    strEasyQueryVer3Result = easyQueryVer3(strSql, synchronization, useCache, strStart);
    if (!strEasyQueryVer3Result)
    {
        return null;
    }
    else if (strEasyQueryVer3Result.substring(0, strEasyQueryVer3Result.indexOf("|")) != "0")
        {
            return null;
        }
        else
        {
            return decodeEasyQueryResult(strEasyQueryVer3Result, notUseEasyQuery, notUseTurnPage);
        }
}

/**
 * 拆分约定格式字符串至二维数组函数
 * 将查询结果字符串拆分到查询结果数组中，字符串头部有总记录数信息
 * 如果与翻页功能配合使用（定义了turnPage全局对象），则进行数组缩小至turnPage.pageLineNum * turnPage.blockPageNum
 * 并且显示行、页数turnPage.pageLineNum * turnPage.blockPageNum设置大于数据库查询返回量是有错误的
 * @param strResult - 约定格式字符串，以“^”分隔记录，以“|”分隔字段
 * @param notUseEasyQuery - 非EASYQUERY返回字符串标记，自己编写查询后台时使用，“1”非，“0”是，默认非
 * @param notUseTurnPage - 不使用翻页功能标记，“1”非，“0”是，默认非
 * @param otherTurnPage - 使用其它的翻页对象，而不是默认的turnPage对象，必须是一个turnPageClass的对象
 * @return 二维数组，失败返回NULL
 */
function decodeEasyQueryResult(strResult, notUseEasyQuery, notUseTurnPage, otherTurnPage)
{
    var arrEasyQuery = new Array();
    var arrRecord = new Array();
    var arrField = new Array();
    var recordNum, fieldNum, i, j;
    var cTurnPage;
    if (typeof(otherTurnPage)=="object")
    {
        cTurnPage = otherTurnPage;
    }
    else
    {
        try
        {
            cTurnPage = turnPage;
        }
        catch(e)
        {}
    }
    if (typeof(strResult) == "undefined" || strResult == "" || strResult == false)
    {
        return null;
    }

    //公用常量处理，增强容错性
    if (typeof(RECORDDELIMITER) == "undefined") RECORDDELIMITER = "^";
    if (typeof(FIELDDELIMITER) == "undefined") FIELDDELIMITER = "|";
    try
    {
        arrRecord = strResult.split(RECORDDELIMITER);   //拆分查询结果，得到记录数组
        //特殊处理，查询结果字符串首位存有所有满足条件记录总数信息
        if ((typeof(notUseTurnPage)=="undefined" || notUseTurnPage==0) && typeof(cTurnPage)=="object")
        {
            cTurnPage.queryAllRecordCount = arrRecord[0].substring(arrRecord[0].indexOf(FIELDDELIMITER) + 1);
        }
        if(typeof(notUseEasyQuery)=="undefined" || notUseEasyQuery=="" || notUseEasyQuery==0)
        {
            if (arrRecord[0].substring(arrRecord[0].indexOf(FIELDDELIMITER) + 1) != "" && arrRecord[0].substring(arrRecord[0].indexOf(FIELDDELIMITER) + 1) == 0)
            {
                return null;
            }
            arrRecord.shift();
        }
        recordNum = arrRecord.length;
//      for(i=0; i<recordNum; i++)
        var i=0;
        while(i<recordNum)
        {
            arrField = arrRecord[i].split(FIELDDELIMITER);  //拆分记录，得到字段数组
            fieldNum = arrField.length;
            arrEasyQuery[i] = new Array();
//          for(j=0; j<fieldNum; j++)
            var j=0;
            while(j<fieldNum)
            {
                arrEasyQuery[i][j] = arrField[j];   //形成以行为记录，列为字段的二维数组
                j++;
            }
            i++;
        }

    }
    catch(ex)
    {
        alert("拆分数据失败！错误原因是：" + ex);
        return null;
    }
    //配合翻页控制进行显示数据处理
    if ((typeof(notUseTurnPage)=="undefined" || notUseTurnPage==0) && typeof(cTurnPage)=="object" && cTurnPage.useSimulation==0)
    {
        var arrQueryResultNum = arrEasyQuery.length;
//      for (i=cTurnPage.dataBlockNum; i<arrQueryResultNum; i++)
        var i=cTurnPage.dataBlockNum;
        while(i<arrQueryResultNum)
        {
            arrEasyQuery.pop();
            i++;
        }
    }
    else if ((typeof(notUseTurnPage)=="undefined" || notUseTurnPage==0) && typeof(cTurnPage)=="object" && cTurnPage.useSimulation==1)
        {
            cTurnPage.blockPageNum = cTurnPage.queryAllRecordCount / cTurnPage.pageLineNum;
        }
    return arrEasyQuery;
}

/**
 * 拆分约定格式字符串至二维数组函数
 * 将查询结果字符串拆分到查询结果数组中，字符串头部有总记录数信息
 * 如果与翻页功能配合使用（定义了turnPage全局对象），则进行数组缩小至turnPage.pageLineNum * turnPage.blockPageNum
 * 并且显示行、页数turnPage.pageLineNum * turnPage.blockPageNum设置大于数据库查询返回量是有错误的
 * @param strResult - 约定格式字符串，以“^”分隔记录，以“|”分隔字段
 * @param notUseEasyQuery - 非EASYQUERY返回字符串标记，自己编写查询后台时使用，“1”非，“0”是，默认非
 * @param notUseTurnPage - 不使用翻页功能标记，“1”非，“0”是，默认非
 * @param otherTurnPage - 使用其它的翻页对象，而不是默认的turnPage对象，必须是一个turnPageClass的对象
 * @return 二维数组，失败返回NULL
 */
function decodeEasyQueryResultV2(statment,strResult,notUseEasyQuery)
{
    var arrEasyQuery = new Array();
    var arrRecord = new Array();
    var arrField = new Array();
    var recordNum, fieldNum, i, j;

    //公用常量处理，增强容错性
    if (typeof(RECORDDELIMITER) == "undefined") RECORDDELIMITER = "^";
    if (typeof(FIELDDELIMITER) == "undefined") FIELDDELIMITER = "|";

    try
    {
        strResult=strResult.substring(strResult.indexOf(RECORDDELIMITER)+1);    //去掉第一个长度字段
        arrRecord = strResult.split(RECORDDELIMITER);   //拆分查询结果，得到记录数组
        recordNum = arrRecord.length;
        for(i=0; i<recordNum; i++)
        {
            arrField = arrRecord[i].split(FIELDDELIMITER);  //拆分记录，得到字段数组
            fieldNum = arrField.length;
            if ( fieldNum!=statment.length )
            {
                alert("长度不匹配,不能使用该方法");
                return null;
            }
            arrEasyQuery[i] = new Array();
            for(j=0; j<fieldNum; j++)
            {
                sb = trim(statment[j]);
                arrEasyQuery[i][sb] = arrField[j];  //形成以行为记录，列为字段的二维数组
            }
            for(j=0;j<fieldNum; j++)
            {
                sb = trim(statment[j]);
            }
        }
    }
    catch(ex)
    {
        alert("拆分数据失败！错误原因是：" + ex.message);
        return null;
    }

    return arrEasyQuery;
}

/**
 * 用MULTILINE显示二维数组函数
 * @param arrDisplayData - 二维数组
 * @param multilineGrid - multiline对象
 * @return 失败返回FALSE
 */
function displayMultiline(arrDisplayData, multilineGrid, otherTurnPage)
{
    var i, j;
    var recordNum, fieldNum;
    var cTurnPage;
    if (typeof(otherTurnPage)=="object")
    {
        cTurnPage = otherTurnPage;
    }
    else
    {
        cTurnPage = turnPage;
    }
    //arrDisplayData接口参数处理
    if (typeof(arrDisplayData) == "undefined")
    {
        alert( "displayEasyResult没有收到要显示的数据!" );
        return false;
    }
    //multilineGrid接口参数处理
    if (typeof(multilineGrid) == "undefined")
    {
        alert( "displayEasyResult没有找到显示容器对象MULTILINE!" );
        return false;
    }
    // 初始化MULTILINE表格
    try
    {
        multilineGrid.mulLineCount = arrDisplayData.length;
        // 使记录数与序号统一
        if (typeof(cTurnPage) == "object")
        {
            multilineGrid.recordNo = cTurnPage.pageIndex * cTurnPage.pageLineNum;
        }
        else
        {
            multilineGrid.recordNo = 0;

        }
        //2006-3-13 15:19 朱向峰修改数据装载逻辑
//      multilineGrid.loadMulLine(multilineGrid.arraySave);
        multilineGrid.loadMulLineArr(multilineGrid.arraySave,arrDisplayData);
    }
    catch(ex)
    {
        alert("displayMultiline 初始化Multiline对象错误!");
        return false;
    }
//  for(i=0; i<arrDisplayData.length; i++)
//  {
//      fieldNum = arrDisplayData[i].length;
//      for(j=0; j<fieldNum; j++)
//      {
//          multilineGrid.setRowColData(i, j+1, arrDisplayData[i][j]);
//      }
//  }
}

/**
 * 根据页索引和页面记录数，从整个数据块中取出当前页要显示的记录块
 * @param arrDataSet - 二维数组
 * @param arrDataIndex - 页索引，根据他计算出开始截取的位置
 * @param pageRecordNum - 页面记录数，根据他确定取的行数
 * @return 二维数组，失败返回NULL
 */
function getPageDisplayData(arrDataSet, arrDataIndex, pageRecordNum, otherTurnPage)
{
    var arrDisplayData = new Array();
    var fieldNum;
    var cTurnPage;

    if (typeof(otherTurnPage)=="object")
    {
        cTurnPage = otherTurnPage;
    }
    else
    {
        cTurnPage = turnPage;
    }

    if (typeof(pageRecordNum) == "undefined") pageRecordNum = cTurnPage.pageLineNum;

    try {
        for( i = arrDataIndex; i < (arrDataIndex + pageRecordNum < arrDataSet.length ? arrDataIndex + pageRecordNum : arrDataSet.length); i++ )
        {
            fieldNum = arrDataSet[i].length;
            arrDisplayData[i - arrDataIndex] = new Array();
            for( j = 0; j < fieldNum; j++ )
            {
                arrDisplayData[i - arrDataIndex][j] = arrDataSet[i][j];
            }
        }
    }
    catch(ex)
    {
        alert("getPageDisplayData处理出错！");
        return null;
    }
    return arrDisplayData;
}

/**
 * 查找下一页主函数，主要针对查询显示下一页按钮的事件
 * @return 失败返回FALSE
 */
function getNextPage()
{
    //为兼容已允许的程序，容错而增加
    var cTurnPage = this;
    if (typeof(this.strQueryResult)=="undefined")
    {
        cTurnPage = turnPage;
    }
    if (cTurnPage.strQueryResult == "" || cTurnPage.strQueryResult == false)
    {
        alert("请先查询！");
        return false;
    }
    //页尾判断：cTurnPage.pageIndex从0开始记数，cTurnPage.queryAllRecordCount全部记录数/cTurnPage.pageLineNum每页行数，得到全部页数
    if (cTurnPage.pageIndex == Math.ceil(cTurnPage.queryAllRecordCount/cTurnPage.pageLineNum) - 1)
    {
        alert("已经到达尾页!");
        return false;
    }

    //翻页查询,判断是否跨跃到新的后台数据库缓存块：cTurnPage.pageIndex是累加的，cTurnPage.dataBlockNum 是后台数据库缓存块的大小，判断下一页的第一条记录号是否是位于新的后台数据库缓存块的第一条,是则重新请求新的后台数据库缓存块,否则仍就在旧的后台数据库缓存块取出当前请求页的数据记录
    var intStart = Math.ceil(((cTurnPage.pageIndex + 1)*cTurnPage.pageLineNum + 1)/cTurnPage.dataBlockNum -1) * cTurnPage.dataBlockNum + 1 ;
    if(cTurnPage.useSimulation == 1)
    {
        //对虚拟数据源沿用以前的处理2004-04-22 add by sxy
        //一般情况下的控制，仅为页数加一和显示即可
        cTurnPage.pageIndex = cTurnPage.pageIndex + 1;
        displayMultiline(getPageDisplayData(cTurnPage.arrDataCacheSet, cTurnPage.pageIndex % cTurnPage.blockPageNum * cTurnPage.pageLineNum, cTurnPage.pageLineNum), cTurnPage.pageDisplayGrid, cTurnPage);
        cTurnPage.pageDisplayGrid.setPageMark(cTurnPage);
        return false;
    }
    cTurnPage.pageIndex = cTurnPage.pageIndex + 1;
    if (((cTurnPage.pageIndex * cTurnPage.pageLineNum )% cTurnPage.dataBlockNum) == 0 && cTurnPage.useSimulation == 0)
    {
        cTurnPage.strQueryResult = easyQueryVer3(cTurnPage.strQuerySql, cTurnPage.synchronization, cTurnPage.useCache, intStart, mLargeFlag);
        cTurnPage.arrDataCacheSet = decodeEasyQueryResult(cTurnPage.strQueryResult, 0, 0, cTurnPage);
        var nextDateBlockLineIndex = cTurnPage.pageIndex *cTurnPage.pageLineNum +1 - intStart ;
        displayMultiline(getPageDisplayData(cTurnPage.arrDataCacheSet, nextDateBlockLineIndex, cTurnPage.pageLineNum), cTurnPage.pageDisplayGrid, cTurnPage);
        cTurnPage.pageDisplayGrid.setPageMark(cTurnPage);
        return false;
    }
    //获得以cTurnPage.pageLineNum为以单位大小划分的最后数据页起始记录在cTurnPage.dataBlockNum为单位大小划分的最后数据库块中的排序号
    var nextDateBlockLineIndex = cTurnPage.pageIndex *cTurnPage.pageLineNum +1 - intStart ;
    displayMultiline(getPageDisplayData(cTurnPage.arrDataCacheSet, nextDateBlockLineIndex, cTurnPage.pageLineNum), cTurnPage.pageDisplayGrid, cTurnPage);
    cTurnPage.pageDisplayGrid.setPageMark(cTurnPage);
    return false;
}

/**
 * 查找上一页主函数，主要针对查询显示上一页按钮的事件
 * @return 失败返回FALSE
 */
function getPreviousPage()
{
    //为兼容已允许的程序，容错而增加
    var cTurnPage = this;
    if (typeof(this.strQueryResult)=="undefined")
    {
        cTurnPage = turnPage;
    }

    if (cTurnPage.strQueryResult == "" || cTurnPage.strQueryResult == false)
    {
        alert("请先查询！");
        return false;
    }

    //cTurnPage.pageIndex从0开始记数，并且是累积的，因此为零时为全部记录的第一条
    if (cTurnPage.pageIndex == 0)
    {
        alert("已经到达首页!");
        return false;
    }

    //翻页查询,判断是否跨跃到新的后台数据库缓存块：cTurnPage.pageIndex是累加的，cTurnPage.dataBlockNum 是后台数据库缓存块的大小，判断下一页的第一条记录号是否是位于新的后台数据库缓存块的第一条,是则重新请求新的后台数据库缓存块,否则仍就在旧的后台数据库缓存块取出当前请求页的数据记录
    var intStart = Math.ceil(((cTurnPage.pageIndex - 1)*cTurnPage.pageLineNum + 1)/cTurnPage.dataBlockNum -1) * cTurnPage.dataBlockNum + 1 ;
    cTurnPage.pageIndex = cTurnPage.pageIndex - 1;
    if (((cTurnPage.pageIndex+1)*cTurnPage.pageLineNum)%cTurnPage.dataBlockNum == 0 && cTurnPage.useSimulation == 0)
    {
        cTurnPage.strQueryResult = easyQueryVer3(cTurnPage.strQuerySql, cTurnPage.synchronization, cTurnPage.useCache, intStart, mLargeFlag);
        cTurnPage.arrDataCacheSet = decodeEasyQueryResult(cTurnPage.strQueryResult, 0, 0, cTurnPage);
        //获得以cTurnPage.pageLineNum为以单位大小划分的最后数据页起始记录在cTurnPage.dataBlockNum为单位大小划分的最后数据库块中的排序号
        var previousDateBlockLineIndex = (cTurnPage.pageIndex) *cTurnPage.pageLineNum +1 - intStart ;
        displayMultiline(getPageDisplayData(cTurnPage.arrDataCacheSet,previousDateBlockLineIndex , cTurnPage.pageLineNum), cTurnPage.pageDisplayGrid, cTurnPage);
        cTurnPage.pageDisplayGrid.setPageMark(cTurnPage);
        return false;
    }
    //获得以cTurnPage.pageLineNum为以单位大小划分的最后数据页起始记录在cTurnPage.dataBlockNum为单位大小划分的最后数据库块中的排序号
    var previousDateBlockLineIndex = (cTurnPage.pageIndex) *cTurnPage.pageLineNum +1 - intStart ;
    displayMultiline(getPageDisplayData(cTurnPage.arrDataCacheSet,previousDateBlockLineIndex , cTurnPage.pageLineNum), cTurnPage.pageDisplayGrid, cTurnPage);
    cTurnPage.pageDisplayGrid.setPageMark(cTurnPage);
}

/**
 * 查找尾页主函数，主要针对查询显示尾页按钮的事件
 * @return 失败返回FALSE
 */
function getLastPage()
{
    //为兼容已允许的程序，容错而增加
    var cTurnPage = this;
    if (typeof(this.strQueryResult)=="undefined")
    {
        cTurnPage = turnPage;
    }

    if (cTurnPage.strQueryResult == "" || cTurnPage.strQueryResult == false)
    {
        alert("请先查询！");
        return false;
    }

    //页尾判断：cTurnPage.pageIndex从0开始记数，cTurnPage.queryAllRecordCount全部记录数/cTurnPage.pageLineNum每页行数，得到全部页数
    if (cTurnPage.pageIndex == Math.ceil(cTurnPage.queryAllRecordCount/cTurnPage.pageLineNum) - 1)
    {
        alert("已经到达尾页!");
        return false;
    }

    //将页数设置到最后一块的最后一页，cTurnPage.queryAllRecordCount - cTurnPage.queryAllRecordCount % (cTurnPage.blockPageNum * cTurnPage.pageLineNum) + 1 为计算最后一块
    cTurnPage.pageIndex = Math.ceil(cTurnPage.queryAllRecordCount/cTurnPage.pageLineNum - 1);
    //获得页面总数
    cTurnPage.blockPageNum = Math.ceil(cTurnPage.queryAllRecordCount/cTurnPage.pageLineNum );

    //计算查询开始位置:是所查数据记录所在后台数据库缓存块的起始位置.
    var intStart = Math.ceil(cTurnPage.queryAllRecordCount/cTurnPage.dataBlockNum -1)*cTurnPage.dataBlockNum + 1 ;

    if (cTurnPage.useSimulation == 0)
    {
        //获得计算所查数据记录所在后台数据库缓存块的起始位置.
        cTurnPage.strQueryResult = easyQueryVer3(cTurnPage.strQuerySql, cTurnPage.synchronization, cTurnPage.useCache, intStart, mLargeFlag);
        cTurnPage.arrDataCacheSet = decodeEasyQueryResult(cTurnPage.strQueryResult, 0, 0, cTurnPage);
        //获得以cTurnPage.pageLineNum为以单位大小划分的最后数据页起始记录在cTurnPage.dataBlockNum为单位大小划分的最后数据库块中的排序号
        var lastDateBlockLineIndex = Math.ceil(cTurnPage.queryAllRecordCount/cTurnPage.pageLineNum -1)*cTurnPage.pageLineNum - Math.ceil(cTurnPage.queryAllRecordCount/cTurnPage.dataBlockNum -1)*cTurnPage.dataBlockNum ;
        displayMultiline(getPageDisplayData(cTurnPage.arrDataCacheSet, lastDateBlockLineIndex, cTurnPage.pageLineNum), cTurnPage.pageDisplayGrid, cTurnPage);
        cTurnPage.pageDisplayGrid.setPageMark(cTurnPage);
        return false;
    }
    //获得以cTurnPage.pageLineNum为以单位大小划分的最后数据页起始记录在cTurnPage.dataBlockNum为单位大小划分的最后数据库块中的排序号
    var lastDateBlockLineIndex = Math.ceil(cTurnPage.queryAllRecordCount/cTurnPage.pageLineNum -1)*cTurnPage.pageLineNum - Math.ceil(cTurnPage.queryAllRecordCount/cTurnPage.dataBlockNum -1)*cTurnPage.dataBlockNum ;
    displayMultiline(getPageDisplayData(cTurnPage.arrDataCacheSet, lastDateBlockLineIndex, cTurnPage.pageLineNum), cTurnPage.pageDisplayGrid, cTurnPage);
    cTurnPage.pageDisplayGrid.setPageMark(cTurnPage);
}

/**
 * 查找首页主函数，主要针对查询显示首页按钮的事件
 * @return 失败返回FALSE
 */
function getFirstPage()
{
    //为兼容已允许的程序，容错而增加
    var cTurnPage = this;
    if (typeof(this.strQueryResult)=="undefined")
    {
        cTurnPage = turnPage;
    }

    if (cTurnPage.strQueryResult == "" || cTurnPage.strQueryResult == false)
    {
        alert("请先查询！");
        return false;
    }

    //cTurnPage.pageIndex从0开始记数，并且是累积的，因此为零时为全部记录的第一条
    if (cTurnPage.pageIndex == 0)
    {
        alert("已经到达首页!");
        return false;
    }

    //将页数设置到第一块的第一页
    cTurnPage.pageIndex = 0;
    if (cTurnPage.useSimulation == 0)
    {
        cTurnPage.strQueryResult = easyQueryVer3(cTurnPage.strQuerySql, cTurnPage.synchronization, cTurnPage.useCache, 1, mLargeFlag);
        cTurnPage.arrDataCacheSet = decodeEasyQueryResult(cTurnPage.strQueryResult, 0, 0, cTurnPage);
        cTurnPage.pageDisplayGrid.setPageMark(cTurnPage);
    }
    displayMultiline(getPageDisplayData(cTurnPage.arrDataCacheSet, 0, cTurnPage.pageLineNum), cTurnPage.pageDisplayGrid, cTurnPage);
    cTurnPage.pageDisplayGrid.setPageMark(cTurnPage);
}

/**
 * 翻页类
 * 对显示的页数和行数进行了初始化
 */
function turnPageClass()
{
    this.arrDataCacheSet = new Array();
    this.pageDisplayGrid = "";  //显示表格控件（MULTILINE）
    this.strQuerySql = "";  //查询的SQL语句
    this.strQueryResult = "";   //查询结果字符串
    this.queryAllRecordCount = 0;   //查询结果记录总数
    this.pageIndex = 0; //缓存索引
    this.pageRecordNum = 0; //页面显示数量
    this.synchronization = 1;   //同、异步查询标记
    this.useCache = 0;  //使用缓存标记
    this.startQueryRecord = 1;  //查询开始的位置标记，既从第几个记录开始进行查询
    this.useSimulation = 0; //使用模拟数据源,为1时表示使用
    this.blockPageNum = 20; //数据块的页面数(暂时未用)
    this.dataBlockNum = 200;    //数据库后台数据块的页面数（该值为数据库后台缓存集合大小,所以当数据库后台缓存集合其大小变化时需要同时改变该值sxy-add 2004-02-02）
    this.pageLineNum = 10;  //每页的数据行数(其值必须为dataBlockNum的约数sun-add-2004-02-02)
    this.pageDivName = "divPage";   //每页的数据行数
    this.getData = getPageDisplayData;
    this.nextPage = getNextPage;
    this.previousPage = getPreviousPage;
    this.lastPage = getLastPage;
    this.firstPage = getFirstPage;
    this.queryModal = easyQueryVer3Modal;
    this.allowsortcol =0;   //要排序的列
    this.sortdesc = new Array();
    this.allowsort =allowsort;
    this.allowsortparam =allowsortparam;
    this.gotoPage = showGotoPage;
    if (typeof(MAXMEMORYPAGES) != "undefined") this.blockPageNum = MAXMEMORYPAGES;
    if (typeof(MAXSCREENLINES) != "undefined") this.pageLineNum = MAXSCREENLINES;
}

/**
 * 集成查询、拆串、显示、翻页控制的查询模板
 * @param strSql - SQL语句
 * @param multilineGrid - multiline对象
 * @param LargeFlag - 大数据量查询控制
 * @param IndexFlag - 是否展现页面跳转功
 * @return 失败返回FALSE
 */
function easyQueryVer3Modal(strSql, multilineGrid, LargeFlag, IndexFlag, PageLineNum)
{
    //为兼容已允许的程序，容错而增加
    var cTurnPage = this;
    if (typeof(this.strQueryResult)=="undefined")
    {
        cTurnPage = turnPage;
    }

    //LargeFlag 接口参数处理
    if (typeof(LargeFlag) == "undefined" || LargeFlag == "0" || (typeof(LargeFlag) == "string" && LargeFlag == ""))
    {
        //查询SQL，返回结果字符串
        cTurnPage.strQueryResult = easyQueryVer3(strSql, 1, 0, 1);
    }
    else
    {
        //查询SQL，返回结果字符串
        cTurnPage.strQueryResult = easyQueryVer3(strSql, 1, 0, 1, 1, 1);
    }
    //PageLineNum 接口参数处理
    if (typeof(PageLineNum) == "undefined" || (typeof(PageLineNum) == "string" && PageLineNum == ""))
    {}
    else
    {
        cTurnPage.pageLineNum = PageLineNum;
    }

    //判断是否查询成功
    if (!cTurnPage.strQueryResult)
    {
        //清空MULTILINE，使用方法见MULTILINE使用说明
        multilineGrid.clearData();
        try
        {
            window.document.all(cTurnPage.pageDivName).style.display = "none";
        }
        catch (ex) {}
        return false;
    }

    //清空数据容器，两个不同查询共用一个turnPage对象时必须使用，最好加上，容错
    cTurnPage.arrDataCacheSet = clearArrayElements(cTurnPage.arrDataCacheSet);
    //查询成功则拆分字符串，返回二维数组
    cTurnPage.arrDataCacheSet = decodeEasyQueryResult(cTurnPage.strQueryResult, 0, 0, cTurnPage);
    //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
    cTurnPage.pageDisplayGrid = multilineGrid;
    if(typeof(IndexFlag) == "undefined" || IndexFlag == "0" || (typeof(IndexFlag) == "string" && IndexFlag == ""))
    {}
    else if(IndexFlag == "1" )
    {
        multilineGrid.SortPage = cTurnPage;
    }
    //保存SQL语句
    cTurnPage.strQuerySql = strSql;
    //设置查询起始位置
    cTurnPage.pageIndex = 0;
    //在查询结果数组中取出符合页面显示大小设置的数组
    var arrDataSet = cTurnPage.getData(cTurnPage.arrDataCacheSet, cTurnPage.pageIndex, cTurnPage.pageLineNum);
    //调用MULTILINE对象显示查询结果
    displayMultiline(arrDataSet, cTurnPage.pageDisplayGrid, cTurnPage);
    //控制是否显示翻页按钮
    if (cTurnPage.queryAllRecordCount > cTurnPage.pageLineNum)
    {
        try
        {
            window.document.all(cTurnPage.pageDivName).style.display = "";
        }
        catch (ex) {}
    }
    else
    {
        try
        {
            window.document.all(cTurnPage.pageDivName).style.display = "none";
        }
        catch (ex) {}
    }
    multilineGrid.setPageMark(cTurnPage);
}

/**
 * 查询拼串工具
 * @param fieldName - 字段名称
 * @param controlName - 控件名称
 * @param strOperate - 操作符
 * @param type - 字段类型( 0:字符型　1:数字型 )
 * @return 拼好的串
 */

 function getWherePart(fieldName, controlName, strOperate, fieldType) {

    var strWherePart = "";
    var value = "";
    if(controlName == "" || controlName == null) controlName = fieldName;
    //XinYQ modified on 2006-05-09 : 修正如果存在同名控件则无法获取任何值,修改为取首个同名控件的值
    //OLD : value = eval( "fm." + trim( controlName ) + ".value" );
    value = window.document.getElementsByName(trim(controlName))[0].value;
    if(value == "" || value == null) return strWherePart;
    if(fieldType == null || fieldType == "") fieldType = "0";
    if(strOperate == "" || strOperate == null) strOperate = "=";
    if(fieldType == "0") { // 0:字符型


        if(strOperate == "like")
        {
            strWherePart = " and " + trim( fieldName ) + " like '" + trim( value ) + "%25' ";
        }
        else
        {
            strWherePart = " and " + trim( fieldName ) + trim( strOperate ) + "'" + trim( value ) + "' ";
        }
    }
    if(fieldType == "1") { // 1:数字型


        strWherePart = " and " + trim(fieldName) + trim(strOperate) + trim(value) + " ";
    }
    return strWherePart;
}

/**
 * 清空数组函数
 * @param arrData - 数组
 * @return 空数组
 */
function clearArrayElements(arrData)
{
    try
    {
        while (arrData.length != 0) arrData.pop();
    }
    catch(ex)
    {
        alert("clearArrayElements进行数组清空失败:"+ex);
        return new Array();
    }
    return arrData;
}

/**
* 截取sql的statement部分，分解数租
*/
function getStatment(strsql)
{
    try

    {
        strsql= trim(strsql);
        var  statpart = strsql.substring(6, strsql.indexOf("from"));
        var statment= statpart.split(",");
        for ( i=0;i<statment.length;i++)
        {
            statment[i] = trim(statment[i]);
        }
        return statment ;
    }
    catch(ex)
    {
        alert("getStatement出错："+ex.message);
        return null;
    }
}

/**
* 根据传入的sql从数据库中查询记录，显示到页面中
* 注意:sql中出现的字段名字要和页面元素的名字一样，能处理多行文本输入的情况
* author:wujs 2005-1-14 11:42
*/
function EasyShowForm( strSQL,alterflag,paraForm)
{
    if ( paraForm == null || paraForm=='undefined')
    {
        //默认取fm
        paraForm = fm;
    }
    if ( alterflag ==null || alterflag=='undefined' || alterflag==1)
    {
         alterflag=true;
    }
    else
    {
        alterflag=false;
    }
    var strQueryResult = easyQueryVer3(strSQL, 1, 0, 1);
    if (!strQueryResult)
    {

        return false;
    }
    var statment = getStatment(strSQL );
    if (statment==null)
    {
        alert("传入sql不符合要求");
        return false;
    }
    var deResult= decodeEasyQueryResultV2(statment,strQueryResult);

    for( i=0;i<statment.length ;i++)
    {
        try
        {
            paraForm.all(statment[i]).value =Conversion( deResult[0][statment[i]] ) ;
        }
        catch(ex)
        {
            if ( alterflag)
            {
                alert("没找到页面元素:"+ statment[i]);
                return false;
            }
        }
    }
    return true;
}

function allowsort(i)
{
    var sortturnPage =this;
    allowsortcol=i-1;//为什么不能用page取得？不归属与turnpageclass对象...?
    if(sortturnPage.sortdesc[allowsortcol]!="asc")
    {
        sortturnPage.arrDataCacheSet = sortturnPage.arrDataCacheSet.sort(allowsortparam);
        sortturnPage.sortdesc[allowsortcol]="asc";
        for(var n=0;n<sortturnPage.pageDisplayGrid.colCount;n++)
        {
            if(n!=allowsortcol) sortturnPage.sortdesc[n]="desc";
        }
    }
    else
    {
        sortturnPage.arrDataCacheSet = sortturnPage.arrDataCacheSet.reverse(allowsortparam);
        for(var n=0;n<sortturnPage.pageDisplayGrid.colCount;n++)
        {
            if(n!=allowsortcol) sortturnPage.sortdesc[n]="desc";
        }
    }
    //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
    //设置查询起始位置
    sortturnPage.pageIndex = 0;
    //在查询结果数组中取出符合页面显示大小设置的数组
    var arrDataSet = sortturnPage.getData(sortturnPage.arrDataCacheSet, sortturnPage.pageIndex, sortturnPage.pageLineNum);
    //调用MULTILINE对象显示查询结果
    displayMultiline(arrDataSet, sortturnPage.pageDisplayGrid, sortturnPage);

}

function allowsortparam(x,y)
{
    if (x[allowsortcol] > y[allowsortcol])
    {
        return 1;
    }
    else if (x[allowsortcol] < y[allowsortcol])
        {
            return -1;
        }
        else
        {
            return 0;
        }
    //根据二维数组的第三列的第一个字母的ASCII码来降序排序
}

//按页数翻页
function showGotoPage(tIndex){
    var cTurnPage = this;
    if (typeof(this.strQueryResult)=="undefined") {
        cTurnPage = turnPage;
    }
    if (cTurnPage.strQueryResult == "" || cTurnPage.strQueryResult == false) {
        alert("请先查询！");
        return false;
    }
    if(tIndex==null||tIndex==""||!isInteger(tIndex))
    {
        alert("请输入正确页号!");
        return;
    }
    if(tIndex<1 || tIndex>Math.ceil(cTurnPage.queryAllRecordCount/cTurnPage.pageLineNum)){
        alert("跳转页号超出范围!");
        return;
    }
    var tIndexPage = tIndex-1;
    var currentBlock = Math.floor(cTurnPage.pageIndex/cTurnPage.blockPageNum);
    var gotoBlock = Math.floor(tIndexPage/cTurnPage.blockPageNum);
    cTurnPage.pageIndex = tIndexPage;
    cTurnPage.blockPageNum = Math.ceil(cTurnPage.queryAllRecordCount/cTurnPage.pageLineNum );

    //计算查询开始位置:是所查数据记录所在后台数据库缓存块的起始位置.
    var intStart =((Math.ceil((tIndex*cTurnPage.pageLineNum)/cTurnPage.dataBlockNum))-1)*cTurnPage.dataBlockNum+1;
    if (cTurnPage.useSimulation == 0)
    {
        //获得计算所查数据记录所在后台数据库缓存块的起始位置.
        cTurnPage.strQueryResult = easyQueryVer3(cTurnPage.strQuerySql, cTurnPage.synchronization, cTurnPage.useCache, intStart, mLargeFlag);
        cTurnPage.arrDataCacheSet = decodeEasyQueryResult(cTurnPage.strQueryResult, 0, 0, cTurnPage);
        //获得以cTurnPage.pageLineNum为以单位大小划分的最后数据页起始记录在cTurnPage.dataBlockNum为单位大小划分的最后数据库块中的排序号
        var mIndex = ((Math.ceil(((tIndex*cTurnPage.pageLineNum)%cTurnPage.dataBlockNum))/cTurnPage.pageLineNum)-1)*cTurnPage.pageLineNum ;
        if(mIndex==-10)
        {
            mIndex=190;
        }
        displayMultiline(getPageDisplayData(cTurnPage.arrDataCacheSet,mIndex, cTurnPage.pageLineNum), cTurnPage.pageDisplayGrid, cTurnPage);
        cTurnPage.pageDisplayGrid.setPageMark(cTurnPage);
        return false;
    }
    if(cTurnPage.useSimulation == 1)
    {
        //对虚拟数据源沿用以前的处理2004-04-22 add by sxy
        //一般情况下的控制，仅为页数加一和显示即可
        displayMultiline(getPageDisplayData(cTurnPage.arrDataCacheSet, cTurnPage.pageIndex % cTurnPage.blockPageNum * cTurnPage.pageLineNum, cTurnPage.pageLineNum), cTurnPage.pageDisplayGrid, cTurnPage);
        cTurnPage.pageDisplayGrid.setPageMark(cTurnPage);
        return false;
    }
    //获得以cTurnPage.pageLineNum为以单位大小划分的最后数据页起始记录在cTurnPage.dataBlockNum为单位大小划分的最后数据库块中的排序号
    displayMultiline(getPageDisplayData(cTurnPage.arrDataCacheSet, cTurnPage.pageIndex, cTurnPage.pageLineNum), cTurnPage.pageDisplayGrid, cTurnPage);
}

/**
 * 在String对象上添加trim方法
 * common.js 中有, 但是为了保证翻页之后的批次查询结果正确, 再次添加, 防止没有引入 common.js 而误报查询错误
 */
function String.prototype.trim()
{
    return this.replace(/(^\s*)|(\s*$)/g,"");
}



//EASYQUERY处理类
//主要用于保存JSP名称和SQL的名称及参数
//并生成服务器可以解析的串
//mojiao 2006-04-21
//
function SqlClass()
{
    this.sqlParaName = new Array();//参数名称数组
    this.sqlParaValue = new Array();//参数值数组
    this.paraCount = 0;//参数的count
    this.jspName = "";//对应的jsp文件名称
    this.sqlId = "";    //查询的sql对应的名称
    this.setJspName = _setJspName;//设置jsp名称函数
    this.setSqlId             = _setSQLID;//设置sql对应的名称函数
  this.addPara            = _addPara;//添加参数的函数
  this.getString        = _getString;//获得串的函数
  this.getWherePart =_getWherePart;//从界面获得对象的值的函数
  this.setValue =_setValue;
  this.setInPart=_setInpart;

}

//设置JSP名称
function _setJspName(jspName)
{
    var cMySqlClass=this;
    cMySqlClass.jspName=jspName;
}

//设置SQL的ID
function _setSQLID(sqlId)
{
    var cMySqlClass=this;
    cMySqlClass.sqlId=sqlId;
}

//添加参数
function _addPara(paraName,paraValue)
{
    var cMySqlClass=this;
    cMySqlClass.sqlParaName[cMySqlClass.paraCount]=paraName;
    cMySqlClass.sqlParaValue[cMySqlClass.paraCount]=paraValue;
    cMySqlClass.paraCount++;
}

//生成一个传入服务器处理的串
function _getString()
{
    var cMySqlClass=this;
    var strRetString="";
    strRetString+=cMySqlClass.jspName+";";
    strRetString+=cMySqlClass.sqlId+";";
    for( j = 0; j < cMySqlClass.paraCount; j++ )
    {
        strRetString+=cMySqlClass.sqlParaName[j]+"="+cMySqlClass.sqlParaValue[j]+";";
    }

//  alert(strRetString);
    return strRetString
}

//从界面获得指定名称的对象的值，如果不为空则加入到参数数组中
function _getWherePart(paraName, controlName)
{
    var cMySqlClass=this;
    var value = "";
    if(controlName == "" || controlName == null) controlName = paraName;
    value = eval("fm." + trim(controlName) + ".value");
    if(value == "" || value == null)
    {
        return;
    }else
    {
        cMySqlClass.addPara(trim(paraName),trim(value)) ;
        return;
    }
}

function _setValue(paraName,controlvalue)
 {
      var cMySqlClass=this;
      var value=controlvalue;
    cMySqlClass.addPara(trim(paraName),trim(value));
 }

function _setInpart(paraName,controlvalue)
{
      var i;
    var cMySqlClass=this;
    var value=controlvalue;
    for(i=0;i<cMySqlClass.paraCount;i++)
    {
        if(trim(cMySqlClass.sqlParaName[i])==trim(paraName))
        {
            cMySqlClass.sqlParaValue[i]=cMySqlClass.sqlParaValue[i].substring(0,cMySqlClass.sqlParaValue[i].length-1)+",'"+controlvalue+"')";
            break ;
        }
    }
    if(i==cMySqlClass.paraCount)
      {
       controlvalue="('"+controlvalue+"')";
         cMySqlClass.addPara(paraName,controlvalue);
      }
 }