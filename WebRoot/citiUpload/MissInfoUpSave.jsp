<%@page contentType="text/html;charset=GBK"%>
<%
	//程序名称：网点专员批量提交SAVE页面
	//程序功能：EXCEL批量提交到AXAAGENT表中
	//创建日期：2011-09-02
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.citi.*"%>
<%@page import="com.sinosoft.midplat.exception.*"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.fileupload.*"%>


  
<%
	GlobalInput cGlobalInput = (GlobalInput) session.getValue("GI");
	String FlagStr = "Fail";
	String Content = "";
	String tBatchNo = "";
	
	String mOperType = request.getParameter("OperType");
	String policyNo = request.getParameter("PolicyNo");
	
System.out.println("mOperType:"+mOperType+",PolicyNo:"+policyNo);
	
	if("UP".equals(mOperType)){
	String sWebAppPath = "";
	String ImportPath = "citiUpload/temp/";
	String FileName = ""; 
	int count = 0;
//	tGlobalInput = (GlobalInput) session.getValue("GI");
//	sOperator = tGlobalInput.Operator;
	sWebAppPath = application.getRealPath("").replace('\\', '/');
//	String InfoType = request.getParameter("InfoType");
	
	

	if (!sWebAppPath.endsWith("/")) {
		sWebAppPath += "/";
	}

	File tFile = new File(sWebAppPath + ImportPath);
	if (!tFile.exists()) {
		System.out.println("文件路径不存在" + tFile);
		tFile.mkdir();
	}

	System.out.println("文件路径存在" + tFile);

	DiskFileUpload fu = new DiskFileUpload();
	fu.setSizeMax(10000000);// 设置允许用户上传文件大小,单位:字节
	fu.setSizeThreshold(4096);// 设置最多只允许在内存中存储的数据,单位:字节
	fu.setRepositoryPath(sWebAppPath + "citiUpload/temp");// 设置一旦文件大小超过getSizeThreshold()的值时数据存放在硬盘的目录
	List fileItems = fu.parseRequest(request);	//开始读取上传信息
	Iterator iter = fileItems.iterator();
	while (iter.hasNext()) { 
		FileItem item = (FileItem) iter.next();
		if (!item.isFormField()) {//忽略其他不是文件域的所有表单信息
			String name = item.getName();
			System.out.println("上传文件的文件名为:" + name);
			long size = item.getSize();
			if ((name == null || name.equals("")) && size == 0)
				continue;
			ImportPath = sWebAppPath + ImportPath;
			System.out.println("上传路径:" + ImportPath);
			FileName = name.replace('\\', '/');
			FileName = FileName
					.substring(FileName.lastIndexOf("/") + 1);
			try {//保存上传的文件到指定的目录
				System.out.println("最终结果" + ImportPath + FileName);
				item.write(new File(ImportPath + FileName));
				count = 1;
			} catch (Exception e) {
				System.out.println("upload file error ...");
			}
		} else if (item.isFormField()) {
			String fieldName = item.getFieldName();
			System.out.println(fieldName);
		}
	}
	
	
	//输出参数
	CErrors tError = null;
	String tRela = "";
	String Result = "";
	System.out.println("----FileName:" + FileName);
	boolean res = false;
	
	MissInfoImportUI tMissInfoImportUI = new MissInfoImportUI();
	
	// 准备传输数据 VData
	VData tVData = new VData();
	TransferData tTransferData = new TransferData();
	FlagStr = "";
	tTransferData.setNameAndValue("FileName", FileName);
	tTransferData.setNameAndValue("FilePath", ImportPath);
	tTransferData.setNameAndValue("ConfigFileName", ImportPath+"MissInfo.xml");
//	tTransferData.setNameAndValue("UserID",tGlobalInput);
	tVData.add(tTransferData);
	tVData.add(cGlobalInput);

	if (count > 0) {

		try {
			System.out.println("submitData");
			res = tMissInfoImportUI.submitData(tVData);
		} catch (Exception ex) {
			tError = tMissInfoImportUI.mErrors;
		Content = tError.getLastError();
			FlagStr = "Fail";
		}
	} else {
		Content = "上载文件失败! ";
		FlagStr = "Fail";
	}

	String errMess = "";

	if (res) {
		Content = "";
		if(tMissInfoImportUI.iSuccNo != 0){
			Content = Content + "成功上传 "
			+ tMissInfoImportUI.iSuccNo + "条保单缺失信息! <br />";
		}
		//if(tMissInfoImportUI.iUpdateNo != 0){
		//	Content = Content + "成功更新"
		//	+ tMissInfoImportUI.iUpdateNo + "条保单缺失信息! <br />";
		//}
		
		if (tMissInfoImportUI.mErrors.needDealError()) {
			Content = Content + ""
					+ tMissInfoImportUI.mErrors.getLastError();
		}else if(!tMissInfoImportUI.mErrors.needDealError()){
			//Content = Content + ""
			//+ "第" + tMissInfoImportUI.iEndNo + "行为空行,上载结束!";
		}

		
		FlagStr = "Succ";
		System.out.println("---aaa");
	} else {
		tError = tMissInfoImportUI.mErrors;
		errMess = tError.getLastError();
		System.out.println("---ccc");
		Content = errMess;
		FlagStr = "Fail";
	}
	
}else if("DELETE".equals(mOperType)){
	MissInfoImportUI tMissInfo = new MissInfoImportUI();
	tMissInfo.setPolicyNo(policyNo);
	try{tMissInfo.delete();
	FlagStr = "Succ";
	Content = "删除成功！";
	}catch(Exception e){
	FlagStr = "Fail";
	Content = "删除失败！";
	}
	
	
}else if("UPDATE".equals(mOperType)){

	String mExtractedDate = request.getParameter("iExtractedDate");
	String mPolicyNo = request.getParameter("iPolicyNo");
	String mInsuredIdType = request.getParameter("iInsuredIdType");
	String mApplicantIdType = request.getParameter("iApplicantIdType");
	String mApplicantIdNo = request.getParameter("iApplicantIdNo");
	String mApplicantAcctNo = request.getParameter("iApplicantAcctNo");
	String mCustomNO  = request.getParameter("iCustomNO");
	String mCancelReason = request.getParameter("iCancelReason");
	String mApplicateNo = request.getParameter("iApplicateNo");
	String mCancelCode = request.getParameter("iCancelCode");
	
	//System.out.println("mExtractedDate:"+mExtractedDate);
	//System.out.println("mPolicyNo:"+mPolicyNo);
	//System.out.println("mInsuredIdType:"+mInsuredIdType);
	//System.out.println("mApplicantIdType:"+mApplicantIdType);
	//System.out.println("mApplicantIdNo:"+mApplicantIdNo);
	//System.out.println("mApplicantAcctNo:"+mApplicantAcctNo);
	//System.out.println("mCustomNO:"+mCustomNO);
	//System.out.println("mCancelReason:"+mCancelReason);
	//System.out.println("mApplicateNo:"+mApplicateNo);
	//System.out.println("mCancelCode:"+mCancelCode);
	
	
	VData vdate = new VData();
	TransferData tTransferData = new TransferData();
	tTransferData.setNameAndValue("ExtractedDate", mExtractedDate);
	tTransferData.setNameAndValue("PolicyNo", mPolicyNo);
	tTransferData.setNameAndValue("InsuredIdType", mInsuredIdType);
	tTransferData.setNameAndValue("ApplicantIdType", mApplicantIdType);
	tTransferData.setNameAndValue("ApplicantIdNo", mApplicantIdNo);
	tTransferData.setNameAndValue("ApplicantAcctNo", mApplicantAcctNo);
	tTransferData.setNameAndValue("CustomNO", mCustomNO);
	tTransferData.setNameAndValue("CancelReason", mCancelReason);
	tTransferData.setNameAndValue("ApplicateNo", mApplicateNo);
	tTransferData.setNameAndValue("CancelCode", mCancelCode);
	vdate.add(tTransferData);
	
	MissInfoImportUI tMissInfo = new MissInfoImportUI(vdate);
	try{
	tMissInfo.update();
	FlagStr = "Succ";
	Content = "操作成功！";
	}catch(Exception e){
	FlagStr = "Fail";
	Content = "操作失败！";	
	}
	
}else {
	FlagStr = "Fail";
	Content = "操作码"+mOperType+"有误，上传失败！";
}
		
System.out.println("---bbb" + FlagStr);
//添加各种预处理
%>
<script language="javascript">
parent.fraInterface.afterSubmitIn("<%=FlagStr%>", "<%=Content%>", "",
	"<%=tBatchNo%>");
</script>
