<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%> 
<%@page import="java.io.*"%>
<%@page import="java.lang.*"%>
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
<%@page import="com.sinosoft.midplat.newccb.NewCcbConf"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="test.NewCCBTestUI"%> 
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
	//��������
	String sContNo = request.getParameter("ContNo");
	//������Ա����
	String sBO_Sale_Stff_ID=request.getParameter("BkRckrNo");
	//����������
	String sBO_Sale_Stff_Nm=request.getParameter("BkSaleName");
	//������Ա�ʸ�֤��
	String sSale_Stff_AICSQCtf_ID=request.getParameter("BkSaleCertNo");
	//�ͻ����ճ�����������
	String sCst_Rsk_Tlrnc_Cpy_Cd=request.getParameter("Cst_Rsk_Tlrnc_Cpy_Cd");
	//���ղ�����Ч��
	String sRsk_Evlt_AvlDt=request.getParameter("Rsk_Evlt_AvlDt");
	//Ԥ����
	String sBdgt_Amt=request.getParameter("Bdgt_Amt");
	
	if(!sHOAppFormNumber.equals("")){
		//sHOAppFormNumber=CheckProlNo.retCheck(sHOAppFormNumber);
		}
	//����ӡˢ��
	String sProviderFormNumber="";
	//if(sFlag.equals("P53819113")){
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
	//Ͷ����֤����Ч����
	String sAppGovtEfcDate = request.getParameter("AppGovtEfcDate");
	sAppGovtEfcDate=DateUtil.date10to8(sAppGovtEfcDate);
	//Ͷ����֤��ʧЧ����
	String sAppGovtTermDate = request.getParameter("AppGovtTermDate");
	sAppGovtTermDate=DateUtil.date10to8(sAppGovtTermDate);
	//Ͷ���˵�������
	String sAppAddrLine = request.getParameter("AppAddrLine");
	//Ͷ����ͨѶ��ַ	
	String sAppLine1 = request.getParameter("AppLine1");
	//Ͷ������������
	String sAppZip = request.getParameter("AppZip");
	
	//Ͷ���˵�λ��ַ	
	String sAppWorkAddress = request.getParameter("AppWorkAddress");
	//Ͷ���˵�λ��������
	String sAppWorkZipCode = request.getParameter("AppWorkZipCode"); 
	 //Ͷ���˵�λ�绰 WorkPhone
	String sWorkPhone = request.getParameter("WorkPhone");
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
	//����������
	String sRspbPsn_Nm = request.getParameter("RspbName");
	//Ͷ�����뱻���˵Ĺ�ϵ
	String sAppToInsRelation	 = request.getParameter("AppToInsRelation");
	String sAppCountry = request.getParameter("AppCountry");
	String sApplJobCode = request.getParameter("ApplJobCode");
	
	String mPlchd_Prov_Cd = request.getParameter("Plchd_Prov_Cd");
	String mPlchd_City_Cd = request.getParameter("Plchd_City_Cd");
	String mPlchd_CntyAndDstc_Cd = request.getParameter("Plchd_CntyAndDstc_Cd");
	String mPlchd_Dtl_Adr_Cntnt = request.getParameter("Plchd_Dtl_Adr_Cntnt");
	
	//����������
	String sInsFullName = request.getParameter("InsFullName");
	//����������ƴ��ȫ��
	String sRcgn_CPA_FullNm = request.getParameter("Rcgn_CPA_FullNm");
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
	//������֤����Ч����
	String sInsGovtEfcDate = request.getParameter("InsGovtEfcDate");
	sInsGovtEfcDate=DateUtil.date10to8(sInsGovtEfcDate);
	//������֤��ʧЧ����
	String sInsGovtExpDate = request.getParameter("InsGovtExpDate");
	sInsGovtExpDate=DateUtil.date10to8(sInsGovtExpDate);
	//�����˵�������
	String sInsAddrLine = request.getParameter("InsAddrLine"); 
	//������ͨѶ��ַ	 
	String sInsLine1 = request.getParameter("InsLine1");
	//��������������
	String sInsZip = request.getParameter("InsZip"); 
	//�����˹̶��绰
	String sInsDialNumber1 = request.getParameter("InsDialNumber1");
	//�������ƶ��绰
	String sInsDialNumber3 = request.getParameter("InsDialNumber3");
	String sInsCountry = request.getParameter("InsCountry");
	//������֪��־ 
	String sHealthIndicator	 = request.getParameter("HealthIndicator");
	//δ�������ۼ���ʷ��ձ���
	String sInsCovSumAmt = request.getParameter("InsCovSumAmt");
	//������ְҵ����
	String sInsuJobCode = request.getParameter("InsuJobCode");
	//������������
	String sInsInCome = request.getParameter("InsInCome");
	//������ǰ��Ŀ�ĵظ���
	String sDesNum = request.getParameter("DesNum");
	//������ǰ��Ŀ�ĵ�
	String sDestinations = request.getParameter("Destinations");
	
	String mRcgn_Prov_Cd = request.getParameter("Rcgn_Prov_Cd");
	String mRcgn_City_Cd = request.getParameter("Rcgn_City_Cd");
	String mRcgn_CntyAndDstc_Cd = request.getParameter("Rcgn_CntyAndDstc_Cd");
	String mRcgn_Dtl_Adr_Cntnt = request.getParameter("Rcgn_Dtl_Adr_Cntnt");
	
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
	//������֤����Ч����
	String sApplbegindate1= request.getParameter("applbegindate1");
	sApplbegindate1=DateUtil.date10to8(sApplbegindate1);
	//������֤��ʧЧ����
	String sApplvaliddate1 = request.getParameter("applvaliddate1");
	sApplvaliddate1=DateUtil.date10to8(sApplvaliddate1);
	//�����˵�ַ
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
	//�����˵�ַ
	String sBnfAdress2 = request.getParameter("BnfAdress2");
	//������֤����Ч����
	String sApplbegindate2 = request.getParameter("applbegindate2");
	sApplbegindate2=DateUtil.date10to8(sApplbegindate2);
	//������֤��ʧЧ����
	String sApplvaliddate2 = request.getParameter("applvaliddate2");
	sApplvaliddate2=DateUtil.date10to8(sApplvaliddate2);
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
	//�����˵�ַ
	String sBnfAdress3 = request.getParameter("BnfAdress3");
	//������֤����Ч����
	String sApplbegindate3= request.getParameter("applbegindate3");
	sApplbegindate3=DateUtil.date10to8(sApplbegindate3);
	//������֤��ʧЧ����
	String sApplvaliddate3 = request.getParameter("applvaliddate3");
	sApplvaliddate3=DateUtil.date10to8(sApplvaliddate3);
	//����ٷ���
	String sInterestPercent3 = request.getParameter("InterestPercent3"); 
	//����˳ ��
	String sSequence3 = request.getParameter("Sequence3");
	//�������뱻���˵Ĺ�ϵ
	String sBnfToInsRelation3 = request.getParameter("BnfToInsRelation3");
	 //����������
    String sBenefiType3 = request.getParameter("BenefiType3");

	//�������ײͱ�� 
	String sAgIns_Pkg_ID = request.getParameter("AgIns_Pkg_ID");
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
	//Ͷ��������Ϣ
	String sIns_Scm_Inf = request.getParameter("Ins_Scm_Inf");
	//��ѡ������ʱ��ս��
	String sOpt_Part_DieIns_Amt = request.getParameter("Opt_Part_DieIns_Amt");
	//�״ζ���׷�ӱ���
	String sFTm_Extr_Adl_InsPrem = request.getParameter("FTm_Extr_Adl_InsPrem");
	//������ϵ���뱻���˹�ϵ
	String sEmgrCtcPsnAndRcReTpCd = request.getParameter("EmgrCtcPsnAndRcReTpCd");
	//������ϵ��
	String sEmgr_CtcPsn = request.getParameter("Emgr_CtcPsn");
	//�״���ϵ�绰
	String sEmgr_Ctc_Tel = request.getParameter("Emgr_Ctc_Tel");
	
	
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
		if(sFTm_Extr_Adl_InsPrem == null || "".equals(sFTm_Extr_Adl_InsPrem)){
			sFTm_Extr_Adl_InsPrem="0";
		}
		if(sProductCode1.equals("102") || sProductCode2.equals("101")){
		iPaymentAmt = Integer.valueOf(sModalPremAmt).intValue()+Integer.valueOf(sFTm_Extr_Adl_InsPrem).intValue()+Integer.valueOf(sModalPremAmt1).intValue() +Integer.valueOf(sModalPremAmt2).intValue();
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
		xmlContent +="\t ������Ա����+"+sBO_Sale_Stff_ID+"\\n";
		xmlContent +="\t ������Ա����+"+sBO_Sale_Stff_Nm+"\\n";
		xmlContent +="\t ������Ա�ʸ�֤��+"+sSale_Stff_AICSQCtf_ID+"\\n";
		
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
		xmlContent += "  ����������: " + sRspbPsn_Nm+ "\\n";	
        xmlContent += "  Ͷ���˾�������: " + sPbDenType+ "\\n";	
        xmlContent += "  Ͷ����ְҵ����: " + sApplJobCode+ "\\n";	
		
		xmlContent += "������������Ϣ\\n";
		xmlContent += "  ����������: " + sInsFullName;
		xmlContent += "\t\t\t�������Ա�: " + sInsGender + "\\n";
		xmlContent += "  ������֤������: " + sInsGovtIDTC ;
		xmlContent += "\t\t������֤������: " + sInsGovtID + "\\n";
		xmlContent += "  ����������: " + sInsBirthDate ;
		xmlContent += "\t\t�����˵������� : " + sInsAddrLine + "\\n";		
		xmlContent += "  �����˼�ͥ�绰: " + sInsDialNumber1;
		xmlContent += "\t�������ƶ��绰: " + sInsDialNumber3+ "\\n";
		xmlContent += "\t������ְҵ����: " + sInsuJobCode+ "\\n";
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
		xmlContent += "\t�״ζ���׷�ӱ���: " + sFTm_Extr_Adl_InsPrem + "\\n";
	
String InsuId = NewCcbConf.newInstance().getConf().getRootElement().getChild("bank").getAttributeValue("insu");
		
 System.out.println("************************װ��XML************************");		
 //���ڵ�
 ElementLis TX = new ElementLis("TX");	 
 ElementLis TX_HEADER = new ElementLis("TX_HEADER",TX); 
 
 ElementLis SYS_HDR_LEN = new ElementLis("SYS_HDR_LEN","0",TX_HEADER);  
 ElementLis SYS_PKG_VRSN = new ElementLis("SYS_PKG_VRSN","01",TX_HEADER);  
 ElementLis SYS_TTL_LEN = new ElementLis("SYS_TTL_LEN","0",TX_HEADER);  
 ElementLis SYS_REQ_SEC_ID = new ElementLis("SYS_REQ_SEC_ID","420020",TX_HEADER);  
 ElementLis SYS_SND_SEC_ID = new ElementLis("SYS_SND_SEC_ID","108011",TX_HEADER);  
 ElementLis SYS_TX_CODE = new ElementLis("SYS_TX_CODE",TX_HEADER);  
 ElementLis SYS_TX_VRSN = new ElementLis("SYS_TX_VRSN","01",TX_HEADER);  
 ElementLis SYS_TX_TYPE = new ElementLis("SYS_TX_TYPE","020000",TX_HEADER);  
 ElementLis SYS_RESERVED = new ElementLis("SYS_RESERVED","0",TX_HEADER);  
 ElementLis SYS_EVT_TRACE_ID = new ElementLis("SYS_EVT_TRACE_ID",TX_HEADER);  
 ElementLis SYS_SND_SERIAL_NO = new ElementLis("SYS_SND_SERIAL_NO","",TX_HEADER);  
 ElementLis SYS_PKG_TYPE = new ElementLis("SYS_PKG_TYPE","1",TX_HEADER);  
 ElementLis SYS_MSG_LEN = new ElementLis("SYS_MSG_LEN","",TX_HEADER);  
 ElementLis SYS_IS_ENCRYPTED = new ElementLis("SYS_IS_ENCRYPTED","3",TX_HEADER);  
 ElementLis SYS_ENCRYPT_TYPE = new ElementLis("SYS_ENCRYPT_TYPE","3",TX_HEADER);  
 ElementLis SYS_COMPRESS_TYPE = new ElementLis("SYS_COMPRESS_TYPE","0",TX_HEADER);  
 ElementLis SYS_EMB_MSG_LEN = new ElementLis("SYS_EMB_MSG_LEN","0",TX_HEADER);  
 SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS");
 Date date=new Date();
 ElementLis SYS_REQ_TIME = new ElementLis("SYS_REQ_TIME",sdf.format(date),TX_HEADER);  
 ElementLis SYS_TIME_LEFT = new ElementLis("SYS_TIME_LEFT",TX_HEADER);  
 ElementLis SYS_PKG_STS_TYPE = new ElementLis("SYS_PKG_STS_TYPE","00",TX_HEADER);  
 
 ElementLis TX_BODY = new ElementLis("TX_BODY",TX);  
 
 ElementLis COMMON = new ElementLis("COMMON",TX_BODY);  
 ElementLis FILE_LIST_PACK = new ElementLis("FILE_LIST_PACK",COMMON);  
 ElementLis FILE_NUM = new ElementLis("FILE_NUM","0",FILE_LIST_PACK);  
 ElementLis FILE_MODE = new ElementLis("FILE_MODE","0",FILE_LIST_PACK);  
 ElementLis FILE_NODE = new ElementLis("FILE_NODE",FILE_LIST_PACK);  
 ElementLis FILE_NAME_PACK = new ElementLis("FILE_NAME_PACK",FILE_LIST_PACK);  
 ElementLis FILE_PATH_PACK = new ElementLis("FILE_PATH_PACK",FILE_LIST_PACK);  
 
 ElementLis ENTITY = new ElementLis("ENTITY",TX_BODY);  
 
 ElementLis COM_ENTITY = new ElementLis("COM_ENTITY",ENTITY);  
 ElementLis Inst_Eng_ShrtNm = new ElementLis("Inst_Eng_ShrtNm","CCB",COM_ENTITY);  
 ElementLis Ins_Co_ID = new ElementLis("Ins_Co_ID",InsuId,COM_ENTITY);  
 ElementLis SvPt_Jrnl_No = new ElementLis("SvPt_Jrnl_No",sTransRefGUID,COM_ENTITY);  
 ElementLis TXN_ITT_CHNL_ID = new ElementLis("TXN_ITT_CHNL_ID","",COM_ENTITY);  
 ElementLis TXN_ITT_CHNL_CGY_CODE = new ElementLis("TXN_ITT_CHNL_CGY_CODE","20170029",COM_ENTITY);  
 ElementLis CCBIns_ID = new ElementLis("CCBIns_ID","330613535",COM_ENTITY);  
// ElementLis CCBIns_ID = new ElementLis("CCBIns_ID","0020000019",COM_ENTITY);  
 ElementLis CCB_EmpID = new ElementLis("CCB_EmpID","330613535351",COM_ENTITY);  
//  ElementLis CCB_EmpID = new ElementLis("CCB_EmpID","00200000191",COM_ENTITY);  
 ElementLis OprgDay_Prd = new ElementLis("OprgDay_Prd",sTransExeDate,COM_ENTITY);  
 ElementLis LNG_ID = new ElementLis("LNG_ID","zh-cn",COM_ENTITY);  
 
 ElementLis APP_ENTITY = new ElementLis("APP_ENTITY",ENTITY);  
 
 //ElementLis LiBankID = new ElementLis("LiBankID","CCB",TX_HEADER);  
//���չ�˾����
 //ElementLis PbInsuId = new ElementLis("PbInsuId",InsuId,TX_HEADER);  
//������ˮ��
 //ElementLis BkPlatSeqNo = new ElementLis("BkPlatSeqNo",sTransRefGUID,TX_HEADER);  
// ElementLis BkTxCode = new ElementLis("BkTxCode",sFlag,TX_HEADER);  
 //������������
 //ElementLis BkChnlNo = new ElementLis("BkChnlNo","1",TX_HEADER);  
 //ԭʼ���󷽻�������
 //ElementLis BkBrchNo = new ElementLis("BkBrchNo",sRegionCode+sBranch,TX_HEADER);  
//���׹�Ա����
// ElementLis BkTellerNo = new ElementLis("BkTellerNo",sTeller,TX_HEADER);  
//���н�������
 //ElementLis BkPlatDate = new ElementLis("BkPlatDate",sTransExeDate,TX_HEADER);  
//���н���ʱ��
// ElementLis BkPlatTime = new ElementLis("BkPlatTime",sTransExeTime,TX_HEADER);  
 if(sFlag.equals("P53819113")||sFlag.equals("P53819188")){
	 if(sFlag.equals("P53819113")){
		 SYS_TX_CODE.setText("P53819113");
	 }
	 if(sFlag.equals("P53819188")){
		 SYS_TX_CODE.setText("P53819188");
	 }
//Ͷ�������� 
ElementLis Plchd_Nm = new ElementLis("Plchd_Nm",sAppFullName,APP_ENTITY);  
//Ͷ�����Ա� 
ElementLis Plchd_Gnd_Cd = new ElementLis("Plchd_Gnd_Cd",sAppGender,APP_ENTITY);	
//Ͷ���˳�������
ElementLis Plchd_Brth_Dt = new ElementLis("Plchd_Brth_Dt",sAppBirthDate,APP_ENTITY);
//Ͷ����֤������
ElementLis  Plchd_Crdt_TpCd = new ElementLis("Plchd_Crdt_TpCd",sAppGovtIDTC,APP_ENTITY);
//Ͷ����֤������ 
ElementLis Plchd_Crdt_No = new ElementLis("Plchd_Crdt_No",sAppGovtID,APP_ENTITY);
//Ͷ����֤����Ч����
ElementLis Plchd_Crdt_EfDt = new ElementLis("Plchd_Crdt_EfDt",sAppGovtEfcDate,APP_ENTITY);
//Ͷ����֤��ʧЧ���� 
ElementLis Plchd_Crdt_ExpDt = new ElementLis("Plchd_Crdt_ExpDt",sAppGovtTermDate,APP_ENTITY);
//Ͷ����ͨѶ��ַ
ElementLis Plchd_Comm_Adr = new ElementLis("Plchd_Comm_Adr",sAppLine1,APP_ENTITY);

//Ͷ���˵�λ��ַ
// ElementLis PbHoldAddr = new ElementLis("PbHoldAddr",sAppWorkAddress,APP_ENTITY);
//Ͷ���˵�λ�ʱ�
// ElementLis PbHoldPost = new ElementLis("PbHoldPost",sAppWorkZipCode,APP_ENTITY);
//Ͷ�����ʱ�
ElementLis Plchd_ZipECD = new ElementLis("Plchd_ZipECD",sAppZip,APP_ENTITY);
//Ͷ����סլ�绰 (Ͷ���˹̶��绰)
ElementLis Plchd_Fix_TelNo = new ElementLis("Plchd_Fix_TelNo",sAppDialNumber1,APP_ENTITY);
//Ͷ���˵�λ�绰
// ElementLis PbHoldOfficTele = new ElementLis("PbHoldOfficTele",sWorkPhone,APP_ENTITY);
//Ͷ�����ƶ��绰
ElementLis Plchd_Move_TelNo = new ElementLis("Plchd_Move_TelNo",sAppDialNumber3,APP_ENTITY);
//Ͷ��������
ElementLis Plchd_Email_Adr = new ElementLis("Plchd_Email_Adr",sAppAddrLine,APP_ENTITY);
//Ͷ����ְҵ���
ElementLis Plchd_Ocp_Cd = new ElementLis("Plchd_Ocp_Cd",sApplJobCode,APP_ENTITY);
//����
ElementLis Plchd_Nat_Cd = new ElementLis("Plchd_Nat_Cd",sAppCountry,APP_ENTITY);
//ְҵ����
//ElementLis ApplJobCode = new ElementLis("PbHoldOccupCode",sApplJobCode,APP_ENTITY);
		xmlContent +="\t ������Ա����+"+sBO_Sale_Stff_ID+"\\n";
		xmlContent +="\t ������Ա����+"+sBO_Sale_Stff_Nm+"\\n";
		xmlContent +="\t ������Ա�ʸ�֤��+"+sSale_Stff_AICSQCtf_ID+"\\n";
		
//Ͷ����֤�������� 
//ElementLis PbIdEndDate = new ElementLis("PbIdEndDate",sAppGovtTermDate,APP_ENTITY);
//������Ա����
//ElementLis BO_Sale_Stff_Nm = new ElementLis("BO_Sale_Stff_Nm",sBO_Sale_Stff_Nm,APP_ENTITY);
//������Ա����
//ElementLis BO_Sale_Stff_ID = new ElementLis("BO_Sale_Stff_Nm",sBO_Sale_Stff_ID,APP_ENTITY);
//������Ա�ʸ�֤���� 
//ElementLis Sale_Stff_AICSQCtf_ID = new ElementLis("Sale_Stff_AICSQCtf_ID",sSale_Stff_AICSQCtf_ID,APP_ENTITY);
//Ͷ����������
ElementLis Plchd_Yr_IncmAm = new ElementLis("Plchd_Yr_IncmAm",sPbInCome,APP_ENTITY);
//Ͷ���˼�ͥ������
ElementLis Fam_Yr_IncmAm = new ElementLis("Fam_Yr_IncmAm",sPbHomeInCome,APP_ENTITY);
//Ͷ���˾�������
ElementLis Rsdnt_TpCd = new ElementLis("Rsdnt_TpCd",sPbDenType,APP_ENTITY);
//Ͷ���˻���״��
//ElementLis AppMarStat = new ElementLis("AppMarStat","",APP_ENTITY);
//����������
ElementLis RspbPsn_Nm = new ElementLis("RspbPsn_Nm",sRspbPsn_Nm,APP_ENTITY);
ElementLis  cmPlchd_Prov_Cd= new ElementLis("Plchd_Prov_Cd",mPlchd_Prov_Cd,APP_ENTITY);
ElementLis  cmPlchd_City_Cd= new ElementLis("Plchd_City_Cd",mPlchd_City_Cd,APP_ENTITY);
ElementLis  cmPlchd_CntyAndDstc_Cd= new ElementLis("Plchd_CntyAndDstc_Cd",mPlchd_CntyAndDstc_Cd,APP_ENTITY);
ElementLis  cmPlchd_Dtl_Adr_Cntnt= new ElementLis("Plchd_Dtl_Adr_Cntnt",mPlchd_Dtl_Adr_Cntnt,APP_ENTITY);

//Ͷ�����뱻���˹�ϵ
ElementLis Plchd_And_Rcgn_ReTpCd = new ElementLis("Plchd_And_Rcgn_ReTpCd",sAppToInsRelation,APP_ENTITY);

//��������Ϣ
ElementLis Rcgn_Nm = new ElementLis("Rcgn_Nm",sInsFullName,APP_ENTITY);
ElementLis Rcgn_CPA_FullNm = new ElementLis("Rcgn_CPA_FullNm",sRcgn_CPA_FullNm,APP_ENTITY);
ElementLis Rcgn_Gnd_Cd = new ElementLis("Rcgn_Gnd_Cd",sInsGender,APP_ENTITY);
ElementLis Rcgn_Brth_Dt = new ElementLis("Rcgn_Brth_Dt",sInsBirthDate,APP_ENTITY); 
ElementLis Rcgn_Crdt_TpCd = new ElementLis("Rcgn_Crdt_TpCd",sInsGovtIDTC,APP_ENTITY);
ElementLis Rcgn_Crdt_No = new ElementLis("Rcgn_Crdt_No",sInsGovtID,APP_ENTITY);
//������֤����Ч����
ElementLis Rcgn_Crdt_EfDt = new ElementLis("Rcgn_Crdt_EfDt",sInsGovtEfcDate,APP_ENTITY);
//������֤��ʧЧ���� 
ElementLis Rcgn_Crdt_ExpDt = new ElementLis("Rcgn_Crdt_ExpDt",sInsGovtExpDate,APP_ENTITY);
//����������
ElementLis Rcgn_Email_Adr = new ElementLis("Rcgn_Email_Adr",sInsAddrLine,APP_ENTITY);
//�����˵�ַ
ElementLis Rcgn_Comm_Adr = new ElementLis("Rcgn_Comm_Adr",sInsLine1,APP_ENTITY);
ElementLis Rcgn_ZipECD = new ElementLis("Rcgn_ZipECD",sInsZip,APP_ENTITY);
//ElementLis InsCompany = new ElementLis("InsCompany","",APP_ENTITY);
//ElementLis InsCall = new ElementLis("InsCall","",APP_ENTITY);
ElementLis Rcgn_Ocp_Cd = new ElementLis("Rcgn_Ocp_Cd",sInsuJobCode,APP_ENTITY);

ElementLis Rcgn_Fix_TelNo = new ElementLis("Rcgn_Fix_TelNo",sInsDialNumber1,APP_ENTITY);
ElementLis Rcgn_Move_TelNo = new ElementLis("Rcgn_Move_TelNo",sInsDialNumber3,APP_ENTITY);
//������������
ElementLis Rcgn_Yr_IncmAm = new ElementLis("Rcgn_Yr_IncmAm",sInsInCome,APP_ENTITY);
//δ�������ۼ���ʷ��ձ���	
ElementLis Minr_Acm_Cvr = new ElementLis("Minr_Acm_Cvr",sInsCovSumAmt,APP_ENTITY);
// ElementLis LiIdEndDate = new ElementLis("LiIdEndDate",sInsGovtTermDate,APP_ENTITY);
//ְҵ����
// ElementLis InsuJobCode = new ElementLis("LiRcgnOccupCode",sInsuJobCode,APP_ENTITY);

ElementLis Rcgn_Nat_Cd = new ElementLis("Rcgn_Nat_Cd",sInsCountry,APP_ENTITY);
ElementLis Rcgn_LvFr_Pps_Lnd_Num = new ElementLis("Rcgn_LvFr_Pps_Lnd_Num",sDesNum,APP_ENTITY);
ElementLis Pps_List = new ElementLis("Pps_List",APP_ENTITY);
for(int i=0;i<Integer.parseInt(sDesNum);i++){
ElementLis Pps_Detail = new ElementLis("Pps_Detail",Pps_List);
ElementLis Rcgn_LvFr_Pps_Lnd = new ElementLis("Rcgn_LvFr_Pps_Lnd",sDestinations,Pps_Detail);
		ElementLis  cmRcgn_Prov_Cd= new ElementLis("Rcgn_Prov_Cd",mPlchd_Prov_Cd,APP_ENTITY);
 		ElementLis  cmRcgn_City_Cd= new ElementLis("Rcgn_City_Cd",mPlchd_City_Cd,APP_ENTITY);
 		ElementLis  cmRcgn_CntyAndDstc_Cd= new ElementLis("Rcgn_CntyAndDstc_Cd",mPlchd_CntyAndDstc_Cd,APP_ENTITY);
 		ElementLis  cmRcgn_Dtl_Adr_Cntnt= new ElementLis("Rcgn_Dtl_Adr_Cntnt",mPlchd_Dtl_Adr_Cntnt,APP_ENTITY);
 		
}

//�����˻���״��
//ElementLis InsMarStat = new ElementLis("InsMarStat","",APP_ENTITY);

//��������Ϣ����
ElementLis Benf_Num = new ElementLis("Benf_Num","",APP_ENTITY);
ElementLis Benf_List = new ElementLis("Benf_List",APP_ENTITY);
int BenefitNum=0;
//��һ������
if(!(sBnfFullName1.equals(""))){
ElementLis Benf_Detail = new ElementLis("Benf_Detail",Benf_List);
ElementLis AgIns_Benf_TpCd = new ElementLis("AgIns_Benf_TpCd",sBenefiType1,Benf_Detail);
ElementLis Benf_SN = new ElementLis("Benf_SN","",Benf_Detail);
ElementLis Benf_Bnft_Seq = new ElementLis("Benf_Bnft_Seq",sSequence1,Benf_Detail);
ElementLis Benf_Nm = new ElementLis("Benf_Nm",sBnfFullName1,Benf_Detail);
ElementLis Benf_Gnd_Cd = new ElementLis("Benf_Gnd_Cd",sBnfGender1,Benf_Detail);
ElementLis Benf_Brth_Dt = new ElementLis("Benf_Brth_Dt",sBnfBirthDate1,Benf_Detail); 
ElementLis Benf_Crdt_TpCd = new ElementLis("Benf_Crdt_TpCd",sBnfGovtIDTC1,Benf_Detail);
ElementLis Benf_Crdt_No = new ElementLis("Benf_Crdt_No",sBnfGovtID1,Benf_Detail);
ElementLis Benf_Crdt_EfDt = new ElementLis("Benf_Crdt_EfDt",sApplbegindate1,Benf_Detail);
ElementLis Benf_Crdt_ExpDt = new ElementLis("Benf_Crdt_ExpDt",sApplvaliddate1,Benf_Detail);
ElementLis Benf_Nat_Cd = new ElementLis("Benf_Nat_Cd","0156",Benf_Detail);
ElementLis Benf_And_Rcgn_ReTpCd = new ElementLis("Benf_And_Rcgn_ReTpCd",sBnfToInsRelation1,Benf_Detail);
ElementLis Bnft_Pct = new ElementLis("Bnft_Pct",sInterestPercent1,Benf_Detail);
ElementLis Benf_Comm_Adr = new ElementLis("Benf_Comm_Adr",sBnfAdress1,Benf_Detail);
BenefitNum++;
}
//����еڶ������� 									
if(!(sBnfFullName2==null||sBnfFullName2.equals(""))){
	ElementLis Benf_Detail = new ElementLis("Benf_Detail",Benf_List);
	ElementLis AgIns_Benf_TpCd = new ElementLis("AgIns_Benf_TpCd",sBenefiType2,Benf_Detail);
	ElementLis Benf_SN = new ElementLis("Benf_SN","",Benf_Detail);
	ElementLis Benf_Bnft_Seq = new ElementLis("Benf_Bnft_Seq",sSequence2,Benf_Detail);
	ElementLis Benf_Nm = new ElementLis("Benf_Nm",sBnfFullName2,Benf_Detail);
	ElementLis Benf_Gnd_Cd = new ElementLis("Benf_Gnd_Cd",sBnfGender2,Benf_Detail);
	ElementLis Benf_Brth_Dt = new ElementLis("Benf_Brth_Dt",sBnfBirthDate2,Benf_Detail); 
	ElementLis Benf_Crdt_TpCd = new ElementLis("Benf_Crdt_TpCd",sBnfGovtIDTC2,Benf_Detail);
	ElementLis Benf_Crdt_No = new ElementLis("Benf_Crdt_No",sBnfGovtID2,Benf_Detail);
	ElementLis Benf_Crdt_EfDt = new ElementLis("Benf_Crdt_EfDt",sApplbegindate2,Benf_Detail);
	ElementLis Benf_Crdt_ExpDt = new ElementLis("Benf_Crdt_ExpDt",sApplvaliddate2,Benf_Detail);
	ElementLis Benf_Nat_Cd = new ElementLis("Benf_Nat_Cd","0156",Benf_Detail);
	ElementLis Benf_And_Rcgn_ReTpCd = new ElementLis("Benf_And_Rcgn_ReTpCd",sBnfToInsRelation2,Benf_Detail);
	ElementLis Bnft_Pct = new ElementLis("Bnft_Pct",sInterestPercent2,Benf_Detail);
	ElementLis Benf_Comm_Adr = new ElementLis("Benf_Comm_Adr",sBnfAdress2,Benf_Detail);
	BenefitNum++;
}
//����е��������� 									 
if(!(sBnfFullName3==null||sBnfFullName3.equals(""))){
	ElementLis Benf_Detail = new ElementLis("Benf_Detail",Benf_List);
	ElementLis AgIns_Benf_TpCd = new ElementLis("AgIns_Benf_TpCd",sBenefiType3,Benf_Detail);
	ElementLis Benf_SN = new ElementLis("Benf_SN","",Benf_Detail);
	ElementLis Benf_Bnft_Seq = new ElementLis("Benf_Bnft_Seq",sSequence3,Benf_Detail);
	ElementLis Benf_Nm = new ElementLis("Benf_Nm",sBnfFullName3,Benf_Detail);
	ElementLis Benf_Gnd_Cd = new ElementLis("Benf_Gnd_Cd",sBnfGender3,Benf_Detail);
	ElementLis Benf_Brth_Dt = new ElementLis("Benf_Brth_Dt",sBnfBirthDate3,Benf_Detail); 
	ElementLis Benf_Crdt_TpCd = new ElementLis("Benf_Crdt_TpCd",sBnfGovtIDTC3,Benf_Detail);
	ElementLis Benf_Crdt_No = new ElementLis("Benf_Crdt_No",sBnfGovtID3,Benf_Detail);
	ElementLis Benf_Crdt_EfDt = new ElementLis("Benf_Crdt_EfDt",sApplbegindate3,Benf_Detail);
	ElementLis Benf_Crdt_ExpDt = new ElementLis("Benf_Crdt_ExpDt",sApplvaliddate3,Benf_Detail);
	ElementLis Benf_Nat_Cd = new ElementLis("Benf_Nat_Cd","0156",Benf_Detail);
	ElementLis Benf_And_Rcgn_ReTpCd = new ElementLis("Benf_And_Rcgn_ReTpCd",sBnfToInsRelation3,Benf_Detail);
	ElementLis Bnft_Pct = new ElementLis("Bnft_Pct",sInterestPercent3,Benf_Detail);
	ElementLis Benf_Comm_Adr = new ElementLis("Benf_Comm_Adr",sBnfAdress3,Benf_Detail);
	BenefitNum++;
}
    String BenitNum=""+BenefitNum;
    Benf_Num.setText(BenitNum);//�޸������˸�����
    
	ElementLis Busi_List = new ElementLis("Busi_List",APP_ENTITY); 
	ElementLis Cvr_Num = new ElementLis("Cvr_Num","1",APP_ENTITY); 
	ElementLis Busi_Detail = new ElementLis("Busi_Detail",Busi_List); 
	//������Ϣ
	//�������ײͱ��
	ElementLis AgIns_Pkg_ID = new ElementLis("AgIns_Pkg_ID",sAgIns_Pkg_ID,Busi_Detail); 
	//���ִ���
	ElementLis Cvr_ID = new ElementLis("Cvr_ID",sProductCode,Busi_Detail); 
	String sRiskName=null;
	if(sProductCode.equals("0001")){
		sRiskName="�к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�A��";
	}
	if(sProductCode.equals("0002")){
		sRiskName="�к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�B��";
	}
	if(sProductCode.equals("0003")){
		sRiskName="�к�׿Խ�Ƹ���ȫ���գ��ֺ��ͣ�";
	}
	if(sProductCode.equals("0004")){
		sRiskName="�к����ݻ�����ȫ����A��";
	}
	if(sProductCode.equals("0005")){
		sRiskName="�к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�C��";
	}
	if(sProductCode.equals("0006")){
		sRiskName="�к���δ�������";
	}
	//��������
	ElementLis Cvr_Nm = new ElementLis("Cvr_Nm",sRiskName,Busi_Detail); 
	// Ͷ������
	ElementLis Ins_Cps = new ElementLis("Ins_Cps",sIntialNumberOfUnits,Busi_Detail);
	//��������
	ElementLis InsPrem_Amt = new ElementLis("InsPrem_Amt",sModalPremAmt,Busi_Detail);
	//��������
	ElementLis Ins_Cvr = new ElementLis("Ins_Cvr", sInitCovAmt,Busi_Detail);
	//Ͷ��������Ϣ
	ElementLis Ins_Scm_Inf = new ElementLis("Ins_Scm_Inf", sIns_Scm_Inf,Busi_Detail);
	//��ѡ������ʱ��ս��
	ElementLis Opt_Part_DieIns_Amt = new ElementLis("Opt_Part_DieIns_Amt",sOpt_Part_DieIns_Amt,Busi_Detail); 
	//�״ζ���׷�ӱ���
	ElementLis FTm_Extr_Adl_InsPrem = new ElementLis("FTm_Extr_Adl_InsPrem",sFTm_Extr_Adl_InsPrem,Busi_Detail); 
	//������ϵ��
	ElementLis Emgr_CtcPsn = new ElementLis("Emgr_CtcPsn",sEmgr_CtcPsn,Busi_Detail); 
	//������ϵ���뱻���˹�ϵ
	ElementLis EmgrCtcPsnAndRcReTpCd = new ElementLis("EmgrCtcPsnAndRcReTpCd",sEmgrCtcPsnAndRcReTpCd,Busi_Detail); 
	//������ϵ�绰
    ElementLis Emgr_Ctc_Tel = new ElementLis("Emgr_Ctc_Tel",sEmgr_Ctc_Tel,Busi_Detail); 
	//���д����ͬ���
	ElementLis Bnk_Loan_Ctr_ID = new ElementLis("Bnk_Loan_Ctr_ID",Busi_Detail); 
	//���к�ͬʧЧ����
	ElementLis Ln_Ctr_ExpDt = new ElementLis("Ln_Ctr_ExpDt",Busi_Detail); 
	//δ��������
	ElementLis Upd_Loan_Amt = new ElementLis("Upd_Loan_Amt",Busi_Detail); 
	//��������ƾ֤����
	ElementLis PrimBlInsPolcyVchr_No = new ElementLis("PrimBlInsPolcyVchr_No",Busi_Detail); 
	//���ѽɷѷ�ʽ	
	ElementLis PbPayPeriod = new ElementLis("InsPrem_PyF_MtdCd",sPaymentMode,Busi_Detail);
	//���ѽɷ�����	
	ElementLis InsPrem_PyF_Prd_Num = new ElementLis("InsPrem_PyF_Prd_Num",sPaymentDuration,Busi_Detail);
	//���ѽɷ����ڴ���	
	ElementLis InsPrem_PyF_Cyc_Cd = new ElementLis("InsPrem_PyF_Cyc_Cd","1",Busi_Detail);
	//���ڱ��ս���ȡ��ʽ �� ���/�������ȡ��ʽ��
	ElementLis ExpPrmmRcvModCgyCd = new ElementLis("ExpPrmmRcvModCgyCd","",Busi_Detail); 
	ElementLis SvBnf_Drw_Cyc_Cd = new ElementLis("SvBnf_Drw_Cyc_Cd",sBenefitMode,Busi_Detail); 
	//Լ�����ѵ潻��ʽ
	ElementLis ApntInsPremPyAdvnInd = new ElementLis("ApntInsPremPyAdvnInd",sAutoPayFlag,Busi_Detail); 
	//������ȡ��ʽ
	ElementLis XtraDvdn_Pcsg_MtdCd = new ElementLis("XtraDvdn_Pcsg_MtdCd",sDivType,Busi_Detail);
	//������־
	ElementLis RdAmtPyCls_Ind = new ElementLis("RdAmtPyCls_Ind","",Busi_Detail); 

	//�����ȡ������
	ElementLis Anuty_Drw_CgyCd = new ElementLis("Anuty_Drw_CgyCd",Busi_Detail);
	//�����ȡ����/��ȡ����
	ElementLis Anuty_Drw_Prd_Num = new ElementLis("Anuty_Drw_Prd_Num",sPbDrawAgeTag,Busi_Detail); 
	//�����ȡ���ڴ���
	ElementLis Anuty_Drw_Cyc_Cd = new ElementLis("Anuty_Drw_Cyc_Cd",Busi_Detail);
	//������������
	ElementLis Ins_Yr_Prd_CgyCd = new ElementLis("Ins_Yr_Prd_CgyCd",sDurationMode,Busi_Detail);
	//��������
	ElementLis Ins_Ddln = new ElementLis("Ins_Ddln",sDuration,Busi_Detail);
	//�������ڴ���
	ElementLis Ins_Cyc_Cd = new ElementLis("Ins_Cyc_Cd",Busi_Detail);
	//Ͷ�ʷ�ʽ����
	ElementLis Ivs_MtdCd = new ElementLis("Ivs_MtdCd","",Busi_Detail); 
	//�Զ�������־
	ElementLis Auto_RnwCv_Ind = new ElementLis("Auto_RnwCv_Ind","0",Busi_Detail); 
	//�����ٲ÷�ʽ
	ElementLis Dspt_Pcsg_MtdCd = new ElementLis("Dspt_Pcsg_MtdCd","",Busi_Detail);
	//�ٲû�������
	ElementLis Dspt_Arbtr_Inst_Nm = new ElementLis("Dspt_Arbtr_Inst_Nm","",Busi_Detail);
	//�����ֶ�
	ElementLis Rsrv_Fld_1 = new ElementLis("Rsrv_Fld_1","",Busi_Detail);
	ElementLis Rsrv_Fld_2 = new ElementLis("Rsrv_Fld_2","",Busi_Detail);
	ElementLis Rsrv_Fld_3 = new ElementLis("Rsrv_Fld_3","",Busi_Detail);
	ElementLis Rsrv_Fld_4 = new ElementLis("Rsrv_Fld_4","",Busi_Detail);
	ElementLis Rsrv_Fld_5 = new ElementLis("Rsrv_Fld_5","",Busi_Detail);
	ElementLis Rsrv_Fld_6 = new ElementLis("Rsrv_Fld_6","",Busi_Detail);
	ElementLis Rsrv_Fld_7 = new ElementLis("Rsrv_Fld_7","",Busi_Detail);
	ElementLis Rsrv_Fld_8 = new ElementLis("Rsrv_Fld_8","",Busi_Detail);
	ElementLis Rsrv_Fld_9 = new ElementLis("Rsrv_Fld_9","",Busi_Detail);
	ElementLis Rsrv_Fld_10 = new ElementLis("Rsrv_Fld_10","",Busi_Detail);
	
	//�������ͷ�ʽ
//	ElementLis PbSendMode = new ElementLis("PbSendMode",sPolicyDeliveryMethod,APP_ENTITY);
	//������
//	ElementLis PbBeginDate = new ElementLis("PbBeginDate","",APP_ENTITY); 
	//���ѷ�ʽ
//	ElementLis PbInsuPayMode = new ElementLis("PbInsuPayMode",sPaymentMethod,APP_ENTITY); 
	   //�ɷ��ʻ�
//	ElementLis BkAcctNo1 = new ElementLis("BkAcctNo1",sAccountNumber,APP_ENTITY);
	//�ر�Լ��
//	ElementLis LiSpec = new ElementLis("LiSpec",sSpecialClause,APP_ENTITY); 
	//����������
//	ElementLis LiBonusDistbTag = new ElementLis("LiBonusDistbTag","",APP_ENTITY); 
	
//	ElementLis BkAcctNo3 = new ElementLis("BkAcctNo3","",APP_ENTITY); 
//	ElementLis BkAcctNo2 = new ElementLis("BkAcctNo2","",APP_ENTITY); 
	//�ɷ�����
//	ElementLis PbPayAgeTag = new ElementLis("PbPayAgeTag",sPaymentDuration,APP_ENTITY);
// 	ElementLis PbPayAge = new ElementLis("PbPayAge","",APP_ENTITY); 
// 	ElementLis PbDrawAge = new ElementLis("PbDrawAge",sPbDrawAge,APP_ENTITY); 
// 	ElementLis PbInsuYear = new ElementLis("PbInsuYear","",APP_ENTITY); 
//	ElementLis BkNum1 = new ElementLis("BkNum1","",APP_ENTITY); 
//    int RiskCount=0;
    //����и�����1    
/*	if(!(sProductCode1==null||sProductCode1.equals(""))){
		ElementLis Appd_List = new ElementLis("Appd_List",APP_ENTITY); 
		RiskCount++;
		ElementLis Appd_Detail = new ElementLis("Appd_Detail",Appd_List);
		ElementLis LiAppdInsuType = new ElementLis("LiAppdInsuType",sProductCode1,Appd_Detail);
		
		ElementLis LiAppdInsuName = new ElementLis("LiAppdInsuName","",Appd_Detail);
		//Ͷ������
		ElementLis LiAppdInsuNumb = new ElementLis("LiAppdInsuNumb",sIntialNumberOfUnits1,Appd_Detail);	
		ElementLis LiAppdInsuExp = new ElementLis("LiAppdInsuExp",sModalPremAmt1,Appd_Detail);
		ElementLis LiAppdInsuAmot = new ElementLis("LiAppdInsuAmot",sInitCovAmt1,Appd_Detail);
		
		ElementLis LiAppdInsuTerm = new ElementLis("LiAppdInsuTerm",sDuration1,Appd_Detail);
	
		ElementLis LiAppdInsuPayTerm = new ElementLis("LiAppdInsuPayTerm",sPaymentDuration1,Appd_Detail);
		
		ElementLis LiBackTag = new ElementLis("LiBackTag","",Appd_Detail);
		
		ElementLis PiReserve = new ElementLis("PiReserve","",Appd_Detail);
	}*/
//����и�����2  
/*	if(!(sProductCode2==null||sProductCode2.equals(""))){	
		RiskCount++;
		ElementLis Appd_Detail = new ElementLis("Appd_Detail",Appd_List);
		ElementLis LiAppdInsuType = new ElementLis("LiAppdInsuType",sProductCode2,Appd_Detail);
		
		ElementLis LiAppdInsuName = new ElementLis("LiAppdInsuName","",Appd_Detail);
		//Ͷ������
		ElementLis LiAppdInsuNumb = new ElementLis("LiAppdInsuNumb",sIntialNumberOfUnits2,Appd_Detail);	
		ElementLis LiAppdInsuExp = new ElementLis("LiAppdInsuExp",sModalPremAmt2,Appd_Detail);
		ElementLis LiAppdInsuAmot = new ElementLis("LiAppdInsuAmot",sInitCovAmt2,Appd_Detail);
		
		ElementLis LiAppdInsuTerm = new ElementLis("LiAppdInsuTerm",sDuration2,Appd_Detail);
	
		ElementLis LiAppdInsuPayTerm = new ElementLis("LiAppdInsuPayTerm",sPaymentDuration2,Appd_Detail);
		
		ElementLis LiBackTag = new ElementLis("LiBackTag","",Appd_Detail);
		
		ElementLis PiReserve = new ElementLis("PiReserve","",Appd_Detail);
	}*/
//	String RiskCt=""+RiskCount;
//	BkNum1.setText(RiskCt);
//Ͷ���˻���Ϣ
	String sIvsAc_Num="0";
	ElementLis IvsAc_Num= new ElementLis("IvsAc_Num",sIvsAc_Num,Busi_Detail);
	ElementLis PayAcctCode_List= new ElementLis("PayAcctCode_List",Busi_Detail);
	if(!sIvsAc_Num.equals("0")){
		ElementLis PayAcctCode_Detail= new ElementLis("PayAcctCode_Detail",PayAcctCode_List);
		ElementLis ILIVA_ID = new ElementLis("ILIVA_ID",PayAcctCode_Detail);
		ElementLis ILIVA_Nm = new ElementLis("ILIVA_Nm",PayAcctCode_Detail);
		ElementLis Ivs_Tp_Alct_Pctg = new ElementLis("Ivs_Tp_Alct_Pctg",PayAcctCode_Detail);
		ElementLis Adl_Ins_Fee_Alct_Pctg = new ElementLis("Adl_Ins_Fee_Alct_Pctg",PayAcctCode_Detail);
	}
		//������֪���
	
	ElementLis Ntf_Itm_Ind = new ElementLis("Ntf_Itm_Ind",sHealthIndicator,APP_ENTITY);
	
	//Ͷ�����
	ElementLis Ins_Bl_Prt_No = new ElementLis("Ins_Bl_Prt_No",sHOAppFormNumber,APP_ENTITY);
	ElementLis BkVchNo = new ElementLis("BkVchNo",sProviderFormNumber,APP_ENTITY);
	//��������
	ElementLis InsPolcy_Pswd = new ElementLis("InsPolcy_Pswd","10086sb",APP_ENTITY);
	//��Ʊ��
	ElementLis Inv_No = new ElementLis("Inv_No","1008610010",APP_ENTITY);
	
//	ElementLis BkRckrNo = new ElementLis("BkRckrNo",sTeller,APP_ENTITY);

	//���л�������
	ElementLis CCBIns_ID2 = new ElementLis("CCBIns_ID","330613535",APP_ENTITY);
//	ElementLis CCBIns_ID2 = new ElementLis("CCBIns_ID","0020000019",APP_ENTITY);
	//һ�����к�
	ElementLis Lv1_Br_No = new ElementLis("Lv1_Br_No","110000000",APP_ENTITY);
	//������ȡ��ʽ����
	ElementLis InsPolcy_Rcv_MtdCd = new ElementLis("InsPolcy_Rcv_MtdCd","",APP_ENTITY);
	//����ר��������
	ElementLis Ins_Prj_CgyCd = new ElementLis("Ins_Prj_CgyCd","",APP_ENTITY);
	//���ѳ���������
	ElementLis PydFeeOutBill_CgyCd = new ElementLis("PydFeeOutBill_CgyCd","",APP_ENTITY);
	//����ʵ�����۵�������
	ElementLis InsPolcyActSaleRgonID = new ElementLis("InsPolcyActSaleRgonID","",APP_ENTITY);
	//���տͻ������ṩ��������
	ElementLis Ins_CsLs_Prvd_Rgon_ID = new ElementLis("Ins_CsLs_Prvd_Rgon_ID","",APP_ENTITY);
	//���Ա�ע
	ElementLis Pstcrpt_Rmrk = new ElementLis("Pstcrpt_Rmrk","",APP_ENTITY);
	//��������Ч����
	ElementLis InsPolcy_Intnd_EfDt = new ElementLis("InsPolcy_Intnd_EfDt","",APP_ENTITY);
	//��������
	ElementLis BO_Nm = new ElementLis("BO_Nm","",APP_ENTITY);
	//���㱣�ռ�ְ����ҵ�����֤����
	ElementLis BOInsPrAgnBsnLcns_ECD = new ElementLis("BOInsPrAgnBsnLcns_ECD","",APP_ENTITY);
	//���㱣�ռ�ְ����ҵ�����֤��Ч����
	ElementLis BOInsPrAgBsnLcnVld_Dt = new ElementLis("BOInsPrAgBsnLcnVld_Dt","",APP_ENTITY);
	//����������Ա����
	ElementLis BO_Sale_Stff_Nm = new ElementLis("BO_Sale_Stff_Nm",sBO_Sale_Stff_Nm,APP_ENTITY);
	//����������Ա���
	ElementLis BO_Sale_Stff_ID = new ElementLis("BO_Sale_Stff_ID",sBO_Sale_Stff_ID,APP_ENTITY);
	//������Ա�����մ�ҵ��Ա�ʸ�֤����
	ElementLis Sale_Stff_AICSQCtf_ID = new ElementLis("Sale_Stff_AICSQCtf_ID",sSale_Stff_AICSQCtf_ID,APP_ENTITY);
	//���մ����ҵ��Ա�ʸ�֤����Ч����
	ElementLis InsAgnCrStQuaCtVld_Dt = new ElementLis("InsAgnCrStQuaCtVld_Dt","",APP_ENTITY);
	//����ֹܴ�����ҵ���˱��
	ElementLis BOIChOfAgInsBsnPnp_ID = new ElementLis("BOIChOfAgInsBsnPnp_ID","",APP_ENTITY);
	//����ֹܴ�����ҵ��������
	ElementLis BOIChOfAgInsBsnPnp_Nm = new ElementLis("BOIChOfAgInsBsnPnp_Nm","",APP_ENTITY);
	//���ڽɷ�֧����ʽ����
	ElementLis Rnew_PyF_PyMd_Cd = new ElementLis("Rnew_PyF_PyMd_Cd","",APP_ENTITY);
	//Ͷ���˽ɷ��ʺ�
	ElementLis Plchd_PyF_AccNo = new ElementLis("Plchd_PyF_AccNo",sAccountNumber,APP_ENTITY);
	//Ͷ������ȡ�ʺ�
	ElementLis Plchd_Drw_AccNo = new ElementLis("Plchd_Drw_AccNo","",APP_ENTITY);
	//�������˺�
	ElementLis Rcgn_AccNo = new ElementLis("Rcgn_AccNo","",APP_ENTITY);
	//�������˺�
	ElementLis Benf_AccNo = new ElementLis("Benf_AccNo","",APP_ENTITY);
	//�����ֶ�
	ElementLis Rsrv_Fld_11 = new ElementLis("Rsrv_Fld_11","",APP_ENTITY);
	ElementLis Rsrv_Fld_12 = new ElementLis("Rsrv_Fld_12","",APP_ENTITY);
	ElementLis Rsrv_Fld_13 = new ElementLis("Rsrv_Fld_13","",APP_ENTITY);
	ElementLis Rsrv_Fld_14 = new ElementLis("Rsrv_Fld_14","",APP_ENTITY);
	ElementLis Rsrv_Fld_15 = new ElementLis("Rsrv_Fld_15","",APP_ENTITY);
	ElementLis Rsrv_Fld_16 = new ElementLis("Rsrv_Fld_16","",APP_ENTITY);
	ElementLis Rsrv_Fld_17 = new ElementLis("Rsrv_Fld_17","",APP_ENTITY);
	ElementLis Rsrv_Fld_18 = new ElementLis("Rsrv_Fld_18","",APP_ENTITY);
	ElementLis Rsrv_Fld_19 = new ElementLis("Rsrv_Fld_19","",APP_ENTITY);
	ElementLis Rsrv_Fld_20 = new ElementLis("Rsrv_Fld_20","",APP_ENTITY);
	
	ElementLis Cst_Rsk_Tlrnc_Cpy_Cd = new ElementLis("Cst_Rsk_Tlrnc_Cpy_Cd",sCst_Rsk_Tlrnc_Cpy_Cd,APP_ENTITY);
	ElementLis Rsk_Evlt_AvlDt = new ElementLis("Rsk_Evlt_AvlDt",sRsk_Evlt_AvlDt,APP_ENTITY);
	ElementLis Bdgt_Amt = new ElementLis("Bdgt_Amt",sBdgt_Amt,APP_ENTITY);
	
	//������
//	ElementLis BkAreaNo = new ElementLis("BkAreaNo",sRegionCode,APP_ENTITY);
	//������
//	ElementLis PiManBankNo = new ElementLis("PiManBankNo",sBranch,APP_ENTITY);

						//Ͷ���˻�
						int iSubAccountCount = 0;
						if(!(sAccCode1==null||sAccCode1.equals("")) || !(sAccCode2==null||sAccCode2.equals("")) || !(sAccCode3==null||sAccCode3.equals("")) || !(sAccCode4==null||sAccCode4.equals("")) || !(sAccCode5==null||sAccCode5.equals(""))){
								ElementLis Invests1 = new ElementLis("Invests",APP_ENTITY);
							if(!(sAccCode1==null||sAccCode1.equals(""))){
								ElementLis Invest1 = new ElementLis("Invest",Invests1);
								ElementLis Code1 = new ElementLis("Code",sAccCode1,Invest1);
								ElementLis Name1 = new ElementLis("Name",sAccCodeName1,Invest1);
								ElementLis Rate1 = new ElementLis("Rate",sAllocPercent1,Invest1);
								ElementLis SupAddRate1 = new ElementLis("SupAddRate","",Invest1);
								iSubAccountCount++;
							}
							if(!(sAccCode2==null||sAccCode2.equals(""))){
								ElementLis Invest2 = new ElementLis("Invest",Invests1);
								ElementLis Code2 = new ElementLis("Code",sAccCode2,Invest2);
								ElementLis Name2 = new ElementLis("Name",sAccCodeName2,Invest2);
								ElementLis Rate2 = new ElementLis("Rate",sAllocPercent2,Invest2);
								ElementLis SupAddRate2 = new ElementLis("SupAddRate","",Invest2);
								iSubAccountCount++;
							}
							if(!(sAccCode3==null||sAccCode3.equals(""))){
								ElementLis Invest3 = new ElementLis("Invest",Invests1);
								ElementLis Code3 = new ElementLis("Code",sAccCode3,Invest3);
								ElementLis Name3 = new ElementLis("Name",sAccCodeName3,Invest3);
								ElementLis Rate3 = new ElementLis("Rate",sAllocPercent3,Invest3);
								ElementLis SupAddRate1 = new ElementLis("SupAddRate","",Invest3);
								iSubAccountCount++;
							}
							if(!(sAccCode4==null||sAccCode4.equals(""))){
								ElementLis Invest4 = new ElementLis("Invest",Invests1);
								ElementLis Code4 = new ElementLis("Code",sAccCode4,Invest4);
								ElementLis Name4 = new ElementLis("Name",sAccCodeName4,Invest4);
								ElementLis Rate4 = new ElementLis("Rate",sAllocPercent4,Invest4);
								ElementLis SupAddRate4 = new ElementLis("SupAddRate","",Invest4);
									iSubAccountCount++;
								}
							if(!(sAccCode5==null||sAccCode5.equals(""))){
								ElementLis Invest5 = new ElementLis("Invest",Invests1);
								ElementLis Code5 = new ElementLis("Code",sAccCode5,Invest5);
								ElementLis Name5 = new ElementLis("Name",sAccCodeName5,Invest5);
								ElementLis Rate5 = new ElementLis("Rate",sAllocPercent5,Invest5);
								ElementLis SupAddRate5 = new ElementLis("SupAddRate","",Invest5);
									iSubAccountCount++;
								}
							int CountVlaue=0;
							CountVlaue=Integer.valueOf(sAllocPercent1).intValue()+Integer.valueOf(sAllocPercent2).intValue()+Integer.valueOf(sAllocPercent3).intValue()+Integer.valueOf(sAllocPercent5).intValue()+Integer.valueOf(sAllocPercent5).intValue();
							String CValue=""+CountVlaue;
							ElementLis SumRate = new ElementLis("SumRate",CValue,Invests1);
							String sSubAccountCount = String.valueOf(iSubAccountCount);
						ElementLis AccountCount = new ElementLis("AccountCount",sSubAccountCount,APP_ENTITY);
						}
						 
 }else if(sFlag.equals("P53819151")){
		SYS_TX_CODE.setText("P53819151");
        //��������
	    String sBkAcctNo=request.getParameter("BkAcctNo");
	    String sBkPayMode=request.getParameter("BkPayMode");
//		ElementLis BkAcctNo = new ElementLis("BkAcctNo",sBkAcctNo,APP_ENTITY);
//		ElementLis BkPayMode = new ElementLis("BkPayMode",sBkPayMode,APP_ENTITY);
		//Ͷ������
		ElementLis Ins_BillNo = new ElementLis("Ins_BillNo",sHOAppFormNumber,APP_ENTITY);
		//��������
		ElementLis InsPolcy_No = new ElementLis("InsPolcy_No",sContNo,APP_ENTITY);
		//Ͷ��������
		ElementLis Plchd_Nm = new ElementLis("Plchd_Nm",sAppFullName,APP_ENTITY);
		//Ͷ����֤������
		ElementLis Plchd_Crdt_TpCd = new ElementLis("Plchd_Crdt_TpCd",sAppGovtIDTC,APP_ENTITY);
		//Ͷ����֤����
		ElementLis Plchd_Crdt_No = new ElementLis("Plchd_Crdt_No",sAppGovtID,APP_ENTITY);
		//Ͷ����֤����Ч����
		ElementLis Plchd_Crdt_EfDt = new ElementLis("Plchd_Crdt_EfDt",sAppGovtEfcDate,APP_ENTITY);
		//Ͷ����֤��ʧЧ����
		ElementLis Plchd_Crdt_ExpDt = new ElementLis("Plchd_Crdt_ExpDt",sAppGovtTermDate,APP_ENTITY);
		//Ͷ���˹���
		ElementLis Plchd_Nat_Cd = new ElementLis("Plchd_Nat_Cd",sAppCountry,APP_ENTITY);
 }else if(sFlag.equals("P53819152")){
		SYS_TX_CODE.setText("P53819152");
        //��������
	    String sBkAcctNo=request.getParameter("BkAcctNo");
	    String sBkPayMode=request.getParameter("BkPayMode");
//		ElementLis BkAcctNo = new ElementLis("BkAcctNo",sBkAcctNo,APP_ENTITY);
//		ElementLis BkPayMode = new ElementLis("BkPayMode",sBkPayMode,APP_ENTITY);
		//Ͷ��������
		ElementLis Plchd_Nm = new ElementLis("Plchd_Nm",sAppFullName,APP_ENTITY);
		//Ͷ����֤������
		ElementLis Plchd_Crdt_TpCd = new ElementLis("Plchd_Crdt_TpCd",sAppGovtIDTC,APP_ENTITY);
		//Ͷ����֤����
		ElementLis Plchd_Crdt_No = new ElementLis("Plchd_Crdt_No",sAppGovtID,APP_ENTITY);
		//Ͷ����֤����Ч����
		ElementLis Plchd_Crdt_EfDt = new ElementLis("Plchd_Crdt_EfDt",sAppGovtEfcDate,APP_ENTITY);
		//Ͷ����֤��ʧЧ����
		ElementLis Plchd_Crdt_ExpDt = new ElementLis("Plchd_Crdt_ExpDt",sAppGovtTermDate,APP_ENTITY);
		//Ͷ���˹���
		ElementLis Plchd_Nat_Cd = new ElementLis("Plchd_Nat_Cd",sAppCountry,APP_ENTITY);
		//Ͷ������
		ElementLis Ins_BillNo = new ElementLis("Ins_BillNo",sHOAppFormNumber,APP_ENTITY);
		//���սɷѽ��
		ElementLis Ins_PyF_Amt = new ElementLis("Ins_PyF_Amt","",APP_ENTITY);
		//����֧����ʽ����
		ElementLis InsPrem_PyMd_Cd = new ElementLis("InsPrem_PyMd_Cd",sBkPayMode,APP_ENTITY);
		//�����ʺ�
		ElementLis CCB_AccNo = new ElementLis("CCB_AccNo",sBkAcctNo,APP_ENTITY);
		//���չ�˾��ˮ��
		ElementLis Ins_Co_Jrnl_No = new ElementLis("Ins_Co_Jrnl_No",sReqsrNo,APP_ENTITY);
 }else if(sFlag.equals("P53819182")){
		SYS_TX_CODE.setText("P53819182");
        //��������
	    String sBkAcctNo=request.getParameter("BkAcctNo");
	    String sBkPayMode=request.getParameter("BkPayMode");
//		ElementLis BkAcctNo = new ElementLis("BkAcctNo",sBkAcctNo,APP_ENTITY);
//		ElementLis BkPayMode = new ElementLis("BkPayMode",sBkPayMode,APP_ENTITY);
		//���ִ���
		ElementLis Cvr_ID = new ElementLis("Cvr_ID",sProductCode,APP_ENTITY);
		//������
		ElementLis InsPolcy_No = new ElementLis("InsPolcy_No",sContNo,APP_ENTITY);

		//ѭ����¼����
		ElementLis Rvl_Rcrd_Num = new ElementLis("Rvl_Rcrd_Num","1",APP_ENTITY);
		ElementLis Detail_List = new ElementLis("Detail_List",APP_ENTITY);
		ElementLis Detail = new ElementLis("Detail",Detail_List);
		
		//��֤����
		ElementLis AgIns_Vchr_TpCd = new ElementLis("AgIns_Vchr_TpCd","0101141",Detail);
		//��֤��
		ElementLis Ins_IBVoch_ID = new ElementLis("Ins_IBVoch_ID",sProviderFormNumber,Detail);
 }
						Document pXmlDoc = new Document(TX);	
						System.out.println("������Ϣ������");
						JdomUtil.print(pXmlDoc);
		int iPort = Integer.valueOf(sPort).intValue();
		NewCCBTestUI mTestUI = new NewCCBTestUI(sIp,iPort);  
		
			byte[] mOutBytes  = mTestUI.sendRequest(sFlag,pXmlDoc);
		Document mOutXmlDoc = JdomUtil.build(mOutBytes,"UTF-8");
	//	JdomUtil.print(mOutXmlDoc);
		
	//	InputStream inputStream = new ByteArrayInputStream(JdomUtil.toBytes(pXmlDoc));
	////	byte[] mOutBytes = mTestUI.sendRequest(inputStream);
	 
		//Document mOutXmlDoc = JdomUtil.build(mOutBytes); 
			JdomUtil.print(mOutXmlDoc);
		ResultCode = mOutXmlDoc.getRootElement().getChild("TX_HEADER").getChildTextTrim("SYS_TX_STATUS");
		ResultInfoDesc = mOutXmlDoc.getRootElement().getChild("TX_HEADER").getChildTextTrim("SYS_RESP_DESC");
		PubFun1 pubfun = new PubFun1();
			System.out.println(ResultCode + "  " + ResultInfoDesc);
        System.out.println("�������ݣ�"+ResultInfoDesc);
		if (ResultCode.equals("00")) {
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