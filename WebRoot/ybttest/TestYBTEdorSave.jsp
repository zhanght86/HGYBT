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
<%@page import="com.sinosoft.midplat.common.DateUtil"%>
<%@page import="com.sinosoft.midplat.common.SysInfo"%>
<%@page import="com.sinosoft.midplat.common.JdomUtil"%>
<%@page import="test.*" %>
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
	//sTransExeDate=DateUtil.date10to8(sTransExeDate);//���ж˵Ľ���������10λ��
	
	String sTransExeTime = DateUtil.getCur8Time();
	
	//������ˮ��
	String sTransRefGUID = request.getParameter("TransNo");
	//��������
	String sZoneNo = request.getParameter("ZoneNo");
	//�������
	String sBrNo = request.getParameter("NodeNo");
	
	//������ 
	String sPolicyNo = request.getParameter("PolNumber");
	
	//���ױ�־
	String sTranCode = request.getParameter("TranType");

	//�����˺�
	String sPayAcc = request.getParameter("AccountNumber");
	//Ͷ������Ϣ
	String sAppFullName = request.getParameter("AppntName");
	String sAppGovtIDTC=request.getParameter("AppGovtIDTC");
	String sAppGovtID = request.getParameter("AppGovtID");
	//��������Ϣ
	String sInsFullName=request.getParameter("InsureName");
	String sInsGovtIDTC=request.getParameter("InsureGovtIDTC");
	String sInsGovtID =request.getParameter("InsureGovtID"); 
	
	//��֤ӡˢ��
	String sProviderFormNumber = request.getParameter("ProviderFormNumber");
	
	//���н�������
	String sSourceType=request.getParameter("saleChannel");
	
	
	String sIp = request.getParameter("ip");
	String sPort = request.getParameter("port");
		
	String sFlag = request.getParameter("TranType");
	
	String sAccountForName = request.getParameter("AccountForName");
	String sCortransrno = request.getParameter("CortransrnoNo");
	
		//�ѱ�����Ϣ��ҳ����ʾ
		xmlContent = new String("");

		xmlContent += "һ��������Ϣ\\n";
		xmlContent += "  ���ж�����: " + sTransExeDate;
		xmlContent += "  ��������: " + sZoneNo; 
		xmlContent += "\t\t\t�������: " + sBrNo + "\\n"; 
		xmlContent += "\t������: " + sPolicyNo + "\\n";
		
 		System.out.println("************************װ��XML************************");		 


 	ElementLis TXLife = new ElementLis("TXLife");	 
		ElementLis TXLifeRequest = new ElementLis("TXLifeRequest",TXLife);
	
		
		//������ˮ��
		ElementLis TransRefGUID = new ElementLis("TransRefGUID",sTransRefGUID,TXLifeRequest);
		//������
		ElementLis TransType = new ElementLis("TransType",sFlag,TXLifeRequest);
		//���н�������
		ElementLis TransExeDate = new ElementLis("TransExeDate",sTransExeDate,TXLifeRequest); 
		ElementLis TransExeTime = new ElementLis("TransExeTime",sTransExeTime,TXLifeRequest);  
		//���ͱ�ʶ
		ElementLis RepeatType = new ElementLis("RepeatType","0",TXLifeRequest);
		
		ElementLis OLifE = new ElementLis("OLifE",TXLifeRequest);
		ElementLis Holding = new ElementLis("Holding","id","Holding_1",OLifE);
		
			ElementLis Policy = new ElementLis("Policy",Holding);
			//������
			ElementLis PolNumber = new ElementLis("PolNumber",sPolicyNo,Policy);
			//���ִ���
			ElementLis ProductCode = new ElementLis("ProductCode","001",Policy);
		
				ElementLis OLifEExtension2 = new ElementLis("OLifEExtension","VendorCode","2",Holding);
				//��������
				ElementLis Password = new ElementLis("Password","Password",OLifEExtension2);
			ElementLis Arrangement = new ElementLis("Arrangement","",Holding);
		
			
			
		ElementLis HoldingAcct = new ElementLis("Holding","id","Acct_1",OLifE);
			ElementLis Banking = new ElementLis("Banking",HoldingAcct);
				//�����˺�
				ElementLis AccountNumber = new ElementLis("AccountNumber",sPayAcc,Banking);
				//�˻���
				ElementLis AcctHolderName = new ElementLis("AcctHolderName",sAccountForName,Banking);
		
		ElementLis FormInstance = new ElementLis("FormInstance","id","Form_1",OLifE);
			//��֤����
			
		if(sFlag.equals("1003")||sFlag.equals("1021")){
		
		if(!sAppFullName.equals("")||!sAppGovtID.equals("")||!sAppGovtIDTC.equals("")){
	ElementLis Party1 = new ElementLis("Party","id","Party_1",OLifE);
	ElementLis AppFullName = new ElementLis("FullName",sAppFullName,Party1);
	ElementLis AppGovtID = new ElementLis("GovtID",sAppGovtID,Party1);				
	ElementLis  AppGovtIDTC = new ElementLis("GovtIDTC",sAppGovtIDTC,"tc",sAppGovtIDTC,Party1);
	ElementLis Relation_1 = new ElementLis("Holding_1","Party_1","Relation_1","4","6","80",OLifE);
	}
	if(!sInsFullName.equals("")||!sInsGovtID.equals("")||!sInsGovtIDTC.equals("")){
	ElementLis Party2 = new ElementLis("Party","id","Party_2",OLifE);
	ElementLis InsFullName = new ElementLis("FullName",sInsFullName,Party2);
	ElementLis InsGovtID = new ElementLis("GovtID",sInsGovtID,Party2);
	ElementLis InsGovtIDTC = new ElementLis("GovtIDTC",sInsGovtIDTC,"tc",sInsGovtIDTC,Party2);
		ElementLis Relation_2 = new ElementLis("Holding_1","Party_2","Relation_2","4","6","81",OLifE);
	}
}
if(sFlag.equals("1004")||sFlag.equals("1017")){
    if(!sAppFullName.equals("")||!sAppGovtID.equals("")||!sAppGovtIDTC.equals("")){
	ElementLis Party1 = new ElementLis("Party","id","Party_1",OLifE);
	ElementLis AppFullName = new ElementLis("FullName",sAppFullName,Party1);
	ElementLis AppGovtID = new ElementLis("GovtID",sAppGovtID,Party1);				
	ElementLis  AppGovtIDTC = new ElementLis("GovtIDTC",sAppGovtIDTC,"tc",sAppGovtIDTC,Party1);
	
	ElementLis Relation_1 = new ElementLis("Holding_1","Party_1","Relation_1","4","6","80",OLifE);
	}
}
			ElementLis FormName = new ElementLis("FormName","3",FormInstance);
			//ӡˢ��
			ElementLis ProviderFormNumber = new ElementLis("ProviderFormNumber",sProviderFormNumber,FormInstance);
		ElementLis OLifEExtension1	= new ElementLis("OLifEExtension","VendorCode","1",TXLifeRequest);
			ElementLis CarrierCode = new ElementLis("CarrierCode","050",OLifEExtension1);
			ElementLis BankCode = new ElementLis("BankCode","0101",OLifEExtension1);
			//ElementLis TransNo = new ElementLis("TransNo",sFlag,OLifEExtension1);
			ElementLis RegionCode = new ElementLis("RegionCode",sZoneNo,OLifEExtension1);
			if(sFlag.equals("1026")||sFlag.equals("1030")){
			ElementLis Cortransrno = new ElementLis("Cortransrno",sCortransrno,OLifEExtension1);
			}
			
			ElementLis Branch = new ElementLis("Branch",sBrNo,OLifEExtension1);
			ElementLis Teller = new ElementLis("Teller","00001",OLifEExtension1);
			ElementLis SourceType = new ElementLis("SourceType",sSourceType,OLifEExtension1);
		String ip = request.getParameter("ip");
		String port = request.getParameter("port"); 
	 
		System.out.println("������" + ip + "�˿�:" + Integer.parseInt(port));
		
		
	System.out.println("************************���ñ���·��************************");	
	StringBuilder mFilePath = new StringBuilder(SysInfo.cHome);
	System.out.println("mFilePath1************************"+mFilePath+"************************");   
		int number =(mFilePath.length()-1);
		int num = (number-8);
		mFilePath.delete(num, number);
 	System.out.println("mFilePath2************************"+mFilePath+"************************");  
		mFilePath.append("msg") 
				 .append("1").append('/')
				 .append(DateUtil.getCurDate("yyyy/yyyyMM/yyyyMMdd/"));
		String mInFilePath = mFilePath.toString()+"/RetXMLIn.xml";		
		String mOutFilePath = mFilePath.toString()+"/RetXMLReturn.xml";
		 
