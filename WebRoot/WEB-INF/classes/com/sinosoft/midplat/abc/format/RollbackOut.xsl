<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

<xsl:template match="TranData">
<Ret>
		<RetData>
	  		<Flag><xsl:value-of select ="Head/Flag"/></Flag>
	  		<Mesg><xsl:value-of select ="Head/Desc"/></Mesg>
	  	</RetData>
</Ret>
</xsl:template>

</xsl:stylesheet>