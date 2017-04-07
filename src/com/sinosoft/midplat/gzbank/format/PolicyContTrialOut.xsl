<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
 	exclude-result-prefixes="java">
<xsl:template match="/TranData">
<TXLife>
	<TranTime><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur8Time()"/></TranTime>
	<CpicWater></CpicWater>
	<ResultCode>0<xsl:value-of select ="Head/Flag"/></ResultCode>
	<ResultInfoDesc><xsl:value-of select ="Head/Desc"/></ResultInfoDesc>
</TXLife>
</xsl:template>
</xsl:stylesheet>