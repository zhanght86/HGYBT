function TranFlag(c){
	var sTranFlagCode = fm.all('tranFlagCode').value;
	if(sTranFlagCode == '01'){
		NewContInput();
	}
	if(sTranFlagCode == '02'){
		NewContConfirm();
	}
	//if(sTranFlagCode == '03'){
	//	NewContConcel();
	//}
	if(sTranFlagCode == '03'){
		ContConcel();
	}
}

function RiskFlag(a){
	var sTranFlagCode = fm.all('Code').value;
	if(sTranFlagCode == '211901'){	
	document.getElementById("divOwnlist1").style.display='';
	fm.LoanNo.value='20130129';
	fm.LoanBank.value='�й�ũҵ����';
	fm.LoanDate.value=fm.BankDate.value;
	fm.LoanEndDate.value=fm.BankDate.value;
	fm.LoanType.value='00';
	fm.LoanAccNo.value='6227001700130252734';
	fm.LoanPrem.value='1000000';
	fm.InsuDate.value=fm.BankDate.value;
	fm.InsuEndDate.value=fm.BankDate.value;
	
	fm.Prem.value='500';
	fm.Amnt.value='1000000';
	fm.InsuYear.value='1';
	
	document.getElementById("BnfFlag").checked = true;
	setBnfFlag(document.getElementById("BnfFlag"));
	

	}else  if(sTranFlagCode == '211902'){	
		document.getElementById("divOwnlist1").style.display='';
		fm.LoanNo.value='2014010600000001';
		fm.LoanBank.value='�й�ũҵ����';
		fm.LoanDate.value=fm.BankDate.value;
		fm.LoanEndDate.value=fm.BankDate.value;
		fm.LoanType.value='00';
		fm.LoanAccNo.value='6227001700130252734';
		fm.LoanPrem.value='1000000';
		fm.InsuDate.value=fm.BankDate.value;
		fm.InsuEndDate.value=fm.BankDate.value;
		
		fm.Prem.value='500';
		fm.Amnt.value='1000000';
		fm.InsuYear.value='1';
		
		document.getElementById("BnfFlag").checked = true;
		setBnfFlag(document.getElementById("BnfFlag"));
		

		}else{		
		document.getElementById("divOwnlist1").style.display='none';
		fm.LoanNo.value='';
		fm.LoanBank.value='';
		fm.LoanDate.value='';
		fm.LoanEndDate.value='';
		fm.LoanType.value='';
		fm.LoanAccNo.value='';
		fm.LoanPrem.value='';
		fm.InsuDate.value='';
		fm.InsuEndDate.value='';
		
		fm.Prem.value = '30000.00';
		fm.Amnt.value='';
		fm.InsuYear.value='5';
		
		document.getElementById("BnfFlag").checked = false;
		setBnfFlag(document.getElementById("BnfFlag"));
	}

}


function SSRiskFlag(a){
	var sTranFlagCode1 = fm.all('Code1').value;
	if(sTranFlagCode1 == '101'){
	//���� 
	fm.Mult1.value = '';
	 //���� 
	fm.Amnt1.value = '';
	//����
	fm.Prem1.value = '5000000';
	//������������
	fm.InsuYearFlag1.value = fm.InsuYearFlag.value;
	//�������� 
	fm.InsuYear1.value = fm.InsuYear.value;
	//�ɷ��������� 
	fm.PayEndYearFlag1.value = fm.PayEndYearFlag.value;
	//�ɷ����� 
	fm.PayEndYear1.value = fm.PayEndYear.value;

	}
		if(sTranFlagCode1 == ''){
		
	//���� 
	fm.Mult1.value = '';
	//���� 
	fm.Amnt1.value = '';
	//���� 
	fm.Prem1.value = '';
	//������������  
	fm.InsuYearFlag1.value = '';
	//�������� 
	fm.InsuYear1.value = '';
	//�ɷ��������� 
	fm.PayEndYearFlag1.value = '';
	//�ɷ����� 
	fm.PayEndYear1.value = '';	
	
	}
}


