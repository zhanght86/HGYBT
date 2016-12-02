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
	<ContNo><xsl:value-of select="Holding/Policy/PolNumber"/></ContNo><!-- ������ -->
	<Password><xsl:value-of select="Holding/Policy/OLifEExtension/Password"/></Password> <!--��������-->
    <CarrierCode><xsl:value-of select="../OLifEExtension/CarrierCode"/></CarrierCode > <!--���չ�˾����-->
	<RiskCode><xsl:apply-templates select="Holding/Policy/ProductCode" /></RiskCode> <!--���ղ�Ʒ����-->
	<Channel><xsl:value-of select="../OLifEExtension/SourceType"/></Channel><!--���н�������-->
	<AppntIDType ><xsl:apply-templates select="Party[@id='Party_1']/GovtIDTC" /></AppntIDType><!--Ͷ����֤������--><!-- Modify by chengqi in 20120925 -->
    <AppntIDNo><xsl:value-of select="Party[@id='Party_1']/GovtID"/></AppntIDNo> <!-- Ͷ����֤������ --><!-- Modify by chengqi in 20120925 -->
    <AppntName><xsl:value-of select="Party[@id='Party_1']/FullName"/></AppntName><!--Ͷ��������--><!-- Modify by chengqi in 20120925 -->
	<IDType ><xsl:apply-templates select="Party[@id='Party_2']/GovtIDTC" /></IDType><!--������֤������-->
    <IDNo><xsl:value-of select="Party[@id='Party_2']/GovtID"/></IDNo><!--������֤������-->
    <Name><xsl:value-of select="Party[@id='Party_2']/FullName"/></Name><!--����������-->

	
</xsl:template>
<!-- Ͷ���� -->
<xsl:template name="Appnt" match="Relation[RelationRoleCode=80]">
	<xsl:variable name="PartyID" select="@RelatedObjectID" />
	<xsl:variable name="PartyNode" select="../Party[@id=$PartyID]" />
		<AppntIDType><xsl:apply-templates select="PartyNode/GovtIDTC" /></AppntIDType><!--Ͷ����֤������-->
        <AppntIDNo><xsl:value-of select="PartyNode/GovtID"/></AppntIDNo> <!-- Ͷ����֤������ -->
        <AppntName><xsl:value-of select="PartyNode/FullName"/></AppntName><!--Ͷ��������-->
	
</xsl:template>
<!-- ������ -->
<xsl:template name="Insured" match="Relation[RelationRoleCode=81]">
		<xsl:variable name="PartyID" select="@RelatedObjectID" />
		<xsl:variable name="PartyNode" select="../Party[@id=$PartyID]" />
		   <IDType><xsl:apply-templates select="PartyNode/GovtIDTC" /></IDType><!--������֤������-->
		   <IDNo><xsl:value-of select="PartyNode/GovtID"/></IDNo> <!-- ������֤������ -->
		   <Name><xsl:value-of select="PartyNode/FullName"/></Name><!--����������-->
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
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
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
