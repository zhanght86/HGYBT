var showInfo;
var mDebug = "0";
var turnPage = new turnPageClass();

function resetForm() {
	
	try {
		initForm();
	} catch (re) {
		alert("��BankQuery.js-->resetForm�����з����쳣:��ʼ���������!");
	}
}


//��ѯ�¼�
function queryItems() {
	

	initGrid();

	var sLogNo = fm.IFLog.value;
    //alert(sLogNo);
	var mSqlStr = "SELECT "
			+ "   I.FileName �ļ���, "
			+ "   I.RCode �����ϸ"
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
      alert( "�ر�ҳ��ʱ�����쳣:" + ex );
    }
   
  } 