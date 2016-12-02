<%!
Connection conn;
PreparedStatement stmt;
Statement st;
ResultSet rs;
int SubHeight=20;
boolean explanAll = false;

void MenuList(String ManageCom, String UpAgentCom, String Name, int position,StringBuffer outHTML,boolean init,String BankType,String BranchType,String ACType,String AllType){
	System.out.println(ManageCom + UpAgentCom);
	
	Vector childList=new Vector();
	Vector nameList=new Vector();
	try{
		if(UpAgentCom == null || UpAgentCom.equals(""))
		{
			String SQLStr="";
			if(BranchType.equals("3"))
			{
				if(ManageCom.length()!= 8)
				{
					if(ManageCom.length()== 2)
						SQLStr = "SELECT AgentCom,Name FROM lacom WHERE ManageCom = '86' and State='N' and agentcom like '"+BankType+"%' and BranchType='"+BranchType+"' order by AgentCom";
					else
						SQLStr = "SELECT AgentCom,Name FROM lacom WHERE ManageCom = '"+ManageCom.substring(0,ManageCom.length()-2)+"' and State='N' and agentcom like '"+BankType+"%' and BranchType='"+BranchType+"' order by AgentCom";
				}
				else
					SQLStr = "SELECT AgentCom,Name FROM lacom WHERE ManageCom = '"+ManageCom+"' and BankType='03' and State='N' and agentcom like '"+BankType+"%' and BranchType='"+BranchType+"' order by AgentCom";
			}
			else if(BranchType.equals("2"))
			{
				if(ManageCom.length()!= 8)
				{
					if(ManageCom.length()== 2)
						SQLStr = "SELECT AgentCom,Name FROM lacom WHERE ManageCom = '86' and State='N' and agentcom like '"+BankType+"%' and BranchType='"+BranchType+"' and ACType='"+ACType+"'";
					else
						SQLStr = "SELECT AgentCom,Name FROM lacom WHERE ManageCom = '"+ManageCom.substring(0,ManageCom.length()-2)+"' and State='N' and agentcom like '"+BankType+"%' and BranchType='"+BranchType+"' and ACType='"+ACType+"'";
				}
				else
					SQLStr = "SELECT AgentCom,Name FROM lacom WHERE ManageCom = '"+ManageCom+"' and BankType='03' and State='N' and agentcom like '"+BankType+"%' and BranchType='"+BranchType+"' and ACType='"+ACType+"'";
				
				if(AllType.equals("0"))
					SQLStr += " and not exists(select '1' from lacont where agentcom = lacom.agentcom)";
				SQLStr += " order by AgentCom";
			}
			
			rs = st.executeQuery(SQLStr);
			System.out.println("#####"+SQLStr);
		}
		
		else
		{
			stmt.setString(1,UpAgentCom);
			stmt.setString(2,BranchType);
			stmt.setString(3,ACType);
			rs=stmt.executeQuery();
		}
		
  	while(rs.next())
  	{
			childList.addElement(rs.getString("AgentCom"));
			nameList.addElement(rs.getString("Name"));
  	}
	}catch(Exception e)
	{
		outHTML.append(e.toString());
	}
	
	int msize=childList.size();
	if(msize>0)
	{
		if(!init)
		{//不为根节点
			outHTML.append("<td class=\"OutMenu\" dix='"+position+"' onClick=\"SubThis('"+UpAgentCom+"')\" onMouseOver=\"OverItem(this,'"+UpAgentCom+"');turnLayer('"+UpAgentCom+"','show')\" onMouseOut=\"OutItem(this);turnLayer('"+UpAgentCom+"','hide')\"><img src=\"../pic/more.gif\" align='right'>"+"("+UpAgentCom+")"+Name+"\n");
	  	outHTML.append("<div id=\""+UpAgentCom+"\" style=\"width:220px; position:absolute; left:118px; top:"+position*SubHeight+"px; visibility: hidden;\" onMouseOut=\"turnLayer('"+UpAgentCom+"','hide')\" >\n");
		}
		outHTML.append("<table border=\"1\" cellpadding=\"0\" cellspacing=\"0\" class=\"FixMenu\">\n");

		for(int i=0;i<msize;i++)
		{
			outHTML.append("<tr>\n");
			if(explanAll)
			{
				MenuList(ManageCom,(String)childList.elementAt(i),(String)nameList.elementAt(i),i,outHTML,false,BankType,BranchType,ACType,AllType);
			}
			else
			{
			  outHTML.append("<td  class=\"OutMenu\" dix='"+i+"' onClick=\"SubThis('"+childList.elementAt(i)+"')\" onMouseOver=\"GetSubItems(this,'"+childList.elementAt(i)+"','"+BranchType+"','"+ACType+"','"+AllType+"')\" onMouseOut=\"OutItem(this);turnLayer('"+childList.elementAt(i)+"','hide')\">"+"("+childList.elementAt(i)+")"+nameList.elementAt(i)+"</td>\n");
			}
			outHTML.append("</tr>\n");
			if(i<msize-1) outHTML.append("<tr><td height=\"2\" bgcolor=\"#000000\"></td></tr>");
	  }

		outHTML.append("</table>\n");
		if(!init)
		{//不为根节点
			outHTML.append("</div>\n");
			outHTML.append("</td>\n");
		}
	}else
	{
		//outHTML.append("没有找到任何分公司！");
		outHTML.append("<td  class=\"OutMenu\" dix='"+position+"' onClick=\"SubThis('"+UpAgentCom+"')\" onMouseOver=\"OverItem(this,'"+UpAgentCom+"')\" onMouseOut=\"OutItem(this)\">"+"("+UpAgentCom+")"+Name+"</td>\n");
	}
  return;
}
%>
<%
Connection conn = DBConnPool.getConnection();

