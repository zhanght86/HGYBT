 <%@page contentType="text/html;charset=GBK"%>
   <%@page import="com.sinosoft.lis.pubfun.*"%>
    <%@page import="com.sinosoft.utility.*"%>
<%
   
   String tBranchType = "";
  
   String tSql1 =" BranchLevel";
   String tSqlAgentGrade =""; //限制代理人职级的查询条件（BranchType不同，职级显示不同）
   String tSqlDestAgentGrade =""; //上面所查职级在外加'清退00'
   String tSqlBranchLevel =""; //限制销售单位级别的查询条件（BranchType不同）
   String tSqlBranchLevel2 = "";
   String tSqlBranchLevelT = ""; //用于团体机构的级别
   String tSqlAgentGrade1 = "";
   String tSqlAgentGrade2 = "";
   String tSqlAgentGrade3 = "";
   String tSqlTollGrade = ""; //用于业务职级
   
   /* zsj */
   String tSqlAgentLine ="";//代理人系列查询初始化
   String tInitTollAgentDate = PubFun.getCurrentDate();
   
   try
   {
  
   
      	tBranchType = request.getParameter("BranchType");
      	System.out.println("BranchType:"+tBranchType);
      	if (tBranchType!=null)
   		session.putValue("BranchType",tBranchType);
   	tBranchType=(String)session.getValue("BranchType");
   	if(tBranchType=="1")
   	{
   		tSql1=" BranchLevel";
   	}
   	if(tBranchType=="2")//团险
   	{
   		tSql1=" BranchLevel2";
   		
   	}
   	if(tBranchType=="3")
   	{
   		tSql1=" BranchLevel";
   	}
   	
   	
   	 ExeSQL tExeSQL=new ExeSQL();
   String tSqlInitDate="select startDate,enddate from lastatsegment where startdate<='"+tInitTollAgentDate+"' and enddate>='"+tInitTollAgentDate+"' and stattype='5'";
   SSRS tSSRS=new SSRS();
   tSSRS=tExeSQL.execSQL(tSqlInitDate);
   if (tSSRS!=null&& tSSRS.getMaxRow()>0)
   {   
      if (!tInitTollAgentDate.equals(tSSRS.GetText(1,1)))
      {
         tInitTollAgentDate=PubFun.calDate(tSSRS.GetText(1,2),1,"D",null);
      }
      else
      {
      tInitTollAgentDate=tSSRS.GetText(1,1);
      }
      System.out.println("tInitTollAgentDate:"+tInitTollAgentDate);
   
   	}
   	/* liujw */   
   	tSqlAgentGrade = "1 and branchtype = #"+tBranchType+"#";   	   	
   	tSqlDestAgentGrade = "1 and (("+tBranchType+"=1 and (codealias is null or codealias = #"+tBranchType+"#)) or ("+tBranchType+"<>1 and codealias = #"+tBranchType+"#))";
   	tSqlBranchLevel = "1 and trim(branchlevelcode) <= #02#";
    tSqlAgentLine = "1 and trim(codetype)=#agentline#";   
   	tSqlBranchLevel2 = "branchtype ="+tBranchType+" and GradeProperty8 = 1 and BranchType2 ";
   	
   	/* liuyin */
   	tSqlBranchLevelT = "branchtype ="+tBranchType+" and BranchType2 ";
   	
   	/* zhangys */
   	tSqlTollGrade = "1 and branchtype = #"+tBranchType+"# and gradeid > 1";   
   	
   	/* zy */
   		//增员时只显示可以直接定级的职级代码
   	tSqlAgentGrade1 = "1 and branchtype = #"+tBranchType+"# and GradeProperty10 = #1#";
   	/*cg */
   	tSqlAgentGrade2 = "1 and branchtype = #"+tBranchType+"# and GradeProperty8 = #2#";
   	//考核时只显示外勤职级
   	tSqlAgentGrade3 = "1 and branchtype = #"+tBranchType+"# and GradeProperty7=#1#";
 
   }
   catch(Exception e)
   {}
%>
<script language="javascript">
 
 function getBranchType()
 {
 var tBranchType;
 tBranchType='<%=tBranchType%>';
 return tBranchType;
 }
 
  function getInitTollDate()
 {
 var tInitTollAgentDate;
 tInitTollAgentDate='<%=tInitTollAgentDate%>';
 
 return tInitTollAgentDate;
 }
 
</script>