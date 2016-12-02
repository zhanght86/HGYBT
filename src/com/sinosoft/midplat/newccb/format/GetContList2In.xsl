<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">

	<xsl:template match="TX">
		<TranData>
			<Head>
				<!--交易日期 -->
				<TranDate>
					<xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,0,8)" />
				</TranDate>
				<!--交易时间 -->
				<TranTime>
					<xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,8,14)" />
				</TranTime>
				<!-- 银行网点 -->
				<NodeNo>
					<xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/CCBIns_ID" />
				</NodeNo>
				<!-- 银行编码 -->
				<BankCode>0104</BankCode>
				<!--柜员号 -->
				<TellerNo>
					<xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/CCB_EmpID" />
				</TellerNo>
				<!-- 交易流水号 -->
				<TranNo>
					<xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/SvPt_Jrnl_No" />
				</TranNo>
				<xsl:copy-of select="Head/*"/>
			</Head>
			<!-- 报文体 -->
			<xsl:apply-templates select="TX_BODY/ENTITY/APP_ENTITY" />
		</TranData>
	</xsl:template>

	<!-- 报文体 -->
	<xsl:template match="TX_BODY/ENTITY/APP_ENTITY">
		<Body>
			<!-- #批量包名称 -->
			<BagName>
				<xsl:value-of select="AgIns_BtchBag_Nm" />
			</BagName>
			<!-- #查询日期 -->
			<QryStrDate></QryStrDate>
		</Body>
	</xsl:template>


</xsl:stylesheet>

