<%@page contentType="text/html;charset=GBK"%>
<%
	//�������ƣ�����רԱ�����ύSAVEҳ��
	//�����ܣ�EXCEL�����ύ��AXAAGENT����
	//�������ڣ�2011-09-02
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.citi.*"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.fileupload.*"%>
<%@page import="com.sinosoft.midplat.common.*"%>  
  
<%
//	GlobalInput tGlobalInput = new GlobalInput();
//	String sOperator = "";
	String sWebAppPath = "";
	String ImportPath = "citiUpload/temp/";
	String FileName = ""; 
	int count = 0;
//	tGlobalInput = (GlobalInput) session.getValue("GI");
//	sOperator = tGlobalInput.Operator;
	sWebAppPath = application.getRealPath("").replace('\\', '/');
	String InfoType = request.getParameter("InfoType");

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
	fu.setRepositoryPath(sWebAppPath + "citiUpload/temp");// ����һ���ļ���С����getSizeThreshold()��ֵʱ���ݴ����Ӳ�̵�Ŀ¼
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
	
	String sDay = DateUtil.getCur10Date();
	
	//�������
	CErrors tError = null;
	String tRela = "";
	String FlagStr = "Fail";
	String Content = "";
	String Result = "";
	String tBatchNo = "";
	System.out.println("----FileName:" + FileName);
	boolean res = false;
	
	// ׼���������� VData
	StringBuffer sourceFile = new StringBuffer(ImportPath);	
	sourceFile=sourceFile.append(FileName);
	FundPriceImportUI tFundPriceImportUI = new FundPriceImportUI();
	String errMess = "";
	try{
	tFundPriceImportUI.upByFileName(sourceFile.toString());
	if (count > 0) {

		try {
			System.out.println("submitData");
			res = tFundPriceImportUI.submitData();
		} catch (Exception ex) {
			Content = ex.toString();
			FlagStr = "Fail";
		}
	} else {
		Content = "�����ļ�ʧ��! ";
		FlagStr = "Fail";
	}


	if (res) {
		Content = "";
		if(tFundPriceImportUI.iSuccNo != 0){
			Content = Content + sDay+"��Ͷ���۸��ϴ��ɹ�! <br />";
		}
		//if(tFundPriceImportUI.iUpdateNo != 0){
		//	Content = Content + "�ɹ�����"
		//	+ tFundPriceImportUI.iUpdateNo + "��Ͷ���˻��۸�! <br />";
		//}
		
		if (tFundPriceImportUI.mErrors.needDealError()) {
			Content = Content + ""
					+ tFundPriceImportUI.mErrors.getLastError();
		}else if(!tFundPriceImportUI.mErrors.needDealError()){
			if(tFundPriceImportUI.iEndNo==0){
					Content = Content +" �ϴ��ļ���ʽ����������ѡ���ϴ�";
					}else{
			//Content = Content + ""
			//+ "��" + tFundPriceImportUI.iEndNo + "��Ϊ����,���ؽ���!";
			}
		}
		if(!tFundPriceImportUI.mErrors.needDealError()&&tFundPriceImportUI.iSuccNo == 0){
			Content = "�ϴ�����ϢΪ�գ�������ѡ���ϴ� <br />";
		}

		
		FlagStr = "Succ";
		System.out.println("---aaa");
	} else {
		tError = tFundPriceImportUI.mErrors;
		errMess = tError.getLastError();
		System.out.println("---ccc");
		Content = errMess;
		FlagStr = "Fail";
	}
	}catch(Exception e){
		tError = tFundPriceImportUI.mErrors;
		errMess = tError.getFirstError();
		System.out.println("---ccc");
		Content = errMess;
		FlagStr = "Fail";
	}
%>
<script language="javascript">
parent.fraInterface.afterSubmitIn("<%=FlagStr%>", "<%=Content%>", "",
	"<%=tBatchNo%>");
</script>
