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
     
     String sStartDay1 = DateUtil.getCur10Date();
 	 String sEndDay1 = DateUtil.getCur10Date();
 	 String sStartDay2 = DateUtil.getCur10Date();
 	 String sEndDay2 = DateUtil.getCur10Date();
%>                            

<script language="JavaScript">
function initForm()
{
  try
  { 
      fm.StartDay1.value = "<%=sStartDay1%>";
      fm.EndDay1.value = "<%=sEndDay1%>";
      fm.StartDay2.value = "<%=sStartDay2%>";
      fm.EndDay2.value = "<%=sEndDay2%>";
  }
  catch(ex)
  {
    alert("rehandCreateIFInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
</script>