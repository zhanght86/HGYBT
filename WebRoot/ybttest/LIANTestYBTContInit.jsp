<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%@page import="com.sinosoft.lis.pubfun.*" %>
<%@page import="org.jdom.*" %>
<%@page import="com.sinosoft.midplat.MidplatConf" %>
<% 
	//�������ƣ�LIANTestYBTContInit.jsp
	//�����ܣ����ȷ�� 
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%  
	PubFun1 pubfun = new PubFun1();
	String strBankDate = DateUtil.getCur10Date();
	String strTransrNo = PubFun1.CreateMaxNo("TransNo",16);
	String strProposalContNo = "2105131"+PubFun1.CreateMaxNo("ProNo",8);
	String strPrtNo = "";
	String strAppntName = "Appnt"+PubFun1.CreateMaxNo("AppntName",5);
	String strInsuName = "Insu"+PubFun1.CreateMaxNo("InsuName",5);
	Element mTestUI = 
  		 MidplatConf.newInstance().getConf().getRootElement().getChild("ABCTestUI");
  	 String tIp = mTestUI.getAttributeValue("ip");
  	 String tPort = mTestUI.getAttributeValue("port");
  	 String tPortName = mTestUI.getAttributeValue("portname");
  	 
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
		alert("LIANTestYBTContInit.jsp-->InitForm �����з����쳣:��ʼ���������!");
	}
}



function initContConfirm()
{
try { 
//������Ϣ
	
	
	fm.ReqsrNo.value = fm.InputTransrNo.value;
	
	//���ж˽�������
	fm.BankDate.value = '<%=strBankDate%>';
	//������ˮ��
	fm.TransrNo.value = '<%=PubFun1.CreateMaxNo("TransrNo",16)%>';
	//��������
	//fm.ZoneNo.value = '1101';
	//�������
	//fm.BrNo.value = '0102';
	//��Ա����
	//fm.TellerNo.value = '00001';
	//Ͷ������ 
	//fm.PolApplyDate.value = '';
	//Ͷ�����
	//fm.ProposalContNo.value = '';
	//����ӡˢ��
	fm.PrtNo.value = '';
   
	//Ͷ������Ϣ
	//Ͷ��������
	fm.ApplName.value = '';
	//Ͷ�����Ա�
	fm.ApplSex.value = '';
	//Ͷ����֤������
	fm.ApplIDType.value = '';
	//Ͷ����֤������
	fm.ApplIDNo.value = '';
	//Ͷ��������
	fm.ApplBirthday.value = '';
	//Ͷ���˵�������
	fm.ApplEmail.value = '';
	//Ͷ����ͨѶ��ַ	
	fm.ApplAddress.value = '';
	//Ͷ������������
	fm.ApplZipCode.value = '';
	//Ͷ���˼�ͥ�绰
	fm.ApplPhone.value = '';
	//Ͷ�����ƶ��绰
	fm.ApplMobile.value = '';
	//Ͷ�����뱻���˵Ĺ�ϵ
	fm.ApplRelaToInsured.value = '';
	//Ͷ����ְҵ����
	fm.ApplJobCode.value = '';
	fm.ApplValidYear.value='';
	
//��������Ϣ
	//����������
	fm.InsuName.value = '';
	//�������Ա�
	fm.InsuSex.value = ''; 
	fm.InsuSexh.value = '';
	//������֤������
	fm.InsuIDType.value = '';
	fm.InsuIDTypeh.value = '';
	//������֤������
	fm.InsuIDNo.value = '';
	//����������
	fm.InsuBirthday.value = '';
	//�����˵�������
	fm.InsuEmail.value = '';
	//������ͨѶ��ַ	
	fm.InsuAddress.value = ''; 
	//��������������
	fm.InsuZipCode.value = '';
	//�����˼�ͥ�绰 
	fm.InsuPhone.value = '';
	//�������ƶ��绰
	fm.InsuMobile.value = '';
	//������֪
	fm.HealthFlag.value = '';
	//������ְҵ����
	fm.InsuJobCode.value = '';
	fm.InsuValidYear.value='';
	
//��������Ϣ1
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
	//fm.BeneficiaryIndicator.value='';  
 
//��������Ϣ2
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
	 
//��������Ϣ3
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
	
//������Ϣ
	//���մ��� 
	fm.Code.value = '';
	//���� 
	fm.Mult.value = '';
	//���� 
	fm.Prem.value = '';
	//���� 
	fm.Amnt.value = '';
	//������������  
	fm.InsuYearFlag.value = '';
	//�������� 
	fm.InsuYear.value = '';
	//�ɷ��������� 
	fm.PayEndYearFlag.value = '';
	//�ɷ����� 
	fm.PayEndYear.value = '';

//��������Ϣ1
	//���մ��� 
	//fm.Code1.value = '';
	//���� 
	//fm.Mult1.value = '';
	//���� 
	//fm.Prem1.value = '';
	//���� 
	//fm.Amnt1.value = '';
	//������������  
	//fm.InsuYearFlag1.value = '';
	//�������� 
	//fm.InsuYear1.value = '';
	//�ɷ��������� 
	//fm.PayEndYearFlag1.value = '';
	//�ɷ����� 
	//fm.PayEndYear1.value = '';
		
//Ͷ���˻��ڵ�
	//fm.AccNo1.value = '';
	//fm.Rate1.value = '';
	//fm.AccNo2.value = '';
	//fm.Rate2.value = '';
	//fm.AccNo3.value = '';
	//fm.Rate3.value = '';
	
//Ͷ����Ϣ

	//�ɷѷ�ʽ 
	fm.PayIntv.value = ''; 
	//�������ͷ�ʽ
	//fm.PolicyDeliveryMethod.value = ''; 
	//�����˺�  
	fm.AccNo.value = '';
	//�����˺�����
	fm.AccName.value = '';
	//������ȡ��ʽ
	fm.BonusGetMode.value = '';
	//�״�׷�ӱ���
	//fm.FirstSuperaddAmt.value = '';
	//Ͷ�����ڱ�־ 
	//fm.InvestDateInd.value = '';
	//fm.OccupationIndicator.value = '';	
	
//
	fm.ip.value = '<%=tIp%>';
	fm.port.value = '<%=tPort%>';
	fm.portName.value = '<%=tPortName%>';
	//fm.ip.value = '10.26.5.31';
	fm.tranFlagCode.value = '02'; 
	//fm.tranFlagCodeName.value = '����Լ����';
	}
	catch(re)
	{
		alert("LIANTestYBTContInit.jsp-->initBox �����з����쳣:��ʼ���������!");
	}
}


