<%@page contentType="text/html;charset=GBK"%>
<%
	//�������ƣ������������ύSAVEҳ��
	//�����ܣ�EXCEL�����ύ��LKCONTNO����
	//�������ڣ�2011-09-02
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
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
	System.out.println("ITPrintInSave�е�ManageCodeNo---------"+sManageCodeNo);
	if (!sWebAppPath.endsWith("/")) {
		sWebAppPath += "/";
	}

	File tFile = new File(sWebAppPath + ImportPath);
	if (!tFile.exists()) {
		System.out.println("�ļ�·��������" + tFile);
		tFile.mkdir();
	}

	System.out.println("�ļ�·������" + tFile);

	DiskFileUpload fu = new DiskFileUpload();
	fu.setSizeMax(10000000);// ���������û��ϴ��ļ���С,��λ:�ֽ�
	fu.setSizeThreshold(4096);// �������ֻ�������ڴ��д洢������,��λ:�ֽ�
	fu.setRepositoryPath(sWebAppPath + "reportit/temp");// ����һ���ļ���С����getSizeThreshold()��ֵʱ���ݴ����Ӳ�̵�Ŀ¼
	List fileItems = fu.parseRequest(request);	//��ʼ��ȡ�ϴ���Ϣ
	Iterator iter = fileItems.iterator();
	while (iter.hasNext()) {
		FileItem item = (FileItem) iter.next();
		if (!item.isFormField()) {//�������������ļ�������б���Ϣ
			String name = item.getName();
			System.out.println("�ϴ��ļ����ļ���Ϊ:" + name);
			long size = item.getSize();
			if ((name == null || name.equals("")) && size == 0)
				continue;
			ImportPath = sWebAppPath + ImportPath;
			System.out.println("�ϴ�·��:" + ImportPath);
			FileName = name.replace('\\', '/');
			FileName = FileName
					.substring(FileName.lastIndexOf("/") + 1);
			try {//�����ϴ����ļ���ָ����Ŀ¼
				System.out.println("���ս��" + ImportPath + FileName);
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
	 

	//�������
	CErrors tError = null;
	String tRela = "";
	String FlagStr = "Fail";
	String Content = "";
	String Result = "";
	String tBatchNo = "";
	TransferData tTransferData = new TransferData();
	System.out.println("----FileName:" + FileName);
	boolean res = false;
	// ׼���������� VData
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
		Content = "�����ļ�ʧ��! ";
		FlagStr = "Fail";
	}

	String errMess = "";

	if (res) {
		Content = " �ɹ��ύ"  + tITImportUI.iSuccNo + "������! <br />";
		if(tITImportUI.iRepeatNo != 0){
			Content = Content + ""
			+ tITImportUI.iRepeatNo + "�������Ѵ���! <br />";
		}
		
		if (tITImportUI.mErrors.needDealError()) {
			Content = Content + ""
					+ tITImportUI.mErrors.getLastError();
		}else if(!tITImportUI.mErrors.needDealError()){
			Content = Content + ""
			+ "��" + tITImportUI.iEndNo + "��Ϊ����,���ؽ���!";
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
//��Ӹ���Ԥ����
%>
<script language="javascript">
parent.fraInterface.afterSubmitIn("<%=FlagStr%>", "<%=Content%>", "",
	"<%=tBatchNo%>");
</script>
