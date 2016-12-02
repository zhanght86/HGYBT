<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

<xsl:template match="TranData">
 <ABCB2I><!-- 农业银行取消交易应答报文 -->
 <App>
     <Ret>
	      <OrgSerialNo></OrgSerialNo>
	      <OrgTransDate></OrgTransDate>
	      <TransCode></TransCode>
	  	</Ret>
 </App>
</ABCB2I>
</xsl:template>

</xsl:stylesheet>