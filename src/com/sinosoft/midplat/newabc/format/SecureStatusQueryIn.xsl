<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
 	
	<xsl:template match="ABCB2I">
	<TranData>
		<!--基本信息-->
		<Head>
				<!-- 交易日期 -->
				<TranDate><xsl:value-of select="Header/TransDate"/></TranDate>
		  		<!-- 交易时间-->
				<TranTime><xsl:value-of select="Header/TransTime"/></TranTime>
				<!-- 银行代码 -->
				<BankCode>0102</BankCode>
				<!-- 地区代码 -->
				<ZoneNo><xsl:value-of select="Header/ProvCode"/></ZoneNo>
				<!-- 银行网点 -->
				<NodeNo><xsl:value-of select="Header/ProvCode"/><xsl:value-of select="Header/BranchNo"/></NodeNo>
				<!-- 柜员代码 -->
				<TellerNo><xsl:value-of select="Header/Tlid"/></TellerNo>
		        <!-- 交易流水号 -->
				<TranNo><xsl:value-of select="Header/SerialNo"/></TranNo>
				<!-- YBT组织的节点信息 -->
				 <xsl:copy-of select="Head/*"/>
		</Head>
		<Body>
				<!--保单号 -->
				<ContNo><xsl:value-of select="App/Req/PolicyNo"/></ContNo>
				 <!-- 险种代码 -->
				<RiskCode><xsl:apply-templates select="App/Req/RiskCode"/></RiskCode>
				<!--业务类型: 05投连险转换，06投连险部分领取，07犹豫期撤保，09 满期给付，10退保，11续期缴费 -->
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
		<xsl:if test="$BusiType = 02">09</xsl:if><!-- 满期给付 -->
		<xsl:if test="$BusiType = 03">10</xsl:if><!-- 退保 -->
	</xsl:template>

</xsl:stylesheet>