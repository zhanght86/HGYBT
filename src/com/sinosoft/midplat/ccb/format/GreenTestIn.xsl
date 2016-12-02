<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">

	<xsl:template match="Transaction">
		<TranData>
			<Head>
				<TranDate>
					<xsl:value-of
						select="Transaction_Header/BkPlatDate" />
				</TranDate>
				<TranTime>
					<xsl:value-of
						select="Transaction_Header/BkPlatTime" />
				</TranTime>
				<TellerNo>
					<xsl:value-of
						select="Transaction_Header/BkTellerNo" />
				</TellerNo>
				<TranNo>
					<xsl:value-of
						select="Transaction_Header/BkPlatSeqNo" />
				</TranNo>
				<NodeNo>
					<xsl:value-of select="Transaction_Header/BkBrchNo" />
				</NodeNo>
					<BankCode>0104</BankCode>
					<xsl:copy-of select="Head/*"/>
			</Head>
			<!-- 报文体 -->
			<xsl:apply-templates select="Transaction_Body" />
		</TranData>
	</xsl:template>
	<!-- 报文体 -->
	<xsl:template match="Transaction_Body">
		<Body>
			<BkDetail1><xsl:value-of select="BkDetail1" /></BkDetail1>
			<BkDetail2><xsl:value-of select="BkDetail2" /></BkDetail2>
		</Body>
	</xsl:template>
</xsl:stylesheet>

