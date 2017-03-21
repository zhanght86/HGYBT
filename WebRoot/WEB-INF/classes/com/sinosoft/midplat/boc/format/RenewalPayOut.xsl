<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes" />
	<xsl:template match="TranData">
		<InsuRet><!-- 中国银行续期缴费响应报文 -->
			<Main>
				<xsl:if test="Head/Flag='0'">
					<ResultCode>0000</ResultCode>
				</xsl:if>
				<xsl:if test="Head/Flag='1'">
					<ResultCode>0001</ResultCode>
				</xsl:if>
				<ResultInfo><xsl:value-of select="Head/Desc" /></ResultInfo>
			</Main>
		</InsuRet>
	</xsl:template>
	
</xsl:stylesheet>
