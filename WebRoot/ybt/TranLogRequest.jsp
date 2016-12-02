<%@ page language="java" contentType="text/html; charset=GBK"%>
<jsp:directive.page import="com.sinosoft.lis.db.TranLogDB"/>
<jsp:directive.page import="com.sinosoft.midplat.MidplatConf"/>
<jsp:directive.page import="java.io.InputStream"/>
<jsp:directive.page import="java.io.File"/>
<jsp:directive.page import="java.io.BufferedReader"/>
<jsp:directive.page import="java.io.FileInputStream"/>
<jsp:directive.page import="java.io.InputStreamReader"/>
<jsp:directive.page import="java.io.FileNotFoundException"/>
<jsp:directive.page import="java.io.IOException"/>
<jsp:directive.page import="com.sinosoft.midplat.common.StringTool"/>
<jsp:directive.page import="com.sinosoft.lis.pubfun.GlobalInput"/>
<jsp:directive.page import="com.sinosoft.utility.CError"/>
    <%
//  准备通用参数
	String sManageCom = "";
	String sOperator = "";
	CError cError = new CError();
	String sHtml = "";
	File file = null;
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");
	if (tG == null) {
		System.out.println("登录信息没有获取!!!");
		return;
	} else if (tG != null) {
		sManageCom = tG.ManageCom;
		sOperator = tG.Operator;
	}
	
    int TranCom=0;
    int TranDate=0;
    String InNoDoc="";
    String OutNoDoc="";
    InputStream is = null; 
    String tSavePath = MidplatConf.newInstance().getConf().getRootElement().getChildText("SavePath");
    String LOGNO=request.getParameter("TranLog");
    String str="";
	String string1="\n";
	String string2="\n";
	String fileinName="";
	String fileoutName="";
    if(LOGNO!=""){
    TranLogDB td=new TranLogDB();
    td.setLogNo(LOGNO);
    if(td.getInfo()){
    	TranCom= td.getTranCom();
    	TranDate= td.getMakeDate();
    	InNoDoc=td.getInNoDoc();
    	OutNoDoc=td.getOutNoDoc();
    	if(TranCom!=0){
    		tSavePath+="msg/0"+TranCom;
    		if((String.valueOf(TranDate).length()==8)){
    			tSavePath+="/"+String.valueOf(TranDate).substring(0,4);
    			tSavePath+="/"+String.valueOf(TranDate).substring(0,6);
    			tSavePath+="/"+String.valueOf(TranDate).substring(0,8);
    		}
    	}
    }
    String filei=tSavePath+"/"+InNoDoc;
	String fileou=tSavePath+"/"+OutNoDoc;
	if(filei.indexOf(' ')!=-1){
	      fileinName=filei.replaceAll(" ","%20");
	}
	else {
		fileinName=filei;
	}
		if(fileou.indexOf(' ')!=-1){
	        fileoutName=fileou.replaceAll(" ","%20");
		}else {
			fileoutName=fileou;
		}
	
    //File filein=new File(fileinName);
	//File fileout=new File(fileoutName);
	File filein=new File(filei);
	File fileout=new File(fileou);
	try{
	if (!filein.exists()) {
		System.out.println(filein.getAbsolutePath() + " 文件不存在!");
		fileinName="";
	}if (!fileout.exists()) {
		System.out.println(filein.getAbsolutePath() + " 文件不存在!");
		fileoutName="";
	}
	}catch (Exception e) {
		e.printStackTrace();
	}
	System.out.println(fileinName);
	System.out.println(fileoutName);
    BufferedReader br = null;
    try {
		is= new FileInputStream(filei);
	   br = new BufferedReader(new InputStreamReader(is));
		while ((str = br.readLine()) != null) {
			string1+=str;
			string1+="\n";
		}
		if(fileout.exists()){
		is= new FileInputStream(fileou);
		   br = new BufferedReader(new InputStreamReader(is));
		while ((str = br.readLine()) != null) {
			string2+=str;
			string2+="\n";
		}
		}
	} catch (FileNotFoundException e1) {
		e1.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	//System.out.println(string1);
	//System.out.println(string2);
	string1=StringTool.toHTMLValueFormat(string1);
	string2=StringTool.toHTMLValueFormat(string2);
	//System.out.println(string1);
	//System.out.println(string2);
    }
    %>
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
<META HTTP-EQUIV="expires" CONTENT="0">
<title>报文展示页面</title>
<SCRIPT type="text/javascript" src="../js/jquery-1.3.2.min.js"></SCRIPT>
<script type="text/javascript" src="../js/scripts-pack.js"></script>
<script type="text/javascript" src="../js/jquery-ui-1.7.2.custom.min.js"></script>
<script type="text/javascript" src="../js/jquery.chromatable.js"></script>
		<script src="../common/javascript/Common.js">
</script>
		<script src="../common/cvar/CCodeOperate.js">
</script>
		<script src="../common/Calendar/Calendar.js">
</script>
		<script src="../common/easyQueryVer3/EasyQueryCache.js">
</script>
		<script src="../common/easyQueryVer3/EasyQueryVer3.js">
</script>
		<script src="../common/easyQueryVer3/EasyQueryPrint.js">
</script>
		<script src="../common/javascript/MulLine.js">
</script>
		<script src="../common/javascript/VerifyInput.js">
</script>
<script src="../common/javascript/AXANodeMap.js">
</script>
		<script src="./TranLogRequest.js">
</script>

		<link href="../common/css/Project.css" rel=stylesheet type=text/css />
		<link href="../common/css/mulLine.css" rel=stylesheet type=text/css />

</head>
<body >
<form action="" method=post name=fm target="fraSubmit">
<input type="hidden" name="filein" value=<%= fileinName%>>
<input type="hidden" name="fileout" value=<%= fileoutName%>>
<table> 
 
<TD  class="cssbutton"  width="26%">
        <INPUT VALUE="关闭页面" class="cssbutton" type="button" onclick="returnParent();">
</TD>
 </table> 
<hr>
<table class="common">
		<tr>
			<td class="titleImg" align="center">
			<IMG src="../common/images/butExpand.gif" style="cursor: hand;"
							OnClick="showPage(this,divShow);"></IMG>
                   <INPUT VALUE="下载请求报文" class="cssbutton" type="button" onclick="DownloadRequest();">
                      </TD>
				</tr>
				</table>
<div id="divShow" style="display:''">
<table id='exceltitletable' width="624"   border="0" cellspacing="0" cellpadding="0">
<%=string1 %>
</table>
</div>
<table class="common">
		<tr>
			<td class="titleImg" align="center">
			<IMG src="../common/images/butExpand.gif" style="cursor: hand;"
							OnClick="showPage(this,divShowTo);"></IMG>
                   <INPUT VALUE="下载返回报文" class="cssbutton" type="button" onclick="DownloadResponse();">
                      </TD>
				</tr>
				</table>
<div id="divShowTo" style="display:''">
<table id='exceltable'  width="624"  border="0" cellspacing="0" cellpadding="0">
<%=string2%>
<thead>  
</thead>
<tbody>
</tbody>
</table>
</div>
</form>
</body>
</html>