String sql = "SELECT AgentCom,Name FROM lacom WHERE ManageCom like '"+ManageCom+"%' and State='N' and UpAgentCom=? and BranchType=? ";
if(!ACType.equals("-1"))
   sql += " and ACType=? ";
if(AllType.equals("0"))
	 sql += " and not exists(select '1' from lacont where agentcom = lacom.agentcom)";
sql += " order by AgentCom";
//System.out.println("SQL is :"+ sql);
stmt = conn.prepareStatement(sql);
st = conn.createStatement();
%>

<script language="">
	
var ManageCom = "<%=ManageCom%>";
	
var arrGot = new Array();

function GetSubItems(src,UpAgentCom,BranchType,ACType,AllType){
//	showModalDialog("BankSelectCategory.jsp");
	var position = src.dix;
	var SubHeight=20; 
	
	for(var i=0;i<arrGot.length;i++)  //已经取得
	{
		if(arrGot[i][0]==UpAgentCom)
		{
			if(arrGot[i][1]>0)
			{
				turnLayer(UpAgentCom,'show');
			}
			return ;
			
		}
		
	}
	
	var sql = "SELECT AgentCom,Name FROM lacom WHERE ManageCom like '"+trim(ManageCom)+"%%' and State='N' and UpAgentCom='" + trim(UpAgentCom) + "' and BranchType='"+BranchType+"'";
	if (ACType != '-1')
	sql += " and ACType = '"+ACType+"'";
	if (AllType == '0')
	sql += " and not exists(select '1' from lacont where agentcom = lacom.agentcom)";
	
	sql += " order by AgentCom";
	var outHTML="";
	
	var arrResult=new Array();
	arrResult=decode(query(sql));
	
	if(arrResult)
	{
	  outHTML +="<div id=\""+UpAgentCom+"\" style=\"width:220px; position:absolute; left:118px; top:"+position*SubHeight+"px; visibility: hidden;\" onMouseOut=\"turnLayer('"+UpAgentCom+"','hide')\" >\n";
		outHTML +="<table border=\"1\" cellpadding=\"0\" cellspacing=\"0\" class=\"FixMenu\">\n";
		for(var i=0;i<arrResult.length;i++)
		{
			outHTML+="<tr>\n";
			outHTML+="<td  class=\"OutMenu\" dix=\""+i+"\" onClick=\"SubThis('"+arrResult[i][0]+"')\" onMouseOver=\"GetSubItems(this,'"+arrResult[i][0]+"','"+BranchType+"','"+ACType+"','"+AllType+"')\" onMouseOut=\"OutItem(this);turnLayer('"+arrResult[i][0]+"','hide')\">"+"("+arrResult[i][0]+")"+arrResult[i][1]+"</td>\n";
			outHTML+="</tr>\n";
			if(i<arrResult.length-1) outHTML+="<tr><td height=\"2\" bgcolor=\"#000000\"></td></tr>";
	  }
		outHTML+="</table>\n";
		outHTML+="</div>\n";
		outHTML+="</td>\n";

		src.innerHTML +=outHTML;
		turnLayer(UpAgentCom,'show');
		arrGot[arrGot.length] = new Array(UpAgentCom,arrResult.length);
	}
	else
	{
		arrGot[arrGot.length] = new Array(UpAgentCom,0);
	}
	
}
	
	
function decode(strResult)
{
	var arrEasyQuery = new Array();
	var arrRecord = new Array();
	var arrField = new Array();
	var recordNum, fieldNum, i, j;

	if(strResult==""||strResult=="null"||strResult==null)
		return null;

	//公用常量处理，增强容错性
	if (typeof(RECORDDELIMITER) == "undefined") RECORDDELIMITER = "^";
	if (typeof(FIELDDELIMITER) == "undefined") FIELDDELIMITER = "|";
	
	arrRecord = strResult.split(RECORDDELIMITER);
  	arrRecord.shift();
	  recordNum = arrRecord.length;
	  for(i=0; i<recordNum; i++) {
	  	arrField = arrRecord[i].split(FIELDDELIMITER); //拆分记录，得到字段数组

	  	fieldNum = arrField.length;
	  	arrEasyQuery[i] = new Array();
	  	for(j=0; j<fieldNum; j++) {
		  	arrEasyQuery[i][j] = arrField[j];          //形成以行为记录，列为字段的二维数组
		  }
	  }


	return arrEasyQuery;
}
	
	
	
