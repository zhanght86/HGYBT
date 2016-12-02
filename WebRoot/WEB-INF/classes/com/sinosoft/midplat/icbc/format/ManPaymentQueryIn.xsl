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
	    <xsl:value-of select="OLifEExtension/Branch"/></NodeNo>
	 
	<xsl:copy-of select="../Head/*"/>
	<BankCode><xsl:value-of select="OLifEExtension/BankCode"/></BankCode>
</Head>
</xsl:template>

<xsl:template name="Body" match="OLifE">
	<ContNo><xsl:value-of select="PolNumber"/></ContNo>
	<Password><xsl:value-of select="Holding/Policy/OLifEExtension/Password"/></Password> <!--��������-->
	<CarrierCode><xsl:value-of select="../OLifEExtension/CarrierCode"/></CarrierCode > <!--���չ�˾����-->
	<RiskCode><xsl:value-of select="Holding/Policy/ProductCode"/></RiskCode> <!--���ղ�Ʒ����-->
	<IDType ><xsl:value-of select="Party/GovtIDTC"/></IDType > <!--������֤������-->
	<IDNo ><xsl:value-of select="Party/GovtID"/></IDNo > <!--������֤������-->
	<Name ><xsl:value-of select="Party/FullName"/></Name > <!--����������-->
	<Channel><xsl:value-of select="../OLifEExtension/SourceType"/></Channel><!--���н�������-->
</xsl:template>

</xsl:stylesheet>
