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
	
	<BankCode><xsl:value-of select="OLifEExtension/BankCode"/></BankCode>
</Head>
</xsl:template>

<xsl:template name="Body" match="OLifE">
	
	<TransMode><xsl:value-of select="../TransMode"/></TransMode><!--交易模式-->
    <ContNo><xsl:value-of select="Holding[@id='Holding_1']/Policy/PolNumber"/></ContNo><!--保单号码-->
    <FinActivityType ><xsl:value-of select="Holding[@id='Holding_1']/Policy/FinancialActivity/FinActivityType"/></FinActivityType ><!--财务活动类型-->
	<Prem><xsl:value-of select="Holding[@id='Holding_1']/Policy/FinancialActivity/FinActivityGrossAmt"/></Prem><!--交易金额-->
	<PayAcc><xsl:value-of select="Holding[@id='Acct_1']/Banking/AccountNumber"/></PayAcc><!--银行帐号-->
	<TranNo><xsl:value-of select="../OLifEExtension/Cortransrno"/></TranNo><!--待冲正的交易流水号-->
</xsl:template>

</xsl:stylesheet>
