<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%@page import="com.sinosoft.lis.pubfun.*" %>
<%@page import="org.jdom.*" %>
<%@page import="com.sinosoft.midplat.MidplatConf" %>
<%  
	PubFun1 pubfun = new PubFun1();
	String strTransExeDate = DateUtil.getCur10Date();

	
	Element mTestUI = 
  		 MidplatConf.newInstance().getConf().getRootElement().getChild("ABCTestUI");
  	 String tIp = mTestUI.getAttributeValue("ip");
  	 String tPort = mTestUI.getAttributeValue("port");
  	 
  	 String transrNo = "YBTTEST"+PubFun1.CreateMaxNo("TransrNo",9);
  	//String effectiveDate = DateUtilZR.nextDay(DateUtil.getCur10Date());

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
	
	//document.getElementById("OriginTransNo").disabled=true;
	//document.getElementById("PrintNo").disabled=true;
	//document.getElementById("Password").disabled=true;
	//document.getElementById("Premium0").disabled=true;
	
	//-------������Ϣ
    fm.FunctionFlag.value='01';
	//���ж˽�������
	fm.BankDate.value = '<%=strTransExeDate%>';
	//������ˮ��
	fm.TransrNo.value = '<%=transrNo%>';
	//���ؽ�����ˮ��
	fm.NewTransNo.value = fm.TransrNo.value;
	//�������� 
	fm.ZoneNo.value='0021';
	//fm.ZoneNoName.value = '�Ϻ�';
	//�������  
	fm.BrNo.value='ABCOT01';
	//fm.BrNoName.value = '�й�ũҵ����-�Ϻ�֧��';
	//��Ա����
	fm.TellerNo.value = 'ABCCT01';
	
	
	//--------Ͷ����Ϣ
	//Ͷ������
	fm.ProposalContNo.value = '<%="C"+PubFun1.CreateMaxNo("APPNo",9)%>';
	//Ͷ������ 
	fm.PolApplyDate.value = '<%=strTransExeDate%>';
	//���ո��˻�����
	fm.AccName.value='����';
	//���ո���������
	//fm.AccBankNAME.value='01';
	//fm.AccBankNAMEName.value='�й���������';
	//���ո������˻�
	//fm.BankAccNo.value='6227001700130252734';
	//������ʽ
	fm.PayMode.value='0';
	//�����ʺ�
	fm.AccNo.value='6227001700130252734';
	//�ر�Լ��
	fm.SpecContent.value='';
	//��������
	//fm.Password.value='Password';
	//����ӡˢ����
	fm.PrtNo.value='P12345678';

   
//Ͷ������Ϣ
	//����
	fm.Name.value = '����';
	//�Ա�
	fm.Sex.value = '0';
	//֤������
	fm.IDType.value = '110001';
	fm.IDTypeName.value = '�������֤';
	//֤������
	fm.IDNo.value = '110101198001010013';
	//����
	fm.Birthday.value = '1980-01-01';
	//��ϵ�绰           
	fm.Phone.value='021-2121211';
	//�ֻ���        
	fm.Mobile.value='13636440432';
	//ͨѶ��ַ            
	fm.Address.value='�Ϻ��а���·8��6¥';
	//ͨѶ��������        
	fm.ZipCode.value='121212';
	//�뱻���˹�ϵ        
	fm.RelaToInsured.value='3';
	fm.RelaToInsuredName.value='����';
	//��������
	fm.County.value='CHN';	
	//֤����Ч��          
	fm.ValidYear.value='20';
	//�����ʼ�            
	fm.Email.value='915543374@qq.com';
	//ְҵ����            
	fm.JobCode.value='01';
	fm.JobCodeName.value='���һ��ء���Ⱥ��֯����ҵ����ҵ��λ��Ա';
	
	
//��������Ϣ
		fm.BName.value = '���Ա�����';
		//�Ա�
		fm.BSex.value = '1';
		//֤������
		fm.BIDType.value = '110001';
		fm.BIDTypeName.value = '�������֤';
		//֤������
		fm.BIDNo.value = '110101198903030029';
		//����
		fm.BBirthday.value = '1989-03-03';
		//��ϵ�绰           
		fm.BPhone.value='021-2121211';
		//�ֻ���        
		fm.BMobile.value='13636440432';
		//ͨѶ��ַ            
		fm.BAddress.value='�Ϻ���½���·90��';
		//ͨѶ��������        
		fm.BZipCode.value='121212';
		//��������
		fm.BCounty.value='CHN';	
		//֤����Ч��          
		fm.BValidYear.value='20';
		//�����ʼ�            
		fm.BEmail.value='121212121@qq.com';
		//ְҵ����            
		fm.BJobCode.value='01';
		fm.BJobCodeName.value='���һ��ء���Ⱥ��֯����ҵ����ҵ��λ��Ա';
			
	
