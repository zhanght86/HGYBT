<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="test.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.io.*"%>
<%@page import="org.jdom.Document"%>  
<%@page import="org.jdom.Element"%>
<%@page import="java.net.ConnectException"%>
<%@page import="java.util.Calendar"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%@page import="com.sinosoft.midplat.net.*"%>
<%@page import="com.sinosoft.midplat.newabc.NewAbcConf"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%
	System.out.println("--> ins");
	String FlagStr = null;
	String Content = null;
	String strNewTransNo = null;
	String strContNo = null;
	String xmlContent = null;
	String ResultCode = null;

	String ResultInfoDesc = null; 

	String uiPrint = null;   
%>
<%
    InputStream ins = null;
    System.out.println("--> 1");
	try {
	
	 System.out.println("****************���ж���Ϣ1*******************���ж���Ϣ1*******************");
	//���ж˽�������
	String sTransExeDate = request.getParameter("TransExeDate").trim();
	sTransExeDate=DateUtil.date10to8(sTransExeDate);

	//���н���ʱ��    
	String sTransExeTime = DateUtil.getCur8Time(); 
	sTransExeTime=DateUtil.time8to6(sTransExeTime);
	//������ˮ��
	String sTransRefGUID = request.getParameter("TransRefGUID");
	//�����ش�ʱ�õ�ԭ������ˮ��
	String sTransRefGUID2 = request.getParameter("OldTransRefGUID");

	//��������
	String sRegionCode = request.getParameter("RegionCode");
	//�������
	String sBranch = request.getParameter("Branch");
	//��Ա����
	String sTeller = request.getParameter("Teller"); 
	//Ͷ������
	//String sHOAppFormNumber = request.getParameter("HOAppFormNumber");
	//������ 
	String sPolNumber = request.getParameter("PolNumber");
	System.out.println("1111111111111111111111");
	//��֤�� 
	String sProviderFormNumber = request.getParameter("ProviderFormNumber");
	//ԭ��֤��  �ش�
	String sProviderFormNumber2 = request.getParameter("ProviderFormNumber2");
	//Ͷ������  �ش�
	String sProposalPrtNo = request.getParameter("ProposalPrtNo");
	//Ͷ��������
	String sApptName = request.getParameter("ApptName");
	
		System.out.println("sProviderFormNumber"+sProviderFormNumber);
	//if(!sProviderFormNumber.equals("")){
	//	sProviderFormNumber=CheckProlNo.ReturnCheck(sProviderFormNumber);
	//	}
	System.out.println("1111111");

	//�Ա�
	String sAppGender = request.getParameter("Gender");
	
	//ԭ��֤�� 
	//String sOriginalProviderFormNumber = request.getParameter("OriginalProviderFormNumber");
	//if(sProviderFormNumber!=null){
	//sOriginalProviderFormNumber=CheckProlNo.ReturnCheck(sOriginalProviderFormNumber);
	//}
	//���ִ���
	String sProductCode = request.getParameter("ProductCode");

	//�Ա�
	String sGender = request.getParameter("Gender");
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
	//�ɷ���������
	String sPaymentDuration = request.getParameter("PaymentDuration");
	//���㱣�ѽɷ����ڴ���
	String sPaymentMode = request.getParameter("PaymentMode");
	//��������ѯ
	String sApplyNo = request.getParameter("ApplyNo");
	//�̵Ʋ���ʹ��
	String sReserve = request.getParameter("Reserve");

	//	��������	
	String sReturnAmnt = request.getParameter("ReturnAmnt");
	//	ԭ�������� ����	
	String sOrgTransDate = request.getParameter("OrgTransDate");
	sOrgTransDate=DateUtil.time8to6(sOrgTransDate);
	//	ԭ���ױ��� ����	
	String sTransCode = request.getParameter("tranCode");
	//	�ɷѽ�� ����	
	String sPayAmt = request.getParameter("PayAmt");
	//	�ɷ��ʺ� ����	
	String sPayAcc = request.getParameter("PayAcc");
	System.out.println("222222222222222222222");
	
	String sIp = request.getParameter("ip");
	String sPort = request.getParameter("port");
		//���ױ�־
	String sTransNo = request.getParameter("tranFlagCode");
	
		//�ѱ�����Ϣ��ҳ����ʾ
		xmlContent = new String("");
		xmlContent += "һ��������Ϣ\\n";
		xmlContent += "  ���ж�����: " + sTransExeDate;
		xmlContent += "\t\t������ˮ��: " + sTransRefGUID + "\\n";
		xmlContent += "  ��������: " + sRegionCode; 
		xmlContent += "\t\t\t�������: " + sBranch + "\\n"; 
		xmlContent += "  ��Ա����: " + sTeller; 
		xmlContent += "\t\t\tͶ������: " + sTransExeDate + "\\n"; 
		xmlContent += "\t������: " + sPolNumber + "\\n";
		xmlContent += "  ��֤ӡˢ��: " + sProviderFormNumber+ "\\n";
		//xmlContent += "\tԭ��֤ӡˢ��: " + sOriginalProviderFormNumber ;
		
 System.out.println("************************װ��XML************************");	
 String InsuId = NewAbcConf.newInstance().getConf().getRootElement().getChild("bank").getAttributeValue("insu");
 Element mABCB2I = new Element("ABCB2I"); 
  ElementLis mHeader = new ElementLis("Header",mABCB2I);	

	ElementLis mSerialNo = new ElementLis("SerialNo",sTransRefGUID,mHeader);
	ElementLis mInsuSerial = new ElementLis("InsuSerial",sTransRefGUID,mHeader);
	ElementLis mTransDate = new ElementLis("TransDate",sTransExeDate,mHeader);
	ElementLis mTransTime = new ElementLis("TransTime",sTransExeTime,mHeader);
	ElementLis mBankCode = new ElementLis("BankCode",null,mHeader);
	ElementLis mCorpNo = new ElementLis("CorpNo","",mHeader);
	ElementLis mTransCode = new ElementLis("TransCode",sTransNo,mHeader);
	ElementLis mTransSide = new ElementLis("TransSide","1",mHeader);
	ElementLis mEntrustWay = new ElementLis("EntrustWay",null,mHeader);
	ElementLis mProvCode = new ElementLis("ProvCode",sRegionCode,mHeader);
	ElementLis mBranchNo = new ElementLis("BranchNo",sBranch,mHeader);
	ElementLis mTlid = new ElementLis("Tlid","10010",mHeader);

ElementLis mApp = new ElementLis("App",mABCB2I);
ElementLis mReq = new ElementLis("Req",mApp);
 	 
		if(sTransNo.equals("1010")){//���ճ���
			ElementLis RiskCode = new ElementLis("RiskCode",sProductCode,mReq);
			ElementLis ProdCode = new ElementLis("ProdCode","",mReq);
			ElementLis PolicyNo = new ElementLis("PolicyNo",sPolNumber,mReq); 
			ElementLis VchNo = new ElementLis("VchNo",sProviderFormNumber,mReq); 
			ElementLis ApptName = new ElementLis("TbrName",sApptName,mReq); 
			ElementLis TbrIdKind = new ElementLis("TbrIdKind","",mReq); 
			ElementLis TbrIdCode = new ElementLis("TbrIdCode","",mReq); 
			ElementLis PayAcc = new ElementLis("PayAcc",sPayAcc,mReq);
			ElementLis PayAmt = new ElementLis("PayAmt",sPayAmt,mReq);
			ElementLis OrgSerialNo = new ElementLis("OrgSerialNo",sTransRefGUID2,mReq); 
		}else if(sTransNo.equals("1009")){//�Զ�����
			ElementLis OrgSerialNo = new ElementLis("OrgSerialNo",sTransRefGUID2,mReq);
			ElementLis OrgTransDate = new ElementLis("OrgTransDate",sOrgTransDate,mReq);
			ElementLis TransCode = new ElementLis("TransCode",sTransCode,mReq);
			ElementLis ProdCode = new ElementLis("ProdCode","123456",mReq);
			ElementLis PolicyNo = new ElementLis("PolicyNo",sPolNumber,mReq);
			ElementLis PayAcc = new ElementLis("PayAcc",sPayAcc,mReq);
			ElementLis PayAmt = new ElementLis("PayAmt",sPayAmt,mReq);
		}else if(sTransNo.equals("1018")){//�����ش�
			ElementLis RiskCode = new ElementLis("RiskCode",sProductCode,mReq);
			ElementLis ProposalPrtNo = new ElementLis("PolicyApplyNo",sProposalPrtNo,mReq); 
			ElementLis OldVchNo = new ElementLis("OldVchNo",sProviderFormNumber2,mReq); 
			ElementLis NewVchNo = new ElementLis("NewVchNo",sProviderFormNumber,mReq); 
		}else if(sTransNo.equals("1005")){//��������ѯ
			ElementLis ApplyNo = new ElementLis("ApplyNo",sApplyNo,mReq);
		}else if(sTransNo.equals("1000")){//�̵ƽ���
			ElementLis Reserve = new ElementLis("Reserve",sReserve,mReq);
		}
		
		String ip = request.getParameter("ip");
		String port = request.getParameter("port");
		System.out.println("������" + ip + "�˿�:" + Integer.valueOf(port).intValue());
	
//System.out.println("************************װ��Document************************");	
		String mABCB2IS=JdomUtil.toString(mABCB2I);
		mABCB2IS=mABCB2IS.substring(mABCB2IS.indexOf("<ABCB2I>"));
		System.out.println("-----------------------------------------------"+mABCB2IS);
 			Document pXmlDoc = new Document(mABCB2I);	
 			JdomUtil.print(pXmlDoc);
		int iPort = Integer.valueOf(sPort).intValue();
		NewABCTest mTest = new NewABCTest(sIp,iPort); 
        byte[] mABCB2IB=mABCB2IS.getBytes();
//		InputStream mIs = new FileInputStream(mInFilePath);
	//	byte[] mOutBytes = mTestUI.sendRequest(sFlag, mIs);
//	byte[] mOutBytes=null;
//	InputStream inputStream = new ByteArrayInputStream(JdomUtil.toBytes(pXmlDoc));
		 Document mOutXmlDoc = mTest.sendRequest(sTransNo,mABCB2IB);
System.out.println("************************4************************");		 
//		Document mOutXmlDoc = JdomUtil.build(mOutBytes);  
		//JdomUtil.print(mOutXmlDoc);
 
		//��ӡ�������� 
		//uiPrint = JdomUtil.toStringFmt(pXmlDoc);
		//System.out.println(uiPrint);		
	//	mFos.flush();  
	//	mFos.close(); 
System.out.println("*****************3");
ResultCode = mOutXmlDoc.getRootElement().getChild("Header").getChildTextTrim("RetCode");
ResultInfoDesc = mOutXmlDoc.getRootElement().getChild("Header").getChildTextTrim("RetMsg");
System.out.println(ResultCode + "  " + ResultInfoDesc);
		strNewTransNo = PubFun1.CreateMaxNo("TransNo",16);
	//	System.out.println("strNewTransNo = " + strNewTransNo);
        System.out.println("�������ݣ�"+ResultInfoDesc);
		if (ResultCode.equals("0")) { // fail
	       ResultInfoDesc = ResultInfoDesc.replace("%","%25");
	        Content = "����ʧ�ܣ�" + ResultInfoDesc ;
            //jsp��String�ϲ��ܳ���"%",��������ʾ����
		} else { 
	       System.out.println("��������2��"+Content);
	       if (sTransNo.trim().equals("27")) {
		       Content = ResultInfoDesc + " �����ǣ�" + sReturnAmnt+"Ԫ ";
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
