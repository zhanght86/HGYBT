<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:template match="TranData">
	
	<ABCB2I>
		<Header>
		<!--������ -->
		<RetCode></RetCode>
		<!--������Ϣ -->
		<RetMsg></RetMsg>
		<!--���н�����ˮ�� -->
		<SerialNo><xsl:value-of select="Head/TranNo"/></SerialNo>
		<!--���չ�˾��ˮ�� -->
		<InsuSerial></InsuSerial>
		<!-- ��������-->
		<TransTime><xsl:value-of select="Head/TransTime"/></TransTime>
		<!-- ����ʱ-->
		<TransDate><xsl:value-of select="Head/TranDate"/></TransDate>
		<!-- ���д���-->
		<BankCode></BankCode>
		<!--���չ�˾���� -->
		<CorpNo></CorpNo>
		<!-- ���д���-->
		<TransCode>1005</TransCode>
	</Header>
	<App>
		<Ret>
			<!--�˱����-->
			<AppResult></AppResult>
			<!--������ -->
			<PolicyNo><xsl:value-of select ="Body/ContNo"/></PolicyNo>
			<!-- ���չ�˾�����ִ��� -->
			<RiskCode><xsl:value-of select="Body/RiskCode"/></RiskCode>
			<!-- ���� -->
			<Prem><xsl:value-of select ="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Body/Prem)"/></Prem>
		</Ret>	
	</App>
</ABCB2I>
	</xsl:template>
	
<!-- ���ִ��� -->
<xsl:template name="Code" match="RiskCode">
<xsl:choose>
	<xsl:when test=".=231201">231201</xsl:when>	<!-- �к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�A�� -->
	<xsl:when test=".=231202">231202</xsl:when>	<!-- �к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�B�� -->
	<xsl:when test=".=231203">231203</xsl:when> 	<!-- �к�׿Խ�Ƹ���ȫ���գ��ֺ��ͣ� -->
	<xsl:when test=".=211901">211901</xsl:when>  	<!-- �к���Ӯ����������˺����� -->
	<xsl:when test=".=221201">221201</xsl:when>  	<!-- �к����ݻ�����ȫ����A�� -->
	<xsl:when test=".=231204">231204</xsl:when>	<!-- �к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�C�� -->
	<xsl:when test=".=211902">211902</xsl:when>  	<!-- �к���Ӯ����������˺����� A��-->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>
	
</xsl:stylesheet>
