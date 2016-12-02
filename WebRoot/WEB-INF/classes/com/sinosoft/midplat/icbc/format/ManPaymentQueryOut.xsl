<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

<xsl:template match="/">

<TXLife>
	<xsl:copy-of select="TranData/Head"/>
	<TXLifeResponse>
	<TransRefGUID><xsl:value-of select="TranData/Body/TranNo"/></TransRefGUID>
		<!-- ������ˮ�� -->
		<TransType><xsl:value-of select="TranData/Body/FuncFlag"/></TransType>
		<!-- ������/�����־ -->
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
			<!-- �������� -->
			<Holding id="Holding_1">
				<Policy>
					<PolNumber><xsl:value-of select="ContNo "/></PolNumber>
					<!--������-->
					<!--����״̬-->
					<PolicyStatus><xsl:value-of select="PolicyStatus "/></PolicyStatus>
					<BenefitMode><xsl:value-of select="BonusGetMode"/></BenefitMode>
					<!-- ������ʽ -->
					<OLifEExtension VendorCode="10">
						<BonusAmnt><xsl:value-of select="BonusAmnt "/></BonusAmnt>
						<!-- �ۼƺ��� -->
					</OLifEExtension>	
				</Policy>
				<FinancialActivity>
					<FinActivityGrossAmt></FinActivityGrossAmt>
					<!--�������-->
				</FinancialActivity>
			</Holding>	
			<!-- ί����Ȩ���־, 201002v added -->
			<EntrustFlag></EntrustFlag>		
		</OLifE>
</xsl:template>
</xsl:stylesheet>
