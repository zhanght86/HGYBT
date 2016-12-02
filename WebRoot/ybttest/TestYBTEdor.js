
//提交，保存按钮对应操作
function submitForm()
{
	
	if(!verifyInputForm()){
		return false;
	}
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   

  fm.submit(); //提交
}

//提交后操作,服务器数据返回后执行的操作




function afterSubmit(ResultCode,ResultInfoDesc)
{ 	
	  showInfo.close(); 
	  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" +ResultInfoDesc;   
	  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");	  		  
}  


function  verifyInputForm(){
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
	 
	var vTransExeDate = fm.TransExeDate.value; 
	if (vTransExeDate.trim() == ""){    
		alert("银行端交易日期不能为空");
		return false; 
	}   
	  
	var vTransRefGUID = fm.TransNo.value; 
	if (vTransRefGUID.trim() == ""){    
		alert("交易流水号不能为空");
		return false; 
	}   
	
	var vRegionCode = fm.ZoneNo.value; 
	if (vRegionCode.trim() == ""){    
		alert("地区代码不能为空");
		return false; 
	} 
	
	var vBranch = fm.NodeNo.value; 
	if (vBranch.trim() == ""){    
		alert("网点代码不能为空");
		return false; 
	} 
	
	var vtranFlagCode = fm.TranType.value; 
	if (vtranFlagCode.trim() == ""){    
		alert("交易码不能为空");
		return false; 
	} 
	
		var vPolNumber = fm.PolNumber.value; 
	if (vPolNumber.trim() == ""){    
		alert("保单号不能为空");
		return false; 
	} 

	 
	return true;
}  




function TranFlag(transno){

	 if("1004"==transno){
		 insureInfoTitle.style.display="none";
		 insurenameTitle.style.display="none";
		 insurenameInput.style.display="none";
		 insureIDTypeTitle.style.display="none";
		 insureIDTypeInput.style.display="none";
		 insureIDTitle.style.display="none";
		 insureIDInput.style.display="none";
		 AccountNameTitle.style.display="";
		 AccountName.style.display="";
		 appntInfoTitle.style.display="";
		 appntnameTitle.style.display="";
		 appntnameInput.style.display="";
		 appntIDTypeTitle.style.display="";
		 appntIDTypeInput.style.display="";
		 appntIDTitle.style.display="";
		 appntIDInput.style.display="";
		 CortransrnoName.style.display="none";
		 Cortransrno.style.display="none";
	 
	 }	 if("1003"==transno){
		 insureInfoTitle.style.display="";
		 insurenameTitle.style.display="";
		 insurenameInput.style.display="";
		 insureIDTypeTitle.style.display="";
		 insureIDTypeInput.style.display="";
		 insureIDTitle.style.display="";
		 insureIDInput.style.display="";
		 
		 appntInfoTitle.style.display="";
		 appntnameTitle.style.display="";
		 appntnameInput.style.display="";
		 appntIDTypeTitle.style.display="";
		 appntIDTypeInput.style.display="";
		 appntIDTitle.style.display="";
		 appntIDInput.style.display="";
		 
		 AccountNameTitle.style.display="";
		 AccountName.style.display="";
		 
		 CortransrnoName.style.display="none";
		 Cortransrno.style.display="none";
		 
	 }	 if("1021"==transno){
		 insureInfoTitle.style.display="";
		 insurenameTitle.style.display="";
		 insurenameInput.style.display="";
		 insureIDTypeTitle.style.display="";
		 insureIDTypeInput.style.display="";
		 insureIDTitle.style.display="";
		 insureIDInput.style.display="";
		 
		 appntInfoTitle.style.display="";
		 appntnameTitle.style.display="";
		 appntnameInput.style.display="";
		 appntIDTypeTitle.style.display="";
		 appntIDTypeInput.style.display="";
		 appntIDTitle.style.display="";
		 appntIDInput.style.display="";
		 
		 AccountNameTitle.style.display="";
		 AccountName.style.display="";
		 
		 CortransrnoName.style.display="none";
		 Cortransrno.style.display="none";
		 
	 }	 if("1017"==transno){
		 insureInfoTitle.style.display="none";
		 insurenameTitle.style.display="none";
		 insurenameInput.style.display="none";
		 insureIDTypeTitle.style.display="none";
		 insureIDTypeInput.style.display="none";
		 insureIDTitle.style.display="none";
		 insureIDInput.style.display="none";
		 
		 appntInfoTitle.style.display="";
		 appntnameTitle.style.display="";
		 appntnameInput.style.display="";
		 appntIDTypeTitle.style.display="";
		 appntIDTypeInput.style.display="";
		 appntIDTitle.style.display="";
		 appntIDInput.style.display="";
		 
		 AccountNameTitle.style.display="";
		 AccountName.style.display="";
		 
		 CortransrnoName.style.display="none";
		 Cortransrno.style.display="none";
		 
	 }	 if("1030"==transno){
		 insureInfoTitle.style.display="none";
		 insurenameTitle.style.display="none";
		 insurenameInput.style.display="none";
		 insureIDTypeTitle.style.display="none";
		 insureIDTypeInput.style.display="none";
		 insureIDTitle.style.display="none";
		 insureIDInput.style.display="none";
		 
		 appntInfoTitle.style.display="none";
		 appntnameTitle.style.display="none";
		 appntnameInput.style.display="none";
		 appntIDTypeTitle.style.display="none";
		 appntIDTypeInput.style.display="none";
		 appntIDTitle.style.display="none";
		 appntIDInput.style.display="none";
		 
		 AccountNameTitle.style.display="none";
		 AccountName.style.display="none";
		 CortransrnoName.style.display="";
		 Cortransrno.style.display="";
		 
	 }	 if("1026"==transno){
		 insureInfoTitle.style.display="none";
		 insurenameTitle.style.display="none";
		 insurenameInput.style.display="none";
		 insureIDTypeTitle.style.display="none";
		 insureIDTypeInput.style.display="none";
		 insureIDTitle.style.display="none";
		 insureIDInput.style.display="none";
		 
		 appntInfoTitle.style.display="none";
		 appntnameTitle.style.display="none";
		 appntnameInput.style.display="none";
		 appntIDTypeTitle.style.display="none";
		 appntIDTypeInput.style.display="none";
		 appntIDTitle.style.display="none";
		 appntIDInput.style.display="none";
		 
		 AccountNameTitle.style.display="none";
		 AccountName.style.display="none";
		 
		 CortransrnoName.style.display="";
		 Cortransrno.style.display="";
		 
	 }
	 
  }

