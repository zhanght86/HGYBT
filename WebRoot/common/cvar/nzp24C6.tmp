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
<%@page contentType='text/html;charset=GBK' %>
<%@page import="com.sinosoft.utility.*"%> 
<%@page import="com.taikang.bas.blsvr.common.*"%>
<%@page import="com.taikang.bas.dbsvr.common.*"%>
<%@page import="com.taikang.bas.schema.*"%>
<%@page import="java.util.Vector"%>

<%
	String codename             = "";                 // 代码名称lx_code 
  	String riskcode             = "";                 // 险种代码    
  	String framename            = "";                 // Frame的名字 
  	String intElementIndex      = "";                 // 输入域在整个Form中的序号 
  	String intFunctionIndex     = "";                 // 1--给前一个域赋值 2--给后一个域赋值 
  	String ex                   = "";                 // 鼠标坐标x   
  	String ey                   = "";                 // 鼠标坐标y   
  	String stationCode          = "";                 // 部门代码    
  	StringBuffer strBufferValue = new StringBuffer(); // 装载代码查询结果 
  	String strValue             = "";
  	String strError             = "";                 // 错误信息    
  	int    intSize              = -1;                 // 记录条数信息 -1--null, 0--等于0, 1--大于0  
  	BLCode Dcode                = new BLCode();       // 标准代码类

 	//得到请求页面的方式,以确定是初次下载页面,还是通过页面查询代码
  	String strMethod = request.getMethod();
    
  	//如果是POST方式请求页面,说明是要通过页面查询代码,执行以下查询程序,否则直接返回默认页面
  	if(strMethod.equals("POST")) /* 以POST方式请求 */
  	{
    	Vector vector            = new Vector(); //装载代码查询结果的对象数组容器
    	String strQueryCondition = "";	         //代码查询条件
    	
    	//******************** delete B *********************
    	//LocCode loccode  		 = new LocCode();
    	//StdCode	stdcode      = new StdCode();
		//Exhaust	exhaust      = new Exhaust();
		//Dprintery dprintery    = new Dprintery();
		//Level level            = new Level();
		//******************** delete E *********************
		
    	//根据不同的代码类型,调用不同的JAVA类查询代码
    	vector = null;	//初始化返回结果

    	//获得代码查询条件,包括代码类型,险种信息等
    	codename          = request.getParameter("codename");
    	framename         = request.getParameter("framename");
    	intElementIndex   = request.getParameter("intElementIndex");
    	stationCode       = request.getParameter("stationCode");
    	intFunctionIndex  = request.getParameter("intFunctionIndex");
    	ex                = request.getParameter("ex");
    	ey                = request.getParameter("ey");
    	riskcode          = request.getParameter("currentRisk");
   	 	        
	    codename          = codename.trim();
		riskcode          = riskcode.trim();         
		framename         = framename.trim();       
		intElementIndex   = intElementIndex.trim(); 
		intFunctionIndex  = intFunctionIndex.trim(); 
		ex                = ex.trim();               
		ey                = ey.trim();               
		stationCode       = stationCode.trim();      

    	try
		{
  		  	strQueryCondition = "lx_code"      + SysConst.ENDOFPARAMETER + 
  		  	                    SysConst.EQUAL + SysConst.ENDOFPARAMETER + 
  		  	                    codename       + SysConst.ENDOFPARAMETER ;
  		  	if (stationCode.length()>0 )
  		  	{
  		  		strQueryCondition = strQueryCondition + 
  		  		                "station"       + SysConst.ENDOFPARAMETER + 
  		  	                    SysConst.CONTAIN+ SysConst.ENDOFPARAMETER+
  		  	                    stationCode     + SysConst.ENDOFPARAMETER ;
  		  	}
  		  	  		  	
  		  	//vector  = 
  		  	//申请后台服务
  		  	strQueryCondition = StrTool.makeCondition(strQueryCondition.trim());
  		  	strQueryCondition = strQueryCondition + "order by bm_code";	                    
  		  	UserLog.println(strQueryCondition);
  		  	Dcode.query(strQueryCondition);

  		  	if ( Dcode.getSize( ) > 0 )    //后台服务调用成功
  		  	{
  		    	int i=0;
  		    	intSize=1;
  		    	for ( i = 0; i< Dcode.getSize( ); i++ )	//将查询结果的对象数组打包
  		    	{
		        	strBufferValue.append(((CodeScm)Dcode.getArr(i)).getBm_code());
		        	strBufferValue.append("=");
		        	strBufferValue.append(((CodeScm)Dcode.getArr(i)).getM_code());
 		        	strBufferValue.append("&");
  		    	}
  			}
  		  	else if( vector.size()==0 ) intSize=0;
  	  		
  		}
  		catch(Exception exception)
  		{
  			strError = exception.toString();
  			UserLog.printException(strError);
  		}

  		//根据返回值的不同进行处理
  		if( intSize>0 )	        //调用成功,并返回数据
  		{
  	    	//统一去掉字符串末尾的&符号,并将其赋值字符串变量
  			if(strBufferValue.length()>1)
  		  		strBufferValue.setLength(strBufferValue.length()-1);
  			strValue=strBufferValue.toString();
  		}
  		else if( intSize==0 )	//调用成功,但没有满足查询条件的结果
  		{
  			strValue = strValue.valueOf(SysConst.NOTFOUND);
  		}
  		else                    //调用失败
  		{                      
  		    strValue = strValue.valueOf(SysConst.FAILURE);
  		}

  	}//当请求页面的方式为POST时的代码查询程序执行完毕
  	
%>


 <%--查询结束后，将查询结果放在input域传回到客户端，并且重画页面，使其恢复到初始状态--%>
<form name='fm' action='CodeGet.jsp' method='POST'>
  <input type='text' name='<%=codename%>' value='<%=strValue%>'>
  <input type='text' name='codename'>
  <input type='text' name='currentRisk' value='<%=riskcode%>'>
  <input type='text' name='framename'>
  <input type='text' name='intElementIndex'>
  <input type='text' name='intFunctionIndex'>
  <input type='text' name='ex'>
  <input type='text' name='ey'>
  <input type='text' name='stationCode' value=<%=stationCode%>>
</form>



<%
  //执行代码清单显示和选择的客户端JavaScript函数
  if(strMethod.equals("POST"))                 
  {
    out.println("<script language=javascript>");
    if(!strError.equals(""))
    {
      out.println("errorMessage('"+strError+"');");
      return;
    }
 
 	System.out.println(framename);
 
    out.println("try{");
    out.println("  parent.document.frames('"+framename+"').initializeCode('"+codename+"');");
    out.println("}catch(exception){}"); 
    String tempStr="";
    if ( (!strValue.equals(tempStr.valueOf(SysConst.NOTFOUND))) && (!strValue.equals(tempStr.valueOf(SysConst.FAILURE))) )
    {
    	out.println("try{"); 
    	out.println("  parent.document.frames('"+framename+"').showCodeByList('"+intElementIndex+"','"+codename+"','"+intFunctionIndex+"','"+ex+"','"+ey+"');");
    	out.println("}catch(exception){}"); 
    }
    out.println("</script>");
  } 
%>
