<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

<xsl:template match="Req">
<TranData>
	  	<Head>
	  		<!-- 银行交易日期 -->
	  		<TranDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur8Date()"/></TranDate>
	  		<!-- 交易时间 农行不传交易时间 取系统当前时间 -->
			<TranTime><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur6Time()"/></TranTime>
			<!-- 银行地区码 -->
			<ZoneNo><xsl:value-of select="ZoneNo"/></ZoneNo>
			<!-- 银行网点码 -->
			<NodeNo><xsl:value-of select="ZoneNo"/><xsl:value-of select="BrNo"/></NodeNo>
			<!-- 柜员代码 -->
			<TellerNo><xsl:value-of select="TellerNo"/></TellerNo>
			<!-- 银行交易流水号 -->
			<TranNo><xsl:value-of select="TransrNo"/></TranNo>
			<!-- 银行编码 -->
			<BankCode>0102</BankCode>
			<!-- YBT组织的节点信息 -->
			<xsl:copy-of select="Head/*"/>
	  	</Head>
		<Body>
			<ContNo><xsl:value-of select="ConfirmInfo/ContNo"/></ContNo>
			<OldTranNo><xsl:value-of select="ConfirmInfo/OldTranNo"/></OldTranNo>
			<ProposalPrtNo></ProposalPrtNo>
		</Body>
</TranData>
</xsl:template>

</xsl:stylesheet>