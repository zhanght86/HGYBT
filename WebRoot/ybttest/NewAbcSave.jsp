<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.utility.ElementLis"%>
<%@page import="com.sinosoft.midplat.common.DateUtil"%>  
<%@page import="com.sinosoft.midplat.common.JdomUtil"%>  
<%@page import="com.sinosoft.midplat.common.IOTrans"%>  
<%@page import="com.sinosoft.lis.pubfun.PubFun1"%> 
<%@page import="org.jdom.Document"%>  
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%> 
<%@page import="java.text.DateFormat"%> 
<%@page import="java.text.SimpleDateFormat"%> 
<%@page import=" java.util.GregorianCalendar"%> 
<%@page import="com.sinosoft.utility.*"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.ByteArrayInputStream"%>
<%@page import="java.io.InputStream"%>
<%@page import="test.NewABCTest"%>
<%@page import="org.jdom.Element"%>
<% 
	String ResultCode = null;
	String Content = null;
	String ResultInfoDesc = null; 
	String strNewTransNo = null;
	String xmlContent = null;
	String strNewHOAppFormNumber = null;
	String strNewProviderFormNumber = null;
	String uiPrint = null;   
	String strNewInsurialno = null;
%>

<%
  try {
 
 //ͷ�ڵ�
	request.setCharacterEncoding("GBK");
	String sTRANSCODE = request.getParameter("TRANSCODE");
	String sTranCom = request.getParameter("TranCom");
	String sSERIALNO = request.getParameter("SERIALNO");
	String sINSUSERIAL = request.getParameter("INSUSERIAL");
	String sTRANSDATE = DateUtil.date10to8(request.getParameter("TRANSDATE"));
	String sTRANSTIME = DateUtil.time8to6(DateUtil.getCur8Time());
	String sCORPNO = request.getParameter("CORPNO");
	String sBRANCHNO = request.getParameter("BRANCHNO");
	String sTLID = request.getParameter("TLID");
	String sProvCode=request.getParameter("ProvCode");
	String sAPPNO = request.getParameter("APPNO");
	
		
//Ͷ����DateUtil.date10to8(
	String sAPPLIDKIND = request.getParameter("APPLIDKIND");
	String sAPPLIDCODE = request.getParameter("APPLIDCODE");
	String sAPPLBEGINDATE = DateUtil.date10to8(request.getParameter("APPLBEGINDATE"));
	String sAPPLINVALIDDATE = DateUtil.date10to8(request.getParameter("APPLINVALIDDATE"));
	String sAPPLNAME = request.getParameter("APPLNAME");
	String sAPPLSEX = request.getParameter("APPLSEX");
	String sAPPLBIRTHDAY = DateUtil.date10to8(request.getParameter("APPLBIRTHDAY"));
	String sAPPLCOUNTRY = request.getParameter("APPLCOUNTRY");
	String sAPPLADDRESS = request.getParameter("APPLADDRESS");
	String sAPPLZONE = request.getParameter("APPLZONE");
	String sAPPLZIPCODE = request.getParameter("APPLZIPCODE");
	String sAPPLEMAIL = request.getParameter("APPLEMAIL");
	String sAPPLPHONE = request.getParameter("APPLPHONE");
	String sAPPLMOBILE = request.getParameter("APPLMOBILE");
	String sAPPLFIXINCOME = request.getParameter("APPLFIXINCOME");
	String sAPPLANNUALINCOME = request.getParameter("APPLANNUALINCOME");
	String sAPPLJOBTYPE = request.getParameter("APPLJOBTYPE");
	String sAPPLJOBCODE = request.getParameter("APPLJOBCODE");
	String sAPPLNOTICE = request.getParameter("APPLNOTICE");
	String sAPPLRELATOINSURED = request.getParameter("APPLRELATOINSURED");
	String sAPPLCustSource = request.getParameter("PbDenType");
	
	//������
	String sINSUIDKIND = request.getParameter("INSUIDKIND");
	String sINSUIDCODE = request.getParameter("INSUIDCODE");
	String sINSUBEGINDATE = DateUtil.date10to8(request.getParameter("INSUBEGINDATE"));
	String sINSUINVALIDDATE = DateUtil.date10to8(request.getParameter("INSUINVALIDDATE"));
	String sINSUNAME = request.getParameter("INSUNAME");
	String sINSUSEX = request.getParameter("INSUSEX");
	String sINSUBIRTHDAY = DateUtil.date10to8(request.getParameter("INSUBIRTHDAY"));
	String sINSUCOUNTRY = request.getParameter("INSUCOUNTRY");
	String sINSUADDRESS = request.getParameter("INSUADDRESS");
	String sINSUPROV = request.getParameter("INSUPROV");
	String sINSUZIPCODE = request.getParameter("INSUZIPCODE");
	String sINSUEMAIL = request.getParameter("INSUEMAIL");
	String sINSUPHONE = request.getParameter("INSUPHONE");
	String sINSUMOBILE = request.getParameter("INSUMOBILE");
	String sINSUJOBTYPE = request.getParameter("INSUJOBTYPE");
	String sINSUJOBCODE = request.getParameter("INSUJOBCODE");
	String sINSUNOTICE = request.getParameter("INSUNOTICE");
	String sINSUANNUALINCOME = request.getParameter("INSUANNUALINCOME");
	String sINSUISRISKJOB = request.getParameter("INSUISRISKJOB");
	String sINSUHEALTHNOTICE = request.getParameter("INSUHEALTHNOTICE");
	String sINSUIMMATURITY = request.getParameter("INSUIMMATURITY");
	String sINSUCREDITTYPE = request.getParameter("INSUCREDITTYPE");
	String sINSUCREDITCARD = request.getParameter("INSUCREDITCARD");
	
	//������1
	
	String sBNFCOUNT = request.getParameter("BNFCOUNT");
	
	String sBNFTYPE1 = request.getParameter("BNFTYPE1");
	String sBNFNAME1 = request.getParameter("BNFNAME1");
	String sBNFSEX1 = request.getParameter("BNFSEX1");
	String sBNFIDKIND1 = request.getParameter("BNFIDKIND1");
	String sBNFIDCODE1 = request.getParameter("BNFIDCODE1");
	String sBNFBIRTHDAY1 = request.getParameter("BNFBIRTHDAY1");
	String sBNFPROP1 = request.getParameter("BNFPROP1");
	String sBNFSEQUENCE1 = request.getParameter("BNFSEQUENCE1");
  String sBNFINSUREDREF1 = request.getParameter("BnfRelationToInsured1");
	//������2
	String sBNFTYPE2 = request.getParameter("BNFTYPE2");
	String sBNFNAME2 = request.getParameter("BNFNAME2");
	String sBNFSEX2 = request.getParameter("BNFSEX2");
	String sBNFIDKIND2 = request.getParameter("BNFIDKIND2");
	String sBNFIDCODE2 = request.getParameter("BNFIDCODE2");
	String sBNFBIRTHDAY2 = request.getParameter("BNFBIRTHDAY2");
	String sBNFPROP2 = request.getParameter("BNFPROP2");
	String sBNFSEQUENCE2 = request.getParameter("BNFSEQUENCE2");
	String sBNFINSUREDREF2 = request.getParameter("BnfRelationToInsured2");
	//������3
	String sBNFTYPE3 = request.getParameter("BNFTYPE3");
	String sBNFNAME3 = request.getParameter("BNFNAME3");
	String sBNFSEX3 = request.getParameter("BNFSEX3");
	String sBNFIDKIND3 = request.getParameter("BNFIDKIND3");
	String sBNFIDCODE3 = request.getParameter("BNFIDCODE3");
	String sBNFBIRTHDAY3 = request.getParameter("BNFBIRTHDAY3");
	String sBNFPROP3 = request.getParameter("BNFPROP3");
	String sBNFSEQUENCE3 = request.getParameter("BNFSEQUENCE3");
	String sBNFINSUREDREF3 = request.getParameter("BnfRelationToInsured3");
	
	//����
	
	//�ɷѷ�ʽ
	String sRISKSPAYTYPE = request.getParameter("PayType");
	//��������
	String sRISKSRISKCODENAEM = request.getParameter("RiskCodeName");
	//���մ���
	String sRISKSRISKCODE = request.getParameter("RISKSRISKCODE");
	//���ձ���
	String sRISKSPREM = request.getParameter("RISKSPREM");
	//���ս��
	String sRISKSAMNT = request.getParameter("RISKSAMNT");
	//����
	String sRISKSHARE = request.getParameter("RISKSHARE");
	//���
	String sBonusGetMode=request.getParameter("BonusGetMode");
	//�����ڼ�����
	String sRISKSINSUDUETYPE = request.getParameter("RISKSINSUDUETYPE");
	//�����ڼ�
	String sRISKSINSUDUEDATE = request.getParameter("RISKSINSUDUEDATE");
	//�����ڼ�����
	String sRISKSPAYDUETYPE = request.getParameter("RISKSPAYDUETYPE");
	//�����ڼ�
	String sRISKSPAYDUEDATE = request.getParameter("RISKSPAYDUEDATE");
	//
	String sRISKSBEGINDATE = DateUtil.date10to8(request.getParameter("RISKSBEGINDATE"));
	String sRISKSENDEDATE = DateUtil.date10to8(request.getParameter("RISKSENDDATE"));
	
	//������1
	String sADDTCOUNT = "0";
	//����
	String sADDTRISKCODE1 = request.getParameter("ADDTRISKCODE1");
	//����
	String sADDTSHARE1 = request.getParameter("ADDTSHARE1");
	//����
	String sADDTPREM1 = request.getParameter("ADDTPREM1");
	//�������շ�
	String sADDTAMNT1 = request.getParameter("ADDTAMNT1");
	//�����ڼ�����
	String sADDTINSUDUETYPE1 = request.getParameter("ADDTINSUDUETYPE1");
	//��������
	String sADDTINSUDUEDATE1 = request.getParameter("ADDTINSUDUEDATE1");
	//��������
	String sADDTPAYTYPE1 = request.getParameter("ADDTPAYTYPE1");
	//���Ѵ���
	String sADDTPAYDATE1 = request.getParameter("ADDTPAYDATE1");
    //�����ڼ�����
	String sADDTPAYDUETYPE1=request.getParameter("ADDTPAYDUETYPE1");
	//���Ѵ���
	String sADDTPAYDUEDATE1=request.getParameter("ADDTPAYDUEDATE1");
	
	
	//������2
	String sADDTRISKCODE2 = request.getParameter("ADDTRISKCODE2");
	String sADDTSHARE2 = request.getParameter("ADDTSHARE2");
	String sADDTPREM2 = request.getParameter("ADDTPREM2");
	String sADDTAMNT2 = request.getParameter("ADDTAMNT2");
	String sADDTINSUDUETYPE2 = request.getParameter("ADDTINSUDUETYPE2");
	String sADDTINSUDUEDATE2 = request.getParameter("ADDTINSUDUEDATE2");
	String sADDTPAYTYPE2 = request.getParameter("ADDTPAYTYPE2");
	String sADDTPAYDATE2 = request.getParameter("ADDTPAYDATE2");

	//����
	String sLOANCOM = request.getParameter("LoanCom");
	String sLOANCONTRACTNO = request.getParameter("LoanContractNo");
	String sLOANSTARTDATE = DateUtil.date10to8(request.getParameter("LoanStartDate"));
	String sLOANENDDATE = DateUtil.date10to8(request.getParameter("LoanEndDate"));
	String sLOANCONTRACTAMT = request.getParameter("LoanContractAmt");
	String sLOANTYPE = request.getParameter("LoanType");
	
	//Base�ڵ�
	String sSPECARRANGED = request.getParameter("SPECARRANGED");
	String sARUGEFLAG = request.getParameter("ARUGEFLAG");
	String sARBTENAME = request.getParameter("ARBTENAME");
	String sCONACCNAME = request.getParameter("CONACCNAME");
	String sCONACCNO = request.getParameter("CONACCNO");
	String sAPPLYDATE = DateUtil.date10to8(request.getParameter("APPLYDATE"));
	String sPOLICYAPPLYSERIAL = request.getParameter("POLICYAPPLYSERIAL");
	String sVCHTYPE = request.getParameter("VCHTYPE");
	String sVCHNO = request.getParameter("VCHNO");
	String sSALER = request.getParameter("SALER");
	String sSALERCERTNO = request.getParameter("SALERCERTNO");
	String sBRANCHCERTNO = request.getParameter("BRANCHCERTNO");
	String sBRANCHNAME = request.getParameter("BRANCHNAME");
	

	String sIp = request.getParameter("ip");
	String sPort = request.getParameter("port");
 


//�ѱ�����Ϣ��ҳ����ʾ
		xmlContent = new String(""); 
   
		xmlContent += "һ��������Ϣ\\n";
		xmlContent += "  ���ж�����: " + sTRANSDATE;
		xmlContent += "\t\t������ˮ��: " + sSERIALNO + "\\n";
		//xmlContent += "  ��������: " + sRegionCode; 
		xmlContent += "\t\t\t�����������: " + sBRANCHNO + "\\n"; 
		xmlContent += "  ��Ա����: " + sTLID;
		xmlContent += "\t\t\tͶ������: " + sAPPLYDATE + "\\n"; 
		xmlContent += "  Ͷ�����: " + sPOLICYAPPLYSERIAL;
		xmlContent += "\t\t����ӡˢ��: " + sVCHNO + "\\n";
		xmlContent += "  �����������: " + sLOANCOM;
		xmlContent += "\t\t�����ͬ��: " + sLOANCONTRACTNO + "\\n";
		xmlContent += "  ������ʼ����: " + sLOANSTARTDATE;
		xmlContent += "\t\t������ֹ����: " + sLOANENDDATE + "\\n";
		xmlContent += "  ������: " + sLOANCONTRACTAMT;
		xmlContent += "\t\t���ѷ�ʽ: " + sRISKSPAYTYPE + "\\n";
		
		xmlContent += "����Ͷ������Ϣ\\n";
		xmlContent += "  Ͷ��������: " + sAPPLNAME;
		xmlContent += "\t\tͶ�����Ա�: " + sAPPLSEX + "\\n";
		xmlContent += "  Ͷ����֤������: " + sAPPLIDKIND ;
		xmlContent += "\t\tͶ����֤������: " + sAPPLIDCODE + "\\n";
		xmlContent += "  Ͷ��������: " + sAPPLBIRTHDAY ;
	//	xmlContent += "\t\tͶ����֤����Ч�� : " + sAppGovtTermDate + "\\n";	
		xmlContent += "  Ͷ���˵�ַ: " + sAPPLADDRESS ;
		xmlContent += "\tͶ���˵������� : " + sAPPLEMAIL + "\\n";	
		xmlContent += "  Ͷ�����ʱ� " + sAPPLZIPCODE ;
		//xmlContent += "\t\tͶ���˹�˾�绰: : " + sAppDialNumber1 + "\\n";	
		
		xmlContent += "  Ͷ���˹̶��绰: " + sAPPLPHONE;
		xmlContent += "\tͶ�����ƶ��绰: " + sAPPLMOBILE+ "\\n";
		xmlContent += "  Ͷ�����뱻���˵Ĺ�ϵ: " + sAPPLRELATOINSURED+ "\\n";	
		xmlContent += "Ͷ���������룺" + sAPPLANNUALINCOME ;	
		xmlContent += "Ͷ���˾������ͣ�" + sAPPLCustSource ;	
  
		
		xmlContent += "������������Ϣ\\n";
		xmlContent += "  ����������: " + sINSUNAME;
		xmlContent += "\t\t�������Ա�: " + sINSUSEX + "\\n";
		xmlContent += "  ������֤������: " + sINSUIDKIND ;
		xmlContent += "\t\t������֤������: " + sINSUIDCODE + "\\n";
		xmlContent += "  ����������: " + sINSUBIRTHDAY ;
	//	xmlContent += "\t\t������֤����Ч�� : " + sInsGovtTermDate + "\\n";	
		xmlContent += "  �����˵�ַ: " +sINSUADDRESS ;
		xmlContent += "\t�����˵������� : " + sINSUEMAIL + "\\n";	
		xmlContent += "  �������ʱ� " + sINSUZIPCODE ;
	//	xmlContent += "\t\t�����˹�˾�绰: : " + sInsDialNumber1 + "\\n";	
		xmlContent += "  Ͷ���˹̶��绰: " + sINSUPHONE;	
		xmlContent += "\t�������ƶ��绰: " + sINSUMOBILE+ "\\n";
		//xmlContent += "  ��������Ͷ���˹�ϵ: " + sAPPLRELATOINSURED+ "\\n";	
 
 
		xmlContent += "�ġ���������Ϣ\\n";
		if(!(sBNFNAME1==null||sBNFNAME1.equals(""))){
		xmlContent += "1����������Ϣ1\\n";
		xmlContent += "  ����������: " + sBNFNAME1;
		xmlContent += "\t\t\t�������Ա�: " + sBNFSEX1 + "\\n";
		xmlContent += "  ������֤������: " + sBNFIDKIND1 ;
		xmlContent += "\t\t������֤������: " + sBNFIDCODE1 + "\\n";
		//xmlContent += "  ������֤����Ч�� : " + sBnfGovtTermDate1 + "\\n";	
		xmlContent += "  ����������: " + sBNFBIRTHDAY1 ;
		xmlContent += "\t\t������� : " + sBNFPROP1 + "\\n";		
		xmlContent += "  ����˳��: " + sBNFSEQUENCE1+ "\\n";	
		}
		if(!(sBNFNAME2==null||sBNFNAME2.equals(""))){
		xmlContent += "2����������Ϣ2\\n";
		xmlContent += "  ����������: " + sBNFNAME2;
		xmlContent += "\t\t\t�������Ա�: " + sBNFSEX2 + "\\n";
		xmlContent += "  ������֤������: " + sBNFIDKIND2 ;
		xmlContent += "\t\t������֤������: " + sBNFIDCODE2 + "\\n";
	//	xmlContent += "������֤����Ч�� : " + sBnfGovtTermDate2 + "\\n";
		xmlContent += "  ����������: " + sBNFBIRTHDAY2 ;
		xmlContent += "\t\t������� : " + sBNFPROP2 + "\\n";		
		xmlContent += "  ����˳��: " + sBNFSEQUENCE2+ "\\n";
		}
		
		if(!(sBNFNAME3==null||sBNFNAME3.equals(""))){
		xmlContent += "3����������Ϣ3\\n";
		xmlContent += "  ����������: " + sBNFNAME3;
		xmlContent += "\t\t\t�������Ա�: " + sBNFSEX3 + "\\n";
		xmlContent += "  ������֤������: " + sBNFIDKIND3 ;
		xmlContent += "\t\t������֤������: " + sBNFIDCODE3 + "\\n";
		//xmlContent += "������֤����Ч�� : " + sBnfGovtTermDate3 + "\\n";
		xmlContent += "  ����������: " + sBNFBIRTHDAY3 ;
		xmlContent += "\t\t������� : " + sBNFPROP3 + "\\n";		
		xmlContent += "  ����˳��: " + sBNFSEQUENCE3+ "\\n";
		}
		
		xmlContent += "�塢������Ϣ\\n";
		
		xmlContent += "1��������Ϣ\\n";
		xmlContent += "  ���մ���: " + sRISKSRISKCODE;
		xmlContent += "\t\t\t����: " + sRISKSHARE ;
		xmlContent += "\t\t����: " +  sRISKSPREM;  
		xmlContent += "\t\t����: " + sRISKSAMNT + "\\n";  
		xmlContent += "  ������������: " + sRISKSINSUDUETYPE ;
		xmlContent += "  �������� : " + sRISKSINSUDUEDATE + "\\n";		
		xmlContent += "\t\t�ɷ���������: " + sRISKSPAYDUETYPE ;		
		xmlContent += "\t\t�ɷ�����: " + sRISKSPAYDUEDATE + "\\n";	
		 
		if(!(sADDTRISKCODE1==null||sADDTRISKCODE1.equals(""))){
		xmlContent += "2����������Ϣ1\\n";
		xmlContent += "  �����մ���: " + sADDTRISKCODE1;
		xmlContent += "\t\t\t����: " + sADDTSHARE1 ;
		xmlContent += "\t\t����: " + sADDTPREM1 ; 
		xmlContent += "\t\t����: " + sADDTAMNT1 + "\\n";  
		xmlContent += "  ������������: " + sADDTINSUDUETYPE1 ;
		xmlContent += "\t\t�������� : " + sADDTINSUDUEDATE1 ;		
		xmlContent += "\t\t�ɷ���������: " + sADDTPAYTYPE1 ;		
		xmlContent += "\t\t�ɷ�����: " + sADDTPAYDATE1 + "\\n";
		}
		   
		if(!(sADDTRISKCODE2==null||sADDTRISKCODE2.equals(""))){
		xmlContent += "3����������Ϣ2\\n";
		xmlContent += "  �����մ���: " + sADDTRISKCODE2;
		xmlContent += "\t\t\t����: " + sADDTSHARE2 ;
		xmlContent += "\t\t����: " + sADDTPREM2 ; 
		xmlContent += "\t\t����: " + sADDTAMNT2 + "\\n";  
		xmlContent += "  ������������: " + sADDTINSUDUETYPE2 ;
		xmlContent += "\t\t�������� : " + sADDTINSUDUEDATE2 ;		
		xmlContent += "\t\t�ɷ���������: " + sADDTPAYTYPE2 ;		
		xmlContent += "\t\t�ɷ�����: " + sADDTPAYDATE2 + "\\n";
		xmlContent += "\t\t������ʼ����: " + sRISKSBEGINDATE + "\\n";
		xmlContent += "\t\t���ս�������: " + sRISKSENDEDATE + "\\n";
		} 
		
 System.out.println(sBNFIDKIND1+"************************װ��XML************************"+"========"+xmlContent);		 
	   Element mABCB2I = new Element("ABCB2I"); 
 	   ElementLis mHeader = new ElementLis("Header",mABCB2I);	
 	
	 	ElementLis mSerialNo = new ElementLis("SerialNo",sSERIALNO,mHeader);
	 	ElementLis mInsuSerial = new ElementLis("InsuSerial",sINSUSERIAL,mHeader);
	 	ElementLis mTransDate = new ElementLis("TransDate",sTRANSDATE,mHeader);
	 	ElementLis mTransTime = new ElementLis("TransTime",sTRANSTIME,mHeader);
	 	ElementLis mBankCode = new ElementLis("BankCode",null,mHeader);
	 	ElementLis mCorpNo = new ElementLis("CorpNo",sCORPNO,mHeader);
	 	ElementLis mTransCode = new ElementLis("TransCode",sTRANSCODE,mHeader);
	 	ElementLis mTransSide = new ElementLis("TransSide","1",mHeader);
	 	ElementLis mEntrustWay = new ElementLis("EntrustWay",null,mHeader);
	 	ElementLis mProvCode = new ElementLis("ProvCode",sProvCode,mHeader);
	 	ElementLis mBranchNo = new ElementLis("BranchNo",sBRANCHNO,mHeader);
	 	ElementLis mTlid = new ElementLis("Tlid",sTLID,mHeader);

 	ElementLis mApp = new ElementLis("App",mABCB2I);
 	ElementLis mReq = new ElementLis("Req",mApp);
 		if(sTRANSCODE.equals("1002")){
 		ElementLis mAppNo = new ElementLis("AppNo",sAPPNO,mReq);
		ElementLis eSaleStaff = new ElementLis("Reserve2",sTLID,mReq);
 		
 		ElementLis mAppl = new ElementLis("Appl",mReq);
 		
	 		ElementLis mIDKind = new ElementLis("IDKind",sAPPLIDKIND,mAppl);
	 		ElementLis mIDCode = new ElementLis("IDCode",sAPPLIDCODE,mAppl);
	 		ElementLis mBeginDate = new ElementLis("BeginDate",sAPPLBEGINDATE,mAppl);
	 		ElementLis mInvalidDate = new ElementLis("InvalidDate",sAPPLINVALIDDATE,mAppl);
	 		ElementLis mName = new ElementLis("Name",sAPPLNAME,mAppl);
	 		ElementLis mSex = new ElementLis("Sex",sAPPLSEX,mAppl);
	 		ElementLis mBirthday = new ElementLis("Birthday",sAPPLBIRTHDAY,mAppl);
	 		ElementLis mCountry = new ElementLis("Country",sAPPLCOUNTRY,mAppl);
	 		ElementLis mAddress = new ElementLis("Address",sAPPLADDRESS,mAppl);
	 		ElementLis mZone = new ElementLis("Zone",sAPPLZONE,mAppl);
	 		ElementLis mZipCode = new ElementLis("ZipCode",sAPPLZIPCODE,mAppl);
	 		ElementLis mEmail = new ElementLis("Email",sAPPLEMAIL,mAppl);
	 		ElementLis mPhone = new ElementLis("Phone",sAPPLPHONE,mAppl);
	 		ElementLis mMobile = new ElementLis("Mobile",sAPPLMOBILE,mAppl);
	 		ElementLis mFixIncome = new ElementLis("FixIncome",sAPPLFIXINCOME,mAppl);
	 		ElementLis mAnnualIncome = new ElementLis("AnnualIncome",sAPPLANNUALINCOME,mAppl);
	 		ElementLis mJobType = new ElementLis("JobType",sAPPLJOBTYPE,mAppl);
	 		ElementLis mJobCode = new ElementLis("JobCode",sAPPLJOBCODE,mAppl);
	 		ElementLis mNotice = new ElementLis("Notice",sAPPLNOTICE,mAppl);
	 		ElementLis mRelaToInsured = new ElementLis("RelaToInsured",sAPPLRELATOINSURED,mAppl);
	 		ElementLis mCustSource = new ElementLis("CustSource",sAPPLCustSource,mAppl);
 	
 		  ElementLis mInsu = new ElementLis("Insu",mReq);
 		
 			ElementLis cIDKind = new ElementLis("IDKind",sINSUIDKIND,mInsu);
 			ElementLis cIDCode = new ElementLis("IDCode",sINSUIDCODE,mInsu);
 			ElementLis cBeginDate = new ElementLis("BeginDate",sINSUBEGINDATE,mInsu);
 			ElementLis cValidDate = new ElementLis("ValidDate",sINSUINVALIDDATE,mInsu);
 			ElementLis cName = new ElementLis("Name",sINSUNAME,mInsu);
 			ElementLis cSex = new ElementLis("Sex",sINSUSEX,mInsu);
 			ElementLis cBirthday = new ElementLis("Birthday",sINSUBIRTHDAY,mInsu);
 			ElementLis cCountry = new ElementLis("Country",sINSUCOUNTRY,mInsu);
 			ElementLis cAddress = new ElementLis("Address",sINSUADDRESS,mInsu);
 			ElementLis cProv = new ElementLis("Prov",sINSUPROV,mInsu);
 			ElementLis cZipCode = new ElementLis("ZipCode",sINSUZIPCODE,mInsu);
 			ElementLis cEmail = new ElementLis("Email",sINSUEMAIL,mInsu);
 			ElementLis cPhone = new ElementLis("Phone",sINSUPHONE,mInsu);
 			ElementLis cMobile = new ElementLis("Mobile",sINSUMOBILE,mInsu);
 			ElementLis cJobType = new ElementLis("JobType",sINSUJOBTYPE,mInsu);
 			ElementLis cJobCode = new ElementLis("JobCode",sINSUJOBCODE,mInsu);
 			ElementLis cAnnualIncome = new ElementLis("AnnualIncome",sINSUANNUALINCOME,mInsu);
 			ElementLis cIsRiskJob = new ElementLis("IsRiskJob",sINSUISRISKJOB,mInsu);
 			ElementLis cHealthNotice = new ElementLis("HealthNotice",sINSUHEALTHNOTICE,mInsu);
 			ElementLis cNotice = new ElementLis("Notice",sINSUNOTICE,mInsu);
 	
 	//������
 		   ElementLis mBnfs = new ElementLis("Bnfs",mReq);
 		   sBNFCOUNT = "0";
 			if (!(sBNFNAME1 == null || sBNFNAME1.equals(""))){
 				sBNFCOUNT = "1";
 			}
 			if (!(sBNFNAME2 == null || sBNFNAME2.equals(""))){
 			     sBNFCOUNT = "2";
 			}
 					if (!(sBNFNAME3 == null || sBNFNAME3.equals(""))){
 			     sBNFCOUNT = "3";
 			}
 			
 			
 			
 			ElementLis bCount = new ElementLis("Count",sBNFCOUNT,mBnfs);
 			ElementLis bType1 = new ElementLis("Type1",sBNFTYPE1,mBnfs);
 			ElementLis bName1 = new ElementLis("Name1",sBNFNAME1,mBnfs);
 			ElementLis bSex1 = new ElementLis("Sex1",sBNFSEX1,mBnfs);
 			ElementLis bBirthday1 = new ElementLis("Birthday1",sBNFBIRTHDAY1,mBnfs);
 			ElementLis bIDCode1 = new ElementLis("IDCode1",sBNFIDCODE1,mBnfs);
 			ElementLis bBeginDate1 = new ElementLis("BeginDate1",null,mBnfs);
 			ElementLis bInvalidDate1 = new ElementLis("InvalidDate1",null,mBnfs);
 			ElementLis bIDKind1 = new ElementLis("IDKind1",sBNFIDKIND1,mBnfs);
 			ElementLis bSequence1 = new ElementLis("Sequence1",sBNFSEQUENCE1,mBnfs);
 			ElementLis bProp1 = new ElementLis("Prop1",sBNFPROP1,mBnfs);
 			ElementLis bPhone1 = new ElementLis("Phone1",null,mBnfs);
 			ElementLis bCountry1 = new ElementLis("Country1",null,mBnfs);
 			ElementLis bGetAccNo1 = new ElementLis("GetAccNo1",null,mBnfs);
 			ElementLis bAddress1 = new ElementLis("Address1",null,mBnfs);
 			ElementLis bProv1 = new ElementLis("Prov1",null,mBnfs);
 			ElementLis bCity1 = new ElementLis("City1",null,mBnfs);
 			ElementLis bZone1 = new ElementLis("Zone1",null,mBnfs);
 			ElementLis bsBNFINSUREDREF1=new ElementLis("RelationToInsured1",sBNFINSUREDREF1,mBnfs);
 			
 			ElementLis bType2 = new ElementLis("Type2",sBNFTYPE2,mBnfs);
 			ElementLis bName2 = new ElementLis("Name2",sBNFNAME2,mBnfs);
 			ElementLis bSex2 = new ElementLis("Sex2",sBNFSEX2,mBnfs);
 			ElementLis bBirthday2 = new ElementLis("Birthday2",sBNFBIRTHDAY2,mBnfs);
 			ElementLis bIDCode2 = new ElementLis("IDCode2",sBNFIDCODE2,mBnfs);
 			ElementLis bBeginDate2 = new ElementLis("BeginDate2",null,mBnfs);
 			ElementLis bInvalidDate2 = new ElementLis("InvalidDate2",null,mBnfs);
 			ElementLis bIDKind2 = new ElementLis("IDKind2",sBNFIDKIND2,mBnfs);
 			ElementLis bSequence2 = new ElementLis("Sequence2",sBNFSEQUENCE2,mBnfs);
 			ElementLis bProp2 = new ElementLis("Prop2",sBNFPROP2,mBnfs);
 			ElementLis bPhone2 = new ElementLis("Phone2",null,mBnfs);
 			ElementLis bCountry2 = new ElementLis("Country2",null,mBnfs);
 			ElementLis bGetAccNo2 = new ElementLis("GetAccNo2",null,mBnfs);
 			ElementLis bAddress2 = new ElementLis("Address2",null,mBnfs);
 			ElementLis bProv2 = new ElementLis("Prov2",null,mBnfs);
 			ElementLis bCity2 = new ElementLis("City2",null,mBnfs);
 			ElementLis bZone2 = new ElementLis("Zone2",null,mBnfs);
 			ElementLis bsBNFINSUREDREF2=new ElementLis("RelationToInsured2",sBNFINSUREDREF2,mBnfs);
 			
 			ElementLis bType3 = new ElementLis("Type3",sBNFTYPE3,mBnfs);
 			ElementLis bName3 = new ElementLis("Name3",sBNFNAME3,mBnfs);
 			ElementLis bSex3 = new ElementLis("Sex3",sBNFSEX3,mBnfs);
 			ElementLis bBirthday3 = new ElementLis("Birthday3",sBNFBIRTHDAY3,mBnfs);
 			ElementLis bIDCode3 = new ElementLis("IDCode3",sBNFIDCODE3,mBnfs);
 			ElementLis bBeginDate3 = new ElementLis("BeginDate3",null,mBnfs);
 			ElementLis bInvalidDate3 = new ElementLis("InvalidDate3",null,mBnfs);
 			ElementLis bIDKind3 = new ElementLis("IDKind3",sBNFIDKIND3,mBnfs);
 			ElementLis bSequence3 = new ElementLis("Sequence3",sBNFSEQUENCE3,mBnfs);
 			ElementLis bProp3 = new ElementLis("Prop3",sBNFPROP3,mBnfs);
 			ElementLis bPhone3 = new ElementLis("Phone3",null,mBnfs);
 			ElementLis bCountry3 = new ElementLis("Country3",null,mBnfs);
 			ElementLis bGetAccNo3 = new ElementLis("GetAccNo3",null,mBnfs);
 			ElementLis bAddress3 = new ElementLis("Address3",null,mBnfs);
 			ElementLis bProv3 = new ElementLis("Prov3",null,mBnfs);
 			ElementLis bCity3 = new ElementLis("City3",null,mBnfs);
 			ElementLis bZone3 = new ElementLis("Zone3",null,mBnfs);
 			ElementLis bsBNFINSUREDREF3=new ElementLis("RelationToInsured3",sBNFINSUREDREF3,mBnfs);
 			
 		//����
 		ElementLis mLoan = new ElementLis("Loan",mReq);
 			
 			ElementLis nContNo = new ElementLis("ContNo",sLOANCONTRACTNO,mLoan);
 			ElementLis nLoanBank = new ElementLis("LoanBank",sLOANCOM,mLoan);
 			ElementLis nVchCheckNo = new ElementLis("VchCheckNo",null,mLoan);
 			ElementLis nFlag = new ElementLis("Flag",null,mLoan);
 			ElementLis nHurtAmnt = new ElementLis("HurtAmnt",null,mLoan);
 			ElementLis nVchNo = new ElementLis("VchNo",null,mLoan);
 			ElementLis nAccNo = new ElementLis("AccNo",null,mLoan);
 			ElementLis nPrem = new ElementLis("Prem",sLOANCONTRACTAMT,mLoan);	
 			ElementLis nBegDate = new ElementLis("BegDate",sLOANSTARTDATE,mLoan);
 			ElementLis nEndDate = new ElementLis("EndDate",sLOANENDDATE,mLoan);
 			ElementLis nLoadType = new ElementLis("LoanType",sLOANTYPE,mLoan);
 	 			
 			
 		//����
 		ElementLis mRisks = new ElementLis("Risks",mReq);
 			
 			ElementLis rCount = new ElementLis("Count","1",mRisks);
 			ElementLis rRiskCode = new ElementLis("RiskCode",sRISKSRISKCODE,mRisks);
 			ElementLis rName = new ElementLis("Name",null,mRisks);
 			ElementLis rPolicypwd = new ElementLis("Policypwd",null,mRisks);
 			ElementLis rShare = new ElementLis("Share",sRISKSHARE,mRisks);
 			ElementLis rPrem = new ElementLis("Prem",sRISKSPREM,mRisks);
 			ElementLis rPrice = new ElementLis("Price",null,mRisks);
 			ElementLis rAmnt = new ElementLis("Amnt",sRISKSAMNT,mRisks);
 			ElementLis rPayType = new ElementLis("PayType",sRISKSPAYTYPE,mRisks);
 			ElementLis rPayDueType = new ElementLis("PayDueType",sRISKSPAYDUETYPE,mRisks);
 			ElementLis rPayDueDate = new ElementLis("PayDueDate",sRISKSPAYDUEDATE,mRisks);
 			ElementLis rInsuDueType = new ElementLis("InsuDueType",sRISKSINSUDUETYPE,mRisks);
 			ElementLis rInsuDueDate = new ElementLis("InsuDueDate",sRISKSINSUDUEDATE,mRisks);
 			ElementLis rBonusGetMode = new ElementLis("BonusGetMode",sBonusGetMode,mRisks);
 			ElementLis rRiskBeginDate = new ElementLis("RiskBeginDate",sRISKSBEGINDATE,mRisks);
 			ElementLis rRiskEndDate = new ElementLis("RiskEndDate",sRISKSENDEDATE,mRisks);
 			
 			
 		//������
 		ElementLis mAddt = new ElementLis("Addt",mReq);
 			
 			ElementLis aCount = new ElementLis("Count",sADDTCOUNT,mAddt);
 			ElementLis aRiskCode1 = new ElementLis("RiskCode1",sADDTRISKCODE1,mAddt);
 			ElementLis aName1 = new ElementLis("Name1",null,mAddt);
 			ElementLis aShare1 = new ElementLis("Share1",sADDTSHARE1,mAddt);
 			ElementLis aAutoPayFlag1 = new ElementLis("AutoPayFlag1",null,mAddt);
 			ElementLis aPrem1 = new ElementLis("Prem1",sADDTPREM1,mAddt);
 			ElementLis aAmnt1 = new ElementLis("Amnt1",sADDTAMNT1,mAddt);
 			ElementLis aPayType1 = new ElementLis("PayType1",sADDTPAYTYPE1,mAddt);
 			ElementLis aPayDueDate1 = new ElementLis("PayDueDate1",sADDTPAYDATE1,mAddt);
 			ElementLis aInsuDueType1 = new ElementLis("InsuDueType1",sADDTINSUDUETYPE1,mAddt);
 			ElementLis aInsuDueDate1 = new ElementLis("InsuDueDate1",sADDTINSUDUEDATE1,mAddt);
 			
 			ElementLis aRiskCode2 = new ElementLis("RiskCode2",sADDTRISKCODE2,mAddt);
 			ElementLis aName2 = new ElementLis("Name2",null,mAddt);
 			ElementLis aShare2 = new ElementLis("Share2",sADDTSHARE2,mAddt);
 			ElementLis aAutoPayFlag2 = new ElementLis("AutoPayFlag2",null,mAddt);
 			ElementLis aPrem2 = new ElementLis("Prem2",sADDTPREM2,mAddt);
 			ElementLis aAmnt2 = new ElementLis("Amnt2",sADDTAMNT2,mAddt);
 			ElementLis aPayType2 = new ElementLis("PayType2",sADDTPAYTYPE2,mAddt);
 			ElementLis aPayDueDate2 = new ElementLis("PayDueDate2",sADDTPAYDATE2,mAddt);
 			ElementLis aInsuDueType2 = new ElementLis("InsuDueType2",sADDTINSUDUETYPE2,mAddt);
 			ElementLis aInsuDueDate2 = new ElementLis("InsuDueDate2",sADDTINSUDUEDATE2,mAddt);
 			
 			
 		ElementLis mBase = new ElementLis("Base",mReq);
 			
 			ElementLis eSpecArranged = new ElementLis("SpecArranged",sSPECARRANGED,mBase);
 			ElementLis eArugeFlag = new ElementLis("ArugeFlag",sARUGEFLAG,mBase);
 			ElementLis eArbteName = new ElementLis("ArbteName",sARBTENAME,mBase);
 			ElementLis eConAccName = new ElementLis("ConAccName",sCONACCNAME,mBase);
 			ElementLis eConAccNo = new ElementLis("ConAccNo",sCONACCNO,mBase);
 			ElementLis eApplGetAccNo = new ElementLis("ApplGetAccNo",null,mBase);
 			ElementLis eInsuGetAccNo = new ElementLis("InsuGetAccNo",null,mBase);
 			ElementLis eApplyDate = new ElementLis("ApplyDate",sAPPLYDATE,mBase);
 			ElementLis ePolicyApplySerial = new ElementLis("PolicyApplySerial",sPOLICYAPPLYSERIAL,mBase);
 			ElementLis eVchType = new ElementLis("VchType",sVCHTYPE,mBase);
 			ElementLis eVchNo = new ElementLis("VchNo",sVCHNO,mBase);
 			ElementLis eSaler = new ElementLis("Saler",sSALER,mBase);
 			ElementLis eSalerCertNo = new ElementLis("SalerCertNo",sSALERCERTNO,mBase);
 			ElementLis eBranchCertNo = new ElementLis("BranchCertNo",sBRANCHCERTNO,mBase);
 			ElementLis eBranchName = new ElementLis("BranchName",sBRANCHCERTNO,mBase);

 	}else if(sTRANSCODE.equals("1004")){
 	    String PayAmtStr = request.getParameter("PayAmt");
 	    String sAPPNOSer = request.getParameter("APPNO");
 	    String sPayAccount = request.getParameter("PayAccount");
 		ElementLis ProvCode = new ElementLis("ProvCode",sProvCode,mReq);
 		ElementLis BranchNo = new ElementLis("BranchNo",sBRANCHNO,mReq);
 		ElementLis ApplySerial = new ElementLis("ApplySerial",sAPPNOSer,mReq);
 		ElementLis PayAmt = new ElementLis("PayAmt",PayAmtStr,mReq);
 		ElementLis PayAccount = new ElementLis("PayAccount",sPayAccount,mReq);
 		ElementLis RiskCode = new ElementLis("RiskCode",null,mReq);
 		ElementLis AccName = new ElementLis("AccName",null,mReq);
 		ElementLis PolicyNo = new ElementLis("PolicyNo",sPOLICYAPPLYSERIAL,mReq);
 	}
		//JdomUtil.print(mABCB2I);
		String mABCB2IS=JdomUtil.toString(mABCB2I);
		mABCB2IS=mABCB2IS.substring(mABCB2IS.indexOf("<ABCB2I>"));
		System.out.println("-----------------------------------------------"+mABCB2IS);
		
		int iPort = Integer.valueOf(sPort).intValue();
		NewABCTest test = new NewABCTest(sIp, iPort); 
        byte[] mABCB2IB=mABCB2IS.getBytes();
		Document mOutXmlDoc    = test.sendRequest(sTRANSCODE,mABCB2IB);
//		InputStream is=new ByteArrayInputStream(mABCB2IB);
//		Document mOutXmlDoc    = test.sendRequest("1002",is);
		ResultCode =  mOutXmlDoc.getRootElement().getChild("Header").getChildTextTrim("RetCode");
		ResultInfoDesc =mOutXmlDoc.getRootElement().getChild("Header").getChildTextTrim("RetMsg");
		PubFun1 pubfun = new PubFun1();
		System.out.println(ResultCode + "  " + ResultInfoDesc);


        System.out.println("�������ݣ�"+ResultInfoDesc);
		if (!(ResultCode.equals("000000"))) {
	        Content = "����ʧ�ܣ�" + ResultInfoDesc ;
	        ResultInfoDesc = ResultInfoDesc.replace("%","");
			
	      
		} else {
		 
		
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
   parent.fraInterface.fm.xmlContent.value = '<%=xmlContent.toString()%>'; 
   //parent.fraInterface.fm.ContNo.value = 'strContNo';
</script>