<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">

<xsl:template match="TranData">
  <TX>
	<!-- 报文头 -->
	<TX_HEADER>
      <!-- 报文头长度 -->
	  <SYS_HDR_LEN></SYS_HDR_LEN>
      <!-- 协议版本号 -->
      <SYS_PKG_VRSN>01</SYS_PKG_VRSN>
	  <!-- 报文总长度 -->
	  <SYS_TTL_LEN></SYS_TTL_LEN>
	  <!-- 发送方安全节点编号 -->
	  <SYS_SND_SEC_ID>510096</SYS_SND_SEC_ID>
	  <!-- 发起发安全节点编号  转换类中添加-->
	  <SYS_REQ_SEC_ID></SYS_REQ_SEC_ID>
	  <!--服务种类-->
	  <SYS_TX_TYPE>020000</SYS_TX_TYPE>
	  <!-- 全局事件跟踪号  转换类中添加-->
	  <SYS_EVT_TRACE_ID></SYS_EVT_TRACE_ID>
	  <!-- 子交易序号  转换类中添加-->
	  <SYS_SND_SERIAL_NO></SYS_SND_SERIAL_NO>
	  <!-- 应用报文格式类型 -->
	  <SYS_PKG_TYPE>1</SYS_PKG_TYPE>    
	  <!-- 应用报文长度  转换类中添加-->
	  <SYS_MSG_LEN></SYS_MSG_LEN>
      <!-- 应用报文是否加密 -->
      <SYS_IS_ENCRYPTED>3</SYS_IS_ENCRYPTED>    
      <!-- 应用报文加密方式 -->
	  <SYS_ENCRYPT_TYPE>3</SYS_ENCRYPT_TYPE>
	  <!-- 应用报文压缩方式 -->
	  <SYS_COMPRESS_TYPE>0</SYS_COMPRESS_TYPE>    
	  <!-- 捎带报文长度 -->
	  <SYS_EMB_MSG_LEN>0</SYS_EMB_MSG_LEN>
	  <!-- 服务接受时间 转换类中添加-->
	  <SYS_RECV_TIME></SYS_RECV_TIME>    
	  <!-- 服务响应时间  转换类中添加-->
	  <SYS_RESP_TIME></SYS_RESP_TIME>
	  <!-- 报文状态类型  -->
	  <SYS_PKG_STS_TYPE>01</SYS_PKG_STS_TYPE>    
	  <xsl:if test = "RetData/Flag='1'">
	  <!-- 服务状态 -->
	  <SYS_TX_STATUS>00</SYS_TX_STATUS>    
	  <!-- 服务响应码 -->
	  <SYS_RESP_CODE>000000000000</SYS_RESP_CODE>    
	  </xsl:if>
	  <xsl:if test = "RetData/Flag='0'">
	  <!-- 服务状态 -->
	  <SYS_TX_STATUS>01</SYS_TX_STATUS>    
	  <!-- 服务响应码 -->
	  <SYS_RESP_CODE>ZZZ072000001</SYS_RESP_CODE>    
	  </xsl:if>    
	  <!-- 服务响应描述长度  转换类中添加-->
	  <SYS_RESP_DESC_LEN></SYS_RESP_DESC_LEN>    
	  <!-- 服务响应描述 -->
	  <SYS_RESP_DESC><xsl:value-of select="RetData/Desc"/></SYS_RESP_DESC>    
   </TX_HEADER>
   <!-- 报文体 -->
   <TX_BODY>
	  <COMMON>
	    <FILE_LIST_PACK>
	    <!-- 文件个数 -->
		<FILE_NUM>0</FILE_NUM>
		<!-- 文件处理方式 -->
		<FILE_MODE></FILE_MODE>
		<!-- 文件节点 -->
		<FILE_NODE></FILE_NODE>
		<!-- 打包后后文件名 -->
		<FILE_NAME_PACK></FILE_NAME_PACK>
		<!-- 打包文件获取路径 -->
		<FILE_PATH_PACK></FILE_PATH_PACK>
		<!-- 文件信息 -->
		<FILE_INFO>
		<!-- 文件信息 -->
		<FILE_NAME></FILE_NAME>
		<!-- 文件路径 -->
		<FILE_PATH></FILE_PATH>
		</FILE_INFO>
	    </FILE_LIST_PACK>
	  </COMMON>
	  <ENTITY>
	     <COM_ENTITY>
            <!--公共域-->
            <Inst_Eng_ShrtNm></Inst_Eng_ShrtNm>
            <Ins_Co_ID></Ins_Co_ID>
            <!--保险公司编号-->
            <SvPt_Jrnl_No></SvPt_Jrnl_No>
            <!--服务方流水号-->
            <TXN_ITT_CHNL_ID></TXN_ITT_CHNL_ID>
            <!--交易发起渠道编号-->
            <TXN_ITT_CHNL_CGY_CODE></TXN_ITT_CHNL_CGY_CODE>
            <!--交易发起渠道类别-->
            <CCBIns_ID></CCBIns_ID>
            <!--建行机构编号&网点代码-->
            <CCB_EmpID></CCB_EmpID>
            <!--建行员工编号&柜员编码-->
            <OprgDay_Prd></OprgDay_Prd>
            <!--营业日期-->
            <LNG_ID></LNG_ID>
            <SYS_TX_CODE></SYS_TX_CODE>
            <Ins_Co_Acg_Dt></Ins_Co_Acg_Dt>
            <Ins_Co_Jrnl_No></Ins_Co_Jrnl_No>
            <Ins_Co_Cst_Svc_Tel></Ins_Co_Cst_Svc_Tel>
         </COM_ENTITY>
	   </ENTITY>
	 </TX_BODY>
  </TX>
</xsl:template>		
</xsl:stylesheet>