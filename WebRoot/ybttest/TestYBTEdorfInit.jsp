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
	String strHOAppFormNumber = "1003"+PubFun1.CreateMaxNo("PrtNo",8);
	String strProviderFormNumber = "2003"+PubFun1.CreateMaxNo("ContPrtNo",8);
	String strAccNo = "6225"+PubFun1.CreateMaxNo("AccNo",8);
	
	
	 
  	 String transNo = PubFun1.CreateMaxNo("TransNo",16);

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

function initInputBox() 
{
	try {
	//���ж˽�������
	fm.TransExeDate.value='<%=strTransExeDate%>';
	
	//������ˮ��
	fm.TransNo.value = '<%=transNo%>';


	//�������� 
	fm.ZoneNo.value='10011';
	
	//�������  
	fm.NodeNo.value='14800';
	
	//ip��ַ
	fm.ip.value='127.0.0.1';
	//�˿�
	fm.port.value='35000';
	
	fm.AccountNumber.value='01234567890123';
	fm.AccountForName.value='����';
	
	fm.AppntName.value='����';
	fm.AppGovtIDTC.value='0';
	fm.AppGovtID.value='220523198509173414';
	
	fm.InsureName.value='����';
	fm.InsureGovtIDTC.value='0';
	fm.InsureGovtID.value='220523850917341';
	
	}
	catch(re)
	{
		alert("TestYBTContInit.jsp-->initInputBox �����з����쳣:��ʼ���������!");
	}
}


