<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%@page import="com.sinosoft.lis.pubfun.*" %>
<%@page import="org.jdom.*" %>
<%@page import="com.sinosoft.midplat.MidplatConf" %>
<% 
	//�������ƣ�TestYBTWriteOffInit.jsp
	//�����ܣ����ȷ��   
	//�������ڣ�2011-06-14 16:43:36
	//������  ���ź���
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%  
	PubFun1 pubfun = new PubFun1();
	String strTransExeDate = DateUtil.getCur10Date();
	String strTransRefGUID = pubfun.CreateMaxNo("TransNo",16);
 	 String tIp = "127.0.0.1";
 	 String tPort = "14999";
  	 String tZoneNo = "10011";
  	 String tNodeNo = "1001114800";
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
		alert("TestYBTCCBWriteOffInit.jsp-->InitForm �����з����쳣:��ʼ���������!");
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
	fm.RegionCode.value = '';
	//�������
	fm.Branch.value = '322989036';
	//��Ա����
	fm.Teller.value = '';
	//������
	fm.PolNumber.value = '';
 	//��֤ӡˢ�� 
	fm.ProviderFormNumber.value = strProviderFormNumber;
 	
	document.getElementById("divTranInfo").style.display="";
	document.getElementById("divTranInfoimg").src="../common/images/butExpand.gif";
	//������Ϣ
	document.getElementById("divContInfo").style.display="none";
	document.getElementById("divContInfoimg").src="../common/images/butCollapse.gif";
	//�̵ƽ�����Ϣ
	document.getElementById("divDate").style.display="";
	document.getElementById("divDateimg").src="../common/images/butExpand.gif";
	//�ؿպ˶���Ϣ
	document.getElementById("divCheck").style.display="none";
	document.getElementById("divCheckimg").src="../common/images/butCollapse.gif";
	//�˱���Ϣ����
	document.getElementById("divCancelTranfor").style.display="none";
	document.getElementById("divCancelTranforimg").src="../common/images/butCollapse.gif";
	//ǩԼ��Լ����
	document.getElementById("divSignal").style.display="none";
	document.getElementById("divSignalimg").src="../common/images/butCollapse.gif";
//
	fm.ip.value = '<%=tIp%>';
	fm.port.value = '<%=tPort%>';
	fm.tranFlagCode.value = 'OPR999'; 
	}
	catch(re)
	{
		alert("TestYBTCCBWriteOffInit.jsp-->initBox �����з����쳣:��ʼ���������!");
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
	fm.Teller.value = '00001';
	//����ӡˢ�� 
	fm.PolNumber.value = '';
	//��֤ӡˢ�� 
	fm.ProviderFormNumber.value = '';
	
	document.getElementById("divTranInfo").style.display="";
	document.getElementById("divTranInfoimg").src="../common/images/butExpand.gif";
	//������Ϣ
	document.getElementById("divContInfo").style.display="none";
	document.getElementById("divContInfoimg").src="../common/images/butCollapse.gif";
	//�̵ƽ�����Ϣ
	document.getElementById("divDate").style.display="";
	document.getElementById("divDateimg").src="../common/images/butExpand.gif";
	//�ؿպ˶���Ϣ
	document.getElementById("divCheck").style.display="none";
	document.getElementById("divCheckimg").src="../common/images/butCollapse.gif";
	//�˱���Ϣ����
	document.getElementById("divCancelTranfor").style.display="none";
	document.getElementById("divCancelTranforimg").src="../common/images/butCollapse.gif";
	//ǩԼ��Լ����
	document.getElementById("divSignal").style.display="none";
	document.getElementById("divSignalimg").src="../common/images/butCollapse.gif";
	fm.ip.value = '<%=tIp%>';
	fm.port.value = '<%=tPort%>';
	fm.tranFlagCode.value = 'OPR999';
	}
	catch(re)
	{
		alert("TestYBTCCBWriteOffInit.jsp-->initInputBox �����з����쳣:��ʼ���������!");
	}
}


</script>
