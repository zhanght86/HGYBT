



//提交，保存按钮对应操作
function submitForm()
{
  if(!verifyInput())
  {
	 // alert("false");
  	return false;
  }
  	
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  

  fm.submit(); //提交
}
 
function  verifyInput(){
	var vIp = fm.ip.value; 
	if (vIp.trim() == ""){
		alert("接收报文ip地址不能为空");
		return false;
	}
	
	var vPort = fm.port.value; 
	if (vPort.trim() == ""){    
		alert("接收报文端口不能为空");
		return false; 
	}
	 
	var vTRANSDATE = fm.mSYS_REQ_TIME.value; 
	if (vTRANSDATE.trim() == ""){    
		alert("银行端交易日期不能为空");
		return false; 
	}   
	  
	var vSERIALNO = fm.mSvPt_Jrnl_No.value; 
	if (vSERIALNO.trim() == ""){    
		alert("交易流水号不能为空");
		return false; 
	}   

	var vBRANCHNO = fm.mCCBIns_ID.value; 
	if (vBRANCHNO.trim() == ""){    
		alert("网点代码不能为空");
		return false; 
	} 
	
	var vTLID = fm.mCCB_EmpID.value; 
	if (vTLID.trim() == ""){    
		alert("柜员代码不能为空");
		return false; 
	}  
	
	var vPOLICYAPPLYSERIAL = fm.Enqr_Dt.value; 
	if (vPOLICYAPPLYSERIAL.trim() == ""){    
		alert("查询日期不能为空");
		return false;  
	} 
	

	
	 
	return true;
}  
 
//提交后操作,服务器数据返回后执行的操作

 

function afterSubmit(ResultCode,ResultInfoDesc)
{ 	
	  showInfo.close(); 
	  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" +ResultInfoDesc;   
	  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");	  		  
}  

function queryPrtNo()
{
	var vBankCode = fm.BankCode.value;
	var vZoneNo = fm.ZoneNo.value;
	var vBrNo = fm.BrNo.value;
	
	if( vBankCode == "" || vZoneNo == "" || vBrNo == "" ) {
		alert("请输入银行网点信息！");
		return false;
	}
	
	var vSQL = "SELECT AgentCom FROM LKCodeMapping WHERE BankCode = '" + vBankCode
			+ "' AND ZoneNo = '" + vZoneNo
			+ "' AND BankNode = '" + vBrNo + "'";
			
	var vResult = easyExecSql(vSQL, 1, 0, 1, 1);
	
	if( vResult == null ) {
		alert("该银行网点信息不存在！");
		return false;
	}
	
	// 查询到对应的单证接收机构
	var vAgentCom = "F" + vResult[1][0];
	
	// 注意SQL语句的写法，单证有可能直接发放到网点上，也有可能只发放到上级网点上。所以，此处用LIKE操作。
	vSQL = "SELECT MIN(StartNO) FROM LZCard WHERE CertifyCode IN ('109001') AND '" + vAgentCom + "' LIKE ReceiveCom || '%%'";
	
	vResult = easyExecSql(vSQL, 1, 0, 1, 1);
	
	if( vResult == null ) {
		alert("没有查询到该网点关联的印刷号，请直接在后台查询！");
		return false;
	}
	
	// 将查询得到的保单印刷号赋值给对应的输入框
	fm.PrtNo.value = vResult[1][0];
	return true;
}
