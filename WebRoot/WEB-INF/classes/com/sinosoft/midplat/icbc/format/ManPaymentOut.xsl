<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

<xsl:template match="/">

<TXLife>
	<xsl:copy-of select="TranData/Head"/>
	<TXLifeResponse>
	<TransRefGUID><xsl:value-of select="TranData/Body/TranNo"/></TransRefGUID>
		<!-- 交易流水号 -->
		<TransType><xsl:value-of select="TranData/Body/FuncFlag"/></TransType>
		<!-- 交易码/处理标志 -->
 	 <TransExeDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/></TransExeDate> 
 	 <TransExeTime><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur8Time()"/></TransExeTime> 
 	 <TransResult>
			<ResultCode><xsl:value-of select="TranData/Head/Flag"/></ResultCode>
			<ResultInfo>
				<ResultInfoDesc><xsl:value-of select="TranData/Head/Desc"/></ResultInfoDesc>
			</ResultInfo>
		</TransResult>
		<xsl:apply-templates select="TranData/Body"/>
	</TXLifeResponse>    
</TXLife> 
</xsl:template>   
<xsl:template match="Body">
	<OLifE>
			<FormInstance id="Form_1">
				<!-- 单证名称 --> 
				<FormName><xsl:value-of select="PaperTitle"/></FormName>
				<!-- 批单号 -->
				<DocumentControlNumber><xsl:value-of select="CardNo"/></DocumentControlNumber>
				<!-- 批文（可重复） -->
				<Attachment id="Attachment_1">
					<!-- 批文内容1（第一行） -->
					<AttachmentData><xsl:value-of select="AttachmentData "/></AttachmentData>
				</Attachment>
				
			</FormInstance>
		</OLifE>
</xsl:template>
</xsl:stylesheet>