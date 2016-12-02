<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

<xsl:template match="/">

<TXLife>
	<xsl:copy-of select="TranData/Head"/>
	<TXLifeResponse>
	<TransRefGUID><xsl:value-of select="TranData/Body/TranNo"/></TransRefGUID>
		<!-- 交易流水号 -->
		<TransType><xsl:value-of select="TranData/Body/FuncFlag"/></TransType>
		<!-- 交易码/处理标志 -->
 	 <TransExeDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/></TransExeDate> 
 	 <TransExeTime><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur8Time()"/></TransExeTime> 
 	 <TransResult>
			<ResultCode><xsl:value-of select="TranData/Head/Flag"/></ResultCode>
			<ResultInfo>
				<ResultInfoDesc><xsl:value-of select="TranData/Head/Desc"/></ResultInfoDesc>
			</ResultInfo>
		</TransResult>
		<xsl:apply-templates select="TranData/Body"/>
	</TXLifeResponse>    
</TXLife> 
</xsl:template>   
<xsl:template match="Body">
	<OLifE>
			<!-- 保单数据 -->
			<Holding id="Holding_1">
				<Policy>
					<PolNumber><xsl:value-of select="ContNo "/></PolNumber>
					<!--保单号-->
					<!--保单状态-->
					<PolicyStatus><xsl:value-of select="PolicyStatus "/></PolicyStatus>
					<BenefitMode><xsl:value-of select="BonusGetMode"/></BenefitMode>
					<!-- 给付方式 -->
					<OLifEExtension VendorCode="10">
						<BonusAmnt><xsl:value-of select="BonusAmnt "/></BonusAmnt>
						<!-- 累计红利 -->
					</OLifEExtension>	
				</Policy>
				<FinancialActivity>
					<FinActivityGrossAmt></FinActivityGrossAmt>
					<!--财务活动金额-->
				</FinancialActivity>
			</Holding>	
			<!-- 委托授权书标志, 201002v added -->
			<EntrustFlag></EntrustFlag>		
		</OLifE>
</xsl:template>
</xsl:stylesheet>
