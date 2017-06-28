<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
<xsl:template match="/REQUEST">
	 <TranData>
		<!--基本信息-->
		<Head>
	  		<!-- 交易日期 -->
	  		<TranDate><xsl:value-of select="BUSI/TRSDATE"/></TranDate>
	  		<!-- 交易时间-->
			<TranTime><xsl:value-of select="string(java:com.sinosoft.midplat.common.DateUtil.getCur6Time())"/></TranTime>
			<!-- 银行代码 -->
			<BankCode><xsl:value-of select="Head/BankCode"/></BankCode>
			<!-- 地区代码 -->
			<ZoneNo><xsl:value-of select="DIST/ZONE"/></ZoneNo>
			<!-- 银行网点 -->
			<NodeNo><xsl:value-of select="DIST/DEPT"/></NodeNo>
			<!-- 柜员代码 -->
			<TellerNo><xsl:value-of select="DIST/TELLER"/></TellerNo>
			<!-- 交易流水号 -->
			<TranNo><xsl:value-of select="BUSI/TRANS"/></TranNo>
			<!-- YBT组织的节点信息 -->
			<xsl:copy-of select="Head/*"/>
		</Head>
		<Body>
			<!-- 险种代码 -->
			<RiskCode/>
			<!-- 保险单号 -->
			<ContNo/>
			 <!-- 保单合同印刷号 -->
			<ContPrtNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(BUSI/CONTENT/BILL_USED)"/></ContPrtNo>
			<!-- 投保单(印刷)号 -->
			<ProposalPrtNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(BUSI/CONTENT/APPNO)"/></ProposalPrtNo> 
		</Body>
	</TranData>
</xsl:template>
</xsl:stylesheet>