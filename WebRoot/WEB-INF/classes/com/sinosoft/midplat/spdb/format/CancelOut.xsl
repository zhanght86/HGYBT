<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
<xsl:template match="/">
<!-- 当日撤单 应答样式表 -->
<RETURN>
	<ACKSTS>0</ACKSTS>
	<STSDESC>正常</STSDESC>
	<BUSI>
		<SUBJECT>1</SUBJECT>
		<TRANS><xsl:value-of select="TranData/Body/TranNo"/></TRANS>
		<CONTENT>
			<!-- 如果成功，返回下面节点 -->
			<xsl:if test="TranData/Head/Flag='0'">
				<INSU_TRANS></INSU_TRANS>
				<APPNO><xsl:value-of select="TranData/Body/ContNo"/></APPNO>			
				<REJECT_CODE>0000</REJECT_CODE>
				<REJECT_DESC>反交易成功!</REJECT_DESC>
				<MAIN></MAIN>				
			</xsl:if>
			<!-- 如果失败，返回下面节点 -->
			<xsl:if test="/TranData/Head/Flag!='0'">
				<REJECT_CODE>1234</REJECT_CODE>
				<REJECT_DESC><xsl:value-of select="TranData/Head/Desc" /></REJECT_DESC>
			</xsl:if>
		</CONTENT>
	</BUSI>
</RETURN>
</xsl:template>
</xsl:stylesheet>