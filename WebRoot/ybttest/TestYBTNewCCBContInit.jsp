<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%@page import="com.sinosoft.lis.pubfun.*" %>
<% 
	//�������ƣ�TestYBTContInit.jsp
	//�����ܣ�
	//�������ڣ�2010-01-20 16:43:36
	//������  ��
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%  
	PubFun1 pubfun = new PubFun1();
	String strTransExeDate = DateUtil.getCur10Date();
	String strTransRefGUID = pubfun.CreateMaxNo("TransNo",16);
	String strHOAppFormNumber = "2104121"+pubfun.CreateMaxNo("PrtNo",8);
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
		alert("TestYBTNewCCBContInit.jsp-->InitForm �����з����쳣:��ʼ���������!");
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
	fm.RegionCode.value = '3306';
	//�������  
	fm.Branch.value = '13535';
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
	fm.ProviderFormNumber.value = '';
	//��������
	fm.TransMode.value=  '00';
	//���շֹ�˾����
	fm.BranthCmp.value='310IF10016';
	//������Ա����
	fm.BkRckrNo.value=  '1008611';
	//������Ա����
	fm.BkSaleName.value='���Ȼ�';
	//������Ա�ʸ�֤����
	fm.BkSaleCertNo.value='1008610010';
	
	fm.Cst_Rsk_Tlrnc_Cpy_Cd.value = '01';
	fm.Rsk_Evlt_AvlDt.value = '20160707';
	fm.Bdgt_Amt.value = '10000.00';
   
//Ͷ������Ϣ
	//Ͷ��������
	fm.AppFullName.value = 'Ͷ����8';
	//Ͷ�����Ա�
	fm.AppGender.value = '1';
	//Ͷ����֤������
	fm.AppGovtIDTC.value = '1022';
	//Ͷ����֤������
	fm.AppGovtID.value = '3103148222222';
	//Ͷ��������
	fm.AppBirthDate.value = '1965-09-17';
	//Ͷ����֤����Ч����
	fm.AppGovtEfcDate.value = '2005-09-17';
	//Ͷ����֤��ʧЧ����
	fm.AppGovtTermDate.value = '2020-09-17';
	//Ͷ���˵�������
	fm.AppAddrLine.value = 'zhangsan@163.com';
	//Ͷ����ͨѶ��ַ	 
	fm.AppLine1.value = '�Ϻ����������Ӽ��ߴ�'; 
	//Ͷ����������
	fm.PbInCome.value = '15000000';
	//Ͷ���˼�ͥ������
	fm.PbHomeInCome.value = '25000000';	
	//Ͷ������������
	fm.AppZip.value = '333333';
	//Ͷ���˹̶��绰
	fm.AppDialNumber1.value = '02123558550';
	//Ͷ�����ƶ��绰
	fm.AppDialNumber3.value = '15821576966';
	//Ͷ�����뱻���˵Ĺ�ϵ
	fm.AppToInsRelation.value = '0133015';
	//����������
 	fm.RspbName.value = 'ë����';
	
	fm.AppCountry.value='0156';
	fm.ApplJobCode.value='3010101';
	
	fm.Plchd_Prov_Cd.value='340000';
	fm.Plchd_City_Cd.value='340100';
	fm.Plchd_CntyAndDstc_Cd.value='340103';
	fm.Plchd_Dtl_Adr_Cntnt.value='������·305��';
	
