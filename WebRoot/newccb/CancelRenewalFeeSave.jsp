<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.utility.ElementLis"%> 
<%@page import="com.sinosoft.midplat.common.DateUtil"%>   
<%@page import="com.sinosoft.midplat.common.JdomUtil"%>   
<%@page import="com.sinosoft.lis.pubfun.PubFun1"%>  
<%@page import="org.jdom.Document"%>   
<%@page import="java.util.Calendar"%> 
<%@page import="java.util.Date"%>  
<%@page import="java.text.DateFormat"%>  
<%@page import="java.text.SimpleDateFormat"%>  
<%@page import=" java.util.GregorianCalendar"%>  
<%@page import="com.sinosoft.utility.*"%> 
<%@page import="java.io.FileInputStream"%> 
<%@page import="java.io.ByteArrayInputStream"%> 
<%@page import="java.io.InputStream"%> 
<%@page import="test.NewABCTest"%> 
<%@page import="org.jdom.Element"%>
<%@page import="test.NewCCBTestUI"%> 

<%  
	String ResultCode = null; 
	String Content = null; 
	String ResultInfoDesc = null;  
	String strNewTransNo = null; 
	String xmlContent = null; 
	String strNewHOAppFormNumber = null; 
	String strNewProviderFormNumber = null; 
	String uiPrint = null;    
	String strNewInsurialno = null; 
%> 
 
