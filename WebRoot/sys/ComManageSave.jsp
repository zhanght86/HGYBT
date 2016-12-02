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

	//操作类型(INSERT/UPDATE/DELETE)
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
	System.out.println("mComGrade是"+mComGrade);
	if(mComGrade.equals("02")){
		
		System.out.println("mUpComCode是"+mUpComCode);
		System.out.println("mOutComCode是"+mOutComCode);
		if(mOutComCode.length()!=4){
			throw new MidplatException("地区级管理机构代码必须是4位!");
		}
		else if(!(mOutComCode.substring(0,2).equals(mUpComCode))){
			throw new MidplatException("地区管理机构代码前二位必须与上级机构相同!");
		}
		char []c =mShortName.toCharArray();
		  for(char cc : c )
	                {
	                    if(cc<'A'|| cc>'Z'){
	                        throw new MidplatException("机构简称必须是大写字母!");
	                      }
	                }
	}else if(mComGrade.equals("03")){
	
		System.out.println("mUpComCode是"+mUpComCode);
		System.out.println("mOutComCode是"+mOutComCode);
		if(mOutComCode.length()!=6){
			throw new MidplatException("省级管理机构代码必须是6位!");
		}
		else if(!(mOutComCode.substring(0,4).equals(mUpComCode))){
			throw new MidplatException("省级管理机构代码前四位必须与上级机构相同!");
		}
		char []c =mShortName.toCharArray();
		  for(char cc : c )
	                {
	                    if(cc<'A'|| cc>'Z'){
	                        throw new MidplatException("机构简称必须是大写字母!");
	                      }
	                }
	}else if(mComGrade.equals("04")){
		System.out.println("mUpComCode是"+mUpComCode);
		System.out.println("mOutComCode是"+mOutComCode);
		char []c =mShortName.toCharArray();
		  for(char cc : c )
	                {
	                    if(cc<'A'|| cc>'Z'){
	                        throw new MidplatException("机构简称必须是大写字母!");
	                      }
	                }
		if(mOutComCode.length()!=9){
			throw new MidplatException("市级管理机构代码必须是9位!");
		}
		else if(!(mOutComCode.substring(0,6).equals(mUpComCode))){
			throw new MidplatException("市级管理机构代码前六位必须与上级机构相同!");
		}
	}
	aComManageUI.insert();
		} else if ("UPDATE".equals(mOperType)) {
	System.out.println("mComGrade是"+mComGrade);
	if(mComGrade.equals("02")){
		System.out.println("mUpComCode是"+mUpComCode);
		System.out.println("mOutComCode是"+mOutComCode);
		if(mOutComCode.length()!=4){
			throw new MidplatException("市级管理机构代码必须是4位!");
		}
		else if(!(mOutComCode.substring(0,2).equals(mUpComCode))){
			throw new MidplatException("管理机构代码前二位必须与上级机构相同!");
		}
	}else if(mComGrade.equals("03")){
		System.out.println("mUpComCode是"+mUpComCode);
		System.out.println("mOutComCode是"+mOutComCode);
		if(mOutComCode.length()!=6){
			throw new MidplatException("市级管理机构代码必须是6位!");
		}
		else if(!(mOutComCode.substring(0,4).equals(mUpComCode))){
			throw new MidplatException("管理机构代码前四位必须与上级机构相同!");
		}
	}else if(mComGrade.equals("04")){
		System.out.println("mUpComCode是"+mUpComCode);
		System.out.println("mOutComCode是"+mOutComCode);
		char []c =mShortName.toCharArray();
		  for(char cc : c )
	                {
	                    if(cc<'A'|| cc>'Z'){
	                        throw new MidplatException("机构简称必须是大写字母!");
	                      }
	                }
		if(mOutComCode.length()!=9){
			throw new MidplatException("市级管理机构代码必须是9位!");
		}
		else if(!(mOutComCode.substring(0,6).equals(mUpComCode))){
			throw new MidplatException("管理机构代码前六位必须与上级机构相同!");
		}
	}
	aComManageUI.update();
		} else if ("DELETE".equals(mOperType)) {
	aComManageUI.delete();
		} else {
	throw new MidplatException("操作码有误！" + mOperType);
		}

		mFlag = "Succ";
		mMessage = "操作成功！";
	} catch (Exception ex) {
		cLogger.error("操作失败！", ex);

		mFlag = "Fail";
		mMessage = "操作失败：" + ex.getMessage();
	}

	cLogger.info("out NodeManageSave.jsp!");
%>
<html>
	<script language="javascript">
parent.fraInterface.afterSubmit("<%=mFlag%>", "<%=mMessage%>");
</script>
</html>