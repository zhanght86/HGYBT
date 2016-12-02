<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes" />
	<xsl:template match="/">
		<Transaction>
			<!--报文体-->
			<xsl:apply-templates select="TranData"/>
		</Transaction>
	</xsl:template>
	<xsl:template match="TranData">
	<xsl:apply-templates select="Head"/>
	<xsl:if test="/TranData/Head/Flag='0'">
	<xsl:apply-templates select="Body"/>
	</xsl:if>
	</xsl:template>
	<xsl:template match="Head">
		<Transaction_Header>
		<LiBankID>CCB</LiBankID>
		<PbInsuId></PbInsuId>
		 <BkTxCode>OPR999</BkTxCode>
		 <BkChnlNo>1</BkChnlNo>
				<BkPlatDate>
					<xsl:value-of
						select="TranDate" />
				</BkPlatDate>
				<BkPlatTime>
					<xsl:value-of
						select="TranTime"  />
				</BkPlatTime>
				<BkTellerNo>
					<xsl:value-of
						select="TellerNo" />
				</BkTellerNo>
				<BkPlatSeqNo>
					<xsl:value-of
						select="TranNo" />
				</BkPlatSeqNo>
				<BkBrchNo>
					<xsl:value-of select="NodeNo" />
				</BkBrchNo>
			</Transaction_Header>
	</xsl:template>
	<xsl:template match="Body">
		<Transaction_Body>
			<BkDetail1><xsl:value-of select="BkDetail1" /></BkDetail1>
			<BkDetail2><xsl:value-of select="BkDetail2" /></BkDetail2>
		</Transaction_Body>
	</xsl:template>
	
</xsl:stylesheet>
