
function TranFlag(c){
	var sTranFlagCode = fm.all('tranFlagCode').value;
	if(sTranFlagCode == '510015'){
		NewContInput();
	}
	if(sTranFlagCode == '510001'){
		NewContConfirm();
	}
}
function NewContInput(){
	 window.location.reload();
		initInputBox();
	document.getElementById("ProviderFormNumber").disabled=false;
	document.getElementById("ReqsrNo").disabled=true;
	//document.getElementById("OldPrtNo").disabled=true;
	//document.getElementById("ContNo").disabled=true;
}

function NewContConfirm(){
	initContConfirm();
	document.getElementById("ProviderFormNumber").disabled=true;
	document.getElementById("ReqsrNo").disabled=false;
	fm.ReqsrNo.value = fm.InputTransrNo.value;
}
function RiskFlag(a){
	var sTranFlagCode = fm.all('ProductCode').value;
	if(sTranFlagCode == '113030'){
		//fm.PayIntv.value = '1';
		
	//���� 
	fm.IntialNumberOfUnits.value = '20';
	 //���� 
	fm.InitCovAmt.value = '';
	//����
	fm.ModalPremAmt.value = '2000000';
	//������������
	//fm.InsuYearFlag.value = '4';
	//�������� 
	//fm.InsuYear.value = '5';
	//�ɷ��������� 
	//fm.PayEndYearFlag.value = '';
	//�ɷ����� 
	//fm.PayEndYear.value = '';
		//document.getElementById("fRiskCode").click();
	//	document.getElementById("AccountFlag").click();
		//document.getElementById("divRiskList").style=="display: ''";
	//	document.getElementById("divRiskList2").style=="display: ''";
		
	//fm.AccCode1.value = 'U1ZY';
	//fm.AllocPercent1.value = '100';
	//fm.InvestDateInd.value = '1';
	}
		if(sTranFlagCode == '222002'){
		
	//���� 
	fm.Mult.value = '30';
	//���� 
	fm.Amnt.value = '';
	//���� 
	fm.Prem.value = '30000.00';
	//������������  
	fm.InsuYearFlag.value = '4';
	//�������� 
	fm.InsuYear.value = '5';
	//�ɷ��������� 
	fm.PayEndYearFlag.value = '';
	//�ɷ����� 
	fm.PayEndYear.value = ''; 
	
	fm.PayIntv.value = '1';

		//document.getElementById("fRiskCode").click();
		//document.getElementById("AccountFlag").click();
		//document.getElementById("divRiskList").style=="display: 'none'";
		//document.getElementById("divRiskList2").style=="display: 'none'";
	}
		
		if(sTranFlagCode == '222003'){
		
	//���� 
	fm.Mult.value = '10';
	//���� 
	fm.Amnt.value = '';
	//���� 
	fm.Prem.value = '10000.00';
	//������������  
	fm.InsuYearFlag.value = '4';
	//�������� 
	fm.InsuYear.value = '5';
	//�ɷ��������� 
	fm.PayEndYearFlag.value = '4';
	//�ɷ����� 
	fm.PayEndYear.value = '5'; 
	
	fm.PayIntv.value = '5';

		//document.getElementById("fRiskCode").click();
		//document.getElementById("AccountFlag").click();
		//document.getElementById("divRiskList").style=="display: 'none'";
		//document.getElementById("divRiskList2").style=="display: 'none'";
	}
}
function showRegionCode() {
	fm.RegionCode.value='';
	fm.RegionCodeName.value='';
		fm.Branch.value='';
	fm.BranchName.value='';
	return showCodeList('Region_Code',[fm.RegionCode,fm.RegionCodeName],[0,1],null,' 1=1 And TranCom=#'+ '011' +'#',null,1,null,1);
}

function showBranchCode() {
	fm.Branch.value='';
	fm.BranchName.value='';
	return showCodeList('Branch_Code',[fm.Branch,fm.BranchName],[0,1],null,' 1=1 And TranCom=#'+ '011' +'#' + 'And ZoneNo=#'+ fm.RegionCode.value +'#',null,1,null,1);
}

