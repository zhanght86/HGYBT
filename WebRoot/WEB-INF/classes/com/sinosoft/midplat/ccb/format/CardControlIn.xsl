<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
	<xsl:template match="/">
		<!--����-->
		<TranData>
		<xsl:for-each select="/Transaction/Transaction_Header">
				<Head>
					<TranDate>
						<xsl:value-of select="BkPlatDate"/>
					</TranDate>
					<!--���н�������-->
					<TranTime>
					<xsl:value-of select="BkPlatTime" />
				    </TranTime>
					<NodeNo>
						<xsl:value-of select="BkBrchNo"/>
					</NodeNo>
					<!--�������-->
					<TellerNo>
						<xsl:value-of select="BkTellerNo"/>
					</TellerNo>
					<!--��Ա����-->
					<TranNo>
						<xsl:value-of select="BkPlatSeqNo"/>
					</TranNo>
					<!--������ˮ��-->
						<BankCode>0104</BankCode>
					<xsl:copy-of select="../Head/*"/>
				</Head>

		</xsl:for-each>
			<Body>
				<CardType>
					<xsl:value-of select="/Transaction/Transaction_Body/BkType1"/>
				</CardType>
				<StartNo>
					<xsl:value-of select="/Transaction/Transaction_Body/BkVchNo1"/>
				</StartNo>
				<EndNo>
					<xsl:value-of select="/Transaction/Transaction_Body/BkVchNo2"/>
				</EndNo>
			</Body>
		</TranData>
	</xsl:template>
</xsl:stylesheet>