function initContConfirm()
{
try { 

document.getElementById("OriginTransNo").disabled=false;
	document.getElementById("PrintNo").disabled=false;
	document.getElementById("Password").disabled=false;
	document.getElementById("Premium0").disabled=false;
//������Ϣ
   fm.TranCode.value='1002';
	//���ж˽�������
	fm.TranDate.value = '<%=strTransExeDate%>';
	//������ˮ��
	fm.TransNo.value = '<%=PubFun1.CreateMaxNo("TransNo",16)%>';
	//��Ա����
	fm.TellerNo.value = '00001';
	//Ͷ������ 
	fm.ApplyDate.value = '<%=strTransExeDate%>';
	//Ͷ�����
	//fm.ApplyNo.value = '<%="C"+PubFun1.CreateMaxNo("PrtNo",9)%>';
	fm.Channel.value = '1';
	

	}
	catch(re)
	{
		alert("TestYBTContInit.jsp-->NewContConfirm �����з����쳣:��ʼ���������!");
	}
}




	function initBox(){
	
	try {
//������Ϣ

   fm.TranCode.value='1001';
	//���ж˽�������
	fm.TranDate.value = '<%=strTransExeDate%>';
	//������ˮ��
	fm.TransNo.value = '<%=PubFun1.CreateMaxNo("TransNo",16)%>';
	//���ؽ�����ˮ��
//	fm.InputTransRefGUID.value = fm.TransRefGUID.value;
	//�������� 
	fm.ZoneNo.value='';
	//�������  
	fm.BrNo.value='';
	//��Ա����
	fm.TellerNo.value = '';
	//Ͷ������ 
	fm.ApplyDate.value = '<%=strTransExeDate%>';
	//Ͷ�����
	fm.ApplyNo.value = '<%="YBTTEST"+PubFun1.CreateMaxNo("PrtNo",9)%>';
	fm.Channel.value = '';

   
//Ͷ������Ϣ
	//����
	fm.Name.value = '';
	//�Ա�
	fm.Sex.value = '';
	//֤������
	fm.IDType.value = '';
	//֤������
	fm.IDNo.value = '';
	//����
	fm.Birthday.value = '';
	//���֤����Ч����
	fm.IDStartDate.value='';
	//���֤����Ч�ڵ�����
	fm.IDEndDate.value='';
	//����״̬  
	fm.Marriage.value='';
	//��������
	fm.Nationality.value='';
	//סլ�绰            
	fm.HomePhone.value='';
	//�칫�绰            
	fm.OfficePhone.value='';
	//�ƶ��绰            
	fm.MobilePhone.value='';
	//��ַ                
	fm.MailAddr.value='';
	//��������            
	fm.MailZipCode.value='';
	//ͨѶ��ַ            
	fm.HomeAddr.value='';
	//ͨѶ��������        
	fm.HomeZipCode.value='';
	//�����ʼ�            
	fm.Email.value='';
	//ְҵ����            
	fm.JobCode.value='';
	//������              
	fm.Income.value='';
	//��˾����            
	fm.WorkCompany.value='';
	//�뱻���˹�ϵ        
	fm.RelaToInsured.value='';
	
	
//��������Ϣ
			//����
			fm.BName.value = '';
			//�Ա�
			fm.BSex.value = '';
			//֤������
			fm.BIDType.value = '';
			//֤������
			fm.BIDNo.value = '';
			//����
			fm.BBirthday.value = '';
			//���֤����Ч����
			fm.BIDStartDate.value='';
			//���֤����Ч�ڵ�����
			fm.BIDEndDate.value='';
			//����״̬  
			fm.BMarriage.value='';
			//��������
			fm.BNationality.value='';
			//סլ�绰            
			fm.BHomePhone.value='';
			//�칫�绰            
			fm.BOfficePhone.value='';
			//�ƶ��绰            
			fm.BMobilePhone.value='';
			//��ַ                
			fm.BMailAddr.value='';
			//��������            
			fm.BMailZipCode.value='';
			//ͨѶ��ַ            
			fm.BHomeAddr.value='';
			//ͨѶ��������        
			fm.BHomeZipCode.value='';
			//�����ʼ�            
			fm.BEmail.value='';
			//ְҵ����            
			fm.BJobCode.value='';
			//������              
			fm.BIncome.value='';
			//��˾����            
			fm.BWorkCompany.value='';
	
//��������Ϣ1 
	//����������
	fm.Type1.value='';
	//����������
	fm.Name1.value = '';
	//�������Ա� 
	fm.Sex1.value = '';
	//�����˳�������
	fm.Birthday1.value = '';
	//������֤������
	fm.IDType1.value = '';
	//������֤������
	fm.IDNo1.value = '';
	//���֤����Ч����
	fm.IDStartDate1.value='';
	//���֤����Ч�ڵ�����
	fm.IDEndDate1.value='';
	//��������
	fm.Nationality1.value='';
	//��ַ                
	fm.HomeAddr1.value='';
	//ְҵ����            
	fm.JobCode1.value='';
	//�ƶ��绰            
	fm.MobilePhone1.value='';
	//�������뱻���˵Ĺ�ϵ  
	fm.RelationToInsured1.value='';	
	//����ٷ��� 
	fm.Percent1.value = '';
	//�������
	fm.Sequence1.value = ''; 	
	//����˳��
	fm.Order1.value = ''; 	
	//�������Ƿ�Ϊ������־ 
	fm.BeneficiaryIndicator.value='N';  
 
	
//������Ϣ
	//���մ��� 
	fm.Code.value = '';
	fm.CodeName.value='';
	//���� 
	fm.Unit.value = '';
	//���� 
	fm.InsuAmount.value = '';
	//���� 
	fm.Premium.value = '';
	//�ɷ��������� 
	fm.PayEndYearFlag.value = '';
	//�ɷ����� 
	fm.PayEndYear.value = '';
	//������������  
	fm.InsuYearFlag.value = '';
	//�������� 
	fm.InsuYear.value = '';
	


//Ͷ���˻��ڵ�
	
//Ͷ����Ϣ

	fm.SellTeller.value='';
	fm.SellTellerName.value='';
	fm.SellCertID.value='';
	fm.CertIDValidDate.value='';
	fm.PayIntv.value='';
	fm.PayMethod.value='';
	fm.SendMethod.value='';
	fm.BonusPayMode.value='';
	fm.HealthFlag.value='';
	fm.OccupationFlag.value='';
	fm.BonusGetMode.value='';
	fm.AutoPayFlag.value='';
	fm.SubFlag.value='';
	fm.AutoRenewFlag.value='';
	fm.GetYearFlag.value='';
	fm.GetYear.value='';
	fm.NorskPrem.value='';
	fm.NorskAmt.value='';
	fm.DealType.value='';
	fm.ArbitrationInst.value='';
	fm.FirstRate.value='';
	fm.SureRate.value='';
	fm.InvestDateInd.value='';
	fm.FullBonusGetMode.value='';
	
	fm.GetAccBankName.value='';
	fm.GetAccName.value='';
	fm.GetAcc.value='';
	
	fm.PayAccBankName.value='';
	fm.PayAccName.value='';
	fm.PayAcc.value='';
	
	fm.SpecContent.value='';
	}
	catch(re)
	{
		alert("TestYBTContInit.jsp-->initInputBox �����з����쳣:��ʼ���������!");
	}
	}


</script>
