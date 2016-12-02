<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:template match="TranData">
	
	<ABCB2I><!-- 农业银行保全申请应答报文 -->
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
			<!--险种名称-->
			<InsuName><xsl:value-of select="Body/RiskName"/></InsuName>
			<!--保单号 -->
			<PolicyNo><xsl:value-of select="Body/ContNo"/></PolicyNo>
			<!-- 拟领取金额 -->
			<OccurBala><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Body/EdorCTPrem)"/></OccurBala>
			<!--保全生效日-->
			<BQValidDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date10to8(Body/CValiDate)"/></BQValidDate>
			<!--逐行打印行数-->
			<PrntCount>5</PrntCount>
			<!--逐行打印内容-->
			<Prnt1>　　　　受理号：<xsl:value-of select="Body/EdorAcceptNo"/>，上述保单自申请之日起效力终止。</Prnt1> 
			<Prnt2></Prnt2>
			<Prnt3></Prnt3> 
			<Prnt4></Prnt4>
			<Prnt5></Prnt5>
		</Ret>
	</App>
	</ABCB2I>
		
	</xsl:template>
</xsl:stylesheet>
