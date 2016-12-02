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
	<CarrierCode><xsl:value-of select="../OLifEExtension/CarrierCode"/></CarrierCode > <!--���չ�˾����-->
	<ContNo><xsl:value-of select="Holding/Policy/PolNumber"/></ContNo>
	<RiskCode><xsl:apply-templates select="Holding/Policy/ProductCode" /></RiskCode> <!--���ղ�Ʒ����-->
	<Password><xsl:value-of select="Holding/Policy/OLifEExtension/Password"/></Password> <!--��������-->
	<PayAcc><xsl:value-of select="Holding/Banking/AccountNumber"/></PayAcc> <!--�����ʻ��ʺ�-->
	<AccName ><xsl:value-of select="Holding/Banking/AcctHolderName"/></AccName > <!--�տ��ʻ�����-->
    <PaperTitle ><xsl:value-of select="FormInstance/FormName"/></PaperTitle > <!--��֤����-->
    <PrintNo ><xsl:value-of select="FormInstance/ProviderFormNumber"/></PrintNo > <!--��֤ӡˢ��-->
	<Channel><xsl:value-of select="../OLifEExtension/SourceType"/></Channel><!--���н�������-->
    <RepeatType ><xsl:value-of select="../RepeatType"/></RepeatType ><!--���ͱ�־-->
</xsl:template>

<!-- ���ִ��� -->
<xsl:template name="tran_RiskCode" match="ProductCode">
<xsl:choose>
	<xsl:when test=".=001">231201</xsl:when>	
	<xsl:when test=".=002">231202</xsl:when>
	<xsl:when test=".=003">231203</xsl:when>  
	<xsl:when test=".=004">211901</xsl:when>
	<xsl:when test=".=005">231301</xsl:when>
	<xsl:when test=".=006">221201</xsl:when>
	<xsl:when test=".=007">231204</xsl:when><!-- �к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�C�� -->
	<xsl:when test=".=008">211902</xsl:when><!-- �к���Ӯ����������˺�����A�� -->
	<xsl:when test=".=012">241201</xsl:when><!-- �к���Ӯ�Ƹ���ȫ���գ������ͣ�A��-->
	<xsl:when test=".=013">221206</xsl:when><!-- �к���Խ�Ƹ���ȫ���� -->
	<xsl:when test=".=102">145201</xsl:when><!-- �к����Ӷ�ӯ����ȫ���գ������ͣ� -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template> 

</xsl:stylesheet>
