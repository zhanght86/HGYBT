<%@ page contentType='text/html;charset=GBK'%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
</head>
<%
/**************************************************************
 *               Program NAME: ��ȡ��̨����
 *                 programmer: Ouyangsheng
 *                Create DATE: 2002.04.15
 *             Create address: Beijing
 *                Modify DATE:
 *             Modify address:
 *****************************************************************
 *
 *         ͨ�ô����ѯ����ҳ��,���������صĿ����,����
 *     ������Ҫ��ʾ�����嵥ʱ���ô�ҳ��
 *
 *****************************************************************/
  
  //Added by wellhi 2007-12-26 ������������Bug
	response.setHeader("Pragma","No-cache");
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires", 0);
%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.schema.LDCodeSchema"%>
<%@page import="com.sinosoft.utility.*"%>
<%
//System.out.println("\n\n\n---Start InitIndex CExec.jsp---\n\n");
String codename             = "";	// ��������lx_code
String riskcode             = "";	// ���ִ���
String framename            = "";	// Frame������
String strOther             = "";	// ���Ŵ���
String strValue             = "";
String strError             = "";	// ������Ϣ
int    intSize              = -1;	// ��¼������Ϣ -1--null, 0--����0, 1--����0
String tStr                 = "";
String codeCondition        = "";	//�����ѯ����
String conditionField       = "";	//�����ѯ������Ӧ�����ݿ��ֶ�
String showWidth            = "";	//������Ŀ��
String changeEven			= "";	//����ѡ��ʱ�������¼�

//�õ�����ҳ��ķ�ʽ,��ȷ���ǳ�������ҳ��,����ͨ��ҳ���ѯ����
String strMethod = request.getMethod();

//�����POST��ʽ����ҳ��,˵����Ҫͨ��ҳ���ѯ����,ִ�����²�ѯ����,����ֱ�ӷ���Ĭ��ҳ��
/* ��POST��ʽ���� */
if(strMethod.equals("POST"))
{
	if( !request.getParameter( "mOperate" ).equals( "EASYQUERY" ))
	{
		//��ô����ѯ����,������������,������Ϣ��
		codename          = request.getParameter("txtCodeName");
		tStr=codename.toLowerCase();
		framename         = request.getParameter("txtFrameName");
		strOther          = request.getParameter("txtOther");
		codename          = codename.trim();
		framename         = framename.trim();
		strOther          = strOther.trim();
		codeCondition     = request.getParameter("txtCodeCondition");
		conditionField    = request.getParameter("txtConditionField");
		showWidth         = request.getParameter("txtShowWidth");
		changeEven		  = request.getParameter("changeEven");

		CodeQueryUI tCodeQueryUI=new CodeQueryUI();
		VData tData=new VData();
		LDCodeSchema tLDCodeSchema =new LDCodeSchema();
		tLDCodeSchema.setCodeType(tStr);

		GlobalInput tGI = new GlobalInput();
		try
		{
			tGI = (GlobalInput)session.getValue("GI");
		}
		catch(Exception ex)
		{
			tGI = new GlobalInput();
			System.out.println("GlobalInput is null");
		}

		tData.add(tLDCodeSchema);
		tData.add(tGI);

		//���ݲ�ѯ����
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("codeCondition", codeCondition);
		tTransferData.setNameAndValue("conditionField", conditionField);
		tData.add(tTransferData);

		tCodeQueryUI.submitData(tData,"QUERY||MAIN");
		if (tCodeQueryUI.mErrors.needDealError())
		{
			System.out.println("CodeQueryUI throw Errors:" + tCodeQueryUI.mErrors.getFirstError()) ;
			strValue="Code Query Faile";
		}
		else
		{
			tData.clear() ;
			tData=tCodeQueryUI.getResult() ;
			strValue=(String)tData.get(0);
		}
	}
}
//������ҳ��ķ�ʽΪPOSTʱ�Ĵ����ѯ����ִ�����
//strValue="0|^001|�ܹ�˾|�ܹ�˾������Ϣ^002|�����ֹ�˾|�����ֹ�˾������Ϣ";
//��ѯ�����󣬽���ѯ�������input�򴫻ص��ͻ��ˣ������ػ�ҳ�棬ʹ��ָ�����ʼ״̬
%>
<form name='fm' action='CExec.jsp' method='POST'>
	<input type='hidden' name='txtVarData' value='<%=strValue%>'>
	<input type='hidden' name='txtCodeName' value=''>
	<input type='hidden' name='txtOther'>
	<input type='hidden' name='txtFrameName'>
	<input type='hidden' name='txtSQL'>
	<input type='hidden' name='startIndex'>
	<input type='hidden' name='txtQueryResult'>
	<input type='hidden' name='mOperate'>
	<input type='hidden' name='txtCodeCondition'>
	<input type='hidden' name='txtConditionField'>
	<input type='hidden' name='txtShowWidth'>
	<input type='hidden' name='changeEven'>
</form>
<%
//ִ�д����嵥��ʾ��ѡ��Ŀͻ���JavaScript����
if(strMethod.equals("POST"))
{
	if( !request.getParameter( "mOperate" ).equals( "EASYQUERY" ))
	{
		out.println("<script language=javascript>");
		if(!strError.equals(""))
		{
			out.println("alert('"+strError+"');");
			out.println("</script>");
			return;
		}

		out.println("try{");
		out.println("  parent.document.frames('"+framename+"').initializeCode('"+codename+"', '" + codeCondition + "' , '" + conditionField + "');");
		out.println("}catch(exception){}");
		String tempStr="";
		out.println("try{");
		out.println("  parent.document.frames('"+framename+"').showCodeList('"+codename+"',null,null,null, '" + codeCondition + "' , '" + conditionField + "' , null, '" + showWidth + "',"+changeEven+");");
		out.println("}catch(exception){}");
		out.println("</script>");
	}
	else
	{
		// easyQuery����
		String tSQL = request.getParameter( "txtSQL" );
		String tStart = request.getParameter( "startIndex" );
		Integer tIntStart = new Integer( String.valueOf( tStart ));
		//String tFrameName = request.getParameter( "txtFrameName" );
		String tResult = "";
		String tError = "";

		EasyQueryUI tEasyQueryUI = new EasyQueryUI();
		VData tVData = new VData();
		tVData.add( tSQL );
		tVData.add( tIntStart );

		tEasyQueryUI.submitData( tVData, "QUERY||MAIN" );
		if( tEasyQueryUI.mErrors.needDealError())
		{
			tError = tEasyQueryUI.mErrors.getFirstError();
		}
		else
		{
			tVData.clear() ;
			tVData = tEasyQueryUI.getResult() ;
			tResult = ( String )tVData.getObject( 0 );
%>
<script language="javascript">
fm.all('txtQueryResult').value = '<%= tResult %>';
</script>
<%
		}
		out.println("<script language=javascript>");
		out.println("try{");
		out.println("  parent.fraInterface.afterEasyQuery('" + tError + "');");
		out.println("}catch(exception){}");
		out.println("</script>");
	}
}
%>
<SCRIPT language="JavaScript1.2">
window.status="finished";
try
{
	top.achieveEX = true;	//�����ж�ҳ���ʼ�����
}
catch(ex)
{}
</SCRIPT>
</html>