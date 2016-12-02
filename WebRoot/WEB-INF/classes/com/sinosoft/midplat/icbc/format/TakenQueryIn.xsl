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
	<ContNo><xsl:value-of select="Holding/Policy/PolNumber"/></ContNo><!-- 保单号 -->
	<Password><xsl:value-of select="Holding/Policy/OLifEExtension/Password"/></Password> <!--保单密码-->
    <CarrierCode><xsl:value-of select="../OLifEExtension/CarrierCode"/></CarrierCode > <!--保险公司代码-->
	<RiskCode><xsl:apply-templates select="Holding/Policy/ProductCode" /></RiskCode> <!--保险产品代码-->
	<Channel><xsl:value-of select="../OLifEExtension/SourceType"/></Channel><!--银行交易渠道-->
	<AppntIDType ><xsl:apply-templates select="Party[@id='Party_1']/GovtIDTC" /></AppntIDType><!--投保人证件类型--><!-- Modify by chengqi in 20120925 -->
    <AppntIDNo><xsl:value-of select="Party[@id='Party_1']/GovtID"/></AppntIDNo> <!-- 投保人证件号码 --><!-- Modify by chengqi in 20120925 -->
    <AppntName><xsl:value-of select="Party[@id='Party_1']/FullName"/></AppntName><!--投保人姓名--><!-- Modify by chengqi in 20120925 -->
	<IDType ><xsl:apply-templates select="Party[@id='Party_2']/GovtIDTC" /></IDType><!--被保人证件类型-->
    <IDNo><xsl:value-of select="Party[@id='Party_2']/GovtID"/></IDNo><!--被保人证件号码-->
    <Name><xsl:value-of select="Party[@id='Party_2']/FullName"/></Name><!--被保人姓名-->

	
</xsl:template>
<!-- 投保人 -->
<xsl:template name="Appnt" match="Relation[RelationRoleCode=80]">
	<xsl:variable name="PartyID" select="@RelatedObjectID" />
	<xsl:variable name="PartyNode" select="../Party[@id=$PartyID]" />
		<AppntIDType><xsl:apply-templates select="PartyNode/GovtIDTC" /></AppntIDType><!--投保人证件类型-->
        <AppntIDNo><xsl:value-of select="PartyNode/GovtID"/></AppntIDNo> <!-- 投保人证件号码 -->
        <AppntName><xsl:value-of select="PartyNode/FullName"/></AppntName><!--投保人姓名-->
	
</xsl:template>
<!-- 被保人 -->
<xsl:template name="Insured" match="Relation[RelationRoleCode=81]">
		<xsl:variable name="PartyID" select="@RelatedObjectID" />
		<xsl:variable name="PartyNode" select="../Party[@id=$PartyID]" />
		   <IDType><xsl:apply-templates select="PartyNode/GovtIDTC" /></IDType><!--被保人证件类型-->
		   <IDNo><xsl:value-of select="PartyNode/GovtID"/></IDNo> <!-- 被保人证件号码 -->
		   <Name><xsl:value-of select="PartyNode/FullName"/></Name><!--被保人姓名-->
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
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template> 

<!-- 证件类型 -->
<xsl:template name="tran_idtype" match="GovtIDTC">
<xsl:choose> 
	<xsl:when test=".=0">0</xsl:when>	<!-- 身份证 -->
	<xsl:when test=".=1">1</xsl:when>	<!-- 护照 -->
	<xsl:when test=".=2">2</xsl:when>	<!-- 军官证 -->
	<xsl:when test=".=3">A</xsl:when>	<!-- 士兵证  -->
	<xsl:when test=".=4">B</xsl:when>	<!-- 回乡证  -->
	<xsl:when test=".=5">C</xsl:when>	<!-- 临时身份证  -->
	<xsl:when test=".=6">4</xsl:when>	<!-- 户口本  -->
	<xsl:when test=".=7">8</xsl:when>	<!-- 其他  -->
	<xsl:when test=".=9">D</xsl:when>	<!-- 警官证  -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>

</xsl:stylesheet>
