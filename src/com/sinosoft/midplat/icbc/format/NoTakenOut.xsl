<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

<xsl:template match="/">

<TXLife>
	<xsl:copy-of select="TranData/Head"/>
	<TXLifeResponse>
	<!-- ������ˮ�� -->
	<TransRefGUID><xsl:value-of select="TranData/Body/TranNo"/></TransRefGUID>
	<!-- ������/�����־ -->
	<TransType>1004</TransType>
		
 	 <TransExeDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/></TransExeDate> 
 	 <TransExeTime><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur8Time()"/></TransExeTime> 
		<xsl:apply-templates select="TranData/Body"/>
	</TXLifeResponse>    
</TXLife> 
</xsl:template>   
<xsl:template match="Body">
	<OLifE>
			<FormInstance id="Form_1">
				<!-- ��֤���� --> 
				<FormName><xsl:value-of select="PaperTitle"/></FormName>
				<!-- ������ -->
				<DocumentControlNumber><xsl:value-of select="CardNo"/></DocumentControlNumber>				
			</FormInstance>
		</OLifE>
</xsl:template>
</xsl:stylesheet>