//��������Ϣ1 
		//����������
		fm.Type1.value='1';
		//����������
		fm.Name1.value = '����������';
		//�������Ա� 
		fm.Sex1.value = '0';
		//�����˳�������
		fm.Birthday1.value = '2010-09-17';
		//������֤������
		fm.IDType1.value = '110001';
		fm.IDTypeName1.value = '�������֤';
		//������֤������
		fm.IDNo1.value = '110101201009170013';
		//�������뱻���˵Ĺ�ϵ  
		fm.RelationToInsured1.value='04';	
		fm.RelationToInsuredName1.value='����';
		//����˳��
		fm.BnfGrade1.value = '1'; 	
		//������� 
		fm.BnfLot1.value = '100';
		//ͨѶ��ַ
		fm.Address1.value='�Ϻ�������·2277��������ʴ���6¥';
		//�������Ƿ�Ϊ������־ 
		fm.BeneficiaryIndicator.value='N'; 
		
	
//������Ϣ
	//���մ��� 
	fm.Code.value = '241201';
	//fm.RiskPassword.value='Password';
	//���� 
	fm.Prem.value = '100000';
	//���� 
	fm.Amnt.value = '';
	//fm.Rate.value='30';
	fm.CValiDate.value='<%=strTransExeDate%>';
	//���� 
	fm.Mult.value = '';
	//���ѷ�ʽ
	fm.PayIntv.value='5';
	//�����ڼ�
	fm.Years.value='10';
	//�ɷ��������� 
	fm.PayEndYearFlag.value = '4';
	//�ɷ����� 
	fm.PayEndYear.value = '10';
	//������������  
	fm.InsuYearFlag.value = '4';
	//�������� 
	fm.InsuYear.value = '10';
	//������֪��ʶ
	fm.HealthFlag.value='0';
	//�Ƿ�ͬ���ֽ��ֵ�Զ��潻���շ�
	//fm.IsCashAutoPay.value='0';
	//��ԥ���ڵ��ʽ��Ƿ����Ͷ��
	//fm.IsCashJoinInvest.value='0';


//Ͷ���˻��ڵ�
	

	fm.ip.value = '<%=tIp%>';
	fm.port.value = '<%=tPort%>';
	}
	catch(re)
	{
		alert("TestYBTContInit.jsp-->initInputBox �����з����쳣:��ʼ���������!");
	}
}


