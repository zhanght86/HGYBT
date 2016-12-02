<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
	<xsl:template match="SRCB">
		<!--重打请求-->
		<TranData>
			<Head>
			     <!--交易日期 -->
				<TranDate><xsl:value-of select="Header/TransDate" /></TranDate>
				<!--交易时间 -->
				<TranTime><xsl:value-of select="Header/TransTime" /></TranTime>
				<!-- 交易渠道 -->
				<!-- <TranCom><xsl:value-of select="" /></TranCom> -->
				<!-- 银行网点 -->
				<NodeNo><xsl:value-of select="Header/BranchNo" /></NodeNo>
				<!-- 银行编码 -->
				<BankCode>0107</BankCode>
				<!--柜员号 -->
				<TellerNo><xsl:value-of select="Header/TellerNo" /></TellerNo>
				<!-- 交易流水号 -->
				<TranNo><xsl:value-of select="Header/SerialNo" /></TranNo>
				<xsl:copy-of select="Head/*"/>
			</Head>

			<Body>
				<!--保单号 -->
				<ContNo>
					<xsl:value-of select="Body/PolicyNo"/>
				</ContNo>
				<!--保单印刷号-->
				<ContPrtNo>
					<xsl:value-of select="Body/InsurancePrintNo"/>
				</ContPrtNo>
				<!--投保单号-->
				<ProposalPrtNo><xsl:value-of select="Body/InsuranceSlipNo"/></ProposalPrtNo>
				<!-- 旧保单印刷号 -->
				<OldContPrtNo></OldContPrtNo>
				<!-- 原交易流水号 -->
				<OldTranNo></OldTranNo>
			</Body>
		</TranData>
	</xsl:template>
</xsl:stylesheet>
