<%@page contentType="text/html;charset=gb2312" %>
<%@page import="java.util.*" %>
<%@page import="java.io.*" %>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>

<%
    //�ڴ����õ���Excel��������Ӧ��sql���ȡ���������Ӧ

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
    tLB.colName=new String[]{"������","Ͷ������", "Ͷ��������", "����������", "���ֱ���", "BAK1", "����", "����","����״̬", "ǩ������", "���׻���", "��������", "�ͻ�����","��Ա����"};
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
      System.out.println("����Excelʧ�ܣ�");
    };

%>
