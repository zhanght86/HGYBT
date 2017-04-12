<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"  exclude-result-prefixes="java">
 	
	<xsl:template match="ABCB2I">
	<TranData><!-- 核心农行续期缴费请求报文 -->
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
				<xsl:choose>
					<xsl:when test="Header/EntrustWay ='11'">
						<TellerNo><xsl:value-of select="Header/Tlid"/></TellerNo>
					</xsl:when>
					<xsl:otherwise>
						<TellerNo>0005</TellerNo>
					</xsl:otherwise>
				</xsl:choose>
		        <!-- 银行交易流水号 -->
				<TranNo><xsl:value-of select="Header/SerialNo"/></TranNo>
				<!-- YBT组织的节点信息 -->
				 <xsl:copy-of select="Head/*"/>
		  	</Head>
		<Body>
				<!-- 保费支付方式代码:Y -->
				<PayMode >B</PayMode >
				<!-- 投保单(印刷)号 -->
			     <ProposalPrtNo>
			     	<xsl:if test="Header/EntrustWay = '11'">
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(App/Req/PolicyApplyNo)"/>
					</xsl:if>
					<xsl:if test="Header/EntrustWay = '04'">
						<xsl:value-of select="java:com.sinosoft.midplat.newabc.format.NewCont.trannoStringBuffer(Header/TransDate,Header/SerialNo)"/>
					</xsl:if>
			     </ProposalPrtNo>
				<!-- 保险公司流水号:Y -->
				<TranNo><xsl:value-of select="Header/InsuSerial"></xsl:value-of></TranNo>
			    <!-- 保单号码 -->
				<ContNo>
					<xsl:if test="Header/EntrustWay = '11'"><xsl:value-of select="App/Req/PolicyNo" /></xsl:if>
					<xsl:if test="Header/EntrustWay = '04'"><xsl:value-of select="java:com.sinosoft.midplat.newabc.format.NewCont.trannoStringBuffer(Header/TransDate,Header/SerialNo)"/></xsl:if>
				</ContNo>
			    <!-- 应收金额:Y -->
				<Prem><xsl:value-of select="App/Req/PayAmt"/></Prem>
			    <!-- 缴费帐号:Y -->
				<PayAcc><xsl:value-of select="App/Req/PayAcc"/></PayAcc>
		</Body>
	</TranData>
	</xsl:template>

</xsl:stylesheet>