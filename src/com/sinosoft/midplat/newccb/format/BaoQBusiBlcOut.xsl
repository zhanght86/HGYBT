<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes" />
	<xsl:template match="TranData">
    <TX>
	<TX_HEADER>
		<!-- 报文头长度 -->
		<SYS_HDR_LEN></SYS_HDR_LEN>
		<!-- 协议版本号 -->
		<SYS_PKG_VRSN>01</SYS_PKG_VRSN>
		<!-- 报文总长度 -->
		<SYS_TTL_LEN></SYS_TTL_LEN>				
		<!-- 发送方安全节点编号 -->
		<SYS_SND_SEC_ID>510051</SYS_SND_SEC_ID>
		<!-- 发起方安全节点编号 -->
		<SYS_REQ_SEC_ID></SYS_REQ_SEC_ID>
		<!-- 服务种类 -->
		<SYS_TX_TYPE>020000</SYS_TX_TYPE>
		<!-- 全局事件跟踪号 -->
		<SYS_EVT_TRACE_ID></SYS_EVT_TRACE_ID>
		<!-- 子交易序号 -->
		<SYS_SND_SERIAL_NO></SYS_SND_SERIAL_NO>
		<!-- 应用报文格式类型 -->
		<SYS_PKG_TYPE>1</SYS_PKG_TYPE>
		<!-- 应用报文长度 -->
		<SYS_MSG_LEN></SYS_MSG_LEN>
		<!-- 应用报文是否加密 -->
		<SYS_IS_ENCRYPTED>3</SYS_IS_ENCRYPTED>
		<!-- 应用报文加密方式 -->
		<SYS_ENCRYPT_TYPE>3</SYS_ENCRYPT_TYPE>
		<!-- 应用报文压缩方式 -->
		<SYS_COMPRESS_TYPE>0</SYS_COMPRESS_TYPE>
		<!-- 捎带报文长度 -->
		<SYS_EMB_MSG_LEN>0</SYS_EMB_MSG_LEN>
		<!-- 服务接受时间 -->
		<SYS_RECV_TIME></SYS_RECV_TIME>
		<!-- 服务响应时间 -->
		<SYS_RESP_TIME></SYS_RESP_TIME>
		<!-- 报文状态类型 -->
		<SYS_RESP_TIME>01</SYS_RESP_TIME>
		<!-- 服务状态 -->
		<SYS_TX_STATUS></SYS_TX_STATUS>
		<!-- 服务响应码 -->
		<SYS_RESP_CODE></SYS_RESP_CODE>
		<!-- 服务响应描述长度 -->
		<SYS_RESP_DESC_LEN></SYS_RESP_DESC_LEN>
		<!-- 服务响应描述 -->
		<SYS_RESP_DESC></SYS_RESP_DESC>
	</TX_HEADER>
	<TX_BODY>
		<COMMON>
			<FILE_LIST_PACK>
			<!-- 文件个数 -->
			<FILE_NUM></FILE_NUM>
			<!-- 文件处理方式 -->
			<FILE_MODE>0</FILE_MODE>
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
				<!-- 机构英文简称 -->
				<Inst_Eng_ShrtNm>CCB</Inst_Eng_ShrtNm>
				<!-- 保险公司编号 -->
				<Ins_Co_ID>73</Ins_Co_ID>
				<!-- #服务方流水号 -->
				<SvPt_Jrnl_No></SvPt_Jrnl_No>
				<!-- 服务名 -->
				<SYS_TX_CODE>P53818152</SYS_TX_CODE>
				<!-- 交易发起渠道编号 -->
				<TXN_ITT_CHNL_ID></TXN_ITT_CHNL_ID>
				<!-- 交易发起渠道类别 -->
				<TXN_ITT_CHNL_CGY_CODE></TXN_ITT_CHNL_CGY_CODE>
				<!-- 建行机构编号 -->
				<CCBIns_ID><xsl:value-of select="Head/NodeNo"/></CCBIns_ID>
				<!-- 建行员工编号 -->
				<CCB_EmpID><xsl:value-of select="Head/TellerNo"/></CCB_EmpID>
				<!-- 保险公司账务日期 -->
				<Ins_Co_Acg_Dt><xsl:value-of select="Head/TranDate"/></Ins_Co_Acg_Dt>
				<!-- 保险公司流水号 -->
				<Ins_Co_Jrnl_No><xsl:value-of select="Head/TranNo"/></Ins_Co_Jrnl_No>
			</COM_ENTITY>
			<APP_ENTITY/>
		</ENTITY>
	</TX_BODY>
</TX>
</xsl:template>
</xsl:stylesheet>
