//�ύ�����水ť��Ӧ����
function submitForm()
{

 if(!verifyInput())
  {
  	return false;
  }
	//var mOldTranNo = fm.OldTranNo.value;
	
	//if( mOldTranNo == "") {
	//	alert("������ɽ�����ˮ�ţ�");
	//	return false;
	//} 
	
//	var vSQL = "select contno from cont where funcflag = '01' and rcode = '1' and transno = '"
//			+ mOldTranNo + "'";
			
//	var vResult = easyExecSql(vSQL, 1, 0, 1, 1);
	
//	if( vResult == null ) {
//		alert("δ�ҵ���Ӧ����Ч������");
//		return false;
//	}
	//fm.ContNo.value = vResult[1][0];
	 
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

function cancelTransaction()
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

function  verifyInput(){
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
	
	var vHOAppFormNumber = fm.HOAppFormNumber.value; 
	var vtranFlagCode = fm.tranFlagCode.value;
	var vProviderFormNumber = fm.ProviderFormNumber.value;
	var vOriginalProviderFormNumber = fm.OriginalProviderFormNumber.value;
	//alert(vtranFlagCode);
	if (vHOAppFormNumber.trim() == "" && (vtranFlagCode.trim() == "1015")){    
		alert("���ճ�������ʱͶ����Ų���Ϊ��");
		return false;   
	} 
	if (vProviderFormNumber.trim() == "" && (vtranFlagCode.trim() == "1015")){    
		alert("���ճ�������ʱ��֤�Ų���Ϊ��");
		return false;   
	} 
//	if (vHOAppFormNumber.trim() == "" && (vtranFlagCode.trim() == "1011")){    
//		alert("�����ش���ʱͶ����Ų���Ϊ��");
//		return false;  
//	}  
		if (vProviderFormNumber.trim() == "" && (vtranFlagCode.trim() == "1011")){    
		alert("�����ش���ʱ��֤�Ų���Ϊ��");
		return false;  
	}
			if (vOriginalProviderFormNumber.trim() == "" && (vtranFlagCode.trim() == "1011")){    
		alert("�����ش���ʱԭ��֤�Ų���Ϊ��");
		return false;  
	}
	 
	return true;
}  
