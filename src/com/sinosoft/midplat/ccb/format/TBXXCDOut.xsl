<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes" />
	<xsl:template match="/">
		<Transaction>
			<Transaction_Body>
				<BkRecNum><xsl:value-of select="TranData/Body/BkRecNum" /></BkRecNum><!-- 记录数 -->
				<xsl:for-each select = "TranData/Body/Detail_List/Detail">
					<Detail_List>
						<Detail>
							<PbInsuType>
								<xsl:call-template name="tran_RiskCode">
									<xsl:with-param name="RiskCode">
										<xsl:value-of select="PbInsuType" />
									</xsl:with-param>
								</xsl:call-template>
							</PbInsuType><!-- 险种代码 -->
							<PbInsuSlipNo><xsl:value-of select="PbInsuSlipNo" /></PbInsuSlipNo><!--保单号码-->
							<BkActBrch><xsl:value-of select="BkActBrch" /></BkActBrch>
							<BkActTeller><xsl:value-of select="BkActTeller" /></BkActTeller><!--出单柜员号-->
							<PbHoldName><xsl:value-of select="PbHoldName" /></PbHoldName><!--投保人姓名-->
							<LiRcgnName><xsl:value-of select="LiRcgnName" /></LiRcgnName><!-- 被保人姓名 -->
							<BkTotAmt><xsl:value-of select="BkTotAmt"/></BkTotAmt><!-- 总保费 -->
							<BkFlag1><xsl:value-of select="BkFlag1" /></BkFlag1><!-- 退保标志 -->
							<PbSlipNumb><xsl:value-of select="PbSlipNumb" /></PbSlipNumb><!-- 主险退保份数 -->
							<PiAmount><xsl:value-of select="PiAmount" /></PiAmount><!-- 主险退保金额 -->
							<BkOthTxCode><xsl:value-of select="BkOthTxCode" /></BkOthTxCode><!-- 退保交易类型 -->
							<PiQdDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date10to8(PiQdDate)" /></PiQdDate><!-- 签单日期 -->
							<Bk8Date1><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date10to8(Bk8Date1)" /></Bk8Date1><!-- 退保日期 -->
							<BkNum1><xsl:value-of select="BkNum1" /></BkNum1><!-- 附加险个数 -->
							<!-- <xsl:for-each select = "Appd_List/Appd_Detail"> -->
								<Appd_List>
									<Appd_Detail>
										<LiAppdInsuType><xsl:value-of select="LiAppdInsuType" /></LiAppdInsuType><!-- 附加险退保险种 -->
										<LiAppdInsuNumb><xsl:value-of select="LiAppdInsuNumb" /></LiAppdInsuNumb><!-- 附加险退保份数 -->
										<LiAppdInsuExp><xsl:value-of select="LiAppdInsuExp" /></LiAppdInsuExp><!-- 附加险退保金额 -->
									</Appd_Detail>
								</Appd_List>
							<!-- </xsl:for-each> -->
						</Detail>
					</Detail_List>
				</xsl:for-each>
			</Transaction_Body>
		</Transaction>
	</xsl:template>
	
	<!-- 险种代码 -->
	<xsl:template name="tran_RiskCode">
		<xsl:param name="RiskCode"></xsl:param>
		<xsl:if test="$RiskCode = 313030">0001</xsl:if>
		<xsl:if test="$RiskCode = 321010">0002</xsl:if>
		<xsl:if test="$RiskCode = 313050">0003</xsl:if>
		<xsl:if test="$RiskCode = 321110">0004</xsl:if>
		<xsl:if test="$RiskCode = 321040">0005</xsl:if>
		<xsl:if test="$RiskCode = 321120">0006</xsl:if>
		<xsl:if test="$RiskCode = 321170">0007</xsl:if>
		<xsl:if test="$RiskCode = 321180">0008</xsl:if>
		<xsl:if test="$RiskCode = 321030">0009</xsl:if>
		<xsl:if test="$RiskCode = 321220">0010</xsl:if>
	</xsl:template>
	
</xsl:stylesheet>
