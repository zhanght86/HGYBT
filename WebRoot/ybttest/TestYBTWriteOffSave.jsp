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
	String sHOAppFormNumber = request.getParameter("HOAppFormNumber");
	//������ 
	String sPolNumber = request.getParameter("PolNumber");
	//��֤�� 
	String sProviderFormNumber = request.getParameter("ProviderFormNumber");
	//ԭ��֤�� 
	String sOriginalProviderFormNumber = request.getParameter("OriginalProviderFormNumber");

	
	
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
		xmlContent += "  Ͷ�����: " + sHOAppFormNumber;
		xmlContent += "\t������: " + sPolNumber + "\\n";
		xmlContent += "  ��֤ӡˢ��: " + sProviderFormNumber;
		xmlContent += "\tԭ��֤ӡˢ��: " + sOriginalProviderFormNumber + "\\n";
		
 System.out.println("************************װ��XML************************");		 

 ElementLis TXLife = new ElementLis("TXLife");	 
	ElementLis TXLifeRequest = new ElementLis("TXLifeRequest",TXLife);  
		ElementLis TransRefGUID = new ElementLis("TransRefGUID",sTransRefGUID,TXLifeRequest);    
		ElementLis TransExeDate = new ElementLis("TransExeDate",sTransExeDate,TXLifeRequest); 
		ElementLis TransExeTime = new ElementLis("TransExeTime",sTransExeTime,TXLifeRequest);	
		ElementLis OLifE = new ElementLis("OLifE",TXLifeRequest); 
			ElementLis HoldingH1 = new ElementLis("Holding","id","Holding_1",OLifE);
				ElementLis Policy = new ElementLis("Policy",HoldingH1);  
					ElementLis PolNumber = new ElementLis("PolNumber",sPolNumber,Policy); 
					ElementLis ApplicationInfo = new ElementLis("ApplicationInfo",Policy);
						ElementLis HOAppFormNumber = new ElementLis("HOAppFormNumber",sHOAppFormNumber,ApplicationInfo);
						  
			ElementLis OLifEExtension1 = new ElementLis("OLifEExtension","VendorCode","1",TXLifeRequest); 
			ElementLis RegionCode = new ElementLis("RegionCode",sRegionCode,OLifEExtension1);
			ElementLis Branch = new ElementLis("Branch",sBranch,OLifEExtension1);
			ElementLis Teller = new ElementLis("Teller",sTeller,OLifEExtension1);
			ElementLis TransNo = new ElementLis("TransNo",sTransNo,OLifEExtension1);
		
		ElementLis FormInstance = new ElementLis("FormInstance","id","Form_2",OLifE);
				ElementLis FormName = new ElementLis("FormName","2",FormInstance);
				ElementLis ProviderFormNumber = new ElementLis("ProviderFormNumber",sProviderFormNumber,FormInstance);
				ElementLis OriginalProviderFormNumber = new ElementLis("OriginalProviderFormNumber",sOriginalProviderFormNumber,FormInstance);
		String ip = request.getParameter("ip");
		String port = request.getParameter("port"); 
		System.out.println("������" + ip + "�˿�:" + Integer.valueOf(port).intValue());
		
		
	//System.out.println("************************���ñ���·��************************");	
	//StringBuilder mFilePath = new StringBuilder(SysInfo.cHome);
	//System.out.println("mFilePath1************************"+mFilePath+"************************");   
	//	int number =(mFilePath.length()-1);
	//	int num = (number-8);
	//	mFilePath.delete(num, number);
 	//System.out.println("mFilePath2************************"+mFilePath+"************************");  
	//	mFilePath.append("msg"); 
				 //.append("1").append('/');
				 //.append(DateUtil.getCurDate("yyyy/yyyyMM/yyyyMMdd/"));
	//	String mInFilePath = mFilePath.toString()+"/RetXMLIn.xml";		
	//	String mOutFilePath = mFilePath.toString()+"/RetXMLReturn.xml";
		 
//System.out.println("************************"+mInFilePath+"************************");   
//System.out.println("************************"+mOutFilePath+"************************");  

System.out.println("************************װ��Document************************");	
   
 			Document pXmlDoc = new Document(TXLife);	
//System.out.println("************************1************************");	
//			FileOutputStream tFos = new FileOutputStream(mInFilePath); 
//System.out.println("************************2************************");	
//			JdomUtil.output(pXmlDoc, tFos,"GBK");
//System.out.println("************************3************************");		
			    
		
		int iPort = Integer.valueOf(sPort).intValue();
		ICBCTestUI mTestUI = new ICBCTestUI(sIp,iPort); 
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
 
	       if (sTransNo.trim().equals("1013")) {
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
