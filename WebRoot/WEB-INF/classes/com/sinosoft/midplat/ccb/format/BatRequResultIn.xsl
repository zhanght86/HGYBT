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
		<FileName><xsl:value-of select="BkFileName" /></FileName>
		<BkFlag><xsl:value-of select="BkFlag1" /></BkFlag>
		<Flag>
		<xsl:call-template name="Tran_Flag">
		<xsl:with-param name="flag">
		<xsl:value-of select="BkOthRetCode"></xsl:value-of>
		</xsl:with-param>
		</xsl:call-template>
		</Flag>
		<Desc><xsl:value-of select="BkOthRetMsg" /></Desc>
		</Body>
	</xsl:template>
	<xsl:template name="Tran_Flag">
	<xsl:param name="flag">00000</xsl:param>
	<xsl:choose>
	<xsl:when test="$flag='00000'">0</xsl:when>
	<xsl:otherwise>1</xsl:otherwise>
	</xsl:choose>
	</xsl:template>
</xsl:stylesheet>

