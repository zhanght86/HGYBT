<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:template match="TranData">
	
	<TranData>
	<Head>
		<!-- YBT组织的节点信息 -->
		 <xsl:copy-of select="Head/*"/> <!-- -->
	</Head>
	<Body>
		<xsl:for-each select="Body/Detail">
		 	<!--循环节点 -->
			<Detail>
				<!--险种代码-->
				<RiskCode><xsl:value-of select="RiskCode"/></RiskCode>
				<!--保单号-->
				<ContNo><xsl:value-of select="ContNo"/></ContNo>
				<!--保费-->
				<Prem><xsl:value-of select="Prem"/></Prem>
				<!--保额-->
				<Amnt><xsl:value-of select="Amnt"/></Amnt>
				<!--缴费方式-->
				<PayIntv><xsl:apply-templates select="PayIntv"/></PayIntv >
				<!--缴费期间-->
				<PayEndYear><xsl:value-of select="PayEndYear"/></PayEndYear>
				<!--缴费期间类型-->
				<PayEndYearFlag><xsl:apply-templates select="PayEndYearFlag"/></PayEndYearFlag>
				<!--保险期间-->
				<InsuYear><xsl:value-of select="InsuYear"/></InsuYear>
				<!--保险期间类型-->
				<InsuYearFlag><xsl:apply-templates select="InsuYearFlag"/></InsuYearFlag>
			</Detail>
		</xsl:for-each>
	  </Body>
	</TranData>
		
	</xsl:template>
	

<xsl:template name="tran_payintv" match="PayIntv">
	<xsl:choose>
		<xsl:when test=".=0">1</xsl:when>	<!-- 趸交 -->
		<xsl:when test=".=1">2</xsl:when>	<!-- 月交 -->
		<xsl:when test=".=3">3</xsl:when>	<!-- 季交 -->
		<xsl:when test=".=6">4</xsl:when>	<!-- 半年交 -->
		<xsl:when test=".=12">5</xsl:when>	<!-- 年交 -->
		<xsl:when test=".=-1">0</xsl:when>	<!-- 不定期 -->
	</xsl:choose>
</xsl:template>
	
<xsl:template name="tran_PayEndYearFlag" match="PayEndYearFlag">
	<xsl:choose>
		<xsl:when test=".='A'">0</xsl:when>	<!-- 趸缴-->
		<xsl:when test=".='M'">2</xsl:when>	<!-- 月 -->
		<xsl:when test=".='D'">3</xsl:when>	<!-- 日 -->
		<xsl:when test=".='Y'">4</xsl:when>	<!-- 年 -->
		<xsl:otherwise></xsl:otherwise>  
	</xsl:choose>
</xsl:template>		
	
<xsl:template name="tran_insuyearflag" match="InsuYearFlag">
	<xsl:choose>
		<xsl:when test=".='A'">5</xsl:when>	<!-- 年龄 -->
		<xsl:when test=".='M'">2</xsl:when>	<!-- 月 -->
		<xsl:when test=".='D'">1</xsl:when>	<!-- 日 -->
		<xsl:when test=".='Y'">4</xsl:when>  	<!-- 年 -->
		<xsl:otherwise></xsl:otherwise>  
	</xsl:choose>
</xsl:template>		
	
</xsl:stylesheet>
