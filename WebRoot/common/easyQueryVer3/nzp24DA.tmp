//程序名称：QueryCache.js
//程序功能：查询结果缓存页面
//创建日期：2002-09-28
//创建人  ：胡博
//更新记录：  更新人    更新日期     更新原因/内容
   
var sqlCacheIndex = 0;                //记录SQL语句在数组中的位置
var sqlCacheCount = 0;                //记录下的SQL语句的数量
var arrSql = new Array();             //记录SQL语句的数组
var arrQueryResult = new Array();     //记录与每条SQL语句对应的查询结果

//判断SQL语句是否缓存有，如果有返回内存中的查询结果，没有则存储SQL语句，返回FALSE
function SqlCache(strSql) {
  var isDupliQuery = false;           //判断当前SQL语句是否与数组中记录重复的标志
  //alert("sqlCacheCount:" + sqlCacheCount + "sqlCacheIndex:" + sqlCacheIndex );
  
  //判断SQL语句是否已经在数组中存在，标记
  for (sqlCacheIndex=0; sqlCacheIndex<sqlCacheCount; sqlCacheIndex++) {
    if (arrSql[sqlCacheIndex] == strSql) {
      isDupliQuery = true;          
      //alert("arrSql:" + arrSql + "\narrQueryResult:" + arrQueryResult);
      break;
    };
  };
  
  if (isDupliQuery) {                 //如果SQL语句存在，返回对应的查询结果字符串  
    return arrQueryResult[sqlCacheIndex]; 
  } else {                            //如果不存在，则将该条SQL语句记录进数组中
    arrSql[sqlCacheCount] = strSql;
    sqlCacheCount++;
    return isDupliQuery;
  }            
}

//存储查询结果
function setQueryResult(strResult) {
  arrQueryResult[sqlCacheCount - 1] = strResult;
}

//强制查询
function mustQuery(strSql) {
  for (sqlCacheIndex=0; sqlCacheIndex<sqlCacheCount; sqlCacheIndex++) {
    if (arrSql[sqlCacheIndex] == strSql) {
      arrSql[sqlCacheIndex] = "";          
      arrQueryResult[sqlCacheIndex] = "";
      break;
    };
  };	
}