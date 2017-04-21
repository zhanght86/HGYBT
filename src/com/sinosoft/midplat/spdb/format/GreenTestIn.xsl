<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
<xsl:template match="REQUEST">
<TranData>
	<Head>
		<!-- 银行交易日期 -->
		<TranDate><xsl:value-of select="BUSI/TRSDATE"/></TranDate>
		<!-- 交易时间 浦发不传交易时间 取系统当前时间 -->
		<TranTime><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur6Time()"/></TranTime>
		<!-- 地区代码 -->
		<ZoneNo><xsl:value-of select="DIST/ZONE"/></ZoneNo>
		<!-- 网点代码 -->
		<NodeNo><xsl:value-of select="DIST/DEPT"/></NodeNo>
		<!-- 柜员代码 -->
		<TellerNo><xsl:value-of select="DIST/TELLER"/></TellerNo>
		<!-- 银行交易流水号 -->
		<TranNo><xsl:value-of select="BUSI/TRANS"/></TranNo>
		<!-- YBT组织的节点信息 -->
		<xsl:copy-of select="Head/*"/>
	</Head>
</TranData>
</xsl:template>
</xsl:stylesheet>