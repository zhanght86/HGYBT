<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

<xsl:template match="/">
<TXLife>
	
	<xsl:copy-of select="TranData/Head" />
	<TXLifeResponse>
    
 	 <TransExeDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/></TransExeDate> 
 	 <TransExeTime><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur8Time()"/></TransExeTime> 
	<OLifE>
			<Holding>
				<Policy>
					<PolNumber><xsl:value-of select="TranData/Body/PolNumber"/></PolNumber>
				</Policy>
			</Holding>
		</OLifE>
</TXLifeResponse> 
</TXLife>
</xsl:template>

</xsl:stylesheet>