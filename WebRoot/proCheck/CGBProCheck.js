function showRegionCode() {
	fm.RegionCode.value='';
	fm.RegionCodeName.value='';
		fm.Branch.value='';
	fm.BranchName.value='';
	return showCodeList('Region_Code',[fm.RegionCode,fm.RegionCodeName],[0,1],null,' 1=1 And TranCom=#'+ '012' +'#',null,1,null,1);
}

function showBranchCode() {
	fm.Branch.value='';
	fm.BranchName.value='';
	return showCodeList('Branch_Code',[fm.Branch,fm.BranchName],[0,1],null,' 1=1 And TranCom=#'+ '012' +'#' + 'And ZoneNo=#'+ fm.RegionCode.value +'#',null,1,null,1);
}

function TranFlag(c){
	var sTranFlagCode = fm.all('tranFlagCode').value;
	if(sTranFlagCode == '1013'){
		NewContInput();
	}
	if(sTranFlagCode == '1113'){
		NewContConfirm();
	}
	if(sTranFlagCode == '1115'){
		NewContConcel();
	}
	if(sTranFlagCode == '1011'){
		ContRePrint();
	}
	if(sTranFlagCode == '1015'){
		ContConcel();
	}
}

function RiskFlag(a){
	var sTranFlagCode = fm.all('ProductCode').value;
	if(sTranFlagCode == '001'){
	//���� 
	fm.IntialNumberOfUnits.value = '';
	 //���� 
	fm.InitCovAmt.value = '2000000';
	//����
	fm.ModalPremAmt.value = '356840';
	//������������
	fm.DurationMode.value = '1';
	//�������� 
	fm.Duration.value = '100';
	//�ɷ��������� 
	fm.PaymentDurationMode.value = '2';
	//�ɷ����� 
	fm.PaymentDuration.value = '5';
	

		
		document.getElementById("fRiskCode").click();
		document.getElementById("fRiskCode1").click();
		document.getElementById("AccountFlag").click();
		document.getElementById("divRiskList").style=="display: ''";
		document.getElementById("divRiskList2").style=="display: ''";
		
	fm.AccCode1.value = 'U1ZY';
	fm.AllocPercent1.value = '100';
	fm.InvestDateInd.value = '1';
	}
		if(sTranFlagCode == '003'){
		
	//���� 
	fm.IntialNumberOfUnits.value = '';
	//���� 
	fm.InitCovAmt.value = '1000000';
	//���� 
	fm.ModalPremAmt.value = '297250';
	//������������  
	fm.DurationMode.value = '1';
	//�������� 
	fm.Duration.value = '100';
	//�ɷ��������� 
	fm.PaymentDurationMode.value = '2';
	//�ɷ����� 
	
	
	fm.ProductCode1.value = '';
	//���� 
	fm.IntialNumberOfUnits1.value = '';
	//���� 
	fm.InitCovAmt1.value = '';
	//���� 
	fm.ModalPremAmt1.value = '';
	//������������  
	fm.DurationMode1.value = '';
	//�������� 
	fm.Duration1.value = '';
	//�ɷ��������� 
	fm.PaymentDurationMode1.value = '';
	//�ɷ����� 
	fm.PaymentDuration1.value = '';	
	
	fm.PaymentDuration.value = '';	
	fm.AccCode1.value = '';
	fm.AllocPercent1.value = '';
	fm.InvestDateInd.value = '';
		document.getElementById("fRiskCode").click();
		document.getElementById("fRiskCode1").click();
		document.getElementById("AccountFlag").click();
		document.getElementById("divRiskList").style=="display: 'none'";
		document.getElementById("divRiskList2").style=="display: 'none'";
	}
}


function SSRiskFlag(a){
	var sTranFlagCode1 = fm.all('ProductCode1').value;
	if(sTranFlagCode1 == '102'){
	//���� 
	fm.IntialNumberOfUnits1.value = '';
	 //���� 
	fm.InitCovAmt1.value = '';
	//����
	fm.ModalPremAmt1.value = '5000000';
	//������������
	fm.DurationMode1.value = fm.DurationMode.value;
	//�������� 
	fm.Duration1.value = fm.Duration.value;
	//�ɷ��������� 
	fm.PaymentDurationMode1.value = fm.PaymentDurationMode.value;
	//�ɷ����� 
	fm.PaymentDuration1.value = fm.PaymentDuration.value;

	}
		if(sTranFlagCode1 == ''){
		
	//���� 
	fm.IntialNumberOfUnits1.value = '';
	//���� 
	fm.InitCovAmt1.value = '';
	//���� 
	fm.ModalPremAmt1.value = '';
	//������������  
	fm.DurationMode1.value = '';
	//�������� 
	fm.Duration1.value = '';
	//�ɷ��������� 
	fm.PaymentDurationMode1.value = '';
	//�ɷ����� 
	fm.PaymentDuration1.value = '';	
	
	}
}

