<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
	<xsl:template match="TranData">
		<TranData>
			<Head>
				<xsl:copy-of select="Head/*"/>
			</Head>
			<Body>
			<!-- 保全数据回传返回报文相关字段Mapping -->
			<xsl:apply-templates select="Body/Detail"/>
			</Body>
		</TranData>
</xsl:template>

		<xsl:template name="policyDtl" match="Detail">			
					<Detail>
						<TranDate><xsl:value-of select="TranDate" /></TranDate>
						
						<BusiType><!-- 业务种类，需要Mapping -->
						<xsl:apply-templates select="BusiType" />
						</BusiType>
						
						<ModifyDate><xsl:value-of select="ModifyDate" /></ModifyDate><!--  保单状态变更日期 -->
						<InsuId>050</InsuId><!-- 保险公司代码，工行定义的，中韩为固定值 050 -->
						<NodeNo><xsl:value-of select="NodeNo" /></NodeNo>
						<ProposalPrtNo><xsl:value-of select="ProposalPrtNo" /></ProposalPrtNo>
						<ContNo><xsl:value-of select="ContNo" /></ContNo>
						<AppntName><xsl:value-of select="AppntName" /></AppntName>
						
						<AppntIDType><!-- 客户证件类型 ，需要Mapping -->
						<xsl:apply-templates select="AppntIDType" />
						</AppntIDType>
						
						<AppntIDNo><xsl:value-of select="AppntIDNo" /></AppntIDNo>
						
						<State><!-- 保单最新状态，需要映射 -->
						<xsl:apply-templates select="State" />
						</State>
						
						<InsuEndDate><xsl:value-of select="InsuEndDate" /></InsuEndDate><!-- 保单到期日期 -->
						<Bak1><xsl:value-of select="Bak1" /></Bak1>
						<Bak2><xsl:value-of select="Bak2" /></Bak2>
						<Bak3><xsl:value-of select="Bak3" /></Bak3>
						<Bak4><xsl:value-of select="Bak4" /></Bak4>
					</Detail>
		</xsl:template>

<!-- 业务类型  核心-》银行的  -->
<xsl:template name="tran_BusiType" match="BusiType">
<xsl:choose>
	<xsl:when test=".=01">001</xsl:when>	<!-- 001满期给付 -->
	<xsl:when test=".=06">002</xsl:when>	<!-- 002犹豫期撤保-->
	<xsl:when test=".=02">003</xsl:when>	<!-- 003退保 -->
	<xsl:when test=".=15">004</xsl:when>	<!-- 004续期交费  -->
	<xsl:when test=".=16">006</xsl:when>	<!-- 006其他保单失效类业务  -->
	<xsl:when test=".=04">099</xsl:when>	<!-- 099理赔终止  -->
	
	<!-- 核心暂时没有 以下状态-->
	<xsl:when test=".=5">005</xsl:when>	<!-- 005追加投保  -->
	<xsl:otherwise></xsl:otherwise>
</xsl:choose>
</xsl:template>
	
<!-- 证件类型  核心-》银行的 -->
<xsl:template name="tran_AppntIDType" match="AppntIDType">
<xsl:choose> 
	<xsl:when test=".=0">0</xsl:when>	<!-- 身份证 -->
	<xsl:when test=".=1">1</xsl:when>	<!-- 护照 -->
	<xsl:when test=".=2">2</xsl:when>	<!-- 军官证 -->
	<xsl:when test=".=A">3</xsl:when>	<!-- 士兵证  -->
	<xsl:when test=".=B">4</xsl:when>	<!-- 回乡证  -->
	<xsl:when test=".=C">5</xsl:when>	<!-- 临时身份证  -->
	<xsl:when test=".=4">6</xsl:when>	<!-- 户口本  -->
	<xsl:when test=".=8">7</xsl:when>	<!-- 其他  -->
	<xsl:when test=".=D">9</xsl:when>	<!-- 警官证  -->
										<!--12  外国人居留证  -->
	<xsl:otherwise>7</xsl:otherwise>
</xsl:choose>
</xsl:template>

<!-- 保单最新状态   核心-》银行的    -->
<xsl:template name="tran_State" match="State">
<xsl:choose>
	<xsl:when test=".=15">12</xsl:when>	<!-- 12-保单有效 -->
	<xsl:when test=".=06">14</xsl:when>	<!-- 14-犹豫期退保保单已终止 -->
	<xsl:when test=".=02">20</xsl:when>  <!-- 20-退保终止 -->
	<xsl:when test=".=04">21</xsl:when>	<!-- 21-理赔终止 -->
	<xsl:when test=".=16">22</xsl:when>	<!-- 22-其他保单失效状态 -->
	<xsl:when test=".=01">23</xsl:when>	<!-- 23-满期给付终止 -->
	<xsl:otherwise></xsl:otherwise>
</xsl:choose>
</xsl:template>

</xsl:stylesheet>
