
<%--*************************************************************************
 * 程序名称: Common.jsp
 * 程序功能: 公共代码
 * 最近更新人: 周咸立
 * 最近更新日期: 2002-01-16
 ************************************************************************--%>

<%@page import="java.text.*"%>
<%@page import="java.util.Vector"%>

<%!
  public String UserCode_PUB = "";
  public String UserName_PUB = "";
  public String ComName_PUB  = "";
  public String RiskCode_PUB = "";

    public String optionCodeForDcode(String strCodeName,String strRiskCode)
    {
      int i;
        String strReturn="";
        String strQueryCondition ="prpdcode.codetype^=^"+strCodeName+"^riskcode^=^"+strRiskCode+"^";
        Vector vector = new Vector();
        try
        {
          vector = Dcode.query(strQueryCondition);
        }
        catch(Exception exception)
        {
          vector=null;
        }
        if(vector==null) return strReturn;
        if(vector.size()>0)
        {
          for (i=0;i<vector.size();i++)
          {
          strReturn = strReturn + "<option value="+((Dcode)vector.elementAt(i)).Codecode + ">" ;
          strReturn = strReturn + ((Dcode)vector.elementAt(i)).CodeCName + "</option>\n" ;
          }
        }
        return strReturn;
    }

    /*-- 查询代码,返回代码包  --*/
    public String setOptionCode(String CodeName,String RiskCode)
    {
        Vector vecDcode                     = new Vector();
        String strQueryCondition    = "";
        String strReturn                    =   "";
        int i = 0;

        strQueryCondition = "prpdcode.codetype" + Str.DELIMITER+Str.EQUAL+Str.DELIMITER+CodeName+Str.DELIMITER;
                                     //+"riskcode" + Str.DELIMITER+Str.EQUAL+Str.DELIMITER+RiskCode+Str.DELIMITER;

        try
        {
          vecDcode = Dcode.query(strQueryCondition);
        }
        catch(Exception exception)
        {
          vecDcode = null;
        }

        if(vecDcode==null)
            return strReturn;
        if(vecDcode.size()>0)
        {
          for (i=0;i<vecDcode.size();i++)
          {
          strReturn=strReturn+ ((Dcode)vecDcode.elementAt(i)).Codecode + "=" ;
          strReturn=strReturn+ ((Dcode)vecDcode.elementAt(i)).CodeCName + "&" ;
          }
          strReturn=strReturn.substring(0,strReturn.length()-1);
        }
        return strReturn;
    }

  public void pgOut(JspWriter out,String strName,String strOldName)
  {
    try
    {
      out.println("value='"+ strName +"'");
      if( !strName.equals(strOldName) )
      {
        out.println("title='"+ strOldName +"'");
        out.println("style='background-Color:FFFF00'");
      }
    }
    catch(Exception exception)
    {
      Log.printException(exception.toString());
        return;
    }
  }
%>

<%!
  //判断用户在指定任务及类型、险种上的操作级别
  public int checkLevel(String strTask,String strCheckType,String strRiskCode,String strUserCode)
  {
    if (strTask == null
        || strCheckType == null
        || strRiskCode == null
        || strUserCode == null) return 0;
    Power power = new Power(strUserCode,strRiskCode);
    if (power.getData() != SysConst.SUCCESS ) return 0;
    power.taskcode = strTask;
    return power.getLevel(strCheckType);
  }
%>


<%
  /**
   *  函数定义结束,以下为执行代码
   */
  //登陆验证
  String UserCode_inc = (String)session.getValue("UserCode");
  String UserName_inc = (String)session.getValue("UserName");
  String ComCode_inc  = (String)session.getValue("ComCode");
  String ComName_inc  = (String)session.getValue("ComName");
  String RiskCode_inc = (String)session.getValue("RiskCode");
  String RiskName_inc = (String)session.getValue("RiskName");

  UserCode_PUB = UserCode_inc;
  UserName_PUB = UserName_inc;
  ComName_PUB  = ComName_inc;
  RiskCode_PUB = RiskCode_inc;

  if(UserCode_inc==null ||
     UserName_inc==null ||
     ComCode_inc==null ||
     ComName_inc==null ||
     RiskCode_inc==null ||
     RiskName_inc==null)
  {
    response.sendRedirect("LogonInput.jsp");
    return;
  }
%>
