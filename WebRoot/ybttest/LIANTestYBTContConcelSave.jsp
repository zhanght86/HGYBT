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
<%@page import="test.*"%>
<%@page import="test.*"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%@page import="org.jdom.output.*"%>
<%@page import="org.jdom.input.*"%>
<%@page import="org.jdom.Attribute"%> 
<jsp:directive.page import="java.util.HashMap"/> 
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
 System.out.println("****************���ж���Ϣ1*******************���ж���Ϣ1*******************");
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
	//����ӡˢ��
	//String sProviderFormNumber = request.getParameter("ProviderFormNumber");
	//������ˮ��
	//String sReqsrNo = request.getParameter("ReqsrNo");
	//Ͷ������
	String sProposalContNo= request.getParameter("ProposalContNo");
	//������
	String sContNo = request.getParameter("ContNo");
	//���ױ�־
	String sFunctionFlag = "03";
	
System.out.println("****************ip-port-flag*******************ip-port-flag*******************");	
String sIp = request.getParameter("ip");
String sPort = request.getParameter("port");
String sFlag = request.getParameter("tranFlagCode");

 


//�ѱ�����Ϣ��ҳ����ʾ
		xmlContent = new String(""); 
   
		xmlContent += "һ��������Ϣ\\n";
		xmlContent += "  ���ж�����: " + sBankDate;
		xmlContent += "\t\t������ˮ��: " + sTransrNo + "\\n";
		xmlContent += "  ��������: " + sZoneNo; 
		xmlContent += "\t\t\t�������: " + sBrNo + "\\n"; 
		xmlContent += "  ��Ա����: " + sTellerNo;
		xmlContent += "\t\t\tͶ������: " + sProposalContNo + "\\n"; 
		xmlContent += "  ������: " + sContNo + "\\n";

	


		
 System.out.println("************************װ��XML************************");		 
 ElementLis Req = new ElementLis("Req");	
 	ElementLis BankDate = new ElementLis("BankDate",DateUtil.date10to8(sBankDate), Req);
 	ElementLis BankTime = new ElementLis("BankTime", Req);
 	ElementLis TransrNo = new ElementLis("TransrNo",sTransrNo, Req);
 	ElementLis ZoneNo = new ElementLis("ZoneNo",sZoneNo, Req);
 	ElementLis BrNo = new ElementLis("BrNo",sBrNo, Req);
 	ElementLis TellerNo = new ElementLis("TellerNo",sTellerNo, Req);
 	ElementLis FunctionFlag = new ElementLis("FunctionFlag",sFunctionFlag, Req);
 	
 	ElementLis Base = new ElementLis("Base",Req);
 		ElementLis ProposalContNo = new ElementLis("ProposalContNo",sProposalContNo, Base);
 		ElementLis ContNo = new ElementLis("ContNo",sContNo, Base);
 		ElementLis Password = new ElementLis("Password", Base);
 		ElementLis Prem = new ElementLis("Prem", Base);

 
 
 
 
 
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
   
 			Document pXmlDoc = new Document(Req);	
//System.out.println("************************1************************");	
	//		FileOutputStream tFos = new FileOutputStream(mInFilePath); 
//System.out.println("************************2************************");	
			//JdomUtil.output(pXmlDoc, tFos,"GBK");
//System.out.println("************************3************************");
		int iPort = Integer.valueOf(sPort).intValue();
		YD_ABCTestUI mTestUI = new YD_ABCTestUI(sIp,iPort);  
//		InputStream mIs = new FileInputStream(mInFilePath);
	//	byte[] mOutBytes = mTestUI.sendRequest(sFlag, mIs);
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


		 
		ResultFlag = mOutXmlDoc.getRootElement().getChild("TXLifeResponse").getChild("TransResult").getChildTextTrim("ResultCode");
		ResultMesg = mOutXmlDoc.getRootElement().getChild("TXLifeResponse").getChild("TransResult").getChild("ResultInfo").getChildTextTrim("ResultInfoDesc");

System.out.println(ResultFlag + "  " + ResultMesg);
		strNewTransNo = PubFun1.CreateMaxNo("TransNo",16);
	//	System.out.println("strNewTransNo = " + strNewTransNo);
        System.out.println("�������ݣ�"+ResultMesg);
		if (ResultFlag.equals("1234")) { // fail
	        Content = "����ʧ�ܣ�" + ResultMesg;
	     
            //jsp��String�ϲ��ܳ���"%",��������ʾ����
	       ResultMesg = ResultMesg.replace("%","");
	      
		} else { 
	      
	       System.out.println("��������2��"+Content);

	       if (sFlag.trim().equals("1013")) {
		      // rPrem = mOutXmlDoc.getRootElement().getChild("K_BI").getChild("Info").getChildTextTrim("Premium");
		       //RetBaseAmt = result.getRootElement().getChild("K_BI").getChild("Info").getChildTextTrim("BaseAmt");
		     //  int tPLength = RetPrem.length();
		     
		    //   if(tPLength>2){
		    //      RetPrem = RetPrem.substring(0,tPLength-2);
		    //   }
		   
		       Content = ResultMesg + " �����ǣ�" + "RetPrem"+"Ԫ ";
		       }

	        System.out.println("-----------��ʼȡ����saveҳ�棩----------");
	
		   }  

	} catch (Exception e) {
		e.printStackTrace();
		ResultFlag = "Fail";
		xmlContent= e.getMessage();  
		Content = e.getMessage(); 
	}      
%>   
<script language="javascript">
   parent.fraInterface.afterSubmit("<%=ResultFlag%>", "<%=ResultMesg%>");
   parent.fraInterface.fm.TransrNo.value = '<%=strNewTransNo%>';

   parent.fraInterface.fm.xmlContent.value = '<%=xmlContent.toString()%>'; 
   //parent.fraInterface.fm.ContNo.value = 'strContNo';
</script>