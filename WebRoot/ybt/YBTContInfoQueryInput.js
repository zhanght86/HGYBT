var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var arrDataSet;

var turnPage = new turnPageClass();
var mySql = new SqlClass();
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
function resetForm()
{
  try
  {
    //showDiv(operateButton,"true");
    //showDiv(inputButton,"false");
    initForm();
  }
  catch(re)
  {
    alert("��LAComInput.js-->resetForm�����з����쳣:��ʼ���������!");
  }
}
// ��ѯ��ť
function easyQueryClick()
{

    
	// ��ʼ�����
	initybtPolGrid();
	//mySql = new SqlClass();
	// ��дSQL���
	var strSQL = "";
	var StartDate = fm.all('StartDate').value;
	var EndDate = fm.all('EndDate').value;
	var BankCode = fm.all('BankCode').value;
	var ContNo = fm.all('ContNo').value;
	

  if((fm.all('StartDate').value==null||fm.all('StartDate').value=="")&& (fm.all('EndDate').value==null||fm.all('EndDate').value=="") && (fm.all('ContNo').value==null||fm.all('ContNo').value=="") )
  {
        alert("������һ���Ҫ��ѯ����!");
		    return;
  }
  
  /*
  if (fm.all('BankCode').value!=null && fm.all('BankCode').value!="" )
  {  
  
      if(fm.all('ContNo').value != null && fm.all('ContNo').value != '')
 	     
	    {
		    alert("��ѡ����Ƕ�������ѯ,�벻Ҫ���뱣����!");
	 	    return;
	    } 
      if (fm.all('StartDate').value == null || fm.all('StartDate').value == "" ||fm.all('EndDate').value == null || fm.all('EndDate').value == "")
      { 	    
	    	alert("��ʼ�ͽ������ڲ���Ϊ��!");
		    return;
	   }  
	   
 	
      
   }
   */
   
   if ((fm.all('StartDate').value != null && fm.all('StartDate').value != "")||(fm.all('EndDate').value != null && fm.all('EndDate').value != ""))
     {
       if(fm.all('ContNo').value != null && fm.all('ContNo').value != '')
 	     
	      {
		    alert("��ѡ����Ƕ�������ѯ,�벻Ҫ���뱣����!");
	 	    return;
	      }
    }
    
   if((fm.all('StartDate').value == null || fm.all('StartDate').value == "" ||fm.all('EndDate').value == null || fm.all('EndDate').value == "")&&(fm.all('ContNo').value == null || fm.all('ContNo').value == ""))
	     {
		    alert("��ʼ�ͽ������ڲ���Ϊ��!");
	 	    return;
	     }

 

	var mStartDate = date10to8(fm.StartDate.value);
	var mEndDate = date10to8(fm.EndDate.value);
	  strSQL= "  select c.contno ,c.ProposalPrtNo ,c.AppntName ,"
          +" c.InsuredName ,c.ProductId ,"
          +" c.amnt/100  ,c.prem/100  , "
          +" case when c.State = '0' then '¼��' "
          +" when c.State = '2' then 'ǩ��' "
          +" when c.State = '3' then '����' else '' end, "
          +" date8to10(c.SignDate)  ,(select CodeName from LDCode where CodeType='trancom_bank' and Code=c.trancom) ,c.nodeno  ,(select agentcomname from nodemap where nodeno = c.nodeno and agentcom=c.agentcom), c.bak2"
          +" from cont c " 
          +" where 1=1 and c.state in ('0','2','3') "
       //   +" and trans.funcflag = 'CB002'"
//	      +" and c.SignDate between '?StartDate?' and '?EndDate?' ";
	mySql.setJspName("../../query/YBTContInfoQueryInputSQL.jsp");

	if(StartDate != null && StartDate != "" && EndDate != null && EndDate != "" )
	{
		 
     
	
		  
	  strSQL += " and c.SignDate between " + mStartDate + " and " + mEndDate;
	  strSQL+= getWherePart('c.TranCom','BankCode')
	  strSQL+=' order by c.SignDate desc  ';
  	
	 
  }
  // modify by pangmingshi for add the query contents on 2008-11-07
  else 
  {
  	
	
  	strSQL+= getWherePart('c.ContNo','ContNo')
   strSQL+=' order by c.SignDate desc  ';

  	
  	
  } 
 
  turnPage.queryModal(strSQL, ybtPolGrid1);
  
}
   
   //������ť ����EXCEL���
function NCExecel()
{
	if (turnPage.queryAllRecordCount <= 0)
	{
		alert('��ѯ���Ϊ��');
		return ;
	}
  fm.action="./YBTContInfoQuerySave.jsp?strSQL=" + mySql.getString();  
  fm.submit(); //�ύ
}
function queryDetail() {
	var ContNo =  fillForm();
	
	if(ContNo == false){
		return false;
	}
	if (ContNo != false && ContNo != ""){
		if (!beforeSubmit()) {
			return false;
	}
	var winQuery = window.open("ContDetial.jsp?ContNo=" + ContNo+"");
	}
}

//�ύǰ��У��
function beforeSubmit() {
	if (!verifyData()) {
		return false;
	}
	return true;
}

//�ύǰ��У��
function verifyData() {
	
	return true;
}
function fillForm() {
	if(turnPage.queryAllRecordCount <= 0) {
		alert("���Ȳ�ѯ��");
		return false;
	}
   
   var mCurRowNo = checkedRowNo("QueryGridSel");

	if (-1 == mCurRowNo) {
		alert("��ѡ��һ����¼��");
		return false;
	}
	 
	var mRowArr = QueryGrid.getRowData(mCurRowNo);

	if (null == mRowArr) {
		alert("��������Ϊ�գ�");
		return false;
	}
	             
    var vContNo = mRowArr[0]; 
	
	var mSqlStr = "select count(1) from LCCONT C where C.CONTNO = '"+vContNo + "'";
		var mComInfos = easyExecSql(mSqlStr); 
		if(mComInfos == '0'){
			alert("��ѯ�����쳣!");
			return false;
		}
	return vContNo;
}
function checkedRowNo(name) {
	
	var obj = document.all[name];

	if ("undefined" == typeof(obj)) {
		return -1;
	} else if ("undefined" == typeof(obj.length)) {
		return 0;
	}
	
	for (i = 0; i < obj.length; i++) {
		if(obj[i].checked) {
			return i; 
		}
	} 
	
	return -1;
}