
function WTranFlag(c){
	//var sPolNumber = fm.all('PolNumber').value;
	var stranFlagCode = fm.all('tranFlagCode').value;
	if(stranFlagCode == '03'){
		document.getElementById('div1').style.display="none";
		document.getElementById('div11').style.display="none";
		document.getElementById('div2').style.display="";
		document.getElementById('div22').style.display="";
		document.getElementById('div3').style.display="";
		document.getElementById('div33').style.display="";
		fm.tranFlagCode.value = '03';
	}
	if(stranFlagCode == '1011'){
		//var vSQL = "select bak2 from cont where state = '2' and contno = '"+ sPolNumber + "'";
		//var vResult = easyExecSql(vSQL, 1, 0, 1, 1);
		document.getElementById('div1').style.display="";
		document.getElementById('div2').style.display="none";
		document.getElementById('div11').style.display="";
		document.getElementById('div22').style.display="none";
		 //document.getElementById('APPDOCNOId').disabled=true;
		//document.getElementById('IdProviderFormNumber').disabled=false;
		fm.tranFlagCode.value = '1011';
	}
	if(stranFlagCode == '04'){
	document.getElementById('div2').style.display="none";
	document.getElementById('div3').style.display="none";
	document.getElementById('div33').style.display="none";
	document.getElementById('div1').style.display="none";
	document.getElementById('div22').style.display="none";
	document.getElementById('div11').style.display="none";
	 //document.getElementById('IdProviderFormNumber').disabled=true;
		//document.getElementById('APPDOCNOId').disabled=false;
		fm.tranFlagCode.value = '04';
	}
}

