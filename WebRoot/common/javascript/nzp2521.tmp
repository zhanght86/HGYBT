//程序名称：NewEasyQueryCache.js
//程序功能：查询结果缓存页面
//创建日期：2002-09-28
//创建人  ：hubo
//更新记录：  更新人    更新日期     更新原因/内容
   
var sqlIndex = 0;                     //记录SQL语句在数组中的位置
var sqlCount = 0;                     //记录下的SQL语句的数量
var isDupliQuery = false;             //判断当前SQL语句是否与数组中记录重复的标志
var arrSql = new Array();             //记录SQL语句的数组
var arrQueryResult = new Array();     //记录与每条SQL语句对应的查询结果

//判断SQL语句是否缓存有，如果有返回内存中的查询结果，没有则存储SQL语句
function SqlCache( strSql ) {
  isDupliQuery = false;
  //判断SQL语句是否已经在数组中存在，标记
  for (sqlIndex=0; sqlIndex<sqlCount; sqlIndex++) {
    if (arrSql[sqlIndex] == strSql) {
      isDupliQuery = true;          
      break;
    };
  };
  
  if (isDupliQuery) {  
    //如果SQL语句存在，返回对应的查询结果字符串
    return arrQueryResult[sqlIndex]; 
  } else {
    //如果不存在，则将该条SQL语句记录进数组中
    arrSql[sqlCount] = strSql;
    sqlCount++;
    return isDupliQuery;
  }   
            
}

//存储查询结果
function setQueryResult( strResult ) {
  arrQueryResult[sqlCount - 1] = strResult;
}

//强制查询
function mustQuery( strSql ) {
  for (sqlIndex=0; sqlIndex<sqlCount; sqlIndex++) {
    if (arrSql[sqlIndex] == strSql) {
      arrSql[sqlIndex] = "";          
      arrQueryResult[sqlIndex] = "";
      break;
    };
  };	
}