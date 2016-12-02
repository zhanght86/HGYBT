<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>

<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%@page import="com.sinosoft.midplat.exception.*"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.midplat.manage.ComManageUI"%>

<%
	Logger cLogger = Logger.getLogger(getClass());
	cLogger.info("into NodeManageSave.jsp...");

	GlobalInput cGlobalInput = (GlobalInput) session.getValue("GI");

	//��������(INSERT/UPDATE/DELETE)
	String mOperType = request.getParameter("OperType");
	
	String mComGrade = request.getParameter("iComGrade");
	//String mAreaNo = request.getParameter("iAreaNo");
	//String mAreaName = request.getParameter("iAreaName");
	String mOutComCode = request.getParameter("iOutComCode");
	String mComCode = request.getParameter("iOutComCode");
	String mName = request.getParameter("iName");
	String mShortName = request.getParameter("iShortName");
	String mUpComCode = request.getParameter("UpComCode");
	
	System.out.println("mComGrade" + mComGrade);
	//System.out.println("mAreaNo" +mAreaNo);
	//System.out.println("mAreaName" + mAreaName);
	System.out.println("mOutComCode" + mOutComCode);
	System.out.println("mComCode" + mComCode);
	System.out.println("mName" +mName );
	System.out.println("mShortName" + mShortName);
	System.out.println("mUpComCode" + mUpComCode);
	
	
	String mFlag = "";
	String mMessage = "";
	
	
	VData vdate = new VData();
	TransferData tTransferData = new TransferData();
	tTransferData.setNameAndValue("ComGrade", mComGrade);
	//tTransferData.setNameAndValue("AreaID", mAreaNo);
	//tTransferData.setNameAndValue("AreaName", mAreaName);
	tTransferData.setNameAndValue("OutComCode", mOutComCode);
	tTransferData.setNameAndValue("ComCode", mComCode);
	tTransferData.setNameAndValue("Name", mName);
	tTransferData.setNameAndValue("ShortName", mShortName);
	tTransferData.setNameAndValue("UpComCode", mUpComCode);


	vdate.add(tTransferData);
	vdate.add(cGlobalInput);



	
	try {
		ComManageUI aComManageUI = new ComManageUI(vdate);
	
		if ("INSERT".equals(mOperType)) {
	System.out.println("mComGrade��"+mComGrade);
	if(mComGrade.equals("02")){
		
		System.out.println("mUpComCode��"+mUpComCode);
		System.out.println("mOutComCode��"+mOutComCode);
		if(mOutComCode.length()!=4){
			throw new MidplatException("����������������������4λ!");
		}
		else if(!(mOutComCode.substring(0,2).equals(mUpComCode))){
			throw new MidplatException("���������������ǰ��λ�������ϼ�������ͬ!");
		}
		char []c =mShortName.toCharArray();
		  for(char cc : c )
	                {
	                    if(cc<'A'|| cc>'Z'){
	                        throw new MidplatException("������Ʊ����Ǵ�д��ĸ!");
	                      }
	                }
	}else if(mComGrade.equals("03")){
	
		System.out.println("mUpComCode��"+mUpComCode);
		System.out.println("mOutComCode��"+mOutComCode);
		if(mOutComCode.length()!=6){
			throw new MidplatException("ʡ������������������6λ!");
		}
		else if(!(mOutComCode.substring(0,4).equals(mUpComCode))){
			throw new MidplatException("ʡ�������������ǰ��λ�������ϼ�������ͬ!");
		}
		char []c =mShortName.toCharArray();
		  for(char cc : c )
	                {
	                    if(cc<'A'|| cc>'Z'){
	                        throw new MidplatException("������Ʊ����Ǵ�д��ĸ!");
	                      }
	                }
	}else if(mComGrade.equals("04")){
		System.out.println("mUpComCode��"+mUpComCode);
		System.out.println("mOutComCode��"+mOutComCode);
		char []c =mShortName.toCharArray();
		  for(char cc : c )
	                {
	                    if(cc<'A'|| cc>'Z'){
	                        throw new MidplatException("������Ʊ����Ǵ�д��ĸ!");
	                      }
	                }
		if(mOutComCode.length()!=9){
			throw new MidplatException("�м�����������������9λ!");
		}
		else if(!(mOutComCode.substring(0,6).equals(mUpComCode))){
			throw new MidplatException("�м������������ǰ��λ�������ϼ�������ͬ!");
		}
	}
	aComManageUI.insert();
		} else if ("UPDATE".equals(mOperType)) {
	System.out.println("mComGrade��"+mComGrade);
	if(mComGrade.equals("02")){
		System.out.println("mUpComCode��"+mUpComCode);
		System.out.println("mOutComCode��"+mOutComCode);
		if(mOutComCode.length()!=4){
			throw new MidplatException("�м�����������������4λ!");
		}
		else if(!(mOutComCode.substring(0,2).equals(mUpComCode))){
			throw new MidplatException("�����������ǰ��λ�������ϼ�������ͬ!");
		}
	}else if(mComGrade.equals("03")){
		System.out.println("mUpComCode��"+mUpComCode);
		System.out.println("mOutComCode��"+mOutComCode);
		if(mOutComCode.length()!=6){
			throw new MidplatException("�м�����������������6λ!");
		}
		else if(!(mOutComCode.substring(0,4).equals(mUpComCode))){
			throw new MidplatException("�����������ǰ��λ�������ϼ�������ͬ!");
		}
	}else if(mComGrade.equals("04")){
		System.out.println("mUpComCode��"+mUpComCode);
		System.out.println("mOutComCode��"+mOutComCode);
		char []c =mShortName.toCharArray();
		  for(char cc : c )
	                {
	                    if(cc<'A'|| cc>'Z'){
	                        throw new MidplatException("������Ʊ����Ǵ�д��ĸ!");
	                      }
	                }
		if(mOutComCode.length()!=9){
			throw new MidplatException("�м�����������������9λ!");
		}
		else if(!(mOutComCode.substring(0,6).equals(mUpComCode))){
			throw new MidplatException("�����������ǰ��λ�������ϼ�������ͬ!");
		}
	}
	aComManageUI.update();
		} else if ("DELETE".equals(mOperType)) {
	aComManageUI.delete();
		} else {
	throw new MidplatException("����������" + mOperType);
		}

		mFlag = "Succ";
		mMessage = "�����ɹ���";
	} catch (Exception ex) {
		cLogger.error("����ʧ�ܣ�", ex);

		mFlag = "Fail";
		mMessage = "����ʧ�ܣ�" + ex.getMessage();
	}

	cLogger.info("out NodeManageSave.jsp!");
%>
<html>
	<script language="javascript">
parent.fraInterface.afterSubmit("<%=mFlag%>", "<%=mMessage%>");
</script>
</html>