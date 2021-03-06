<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
	<xsl:template match="/">
		<!--����-->
		<TranData>
			<Head>
			<TranDate>
					<xsl:value-of
						select="/Transaction/Transaction_Header/BkPlatDate" />
				</TranDate>
				<TranTime>
					<xsl:value-of
						select="/Transaction/Transaction_Header/BkPlatTime" />
				</TranTime>
				<TellerNo>
					<xsl:value-of
						select="/Transaction/Transaction_Header/BkTellerNo" />
				</TellerNo>
				<TranNo>
					<xsl:value-of
						select="/Transaction/Transaction_Header/BkPlatSeqNo" />
				</TranNo>
				<NodeNo>
					<xsl:value-of select="/Transaction/Transaction_Header/BkBrchNo" />
				</NodeNo>
					<BankCode>0104</BankCode>
				<xsl:copy-of select="/Transaction/Head/*"/>
			</Head>
			<Body>
			<ContNo></ContNo>
			<ProposalPrtNo></ProposalPrtNo>
			<ContPrtNo></ContPrtNo>
				<OldTranNo>
					<xsl:value-of select="/Transaction/Transaction_Body/BkOldSeq"/>
				</OldTranNo>
				<FuncFlag>
				<xsl:value-of select="/Transaction/Transaction_Body/BkOthTxCode"/>
				</FuncFlag>
			</Body>
		</TranData>
	</xsl:template>
</xsl:stylesheet>