function initContConfirm()
{
try { 
	fm.ReqsrNo.value=fm.TransrNo.value;

	}
	catch(re)
	{
		alert("TestYBTContInit.jsp-->NewContConfirm �����з����쳣:��ʼ���������!");
	}
}

	function initBox(){
	
	try {
	
	//-------������Ϣ
    fm.FunctionFlag.value='01';
	//���ж˽�������
	fm.BankDate.value = '<%=strTransExeDate%>';
	//������ˮ��
	fm.TransrNo.value = '<%=transrNo%>';
	//���ؽ�����ˮ��
	fm.NewTransNo.value = fm.TransrNo.value;
	//�������� 
	fm.ZoneNo.value='';
	//fm.ZoneNoName.value = '';
	//�������  
	fm.BrNo.value='';
	//fm.BrNoName.value = '';
	//��Ա����
	fm.TellerNo.value = '';
	
	
	//--------Ͷ����Ϣ
	//Ͷ������
	fm.ProposalContNo.value = '';
	//Ͷ������ 
	fm.PolApplyDate.value = '';
	//�˻�����
	fm.AccName.value='';
	//���ո���������
	//fm.AccBankNAME.value='';
	//fm.AccBankNAMEName.value='';
	//���ո������˻�
	//fm.BankAccNo.value='';
	//������ʽ
	fm.PayMode.value='';
	//�����ʺ�
	fm.AccNo.value='';
	//�ر�Լ��
	fm.SpecContent.value='SpecContent';
	//��������
	//fm.Password.value='';
	//����ӡˢ����
	fm.PrtNo.value='';

   
//Ͷ������Ϣ
	//����
	fm.Name.value = '';
	//�Ա�
	fm.Sex.value = '';
	//֤������
	fm.IDType.value = '';
	fm.IDTypeName.value = '';
	//֤������
	fm.IDNo.value = '';
	//����
	fm.Birthday.value = '';
	//��ϵ�绰           
	fm.Phone.value='';
	//�ֻ���        
	fm.Mobile.value='';
	//ͨѶ��ַ            
	fm.Address.value='';
	//ͨѶ��������        
	fm.ZipCode.value='';
	//�뱻���˹�ϵ        
	fm.RelaToInsured.value='';
	fm.RelaToInsuredName.value='';
	//��������
	fm.County.value='';	
	//֤����Ч��          
	fm.ValidYear.value='';
	//�����ʼ�            
	fm.Email.value='';
	//ְҵ����            
	fm.JobCode.value='';
	fm.JobCodeName.value='';
	
	
	
	
//��������Ϣ
		fm.BName.value = '';
		//�Ա�
		fm.BSex.value = '';
		//֤������
		fm.BIDType.value = '';
		fm.BIDTypeName.value = '';
		//֤������
		fm.BIDNo.value = '';
		//����
		fm.BBirthday.value = '';
		//��ϵ�绰           
		fm.BPhone.value='';
		//�ֻ���        
		fm.BMobile.value='';
		//ͨѶ��ַ            
		fm.BAddress.value='';
		//ͨѶ��������        
		fm.BZipCode.value='';
		//��������
		fm.BCounty.value='';	
		//֤����Ч��          
		fm.BValidYear.value='';
		//�����ʼ�            
		fm.BEmail.value='';
		//ְҵ����            
		fm.BJobCode.value='';
		fm.BJobCodeName.value='';
			
	
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
		fm.IDTypeName1.value = '';
		//������֤������
		fm.IDNo1.value = '';
		//�������뱻���˵Ĺ�ϵ  
		fm.RelationToInsured1.value='';	
		fm.RelationToInsuredName1.value='';
		//����˳��
		fm.BnfGrade1.value = ''; 	
		//������� 
		fm.BnfLot1.value = '';
		//ͨѶ��ַ
		fm.Address1.value='';
		//�������Ƿ�Ϊ������־ 
		fm.BeneficiaryIndicator.value=''; 
		
		fm.Type2.value='';
		fm.Name2.value = '';
		fm.Sex2.value = '';
		fm.Birthday2.value = '';
		fm.IDType2.value = '';
		fm.IDTypeName2.value = '';
		fm.IDNo2.value = '';
		fm.RelationToInsured2.value='';	
		fm.RelationToInsuredName2.value='';
		fm.BnfGrade2.value = ''; 	
		fm.BnfLot2.value = '';
		fm.Address2.value='';
		
		fm.Type3.value='';
		fm.Name3.value = '';
		fm.Sex3.value = '';
		fm.Birthday3.value = '';
		fm.IDType3.value = '';
		fm.IDTypeName3.value = '';
		fm.IDNo3.value = '';
		fm.RelationToInsured3.value='';	
		fm.RelationToInsuredName3.value='';
		fm.BnfGrade3.value = ''; 	
		fm.BnfLot3.value = '';
		fm.Address3.value='';
	
//������Ϣ
	fm.Code.value = '';
	//fm.RiskPassword.value='';
	fm.Prem.value = '';
	fm.Amnt.value = '';
	//fm.Rate.value='';
	fm.CValiDate.value='';
	fm.Mult.value = '';
	fm.PayIntv.value='';
	fm.Years.value='';
	fm.PayEndYearFlag.value = '';
	fm.PayEndYear.value = '';
	fm.InsuYearFlag.value = '';
	fm.InsuYear.value = '';
	fm.GetYearFlag.value='';
	fm.GetYear.value='';
	fm.HealthFlag.value='';
	//fm.IsCashAutoPay.value='';
	//fm.IsCashJoinInvest.value='';
	
//��������Ϣ
	fm.Code1.value = '';
	fm.Prem1.value = '';
	fm.Amnt1.value = '';
	fm.CValiDate1.value='';
	fm.Mult1.value = '';
	fm.PayIntv1.value='';
	fm.Years1.value='';
	fm.PayEndYearFlag1.value = '';
	fm.PayEndYear1.value = '';
	fm.InsuYearFlag1.value = '';
	fm.InsuYear1.value = '';
	fm.GetYearFlag1.value='';
	fm.GetYear1.value='';
	
	fm.Code2.value = '';
	fm.Prem2.value = '';
	fm.Amnt2.value = '';
	fm.CValiDate2.value='';
	fm.Mult2.value = '';
	fm.PayIntv2.value='';
	fm.Years2.value='';
	fm.PayEndYearFlag2.value = '';
	fm.PayEndYear2.value = '';
	fm.InsuYearFlag2.value = '';
	fm.InsuYear2.value = '';
	fm.GetYearFlag2.value='';
	fm.GetYear2.value='';

	fm.ip.value = '<%=tIp%>';
	fm.port.value = '<%=tPort%>';
	}
	catch(re)
	{
		alert("TestYBTContInit.jsp-->initInputBox �����з����쳣:��ʼ���������!");
	}
	}


</script>
