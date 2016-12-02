<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
	<xsl:template match="/">
		<TranData>
			<xsl:for-each select="/Transaction/Transaction_Header">
				<Head>
					<TranDate>
						<xsl:value-of select="BkPlatDate"/>
					</TranDate>
					<!--银行交易日期-->
					<TranTime>
					<xsl:value-of select="BkPlatTime" />
				    </TranTime>
					<NodeNo>
						<xsl:value-of select="BkBrchNo"/>
					</NodeNo>
					<!--银行代码-->
					<BankCode>0122</BankCode>
					<!--网点代码-->
					<TellerNo>
						<xsl:value-of select="BkTellerNo"/>
					</TellerNo>
					<!--柜员代码-->
					<TranNo>
						<xsl:value-of select="BkPlatSeqNo"/>
					</TranNo>
					<!--交易流水号-->
					<TranCom>07</TranCom>
				    <xsl:copy-of select="../Head/*"/>
				</Head>
			</xsl:for-each>
			<Body>
				<OldTranNo>
					<xsl:value-of select="/Transaction/Transaction_Body/BkOthOldSeq"/>
				</OldTranNo>
				<!--原来的交易流水号   20150109 银行又说用核心的代码-->
				<RiskCode><xsl:value-of select="/Transaction/Transaction_Body/PbInsuType" /></RiskCode>
				<ContNo></ContNo> <!-- 保险单号 -->
                <ProposalPrtNo></ProposalPrtNo> <!-- 投保单(印刷)号 -->
                <ContPrtNo></ContPrtNo> <!-- 保单合同印刷号 -->
                <!-- 首期缴费方式 -->
                <BkPayMode>
                <xsl:call-template name="tran_BKPayMode">
						<xsl:with-param name="PayMode">
							<xsl:value-of select="/Transaction/Transaction_Body/BkPayMode" />
						</xsl:with-param>
					</xsl:call-template>
				</BkPayMode>
                 <!-- 首期缴费帐号 -->
                <BkAcctNo><xsl:value-of select="/Transaction/Transaction_Body/BkAcctNo" /></BkAcctNo>
			</Body>
		</TranData>
	</xsl:template>
		<!-- 主险种代码 -->
	<xsl:template name="tran_MainRiskCode">
		<xsl:param name="MainRiskCode">0</xsl:param>
		<xsl:if test="$MainRiskCode = 0001">231201</xsl:if><!-- 中韩智赢财富两全保险（分红型）A款 -->
		<xsl:if test="$MainRiskCode = 0002">231202</xsl:if><!-- 中韩智赢财富两全保险（分红型）B款 -->
		<xsl:if test="$MainRiskCode = 0003">231203</xsl:if><!-- 中韩卓越财富两全保险（分红型） -->
		<xsl:if test="$MainRiskCode = 0004">221201</xsl:if><!-- 中韩保驾护航两全保险A款 -->
		<xsl:if test="$MainRiskCode = 0005">231204</xsl:if><!-- 中韩智赢财富两全保险（分红型）C款 -->
		<xsl:if test="$MainRiskCode = 0006">221301</xsl:if><!-- 中韩悦未来年金险 -->
	</xsl:template>
	   <!-- 首期缴费方式 -->
	<xsl:template name="tran_BKPayMode">
		<xsl:param name="PayMode">0</xsl:param>
		<xsl:if test="$PayMode = 1">0</xsl:if><!-- 现金 -->
		<xsl:if test="$PayMode = 2">0</xsl:if><!-- 折代扣 -->
		<xsl:if test="$PayMode = 3">0</xsl:if><!-- 卡代扣 -->
		<xsl:if test="$PayMode = 9">0</xsl:if><!-- 对公代扣 -->
	</xsl:template>	
</xsl:stylesheet>
