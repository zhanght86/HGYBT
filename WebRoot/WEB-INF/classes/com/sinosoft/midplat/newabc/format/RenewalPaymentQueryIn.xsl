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
				<!-- 保单号码 -->
				<ContNo><xsl:value-of select="App/Req/PolicyNo"/></ContNo>
				 <!--保单密码-->
				<Password></Password>
				<!--保险公司代码-->
				<CarrierCode></CarrierCode > 
				<!--保险产品代码-->
				<RiskCode><xsl:value-of select="App/Req/RiskCode"/></RiskCode> 
				<!--投保人证件类型-->
				<IDType ></IDType > 
				<!--投保人证件号码-->
				<IDNo ></IDNo > 
				<!--投保人姓名-->
				<Name ></Name > 
				<!--银行交易渠道-->
				<Channel>1</Channel>
				
				<!-- 投保单(印刷)号 -->
				<ProposalPrtNo></ProposalPrtNo>
		</Body>
	</TranData>
	</xsl:template>

</xsl:stylesheet>