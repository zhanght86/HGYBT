<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes" />
	<xsl:template match="/">
		<InsuRet><!-- 中国银行续期缴费响应报文 -->
			<Main>
				<ResultCode><xsl:value-of select="Insut/Maina/ResultCode" /></ResultCode>
				<ResultInfo><xsl:value-of select="Insut/Maina/ResultInfo" /></ResultInfo>
			</Main>
		</InsuRet>
	</xsl:template>
	
</xsl:stylesheet>
