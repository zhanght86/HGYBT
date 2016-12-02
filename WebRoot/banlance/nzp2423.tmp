<%@page contentType="text/html;charset=gb2312" %>
<%@page import="java.util.*" %>
<%@page import="java.io.*" %>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>

<%
    //在此设置导出Excel的列名，应与sql语句取出的域相对应

    ExportExcel.Format format = new ExportExcel.Format();
    ArrayList listCell = new ArrayList();
    ArrayList listLB = new ArrayList();
    ArrayList listColWidth = new ArrayList();
    format.mListCell=listCell;
    format.mListBL=listLB;
    format.mListColWidth=listColWidth;

    ExportExcel.Cell tCell=null;
    ExportExcel.ListBlock tLB=null;


    listColWidth.add(new String[]{"0","5000"});
    String strSQL = "";
    strSQL = (String)session.getAttribute("SQLString");
    session.removeAttribute("SQLString");
    String strSql = request.getParameter("strSQL");  
    EasyQuerySql tEasyQuerySql=new EasyQuerySql();
    tEasyQuerySql.parsePara(strSql);
    strSQL = tEasyQuerySql.convertToValue(strSQL);
    System.out.println("[" + strSQL + "]");

    
    tLB = new ExportExcel.ListBlock("001");
    tLB.colName=new String[]{"保单号","投保单号", "投保人姓名", "被保人姓名", "险种编码", "BAK1", "保额", "保费","保单状态", "签单日期", "交易机构", "交易网点", "客户经理","柜员代码"};
    tLB.sql=strSQL;
    tLB.row1=0;
    tLB.col1=0;
    tLB.InitData();
    listLB.add(tLB);


    try
    {

      response.reset();
      response.setContentType("application/octet-stream");
      response.setHeader("Content-Disposition","attachment; filename=YBT_"+PubFun.getCurrentDate()+"_List.xls");
      OutputStream outOS=response.getOutputStream();

      BufferedOutputStream bos=new BufferedOutputStream(outOS);

      ExportExcel excel = new ExportExcel();

      excel.write(format,bos);

			out.clear();
			out = pageContext.pushBody();

      bos.flush();
      bos.close();
    }
    catch(Exception e)
    {
      System.out.println("导出Excel失败！");
    };

%>