function NewContInput(){
	 window.location.reload();

	initInputBox();
	//document.getElementById("PrtNo").disabled=false;
	document.getElementById("ReqsrNo").disabled=true;
	//document.getElementById("OldPrtNo").disabled=true;
	//document.getElementById("ContNo").disabled=true;
}

function NewContConfirm(){
	initContConfirm();
	//document.getElementById("PrtNo").disabled=false;
	document.getElementById("ReqsrNo").disabled=false;
	//document.getElementById("OldPrtNo").disabled=true;
	//document.getElementById("ContNo").disabled=true;
	fm.ReqsrNo.value = fm.InputTransrNo.value;
}

//function NewContConcel(){
	//initContConcel();
	//document.getElementById("PrtNo").disabled=true;
	//document.getElementById("OldTransNo").disabled=false;
	//document.getElementById("OldPrtNo").disabled=true;
	//document.getElementById("ContNo").disabled=true;
//}
function ContRePrint(){
	alert("ContRePrint");
}

function ContConcel(){
    initContConcel();
	//document.getElementById("PrtNo").disabled=true;
	document.getElementById("ReqsrNo").disabled=false;
	//alert("ContConcel");
}




function setRiskFlag(c){
		if(c.checked == true){
			
			fm.all('hiddenBnf').value='0';
		    //alert(fm.all('hiddenBnf').value);
		}else{ 
			fm.all('hiddenBnf').value='1';
			//alert(fm.all('hiddenBnf').value);
		}
	}


	function setBnfFlag(BnfFlag){ 
		if(BnfFlag.checked == true){
			
			
			//��������Ϣ
			//����������
			fm.BnfType1.value = '';
			//����������
			fm.BnfName1.value = ''; 
			//�������Ա�
			fm.BnfSex1.value = '';
			//������֤������
			fm.BnfIDType1.value = '';
			//������֤������
			fm.BnfIDNo1.value = '';
			//�����˳�������
			fm.BnfBirthday1.value = '';
			//����ٷ���
			fm.BnfBnfLot1.value = '';
			//����˳��
			fm.BnfBnfGrade1.value = '';  
			//�������뱻���˵Ĺ�ϵ
			fm.BnfRelationToInsured1.value = '';
			//�������Ƿ�Ϊ������־  
			//fm.BeneficiaryIndicator.value='Y'; 
			
			fm.BnfValidYear1.value = ''; 
			
			//��������Ϣ
			//����������
			fm.BnfType2.value = '';
			//����������
			fm.BnfName2.value = ''; 
			//�������Ա�
			fm.BnfSex2.value = '';
			//������֤������
			fm.BnfIDType2.value = '';
			//������֤������
			fm.BnfIDNo2.value = '';
			//�����˳�������
			fm.BnfBirthday2.value = '';
			//����ٷ���
			fm.BnfBnfLot2.value = '';
			//����˳��
			fm.BnfBnfGrade2.value = '';  
			//�������뱻���˵Ĺ�ϵ
			fm.BnfRelationToInsured2.value = '';
			fm.BnfValidYear2.value = ''; 
			
		
			
			//��������Ϣ
			//����������
			fm.BnfType3.value = '';
			//����������
			fm.BnfName3.value = ''; 
			//�������Ա�
			fm.BnfSex3.value = '';
			//������֤������
			fm.BnfIDType3.value = '';
			//������֤������
			fm.BnfIDNo3.value = '';
			//�����˳�������
			fm.BnfBirthday3.value = '';
			//����ٷ���
			fm.BnfBnfLot3.value = '';
			//����˳��
			fm.BnfBnfGrade3.value = '';  
			//�������뱻���˵Ĺ�ϵ
			fm.BnfRelationToInsured3.value = '';
			
			fm.BnfValidYear3.value = ''; 
			document.getElementById("BnfReadOnly11").disabled=true; 
			document.getElementById("BnfReadOnly12").readOnly=true; 
			document.getElementById("BnfReadOnly13").disabled=true; 
			document.getElementById("BnfReadOnly14").disabled=true; 
			document.getElementById("BnfReadOnly15").readOnly=true; 
			document.getElementById("BnfReadOnly16").readOnly=true; 
			document.getElementById("BnfReadOnly17").disabled=true;
			document.getElementById("BnfReadOnly18").readOnly=true; 
			document.getElementById("BnfReadOnly21").disabled=true; 
			document.getElementById("BnfReadOnly22").readOnly=true; 
			document.getElementById("BnfReadOnly23").disabled=true; 
			document.getElementById("BnfReadOnly24").disabled=true; 
			document.getElementById("BnfReadOnly25").readOnly=true; 
			document.getElementById("BnfReadOnly26").readOnly=true; 
			document.getElementById("BnfReadOnly27").disabled=true;
			document.getElementById("BnfReadOnly28").readOnly=true; 
			document.getElementById("BnfReadOnly31").disabled=true; 
			document.getElementById("BnfReadOnly32").readOnly=true; 
			document.getElementById("BnfReadOnly33").disabled=true; 
			document.getElementById("BnfReadOnly34").disabled=true; 
			document.getElementById("BnfReadOnly35").readOnly=true; 
			document.getElementById("BnfReadOnly36").readOnly=true; 
			//document.getElementById("BnfReadOnly37").disabled=true;
			document.getElementById("BnfReadOnly38").readOnly=true; 
			document.getElementById("BnfReadOnly39").disabled=true;
			document.getElementById("BnfReadOnly40").disabled=true;
			document.getElementById("BnfReadOnly41").disabled=true;
			 
		
		}else{ 
			document.getElementById("BnfReadOnly11").disabled=false; 
			document.getElementById("BnfReadOnly12").readOnly=false; 
			document.getElementById("BnfReadOnly13").disabled=false; 
			document.getElementById("BnfReadOnly14").disabled=false; 
			document.getElementById("BnfReadOnly15").readOnly=false; 
			document.getElementById("BnfReadOnly16").readOnly=false;
			document.getElementById("BnfReadOnly17").disabled=false; 
			document.getElementById("BnfReadOnly18").readOnly=false; 
			document.getElementById("BnfReadOnly21").disabled=false; 
			document.getElementById("BnfReadOnly22").readOnly=false; 
			document.getElementById("BnfReadOnly23").disabled=false; 
			document.getElementById("BnfReadOnly24").disabled=false; 
			document.getElementById("BnfReadOnly25").readOnly=false; 
			document.getElementById("BnfReadOnly26").readOnly=false; 
			document.getElementById("BnfReadOnly27").disabled=false;
			document.getElementById("BnfReadOnly28").readOnly=false;  
			document.getElementById("BnfReadOnly31").disabled=false; 
			document.getElementById("BnfReadOnly32").readOnly=false; 
			document.getElementById("BnfReadOnly33").disabled=false; 
			document.getElementById("BnfReadOnly34").disabled=false; 
			document.getElementById("BnfReadOnly35").readOnly=false; 
			document.getElementById("BnfReadOnly36").readOnly=false; 
			//document.getElementById("BnfReadOnly37").disabled=false;
			document.getElementById("BnfReadOnly38").readOnly=false; 
			document.getElementById("BnfReadOnly39").disabled=false;
			document.getElementById("BnfReadOnly40").disabled=false;
			document.getElementById("BnfReadOnly41").disabled=false;
			 
			//��������Ϣ1
			//����������
			fm.BnfType1.value = '1';
			//����������
			fm.BnfName1.value = '����';
			//�������Ա�
			fm.BnfSex1.value = '0';
			//������֤������
			fm.BnfIDType1.value = '110001';
			//������֤������
			fm.BnfIDNo1.value = '220523850917341';
			//�����˳�������
			fm.BnfBirthday1.value = '1985-09-17';
			//����ٷ���
			fm.BnfBnfLot1.value = '100'; 
			//����˳��
			fm.BnfBnfGrade1.value = '1';
			//�������뱻���˵Ĺ�ϵ
			fm.BnfRelationToInsured1.value = '2';
			//�������Ƿ�Ϊ������־
			//fm.BeneficiaryIndicator.value = 'N';
			
			fm.BnfValidYear1.value = '2020-09-16'; 
		}
	}
	
	function setInsFlag(InsFlag){
		if(InsFlag.checked == true){
		  
			document.getElementById("Ins1").readOnly=true;  
			document.getElementById("Ins2").disabled=true; 
			document.getElementById("Ins3").disabled=true; 
			document.getElementById("Ins4").readOnly=true;
			document.getElementById("Ins5").readOnly=true;  
			document.getElementById("Ins6").readOnly=true;
			document.getElementById("Ins7").readOnly=true;
			document.getElementById("Ins8").readOnly=true; 
			document.getElementById("Ins9").readOnly=true; 
			document.getElementById("Ins10").readOnly=true; 
			document.getElementById("Ins11").disabled=true; 
			document.getElementById("Ins12").readOnly=true;
		

//��������Ϣ 
	//����������
	fm.InsuName.value = fm.ApplName.value;
	//�������Ա�
	fm.InsuSex.value = fm.ApplSex.value;
	fm.InsuSexh.value = fm.ApplSex.value;
	//������֤������
	fm.InsuIDType.value = fm.ApplIDType.value;
	fm.InsuIDTypeh.value = fm.ApplIDType.value ;
	//������֤������
	fm.InsuIDNo.value = fm.ApplIDNo.value;
	//����������
	fm.InsuBirthday.value = fm.ApplBirthday.value;
	//�����˵�������
	fm.InsuEmail.value = fm.ApplEmail.value;
	//������ͨѶ��ַ	
	fm.InsuAddress.value = fm.ApplAddress.value;
	//��������������
	fm.InsuZipCode.value = fm.ApplZipCode.value;
	//�����˼�ͥ�绰 
	fm.InsuPhone.value = fm.ApplPhone.value;
	//�������ƶ��绰
	fm.InsuMobile.value = fm.ApplMobile.value;
	//������ְҵ����
	fm.InsuJobCode.value = fm.ApplJobCode.value;
	
	//������֪
	fm.HealthFlag.value = 'N';
	fm.ApplRelaToInsured.value = '1';
	fm.all('ApplRelaToInsured').value='1'; 
		}  
		else 
		{   
			document.getElementById("Ins1").readOnly=false;
			document.getElementById("Ins2").disabled=false;
			document.getElementById("Ins3").disabled=false;
			document.getElementById("Ins4").readOnly=false;
			document.getElementById("Ins5").readOnly=false;
			document.getElementById("Ins6").readOnly=false;
			document.getElementById("Ins7").readOnly=false;
			document.getElementById("Ins8").readOnly=false;
			document.getElementById("Ins9").readOnly=false;
			document.getElementById("Ins10").readOnly=false;
			document.getElementById("Ins11").disabled=false;
			document.getElementById("Ins12").readOnly=false;

//��������Ϣ
	//����������
			//����������
			fm.InsuName.value = '������1';
			//�������Ա�
			fm.InsuSex.value = '0';
			//������֤������
			fm.InsuIDType.value = '110001';
			//������֤������
			fm.InsuIDNo.value = '220523850917341';
			//����������
			fm.InsuBirthday.value = '1985-09-17';
			//�����˵�������
			fm.InsuEmail.value = 'lisi@163.com';
			//������ͨѶ��ַ	
			fm.InsuAddress.value = '����к�ƽ��ͬ����'; 
			//��������������
			fm.InsuZipCode.value = '222222';
			//�����˼�ͥ�绰 
			fm.InsuPhone.value = '02153558550';
			//�������ƶ��绰
			fm.InsuMobile.value = '13821576911';
			//������֪
			fm.HealthFlag.value = 'N';
			//������ְҵ����
			fm.InsuJobCode.value = '01';
	
		} 
	}