function showAccCode1() {
	fm.AccCode1.value='';
	fm.AccCodeName1.value='';
	fm.AllocPercent1.value='';
	return showCodeList('account_code',[fm.AccCode1,fm.AccCodeName1],[0,1],null,null,null,1,null,1);
}

function showAccCode1() {
	fm.AccCode1.value='';
	fm.AccCodeName1.value='';
	fm.AllocPercent1.value='';
	return showCodeList('account_code',[fm.AccCode1,fm.AccCodeName1],[0,1],null,null,null,1,null,1);
}

function showAccCode2() {
	fm.AccCode2.value='';
	fm.AccCodeName2.value='';
	fm.AllocPercent2.value='';
	return showCodeList('account_code',[fm.AccCode2,fm.AccCodeName2],[0,1],null,null,null,1,null,1);
}

function showAccCode3() {
	fm.AccCode3.value='';
	fm.AccCodeName3.value='';
	fm.AllocPercent3.value='';
	return showCodeList('account_code',[fm.AccCode3,fm.AccCodeName3],[0,1],null,null,null,1,null,1);
}

function showAccCode4() {
	fm.AccCode4.value='';
	fm.AccCodeName4.value='';
	fm.AllocPercent4.value='';
	return showCodeList('account_code',[fm.AccCode4,fm.AccCodeName4],[0,1],null,null,null,1,null,1);
}

function showAccCode5() {
	fm.AccCode5.value='';
	fm.AccCodeName5.value='';
	fm.AllocPercent5.value='';
	return showCodeList('account_code',[fm.AccCode5,fm.AccCodeName5],[0,1],null,null,null,1,null,1);
}

function showProductCode() {
	clearMainRisk();
	clearRisk1();
	clearRisk2();
	clearAccCount();
	showCodeList('ICBC_MainRiskCode',[fm.ProductCode,fm.RiskCodeName,fm.IntialNumberOfUnits,fm.ModalPremAmt,fm.InitCovAmt,fm.DurationMode,fm.Duration,fm.PaymentDurationMode,fm.PaymentDuration,fm.PaymentMode,fm.RiskCode,fm.AccCode1,fm.AllocPercent1,fm.AccCodeName1,fm.InvestDateInd],[0,1,2,3,4,5,6,7,8,9,10,11,12,13,14],null,null,null,1,null,1);
}

function showProductCode1() {
	
	clearRisk1();
	clearRisk2();
	
	if(!checkRiskCode1()){
		return false;
	}
	var MainRiskCode = trim(fm.RiskCode.value);
	var RiskCode2 = trim(fm.RiskCode2.value);
	if(RiskCode2 ==''){
		RiskCode2=' ';
	}
	showCodeList('ICBC_SubRiskCode',[fm.ProductCode1,fm.RiskCodeName1,fm.IntialNumberOfUnits1,fm.ModalPremAmt1,fm.InitCovAmt1,fm.DurationMode1,fm.Duration1,fm.PaymentDurationMode1,fm.PaymentDuration1,fm.RiskCode1],[0,1,2,3,4,5,6,7,8,9],null,' 1=1 And m.mainriskcode=#'+ MainRiskCode +'#'+' And m.riskcode!=#'+ RiskCode2 +'#' ,null,1,null,1);
}

 
function showProductCode2() {
	clearRisk2();
	if(!checkRiskCode1()){
		return false;
	}
	var MainRiskCode = trim(fm.RiskCode.value);
	var RiskCode1 = trim(fm.RiskCode1.value);
	if(RiskCode1 ==''){
		RiskCode1=' ';
	}
	showCodeList('ICBC_SubRiskCode',[fm.ProductCode2,fm.RiskCodeName2,fm.IntialNumberOfUnits2,fm.ModalPremAmt2,fm.InitCovAmt2,fm.DurationMode2,fm.Duration2,fm.PaymentDurationMode2,fm.PaymentDuration2,fm.RiskCode2],[0,1,2,3,4,5,6,7,8,9],null,' 1=1 And m.mainriskcode=#'+ MainRiskCode +'#'+' And m.riskcode!=#'+ RiskCode1 +'#' ,null,1,null,1);
} 



