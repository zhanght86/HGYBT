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
<%@page import="com.sinosoft.midplat.nbcb.NbcbConf"%>
<% 
	String ResultCode = null;
	String Content = null;
	String ResultInfoDesc = null; 
	String strNewTransNo = null;
	String xmlContent = null;
	String strNewHOAppFormNumber = null;
	String strNewProviderFormNumber = null;
	String uiPrint = null;   
%>
<%
////modefied by zhoupan 20120710
  try {
	  String sIp = request.getParameter("ip");
	  String sPort = request.getParameter("port");
	  String sFlag = request.getParameter("tranFlagCode");
	//���ж˽�������
	String sTransExeDate = request.getParameter("TransExeDate");
	sTransExeDate=DateUtil.date10to8(sTransExeDate);
	//���н���ʱ��    
	String sTransExeTime = DateUtil.getCur8Time(); 
	sTransExeTime=DateUtil.time8to6(sTransExeTime);
	//������ˮ��
	String sTransRefGUID = request.getParameter("TransRefGUID");
	//�ɽ�����ˮ��
	String sReqsrNo = request.getParameter("ReqsrNo");
	//��������
	String sRegionCode = request.getParameter("RegionCode").trim();
	//�������
	String sBranch = request.getParameter("Branch").trim();
	//��Ա����
	String sTeller = request.getParameter("Teller"); 
	//��������
	String sTransMode=request.getParameter("TransMode");
	//���շֹ�˾����
	String sBranthCmp=request.getParameter("BranthCmp");
	//Ͷ������
	String sSubmissionDate = request.getParameter("SubmissionDate");
	sSubmissionDate=DateUtil.date10to8(sSubmissionDate);
	//Ͷ�����
	String sHOAppFormNumber = request.getParameter("HOAppFormNumber");
	//������Ա����
	String sBkRckrNo=request.getParameter("BkRckrNo");
	//����������
	String sBkSaleName=request.getParameter("BkSaleName");
	//������Ա�ʸ�֤��
	String sBkSaleCertNo=request.getParameter("BkSaleCertNo");
	
	if(!sHOAppFormNumber.equals("")){
		//sHOAppFormNumber=CheckProlNo.retCheck(sHOAppFormNumber);
		}
	//����ӡˢ��
	String sProviderFormNumber="";
	//if(sFlag.equals("OPR001")){
		 sProviderFormNumber = request.getParameter("ProviderFormNumber");
	//	if(!sProviderFormNumber.equals("")){
	//		sProviderFormNumber = CheckProlNo.ReturnCheck(sProviderFormNumber);
	//		}
	//}
	
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
	sAppBirthDate=DateUtil.date10to8(sAppBirthDate);
	//Ͷ����֤����Ч��
	String sAppGovtTermDate = request.getParameter("AppGovtTermDate");
	sAppGovtTermDate=DateUtil.date10to8(sAppGovtTermDate);
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
	//Ͷ����������
	String sPbInCome = request.getParameter("PbInCome");
	//Ͷ���˼�ͥ������
	String sPbHomeInCome = request.getParameter("PbHomeInCome");
	//Ͷ���˾�������
	String sPbDenType = request.getParameter("PbDenType");
	//Ͷ�����뱻���˵Ĺ�ϵ
	String sAppToInsRelation	 = request.getParameter("AppToInsRelation");
	String sAppCountry = request.getParameter("AppCountry");
	String sApplJobCode = request.getParameter("ApplJobCode");
	
	//����������
	String sInsFullName = request.getParameter("InsFullName");
	//�������Ա�
	String sInsGender = request.getParameter("InsGender");
	String sInsGenderh = request.getParameter("InsGenderh");
	//������֤������
	System.out.println("-----"+sInsGender);
	if(sInsGender == null) sInsGender = sInsGenderh;
	String sInsGovtIDTCh = request.getParameter("sInsGovtIDTCh");
	String sInsGovtIDTC = request.getParameter("InsGovtIDTC");
	if(sInsGovtIDTC == null) sInsGovtIDTC = sInsGovtIDTCh;
	
	if(sInsGovtIDTC == null) sInsGovtIDTC = sAppGovtIDTC;
	//������֤������
	String sInsGovtID = request.getParameter("InsGovtID"); 
	//����������
	String sInsBirthDate = request.getParameter("InsBirthDate");
	sInsBirthDate=DateUtil.date10to8(sInsBirthDate);
	//������֤����Ч��
	String sInsGovtTermDate = request.getParameter("InsGovtTermDate");
	sInsGovtTermDate=DateUtil.date10to8(sInsGovtTermDate);
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
	String sInsCountry = request.getParameter("InsCountry");
	//������֪��־ 
	String sHealthIndicator	 = request.getParameter("HealthIndicator");

	String sInsuJobCode = request.getParameter("InsuJobCode");
	
	if(sHealthIndicator == null) sHealthIndicator = "N";
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
	sBnfBirthDate1=DateUtil.date10to8(sBnfBirthDate1);
	//������֤����Ч��
	String sBnfAdress1 = request.getParameter("BnfAdress1");
	//����ٷ���
	String sInterestPercent1 = request.getParameter("InterestPercent1"); 
	//����˳ ��
	String sSequence1 = request.getParameter("Sequence1");
	//�������뱻���˵Ĺ�ϵ
	String sBnfToInsRelation1 = request.getParameter("BnfToInsRelation1");
	//�������Ƿ�Ϊ����
	String sBeneficiaryIndicator = request.getParameter("BeneficiaryIndicator");
    //����������
    String sBenefiType1 = request.getParameter("BenefiType1");
	
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
	sBnfBirthDate2=DateUtil.date10to8(sBnfBirthDate2);
	//������֤����Ч��
	String sBnfAdress2 = request.getParameter("BnfAdress2");
	//����ٷ���
	String sInterestPercent2 = request.getParameter("InterestPercent2"); 
	//����˳ ��
	String sSequence2 = request.getParameter("Sequence2");
	//�������뱻���˵Ĺ�ϵ
	String sBnfToInsRelation2 = request.getParameter("BnfToInsRelation2");
	 //����������
    String sBenefiType2 = request.getParameter("BenefiType2");
	
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
	sBnfBirthDate3=DateUtil.date10to8(sBnfBirthDate3);
	//������֤����Ч��
	String sBnfAdress3 = request.getParameter("BnfAdress3");
	//����ٷ���
	String sInterestPercent3 = request.getParameter("InterestPercent3"); 
	//����˳ ��
	String sSequence3 = request.getParameter("Sequence3");
	//�������뱻���˵Ĺ�ϵ
	String sBnfToInsRelation3 = request.getParameter("BnfToInsRelation3");
	 //����������
    String sBenefiType3 = request.getParameter("BenefiType3");

	//���մ��� 
	String sProductCode = request.getParameter("ProductCode");
	//���� 
	String sIntialNumberOfUnits = request.getParameter("IntialNumberOfUnits");
	//���� 
	String sModalPremAmt = request.getParameter("ModalPremAmt"); 
	//����
	String sInitCovAmt = request.getParameter("InitCovAmt"); 
	//������������
	String sDurationMode = request.getParameter("DurationMode"); 
	//�������� 
	String sDuration = request.getParameter("Duration"); 
	//�ɷ���������
	String sPaymentDurationMode = request.getParameter("PaymentDurationMode");
	//�ɷ����� 
	String sPaymentDuration = request.getParameter("PaymentDuration"); 
	
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
	sLoanStartDate=DateUtil.date10to8(sLoanStartDate);
	//���������
	String sLoanEndDate = request.getParameter("LoanEndDate");
	sLoanEndDate=DateUtil.date10to8(sLoanEndDate);
	//���պ�ͬ��Ч����
	String sContractEffDate = request.getParameter("ContractEffDate");
	sContractEffDate=DateUtil.date10to8(sContractEffDate);
	//���պ�ͬ��������
	String sContractEndDate = request.getParameter("ContractEndDate");
	sContractEndDate=DateUtil.date10to8(sContractEndDate);
	//��������
	String sLoanProductCode = request.getParameter("LoanProductCode");
	
//****************��������Ϣ1********************��������Ϣ1*******************
 System.out.println("****************��������Ϣ1*******************��������Ϣ1*******************");
	//���մ��� 
	String sProductCode1 = request.getParameter("ProductCode1");
	//���� 
	String sIntialNumberOfUnits1 = request.getParameter("IntialNumberOfUnits1");
	//���� 
	String sModalPremAmt1 = request.getParameter("ModalPremAmt1"); 
	//����
	String sInitCovAmt1 = request.getParameter("InitCovAmt1");
	//������������
	String sDurationMode1 = request.getParameter("DurationMode1"); 
	//�������� 
	String sDuration1 = request.getParameter("Duration1"); 
	//�ɷ���������
	String sPaymentDurationMode1 = request.getParameter("PaymentDurationMode1");
	//�ɷ����� 
	String sPaymentDuration1 = request.getParameter("PaymentDuration1"); 
	
	String sPbDrawAge=request.getParameter("PbDrawAge");

	//���մ��� 
	String sProductCode2 = request.getParameter("ProductCode2");
	//���� 
	String sIntialNumberOfUnits2 = request.getParameter("IntialNumberOfUnits2");
	//���� 
	String sModalPremAmt2 = request.getParameter("ModalPremAmt2"); 
	//����
	String sInitCovAmt2 = request.getParameter("InitCovAmt2");
	
	//������������
	String sDurationMode2 = request.getParameter("DurationMode2"); 
	//�������� 
	String sDuration2 = request.getParameter("Duration2"); 
	//�ɷ���������
	String sPaymentDurationMode2 = request.getParameter("PaymentDurationMode2");
	//�ɷ����� 
	String sPaymentDuration2 = request.getParameter("PaymentDuration2"); 
		
		String sAccCode1 = request.getParameter("AccCode1");
		String sAccCodeName1 = request.getParameter("AccCodeName1");
		String sAllocPercent1 = request.getParameter("AllocPercent1");
		String sAccCode2 = request.getParameter("AccCode2");
		String sAccCodeName2 = request.getParameter("AccCodeName2");
		String sAllocPercent2 = request.getParameter("AllocPercent2");
		String sAccCode3 = request.getParameter("AccCode3");
		String sAccCodeName3 = request.getParameter("AccCodeName3");
		String sAllocPercent3 = request.getParameter("AllocPercent3");	
		String sAccCode4 = request.getParameter("AccCode4");
		String sAccCodeName4 = request.getParameter("AccCodeName4");
		String sAllocPercent4 = request.getParameter("AllocPercent4");	
		String sAccCode5 = request.getParameter("AccCode5");
		String sAccCodeName5 = request.getParameter("AccCodeName5");
		String sAllocPercent5 = request.getParameter("AllocPercent5");	

	//�ɷѷ�ʽ
	String sPaymentMode = request.getParameter("PaymentMode");
	//�ɷ���ʽ(Ƶ��)
	String sPaymentMethod = request.getParameter("PaymentMethod");
	//��ȡ��ʽ
	String sBenefitMode = request.getParameter("BenefitMode");
	//������ȡ��ʽ
	String sDivType = request.getParameter("DivType"); 
	//�������ͷ�ʽ
	String sPolicyDeliveryMethod = request.getParameter("PolicyDeliveryMethod"); 
	//�Զ��潻��־
	String sAutoPayFlag =request.getParameter("AutoPayFlag");
	
	//�����˺�
	String sAccountNumber = request.getParameter("AccountNumber"); 
	//�ʻ�����
	String sAcctHolderName = request.getParameter("AcctHolderName");
    String sPbDrawAgeTag=request.getParameter("PbDrawAgeTag");
	String sSpecialClause = request.getParameter("SpecialClause");
	String sOccupationIndicator = request.getParameter("OccupationIndicator");
	//Ͷ�����ڱ�־
	String sInvestDateInd = request.getParameter("InvestDateInd");
	//�״ζ���׷�ӱ���
	String sFirstSuperaddAmt = request.getParameter("FirstSuperaddAmt");
	
	
	int iPaymentAmt = 0;
	if(sProductCode.equals("001")){
		if(sModalPremAmt == null || "".equals(sModalPremAmt)){
			sModalPremAmt="0";
		}
		iPaymentAmt = Integer.valueOf(sModalPremAmt).intValue();
	}else if(sProductCode.equals("002")){
		if(sModalPremAmt == null || "".equals(sModalPremAmt)){
			sModalPremAmt="0";
		}
		
		if(sModalPremAmt1 == null || "".equals(sModalPremAmt1)){
			sModalPremAmt1="0";
		}
		if(sModalPremAmt2 == null || "".equals(sModalPremAmt2)){
			sModalPremAmt2="0";
		}
		if(sFirstSuperaddAmt == null || "".equals(sFirstSuperaddAmt)){
			sFirstSuperaddAmt="0";
		}
		if(sProductCode1.equals("102") || sProductCode2.equals("101")){
		iPaymentAmt = Integer.valueOf(sModalPremAmt).intValue()+Integer.valueOf(sFirstSuperaddAmt).intValue()+Integer.valueOf(sModalPremAmt1).intValue() +Integer.valueOf(sModalPremAmt2).intValue();
		}else{
			iPaymentAmt = Integer.valueOf(sModalPremAmt).intValue();
		}
	}
	String sPaymentAmt = String.valueOf(iPaymentAmt);	
	


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
		xmlContent +="\t ������Ա����+"+sBkRckrNo+"\\n";
		xmlContent +="\t ������Ա����+"+sBkSaleName+"\\n";
		xmlContent +="\t ������Ա�ʸ�֤��+"+sBkSaleCertNo+"\\n";
		
		xmlContent += "����Ͷ������Ϣ\\n";
		xmlContent += "  Ͷ��������: " + sAppFullName;
		xmlContent += "\t\t\tͶ�����Ա�: " + sAppGender + "\\n";
		xmlContent += "  Ͷ����֤������: " + sAppGovtIDTC ;
		xmlContent += "\t\tͶ����֤������: " + sAppGovtID + "\\n";
		xmlContent += "  Ͷ��������: " + sAppBirthDate ;
		xmlContent += "\t\tͶ���˵������� : " + sAppAddrLine + "\\n";		
		xmlContent += "  Ͷ���˼�ͥ�绰: " + sAppDialNumber1;
		xmlContent += "\tͶ�����ƶ��绰: " + sAppDialNumber3+ "\\n";
		xmlContent += "  Ͷ�����뱻���˵Ĺ�ϵ: " + sAppToInsRelation+ "\\n";		
		xmlContent += "  Ͷ����������: " + sPbInCome+ "\\n";		
		xmlContent += "  Ͷ���˼�ͥ������: " + sPbHomeInCome+ "\\n";	
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
		xmlContent += "\t\t����(��): " +  sModalPremAmt;  
		xmlContent += "\t\t����(��): " + sInitCovAmt + "\\n";  
		xmlContent += "  ������������: " + sDurationMode ;
		xmlContent += "\t\t�������� : " + sDuration ;		
		xmlContent += "\t\t�ɷ���������: " + sPaymentDurationMode ;		
		xmlContent += "\t\t�ɷ�����: " + sPaymentDuration + "\\n";	
		 
		if(!(sProductCode1==null||sProductCode1.equals(""))){
		xmlContent += "2����������Ϣ1\\n";
		xmlContent += "  �����մ���: " + sProductCode1;
		xmlContent += "\t\t\t����: " + sIntialNumberOfUnits1 ;
		xmlContent += "\t\t����(��): " + sInitCovAmt1 ; 
		xmlContent += "\t\t����(��): " + sModalPremAmt1 + "\\n";  
		xmlContent += "  ������������: " + sDurationMode1 ;
		xmlContent += "\t\t�������� : " + sDuration1 ;		
		xmlContent += "\t\t�ɷ���������: " + sPaymentDurationMode1 ;		
		xmlContent += "\t\t�ɷ�����: " + sPaymentDuration1 + "\\n";
		}
		   
		if(!(sProductCode2==null||sProductCode2.equals(""))){
		xmlContent += "3����������Ϣ2\\n";
		xmlContent += "  �����մ���: " + sProductCode2;
		xmlContent += "\t\t\t����: " + sIntialNumberOfUnits2 ;
		xmlContent += "\t\t����(��): " + sInitCovAmt2 ; 
		xmlContent += "\t\t����(��): " + sModalPremAmt1 + "\\n";  
		xmlContent += "  ������������: " + sDurationMode2 ;
		xmlContent += "\t\t�������� : " + sDuration2 ;		
		xmlContent += "\t\t�ɷ���������: " + sPaymentDurationMode1 ;		
		xmlContent += "\t\t�ɷ�����: " + sPaymentDuration2 + "\\n";
		} 
		
		if(!(sAccCode1==null||sAccCode1.equals(""))){
			xmlContent += "3��Ͷ���˻���Ϣ\\n";
			xmlContent += "  Ͷ���˻�1: " + sAccCode1;
			xmlContent += "\t\t\tͶ�ʱ���: " + sAllocPercent1 ;
			xmlContent += "\t\tͶ���˻�2: " + sAccCode2 ; 
			xmlContent += "\t\tͶ�ʱ���: " + sAllocPercent2 + "\\n";  
			xmlContent += "  Ͷ���˻�3: " + sAccCode3 ;
			xmlContent += "\t\tͶ�ʱ���: " + sAllocPercent3 + "\\n";
			xmlContent += "  Ͷ���˻�4: " + sAccCode4 ;
			xmlContent += "\t\tͶ�ʱ���: " + sAllocPercent4 + "\\n";
			xmlContent += "  Ͷ���˻�5: " + sAccCode5 ;
			xmlContent += "\t\tͶ�ʱ���: " + sAllocPercent5 + "\\n";
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
		xmlContent += "\t\tͶ�����ڱ�־ : " + sInvestDateInd;	
		xmlContent += "\t�״ζ���׷�ӱ���: " + sFirstSuperaddAmt + "\\n";
	
String InsuId = NbcbConf.newInstance().getConf().getRootElement().getChild("bank").getAttributeValue("insu");
		
 System.out.println("************************װ��XML************************");		
//���ڵ�
ElementLis REQUEST = new ElementLis("REQUEST");
ElementLis DIST = new ElementLis("DIST",REQUEST);	

ElementLis BANK = new ElementLis("BANK","NBYH",DIST);
ElementLis ZONE = new ElementLis("ZONE",sRegionCode,DIST);
ElementLis DEPT = new ElementLis("DEPT",sBranch,DIST);
ElementLis TELLER = new ElementLis("TELLER",sTeller,DIST);
ElementLis FINANCIALID = new ElementLis("FINANCIALID",sBkRckrNo,DIST);
ElementLis FINANCIALNAME = new ElementLis("FINANCIALNAME",sBkSaleName,DIST);

ElementLis BUSI = new ElementLis("BUSI",REQUEST);
ElementLis TRSDATE = new ElementLis("TRSDATE",sTransExeDate,BUSI);
ElementLis TRANS = new ElementLis("TRANS",sTransRefGUID,BUSI);

ElementLis CONTENT = new ElementLis("CONTENT",BUSI);

if(sFlag.equals("1")){
ElementLis CATE = new ElementLis("CATE","2",BUSI);
ElementLis SUBJECT = new ElementLis("SUBJECT","1",BUSI);
ElementLis MAIN = new ElementLis("MAIN",CONTENT);
ElementLis APPNO = new ElementLis("APPNO",sHOAppFormNumber,MAIN);
ElementLis CONTPRTNO = new ElementLis("BILL_USED",sProviderFormNumber,MAIN);
ElementLis APPDATE = new ElementLis("APPDATE",sSubmissionDate,MAIN);
ElementLis PAYACC = new ElementLis("PAYACC",sAccountNumber,MAIN);
ElementLis DELIVER = new ElementLis("DELIVER","1",MAIN);
ElementLis CALL_T = new ElementLis("CALL_T",MAIN);
ElementLis SPEC = new ElementLis("SPEC",MAIN);
//Ͷ����
ElementLis TBR = new ElementLis("TBR",CONTENT);
ElementLis tNAME = new ElementLis("NAME",sAppFullName,TBR);
ElementLis tSEX = new ElementLis("SEX",sAppGender,TBR);
ElementLis tBIRTH = new ElementLis("BIRTH",sAppBirthDate,TBR);
ElementLis tIDTYPE = new ElementLis("IDTYPE",sAppGovtIDTC,TBR);
ElementLis tIDNO = new ElementLis("IDNO",sAppGovtID,TBR);
ElementLis tIDVALIDATE = new ElementLis("IDVALIDATE",sAppGovtTermDate,TBR);//δȷ�� ֤����Ч����
ElementLis tADDR = new ElementLis("ADDR",sAppLine1,TBR);
ElementLis tZIP = new ElementLis("ZIP",sAppZip,TBR);
ElementLis tTEL = new ElementLis("TEL",sAppDialNumber1,TBR);
ElementLis tMP = new ElementLis("MP",sAppDialNumber3,TBR);
ElementLis tLITTLE_MOBILE = new ElementLis("LITTLE_MOBILE",TBR);
ElementLis tEMAIL = new ElementLis("EMAIL",sAppAddrLine,TBR);
ElementLis tBBR_RELA = new ElementLis("BBR_RELA",sAppToInsRelation,TBR);
ElementLis tOccupation = new ElementLis("Occupation",TBR);
ElementLis tIncomeYear = new ElementLis("IncomeYear",sPbInCome,TBR);
ElementLis tFamilyEstSalary = new ElementLis("FamilyEstSalary",sPbHomeInCome,TBR);
ElementLis tResident = new ElementLis("Resident",sPbDenType,TBR);
//������
ElementLis BBR = new ElementLis("BBR",CONTENT);
ElementLis bNAME = new ElementLis("NAME",sInsFullName,BBR);
ElementLis bSEX = new ElementLis("SEX",sInsGender,BBR);
ElementLis bBIRTH = new ElementLis("BIRTH",sInsBirthDate,BBR);
ElementLis bIDTYPE = new ElementLis("IDTYPE",sInsGovtIDTC,BBR);
ElementLis bIDNO = new ElementLis("IDNO",sInsGovtID,BBR);
ElementLis bIDVALIDATE = new ElementLis("IDVALIDATE",sInsGovtTermDate,BBR);//δȷ��
ElementLis bADDR = new ElementLis("ADDR",sInsLine1,BBR);
ElementLis bZIP = new ElementLis("ZIP",sInsZip,BBR);
ElementLis bTEL = new ElementLis("TEL",sInsDialNumber1,BBR);
ElementLis bLITTLE_MOBILE = new ElementLis("LITTLE_MOBILE",BBR);
ElementLis bEMAIL = new ElementLis("EMAIL",sInsAddrLine,BBR);
//����������
if(sBeneficiaryIndicator.equals("Y")){
	ElementLis SYR = new ElementLis("SYR",CONTENT);
	ElementLis sNAME = new ElementLis("NAME",SYR);
	ElementLis sSEX = new ElementLis("SEX",SYR);
	ElementLis sBIRTH = new ElementLis("BIRTH",SYR);
	ElementLis sIDTYPE = new ElementLis("IDTYPE",SYR);
	ElementLis sIDNO = new ElementLis("IDNO",SYR);
	ElementLis sIDVALIDATE = new ElementLis("IDVALIDATE",SYR);//δȷ��
	ElementLis sORDER = new ElementLis("ORDER",SYR);
	ElementLis sRATIO = new ElementLis("RATIO",SYR);
	ElementLis sBBR_RELA = new ElementLis("BBR_RELA","Z",SYR);//��Ҫδ��
}else if(!(sBnfFullName1==null||sBnfFullName1.equals(""))){
	ElementLis SYR = new ElementLis("SYR",CONTENT);
	ElementLis sNAME = new ElementLis("NAME",sBnfFullName1,SYR);
	ElementLis sSEX = new ElementLis("SEX",sBnfGender1,SYR);
	ElementLis sBIRTH = new ElementLis("BIRTH",sBnfBirthDate1,SYR);
	ElementLis sIDTYPE = new ElementLis("IDTYPE",sBnfGovtIDTC1,SYR);
	ElementLis sIDNO = new ElementLis("IDNO",sBnfGovtID1,SYR);
	ElementLis sIDVALIDATE = new ElementLis("IDVALIDATE",sBnfAdress1,SYR);//δȷ��
	ElementLis sORDER = new ElementLis("ORDER",sSequence1,SYR);
	ElementLis sRATIO = new ElementLis("RATIO",sInterestPercent1,SYR);
	ElementLis sBBR_RELA = new ElementLis("BBR_RELA",sBnfToInsRelation1,SYR);//��Ҫδ��
}
ElementLis bMP = new ElementLis("MP",sInsDialNumber3,BBR);
//������
if(!(sBnfFullName2==null||sBnfFullName2.equals(""))){
	ElementLis SYR = new ElementLis("SYR",CONTENT);
	ElementLis sNAME = new ElementLis("NAME",sBnfFullName2,SYR);
	ElementLis sSEX = new ElementLis("SEX",sBnfGender2,SYR);
	ElementLis sBIRTH = new ElementLis("BIRTH",sBnfBirthDate2,SYR);
	ElementLis sIDTYPE = new ElementLis("IDTYPE",sBnfGovtIDTC2,SYR);
	ElementLis sIDNO = new ElementLis("IDNO",sBnfGovtID2,SYR);
	ElementLis sIDVALIDATE = new ElementLis("IDVALIDATE",sBnfAdress2,SYR);//δȷ��
	ElementLis sORDER = new ElementLis("ORDER",sSequence2,SYR);
	ElementLis sRATIO = new ElementLis("RATIO",sInterestPercent2,SYR);
	ElementLis sBBR_RELA = new ElementLis("BBR_RELA",sBnfToInsRelation2,SYR);//��Ҫδ��
}
//������
if(!(sBnfFullName3==null||sBnfFullName3.equals(""))){
	ElementLis SYR = new ElementLis("SYR",CONTENT);
	ElementLis sNAME = new ElementLis("NAME",sBnfFullName3,SYR);
	ElementLis sSEX = new ElementLis("SEX",sBnfGender3,SYR);
	ElementLis sBIRTH = new ElementLis("BIRTH",sBnfBirthDate3,SYR);
	ElementLis sIDTYPE = new ElementLis("IDTYPE",sBnfGovtIDTC3,SYR);
	ElementLis sIDNO = new ElementLis("IDNO",sBnfGovtID3,SYR);
	ElementLis sIDVALIDATE = new ElementLis("IDVALIDATE",sBnfAdress3,SYR);//δȷ��
	ElementLis sORDER = new ElementLis("ORDER",sSequence3,SYR);
	ElementLis sRATIO = new ElementLis("RATIO",sInterestPercent3,SYR);
	ElementLis sBBR_RELA = new ElementLis("BBR_RELA",sBnfToInsRelation3,SYR);//��Ҫδ��
}
//����
ElementLis PTS = new ElementLis("PTS",CONTENT);
ElementLis PT = new ElementLis("PT",PTS);
ElementLis ID = new ElementLis("ID",sProductCode,PT);
ElementLis FLAG = new ElementLis("FLAG","1",PT);//�����ձ�־
ElementLis UNIT = new ElementLis("UNIT",sIntialNumberOfUnits,PT);
ElementLis CRG_T = new ElementLis("CRG_T",sPaymentMode,PT);
ElementLis CRG_Y = new ElementLis("CRG_Y",sPaymentDuration,PT);
ElementLis COVER_T = new ElementLis("COVER_T",sDurationMode,PT);
ElementLis COVER_Y = new ElementLis("COVER_Y",sDuration,PT);
ElementLis PREMIUM = new ElementLis("PREMIUM",sModalPremAmt,PT);
ElementLis AMNT = new ElementLis("AMNT",sInitCovAmt,PT);
ElementLis DRAW_T = new ElementLis("DRAW_T",PT);
ElementLis DRAW_FST = new ElementLis("DRAW_FST",PT);
ElementLis DRAW_LST = new ElementLis("DRAW_LST",PT);
ElementLis HLLQ_T = new ElementLis("HLLQ_T",sDivType,PT);
ElementLis INVEST = new ElementLis("INVEST",PT);
ElementLis NVEST_ACC_TYPE = new ElementLis("NVEST_ACC_TYPE",INVEST);
ElementLis INVEST_ACC = new ElementLis("INVEST_ACC",INVEST);
ElementLis ACC_ID = new ElementLis("ACC_ID",INVEST_ACC);
ElementLis RATE = new ElementLis("RATE",INVEST_ACC);

//������1
if(!sProductCode1.equals("") && sProductCode1 !=null){
	ElementLis PT1 = new ElementLis("PT",PTS);
	ElementLis ID1 = new ElementLis("ID",sProductCode1,PT1);
	ElementLis FLAG1 = new ElementLis("FLAG","0",PT1);//�����ձ�־
	ElementLis UNIT1 = new ElementLis("UNIT",sIntialNumberOfUnits1,PT1);
	ElementLis CRG_T1 = new ElementLis("CRG_T",sPaymentDurationMode1,PT1);
	ElementLis CRG_Y1 = new ElementLis("CRG_Y",sPaymentDuration1,PT1);
	ElementLis COVER_T1 = new ElementLis("COVER_T",sDurationMode1,PT1);
	ElementLis COVER_Y1 = new ElementLis("COVER_Y",sDuration1,PT1);
	ElementLis PREMIUM1 = new ElementLis("PREMIUM",sModalPremAmt1,PT1);
	ElementLis AMNT1 = new ElementLis("AMNT",sInitCovAmt1,PT1);
	ElementLis DRAW_T1 = new ElementLis("DRAW_T",PT1);
	ElementLis DRAW_FST1 = new ElementLis("DRAW_FST",PT1);
	ElementLis DRAW_LST1 = new ElementLis("DRAW_LST",PT1);
	ElementLis HLLQ_T1 = new ElementLis("HLLQ_T",sDivType,PT1);
	ElementLis INVEST1 = new ElementLis("INVEST",PT1);
	ElementLis NVEST_ACC_TYPE1 = new ElementLis("NVEST_ACC_TYPE",INVEST1);
	ElementLis INVEST_ACC1 = new ElementLis("INVEST_ACC",INVEST1);
	ElementLis ACC_ID1 = new ElementLis("ACC_ID",INVEST_ACC1);
	ElementLis RATE1 = new ElementLis("RATE",INVEST_ACC1);
}

ElementLis HEALTH = new ElementLis("HEALTH",CONTENT);
ElementLis NOTICE = new ElementLis("NOTICE",sHealthIndicator,HEALTH);

ElementLis BILL = new ElementLis("BILL",CONTENT);
ElementLis BILL_USED = new ElementLis("BILL_USED",BILL);
ElementLis BILL_T = new ElementLis("BILL_T",BILL);

ElementLis LFBF = new ElementLis("LFBF",CONTENT);
ElementLis LifeBnft = new ElementLis("LifeBnft",LFBF);
ElementLis Nation = new ElementLis("Nation",LifeBnft);
ElementLis Relation = new ElementLis("Relation",LifeBnft);
ElementLis Province = new ElementLis("Province",LifeBnft);
ElementLis IdNo = new ElementLis("IdNo",LifeBnft);
ElementLis BirthDate = new ElementLis("BirthDate",LifeBnft);
ElementLis Name = new ElementLis("Name",LifeBnft);
ElementLis Sex = new ElementLis("Sex",LifeBnft);
ElementLis HomeAdrress = new ElementLis("HomeAdrress",LifeBnft);
ElementLis ContactZip = new ElementLis("ContactZip",LifeBnft);
ElementLis ContactPhone = new ElementLis("ContactPhone",LifeBnft);
ElementLis Email = new ElementLis("Email",LifeBnft);
ElementLis Occupation = new ElementLis("Occupation",LifeBnft);

ElementLis LifeBnftAssign = new ElementLis("LifeBnftAssign",LFBF);
ElementLis Beneficiary = new ElementLis("Beneficiary",LifeBnftAssign);
ElementLis BnftType = new ElementLis("BnftType",Beneficiary);
ElementLis BnftNo = new ElementLis("BnftNo",Beneficiary);
ElementLis Percent = new ElementLis("Percent",Beneficiary);

ElementLis AXDGZ = new ElementLis("AXDGZ",CONTENT);
ElementLis LoanNo = new ElementLis("LoanNo",sContractNo,AXDGZ);
ElementLis LoanBank = new ElementLis("LoanBank",AXDGZ);
ElementLis LoanDate = new ElementLis("LoanDate",sLoanStartDate,AXDGZ);
ElementLis LoanEndDate = new ElementLis("LoanEndDate",sLoanEndDate,AXDGZ);
ElementLis LoanType = new ElementLis("LoanType",sLoanProductCode,AXDGZ);
ElementLis AccNo = new ElementLis("AccNo",sLoanAccountNo,AXDGZ);
ElementLis LoanPrem = new ElementLis("LoanPrem",sLoanAmount,AXDGZ);
ElementLis InsuDate = new ElementLis("InsuDate",sContractEffDate,AXDGZ);
ElementLis InsuEndDate = new ElementLis("InsuEndDate",sContractEndDate,AXDGZ);
}else if(sFlag.equals("2")){
	ElementLis CATE = new ElementLis("CATE","102",BUSI);
	ElementLis SUBJECT = new ElementLis("SUBJECT","1",BUSI);
ElementLis OLDTRANS = new ElementLis("OLDTRANS",sReqsrNo,CONTENT);
ElementLis APPNO = new ElementLis("APPNO",sHOAppFormNumber,CONTENT);
}
	Document pXmlDoc = new Document(REQUEST);	
    JdomUtil.print(pXmlDoc);
	System.out.println("������Ϣ������");
	JdomUtil.print(pXmlDoc);
	int iPort = Integer.valueOf(sPort).intValue();
	NCCBTestUI mTestUI = new NCCBTestUI(sIp,iPort);  
	InputStream inputStream = new ByteArrayInputStream(JdomUtil.toBytes(pXmlDoc));
	byte[] mOutBytes = mTestUI.sendRequest(inputStream);
	Document mOutXmlDoc = JdomUtil.build(mOutBytes); 
	
	JdomUtil.print(mOutXmlDoc);
	ResultCode = mOutXmlDoc.getRootElement().getChild("BUSI").getChild("CONTENT").getChildTextTrim("REJECT_CODE");
	ResultInfoDesc = mOutXmlDoc.getRootElement().getChild("BUSI").getChild("CONTENT").getChildTextTrim("REJECT_DESC");
	PubFun1 pubfun = new PubFun1();
	if (ResultCode.equals("1")) {
		strNewTransNo = pubfun.CreateMaxNo("TransNo", 16);
		strNewHOAppFormNumber = "2"+pubfun.CreateMaxNo("PrtNo",9);
	      System.out.println("-----------��ʼȡ����saveҳ�棩----------");
	} else {
		strNewTransNo = sTransRefGUID;
	    ResultInfoDesc = ResultInfoDesc.replace("%","%25");
		Content = "����ʧ�ܣ�" + ResultInfoDesc ;
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