function initContConcel()
{
try { 
//������Ϣ
	//���ж˽�������
	
	fm.OriginalTransrNo.value = fm.InputTransrNo.value;
	fm.BankDate.value = '<%=strBankDate%>';
	//������ˮ��
	fm.TransrNo.value = '<%=strTransrNo%>';
	//��������
	fm.ZoneNo.value = '1101';
	//�������
	fm.BrNo.value = '0102';
	//��Ա����
	fm.TellerNo.value = '00001';
	//Ͷ������ 
	fm.PolApplyDate.value = '<%=strBankDate%>';
	//Ͷ�����
	//����ӡˢ��
	fm.PrtNo.value = '<%=strPrtNo%>';
   
	//Ͷ������Ϣ
	//Ͷ��������
	fm.ApplName.value = '';
	//Ͷ�����Ա�
	fm.ApplSex.value = '';
	//Ͷ����֤������
	fm.ApplIDType.value = '';
	//Ͷ����֤������
	fm.ApplIDNo.value = '';
	//Ͷ��������
	fm.ApplBirthday.value = '';
	//Ͷ���˵�������
	fm.ApplEmail.value = '';
	//Ͷ����ͨѶ��ַ	
	fm.ApplAddress.value = '';
	//Ͷ������������
	fm.ApplZipCode.value = '';
	//Ͷ���˼�ͥ�绰
	fm.ApplPhone.value = '';
	//Ͷ�����ƶ��绰
	fm.ApplMobile.value = '';
	//Ͷ�����뱻���˵Ĺ�ϵ
	fm.ApplRelaToInsured.value = '';
	//Ͷ����ְҵ����
	fm.ApplJobCode.value = '';
	
//��������Ϣ
	//����������
	fm.InsuName.value = '';
	//�������Ա�
	fm.InsuSex.value = ''; 
	fm.InsuSexh.value = '';
	//������֤������
	fm.InsuIDType.value = '';
	fm.InsuIDTypeh.value = '';
	//������֤������
	fm.InsuIDNo.value = '';
	//����������
	fm.InsuBirthday.value = '';
	//�����˵�������
	fm.InsuEmail.value = '';
	//������ͨѶ��ַ	
	fm.InsuAddress.value = ''; 
	//��������������
	fm.InsuZipCode.value = '';
	//�����˼�ͥ�绰 
	fm.InsuPhone.value = '';
	//�������ƶ��绰
	fm.InsuMobile.value = '';
	//������֪
	fm.HealthFlag.value = '';
	//������ְҵ����
	fm.InsuJobCode.value = '';
	
//��������Ϣ1
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
	//fm.BeneficiaryIndicator.value='';  
 
//��������Ϣ2
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
	 
//��������Ϣ3
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
	
//������Ϣ
	//���մ��� 
	fm.Code.value = '';
	//���� 
	fm.Mult.value = '';
	//���� 
	fm.Prem.value = '';
	//���� 
	fm.Amnt.value = '';
	//������������  
	fm.InsuYearFlag.value = '';
	//�������� 
	fm.InsuYear.value = '';
	//�ɷ��������� 
	fm.PayEndYearFlag.value = '';
	//�ɷ����� 
	fm.PayEndYear.value = '';

//��������Ϣ1
	//���մ��� 
	fm.Code1.value = '';
	//���� 
	fm.Mult1.value = '';
	//���� 
	fm.Prem1.value = '';
	//���� 
	fm.Amnt1.value = '';
	//������������  
	fm.InsuYearFlag1.value = '';
	//�������� 
	fm.InsuYear1.value = '';
	//�ɷ��������� 
	fm.PayEndYearFlag1.value = '';
	//�ɷ����� 
	fm.PayEndYear1.value = '';
		
//Ͷ���˻��ڵ�
	//fm.AccNo1.value = '';
	//fm.Rate1.value = '';
	//fm.AccNo2.value = '';
	//fm.Rate2.value = '';
	//fm.AccNo3.value = '';
	//fm.Rate3.value = '';
	
//Ͷ����Ϣ

	//�ɷѷ�ʽ 
	fm.PayIntv.value = ''; 
	//�������ͷ�ʽ
	//fm.PolicyDeliveryMethod.value = ''; 
	//�����˺�  
	fm.AccNo.value = '';
	//�����˺�����
	fm.AccName.value = '';
	//������ȡ��ʽ
	fm.BonusGetMode.value = '';
	//�״�׷�ӱ���
	//fm.FirstSuperaddAmt.value = '';
	//Ͷ�����ڱ�־ 
	//fm.InvestDateInd.value = '';
	//fm.OccupationIndicator.value = '';	
	
//
	fm.ip.value = '<%=tIp%>';
	fm.port.value = '<%=tPort%>';
	fm.portName.value = '<%=tPortName%>';
	//fm.ip.value = '10.26.5.31';
	fm.tranFlagCode.value = '03'; 
	//fm.tranFlagCodeName.value = '����Լ����';
	}
	catch(re)
	{
		alert("LIANTestYBTContInit.jsp-->initBox �����з����쳣:��ʼ���������!");
	}
}


