<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:template match="TranData">
	
	<ABCB2I>
		<Header>
		<!--返回码 -->
		<RetCode></RetCode>
		<!--返回信息 -->
		<RetMsg></RetMsg>
		<!--银行交易流水号 -->
		<SerialNo><xsl:value-of select="Head/TranNo"/></SerialNo>
		<!--保险公司流水号 -->
		<InsuSerial></InsuSerial>
		<!-- 交易日期-->
		<TransTime><xsl:value-of select="Head/TransTime"/></TransTime>
		<!-- 交易时-->
		<TransDate><xsl:value-of select="Head/TranDate"/></TransDate>
		<!-- 银行代码-->
		<BankCode></BankCode>
		<!--保险公司代码 -->
		<CorpNo></CorpNo>
		<!-- 银行代码-->
		<TransCode>1005</TransCode>
	</Header>
	<App>
		<Ret>
			<!--核保结果-->
			<AppResult></AppResult>
			<!--保单号 -->
			<PolicyNo><xsl:value-of select ="Body/ContNo"/></PolicyNo>
			<!-- 保险公司方险种代码 -->
			<RiskCode><xsl:value-of select="Body/RiskCode"/></RiskCode>
			<!-- 保费 -->
			<Prem><xsl:value-of select ="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Body/Prem)"/></Prem>
		</Ret>	
	</App>
</ABCB2I>
	</xsl:template>
	
<!-- 险种代码 -->
<xsl:template name="Code" match="RiskCode">
<xsl:choose>
	<xsl:when test=".=231201">231201</xsl:when>	<!-- 中韩智赢财富两全保险（分红型）A款 -->
	<xsl:when test=".=231202">231202</xsl:when>	<!-- 中韩智赢财富两全保险（分红型）B款 -->
	<xsl:when test=".=231203">231203</xsl:when> 	<!-- 中韩卓越财富两全保险（分红型） -->
	<xsl:when test=".=211901">211901</xsl:when>  	<!-- 中韩安赢借款人意外伤害保险 -->
	<xsl:when test=".=221201">221201</xsl:when>  	<!-- 中韩保驾护航两全保险A款 -->
	<xsl:when test=".=231204">231204</xsl:when>	<!-- 中韩智赢财富两全保险（分红型）C款 -->
	<xsl:when test=".=211902">211902</xsl:when>  	<!-- 中韩安赢借款人意外伤害保险 A款-->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>
	
</xsl:stylesheet>
