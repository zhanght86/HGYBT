
var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var arrDataSet;

  var turnPage = new turnPageClass();

//显示div，第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
function showDiv(cDiv,cShow)
{
  if (cShow=="true")
  {
    cDiv.style.display="";
  }
  else
  {
    cDiv.style.display="none";  
  }
}

//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,0,0,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
	
	parent.fraMain.rows = "0,0,0,0,*";
}
// 查询按钮
function easyQueryClick()
{
    
	// 初始化表格
	initybtPolGrid();
	
	// 书写SQL语句
	var strSQL = "";
	var StartTransDate = fm.all('StartTransDate').value ;
	var EndTransDate = fm.all('EndTransDate').value;
	var managecom = '86'; //fm.ManageCom.value;
	var BankCode = fm.BankCode.value;
	var ZoneNo = fm.ZoneNo.value;
	var BankNode = fm.BankNode.value;
	if (BankCode == null || BankCode == "")
	{
		alert("请输入银行代码");
		return;
	}
	
	if (StartTransDate == null || StartTransDate == "" || 
	   EndTransDate == null || EndTransDate == "")
	{
	   alert("请选择对帐查询日期");
	   return;
	}
  	
  if (fm.all('StartTransDate').value > fm.all('EndTransDate').value)
  {
  	 alert("查询终止日期不能在查询起始日期之前！");  	
  	 return false;
  }
  
 
  mySql = new SqlClass();
  var mStartTransDate = date10to8(fm.StartTransDate.value);
	var mEndTransDate = date10to8(fm.EndTransDate.value);	
  mySql.setJspName("../../banlance/ybtBalanceQuerySQL.jsp"); //通过新的SQL方法查询
    	
  mySql.setSqlId("easyQuery1");
  mySql.addPara("managecom", managecom);
  mySql.addPara("BankCode", BankCode);
  mySql.getWherePart("a.bankcode", "BankCode");
	mySql.getWherePart("a.bankbranch", "ZoneNo");
	mySql.getWherePart("a.banknode", "BankNode");
	mySql.addPara("StartDate", mStartTransDate);
	mySql.addPara("EndDate", mEndTransDate);  	
	strSQL = mySql.getString();  
	turnPage.queryModal(strSQL, ybtPolGrid);
	 
} 
function getstrG()
{
	// length 在Sqlserver2000用不了
	//str="1  And length(trim(code))=4 and code like #"+comcode+"%%#";
	str="1  and code like #"+comcode+"%%#";
}
    
function getstr1()
{
	// 取全部
  // str1 ="1 and #1#=#1# and len(rtrim(comcode))= 4";
  str1 ="1 and #1#=#1# ";
}