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
	
	
	//�������
	CErrors tError = null;
	String tRela = "";
	String Result = "";
	System.out.println("----FileName:" + FileName);
	boolean res = false;
	
	MissInfoImportUI tMissInfoImportUI = new MissInfoImportUI();
	
	// ׼���������� VData
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
		Content = "�����ļ�ʧ��! ";
		FlagStr = "Fail";
	}

	String errMess = "";

	if (res) {
		Content = "";
		if(tMissInfoImportUI.iSuccNo != 0){
			Content = Content + "�ɹ��ϴ� "
			+ tMissInfoImportUI.iSuccNo + "������ȱʧ��Ϣ! <br />";
		}
		//if(tMissInfoImportUI.iUpdateNo != 0){
		//	Content = Content + "�ɹ�����"
		//	+ tMissInfoImportUI.iUpdateNo + "������ȱʧ��Ϣ! <br />";
		//}
		
		if (tMissInfoImportUI.mErrors.needDealError()) {
			Content = Content + ""
					+ tMissInfoImportUI.mErrors.getLastError();
		}else if(!tMissInfoImportUI.mErrors.needDealError()){
			//Content = Content + ""
			//+ "��" + tMissInfoImportUI.iEndNo + "��Ϊ����,���ؽ���!";
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
	Content = "ɾ���ɹ���";
	}catch(Exception e){
	FlagStr = "Fail";
	Content = "ɾ��ʧ�ܣ�";
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
	Content = "�����ɹ���";
	}catch(Exception e){
	FlagStr = "Fail";
	Content = "����ʧ�ܣ�";	
	}
	
}else {
	FlagStr = "Fail";
	Content = "������"+mOperType+"�����ϴ�ʧ�ܣ�";
}
		
System.out.println("---bbb" + FlagStr);
//��Ӹ���Ԥ����
%>
<script language="javascript">
parent.fraInterface.afterSubmitIn("<%=FlagStr%>", "<%=Content%>", "",
	"<%=tBatchNo%>");
</script>
