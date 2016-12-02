<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
	<xsl:template match="/">
		<Transaction>
			<!--报文体-->
			<xsl:if test="/TranData/Head/Flag='0'">
			<Transaction_Body>
			
				<BkNum1><xsl:value-of select="/TranData/Body/BatIncome"/></BkNum1>
				<BkNum2><xsl:value-of select="/TranData/Body/BatPay"/></BkNum2>
			</Transaction_Body>
			</xsl:if>
		</Transaction>
	</xsl:template>
</xsl:stylesheet>
