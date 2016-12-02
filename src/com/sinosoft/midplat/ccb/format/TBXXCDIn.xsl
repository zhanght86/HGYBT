<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
	<xsl:template match="Transaction">
		<TranData>
			<Head>
				<TranDate>
					<xsl:value-of select="Transaction_Header/BkPlatDate" />
				</TranDate>
				<!--���н�������-->
				<TranTime>
				   <xsl:value-of select="Transaction_Header/BkPlatTime" />
				</TranTime>
				<!--����ʱ��-->
				<TranCom>4</TranCom>
				<BankCode>04</BankCode>
				<!--���д���-->
				<NodeNo>
					<xsl:value-of select="Transaction_Header/BkBrchNo" />
				</NodeNo>
				<!--�������-->
				<TellerNo>
					<xsl:value-of select="Transaction_Header/BkTellerNo" />
				</TellerNo>
				<!--��Ա����-->
				<TranNo>
					<xsl:value-of select="Transaction_Header/BkPlatSeqNo" />
				</TranNo>
				<!--������ˮ��-->
				<FuncFlag>110</FuncFlag>
				<AgentCode></AgentCode>
				<AgentCom></AgentCom>
				<!--�����־-->
			</Head>
			<Body>
				<CancelDate>
					<xsl:value-of select="Transaction_Body/PbBatChgBegDate" />
				</CancelDate>
			</Body>
		</TranData>
	</xsl:template>
</xsl:stylesheet>
