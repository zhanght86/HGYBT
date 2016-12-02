<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%
//程序名称：LAComInInit.jsp
//程序功能：
//创建日期：2009-09-30
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     String gToday = PubFun.getCurrentDate(); //添加页面控件的初始化。
    GlobalInput tGI = new GlobalInput();
     tGI=(GlobalInput)session.getValue("GI");//添加页面控件的初始化。
     
     String sTranDay = DateUtil.getCur10Date();
%>                            

<script language="JavaScript">
	

function initForm()
{
  try
  { 
      fm.TranDay.value = "<%=sTranDay%>";
  }
  catch(ex)
  {
    alert("YBTdownloadInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
</script>