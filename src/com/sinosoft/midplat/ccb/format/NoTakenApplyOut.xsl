<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

<xsl:template match="/">

<Transaction>
	<xsl:if test="/TranData/Head/Flag='0'">
	<Transaction_Body>
	<!-- 险种代码 -->
	<PbInsuType>
			<xsl:call-template name="tran_MainRiskCode">
					<xsl:with-param name="MainRiskCode">
						<xsl:value-of select="TranData/Body/RiskCode"/>
					</xsl:with-param>
			</xsl:call-template>
	</PbInsuType>
	<!-- 保单号 -->
	<PbInsuSlipNo><xsl:value-of select="TranData/Body/ContNo"/></PbInsuSlipNo>
	<!-- 投保人姓名 -->
	<PbHoldName><xsl:value-of select="TranData/Body/AppntName"/></PbHoldName>
	<!-- 被保人姓名 -->
	<LiRcgnName><xsl:value-of select="TranData/Body/InSuName"/></LiRcgnName>
	<!-- 退保金额 -->
	<LiLoanValue><xsl:value-of select="TranData/Body/EdorCTPrem"/></LiLoanValue>
	<!-- 批单号 -->
	<LiRatifyId><xsl:value-of select="TranData/Body/RiskCode"/></LiRatifyId>
	<!-- 投保人证件号 -->
	<PbHoldId><xsl:value-of select="TranData/Body/AppntIdNo"/></PbHoldId>
	<!-- 退保帐号 -->
	<BkAcctNo1><xsl:value-of select="TranData/Body/BankAccNo"/></BkAcctNo1>
	</Transaction_Body>    
	</xsl:if>
</Transaction> 
</xsl:template>   

	<!-- 险种代码  -->
	<xsl:template name="tran_MainRiskCode">
		<xsl:param name="MainRiskCode">0</xsl:param>
		<xsl:if test="$MainRiskCode = 231201">0001</xsl:if><!-- 中韩智赢财富两全保险（分红型）A款 -->
		<xsl:if test="$MainRiskCode = 231202">0002</xsl:if><!-- 中韩智赢财富两全保险（分红型）B款 -->
		<xsl:if test="$MainRiskCode = 231203">0003</xsl:if><!-- 中韩卓越财富两全保险（分红型） -->
		<xsl:if test="$MainRiskCode = 221201">0004</xsl:if><!-- 中韩保驾护航两全保险A款 -->
		<xsl:if test="$MainRiskCode = 231204">0005</xsl:if><!-- 中韩智赢财富两全保险（分红型）C款 -->
		<xsl:if test="$MainRiskCode = 221301">0006</xsl:if><!-- 中韩悦未来年金险-->
	</xsl:template>

</xsl:stylesheet>
