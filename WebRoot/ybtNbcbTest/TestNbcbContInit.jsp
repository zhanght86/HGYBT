<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%@page import="com.sinosoft.lis.pubfun.*" %>
<% 
	//�������ƣ�TestYBTContInit.jsp
	//�����ܣ����ȷ�� 
	//�������ڣ�2014-07-07 16:43:36
	//������  ����С�� 
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%  
	PubFun1 pubfun = new PubFun1();
	String strTransExeDate = DateUtil.getCur10Date();
	String strTransRefGUID = pubfun.CreateMaxNo("TransNo",16);
	String strHOAppFormNumber = "21041410000"+pubfun.CreateMaxNo("PrtNo",4);
	String InsNumber=pubfun.CreateMaxNo("InsNumber",3);
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
		alert("TestYBTCCBContInit.jsp-->InitForm �����з����쳣:��ʼ���������!");
	}
}



function initInputBox() 
{
	try {
	//������Ϣ
	//���ж˽�������
	fm.TransExeDate.value = '<%=strTransExeDate%>';
	//������ˮ��
	fm.TransRefGUID.value = '<%=strTransRefGUID%>';
	//�������� 
	fm.RegionCode.value = '01500';
	//�������  
	fm.Branch.value = '01201';
	//��Ա����
	fm.Teller.value = '3104417';
	//Ͷ������ 
	fm.SubmissionDate.value = '<%=strTransExeDate%>';
	//Ͷ�����
	fm.HOAppFormNumber.value = '<%=strHOAppFormNumber%>';
	//ԭ������ˮ��
	fm.InputTransrNo.value = fm.TransRefGUID.value;
	
	fm.ReqsrNo.value ='';
	//����ӡˢ��
	fm.ProviderFormNumber.value = '010114107059998';
	//��������
	fm.TransMode.value=  '00';
	//���շֹ�˾����
	fm.BranthCmp.value='310IF10016';
	fm.BkRckrNo.value='1001010086';
	fm.BkSaleName.value='���㲨';
	fm.BkSaleCertNo.value='10101010';
   
//Ͷ������Ϣ
	//Ͷ��������
	fm.AppFullName.value = 'Ͷ����7';
	//Ͷ�����Ա�
	fm.AppGender.value = '2';
	//Ͷ����֤������
	fm.AppGovtIDTC.value = '4';
	//Ͷ����֤������
	fm.AppGovtID.value = '3103148222234';
	//Ͷ��������
	fm.AppBirthDate.value = '1965-09-17';
	//Ͷ����֤����Ч��
	fm.AppGovtTermDate.value = '2020-09-17';
	//Ͷ���˵�������
	fm.AppAddrLine.value = 'zhangsan@163.com';
	//Ͷ����ͨѶ��ַ	 
	fm.AppLine1.value = '�Ϻ����������Ӽ��ߴ�'; 
		
	//Ͷ������������
	fm.AppZip.value = '333333';
	//Ͷ���˼�ͥ�绰
	fm.AppDialNumber1.value = '02223558550';
	//Ͷ�����ƶ��绰
	fm.AppDialNumber3.value = '15821576966';
	//Ͷ�����뱻���˵Ĺ�ϵ
	fm.AppToInsRelation.value = '1';
	
	fm.AppCountry.value='0156';
	fm.ApplJobCode.value='3010101';
	fm.PbInCome.value='12000000';
	fm.PbHomeInCome.value='12000000';	
	
//��������Ϣ
	//����������
	fm.InsFullName.value = '������'+<%=InsNumber%>;
	//�������Ա�
	fm.InsGender.value = '1';
	//������֤������
	fm.InsGovtIDTC.value = '2';
	//������֤������
	fm.InsGovtID.value = '314314834324222';
	//����������
	fm.InsBirthDate.value = '1985-09-17';
	//������֤����Ч��
	fm.InsGovtTermDate.value = '2020-09-17';
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
	fm.HealthIndicator.value = '0';
	fm.InsCountry.value='0156';
	fm.InsuJobCode.value='3010101';
	
//��������Ϣ1 
	//����������
	fm.BnfFullName1.value = '����';
	//�������Ա� 
	fm.BnfGender1.value = '1';
	//������֤������
	fm.BnfGovtIDTC1.value = '2';
	//������֤������
	fm.BnfGovtID1.value = '3103323232168';
	//�����˳�������
	fm.BnfBirthDate1.value = '1985-09-17';
	//������ͨѶ��ַ
	fm.BnfAdress1.value = '����к�ƽ��ͬ����';
	//����ٷ��� 
	fm.InterestPercent1.value = '100';
	//����˳��  
	fm.Sequence1.value = '1'; 
	//�������뱻���˵Ĺ�ϵ  
	fm.BnfToInsRelation1.value = '4';
	//�������Ƿ�Ϊ������־ 
	fm.BeneficiaryIndicator.value='0';  
	//����������
	fm.BenefiType1.value='1';
 
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
	//������֤����Ч��
	fm.BnfAdress2.value = '';
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
	//������֤����Ч��
	fm.BnfAdress3.value = '';
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
	fm.ProductCode.value = '03000010';
	//��������
	fm.RiskCodeName.value = '';
	//���� 
	fm.IntialNumberOfUnits.value = '1';
	//���� 
	fm.InitCovAmt.value = '50000000';
	//���� 
	fm.ModalPremAmt.value = '3000000';
	//������������  
	fm.DurationMode.value = '2';
	//�������� 
	fm.Duration.value = '10';
	//�ɷ��������� 
	fm.PaymentDurationMode.value = '1';
	//�ɷ����� 
	fm.PaymentDuration.value = '1';
	
	fm.AutoPayFlag.value = '0';

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
	
		
//��������Ϣ2
	//���մ��� 
	fm.ProductCode2.value = '';
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
	
//Ͷ���˻��ڵ�
	fm.AccCode1.value = '';
	fm.AllocPercent1.value = '';
	fm.AccCode2.value = '';
	fm.AllocPercent2.value = '';
	fm.AccCode3.value = '';
	fm.AllocPercent3.value = '';
	fm.AccCode4.value = '';
	fm.AllocPercent4.value = '';
	fm.AccCode5.value = '';
	fm.AllocPercent5.value = '';
	
	
	
//Ͷ����Ϣ

	//�ɷѷ�ʽ 
	fm.PaymentMode.value = '1';
	//���ѷ�ʽ
	fm.PaymentMethod.value = '3';     
	//��ȡ��ʽ  
	fm.BenefitMode.value = '3'; 
	//������ȡ��ʽ  
	fm.DivType.value = '';
	//�������ͷ�ʽ
	fm.PolicyDeliveryMethod.value = '1'; 
	//�����˺�  
	fm.AccountNumber.value = '6227327856783423';
	fm.AcctHolderName.value = 'Ͷ����1';
	//�ر�Լ�� 
	fm.SpecialClause.value = 'SpecialClause';	
	fm.OccupationIndicator.value='N';
	//Ͷ�����ڱ�־
	fm.InvestDateInd.value = '4';
	//�״�׷�ӱ���
	fm.FirstSuperaddAmt.value = '';
	
// 
	fm.ip.value = '127.0.0.1';
	fm.port.value = '35003';
	fm.tranFlagCode.value = '1'; 
	}
	catch(re)
	{
		alert("TestYBTCCBContInit.jsp-->initInputBox �����з����쳣:��ʼ���������!");
	}
}


