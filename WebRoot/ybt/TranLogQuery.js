var turnPage = new turnPageClass();
function requestItems(){

	if(turnPage.queryAllRecordCount <= 0) {
		alert("请先查询！");
		return false;
	}
   
   var mCurRowNo = checkedRowNo("QueryGridSel");
	//alert(mCurRowNo);
	if (-1 == mCurRowNo) {
		alert("请选中一条记录！");
		return false;
	}
	 
	var mRowArr = QueryGrid.getRowData(mCurRowNo);

	if (null == mRowArr) {
		alert("该数据项为空！");
		return false;
	}
	//从页面直接获取的日志主皱键
	var vTranLog = mRowArr[9];
	if(vTranLog!=""){
	var winQuery = window.open("TranLogRequest.jsp?TranLog=" + vTranLog +"");
    }else {
    alert("日志号为空,无法查看！");
    return false;
    }
}

function resetItems() {

	fm.TranCom.value='';
	fm.TranComName.value='';
	fm.FuncFlag.value='';
	fm.FuncFlagName.value='';
	fm.RCode.value='';
	fm.RCodeName.value='';
	
	initQueryGrid();
	
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
function queryItems() {
	initQueryGrid();
	 
	var mProposalPrtNo = trim(fm.ProposalPrtNo.value);
	var mContNo = trim(fm.ContNo.value);
	var mStartDay = date10to8(fm.StartDay.value); 
	var mEndDay = date10to8(fm.EndDay.value);
	
	
	var mSqlStr = "select " 
			+ "(select CodeName from LDCode where CodeType='trancom' and Code=a.TranCom), "
			+ "(select CodeName from LDCode where CodeType='ybttranstype' and Code=a.FuncFlag), " 
			+ "a.ContNo, a.ProposalPrtNo, date8to10(a.TranDate)||' '||time6to8(a.TranTime), a.RText , " 
			+	" a.UsedTime"
			+ ",a.NodeNo,a.tranno,a.LogNo from TranLog a where a.funcflag not in('1005','1006','2005','1201','1202','1203','1204','1104','1205') "; 
	
	//alert(mSqlStr);
	if (""!=mProposalPrtNo || ""!=mContNo) {	//精确查询
		if ("" != mProposalPrtNo) {
			mSqlStr += " and a.ProposalPrtNo='" + mProposalPrtNo + "'";
		} 
		if ("" != mContNo) {
			mSqlStr += " and a.ContNo='" + mContNo + "'";
		}
	} else {	//模糊查询 
		if ("" == mStartDay) { 
			alert("开始时间不能为空");
			return false; 
		}
		if ("" == mEndDay) {
			alert("截止时间不能为空");
			return false;
		}
		mSqlStr += " and to_date(a.MakeDate,'yyyyMMdd') between to_date('" + mStartDay + "','yyyyMMdd') and to_date('" + mEndDay + "','yyyyMMdd')";	
	}  
	
	mSqlStr += getWherePart('FuncFlag','FuncFlag');
	mSqlStr += getWherePart('RCode', 'RCode');
	mSqlStr += getWherePart('TranCom','TranCom');
	mSqlStr += " order by a.LogNo desc";
	
	turnPage.queryModal(mSqlStr, QueryGrid, 0, 0);
}


function showAgentCom1() {
	showCodeList('AgentCom',[fm.AgentCom1,fm.AgentCom1Name],[0,1],null,'type=#0# and length(AgentCom)=4',null,1,null,1);
	
	fm.AgentCom2.value = "";
	fm.AgentCom2Name.value = "";
	fm.AgentCom3.value = "";
	fm.AgentCom3Name.value = "";
	fm.AgentCom4.value = "";
	fm.AgentCom4Name.value = "";
}

function showAgentCom2() {
	showCodeList('AgentCom',[fm.AgentCom2,fm.AgentCom2Name],[0,1],null,'AgentCom like #'+fm.AgentCom1.value+'__#',null,1,null,1);
	
	fm.AgentCom3.value = "";
	fm.AgentCom3Name.value = "";
	fm.AgentCom4.value = "";
	fm.AgentCom4Name.value = "";
}

function showAgentCom3() {
	showCodeList('AgentCom',[fm.AgentCom3,fm.AgentCom3Name],[0,1],null,'AgentCom like #'+fm.AgentCom2.value+'__#',null,1,null,1);
	
	fm.AgentCom4.value = "";
	fm.AgentCom4Name.value = "";
}

function showAgentCom4() {
	showCodeList('AgentCom',[fm.AgentCom4,fm.AgentCom4Name],[0,1],null,'AgentCom like #'+fm.AgentCom3.value+'___#',null,1,350);
}