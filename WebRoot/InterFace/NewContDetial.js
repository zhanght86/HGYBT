var showInfo;
var mDebug = "0";
var turnPage = new turnPageClass();

function resetForm() {
	
	try {
		initForm();
	} catch (re) {
		alert("在BankQuery.js-->resetForm函数中发生异常:初始化界面错误!");
	}
}


//查询事件
function queryItems() {
	

	initGrid();

	var sLogNo = fm.IFLog.value;
    //alert(sLogNo);
	var mSqlStr = "SELECT "
			+ "   I.FileName 文件名, "
			+ "   I.RCode 结果明细"
            + " FROM IFdetail I" + " WHERE "
			+ " 1 = 1 AND I.IFLogNo="+sLogNo+"";

	mSqlStr += "  ORDER BY I.LogNo";
	turnPage.queryModal(mSqlStr, QueryGrid, 0, 0);
}



function closePage()
{
 
    try
    { 
    	top.window.close();
    }
    catch(ex)
    {
      alert( "关闭页面时发生异常:" + ex );
    }
   
  } 