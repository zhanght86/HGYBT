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
				<BankCode>0122</BankCode>
				<xsl:copy-of select="/InsuReq/Head/*"/>
			</Head>
			<Body>
				<PayMode>B</PayMode><!-- 保费支付方式代码 -->
				<ProposalPrtNo /><!-- 投保单(印刷)号 -->
				<TranNo /><!-- 保险公司流水号 -->
				<ContNo><!-- 保单号码 -->
					<xsl:value-of select="InsuReq/Main/PolicyNo"/>
				</ContNo>
				<Prem><xsl:value-of select="InsuReq/Main/Premium"/></Prem><!-- 应收金额 -->
				<PayAcc><xsl:value-of select="InsuReq/Main/PayAcc"/></PayAcc><!-- 缴费帐号 -->
			</Body>
		</TranData>
	</xsl:template>
</xsl:stylesheet>
