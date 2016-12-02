<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

<xsl:template match="/">
 <TranData>
 	<xsl:apply-templates select="Transaction/Transaction_Header" />
	<Body>
		<xsl:apply-templates select="Transaction/Transaction_Body" />
	</Body>
</TranData>
</xsl:template>
    
<xsl:template name="Head" match="Transaction_Header">
	<Head>
		<!--交易日期 -->
		<TranDate><xsl:value-of select="BkPlatDate"/></TranDate>
		<!--交易时间 -->
		<TranTime><xsl:value-of select="BkPlatTime"/></TranTime>
		<!--柜员号 -->
		<TellerNo><xsl:value-of select="BkTellerNo"/></TellerNo>
		<!-- 交易流水号 -->
		<TranNo><xsl:value-of select="BkPlatSeqNo"/></TranNo>
		<!-- 银行网点 -->
		<NodeNo><xsl:value-of select="BkBrchNo"/></NodeNo>
		<xsl:copy-of select="../Head/*"/>
		<!-- 银行编码 -->
		<BankCode>0104</BankCode>
	</Head>
</xsl:template>

<xsl:template name="Body" match="Transaction_Body">
	<!--保单号码-->
	<ContNo><xsl:value-of select="PbInsuSlipNo"/></ContNo>
	<!--保单密码-->
	<Password><xsl:value-of select="LiInsuSlipPWD"/></Password> 
	<!--保险公司代码-->
	<CarrierCode></CarrierCode > 
	<!--保险产品代码-->
	<RiskCode>
		<xsl:call-template name="tran_MainRiskCode">
			<xsl:with-param name="MainRiskCode">
				<xsl:value-of select="PbInsuType"/>
			</xsl:with-param>
		</xsl:call-template>
	</RiskCode> 
    <!--银行交易渠道-->
	<Channel></Channel>
	<!--投保人证件类型-->
	<IDType></IDType >
	<!-- 投保人证件号码 -->
    <IDNo><xsl:value-of select="PbHoldId"/></IDNo> 
    <!--投保人姓名-->
    <Name></Name>
    <!--被保人证件类型-->
	<IDType></IDType >
	<!--被保人证件号码-->
    <IDNo></IDNo>
    <!--被保人姓名-->
    <Name></Name>

</xsl:template>

	<!-- 主险种代码 -->
	<xsl:template name="tran_MainRiskCode">
		<xsl:param name="MainRiskCode">0</xsl:param>
		<xsl:if test="$MainRiskCode = 0001">231201</xsl:if><!-- 中韩智赢财富两全保险（分红型）A款 -->
		<xsl:if test="$MainRiskCode = 0002">231202</xsl:if><!-- 中韩智赢财富两全保险（分红型）B款 -->
		<xsl:if test="$MainRiskCode = 0003">231203</xsl:if><!-- 中韩卓越财富两全保险（分红型） -->
		<xsl:if test="$MainRiskCode = 0004">221201</xsl:if><!-- 中韩保驾护航两全保险A款 -->
		<xsl:if test="$MainRiskCode = 0005">231204</xsl:if><!-- 中韩智赢财富两全保险（分红型）C款 -->
		<xsl:if test="$MainRiskCode = 0006">221301</xsl:if><!-- 中韩悦未来年金险-->
	</xsl:template>

</xsl:stylesheet>
