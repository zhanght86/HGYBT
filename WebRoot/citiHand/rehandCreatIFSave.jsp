<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.File"%>
<%@page import="org.jdom.xpath.XPath"%>
<%@page import="org.jdom.Element"%>
<%@page import="com.sinosoft.lis.citi.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="org.apache.commons.fileupload.*"%>
<%@page import="com.sinosoft.midplat.citi.service.*"%>
<%@page import="com.sinosoft.midplat.citi.createfile.*"%>

<%
	//׼��ͨ�ò���
	CError cError = new CError();
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");

	if (tG == null) {
		System.out.println("��¼��Ϣû�л�ȡ!!!");
		return;
	}

	//׼������������Ϣ
	TransferData tTransferData = new TransferData();
	
	String fileTypeCode = request.getParameter("FileTypeCode");

	String startDay1 = request.getParameter("StartDay1");
	String startDay2 = request.getParameter("StartDay2");
	String endDay1 = request.getParameter("EndDay1");
	String endDay2 = request.getParameter("EndDay2");
    //System.out.println("startDay1:"+startDay1);
   // System.out.println("EndDay2:"+endDay2);
	tTransferData.setNameAndValue("FileTypeCode", fileTypeCode);

	tTransferData.setNameAndValue("StartDay1", startDay1);
	tTransferData.setNameAndValue("EndDay1", endDay1);
	tTransferData.setNameAndValue("EndDay2", endDay2);
	

	VData tVData = new VData();
	tVData.addElement(tTransferData);
	tVData.addElement(tG);
	String Content = "";
	String mResultMsg = "";
	String FlagStr = "";
	String sWebAppPath = "";
	String ImportPath = "citiHand/temp/";
	String FileName = ""; 
	int count = 0;
	sWebAppPath = application.getRealPath("").replace('\\', '/');
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
	fu.setRepositoryPath(sWebAppPath + "citiHand/temp");// ����һ���ļ���С����getSizeThreshold()��ֵʱ���ݴ����Ӳ�̵�Ŀ¼
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
	String tBatchNo = "";
	System.out.println("----FileName:" + FileName);
	boolean res = false;
	
	// ׼���������� VData
	StringBuffer sourceFile = new StringBuffer(ImportPath);	
	sourceFile=sourceFile.append(FileName);
	
	CitiHand citiHand = new CitiHand(sourceFile.toString());

	if (count > 0) {
		try {
			System.out.println("submitData");
			res = citiHand.submitData(tVData);
		} catch (Exception ex) {
			Content = ex.toString();
			FlagStr = "Fail";
		}
	} else {
		Content = "�����ļ���ʽ�������ɽӿ��ļ�ʧ�ܣ�������ѡ��!";
		FlagStr = "Fail";
	}

	String errMess = "";

	if (res) {
		Content = "";
		if(citiHand.iSuccNo != 0){
		//�ڴ˴����������ͷ����������ϴ���ı�����Ӧ����������ȵĸ��¡�
		try{
			res = citiHand.dealUpdate();
			if(res){
			try{
				 //citiHand.checkFile();
				 
				 CitiConf citiConf = CitiConf.newInstance();
					XPath tXPath = XPath.newInstance(
							"business[IFtype='" + fileTypeCode+ "']");
					Element tBusinessEle = (Element) tXPath.selectSingleNode(citiConf.getConf().getRootElement());
					

					if("Policy".equals(fileTypeCode)){
						PolicyFileCreate sPolicyFileCreate = new PolicyFileCreate(tBusinessEle,startDay2,endDay2);
						sPolicyFileCreate.run();
						mResultMsg = sPolicyFileCreate.getResultMsg();
						FlagStr=sPolicyFileCreate.getRcode();
					}else if("FundTxn".equals(fileTypeCode)){
						FundTransactionCreate sFundTransactionCreate=new FundTransactionCreate(tBusinessEle,startDay2,endDay2);
						sFundTransactionCreate.run();
						mResultMsg = sFundTransactionCreate.getResultMsg();
						FlagStr=sFundTransactionCreate.getRcode();
					}else if("FundSum".equals(fileTypeCode)){
						FundSummaryCreate sFundSummaryCreate=new FundSummaryCreate(tBusinessEle,startDay2,endDay2);
						sFundSummaryCreate.run();
						mResultMsg = sFundSummaryCreate.getResultMsg();
						FlagStr=sFundSummaryCreate.getRcode();
					}else if("FundPrice".equals(fileTypeCode)){
						FundPricingCreate sFundPricingCreate=new FundPricingCreate(tBusinessEle,startDay2,endDay2);
						sFundPricingCreate.run();
						mResultMsg = sFundPricingCreate.getResultMsg();
						FlagStr=sFundPricingCreate.getRcode();
					}else if("Commission".equals(fileTypeCode)){
						CommissionCreate sCommissionCreate=new CommissionCreate(tBusinessEle,startDay2,endDay2);
						sCommissionCreate.run();
						mResultMsg = sCommissionCreate.getResultMsg();
						FlagStr=sCommissionCreate.getRcode();
					}
			
					if("Succ".equals(FlagStr)){
						Content= "�ӿ��ļ����ɳɹ�";
					}else{
						Content=mResultMsg;
					}
			//Content = "���³ɹ�";
			}catch(Exception e){
				FlagStr = "Fail";
				Content= "����ʧ��";
			}
			
			}else{
			//�ύʧ��
				Content = Content +"�ӿ��ļ�����ʧ��";
			}
			}catch(Exception e){
				FlagStr = "Fail";
				Content= "����ʧ��"+mResultMsg;
				
			}
		}
		
		if (citiHand.mErrors.needDealError()) {
			Content = Content + ""
					+ citiHand.mErrors.getLastError();
		}else if(!citiHand.mErrors.needDealError()){
			if(citiHand.iEndNo==0){
					Content = Content +" ѡ����ϴ��ļ���ʽ����������ѡ���ϴ�";
					}
		}
		
		//FlagStr = "Succ";
		//System.out.println("---aaa");
	} else {
		tError = citiHand.mErrors;
		errMess = tError.getLastError();
		//System.out.println("---ccc");
		Content = errMess;
		FlagStr = "Fail";
	}
%>
<html>
	<script language=jscript.encode>
parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>");
</script>
</html>




