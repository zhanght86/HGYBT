<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%@page import="com.sinosoft.lis.pubfun.*" %>
<%@page import="org.jdom.*" %>
<%@page import="com.sinosoft.midplat.MidplatConf" %>
<% 
	//�������ƣ�TestYBTContInit.jsp
	//�����ܣ����ȷ�� 
	//�������ڣ�2010-01-20 16:43:36
	//������  �����ź���
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%  
	PubFun1 pubfun = new PubFun1();
	String strTransExeDate = DateUtil.getCur10Date();
	String strAccNo = "6225"+PubFun1.CreateMaxNo("AccNo",16);
	
	 
	String strTransRefGUID = "CGBTest"+pubfun.CreateMaxNo("ProTestTransNo",6);
	String strHOAppFormNumber = "CGBTest"+pubfun.CreateMaxNo("PrtNo",12);
	String strProviderFormNumber = "CGBTest"+pubfun.CreateMaxNo("ContNo",12);

  	 String tIp = "127.0.0.1";
  	 String tPort = "8001";
  	 String tZoneNo = "000006";
  	 String tNodeNo = "000048";

%>
<script language="JavaScript">
function initForm()
{
	try 
	{
		initInputBox();
	}
	catch(re)
	{
		alert("TestYBTContInit.jsp-->InitForm �����з����쳣:��ʼ���������!");
	}
}



function initContConfirm()
{
try { 
//������Ϣ
	//���ж˽�������
	 
	fm.OriginalTransRefGUID.value = fm.TransRefGUID.value;
	
	fm.TransExeDate.value = '<%=strTransExeDate%>';
	//������ˮ��
	fm.TransRefGUID.value = '<%=PubFun1.CreateMaxNo("TransNo",16)%>';
	//��������
	//fm.RegionCode.value = '00000';
	//�������
	//fm.Branch.value = '11111';
	//��Ա����
	fm.Teller.value = 'AXA_PRO_TEST';
	//Ͷ������ 
	fm.SubmissionDate.value = '<%=strTransExeDate%>';
	//Ͷ�����
	//����ӡˢ��
	fm.ProviderFormNumber.value = '<%=strProviderFormNumber%>';
   
	//Ͷ������Ϣ
	//Ͷ��������
	fm.AppFullName.value = '';
	//Ͷ�����Ա�
	fm.AppGender.value = '';
	//Ͷ����֤������
	fm.AppGovtIDTC.value = '';
	//Ͷ����֤������
	fm.AppGovtID.value = '';
	//Ͷ��������
	fm.AppBirthDate.value = '';
	//Ͷ���˵�������
	fm.AppAddrLine.value = '';
	//Ͷ����ͨѶ��ַ	
	fm.AppLine1.value = '';
	//Ͷ������������
	fm.AppZip.value = '';
	//Ͷ���˼�ͥ�绰
	fm.AppDialNumber1.value = '';
	//Ͷ�����ƶ��绰
	fm.AppDialNumber3.value = '';
	//Ͷ�����뱻���˵Ĺ�ϵ
	fm.AppToInsRelation.value = '';
	
//��������Ϣ
	//����������
	fm.InsFullName.value = '';
	//�������Ա�
	fm.InsGender.value = ''; 
	fm.InsGenderh.value = '';
	//������֤������
	fm.InsGovtIDTC.value = '';
	//������֤������
	fm.InsGovtID.value = '';
	//����������
	fm.InsBirthDate.value = '';
	//�����˵�������
	fm.InsAddrLine.value = '';
	//������ͨѶ��ַ	
	fm.InsLine1.value = ''; 
	//��������������
	fm.InsZip.value = '';
	//�����˼�ͥ�绰 
	fm.InsDialNumber1.value = '';
	//�������ƶ��绰
	fm.InsDialNumber3.value = '';
	//������֪
	fm.HealthIndicator.value = '';
	
//��������Ϣ1
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
	//����ٷ���
	fm.InterestPercent1.value = '';
	//����˳��  
	fm.Sequence1.value = ''; 
	//�������뱻���˵Ĺ�ϵ 
	fm.BnfToInsRelation1.value = '';
	//�������Ƿ�Ϊ������־ 
	fm.BeneficiaryIndicator.value='';  
 
//��������Ϣ2
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
	 
//��������Ϣ3
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
	
//������Ϣ
	//���մ��� 
	fm.ProductCode.value = '';
	//���� 
	fm.IntialNumberOfUnits.value = '';
	//���� 
	fm.InitCovAmt.value = '';
	//���� 
	fm.ModalPremAmt.value = '';
	//������������  
	fm.DurationMode.value = '';
	//�������� 
	fm.Duration.value = '';
	//�ɷ��������� 
	fm.PaymentDurationMode.value = '';
	//�ɷ����� 
	fm.PaymentDuration.value = '';

//��������Ϣ1
	//���մ��� 
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
		
//Ͷ���˻��ڵ�
	fm.AccCode1.value = '';
	fm.AllocPercent1.value = '';
	fm.AccCode2.value = '';
	fm.AllocPercent2.value = '';
	fm.AccCode3.value = '';
	fm.AllocPercent3.value = '';
	
//Ͷ����Ϣ

	//�ɷѷ�ʽ 
	fm.PaymentMode.value = ''; 
	//�������ͷ�ʽ
	fm.PolicyDeliveryMethod.value = ''; 
	//�����˺�  
	fm.AccountNumber.value = '';
	//�״�׷�ӱ���
	fm.FirstSuperaddAmt.value = '';
	//Ͷ�����ڱ�־ 
	fm.InvestDateInd.value = '';
	fm.OccupationIndicator.value = '';	
	
//
	fm.ip.value = '127.0.0.1';
	fm.port.value = '8001';
	//fm.ip.value = '10.26.5.31';
	fm.tranFlagCode.value = '1113'; 
	//fm.tranFlagCodeName.value = '����Լ����';
	}
	catch(re)
	{
		alert("TestYBTContInit.jsp-->initBox �����з����쳣:��ʼ���������!");
	}
}


