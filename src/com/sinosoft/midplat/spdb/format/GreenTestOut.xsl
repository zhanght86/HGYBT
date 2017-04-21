<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
<xsl:template match="/">
<!--绿灯 返回样式表 -->
<RETURN>
	<ACKSTS>0</ACKSTS>
	<STSDESC>正常</STSDESC>
	<BUSI>
	    <SUBJECT>1</SUBJECT>
		<TRANS><xsl:value-of select="TranData/Body/TranNo"/></TRANS>
		<CONTENT>
			<REJECT_CODE>0000</REJECT_CODE>
			<REJECT_DESC>处理成功!</REJECT_DESC>
		</CONTENT>
	</BUSI>
</RETURN>
</xsl:template>
</xsl:stylesheet>