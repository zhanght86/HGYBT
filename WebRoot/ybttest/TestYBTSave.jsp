<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.midplat.common.*"%>  
<%@page import="com.sinosoft.lis.pubfun.*"%> 
<%@page import="java.io.*"%>
<%@page import="org.jdom.Document"%>  
<%@page import="org.jdom.Element"%>  
<%@page import="java.net.ConnectException"%> 
<%@page import="com.sinosoft.utility.*"%>
<%@page import="java.util.Calendar"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%@page import="test.*"%>
<%@page import="com.sinosoft.midplat.net.*"%>
<%@page import="org.jdom.output.*"%>
<%@page import="org.jdom.input.*"%>
<%@page import="org.jdom.Attribute"%> 
<jsp:directive.page import="java.util.HashMap"/>
<% 
	System.out.println("--> ins");
	String ResultCode = null;
	String Content = null;
	String ResultInfoDesc = null; 
	String strNewTransNo = null;
	String xmlContent = null;
	String uiPrint = null;   
%>

<%
	try {
 
 
	
	
		


//****************��������Ϣ1*******************��������Ϣ1*******************
 System.out.println("****************���ж���Ϣ1*******************���ж���Ϣ1*******************");
	//���ж˽�������
	String sTransExeDate = request.getParameter("TransExeDate").trim();
	//���н���ʱ��    
	String sTransExeTime = DateUtil.getCur8Time(); 
	//������ˮ��
	String sTransRefGUID = request.getParameter("TransRefGUID");
	//��������
	String sRegionCode = request.getParameter("RegionCode");
	//�������
	String sBranch = request.getParameter("Branch");
	//��Ա����
	String sTeller = request.getParameter("Teller"); 
	//Ͷ������
	String sSubmissionDate = request.getParameter("SubmissionDate");
	//Ͷ�����
	String sHOAppFormNumber = request.getParameter("HOAppFormNumber");
	//����ӡˢ��
	String sProviderFormNumber = request.getParameter("ProviderFormNumber");
	//�ʸ�֤
	String sBankManagerAgentId = request.getParameter("BankManagerAgentId");
	//������Ա����
	String sBankManagerName = request.getParameter("BankManagerName");
	//���ױ�־
	String sTransNo = "1013";
	//�������� 0 ��̨��1 ������8 �����ն�
	String sSourceType = request.getParameter("saleChannel");
	
//****************Ͷ������Ϣ*******************Ͷ������Ϣ*******************
 System.out.println("****************Ͷ������Ϣ*******************Ͷ������Ϣ*******************");
	//Ͷ��������
	String sAppFullName = request.getParameter("AppFullName");
	//Ͷ�����Ա�
	String sAppGender = request.getParameter("AppGender");
	//Ͷ����֤������
	String sAppGovtIDTC = request.getParameter("AppGovtIDTC");
	//Ͷ����֤������
	String sAppGovtID = request.getParameter("AppGovtID");
	//Ͷ��������
	String sAppBirthDate = request.getParameter("AppBirthDate");
	//Ͷ���˵�������
	String sAppAddrLine = request.getParameter("AppAddrLine");
	//Ͷ����ͨѶ��ַ	
	String sAppLine1 = request.getParameter("AppLine1");
	//Ͷ������������
	String sAppZip = request.getParameter("AppZip"); 
	//Ͷ���˼�ͥ�绰
	String sAppDialNumber1 = request.getParameter("AppDialNumber1");
	//Ͷ�����ƶ��绰
	String sAppDialNumber3 = request.getParameter("AppDialNumber3");
	//Ͷ�����뱻���˵Ĺ�ϵ
	String sAppToInsRelation	 = request.getParameter("AppToInsRelation");
	 System.out.println("Ͷ�����뱻���˵Ĺ�ϵdfsfsddfwqwe"+sAppToInsRelation);
	//Ͷ����ְҵ����
	String sAppJobCode= request.getParameter("AppJobCode");
	//Ͷ���˹�˾
	String sAppCompany= request.getParameter("AppCompany");
	//Ͷ�������
	String sAppStature= request.getParameter("AppStature");
	//Ͷ��������
	String sAppWeight= request.getParameter("AppWeight");
	//Ͷ���˹���
	String sAppNationalityNo= request.getParameter("AppNationalityNo");
	//Ͷ�������֤��Ч��
	String sAppGovtTermDate= request.getParameter("AppGovtTermDate");
	//Ͷ����������
	String sAppEstSalary= request.getParameter("AppEstSalary");
	//Ͷ���˼�ͥ������
	String sFamilyEstSalary= request.getParameter("FamilyEstSalary");
	//Ͷ����������
	String sPbDenType= request.getParameter("PbDenType");
	
//****************��������Ϣ*******************��������Ϣ*******************
 System.out.println("****************��������Ϣ*******************��������Ϣ*******************");
	//����������
	String sInsFullName = request.getParameter("InsFullName");
	//�������Ա�
	String sInsGender = request.getParameter("InsGender");
	String sInsGenderh = request.getParameter("InsGenderh");
	//������֤������
	System.out.println("-----"+sInsGender);
	if(sInsGender == null) sInsGender = sInsGenderh;
  
	  
System.out.println("-----"+sInsGenderh); 
	
	String sInsGovtIDTCh = request.getParameter("sInsGovtIDTCh");
	String sInsGovtIDTC = request.getParameter("InsGovtIDTC");
	if(sInsGovtIDTC == null) sInsGovtIDTC = sInsGovtIDTCh;
	
	if(sInsGovtIDTC == null) sInsGovtIDTC = sAppGovtIDTC;
	System.out.println("-----"+sInsGender); 
	//������֤������
	String sInsGovtID = request.getParameter("InsGovtID"); 
	//����������
	String sInsBirthDate = request.getParameter("InsBirthDate");
	//�����˵�������
	String sInsAddrLine = request.getParameter("InsAddrLine"); 
	//������ͨѶ��ַ	 
	String sInsLine1 = request.getParameter("InsLine1");
	//��������������
	String sInsZip = request.getParameter("InsZip"); 
	//�����˼�ͥ�绰
	String sInsDialNumber1 = request.getParameter("InsDialNumber1");
	//�������ƶ��绰
	String sInsDialNumber3 = request.getParameter("InsDialNumber3");
	//Ͷ����ְҵ����
	String sInsJobCode= request.getParameter("InsJobCode");
	//Ͷ���˹�˾
	String sInsCompany= request.getParameter("InsCompany");
	//Ͷ�������
	String sInsStature= request.getParameter("InsStature");
	//Ͷ��������
	String sInsWeight= request.getParameter("InsWeight");
	//Ͷ���˹���
	String sInsNationalityNo= request.getParameter("InsNationalityNo");
	//Ͷ�������֤��Ч��
	String sInsGovtTermDate= request.getParameter("InsGovtTermDate");
	//Ͷ����������
	String sInsEstSalary= request.getParameter("InsEstSalary");
	//������֪��־ 
	String sHealthIndicator	 = request.getParameter("HealthIndicator");
	if(sHealthIndicator == null) sHealthIndicator = "N";
	
//****************��������Ϣ1*******************��������Ϣ1*******************
 System.out.println("****************��������Ϣ1*******************��������Ϣ1*******************");
	//���������� 
	String sBnfFullName1 = request.getParameter("BnfFullName1");
	//�������Ա�
	String sBnfGender1 = request.getParameter("BnfGender1");
	//������֤������
	String sBnfGovtIDTC1 = request.getParameter("BnfGovtIDTC1");
	//������֤������
	String sBnfGovtID1 = request.getParameter("BnfGovtID1"); 
	//����������
	String sBnfBirthDate1 = request.getParameter("BnfBirthDate1"); 
	//����ٷ���
	String sInterestPercent1 = request.getParameter("InterestPercent1"); 
	//����˳ ��
	String sSequence1 = request.getParameter("Sequence1");
	//�������뱻���˵Ĺ�ϵ
	String sBnfToInsRelation1 = request.getParameter("BnfToInsRelation1");
	//�������Ƿ�Ϊ����
	String sBeneficiaryIndicator = request.getParameter("BeneficiaryIndicator");
System.out.println("�������Ƿ�Ϊ��"+sBeneficiaryIndicator);
//****************��������Ϣ2*******************��������Ϣ2*******************
 System.out.println("****************��������Ϣ2*******************��������Ϣ2*******************");
	//�������� ��
	String sBnfFullName2 = request.getParameter("BnfFullName2");
	//�������Ա�
	String sBnfGender2 = request.getParameter("BnfGender2");
	//������֤������
	String sBnfGovtIDTC2 = request.getParameter("BnfGovtIDTC2");
	//������֤������
	String sBnfGovtID2 = request.getParameter("BnfGovtID2"); 
	//����������
	String sBnfBirthDate2 = request.getParameter("BnfBirthDate2"); 
	//����ٷ���
	String sInterestPercent2 = request.getParameter("InterestPercent2"); 
	//����˳ ��
	String sSequence2 = request.getParameter("Sequence2");
	//�������뱻���˵Ĺ�ϵ
	String sBnfToInsRelation2 = request.getParameter("BnfToInsRelation2");

//****************��������Ϣ3*******************��������Ϣ3*******************
 System.out.println("****************��������Ϣ3*******************��������Ϣ3*******************");
	//����������
	String sBnfFullName3 = request.getParameter("BnfFullName3");
	//�������Ա�
	String sBnfGender3 = request.getParameter("BnfGender3");
	//������֤������
	String sBnfGovtIDTC3 = request.getParameter("BnfGovtIDTC3");
	//������֤������
	String sBnfGovtID3 = request.getParameter("BnfGovtID3"); 
	//����������
	String sBnfBirthDate3 = request.getParameter("BnfBirthDate3"); 
	//����ٷ���
	String sInterestPercent3 = request.getParameter("InterestPercent3"); 
	//����˳ ��
	String sSequence3 = request.getParameter("Sequence3");
	//�������뱻���˵Ĺ�ϵ
	String sBnfToInsRelation3 = request.getParameter("BnfToInsRelation3");

//****************������Ϣ*******************������Ϣ*******************
 System.out.println("****************������Ϣ*******************������Ϣ*******************");
	//���մ��� 
	String sProductCode = request.getParameter("ProductCode");
	//���� 
	String sIntialNumberOfUnits = request.getParameter("IntialNumberOfUnits");
	//���� 
	String sInitCovAmt = request.getParameter("InitCovAmt"); 
	//����
	String sModalPremAmt = request.getParameter("ModalPremAmt"); 
	//������������
	String sDurationMode = request.getParameter("DurationMode"); 
	//�������� 
	String sDuration = request.getParameter("Duration"); 
	//�ɷ���������
	String sPaymentDurationMode = request.getParameter("PaymentDurationMode");
	//�ɷ����� 
	String sPaymentDuration = request.getParameter("PaymentDuration"); 
	
//****************��������Ϣ1********************��������Ϣ1*******************
 System.out.println("****************��������Ϣ1*******************��������Ϣ1*******************");
	//���մ��� 
	String sProductCode1 = request.getParameter("ProductCode1");
	//���� 
	String sIntialNumberOfUnits1 = request.getParameter("IntialNumberOfUnits1");
	//���� 
	String sInitCovAmt1 = request.getParameter("InitCovAmt1");
	//����
	String sModalPremAmt1 = request.getParameter("ModalPremAmt1"); 
	//������������
	String sDurationMode1 = request.getParameter("DurationMode1"); 
	//�������� 
	String sDuration1 = request.getParameter("Duration1"); 
	//�ɷ���������
	String sPaymentDurationMode1 = request.getParameter("PaymentDurationMode1");
	//�ɷ����� 
	String sPaymentDuration1 = request.getParameter("PaymentDuration1"); 
	
//****************��������Ϣ2*******************��������Ϣ2*******************
 System.out.println("****************��������Ϣ2*******************��������Ϣ2*******************");
	//���մ��� 
	String sProductCode2 = request.getParameter("ProductCode2");
	//���� 
	String sIntialNumberOfUnits2 = request.getParameter("IntialNumberOfUnits2");
	//���� 
	String sInitCovAmt2 = request.getParameter("InitCovAmt2");
	//����
	String sModalPremAmt2 = request.getParameter("ModalPremAmt2"); 
	//������������
	String sDurationMode2 = request.getParameter("DurationMode2"); 
	//�������� 
	String sDuration2 = request.getParameter("Duration2"); 
	//�ɷ���������
	String sPaymentDurationMode2 = request.getParameter("PaymentDurationMode2");
	//�ɷ����� 
	String sPaymentDuration2 = request.getParameter("PaymentDuration2"); 
//****************Ͷ����Ϣ*******************Ͷ����Ϣ*******************	
 System.out.println("****************Ͷ����Ϣ*******************Ͷ����Ϣ*******************");
	//�ɷѷ�ʽ
	String sPaymentMode = request.getParameter("PaymentMode");
	//�ɷ���ʽ(Ƶ��)
	String sPaymentMethod = request.getParameter("PaymentMethod");
	//��ȡ��ʽ
	String sBenefitMode = request.getParameter("BenefitMode");
	//������ȡ��ʽ
	String sDivType = request.getParameter("DivType"); 
	System.out.println("**(((((((((((((((((((((())))))))))))))))))))))))*****"+sDivType);
	//�������ͷ�ʽ
	String sPolicyDeliveryMethod = request.getParameter("PolicyDeliveryMethod"); 
	//�����˺�
	String sAccountNumber = request.getParameter("AccountNumber"); 
	//�ʻ�����
	String sAcctHolderName = request.getParameter("AcctHolderName");
System.out.println("*******"+sAcctHolderName);
	//�ر�Լ��
	String sSpecialClause = request.getParameter("SpecialClause");
	String sOccupationIndicator = request.getParameter("OccupationIndicator");
	
	//����յļ���������ֶ�
	String sPayOutDurationMode = request.getParameter("PayOutDurationMode");
	String sPayoutStart = request.getParameter("PayoutStart");
	String sPayoutDuration = request.getParameter("PayoutDuration");
	
	//�����ͬ��
	String sContractNo = request.getParameter("ContractNo");
	//�����˺�
	String sLoanAccountNo = request.getParameter("LoanAccountNo");
	//�����
	String sLoanAmount = request.getParameter("LoanAmount");
	//���ս��
	String sFaceAmount = request.getParameter("FaceAmount");
	//������ʼ����
	String sLoanStartDate = request.getParameter("LoanStartDate");
	//���������
	String sLoanEndDate = request.getParameter("LoanEndDate");
	//���պ�ͬ��Ч����
	String sContractEffDate = request.getParameter("ContractEffDate");
	//���պ�ͬ��������
	String sContractEndDate = request.getParameter("ContractEndDate");
	
	//��������
	String sLoanProductCode = request.getParameter("LoanProductCode");
	
//****************ip-port-flag*******************ip-port-flag*******************	
System.out.println("****************ip-port-flag*******************ip-port-flag*******************===="+sAppEstSalary);	
String sIp = request.getParameter("ip");
String sPort = request.getParameter("port");
String sFlag = request.getParameter("tranFlagCode");

 


//�ѱ�����Ϣ��ҳ����ʾ
		xmlContent = new String(""); 
   
		xmlContent += "һ��������Ϣ\\n";
		xmlContent += "  ���ж�����: " + sTransExeDate;
		xmlContent += "\t\t������ˮ��: " + sTransRefGUID + "\\n";
		xmlContent += "  ��������: " + sRegionCode; 
		xmlContent += "\t\t\t�������: " + sBranch + "\\n"; 
		xmlContent += "  ��Ա����: " + sTeller;
		xmlContent += "\t\t\tͶ������: " + sSubmissionDate + "\\n"; 
		xmlContent += "  Ͷ�����: " + sHOAppFormNumber;
		xmlContent += "\t����ӡˢ��: " + sProviderFormNumber + "\\n";
		
		xmlContent += "����Ͷ������Ϣ\\n";
		xmlContent += "  Ͷ��������: " + sAppFullName;
		xmlContent += "\t\t\tͶ�����Ա�: " + sAppGender + "\\n";
		xmlContent += "  Ͷ����֤������: " + sAppGovtIDTC ;
		xmlContent += "\t\tͶ����֤������: " + sAppGovtID + "\\n";
		xmlContent += "  Ͷ��������: " + sAppBirthDate ;
		xmlContent += "\t\tͶ���˵������� : " + sAppAddrLine + "\\n";		
		xmlContent += "  Ͷ���˼�ͥ�绰: " + sAppDialNumber1;
		xmlContent += "\tͶ�����ƶ��绰: " + sAppDialNumber3+ "\\n";
		xmlContent += "  Ͷ�����Ǳ����˵�: " + sAppToInsRelation+ "\\n";		
  xmlContent += "  Ͷ����ְҵ����: " + sAppJobCode;
  xmlContent += "  Ͷ���˹�˾: " + sAppCompany+ "\\n";
  xmlContent += "  Ͷ�������: " + sAppStature;
  xmlContent += "  Ͷ��������: " + sAppWeight+ "\\n";
  xmlContent += "  Ͷ���˹���: " + sAppNationalityNo+ "\\n";
  xmlContent += "  Ͷ�������֤��Ч��: " + sAppGovtTermDate;
  xmlContent += "  Ͷ����������: " + sAppEstSalary+ "\\n";
  xmlContent += "  Ͷ���˼�ͥ������: " + sFamilyEstSalary+ "\\n";
  xmlContent += "  Ͷ���˾�������: " + sPbDenType+ "\\n";
		
		xmlContent += "������������Ϣ\\n";
		xmlContent += "  ����������: " + sInsFullName;
		xmlContent += "\t\t\t�������Ա�: " + sInsGender + "\\n";
		xmlContent += "  ������֤������: " + sInsGovtIDTC ;
		xmlContent += "\t\t������֤������: " + sInsGovtID + "\\n";
		xmlContent += "  ����������: " + sInsBirthDate ;
		xmlContent += "\t\t�����˵������� : " + sInsAddrLine + "\\n";		
		xmlContent += "  �����˼�ͥ�绰: " + sInsDialNumber1;
		xmlContent += "\t�������ƶ��绰: " + sInsDialNumber3+ "\\n";
		xmlContent += "  ������֪: " + sHealthIndicator+ "\\n";	
 xmlContent += "  ������ְҵ����: " + sInsJobCode;
  xmlContent += "  �����˹�˾: " + sInsCompany+ "\\n";
  xmlContent += "  ���������: " + sInsStature;
  xmlContent += "  ����������: " + sInsWeight+ "\\n";
  xmlContent += "  �����˹���: " + sInsNationalityNo+ "\\n";
  xmlContent += "  ���������֤��Ч��: " + sInsGovtTermDate;
  xmlContent += "  ������������: " + sInsEstSalary+ "\\n";
 
		xmlContent += "�ġ���������Ϣ\\n";
		xmlContent += "1����������Ϣ1\\n";
		xmlContent += "  ����������: " + sBnfFullName1;
		xmlContent += "\t\t\t�������Ա�: " + sBnfGender1 + "\\n";
		xmlContent += "  ������֤������: " + sBnfGovtIDTC1 ;
		xmlContent += "\t\t������֤������: " + sBnfGovtID1 + "\\n";
		xmlContent += "  ����������: " + sBnfBirthDate1 ;
		xmlContent += "\t\t������� : " + sInterestPercent1 + "\\n";		
		xmlContent += "  ����˳��: " + sSequence1+ "\\n";	

		if(!(sBnfFullName2==null||sBnfFullName2.equals(""))){
		xmlContent += "2����������Ϣ2\\n";
		xmlContent += "  ����������: " + sBnfFullName2;
		xmlContent += "\t\t\t�������Ա�: " + sBnfGender2 + "\\n";
		xmlContent += "  ������֤������: " + sBnfGovtIDTC2 ;
		xmlContent += "\t\t������֤������: " + sBnfGovtID2 + "\\n";
		xmlContent += "  ����������: " + sBnfBirthDate2 ;
		xmlContent += "\t\t������� : " + sInterestPercent2 + "\\n";		
		xmlContent += "  ����˳��: " + sSequence2+ "\\n";
		}
		
		if(!(sBnfFullName3==null||sBnfFullName3.equals(""))){
		xmlContent += "3����������Ϣ3\\n";
		xmlContent += "  ����������: " + sBnfFullName3;
		xmlContent += "\t\t\t�������Ա�: " + sBnfGender3 + "\\n";
		xmlContent += "  ������֤������: " + sBnfGovtIDTC3 ;
		xmlContent += "\t\t������֤������: " + sBnfGovtID3 + "\\n";
		xmlContent += "  ����������: " + sBnfBirthDate3 ;
		xmlContent += "\t\t������� : " + sInterestPercent3 + "\\n";		
		xmlContent += "  ����˳��: " + sSequence3+ "\\n";
		}
		
		xmlContent += "�塢������Ϣ\\n";
		xmlContent += "1��������Ϣ\\n";
		xmlContent += "  ���մ���: " + sProductCode;
		xmlContent += "\t\t\t����: " + sIntialNumberOfUnits ;
		xmlContent += "\t\t����: " +  sModalPremAmt;  
		xmlContent += "\t\t����: " + sInitCovAmt + "\\n";  
		xmlContent += "  ������������: " + sDurationMode ;
		xmlContent += "\t\t�������� : " + sDuration ;		
		xmlContent += "\t\t�ɷ���������: " + sPaymentDurationMode ;		
		xmlContent += "\t\t�ɷ�����: " + sPaymentDuration + "\\n";	
		 
		if(!(sProductCode1==null||sProductCode1.equals(""))){
		xmlContent += "2����������Ϣ1\\n";
		xmlContent += "  �����մ���: " + sProductCode1;
		xmlContent += "\t\t\t����: " + sIntialNumberOfUnits1 ;
		xmlContent += "\t\t����: " + sInitCovAmt1 ; 
		xmlContent += "\t\t����: " + sModalPremAmt1 + "\\n";  
		xmlContent += "  ������������: " + sDurationMode1 ;
		xmlContent += "\t\t�������� : " + sDuration1 ;		
		xmlContent += "\t\t�ɷ���������: " + sPaymentDurationMode1 ;		
		xmlContent += "\t\t�ɷ�����: " + sPaymentDuration1 + "\\n";
		}
		   
		if(!(sProductCode2==null||sProductCode2.equals(""))){
		xmlContent += "3����������Ϣ2\\n";
		xmlContent += "  �����մ���: " + sProductCode2;
		xmlContent += "\t\t\t����: " + sIntialNumberOfUnits2 ;
		xmlContent += "\t\t����: " + sInitCovAmt2 ; 
		xmlContent += "\t\t����: " + sModalPremAmt1 + "\\n";  
		xmlContent += "  ������������: " + sDurationMode2 ;
		xmlContent += "\t\t�������� : " + sDuration2 ;		
		xmlContent += "\t\t�ɷ���������: " + sPaymentDurationMode1 ;		
		xmlContent += "\t\t�ɷ�����: " + sPaymentDuration2 + "\\n";
		} 
		
		xmlContent += "����Ͷ����Ϣ\\n"; 
		xmlContent += "  �ɷѷ�ʽ: " + sPaymentMode;
		xmlContent += "\t\t�ɷ���ʽ: " + sPaymentMethod ; 
		xmlContent += "\t\t��ȡ��ʽ: " + sBenefitMode ;  
		xmlContent += "\t\t������ȡ��ʽ: " + sDivType + "\\n";  
		xmlContent += "  �������ͷ�ʽ: " + sPolicyDeliveryMethod ;
		xmlContent += "\t\t�����˺� : " + sAccountNumber;	
		xmlContent += "\t�˺����� : " + sAcctHolderName ;	
		xmlContent += "\t\t�ر�Լ��: " + sSpecialClause + "\\n";		


		
 System.out.println("************************װ��XML************************");		 
 //ElementLis aaaa = new ElementLis("","","",);  
 //ElementLis  aaaa= new ElementLis("",);
 //ElementLis aaaa = new ElementLis("",""); 
 ElementLis TXLife = new ElementLis("TXLife");	 
	ElementLis TXLifeRequest = new ElementLis("TXLifeRequest",TXLife);  
		ElementLis TransRefGUID = new ElementLis("TransRefGUID",sTransRefGUID,TXLifeRequest);    
		ElementLis TransExeDate = new ElementLis("TransExeDate",sTransExeDate,TXLifeRequest); 
		ElementLis TransExeTime = new ElementLis("TransExeTime",sTransExeTime,TXLifeRequest);	
		ElementLis OLifE = new ElementLis("OLifE",TXLifeRequest); 
	ElementLis HoldingH1 = new ElementLis("Holding","id","Holding_1",OLifE);
		ElementLis Policy = new ElementLis("Policy",HoldingH1);       
			ElementLis ProductCode = new ElementLis("ProductCode",sProductCode,Policy); 
			ElementLis PaymentMode = new ElementLis("PaymentMode",sPaymentMode,"tc",sPaymentMode,Policy);
			ElementLis PaymentMethod = new ElementLis("PaymentMethod",sPaymentMethod,"tc",sPaymentMethod,Policy);
			ElementLis AcctHolderName = new ElementLis("AcctHolderName",sAcctHolderName,Policy);
			ElementLis AccountNumber = new ElementLis("AccountNumber",sAccountNumber,Policy);
			//�ʸ�֤��
			ElementLis BankManagerAgentId = new ElementLis("BankManagerAgentId",sBankManagerAgentId,Policy);
			//������Ա����
			ElementLis BankManagerName = new ElementLis("BankManagerName",sBankManagerName,Policy);
			ElementLis Life = new ElementLis("Life",Policy); 
				ElementLis DivType = new ElementLis("DivType",sDivType,"tc",sDivType,Life);
				
				ElementLis Coverage = new ElementLis("Coverage","id","Cov_1",Life);
					ElementLis ProductCodeMain = new ElementLis("ProductCode",sProductCode,Coverage);
					ElementLis IndicatorCode = new ElementLis("IndicatorCode","1","tc","1",Coverage);
					ElementLis InitCovAmt = new ElementLis("InitCovAmt",sInitCovAmt,Coverage);
					ElementLis IntialNumberOfUnits = new ElementLis("IntialNumberOfUnits",sIntialNumberOfUnits,Coverage);
					ElementLis ModalPremAmt = new ElementLis("ModalPremAmt",sModalPremAmt,Coverage);
					ElementLis BenefitMode = new ElementLis("BenefitMode",sBenefitMode,"tc",sBenefitMode,Coverage);
					ElementLis OLifEExtension10 = new ElementLis("OLifEExtension","VendorCode","10",Coverage);
						ElementLis PaymentDurationMode = new ElementLis("PaymentDurationMode",sPaymentDurationMode,"tc",sPaymentDurationMode,OLifEExtension10);
						ElementLis PaymentDuration = new ElementLis("PaymentDuration",sPaymentDuration,OLifEExtension10);
						ElementLis DurationMode = new ElementLis("DurationMode",sDurationMode,"tc",sDurationMode,OLifEExtension10);
						ElementLis Duration = new ElementLis("Duration",sDuration,OLifEExtension10);
						
						//��װ����յļ�������Ľڵ� 20130408
						ElementLis PayOutDurationMode = new ElementLis("PayOutDurationMode",sPayOutDurationMode,OLifEExtension10);
						ElementLis PayoutStart = new ElementLis("PayoutStart",sPayoutStart,OLifEExtension10);
						ElementLis PayoutDuration = new ElementLis("PayoutDuration",sPayoutDuration,OLifEExtension10);
						 
//����и�����1    
				if(!(sProductCode1==null||sProductCode1.equals(""))){	
				ElementLis Coverage1 = new ElementLis("Coverage","id","Cov_2",Life);
					ElementLis ProductCodeMain1 = new ElementLis("ProductCode",sProductCode1,Coverage1);
					ElementLis IndicatorCode1 = new ElementLis("IndicatorCode","2","tc","2",Coverage1);
					ElementLis InitCovAmt1 = new ElementLis("InitCovAmt",sInitCovAmt1,Coverage1);
					ElementLis IntialNumberOfUnits1 = new ElementLis("IntialNumberOfUnits",sIntialNumberOfUnits1,Coverage1);
					ElementLis ModalPremAmt1 = new ElementLis("ModalPremAmt",sModalPremAmt1,Coverage1);
					ElementLis BenefitMode1 = new ElementLis("BenefitMode",sBenefitMode,"tc",sBenefitMode,Coverage1);
					ElementLis OLifEExtension11 = new ElementLis("OLifEExtension","VendorCode","11",Coverage1);
						ElementLis PaymentDurationMode1 = new ElementLis("PaymentDurationMode",sPaymentDurationMode1,"tc",sPaymentDurationMode1,OLifEExtension11);
						ElementLis PaymentDuration1 = new ElementLis("PaymentDuration",sPaymentDuration1,OLifEExtension11);
						ElementLis DurationMode1 = new ElementLis("DurationMode",sDurationMode1,"tc",sDurationMode1,OLifEExtension11);
						ElementLis Duration1 = new ElementLis("Duration",sDuration1,OLifEExtension11);						
				}
//����и�����2  
				if(!(sProductCode2==null||sProductCode2.equals(""))){	
				ElementLis Coverage2 = new ElementLis("Coverage","id","Cov_3",Life);
					ElementLis ProductCodeMain2 = new ElementLis("ProductCode",sProductCode2,Coverage2);
					ElementLis IndicatorCode2 = new ElementLis("IndicatorCode","2","tc","2",Coverage2);
					ElementLis InitCovAmt2 = new ElementLis("InitCovAmt",sInitCovAmt2,Coverage2);
					ElementLis IntialNumberOfUnits2 = new ElementLis("IntialNumberOfUnits",sIntialNumberOfUnits2,Coverage2);
					ElementLis ModalPremAmt2 = new ElementLis("ModalPremAmt",sModalPremAmt2,Coverage2);
					ElementLis BenefitMode2 = new ElementLis("BenefitMode",sBenefitMode,"tc",sBenefitMode,Coverage2);
					ElementLis OLifEExtension12 = new ElementLis("OLifEExtension","VendorCode","12",Coverage2);
						ElementLis PaymentDurationMode2 = new ElementLis("PaymentDurationMode",sPaymentDurationMode2,"tc",sPaymentDurationMode2,OLifEExtension12);
						ElementLis PaymentDuration2 = new ElementLis("PaymentDuration",sPaymentDuration2,OLifEExtension12);
						ElementLis DurationMode2 = new ElementLis("DurationMode",sDurationMode2,"tc",sDurationMode2,OLifEExtension12);
						ElementLis Duration2 = new ElementLis("Duration",sDuration2,OLifEExtension12);						
				}
											    
			ElementLis ApplicationInfo = new ElementLis("ApplicationInfo",Policy);
				ElementLis HOAppFormNumber = new ElementLis("HOAppFormNumber",sHOAppFormNumber,ApplicationInfo);
				ElementLis SubmissionDate = new ElementLis("SubmissionDate",sSubmissionDate,ApplicationInfo);
			ElementLis OLifEExtension2 = new ElementLis("OLifEExtension","VendorCode","2",Policy);
				ElementLis PolicyDeliveryMethod = new ElementLis("PolicyDeliveryMethod",sPolicyDeliveryMethod,OLifEExtension2);
				ElementLis SpecialClause = new ElementLis("SpecialClause",sSpecialClause,OLifEExtension2);
				ElementLis HealthIndicator = new ElementLis("HealthIndicator",sHealthIndicator,OLifEExtension2);
				ElementLis OccupationIndicator = new ElementLis("OccupationIndicator",sOccupationIndicator,OLifEExtension2);
				ElementLis BeneficiaryIndicator = new ElementLis("BeneficiaryIndicator",sBeneficiaryIndicator,OLifEExtension2);
				
				//������Ϣ
				ElementLis ContractNo = new ElementLis("ContractNo",sContractNo,OLifEExtension2);
							ElementLis LoanAccountNo = new ElementLis("LoanAccountNo",sLoanAccountNo,OLifEExtension2);
							ElementLis LoanAmount = new ElementLis("LoanAmount",sLoanAmount,OLifEExtension2);
							ElementLis FaceAmount = new ElementLis("FaceAmount",sFaceAmount,OLifEExtension2);
							ElementLis LoanStartDate = new ElementLis("LoanStartDate",sLoanStartDate,OLifEExtension2);
							ElementLis LoanEndDate = new ElementLis("LoanEndDate",sLoanEndDate,OLifEExtension2);
							ElementLis ContractEffDate = new ElementLis("ContractEffDate",sContractEffDate,OLifEExtension2);
							ElementLis ContractEndDate = new ElementLis("ContractEndDate",sContractEndDate,OLifEExtension2);
							
							ElementLis LoanProductCode = new ElementLis("LoanProductCode",sLoanProductCode,OLifEExtension2);
							
				
	ElementLis HoldingA1 = new ElementLis("Holding","id","Acct_1",OLifE); 
		ElementLis Banking = new ElementLis("Banking",HoldingA1);	
			 AccountNumber = new ElementLis("AccountNumber",sAccountNumber,Banking);	
		
			 
	ElementLis Party1 = new ElementLis("Party","id","Party_1",OLifE);
		ElementLis AppFullName = new ElementLis("FullName",sAppFullName,Party1);
		ElementLis AppGovtID = new ElementLis("GovtID",sAppGovtID,Party1);
		ElementLis AppGovtIDTC = new ElementLis("GovtIDTC",sAppGovtIDTC,"tc",sAppGovtIDTC,Party1);
		ElementLis AppGovtTermDate = new ElementLis("GovtTermDate",sAppGovtTermDate,Party1);
		ElementLis AppPerson = new ElementLis("Person",Party1);
			ElementLis AppGender = new ElementLis("Gender",sAppGender,"tc",sAppGender,AppPerson);
			ElementLis AppBirthDate = new ElementLis("BirthDate",sAppBirthDate,AppPerson);
			ElementLis AppNationalityNo = new ElementLis("Nationality",sAppNationalityNo,AppPerson);
			ElementLis AppOccupationType = new ElementLis("OccupationType",sAppJobCode,AppPerson);
			ElementLis AppCompany = new ElementLis("Company",sAppCompany,AppPerson);
			ElementLis AppStature = new ElementLis("Stature",sAppStature,AppPerson);
			ElementLis AppWeight = new ElementLis("Weight",sAppWeight,AppPerson);
			ElementLis AppEstSalary = new ElementLis("EstSalary",sAppEstSalary,AppPerson);
			//Ͷ���˼�ͥ������
			ElementLis FamilyEstSalary = new ElementLis("FamilyEstSalary",sFamilyEstSalary,AppPerson);
			//Ͷ���˾�������
			ElementLis LiveZone = new ElementLis("LiveZone",sPbDenType,AppPerson);
			
		    ElementLis AppAddress = new ElementLis("Address","id","Address_2",Party1);
			ElementLis AppAddressTypeCode = new ElementLis("AddressTypeCode","1","id","1",AppAddress);
			ElementLis AppLine1 = new ElementLis("Line1",sAppLine1,AppAddress);
			ElementLis AppZip = new ElementLis("Zip",sAppZip,AppAddress);
		    ElementLis AppPhone1 = new ElementLis("Phone","id","Phone_1",Party1);
			ElementLis AppPhoneTypeCode1 = new ElementLis("PhoneTypeCode","1","tc","1",AppPhone1);
			ElementLis AppDialNumber1 = new ElementLis("DialNumber",sAppDialNumber1,AppPhone1);
		    ElementLis AppPhone3 = new ElementLis("Phone","id","Phone_3",Party1);
			ElementLis AppPhoneTypeCode3 = new ElementLis("PhoneTypeCode","3","tc","3",AppPhone3);
			ElementLis AppDialNumber3 = new ElementLis("DialNumber",sAppDialNumber3,AppPhone3);
		    ElementLis AppEMailAddress = new ElementLis("EMailAddress","id","EMailAddress_1",Party1);	
			ElementLis AppAddrLine = new ElementLis("AddrLine",sAppAddrLine,AppEMailAddress);
	        ElementLis Relation_1 = new ElementLis("Holding_1","Party_1","Relation_1","4","6","80",OLifE);	
	
	ElementLis Party2 = new ElementLis("Party","id","Party_2",OLifE);
		ElementLis InsFullName = new ElementLis("FullName",sInsFullName,Party2);
		ElementLis InsGovtID = new ElementLis("GovtID",sInsGovtID,Party2);
		ElementLis InsGovtIDTC = new ElementLis("GovtIDTC",sInsGovtIDTC,"tc",sInsGovtIDTC,Party2);
		ElementLis InsGovtTermDate = new ElementLis("GovtTermDate",sInsGovtTermDate,Party2);
		ElementLis InsPerson = new ElementLis("Person",Party2);
			ElementLis InsGender = new ElementLis("Gender",sInsGender,"tc",sInsGender,InsPerson);
			ElementLis InsBirthDate = new ElementLis("BirthDate",sInsBirthDate,InsPerson); 
			ElementLis InsNationalityNo = new ElementLis("Nationality",sInsNationalityNo,InsPerson);
			ElementLis InsOccupationType = new ElementLis("OccupationType",sInsJobCode,InsPerson);
			ElementLis InsCompany = new ElementLis("Company",sInsCompany,InsPerson);
			ElementLis InsStature = new ElementLis("Stature",sInsStature,InsPerson);
			ElementLis InsWeight = new ElementLis("Weight",sInsWeight,InsPerson);
			ElementLis InsEstSalary = new ElementLis("EstSalary",sInsEstSalary,InsPerson);
			
		ElementLis InsAddress = new ElementLis("Address","id","Address_2",Party2);
			ElementLis InsAddressTypeCode = new ElementLis("AddressTypeCode","1","id","1",InsAddress);
			ElementLis InsLine1 = new ElementLis("Line1",sInsLine1,InsAddress);
			ElementLis InsZip = new ElementLis("Zip",sInsZip,InsAddress);
		ElementLis InsPhone1 = new ElementLis("Phone","id","Phone_1",Party2);
			ElementLis InsPhoneTypeCode1 = new ElementLis("PhoneTypeCode","1","tc","1",InsPhone1);
			ElementLis InsDialNumber1 = new ElementLis("DialNumber",sInsDialNumber1,InsPhone1);
		ElementLis InsPhone3 = new ElementLis("Phone","id","Phone_3",Party2);
			ElementLis InsPhoneTypeCode3 = new ElementLis("PhoneTypeCode","3","tc","3",InsPhone3);
			ElementLis InsDialNumber3 = new ElementLis("DialNumber",sInsDialNumber3,InsPhone3);
		ElementLis InsEMailAddress = new ElementLis("EMailAddress","id","EMailAddress_1",Party2);	
			ElementLis InsAddrLine = new ElementLis("AddrLine",sInsAddrLine,InsEMailAddress); 
		ElementLis OLifEExtension200 = new ElementLis("OLifEExtension","VendorCode","200",Party2);
			ElementLis InsHealthIndicator = new ElementLis("HealthIndicator",sHealthIndicator,OLifEExtension200);
	ElementLis Relation_2 = new ElementLis("Holding_1","Party_2","Relation_2","4","6","81",OLifE);
            System.out.println("����:"+sAppToInsRelation);
	ElementLis Relation_4 = new ElementLis("Party_2","Party_1","Relation_4","6","6",sAppToInsRelation,OLifE);	
	
	if(!sBeneficiaryIndicator.equals("Y")){
					
	ElementLis Party3 = new ElementLis("Party","id","Party_3",OLifE);
		ElementLis BnfFullName1 = new ElementLis("FullName",sBnfFullName1,Party3);
		ElementLis BnfGovtID1 = new ElementLis("GovtID",sBnfGovtID1,Party3);
		ElementLis BnfGovtIDTC1 = new ElementLis("GovtIDTC",sBnfGovtIDTC1,"tc",sBnfGovtIDTC1,Party3);
		ElementLis BnfPerson1 = new ElementLis("Person",Party3);
			ElementLis BnfGender1 = new ElementLis("Gender",sBnfGender1,"tc",sBnfGender1,BnfPerson1);
			ElementLis BnfBirthDate1 = new ElementLis("BirthDate",sBnfBirthDate1,BnfPerson1); 
	ElementLis Relation_3 = new ElementLis("Holding_1","Party_3","Relation_3","4","6","82",OLifE);
		ElementLis InterestPercent1 = new ElementLis("InterestPercent",sInterestPercent1,Relation_3);
		ElementLis Sequence1 = new ElementLis("Sequence",sSequence1,Relation_3);
	ElementLis Relation_5 = new ElementLis("Party_2","Party_3","Relation_4","4","6",sBnfToInsRelation1,OLifE);
	}
//����еڶ������� 									
	if(!(sBnfFullName2==null||sBnfFullName2.equals(""))){
	ElementLis Party4 = new ElementLis("Party","id","Party_4",OLifE);
		ElementLis BnfFullName2 = new ElementLis("FullName",sBnfFullName2,Party4);
		ElementLis BnfGovtID2 = new ElementLis("GovtID",sBnfGovtID2,Party4);
		ElementLis BnfGovtIDTC2 = new ElementLis("GovtIDTC",sBnfGovtIDTC2,"tc",sBnfGovtIDTC2,Party4);
		ElementLis BnfPerson2 = new ElementLis("Person",Party4);
			ElementLis BnfGender2 = new ElementLis("Gender",sBnfGender2,"tc",sBnfGender2,BnfPerson2);
			ElementLis BnfBirthDate2 = new ElementLis("BirthDate",sBnfBirthDate2,BnfPerson2); 	
	ElementLis Relation_6 = new ElementLis("Holding_1","Party_4","Relation_6","4","6","82",OLifE);
		ElementLis InterestPercent2 = new ElementLis("InterestPercent",sInterestPercent2,Relation_6);
		ElementLis Sequence2 = new ElementLis("Sequence",sSequence2,Relation_6);
	ElementLis Relation_7 = new ElementLis("Party_2","Party_4","Relation_7","4","6",sBnfToInsRelation2,OLifE);
	}
//����е��������� 									 
	if(!(sBnfFullName3==null||sBnfFullName3.equals(""))){
	ElementLis Party5 = new ElementLis("Party","id","Party_5",OLifE);
		ElementLis BnfFullName3 = new ElementLis("FullName",sBnfFullName3,Party5);
		ElementLis BnfGovtID3 = new ElementLis("GovtID",sBnfGovtID3,Party5);
		ElementLis BnfGovtIDTC3 = new ElementLis("GovtIDTC",sBnfGovtIDTC3,"tc",sBnfGovtIDTC3,Party5);
		ElementLis BnfPerson3 = new ElementLis("Person",Party5);
			ElementLis BnfGender3 = new ElementLis("Gender",sBnfGender3,"tc",sBnfGender3,BnfPerson3);
			ElementLis BnfBirthDate3 = new ElementLis("BirthDate",sBnfBirthDate3,BnfPerson3); 	
	ElementLis Relation_8 = new ElementLis("Holding_1","Party_5","Relation_8","4","6","82",OLifE);
		ElementLis InterestPercent3 = new ElementLis("InterestPercent",sInterestPercent3,Relation_8);
		ElementLis Sequence3 = new ElementLis("Sequence",sSequence3,Relation_8);
	ElementLis Relation_9 = new ElementLis("Party_2","Party_5","Relation_9","4","6",sBnfToInsRelation3,OLifE);
	}
	 
	
	
	
	ElementLis FormInstance = new ElementLis("FormInstance","id","Form_2",OLifE);
		ElementLis FormName = new ElementLis("FormName","2",FormInstance);
		ElementLis ProviderFormNumber = new ElementLis("ProviderFormNumber",sProviderFormNumber,FormInstance);
		ElementLis OLifEExtension1 = new ElementLis("OLifEExtension","VendorCode","1",TXLifeRequest); 
	ElementLis RegionCode = new ElementLis("RegionCode",sRegionCode,OLifEExtension1);
	ElementLis Branch = new ElementLis("Branch",sBranch,OLifEExtension1);
	ElementLis Teller = new ElementLis("Teller",sTeller,OLifEExtension1);
	ElementLis TransNo = new ElementLis("TransNo",sTransNo,OLifEExtension1);
	ElementLis SourceType = new ElementLis("SourceType",sSourceType,"tc",sSourceType,OLifEExtension1);
 //	TXLifeRequest.addContent(TransRefGUID);                 
//	TransRefGUID.setText(sTransRefGUID);                        
//	TransExeDate.setText(sTransRefGUID);            
// 	TransExeTime.setText(sTransRefGUID);  

// System.out.println("************************���ñ���·��************************");	
//	StringBuilder mFilePath = new StringBuilder(SysInfo.cHome);
//	System.out.println("mFilePath1************************"+mFilePath+"************************");   
//		int number =(mFilePath.length()-1);
//		int num = (number-8);
//		mFilePath.delete(num, number);
 //	System.out.println("mFilePath2************************"+mFilePath+"************************");  
//		mFilePath.append("msg"); 
		 //.append("1").append('/');
		 //.append(DateUtil.getCurDate("yyyy/yyyyMM/yyyyMMdd/"));
	//	String mInFilePath = mFilePath.toString()+"/icbcTestIn.xml";		
	//	String mOutFilePath = mFilePath.toString()+"/icbcTestReturn.xml";
		 
//System.out.println("************************"+mInFilePath+"************************");   
//System.out.println("************************"+mOutFilePath+"************************");  

System.out.println("************************װ��Document************************");	
   
 			Document pXmlDoc = new Document(TXLife);	
 			JdomUtil.print(pXmlDoc);
//System.out.println("************************1************************");	
	//		FileOutputStream tFos = new FileOutputStream(mInFilePath); 
//System.out.println("************************2************************");	
	//JdomUtil.output(pXmlDoc, tFos,"GBK");
//System.out.println("************************3************************");		
	    
		
		int iPort = Integer.valueOf(sPort).intValue();
		ICBCTestUI mTestUI = new ICBCTestUI(sIp,iPort);  
//		InputStream mIs = new FileInputStream(mInFilePath);
	//	byte[] mOutBytes = mTestUI.sendRequest(sFlag, mIs);
		byte[] mOutBytes = mTestUI.sendRequestUI(sFlag, pXmlDoc);
System.out.println("************************4************************");		 
		Document mOutXmlDoc = JdomUtil.build(mOutBytes); 
	//	OutputStream mFos = new FileOutputStream(mOutFilePath);
	//	JdomUtil.output(mOutXmlDoc, mFos);
 
		//��ӡ�������� 
		//uiPrint = JdomUtil.toStringFmt(pXmlDoc);
		//System.out.println(uiPrint);		
	//	mFos.flush();  
	//	mFos.close(); 
	   
System.out.println("*****************3");


		 
		ResultCode = mOutXmlDoc.getRootElement().getChild("TXLifeResponse").getChild("TransResult").getChildTextTrim("ResultCode");
		ResultInfoDesc = mOutXmlDoc.getRootElement().getChild("TXLifeResponse").getChild("TransResult").getChild("ResultInfo").getChildTextTrim("ResultInfoDesc");

System.out.println(ResultCode + "  " + ResultInfoDesc);
		strNewTransNo = PubFun1.CreateMaxNo("TransNo",16);
	//	System.out.println("strNewTransNo = " + strNewTransNo);
        System.out.println("�������ݣ�"+ResultInfoDesc);
		if (ResultCode.equals("1234")) { // fail
	        Content = "����ʧ�ܣ�" + ResultInfoDesc ;
	    
            //jsp��String�ϲ��ܳ���"%",��������ʾ����
	       ResultInfoDesc = ResultInfoDesc.replace("%","");
	      
		} else { 
	      
	       System.out.println("��������2��"+Content);

	       if (sFlag.trim().equals("1013")) {
		      // rPrem = mOutXmlDoc.getRootElement().getChild("K_BI").getChild("Info").getChildTextTrim("Premium");
		       //RetBaseAmt = result.getRootElement().getChild("K_BI").getChild("Info").getChildTextTrim("BaseAmt");
		     //  int tPLength = RetPrem.length();
		     
		    //   if(tPLength>2){
		    //      RetPrem = RetPrem.substring(0,tPLength-2);
		    //   }
		   
		       Content = ResultInfoDesc + " �����ǣ�" + "RetPrem"+"Ԫ ";
		       }

	        System.out.println("-----------��ʼȡ����saveҳ�棩----------");
	
		   }  

	} catch (Exception e) {
		e.printStackTrace();
		ResultCode = "Fail";
		xmlContent= e.getMessage();  
		Content = e.getMessage(); 
	}
%>   
<script language="javascript">
   parent.fraInterface.afterSubmit("<%=ResultCode%>", "<%=ResultInfoDesc%>");
   parent.fraInterface.fm.TransRefGUID.value = '<%=strNewTransNo%>';

   parent.fraInterface.fm.xmlContent.value = '<%=xmlContent.toString()%>'; 
   //parent.fraInterface.fm.ContNo.value = 'strContNo';
</script>