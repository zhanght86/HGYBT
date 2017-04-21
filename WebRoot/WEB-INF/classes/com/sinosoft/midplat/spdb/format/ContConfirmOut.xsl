<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
<xsl:template match="/TranData">
<RETURN>
	<ACKSTS>0</ACKSTS>
	<STSDESC>正常</STSDESC>
	<BUSI>
	  	<SUBJECT>1</SUBJECT>
	  	<TRANS><xsl:value-of select ="Head/TranNo"/></TRANS>
	  	<CONTENT>
	  		<xsl:if test ="Head/Flag !=0">
				<REJECT_CODE><xsl:value-of select ="Head/Flag"/></REJECT_CODE>
		  		<REJECT_DESC><xsl:value-of select ="Head/Desc"/></REJECT_DESC>
	  		</xsl:if>
	  		<xsl:if test ="Head/Flag =0">
				<REJECT_CODE>0000</REJECT_CODE>
		  		<REJECT_DESC>交易成功</REJECT_DESC>
	  		</xsl:if>
		</CONTENT>
	</BUSI>
</RETURN>
</xsl:template>
</xsl:stylesheet>