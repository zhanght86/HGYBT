<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

<xsl:template match="/">

<TXLife>
	<xsl:copy-of select="TranData/Head"/>
	<TXLifeResponse>
	<!-- 交易流水号 -->
	<TransRefGUID><xsl:value-of select="TranData/Body/TranNo"/></TransRefGUID>
	<!-- 交易码/处理标志 -->
	<TransType><xsl:value-of select="TranData/Body/FuncFlag"/></TransType>
		
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
		<Holding>
			<Policy>
				<PolNumber><xsl:value-of select="ContNo"/></PolNumber>
				<Life>
						<!--  险种数目   --> 
  					<CoverageCount>1</CoverageCount> 
						<!-- Optional repeating -->
						<Coverage>
							<!-- 循环节点,返回时按应交序号顺序递增返回 -->
							<PlanName><xsl:value-of select="PlanName"/></PlanName>
							<!-- 险种名称 -->
							<ProductCode><xsl:value-of select="RiskCode"/></ProductCode>
							<!-- 险种代码 -->
							<OLifEExtension VendorCode="10">
								<!-- 应交序号,返回时按顺序递增 -->
								<PaymentOrder>1</PaymentOrder>
								<!-- 缴费金额 -->
								<NextPayAmt><xsl:value-of select="Prem"/></NextPayAmt>
								<!-- 应收日期-->
								<PaymentDate><xsl:value-of select="RecvDate"/></PaymentDate>
								<!-- 应交记录状态-->
								<PaymentState><xsl:value-of select="PaymentState"/></PaymentState>
								<!-- 提示信息-->
								<Remark><xsl:value-of select="Remark"/></Remark>			
							</OLifEExtension>
						</Coverage>
					</Life>	
				</Policy>
				<FinancialActivity>
					<FinActivityGrossAmt><xsl:value-of select="RecvAmount"/></FinActivityGrossAmt>
					<!--应收金额-->
					<FinEffDate><xsl:value-of select="RecvDate"/></FinEffDate> 
					<!-- 应收日期（国寿）  --> 
					<OLifEExtension VendorCode="9">
						<!--  缴费年期   --> 
  					<PaymentYears><xsl:value-of select="PayEndYear"/></PaymentYears> 
						<!-- 收费项目-->
						<PayItm><xsl:value-of select="PayItm"/></PayItm>					
						<!-- 应缴期数 -->
						<PaymentTimes><xsl:value-of select="RecvNum"/></PaymentTimes>
						<!-- 已缴期数 -->
						<PayedTimes><xsl:value-of select="PayedTimes"/> </PayedTimes>
						<PaymentStartDate><xsl:value-of select="PayStartDate"/></PaymentStartDate>
						<!-- 缴费起始日期 -->
						<PaymentEndDate><xsl:value-of select="PayEndDate"/></PaymentEndDate>
						<!-- 缴费终止日期 -->
						<ACCCODE/>
						<!-- for中国人寿，账户代码 -->
					</OLifEExtension>
				</FinancialActivity>
		</Holding>
		<Party id="Party_1">
				<FullName><xsl:value-of select="Name"/></FullName>
				<!-- 投保人姓名 -->
				<GovtID><xsl:value-of select="IDNo"/></GovtID>
				<!-- 投保人证件号码 -->
				<GovtIDTC tc="1"><xsl:value-of select="IDType"/></GovtIDTC>
				<!-- 投保人证件类型 -->				
			</Party>
			<Relation OriginatingObjectID="Holding_1" RelatedObjectID="Party_1" id="Relation_1">
				<OriginatingObjectType tc="4">Holding</OriginatingObjectType>
				<RelatedObjectType tc="6">Party</RelatedObjectType>
				<RelationRoleCode tc="8">Owner</RelationRoleCode>
				<!-- 投保人关系 -->
			</Relation>
	</OLifE>
</xsl:template>
</xsl:stylesheet>
