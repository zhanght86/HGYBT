var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var arrDataSet;

var turnPage = new turnPageClass();
var mySql = new SqlClass();
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
    alert("在LAComInput.js-->resetForm函数中发生异常:初始化界面错误!");
  }
}
// 查询按钮
function easyQueryClick()
{

    
	// 初始化表格
	initybtPolGrid();
	//mySql = new SqlClass();
	// 书写SQL语句
	var strSQL = "";
	var StartDate = fm.all('StartDate').value;
	var EndDate = fm.all('EndDate').value;
	var BankCode = fm.all('BankCode').value;
	var ContNo = fm.all('ContNo').value;
	

  if((fm.all('StartDate').value==null||fm.all('StartDate').value=="")&& (fm.all('EndDate').value==null||fm.all('EndDate').value=="") && (fm.all('ContNo').value==null||fm.all('ContNo').value=="") )
  {
        alert("请输入一项必要查询条件!");
		    return;
  }
  
  /*
  if (fm.all('BankCode').value!=null && fm.all('BankCode').value!="" )
  {  
  
      if(fm.all('ContNo').value != null && fm.all('ContNo').value != '')
 	     
	    {
		    alert("您选择的是多条件查询,请不要输入保单号!");
	 	    return;
	    } 
      if (fm.all('StartDate').value == null || fm.all('StartDate').value == "" ||fm.all('EndDate').value == null || fm.all('EndDate').value == "")
      { 	    
	    	alert("开始和结束日期不能为空!");
		    return;
	   }  
	   
 	
      
   }
   */
   
   if ((fm.all('StartDate').value != null && fm.all('StartDate').value != "")||(fm.all('EndDate').value != null && fm.all('EndDate').value != ""))
     {
       if(fm.all('ContNo').value != null && fm.all('ContNo').value != '')
 	     
	      {
		    alert("您选择的是多条件查询,请不要输入保单号!");
	 	    return;
	      }
    }
    
   if((fm.all('StartDate').value == null || fm.all('StartDate').value == "" ||fm.all('EndDate').value == null || fm.all('EndDate').value == "")&&(fm.all('ContNo').value == null || fm.all('ContNo').value == ""))
	     {
		    alert("开始和结束日期不能为空!");
	 	    return;
	     }

 

	var mStartDate = date10to8(fm.StartDate.value);
	var mEndDate = date10to8(fm.EndDate.value);
	  strSQL= "  select c.contno ,c.ProposalPrtNo ,c.AppntName ,"
          +" c.InsuredName ,c.ProductId ,"
          +" c.amnt/100  ,c.prem/100  , "
          +" case when c.State = '0' then '录单' "
          +" when c.State = '2' then '签单' "
          +" when c.State = '3' then '撤单' else '' end, "
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
   
   //导出按钮 生成EXCEL表格
function NCExecel()
{
	if (turnPage.queryAllRecordCount <= 0)
	{
		alert('查询结果为空');
		return ;
	}
  fm.action="./YBTContInfoQuerySave.jsp?strSQL=" + mySql.getString();  
  fm.submit(); //提交
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

//提交前的校验
function beforeSubmit() {
	if (!verifyData()) {
		return false;
	}
	return true;
}

//提交前的校验
function verifyData() {
	
	return true;
}
function fillForm() {
	if(turnPage.queryAllRecordCount <= 0) {
		alert("请先查询！");
		return false;
	}
   
   var mCurRowNo = checkedRowNo("QueryGridSel");

	if (-1 == mCurRowNo) {
		alert("请选中一条记录！");
		return false;
	}
	 
	var mRowArr = QueryGrid.getRowData(mCurRowNo);

	if (null == mRowArr) {
		alert("该数据项为空！");
		return false;
	}
	             
    var vContNo = mRowArr[0]; 
	
	var mSqlStr = "select count(1) from LCCONT C where C.CONTNO = '"+vContNo + "'";
		var mComInfos = easyExecSql(mSqlStr); 
		if(mComInfos == '0'){
			alert("查询数据异常!");
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