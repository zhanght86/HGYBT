<%@ page contentType='text/html;charset=GBK'%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
</head>
<%/**************************************************************
 *               Program NAME: 读取后台代码                       
 *                 programmer: Ouyangsheng                    
 *                Create DATE: 2002.04.15                    
 *             Create address: Beijing                       
 *                Modify DATE:                               
 *             Modify address:                               
 *****************************************************************
 *                                                
 *         通用代码查询处理页面,包含在隐藏的框架中,输入
 *     过程中要显示代码清单时调用此页面
 * 
 *****************************************************************/%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  
  
<%
    String codename             = "";                 // 代码名称lx_code 
    String riskcode             = "";                 // 险种代码    
    String framename            = "";                 // Frame的名字 
    String strOther             = "";                 // 部门代码    
    String strValue             = "";
    String strError             = "";                 // 错误信息    
    int    intSize              = -1;                 // 记录条数信息 -1--null, 0--等于0, 1--大于0  
    String tStr                 = "";
    String intElementIndex      = "";                 // 输入域在整个Form中的序号 
    String intFunctionIndex     = "";                 // 1--给前一个域赋值 2--给后一个域赋值 
    String ex                   = "";                 // 鼠标坐标x   
    String ey                   = "";                 // 鼠标坐标y   
    String stationCode          = "";
  //得到请求页面的方式,以确定是初次下载页面,还是通过页面查询代码
    String strMethod = request.getMethod();
    
    //如果是POST方式请求页面,说明是要通过页面查询代码,执行以下查询程序,否则直接返回默认页面
    if(strMethod.equals("POST")) /* 以POST方式请求 */
    {
      System.out.println("==============GYJ9999======");
      //获得代码查询条件,包括代码类型,险种信息等
      codename          = request.getParameter("txtCodeName");
      tStr=codename.toLowerCase();
      framename         = request.getParameter("txtFrameName");
      strOther          = request.getParameter("txtOther");
      intFunctionIndex  = request.getParameter("intFunctionIndex");
      intElementIndex   = request.getParameter("intElementIndex");
      ex                = request.getParameter("ex");
      ey                = request.getParameter("ey");
      codename          = codename.trim();
      framename         = framename.trim();       
      strOther          = strOther.trim();      
      intElementIndex   = intElementIndex.trim(); 
      intFunctionIndex  = intFunctionIndex.trim(); 
      ex                = ex.trim();               
      ey                = ey.trim();               
      stationCode       = stationCode.trim();     
      CodeQueryUI tCodeQueryUI=new CodeQueryUI();
      VData tData=new VData();
      LDCodeSchema tLDCodeSchema =new LDCodeSchema();
      tLDCodeSchema.setCodeType(tStr);
      tData.add(tLDCodeSchema);

      tCodeQueryUI.submitData(tData,"QUERY||MAIN");
      if (tCodeQueryUI.mErrors.needDealError())
      {
        System.out.println(tCodeQueryUI.mErrors.getFirstError()) ;
      }
      else
      {
        tData.clear() ;
        tData=tCodeQueryUI.getResult() ;
        strValue=(String)tData.get(0);
      //  strValue=StrTool.unicodeToGBK(strValue);
    }

    }//当请求页面的方式为POST时的代码查询程序执行完毕

    //strValue="0|^001|总公司|总公司描述信息^002|北京分公司|北京分公司描述信息";
    System.out.println("----GYJ---"+strValue);
%>
 <%--查询结束后，将查询结果放在input域传回到客户端，并且重画页面，使其恢复到初始状态--%>

<form name='fm' action='CExec1.jsp' method='POST'>
  <input type="text" name="txtVarData" value='<%=strValue%>'>
  <input type="text" name="txtCodeName" value=''`>
  <input type="text" name="txtOther" value=''>
  <input type="text" name="ex" value=''>
  <input type="text" name="ey" value=''>
  <input type="text" name="intElementIndex" value=''>
  <input type="text" name="intFunctionIndex" value=''>
  <input type="text" name="txtFrameName" value=''>
  <input type="text" name="stationCode" value='<%=stationCode%>'>
</form>
<%
  //执行代码清单显示和选择的客户端JavaScript函数
  if(strMethod.equals("POST"))                 
  {
    out.println("<script language=javascript>");
    if(!strError.equals(""))
    {
      out.println("alert('"+strError+"');");
      out.println("</script>");
      return;
    }

 //   out.println("try{");
 //   out.println("  parent.document.frames('"+framename+"').initializeCode('"+codename+"');");
 //   out.println("}catch(exception){}"); 
    String tempStr="";
//    if ( (!strValue.equals(tempStr.valueOf(SysConst.NOTFOUND))) && (!strValue.equals(tempStr.valueOf(SysConst.FAILURE))) )
//    {
      out.println("try{"); 
      out.println("  parent.document.frames('"+framename+"').showCodeByList('"+intElementIndex+"','"+codename+"','"+intFunctionIndex+"','"+ex+"','"+ey+"');");
      out.println("}catch(exception){}"); 
//    }
    out.println("</script>");
  } 
%>
<SCRIPT language="JavaScript1.2">
  window.status="finished";
</SCRIPT>  
</html>