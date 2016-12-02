<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes" />
	<xsl:template match="/">
		<Transaction>
			<!--报文体-->
			<xsl:if test="/TranData/Head/Flag='0'">
			<Transaction_Body>
			    <!-- 主险份数 -->
				<PbSlipNumb />
				<!-- 起保日期 -->
				<PiStartDate>
					<xsl:value-of
						select="TranData/Body/Risk/CValiDate" />
				</PiStartDate>
				<!-- 终止日期 -->
				<PiEndDate>
					<xsl:value-of
						select="TranData/Body/Risk/InsuEndDate" />
				</PiEndDate>
				<BkTotAmt>
					<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(TranData/Body/Prem)" />
					<!-- 总保费 -->
				</BkTotAmt>
				<BkTxAmt>
					<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(TranData/Body/Prem)" />
					<!-- 首期保费 -->
				</BkTxAmt>
				<PbMainExp>
					<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(TranData/Body/Risk[RiskCode=MainRiskCode]/Prem)" />
					<!-- 首期主险保费-->
				</PbMainExp>
				<BkNum1>0</BkNum1>
				<!-- 保监会要求 -->
				<!-- 每期转账金额 -->
				<PbPayPerAmt>
				   <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(TranData/Body/Prem)" />
				</PbPayPerAmt>
				<!-- 附加险 -->
				<xsl:if test="count(/TranData/Body/Risk) != '1'">
				<Appd_Lis>
					<Appd_Detail>
						<!-- 险种代码  -->
						<LiAppdInsuType><xsl:value-of select="TranData/Body/Risk[RiskCode!=MainRiskCode]/RiskCode" /></LiAppdInsuType>
						<!-- 首期附加险保费 -->	
						<LiAppdInsuExp><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(TranData/Body/Risk[RiskCode!=MainRiskCode]/Prem)" /></LiAppdInsuExp>
					</Appd_Detail>
				</Appd_Lis>
				</xsl:if>
				<!-- 转账频率 -->
				<PbPayFre>
				<xsl:call-template name="tran_payintv">
						<xsl:with-param name="payintv">
							<xsl:value-of select="TranData/Body/PayIntv" />
						</xsl:with-param>
					</xsl:call-template>
				</PbPayFre>
				 <!--  转账期限   PAYENDYEARFLAG   A－年龄，M－月，D－日，Y－年  -->
				 <xsl:variable name="PAYENDYEARFLAG" select="TranData/Body/PayendYearFlag" />
				 <xsl:variable name="PayendYear" select="TranData/Body/PayendYear" />
				 <xsl:if test="$PAYENDYEARFLAG = 'A'">		    
					<PbPayDeadLine>交至<xsl:value-of select="$PayendYear" />周岁</PbPayDeadLine>
				 </xsl:if>
				 <xsl:if test="$PAYENDYEARFLAG = 'M'">
				    <PbPayDeadLine><xsl:value-of select="$PayendYear" />月</PbPayDeadLine>		
				 </xsl:if>
				 <xsl:if test="$PAYENDYEARFLAG = 'D'">
				    <PbPayDeadLine><xsl:value-of select="$PayendYear" />日</PbPayDeadLine>		
				 </xsl:if>
				 <xsl:if test="$PAYENDYEARFLAG = 'Y'">
				 <xsl:choose>
				 <xsl:when test="$PayendYear = 1000"> 
				    <PbPayDeadLine>一次交清</PbPayDeadLine>		
				 </xsl:when>
				 <xsl:otherwise>
				      <PbPayDeadLine><xsl:value-of select="$PayendYear" />年</PbPayDeadLine>	
				 </xsl:otherwise>
				 </xsl:choose>
				</xsl:if>
			</Transaction_Body>
			</xsl:if>
		</Transaction>
	</xsl:template>
	
    <!-- 转账频率转换 -->
	<xsl:template name="tran_payintv">
		<xsl:param name="payintv">0</xsl:param>
		<xsl:if test="$payintv = '-1'">不定期交</xsl:if><!-- 不定期交 -->
		<xsl:if test="$payintv = '0'">一次交清</xsl:if><!-- 趸交打成一次交清 -->
		<xsl:if test="$payintv = '1'">月交</xsl:if><!-- 月交-->
		<xsl:if test="$payintv = '3'">季交</xsl:if><!-- 季交 -->
		<xsl:if test="$payintv = '6'">半年交</xsl:if><!-- 半年交 -->
		<xsl:if test="$payintv = '12'">年交</xsl:if><!-- 年交 -->
	</xsl:template>
</xsl:stylesheet>