function SSRiskFlag2(a){
	var sTranFlagCode2 = fm.all('ProductCode2').value;
	if(sTranFlagCode2 == '101'){
	//���� 
	fm.IntialNumberOfUnits2.value = '';
	 //���� 
	fm.InitCovAmt2.value = '';
	//����
	fm.ModalPremAmt2.value = '5000000';
	//������������
	fm.DurationMode2.value = fm.DurationMode.value;
	//�������� 
	fm.Duration2.value = fm.Duration.value;
	//�ɷ��������� 
	fm.PaymentDurationMode2.value = '5';
	//�ɷ����� 
	fm.PaymentDuration2.value = '999';

	}
		if(sTranFlagCode2 == ''){
		
	//���� 
	fm.IntialNumberOfUnits2.value = '';
	//���� 
	fm.InitCovAmt2.value = '';
	//���� 
	fm.ModalPremAmt2.value = '';
	//������������  
	fm.DurationMode2.value = '';
	//�������� 
	fm.Duration2.value = '';
	//�ɷ��������� 
	fm.PaymentDurationMode2.value = '';
	//�ɷ����� 
	fm.PaymentDuration2.value = '';	
	
	}
}


function NewContInput(){
	 window.location.reload();

	initInputBox();
	document.getElementById("PrtNo").disabled=true;
	document.getElementById("OldTransNo").disabled=true;
	//document.getElementById("OldPrtNo").disabled=true;
	//document.getElementById("ContNo").disabled=true;
}

function NewContConfirm(){
	initContConfirm();
	document.getElementById("PrtNo").disabled=false;
	document.getElementById("OldTransNo").disabled=false;
	//document.getElementById("OldPrtNo").disabled=true;
	//document.getElementById("ContNo").disabled=true;
}

function NewContConcel(){
	initContConcel();
	document.getElementById("PrtNo").disabled=true;
	document.getElementById("OldTransNo").disabled=false;
	//document.getElementById("OldPrtNo").disabled=true;
	//document.getElementById("ContNo").disabled=true;
}
function ContRePrint(){
	alert("ContRePrint");
}

