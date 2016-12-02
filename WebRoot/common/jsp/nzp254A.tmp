
<%
//程序名称：codeQueryKernel.jsp
//程序功能：codeQuery查询功能的核心，暂时用于校验功能的代码查询部分
//创建日期：2002-10-18
//创建人  ：胡 博
//更新记录：  更新人    更新日期     更新原因/内容       
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<%!
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
%>