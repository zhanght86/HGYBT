function WTranFlag(){
	var sTranFlagCode = fm.all('tranFlagCode').value;
	if(sTranFlagCode == 'P53816141'){
		//���㱣�ղ�Ʒ
		Caculate();
	}
	if(sTranFlagCode == 'P53819142'){
		//���ճ���
		ContConcel();
	}
	else if(sTranFlagCode == 'P53818154'){
		//���ճ���
		ContRollBack();
	}
	else if(sTranFlagCode == 'P53819184'){
		//�����ش�
		ContRePrint();
	}
	else if(sTranFlagCode == 'P53818152'){
		//�̵ƽ���
		GreenTest();
	}else if(sTranFlagCode == 'SPE801'){
		//�˱����״���
		ContCancelBlc();
	}else if(sTranFlagCode == 'P538191A2'){
		//�ؿպ˶�
		ContCheck();
	}else if(sTranFlagCode == 'BAT900'){
		//������ѯ
		BatQuery();
	}else if(sTranFlagCode == 'SPE010'){
		//ǩԼ����
		ContSigal();
	}else if(sTranFlagCode == 'SPE013'){
		//��Լ����
		ContDismiss();
	}
}
//ʧ�㱣�ղ�Ʒ
function Caculate(){
	//����ͷ��Ϣ
	document.getElementById("divTranInfo").style.display="";
	document.getElementById("divTranInfoimg").src="../common/images/butExpand.gif";
	//������Ϣ
	document.getElementById("divContInfo").style.display="none";
	document.getElementById("divContInfoimg").src="../common/images/butCollapse.gif";
	//���㱣�ղ�Ʒ
	document.getElementById("divCaculate").style.display="";
	document.getElementById("divCaculateimg").src="../common/images/butCollapse.gif";
	//�̵ƽ�����Ϣ
	document.getElementById("divDate").style.display="none";
	document.getElementById("divDateimg").src="../common/images/butCollapse.gif";
	//�ؿպ˶���Ϣ
	document.getElementById("divCheck").style.display="none";
	document.getElementById("divCheckimg").src="../common/images/butCollapse.gif";
	//�˱���Ϣ����
	document.getElementById("divCancelTranfor").style.display="none";
	document.getElementById("divCancelTranforimg").src="../common/images/butCollapse.gif";
	//ǩԼ��Լ����
	document.getElementById("divSignal").style.display="none";
	document.getElementById("divSignalimg").src="../common/images/butCollapse.gif";
	
	document.getElementById("OldTransRefGUID").disabled=true;
	document.getElementById("ProviderFormNumber").disabled=true;
	document.getElementById("PolNumber").disabled=true;
}
//������ѯ
function BatQuery(){
		//����ͷ��Ϣ
	document.getElementById("divTranInfo").style.display="";
	document.getElementById("divTranInfoimg").src="../common/images/butExpand.gif";
	//������Ϣ
	document.getElementById("divContInfo").style.display="none";
	document.getElementById("divContInfoimg").src="../common/images/butCollapse.gif";
	//���㱣�ղ�Ʒ
	document.getElementById("divCaculate").style.display="none";
	document.getElementById("divCaculateimg").src="../common/images/butCollapse.gif";
	//�̵ƽ�����Ϣ
	document.getElementById("divDate").style.display="none";
	document.getElementById("divDateimg").src="../common/images/butCollapse.gif";
	//�ؿպ˶���Ϣ
	document.getElementById("divCheck").style.display="none";
	document.getElementById("divCheckimg").src="../common/images/butCollapse.gif";
	//�˱���Ϣ����
	document.getElementById("divCancelTranfor").style.display="none";
	document.getElementById("divCancelTranforimg").src="../common/images/butCollapse.gif";
	//ǩԼ��Լ����
	document.getElementById("divSignal").style.display="none";
	document.getElementById("divSignalimg").src="../common/images/butCollapse.gif";
	
	document.getElementById("OldTransRefGUID").disabled=true;
	document.getElementById("ProviderFormNumber").disabled=true;
	document.getElementById("PolNumber").disabled=true;
}
//ǩԼ����
function ContSigal(){
		//����ͷ��Ϣ
	document.getElementById("divTranInfo").style.display="";
	document.getElementById("divTranInfoimg").src="../common/images/butExpand.gif";
	//������Ϣ
	document.getElementById("divContInfo").style.display="none";
	document.getElementById("divContInfoimg").src="../common/images/butCollapse.gif";
	//���㱣�ղ�Ʒ
	document.getElementById("divCaculate").style.display="none";
	document.getElementById("divCaculateimg").src="../common/images/butCollapse.gif";
	//�̵ƽ�����Ϣ
	document.getElementById("divDate").style.display="none";
	document.getElementById("divDateimg").src="../common/images/butCollapse.gif";
	//�ؿպ˶���Ϣ
	document.getElementById("divCheck").style.display="none";
	document.getElementById("divCheckimg").src="../common/images/butCollapse.gif";
	//�˱���Ϣ����
	document.getElementById("divCancelTranfor").style.display="none";
	document.getElementById("divCancelTranforimg").src="../common/images/butCollapse.gif";
	//ǩԼ��Լ����
	document.getElementById("divSignal").style.display="";
	document.getElementById("divSignalimg").src="../common/images/butExpand.gif";
	
	document.getElementById("OldTransRefGUID").disabled=true;
	document.getElementById("ProviderFormNumber").disabled=true;
	document.getElementById("PolNumber").disabled=true;
}
//��Լ����
function ContDismiss(){
		//����ͷ��Ϣ
	document.getElementById("divTranInfo").style.display="";
	document.getElementById("divTranInfoimg").src="../common/images/butExpand.gif";
	//������Ϣ
	document.getElementById("divContInfo").style.display="none";
	document.getElementById("divContInfoimg").src="../common/images/butCollapse.gif";
	//���㱣�ղ�Ʒ
	document.getElementById("divCaculate").style.display="none";
	document.getElementById("divCaculateimg").src="../common/images/butCollapse.gif";
	//�̵ƽ�����Ϣ
	document.getElementById("divDate").style.display="none";
	document.getElementById("divDateimg").src="../common/images/butCollapse.gif";
	//�ؿպ˶���Ϣ
	document.getElementById("divCheck").style.display="none";
	document.getElementById("divCheckimg").src="../common/images/butCollapse.gif";
	//�˱���Ϣ����
	document.getElementById("divCancelTranfor").style.display="none";
	document.getElementById("divCancelTranforimg").src="../common/images/butCollapse.gif";
	//ǩԼ��Լ����
	document.getElementById("divSignal").style.display="";
	document.getElementById("divSignalimg").src="../common/images/butExpand.gif";
	
	document.getElementById("OldTransRefGUID").disabled=true;
	document.getElementById("ProviderFormNumber").disabled=true;
	document.getElementById("PolNumber").disabled=true;
}
function ContConcel(){
	//����ͷ��Ϣ
	document.getElementById("divTranInfo").style.display="";
	document.getElementById("divTranInfoimg").src="../common/images/butExpand.gif";
	//������Ϣ
	document.getElementById("divContInfo").style.display="";
	document.getElementById("divContInfoimg").src="../common/images/butExpand.gif";
	//���㱣�ղ�Ʒ
	document.getElementById("divCaculate").style.display="none";
	document.getElementById("divCaculateimg").src="../common/images/butCollapse.gif";
	//�̵ƽ�����Ϣ
	document.getElementById("divDate").style.display="none";
	document.getElementById("divDateimg").src="../common/images/butCollapse.gif";
	//�ؿպ˶���Ϣ
	document.getElementById("divCheck").style.display="none";
	document.getElementById("divCheckimg").src="../common/images/butCollapse.gif";
	//�˱���Ϣ����
	document.getElementById("divCancelTranfor").style.display="none";
	document.getElementById("divCancelTranforimg").src="../common/images/butCollapse.gif";
	//ǩԼ��Լ����
	document.getElementById("divSignal").style.display="none";
	document.getElementById("divSignalimg").src="../common/images/butCollapse.gif";
	
	document.getElementById("OldTransRefGUID").disabled=false;
	document.getElementById("ProviderFormNumber").disabled=true;
	//document.getElementById("OriginalProviderFormNumber").disabled=true;
	document.getElementById("PolNumber").disabled=false;
	//document.getElementById("HOAppFormNumber").disabled=true;
}
//�̵Ʋ���
function GreenTest(){
		//����ͷ��Ϣ
	document.getElementById("divTranInfo").style.display="";
	document.getElementById("divTranInfoimg").src="../common/images/butExpand.gif";
	//������Ϣ
	document.getElementById("divContInfo").style.display="none";
	document.getElementById("divContInfoimg").src="../common/images/butCollapse.gif";
	//���㱣�ղ�Ʒ
	document.getElementById("divCaculate").style.display="none";
	document.getElementById("divCaculateimg").src="../common/images/butCollapse.gif";
	//�̵ƽ�����Ϣ
	document.getElementById("divDate").style.display="";
	document.getElementById("divDateimg").src="../common/images/butExpand.gif";
	//�ؿպ˶���Ϣ
	document.getElementById("divCheck").style.display="none";
	document.getElementById("divCheckimg").src="../common/images/butCollapse.gif";
	//�˱���Ϣ����
	document.getElementById("divCancelTranfor").style.display="none";
	document.getElementById("divCancelTranforimg").src="../common/images/butCollapse.gif";
	//ǩԼ��Լ����
	document.getElementById("divSignal").style.display="none";
	document.getElementById("divSignalimg").src="../common/images/butCollapse.gif";
	document.getElementById("OldTransRefGUID").disabled=true;
	document.getElementById("ProviderFormNumber").disabled=true;
	document.getElementById("PolNumber").disabled=true;
}
//�˱���Ϣ����
function ContCancelBlc(){
		//����ͷ��Ϣ
	document.getElementById("divTranInfo").style.display="";
	document.getElementById("divTranInfoimg").src="../common/images/butExpand.gif";
	//������Ϣ
	document.getElementById("divContInfo").style.display="none";
	document.getElementById("divContInfoimg").src="../common/images/butCollapse.gif";
	//���㱣�ղ�Ʒ
	document.getElementById("divCaculate").style.display="none";
	document.getElementById("divCaculateimg").src="../common/images/butCollapse.gif";
	//�̵ƽ�����Ϣ
	document.getElementById("divDate").style.display="none";
	document.getElementById("divDateimg").src="../common/images/butCollapse.gif";
	//�ؿպ˶���Ϣ
	document.getElementById("divCheck").style.display="none";
	document.getElementById("divCheckimg").src="../common/images/butCollapse.gif";
	//�˱���Ϣ����
	document.getElementById("divCancelTranfor").style.display="";
	document.getElementById("divCancelTranforimg").src="../common/images/butExpand.gif";
	//ǩԼ��Լ����
	document.getElementById("divSignal").style.display="none";
	document.getElementById("divSignalimg").src="../common/images/butCollapse.gif";
	document.getElementById("OldTransRefGUID").disabled=true;
	document.getElementById("ProviderFormNumber").disabled=true;
	document.getElementById("PolNumber").disabled=true;
}
//�ؿպ˶�
function ContCheck(){
		//����ͷ��Ϣ
	document.getElementById("divTranInfo").style.display="";
	document.getElementById("divTranInfoimg").src="../common/images/butExpand.gif";
	//������Ϣ
	document.getElementById("divContInfo").style.display="none";
	document.getElementById("divContInfoimg").src="../common/images/butCollapse.gif";
	//���㱣�ղ�Ʒ
	document.getElementById("divCaculate").style.display="none";
	document.getElementById("divCaculateimg").src="../common/images/butCollapse.gif";
	//�̵ƽ�����Ϣ
	document.getElementById("divDate").style.display="none";
	document.getElementById("divDateimg").src="../common/images/butCollapse.gif";
	//�ؿպ˶���Ϣ
	document.getElementById("divCheck").style.display="";
	document.getElementById("divCheckimg").src="../common/images/butExpand.gif";
	//�˱���Ϣ����
	document.getElementById("divCancelTranfor").style.display="none";
	document.getElementById("divCancelTranforimg").src="../common/images/butCollapse.gif";
	//ǩԼ��Լ����
	document.getElementById("divSignal").style.display="none";
	document.getElementById("divSignalimg").src="../common/images/butCollapse.gif";
	
	document.getElementById("OldTransRefGUID").disabled=true;
	document.getElementById("ProviderFormNumber").disabled=true;
	document.getElementById("PolNumber").disabled=true;
}
function ContRollBack(){
		//����ͷ��Ϣ
	document.getElementById("divTranInfo").style.display="";
	document.getElementById("divTranInfoimg").src="../common/images/butExpand.gif";
	//������Ϣ
	document.getElementById("divContInfo").style.display="";
	document.getElementById("divContInfoimg").src="../common/images/butExpand.gif";
	//���㱣�ղ�Ʒ
	document.getElementById("divCaculate").style.display="none";
	document.getElementById("divCaculateimg").src="../common/images/butCollapse.gif";
	//�̵ƽ�����Ϣ
	document.getElementById("divDate").style.display="none";
	document.getElementById("divDateimg").src="../common/images/butCollapse.gif";
	//�ؿպ˶���Ϣ
	document.getElementById("divCheck").style.display="none";
	document.getElementById("divCheckimg").src="../common/images/butCollapse.gif";
	//�˱���Ϣ����
	document.getElementById("divCancelTranfor").style.display="none";
	document.getElementById("divCancelTranforimg").src="../common/images/butCollapse.gif";
	//ǩԼ��Լ����
	document.getElementById("divSignal").style.display="none";
	document.getElementById("divSignalimg").src="../common/images/butCollapse.gif";
	//ԭ������ˮ��
	document.getElementById("OldTransRefGUID").disabled=false;
	//������
	document.getElementById("PolNumber").disabled=true;
	//Ͷ������
	//document.getElementById("HOAppFormNumber").disabled=true;
	//����ӡˢ��
	document.getElementById("ProviderFormNumber").disabled=true;
		//�ɱ���ӡˢ��
	//document.getElementById("OriginalProviderFormNumber").disabled=true;
}
function ContRePrint(){
		//����ͷ��Ϣ
	document.getElementById("divTranInfo").style.display="";
	document.getElementById("divTranInfoimg").src="../common/images/butExpand.gif";
	//������Ϣ
	document.getElementById("divContInfo").style.display="";
	document.getElementById("divContInfoimg").src="../common/images/butExpand.gif";
	//���㱣�ղ�Ʒ
	document.getElementById("divCaculate").style.display="none";
	document.getElementById("divCaculateimg").src="../common/images/butCollapse.gif";
	//�̵ƽ�����Ϣ
	document.getElementById("divDate").style.display="none";
	document.getElementById("divDateimg").src="../common/images/butCollapse.gif";
	//�ؿպ˶���Ϣ
	document.getElementById("divCheck").style.display="none";
	document.getElementById("divCheckimg").src="../common/images/butCollapse.gif";
	//�˱���Ϣ����
	document.getElementById("divCancelTranfor").style.display="none";
	document.getElementById("divCancelTranforimg").src="../common/images/butCollapse.gif";
	//ǩԼ��Լ����
	document.getElementById("divSignal").style.display="none";
	document.getElementById("divSignalimg").src="../common/images/butCollapse.gif";
	document.getElementById("OldTransRefGUID").disabled=false;
	document.getElementById("PolNumber").disabled=false;
	//document.getElementById("HOAppFormNumber").disabled=true;
	document.getElementById("ProviderFormNumber").disabled=false;
	//document.getElementById("OriginalProviderFormNumber").disabled=true;
}

