<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>

<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : ���� <HuBo@sinosoft.com.cn>, ����ǿ <XinYQ@sinosoft.com.cn>
 * @version  : 1.00, 1.10
 * @date     : 2002-10-18, 2006-06-30
 * @direction: ���ò�ѯ LDCode �ȴ�ҳ��
 ******************************************************************************/
 	//Added by wellhi 2007-12-26 ������������Bug
 	response.setHeader("Pragma","No-cache");
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires", 0);
%>


<%@ page import="com.sinosoft.lis.pubfun.*" %>

<%@ include file="CodeQueryKernel.jsp" %>

<%
    //�������ݱ���
    String sCodeType = request.getParameter("codeType");
    String sCodeField = request.getParameter("codeField");
    String sCodeConditon = request.getParameter("codeConditon");
    String sQueryResult = new String("");

    //�ռ���������
    //GlobalInput
    GlobalInput tGlobalInput = new GlobalInput();
    tGlobalInput = (GlobalInput)session.getValue("GI");

    //��ʾ��־��Ϣ
    //Remarked by wellhi 2007-12-26 ȥ�������־���������Ļ���������־����ʵ�ô�������Ҫ�����ٷų���
    //System.out.println("");
    //System.out.println("========== CodeQuery BGN ==========");
    //System.out.println("CodeType  : " + sCodeType);

    //���ú�̨��ѯ
    try
    {
        if (sCodeField == null || sCodeField.equals("") || sCodeConditon == null || sCodeConditon.equals(""))
        {
            sQueryResult = codeQueryKernel(sCodeType, tGlobalInput);
        }
        else
        {
            sQueryResult = codeQueryKernel2(sCodeType, sCodeField, sCodeConditon, tGlobalInput);
        }
    }
    catch (Exception ex)
    {
        System.out.println("@> Errors occured when CodeQuery executing !");
    }

    System.out.println("========== CodeQuery END ==========");
    System.out.println("");
    tGlobalInput = null;

    //ҳ�淴����Ϣ
    out.println("<script language='JavaScript'>");
    out.println("    try");
    out.println("    {");
    if (sQueryResult == null || sQueryResult.equals(""))
    {
        out.println("        window.returnValue = 'Code Query Failed';");
    }
    else
    {
        out.println("        window.returnValue = '" + sQueryResult + "';");
    }
    out.println("        window.close();");
    out.println("    }");
    out.println("    catch (ex) {}");
    out.println("</script>");
%>
