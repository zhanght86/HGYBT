<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
	<xsl:template match="/">
		<!--重打请求-->
		<TranData>
			
			<Head>
				<TranDate>
					<xsl:value-of select="/InsuReq/Main/TranDate"/>
				</TranDate>
				<!--银行交易日期-->
				<TranTime>
					<xsl:value-of select="/InsuReq/Main/TranTime" />
				</TranTime>
				<!-- 银行交易时间 -->
				<!--银行代码(cd05)-->
				<TellerNo>
					<xsl:value-of
						select="/InsuReq/Main/TellerNo" />
				</TellerNo>
				<!-- 柜员代码 -->
				<TranNo>
					<xsl:value-of
						select="/InsuReq/Main/TransNo" />
				</TranNo>
				<!-- 交易流水号 -->
				<NodeNo>
					<xsl:value-of select="/InsuReq/Main/BrNo" />
				</NodeNo>
				<!--网点代码-->
				<BankCode>0104</BankCode>
				<!-- 银行代码 -->
				<xsl:copy-of select="/InsuReq/Head/*"/>
			</Head>

			<Body>
				<ContNo>
					<xsl:value-of select="/InsuReq/Main/PolicyNo"/>
				</ContNo>
				<!--保单号 -->
				<ContPrtNo>
					<xsl:value-of select="/InsuReq/Main/PrintNo"/>
				</ContPrtNo>
				<!--保单印刷号-->
				<ProposalPrtNo></ProposalPrtNo>
					<!--投保单号-->
					<OldContPrtNo></OldContPrtNo>
					<!-- 旧保单印刷号 -->
					<OldTranNo><xsl:value-of select="/InsuReq/Main/BkOthOldSeq"/></OldTranNo>
					<!-- 原交易流水号 -->
				<Password></Password>
			</Body>
		</TranData>
	</xsl:template>
</xsl:stylesheet>