<%
  try {
 
 //头节点
	request.setCharacterEncoding("GBK");
	String sTRANSCODE = request.getParameter("TRANSCODE");
	String sTranCom = request.getParameter("TranCom");
	String mSvPt_Jrnl_No = request.getParameter("mSvPt_Jrnl_No");
	String mSYS_REQ_TIME = request.getParameter("mSYS_REQ_TIME");
	String mCCBIns_ID = request.getParameter("mCCBIns_ID");
	String sTLID = request.getParameter("mCCB_EmpID");
		

	String mAgIns_Pkg_ID = request.getParameter("mAgIns_Pkg_ID");
	String mCvr_ID = request.getParameter("mCvr_ID");
	String mInsPolcy_No = request.getParameter("mInsPolcy_No");
	String mIns_Co_Jrnl_No = request.getParameter("mIns_Co_Jrnl_No");
	String mLiInsuSlipPWD = request.getParameter("mLiInsuSlipPWD");
	String mTxnAmt = request.getParameter("mTxnAmt");

	String sIp = request.getParameter("ip");
	String sPort = request.getParameter("port");   
	String pFuncflag = request.getParameter("TRANSCODE");
		
 System.out.println("************************装载XML************************");		 
	Element TX = new Element("TX"); 
	//协议报文头
 	ElementLis mHeader = new ElementLis("TX_HEADER",TX);	
 		//报文头长度
	 	ElementLis SYS_HDR_LEN = new ElementLis("SYS_HDR_LEN","",mHeader);
	 	//协议版本号
	 	ElementLis SYS_PKG_VRSN = new ElementLis("SYS_PKG_VRSN","0",mHeader);
	 	//报文总长度
	 	ElementLis SYS_TTL_LEN = new ElementLis("SYS_TTL_LEN","",mHeader);
	 	//发起方安全节点编号
	 	ElementLis SYS_REQ_SEC_ID = new ElementLis("SYS_REQ_SEC_ID","000000",mHeader);
	 	//发送方安全节点编号
	 	ElementLis SYS_SND_SEC_ID = new ElementLis("SYS_SND_SEC_ID","111111",mHeader);
	 	//服务名
	 	ElementLis SYS_TX_CODE = new ElementLis("SYS_TX_CODE",pFuncflag,mHeader);
	 	//服务版本号
	 	ElementLis SYS_TX_VRSN = new ElementLis("SYS_TX_VRSN","01",mHeader);
	 	//服务种类
	 	ElementLis SYS_TX_TYPE = new ElementLis("SYS_TX_TYPE","020000",mHeader);
	 	//预留
	 	ElementLis SYS_RESERVED = new ElementLis("SYS_RESERVED",null,mHeader);
	 	//全局事件跟踪号
	 	ElementLis SYS_EVT_TRACE_ID = new ElementLis("SYS_EVT_TRACE_ID",mSvPt_Jrnl_No,mHeader);
	 	//子交易序号
	 	ElementLis SYS_SND_SERIAL_NO = new ElementLis("SYS_SND_SERIAL_NO","123456",mHeader);
	 	//应用报文格式类型
	 	ElementLis SYS_PKG_TYPE = new ElementLis("SYS_PKG_TYPE","1",mHeader);
	 	//应用报文长度
	 	ElementLis SYS_MSG_LEN = new ElementLis("SYS_MSG_LEN","",mHeader);
	 	//应用报文是否加密
	 	ElementLis SYS_IS_ENCRYPTED = new ElementLis("SYS_IS_ENCRYPTED","3",mHeader);
	 	//应用报文加密方式
	 	ElementLis SYS_ENCRYPT_TYPE = new ElementLis("SYS_ENCRYPT_TYPE","3",mHeader);
	 	//应用报文压缩方式
	 	ElementLis SYS_COMPRESS_TYPE = new ElementLis("SYS_COMPRESS_TYPE","0",mHeader);
	 	//捎带报文长度
	 	ElementLis SYS_EMB_MSG_LEN = new ElementLis("SYS_EMB_MSG_LEN","0",mHeader);
	 	//发起方交易时间
	 	ElementLis SYS_REQ_TIME = new ElementLis("SYS_REQ_TIME",mSYS_REQ_TIME,mHeader);
	 	//交易剩余处理时间
	 	ElementLis SYS_TIME_LEFT = new ElementLis("SYS_TIME_LEFT","",mHeader);
	 	//报文状态类型
	 	ElementLis SYS_PKG_STS_TYPE = new ElementLis("SYS_PKG_STS_TYPE","00",mHeader);
	 	
	 	
	
 	ElementLis TX_BODY = new ElementLis("TX_BODY",TX);
 	//COMMON
 	ElementLis COMMON = new ElementLis("COMMON",TX_BODY);
 	//FILE_LIST_PACK域
 	ElementLis FILE_LIST_PACK = new ElementLis("FILE_LIST_PACK",COMMON);
 		//文件个数
 		ElementLis FILE_NUM = new ElementLis("FILE_NUM","",FILE_LIST_PACK);
 		//文件处理方式
 		ElementLis FILE_MODE = new ElementLis("FILE_MODE","0",FILE_LIST_PACK);
 		//文件节点
 		ElementLis FILE_NODE = new ElementLis("FILE_NODE","",FILE_LIST_PACK);
 		//打包后后文件名
 		ElementLis FILE_NAME_PACK = new ElementLis("FILE_NAME_PACK","",FILE_LIST_PACK);
 		//打包文件获取路径
 		ElementLis FILE_PATH_PACK = new ElementLis("FILE_PATH_PACK","",FILE_LIST_PACK);
 		//文件信息
 		ElementLis FILE_INFO = new ElementLis("FILE_INFO","",FILE_LIST_PACK);
 		//文件名
 		ElementLis FILE_NAME = new ElementLis("FILE_NAME","",FILE_LIST_PACK);
 		//文件路径
 		ElementLis FILE_PATH = new ElementLis("FILE_PATH","",FILE_LIST_PACK);
 		
 	//ENTITY	
 	ElementLis ENTITY = new ElementLis("ENTITY",TX_BODY);
 	//COM_ENTITY
 	ElementLis COM_ENTITY = new ElementLis("COM_ENTITY",ENTITY);
 		//机构英文简称
 		ElementLis Inst_Eng_ShrtNm = new ElementLis("Inst_Eng_ShrtNm","CCB",COM_ENTITY);
 		//保险公司编号
 		ElementLis Ins_Co_ID = new ElementLis("Ins_Co_ID","002",COM_ENTITY);
 		//#服务方流水号
 		ElementLis SvPt_Jrnl_No = new ElementLis("SvPt_Jrnl_No",mSvPt_Jrnl_No,COM_ENTITY);
 		//交易发起渠道编号
 		ElementLis TXN_ITT_CHNL_ID = new ElementLis("TXN_ITT_CHNL_ID","567",COM_ENTITY);
 		//交易发起渠道类别
 		ElementLis TXN_ITT_CHNL_CGY_CODE = new ElementLis("TXN_ITT_CHNL_CGY_CODE","234",COM_ENTITY);
 		//建行机构编号
 		ElementLis CCBIns_ID = new ElementLis("CCBIns_ID",mCCBIns_ID,COM_ENTITY);
 		//建行员工编号
 		ElementLis CCB_EmpID = new ElementLis("CCB_EmpID",sTLID,COM_ENTITY);
 		//#营业日期
 		ElementLis OprgDay_Prd = new ElementLis("OprgDay_Prd","",COM_ENTITY);
 		//多语言标识
 		ElementLis LNG_ID = new ElementLis("LNG-ID","zh-cn",COM_ENTITY);
 	
 	//APP_ENTITY
 	ElementLis APP_ENTITY = new ElementLis("APP_ENTITY",ENTITY);	
 		//
 		ElementLis  cmAgIns_Pkg_ID= new ElementLis("AgIns_Pkg_ID",mAgIns_Pkg_ID,APP_ENTITY);
 		ElementLis  cmCvr_ID= new ElementLis("Cvr_ID",mCvr_ID,APP_ENTITY);
 		ElementLis  cmInsPolcy_No= new ElementLis("InsPolcy_No",mInsPolcy_No,APP_ENTITY);
 		ElementLis  cmIns_Co_Jrnl_No= new ElementLis("Ins_Co_Jrnl_No",mIns_Co_Jrnl_No,APP_ENTITY);
 		ElementLis  cmLiInsuSlipPWD= new ElementLis("LiInsuSlipPWD",mLiInsuSlipPWD,APP_ENTITY);
 		ElementLis  cmTxnAmt= new ElementLis("TxnAmt",mTxnAmt,APP_ENTITY);
 	
 		JdomUtil.print(TX);
 			
 		//byte[] tx = JdomUtil.toBytes(TX);
 			
		//Document document = JdomUtil.build(tx,"UTF-8");
 		Document document = new Document(TX);
 		
		System.out.println("-------------------------------------------");

	
		
		int iPort = Integer.valueOf(sPort);
		NewCCBTestUI test = new NewCCBTestUI(sIp, iPort); 

		byte[] mOutBytes  = test.sendRequest(pFuncflag,document);
		Document mOutXmlDoc = JdomUtil.build(mOutBytes,"UTF-8");
		JdomUtil.print(mOutXmlDoc);
	 
		

	   



		 
		ResultCode =  mOutXmlDoc.getRootElement().getChild("TX_HEADER").getChildTextTrim("SYS_TX_STATUS");
		ResultInfoDesc =mOutXmlDoc.getRootElement().getChild("TX_HEADER").getChildTextTrim("SYS_RESP_DESC");
		PubFun1 pubfun = new PubFun1();
		System.out.println(ResultCode + "  " + ResultInfoDesc);
		strNewTransNo = PubFun1.CreateMaxNo("SERIALNO",16);

//
        System.out.println("返回内容："+ResultInfoDesc);
		if (!(ResultCode.equals("00"))) {
	        xmlContent = "交易失败：" + ResultInfoDesc ;
	      //	ResultInfoDesc = ResultInfoDesc.replace("%","");
	      
		} else {
		 
		xmlContent = ResultInfoDesc;
	        System.out.println("-----------开始取数（save页面）----------");
	
		   }  

	} catch (Exception e) {
		e.printStackTrace();
		ResultCode = "Fail";
		xmlContent= e.getMessage();  
		Content = e.getMessage(); 
	}      
%>  
<script language="javascript">
parent.fraInterface.afterSubmit("<%=ResultCode%>", "<%=ResultInfoDesc%>");
parent.fraInterface.fm.mSvPt_Jrnl_No.value = '<%=strNewTransNo%>';
parent.fraInterface.fm.xmlContent.value = '<%=xmlContent.toString()%>'; 
   //parent.fraInterface.fm.ContNo.value = 'strContNo';
</script>