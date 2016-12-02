<%
//程序名称：EasyQueryKernel.jsp
//程序功能：EasyQuery查询功能的核心函数
//创建日期：2002-09-28
//创建人  ：胡博
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>

<%!
//调用EasyQueryUI进行SQL语句提交和数据库查找，返回结果字符串strResult
public String easyQueryKernel(String strSql, String strStart, String strLargeFlag, String strLimitFlag)
{
    String strResult = "";
    String strError = "";
    Integer intStart;
    Integer intLargeFlag;
    Integer intLimitFlag;

    //strStart
    if (strStart == null || strStart.trim().equals("") || strStart.equals("undefined"))
    {
        intStart = new Integer(String.valueOf("1"));
    }
    else
    {
        intStart = new Integer(strStart);
    }

    //strLargeFlag
    if (strLargeFlag == null || strLargeFlag.trim().equals("") || strLargeFlag.equals("undefined"))
    {
        intLargeFlag = new Integer(String.valueOf("0"));
    }
    else
    {
        intLargeFlag = new Integer(strLargeFlag);
    }

    //strLimitFlag
    if (strLimitFlag == null || strLimitFlag.trim().equals("") || strLimitFlag.equals("undefined"))
    {
        intLimitFlag = new Integer(String.valueOf("0"));
    }
    else
    {
        intLimitFlag = new Integer(strLimitFlag);
    }

    VData tVData = new VData();
    tVData.add(strSql);
    tVData.add(intStart);
    tVData.add(intLargeFlag);    //2005-04-18 zhuxf 添加大数据量查询标志
    tVData.add(intLimitFlag);    //2006-09-29 XinYQ 添加突破 200 条限制的查询标志

    EasyQueryUI tEasyQueryUI = new EasyQueryUI();
    tEasyQueryUI.submitData(tVData, "QUERY||MAIN");
    if(tEasyQueryUI.mErrors.needDealError())
    {
        strError = tEasyQueryUI.mErrors.getFirstError();
    }
    else
    {
        tVData.clear() ;
        tVData = tEasyQueryUI.getResult() ;
        strResult = (String)tVData.getObject(0);
    }

    return strResult;
}
%>