function initBox()
{
try { 	
//������Ϣ
	//���ж˽�������
	fm.BankDate.value = '<%=strBankDate%>';
	//������ˮ��
	fm.TransrNo.value= '<%=strTransrNo%>';
	//��������
	fm.ZoneNo.value = '';
	//�������
	fm.BrNo.value = '';
	//��Ա����
	fm.TellerNo.value = '';
	//Ͷ������ 
	fm.PolApplyDate.value = '<%=strBankDate%>';
	//Ͷ�����
	fm.ProposalContNo.value = '<%= strProposalContNo%>';
	//����ӡˢ��
	
	fm.PrtNo.value = '';
   
//Ͷ������Ϣ
	//Ͷ��������
	fm.ApplName.value = '';
	//Ͷ�����Ա�
	fm.ApplSex.value = '';
	//Ͷ����֤������
	fm.ApplIDType.value = '';
	//Ͷ����֤������
	fm.ApplIDNo.value = '';
	//Ͷ��������
	fm.ApplBirthday.value = '';
	//Ͷ���˵�������
	fm.ApplEmail.value = '';
	//Ͷ����ͨѶ��ַ	
	fm.ApplAddress.value = '';
	//Ͷ������������
	fm.ApplZipCode.value = '';
	//Ͷ���˼�ͥ�绰
	fm.ApplPhone.value = '';
	//Ͷ�����ƶ��绰
	fm.ApplMobile.value = '';
	//Ͷ�����뱻���˵Ĺ�ϵ
	fm.ApplRelaToInsured.value = '';
	//Ͷ����ְҵ����
	fm.ApplJobCode.value = '';
	
//��������Ϣ
	//����������
	fm.InsuName.value = '';
	//�������Ա�
	fm.InsuSex.value = ''; 
	fm.InsuSexh.value = '';
	//������֤������
	fm.InsuIDType.value = '';
	fm.InsuIDTypeh.value = '';
	//������֤������
	fm.InsuIDNo.value = '';
	//����������
	fm.InsuBirthday.value = '';
	//�����˵�������
	fm.InsuEmail.value = '';
	//������ͨѶ��ַ	
	fm.InsuAddress.value = ''; 
	//��������������
	fm.InsuZipCode.value = '';
	//�����˼�ͥ�绰 
	fm.InsuPhone.value = '';
	//�������ƶ��绰
	fm.InsuMobile.value = '';
	//������֪
	fm.HealthFlag.value = '';
	//������ְҵ����
	fm.InsuJobCode.value = '';
	
//��������Ϣ1
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
	//fm.BeneficiaryIndicator.value='';  
 
//��������Ϣ2
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
	 
//��������Ϣ3
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
	
//������Ϣ
	//���մ��� 
	fm.Code.value = '';
	//���� 
	fm.Mult.value = '';
	//���� 
	fm.Prem.value = '';
	//���� 
	fm.Amnt.value = '';
	//������������  
	fm.InsuYearFlag.value = '';
	//�������� 
	fm.InsuYear.value = '';
	//�ɷ��������� 
	fm.PayEndYearFlag.value = '';
	//�ɷ����� 
	fm.PayEndYear.value = '';

//��������Ϣ1
	//���մ��� 
	//fm.Code1.value = '';
	//���� 
	//fm.Mult1.value = '';
	//���� 
	//fm.Prem1.value = '';
	//���� 
	//fm.Amnt1.value = '';
	//������������  
	//fm.InsuYearFlag1.value = '';
	//�������� 
	//fm.InsuYear1.value = '';
	//�ɷ��������� 
	//fm.PayEndYearFlag1.value = '';
	//�ɷ����� 
	//fm.PayEndYear1.value = '';
		
//Ͷ���˻��ڵ�
	//fm.AccNo1.value = '';
	//fm.Rate1.value = '';
	//fm.AccNo2.value = '';
	//fm.Rate2.value = '';
	//fm.AccNo3.value = '';
	//fm.Rate3.value = '';
	
//Ͷ����Ϣ

	//�ɷѷ�ʽ 
	fm.PayIntv.value = ''; 
	//�������ͷ�ʽ
	//fm.PolicyDeliveryMethod.value = ''; 
	//�����˺�  
	fm.AccNo.value = '';
	//�����˺�����
	fm.AccName.value = '';
	//������ȡ��ʽ
	fm.BonusGetMode.value = '';
	//�״�׷�ӱ���
	//fm.FirstSuperaddAmt.value = '';
	//Ͷ�����ڱ�־ 
	//fm.InvestDateInd.value = '';
	//fm.OccupationIndicator.value = '';	
	
//
	fm.ip.value = '<%=tIp%>';
	fm.port.value = '<%=tPort%>';
	fm.portName.value = '<%=tPortName%>';
	//fm.ip.value = '10.26.5.31';
	fm.tranFlagCode.value = '01'; 
	//fm.tranFlagCodeName.value = '����Լ����';
	}
	catch(re)
	{
		alert("LIANTestYBTContInit.jsp-->initBox �����з����쳣:��ʼ���������!");
	}
}