function initContConcel()
{
try { 
//������Ϣ
	//���ж˽�������
	
	fm.OriginalTransRefGUID.value =  fm.TransRefGUID.value;
	fm.TransExeDate.value = '<%=strTransExeDate%>';
	//������ˮ��
	fm.TransRefGUID.value = '<%=PubFun1.CreateMaxNo("TransNo",16)%>';
	//��������
	fm.RegionCode.value = '<%=tZoneNo%>';
	//�������
	fm.Branch.value = '<%=tNodeNo%>';
	//��Ա����
	fm.Teller.value = 'AXA_PRO_TEST';
	//Ͷ������ 
	fm.SubmissionDate.value = '<%=strTransExeDate%>';
	//Ͷ�����
	//����ӡˢ��
	fm.ProviderFormNumber.value = '';
   
	//Ͷ������Ϣ
	//Ͷ��������
	fm.AppFullName.value = '';
	//Ͷ�����Ա�
	fm.AppGender.value = '';
	//Ͷ����֤������
	fm.AppGovtIDTC.value = '';
	//Ͷ����֤������
	fm.AppGovtID.value = '';
	//Ͷ��������
	fm.AppBirthDate.value = '';
	//Ͷ���˵�������
	fm.AppAddrLine.value = '';
	//Ͷ����ͨѶ��ַ	
	fm.AppLine1.value = '';
	//Ͷ������������
	fm.AppZip.value = '';
	//Ͷ���˼�ͥ�绰
	fm.AppDialNumber1.value = '';
	//Ͷ�����ƶ��绰
	fm.AppDialNumber3.value = '';
	//Ͷ�����뱻���˵Ĺ�ϵ
	fm.AppToInsRelation.value = '';
	
//��������Ϣ
	//����������
	fm.InsFullName.value = '';
	//�������Ա�
	fm.InsGender.value = ''; 
	fm.InsGenderh.value = '';
	//������֤������
	fm.InsGovtIDTC.value = '';
	//������֤������
	fm.InsGovtID.value = '';
	//����������
	fm.InsBirthDate.value = '';
	//�����˵�������
	fm.InsAddrLine.value = '';
	//������ͨѶ��ַ	
	fm.InsLine1.value = ''; 
	//��������������
	fm.InsZip.value = '';
	//�����˼�ͥ�绰 
	fm.InsDialNumber1.value = '';
	//�������ƶ��绰
	fm.InsDialNumber3.value = '';
	//������֪
	fm.HealthIndicator.value = '';
	
//��������Ϣ1
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
	//����ٷ���
	fm.InterestPercent1.value = '';
	//����˳��  
	fm.Sequence1.value = ''; 
	//�������뱻���˵Ĺ�ϵ 
	fm.BnfToInsRelation1.value = '';
	//�������Ƿ�Ϊ������־ 
	fm.BeneficiaryIndicator.value='';  
 
//��������Ϣ2
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
	 
//��������Ϣ3
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
	
//������Ϣ
	//���մ��� 
	fm.ProductCode.value = '';
	//���� 
	fm.IntialNumberOfUnits.value = '';
	//���� 
	fm.InitCovAmt.value = '';
	//���� 
	fm.ModalPremAmt.value = '';
	//������������  
	fm.DurationMode.value = '';
	//�������� 
	fm.Duration.value = '';
	//�ɷ��������� 
	fm.PaymentDurationMode.value = '';
	//�ɷ����� 
	fm.PaymentDuration.value = '';

//��������Ϣ1
	//���մ��� 
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
		
//Ͷ���˻��ڵ�
	fm.AccCode1.value = '';
	fm.AllocPercent1.value = '';
	fm.AccCode2.value = '';
	fm.AllocPercent2.value = '';
	fm.AccCode3.value = '';
	fm.AllocPercent3.value = '';
	
//Ͷ����Ϣ

	//�ɷѷ�ʽ 
	fm.PaymentMode.value = ''; 
	//�������ͷ�ʽ
	fm.PolicyDeliveryMethod.value = ''; 
	//�����˺�  
	fm.AccountNumber.value = '';
	//�״�׷�ӱ���
	fm.FirstSuperaddAmt.value = '';
	//Ͷ�����ڱ�־ 
	fm.InvestDateInd.value = '';
	fm.OccupationIndicator.value = '';	
	
//
	//fm.ip.value = '127.0.0.1';
	fm.ip.value = '127.0.0.1';
	fm.port.value = '8001';
	fm.tranFlagCode.value = '1115'; 
	//fm.tranFlagCodeName.value = '����Լ����';
	}
	catch(re)
	{
		alert("TestYBTContInit.jsp-->initBox �����з����쳣:��ʼ���������!");
	}
}


