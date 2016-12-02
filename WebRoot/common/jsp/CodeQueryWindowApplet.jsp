<%@page import="com.sinosoft.utility.*"%><%@
page import="com.sinosoft.lis.schema.*"%><%@
page import="com.sinosoft.lis.pubfun.*"%><%@
page import="com.sinosoft.lis.pubfun.*"%><%@
page import="java.io.*"%><%!
public String codeQueryKernel(String codeType, Object session) {     
  String strResult = "";
	codeType = codeType.toLowerCase().trim();  
	      
	CodeQueryUI tCodeQueryUI=new CodeQueryUI();
	VData tData=new VData();
	LDCodeSchema tLDCodeSchema =new LDCodeSchema();
	tLDCodeSchema.setCodeType(codeType);
	tData.add(tLDCodeSchema);
	
  GlobalInput tGI = new GlobalInput();
  tGI = (GlobalInput)session;
  tData.add(tGI);
	
	tCodeQueryUI.submitData(tData, "QUERY||MAIN");
	if (tCodeQueryUI.mErrors.needDealError()) {
	  System.out.println(tCodeQueryUI.mErrors.getFirstError()) ;
	}	else {
	  tData.clear();
	  tData=tCodeQueryUI.getResult();
	  strResult=(String)tData.get(0);
	  //strValue=StrTool.unicodeToGBK(strResult);
	  //System.out.println(strResult);
	}
	
	return strResult;
}

public String codeQueryKernel2(String codeType,String codeField,String codeCondition, Object session) {     
  String strResult = "";
  TransferData tTransferData = new TransferData();
	codeType = codeType.toLowerCase().trim();  	      
	CodeQueryUI tCodeQueryUI=new CodeQueryUI();
	VData tData=new VData();
	LDCodeSchema tLDCodeSchema =new LDCodeSchema();
	tLDCodeSchema.setCodeType(codeType);
	
	tTransferData.setNameAndValue("codeCondition",codeCondition);
	tTransferData.setNameAndValue("conditionField",codeField) ;
	
	tData.add(tTransferData);
	tData.add(tLDCodeSchema);
	
  GlobalInput tGI = new GlobalInput();
  tGI = (GlobalInput)session;
  tData.add(tGI);
	
	tCodeQueryUI.submitData(tData, "QUERY||MAIN");
	if (tCodeQueryUI.mErrors.needDealError()) {
	  System.out.println(tCodeQueryUI.mErrors.getFirstError()) ;
	}	else {
	  tData.clear();
	  tData=tCodeQueryUI.getResult();
	  strResult=(String)tData.get(0);
	  //strValue=StrTool.unicodeToGBK(strResult);
	  //System.out.println(strResult);
	}
	
	return strResult;
}
%><%
	String strResult = "";
	String codeType="";
	String codeField="";
	String codeConditon ="";
  
	try {
		codeType = request.getParameter( "codeType");
  		if(request.getParameter( "codeField" )!=null)
  			codeField = request.getParameter( "codeField" );
  		if(request.getParameter( "codeConditon" )!=null)
   			codeConditon = request.getParameter( "codeConditon" );
  		System.out.println("\n\n---codeQuery Start---\nCodeType:" + codeType);
  	} catch(Exception ex) {
    		System.out.println("codeQuery throw Errors!");	
  	}
  
	try {
		GlobalInput tGI = new GlobalInput();
		tGI = (GlobalInput)session.getValue("GI");
		if(codeConditon==null || codeConditon.equals("")||codeField==null || codeField.equals(""))
			strResult = codeQueryKernel(codeType, tGI);
		else
			strResult = codeQueryKernel2(codeType,codeField,codeConditon, tGI);
	} catch(Exception ex) {
		System.out.println("codeQuery throw Errors!");	
	}
			
	if (strResult == "") {
  		strResult = "Code Query Fail";
  	}
	
	OutputStream ous = response.getOutputStream();
	ous.write(strResult.getBytes("GBK"));
	ous.flush();
	ous.close();
%>