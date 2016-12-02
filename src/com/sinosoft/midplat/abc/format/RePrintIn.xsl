<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="TranData">
<TranData><!-- 核心保单重打请求报文 -->
	<Head>
		<TranDate><xsl:value-of select="BaseInfo/BankDate"/></TranDate>
		<TranTime>120000</TranTime>		
		<TellerNo><xsl:value-of select="BaseInfo/TellerNo"/></TellerNo>
		<TranNo><xsl:value-of select="BaseInfo/TransrNo"/></TranNo>
		<NodeNo><xsl:value-of select="BaseInfo/ZoneNo"/><xsl:value-of select="BaseInfo/BrNo"/></NodeNo>
		<xsl:copy-of select="./Head/*"/>
	</Head>

	<Body>
		<ContNo></ContNo>
		<ProposalPrtNo><xsl:value-of select="LCCont/ProposalContNo"/></ProposalPrtNo>
		<ContPrtNo><xsl:value-of select="LCCont/PrtNo"/></ContPrtNo>
	</Body>
</TranData>
</xsl:template>

</xsl:stylesheet>