function afterCodeSelect( cCodeName, Field )
{  
	if(cCodeName=='Relation'&&Field.value=='5')
	{
		fm.InsuName.value= fm.ApplName.value;
		fm.InsuSex.value= fm.ApplSex.value;
		fm.InsuBirthday.value= fm.ApplBirthday.value;
		fm.InsuIDType.value= fm.ApplIDType.value;
		fm.InsuIDNo.value= fm.ApplIDNo.value;
		//fm.InsuredNationality.value= fm.AppntNationality.value;
		fm.InsuAddress.value= fm.ApplAddress.value;
		fm.InsuZipCode.value= fm.ApplZipCode.value;
		fm.InsuEmail.value= fm.ApplEmail.value;
		fm.InsuPhone.value=fm.ApplPhone.value;
		fm.InsuMobile.value=fm.ApplMobile.value;
		fm.InsuJobCode.value = fm.ApplJobCode.value;

	}
}

//�ύ�����水ť��Ӧ����
function submitForm()
{
	// alert("a");
  if(!verifyInput())
  {
  	return false;
  }
 
  
  var sTranFlagCode = fm.all('tranFlagCode').value;
  
	if(sTranFlagCode == '01'){
	fm.action = "./LIANTestYBTSave.jsp";
	}
	if(sTranFlagCode == '02'){
	fm.action = "./LIANTestYBTConfirmSave.jsp";
	}
	//if(sTranFlagCode == '1115'){
	//fm.action = "./LIANTestYBTNewContConcelSave.jsp";
	//}
	//if(sTranFlagCode == '1011'){
	//fm.action = "./TestYBTRePrintSave.jsp";
	//}
	if(sTranFlagCode == '03'){
	fm.action = "./LIANTestYBTContConcelSave.jsp";
	}
 
  	//alert("a");
  	
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  
  //alert("a");

  fm.submit(); //�ύ
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
	 
	var vBankDate = fm.BankDate.value; 
	if (vBankDate.trim() == ""){    
		alert("���ж˽������ڲ���Ϊ��");
		return false; 
	}   
	  
	var vTransrNo = fm.TransrNo.value; 
	if (vTransrNo.trim() == ""){    
		alert("������ˮ�Ų���Ϊ��");
		return false; 
	}   
	
	var vZoneNo = fm.ZoneNo.value; 
	if (vZoneNo.trim() == ""){    
		alert("�������벻��Ϊ��");
		return false; 
	} 
	
	var vBrNo = fm.BrNo.value; 
	if (vBrNo.trim() == ""){    
		alert("������벻��Ϊ��");
		return false; 
	} 
	
	var vTellerNo = fm.TellerNo.value; 
	if (vTellerNo.trim() == ""){    
		alert("��Ա���벻��Ϊ��");
		return false; 
	}  
	
	var vProposalContNo = fm.ProposalContNo.value; 
	if (vProposalContNo.trim() == ""){    
		alert("Ͷ����Ų���Ϊ��");
		return false;  
	} 
	 
	return true;
}  
 
//�ύ�����,���������ݷ��غ�ִ�еĲ���

 

function afterSubmit(ResultCode,ResultInfoDesc)
{ 	
	  showInfo.close(); 
	  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" +ResultInfoDesc;   
	  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");	  		  
}  

function confirmTransaction()
{
	var vOldAction = fm.action;
	
	fm.action = "./LIANTestYBTConfirmSave.jsp";
	fm.ConfirmFlag.value = "1";
	fm.submit();	
	fm.action = vOldAction;
}


function cancleTransaction()
{
	var vOldAction = fm.action;
	
	fm.action = "./LIANTestYBTContConcelSave.jsp";
	fm.ConfirmFlag.value = "0";
	fm.submit();	
	fm.action = vOldAction;
}

function cancelTransaction()
{
	var vOldAction = fm.action;
	
	fm.action = "./LIANTestYBTContConcelSave.jsp";
	fm.ConfirmFlag.value = "0";
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
