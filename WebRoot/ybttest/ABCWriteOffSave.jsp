<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.utility.ElementLis"%>
<%@page import="test.YD_ABCTestUI"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun1"%> 
<%@page import="java.io.InputStream"%>
<%@page import="org.jdom.Document"%> 
<%@page import="com.sinosoft.midplat.common.DateUtil"%>
<%@page import="com.sinosoft.midplat.common.JdomUtil"%>
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
	//Ͷ�����
	//String sHOAppFormNumber = request.getParameter("HOAppFormNumber");
	//������ 
	String sPolNumber = request.getParameter("PolNumber");
	//��֤�� 
	String sProviderFormNumber = request.getParameter("ProviderFormNumber");
	//ԭ��֤�� 
	String sOriginalProviderFormNumber = request.getParameter("OriginalProviderFormNumber");
//Ͷ������ 
	String sAPPDOCNO = request.getParameter("APPDOCNO");
	String sPrem = request.getParameter("Prem");
	
	
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
		xmlContent += "\t\t\tͶ������: " + sTransRefGUID + "\\n"; 
		xmlContent += "\t������: " + sPolNumber + "\\n";
		xmlContent += "  �µ�֤ӡˢ��: " + sProviderFormNumber;
		xmlContent += "\tԭ��֤ӡˢ��: " + sOriginalProviderFormNumber + "\\n";
		
 System.out.println("************************װ��XML************************");		 

 ElementLis Req = new ElementLis("Req");	 
 ElementLis TransExeDate = new ElementLis("BankDate",sTransExeDate,Req); 
 ElementLis BankCode = new ElementLis("BankCode","05",Req);  
 ElementLis ZoneNo = new ElementLis("ZoneNo",sRegionCode,Req);  
	ElementLis NodeNo = new ElementLis("BrNo",sBranch,Req);  
  
  ElementLis TellerNo = new ElementLis("TellerNo",sTeller,Req); 
  ElementLis TransRefGUID = new ElementLis("TransrNo",sTransRefGUID,Req); 
  ElementLis FunctionFlag = new ElementLis("FunctionFlag",sTransNo,Req); 
  ElementLis InsuID = new ElementLis("InsuID",Req);
  
	  
	
	
		if(sTransNo.equals("1011")){
		ElementLis NewContPrtNo = new ElementLis("NewContPrtNo",sProviderFormNumber,Req);
		}
		if(sTransNo.equals("03")){
		 ElementLis Base = new ElementLis("Base",Req); 
		 ElementLis ContNo = new ElementLis("ContNo",sPolNumber,Base); 
		 ElementLis ProposalContNo = new ElementLis("ProposalContNo",sAPPDOCNO,Base); 
		 ElementLis Prem = new ElementLis("Prem",sPrem,Base); 
		}
		if(sTransNo.equals("04")){
		 ElementLis ConfirmInfo = new ElementLis("ConfirmInfo",Req); 
         ElementLis ContNo = new ElementLis("ContNo",sPolNumber,ConfirmInfo); 
         ElementLis OldTranNo = new ElementLis("OldTranNo",ConfirmInfo);
		}
		String ip = request.getParameter("ip");
		String port = request.getParameter("port");
		System.out.println("������" + ip + "�˿�:" + Integer.valueOf(port));
		
		
	
				 //.append("1").append('/');
				 //.append(DateUtil.getCurDate("yyyy/yyyyMM/yyyyMMdd/"));
		//String mInFilePath = mFilePath.toString()+"/RetXMLIn.xml";		
		//String mOutFilePath = mFilePath.toString()+"/RetXMLReturn.xml";
		 
//System.out.println("************************"+mInFilePath+"************************");   
//System.out.println("************************"+mOutFilePath+"************************");  

//System.out.println("************************װ��Document************************");	
   
 			Document pXmlDoc = new Document(Req);	
//System.out.println("************************1************************");	
	//		FileOutputStream tFos = new FileOutputStream(mInFilePath); 
//System.out.println("************************2************************");	
		//	JdomUtil.output(pXmlDoc, tFos,"GBK");
//System.out.println("************************3************************");		
			    
		
		int iPort = Integer.valueOf(sPort).intValue();
		YD_ABCTestUI mTestUI = new YD_ABCTestUI(sIp,iPort);  
//		InputStream mIs = new FileInputStream(mInFilePath);
	//	byte[] mOutBytes = mTestUI.sendRequest(sFlag, mIs);
		byte[] mOutBytes = mTestUI.sendRequestUI(sTransNo, pXmlDoc);
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


		 
		ResultCode =  mOutXmlDoc.getRootElement().getChild("RetData").getChildTextTrim("Flag");
		ResultInfoDesc =mOutXmlDoc.getRootElement().getChild("RetData").getChildTextTrim("Desc");

System.out.println(ResultCode + "  " + ResultInfoDesc);
		strNewTransNo = PubFun1.CreateMaxNo("TransNo",16);
	//	System.out.println("strNewTransNo = " + strNewTransNo);
        System.out.println("�������ݣ�"+ResultInfoDesc);
		if (ResultCode.equals("0")) { // fail
	        Content = "����ʧ�ܣ�" + ResultInfoDesc ;
	    
            //jsp��String�ϲ��ܳ���"%",��������ʾ����
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
   parent.fraInterface.fm.TransRefGUID.value = '<%=strNewTransNo%>';

   parent.fraInterface.fm.xmlContent.value = '<%=xmlContent.toString()%>'; 
   //parent.fraInterface.fm.ContNo.value = 'strContNo';
</script>
