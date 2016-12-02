<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

<xsl:template match="/">

<TXLife>
	<xsl:copy-of select="TranData/Head"/>
	<TXLifeResponse>
	<TransRefGUID><xsl:value-of select="TranData/Body/TranNo"/></TransRefGUID>
	<TransType>1003</TransType>
 	 <TransExeDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/></TransExeDate> 
 	 <TransExeTime><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur8Time()"/></TransExeTime> 
		<xsl:apply-templates select="TranData/Body"/>
	</TXLifeResponse>    
</TXLife> 
</xsl:template>   
<xsl:template match="Body">
	<OLifE>
		<Holding>
			<Policy>
				<PolNumber><xsl:value-of select="ContNo"/></PolNumber>
				<PolicyStatus><xsl:value-of select="PolicyStatus"/></PolicyStatus>
					<BenefitMode><xsl:value-of select="BonusGetMode "/></BenefitMode>
					<!-- 给付方式 -->
					<OLifEExtension VendorCode="10">
						<BonusAmnt><xsl:value-of select="BonusAmnt"/></BonusAmnt>
						<!-- 累计红利 -->
					</OLifEExtension>	
				</Policy>
				<FinancialActivity>
					<FinActivityGrossAmt><xsl:value-of select="EdorCTPrem"/></FinActivityGrossAmt>
					<!--财务活动金额-->
				</FinancialActivity>
		</Holding>
	</OLifE>
</xsl:template>
</xsl:stylesheet>
