<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

<xsl:template match="/">
<TXLife>
	
	<xsl:copy-of select="TranData/Head" />
	<TXLifeResponse>
	<TransRefGUID><xsl:value-of select="TranData/Body/TranNo"/></TransRefGUID>
		<!-- 交易流水号 -->
		<TransType><xsl:value-of select="TranData/Body/FuncFlag"/></TransType>
		<!-- 交易码/处理标志 -->
 	 <TransExeDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/></TransExeDate>
 	 <TransExeTime><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur8Time()"/></TransExeTime>
	<TransMode>8</TransMode>
		<!-- 交易模式 -->
		<TransResult>
		<ResultCode><xsl:value-of select ="Head/Flag"/></ResultCode>
			<ResultInfo>
				<!-- 出错信息或错误提示 -->
				<ResultInfoDesc><xsl:value-of select ="Head/Desc"/></ResultInfoDesc>
			</ResultInfo>
		</TransResult>
	  	<OLifE>
			<FormInstance id="Form_1">
				<!-- 单证名称 -->
				<FormName>3</FormName>
				<!-- 批单号 -->
				<DocumentControlNumber>123456123</DocumentControlNumber>
				<!-- 批文（可重复） -->
				<Attachment id="Attachment_1">
					<!-- 批文内容1（第一行） -->
					<AttachmentData>content</AttachmentData>
				</Attachment>
			</FormInstance>
		</OLifE>
		<OLifEExtension VendorCode="1">
			<TransNo></TransNo>
			<!-- 交易代码 -->
			<RcptId></RcptId>
			<!-- 平保前置流水号 -->
		</OLifEExtension>
	 </TXLifeResponse>
</TXLife>
</xsl:template>

</xsl:stylesheet>