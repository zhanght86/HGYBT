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
	String strProviderFormNumber = "BP"+PubFun1.CreateMaxNo("ContPrtNo",9);
	Element mTestUI = 
 		 MidplatConf.newInstance().getConf().getRootElement().getChild("TestUI");
 	 String tIp = mTestUI.getAttributeValue("ip");
 	 String tPort = mTestUI.getAttributeValue("port");
  	 String tZoneNo = mTestUI.getAttributeValue("ZoneNo");
  	 String tNodeNo = mTestUI.getAttributeValue("NodeNo");
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
		alert("TestYBTContInit.jsp-->InitForm �����з����쳣:��ʼ���������!");
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
	fm.Branch.value = '';
	//��Ա����
	fm.Teller.value = '';
	//������
	fm.PolNumber.value = '';
 	//��֤ӡˢ�� 
	fm.ProviderFormNumber.value = strProviderFormNumber;
	//ԭ��֤ӡˢ�� 
	//fm.OriginalProviderFormNumber.value = '';  

//
	fm.ip.value = '<%=tIp%>';
	fm.port.value = '<%=tPort%>';
	fm.tranFlagCode.value = '03'; 
	}
	catch(re)
	{
		alert("TestYBTContInit.jsp-->initBox �����з����쳣:��ʼ���������!");
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
	//�������� ����
	//fm.RegionCodeName.value = '�Ϻ��ֹ�˾';
	//�������  
	fm.Branch.value = '<%=tNodeNo%>';
	//������� ���� 
	//fm.BranchName.value = '��������';
	//��Ա����
	fm.Teller.value = '00001';
	
	//����ӡˢ�� 
	fm.PolNumber.value = '';
	//��֤ӡˢ�� 
	fm.ProviderFormNumber.value = '<%=strProviderFormNumber%>';
	//ԭ��֤ӡˢ�� 
	//fm.OriginalProviderFormNumber.value = '';
//document.getElementById("IdProviderFormNumber").disabled=true;
//document.getElementById("APPDOCNOId").disabled=true;
	fm.ip.value = '<%=tIp%>';
	fm.port.value = '<%=tPort%>';
	fm.tranFlagCode.value = '03';
	}
	catch(re)
	{
		alert("TestYBTContInit.jsp-->initInputBox �����з����쳣:��ʼ���������!");
	}
}


</script>
