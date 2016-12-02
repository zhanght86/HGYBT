<%
//程序名称：menuInit.jsp
//程序功能：
//创建日期：2002-10-10
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>
<script language="JavaScript">
function initInpBox()
{
	try
	{
		fm.all("UserCode").value = "";
	}
	catch(ex)
	{
		alert("在menuGrpInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
	}
}

function initForm()
{
	try
	{
		initInpBox();
		initQueryGrpGrid();
	}
	catch(re)
	{
		alert("menuGrpInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
	}
}

function initQueryGrpGrid()
{
	var iArray = new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=10;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="操作员编码";
		iArray[1][1]="100px";
		iArray[1][2]=10;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="菜单组编码";
		iArray[2][1]="120px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="菜单分组名称";
		iArray[3][1]="120px";
		iArray[3][2]=100;
		iArray[3][3]=0;


		iArray[4]=new Array();
		iArray[4][0]="菜单组描述";
		iArray[4][1]="200px";
		iArray[4][2]=100;
		iArray[4][3]=0;


		QueryGrpGrid = new MulLineEnter( "fm" , "QueryGrpGrid" );
		QueryGrpGrid.mulLineCount = 10;
		QueryGrpGrid.displayTitle = 1;
		QueryGrpGrid.canChk =0;
		QueryGrpGrid.canSel =1;
		QueryGrpGrid.locked =1;
		QueryGrpGrid.hiddenPlus=1;
		QueryGrpGrid.hiddenSubtraction=1;
		QueryGrpGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}
</script>