<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
 	exclude-result-prefixes="java">
<xsl:template match="ABCB2I">
	 <TranData><!-- 核心当日撤单请求报文 -->
	     <Head>
	  		<!-- 交易日期 -->
	  		<TranDate><xsl:value-of select="Header/TransDate"/></TranDate>
	  		<!-- 交易时间-->
			<TranTime><xsl:value-of select="Header/TransTime"/></TranTime>
			<!-- 银行代码 -->
			<BankCode>0102</BankCode>
			<!-- 地区代码 -->
			<ZoneNo><xsl:value-of select="Header/ProvCode"/></ZoneNo>
			<!-- 网点代码 -->
			<NodeNo><xsl:value-of select="Header/ProvCode"/><xsl:value-of select="Header/BranchNo"/></NodeNo>
			<!-- 柜员代码 -->
			<TellerNo><xsl:value-of select="Header/Tlid"/></TellerNo>
	        <!-- 交易流水号 -->
			<TranNo><xsl:value-of select="Header/SerialNo"/></TranNo>
			<!-- YBT组织的节点信息 -->
			 <xsl:copy-of select="Head/*"/>
	  	</Head>
		<Body>
			<!-- 保险单号 -->
			<ContNo><xsl:value-of select="App/Req/PolicyNo"/></ContNo>
			<!-- 投保单(印刷)号 -->
			<ProposalPrtNo></ProposalPrtNo>
			<!-- 新保单合同印刷号 -->
			<ContPrtNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(App/Req/VchNo)"/></ContPrtNo>
			
			<TranNo><xsl:value-of select="App/Req/OrgSerialNo"/></TranNo>
			<AccNo><xsl:value-of select="App/Req/PayAcc"/></AccNo>
			<Prem><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(App/Req/PayAmt)"/></Prem>
		</Body>
	</TranData>
</xsl:template>
</xsl:stylesheet>
