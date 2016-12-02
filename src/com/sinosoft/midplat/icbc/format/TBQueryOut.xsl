<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
<xsl:template match="/">
<TXLife>
<xsl:copy-of select="TranData/Head" />
	<TXLifeResponse>
		<!-- <TransResult>
			<ResultCode>
				<xsl:if test="TranData/Head/Flag=0">0000</xsl:if>
				<xsl:if test="TranData/Head/Flag=1">1234</xsl:if>
			</ResultCode>
			<ResultInfo>
				<ResultInfoDesc><xsl:value-of select="TranData/Head/Desc"/></ResultInfoDesc>
			</ResultInfo>
		</TransResult>-->
		<!-- 交易成功才返回以下节点 -->
		<OLifE>
			<Holding>
				<Policy>
					<PolNumber><xsl:value-of select="TranData/Body/PolNumber"/></PolNumber>
					<PolicyStatus>
							<xsl:if test="TranData/Body/PolicyStatus=0">无效</xsl:if>
							<xsl:if test="TranData/Body/PolicyStatus=1">有效</xsl:if>
					</PolicyStatus>
					<BenefitMode><xsl:value-of select="TranData/Body/BenefitMode"/></BenefitMode> 
					<OLifEExtension VendorCode="10">
							<BonusAmnt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(TranData/Body/BonusAmnt)"/></BonusAmnt>
					</OLifEExtension>
				</Policy>
				<FinancialActivity>
						<FinActivityGrossAmt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(-TranData/Body/FinActivityGrossAmt)"/></FinActivityGrossAmt>
				</FinancialActivity>
			</Holding>
		</OLifE>
   </TXLifeResponse> 
</TXLife>


</xsl:template>

</xsl:stylesheet>