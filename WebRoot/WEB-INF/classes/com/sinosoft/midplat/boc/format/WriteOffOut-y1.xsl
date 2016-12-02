<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes" />
	<xsl:template match="TranData">
		<InsuRet>
			<Main>
				<!--交易日期-->
				<TranDate><xsl:value-of select="Head/TranDate" /></TranDate>
				<!--交易时间-->
				<TranTime><xsl:value-of select="Head/TranTime"/></TranTime>
				<!--银行代码-->
				<BankCode>BOC</BankCode>
				<!--保险公司代码-->
				<InsuId/>
				<!--地区代码-->
				<ZoneNo/>
				<!--网点代码-->
				<BrNo><xsl:value-of select="Head/NodeNo" /></BrNo>
				<!--柜员代码-->
				<TellerNo><xsl:value-of select="Head/TellerNo" /></TellerNo>
				<!--交易流水号-->
				<TransNo><xsl:value-of select="Head/TranNo" /></TransNo>
				<!--交易码-->
				<TranCode/>
				<!--渠道标识-->
				<Channel/>
				<!--原出单交易流水号-->
				<OriginTransNo/>
				<!--保单号-->
				<PolicyNo><xsl:value-of select="Main/ContNo" /></PolicyNo>
				<!--保险费-->
				<Premium/>
			</Main>
		</InsuRet>
	</xsl:template>
</xsl:stylesheet>
