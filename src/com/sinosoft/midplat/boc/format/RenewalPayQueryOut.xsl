<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes" />
	<xsl:template match="TranData">
		<InsuRet><!-- 中国银行续期缴费查询响应报文 -->
			<Main>
				<xsl:if test="Head/Flag='0'">
					<ResultCode>0000</ResultCode>
				</xsl:if>
				<xsl:if test="Head/Flag='1'">
					<ResultCode>0001</ResultCode>
				</xsl:if>
				<ResultInfo><xsl:value-of select="Head/Desc" /></ResultInfo>
				<PolicyNo><xsl:value-of select="Body/ContNo" /></PolicyNo>
				<RecvDate><xsl:value-of select="Body/RecvDate" /></RecvDate>
				<RecvAmount><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Body/RecvAmount)" /></RecvAmount>
				<RecvNum><xsl:value-of select="Body/RecvNum" /></RecvNum>
				<PayStartDate><xsl:value-of select="Body/PayStartDate" /></PayStartDate>
				<PayEndDate><xsl:value-of select="Body/PayEndDate" /></PayEndDate>
				<AppntName><xsl:value-of select="Body/Name" /></AppntName>
				<RiskName><xsl:value-of select="Body/PlanName" /></RiskName>
			</Main>
		</InsuRet>
	</xsl:template>
	
</xsl:stylesheet>
