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
				<BankCode>0104</BankCode>
				<!--银行代码-->
				<NodeNo><xsl:value-of
						select="Transaction_Header/BkBrchNo" /></NodeNo>
				<!--网点代码-->
				<TellerNo>
					<xsl:value-of select="Transaction_Header/BkTellerNo" />
				</TellerNo>
				<!--柜员代码-->
				<TranNo>
					<xsl:value-of select="Transaction_Header/BkPlatSeqNo" />
				</TranNo>
				<!--交易流水号17-->
					<xsl:copy-of select="Head/*"/>
			</Head>
			<Body>
				<Count>
					<xsl:value-of select="Transaction_Body/BkTotNum" />
				</Count>
				<xsl:for-each select="Transaction_Body/Detail_List/Detail">
					<Detail>
					    <CardType>0105121</CardType><!-- 重空类型 -核心保单编码 -->
						<CardNo><xsl:value-of select="BkVchNo" /></CardNo><!-- 重空印刷号 -->
						<CardState><xsl:call-template name="tran_CardState">
						<xsl:with-param name="CardState">
							<xsl:value-of select="BkFlag2" />
						</xsl:with-param>
					</xsl:call-template></CardState><!-- 重空状态 -->
						<AgentCom><xsl:value-of select="BkActBrch" /></AgentCom><!-- 记帐机构号 -->
						<TranNo><xsl:value-of select="BkChnlSeq" /></TranNo><!-- 建行方记帐流水号 -->
					</Detail>
				</xsl:for-each>
			</Body>
		</TranData>
	</xsl:template>
	
	<!-- 主险种代码 -->
	<xsl:template name="tran_CardState">
		<xsl:param name="CardState">-1</xsl:param>
		<xsl:if test="$CardState = '2'">4</xsl:if>
		<xsl:if test="$CardState = '3'">6</xsl:if>
	</xsl:template>
</xsl:stylesheet>