function query(strSql) {

/*
		var strDate = Date.parse(new Date());

    var tStrSql = strSql.toLowerCase();
    if (tStrSql.indexOf("where") != -1) {
      tStrSql = strSql.substring(tStrSql.indexOf("where"), tStrSql.indexOf("where")+5);
      strSql = strSql.replace(tStrSql, "where '" + strDate + "'='" + strDate + "' and ");
    }
*/		
    //解决“=”号在JS和JSP之间的传递问题
    while(strSql.indexOf("=") != -1) {
		 strSql = strSql.replace("=", "%3D");
	}
	while(strSql.indexOf("%%") != -1) {
		  strSql = strSql.replace("%%", "%25");
	  }
    while(strSql.indexOf("?") != -1) {
      strSql = strSql.replace("?", "%3F");
    }
	while(strSql.indexOf("+") != -1)
	{
		strSql = strSql.replace("+", "%2B");
	}

  var urlStr = "../../common/easyQueryVer3/EasyQueryVer3Window.jsp?strSql=" + strSql + "&strStart=0";
  //sFeatures：查询窗口样式
  var sFeatures = "status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;resizable=0"
	              + "dialogLeft:2000px;dialogTop:2000px;";


  var strQueryResult = "";             //查询结果字符串
  var arrQueryResult = new Array();    //查询结果数组
  var sqlCacheFlag = false;

	strQueryResult = window.showModalDialog(urlStr, "", sFeatures);;

  //特殊字符转换，该函数由LH制作
  try {
    strQueryResult = Conversion(strQueryResult);
  }
  catch(e) {
  }

  //根据约定返回的字符串格式进行修改
  if (typeof(strQueryResult) == "string" && strQueryResult.substring(0, strQueryResult.indexOf("|")) != "0") {
    return false;
  }
  else {
    return strQueryResult;
  }
}
	
	
</script>

