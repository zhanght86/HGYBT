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
	 <FuncFlag><xsl:value-of select="TransType"/></FuncFlag>
	<xsl:copy-of select="../Head/*"/>
	<BankCode><xsl:value-of select="OLifEExtension/BankCode"/></BankCode>
</Head>
</xsl:template>

<xsl:template name="Body" match="OLifE">
	
	<CarrierCode><xsl:value-of select="../OLifEExtension/CarrierCode"/></CarrierCode > <!--���չ�˾����-->
	<ContNo><xsl:value-of select="Holding[@id='Holding_1']/Policy/PolNumber"/></ContNo> <!--��������-->
	<RiskCode><xsl:value-of select="Holding/Policy/ProductCode"/></RiskCode> <!--���ղ�Ʒ����-->
  	<Password><xsl:value-of select="Holding/Policy/OLifEExtension/Password"/></Password> <!--��������-->
  	<PayAcc><xsl:value-of select="Holding/Banking/AccountNumber"/></PayAcc> <!--�����ʻ��ʺ�-->
	<AccName ><xsl:value-of select="Holding/Banking/AcctHolderName"/></AccName > <!--�տ��ʻ�����-->
	<PaperTitle ><xsl:value-of select="FormInstance/FormName"/></PaperTitle > <!--��֤����-->
	<PrintNo ><xsl:value-of select="FormInstance/ProviderFormNumber"/></PrintNo > <!--��֤ӡˢ��-->
	<Channel><xsl:value-of select="../OLifEExtension/SourceType"/></Channel><!--���н�������-->
	<EntrustFlag ></EntrustFlag> <!--ί����Ȩ���־-->
</xsl:template>

</xsl:stylesheet>
