<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes" />
	<xsl:template match="/">
		<SRCB>
			<!--报文体-->
			<xsl:if test="/TranData/Head/Flag='0'">
			<Body>
			    <!-- 保费 (单位：分) -->
				<Prem><xsl:value-of select="TranData/Body/Prem" /></Prem>
				<!-- 保单号 -->
				<PolicyNo><xsl:value-of	select="TranData/Body/ContNo" /></PolicyNo>
			</Body>
			</xsl:if>
		</SRCB>
	</xsl:template>
	
</xsl:stylesheet>