function showProductCodeKey() {}
function showProductCode1Key() {}
function showProductCode2Key() {}

function checkRiskCode1(){
	var MainRiskCode = trim(fm.RiskCode.value);
	if(MainRiskCode == ''){
		alert('����ѡ�����ձ���');
		return false; 
	}
	var mSqlStr = "select count(1) from lmriskapp s where s.subriskflag='S' and  s.mainriskcode = '"+MainRiskCode+"'";
		var mComInfos = easyExecSql(mSqlStr); 
		if(mComInfos == '0'){
			alert("������δ����Ը�����!");
			return false; 
	}
		return true;
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
  
 
  
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   

  fm.submit(); //�ύ
}
 function showtrancode(){
 	fm.tranFlagCode.value='';
	fm.tranFlagCodeName.value='';
	return showCodeList('servccbtype',[fm.tranFlagCode,fm.tranFlagCodeName],[0,1],null,'',null,1,null,1);
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
	
	fm.action = "./TestYBTCCBConfirmSave.jsp";
	fm.ConfirmFlag.value = "1";
	fm.submit();	
	fm.action = vOldAction;
}


function cancleTransaction()
{
	var vOldAction = fm.action;
	
	fm.action = "./TestYBTCCBCancleSave.jsp";
	fm.ConfirmFlag.value = "0";
	fm.submit();	
	fm.action = vOldAction;
}

function cancelTransaction()
{
	var vOldAction = fm.action;
	
	fm.action = "./TestYBTCCBCancleSave.jsp";
	fm.ConfirmFlag.value = "0";
	fm.submit();
	
	fm.action = vOldAction;
}


