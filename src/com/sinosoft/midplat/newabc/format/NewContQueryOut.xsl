<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:template match="TranData">
	
	<ABCB2I>
		<Header>
		<!--返回码 -->
		<RetCode></RetCode>
		<!--返回信息 -->
		<RetMsg></RetMsg>
		<!--银行交易流水号 -->
		<SerialNo><xsl:value-of select="Head/TranNo"/></SerialNo>
		<!--保险公司流水号 -->
		<InsuSerial></InsuSerial>
		<!-- 交易日期-->
		<TransTime><xsl:value-of select="Head/TransTime"/></TransTime>
		<!-- 交易时-->
		<TransDate><xsl:value-of select="Head/TranDate"/></TransDate>
		<!-- 银行代码-->
		<BankCode></BankCode>
		<!--保险公司代码 -->
		<CorpNo></CorpNo>
		<!-- 银行代码-->
		<TransCode>1005</TransCode>
	</Header>
	<App>
		<Ret>
			<!--核保结果-->
			<AppResult></AppResult>
			<!--保单号 -->
			<PolicyNo><xsl:value-of select ="Body/ContNo"/></PolicyNo>
			<!-- 保险公司方险种代码 -->
			<RiskCode><xsl:value-of select="Body/RiskCode"/></RiskCode>
			<!-- 保费 -->
			<Prem><xsl:value-of select ="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Body/Prem)"/></Prem>
		</Ret>	
	</App>
</ABCB2I>
</xsl:template>
	
</xsl:stylesheet>