function initBox()
{
try { 
//������Ϣ
	//���ж˽�������
	fm.TransExeDate.value = '<%=strTransExeDate%>';
	//������ˮ��
	fm.TransRefGUID.value = '<%=strTransRefGUID%>';
	//�������� 
	fm.RegionCode.value = '310999';
	//�������  
	fm.Branch.value = '322989036';
	//��Ա����
	fm.Teller.value = '3104417';
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
		
//��������Ϣ2
	//���մ��� 
	fm.ProductCode2.value = '';
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
	
	fm.AutoPayFlag.value = '0';
//Ͷ���˻��ڵ�
	fm.AccCode1.value = '';
	fm.AllocPercent1.value = '';
	fm.AccCode2.value = '';
	fm.AllocPercent2.value = '';
	fm.AccCode3.value = '';
	fm.AllocPercent3.value = '';
	fm.AccCode4.value = '';
	fm.AllocPercent4.value = '';
	fm.AccCode5.value = '';
	fm.AllocPercent5.value = '';
	
//Ͷ����Ϣ

	//�ɷѷ�ʽ 
	fm.PaymentMode.value = '';
	//�ɷ���ʽ(Ƶ��)
	fm.PaymentMethod.value = '';     
	//��ȡ��ʽ  
	fm.BenefitMode.value = ''; 
	//������ȡ��ʽ  
	fm.DivType.value = '';
	//�������ͷ�ʽ
	fm.PolicyDeliveryMethod.value = ''; 
	//�����˺�  
	fm.AccountNumber.value = '';
	//�ʻ�����
	fm.AcctHolderName.value = '';
	//�ر�Լ�� 
	fm.SpecialClause.value = '';
	fm.OccupationIndicator.value = '';	
	//Ͷ�����ڱ�־ 
	fm.InvestDateInd.value = '';
	//�״�׷�ӱ���
	fm.FirstSuperaddAmt.value = '';
	
//
	fm.ip.value = '127.0.0.1';
	fm.port.value = '35003'; 
	fm.tranFlagCode.value = '1'; 
	}
	catch(re)
	{
		alert("TestYBTCCBContInit.jsp-->initBox �����з����쳣:��ʼ���������!");
	}
}
	function initContConfirm()
{
try { 
//������Ϣ
	fm.ReqsrNo.value = fm.InputTransrNo.value;
	//���ж˽�������
	fm.TransRefGUID.value = '<%=pubfun.CreateMaxNo("TransNo",16)%>';
	}
	catch(re)
	{
		alert("TestYBTCCBContInit.jsp-->initBox �����з����쳣:��ʼ���������!");
	}
}

    

</script>
