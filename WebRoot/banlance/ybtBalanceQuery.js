
var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var arrDataSet;

  var turnPage = new turnPageClass();

//��ʾdiv����һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
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

//��ʾfrmSubmit��ܣ���������
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
// ��ѯ��ť
function easyQueryClick()
{
    
	// ��ʼ�����
	initybtPolGrid();
	
	// ��дSQL���
	var strSQL = "";
	var StartTransDate = fm.all('StartTransDate').value ;
	var EndTransDate = fm.all('EndTransDate').value;
	var managecom = '86'; //fm.ManageCom.value;
	var BankCode = fm.BankCode.value;
	var ZoneNo = fm.ZoneNo.value;
	var BankNode = fm.BankNode.value;
	if (BankCode == null || BankCode == "")
	{
		alert("���������д���");
		return;
	}
	
	if (StartTransDate == null || StartTransDate == "" || 
	   EndTransDate == null || EndTransDate == "")
	{
	   alert("��ѡ����ʲ�ѯ����");
	   return;
	}
  	
  if (fm.all('StartTransDate').value > fm.all('EndTransDate').value)
  {
  	 alert("��ѯ��ֹ���ڲ����ڲ�ѯ��ʼ����֮ǰ��");  	
  	 return false;
  }
  
 
  mySql = new SqlClass();
  var mStartTransDate = date10to8(fm.StartTransDate.value);
	var mEndTransDate = date10to8(fm.EndTransDate.value);	
  mySql.setJspName("../../banlance/ybtBalanceQuerySQL.jsp"); //ͨ���µ�SQL������ѯ
    	
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
	// length ��Sqlserver2000�ò���
	//str="1  And length(trim(code))=4 and code like #"+comcode+"%%#";
	str="1  and code like #"+comcode+"%%#";
}
    
function getstr1()
{
	// ȡȫ��
  // str1 ="1 and #1#=#1# and len(rtrim(comcode))= 4";
  str1 ="1 and #1#=#1# ";
}