function WRiskFlag(a){
	//var sPolNumber = fm.all('PolNumber').value;
	var stranFlagCode = fm.all('tranFlagCode').value;
	if(stranFlagCode == '1015'){
		//var vSQL = "select bak2 from cont where state = '2' and contno = '"+ sPolNumber + "'";
		//var vResult = easyExecSql(vSQL, 1, 0, 1, 1);
		//document.getElementById("IdOriginalProviderFormNumber").readOnly="true";
	}
	if(stranFlagCode == '1011'){
		//document.getElementById("IdOriginalProviderFormNumber").readOnly="false";
	}
}

//�ύ�����水ť��Ӧ����
function submitForm()
{
 if(!verifyInputForm())
  {
  	return false;
  }
 	document.getElementById("OldTransRefGUID").disabled=false;
	document.getElementById("PolNumber").disabled=false;
	document.getElementById("ProviderFormNumber").disabled=false;
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

function  verifyInputForm(){
	var stranFlagCode = fm.all('tranFlagCode').value;
	if(stranFlagCode == 'P538191A2'){
		  var   num1=fm.BkVchNo1.value;   
		  var   num2=fm.BkVchNo2.value;   
		  if(parseInt(num1)>parseInt(num2))  
		  {   
				alert("�ؿ���ʼ�Ų��ܴ��ڽ�����!");
				return false; 
		  }
		  if(num1.trim() == "")  
		  {   
			  alert("�ؿ���ʼ�Ų���Ϊ��!");
			  return false; 
		  }
		  if(num2.trim() == "")  
		  {   
			  alert("�ؿս����Ų���Ϊ��!");
			  return false; 
		  }
	}
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
	
	//	var vPolNumber = fm.PolNumber.value; 
	//if (vPolNumber.trim() == ""){    
	//	alert("�����Ų���Ϊ��");
	//	return false; 
	//} 
	
	var vPolNumber = fm.PolNumber.value; 
	var vtranFlagCode = fm.tranFlagCode.value;
	var vOldTransRefGUID = fm.OldTransRefGUID.value;
	var vProviderFormNumber = fm.ProviderFormNumber.value;
	//var vOriginalProviderFormNumber = fm.OriginalProviderFormNumber.value;
	//alert(vtranFlagCode);
	if (vPolNumber.trim() == "" && (vtranFlagCode.trim() == "P53819142")){    
		alert("���ճ�������ʱ�����Ų���Ϊ��");
		return false;   
	} 
//	if (vOldTransRefGUID.trim() == "" && (vtranFlagCode.trim() == "P53819142")){    
//		alert("���ճ�������ʱԭ������ˮ�Ų���Ϊ��");
//		return false;   
//	} 
		if (vPolNumber.trim() == "" && (vtranFlagCode.trim() == "P53819184")){    
		alert("�����ش���ʱ�����Ų���Ϊ��");
		return false;   
	}

		if (vProviderFormNumber.trim() == "" && (vtranFlagCode.trim() == "P53819184")){    
		alert("�����ش���ʱ��֤�Ų���Ϊ��");
		return false;
	}
	 
	return true;
}  
