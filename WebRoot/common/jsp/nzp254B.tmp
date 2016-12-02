<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>

<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : 胡博 <HuBo@sinosoft.com.cn>, 辛玉强 <XinYQ@sinosoft.com.cn>
 * @version  : 1.00, 1.10
 * @date     : 2002-10-18, 2006-06-30
 * @direction: 公用查询 LDCode 等待页面
 ******************************************************************************/
 	//Added by wellhi 2007-12-26 解决下拉缓存的Bug
 	response.setHeader("Pragma","No-cache");
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires", 0);
%>


<%@ page import="com.sinosoft.lis.pubfun.*" %>

<%@ include file="CodeQueryKernel.jsp" %>

<%
    //接收数据变量
    String sCodeType = request.getParameter("codeType");
    String sCodeField = request.getParameter("codeField");
    String sCodeConditon = request.getParameter("codeConditon");
    String sQueryResult = new String("");

    //收集整理数据
    //GlobalInput
    GlobalInput tGlobalInput = new GlobalInput();
    tGlobalInput = (GlobalInput)session.getValue("GI");

    //显示日志信息
    //Remarked by wellhi 2007-12-26 去掉这个日志输出，满屏幕都是这个日志，其实用处不大，需要调试再放出来
    //System.out.println("");
    //System.out.println("========== CodeQuery BGN ==========");
    //System.out.println("CodeType  : " + sCodeType);

    //调用后台查询
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

    //页面反馈信息
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
