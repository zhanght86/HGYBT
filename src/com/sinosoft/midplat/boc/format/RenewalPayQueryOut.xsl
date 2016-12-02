<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes" />
	<xsl:template match="/">
		<InsuRet><!-- 中国银行续期缴费查询响应报文 -->
			<Main>
				<ResultCode><xsl:value-of select="Insut/Maina/ResultCode" /></ResultCode>
				<ResultInfo><xsl:value-of select="Insut/Maina/ResultInfo" /></ResultInfo>
				<PolicyNo><xsl:value-of select="Insut/Maina/PolicyNo" /></PolicyNo>
				<RecvDate><xsl:value-of select="Insut/Maina/RecvDate" /></RecvDate>
				<RecvAmount><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Insut/Maina/RecvAmount)" /></RecvAmount>
				<RecvNum><xsl:value-of select="Insut/Maina/RecvNum" /></RecvNum>
				<PayStartDate><xsl:value-of select="Insut/Maina/PayStartDate" /></PayStartDate>
				<PayEndDate><xsl:value-of select="Insut/Maina/PayEndDate" /></PayEndDate>
				<AppntName><xsl:value-of select="Insut/Maina/AppntName" /></AppntName>
				<RiskName><xsl:value-of select="Insut/Maina/RiskName" /></RiskName>
			</Main>
		</InsuRet>
	</xsl:template>
	
</xsl:stylesheet>
