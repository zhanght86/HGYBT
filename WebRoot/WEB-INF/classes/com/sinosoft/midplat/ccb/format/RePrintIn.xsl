<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
	<xsl:template match="/">
		<!--重打请求-->
		<TranData>
			
			<Head>
				<TranDate>
					<xsl:value-of select="/Transaction/Transaction_Header/BkPlatDate"/>
				</TranDate>
				<!--银行交易日期-->
				<TranTime>
					<xsl:value-of select="/Transaction/Transaction_Header/BkPlatTime" />
				</TranTime>
				<!--银行代码(cd05)-->
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
				<!--交易流水号-->
				<BankCode>0104</BankCode>
				<xsl:copy-of select="/Transaction/Head/*"/>
			</Head>

			<Body>
				<ContNo>
					<xsl:value-of select="/Transaction/Transaction_Body/PbInsuSlipNo"/>
				</ContNo>
				<!--保单号 -->
				<ContPrtNo>
					<xsl:value-of select="/Transaction/Transaction_Body/BkVchNo"/>
				</ContPrtNo>
				<!--保单印刷号-->
				<ProposalPrtNo></ProposalPrtNo>
					<!--投保单号-->
					<OldContPrtNo></OldContPrtNo>
					<!-- 旧保单印刷号 -->
					<OldTranNo><xsl:value-of select="/Transaction/Transaction_Body/BkOthOldSeq"/></OldTranNo>
					<!-- 原交易流水号 -->
				<Password></Password>
			</Body>
		</TranData>
	</xsl:template>
</xsl:stylesheet>