//�ύ�����水ť��Ӧ����
function submitForm()
{

 if(!verifyInputForm())
  {
  	return false;
  }
var sTranFlagCode = fm.all('tranFlagCode').value;
	
	fm.action = "./ABCWriteOffSave.jsp";
	
 
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  
  fm.submit(); //�ύ
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���




function afterSubmit( FlagStr, content,strContNo,strCValiDate,strPayMode,strAppntName,strAppntSex,strAppntNo,strAppntBirthday,strInsuredName,strInsuredNo,strSex,strBirthday,strBnfLot,strName,strBnfGrade,strRiskName,strDuration,strPaymentDuration,strInitCovAmt,strPrem,strRiskName1,strDuration1,strPaymentDuration1,strInitCovAmt1,strPrem1,strRiskName2,strDuration2,strPaymentDuration2,strInitCovAmt2,strPrem2)
{ 	
	  showInfo.close();
	  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
	  showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	 if(FlagStr==1)
	 {
	 // alert("Ͷ���ˣ���������Ϣ");
	//  alert(strContNo);
	//  alert(strCValiDate);
	//  alert(strPayMode);
	//  alert(strAppntName);
	 // alert(strAppntSex);
	 // alert(strAppntNo);
	//  alert(strAppntBirthday);
	//  alert(strInsuredName);
	 // alert(strInsuredNo);
	 // alert(strSex);
	//  alert(strBirthday);
	//  alert(strBnfLot);
	//  alert(strName);
	 // alert(strBnfGrade);
	  
	 // alert("������Ϣ"); 
	 // alert(strRiskName);
	//  alert(strDuration);
	 // alert(strPaymentDuration);
	//  alert(strInitCovAmt);
	 // alert(strPrem);
	  
	//  alert("������1��Ϣ");
	//  alert(strRiskName1); 
	//  alert(strDuration1);
	//  alert(strPaymentDuration1);
	//  alert(strInitCovAmt1);
	//  alert(strPrem1);
	  
	//  alert("������2��Ϣ");
	//  alert(strRiskName2);
	 // alert(strDuration2);
	//  alert(strPaymentDuration2);
	 // alert(strInitCovAmt2);
	  //alert(strPrem2);
	  
	 // alert("����");
	//  window.open("./showpolinfoInput.jsp?mContNo="+strContNo+"&mCValiDate="+strCValiDate+"&mPayMode="+strPayMode+"&mAppntName="+strAppntName+"&mAppntSex="+strAppntSex+"&mAppntNo="+strAppntNo+"&mAppntBirthday="+strAppntBirthday+"&mInsuredName="+strInsuredName+"&mInsuredNo="+strInsuredNo+"&mSex="+strSex+"&mBirthday="+strBirthday+"&mBnfLot="+strBnfLot+"&mName="+strName+"&mBnfGrade="+strBnfGrade+"&mRiskName="+strRiskName+"&mDuration="+strDuration+"&mPaymentDuration="+strPaymentDuration+"&mInitCovAmt="+strInitCovAmt+"&mPrem="+strPrem+"&mRiskName1="+strRiskName1+"&mDuration1="+strDuration1+"&mPaymentDuration1="+strPaymentDuration1+"&mInitCovAmt1="+strInitCovAmt1+"&mPrem1="+strPrem1+"&mRiskName2="+strRiskName2+"&mDuration2="+strDuration2+"&mPaymentDuration2="+strPaymentDuration2+"&mInitCovAmt2="+strInitCovAmt2+"&mPrem2="+strPrem2+"");
	}
}

function confirmTransaction()
{
	var vOldAction = fm.action;
	
	fm.action = "./TestYBTConfirmSave.jsp";
	fm.ConfirmFlag.value = "1";
	fm.submit();	
	fm.action = vOldAction;
}


function cancleTransaction()
{
	var vOldAction = fm.action;
	
	fm.action = "./TestYBTCancleSave.jsp";
	fm.ConfirmFlag.value = "0";
	fm.submit();	
	fm.action = vOldAction;
}

function reprintCont()
{
	var vOldAction = fm.action;
	
	fm.action = "./TestYBTRePrtSave.jsp";
	fm.submit();

	fm.action = vOldAction;		
}


function queryPrtNo()
{
	var vBankCode = fm.BankCode.value;
	var vZoneNo = fm.ZoneNo.value;
	var vBrNo = fm.BrNo.value;
	
	if( vBankCode == "" || vZoneNo == "" || vBrNo == "" ) {
		alert("����������������Ϣ��");
		return false;
	}
	
	var vSQL = "SELECT AgentCom FROM LKCodeMapping WHERE BankCode = '" + vBankCode
			+ "' AND ZoneNo = '" + vZoneNo
			+ "' AND BankNode = '" + vBrNo + "'";
			
	var vResult = easyExecSql(vSQL, 1, 0, 1, 1);
	
	if( vResult == null ) {
		alert("������������Ϣ�����ڣ�");
		return false;
	}
	
	// ��ѯ����Ӧ�ĵ�֤���ջ���
	var vAgentCom = "F" + vResult[1][0];
	
	// ע��SQL����д������֤�п���ֱ�ӷ��ŵ������ϣ�Ҳ�п���ֻ���ŵ��ϼ������ϡ����ԣ��˴���LIKE������
	vSQL = "SELECT MIN(StartNO) FROM LZCard WHERE CertifyCode IN ('109001') AND '" + vAgentCom + "' LIKE ReceiveCom || '%%'";
	
	vResult = easyExecSql(vSQL, 1, 0, 1, 1);
	
	if( vResult == null ) {
		alert("û�в�ѯ�������������ӡˢ�ţ���ֱ���ں�̨��ѯ��");
		return false;
	}
	
	// ����ѯ�õ��ı���ӡˢ�Ÿ�ֵ����Ӧ�������
	fm.PrtNo.value = vResult[1][0];
	return true;
}

function  verifyInputForm(){
	var vIp = fm.ip.value; 
	if (vIp.trim() == ""){
		alert("���ձ���ip��ַ����Ϊ��");
		return false;
	}
	
	var vPort = fm.port.value; 
	if (vPort.trim() == ""){    
		alert("���ձ��Ķ˿ڲ���Ϊ��");
		return false; 
	}
	 
	var vTransExeDate = fm.TransExeDate.value; 
	if (vTransExeDate.trim() == ""){    
		alert("���ж˽������ڲ���Ϊ��");
		return false; 
	}   
	  
	var vTransRefGUID = fm.TransRefGUID.value; 
	if (vTransRefGUID.trim() == ""){    
		alert("������ˮ�Ų���Ϊ��");
		return false; 
	}   
	
	var vRegionCode = fm.RegionCode.value; 
	if (vRegionCode.trim() == ""){    
		alert("�������벻��Ϊ��");
		return false; 
	} 
	
	var vBranch = fm.Branch.value; 
	if (vBranch.trim() == ""){    
		alert("������벻��Ϊ��");
		return false; 
	} 
	
	var vtranFlagCode = fm.tranFlagCode.value; 
	if (vtranFlagCode.trim() == ""){    
		alert("�����벻��Ϊ��");
		return false; 
	} 
	
	var vTeller = fm.Teller.value; 
	if (vTeller.trim() == ""){    
		alert("��Ա���벻��Ϊ��");
		return false; 
	}  
	
	var vPolNumber = fm.PolNumber.value; 
	if (vPolNumber.trim() == ""){    
		alert("�����Ų���Ϊ��");
		return false; 
	} 
	
	var stranFlagCode = fm.all('tranFlagCode').value;
	var vAPPDOCNO = fm.APPDOCNO.value; 
	if(stranFlagCode == '03'){
		if (vAPPDOCNO.trim() == ""){    
			alert("Ͷ�����Ų���Ϊ��");
			return false; 
		} 
	}
	
	//var vPolNumber = fm.PolNumber.value; 
	//var vtranFlagCode = fm.tranFlagCode.value;
	//var vProviderFormNumber = fm.ProviderFormNumber.value;
	//var vAPPDOCNO = fm.APPDOCNO.value;
	//var vOriginalProviderFormNumber = fm.OriginalProviderFormNumber.value;
	

	//	if (vProviderFormNumber.trim() == "" && (vtranFlagCode.trim() == "1011")){    
	//	alert("�����ش���ʱ��֤�Ų���Ϊ��");
	//	return false;
	//}
	//	 if (vAPPDOCNO.trim() == "" && (vtranFlagCode.trim() == "1012")){    
	//	alert("�����Զ���������ʱͶ�����Ų���Ϊ��");
	//	return false;  
	//}
	 
	return true;
}  
