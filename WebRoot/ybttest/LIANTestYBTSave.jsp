<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.io.*"%>
<%@page import="org.jdom.Document"%>
<%@page import="org.jdom.Element"%>
<%@page import="java.net.ConnectException"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="java.util.Calendar"%>
<%@page import="test.*"%>
<%@page import="test.*"%>
<%@page import="org.jdom.output.*"%>
<%@page import="org.jdom.input.*"%>
<%@page import="org.jdom.Attribute"%>
<jsp:directive.page import="java.util.HashMap" />
<%
	System.out.println("--> ins");
	String ResultFlag = null;
	String Content = null;
	String ResultMesg = null;
	String strNewTransNo = null;
	String xmlContent = null;
	String uiPrint = null;
%>

<%
	try {

		//****************��������Ϣ1*******************��������Ϣ1*******************
		System.out
				.println("****************���ж���Ϣ1*******************���ж���Ϣ1*******************");
		//���ж˽�������
		String sBankDate = request.getParameter("BankDate").trim();
		//���н���ʱ��    
		String sBankTime = DateUtil.getCur8Time();
		//������ˮ��
		String sTransrNo = request.getParameter("TransrNo");
		//��������
		String sZoneNo = request.getParameter("ZoneNo");
		//�������
		String sBrNo = request.getParameter("BrNo");
		//��Ա����
		String sTellerNo = request.getParameter("TellerNo");
		//Ͷ������
		String sPolApplyDate = request.getParameter("PolApplyDate");
		//Ͷ�����
		String sProposalContNo = request.getParameter("ProposalContNo");
       //����ӡˢ��
		String sContPrtNo = request.getParameter("PrtNo");
		//���ױ�־
		String sFunctionFlag = "01";
		//���ո��˻�����
		String sAccName = request.getParameter("AccName");
		//�����ʺ�
		String sAccNo = request.getParameter("AccNo");

		//****************Ͷ������Ϣ*******************Ͷ������Ϣ*******************
		System.out
				.println("****************Ͷ������Ϣ*******************Ͷ������Ϣ*******************");
		//Ͷ��������
		String sApplName = request.getParameter("ApplName");
		//Ͷ�����Ա�
		String sApplSex = request.getParameter("ApplSex");
		//Ͷ����֤������
		String sApplIDType = request.getParameter("ApplIDType");
		//Ͷ����֤������
		String sApplIDNo = request.getParameter("ApplIDNo");
		//Ͷ��������
		String sApplBirthday = request.getParameter("ApplBirthday");
		//Ͷ���˵�������
		String sApplEmail = request.getParameter("ApplEmail");
		//Ͷ����ͨѶ��ַ	
		String sApplAddress = request.getParameter("ApplAddress");
		//Ͷ������������
		String sApplZipCode = request.getParameter("ApplZipCode");
		//Ͷ���˼�ͥ�绰
		String sApplPhone = request.getParameter("ApplPhone");
		//Ͷ�����ƶ��绰
		String sApplMobile = request.getParameter("ApplMobile");
		//Ͷ�����뱻���˵Ĺ�ϵ
		String sApplRelaToInsured = request
				.getParameter("ApplRelaToInsured");
		//Ͷ����֤����Ч��
		String sApplValidYear = request.getParameter("ApplValidYear");
		//Ͷ����ְҵ����
		String sApplJobCode = request.getParameter("ApplJobCode");

		//****************��������Ϣ*******************��������Ϣ*******************
		System.out
				.println("****************��������Ϣ*******************��������Ϣ*******************");
		//����������
		String sInsuName = request.getParameter("InsuName");
		//�������Ա�
		String sInsuSex = request.getParameter("InsuSex");
		String sInsuSexh = request.getParameter("InsuSexh");
		//������֤������
		System.out.println("-----" + sInsuSex);
		if (sInsuSex == null)
			sInsuSex = sInsuSexh;

		System.out.println("-----" + sInsuSexh);

		String sInsuIDType = request.getParameter("InsuIDType");
		String sInsuIDTypeh = request.getParameter("InsuIDTypeh");

		if (sInsuIDType == null)
			sInsuIDType = sInsuIDTypeh;

		if (sInsuIDType == null)
			sInsuIDType = sApplIDType;
		System.out.println("-----" + sInsuSex);
		//������֤������
		String sInsuIDNo = request.getParameter("InsuIDNo");
		//����������
		String sInsuBirthday = request.getParameter("InsuBirthday");
		//�����˵�������
		String sInsuEmail = request.getParameter("InsuEmail");
		//������ͨѶ��ַ	 
		String sInsuAddress = request.getParameter("InsuAddress");
		//��������������
		String sInsuZipCode = request.getParameter("InsuZipCode");
		//�����˼�ͥ�绰
		String sInsuPhone = request.getParameter("InsuPhone");
		//�������ƶ��绰
		String sInsuMobile = request.getParameter("InsuMobile");
		//������֤����Ч��
		String sInsuValidYear = request.getParameter("InsuValidYear");
		//������ְҵ����
		String sInsuJobCode = request.getParameter("InsuJobCode");
		//������֪��־ 
		String sHealthFlag = request.getParameter("HealthFlag");
		if (sHealthFlag == null)
			sHealthFlag = "N";

		//****************��������Ϣ1*******************��������Ϣ1*******************
		System.out
				.println("****************��������Ϣ1*******************��������Ϣ1*******************");
		//�����˸���
		String sBnfsCount = request.getParameter("BnfsCount");

		//����������
		String sBnfType1 = request.getParameter("BnfType1");
		//���������� 
		String sBnfName1 = request.getParameter("BnfName1");
		//�������Ա�
		String sBnfSex1 = request.getParameter("BnfSex1");
		//������֤������
		String sBnfIDType1 = request.getParameter("BnfIDType1");
		//������֤������
		String sBnfIDNo1 = request.getParameter("BnfIDNo1");
		//����������
		String sBnfBirthday1 = request.getParameter("BnfBirthday1");
		//����ٷ���
		String sBnfBnfLot1 = request.getParameter("BnfBnfLot1");
		//����˳��
		String sBnfBnfGrade1 = request.getParameter("BnfBnfGrade1");
		//�������뱻���˵Ĺ�ϵ
		String sBnfRelationToInsured1 = request
				.getParameter("BnfRelationToInsured1");
		//����֤����Ч��
		String sBnfValidYear1 = request.getParameter("BnfValidYear1");
		
		//������־
		//String sBeneficiaryIndicator = request.getParameter("BeneficiaryIndicator");
		//System.out.println("�������Ƿ�Ϊ��"+sBeneficiaryIndicator);

		//****************��������Ϣ2*******************��������Ϣ2*******************
		System.out
				.println("****************��������Ϣ2*******************��������Ϣ2*******************");
		//����������
		String sBnfType2 = request.getParameter("BnfType2");
		//���������� 
		String sBnfName2 = request.getParameter("BnfName2");
		//�������Ա�
		String sBnfSex2 = request.getParameter("BnfSex2");
		//������֤������
		String sBnfIDType2 = request.getParameter("BnfIDType2");
		//������֤������
		String sBnfIDNo2 = request.getParameter("BnfIDNo2");
		//����������
		String sBnfBirthday2 = request.getParameter("BnfBirthday2");
		//����ٷ���
		String sBnfBnfLot2 = request.getParameter("BnfBnfLot2");
		//����˳��
		String sBnfBnfGrade2 = request.getParameter("BnfBnfGrade2");
		//�������뱻���˵Ĺ�ϵ
		String sBnfRelationToInsured2 = request
				.getParameter("BnfRelationToInsured2");
				//����֤����Ч��
		String sBnfValidYear2 = request.getParameter("BnfValidYear2");

		//****************��������Ϣ3*******************��������Ϣ3*******************
		System.out
				.println("****************��������Ϣ3*******************��������Ϣ3*******************");
		//����������
		String sBnfType3 = request.getParameter("BnfType3");
		//���������� 
		String sBnfName3 = request.getParameter("BnfName3");
		//�������Ա�
		String sBnfSex3 = request.getParameter("BnfSex3");
		//������֤������
		String sBnfIDType3 = request.getParameter("BnfIDType3");
		//������֤������
		String sBnfIDNo3 = request.getParameter("BnfIDNo3");
		//����������
		String sBnfBirthday3 = request.getParameter("BnfBirthday3");
		//����ٷ���
		String sBnfBnfLot3 = request.getParameter("BnfBnfLot3");
		//����˳��
		String sBnfBnfGrade3 = request.getParameter("BnfBnfGrade3");
		//�������뱻���˵Ĺ�ϵ
		String sBnfRelationToInsured3 = request
				.getParameter("BnfRelationToInsured3");
		//����֤����Ч��
		String sBnfValidYear3 = request.getParameter("BnfValidYear3");

		//****************������Ϣ*******************������Ϣ*******************
		System.out
				.println("****************������Ϣ*******************������Ϣ*******************");
		//���ִ��� 
		String sCode = request.getParameter("Code");
		//���մ��� 
	//	String sMainRiskCode = request.getParameter("Code");
		//���� 
		String sMult = request.getParameter("Mult");
		//����
		String sAmnt = request.getParameter("Amnt");
		//���� 
		String sPrem = request.getParameter("Prem");
		//������������
		String sInsuYearFlag = request.getParameter("InsuYearFlag");
		//�������� 
		String sInsuYear = request.getParameter("InsuYear");
		//�ɷ���������
		String sPayEndYearFlag = request.getParameter("PayEndYearFlag");
		//�ɷ����� 
		String sPayEndYear = request.getParameter("PayEndYear");
		//��ȡ��������
		String sGetYearFlag = request.getParameter("GetYearFlag");
		//��ȡ���� 
		String sGetYear = request.getParameter("GetYear");
		//������ȡ��ʽ
		String sBonusGetMode = request.getParameter("BonusGetMode");

		//****************��������Ϣ1********************��������Ϣ1*******************
		System.out
				.println("****************��������Ϣ1*******************��������Ϣ1*******************");
		//���ִ��� 
		String sCode1 = request.getParameter("Code1");
		//���մ��� 
		String sMainRiskCode1 = request.getParameter("MainRiskCode1");
		//���� 
		String sMult1 = request.getParameter("Mult1");
		//����
		String sAmnt1 = request.getParameter("Amnt1");
		//���� 
		String sPrem1 = request.getParameter("Prem1");
		//������������
		String sInsuYearFlag1 = request.getParameter("InsuYearFlag1");
		//�������� 
		String sInsuYear1 = request.getParameter("InsuYear1");
		//�ɷ���������
		String sPayEndYearFlag1 = request
				.getParameter("PayEndYearFlag1");
		//�ɷ����� 
		String sPayEndYear1 = request.getParameter("PayEndYear1");
		//��ȡ��������
		String sGetYearFlag1 = request.getParameter("GetYearFlag1");
		//��ȡ���� 
		String sGetYear1 = request.getParameter("GetYear1");
		//������ȡ��ʽ
		String sBonusGetMode1 = request.getParameter("BonusGetMode1");

		//****************Ͷ���˻��ڵ�*******************Ͷ���˻��ڵ�*******************
		System.out
				.println("****************Ͷ���˻��ڵ�*******************Ͷ���˻��ڵ�*******************");

		String sAccNo1 = request.getParameter("AccNo1");
		String sRate1 = request.getParameter("Rate1");
		String sAccNo2 = request.getParameter("AccNo2");
		String sRate2 = request.getParameter("Rate2");
		String sAccNo3 = request.getParameter("AccNo3");
		String sRate3 = request.getParameter("Rate3");

		//****************Ͷ����Ϣ*******************Ͷ����Ϣ*******************	
		System.out
				.println("****************Ͷ����Ϣ*******************Ͷ����Ϣ*******************");
		//�ɷѷ�ʽ
		String sPayIntv = request.getParameter("PayIntv");
		//�����ʻ�
		//String sBankAccNo = request.getParameter("BankAccNo");
		//�ɷѷ�ʽ
		//String sPayIntv = request.getParameter("PayIntv");
		//�������ͷ�ʽ
		//String sPolicyDeliveryMethod = request.getParameter("PolicyDeliveryMethod"); 
		//�ʻ�����
		//String sAcctHolderName = request.getParameter("AcctHolderName");
		//�����˺�
		//String sAccountNumber = request.getParameter("AccountNumber"); 
		//ְҵ��֪��־
		//String sOccupationIndicator = request.getParameter("OccupationIndicator");
		//Ͷ�����ڱ�־
		//String sInvestDateInd = request.getParameter("InvestDateInd");
		//�״ζ���׷�ӱ���
		//String sFirstSuperaddAmt = request.getParameter("FirstSuperaddAmt");

		//int iPaymentAmt = 0;
		//if(sProductCode.equals("001")){
		//	if(sModalPremAmt == null || "".equals(sModalPremAmt)){
		//		sModalPremAmt="0";
		//	}
		//	iPaymentAmt = Integer.valueOf(sModalPremAmt);
		//}else if(sProductCode.equals("002")){
		//	if(sModalPremAmt == null || "".equals(sModalPremAmt)){
		//		sModalPremAmt="0";
		//	}
		//	
		//	if(sModalPremAmt1 == null || "".equals(sModalPremAmt1)){
		//		sModalPremAmt1="0";
		//	}
		//	if(sFirstSuperaddAmt == null || "".equals(sFirstSuperaddAmt)){
		//		sFirstSuperaddAmt="0";
		//	}
		//	if(sProductCode1.equals("101")){
		//	iPaymentAmt = Integer.valueOf(sModalPremAmt)+Integer.valueOf(sFirstSuperaddAmt)+Integer.valueOf(sModalPremAmt1);
		//	}else{
		//		iPaymentAmt = Integer.valueOf(sModalPremAmt);
		//	}
		//}
		//String sPaymentAmt = String.valueOf(iPaymentAmt);
		
		System.out
				.println("****************������Ϣ*******************������Ϣ*******************");
		//�����ͬ��
		String sLoanNo = request.getParameter("LoanNo");
		//�������
		String sLoanBank = request.getParameter("LoanBank");
		//��������
		String sLoanDate = request.getParameter("LoanDate");
		//�������
		String sLoanEndDate = request.getParameter("LoanEndDate");
		//��������
		String sLoanType = request.getParameter("LoanType");
		//�����˺�
		String sLoanAccNo = request.getParameter("LoanAccNo");
		//������
		String sLoanPrem = request.getParameter("LoanPrem");
		//������ʼ��
		String sInsuDate = request.getParameter("InsuDate");
		//����������
		String sInsuEndDate = request.getParameter("InsuEndDate");
		

		//****************ip-port-flag*******************ip-port-flag*******************	
		System.out
				.println("****************ip-port-flag*******************ip-port-flag*******************");
		String sIp = request.getParameter("ip");
		String sPort = request.getParameter("port");
		String sFlag = request.getParameter("tranFlagCode");

		//�ѱ�����Ϣ��ҳ����ʾ
		xmlContent = new String("");

		xmlContent += "һ��������Ϣ\\n";
		xmlContent += "  ���ж�����: " + sBankDate + "\\n";
		xmlContent += "  ��������: " + sZoneNo;
		xmlContent += "\t\t�������: " + sBrNo + "\\n";
		xmlContent += "  ��Ա����: " + sTellerNo;
		xmlContent += "\t\t������ˮ��: " + sTransrNo + "\\n";

		xmlContent += "����������Ϣ\\n";
		xmlContent += "  Ͷ������: " + sProposalContNo;
		xmlContent += "\t\tͶ������: " + sPolApplyDate + "\\n";
		//xmlContent += "  �ʻ�����: " + sAccName;
		xmlContent += "  �����ʻ�: " + sAccNo + "\\n";
		//xmlContent += "  ����ӡˢ��: " + request.getParameter("PrtNo") ;
		//xmlContent += "  \t�ܱ���: " + request.getParameter("TotalPrem") + "\\n\\n";

		xmlContent += "����Ͷ������Ϣ\\n";
		xmlContent += "  Ͷ��������: " + sApplName;
		xmlContent += "\t\tͶ�����Ա�: " + sApplSex + "\\n";
		xmlContent += "  Ͷ��������: " + sApplBirthday;
		xmlContent += "\t\tͶ����֤������: " + sApplIDType + "\\n";
		xmlContent += "  Ͷ����֤������: " + sApplIDNo;
		xmlContent += "\t\tͶ���˵������� : " + sApplEmail + "\\n";
		xmlContent += "  Ͷ���˼�ͥ�绰: " + sApplPhone;
		xmlContent += "\t\tͶ�����ƶ��绰: " + sApplMobile + "\\n";
		xmlContent += "  Ͷ����ͨ�ŵ�ַ: " + sApplAddress;
		xmlContent += "\t\tͶ�����ʱ�: " + sApplZipCode + "\\n";
		xmlContent += "  Ͷ�����뱻���˵Ĺ�ϵ: " + sApplRelaToInsured + "\\n";

		xmlContent += "�ġ���������Ϣ\\n";
		xmlContent += "  ����������: " + sInsuName;
		xmlContent += "\t\t�������Ա�: " + sInsuSex + "\\n";
		xmlContent += "  ����������: " + sInsuBirthday;
		xmlContent += "\t\t������֤������: " + sInsuIDType + "\\n";
		xmlContent += "  ������֤������: " + sInsuIDNo;
		xmlContent += "\t\t������ͨѶ��ַ: " + sInsuAddress + "\\n";
		xmlContent += "  ������ͨѶ�ʱ�: " + sInsuZipCode;
		xmlContent += "\t\t�����˹̶��绰: " + sInsuPhone + "\\n";
		xmlContent += "  �������ƶ��绰: " + sInsuMobile;
		xmlContent += "\t\t�����˵�������: " + sInsuEmail + "\\n";
		xmlContent += "  ������֪: " + sHealthFlag + "\\n";

		xmlContent += "�塢��������Ϣ\\n";

		xmlContent += "1����������Ϣ1\\n";
		xmlContent += "  ����������: " + sBnfType1 + "\\n";
		xmlContent += "  ����������: " + sBnfName1;
		xmlContent += "\t\t�������Ա�: " + sBnfSex1 + "\\n";
		xmlContent += "  ������֤������: " + sBnfIDType1;
		xmlContent += "\t\t������֤������: " + sBnfIDNo1 + "\\n";
		xmlContent += "  ����������: " + sBnfBirthday1;
		xmlContent += "\t\t������� : " + sBnfBnfLot1 + "\\n";
		xmlContent += "  ����˳��: " + sBnfBnfGrade1;
		xmlContent += "\t\t�������뱻���˹�ϵ: " + sBnfRelationToInsured1
				+ "\\n";

		if (!(sBnfName2 == null || sBnfName2.equals(""))) {
			xmlContent += "2����������Ϣ2\\n";
			xmlContent += "  ����������: " + sBnfType2 + "\\n";
			xmlContent += "  ����������: " + sBnfName2;
			xmlContent += "\t\t�������Ա�: " + sBnfSex2 + "\\n";
			xmlContent += "  ������֤������: " + sBnfIDType2;
			xmlContent += "\t\t������֤������: " + sBnfIDNo2 + "\\n";
			xmlContent += "  ����������: " + sBnfBirthday2;
			xmlContent += "\t\t������� : " + sBnfBnfLot2 + "\\n";
			xmlContent += "  ����˳��: " + sBnfBnfGrade2;
			xmlContent += "\t\t�������뱻���˹�ϵ: " + sBnfRelationToInsured2
					+ "\\n";
		}

		if (!(sBnfName3 == null || sBnfName3.equals(""))) {
			xmlContent += "3����������Ϣ3\\n";
			xmlContent += "  ����������: " + sBnfType3 + "\\n";
			xmlContent += "  ����������: " + sBnfName3;
			xmlContent += "\t\t�������Ա�: " + sBnfSex3 + "\\n";
			xmlContent += "  ������֤������: " + sBnfIDType3;
			xmlContent += "\t\t������֤������: " + sBnfIDNo3 + "\\n";
			xmlContent += "  ����������: " + sBnfBirthday3;
			xmlContent += "\t\t������� : " + sBnfBnfLot3 + "\\n";
			xmlContent += "  ����˳��: " + sBnfBnfGrade3;
			xmlContent += "\t\t�������뱻���˹�ϵ: " + sBnfRelationToInsured3
					+ "\\n";
		}

		xmlContent += "����������Ϣ\\n";

		xmlContent += "1��������Ϣ\\n";
		xmlContent += "  ���ִ���: " + sCode;
		xmlContent += "\t\t���մ���: " + sCode + "\\n";
		xmlContent += "  ����: " + sMult;
		xmlContent += "\t\t����: " + sPrem + "\\n";
		xmlContent += "  ����: " + sAmnt;
		xmlContent += "\t\t������������: " + sInsuYearFlag + "\\n";
		xmlContent += "  �������� : " + sInsuYear;
		xmlContent += "\t\t�ɷ���������: " + sPayEndYearFlag + "\\n";
		xmlContent += "  �ɷ�����: " + sPayEndYear;
		xmlContent += "\t\t��ȡ��������: " + sGetYearFlag + "\\n";
		xmlContent += "  ��ȡ����: " + sGetYear;
		xmlContent += "\t\t������ȡ��ʽ: " + sBonusGetMode + "\\n";
		xmlContent += "  �ɷѷ�ʽ: " + sPayIntv + "\\n";

		if (!(sCode1 == null || sCode1.equals(""))) {
			xmlContent += "2����������Ϣ1\\n";
			xmlContent += "  ���������ִ���: " + sCode1;
			xmlContent += "\t\t���մ���: " + sCode + "\\n";
			xmlContent += "  ����: " + sMult1;
			xmlContent += "\t\t����: " + sPrem1 + "\\n";
			xmlContent += "  ����: " + sAmnt1;
			xmlContent += "\t\t������������: " + sInsuYearFlag1 + "\\n";
			xmlContent += "  �������� : " + sInsuYear1;
			xmlContent += "\t\t�ɷ���������: " + sPayEndYearFlag1 + "\\n";
			xmlContent += "  �ɷ�����: " + sPayEndYear1;
			xmlContent += "\t\t��ȡ��������: " + sGetYearFlag1 + "\\n";
			xmlContent += "  ��ȡ����: " + sGetYear1;
			xmlContent += "\t\t������ȡ��ʽ: " + sBonusGetMode1 + "\\n";

		}

		//if(!(sAccNo1==null||sAccNo1.equals(""))){
		//xmlContent += "3��Ͷ���˻���Ϣ\\n";
		//xmlContent += "  Ͷ���˻�1: " + sAccNo1;
		//xmlContent += "\t\tͶ�ʱ���: " + sRate1 ;
		//xmlContent += "  Ͷ���˻�2: " + sAccNo2 ; 
		//xmlContent += "\t\tͶ�ʱ���: " + sRate2 + "\\n";  
		//xmlContent += "  Ͷ���˻�3: " + sAccNo3 ;
		//xmlContent += "\t\tͶ�ʱ���: " + sRate3 + "\\n";
		//} 

		System.out
				.println("************************װ��XML************************");

		ElementLis Req = new ElementLis("Req");

		ElementLis TransrNo = new ElementLis("TransrNo", sTransrNo, Req);
		ElementLis ZoneNo = new ElementLis("ZoneNo", sZoneNo, Req);
		ElementLis BrNo = new ElementLis("BrNo", sBrNo, Req);
		ElementLis BankDate = new ElementLis("BankDate",
				DateUtil.date10to8(sBankDate), Req);
		ElementLis BankTime = new ElementLis("BankTime", sBankTime, Req);
		ElementLis BankCode = new ElementLis("BankCode", "05", Req);
		ElementLis TellerNo = new ElementLis("TellerNo", sTellerNo, Req);
		ElementLis InsuID = new ElementLis("InsuID", "01", Req);
		ElementLis FunctionFlag = new ElementLis("FunctionFlag",
				sFunctionFlag, Req);

		ElementLis Base = new ElementLis("Base", Req);
		ElementLis ProposalContNo = new ElementLis("ProposalContNo",
				sProposalContNo, Base);
		ElementLis PrtNo = new ElementLis("PrtNo",
				sContPrtNo, Base);
				
				System.out.println("11111111111111111:"+sContPrtNo);
		ElementLis PolApplyDate = new ElementLis("PolApplyDate",
				DateUtil.date10to8(sPolApplyDate), Base);
		
		ElementLis AccName = new ElementLis("AccName", sAccName, Base);
		ElementLis AccNo = new ElementLis("AccNo", sAccNo, Base);
		ElementLis AccBankNAME = new ElementLis("AccBankNAME", Base);
		ElementLis BankAccNo = new ElementLis("BankAccNo", Base);
		ElementLis SpecContent = new ElementLis("SpecContent", Base);
		ElementLis Appl = new ElementLis("Appl", Req);
		ElementLis ApplName = new ElementLis("Name", sApplName, Appl);
		ElementLis ApplSex = new ElementLis("Sex", sApplSex, Appl);
		ElementLis ApplBirthday = new ElementLis("Birthday",
				DateUtil.date10to8(sApplBirthday), Appl);
		ElementLis ApplIDType = new ElementLis("IDType", sApplIDType,
				Appl);
		ElementLis ApplIDNo = new ElementLis("IDNo", sApplIDNo, Appl);
		ElementLis ApplAddress = new ElementLis("Address",
				sApplAddress, Appl);
		ElementLis ApplZipCode = new ElementLis("ZipCode",
				sApplZipCode, Appl);
		ElementLis ApplPhone = new ElementLis("Phone", sApplPhone, Appl);
		ElementLis ApplMobile = new ElementLis("Mobile", sApplMobile,
				Appl);
		ElementLis ApplEmail = new ElementLis("Email", sApplEmail, Appl);
		ElementLis ApplValidYear = new ElementLis("ValidYear",
				DateUtil.date10to8(sApplValidYear), Appl);
		ElementLis ApplRelaToInsured = new ElementLis("RelaToInsured",
				sApplRelaToInsured, Appl);
		ElementLis ApplJobCode = new ElementLis("JobCode",
				sApplJobCode, Appl);

		ElementLis Insu = new ElementLis("Insu", Req);
		ElementLis InsuName = new ElementLis("Name", sInsuName, Insu);
		ElementLis InsuSex = new ElementLis("Sex", sInsuSex, Insu);
		ElementLis InsuBirthday = new ElementLis("Birthday",
				DateUtil.date10to8(sInsuBirthday), Insu);
		ElementLis InsuIDType = new ElementLis("IDType", sInsuIDType,
				Insu);
		ElementLis InsuIDNo = new ElementLis("IDNo", sInsuIDNo, Insu);

		ElementLis InsuAddress = new ElementLis("Address",
				sInsuAddress, Insu);
		ElementLis InsuZipCode = new ElementLis("ZipCode",
				sInsuZipCode, Insu);
		ElementLis InsuPhone = new ElementLis("Phone", sInsuPhone, Insu);
		ElementLis InsuMobile = new ElementLis("Mobile", sInsuMobile,
				Insu);
		ElementLis InsuEmail = new ElementLis("Email", sInsuEmail, Insu);
		ElementLis InsuValidYear = new ElementLis("ValidYear",
				DateUtil.date10to8(sInsuValidYear), Insu);
		ElementLis InsuJobCode = new ElementLis("JobCode",
				sInsuJobCode, Insu);

		ElementLis Bnfs = new ElementLis("Bnfs", Req);
		ElementLis BnfsCount = new ElementLis("Count", "0", Bnfs);

		if (!(sBnfName1== null || sBnfName1.equals(""))) {
			BnfsCount.setText("1");
			
			ElementLis Bnf1 = new ElementLis("Bnf", Bnfs);
			ElementLis BnfName1 = new ElementLis("Name", sBnfName1,
					Bnf1);
			ElementLis BnfSex1 = new ElementLis("Sex", sBnfSex1, Bnf1);
			ElementLis BnfBirthday1 = new ElementLis("Birthday",
					DateUtil.date10to8(sBnfBirthday1), Bnf1);
			ElementLis BnfIDType1 = new ElementLis("IDType",
					sBnfIDType1, Bnf1);
			ElementLis BnfIDNo1 = new ElementLis("IDNo", sBnfIDNo1,
					Bnf1);
			ElementLis BnfType1 = new ElementLis("Type", sBnfType1,
					Bnf1);
			ElementLis BnfBnfLot1 = new ElementLis("BnfLot",
					sBnfBnfLot1, Bnf1);
			ElementLis BnfBnfGrade1 = new ElementLis("BnfGrade",
					sBnfBnfGrade1, Bnf1);
			ElementLis BnfRelationToInsured1 = new ElementLis(
					"RelationToInsured", sBnfRelationToInsured1, Bnf1);
			ElementLis BnfValidYear1 = new ElementLis(
					"ValidYear", DateUtil.date10to8(sBnfValidYear1), Bnf1);
			
			//����еڶ�������
			if (!(sBnfName2 == null || sBnfName2.equals(""))) {
				BnfsCount.setText("2");
			
				ElementLis Bnf2 = new ElementLis("Bnf", Bnfs);
				ElementLis BnfName2 = new ElementLis("Name", sBnfName2,
						Bnf2);
				ElementLis BnfSex2 = new ElementLis("Sex", sBnfSex2,
						Bnf2);
				ElementLis BnfBirthday2 = new ElementLis("Birthday",
						DateUtil.date10to8(sBnfBirthday2), Bnf2);
				ElementLis BnfIDType2 = new ElementLis("IDType",
						sBnfIDType2, Bnf2);
				ElementLis BnfIDNo2 = new ElementLis("IDNo", sBnfIDNo2,
						Bnf2);
				ElementLis BnfType2 = new ElementLis("Type", sBnfType2,
						Bnf2);
				ElementLis BnfBnfLot2 = new ElementLis("BnfLot",
						sBnfBnfLot2, Bnf2);
				ElementLis BnfBnfGrade2 = new ElementLis("BnfGrade",
						sBnfBnfGrade2, Bnf2);
				ElementLis BnfRelationToInsured2 = new ElementLis(
						"RelationToInsured", sBnfRelationToInsured2,
						Bnf2);
				ElementLis BnfValidYear2 = new ElementLis(
					"ValidYear", DateUtil.date10to8(sBnfValidYear2), Bnf2);
			}
			//����е��������� 	
			if (!(sBnfName3 == null || sBnfName3.equals(""))) {
				BnfsCount.setText("3");
				
				ElementLis Bnf3 = new ElementLis("Bnf", Bnfs);
				ElementLis BnfName3 = new ElementLis("Name", sBnfName3,
						Bnf3);
				ElementLis BnfSex3 = new ElementLis("Sex", sBnfSex3,
						Bnf3);
				ElementLis BnfBirthday3 = new ElementLis("Birthday",
						DateUtil.date10to8(sBnfBirthday3), Bnf3);
				ElementLis BnfIDType3 = new ElementLis("IDType",
						sBnfIDType3, Bnf3);
				ElementLis BnfIDNo3 = new ElementLis("IDNo", sBnfIDNo3,
						Bnf3);
				ElementLis BnfType3 = new ElementLis("Type", sBnfType3,
						Bnf3);
				ElementLis BnfBnfLot3 = new ElementLis("BnfLot",
						sBnfBnfLot3, Bnf3);
				ElementLis BnfBnfGrade3 = new ElementLis("BnfGrade",
						sBnfBnfGrade3, Bnf3);
				ElementLis BnfRelationToInsured3 = new ElementLis(
						"RelationToInsured", sBnfRelationToInsured3,
						Bnf3);
						
				ElementLis BnfValidYear3 = new ElementLis(
					"ValidYear", DateUtil.date10to8(sBnfValidYear3), Bnf3);
			}
		}

		ElementLis Risks = new ElementLis("Risks", Req);
		ElementLis Risk = new ElementLis("Risk", Risks);
		ElementLis PayIntv = new ElementLis("PayIntv", sPayIntv, Risk);
		ElementLis MainRiskCode = new ElementLis("MainRiskCode", sCode,
				Risk);
		ElementLis Code = new ElementLis("Code", sCode, Risk);
		ElementLis Mult = new ElementLis("Mult", sMult, Risk);
		ElementLis PayEndYearFlag = new ElementLis("PayEndYearFlag",
				sPayEndYearFlag, Risk);
		ElementLis PayEndYear = new ElementLis("PayEndYear",
				sPayEndYear, Risk);
		ElementLis InsuYearFlag = new ElementLis("InsuYearFlag",
				sInsuYearFlag, Risk);
		ElementLis InsuYear = new ElementLis("InsuYear", sInsuYear,
				Risk);
		ElementLis Prem = new ElementLis("Prem", sPrem, Risk);
		ElementLis Amnt = new ElementLis("Amnt", sAmnt, Risk);
		ElementLis GetYearFlag = new ElementLis("GetYearFlag",
				sGetYearFlag, Risk);
		ElementLis GetYear = new ElementLis("GetYear", sGetYear, Risk);
		ElementLis BonusGetMode = new ElementLis("BonusGetMode",
				sBonusGetMode, Risk);
		ElementLis HealthFlag = new ElementLis("HealthFlag",
				sHealthFlag, Risk);

		//����и�����1  
		if (!(sCode1 == null || sCode1.equals(""))) {
			ElementLis Risk1 = new ElementLis("Risk", Risks);
			ElementLis Code1 = new ElementLis("Code", sCode1, Risk1);
			ElementLis MainRiskCode1 = new ElementLis("MainRiskCode",
					sMainRiskCode1, Risk1);
			ElementLis Mult1 = new ElementLis("Mult", sMult1, Risk1);
			ElementLis Amnt1 = new ElementLis("Amnt", sAmnt1, Risk1);
			ElementLis Prem1 = new ElementLis("Prem", sPrem1, Risk1);
			ElementLis InsuYearFlag1 = new ElementLis("InsuYearFlag",
					sInsuYearFlag1, Risk1);
			ElementLis InsuYear1 = new ElementLis("InsuYear",
					sInsuYear1, Risk1);
			ElementLis PayEndYearFlag1 = new ElementLis(
					"PayEndYearFlag", sPayEndYearFlag1, Risk1);
			ElementLis PayEndYear1 = new ElementLis("PayEndYear",
					sPayEndYear1, Risk1);
			ElementLis GetYearFlag1 = new ElementLis("GetYearFlag",
					sGetYearFlag1, Risk1);
			ElementLis GetYear1 = new ElementLis("GetYear", sGetYear1,
					Risk1);
			ElementLis BoundGetMode1 = new ElementLis("BoundGetMode",
					sBonusGetMode1, Risk1);

		}
		//������Ϣ
		ElementLis Loan = new ElementLis("Loan", Req);
		ElementLis LoanNo = new ElementLis("LoanNo", sLoanNo, Loan);
		ElementLis LoanBank = new ElementLis("LoanBank", sLoanBank, Loan);
		ElementLis LoanDate = new ElementLis("LoanDate", DateUtil.date10to8(sLoanDate), Loan);
		ElementLis LoanEndDate = new ElementLis("LoanEndDate", DateUtil.date10to8(sLoanEndDate), Loan);
		ElementLis LoanType = new ElementLis("LoanType", sLoanType, Loan);
		ElementLis LoanAccNo = new ElementLis("AccNo", sLoanAccNo, Loan);
		ElementLis LoanPrem = new ElementLis("LoanPrem", sLoanPrem, Loan);
		ElementLis InsuDate = new ElementLis("InsuDate", DateUtil.date10to8(sInsuDate), Loan);
		ElementLis InsuEndDate = new ElementLis("InsuEndDate", DateUtil.date10to8(sInsuEndDate), Loan);
		
		//�˻���Ϣ	
		ElementLis Accts = new ElementLis("Accts", Req);
		ElementLis RiskCode = new ElementLis("RiskCode");
		ElementLis Count = new ElementLis("Count");
		RiskCode.setText("");
		Count.setText("0");
		Accts.addContent(RiskCode);
		Accts.addContent(Count);

		// System.out.println("************************���ñ���·��************************");	
		//	StringBuilder mFilePath = new StringBuilder(SysInfo.cHome);
		//	System.out.println("mFilePath1************************"+mFilePath+"************************");   
		//		int number =(mFilePath.length()-1);
		//		int num = (number-8);
		//		mFilePath.delete(num, number);
		//	System.out.println("mFilePath2************************"+mFilePath+"************************");  
		//		mFilePath.append("msg"); 
		//				 .append("1").append('/');
		//				 .append(DateUtil.getCurDate("yyyy/yyyyMM/yyyyMMdd/"));
		//		String mInFilePath = mFilePath.toString()+"/icbcTestIn.xml";		
		//		String mOutFilePath = mFilePath.toString()+"/icbcTestReturn.xml";

		//System.out.println("************************"+mInFilePath+"************************");   
		//System.out.println("************************"+mOutFilePath+"************************");  

		System.out
				.println("************************װ��Document************************");

		Document pXmlDoc = new Document(Req);

		int iPort = Integer.valueOf(sPort).intValue();
		byte[] mOutBytes = null;


			YD_ABCTestUI mTestUI = new YD_ABCTestUI(sIp, iPort);
			mOutBytes = mTestUI.sendRequestUI(sFlag, pXmlDoc);

		System.out
				.println("************************4************************");
		Document mOutXmlDoc = JdomUtil.build(mOutBytes);
		//	OutputStream mFos = new FileOutputStream(mOutFilePath);
		//	JdomUtil.output(mOutXmlDoc, mFos);

		//��ӡ�������� 
		//uiPrint = JdomUtil.toStringFmt(pXmlDoc);
		//System.out.println(uiPrint);		
		//	mFos.flush();
		//	mFos.close();

		System.out.println("*****************3");

		ResultFlag = mOutXmlDoc.getRootElement().getChild("RetData")
				.getChildTextTrim("Flag");
		ResultMesg = mOutXmlDoc.getRootElement().getChild("RetData")
				.getChildTextTrim("Mesg");

		System.out.println(ResultFlag + "  " + ResultMesg);

		//	System.out.println("strNewTransNo = " + strNewTransNo);
		System.out.println("�������ݣ�" + ResultMesg);
		if (ResultFlag.equals("0")) { // fail
			Content = "����ʧ�ܣ�" + ResultMesg;
			strNewTransNo = PubFun1.CreateMaxNo("TransNo", 16);
			//jsp��String�ϲ��ܳ���"%",��������ʾ����
			ResultMesg = ResultMesg.replace("%", "");

		} else {

			System.out.println("��������2��" + ResultMesg);
			strNewTransNo = sTransrNo;
			
			Content = ResultMesg;
			
			System.out.println("-----------��ʼȡ����saveҳ�棩----------");

		}

	} catch (Exception e) {
		e.printStackTrace();
		ResultFlag = "Fail";
		xmlContent = e.getMessage();
		Content = e.getMessage();
	}
%>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=ResultFlag%>", "<%=Content%>");
parent.fraInterface.fm.TransrNo.value = '<%=strNewTransNo%>';

parent.fraInterface.fm.xmlContent.value = '<%=xmlContent.toString()%>';
//parent.fraInterface.fm.ContNo.value = 'strContNo';
</script>