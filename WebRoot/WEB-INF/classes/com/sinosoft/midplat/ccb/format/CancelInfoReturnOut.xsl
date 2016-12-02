<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
	<xsl:template match="/">
		<Transaction>
			<!--报文体-->
			<xsl:if test="/TranData/Head/Flag='0'">
			<Transaction_Body>
				<BkRecNum>
					<xsl:value-of select="count(/TranData/CancelDetails/CancelDetail)" />
				</BkRecNum>
				<!--记录数-->
				<Detail_List>
					<xsl:for-each select="/TranData/CancelDetails/CancelDetail">
						<Detail>
							<!-- 退保单子明细信息 -->
							<PbInsuType>
								<xsl:call-template name="tran_RiskCode">
									<xsl:with-param name="RiskCode">
										<xsl:value-of select="RiskCode"/>
									</xsl:with-param>
								</xsl:call-template>
							</PbInsuType>
							<!--险种代码-->
							<PbInsuSlipNo>
								<xsl:value-of select="ContNo"/>
							</PbInsuSlipNo>
							<!--保单号码-->
							<PbHoldName>
								<xsl:value-of select="AppntName"/>
							</PbHoldName>
							<!--出单机构代码-->
							<BkActBrch>
								<xsl:value-of select="BrNo"/>
							</BkActBrch>
							<!--出单柜员号-->
							<BkActTeller>
								<xsl:value-of select="TellerNo"/>
							</BkActTeller>
							<!--投保人姓名-->
							<LiRcgnName>
								<xsl:value-of select="InsuredName"/>
							</LiRcgnName>
							<!--被保人姓名-->
							<BkTotAmt>
								<xsl:value-of select="Prem"/>
							</BkTotAmt>
							<!--总保费-->
							<BkFlag1>
								<xsl:value-of select="CancelFlag"/>
							</BkFlag1>
							<!--退保标志-->
							<PbSlipNumb>
								<xsl:value-of select="CancelMult"/>
							</PbSlipNumb>
							<!--退保份数-->
							<PiAmount>
								<xsl:value-of select="CancelPrem"/>
							</PiAmount>
							<!--退保金额-->
							<BkOthTxCode>
								<xsl:value-of select="CancelType"/>
							</BkOthTxCode>
							<!--退保交易类型-->
							<PiQdDate>
								<xsl:value-of select="java:com.sinosoft.midplat.channel.util.DateUtil.formatTrans(SignDate,'yyyy-MM-dd','yyyyMMdd')"/>
							</PiQdDate>
							<!--签单日期-->
							<Bk8Date1>
								<xsl:value-of select="java:com.sinosoft.midplat.channel.util.DateUtil.formatTrans(CancelDate,'yyyy-MM-dd','yyyyMMdd')"/>
							</Bk8Date1>
							<!--退保日期-->
						</Detail>
					</xsl:for-each>
				</Detail_List>
			</Transaction_Body>
			</xsl:if>
		</Transaction>
	</xsl:template>
	<!-- 	<xsl:template name="tran_cancelflag">-->
		<!--退保标志转换--> 
	<!-- 	<xsl:param name="cancelflag">0</xsl:param> 
		<xsl:choose>目前交行销售险种均是保险全单犹豫期退保,写死为G
			<xsl:when test="$cancelflag = 'PA'">20</xsl:when>
			<xsl:when test="$cancelflag = 12">12</xsl:when>
			<xsl:when test="$cancelflag = 1">1</xsl:when>
			<xsl:when test="$cancelflag = 6">6</xsl:when>
			<xsl:when test="$cancelflag = 3">3</xsl:when>
			<xsl:when test="$cancelflag = -1">-1</xsl:when>
		</xsl:choose>
	</xsl:template>   -->
	
	<xsl:template name="tran_RiskCode">
		<xsl:param name="RiskCode">0</xsl:param>
		<xsl:if test="$RiskCode = 331201">0002</xsl:if>
		<xsl:if test="$RiskCode = 230701">0001</xsl:if>
		<xsl:if test="$RiskCode = 331301">0003</xsl:if>
		<xsl:if test="$RiskCode = 331601">0004</xsl:if>
		<xsl:if test="$RiskCode = 240501">0005</xsl:if>
		<xsl:if test="$RiskCode = 331701">0006</xsl:if>
	</xsl:template>
</xsl:stylesheet>
