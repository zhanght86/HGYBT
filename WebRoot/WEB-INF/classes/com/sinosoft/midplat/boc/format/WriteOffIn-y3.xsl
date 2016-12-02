<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
	<!--冲正-->
	<xsl:template match="InsuReq">
		<TranData>
			<Head>
			     <!--交易日期 -->
				<TranDate><xsl:value-of select="Main/TranDate" /></TranDate>
				<!--交易时间 -->
				<TranTime><xsl:value-of select="Main/TranTime" /></TranTime>
				<!-- 交易渠道 -->
				<!-- <TranCom><xsl:value-of select="" /></TranCom> -->
				<!-- 银行网点 -->
				<NodeNo><xsl:value-of select="Main/BrNo" /></NodeNo>
				<!-- 银行编码 -->
				<BankCode>0107</BankCode>
				<!--柜员号 -->
				<TellerNo><xsl:value-of select="Main/TellerNo" /></TellerNo>
				<!-- 交易流水号 -->
				<TranNo><xsl:value-of select="Main/TransNo"/></TranNo>
				<xsl:copy-of select="Head/*"/>
			</Head>
			<Body>
				<ContNo>	<xsl:value-of select="/InsuReq/Main/PolicyNo"/>	</ContNo>
				<ContPrtNo><xsl:value-of select="/InsuReq/Main/ContPrtNo"/></ContPrtNo>
				<ProposalPrtNo><xsl:value-of select="/InsuReq/Main/ProposalPrtNo"/></ProposalPrtNo>
			</Body>
		</TranData>
	</xsl:template>
</xsl:stylesheet>
