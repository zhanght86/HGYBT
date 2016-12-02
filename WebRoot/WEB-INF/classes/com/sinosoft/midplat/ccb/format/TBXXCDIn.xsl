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
				<TranCom>4</TranCom>
				<BankCode>04</BankCode>
				<!--银行代码-->
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
				<!--交易流水号-->
				<FuncFlag>110</FuncFlag>
				<AgentCode></AgentCode>
				<AgentCom></AgentCom>
				<!--处理标志-->
			</Head>
			<Body>
				<CancelDate>
					<xsl:value-of select="Transaction_Body/PbBatChgBegDate" />
				</CancelDate>
			</Body>
		</TranData>
	</xsl:template>
</xsl:stylesheet>
