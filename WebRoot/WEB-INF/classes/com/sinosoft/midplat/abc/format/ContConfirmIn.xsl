<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" 
	exclude-result-prefixes="java">


<xsl:template match="Req">
<TranData><!-- 核心收费签单请求报文 -->
	<!--基本信息-->
	  	<Head>
	  		<!-- 银行交易日期 -->
	  		<TranDate><xsl:value-of select="BankDate"/></TranDate>
	  		<!-- 交易时间 农行不传交易时间 取系统当前时间 -->
			<TranTime><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur6Time()"/></TranTime>
			<!-- 银行交易时间 -->
			<!-- <TranTime><xsl:value-of select="BankTime"/></TranTime> -->
			<!-- 地区代码 -->
			<ZoneNo><xsl:value-of select="ZoneNo"/></ZoneNo>
			<!-- 网点代码 -->
			<NodeNo><xsl:value-of select="ZoneNo"/><xsl:value-of select="BrNo"/></NodeNo>
			<!-- 银行代码 -->
			<BankCode>0102</BankCode>
			<!-- 柜员代码 -->
			<TellerNo><xsl:value-of select="TellerNo"/></TellerNo>
			<!-- 银行交易流水号 -->
			<TranNo><xsl:value-of select="TransrNo"/></TranNo>
			<!-- YBT组织的节点信息 -->
			<xsl:copy-of select="Head/*"/>
	  	</Head>
	
		<!--投保信息-->
		<Body>
			<!-- 投保单号 -->
			<ProposalPrtNo><xsl:value-of select="Base/ProposalContNo"/></ProposalPrtNo>
			<!-- 保单印刷号 -->
			<ContPrtNo><xsl:value-of select="Base/PrtNo"/></ContPrtNo>
			<!-- 投保日期 -->
			<PolApplyDate><xsl:value-of select="Base/PolApplyDate"/></PolApplyDate>
			<Prem><xsl:value-of select="Base/Prem"/></Prem>	
		</Body>
	</TranData>
</xsl:template>	

</xsl:stylesheet>
