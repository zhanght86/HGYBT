<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

<xsl:template match="/">
 <TranData>
 	<xsl:apply-templates select="TXLife/TXLifeRequest" />
	<Body>
		<xsl:apply-templates select="TXLife/TXLifeRequest/OLifE" />
	</Body>
</TranData>
</xsl:template>
    
<xsl:template name="Head" match="TXLifeRequest">
<Head>
	<TranDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date10to8(TransExeDate)"/></TranDate>
	<TranTime><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.time8to6(TransExeTime)"/></TranTime>
	<TellerNo><xsl:value-of select="OLifEExtension/Teller"/></TellerNo>
	<TranNo><xsl:value-of select="TransRefGUID"/></TranNo>
	<NodeNo>
		<xsl:value-of select="OLifEExtension/RegionCode"/>
		<xsl:value-of select="OLifEExtension/Branch"/>
	</NodeNo>
	<xsl:copy-of select="../Head/*"/>
	<BankCode>0101</BankCode>
</Head>
</xsl:template>

<xsl:template name="Body" match="OLifE">
	<CarrierCode><xsl:value-of select="../OLifEExtension/CarrierCode"/></CarrierCode > <!--保险公司代码-->
	<ContNo><xsl:value-of select="Holding/Policy/PolNumber"/></ContNo>
	<RiskCode><xsl:apply-templates select="Holding/Policy/ProductCode" /></RiskCode> <!--保险产品代码-->
	<Password><xsl:value-of select="Holding/Policy/OLifEExtension/Password"/></Password> <!--保单密码-->
	<PayAcc><xsl:value-of select="Holding/Banking/AccountNumber"/></PayAcc> <!--给付帐户帐号-->
	<AccName ><xsl:value-of select="Holding/Banking/AcctHolderName"/></AccName > <!--收款帐户户名-->
    <PaperTitle ><xsl:value-of select="FormInstance/FormName"/></PaperTitle > <!--单证名称-->
    <PrintNo ><xsl:value-of select="FormInstance/ProviderFormNumber"/></PrintNo > <!--单证印刷号-->
	<Channel><xsl:value-of select="../OLifEExtension/SourceType"/></Channel><!--银行交易渠道-->
    <RepeatType ><xsl:value-of select="../RepeatType"/></RepeatType ><!--重送标志-->
</xsl:template>

<!-- 险种代码 -->
<xsl:template name="tran_RiskCode" match="ProductCode">
<xsl:choose>
	<xsl:when test=".=001">231201</xsl:when>	
	<xsl:when test=".=002">231202</xsl:when>
	<xsl:when test=".=003">231203</xsl:when>  
	<xsl:when test=".=004">211901</xsl:when>
	<xsl:when test=".=005">231301</xsl:when>
	<xsl:when test=".=006">221201</xsl:when>
	<xsl:when test=".=007">231204</xsl:when><!-- 中韩智赢财富两全保险（分红型）C款 -->
	<xsl:when test=".=008">211902</xsl:when><!-- 中韩安赢借款人意外伤害保险A款 -->
	<xsl:when test=".=012">241201</xsl:when><!-- 中韩创赢财富两全保险（万能型）A款-->
	<xsl:when test=".=013">221206</xsl:when><!-- 中韩优越财富两全保险 -->
	<xsl:when test=".=102">145201</xsl:when><!-- 中韩附加定盈宝两全保险（万能型） -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template> 

</xsl:stylesheet>
