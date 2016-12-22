<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
 	exclude-result-prefixes="java">
<xsl:template match="/TranData"><!-- 核心应答报文转换为贵州银行应答报文 -->
<TXLife>
	<!-- 交易码-->
	<TransNo></TransNo>
	<!-- 签到银行代码-->
	<QdBankCode></QdBankCode>
	<!-- 区域银行代码-->
	<BankCode></BankCode>
	<!--银行网点代码 -->
	<Branch></Branch>
	<!--保险机构代码-->
	<InsuOrgNo></InsuOrgNo>
	<!-- 银行交易日期-->
	<TransExeDate></TransExeDate>
	<!-- 银行交易时间-->
	<TransExeTime></TransExeTime>
	<!-- 银行流水号-->
	<TransRefGUID></TransRefGUID>
	<!-- 银行操作员-->
	<Teller></Teller>
	<!-- 银行操作员姓名-->
	<TellerName></TellerName>
	<CpicWater></CpicWater>
	<ResultCode>0<xsl:value-of select ="Head/Flag"/></ResultCode>
	<ResultInfoDesc><xsl:value-of select ="Head/Desc"/></ResultInfoDesc>
</TXLife>
</xsl:template>
</xsl:stylesheet>