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
<%@page import="com.sinosoft.midplat.ccb.CcbConf"%>
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
	//�˱�������Ϣ����
	String sTransCancelDate = request.getParameter("TransCancelDate").trim();
	sTransCancelDate=DateUtil.date10to8(sTransCancelDate);
	//���н���ʱ��    
	String sTransExeTime = DateUtil.getCur8Time(); 
	sTransExeTime=DateUtil.time8to6(sTransExeTime);
	//������ˮ��
	String sTransRefGUID = request.getParameter("TransRefGUID");
	//ԭ������ˮ��
	String sOldTransRefGUID = request.getParameter("OldTransRefGUID");
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
	
		System.out.println("sProviderFormNumber"+sProviderFormNumber);
	//if(!sProviderFormNumber.equals("")){
	//	sProviderFormNumber=CheckProlNo.ReturnCheck(sProviderFormNumber);
	//	}
	System.out.println("1111111");
	
	
	//ԭ��֤�� 
	//String sOriginalProviderFormNumber = request.getParameter("OriginalProviderFormNumber");
	//if(sProviderFormNumber!=null){
	//sOriginalProviderFormNumber=CheckProlNo.ReturnCheck(sOriginalProviderFormNumber);
	//}
	//���ִ���
	String sProductCode = request.getParameter("ProductCode");
	//�̵Ʋ���ʹ��
	String sBkDetail2 = request.getParameter("BkDetail2");
	String sBkDetail3 = request.getParameter("BkDetail3");
	//�ؿպ˶�ʹ��
	String sCheckCode = request.getParameter("CheckCode");
	String sBkVchNo1 = request.getParameter("BkVchNo1");
	String sBkVchNo2 = request.getParameter("BkVchNo2");
	//ǩԼ��Լʹ��
	String sContCodeNo= request.getParameter("ContCodeNo");
	String sFSType = request.getParameter("FSType");
	String sClientName= request.getParameter("ClientName");
	String sClientAccNo = request.getParameter("ClientAccNo");
	String sMessPrtNo= request.getParameter("MessPrtNo");
	//	��������	
	String sPrem = request.getParameter("Prem");
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
 String InsuId = CcbConf.newInstance().getConf().getRootElement().getChild("bank").getAttributeValue("insu");
 	ElementLis Transaction = new ElementLis("Transaction");	 
 	ElementLis Transaction_Header = new ElementLis("Transaction_Header",Transaction); 
 	ElementLis LiBankID = new ElementLis("LiBankID","CCB",Transaction_Header);  
//���չ�˾����
	 ElementLis PbInsuId = new ElementLis("PbInsuId",InsuId,Transaction_Header);  
