<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:template match="TranData">
	
	<ABCB2I><!-- 农业银行续期缴费应答报文 -->
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
		<TransTime><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date10to8(Body/TranDate)"/></TransTime>
		<!-- 交易时-->
		<TransDate><xsl:value-of select="Body/TranTime"/></TransDate>
		<!-- 银行代码-->
		<BankCode></BankCode>
		<!--保险公司代码 -->
		<CorpNo></CorpNo>
		<!-- 银行代码-->
		<TransCode>1013</TransCode>
	</Header>
	<App>
		<Ret>
			<RiskCode></RiskCode>
			<ProdCode></ProdCode>
			<!--保单号 -->
			<PolicyNo><xsl:value-of select="Body/ContNo"/></PolicyNo>
			<Appl>
				<IDKind></IDKind>
				<IDCode></IDCode>
				<Name></Name>
			</Appl>
			<PayAcc></PayAcc>
			<PayAmt></PayAmt>
		</Ret>
	</App>
	</ABCB2I>
		
	</xsl:template>
</xsl:stylesheet>
