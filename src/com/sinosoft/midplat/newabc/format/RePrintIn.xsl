<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" 
	exclude-result-prefixes="java">

<xsl:template match="ABCB2I">
<TranData><!-- 核心保单重打请求报文 -->
	<!--基本信息-->
	  	<Head>
	  		<!-- 交易日期 -->
	  		<TranDate><xsl:value-of select="Header/TransDate"/></TranDate>
	  		<!-- 交易时间-->
			<TranTime><xsl:value-of select="Header/TransTime"/></TranTime>
			<!-- 银行代码 -->
			<BankCode><xsl:value-of select="Head/BankCode"/></BankCode>
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
		<!-- 销售渠道 -->
		<SaleChannel><xsl:apply-templates select="Header/EntrustWay"/></SaleChannel>
		<!-- 保险单号 -->
		<ContNo></ContNo>
		<!-- 投保单(印刷)号 -->
		<ProposalPrtNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(App/Req/PolicyApplyNo)"/></ProposalPrtNo>
		<!-- 新保单合同印刷号 -->
		<ContPrtNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(App/Req/NewVchNo)"/></ContPrtNo>
		<!-- 旧的保单合同印刷号 -->
		<OldContPrtNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(App/Req/OldVchNo)"/></OldContPrtNo>
	</Body>
</TranData>
</xsl:template>

<!-- 委托方式
01-银行网银渠道
02-掌上银行渠道
04-银行自助终端渠道
11-银行柜台渠道
20-保险公司渠道
 -->
<xsl:template name="tran_salechannel" match="EntrustWay">
	<xsl:choose>
		<xsl:when test=".=01">1</xsl:when><!-- 银行网银渠道 -->
		<xsl:when test=".=02">2</xsl:when><!-- 掌上银行渠道 -->
		<xsl:when test=".=04">8</xsl:when><!-- 银行自助终端渠道 -->
		<xsl:when test=".=11">0</xsl:when><!-- 银行柜台渠道 -->
		<xsl:when test=".=20"></xsl:when><!-- 保险公司渠道 -->
		<xsl:otherwise>--</xsl:otherwise>
	</xsl:choose>
</xsl:template>

</xsl:stylesheet>