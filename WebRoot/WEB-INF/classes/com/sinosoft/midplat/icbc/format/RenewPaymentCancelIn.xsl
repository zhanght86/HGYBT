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
	
	<TransMode><xsl:value-of select="../TransMode"/></TransMode><!--����ģʽ-->
    <ContNo><xsl:value-of select="Holding[@id='Holding_1']/Policy/PolNumber"/></ContNo><!--��������-->
    <FinActivityType ><xsl:value-of select="Holding[@id='Holding_1']/Policy/FinancialActivity/FinActivityType"/></FinActivityType ><!--��������-->
	<Prem><xsl:value-of select="Holding[@id='Holding_1']/Policy/FinancialActivity/FinActivityGrossAmt"/></Prem><!--���׽��-->
	<PayAcc><xsl:value-of select="Holding[@id='Acct_1']/Banking/AccountNumber"/></PayAcc><!--�����ʺ�-->
	<TranNo><xsl:value-of select="../OLifEExtension/Cortransrno"/></TranNo><!--�������Ľ�����ˮ��-->
</xsl:template>

</xsl:stylesheet>