function initBox()
{
try { 
//������Ϣ
	//���ж˽�������
	fm.TransExeDate.value = '<%=strTransExeDate%>';
	//������ˮ��
	fm.TransRefGUID.value = '<%=PubFun1.CreateMaxNo("TransNo",16)%>';
	//��������
	fm.RegionCode.value = '';
	//�������
	fm.Branch.value = '';
	//��Ա����
	fm.Teller.value = 'AXA_PRO_TEST';
	//Ͷ������ 
	fm.SubmissionDate.value = '<%=strTransExeDate%>';
	//Ͷ�����
	fm.HOAppFormNumber.value = '<%=strHOAppFormNumber%>';
	//����ӡˢ��
	
	fm.ProviderFormNumber.value = '';
   
//Ͷ������Ϣ
	//Ͷ��������
	fm.AppFullName.value = '';
	//Ͷ�����Ա�
	fm.AppGender.value = '';
	//Ͷ����֤������
	fm.AppGovtIDTC.value = '';
	//Ͷ����֤������
	fm.AppGovtID.value = '';
	//Ͷ��������
	fm.AppBirthDate.value = '';
	//Ͷ���˵�������
	fm.AppAddrLine.value = '';
	//Ͷ����ͨѶ��ַ	
	fm.AppLine1.value = '';
	//Ͷ������������
	fm.AppZip.value = '';
	//Ͷ���˼�ͥ�绰
	fm.AppDialNumber1.value = '';
	//Ͷ�����ƶ��绰
	fm.AppDialNumber3.value = '';
	//Ͷ�����뱻���˵Ĺ�ϵ
	fm.AppToInsRelation.value = '';
	
//��������Ϣ
	//����������
	fm.InsFullName.value = '';
	//�������Ա�
	fm.InsGender.value = ''; 
	fm.InsGenderh.value = '';
	//������֤������
	fm.InsGovtIDTC.value = '';
	//������֤������
	fm.InsGovtID.value = '';
	//����������
	fm.InsBirthDate.value = '';
	//�����˵�������
	fm.InsAddrLine.value = '';
	//������ͨѶ��ַ	
	fm.InsLine1.value = ''; 
	//��������������
	fm.InsZip.value = '';
	//�����˼�ͥ�绰 
	fm.InsDialNumber1.value = '';
	//�������ƶ��绰
	fm.InsDialNumber3.value = '';
	//������֪
	fm.HealthIndicator.value = '';
	
//��������Ϣ1
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
	//����ٷ���
	fm.InterestPercent1.value = '';
	//����˳��  
	fm.Sequence1.value = ''; 
	//�������뱻���˵Ĺ�ϵ 
	fm.BnfToInsRelation1.value = '';
	//�������Ƿ�Ϊ������־ 
	fm.BeneficiaryIndicator.value='';  
 
//��������Ϣ2
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
	 
//��������Ϣ3
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
	
//������Ϣ
	//���մ��� 
	fm.ProductCode.value = '';
	//���� 
	fm.IntialNumberOfUnits.value = '';
	//���� 
	fm.InitCovAmt.value = '';
	//���� 
	fm.ModalPremAmt.value = '';
	//������������  
	fm.DurationMode.value = '';
	//�������� 
	fm.Duration.value = '';
	//�ɷ��������� 
	fm.PaymentDurationMode.value = '';
	//�ɷ����� 
	fm.PaymentDuration.value = '';

//��������Ϣ1
	//���մ��� 
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
		
//Ͷ���˻��ڵ�
	fm.AccCode1.value = '';
	fm.AllocPercent1.value = '';
	fm.AccCode2.value = '';
	fm.AllocPercent2.value = '';
	fm.AccCode3.value = '';
	fm.AllocPercent3.value = '';
	
//Ͷ����Ϣ

	//�ɷѷ�ʽ 
	fm.PaymentMode.value = ''; 
	//�������ͷ�ʽ
	fm.PolicyDeliveryMethod.value = ''; 
	//�����˺�  
	fm.AccountNumber.value = '';
	//�״�׷�ӱ���
	fm.FirstSuperaddAmt.value = '';
	//Ͷ�����ڱ�־ 
	fm.InvestDateInd.value = '';
	fm.OccupationIndicator.value = '';	
	
//
	fm.ip.value = '127.0.0.1';
	//fm.ip.value = '<%=tIp%>';
	fm.port.value = '8001';
	fm.tranFlagCode.value = '1013'; 
	//fm.tranFlagCodeName.value = '����Լ����';
	}
	catch(re)
	{
		alert("TestYBTContInit.jsp-->initBox �����з����쳣:��ʼ���������!");
	}
}


