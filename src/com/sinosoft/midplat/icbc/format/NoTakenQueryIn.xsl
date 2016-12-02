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
	<BankCode>0101</BankCode>
</Head>
</xsl:template>


<xsl:template name="Body" match="OLifE">
	<ContNo><xsl:value-of select="Holding/Policy/PolNumber"/></ContNo>
    <CarrierCode><xsl:value-of select="../OLifEExtension/CarrierCode"/></CarrierCode > <!--���չ�˾����-->
	<Channel><xsl:value-of select="../OLifEExtension/SourceType"/></Channel><!--���н�������-->
	<AppntIDType ><xsl:apply-templates select="Party[@id='Party_1']/GovtIDTC" /></AppntIDType><!--Ͷ����֤������--><!-- modify by chengqi in 20120928 -->
    <AppntIDNo><xsl:value-of select="Party[@id='Party_1']/GovtID"/></AppntIDNo> <!-- Ͷ����֤������ --><!-- modify by chengqi in 20120928 -->
    <AppntName><xsl:value-of select="Party[@id='Party_1']/FullName"/></AppntName><!--Ͷ��������--><!-- modify by chengqi in 20120928 -->
	<IDType ><xsl:apply-templates select="Party[@id='Party_2']/GovtIDTC" /></IDType><!--������֤������-->
    <IDNo><xsl:value-of select="Party[@id='Party_2']/GovtID"/></IDNo><!--������֤������-->
    <Name><xsl:value-of select="Party[@id='Party_2']/FullName"/></Name><!--����������-->
</xsl:template>

<!-- ֤������ -->
<xsl:template name="tran_idtype" match="GovtIDTC">
<xsl:choose> 
	<xsl:when test=".=0">0</xsl:when>	<!-- ���֤ -->
	<xsl:when test=".=1">1</xsl:when>	<!-- ���� -->
	<xsl:when test=".=2">2</xsl:when>	<!-- ����֤ -->
	<xsl:when test=".=3">A</xsl:when>	<!-- ʿ��֤  -->
	<xsl:when test=".=4">B</xsl:when>	<!-- ����֤  -->
	<xsl:when test=".=5">C</xsl:when>	<!-- ��ʱ���֤  -->
	<xsl:when test=".=6">4</xsl:when>	<!-- ���ڱ�  -->
	<xsl:when test=".=7">8</xsl:when>	<!-- ����  -->
	<xsl:when test=".=9">D</xsl:when>	<!-- ����֤  -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>

</xsl:stylesheet>
