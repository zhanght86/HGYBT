<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"  exclude-result-prefixes="java">
	
<xsl:template match="ABCB2I">
<TranData><!-- 核心收费签单请求报文 -->
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
	        <!-- 交易流水号 -->
			<TranNo><xsl:value-of select="Header/SerialNo"/></TranNo>
			<!-- YBT组织的节点信息 -->
			 <xsl:copy-of select="Head/*"/> <!-- -->
	  	</Head>
	
		<!--投保信息-->
		<Body>
			<!-- 农行自助终端渠道 0柜面 8自助终端 -->
			<xsl:choose>
				<xsl:when test="Header/EntrustWay ='11'">
					<SaleChannel>0</SaleChannel>
				</xsl:when>
				<xsl:when test="Header/EntrustWay ='04'">
					<SaleChannel>8</SaleChannel>
				</xsl:when>
			</xsl:choose>
			
			<!-- 保险单号 -->
			<ContNo />
			<!-- 投保单(印刷)号 -->
			<ProposalPrtNo>
				<xsl:if test="Header/EntrustWay = '11'">
					<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(App/Req/PolicyNo)"/>
				</xsl:if>
				<xsl:if test="Header/EntrustWay = '04'">
					<xsl:value-of select="java:com.sinosoft.midplat.newabc.format.NewCont.trannoStringBuffer(Header/TransDate,Header/SerialNo)"/>
				</xsl:if>
			</ProposalPrtNo>
			<!-- 保单合同印刷号 -->
			<ContPrtNo></ContPrtNo>
			
			<!-- 试算申请顺序号 -->
			<ApplyNo><xsl:value-of select="App/Req/ApplySerial" /></ApplyNo>
			<!-- 投保日期 -->
			<PolApplyDate></PolApplyDate>
			<BkAcctNo><xsl:value-of select="App/Req/PayAccount" /></BkAcctNo>
			<Prem><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(App/Req/PayAmt)"/></Prem>	
		</Body>
	</TranData>
</xsl:template>	

</xsl:stylesheet>
