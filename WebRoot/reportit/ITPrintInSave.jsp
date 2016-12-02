<%@page contentType="text/html;charset=GBK"%>
<%
	//程序名称：保单号批量提交SAVE页面
	//程序功能：EXCEL批量提交到LKCONTNO表中
	//创建日期：2011-09-02
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.input.*"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.fileupload.*"%>

  
<%
	GlobalInput tGlobalInput = new GlobalInput();
	String sOperator = "";
	String sWebAppPath = "";
	String ImportPath = "reportit/temp/";
	String FileName = "";
	int count = 0; 
	tGlobalInput = (GlobalInput) session.getValue("GI");
	sOperator = tGlobalInput.Operator;
	sWebAppPath = application.getRealPath("").replace('\\', '/');
	String sAreaNo = request.getParameter("AreaNo");
	String sCityNo = request.getParameter("CityNo");
	String sManageCodeNo = request.getParameter("ManageCodeNo");
	//System.out.println("sAreaNo"+sAreaNo+"sCityNo" + sCityNo);
	System.out.println("ITPrintInSave中的ManageCodeNo---------"+sManageCodeNo);
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
	fu.setRepositoryPath(sWebAppPath + "reportit/temp");// 设置一旦文件大小超过getSizeThreshold()的值时数据存放在硬盘的目录
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
	String FlagStr = "Fail";
	String Content = "";
	String Result = "";
	String tBatchNo = "";
	TransferData tTransferData = new TransferData();
	System.out.println("----FileName:" + FileName);
	boolean res = false;
	// 准备传输数据 VData
	VData tVData = new VData();
	FlagStr = "";
	tTransferData.setNameAndValue("FileName", FileName);
	tTransferData.setNameAndValue("FilePath", sWebAppPath);
	tTransferData.setNameAndValue("UserID",tGlobalInput);
	tTransferData.setNameAndValue("AreaNo",sAreaNo);
	tTransferData.setNameAndValue("CityNo",sCityNo);
	tTransferData.setNameAndValue("ManageCom",sManageCodeNo);
	tVData.add(tTransferData);
	tVData.add(tGlobalInput);
	
	
	ITImportUI tITImportUI = new ITImportUI();

	if (count > 0) {

		try {
			System.out.println("submitData");
			res = tITImportUI.submitData(tVData, "");
		} catch (Exception ex) {
			Content = ex.toString();
			FlagStr = "Fail";
		}
	} else {
		Content = "上载文件失败! ";
		FlagStr = "Fail";
	}

	String errMess = "";

	if (res) {
		Content = " 成功提交"  + tITImportUI.iSuccNo + "条数据! <br />";
		if(tITImportUI.iRepeatNo != 0){
			Content = Content + ""
			+ tITImportUI.iRepeatNo + "条数据已存在! <br />";
		}
		
		if (tITImportUI.mErrors.needDealError()) {
			Content = Content + ""
					+ tITImportUI.mErrors.getLastError();
		}else if(!tITImportUI.mErrors.needDealError()){
			Content = Content + ""
			+ "第" + tITImportUI.iEndNo + "行为空行,上载结束!";
		}
		FlagStr = "Succ";
		System.out.println("---aaa");
	} else {
		tError = tITImportUI.mErrors;
		errMess = tError.getLastError();
		System.out.println("---ccc");
		Content = errMess;
		FlagStr = "Fail";
	}

System.out.println("---bbb" + FlagStr);
//添加各种预处理
%>
<script language="javascript">
parent.fraInterface.afterSubmitIn("<%=FlagStr%>", "<%=Content%>", "",
	"<%=tBatchNo%>");
</script>
