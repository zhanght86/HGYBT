<%!
Connection conn;
PreparedStatement stmt;
ResultSet rs;
int SubHeight=20;

void MenuList(String Upcomcode,String Name,int position,StringBuffer outHTML,boolean init){
	Vector childList=new Vector();
	Vector nameList=new Vector();
	try{
    	stmt.setString(1,Upcomcode);
    	rs=stmt.executeQuery();

    	while(rs.next()){
      		childList.addElement(rs.getString("Comcode"));
      		nameList.addElement(rs.getString("Name"));
    	}
	}catch(Exception e){outHTML.append(e.toString());}
	int msize=childList.size();
	if(msize>0){
	  
		if(!init){//不为根节点
			outHTML.append("<td class=\"OutMenu\" onClick=\"SubThis('"+Upcomcode+"')\" onMouseOver=\"OverItem(this,'"+Name+"');turnLayer('"+Upcomcode+"','show')\" onMouseOut=\"OutItem(this);turnLayer('"+Upcomcode+"','hide')\"><img src=\"../pic/more.gif\" align='right'>"+"("+Upcomcode+") "+Name+"\n");
	  		outHTML.append("<div id=\""+Upcomcode+"\" style=\"width:220px; position:absolute; left:118px; top:"+position*SubHeight+"px; visibility: hidden;\" onMouseOut=\"turnLayer('"+Upcomcode+"','hide')\" >\n");
		}
		outHTML.append("<table border=\"1\" cellpadding=\"0\" cellspacing=\"0\" class=\"FixMenu\">\n");
		for(int i=0;i<msize;i++){
			outHTML.append("<tr>\n");
			MenuList((String)childList.elementAt(i),(String)nameList.elementAt(i),i,outHTML,false);
			outHTML.append("</tr>\n");
			if(i<msize-1) outHTML.append("<tr><td height=\"2\" bgcolor=\"#000000\"></td></tr>");
	    }
		outHTML.append("</table>\n");
		if(!init){//不为根节点
			outHTML.append("</div>\n");
			outHTML.append("</td>\n");
		}
	}else{
		//outHTML.append("没有找到任何分公司！");
		outHTML.append("<td  class=\"OutMenu\" onClick=\"SubThis('"+Upcomcode+"')\" onMouseOver=\"OverItem(this,'"+Name+"')\" onMouseOut=\"OutItem(this)\">"+"("+Upcomcode+") "+Name+"</td>\n");
	}
    return;
}
%>
<%
Connection conn = DBConnPool.getConnection();
stmt = conn.prepareStatement("SELECT Comcode,Name FROM ldcom WHERE trim(Upcomcode)=trim(?) order by Comcode");
%>

