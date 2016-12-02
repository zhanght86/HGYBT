<?xml version="1.0" encoding="utf-8"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

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
			 <xsl:copy-of select="Head/*"/> <!-- -->
	  	</Head>
	<Body>
			<!-- 险种编号 -->
			<RiskCode><xsl:value-of select="App/Req/RiskCode"/></RiskCode>
			<!-- 保单号码 -->
			<ContNo><xsl:value-of select="App/Req/PolicyNo"/></ContNo>
			<!-- 投保单号 -->
			<ProposalPrtNo></ProposalPrtNo>	
			<!-- 保单密码 -->
			<Password><xsl:value-of select="App/Req/Policypwd"/></Password>
	</Body>
</TranData>
</xsl:template>
</xsl:stylesheet>