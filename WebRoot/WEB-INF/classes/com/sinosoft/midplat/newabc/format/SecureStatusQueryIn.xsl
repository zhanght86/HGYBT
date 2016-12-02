<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
 	exclude-result-prefixes="java">

<xsl:template match="ABCB2I">
<TranData>
	<!--基本信息-->
	<Head>
	        <!-- 银行交易流水号 -->
			<TranNo><xsl:value-of select="Header/SerialNo"/></TranNo>
			<!-- 地区代码 -->
			<ZoneNo><xsl:value-of select="Header/ProvCode"/></ZoneNo>
			<!-- 网点代码 -->
			<NodeNo>
			<xsl:value-of select="Header/ProvCode"/>
			<xsl:value-of select="Header/BranchNo"/>
			</NodeNo>
	  		<!-- 银行交易日期 -->
	  		<TranDate><xsl:value-of select="Header/TransDate"/></TranDate>
	  		<!-- 交易时间-->
			<TranTime><xsl:value-of select="Header/TransTime"/></TranTime>
			<!-- 柜员代码 -->
			<TellerNo><xsl:value-of select="Header/Tlid"/></TellerNo>
			<!-- 银行代码 -->
			<BankCode>0102</BankCode>
			<!-- YBT组织的节点信息 -->
			 <xsl:copy-of select="Head/*"/>
	</Head>
	<Body>
			<!--保单号 -->
			<ContNo><xsl:value-of select="App/Req/PolicyNo"/></ContNo>
			 <!-- 险种代码 -->
			<RiskCode><xsl:apply-templates select="App/Req/RiskCode"/></RiskCode>
			<!--业务类型  -->
			<BusiType>
					<xsl:call-template name="busitype">
						<xsl:with-param name="BusiType">
							<xsl:value-of select="App/Req/BusinType" />
						</xsl:with-param>
					</xsl:call-template>
			</BusiType>
	</Body>
</TranData>
</xsl:template>

	<!--  业务类型 -->
	<xsl:template name="busitype" >
		<xsl:param name="BusiType"></xsl:param>
		<xsl:if test="$BusiType = 01">07</xsl:if><!-- 犹撤 -->
		<xsl:if test="$BusiType = 02">09</xsl:if><!-- 满期 -->
		<xsl:if test="$BusiType = 03">10</xsl:if><!-- 退保 -->
	</xsl:template>

<!-- 险种代码 -->
<xsl:template name="Code" match="RiskCode">
<xsl:choose>
	<xsl:when test=".=231201">231201</xsl:when>	<!-- 中韩智赢财富两全保险（分红型）A款 -->
	<xsl:when test=".=231202">231202</xsl:when>	<!-- 中韩智赢财富两全保险（分红型）B款 -->
	<xsl:when test=".=231203">231203</xsl:when> 	<!-- 中韩卓越财富两全保险（分红型） -->
	<xsl:when test=".=211901">211901</xsl:when>  	<!-- 中韩安赢借款人意外伤害保险 -->
	<xsl:when test=".=221201">221201</xsl:when>  	<!-- 中韩保驾护航两全保险A款 -->
	<xsl:when test=".=231204">231204</xsl:when>	<!-- 中韩智赢财富两全保险（分红型）C款 -->
	<xsl:when test=".=211902">211902</xsl:when>  	<!-- 中韩安赢借款人意外伤害保险 A款-->
</xsl:choose>
</xsl:template>

</xsl:stylesheet>