<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
	<xsl:template match="Transaction">
		<TranData>
			<Head>
				<TranDate>
					<xsl:value-of select="Transaction_Header/BkPlatDate" />
				</TranDate>
				<!--银行交易日期-->
				<TranTime>
				   <xsl:value-of select="Transaction_Header/BkPlatTime" />
				</TranTime>
				<!--交易时间-->
				
				<NodeNo>
					<xsl:value-of select="Transaction_Header/BkBrchNo" />
				</NodeNo>
				<!--网点代码-->
				<TellerNo>
					<xsl:value-of select="Transaction_Header/BkTellerNo" />
				</TellerNo>
				<!--柜员代码-->
				<TranNo>
					<xsl:value-of select="Transaction_Header/BkPlatSeqNo" />
				</TranNo>
				<!--交易流水号17-->
					<xsl:copy-of select="Head/*"/>
			</Head>
			<xsl:copy-of select="Transaction_Body/*"/>
			　　　　　
			
		</TranData>
	</xsl:template>
</xsl:stylesheet>