System.out.println("************************"+mInFilePath+"************************");   
System.out.println("************************"+mOutFilePath+"************************");  

System.out.println("************************װ��Document************************");	
   
 			Document pXmlDoc = new Document(TXLife);	
		
			    
		
		int iPort = Integer.parseInt(sPort);
		ICBCTestUI mTestUI = new ICBCTestUI(sIp,iPort); 
//		InputStream mIs = new FileInputStream(mInFilePath);
	//	byte[] mOutBytes = mTestUI.sendRequest(sFlag, mIs);
		byte[] mOutBytes = mTestUI.sendRequestUI(sFlag, pXmlDoc);
        System.out.println("************************4************************");		 
		Document mOutXmlDoc = JdomUtil.build(new String(mOutBytes));  
	//	String a = mOutXmlDoc.toString();
		
	//	mOutXmlDoc = JdomUtil.build(a);
	//	OutputStream mFos = new FileOutputStream(mOutFilePath);
	//	JdomUtil.output(mOutXmlDoc, mFos); 
 
		//��ӡ�������� 
		//uiPrint = JdomUtil.toStringFmt(pXmlDoc);
		//System.out.println(uiPrint);		
	//	mFos.flush();  
	//	mFos.close(); 
		ResultCode = mOutXmlDoc.getRootElement().getChild("TXLifeResponse").getChild("TransResult").getChildTextTrim("ResultCode");
		
		if("0000".equals(ResultCode)){
			ResultInfoDesc ="���׳ɹ�";
		}else{
		ResultInfoDesc = mOutXmlDoc.getRootElement().getChild("TXLifeResponse").getChild("TransResult").getChild("ResultInfo").getChildTextTrim("ResultInfoDesc");
		}
        System.out.println(ResultCode + "  " + ResultInfoDesc);
		strNewTransNo = PubFun1.CreateMaxNo("TransNo",16);
	//	System.out.println("strNewTransNo = " + strNewTransNo);
        System.out.println("�������ݣ�"+ResultInfoDesc);
		if (ResultCode.equals("1234")) { // fail
	        Content = "����ʧ�ܣ�" + ResultInfoDesc ;
	    
            //jsp��String�ϲ��ܳ���"%",��������ʾ����
	       
		} else { 
	      
	       System.out.println("��������2��"+Content);
 
	      

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
   parent.fraInterface.fm.TransNo.value = '<%=strNewTransNo%>';

  // parent.fraInterface.fm.xmlContent.value = '<%=xmlContent.toString()%>'; 
   //parent.fraInterface.fm.ContNo.value = 'strContNo';
</script>
