<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes" />
	<xsl:template match="TranData">
		<InsuRet>
			<Main>
				<!-- 交易日期 -->
				<TranDate>
					<xsl:value-of select="Body/TranDate"/>
				</TranDate>
				<!-- 交易时间 -->
				<TranTime>
					<xsl:value-of select="Body/TranTime"/>
				</TranTime>
				<!-- 保险公司代码 -->
				<InsuId>
					<xsl:value-of select="Body/InsuId"/>
				</InsuId>
				<!-- 地区代码 -->
				<ZoneNo>
					<xsl:value-of select="Body/ZoneNo"/>
				</ZoneNo>
				<!-- 网点代码 -->
				<BrNo>
					<xsl:value-of select="Body/BrNo"/>
				</BrNo>
				<!-- 柜员代码 -->
				<TellerNo>
					<xsl:value-of select="Body/TellerNo"/>
				</TellerNo>
				<!-- 交易流水号 -->
				<TransNo>
					<xsl:value-of select="Body/TransNo"/>
				</TransNo>
				<!-- 交易码 -->
				<TranCode>
					<xsl:value-of select="Body/TranCode"/>
				</TranCode>
				<!-- 响应编码 -->
				<ResultCode>
					<xsl:value-of select="Body/ResultCode"/>
				</ResultCode>
				<!-- 响应信息 -->
				<ResultInfo>
					<xsl:value-of select="Body/ResultInfo"/>
				</ResultInfo>
				<!-- 保单号 -->
				<PolicyNo>
					<xsl:value-of select="Body/ContNo"/>
				</PolicyNo>
			</Main>
		</InsuRet>
	</xsl:template>
</xsl:stylesheet>
