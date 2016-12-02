<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

<xsl:template match="TranData">
<TranData>
	<Head>
		<TranDate><xsl:value-of select="BaseInfo/BankDate"/></TranDate>
		<TranTime>120000</TranTime>		
		<TellerNo><xsl:value-of select="BaseInfo/TellerNo"/></TellerNo>
		<TranNo><xsl:value-of select="BaseInfo/TransrNo"/></TranNo>
		<!-- <ZoneNo><xsl:value-of select="BaseInfo/ZoneNo"/></ZoneNo> -->
		<NodeNo><xsl:value-of select="BaseInfo/ZoneNo"/><xsl:value-of select="BaseInfo/BrNo"/></NodeNo>
		<xsl:copy-of select="./Head/*"/>
	</Head>

	<Body>
		<ContNo><xsl:value-of select="LCCont/ContNo"/></ContNo>
		<ProposalPrtNo></ProposalPrtNo>
		<ContPrtNo></ContPrtNo>
	</Body>
</TranData>
</xsl:template>

</xsl:stylesheet>