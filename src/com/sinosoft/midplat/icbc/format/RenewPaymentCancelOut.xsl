<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

<xsl:template match="/">
<TXLife>
	<xsl:copy-of select="TranData/Head"/>
	<TXLifeResponse>
	<!-- 交易流水号 -->
	<TransRefGUID><xsl:value-of select="TranData/Body/TranNo"/></TransRefGUID>
	<!-- 交易码/处理标志 -->
	<TransType><xsl:value-of select="TranData/Body/FuncFlag"/></TransType>
		
 	 <TransExeDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/></TransExeDate> 
 	 <TransExeTime><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur8Time()"/></TransExeTime> 
 	 <TransResult>
			<ResultCode><xsl:value-of select="TranData/Head/Flag"/></ResultCode>
			<ResultInfo>
				<ResultInfoDesc><xsl:value-of select="TranData/Head/Desc"/></ResultInfoDesc>
			</ResultInfo>
		</TransResult>
		<OLifEExtension VendorCode="1">
			<TransNo><xsl:value-of select="TranData/Body/FuncFlag"/></TransNo>
			<!-- 交易代码 -->
		</OLifEExtension>
	</TXLifeResponse>    
</TXLife> 
</xsl:template>   
</xsl:stylesheet>
