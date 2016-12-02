<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.utility.ExeSQL"%>
<%@page import="com.sinosoft.lis.db.IFLogDB"%>
<%@page import="com.sinosoft.lis.db.IFdetailDB"%>

<%
    String sLogNo = request.getParameter("LogNo");
    IFLogDB mIFLogDB=new IFLogDB();
    mIFLogDB.setLogNo(sLogNo);
    mIFLogDB.getInfo();
    String FileName = mIFLogDB.getFileName();
    String TranDate = mIFLogDB.getTranDate();
    String DealCout = mIFLogDB.getBak1();
    String FailCout = mIFLogDB.getBak2();
    String MakeDate = mIFLogDB.getMakeDate();
   
%>
<script language="javascript">



function initForm() {
	initGrid();
	fm.IFLog.value="<%=sLogNo%>";
	fm.FileName.value="<%=FileName%>";
	fm.TranDate.value="<%=TranDate%>";
	fm.DealCout.value="<%=DealCout%>";
	fm.FailCout.value="<%=FailCout%>";
	fm.MakeDate.value="<%=MakeDate%>";
	queryItems();
}


function initGrid() {
	var mArray = new Array();
	

	try {
		mArray[0] = new Array();
		mArray[0][0] = "序号"; //列名（此列为顺序号，列名无意义，而且不显示）
		mArray[0][1] = "30px"; //列宽
		mArray[0][2] = 10; //列最大值
		mArray[0][3] = 0; //是否允许输入,1表示允许，0表示不允许

		mArray[1] = new Array();
		mArray[1][0] = "文件名";
		mArray[1][1] = "60px";
		mArray[1][2] = 50;
		mArray[1][3] = 0;

		mArray[2] = new Array();
		mArray[2][0] = "结果明细";
		mArray[2][1] = "130px";
		mArray[2][2] = 130;
		mArray[2][3] = 0;

		

		QueryGrid = new MulLineEnter("fm", "QueryGrid");
		QueryGrid.displayTitle = 1;
		QueryGrid.locked = 0;
		QueryGrid.mulLineCount = 0;
		QueryGrid.hiddenPlus = 1;
		QueryGrid.hiddenSubtraction = 1;
		QueryGrid.canSel = 0;
		QueryGrid.loadMulLine(mArray);
	} catch (ex) {
		alert(ex);
	}
}
</script>
