<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%@page import="com.sinosoft.lis.pubfun.*" %>
<%@page import="org.jdom.*" %>
<%@page import="com.sinosoft.midplat.MidplatConf" %>
<% 
	//�������ƣ�JHYJWriteOffInit.jsp
	//�����ܣ����ȷ��   
	//�������ڣ�2014-11-14 16:45:36
	//������  ����·
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%  
	PubFun1 pubfun = new PubFun1();
	String strTransExeDate = DateUtil.getCur10Date();
	String strTransRefGUID = pubfun.CreateMaxNo("TransNo",16);
 	 String tIp = "127.0.0.1";
 	 String tPort = "35007";
  	 String tZoneNo = "1000";
  	 String tNodeNo = "1001";
%>
<script language="JavaScript">
function initForm()
{ 
	try 
	{
		initInputBox();
	}
	catch(re)
	{
		alert("JHYHWriteOffInit.jsp-->InitForm �����з����쳣:��ʼ���������!");
	}
}

function initBox()
{
try { 
//������Ϣ
	//���ж˽�������
	fm.TransExeDate.value = '<%=strTransExeDate%>';
	//������ˮ��
	fm.TransRefGUID.value = '<%=strTransRefGUID%>';
	//��������
	fm.RegionCode.value = '1000';
	//�������
	fm.Branch.value = '1001';
	//��Ա����
	fm.Teller.value = '1008711';
	//������
	fm.PolNumber.value = '';
 	//��֤ӡˢ�� 
	fm.ProviderFormNumber.value = strProviderFormNumber;
 	
	document.getElementById("divTranInfo").style.display="";
	document.getElementById("divTranInfoimg").src="../common/images/butExpand.gif";
	//������Ϣ
	document.getElementById("divContInfo").style.display="none";
	document.getElementById("divContInfoimg").src="../common/images/butCollapse.gif";
//
	fm.ip.value = '<%=tIp%>';
	fm.port.value = '<%=tPort%>';
	fm.tranFlagCode.value = 'divDate'; 
	}
	catch(re)
	{
		alert("JHYHWriteOffInit.jsp-->initBox �����з����쳣:��ʼ���������!");
	}
}


function initInputBox() 
{
	try {
//������Ϣ
	//���ж˽�������
	fm.TransExeDate.value = '<%=strTransExeDate%>';
	//������ˮ��
	fm.TransRefGUID.value = '<%=strTransRefGUID%>';
	//�������� 
	fm.RegionCode.value = '<%=tZoneNo%>';
	//�������  
	fm.Branch.value = '<%=tNodeNo%>';
	//��Ա����
	fm.Teller.value = '1008711';
	//����ӡˢ�� 
	fm.PolNumber.value = '';
	//��֤ӡˢ�� 
	fm.ProviderFormNumber.value = '';
	
	document.getElementById("divTranInfo").style.display="";
	document.getElementById("divTranInfoimg").src="../common/images/butExpand.gif";
	//������Ϣ
	document.getElementById("divContInfo").style.display="none";
	document.getElementById("divContInfoimg").src="../common/images/butCollapse.gif";
	fm.ip.value = '<%=tIp%>';
	fm.port.value = '<%=tPort%>';
	fm.tranFlagCode.value = 'divDate';
	}
	catch(re)
	{
		alert("JHYHWriteOffInit.jsp-->initInputBox �����з����쳣:��ʼ���������!");
	}
}


</script>
