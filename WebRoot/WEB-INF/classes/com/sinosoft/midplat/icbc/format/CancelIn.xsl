<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

<xsl:template match="/">
 <TranData>
 	<xsl:apply-templates select="TXLife/TXLifeRequest" />
	
	<Body>
		<xsl:apply-templates select="TXLife/TXLifeRequest/OLifE/Holding/Policy" />
	</Body>
</TranData>
</xsl:template>

<xsl:template name="Head" match="TXLifeRequest">
<Head> 
	<!-- 交易日期 -->
	<TranDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date10to8(TransExeDate)"/></TranDate>
	<!-- 交易时间 -->
	<TranTime><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.time8to6(TransExeTime)"/></TranTime>
	<!-- 交易单位(银行/农信社/经代公司) -->
	<xsl:copy-of select="../Head/*"/>
	<BankCode>0101</BankCode>
	<!-- 银行网点 -->
	<ZoneNo><xsl:value-of select="OLifEExtension/RegionCode"/></ZoneNo>
	<NodeNo><xsl:value-of select="OLifEExtension/RegionCode"/><xsl:value-of select="OLifEExtension/Branch"/></NodeNo>
	<!-- 柜员代码 -->
	<TellerNo><xsl:value-of select="OLifEExtension/Teller"/></TellerNo>
	<!-- 交易流水号 -->
	<TranNo><xsl:value-of select="TransRefGUID"/></TranNo>
	<!-- 交易类型 -->
	<FuncFlag><xsl:value-of select="TransMode"/></FuncFlag>
</Head>
</xsl:template>

<xsl:template name="Body" match="Policy">
<ContNo><xsl:value-of select="PolNumber"/></ContNo>
<ProposalPrtNo><xsl:value-of select="ApplicationInfo/HOAppFormNumber"/></ProposalPrtNo>
<ContPrtNo><xsl:value-of select="../../FormInstance[FormName='2']/ProviderFormNumber"/></ContPrtNo>
</xsl:template>

</xsl:stylesheet>
