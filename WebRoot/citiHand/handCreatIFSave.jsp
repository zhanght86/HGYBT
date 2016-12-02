<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@page import="java.io.*"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="org.jdom.xpath.XPath"%>
<%@page import="org.jdom.Element"%>
<%@page import="com.sinosoft.lis.InterFace.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%@page import="com.sinosoft.midplat.citi.service.*"%>
<%@page import="com.sinosoft.midplat.citi.createfile.*"%>
<%@page import="com.sinosoft.lis.citi.*"%>

<%
	//准备通用参数
	CError cError = new CError();
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");

	if (tG == null) {
		System.out.println("登录信息没有获取!!!");
		return;
	}

	//表单传过来的数据
	String fileTypeCode = request.getParameter("FileTypeCode");
	String fileTypeName = request.getParameter("FileTypeName");
	String startDay = request.getParameter("StartDay");
	String endDay = request.getParameter("EndDay");
	
	System.out.println("fileTypeCode:"+fileTypeCode);
	System.out.println("fileTypeName:"+fileTypeName);
	System.out.println("startDay:"+startDay);
	System.out.println("endDay:"+endDay);
	String mResultMsg = null;
	String FlagStr = null;
	String Content = null;
	
	
	TransferData tTransferData = new TransferData();
	tTransferData.setNameAndValue("FileTypeCode", fileTypeCode);
	VData tVData = new VData();
	tVData.addElement(tTransferData);
	tVData.addElement(tG);
	boolean res ;
	
	//HandCreat citiHand  = new HandCreat(tVData);
	try{
	//res =citiHand.checkFile();
		//if(res){
			if(!"FundPrice".equals(fileTypeCode)){
			Date cdate=new Date();	
			List<Date> list = DateUtil.countDays(startDay,endDay);
			for (Date date : list) {
				if(DateUtil.getCur10Date().equals(DateUtil.get10Date(date))){
					continue;
				}
				
				if(cdate.before(date)){
					continue;
				}
			
				
				String tSqlStr = new StringBuilder("SELECT 1 FROM BatTranLog WHERE RCode=").append(CodeDef.RCode_OK)
				.append(" and TranDate=date'").append(DateUtil.get10Date(date))
				.append("' and FuncFlag=").append("401")
				.append(" and TranCom=").append("'021'")
				.toString();
			    ExeSQL tExeSQL = new ExeSQL();
			    if ("1".equals(tExeSQL.getOneValue(tSqlStr))) {
				     continue;
			    } else {
			    	citiService scitiService = new citiService();
			    	scitiService.setDate(date);
			    	scitiService.run();
			    	FlagStr=scitiService.getRcode();
			    	mResultMsg = scitiService.getResultMsg();
			    	
			    }
			
			    String rSqlStr = new StringBuilder("SELECT 1 FROM BatTranLog WHERE RCode in('0')")
				.append(" and TranDate=date'").append(DateUtil.get10Date(date))
				.append("' and FuncFlag=").append("401")
				.append(" and TranCom=").append("'021'")
				.toString();    
			    
			    ExeSQL rExeSQL = new ExeSQL();
			    
			    if (!"1".equals(rExeSQL.getOneValue(rSqlStr))) {
			    	FlagStr = "Fail";			    	
			    		throw new Exception(mResultMsg);
			    }
			   
			}
			
			}
			
			CitiConf citiConf = CitiConf.newInstance();
			XPath tXPath = XPath.newInstance(
					"business[IFtype='" + fileTypeCode+ "']");
			Element tBusinessEle = (Element) tXPath.selectSingleNode(citiConf.getConf().getRootElement());
			

			if("Policy".equals(fileTypeCode)){
				PolicyFileCreate sPolicyFileCreate = new PolicyFileCreate(tBusinessEle,startDay,endDay);
				sPolicyFileCreate.run();
				mResultMsg = sPolicyFileCreate.getResultMsg();
				FlagStr=sPolicyFileCreate.getRcode();
			}else if("FundTxn".equals(fileTypeCode)){
				FundTransactionCreate sFundTransactionCreate=new FundTransactionCreate(tBusinessEle,startDay,endDay);
				sFundTransactionCreate.run();
				mResultMsg = sFundTransactionCreate.getResultMsg();
				FlagStr=sFundTransactionCreate.getRcode();
			}else if("FundSum".equals(fileTypeCode)){
				FundSummaryCreate sFundSummaryCreate=new FundSummaryCreate(tBusinessEle,startDay,endDay);
				sFundSummaryCreate.run();
				mResultMsg = sFundSummaryCreate.getResultMsg();
				FlagStr=sFundSummaryCreate.getRcode();
			}else if("FundPrice".equals(fileTypeCode)){
				FundPricingCreate sFundPricingCreate=new FundPricingCreate(tBusinessEle,startDay,endDay);
				sFundPricingCreate.run();
				mResultMsg = sFundPricingCreate.getResultMsg();
				FlagStr=sFundPricingCreate.getRcode();
			}else if("Commission".equals(fileTypeCode)){
				CommissionCreate sCommissionCreate=new CommissionCreate(tBusinessEle,startDay,endDay);
				sCommissionCreate.run();
				mResultMsg = sCommissionCreate.getResultMsg();
				FlagStr=sCommissionCreate.getRcode();
			}

			
			if("Succ".equals(FlagStr)){
				Content= "接口文件生成成功";
			}else{
				Content=mResultMsg;
			}
			
		//}else{
		//	FlagStr = "Fail";
		//	Content= "接口文件已存在，不能手工生成";
	//	}

	}catch(Exception e){
			FlagStr = "Fail";
			Content= "操作失败   "+mResultMsg;
	}

	
	//try {
	
	
	
	
%>
<html>
	<script language=jscript.encode>
	parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>");
</script>
</html>