function initInputBox() 
{
	try {
//������Ϣ
fm.BranchName.value = "�㷢����-�������˴�֧��";
	fm.RegionCodeName.value = "����";
	//���ж˽�������
	fm.TransExeDate.value = '<%=strTransExeDate%>';
	//������ˮ��
	fm.TransRefGUID.value = '<%=strTransRefGUID%>';
	//���ؽ�����ˮ��
	fm.InputTransRefGUID.value = fm.TransRefGUID.value;
	//�������� 
	fm.RegionCode.value = '<%=tZoneNo%>';
	//�������  
	fm.Branch.value = '<%=tNodeNo%>';
	//��Ա����
	fm.Teller.value = 'AXA_PRO_TEST';
	//Ͷ������ 
	fm.SubmissionDate.value = '<%=strTransExeDate%>';
	//Ͷ�����
	fm.HOAppFormNumber.value = '<%=strHOAppFormNumber%>';
	//����ӡˢ�� 
	fm.ProviderFormNumber.value = '';

fm.OriginalTransRefGUID.value = '';	
//fm.OriginalProviderFormNumber.value = '';	
//fm.PolNumber.value = '';
	
	document.getElementById("PrtNo").disabled=true;
	document.getElementById("OldTransNo").disabled=true;
	//document.getElementById("OldPrtNo").disabled=true;
	//document.getElementById("ContNo").disabled=true;
   
//Ͷ������Ϣ
	//Ͷ��������
	fm.AppFullName.value = 'Ͷ����2';
	//Ͷ�����Ա�
	fm.AppGender.value = '1';
	//Ͷ����֤������
	fm.AppGovtIDTC.value = '0';
	//Ͷ����֤������
	fm.AppGovtID.value = '110101198001010010';
	//Ͷ��������
	fm.AppBirthDate.value = '1980-01-01';
	//Ͷ���˵�������
	fm.AppAddrLine.value = 'zhangsan@163.com';
	//Ͷ����ͨѶ��ַ	 
	fm.AppLine1.value = '�Ϻ����������Ӽ��ߴ�'; 
	//Ͷ������������
	fm.AppZip.value = '666666';
	//Ͷ���˼�ͥ�绰
	fm.AppDialNumber1.value = '02223558550';
	//Ͷ�����ƶ��绰
	fm.AppDialNumber3.value = '15821576966';
	//Ͷ�����뱻���˵Ĺ�ϵ
	fm.AppToInsRelation.value = '2';
	
//��������Ϣ
	//����������
	fm.InsFullName.value = '������2';
	//�������Ա�
	fm.InsGender.value = '1';
	//������֤������
	fm.InsGovtIDTC.value = '0';
	//������֤������
	fm.InsGovtID.value = '110101198001010037';
	//����������
	fm.InsBirthDate.value = '1980-01-01';
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
	fm.BnfToInsRelation1.value = '2';
	//�������Ƿ�Ϊ������־ 
	fm.BeneficiaryIndicator.value='N';  
 
//��������Ϣ2
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
	 
//��������Ϣ3
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
	
//������Ϣ
	//���մ��� 
	fm.ProductCode.value = '003';
	//���� 
	fm.IntialNumberOfUnits.value = '1';
	//���� 
	fm.InitCovAmt.value = '1000000';
	//���� 
	fm.ModalPremAmt.value = '298810';
	//������������  
	fm.DurationMode.value = '1';
	//�������� 
	fm.Duration.value = '100';
	//�ɷ��������� 
	fm.PaymentDurationMode.value = '2';
	//�ɷ����� 
	fm.PaymentDuration.value = '10';

//��������Ϣ1
	//���մ��� 
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

//Ͷ���˻��ڵ�
	fm.AccCode1.value = '';
	fm.AllocPercent1.value = '';
	fm.AccCode2.value = '';
	fm.AllocPercent2.value = '';
	fm.AccCode3.value = '';
	fm.AllocPercent3.value = '';

	
//Ͷ����Ϣ

	//�ɷѷ�ʽ
	fm.PaymentMode.value = '1';
	//�������ͷ�ʽ
	fm.PolicyDeliveryMethod.value = '4'; 
	//�����˺�
	fm.AccountNumber.value = '<%=strAccNo%>';
	//�״�׷�ӱ���
	fm.FirstSuperaddAmt.value = '';
	//Ͷ�����ڱ�־
	fm.InvestDateInd.value = '';
	fm.OccupationIndicator.value='N';
// 
	fm.ip.value = '127.0.0.1';
	//fm.ip.value = '<%=tIp%>';
	fm.port.value = '8001';
	fm.tranFlagCode.value = '1013'; 
//fm.tranFlagCodeName.value = '����Լ����';
	}
	catch(re)
	{
		alert("TestYBTContInit.jsp-->initInputBox �����з����쳣:��ʼ���������!");
	}
}

</script>
