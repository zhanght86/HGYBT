<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
	<xsl:template match="/">
		<Transaction>
			<!--报文体-->
			<xsl:if test="/TranData/Head/Flag='0'">
			<Transaction_Body>
				<BkRecNum>
					<xsl:value-of select="count(/TranData/Body/Detail)" />
				</BkRecNum>
				<!--记录数-->
				<Detail_List>
					<xsl:for-each select="/TranData/Body/Detail">
					<!-- 退保单子明细信息 -->
						<Detail>							
							<!--险种代码-->
							<PbInsuType>
								<xsl:call-template name="tran_RiskCode">
									<xsl:with-param name="RiskCode">
										<xsl:value-of select="RiskCode"/>
									</xsl:with-param>
								</xsl:call-template>
							</PbInsuType>		
							<!--保单号码-->		
							<PbInsuSlipNo>
								<xsl:value-of select="ContNo"/>
							</PbInsuSlipNo>			
							<!--出单机构代码-->
							<BkActBrch>
								<xsl:value-of select="AgentCom"/>
								<!--出单柜员号-->
							<BkActTeller>
								<xsl:value-of select="BankAgent"/>
							</BkActTeller>
							</BkActBrch>
							<!--投保人姓名-->
							<PbHoldName>
								<xsl:value-of select="AppntName"/>
							</PbHoldName>
							<!--被保人姓名-->
							<LiRcgnName>
								<xsl:value-of select="InsuredName"/>
							</LiRcgnName>
							<!--总保费-->
							<BkTotAmt>
								<xsl:value-of select="SumPrem"/>
							</BkTotAmt>
							<!--退保标志-->
							<BkFlag1>G</BkFlag1>
							<!--主险退保份数-->
							<PbSlipNumb>
								<xsl:value-of select="Mult"/>
							</PbSlipNumb>
							<!--主险退保金额-->
							<PiAmount>
								<xsl:value-of select="EdorCTPrem"/>
							</PiAmount>
							<!-- 附加险个数 -->
							<BkNum1>
								<xsl:value-of select="AddRiskCount"/>
							</BkNum1>
							<!-- 附加险的信息 -->
							<xsl:apply-templates select="AddRiskDetail/AddRisk"/>
							
							<!--退保交易类型-->
							<BkOthTxCode>
								<xsl:value-of select="EdorCTType"/>
							</BkOthTxCode>
							<!--签单日期-->
							<PiQdDate>
								<xsl:value-of select="SignDate"/>
							</PiQdDate>
							<!--退保日期-->
							<Bk8Date1>
								<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.formatTrans(CancelDate,'yyyy-MM-dd','yyyyMMdd')"/>
							</Bk8Date1>
						</Detail>
					</xsl:for-each>
				</Detail_List>
			</Transaction_Body>
			</xsl:if>
		</Transaction>
	</xsl:template>

	
	<xsl:template name="tran_RiskCode">
		<xsl:param name="RiskCode">231201</xsl:param>
		<xsl:if test="$RiskCode = 231201">0001</xsl:if><!-- 中韩智赢财富两全保险（分红型）A款 -->
		<xsl:if test="$RiskCode = 231202">0002</xsl:if><!-- 中韩智赢财富两全保险（分红型）B款 -->
		<xsl:if test="$RiskCode = 231203">0003</xsl:if><!-- 中韩卓越财富两全保险（分红型） -->
		<xsl:if test="$RiskCode = 221201">0004</xsl:if><!-- 中韩保驾护航两全保险A款 -->
		<xsl:if test="$RiskCode = 231204">0005</xsl:if><!-- 中韩智赢财富两全保险（分红型）C款 -->
	</xsl:template>
	
	<xsl:template name="AddRisk">
		
		<xsl:if test="AddRiskCount!='' or AddRiskCount!='0'">
			<Appd_List>
				<xsl:for-each select="AddRisk">
				<Appd_Detail>
					<!-- 附加险退保险种 -->
					<LiAppdInsuType><xsl:value-of select="RiskCode"/></LiAppdInsuType>
					<!-- 附加险退保份数 -->
					<LiAppdInsuNumb><xsl:value-of select="EdorCTPrem"/></LiAppdInsuNumb>
					<!-- 附加险退保金额 -->
					<LiAppdInsuExp><xsl:value-of select="Mult"/></LiAppdInsuExp>
				</Appd_Detail>
				</xsl:for-each>
			</Appd_List>
		</xsl:if>
	</xsl:template>
</xsl:stylesheet>
