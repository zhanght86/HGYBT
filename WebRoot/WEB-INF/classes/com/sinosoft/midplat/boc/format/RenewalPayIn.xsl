<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
	<!--续期缴费-->
	<xsl:template match="/">
		<TranData><!-- 核心续期缴费请求报文 -->
			<Head>
				<TranDate>
					<xsl:value-of
						select="InsuReq/Main/TranDate" />
				</TranDate>
				<TranTime>
					<xsl:value-of
						select="InsuReq/Main/TranTime" />
				</TranTime>
				<TellerNo>
					<xsl:value-of
						select="InsuReq/Main/TellerNo" />
				</TellerNo>
				<TranNo>
					<xsl:value-of
						select="InsuReq/Main/TransNo" />
				</TranNo>
				<NodeNo>
					<xsl:value-of select="InsuReq/Main/ZoneNo" /><xsl:value-of select="InsuReq/Main/BrNo" />
				</NodeNo>
				<TranCom>3</TranCom>
				<BankCode>03</BankCode>
				<FuncFlag>442</FuncFlag>
				<AgentCom></AgentCom>
				<AgentCode>-</AgentCode>
			</Head>
			<Body>
				<ContNo>
					<xsl:value-of select="InsuReq/Main/PolicyNo"/>
				</ContNo>
			</Body>
		</TranData>
	</xsl:template>
</xsl:stylesheet>
