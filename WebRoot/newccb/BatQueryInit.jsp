<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%@page import="com.sinosoft.lis.pubfun.*" %>
<%@page import="org.jdom.*" %>
<%@page import="com.sinosoft.midplat.MidplatConf" %>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<% 
	//�������ƣ�XBTContInit.jsp
	
%>
<%  

String strTransExeDate = new SimpleDateFormat("yyyyMMddHHmmssSSS") .format(new Date() );
	PubFun1 pubfun = new PubFun1();
	String strTransRefGUID = pubfun.CreateMaxNo("TransNo",16);
	String strHOAppFormNumber = "2104121"+pubfun.CreateMaxNo("PrtNo",8);
	String InsNumber=pubfun.CreateMaxNo("InsNumber",3);
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
		alert("BatQueryInit.jsp-->InitForm11 �����з����쳣:��ʼ���������!");
	}
}



function initInputBox() 
{
	try {
	fm.TranCom.value='03';
	fm.TranComName.value='�й���������';
	fm.mSvPt_Jrnl_No.value='<%=strTransRefGUID%>';
	fm.mSYS_REQ_TIME.value='<%=strTransExeDate%>';
	fm.mCCB_EmpID.value='0005';
	fm.mCCBIns_ID.value='330613535';
	
	
	
	
fm.ip.value = '127.0.0.1';
	fm.port.value = '39871'; 
	fm.TRANSCODE.value = 'P53818102'; 

	}
	catch(re)
	{
		alert("BatQueryInit.jsp-->initInputBox22 �����з����쳣:��ʼ���������!");
	}
}



function initBox(){
	try{
	fm.TranCom.value='03';
	fm.TranComName.value='�й���������';
	fm.mSvPt_Jrnl_No.value='<%=strTransRefGUID%>';
	fm.mSYS_REQ_TIME.value='<%=strTransExeDate%>';
	fm.mIns_Bl_Prt_No.value='00000000879601005';
	fm.mCCB_EmpID.value='0005';
	fm.mCCBIns_ID.value='330613535';
	
	fm.ip.value = '127.0.0.1';
	fm.port.value = '39871'; 
	fm.TRANSCODE.value = 'P53818102'; 
		
	}catch(re){
		alert("BatQueryInit.jsp-->initBox()�����з����쳣����ʼ������");
	}

}


    

</script>