//������ˮ��
		ElementLis BkPlatSeqNo = new ElementLis("BkPlatSeqNo",sTransRefGUID,Transaction_Header); 

		ElementLis BkTxCode = new ElementLis("BkTxCode",sTransNo,Transaction_Header);
		 //������������
		 ElementLis BkChnlNo = new ElementLis("BkChnlNo","1",Transaction_Header);  
		ElementLis ZoneNo = new ElementLis("ZoneNo",sRegionCode,Transaction_Header);
		ElementLis BkBrchNo = new ElementLis("BkBrchNo",sBranch,Transaction_Header);
		ElementLis BkTellerNo = new ElementLis("BkTellerNo",sTeller,Transaction_Header);
		ElementLis BkPlatDate = new ElementLis("BkPlatDate",sTransExeDate,Transaction_Header); 
		ElementLis BkPlatTime = new ElementLis("BkPlatTime",sTransExeTime,Transaction_Header);	
		 ElementLis Transaction_Body = new ElementLis("Transaction_Body",Transaction);  
		if(sTransNo.equals("OPR911")){//���ճ���
			ElementLis PbInsuType = new ElementLis("PbInsuType",sProductCode,Transaction_Body);
			ElementLis PbInsuSlipNo = new ElementLis("PbInsuSlipNo",sPolNumber,Transaction_Body); 
			ElementLis BkOthOldSeq = new ElementLis("BkOthOldSeq",sOldTransRefGUID,Transaction_Body); 
			ElementLis LiInsuSlipPWD = new ElementLis("LiInsuSlipPWD","",Transaction_Body);
			ElementLis BkTotAmt = new ElementLis("BkTotAmt",sPrem,Transaction_Body);
		}else if(sTransNo.equals("SPE802")){//���ճ���
			ElementLis BkOldSeq = new ElementLis("BkOldSeq",sOldTransRefGUID,Transaction_Body);
			ElementLis BkOthTxCode = new ElementLis("BkOthTxCode","",Transaction_Body);
		}else if(sTransNo.equals("SPE002")){//�����ش�
			ElementLis PbInsuType = new ElementLis("PbInsuType",sProductCode,Transaction_Body);
			ElementLis PbInsuSlipNo = new ElementLis("PbInsuSlipNo",sPolNumber,Transaction_Body); 
			ElementLis BkOthOldSeq = new ElementLis("BkOthOldSeq",sOldTransRefGUID,Transaction_Body);
			ElementLis BkVchNo = new ElementLis("BkVchNo",sProviderFormNumber,Transaction_Body); 
			ElementLis PiFlagVchNo = new ElementLis("PiFlagVchNo","",Transaction_Body);
			ElementLis PiTrfVchNo = new ElementLis("PiTrfVchNo",sPrem,Transaction_Body);
		}else if(sTransNo.equals("OPR999")){//�̵ƽ���
			ElementLis BkDetail1 = new ElementLis("BkDetail1",sBkDetail2,Transaction_Body);
			ElementLis BkDetail2 = new ElementLis("BkDetail2",sBkDetail3,Transaction_Body);
		}else if(sTransNo.equals("VCH102")){//�ؿս���
			ElementLis BkType1 = new ElementLis("BkType1",sCheckCode,Transaction_Body);
			ElementLis BkVchNo1 = new ElementLis("BkVchNo1",sBkVchNo1,Transaction_Body);
			ElementLis BkVchNo2 = new ElementLis("BkVchNo2",sBkVchNo2,Transaction_Body);
		}else if(sTransNo.equals("SPE801")){//�˱�������Ϣ��ѯ
			ElementLis PbBatChgBegDate = new ElementLis("PbBatChgBegDate",sTransCancelDate,Transaction_Body);
		}else if(sTransNo.equals("SPE010")){//ǩԼ����
			ElementLis PbInsuSlipNo = new ElementLis("PbInsuSlipNo",sContCodeNo,Transaction_Body);
			ElementLis PbHoldName = new ElementLis("PbHoldName",sClientName,Transaction_Body);
			ElementLis BkAcctNo1 = new ElementLis("BkAcctNo1",sClientAccNo,Transaction_Body);
			ElementLis BkTxType = new ElementLis("BkTxType",sFSType,Transaction_Body);
			ElementLis LiRatifyPrtId = new ElementLis("LiRatifyPrtId",sMessPrtNo,Transaction_Body);
		}else if(sTransNo.equals("SPE013")){//��Լ����
			ElementLis PbInsuSlipNo = new ElementLis("PbInsuSlipNo",sContCodeNo,Transaction_Body);
			ElementLis PbHoldName = new ElementLis("PbHoldName",sClientName,Transaction_Body);
			ElementLis BkTxType = new ElementLis("BkTxType",sFSType,Transaction_Body);
			ElementLis LiRatifyPrtId = new ElementLis("LiRatifyPrtId",sMessPrtNo,Transaction_Body);
		}else if(sTransNo.equals("BAT900")){//�������ײ�ѯ
			
		}
		
		String ip = request.getParameter("ip");
		String port = request.getParameter("port");
		System.out.println("������" + ip + "�˿�:" + Integer.valueOf(port).intValue());
	
//System.out.println("************************װ��Document************************");	
 			Document pXmlDoc = new Document(Transaction);	
 			JdomUtil.print(pXmlDoc);
		int iPort = Integer.valueOf(sPort).intValue();
		CCBTestUI mTestUI = new CCBTestUI(sIp,iPort); 
//		InputStream mIs = new FileInputStream(mInFilePath);
	//	byte[] mOutBytes = mTestUI.sendRequest(sFlag, mIs);
	byte[] mOutBytes=null;
	InputStream inputStream = new ByteArrayInputStream(JdomUtil.toBytes(pXmlDoc));
		 mOutBytes = mTestUI.sendRequest(inputStream);
System.out.println("************************4************************");		 
		Document mOutXmlDoc = JdomUtil.build(mOutBytes);  
		//JdomUtil.print(mOutXmlDoc);
 
		//��ӡ�������� 
		//uiPrint = JdomUtil.toStringFmt(pXmlDoc);
		//System.out.println(uiPrint);		
	//	mFos.flush();  
	//	mFos.close(); 
System.out.println("*****************3");
ResultCode = mOutXmlDoc.getRootElement().getChild("Transaction_Header").getChild("Tran_Response").getChildTextTrim("BkOthRetCode");
ResultInfoDesc = mOutXmlDoc.getRootElement().getChild("Transaction_Header").getChild("Tran_Response").getChildTextTrim("BkOthRetMsg");
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
		       Content = ResultInfoDesc + " �����ǣ�" + sPrem+"Ԫ ";
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