//��������Ϣ
	//����������
	fm.InsFullName.value = '������'+<%=InsNumber%>;
	//��������������ȫ��
	fm.Rcgn_CPA_FullNm.value = 'Bei Baoren'+<%=InsNumber%>;
	//�������Ա�
	fm.InsGender.value = '1';
	//������֤������
	fm.InsGovtIDTC.value = '1022';
	//������֤������
	fm.InsGovtID.value = '314314232323238';
	//����������
	fm.InsBirthDate.value = '1985-09-17';
	//������֤����Ч����
	fm.InsGovtEfcDate.value = '2005-09-17';
	//������֤��ʧЧ����
	fm.InsGovtExpDate.value = '2020-09-17';
	//�����˵�������
	fm.InsAddrLine.value = 'lisi@163.com';
	//������ͨѶ��ַ	
	fm.InsLine1.value = '����к�ƽ��ͬ����'; 
	//��������������
	fm.InsZip.value = '555555';
	//�����˹̶��绰 
	fm.InsDialNumber1.value = '02153558550';
	//�������ƶ��绰
	fm.InsDialNumber3.value = '13821576911';
	//������ǰ��Ŀ�ĵظ���
	fm.DesNum.value = '1';
	//�������ƶ��绰
	fm.Destinations.value = '�ൺ';
	//������������
	fm.InsInCome.value = '12000000';
	//������֪
	fm.HealthIndicator.value = '0';
	fm.InsCountry.value='0156';
	fm.InsuJobCode.value='3010101';
	
	fm.Rcgn_Prov_Cd.value='340000';
	fm.Rcgn_City_Cd.value='340100';
	fm.Rcgn_CntyAndDstc_Cd.value='340103';
	fm.Rcgn_Dtl_Adr_Cntnt.value='������·305��';
	
	//���������������ʹ���
	fm.AgIns_Benf_TpCd.value = '1';
//��������Ϣ1 
	//����������
	fm.BnfFullName1.value = '����';
	//�������Ա� 
	fm.BnfGender1.value = '1';
	//������֤������
	fm.BnfGovtIDTC1.value = '1022';
	//������֤������
	fm.BnfGovtID1.value = '3103168';
	//������֤����Ч���� 
	fm.bnfappbgndate1.value = '1990-01-01';
	//������֤��ʧЧ���� 
	fm.bnfappvlddate1.value = '2020-01-01';
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
	//������֤����Ч���� 
	fm.bnfappbgndate2.value = '';
	//������֤��ʧЧ���� 
	fm.bnfappvlddate2.value = '';
	//�����˳�������
	fm.BnfBirthDate2.value = '';
	//������ͨѶ��ַ
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
	//������֤����Ч���� 
	fm.bnfappbgndate3.value = '';
	//������֤��ʧЧ���� 
	fm.bnfappvlddate3.value = '';
	//������ͨѶ��ַ
	fm.BnfAdress3.value = '';
	//�����˳�������
	fm.BnfBirthDate3.value = '';
	
	//����ٷ���
	fm.InterestPercent3.value = '';
	//����˳�� 
	fm.Sequence3.value = '';
	//�������뱻���˵Ĺ�ϵ
	fm.BnfToInsRelation3.value = '';
	
	//Ͷ��������Ϣ 
	fm.Ins_Scm_Inf.value = '��Ͷ��������Ϣ';
	//�������ײͱ�� 
	fm.AgIns_Pkg_ID.value = 'C2';
	//��ѡ������ʽ�� 
	fm.Opt_Part_DieIns_Amt.value = '1000000';
	//�״ζ���׷�ӱ��� 
	fm.FTm_Extr_Adl_InsPrem.value = '80000';
	//������ϵ�� 
	fm.Emgr_CtcPsn.value = '������';
	//������ϵ�� 
	fm.Emgr_Ctc_Tel.value = '13812345678';
	//������ϵ���뱻���˵Ĺ�ϵ
	fm.AppToInsRelation.value = '28';

	//�������ײͱ�� 
	fm.AgIns_Pkg_ID.value = '';
