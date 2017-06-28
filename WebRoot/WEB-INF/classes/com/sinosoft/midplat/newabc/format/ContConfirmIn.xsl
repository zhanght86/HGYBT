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
			<xsl:choose>
				<xsl:when test="Header/EntrustWay='11'"><!-- 柜面 -->
					<NodeNo><xsl:value-of select="Header/ProvCode"/><xsl:value-of select="Header/BranchNo"/></NodeNo>
				</xsl:when>
				<xsl:otherwise><!-- 电子渠道实际网点存在取实际网点，不存在取虚拟网点 -->
					<xsl:if test="Header/ProvCode != '' and Header/BranchNo != ''">
						<NodeNo><xsl:value-of select="Header/ProvCode"/><xsl:value-of select="Header/BranchNo"/></NodeNo>
					</xsl:if>
					<xsl:if test="Header/ProvCode = '' or Header/BranchNo = ''">
						<NodeNo>ABCWEB</NodeNo>
					</xsl:if>
				</xsl:otherwise>
			</xsl:choose>
			<!-- 柜员代码 -->
			<xsl:choose>
				<xsl:when test="Header/EntrustWay = '11'">
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
			<!-- 销售渠道 -->
			<SaleChannel><xsl:apply-templates select="Header/EntrustWay"/></SaleChannel>
			<!-- 保险单号 -->
			<ContNo />
			<!-- 投保单(印刷)号 -->
			<xsl:choose>
		   		<!-- 柜面 -->
		   		<xsl:when test="Header/EntrustWay='11'">
		   			<ProposalPrtNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(App/Req/PolicyNo)"/></ProposalPrtNo>
		   		</xsl:when>
		   		<xsl:otherwise>
			   		<xsl:variable name="tMaxPrtNo" select="java:com.sinosoft.midplat.util.YBTFun.CreateMaxNo('ABCPRTNO','SN')"></xsl:variable> 
					<xsl:variable name="tPrtNo" select="java:com.sinosoft.midplat.util.YBTFun.PrtNoTo8($tMaxPrtNo,'05')"></xsl:variable> 
					<ProposalPrtNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15($tPrtNo)" /></ProposalPrtNo>
		   		</xsl:otherwise>
		   	</xsl:choose>
			<!-- 保单合同印刷号 -->
			<ContPrtNo/>
			<!-- 试算申请顺序号 -->
			<ApplyNo><xsl:value-of select="App/Req/ApplySerial" /></ApplyNo>
			<!-- 投保日期 -->
			<PolApplyDate></PolApplyDate>
			<BkAcctNo><xsl:value-of select="App/Req/PayAccount" /></BkAcctNo>
			<Prem><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(App/Req/PayAmt)"/></Prem>	
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