function ContConcel(){
	alert("ContConcel");
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
			document.getElementById("BnfReadOnly37").disabled=true;
			document.getElementById("BnfReadOnly38").readOnly=true; 
			
			//��������Ϣ
			//����������
			fm.BnfFullName1.value = '����'; 
			//�������Ա�
			fm.BnfGender1.value = '';
			//������֤������
			fm.BnfGovtIDTC1.value = '';
			//������֤������
			fm.BnfGovtID1.value = '';
			//�����˳�������
			fm.BnfBirthDate1.value = '';
			//����ٷ���
			fm.InterestPercent1.value = '100';
			//����˳��
			fm.Sequence1.value = '1';  
			//�������뱻���˵Ĺ�ϵ
			fm.BnfToInsRelation1.value = '';
			//�������Ƿ�Ϊ������־  
			fm.BeneficiaryIndicator.value='Y'; 
			
			//��������Ϣ
			//����������
			fm.BnfFullName2.value = ''; 
			//�������Ա�
			fm.BnfGender2.value = '';
			//������֤������
			fm.BnfGovtIDTC2.value = '';
			//������֤������
			fm.BnfGovtID2.value = '';
			//�����˳�������
			fm.BnfBirthDate2.value = '';
			//����ٷ���
			fm.InterestPercent2.value = '';
			//����˳��
			fm.Sequence2.value = '';  
			//�������뱻���˵Ĺ�ϵ
			fm.BnfToInsRelation2.value = '';
			//�������Ƿ�Ϊ������־  
		
			
			//��������Ϣ
			//����������
			fm.BnfFullName3.value = ''; 
			//�������Ա�
			fm.BnfGender3.value = '';
			//������֤������
			fm.BnfGovtIDTC3.value = '';
			//������֤������
			fm.BnfGovtID3.value = '';
			//�����˳�������
			fm.BnfBirthDate3.value = '';
			//����ٷ���
			fm.InterestPercent3.value = '';
			//����˳��
			fm.Sequence3.value = '';  
			//�������뱻���˵Ĺ�ϵ
			fm.BnfToInsRelation3.value = '';
			//�������Ƿ�Ϊ������־  
		
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
			document.getElementById("BnfReadOnly37").disabled=false;
			document.getElementById("BnfReadOnly38").readOnly=false; 
			 
			//��������Ϣ1
			//����������
			fm.BnfFullName1.value = '����';
			//�������Ա�
			fm.BnfGender1.value = '1';
			//������֤������
			fm.BnfGovtIDTC1.value = '0';
			//������֤������
			fm.BnfGovtID1.value = '220523850917341';
			//�����˳�������
			fm.BnfBirthDate1.value = '1985-09-17';
			//����ٷ���
			fm.InterestPercent1.value = '100'; 
			//����˳��
			fm.Sequence1.value = '1';
			//�������뱻���˵Ĺ�ϵ
			fm.BnfToInsRelation1.value = '1';
				//�������Ƿ�Ϊ������־
			fm.BeneficiaryIndicator.value = 'N';
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
			//document.getElementById("Ins11").disabled=true; 
		

//��������Ϣ 
	//����������
	fm.InsFullName.value = fm.AppFullName.value
	//�������Ա�
	fm.InsGender.value = fm.AppGender.value
	//������֤������
	fm.InsGenderh.value = fm.AppGender.value
	
	fm.InsGovtIDTC.value = fm.AppGovtIDTC.value
	fm.InsGovtIDTCh.value = fm.AppGovtIDTC.value 
	//������֤������
	fm.InsGovtID.value = fm.AppGovtID.value
	//����������
	fm.InsBirthDate.value = fm.AppBirthDate.value
	//�����˵�������
	fm.InsAddrLine.value = fm.AppAddrLine.value
	//������ͨѶ��ַ	
	fm.InsLine1.value = fm.AppLine1.value 
	//��������������
	fm.InsZip.value = fm.AppZip.value
	//�����˼�ͥ�绰 
	fm.InsDialNumber1.value = fm.AppDialNumber1.value
	//�������ƶ��绰
	fm.InsDialNumber3.value = fm.AppDialNumber3.value
	//������֪
	fm.HealthIndicator.value = 'N';
	fm.all('AppToInsRelation').value='8'; 
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
			//document.getElementById("Ins11").disabled=false;

//��������Ϣ
	//����������
	fm.InsFullName.value = '����';
	//�������Ա�
	fm.InsGender.value = '1'; 
	//������֤������
	fm.InsGovtIDTC.value = '0';
	//������֤������
	fm.InsGovtID.value = '220523850917341';
	//����������
	fm.InsBirthDate.value = '1985-09-17';
	//�����˵�������
	fm.InsAddrLine.value = 'lisi@163.com';
	//������ͨѶ��ַ	
	fm.InsLine1.value = '����к�ƽ��ͬ����'; 
	//��������������
	fm.InsZip.value = '222222';
	//�����˼�ͥ�绰 
	fm.InsDialNumber1.value = '02153558550';
	//�������ƶ��绰
	fm.InsDialNumber3.value = '13821576911';
	//������֪
	fm.HealthIndicator.value = 'N';	
	
		} 
	}



function afterCodeSelect( cCodeName, Field )
{  
	if(cCodeName=='Relation'&&Field.value=='5')
	{
		fm.InsuredName.value= fm.AppntName.value;
		fm.InsuredSex.value= fm.AppntSex.value;
		fm.InsuredBirthday.value= fm.AppntBirthday.value;
		fm.InsuredIDType.value= fm.AppntIDType.value;
		fm.InsuredIDNo.value= fm.AppntIDNo.value;
		fm.InsuredNationality.value= fm.AppntNationality.value;
		fm.InsuredJobCode.value= fm.AppntJobCode.value;
		fm.InsuredHomeAddress.value= fm.AppntHomeAddress.value;
		fm.InsuredHomeZipCode.value= fm.AppntHomeZipCode.value;
		fm.InsuredEmail.value= fm.AppntEmail.value;
		fm.InsuredHomePhone=fm.AppntPhone;
	}
}

//�ύ�����水ť��Ӧ����
function submitForm()
{ 
	 
  if(!verifyInput())
  {
  	return false;
  }
 
  
  var sTranFlagCode = fm.all('tranFlagCode').value;
	if(sTranFlagCode == '1013'){
	fm.action = "./CGBProCheckSave.jsp";
	}
	if(sTranFlagCode == '1113'){
	fm.action = "./TestYBTConfirmSave.jsp";
	}
	if(sTranFlagCode == '1115'){
	fm.action = "./TestYBTNewContConcelSave.jsp";
	}
	if(sTranFlagCode == '1011'){
	fm.action = "./TestYBTRePrintSave.jsp";
	}
	if(sTranFlagCode == '1015'){
	fm.action = "./TestYBTConcelSave.jsp";
	}
 
  
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   

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
	
	var vTeller = fm.Teller.value; 
	if (vTeller.trim() == ""){    
		alert("��Ա���벻��Ϊ��");
		return false; 
	}  
	
	var vHOAppFormNumber = fm.HOAppFormNumber.value; 
	if (vHOAppFormNumber.trim() == ""){    
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