function reprintCont()
{
	var vOldAction = fm.action;
	
	fm.action = "./TestYBTCCBRePrtSave.jsp";
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
			fm.BnfFullName1.value = ''; 
			//�������Ա�
			fm.BnfGender1.value = '';
			//������֤������
			fm.BnfGovtIDTC1.value = '';
			//������֤������
			fm.BnfGovtID1.value = '';
			//�����˳�������
			fm.BnfBirthDate1.value = '';
			fm.BnfAdress1.value='';
			//����ٷ���
			fm.InterestPercent1.value = '100';
			//����˳��
			fm.Sequence1.value = '1';  
			//�������뱻���˵Ĺ�ϵ
			fm.BnfToInsRelation1.value = '';
			//�������Ƿ�Ϊ������־  
			fm.BeneficiaryIndicator.value='1'; 
			
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
			fm.BnfAdress2.value='';
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
			fm.BnfAdress3.value='';
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
			fm.BnfAdress1.value='����к�ƽ��ͬ����';
			//����ٷ���
			fm.InterestPercent1.value = '100'; 
			//����˳��
			fm.Sequence1.value = '1';
			//�������뱻���˵Ĺ�ϵ
			fm.BnfToInsRelation1.value = '4';
				//�������Ƿ�Ϊ������־
			fm.BeneficiaryIndicator.value = '0';
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
			document.getElementById("Ins12").disabled=true;
			document.getElementById("Ins13").disabled=true;
			document.getElementById("InsCountry").disabled=true;
		

//��������Ϣ 
	//����������
	fm.InsFullName.value = fm.AppFullName.value;
	//�������Ա�
	fm.InsGender.value = fm.AppGender.value;
	//������֤������
	fm.InsGenderh.value = fm.AppGender.value;
	
	fm.InsGovtIDTC.value = fm.AppGovtIDTC.value;
	fm.InsGovtIDTCh.value = fm.AppGovtIDTC.value ;
	//������֤������
	fm.InsGovtID.value = fm.AppGovtID.value;
	//����������
	fm.InsBirthDate.value = fm.AppBirthDate.value;
	//�����˵�������
	fm.InsAddrLine.value = fm.AppAddrLine.value;
	//������ͨѶ��ַ	
	fm.InsLine1.value = fm.AppLine1.value ;
	//��������������
	fm.InsZip.value = fm.AppZip.value;
	//�����˼�ͥ�绰 
	fm.InsDialNumber1.value = fm.AppDialNumber1.value;
	//�������ƶ��绰
	fm.InsDialNumber3.value = fm.AppDialNumber3.value;
	fm.InsCountry.value = fm.AppCountry.value;
	fm.InsuJobCode.value = fm.ApplJobCode.value;
	//������֪
	fm.HealthIndicator.value = 'N';
	fm.all('AppToInsRelation').value='1'; 
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
			
			document.getElementById("Ins12").disabled=false;
			document.getElementById("Ins13").disabled=false;
			document.getElementById("InsCountry").disabled=false;
			
			
			//document.getElementById("Ins11").disabled=false;
					

//��������Ϣ
	//����������
	fm.InsFullName.value = '������1';
	//�������Ա�
	fm.InsGender.value = '1'; 
	//������֤������
	fm.InsGovtIDTC.value = 'B';
	//������֤������
	fm.InsGovtID.value = '3143148';
	//����������
	fm.InsBirthDate.value = '1985-09-17';
	//�����˵�������
	fm.InsAddrLine.value = 'lisi@163.com';
	//������ͨѶ��ַ	
	fm.InsLine1.value = '����к�ƽ��ͬ����'; 
	//��������������
	fm.InsZip.value = '555555';
	//�����˼�ͥ�绰 
	fm.InsDialNumber1.value = '02153558550';
	//�������ƶ��绰
	fm.InsDialNumber3.value = '13821576911';
	//������֪
	fm.HealthIndicator.value = 'N';	
	fm.InsCountry.value = '0156';
	fm.InsuJobCode.value = '3010101';
	
		} 
	}

	function clearMainRisk(){
		fm.RiskCode.value = '';
		fm.RiskCodeName.value = '';
		fm.ProductCode.value = '';
		fm.IntialNumberOfUnits.value = '';
		fm.InitCovAmt.value = '';
		fm.ModalPremAmt.value = '';
		fm.DurationMode.value = '';
		fm.Duration.value = '';
		fm.PaymentDurationMode.value = '';
		fm.PaymentDuration.value = '';
		fm.PaymentMode.value = '';
		fm.InvestDateInd.value = '';
	}
	
	
	function clearRisk1(){
		fm.RiskCode1.value = '';
		fm.RiskCodeName1.value = '';
		fm.ProductCode1.value = '';
		fm.IntialNumberOfUnits1.value = '';
		fm.InitCovAmt1.value = '';
		fm.ModalPremAmt1.value = '';
		fm.DurationMode1.value = '';
		fm.Duration1.value = '';
		fm.PaymentDurationMode1.value = '';
		fm.PaymentDuration1.value = '';
	}
	
	
	function clearRisk2(){
		fm.RiskCode2.value = '';
		fm.RiskCodeName2.value = '';
		fm.ProductCode2.value = '';
		fm.IntialNumberOfUnits2.value = '';
		fm.InitCovAmt2.value = '';
		fm.ModalPremAmt2.value = '';
		fm.DurationMode2.value = '';
		fm.Duration2.value = '';
		fm.PaymentDurationMode2.value = '';
		fm.PaymentDuration2.value = '';
	}
	
	
	function clearAccCount(){
		fm.AccCode1.value = '';
		fm.AccCodeName1.value = '';
		fm.AllocPercent1.value = '';
		fm.AccCode2.value = '';
		fm.AccCodeName2.value = '';
		fm.AllocPercent2.value = '';
		fm.AccCode3.value = '';
		fm.AccCodeName3.value = '';
		fm.AllocPercent3.value = '';
		fm.AccCode4.value = '';
		fm.AccCodeName4.value = '';
		fm.AllocPercent4.value = '';
		fm.AccCode5.value = '';
		fm.AccCodeName5.value = '';
		fm.AllocPercent5.value = '';
	}