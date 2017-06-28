<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes" />
	<xsl:template match="TranData">
		<TX>
			<TX_HEADER>
				<SYS_HDR_LEN>0</SYS_HDR_LEN>
				<SYS_PKG_VRSN>01</SYS_PKG_VRSN>
				<SYS_TTL_LEN>0</SYS_TTL_LEN>
				<SYS_SND_SEC_ID>420020</SYS_SND_SEC_ID>
				<SYS_REQ_SEC_ID>408006</SYS_REQ_SEC_ID>
				<SYS_TX_TYPE>00000</SYS_TX_TYPE>
				<SYS_EVT_TRACE_ID>prgram to edit</SYS_EVT_TRACE_ID>
				<SYS_SND_SERIAL_NO>0000000000</SYS_SND_SERIAL_NO>
				<SYS_PKG_TYPE>A</SYS_PKG_TYPE>
				<SYS_MSG_LEN />
				<SYS_IS_ENCRYPTED></SYS_IS_ENCRYPTED>
				<SYS_ENCRYPT_TYPE></SYS_ENCRYPT_TYPE>
				<SYS_COMPRESS_TYPE></SYS_COMPRESS_TYPE>
				<SYS_EMB_MSG_LEN>0</SYS_EMB_MSG_LEN>
				<SYS_RECV_TIME>prgram to edit</SYS_RECV_TIME>
				<SYS_RESP_TIME></SYS_RESP_TIME>
				<SYS_PKG_STS_TYPE>00</SYS_PKG_STS_TYPE>
				<xsl:if test = "Head/Flag = '0'">
					<SYS_TX_STATUS>00</SYS_TX_STATUS>
					<SYS_RESP_CODE>000000000000</SYS_RESP_CODE>
					<SYS_RESP_DESC><xsl:value-of select="Head/Desc" /></SYS_RESP_DESC>
				</xsl:if>
				
				<xsl:if test = "Head/Flag != '0'">
					<SYS_TX_STATUS>01</SYS_TX_STATUS>
					<SYS_RESP_CODE><xsl:value-of select="Head/Flag"/></SYS_RESP_CODE>
					<SYS_RESP_DESC><xsl:value-of select="Head/Desc" /></SYS_RESP_DESC>
				</xsl:if>
				<SYS_RESP_DESC_LEN>4</SYS_RESP_DESC_LEN>				
			</TX_HEADER>
			<TX_BODY>
				<COMMON>
					<FILE_LIST_PACK>
						<FILE_NUM />
						<FILE_MODE />
						<FILE_NODE />
						<FILE_NAME_PACK />
						<FILE_PATH_PACK />
						<FILE_INFO>
							<FILE_NAME />
							<FILE_PATH />
						</FILE_INFO>
					</FILE_LIST_PACK>
				</COMMON>
				<ENTITY>
					<APP_ENTITY></APP_ENTITY>
				</ENTITY>
			</TX_BODY>
		</TX>
	</xsl:template>
</xsl:stylesheet>
