<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%
//�������ƣ�LAComInInit.jsp
//�����ܣ�
//�������ڣ�2009-09-30
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     String gToday = PubFun.getCurrentDate(); //���ҳ��ؼ��ĳ�ʼ����
    GlobalInput tGI = new GlobalInput();
     tGI=(GlobalInput)session.getValue("GI");//���ҳ��ؼ��ĳ�ʼ����
     
     String sStartDay = DateUtil.getCur10Date();
 	 String sEndDay = DateUtil.getCur10Date();
%>                            

<script language="JavaScript">
	

function initForm()
{
  try
  { 
      fm.StartDay.value = "<%=sStartDay%>";
      fm.EndDay.value = "<%=sEndDay%>";
  }
  catch(ex)
  {
    alert("YBTdownloadInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
</script>