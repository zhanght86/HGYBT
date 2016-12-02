var showInfo;
var turnPage = new turnPageClass();
//提交前的校验、计算

function creatClick(){
	var mPolicyNo = trim(fm.PolicyNo.value);
	var mPremTypeName = trim(fm.PremTypeName.value);
	var mProductType = fm.ProductType.value;
	var mTranDay = fm.TranDay.value;
	var mSqlStr = "SELECT  " +
		" p.PolicyNO 保单号, " +
		" p.costAmount 费用金额, " +
		" p.PolicyYear 保单年度, " +
		" p.PremType 保费类型, " +
		" p.PlanCode 险种代码, " +
		" p.PlanName 险种名称, " +
		" p.TranDate 交易日期, " +	
		" date8to10(p.MakeDate) 更新日期 " +
	
		" FROM PolicyTransactionAdjustment p " +
		" WHERE 1=1 ";
	
	if ("" != mPolicyNo) { 
			mSqlStr += " and p.PolicyNo='" + mPolicyNo + "'";
	} 
	if("" != mPremTypeName){
		mSqlStr += " and p.PremType='" + mPremTypeName + "'";
	}
	if ("" != mProductType) {
		mSqlStr += " and p.PlanCode='" + mProductType + "'";
		}
	if("" != mTranDay){
				mSqlStr += " AND  p.TranDate = '"+mTranDay+"'";
	}
	mSqlStr +=" ORDER by p.MakeDate desc ";
	turnPage.queryModal(mSqlStr, QueryGrid, 0, 0);
	if(QueryGrid.mulLineCount==0){
		alert("查询结果为空，请重新选择！");
  	  }
}



/**
 * 提交后操作, 服务器数据返回后执行的操作
 */
function afterSubmit(FlagStr,Content)
{
	showInfo.close();
	
    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + Content;
        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    }
    else
    {
    	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + Content;
        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
        easyQueryClick();
    
    }
   
}

function resetItems() {

	initQueryGrid();
	initForm();
	
	}


function showPremTypeCode() {
	fm.PremTypeCode.value="";
	fm.PremTypeName.value="";
	
	showCodeList('c_premtype',[fm.PremTypeCode,fm.PremTypeName],[0,1],null,null,null,1,null,1);
} 

function showPremTypeCodeKey(){
	fm.PremTypeCode.value="";
	fm.PremTypeName.value="";
	showCodeListKey('c_premtype',[fm.PremTypeCode,fm.PremTypeName],[0,1],null,null,null,1,null,1);
}

function showProTypeCode() {
	fm.ProductType.value="";
	fm.ProductName.value="";
	
	showCodeList('citi_procode',[fm.ProductType,fm.ProductName],[0,1],null,null,null,1,null,1);
} 

function showProTypeCodeKey(){
	fm.ProductType.value="";
	fm.ProductName.value="";
	showCodeListKey('citi_procode',[fm.ProductType,fm.ProductName],[0,1],null,null,null,1,null,1);
}