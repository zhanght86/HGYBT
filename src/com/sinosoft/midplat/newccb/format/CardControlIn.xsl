<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
	<xsl:template match="TX">
		<!--重空核对-->
		<TranData>
				<Head>
				     <!--交易日期 -->
					<TranDate><xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,0,8)" /></TranDate>
					<!--交易时间 -->
					<TranTime><xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,8,14)" /></TranTime>
					<!-- 银行网点 -->
					<NodeNo><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/CCBIns_ID" /></NodeNo>
					<!-- 银行编码 -->
					<BankCode>0104</BankCode>
					<!--柜员号 -->
					<TellerNo><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/CCB_EmpID" /></TellerNo>
					<!-- 交易流水号 -->
					<TranNo><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/SvPt_Jrnl_No" /></TranNo>
					<!-- 银行端ip[非必须] 
					<ClientIp>127.0.0.1</ClientIp> -->
					<!-- 交易渠道 
					<TranCom>03</TranCom> -->
					<!-- 交易类型 -->
			 <!-- <FuncFlag><xsl:value-of select="TX_HEADER/SYS_TX_CODE" /></FuncFlag>  -->	
					<!-- 服务id 
					<ServiceId>0</ServiceId> -->
				<xsl:copy-of select="Head/*"/>
				</Head>

			<Body>
				<!-- 保险重空类型编码 
				<CardType>
					<xsl:call-template name="tran_type">
						<xsl:with-param name="Type">
							<xsl:value-of select="TX_BODY/ENTITY/APP_ENTITY/Ins_IBVoch_Tp_ECD" />
						</xsl:with-param>
					</xsl:call-template>
				</CardType>-->
				<!-- 保险重空类型编码 -->
				<CardType>
							<xsl:value-of select="TX_BODY/ENTITY/APP_ENTITY/Ins_IBVoch_Tp_ECD" />
				</CardType>
				<!-- 保险重空起始案号 -->
				<StartNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(TX_BODY/ENTITY/APP_ENTITY/Ins_IBVoch_Beg_ID)"/></StartNo>
				<!-- 保险重空结束案号 -->
				<EndNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(TX_BODY/ENTITY/APP_ENTITY/Ins_IBVoch_End_ID)"/></EndNo>
			</Body>
		</TranData>
	</xsl:template>
	
	<!-- 保险重空类型编码 -->
	<xsl:template name="tran_type" >
		<xsl:param name="Type"></xsl:param>
		<xsl:if test="$Type = '010072000001'">2104141</xsl:if><!-- 投保单号 -->
		<xsl:if test="$Type = '010072000002'">0101141</xsl:if><!-- 保单印刷号 -->
	</xsl:template>
	
</xsl:stylesheet>