//������Ϣ
	//���մ��� 
	fm.ProductCode.value = '241201';
	//��������
	fm.RiskCodeName.value = '�к���Ӯ�Ƹ���ȫ���գ������ͣ�A��';
	//���� 
	fm.IntialNumberOfUnits.value = '1';
	//���� 
	fm.InitCovAmt.value = '30000';
	//���� 
	fm.ModalPremAmt.value = '30000';
	//������������  
	fm.DurationMode.value = '2';
	//�������� 
	fm.Duration.value = '10';
	//�ɷ��������� 
	fm.PaymentDurationMode.value = '5';
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
	fm.PaymentMode.value = '02';
	//���ѷ�ʽ
	fm.PaymentMethod.value = '3';     
	//��ȡ��ʽ  
	fm.BenefitMode.value = '3'; 
	//������ȡ��ʽ  
	fm.DivType.value = '2';
	//�������ͷ�ʽ
	fm.PolicyDeliveryMethod.value = '1'; 
	//�����˺�  
	fm.AccountNumber.value = '01234567890123';
	fm.AcctHolderName.value = fm.AppFullName.value;
	//�ر�Լ�� 
	fm.SpecialClause.value = 'SpecialClause';	
	fm.OccupationIndicator.value='N';
	//Ͷ�����ڱ�־
	fm.InvestDateInd.value = '4';
	//�״�׷�ӱ���
	fm.FirstSuperaddAmt.value = '';
	
// 
	fm.ip.value = '127.0.0.1';
	fm.port.value = '39871';
	fm.tranFlagCode.value = 'P53819113'; 
	}
	catch(re)
	{
		alert("TestYBTNewCCBContInit.jsp-->initInputBox �����з����쳣:��ʼ���������!");
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
	//������Ա����
	fm.BkRckrNo.value=  '';
	//������Ա����
	fm.BkSaleName.value='';
	//������Ա�ʸ�֤����
	fm.BkSaleName.value='';
   
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
	//Ͷ���˹̶��绰
	fm.AppDialNumber1.value = '';
	//Ͷ�����ƶ��绰
	fm.AppDialNumber3.value = '';
	//Ͷ�����뱻���˵Ĺ�ϵ
	fm.AppToInsRelation.value = '';
	//����������
	fm.RspbName.value = '';
	//������ǰ��Ŀ�ĵظ���
	fm.DesNum.value = '';
	//�������ƶ��绰
	fm.Destinations.value = '';
	//Ͷ����������
	fm.PbInCome.value = '';
	//Ͷ���˼�ͥ������
	fm.PbHomeInCome.value = '';
	
	fm.Plchd_Prov_Cd.value='';
	fm.Plchd_City_Cd.value='';
	fm.Plchd_CntyAndDstc_Cd.value='';
	fm.Plchd_Dtl_Adr_Cntnt.value='';
	
//��������Ϣ
	//����������
	fm.InsFullName.value = '';
	//����������
	fm.Rcgn_CPA_FullNm.value = '';
	//�������Ա�
	fm.InsGender.value = ''; 
//	fm.InsGenderh.value = '';
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
	//�����˹̶��绰 
	fm.InsDialNumber1.value = '';
	//�������ƶ��绰
	fm.InsDialNumber3.value = '';
	//������֪
	fm.HealthIndicator.value = '';
	
	fm.Plchd_Prov_Cd.value='340000';
	fm.Plchd_City_Cd.value='340100';
	fm.Plchd_CntyAndDstc_Cd.value='340103';
	fm.Plchd_Dtl_Adr_Cntnt.value='������·305��';

	//���������������ʹ���
	fm.AgIns_Benf_TpCd.value = '';
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

	//Ͷ��������Ϣ 
	fm.Ins_Scm_Inf.value = '';
	//�������ײͱ�� 
	fm.AgIns_Pkg_ID.value = '';
	//��ѡ������ʽ�� 
	fm.Opt_Part_DieIns_Amt.value = '';
	//�״ζ���׷�ӱ��� 
	fm.FTm_Extr_Adl_InsPrem.value = '';
	//������ϵ�� 
	fm.Emgr_CtcPsn.value = '';
	//������ϵ�� 
	fm.Emgr_Ctc_Tel.value = '';
	
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
	fm.port.value = '39871'; 
	fm.tranFlagCode.value = 'P53819113'; 
	}
	catch(re)
	{
		alert("TestYBTCNewCBContInit.jsp-->initBox �����з����쳣:��ʼ���������!");
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
		alert("TestYBTNewCCBContInit.jsp-->initBox �����з����쳣:��ʼ���������!");
	}
}



</script>
