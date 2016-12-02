<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
	<xsl:template match="InsuReq">
		<TranData><!-- 核心续期缴费查询请求报文 -->
			<Head>
				<TranDate>
					<xsl:value-of
						select="Main/TranDate" />
				</TranDate>
				<TranTime>
					<xsl:value-of
						select="Main/TranTime" />
				</TranTime>
				<TellerNo>
					<xsl:value-of
						select="Main/TellerNo" />
				</TellerNo>
				<TranNo>
					<xsl:value-of
						select="Main/TransNo" />
				</TranNo>
				<NodeNo>
					<xsl:value-of select="Main/ZoneNo" /><xsl:value-of select="Main/BrNo" />
				</NodeNo>
				<TranCom>3</TranCom>
				<BankCode>03</BankCode>
				<FuncFlag>441</FuncFlag>
				<AgentCom></AgentCom>
				<AgentCode>-</AgentCode>
			</Head>
			<Body>
				<ContNo>
					<xsl:value-of select="Main/PolicyNo"/>
				</ContNo>
			</Body>
		</TranData>
	</xsl:template>
</xsl:stylesheet>
