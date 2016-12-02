<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
	<xsl:template match="TranData">
		<TranData>
			<Head>
				<xsl:copy-of select="Head/*"/>
			</Head>
			<Body>
				<Count><xsl:value-of select="Body/Count" /></Count>
				<xsl:for-each select="Body/Detail">
					<Detail>
						<TranDate><xsl:value-of select="TranDate" /></TranDate>
						<NodeNo><xsl:value-of select="NodeNo" /></NodeNo>
						<TellerNo><xsl:value-of select="TellerNo" /></TellerNo>
						<TranNo><xsl:value-of select="TranNo" /></TranNo>
						<ProposalPrtNo><xsl:value-of select="ProposalPrtNo" /></ProposalPrtNo>
						<!-- 销售渠道,核心银保渠道的销售渠道为03，核心此字段的值是写死的，所以为了保证数据源的原始性，还是传原味的数据给核心吧-->
						<SaleChannel><xsl:apply-templates select="SaleChannel" /></SaleChannel>
						<AppFlag><xsl:value-of select="AppFlag" /></AppFlag>
						<AppntName><xsl:value-of select="AppntName" /></AppntName>
						<AppntIDType><xsl:apply-templates select="AppntIDType" /></AppntIDType><!-- 证件类型需要映射 -->
						<AppntIDNo><xsl:value-of select="AppntIDNo" /></AppntIDNo>
						<AccNo><xsl:value-of select="AccNo" /></AccNo>
					</Detail>
				</xsl:for-each>
			</Body>
		</TranData>
	</xsl:template>
	
<!-- 证件类型 -->
<xsl:template name="tran_idtype" match="AppntIDType">
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
	<xsl:otherwise></xsl:otherwise>
</xsl:choose>
</xsl:template>

<!-- 销售渠道-->
<xsl:template name="tran_SaleChannel" match="SaleChannel">
<xsl:choose> 
	<xsl:when test=".=0">0</xsl:when>	<!-- 柜面-->
	<xsl:when test=".=1">1</xsl:when>	<!-- 网银 -->
	<xsl:when test=".=2">2</xsl:when>	<!-- 电银 -->
	<xsl:when test=".=3">3</xsl:when>	<!-- 法人营销  -->
	<xsl:when test=".=4">4</xsl:when>	<!-- 全部  -->
	<xsl:when test=".=5">5</xsl:when>	<!-- 手机银行  -->
	<xsl:otherwise>03</xsl:otherwise>
</xsl:choose>
</xsl:template>
</xsl:stylesheet>