function initInputBox() 
{
	try {
//������Ϣ
	//���ж˽�������
	fm.BankDate.value = '<%=strBankDate%>';
	//������ˮ��
	fm.TransrNo.value = '<%=strTransrNo%>';
	//���ؽ�����ˮ��
	fm.InputTransrNo.value = fm.TransrNo.value;
	//��������
	fm.ZoneNo.value = '10011';
	//�������
	fm.BrNo.value = '14800';
	//��Ա����
	fm.TellerNo.value = '00001';
	//Ͷ������ 
	fm.PolApplyDate.value = '<%=strBankDate%>';
	//Ͷ�����
	fm.ProposalContNo.value = '<%=strProposalContNo%>';
	//����ӡˢ�� 
	fm.PrtNo.value = '';

    fm.ReqsrNo.value = '';	
//fm.OriginalProviderFormNumber.value = '';	
//fm.PolNumber.value = '';
	
	//document.getElementById("PrtNo").disabled=true;
	document.getElementById("ReqsrNo").disabled=true;
	//document.getElementById("OldPrtNo").disabled=true;
	//document.getElementById("ContNo").disabled=true;
   
//Ͷ������Ϣ
	//Ͷ��������
	fm.ApplName.value = '<%=strAppntName%>';
	//Ͷ�����Ա�
	fm.ApplSex.value = '0';
	//Ͷ����֤������
	fm.ApplIDType.value = '110001';
	//Ͷ����֤������
	fm.ApplIDNo.value = '220523198509173414';
	//Ͷ��������
	fm.ApplBirthday.value = '1985-09-17';
	//Ͷ���˵�������
	fm.ApplEmail.value = 'zhangsan@163.com';
	//Ͷ����ͨѶ��ַ	 
	fm.ApplAddress.value = '�Ϻ����������Ӽ��ߴ�'; 
	//Ͷ������������
	fm.ApplZipCode.value = '666666';
	//Ͷ���˼�ͥ�绰
	fm.ApplPhone.value = '02223558550';
	//Ͷ�����ƶ��绰
	fm.ApplMobile.value = '15821576966';
	//Ͷ�����뱻���˵Ĺ�ϵ
	fm.ApplRelaToInsured.value = '4';
	//Ͷ����ְҵ����
	fm.ApplJobCode.value = '01';
	//Ͷ����֤����Ч��
	fm.ApplValidYear.value='2020-12-31';
	
//��������Ϣ
	//����������
	fm.InsuName.value = '<%=strInsuName%>';
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
	fm.HealthFlag.value = '0';
	//������ְҵ����
	fm.InsuJobCode.value = '01';
	//������֤����Ч��
	fm.InsuValidYear.value='2020-12-31';
	
	
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
	//fm.BeneficiaryIndicator.value='N'; 
	
	//������֤��������Ч��
	fm.BnfValidYear1.value = '2020-09-16'; 
 
//��������Ϣ2
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
	 
//��������Ϣ3
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
	
//������Ϣ
	//���մ��� 
	fm.Code.value = '231201';
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
	fm.PayEndYearFlag.value = '0';
	//�ɷ����� 
	fm.PayEndYear.value = '1000';
	fm.PayIntv.value = '1';

//��������Ϣ1
	//���մ��� 
	//fm.Code1.value = '';
	//���� 
	//fm.Mult1.value = '';
	//���� 
	//fm.Prem1.value = '';
	//���� 
	//fm.Amnt1.value = '';
	//������������ 
	//fm.InsuYearFlag1.value = '';
	//�������� 
	///fm.InsuYear1.value = '';
	//�ɷ��������� 
	//fm.PayEndYearFlag1.value = '';
	//�ɷ����� 
	//fm.PayEndYear1.value = '';

//Ͷ���˻��ڵ�
	//fm.AccNo1.value = '';
	//fm.Rate1.value = '';
	//fm.AccNo2.value = '';
	//fm.Rate2.value = '';
	//fm.AccNo3.value = '';
	//fm.Rate3.value = '';

	
//Ͷ����Ϣ

	//�ɷѷ�ʽ
	fm.PayIntv.value = '1';
	//�������ͷ�ʽ
	//fm.PolicyDeliveryMethod.value = '4'; 
	//�����˺�
	fm.AccNo.value = '01234567890123';
	//�����˺�����
	fm.AccName.value = '<%=strAppntName%>';
	//������ȡ��ʽ
	fm.BonusGetMode.value = '1';
	//�״�׷�ӱ���
	//fm.FirstSuperaddAmt.value = '';
	//Ͷ�����ڱ�־
	//fm.InvestDateInd.value = '';
	//fm.OccupationIndicator.value='N';
// 
	//fm.ip.value = '127.0.0.1';
	fm.ip.value = '<%=tIp%>';
	fm.port.value = '<%=tPort%>';
	fm.portName.value = '<%=tPortName%>';
	fm.tranFlagCode.value = '01'; 
//fm.tranFlagCodeName.value = '����Լ����';
	}
	catch(re)
	{
		alert("LIANTestYBTContInit.jsp-->initInputBox �����з����쳣:��ʼ���������!");
	}
}

</script>
