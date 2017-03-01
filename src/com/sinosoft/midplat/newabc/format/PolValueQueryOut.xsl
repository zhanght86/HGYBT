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
		<TransDate><xsl:value-of select="Head/TranDate"/></TransDate>
		<!-- 交易时间-->
		<TransTime><xsl:value-of select="Head/TransTime"/></TransTime>
		<!-- 银行代码-->
		<BankCode></BankCode>
		<!--保险公司代码 -->
		<CorpNo></CorpNo>
		<!-- 交易编码-->
		<TransCode>1019</TransCode>
	</Header>
	<App>
		<Ret>
			<!-- 退保可返还金额 -->
			<Amt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/></Amt>
			<!-- 退保手续费 -->
			<FeeAmt></FeeAmt>
			<!-- 现金价值打印行数 -->
			<PrntCount></PrntCount>
			<!-- 现金价值逐行打印 -->
			<Prnt></Prnt>
			<!-- 保单到期日 -->
			<ExpireDate><xsl:value-of select="(Risk[RiskCode=MainRiskCode])/InsuEndDate"/></ExpireDate>
		</Ret>
	</App>
</ABCB2I>
		
</xsl:template>
	
</xsl:stylesheet>
